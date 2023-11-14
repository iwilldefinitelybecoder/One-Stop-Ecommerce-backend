package com.Onestop.ecommerce;

import com.Onestop.ecommerce.Service.MailService.MailingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ECommerceApplication {
	@Autowired
	private MailingServices services;
	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}





}
