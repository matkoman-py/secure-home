package siitnocu.bezbednost.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import siitnocu.bezbednost.data.Estate;
import siitnocu.bezbednost.data.Role;
import siitnocu.bezbednost.data.User;
import siitnocu.bezbednost.data.UserRequest;
import siitnocu.bezbednost.dto.UserRequestDTO;
import siitnocu.bezbednost.dto.EstateDTO;
import siitnocu.bezbednost.dto.RoleUpdateInfo;
import siitnocu.bezbednost.dto.UserDTO;
import siitnocu.bezbednost.repositories.EstateRepository;
import siitnocu.bezbednost.repositories.UserRepository;
import siitnocu.bezbednost.repositories.UserRequestRepository;

@Service
public class UserService {

	@Autowired
	private UserRequestRepository userRequestRepository;
	
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
	
	public EstateDTO addEstateToUser(Long userId, Long estateId) {
		User u = findById(userId);
		Estate e = findByIdEstate(estateId);
		Set<Estate> estates = u.getEstates();
		estates.add(e);
		u.setEstates(estates);
		userRepository.save(u);
		return new EstateDTO(e);
	}
	
	public List<EstateDTO> getEstatesForUser(Long id) {
		User u = findById(id);
		return u.getEstates().stream().map(e -> new EstateDTO(e)).collect(Collectors.toList());
	}
	
	public List<EstateDTO> getAllEstates() {
		return estateRepository.findAll().stream().map(e -> new EstateDTO(e)).collect(Collectors.toList());
	}
	

	public Estate findByIdEstate(Long id) throws AccessDeniedException {
		return estateRepository.findById(id).orElseGet(null);
	}

	
	public User findById(Long id) throws AccessDeniedException {
		return userRepository.findById(id).orElseGet(null);
	}

	public List<UserDTO> findAll(String search) throws AccessDeniedException {
		List<User> users = userRepository.search(search.trim().toLowerCase());
		System.out.println(users.size() + " DASDAASD");
		return users.stream().map(u -> new UserDTO(u)).collect(Collectors.toList());
	}

	public User save(UserRequestDTO userRequest) {
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

	public String delete(Long Id) {
		User u = findById(Id);
		
		if(u != null) {
			userRepository.delete(u);
			return "User with id: " + Id + " succesfully deleated!";
		}
		return "User with id: " + Id + " not found!";
	}
	
	public UserDTO update(RoleUpdateInfo roleData) {
		System.out.println("EVO MEEEEEE" + roleData.getId());
		User u = findById(roleData.getId());
		
		if(u != null) {
			List<Role> oldRoles = new ArrayList<>();
			for(String role: roleData.getRoles()) {
				oldRoles.add(roleService.findByName(role));
			}
			u.setRoles(oldRoles);
			userRepository.save(u);
			return new UserDTO(u);
		}
		throw new RuntimeException("User with id: " + roleData.getId() + " not found!");
	}

	public UserDTO activateUser(Long id) {
		User u = findById(id);

		if(u == null){
			throw new RuntimeException("User with id: "+ id + "doesn't exist");
		}

		u.setEnabled(true);
		userRepository.save(u);
		return new UserDTO(u);
	}

	public User approve(Long id) {
		UserRequest ur = userRequestRepository.findById(id).orElse(null);
		if(ur == null) {
			throw new RuntimeException("UserRequest with id: "+ id + "doesn't exist");
		}
		
		User u = new User();
		u.setUsername(ur.getUsername());
		u.setEmail(ur.getEmail());
		u.setFirstName(ur.getFirstname());
		u.setLastName(ur.getLastname());
		u.setPassword(passwordEncoder.encode(ur.getPassword()));
		u.setRoles(new ArrayList<Role>());
		return userRepository.save(u);
	}
}
