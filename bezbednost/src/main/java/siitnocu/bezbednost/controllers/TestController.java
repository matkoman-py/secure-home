package siitnocu.bezbednost.controllers;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

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
	public ResponseEntity<String> getAll(@RequestBody CertificateInfo csrInfo) throws NoSuchAlgorithmException, OperatorCreationException, IOException, KeyStoreException, CertificateException {
		return ResponseEntity.ok(testService.generateCSR(csrInfo));
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/decode")
	public ResponseEntity<CertificateInfo> getThis(@RequestBody String csr) throws NoSuchAlgorithmException, OperatorCreationException, IOException {
		return ResponseEntity.ok(testService.decodeCSR(csr));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/generate-root")
	public ResponseEntity<String> generateRoot() throws Exception {
		testService.generateRootCertificate();
		return ResponseEntity.ok("NAPRAVLJEN");
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/sign-csr")
	public ResponseEntity<String> signCsr() throws Exception {
		return ResponseEntity.ok(testService.signCSR().toString());
	}
}
