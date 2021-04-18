package br.com.alura.microservice.loja.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.microservice.loja.client.FornecedorClient;
import br.com.alura.microservice.loja.controller.dto.CompraDTO;
import br.com.alura.microservice.loja.domain.Compra;

@Service
public class CompraService {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(CompraService.class);

	@Autowired
	private FornecedorClient fornecedorClient;

	public Compra realizaCompra(CompraDTO compra) {

		LOG.info("buscando informa coes do fornecedor de {}", compra.getEndereco().getEstado());
		var info = fornecedorClient.getInfoPorEstado(compra.getEndereco().getEstado());

		LOG.info("realizando pedido");
		var pedido = fornecedorClient.realizaPedido(compra.getItens());
		System.out.println(info.getEndereco());

		var compraRealizada = new Compra(pedido, compra.getEndereco().toString());

		return compraRealizada;
	}

}
