package com.application.game;

import com.application.game.models.PaymentGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GameApplication  {

	@Autowired
	PaymentGateway paymentGateway;

	public static void main(String[] args) {
		SpringApplication.run(GameApplication.class, args);
	}


}
