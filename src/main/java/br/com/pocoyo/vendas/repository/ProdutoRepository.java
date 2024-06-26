package br.com.pocoyo.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import br.com.pocoyo.vendas.entity.Produto;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	List<Produto>findByDescProdutoStartsWithIgnoreCase(String filterText);
	
	

}
