package br.com.pocoyo.vendas.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Vendas {

	
	@Id
	@GeneratedValue
	private Long cd_venda;
	
	@CreatedDate
	private Calendar data;
	
	@CreationTimestamp
	private Timestamp hora;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Produto> produtos = new ArrayList<Produto>();
	
	private float va_total;

	public void addProduto(Produto produto) {
		produtos.add(produto);
	}
	
	public float ValorTotal() {
		float i = 0;
		
		for(Produto p : produtos) {
			i = i + p.getVa_venda();
		}
		
		return i; 
	}
	
	public Vendas() {
		super();
	}

	public Vendas(Long cd_venda, Calendar data, Timestamp hora, List<Produto> produtos, float va_total) {
		super();
		this.cd_venda = cd_venda;
		this.data = data;
		this.hora = hora;
		this.produtos = produtos;
		this.va_total = va_total;
	}

	public Long getCd_venda() {
		return cd_venda;
	}

	public void setCd_venda(Long cd_venda) {
		this.cd_venda = cd_venda;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Timestamp getHora() {
		return hora;
	}

	public void setHora(Timestamp hora) {
		this.hora = hora;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public float getVa_total() {
		return va_total;
	}

	public void setVa_total(float va_total) {
		this.va_total = va_total;
	}


	@Override
	public String toString() {
		return "Vendas [cd_venda=" + cd_venda + ", data=" + data + ", hora=" + hora + ", produtos=" + produtos
				+ ", va_total=" + va_total + "]";
	}
	
	
	
	
}
