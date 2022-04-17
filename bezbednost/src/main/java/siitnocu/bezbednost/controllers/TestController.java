package siitnocu.bezbednost.controllers;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import siitnocu.bezbednost.services.TestService;
import siitnocu.bezbednost.utils.CertificateInfo;


@RestController
@RequestMapping("/api/test")
public class TestController {
	
	private TestService testService;

	@Autowired
	public TestController(TestService testService) {
		this.testService = testService;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAll(@RequestBody CertificateInfo csrInfo) throws NoSuchAlgorithmException, OperatorCreationException, IOException, KeyStoreException, CertificateException, NoSuchProviderException {
		return ResponseEntity.ok(testService.generateCSR(csrInfo));
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/decode")
	public ResponseEntity<String> getThis(@RequestBody String csr) throws NoSuchAlgorithmException, OperatorCreationException, IOException, ParseException, InvalidKeySpecException, InvalidKeyException, CertificateException, KeyStoreException, NoSuchProviderException {
		return ResponseEntity.ok(testService.decodeCSR(csr).getSerialNumber());
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/best")
	public ResponseEntity<CSRExtensions> getThise() throws NoSuchAlgorithmException, OperatorCreationException, IOException, ParseException, InvalidKeySpecException, InvalidKeyException, CertificateException, KeyStoreException, NoSuchProviderException {
		CSRExtensions csr= new CSRExtensions();
		CertificateExtensions ce = new CertificateExtensions();
		ce.setBCCrit(true);
		csr.setExtensions(new CertificateExtensions());
		return ResponseEntity.ok(csr);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/sign-csr/{issuerAlias}/{subjectDomainName}")
	public ResponseEntity<String> signCsr(@PathVariable("issuerAlias") String alias,
										  @PathVariable("subjectDomainName") String subjectDomainName,
										  @RequestBody CSRExtensions csr) throws NoSuchAlgorithmException, OperatorCreationException, IOException, ParseException, InvalidKeySpecException, InvalidKeyException, CertificateException, KeyStoreException, NoSuchProviderException {
		return ResponseEntity.ok(testService.signCSR(csr, alias, subjectDomainName));
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/revoke-certificate/{alias}")
	public ResponseEntity<String> revokeCertifikate(@PathVariable("alias") String alias,
													@RequestBody String reason) throws NoSuchAlgorithmException, OperatorCreationException, IOException, ParseException, InvalidKeySpecException, InvalidKeyException, CertificateException, KeyStoreException, NoSuchProviderException {
		return ResponseEntity.ok(testService.revokeCertificate(alias, reason));
	}
}
