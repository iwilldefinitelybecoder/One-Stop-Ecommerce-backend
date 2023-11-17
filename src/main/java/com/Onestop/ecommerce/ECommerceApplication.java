package com.Onestop.ecommerce;

import com.Onestop.ecommerce.Service.MailService.MailingServices;
import com.Onestop.ecommerce.Service.authService.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ECommerceApplication {
	@Autowired
	private MailingServices services;
	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

//	@Autowired
//	private AuthenticateService authenticateService;
//	@Bean
//	CommandLineRunner runner(){
//		authenticateService.addRoles("ADMIN");
//		authenticateService.addRoles("USER");
//		authenticateService.addRoles("VENDOR");
//		return args -> {
//			System.out.println("Application started");
//		};}



}
