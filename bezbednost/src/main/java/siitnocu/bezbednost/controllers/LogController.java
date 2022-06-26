package siitnocu.bezbednost.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	//Date before, Date after, String level, String message, String app, String user

	@GetMapping("/getAll")
	@PreAuthorize("hasAuthority('READ_LOGS')")
	public List<Logs> getAll(@RequestParam(value = "before", defaultValue = "") String before,
            @RequestParam(value = "after", defaultValue = "") String after,
            @RequestParam(value = "level", defaultValue = "") String level,
            @RequestParam(value = "message", defaultValue = "") String message,
            @RequestParam(value = "app", defaultValue = "") String app,
            @RequestParam(value = "user", defaultValue = "") String user) throws ParseException {
		logger.info(customLogger.info("Requesting all logs"));
		return this.logService.searchLogs(after, before, level, message, app, user);
	}
}
