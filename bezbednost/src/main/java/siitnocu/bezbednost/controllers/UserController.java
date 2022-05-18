package siitnocu.bezbednost.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import siitnocu.bezbednost.data.User;
import siitnocu.bezbednost.dto.UserDTO;
import siitnocu.bezbednost.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/create")
	public ResponseEntity<String> create(@RequestBody UserDTO userData) throws Exception {
		return ResponseEntity.ok(userService.create(userData));
	}
	
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/delete")
	public ResponseEntity<String> create(@PathVariable("Id") Integer Id) throws Exception {
		return ResponseEntity.ok(userService.delete(Id));
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/get-all")
	public ResponseEntity<List<User>> getAll() throws Exception {
		return ResponseEntity.ok(userService.getAll());
	}
}
