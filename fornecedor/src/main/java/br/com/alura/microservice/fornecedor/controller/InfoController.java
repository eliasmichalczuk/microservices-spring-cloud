package br.com.alura.microservice.fornecedor.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.microservice.fornecedor.domain.InfoFornecedor;
import br.com.alura.microservice.fornecedor.service.InfoService;

@RestController
@RequestMapping("/info")
public class InfoController {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(InfoController.class);

	@Autowired
	private InfoService infoService;

	@RequestMapping("/{estado}")
	public InfoFornecedor getInfoPorEstado(@PathVariable String estado) {
		LOG.info("recebido pedido de info do forne de {}", estado);
		return infoService.getInfoPorEstado(estado);
	}
}
