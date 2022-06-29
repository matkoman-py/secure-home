package com.example.myhome.controller;

import com.example.myhome.domain.MessageDTO;
import com.example.myhome.service.CustomLogger;
import com.example.myhome.service.MessageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

	@Autowired
	CustomLogger customLogger;
	
	Logger logger = LoggerFactory.getLogger(CustomLogger.class);
	
    @Autowired
    private MessageService messageService;

    @GetMapping
    @PreAuthorize("hasAuthority('ALL_MESSAGES')")
    public List<MessageDTO> getMessages() {
    	logger.info(customLogger.info("Admin requests all messages"));
        return messageService.getAllMessages();
    }
}
