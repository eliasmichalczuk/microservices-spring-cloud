package br.com.alura.microservice.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.microservice.loja.client.FornecedorClient;
import br.com.alura.microservice.loja.controller.dto.CompraDTO;
import br.com.alura.microservice.loja.domain.Compra;

@Service
public class CompraService {

	@Autowired
	private FornecedorClient fornecedorClient;

	public Compra realizaCompra(CompraDTO compra) {
		var info = fornecedorClient.getInfoPorEstado(compra.getEndereco().getEstado());

		var pedido = fornecedorClient.realizaPedido(compra.getItens());
		System.out.println(info.getEndereco());

		var compraRealizada = new Compra(pedido, compra.getEndereco().toString());

		return compraRealizada;
	}

}
