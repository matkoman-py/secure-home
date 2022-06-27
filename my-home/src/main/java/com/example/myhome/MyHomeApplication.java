package com.example.myhome;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import com.example.myhome.domain.Rule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MyHomeApplication {

	public static void main(String[] args) throws IOException {
//		BufferedWriter writer = new BufferedWriter(new PrintWriter("../bezbednost/src/main/resources/rules.drl"));
//        String ruleString = "import  com.example.myhome.domain.*;\r\n";
//		
//		
//		writer.write(ruleString);
//
//        writer.close();
	
		SpringApplication.run(MyHomeApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
