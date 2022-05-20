package siitnocu.bezbednost.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import siitnocu.bezbednost.data.User;
import siitnocu.bezbednost.dto.RoleUpdateInfo;
import siitnocu.bezbednost.dto.UserRequest;
import siitnocu.bezbednost.services.UserService;

// Primer kontrolera cijim metodama mogu pristupiti samo autorizovani korisnici
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
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

	@GetMapping("/user/all")
	@PreAuthorize("hasAuthority('READ_USERS')")
	public List<User> loadAll() {
		return this.userService.findAll();
	}

	@GetMapping("/whoami")
	@PreAuthorize("hasAuthority('FIND_USER')")
	public User user(Principal user) {
		return this.userService.findByUsername(user.getName());
	}
	
	//TODO: Dodati u data.sql
	
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('SAVE_USER')")
	public User save(@RequestBody UserRequest user) {
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
