package br.com.alura.microservice.loja.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.alura.microservice.loja.domain.Compra;

public interface CompraRepository extends CrudRepository<Compra, Long> {

}
