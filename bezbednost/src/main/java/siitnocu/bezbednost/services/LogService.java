package siitnocu.bezbednost.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	public List<Logs> searchLogs(String after, String before, String level, String message, String app, String user) throws ParseException {
        System.out.println(after + before + level + message + app + user);
		Date dateAfter, dateBefore;
        if(after.equals("")){
            dateAfter = new Date(Long.MIN_VALUE);
        }
        else{
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateAfter = dateFormat.parse(after);
        }

        if(before.equals("")){
            dateBefore = new Date(Long.MAX_VALUE);
        }
        else{
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateBefore = dateFormat.parse(before);
        }

        return logRepository.search(dateAfter, dateBefore, level, message, app, user);
    }
}
