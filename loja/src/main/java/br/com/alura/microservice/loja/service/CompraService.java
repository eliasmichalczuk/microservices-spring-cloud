package br.com.alura.microservice.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
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

	public void realizaCompra(CompraDTO compra) {
//		RestTemplate client = new RestTemplate();
//		String url = "http://fornecedor/info/" + compra.getEndereco().getEstado();
//		var response = client.exchange(url, HttpMethod.GET, null, InfoFornecedorDto.class);
//
//		System.out.println(response.getBody().getEndereco());

		String url = "http://fornecedor-service/info/" + compra.getEndereco().getEstado();
		var response = client.exchange(url, HttpMethod.GET, null, InfoFornecedorDto.class);

		System.out.println(response.getBody().getEndereco());
	}

}
