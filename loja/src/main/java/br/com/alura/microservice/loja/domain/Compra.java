package br.com.alura.microservice.loja.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.alura.microservice.loja.dto.InfoPedidoDto;

@Entity
public class Compra {

	@Id
	private Long pedidoId;
	private Integer tempoDePreparo;
	private String enderecoDestino;
	private LocalDate dataEntrega;
	private Long voucherId;

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
	}

	public Compra(Long pedidoId, Integer tempoDePreparo) {
		super();
		this.pedidoId = pedidoId;
		this.tempoDePreparo = tempoDePreparo;
	}

	public Compra() {
		super();
	}

	public String getEnderecoDestino() {
		return enderecoDestino;
	}

	public void setEnderecoDestino(String enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
	}

	public Compra(InfoPedidoDto pedido, String endereco) {
		this.pedidoId = pedido.id;
		this.tempoDePreparo = pedido.tempoDePreparo;
		this.enderecoDestino = endereco;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public Integer getTempoDePreparo() {
		return tempoDePreparo;
	}

	public void setTempoDePreparo(Integer tempoDePreparo) {
		this.tempoDePreparo = tempoDePreparo;
	}

}
