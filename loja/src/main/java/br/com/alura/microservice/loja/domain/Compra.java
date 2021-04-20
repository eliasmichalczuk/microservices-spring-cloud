package br.com.alura.microservice.loja.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.alura.microservice.loja.dto.InfoPedidoDto;
import br.com.alura.microservice.loja.dto.VoucherDto;

@Entity
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	@Enumerated(EnumType.STRING)
	private SituacaoCompra situacao;
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

	public void pedidoRealizado(InfoPedidoDto pedido) {
		pedidoId = pedido.id;
		tempoDePreparo = pedido.tempoDePreparo;
		situacao = SituacaoCompra.PEDIDO_REALIZADO;
	}

	public void reservaRealizada(VoucherDto voucher) {
		voucherId = voucher.getNumero();
		dataEntrega = voucher.getPrevisaoParaEntrega();
		situacao = SituacaoCompra.RESERVA_REALIZADA;
	}

	public Compra(String endereco) {
		this.enderecoDestino = endereco;
		situacao = SituacaoCompra.RECEBIDO;
	}

	public SituacaoCompra getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoCompra situacao) {
		this.situacao = situacao;
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
