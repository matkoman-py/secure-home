package siitnocu.bezbednost.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import siitnocu.bezbednost.data.User;
import siitnocu.bezbednost.dto.UserDTO;
import siitnocu.bezbednost.repositories.RoleRepository;
import siitnocu.bezbednost.repositories.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	public UserService() {
		
	}
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public String create(UserDTO userData) {
		User newUser = new User(userData.getUsername(),
								userData.getPassword(),
								userData.getName(),
								userData.getSurname(),
								userData.getEmail(),
								userData.getDateOfBirth(),
								roleRepository.findByRoleName(userData.getRoleName()).get());
		
		userRepository.save(newUser);
		return "User " + userData.getUsername() + " succesfully created";
	}
	
	public String delete(Integer Id) {
		if(userRepository.findById(Id) != null) {
			userRepository.deleteById(Id);
			return "User with id: " + Id + " succesfully deleated";
		}
		
		return "No user with id: " + Id + " was found";
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}
}
