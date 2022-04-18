package siitnocu.bezbednost.services;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.text.Normalizer.Form;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;


import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siitnocu.bezbednost.certificates.CertificateGenerator;
import siitnocu.bezbednost.data.CertificateDTO;
import siitnocu.bezbednost.data.ExtensionsDTO;
import siitnocu.bezbednost.data.IssuerData;
import siitnocu.bezbednost.data.SubjectData;
import siitnocu.bezbednost.dto.CsrDTO;
import siitnocu.bezbednost.repositories.CsrInfoRepository;

@Service
public class KeyStoreService {


	private static final String KEY_STORE = "keystore.jks";
	private static final String KEY_STORE_PASSWORD = "pass";
	
	@Autowired
	private KeyStoreReaderService keyStoreReaderService;
	
    @Autowired
	private KeyStoreWriterService keyStoreWriterService;

    @Autowired
    private CsrInfoRepository csrInfoRepository;

    public void createNewSelfSignedCertificate() throws IOException, NoSuchAlgorithmException {
    	KeyPair keyPairIssuer = generateKeyPair();
    	
    	SubjectData subjectData = generateSubjectDataForRoot(keyPairIssuer.getPublic());
        IssuerData issuerData = generateIssuerDataForRoot(keyPairIssuer.getPrivate());
        
        CertificateGenerator cg = new CertificateGenerator();
        X509Certificate cert = cg.generateCertificate(subjectData, issuerData, null);

        X509Certificate[] chain = new X509Certificate[1];
        chain[0]=cert;

        keyStoreWriterService.write(KEY_STORE,"root-certificate", keyPairIssuer.getPrivate(), KEY_STORE_PASSWORD.toCharArray(), chain);
    }
    
    private IssuerData generateIssuerDataForRoot(PrivateKey issuerKey) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Alkeksandar Cepic");
        builder.addRDN(BCStyle.SURNAME, "Aleksandar");
        builder.addRDN(BCStyle.GIVENNAME, "Cepic");
        builder.addRDN(BCStyle.O, "siit-nocu");
        builder.addRDN(BCStyle.OU, "Katedra za informatiku");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "aleksandar.epic@gmail.com");

        // UID (USER ID) je ID korisnika
        builder.addRDN(BCStyle.UID, "220699");

        // Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
        // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
        // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
        return new IssuerData(issuerKey, builder.build());
    }

    private SubjectData generateSubjectDataForRoot(PublicKey publicKey) {
        try {
            // Datumi od kad do kad vazi sertifikat
            SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = iso8601Formater.parse("2021-12-31");
            Date endDate = iso8601Formater.parse("2025-12-31");

            // Serijski broj sertifikata
            String sn = "1";

            // klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
            X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
            builder.addRDN(BCStyle.CN, "Aleksandar Cepic");
            builder.addRDN(BCStyle.SURNAME, "Aleksandar");
            builder.addRDN(BCStyle.GIVENNAME, "Cepic");
            builder.addRDN(BCStyle.O, "siit-nocu");
            builder.addRDN(BCStyle.OU, "Katedra za informatiku");
            builder.addRDN(BCStyle.C, "RS");
            builder.addRDN(BCStyle.E, "aleksandar.epic@gmail.com");

            // Kreiraju se podaci za sertifikat, sto ukljucuje:
            // - javni kljuc koji se vezuje za sertifikat
            // - podatke o vlasniku
            // - serijski broj sertifikata
            // - od kada do kada vazi sertifikat
            return new SubjectData(publicKey, builder.build(), sn, startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<CertificateDTO> getAllCertificates() throws KeyStoreException, NoSuchProviderException, CertificateException, IOException, NoSuchAlgorithmException {
        List<CertificateDTO> certificates = new ArrayList<>();
        KeyStore ks = KeyStore.getInstance("JKS", "SUN");

        // ucitavamo podatke
        File file = new File(KEY_STORE);
        InputStream is = new FileInputStream(file);
        ks.load(is, KEY_STORE_PASSWORD.toCharArray());

        Enumeration<String> enumeration = ks.aliases();
        while(enumeration.hasMoreElements()) {
            String alias = enumeration.nextElement();
            X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);
            String subjectDomainName = certificate.getSubjectDN().getName();
            String issuerDomainName = certificate.getIssuerDN().getName();
            String sigAlgName = certificate.getSigAlgName();
            int serial = certificate.getSerialNumber().intValue();
            RSAPublicKey rsaPk = (RSAPublicKey) certificate.getPublicKey();
            int keySize = rsaPk.getModulus().bitLength();
            Date dateTo = certificate.getNotAfter();
            Date dateFrom = certificate.getNotBefore();
            int version = certificate.getVersion();
            String format = rsaPk.getAlgorithm();


            certificates.add(new CertificateDTO(alias, sigAlgName, keySize, dateTo, dateFrom, subjectDomainName, issuerDomainName, version, serial, format));
        }

        return certificates;
    }

	public List<CertificateDTO> getAllRevokedCertificates() throws KeyStoreException, NoSuchProviderException, CertificateException, IOException, NoSuchAlgorithmException {
		List<CertificateDTO> certificates = new ArrayList<>();
		KeyStore ks = KeyStore.getInstance("JKS", "SUN");

		// ucitavamo podatke
		File file = new File("keystore-deleted.jks");
		InputStream is = new FileInputStream(file);
		ks.load(is, KEY_STORE_PASSWORD.toCharArray());

		Enumeration<String> enumeration = ks.aliases();
		while(enumeration.hasMoreElements()) {
			String alias = enumeration.nextElement();
			X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);
			String subjectDomainName = certificate.getSubjectDN().getName();
			String issuerDomainName = certificate.getIssuerDN().getName();
			String sigAlgName = certificate.getSigAlgName();
			int serial = certificate.getSerialNumber().intValue();
			RSAPublicKey rsaPk = (RSAPublicKey) certificate.getPublicKey();
			int keySize = rsaPk.getModulus().bitLength();
			Date dateTo = certificate.getNotAfter();
			Date dateFrom = certificate.getNotBefore();
			int version = certificate.getVersion();
			String format = rsaPk.getAlgorithm();


			certificates.add(new CertificateDTO(alias, sigAlgName, keySize, dateTo, dateFrom, subjectDomainName, issuerDomainName, version, serial, format));
		}

		return certificates;
	}

    public ExtensionsDTO getExtensionsForCertificate(String alias) throws KeyStoreException, NoSuchProviderException, CertificateException, NoSuchAlgorithmException, InvalidNameException, IOException {
    	X509Certificate cert = getFullCertificate(alias);
    	List<String> keyUsages = new ArrayList<String>();
    	List<String> extendedKeyUsages = new ArrayList<String>();
    	List<String> subjectAlternativeNames = new ArrayList<String>();
    	String ski = null;
    	String aki = null;
    	int basicConstraint = cert.getBasicConstraints();
    	if(cert.getExtensionValue("2.5.29.14") != null) {
    		ASN1Primitive subjectExtensionValue = JcaX509ExtensionUtils
                    .parseExtensionValue(cert.getExtensionValue("2.5.29.14"));
            ski = subjectExtensionValue.toString();
    	}
    	if(cert.getExtensionValue("2.5.29.35") != null) {
    		ASN1Primitive authorityExtensionValue = JcaX509ExtensionUtils
                    .parseExtensionValue(cert.getExtensionValue("2.5.29.35"));
            aki = authorityExtensionValue.toString();

    	}

    	if(cert.getKeyUsage() != null) {
    		for(int i=0;i<cert.getKeyUsage().length;i++) {
        		switch (i) {
    			case 0:
    				if(cert.getKeyUsage()[i] == true) {
    					keyUsages.add("Digital Signature");
    				}
    				break;
    			case 1:
    				if(cert.getKeyUsage()[i] == true) {
    					keyUsages.add("Non Repudiation");
    				}
    				break;
    			case 2:
    				if(cert.getKeyUsage()[i] == true) {
    					keyUsages.add("Key Encipherment");
    				}
    				break;
    			case 3:
    				if(cert.getKeyUsage()[i] == true) {
    					keyUsages.add("Data Encipherment");
    				}
    				break;
    			case 4:
    				if(cert.getKeyUsage()[i] == true) {
    					keyUsages.add("Key Agreement");
    				}
    				break;
    			case 5:
    				if(cert.getKeyUsage()[i] == true) {
    					keyUsages.add("Key Cert Sign");
    				}
    				break;
    			case 6:
    				if(cert.getKeyUsage()[i] == true) {
    					keyUsages.add("CRL Sign");
    				}
    				break;
    			case 7:
    				if(cert.getKeyUsage()[i] == true) {
    					keyUsages.add("Encipher Only");
    				}
    				break;
    			case 8:
    				if(cert.getKeyUsage()[i] == true) {
    					keyUsages.add("Decipher Only");
    				}
    				break;
    			}
        		
        	}
    	}
    	
    	if(cert.getExtendedKeyUsage() != null) {
    		for (String eku : cert.getExtendedKeyUsage()) {
    			switch (eku.trim()) {
    			case "2.5.29.37.0":
    				extendedKeyUsages.add("Any Extended Key Usage " + eku.trim());
    				break;
    			case "1.3.6.1.5.5.7.3.3":
    				extendedKeyUsages.add("Code Signing " + eku.trim());
    				break;
    			case "1.3.6.1.5.5.7.3.4":
    				extendedKeyUsages.add("E-mail Protection " + eku.trim());
    				break;
    			case "1.3.6.1.5.5.7.3.5":
    				extendedKeyUsages.add("IP Security End System " + eku.trim());
    				break;
    			case "1.3.6.1.5.5.7.3.6":
    				extendedKeyUsages.add("IP Security Tunnel Termination " + eku.trim());
    				break;
    			case "1.3.6.1.5.5.7.3.7":
    				extendedKeyUsages.add("IP Security User " + eku.trim());
    				break;
    			case "1.3.6.1.5.5.7.3.9":
    				extendedKeyUsages.add("OCSP Signing " + eku.trim());
    				break;
    			case "1.3.6.1.4.1.311.20.2.2":
    				extendedKeyUsages.add("Smartcard Logon " + eku.trim());
    				break;
    			case "1.3.6.1.5.5.7.3.8":
    				extendedKeyUsages.add("Time Stamping " + eku.trim());
    				break;
    			case "1.3.6.1.5.5.7.3.2":
    				extendedKeyUsages.add("TLS Web Client Authentication " + eku.trim());
    				break;
    			default:
    				extendedKeyUsages.add("TLS Web Server Authentication " + eku.trim());
    				break;
    			}
    		}
    	}
    	
    	
    	if(cert.getSubjectAlternativeNames() != null) {
    		for (List<?> san : cert.getSubjectAlternativeNames()) {
        		if(san.get(0).toString().equals("0")) {
    				subjectAlternativeNames.add("Other Name: " + san.get(1).toString());
    			}
    			if(san.get(0).toString().equals("1")) {
    				subjectAlternativeNames.add("RFC822 Name: " + san.get(1).toString());
    			}
    			if(san.get(0).toString().equals("2")) {
    				subjectAlternativeNames.add("DNS Name: " + san.get(1).toString());
    			}
    			if(san.get(0).toString().equals("3")) {
    				subjectAlternativeNames.add("X400 Address: " + san.get(1).toString());
    			}
    			if(san.get(0).toString().equals("4")) {
    				subjectAlternativeNames.add("Directory Name: " + san.get(1).toString());
    			}
    			if(san.get(0).toString().equals("5")) {
    				subjectAlternativeNames.add("Edi Party Name: " + san.get(1).toString());
    			}
    			if(san.get(0).toString().equals("6")) {
    				subjectAlternativeNames.add("Uniform Resource Identifier: " + san.get(1).toString());
    			}
    			if(san.get(0).toString().equals("7")) {
    				subjectAlternativeNames.add("IP Address: " + san.get(1).toString());
    			}
    			if(san.get(0).toString().equals("8")) {
    				subjectAlternativeNames.add("registered ID: " + san.get(1).toString());
    			}
    		}
    	}
    	
    	boolean ekucrit = false;
    	boolean kucrit = false; //
    	boolean sancrit = false; //
    	boolean bccrit = false; //
    	boolean akicrit = false; //
    	boolean skicrit = false; //
    	if(cert.getCriticalExtensionOIDs() != null) {
    		for (String crit : cert.getCriticalExtensionOIDs()) {
    			switch (crit) {
    			case "2.5.29.14":
    				skicrit = true;
    				break;
    			case "2.5.29.15":
    				kucrit = true;
    				break;
    			case "2.5.29.17":
    				sancrit = true;
    				break;
    			case "2.5.29.19":
    				bccrit = true;
    				break;
    			case "2.5.29.35":
    				akicrit = true;
    				break;
    			case "2.5.29.37":
    				ekucrit = true;
    				break;
    			}
    		}
    	}
    	
    	return new ExtensionsDTO(extendedKeyUsages, keyUsages, subjectAlternativeNames, basicConstraint, aki, ski, ekucrit, kucrit, sancrit, bccrit, akicrit, skicrit);
    }
    
    
    public CertificateDTO getCertificate(String name) throws KeyStoreException, NoSuchProviderException, CertificateException, IOException, NoSuchAlgorithmException, InvalidNameException {
        List<CertificateDTO> certificates = new ArrayList<>();
        KeyStore ks = KeyStore.getInstance("JKS", "SUN");

        // ucitavamo podatke
        File file = new File(KEY_STORE);
        InputStream is = new FileInputStream(file);
        ks.load(is, KEY_STORE_PASSWORD.toCharArray());

        Enumeration<String> enumeration = ks.aliases();
        while(enumeration.hasMoreElements()) {
            String alias = enumeration.nextElement();
            if(alias.equals(name)) {
            	X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);
                String subjectDomainName = certificate.getSubjectDN().getName();
                String issuerDomainName = certificate.getIssuerDN().getName();
                String sigAlgName = certificate.getSigAlgName();
                int serial = certificate.getSerialNumber().intValue();
                RSAPublicKey rsaPk = (RSAPublicKey) certificate.getPublicKey();
                int keySize = rsaPk.getModulus().bitLength();
                Date dateTo = certificate.getNotAfter();
                Date dateFrom = certificate.getNotBefore();
                int version = certificate.getVersion();
                String format = rsaPk.getAlgorithm();
                
                return new CertificateDTO(alias, sigAlgName, keySize, dateTo, dateFrom, subjectDomainName, issuerDomainName, version, serial, format);
            }
        }
		return null;
    }
    
    public X509Certificate getFullCertificate(String name) throws KeyStoreException, NoSuchProviderException, CertificateException, IOException, NoSuchAlgorithmException, InvalidNameException {
        KeyStore ks = KeyStore.getInstance("JKS", "SUN");

        // ucitavamo podatke
        File file = new File(KEY_STORE);
        InputStream is = new FileInputStream(file);
        ks.load(is, KEY_STORE_PASSWORD.toCharArray());

        Enumeration<String> enumeration = ks.aliases();
        while(enumeration.hasMoreElements()) {
            String alias = enumeration.nextElement();
            if(alias.equals(name)) {
            	X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);
                return certificate;
                
            }
        }
		return null;
    }
    
    
    public List<X509Certificate> getAllCertificatesObjects() throws KeyStoreException, NoSuchProviderException, CertificateException, IOException, NoSuchAlgorithmException {
        List<X509Certificate> certificates = new ArrayList<>();
        KeyStore ks = KeyStore.getInstance("JKS", "SUN");

        // ucitavamo podatke
        File file = new File(KEY_STORE);
        InputStream is = new FileInputStream(file);
        ks.load(is, KEY_STORE_PASSWORD.toCharArray());

        Enumeration<String> enumeration = ks.aliases();
        while(enumeration.hasMoreElements()) {
            String alias = enumeration.nextElement();
            java.security.cert.X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);
            certificates.add(certificate);
        }

        return certificates;
    }
    
    public boolean isCertificateValid(String alias) throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, IOException {
    	KeyStore ks = KeyStore.getInstance("JKS", "SUN");

        File file = new File(KEY_STORE);
        InputStream is = new FileInputStream(file);
        ks.load(is, KEY_STORE_PASSWORD.toCharArray());
        
        if(ks.getCertificate(alias) == null) return false;
        
        java.security.cert.Certificate[] chain = ks.getCertificateChain(alias);
        
        Date today = new Date();
        int index = 0;
        for(java.security.cert.Certificate cert: chain) {
        	
        	if(((X509Certificate) cert).getNotAfter().before(today)) {
                return false;
            }
        	if(((X509Certificate) cert).getNotBefore().after(today)) return false;
            try {
                if(index + 1 == chain.length){
                    cert.verify(cert.getPublicKey());
                }
                else{
                    cert.verify(chain[index+1].getPublicKey());
                    index++;
                }
            } catch (InvalidKeyException e) {
                return false;
            } catch (SignatureException e) {
                return false;
            }

        }
    	
    	return true;
    }

    public List<CsrDTO> getAllCsrs() {
        return csrInfoRepository.findAll().stream().map(CsrDTO::new).collect(Collectors.toList());
    }
}
