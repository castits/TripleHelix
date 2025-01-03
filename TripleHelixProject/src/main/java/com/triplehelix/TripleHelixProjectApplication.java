package com.triplehelix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.triplehelix")
public class TripleHelixProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripleHelixProjectApplication.class, args);
	}

}
