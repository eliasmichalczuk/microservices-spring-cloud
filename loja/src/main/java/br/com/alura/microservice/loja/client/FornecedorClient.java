package br.com.alura.microservice.loja.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.alura.microservice.loja.controller.dto.InfoFornecedorDto;
import br.com.alura.microservice.loja.controller.dto.ItemDaCompraDTO;
import br.com.alura.microservice.loja.dto.InfoPedidoDto;

@FeignClient("fornecedor-service")
public interface FornecedorClient {

	@RequestMapping("/info/{estado}")
	InfoFornecedorDto getInfoPorEstado(@PathVariable String estado);

	@RequestMapping(value = "/pedido/{estado}", method = RequestMethod.POST)
	InfoPedidoDto realizaPedido(List<ItemDaCompraDTO> itens);
}
