package com.example.myhome.controller;

import java.io.IOException;

import com.example.myhome.domain.RuleDTO;
import com.example.myhome.service.CustomLogger;
import com.example.myhome.service.RuleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/rules", produces = MediaType.APPLICATION_JSON_VALUE)
public class RuleController {
	
	@Autowired
	CustomLogger customLogger;
	
	Logger logger = LoggerFactory.getLogger(CustomLogger.class);
	
	@Autowired
    private RuleService ruleService;
	
	@PostMapping
    public String postRule(@RequestBody RuleDTO rule) throws IOException {
        ruleService.addRule(rule);
        logger.info(customLogger.info("New rule added succesfully"));
        return "OK";
    }

}
