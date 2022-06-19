package com.example.myhome.service;

import com.example.myhome.domain.*;
import com.example.myhome.repository.EstateRepository;
import com.example.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EstateRepository estateRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;

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

	public User findByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}
	
	public User addEstateToUser(Long userId, Long estateId) {
		User u = findById(userId);
		Estate e = findByIdEstate(estateId);
		Set<Estate> estates = u.getEstates();
		estates.add(e);
		u.setEstates(estates);
		return userRepository.save(u);
		
		
	}
	
	public List<EstateDTO> getEstatesForUser(Long id) {
		User u = findById(id);
		return u.getEstates().stream().map(e -> new EstateDTO(e)).collect(Collectors.toList());
	}
	

	public Estate findByIdEstate(Long id) throws AccessDeniedException {
		return estateRepository.findById(id).orElseGet(null);
	}

	
	public User findById(Long id) throws AccessDeniedException {
		return userRepository.findById(id).orElseGet(null);
	}

	public List<User> findAll(String search) throws AccessDeniedException {
		return userRepository.search(search.trim().toLowerCase());
	}

	public User save(UserRequest userRequest) {
		User u = new User();

		Matcher matcher = usernamePattern.matcher(userRequest.getUsername());
		if(!matcher.matches()){
			throw new RuntimeException("Usernames can only use letters, numbers, underscores, and periods.");
		}
		u.setUsername(userRequest.getUsername());

		matcher = passwordPattern.matcher(userRequest.getPassword());
		if(!matcher.matches()){
			throw new RuntimeException("Password must be 8-20 chars long, contain a big, small letter and a number!");
		}
		u.setPassword(passwordEncoder.encode(userRequest.getPassword()));

		matcher = namePattern.matcher(userRequest.getFirstname());
		if(!matcher.matches()){
			throw new RuntimeException("First name is in bad format (Must contain only alphabetic characters and hyphen)!");
		}
		u.setFirstName(userRequest.getFirstname());

		matcher = namePattern.matcher(userRequest.getLastname());
		if(!matcher.matches()){
			throw new RuntimeException("Last name is in bad format (Must contain only alphabetic characters and hyphen)!");
		}
		u.setLastName(userRequest.getLastname());
		u.setEnabled(true);

		matcher = emailPattern.matcher(userRequest.getEmail());
		if(!matcher.matches()){
			throw new RuntimeException("User email is in bad format!");
		}
		u.setEmail(userRequest.getEmail());

		List<Role> roles = new ArrayList<>();
		for(String role: userRequest.getRoles()) {
			roles.add(roleService.findByName(role));
		}
		u.setRoles(roles);
		
		return this.userRepository.save(u);
	}
}
