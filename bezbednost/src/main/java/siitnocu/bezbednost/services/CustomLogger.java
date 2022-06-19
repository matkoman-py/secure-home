package siitnocu.bezbednost.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import siitnocu.bezbednost.data.Logs;

@Component
public class CustomLogger {

	@Autowired
	MongoTemplate mongoTemplate;

	public String info(String message) {
	    Logs log = new Logs();
	    log.setLevel("INFO");
	    log.setMessage(message);
	    log.setDate(new Date());
	    mongoTemplate.insert(log, "Logs");
	    return message;
	}

	public String error(String message) {
	    Logs log = new Logs();
	    log.setLevel("ERROR");
	    log.setMessage(message);
	    log.setDate(new Date());
	    mongoTemplate.insert(log);
	
	    return message;
	}

	public String debug(String message) {
	    Logs log = new Logs();
	    log.setLevel("DEBUG");
	    log.setMessage(message);
	    log.setDate(new Date());
	    mongoTemplate.insert(log);
	
	    return message;
	}

}