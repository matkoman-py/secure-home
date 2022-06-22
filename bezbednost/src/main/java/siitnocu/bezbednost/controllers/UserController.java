package siitnocu.bezbednost.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import siitnocu.bezbednost.services.CustomLogger;
import siitnocu.bezbednost.services.UserService;

// Primer kontrolera cijim metodama mogu pristupiti samo autorizovani korisnici
@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
@Transactional
public class UserController {
	
	@Autowired
	CustomLogger customLogger;
	
	Logger logger = LoggerFactory.getLogger(CustomLogger.class);

	@Autowired
	private UserService userService;

	// Za pristup ovoj metodi neophodno je da ulogovani korisnik ima READ_USER permisiju
	// Ukoliko nema, server ce vratiti gresku 403 Forbidden
	// Korisnik jeste autentifikovan, ali nije autorizovan da pristupi resursu
	@GetMapping("/user/{userId}")
	@PreAuthorize("hasAuthority('READ_USER')")
	public User loadById(@PathVariable Long userId) {
		logger.info(customLogger.info("Requesting user with id " + userId));
		return this.userService.findById(userId);
	}
	
	@PostMapping("/user/{userId}/{estateId}")
	@PreAuthorize("hasAuthority('ADD_ESTATE')")
	public EstateDTO addEstateToUser(@PathVariable Long userId, @PathVariable Long estateId) {
		
		logger.info(customLogger.info("Adding estate with id " + estateId + " to user with id " + userId));
		return this.userService.addEstateToUser(userId, estateId);
	}
	
	@GetMapping("/user/estates/{userId}")
	@PreAuthorize("hasAuthority('READ_ESTATES_USER')")
	public List<EstateDTO> getEstatesForUser(@PathVariable Long userId) {
		logger.info(customLogger.info("Requesting estates for user with id " + userId));

		return this.userService.getEstatesForUser(userId);
	}
	
	@GetMapping("/user/estates")
	@PreAuthorize("hasAuthority('READ_ESTATES')")
	public List<EstateDTO> getEstates() {
		logger.info(customLogger.info("Requesting all estates"));
		return this.userService.getAllEstates();
	}

	@GetMapping("/all")
	@PreAuthorize("hasAuthority('READ_USERS')")
	public List<UserDTO> loadAll(@RequestParam(value = "search", defaultValue = "") String search) {
		logger.info(customLogger.info("Requesting all  with search parameter: " + search));
		return this.userService.findAll(search);
	}

	@GetMapping("/whoami")
	@PreAuthorize("hasAuthority('FIND_USER')")
	public User user(Principal user) {
		logger.info(customLogger.info("Requesting user with name " + user.getName()));
		return this.userService.findByUsername(user.getName());
	}
	
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('SAVE_USER')")
	public User save(@RequestBody UserRequestDTO user) {
		User existUser = this.userService.findByUsername(user.getUsername());

		if (existUser != null) {
			logger.error(customLogger.error("Unsuccessfull registration, username " + user.getUsername() + " already exists"));
			throw new ResourceConflictException(user.getId(), "Username already exists");
		}

		return this.userService.save(user);
	}

	@PostMapping("/approve/{id}")
	@PreAuthorize("hasAuthority('SAVE_USER')")
	public User approve(@PathVariable Long id) {
		logger.info(customLogger.info("User with id " + id + " approved"));
		return this.userService.approve(id);
	}
	
	@DeleteMapping("/delete/{Id}")
	@PreAuthorize("hasAuthority('DELETE_USER')")
	public String delete(@PathVariable Long Id) {
		logger.info(customLogger.info("User with id " + Id + " deleted"));
		return this.userService.delete(Id);
	}

	@PutMapping("/activate/{Id}")
	@PreAuthorize("hasAuthority('ACTIVATE_USER')")
	public UserDTO activate(@PathVariable Long Id) {
		logger.info(customLogger.info("User with id " + Id + " activated"));
		return this.userService.activateUser(Id);
	}

	@PutMapping("/update")
	@PreAuthorize("hasAuthority('UPDATE_USER')")
	public UserDTO update(@RequestBody RoleUpdateInfo roleUpdateInfo) {
		logger.info(customLogger.info("User with id " + roleUpdateInfo.getId() + " updated"));
		return this.userService.update(roleUpdateInfo);
	}
}
