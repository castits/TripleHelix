package com.triplehelix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.triplehelix.schedulers.BookingReminderScheduler;
import com.triplehelix.schedulers.FeedbackEmailScheduler;

@SpringBootApplication(scanBasePackages = "com.triplehelix")
@EnableScheduling
public class TripleHelixProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripleHelixProjectApplication.class, args);
		
	}

}
