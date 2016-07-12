package com.oriaxx77.microservicedemo.shopservice;

import java.net.URISyntaxException;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableCircuitBreaker
//@EnableResourceServer
public class ShopServiceApplication {	
		
	private static final Logger LOG = LoggerFactory.getLogger(ShopServiceApplication.class);
	
	@Autowired
	private ShopService shopService;
	
	@LoadBalanced
	@Bean
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	@RequestMapping
//	public String home( @RequestHeader(value="Authorization") String authorizationHeader,
//						Principal currentUser) throws RestClientException, URISyntaxException {
//		LOG.info("ShopService: User={}, Auth={}, called with productId={}", currentUser.getName(), authorizationHeader);
//		return shopService.home();
//	}
	

	public String home() throws RestClientException, URISyntaxException {
		return shopService.home();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ShopServiceApplication.class, args);
	}
}
