package com.nick.db.starter.dbstarter;

import com.nick.db.starter.dbstarter.model.Users;
import com.nick.db.starter.dbstarter.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DbStarterApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DbStarterApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(DbStarterApplication.class, args);

	}

	@Bean
	CommandLineRunner commandLineRunner(UsersRepository usersRepository) {
		return args -> {
			Users bobby = new Users(
					"Bobby",
					"Shaftoe",
					"bobby.shaftoe@gmail.com",
					40
			);
			usersRepository.save(bobby);

			Users amy = new Users(
					"Amy",
					"Jackson",
					"amy@hotmail.com",
					37
			);
			usersRepository.save(amy);

			Users randy = new Users(
					"Randy",
					"Waterhouse",
					"randy_waterhouse@fastmail.net",
					45
			);
			usersRepository.save(randy);
		};
	}

}
