package br.com.pocoyo.vendas;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.io.Writer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import br.com.pocoyo.vendas.entity.Produto;
import br.com.pocoyo.vendas.entity.Vendas;
import br.com.pocoyo.vendas.service.ProdutoService;
import br.com.pocoyo.vendas.service.VendasService;

@SpringBootApplication
public class VendasApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(VendasApplication.class, args);
	    ProdutoService produtoService =	configurableApplicationContext.getBean(ProdutoService.class);
	    VendasService vendasService =	configurableApplicationContext.getBean(VendasService.class);
	    
	    
//	    // Cria um produto vazio
//	    Produto produtoTeste = new Produto();
//	    //Coloca no produto vazio o código de barras (Sim o L no final do numero é importante)
//	    produtoTeste.setCd_barras(1L);
//	    //coloca a descrição do produto
//	    produtoTeste.setDesc_produto("teste");
//	    // coloca valor de compra
//	    produtoTeste.setVa_compra(1);
//	    //coloca valor de lucro
//	    produtoTeste.setVa_lucro(0);
//	    //coloca valor de venda
//	    produtoTeste.setVa_venda(1);
//	    
//	    //Registra o Produto, No primeiro parametro sempre coloque produtoService, e no segundo coloque o produto criado, neste caso produtoTeste
//	    RegistrarProduto(produtoService, produtoTeste);
//
//	    //Le todos os produtos registrados
//	    LerProdutos(produtoService);
//	    
//	    //Le um produto com base em seu código(ID)
//	    LerProdutoPorCodigo(produtoService, 1L);
	    
	    //Converte tudo para CSV
	    try {
			CsvConverter(produtoService, vendasService);
		} catch (CsvDataTypeMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CsvRequiredFieldEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}



	
	public static Produto RegistrarProduto(ProdutoService produtoService, Produto produto){	    
	    return produtoService.save(produto);
	}
	
	public static void LerProdutos(ProdutoService produtoService) {
	    List<Produto> produtoLista = produtoService.findAll();
	    
	    for ( Produto p : produtoLista) {
	    	System.out.println(p.getDesc_produto());
	    }
	}

	public static void LerProdutoPorCodigo(ProdutoService produtoService, Long cd_produto) {

		

		
		Optional<Produto> produto = produtoService.findById(cd_produto);
		
		if (produto.isPresent()) {
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println(produto.get().toString());
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
		} else {
			System.out.println("Produto não encontrado");
		}
		
	}

	public static void DeletarProduto(ProdutoService produtoService, Long cd_produto) {
		
		System.out.println("Digite o Código do produto:");
		produtoService.deleteById(cd_produto);
		
	}

	public static Vendas RegistrarVendas(VendasService vendasService, ProdutoService produtoService, List<Long> cd_produto){		
	
		Vendas vendas = new Vendas();
		for(Long p : cd_produto) {
			Optional<Produto> produtoLista = produtoService.findById(p);
			vendas.addProduto(produtoLista.get());
		}
	    vendas.setVa_total(vendas.ValorTotal());
	    return vendasService.save(vendas);
	    
	}
	
	public static void LerVendas(VendasService vendasService) {
	    List<Vendas> vendasLista = vendasService.findAll();
	    
	    for ( Vendas v : vendasLista) {
	    	System.out.println(v.toString());
	    }
	}

	public static void LerVendasPorCodigo(VendasService vendasService, Long cd_vendas) {
		
		Optional<Vendas> vendas = vendasService.findById(cd_vendas);
		if (vendas.isPresent()) {
			System.out.println(vendas.toString());
		} else {
			System.out.println("Venda não encontrado");
		}
		
	}

	public static void DeletarVendas(VendasService vendasService, Long cd_venda) {

	vendasService.deleteById(cd_venda);
 }

	public static void CsvConverter(ProdutoService produtoService, VendasService vendasService) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		
		final String CSV_LOCATION = "vendas.csv";
		final String CSV_LOCATION2 = "produtos.csv";
		
		try {
			Writer writer = Files.newBufferedWriter(Paths.get(CSV_LOCATION));
			
			List<Vendas> ListaVendas = vendasService.findAll();
			
			ColumnPositionMappingStrategy mappingStrategy1 = new ColumnPositionMappingStrategy();
			
			mappingStrategy1.setType(Vendas.class);
			
			String[] columns1 = new String[] { "cd_venda", "data", "hora", "produtos", "va_total" };
			mappingStrategy1.setColumnMapping(columns1);
			
			StatefulBeanToCsvBuilder<Vendas> builder = new StatefulBeanToCsvBuilder<>(writer);
			StatefulBeanToCsv beanWriter = builder.withMappingStrategy(mappingStrategy1).build();
						
			beanWriter.write(ListaVendas);
			
			
			///////////////////////////////////
			
			
			Writer writer2 = Files.newBufferedWriter(Paths.get(CSV_LOCATION2));
						
			List<Produto> ListaProdutos = produtoService.findAll();
			
			ColumnPositionMappingStrategy mappingStrategy2 = new ColumnPositionMappingStrategy();
			
			mappingStrategy2.setType(Produto.class);
			
			String[] columns2 = new String[] { "cd_produto", "cd_barras", "desc_produto", "va_compra", "va_venda", "va_lucro" };
			mappingStrategy2.setColumnMapping(columns2);
			
			StatefulBeanToCsvBuilder<Produto> builder1 = new StatefulBeanToCsvBuilder<>(writer2);
			StatefulBeanToCsv beanWriter1 = builder1.withMappingStrategy(mappingStrategy2).build();
			
			beanWriter1.write(ListaProdutos);
			
			writer.close();
			writer2.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}

