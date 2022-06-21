package com.example.myhome.controller;

import com.example.myhome.domain.Message;
import com.example.myhome.domain.TestDrools;
import com.example.myhome.service.MessageService;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<Message> getMessages() {
        return messageService.getAllMessages();
    }
}
