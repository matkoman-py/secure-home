package siitnocu.bezbednost.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	//Metoda koja se izvrsava ukoliko za prosledjene kredencijale korisnik pokusa da pristupi zasticenom REST servisu
	//Metoda vraca 401 Unauthorized response, ukoliko postoji Login Page u aplikaciji, pozeljno je da se korisnik redirektuje na tu stranicu
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        Map<String, String> error = new HashMap<>();
        error.put("message", "Please log in!");
        response.setContentType("application/json");
        response.setStatus(401);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}

