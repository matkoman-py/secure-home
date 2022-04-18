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
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import extensions.CertificateExtensions;
import siitnocu.bezbednost.certificates.CSRExtensions;
import siitnocu.bezbednost.certificates.CertificateGenerator;
import siitnocu.bezbednost.data.CsrInfo;
import siitnocu.bezbednost.data.DeletedCertificate;
import siitnocu.bezbednost.data.IssuerData;
import siitnocu.bezbednost.data.SubjectData;
import siitnocu.bezbednost.repositories.CsrInfoRepository;
import siitnocu.bezbednost.repositories.DeletedCertificateRepository;
import siitnocu.bezbednost.utils.CSRHandler;
import siitnocu.bezbednost.utils.CertificateInfo;


@Service
@Transactional
public class TestService{

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

	@Autowired
	private DeletedCertificateRepository deletedCertificateRepository;

	@Autowired
	private CsrInfoRepository csrInfoRepository;

	public String generateCSR(CertificateInfo csrInfo) throws NoSuchAlgorithmException, OperatorCreationException, IOException, KeyStoreException, CertificateException, NoSuchProviderException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

		kpg.initialize(csrInfo.getKeySize());
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

		CsrInfo csrInfo1 = new CsrInfo(
			csrInfo.getDomainName(),
				csrInfo.getOrganizationName(),
				csrInfo.getOrganizationUnit(),
				csrInfo.getCity(),
				csrInfo.getState(),
				csrInfo.getCountry(),
				csrInfo.getEmail(),
				str.toString(),
				csrInfo.getReason()
		);
		csrInfoRepository.save(csrInfo1);

		return str.toString();
	}
	
	public SubjectData decodeCSR(String csrString) throws ParseException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, CertificateException, KeyStoreException, NoSuchProviderException {
		// TODO Auto-generated method stub
		PKCS10CertificationRequest csr = CSRHandler.convertPemToPKCS10CertificationRequest(csrString);
		X500Name x500Name = csr.getSubject();

		// Datumi od kad do kad vazi sertifikat
		SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = iso8601Formater.parse("2021-12-31");
		Date endDate = iso8601Formater.parse("2025-12-31");

		//JcaPKCS10CertificationRequest jcaPKCS10CertificationRequest = new JcaPKCS10CertificationRequest(csr);

		RSAKeyParameters pubkey =
				(RSAKeyParameters)PublicKeyFactory.createKey(csr.getSubjectPublicKeyInfo());
		RSAPublicKeySpec spec = new RSAPublicKeySpec(pubkey.getModulus(), pubkey.getExponent());
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PublicKey pub = factory.generatePublic(spec);
		// Serijski broj sertifikata
		int sn = (keyStoreService.getAllCertificates().size() + 1);

		return new SubjectData(pub, x500Name, String.valueOf(sn), startDate, endDate);
	}

	public String signCSR(CSRExtensions csr, String alias, String subjectDomainName) throws ParseException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, CertificateException, KeyStoreException, NoSuchProviderException {
		Optional<CsrInfo> csrInfo = csrInfoRepository.findByDomainName(subjectDomainName);
		if(csrInfo.isEmpty()){
			return "There is no subject with the given domain name!";
		}

		System.out.println("EVOOO ME " + csr.toString());

		SubjectData subjectData = decodeCSR(csrInfo.get().getCsrString());
		PrivateKey privateKey = keyStoreReaderService.readPrivateKey(KEY_STORE, "pass", alias, "pass");

		X509Certificate cert = (X509Certificate) keyStoreReaderService.readCertificate(KEY_STORE, "pass", alias);

		if(cert.getBasicConstraints() == -1){
			return "Issuer is not a CA!";
		}

		X500Principal principal = cert.getSubjectX500Principal();
		X500Name x500name = new X500Name( principal.getName() );

		IssuerData issuerData = new IssuerData(privateKey, x500name);

		CertificateGenerator cg = new CertificateGenerator();
		X509Certificate generatedCert = cg.generateCertificate(subjectData, issuerData, csr.getExtensions());
		
		X509Certificate[] chain = new X509Certificate[1];
		chain[0]=generatedCert;
		keyStoreWriterService.write(KEY_STORE, subjectDomainName, privateKey, KEY_STORE_PASSWORD.toCharArray(), chain);
		keyStoreWriterService.saveKeyStore(KEY_STORE, KEY_STORE_PASSWORD.toCharArray());

		//PUBLIC KEY, ALL CERTS
		List<X509Certificate> chainList = certificateChainService.buildChainFor(generatedCert.getPublicKey(), keyStoreService.getAllCertificatesObjects());
		Collections.reverse(chainList);
		X509Certificate[] chainArray = new X509Certificate[chainList.size()];
		chainList.toArray(chainArray);

		keyStoreWriterService.write(KEY_STORE, subjectDomainName, privateKey, KEY_STORE_PASSWORD.toCharArray(), chainArray);
		keyStoreWriterService.saveKeyStore(KEY_STORE, KEY_STORE_PASSWORD.toCharArray());

		csrInfoRepository.delete(csrInfo.get());
		return "Uspesno potpisan!";
	}

	public String revokeCertificate(String alias, String reason) throws KeyStoreException, NoSuchProviderException, IOException, CertificateException, NoSuchAlgorithmException {
		X509Certificate cert = (X509Certificate) keyStoreReaderService.readCertificate(KEY_STORE, "pass", alias);
		PrivateKey privateKey = keyStoreReaderService.readPrivateKey(KEY_STORE, "pass", alias, "pass");

		keyStoreWriterService.deleteCertificate(KEY_STORE, alias, KEY_STORE_PASSWORD.toCharArray());
		deletedCertificateRepository.save(new DeletedCertificate(alias, reason));

		Queue<String> queue
				= new LinkedList<String>();

		X500Name x500nameSubject = new X500Name( cert.getSubjectX500Principal().getName() );
		RDN cnSubject = x500nameSubject.getRDNs(BCStyle.CN)[0];
		queue.add(IETFUtils.valueToString(cnSubject.getFirst().getValue()));

		while(queue.size() != 0){
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			// ucitavamo podatke
			File file = new File(KEY_STORE);
			InputStream is = new FileInputStream(file);
			ks.load(is, KEY_STORE_PASSWORD.toCharArray());
			Enumeration<String> enumeration = ks.aliases();
			while(enumeration.hasMoreElements()) {
				String aliasInlist = enumeration.nextElement();
				X509Certificate certificate = (X509Certificate) ks.getCertificate(aliasInlist);

				X500Name x500nameIssuer = new X500Name( certificate.getIssuerX500Principal().getName() );
				RDN cnIssuer = x500nameIssuer.getRDNs(BCStyle.CN)[0];
				if(IETFUtils.valueToString(cnIssuer.getFirst().getValue()).equals(queue.peek())){
					X500Name x500nameSubjectDeep = new X500Name( certificate.getSubjectX500Principal().getName() );
					RDN cnSubjectDeep = x500nameSubjectDeep.getRDNs(BCStyle.CN)[0];
					queue.add(IETFUtils.valueToString(cnSubjectDeep.getFirst().getValue()));

					keyStoreWriterService.deleteCertificate(KEY_STORE, aliasInlist, KEY_STORE_PASSWORD.toCharArray());
					deletedCertificateRepository.save(new DeletedCertificate(aliasInlist, "Parent was deleted"));

					X509Certificate[] chain = new X509Certificate[1];
					chain[0]=certificate;
					keyStoreWriterService.write("keystore-deleted.jks", aliasInlist, privateKey, KEY_STORE_PASSWORD.toCharArray(), chain);
					keyStoreWriterService.saveKeyStore("keystore-deleted.jks", KEY_STORE_PASSWORD.toCharArray());
				}
			}
			queue.poll();
		}



		X509Certificate[] chain = new X509Certificate[1];
		chain[0]=cert;
		keyStoreWriterService.write("keystore-deleted.jks", cert.getSerialNumber().toString(), privateKey, KEY_STORE_PASSWORD.toCharArray(), chain);
		keyStoreWriterService.saveKeyStore("keystore-deleted.jks", KEY_STORE_PASSWORD.toCharArray());

		return "Revoke successful!";
	}
}

