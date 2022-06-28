package com.example.myhome.service;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.example.myhome.domain.Logs;
import com.example.myhome.domain.SystemAlarm;
import com.example.myhome.repository.SystemAlarmRepository;

@Component
public class CustomLogger {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	private SystemAlarmRepository systemAlarmRepository;

	public String info(String message) {
	    Logs log = new Logs();
	    log.setLevel("INFO");
	    log.setSourceApp("MY HOME APP");
	    log.setSourceUser(SecurityContextHolder.getContext().getAuthentication().getName());
	    log.setMessage(message);
	    log.setDate(LocalDateTime.now());
	    mongoTemplate.insert(log, "Logs");
	    return message;
	}

	public String error(String message) {
	    Logs log = new Logs();
	    log.setLevel("ERROR");
	    log.setSourceApp("MY HOME APP");
	    log.setSourceUser(SecurityContextHolder.getContext().getAuthentication().getName());
	    log.setMessage(message);
	    log.setDate(LocalDateTime.now());

		SystemAlarm alarm = new SystemAlarm();
		alarm.setDate(new Date());
		alarm.setMessage("Error log appeared with message: " + message);
		systemAlarmRepository.save(alarm);

	    mongoTemplate.insert(log, "Logs");
	    return message;
	}

	public String debug(String message) {
	    Logs log = new Logs();
	    log.setLevel("DEBUG");
	    log.setSourceApp("MY HOME APP");
	    log.setSourceUser(SecurityContextHolder.getContext().getAuthentication().getName());
	    log.setMessage(message);
	    log.setDate(LocalDateTime.now());
	    mongoTemplate.insert(log, "Logs");
	    return message;
	}
}