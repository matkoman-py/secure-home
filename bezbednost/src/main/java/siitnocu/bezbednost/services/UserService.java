package siitnocu.bezbednost.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import siitnocu.bezbednost.data.Role;
import siitnocu.bezbednost.data.User;
import siitnocu.bezbednost.dto.UserRequest;
import siitnocu.bezbednost.dto.RoleUpdateInfo;
import siitnocu.bezbednost.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;

	public User findByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

	public User findById(Long id) throws AccessDeniedException {
		return userRepository.findById(id).orElseGet(null);
	}

	public List<User> findAll() throws AccessDeniedException {
		return userRepository.findAll();
	}

	public User save(UserRequest userRequest) {
		User u = new User();
		u.setUsername(userRequest.getUsername());

		u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		
		u.setFirstName(userRequest.getFirstname());
		u.setLastName(userRequest.getLastname());
		u.setEnabled(true);
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
	
	public String update(RoleUpdateInfo roleData) {
		User u = findById(roleData.getId());
		
		if(u != null) {
			List<Role> oldRoles = new ArrayList<>();
			for(String role: roleData.getRoles()) {
				oldRoles.add(roleService.findByName(role));
			}
			u.setRoles(oldRoles);
			userRepository.save(u);
			return "User with id: " + roleData.getId() + " succesfully updated!";
		}
		return "User with id: " + roleData.getId() + " not found!";
	}
}
