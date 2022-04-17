package siitnocu.bezbednost.services;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.text.ParseException;
import java.util.List;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siitnocu.bezbednost.certificates.CertificateGenerator;
import siitnocu.bezbednost.data.IssuerData;
import siitnocu.bezbednost.data.SubjectData;

@Service
public class KeyStoreService {


	private static final String KEY_STORE = "keystore.jks";
	private static final String KEY_STORE_PASSWORD = "pass";
	
	@Autowired
	private KeyStoreReaderService keyStoreReaderService;
	
    @Autowired
	private KeyStoreWriterService keyStoreWriterService;
	
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
    
    public List<String> getAllCertificates() throws KeyStoreException, NoSuchProviderException, CertificateException, IOException, NoSuchAlgorithmException {
        List<String> certificates = new ArrayList<>();
        KeyStore ks = KeyStore.getInstance("JKS", "SUN");

        // ucitavamo podatke
        File file = new File(KEY_STORE);
        InputStream is = new FileInputStream(file);
        ks.load(is, KEY_STORE_PASSWORD.toCharArray());

        Enumeration<String> enumeration = ks.aliases();
        while(enumeration.hasMoreElements()) {
            String alias = enumeration.nextElement();
            System.out.println("alias name: " + alias);
            java.security.cert.Certificate certificate = ks.getCertificate(alias);
            System.out.println(certificate.toString());
            certificates.add("alias name: " + alias);
        }

        return certificates;
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
}
