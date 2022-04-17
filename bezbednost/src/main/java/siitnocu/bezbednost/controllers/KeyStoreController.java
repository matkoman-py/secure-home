package siitnocu.bezbednost.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import siitnocu.bezbednost.data.CertificateDTO;
import siitnocu.bezbednost.dto.CsrDTO;
import siitnocu.bezbednost.data.ExtensionsDTO;
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
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/get-certificate/{alias}")
	public ResponseEntity<CertificateDTO> getCertificate(@PathVariable("alias") String alias) throws Exception {
		return ResponseEntity.ok(keyStoreService.getCertificate(alias));
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/get-extensions/{alias}")
	public ResponseEntity<ExtensionsDTO> getExtensions(@PathVariable("alias") String alias) throws Exception {
		return ResponseEntity.ok(keyStoreService.getExtensionsForCertificate(alias));
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/check-certificate-validity/{alias}")
	public ResponseEntity<Boolean> isCertificateValid(@PathVariable("alias") String alias) throws Exception {
		return ResponseEntity.ok(keyStoreService.isCertificateValid(alias));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/get-all-csrs")
	public ResponseEntity<List<CsrDTO>> getAllCsrs() throws Exception {
		return ResponseEntity.ok(keyStoreService.getAllCsrs());
	}
}
