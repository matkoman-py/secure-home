package siitnocu.bezbednost.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

	@PreAuthorize("hasAuthority('GENERATE_ROOT')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/generate-root")
	public ResponseEntity<String> generateRoot() throws Exception {
		keyStoreService.createNewSelfSignedCertificate();
		return ResponseEntity.ok("NAPRAVLJEN");
	}

	@PreAuthorize("hasAuthority('GET_ALL_CERTIFICATES')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/get-all-certificates")
	public ResponseEntity<List<CertificateDTO>> getAllCertificates() throws Exception {
		return ResponseEntity.ok(keyStoreService.getAllCertificates());
	}

	@PreAuthorize("hasAuthority('GET_ALL_CA_CERTIFICATES')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/get-all-cacertificates")
	public ResponseEntity<List<CertificateDTO>> getAllCACertificates() throws Exception {
		return ResponseEntity.ok(keyStoreService.getAllCACertificates());
	}

	@PreAuthorize("hasAuthority('GET_ALL_REVOKED_CERTIFICATES')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/get-all-revoked-certificates")
	public ResponseEntity<List<CertificateDTO>> getAllRevokedCertificates() throws Exception {
		return ResponseEntity.ok(keyStoreService.getAllRevokedCertificates());
	}

	@PreAuthorize("hasAuthority('GET_ONE_CERTIFICATE')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/get-certificate/{alias}/{fileName}")
	public ResponseEntity<CertificateDTO> getCertificate(@PathVariable("alias") String alias,@PathVariable("fileName") String fileName) throws Exception {
		return ResponseEntity.ok(keyStoreService.getCertificate(alias, fileName));
	}

	@PreAuthorize("hasAuthority('GET_CERTIFICATE_EXTENSIONS')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/get-extensions/{alias}/{fileName}")
	public ResponseEntity<ExtensionsDTO> getExtensions(@PathVariable("alias") String alias,@PathVariable("fileName") String fileName) throws Exception {
		return ResponseEntity.ok(keyStoreService.getExtensionsForCertificate(alias, fileName));
	}

	@PreAuthorize("hasAuthority('IS_CERTIFICATE_VALID')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/check-certificate-validity/{alias}")
	public ResponseEntity<Boolean> isCertificateValid(@PathVariable("alias") String alias) throws Exception {
		return ResponseEntity.ok(keyStoreService.isCertificateValid(alias));
	}

	@PreAuthorize("hasAuthority('GET_CSRS')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/get-all-csrs")
	public ResponseEntity<List<CsrDTO>> getAllCsrs() throws Exception {
		return ResponseEntity.ok(keyStoreService.getAllCsrs());
	}
}
