package siitnocu.bezbednost.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siitnocu.bezbednost.data.Logs;
import siitnocu.bezbednost.repositories.LogRepository;

@Service
public class LogService {

	@Autowired
	private LogRepository logRepository;
	
	public List<Logs> getAll() {
		return logRepository.findAll();
	}
}
