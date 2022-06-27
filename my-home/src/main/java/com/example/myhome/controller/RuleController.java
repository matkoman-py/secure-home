package com.example.myhome.controller;

import java.io.IOException;
import java.util.List;

import com.example.myhome.domain.Message;
import com.example.myhome.domain.RuleDTO;
import com.example.myhome.service.MessageService;
import com.example.myhome.service.RuleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/rules", produces = MediaType.APPLICATION_JSON_VALUE)
public class RuleController {
	
	@Autowired
    private RuleService ruleService;
	
	@PostMapping
    public String postRule(@RequestBody RuleDTO rule) throws IOException {
        ruleService.addRule(rule);
        return "OK";
    }

}
