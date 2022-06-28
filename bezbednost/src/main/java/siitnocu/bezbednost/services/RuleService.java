package siitnocu.bezbednost.services;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import siitnocu.bezbednost.data.Device;
import siitnocu.bezbednost.data.Rule;
import siitnocu.bezbednost.data.RuleDTO;
import siitnocu.bezbednost.repositories.DeviceRepository;
import siitnocu.bezbednost.repositories.RuleRepository;

import javax.annotation.PostConstruct;

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

	@EventListener(ApplicationReadyEvent.class)
	private void writeAllRules() throws IOException {
		List<Rule> rules = ruleRepository.findAll();
		BufferedWriter writer = new BufferedWriter(new PrintWriter("../my-home/src/main/resources/rules.drl"));
        String ruleString = "import  com.example.myhome.domain.*;\r\n";
		for (Rule rule : rules) {
			ruleString += RuleString(rule);
		}
		
		writer.write(ruleString);

        writer.close();
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
			System.out.println("NE POSTOJI UREDJAJJJJ");
		} else {
			newRule = rule.get();
			newRule.setLowerValue(ruleDTO.getLowerValue());
			newRule.setUpperValue(ruleDTO.getUpperValue());
			System.out.println("IPAK POSTOJI UREDJAJJJJ");

		}
		device.setRule(newRule);
		deviceRepository.save(device);
		writeAllRules();

		
	}
	
}
