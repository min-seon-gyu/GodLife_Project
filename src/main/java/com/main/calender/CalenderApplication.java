package com.main.calender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class CalenderApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(CalenderApplication.class);
		app.addListeners(new ApplicationPidFileWriter()); // ApplicationPidFileWriter 설정
		app.run(args);
	}

}
