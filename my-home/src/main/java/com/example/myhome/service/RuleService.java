package com.example.myhome.service;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import com.example.myhome.domain.Device;
import com.example.myhome.domain.Rule;
import com.example.myhome.domain.RuleDTO;
import com.example.myhome.repository.DeviceRepository;
import com.example.myhome.repository.RoleRepository;
import com.example.myhome.repository.RuleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleService {

	
	@Autowired
	private RuleRepository ruleRepository;
	@Autowired
	private DeviceRepository deviceRepository;    
	
	private String RuleString(Rule rule) {
		String r = "rule \""+rule.getDevice().getId()+"\"\r\n"
				+ "    when\r\n"
				+ "        dto : DroolsDTO( deviceType==\""+rule.getDevice().getPathToFile()+"\" && (value >= "+rule.getUpperValue()+"  || value <="+rule.getLowerValue()+"));\r\n"
				+ "    then\r\n"
				+ "        dto.setAlarm(true);\r\n"
				+ "end;\r\n";
		return r;
	}
	
	public String rulesString() {
		List<Rule> rules = ruleRepository.findAll();
        String ruleString = "import  com.example.myhome.domain.*;\r\n";
		for (Rule rule : rules) {
			ruleString += RuleString(rule);
		}
		return ruleString;
	}
		
	private Device findDevice(Long id) {
		return deviceRepository.findById(id).orElseThrow();
	}
	
	public void addRule(RuleDTO ruleDTO) throws IOException {
		Optional<Rule> rule = ruleRepository.findByDeviceId(ruleDTO.getDeviceId());
		Rule newRule = new Rule();
		Device device = findDevice(ruleDTO.getDeviceId());

		if(rule.isEmpty()) {
			System.out.println(device.getIpAddress() + " DSADSAD");
			newRule.setDevice(device);
			newRule.setLowerValue(ruleDTO.getLowerValue());
			newRule.setUpperValue(ruleDTO.getUpperValue());
		} else {
			newRule = rule.get();
			newRule.setLowerValue(ruleDTO.getLowerValue());
			newRule.setUpperValue(ruleDTO.getUpperValue());
		}
		device.setRule(newRule);
		deviceRepository.save(device);

		
	}
	
}
