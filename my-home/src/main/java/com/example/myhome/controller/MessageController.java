package com.example.myhome.controller;

import com.example.myhome.domain.MessageDTO;
import com.example.myhome.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<MessageDTO> getMessages() {
        return messageService.getAllMessages();
    }
}
