package siitnocu.bezbednost.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import siitnocu.bezbednost.data.User;
import siitnocu.bezbednost.repositories.UserRepository;

// Ovaj servis je namerno izdvojen kao poseban u ovom primeru.
// U opstem slucaju UserServiceImpl klasa bi mogla da implementira UserDetailService interfejs.
@Service
public class CustomUserDetailsService  implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	// Funkcija koja na osnovu username-a iz baze vraca objekat User-a
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			return user;
		}
	}

}
