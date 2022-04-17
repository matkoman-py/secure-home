package siitnocu.bezbednost.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import siitnocu.bezbednost.data.CertificateDTO;
import siitnocu.bezbednost.services.KeyStoreService;

import java.util.List;

@RestController
@RequestMapping("/api/key-store")
public class KeyStoreController {

	private KeyStoreService keyStoreService;
	
	@Autowired
	public KeyStoreController(KeyStoreService keyStoreService) {
		this.keyStoreService = keyStoreService;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/generate-root")
	public ResponseEntity<String> generateRoot() throws Exception {
		keyStoreService.createNewSelfSignedCertificate();
		return ResponseEntity.ok("NAPRAVLJEN");
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/get-all-certificates")
	public ResponseEntity<List<CertificateDTO>> getAllCertificates() throws Exception {
		return ResponseEntity.ok(keyStoreService.getAllCertificates());
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/check-certificate-validity/{alias}")
	public ResponseEntity<Boolean> isCertificateValid(@PathVariable("alias") String alias) throws Exception {
		return ResponseEntity.ok(keyStoreService.isCertificateValid(alias));
	}
}
