package siitnocu.bezbednost.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.text.ParseException;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siitnocu.bezbednost.certificates.CertificateGenerator;
import siitnocu.bezbednost.data.IssuerData;
import siitnocu.bezbednost.data.SubjectData;

@Service
public class KeyStoreService {
	
	private static final String KEY_STORE = "rootNOVI-cert.jks";
	private static final String KEY_STORE_TYPE = "PKCS12";
	private static final String KEY_STORE_PASSWORD = "pass";
	
	@Autowired
	private KeyStoreReaderService keyStoreReaderService;

	private KeyStoreWriterService keyStoreWriterService = new KeyStoreWriterService();
	
    public void createNewSelfSignedCertificate() throws FileNotFoundException {

    	keyStoreWriterService.loadKeyStore(null, KEY_STORE_PASSWORD.toCharArray());
    	
    	KeyPair keyPairIssuer = generateKeyPair();
    	
    	SubjectData subjectData = generateSubjectData(keyPairIssuer.getPublic());
        IssuerData issuerData = generateIssuerData(keyPairIssuer.getPrivate());
        
        CertificateGenerator cg = new CertificateGenerator();
        X509Certificate cert = cg.generateCertificate(subjectData, issuerData);
   
        keyStoreWriterService.write("root-certificate", keyPairIssuer.getPrivate(), KEY_STORE_PASSWORD.toCharArray(), cert);
        keyStoreWriterService.saveKeyStore(KEY_STORE, KEY_STORE_PASSWORD.toCharArray());
    }
    
    private IssuerData generateIssuerData(PrivateKey issuerKey) {
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

    private SubjectData generateSubjectData(PublicKey publicKey) {
        try {
            // Datumi od kad do kad vazi sertifikat
            SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = iso8601Formater.parse("2021-12-31");
            Date endDate = iso8601Formater.parse("2025-12-31");

            // Serijski broj sertifikata
            String sn = "1";

            // klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
            X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
            builder.addRDN(BCStyle.CN, "Alkeksandar Cepic");
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
    
    private void createNewIssuedCertificate() {
        // TODO: Upotrebom klasa iz primeri/pki paketa, prikazati sadrzaj keystore-a, gde korisnik unosi sve potrebne podatke
        // Radi ustede vremena hardkodovati podatke vezane za subjekta sertifikata
    }
}
