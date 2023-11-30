package com.Onestop.ecommerce;

import com.Onestop.ecommerce.Entity.Logistics.WareHouse;
import com.Onestop.ecommerce.Repository.LogisticsRepo.WareHouseRepo;
import com.Onestop.ecommerce.Service.MailService.MailingServices;
import com.Onestop.ecommerce.Service.authService.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;

@SpringBootApplication
public class ECommerceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

//	@Autowired
//	private AuthenticateService authenticateService;
//	@Autowired
//	private WareHouseRepo wareHouseRepo;
//	@Bean
//	CommandLineRunner runner(){
//		authenticateService.addRoles("ADMIN");
//		authenticateService.addRoles("USER");
//		authenticateService.addRoles("VENDOR");
//		var wareHouse = WareHouse.builder()
//				.wareHouseLocation("bangalore")
//				.wareHouseName("bangalore")
//				.contactNumber(8315485645L)
//				.inventory(new ArrayList<>())
//				.capacity(1000L)
//				.build();
//		wareHouseRepo.save(wareHouse);
//		return args -> {
//			System.out.println("Application started");
//		};}






}
