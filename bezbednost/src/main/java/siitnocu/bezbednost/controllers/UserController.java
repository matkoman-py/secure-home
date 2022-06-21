package siitnocu.bezbednost.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import siitnocu.bezbednost.data.User;
import siitnocu.bezbednost.dto.EstateDTO;
import siitnocu.bezbednost.dto.RoleUpdateInfo;
import siitnocu.bezbednost.dto.UserDTO;
import siitnocu.bezbednost.dto.UserRequestDTO;
import siitnocu.bezbednost.exception.ResourceConflictException;
import siitnocu.bezbednost.services.UserService;

// Primer kontrolera cijim metodama mogu pristupiti samo autorizovani korisnici
@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
@Transactional
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
	@PreAuthorize("hasAuthority('ADD_ESTATE')")
	public EstateDTO addEstateToUser(@PathVariable Long userId, @PathVariable Long estateId) {
		return this.userService.addEstateToUser(userId, estateId);
	}
	
	@GetMapping("/user/estates/{userId}")
	@PreAuthorize("hasAuthority('READ_ESTATES_USER')")
	public List<EstateDTO> getEstatesForUser(@PathVariable Long userId) {
		return this.userService.getEstatesForUser(userId);
	}
	
	@GetMapping("/user/estates")
	@PreAuthorize("hasAuthority('READ_ESTATES')")
	public List<EstateDTO> getEstates() {
		return this.userService.getAllEstates();
	}

	@GetMapping("/all")
	@PreAuthorize("hasAuthority('READ_USERS')")
	public List<UserDTO> loadAll(@RequestParam(value = "search", defaultValue = "") String search) {
		return this.userService.findAll(search);
	}

	@GetMapping("/whoami")
	@PreAuthorize("hasAuthority('FIND_USER')")
	public User user(Principal user) {
		return this.userService.findByUsername(user.getName());
	}
	
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('SAVE_USER')")
	public User save(@RequestBody UserRequestDTO user) {
		User existUser = this.userService.findByUsername(user.getUsername());

		if (existUser != null) {
			throw new ResourceConflictException(user.getId(), "Username already exists");
		}

		return this.userService.save(user);
	}

	@PostMapping("/approve/{id}")
	@PreAuthorize("hasAuthority('SAVE_USER')")
	public User approve(@PathVariable Long id) {
		return this.userService.approve(id);
	}
	
	@DeleteMapping("/delete/{Id}")
	@PreAuthorize("hasAuthority('DELETE_USER')")
	public String delete(@PathVariable Long Id) {
		return this.userService.delete(Id);
	}

	@PutMapping("/activate/{Id}")
	@PreAuthorize("hasAuthority('ACTIVATE_USER')")
	public UserDTO activate(@PathVariable Long Id) {
		return this.userService.activateUser(Id);
	}

	@PutMapping("/update")
	@PreAuthorize("hasAuthority('UPDATE_USER')")
	public UserDTO update(@RequestBody RoleUpdateInfo roleUpdateInfo) {
		return this.userService.update(roleUpdateInfo);
	}
}
