package siitnocu.bezbednost.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import siitnocu.bezbednost.data.User;
import siitnocu.bezbednost.data.UserRequest;
import siitnocu.bezbednost.dto.UserRequestDTO;
import siitnocu.bezbednost.exception.ResourceConflictException;
import siitnocu.bezbednost.services.UserRequestService;
import siitnocu.bezbednost.services.UserService;

@RestController
@RequestMapping(value = "/api/user-requests", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRequestController {

	@Autowired
	private UserRequestService userRequestService;
	
	@Autowired	
	private UserService userService;
	
	@PostMapping("/save")
	public UserRequest save(@RequestBody UserRequestDTO userRequestData) {
		User existUser = this.userService.findByUsername(userRequestData.getUsername());

		if (existUser != null) {
			throw new ResourceConflictException(userRequestData.getId(), "Username already exists");
		}

		return this.userRequestService.save(userRequestData);
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('READ_USERS')")
	public List<UserRequest> loadAll() {
		return this.userRequestService.findAll();
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('READ_USERS')")
	public String delete(@PathVariable Long id) {
		return this.userRequestService.delete(id);
	}
}
