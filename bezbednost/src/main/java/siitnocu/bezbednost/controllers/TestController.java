package siitnocu.bezbednost.controllers;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import org.bouncycastle.operator.OperatorCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import extensions.CertificateExtensions;
import siitnocu.bezbednost.certificates.CSRExtensions;
import siitnocu.bezbednost.data.SubjectData;
import siitnocu.bezbednost.services.CustomLogger;
import siitnocu.bezbednost.services.TestService;
import siitnocu.bezbednost.utils.CertificateInfo;


@RestController
@RequestMapping("/api/test")
public class TestController {
	
	private TestService testService;
	
	@Autowired
	CustomLogger customLogger;
	
	Logger logger = LoggerFactory.getLogger(CustomLogger.class);
	

	@Autowired
	public TestController(TestService testService) {
		this.testService = testService;
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('GENERATE_CSR')")
	public ResponseEntity<String> getAll(@RequestBody CertificateInfo csrInfo) throws NoSuchAlgorithmException, OperatorCreationException, IOException, KeyStoreException, CertificateException, NoSuchProviderException {
		String csr = testService.generateCSR(csrInfo);
		logger.info(customLogger.info("CSR generated"));
		return ResponseEntity.ok(csr);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/decode")
	@PreAuthorize("hasAuthority('DECODE_CSR')")
	public ResponseEntity<String> getThis(@RequestBody String csr) throws NoSuchAlgorithmException, OperatorCreationException, IOException, ParseException, InvalidKeySpecException, InvalidKeyException, CertificateException, KeyStoreException, NoSuchProviderException {
		logger.info(customLogger.info("CSR decoded"));
		return ResponseEntity.ok(testService.decodeCSR(csr).getSerialNumber());
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/best")
	@PreAuthorize("hasAuthority('CSR_EXTENSIONS')")
	public ResponseEntity<CSRExtensions> getThise() throws NoSuchAlgorithmException, OperatorCreationException, IOException, ParseException, InvalidKeySpecException, InvalidKeyException, CertificateException, KeyStoreException, NoSuchProviderException {
		CSRExtensions csr= new CSRExtensions();
		CertificateExtensions ce = new CertificateExtensions();
		ce.setBCCrit(true);
		csr.setExtensions(new CertificateExtensions());
		return ResponseEntity.ok(csr);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/sign-csr/{issuerAlias}/{subjectDomainName}")
	@PreAuthorize("hasAuthority('SIGN_CSR')")
	public ResponseEntity<String> signCsr(@PathVariable("issuerAlias") String alias,
										  @PathVariable("subjectDomainName") String subjectDomainName,
										  @RequestBody CSRExtensions csr) throws NoSuchAlgorithmException, OperatorCreationException, IOException, ParseException, InvalidKeySpecException, InvalidKeyException, CertificateException, KeyStoreException, NoSuchProviderException {
		logger.info(customLogger.info("Issuer with alias " + alias + " signed " + subjectDomainName));
		return ResponseEntity.ok(testService.signCSR(csr, alias, subjectDomainName));
	}

	@DeleteMapping(value = "/revoke-certificate/{alias}")
	@PreAuthorize("hasAuthority('REVOKE_CERTIFICATE')")
	public ResponseEntity<String> revokeCertifikate(@PathVariable("alias") String alias,
													@RequestBody String reason) throws NoSuchAlgorithmException, OperatorCreationException, IOException, ParseException, InvalidKeySpecException, InvalidKeyException, CertificateException, KeyStoreException, NoSuchProviderException {
		logger.info(customLogger.info("CSR gwith alias " + alias + " revoked because: " + reason));
		return ResponseEntity.ok(testService.revokeCertificate(alias, reason));
	}
}
