package siitnocu.bezbednost.services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.Certificate;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x500.X500Name;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import siitnocu.bezbednost.utils.CSRHandler;
import siitnocu.bezbednost.utils.CertificateInfo;


@Service
@Transactional
public class TestService implements ITestService{
	


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


	
}

