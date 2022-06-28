package com.example.myhome.service;

import com.example.myhome.domain.Device;
import com.example.myhome.domain.MessageDTO;
import com.example.myhome.repository.DeviceRepository;
import com.example.myhome.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageService {

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private MessageRepository messageRepository;

	public List<MessageDTO> getAllMessages() {
		return messageRepository.findAll().stream().map(m -> {
			Device d = deviceRepository.findByPathToFile(m.getDevice()).get();
			return new MessageDTO(m.getId(), m.getDate(),
					m.getMessage(), d.getType(), d.getEstate().getAddress());
		}).collect(Collectors.toList());
	}
}
