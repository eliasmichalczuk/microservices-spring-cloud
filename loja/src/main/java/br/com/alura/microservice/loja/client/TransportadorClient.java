package br.com.alura.microservice.loja.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.microservice.loja.dto.InfoEntregaDto;
import br.com.alura.microservice.loja.dto.VoucherDto;

@FeignClient("transportador-service")
public interface TransportadorClient {

	@RequestMapping("/entrega")
	public VoucherDto reservaEntrega(InfoEntregaDto dto);
}
