package siitnocu.bezbednost.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import siitnocu.bezbednost.data.NonValidToken;
import siitnocu.bezbednost.data.UnsuccessfullLogin;
import siitnocu.bezbednost.data.User;
import siitnocu.bezbednost.dto.JwtAuthenticationRequest;
import siitnocu.bezbednost.dto.UserRequest;
import siitnocu.bezbednost.dto.UserTokenState;
import siitnocu.bezbednost.exception.ResourceConflictException;
import siitnocu.bezbednost.repositories.NonValidTokenRepository;
import siitnocu.bezbednost.repositories.UnsuccessfullLoginRepository;
import siitnocu.bezbednost.repositories.UserRepository;
import siitnocu.bezbednost.services.CustomLogger;
import siitnocu.bezbednost.services.UserService;
import siitnocu.bezbednost.utils.TokenUtils;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	CustomLogger customLogger;

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private NonValidTokenRepository nonValidTokenRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UnsuccessfullLoginRepository unsuccessfullLoginRepository;

	private static final String USERNAME_PATTERN = "^[A-Za-z0-9_.]+$";
	private static final Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN);
	Logger logger = LoggerFactory.getLogger(CustomLogger.class);

	private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
	private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

	// Prvi endpoint koji pogadja korisnik kada se loguje.
	// Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
	@PostMapping("/login")
	public ResponseEntity<UserTokenState> createAuthenticationToken(
			@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {
		 	logger.info(customLogger.info("--------------------------IDE GAS--------------------------"));
		try {

			Matcher matcher = usernamePattern.matcher(authenticationRequest.getUsername());
			if (!matcher.matches()) {
				throw new RuntimeException("Usernames can only use letters, numbers, underscores, and periods.");
			}

			matcher = passwordPattern.matcher(authenticationRequest.getPassword());
			if (!matcher.matches()) {
				throw new RuntimeException("Invalid character in password attempt!");
			}
			// Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
			// AuthenticationException
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));

			// Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
			// kontekst
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Kreiraj token za tog korisnika
			User user = (User) authentication.getPrincipal();
			String fingerprint = tokenUtils.generateFingerprint();
			String jwt = tokenUtils.generateToken(user.getUsername(), fingerprint, user.getRoles());
			int expiresIn = tokenUtils.getExpiredIn();

			// Kreiraj cookie
			// String cookie = "__Secure-Fgp=" + fingerprint + "; SameSite=Strict; HttpOnly;
			// Path=/; Secure"; // kasnije mozete probati da postavite i ostale atribute,
			// ali tek nakon sto podesite https
			String cookie = "Fingerprint=" + fingerprint + "; HttpOnly; Path=/";

			HttpHeaders headers = new HttpHeaders();
			headers.add("Set-Cookie", cookie);

			// Vrati token kao odgovor na uspesnu autentifikaciju
			return ResponseEntity.ok().headers(headers).body(new UserTokenState(jwt, expiresIn));
		} catch (AuthenticationException exception) {
			User user = userRepository.findByUsername(authenticationRequest.getUsername());
			if (user == null) {
				return new ResponseEntity("Bad credentials!", HttpStatus.UNAUTHORIZED);
			}

			UnsuccessfullLogin unsuccessfullLogin = new UnsuccessfullLogin();
			unsuccessfullLogin.setUsername(authenticationRequest.getUsername());
			unsuccessfullLogin.setDate(new Date());
			unsuccessfullLoginRepository.save(unsuccessfullLogin);
			List<UnsuccessfullLogin> unsuccessfullLoginList = unsuccessfullLoginRepository
					.findByUsername(authenticationRequest.getUsername());

			int howMuchLast5Mins = 0;
			Date currentTime = new Date();
			currentTime.setMinutes(currentTime.getMinutes() - 5);
			for (UnsuccessfullLogin elem : unsuccessfullLoginList) {
				if (elem.getDate().after(currentTime)) {
					howMuchLast5Mins++;
				}
			}

			if (howMuchLast5Mins >= 3) {
				System.out.println("LOCKED ACC");
				user.setEnabled(false);
				userRepository.save(user);
				return new ResponseEntity("Your account has been locked!", HttpStatus.UNAUTHORIZED);
			}
			return new ResponseEntity("Bad credentials, warning: " + (3 - howMuchLast5Mins) + " more attempts!",
					HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("/logout")
	@PreAuthorize("hasAuthority('LOGOUT')")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		String authToken = tokenUtils.getToken(request);
		NonValidToken nonValidToken = new NonValidToken();
		nonValidToken.setToken(authToken);
		nonValidTokenRepository.save(nonValidToken);
		return new ResponseEntity<>("Success!", HttpStatus.OK);
	}
}