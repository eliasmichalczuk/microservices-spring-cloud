package br.com.alura.microservice.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.alura.microservice.loja.controller.dto.CompraDTO;
import br.com.alura.microservice.loja.controller.dto.InfoFornecedorDto;

@Service
public class CompraService {

	// pega o restTemplate, provido no LojaApplication
	// esse rest template, verifica no eureka os servicos, e
	// devolve o fornecedor-service para nós
	@Autowired
	private RestTemplate client;

	@Autowired
	private DiscoveryClient eurekaClient;

	public void realizaCompra(CompraDTO compra) {
//		RestTemplate client = new RestTemplate();
//		String url = "http://fornecedor/info/" + compra.getEndereco().getEstado();
//		var response = client.exchange(url, HttpMethod.GET, null, InfoFornecedorDto.class);
//
//		System.out.println(response.getBody().getEndereco());
//		https://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.2.0.M3/reference/html/#customizing-the-default-for-all-ribbon-clients
		eurekaClient.getInstances("fornecedor-service")
		            .stream()
		            .forEach(fornecedor -> { System.out.println("localhost:" + fornecedor.getPort()); });

		String url = "http://fornecedor-service/info/" + compra.getEndereco().getEstado();
		var response = client.exchange(url, HttpMethod.GET, null, InfoFornecedorDto.class);

		System.out.println(response.getBody().getEndereco());
	}

}
