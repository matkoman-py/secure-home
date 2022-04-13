package siitnocu.bezbednost.services;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.stream.Collectors;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import siitnocu.bezbednost.utils.CSRHandler;
import siitnocu.bezbednost.utils.CertificateInfo;


@Service
@Transactional
public class TestService implements ITestService{


	private static final String BC_PROVIDER = "BC";
	private static final String KEY_ALGORITHM = "RSA";
	private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
	private static final String KEY_STORE = "root-cert.jks";
	private static final String KEY_STORE_TYPE = "PKCS12";
	private static final String KEY_STORE_PASSWORD = "pass";
	@Autowired
	public TestService() {
		
	}
	

	@Override
	public String generateCSR(CertificateInfo csrInfo) throws NoSuchAlgorithmException, OperatorCreationException, IOException, KeyStoreException, CertificateException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		KeyPair pair = kpg.generateKeyPair();
		String info = "CN="+csrInfo.getDomainName()+", O="+csrInfo.getOrganizationName()+", OU="+csrInfo.getOrganizationUnit()+", L="+csrInfo.getCity()+", S="+csrInfo.getState()+", C="+csrInfo.getCountry()+", EMAIL="+csrInfo.getEmail();
		PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(
		    new X500Principal(info), pair.getPublic());
		JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
		ContentSigner signer = csBuilder.build(pair.getPrivate());
		org.bouncycastle.pkcs.PKCS10CertificationRequest csr = p10Builder.build(signer);
		PemObject pemObject = new PemObject("CERTIFICATE REQUEST", csr.getEncoded());
		StringWriter str = new StringWriter();
		PEMWriter pemWriter = new PEMWriter(str);
		pemWriter.writeObject(pemObject);
		pemWriter.close();
		str.close();
		
		
		//==================
		
		File file = new File("tutorial.jks");
        InputStream is = new FileInputStream(file);
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        String password = "petar123";
        keystore.load(is, password.toCharArray());


        Enumeration<String> enumeration = keystore.aliases();
        while(enumeration.hasMoreElements()) {
            String alias = enumeration.nextElement();
            System.out.println("alias name: " + alias);
            java.security.cert.Certificate certificate = keystore.getCertificate(alias);
            System.out.println(certificate.toString());
            
        }
        
        
        //==================
		return str.toString();
		
	}
	
	@Override
	public CertificateInfo decodeCSR(String csrString) {
		// TODO Auto-generated method stub
		PKCS10CertificationRequest csr = CSRHandler.convertPemToPKCS10CertificationRequest(csrString);
        X500Name x500Name = csr.getSubject();
        
        //csr.getSubjectPublicKeyInfo().getPublicKey()
        
        String[] csrDecoded = x500Name.toString().split(",");
        CertificateInfo ci = new CertificateInfo();
        ci.setEmail(csrDecoded[0].split("=")[1]);
        ci.setCountry(csrDecoded[1].split("=")[1]);
        ci.setState(csrDecoded[2].split("=")[1]);
        ci.setCity(csrDecoded[3].split("=")[1]);
        ci.setOrganizationUnit(csrDecoded[4].split("=")[1]);
        ci.setOrganizationName(csrDecoded[5].split("=")[1]);
        ci.setDomainName(csrDecoded[6].split("=")[1]);
   
        return ci;
	}

	public void generateRootCertificate() throws Exception {
		// Add the BouncyCastle Provider
		Security.addProvider(new BouncyCastleProvider());

		// Initialize a new KeyPair generator
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM, BC_PROVIDER);
		keyPairGenerator.initialize(2048);

		// Setup start date to yesterday and end date for 1 year validity
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date startDate = calendar.getTime();

		calendar.add(Calendar.YEAR, 1);
		Date endDate = calendar.getTime();

		// First step is to create a root certificate
		// First Generate a KeyPair,
		// then a random serial number
		// then generate a certificate using the KeyPair
		KeyPair rootKeyPair = keyPairGenerator.generateKeyPair();
		BigInteger rootSerialNum = new BigInteger(Long.toString(new SecureRandom().nextLong()));

		// Issued By and Issued To same for root certificate
		X500Name rootCertIssuer = new X500Name("CN=root-cert");
		X500Name rootCertSubject = rootCertIssuer;
		ContentSigner rootCertContentSigner = new JcaContentSignerBuilder(SIGNATURE_ALGORITHM).setProvider(BC_PROVIDER).build(rootKeyPair.getPrivate());
		X509v3CertificateBuilder rootCertBuilder = new JcaX509v3CertificateBuilder(rootCertIssuer, rootSerialNum, startDate, endDate, rootCertSubject, rootKeyPair.getPublic());

		// Add Extensions
		// A BasicConstraint to mark root certificate as CA certificate
		JcaX509ExtensionUtils rootCertExtUtils = new JcaX509ExtensionUtils();
		rootCertBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));
		rootCertBuilder.addExtension(Extension.subjectKeyIdentifier, false, rootCertExtUtils.createSubjectKeyIdentifier(rootKeyPair.getPublic()));

		// Create a cert holder and export to X509Certificate
		X509CertificateHolder rootCertHolder = rootCertBuilder.build(rootCertContentSigner);
		X509Certificate rootCert = new JcaX509CertificateConverter().setProvider(BC_PROVIDER).getCertificate(rootCertHolder);

		writeCertToFileBase64Encoded(rootCert, "root-cert.cer");
		exportKeyPairToKeystoreFile(rootKeyPair, rootCert, "root-cert", "root-cert.jks", "PKCS12", "pass");
	}

	public X509Certificate signCSR() throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date startDate = calendar.getTime();

		calendar.add(Calendar.YEAR, 1);
		Date endDate = calendar.getTime();


		KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE);
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(KEY_STORE));
		keyStore.load(in, KEY_STORE_PASSWORD.toCharArray());
		PrivateKey privateKeyIssuer = (PrivateKey) keyStore.getKey("root-cert", "pass".toCharArray());
		PublicKey publicKeyIssuer = keyStore.getCertificate("root-cert").getPublicKey();

		//ovo se treba zameniti sa pravim iz csr
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM, BC_PROVIDER);
		keyPairGenerator.initialize(2048);
		KeyPair issuedCertKeyPair = keyPairGenerator.generateKeyPair();

		X500Name issuedCertSubject = new X500Name("CN=issued-cert");
		BigInteger issuedCertSerialNum = new BigInteger(Long.toString(new SecureRandom().nextLong()));

		PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(issuedCertSubject, publicKeyIssuer);
		JcaContentSignerBuilder csrBuilder = new JcaContentSignerBuilder(SIGNATURE_ALGORITHM).setProvider(BC_PROVIDER);

		// Sign the new KeyPair with the root cert Private Key
		ContentSigner csrContentSigner = csrBuilder.build(privateKeyIssuer);
		PKCS10CertificationRequest csr = p10Builder.build(csrContentSigner);

		// Use the Signed KeyPair and CSR to generate an issued Certificate
		// Here serial number is randomly generated. In general, CAs use
		// a sequence to generate Serial number and avoid collisions
		X509v3CertificateBuilder issuedCertBuilder = new X509v3CertificateBuilder(new X500Name("CN=root-cert"), issuedCertSerialNum, startDate, endDate, csr.getSubject(), csr.getSubjectPublicKeyInfo());

		JcaX509ExtensionUtils issuedCertExtUtils = new JcaX509ExtensionUtils();

		// Add Extensions
		// Use BasicConstraints to say that this Cert is not a CA
		issuedCertBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(false));

		// Add Issuer cert identifier as Extension
		issuedCertBuilder.addExtension(Extension.authorityKeyIdentifier, false, issuedCertExtUtils.createAuthorityKeyIdentifier(keyStore.getCertificate("root-cert").getPublicKey()));
		issuedCertBuilder.addExtension(Extension.subjectKeyIdentifier, false, issuedCertExtUtils.createSubjectKeyIdentifier(csr.getSubjectPublicKeyInfo()));

		// Add intended key usage extension if needed
		issuedCertBuilder.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.keyEncipherment));

		// Add DNS name is cert is to used for SSL
		issuedCertBuilder.addExtension(Extension.subjectAlternativeName, false, new DERSequence(new ASN1Encodable[] {
				new GeneralName(GeneralName.dNSName, "mydomain.local"),
				new GeneralName(GeneralName.iPAddress, "127.0.0.1")
		}));

		X509CertificateHolder issuedCertHolder = issuedCertBuilder.build(csrContentSigner);
		X509Certificate issuedCert  = new JcaX509CertificateConverter().setProvider(BC_PROVIDER).getCertificate(issuedCertHolder);

		// Verify the issued cert signature against the root (issuer) cert
		issuedCert.verify(keyStore.getCertificate("root-cert").getPublicKey(), BC_PROVIDER);

		writeCertToFileBase64Encoded(issuedCert, "issued-cert.cer");
		exportKeyPairToKeystoreFile(issuedCertKeyPair, issuedCert, "issued-cert", "issued-cert.jks", "PKCS12", "pass");

		return issuedCert;
	}

	static void exportKeyPairToKeystoreFile(KeyPair keyPair, X509Certificate certificate, String alias, String fileName, String storeType, String storePass) throws Exception {
		KeyStore sslKeyStore = KeyStore.getInstance(storeType, BC_PROVIDER);
		sslKeyStore.load(null, null);
		sslKeyStore.setKeyEntry(alias, keyPair.getPrivate(),"keypass".toCharArray(), new X509Certificate[]{certificate});
		FileOutputStream keyStoreOs = new FileOutputStream(fileName);
		sslKeyStore.store(keyStoreOs, storePass.toCharArray());
	}

	static void writeCertToFileBase64Encoded(X509Certificate certificate, String fileName) throws Exception {
		FileOutputStream certificateOut = new FileOutputStream(fileName);
		certificateOut.write("-----BEGIN CERTIFICATE-----".getBytes());
		certificateOut.write(Base64.getEncoder().encode(certificate.getEncoded()));
		certificateOut.write("-----END CERTIFICATE-----".getBytes());
		certificateOut.close();
	}
}

