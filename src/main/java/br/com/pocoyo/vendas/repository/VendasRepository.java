package br.com.pocoyo.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pocoyo.vendas.entity.Vendas;

@Repository
public interface VendasRepository extends JpaRepository<Vendas, Long>{

}
