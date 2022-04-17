package siitnocu.bezbednost.certificates;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi.SHA1;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.oer.its.HashAlgorithm;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import extensions.CertificateExtensions;
import siitnocu.bezbednost.data.IssuerData;
import siitnocu.bezbednost.data.SubjectData;


public class CertificateGenerator {
    public CertificateGenerator() {
    }

    public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, CertificateExtensions ext) throws IOException, NoSuchAlgorithmException {
        try {
        	Security.addProvider(new BouncyCastleProvider());
            // Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
            // Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
            // Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");

           
            // Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
            builder = builder.setProvider("BC");

            // Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

            // Postavljaju se podaci za generisanje sertifiakta
            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                    new BigInteger(subjectData.getSerialNumber(), 16),
                    subjectData.getStartDate(),
                    subjectData.getEndDate(),
                    subjectData.getX500name(),
                    subjectData.getPublicKey());
            // Generise se sertifikat
            //VDE RADIM============================
            
            if(ext.getBasicConstraints() != null) {
                certGen.addExtension(Extension.basicConstraints, ext.isBCCrit(), new BasicConstraints(ext.getBasicConstraints().isCa())); //GOTOVO            
            }
            if(ext.getKeyUsage() != null) {
            	if(ext.getKeyUsage().getKeyUsages().size() > 0) {
                    certGen.addExtension(Extension.keyUsage, ext.isKUECrit(), new KeyUsage(ext.getKeyUsage().calculateKeyUsages()));
            	}
            }
            if(ext.getExtendedKeyUsage() != null) {
            	if(ext.getExtendedKeyUsage().getKeyPurposes().size() > 0) {
                	certGen.addExtension(Extension.extendedKeyUsage, ext.isEKUCrit(), new ExtendedKeyUsage(ext.getExtendedKeyUsage().transform()));
            	}
            }
            if(ext.getSubjectAlternativeName() != null) {
            	if(ext.getSubjectAlternativeName().getNames().size() > 0) {
                    certGen.addExtension(Extension.subjectAlternativeName, ext.isSANCrit(), ext.getSubjectAlternativeName().transform());
            	}
            }
            if(ext.isSKIExt()) {
              MessageDigest md = MessageDigest.getInstance("SHA-1");
              byte[] hash = md.digest(subjectData.getPublicKey().getEncoded());
              certGen.addExtension(Extension.subjectKeyIdentifier, ext.isSKICrit(), new SubjectKeyIdentifier(hash)); //GOTOVO   
            }
            if(ext.isAKIExt()) {
            	SubjectPublicKeyInfo apki = new SubjectPublicKeyInfo((ASN1Sequence)new ASN1InputStream(subjectData.getPublicKey().getEncoded()).readObject());
              	AuthorityKeyIdentifier aki = new AuthorityKeyIdentifier(apki);
     	   		certGen.addExtension(Extension.authorityKeyIdentifier, ext.isAKICrit(), aki);
            }
            
  
//            SubjectPublicKeyInfo apki = new SubjectPublicKeyInfo((ASN1Sequence)new ASN1InputStream(
//            	       subjectData.getPublicKey().getEncoded()).readObject());
//            
//            	   AuthorityKeyIdentifier aki = new AuthorityKeyIdentifier(apki);
//            certGen.addExtension(Extension.authorityKeyIdentifier, false, aki);
            
            
            //DO OVDE=============================================
            X509CertificateHolder certHolder = certGen.build(contentSigner);
            
            // Builder generise sertifikat kao objekat klase X509CertificateHolder
            // Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");

            // Konvertuje objekat u sertifikat
            return certConverter.getCertificate(certHolder);
        } catch (IllegalArgumentException | IllegalStateException | OperatorCreationException | CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }
}
