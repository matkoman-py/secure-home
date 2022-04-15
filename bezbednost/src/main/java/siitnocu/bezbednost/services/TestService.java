package siitnocu.bezbednost.services;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import siitnocu.bezbednost.certificates.CertificateGenerator;
import siitnocu.bezbednost.data.IssuerData;
import siitnocu.bezbednost.data.SubjectData;
import siitnocu.bezbednost.utils.CSRHandler;
import siitnocu.bezbednost.utils.CertificateInfo;


@Service
@Transactional
public class TestService implements ITestService{

	private static final String KEY_STORE = "keystore.jks";
	private static final String KEY_STORE_TYPE = "PKCS12";
	private static final String KEY_STORE_PASSWORD = "pass";

	@Autowired
	public TestService() {
		
	}

	@Autowired
	private CertificateChainService certificateChainService;
	
	@Autowired
	private KeyStoreReaderService keyStoreReaderService;

	@Autowired
	private KeyStoreWriterService keyStoreWriterService;

	@Autowired
	private KeyStoreService keyStoreService;

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

		return str.toString();
	}
	
	@Override
	public SubjectData decodeCSR(String csrString) throws ParseException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, CertificateException, KeyStoreException, NoSuchProviderException {
		// TODO Auto-generated method stub
		PKCS10CertificationRequest csr = CSRHandler.convertPemToPKCS10CertificationRequest(csrString);
		X500Name x500Name = csr.getSubject();

		// Datumi od kad do kad vazi sertifikat
		SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = iso8601Formater.parse("2021-12-31");
		Date endDate = iso8601Formater.parse("2025-12-31");

		JcaPKCS10CertificationRequest jcaPKCS10CertificationRequest = new JcaPKCS10CertificationRequest(csr);

		// Serijski broj sertifikata
		int sn = (keyStoreService.getAllCertificates().size() + 1);

		return new SubjectData(jcaPKCS10CertificationRequest.getPublicKey(), x500Name, String.valueOf(sn), startDate, endDate);
	}

	@Override
	public String signCSR(String csr, String alias) throws ParseException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, CertificateException, KeyStoreException, NoSuchProviderException {
		SubjectData subjectData = decodeCSR(csr);
		PrivateKey privateKey = keyStoreReaderService.readPrivateKey(KEY_STORE, "pass", alias, "pass");
		X509Certificate cert = (X509Certificate) keyStoreReaderService.readCertificate(KEY_STORE, "pass", alias);

		X500Principal principal = cert.getSubjectX500Principal();
		X500Name x500name = new X500Name( principal.getName() );

		IssuerData issuerData = new IssuerData(privateKey, x500name);

		CertificateGenerator cg = new CertificateGenerator();
		X509Certificate generatedCert = cg.generateCertificate(subjectData, issuerData);
		
//		X509Certificate[] chain = new X509Certificate[2];
//		chain[0]=generatedCert;
//		chain[1]=cert;
																		   //PUBLIC KEY, ALL CERTS
		X509Certificate[] chain = (X509Certificate[]) certificateChainService.buildChainFor(cert.getPublicKey(), keyStoreService.getAllCertificatesObjects()).toArray();
		
		keyStoreWriterService.write(KEY_STORE, generatedCert.getSerialNumber().toString(), privateKey, KEY_STORE_PASSWORD.toCharArray(), chain);
		keyStoreWriterService.saveKeyStore(KEY_STORE, KEY_STORE_PASSWORD.toCharArray());

		return "Uspesno potpisan!";
	}

}

