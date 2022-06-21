package siitnocu.bezbednost.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import siitnocu.bezbednost.data.User;
import siitnocu.bezbednost.data.UserRequest;
import siitnocu.bezbednost.dto.UserDTO;
import siitnocu.bezbednost.dto.UserRequestDTO;
import siitnocu.bezbednost.repositories.UserRequestRepository;

@Service
public class UserRequestService {

	private static final String PASSWORD_PATTERN =
			"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

	private static final String EMAIL_PATTERN =
			"^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";

	private static final String USERNAME_PATTERN =
			"^[A-Za-z0-9_.]+$";

	private static final String NAME_PATTERN =
			"^(?=.{1,40}$)[a-zA-Z]+(?:[-\\s][a-zA-Z]+)*$";

	private static final Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN);
	private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
	private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
	private static final Pattern namePattern = Pattern.compile(NAME_PATTERN);
	
	@Autowired
	private UserRequestRepository userRequestRepository;
	
	public List<UserRequest> findAll() throws AccessDeniedException {
		return userRequestRepository.findAll();
	}
	
	public UserRequest save(UserRequestDTO userRequestData) {
		UserRequest u = new UserRequest();

		Matcher matcher = usernamePattern.matcher(userRequestData.getUsername());
		if(!matcher.matches()){
			throw new RuntimeException("Usernames can only use letters, numbers, underscores, and periods.");
		}
		u.setUsername(userRequestData.getUsername());

		matcher = passwordPattern.matcher(userRequestData.getPassword());
		if(!matcher.matches()){
			throw new RuntimeException("Password must be 8-20 chars long, contain a big, small letter and a number!");
		}
		u.setPassword(userRequestData.getPassword());

		matcher = namePattern.matcher(userRequestData.getFirstname());
		if(!matcher.matches()){
			throw new RuntimeException("First name is in bad format (Must contain only alphabetic characters and hyphen)!");
		}
		u.setFirstname(userRequestData.getFirstname());

		matcher = namePattern.matcher(userRequestData.getLastname());
		if(!matcher.matches()){
			throw new RuntimeException("Last name is in bad format (Must contain only alphabetic characters and hyphen)!");
		}
		u.setLastname(userRequestData.getLastname());

		matcher = emailPattern.matcher(userRequestData.getEmail());
		if(!matcher.matches()){
			throw new RuntimeException("User email is in bad format!");
		}
		u.setEmail(userRequestData.getEmail());
		
		return this.userRequestRepository.save(u);
	}
}
