package com.example.booking_movie_ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication(exclude = {
//		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
//})
@SpringBootApplication
@EnableScheduling
public class BookingMovieTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingMovieTicketApplication.class, args);
	}

}
