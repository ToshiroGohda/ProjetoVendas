package br.com.pocoyo.vendas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Produto {

	@Id
	@GeneratedValue
	private Long cd_produto;
	
	private Long cd_barras;
	
	private String desc_produto;
	
	private float va_compra;
	
	private float va_venda;
	
	private float va_lucro;

	public Produto() {
		super();
	}

	public Produto(Long cd_barras, String desc_produto, float va_compra, float va_venda,
			float va_lucro) {
		super();
		this.cd_barras = cd_barras;
		this.desc_produto = desc_produto;
		this.va_compra = va_compra;
		this.va_venda = va_venda;
		this.va_lucro = va_lucro;
	}

	public Long getCd_produto() {
		return cd_produto;
	}

	public void setCd_produto(Long cd_produto) {
		this.cd_produto = cd_produto;
	}

	public Long getCd_barras() {
		return cd_barras;
	}

	public void setCd_barras(Long cd_barras) {
		this.cd_barras = cd_barras;
	}

	public String getDesc_produto() {
		return desc_produto;
	}

	public void setDesc_produto(String desc_produto) {
		this.desc_produto = desc_produto;
	}

	public float getVa_compra() {
		return va_compra;
	}

	public void setVa_compra(float va_compra) {
		this.va_compra = va_compra;
	}

	public float getVa_venda() {
		return va_venda;
	}

	public void setVa_venda(float va_venda) {
		this.va_venda = va_venda;
	}

	public float getVa_lucro() {
		return va_lucro;
	}

	public void setVa_lucro(float va_lucro) {
		this.va_lucro = va_lucro;
	}

	@Override
	public String toString() {
		return "Produto [cd_produto=" + cd_produto + ", cd_barras=" + cd_barras + ", desc_produto=" + desc_produto
				+ ", va_compra=" + va_compra + ", va_venda=" + va_venda + ", va_lucro=" + va_lucro + "]";
	}
	
	
	
}
