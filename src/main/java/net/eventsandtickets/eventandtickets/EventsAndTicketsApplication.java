package net.eventsandtickets.eventandtickets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class EventsAndTicketsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsAndTicketsApplication.class, args);
	}

}
