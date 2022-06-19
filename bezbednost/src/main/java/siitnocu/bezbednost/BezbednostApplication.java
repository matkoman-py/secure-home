package siitnocu.bezbednost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BezbednostApplication {

	public static void main(String[] args) {
		System.out.println("=================================================");
		System.out.println(System.getProperty("java.class.path") + " DASDSA");
		System.out.println("=================================================");

		SpringApplication.run(BezbednostApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
