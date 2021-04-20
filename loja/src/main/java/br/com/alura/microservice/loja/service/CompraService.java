package br.com.alura.microservice.loja.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.alura.microservice.loja.client.FornecedorClient;
import br.com.alura.microservice.loja.client.TransportadorClient;
import br.com.alura.microservice.loja.controller.dto.CompraDTO;
import br.com.alura.microservice.loja.domain.Compra;
import br.com.alura.microservice.loja.dto.InfoEntregaDto;
import br.com.alura.microservice.loja.dto.VoucherDto;
import br.com.alura.microservice.loja.repository.CompraRepository;

@Service
public class CompraService {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(CompraService.class);

	@Autowired
	private FornecedorClient fornecedorClient;

	@Autowired
	private TransportadorClient transportadorClient;

	@Autowired
	private CompraRepository compraRepository;

	// depois de pouco mais de 1s de aguardo dentro desse metodo, hystrix retorna
	// erro
	// a nao ser que o fallBackMethod seja utilizado
	@HystrixCommand(fallbackMethod = "realizaCompraFallback", threadPoolKey = "realizaCompraThreadPool")
	public Compra realizaCompra(CompraDTO compra) {

		var compraRealizada = new Compra(compra.getEndereco().toString());
		compraRepository.save(compraRealizada);

		LOG.info("buscando informa coes do fornecedor de {}", compra.getEndereco().getEstado());
		var info = fornecedorClient.getInfoPorEstado(compra.getEndereco().getEstado());

		LOG.info("realizando pedido");
		var pedido = fornecedorClient.realizaPedido(compra.getItens());

		compra.setCompraId(compraRealizada.getId());
		compraRealizada.pedidoRealizado(pedido);
		compraRepository.save(compraRealizada);

//		if (true)
//			throw new Error();
		// ao dar erro, cai no metodo fallback descrito no hystrix command

		var infoEntrega = new InfoEntregaDto(pedido.id,
		                                     LocalDate.now().plusDays(pedido.tempoDePreparo),
		                                     info.getEndereco(),
		                                     compra.getEndereco().toString());
		VoucherDto voucher = transportadorClient.reservaEntrega(infoEntrega);

		compraRealizada.reservaRealizada(voucher);
		compraRepository.save(compraRealizada);

		return compraRealizada;
	}

	public Compra realizaCompraFallback(CompraDTO compra) throws Exception {

		if (compra.getCompraId() != null) {
			return compraRepository.findById(compra.getCompraId()).get();

			// o cliente pode reprocessar ou cancelar a compra a partir do id
		}

		throw new Exception("Compra não pode ser realizada no momento.");
	}

	// bulk head, grupos diferentes de threads para requisicoes diferentes
	@HystrixCommand(threadPoolKey = "getByIdThreadPool")
	public Compra getById(Long id) {
		return compraRepository.findById(id).get();
	}

}
