package com.stw.sourceme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SourceMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SourceMeApplication.class, args);
	}

}
