package com.example.booking_movie_ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
//@SpringBootApplication
public class BookingMovieTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingMovieTicketApplication.class, args);
	}

}
