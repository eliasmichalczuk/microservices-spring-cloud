package br.com.alura.microservice.loja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.client.RestTemplate;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker // hystrix
@EnableResourceServer
public class LojaApplication {

	// método invocado pelo Feign para pegar o token da requisicao
	// feita para a loja e enviar esse token para o fornecedor-service
	@Bean
	public RequestInterceptor getAuthRequestInterceptor() {
		return new RequestInterceptor() {

			@Override
			public void apply(RequestTemplate template) {
				var auth = SecurityContextHolder.getContext().getAuthentication();
				if (auth != null) {
					var oauthRequest = (OAuth2AuthenticationDetails) auth.getDetails();
					template.header("Authorization", "Bearer " + oauthRequest.getTokenValue());
				}
			}
		};
	}

	@Bean
	@LoadBalanced // ribbon, client side load balance
	public RestTemplate getRestTempĺate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(LojaApplication.class, args);
	}

}
