package com.nick.db.starter.dbstarter;

import com.nick.db.starter.dbstarter.repository.Users;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbStarterApplication.class, args);
	}

//	CommandLineRunner commandLineRunner(Users users)

}
