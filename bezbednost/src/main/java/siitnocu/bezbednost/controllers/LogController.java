package siitnocu.bezbednost.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import siitnocu.bezbednost.data.Logs;
import siitnocu.bezbednost.services.CustomLogger;
import siitnocu.bezbednost.services.LogService;

@RestController
@RequestMapping(value = "/api/logs", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class LogController {
	
	@Autowired
	CustomLogger customLogger;
	
	Logger logger = LoggerFactory.getLogger(CustomLogger.class);

	@Autowired
	private LogService logService;

	@GetMapping("/getAll")
	@PreAuthorize("hasAuthority('READ_LOGS')")
	public List<Logs> getAll() {
		logger.info(customLogger.info("Requesting all logs"));
		return this.logService.getAll();
	}
}
