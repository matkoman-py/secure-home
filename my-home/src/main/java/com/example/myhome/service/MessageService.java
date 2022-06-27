package com.example.myhome.service;

import com.example.myhome.domain.MessageDTO;
import com.example.myhome.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;

	public List<MessageDTO> getAllMessages() {
		return messageRepository.findAll().stream().map(message -> new MessageDTO(message.getId(), message.getDate(),
				message.getMessage(), message.getDevice().getType(), message.getDevice().getEstate().getAddress())).collect(Collectors.toList());
	}
}
