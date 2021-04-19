package br.com.alura.microservice.loja.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.alura.microservice.loja.client.FornecedorClient;
import br.com.alura.microservice.loja.controller.dto.CompraDTO;
import br.com.alura.microservice.loja.domain.Compra;
import br.com.alura.microservice.loja.repository.CompraRepository;

@Service
public class CompraService {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(CompraService.class);

	@Autowired
	private FornecedorClient fornecedorClient;

	@Autowired
	private CompraRepository compraRepository;

	// depois de pouco mais de 1s de aguardo dentro desse metodo, hystrix retorna
	// erro
	// a nao ser que o fallBackMethod seja utilizado
	@HystrixCommand(fallbackMethod = "realizaCompraFallback")
	public Compra realizaCompra(CompraDTO compra) {

		LOG.info("buscando informa coes do fornecedor de {}", compra.getEndereco().getEstado());
		var info = fornecedorClient.getInfoPorEstado(compra.getEndereco().getEstado());

		LOG.info("realizando pedido");
		var pedido = fornecedorClient.realizaPedido(compra.getItens());
		System.out.println(info.getEndereco());

		var compraRealizada = new Compra(pedido, compra.getEndereco().toString());
		compraRepository.save(compraRealizada);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return compraRealizada;
	}

	public Compra realizaCompraFallback(CompraDTO compra) throws Exception {
		throw new Exception("Compra não pode ser realizada no momento.");
	}

	@HystrixCommand
	public Compra getById(Long id) {
		return compraRepository.findById(id).get();
	}

}
