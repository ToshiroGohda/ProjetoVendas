package br.com.pocoyo.vendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pocoyo.vendas.entity.Produto;
import br.com.pocoyo.vendas.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	public Produto save(Produto produto) {
		return produtoRepository.save(produto);
	}

	public List<Produto> findAll() {
		return produtoRepository.findAll();
	}
	
	public Optional<Produto> findById(Long id){
		return produtoRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		produtoRepository.deleteById(id);
	}
	
}
