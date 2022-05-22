package siitnocu.bezbednost.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import siitnocu.bezbednost.data.Estate;
import siitnocu.bezbednost.data.User;
import siitnocu.bezbednost.dto.EstateDTO;
import siitnocu.bezbednost.dto.RoleUpdateInfo;
import siitnocu.bezbednost.dto.UserRequest;
import siitnocu.bezbednost.exception.ResourceConflictException;
import siitnocu.bezbednost.services.UserService;

// Primer kontrolera cijim metodama mogu pristupiti samo autorizovani korisnici
@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	// Za pristup ovoj metodi neophodno je da ulogovani korisnik ima READ_USER permisiju
	// Ukoliko nema, server ce vratiti gresku 403 Forbidden
	// Korisnik jeste autentifikovan, ali nije autorizovan da pristupi resursu
	@GetMapping("/user/{userId}")
	@PreAuthorize("hasAuthority('READ_USER')")
	public User loadById(@PathVariable Long userId) {
		return this.userService.findById(userId);
	}
	
	@PostMapping("/user/{userId}/{estateId}")
	public User addEstateToUser(@PathVariable Long userId, @PathVariable Long estateId) {
		return this.userService.addEstateToUser(userId, estateId);
	}
	
	@GetMapping("/user/estates/{userId}")
	public List<EstateDTO> getEstatesForUser(@PathVariable Long userId) {
		return this.userService.getEstatesForUser(userId);
	}

	@GetMapping("/all")
	@PreAuthorize("hasAuthority('READ_USERS')")
	public List<User> loadAll(@RequestParam(value = "search", defaultValue = "") String search) {
		return this.userService.findAll(search);
	}

	@GetMapping("/whoami")
	@PreAuthorize("hasAuthority('FIND_USER')")
	public User user(Principal user) {
		return this.userService.findByUsername(user.getName());
	}
	
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('SAVE_USER')")
	public User save(@RequestBody UserRequest user) {
		User existUser = this.userService.findByUsername(user.getUsername());

		if (existUser != null) {
			throw new ResourceConflictException(user.getId(), "Username already exists");
		}

		return this.userService.save(user);
	}
	
	@DeleteMapping("/delete/{Id}")
	@PreAuthorize("hasAuthority('DELETE_USER')")
	public String delete(@PathVariable Long Id) {
		return this.userService.delete(Id);
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasAuthority('UPDATE_USER')")
	public String update(@RequestBody RoleUpdateInfo roleUpdateInfo) {
		return this.userService.update(roleUpdateInfo);
	}
}
