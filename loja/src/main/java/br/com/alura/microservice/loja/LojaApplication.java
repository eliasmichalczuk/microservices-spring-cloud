package br.com.alura.microservice.loja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class LojaApplication {

	@Bean
	@LoadBalanced // ribbon, client side load balance
	public RestTemplate getRestTempĺate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(LojaApplication.class, args);
	}

}
