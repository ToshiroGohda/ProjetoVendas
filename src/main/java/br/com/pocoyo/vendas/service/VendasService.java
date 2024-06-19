package br.com.pocoyo.vendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pocoyo.vendas.entity.Vendas;
import br.com.pocoyo.vendas.repository.VendasRepository;


@Service
public class VendasService {

	@Autowired
	VendasRepository vendasRepository;
	
	public Vendas save(Vendas vendas) {
		return vendasRepository.save(vendas);
	}

	public List<Vendas> findAll() {
		return vendasRepository.findAll();
	}
	
	public Optional<Vendas> findById(Long id){
		return vendasRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		vendasRepository.deleteById(id);
	}
	
}
