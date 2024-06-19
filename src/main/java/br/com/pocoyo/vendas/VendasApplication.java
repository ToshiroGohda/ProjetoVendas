package br.com.pocoyo.vendas;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
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
	    VendasService vendasService = new VendasService();
	    

	    Menu();

	    
	}

	public static void Menu() {
		
		ProdutoService produtoService = new ProdutoService();
		VendasService vendasService = new VendasService();
		
	    Scanner sc = new Scanner(System.in);
	    
	    System.out.println("");
	    System.out.println("");
	    System.out.println("");
	    System.out.println("");
	    System.out.println("Escolha o tipo de operação que dejesa efetuar:");
	    System.out.println("Digite 1 para funções relacionadas a Produtos");
	    System.out.println("Digite 2 para funções relacionadas a Vendas");
	    System.out.println("Digite 3 para salvar todos os dados em CSV");
	    int res = sc.nextInt();
	    if( res == 1) {
	    	
	    	funcProduto(produtoService);
	    	
	    } if (res == 2) {
	    	
	    	funcVendas(vendasService, produtoService);
	    	
	    } if (res == 3){
	    	
	    	try {
				CsvConverter(produtoService, vendasService);
			} catch (CsvDataTypeMismatchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CsvRequiredFieldEmptyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    } else {
	    	System.out.println("Opção invalida");
	    	Menu();
	    }
	    sc.close();
	}
	
	public static void funcProduto(ProdutoService produtoService) {
	    Scanner sc = new Scanner(System.in);
	    
    	System.out.println("");
    	System.out.println("");
    	System.out.println("");
    	System.out.println("");
    	System.out.println("Aperte o NUMERO que representa a função desejada");
    	System.out.println("1: Criar um produto novo");
    	System.out.println("2: Ler todos os produtos registrados");
    	System.out.println("3: Ler um produto");
    	System.out.println("4: Deletar um produto");
    	
    	int res = sc.nextInt();
    	
    	if (res == 1) {
    		RegistrarProduto(produtoService);
    	}
    	if (res == 2) {
    		LerProdutos(produtoService);
    	}
    	if (res == 3) {
    		LerProdutoPorCodigo(produtoService);
    	}
    	if (res == 4) {
    		DeletarProduto(produtoService);
    	} else {
    		System.out.println("Opção Invalida");
    		Menu();
    	}
    	sc.close();
	}
	
	public static  void funcVendas(VendasService vendasService, ProdutoService produtoService) {
	    Scanner sc = new Scanner(System.in);
	    
    	System.out.println("");
    	System.out.println("");
    	System.out.println("");
    	System.out.println("");
    	System.out.println("Aperte o NUMERO que representa a função desejada");
    	System.out.println("1: Criar uma venda nova");
    	System.out.println("2: Ler todas as vendas registradas");
    	System.out.println("3: Ler uma venda");
    	System.out.println("4: Deletar uma venda");
    	
    	int res = sc.nextInt();
    	
    	if (res == 1) {
    		RegistrarVendas(vendasService, produtoService);
    	}
    	if (res == 2) {
    		LerVendas(vendasService);
    	}
    	if (res == 3) {
    		LerVendasPorCodigo(vendasService);
    	}
    	if (res == 4) {
    		DeletarVendas(vendasService);
    	} else {
    		System.out.println("Opção Invalida");
    		Menu();
    	}
    	sc.close();
	}
	
	public static Produto RegistrarProduto(ProdutoService produtoService){
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Digite o código de barras do produto abaixo:");
	    Long cd_barras = sc.nextLong();
	    
	    System.out.println("Digite a descrição do produto abaixo:");
	    String desc_produto = sc.next();
	    
	    System.out.println("Digite o valor de compra do produto abaixo:");
	    float va_compra = sc.nextFloat();
	    
	    System.out.println("Digite o valor de venda do produto abaixo:");
	    float va_venda = sc.nextFloat();
	    
	    System.out.println("Digite o valor de lucro do produto abaixo:");
	    float va_lucro = sc.nextFloat();
	    sc.close();
	    Produto produto = new Produto(cd_barras, desc_produto, va_compra,va_venda,va_lucro);
	    
	    return produtoService.save(produto);
	}
	
	public static void LerProdutos(ProdutoService produtoService) {
	    List<Produto> produtoLista = produtoService.findAll();
	    
	    for ( Produto p : produtoLista) {
	    	System.out.println(p.getDesc_produto());
	    }
	}

	public static void LerProdutoPorCodigo(ProdutoService produtoService) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite o Código do produto:");
		Long cd_produto = sc.nextLong();
		
		Optional<Produto> produto = produtoService.findById(cd_produto);
		
		if (produto.isPresent()) {
			System.out.println(produto.toString());
		} else {
			System.out.println("Produto não encontrado");
		}
		
		sc.close();
	}

	public static void DeletarProduto(ProdutoService produtoService) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite o Código do produto:");
		Long cd_produto = sc.nextLong();
		sc.close();
		produtoService.deleteById(cd_produto);
		
	}

	public static Vendas RegistrarVendas(VendasService vendasService, ProdutoService produtoService){

		boolean i = false;
		
		Scanner sc = new Scanner(System.in);
		
		Vendas vendas = new Vendas();
		
		
		while(i) {
			
		System.out.println("Digite o código do produto à inserir");	
	    Long cd_produto = sc.nextLong();
	    Optional<Produto> ProdutoLista = produtoService.findById(cd_produto);
	   
	    vendas.addProduto(ProdutoLista.get());
	    
	    System.out.println("Quer inserir outro produto na venda? S/N");
	    String aux = sc.next();
	    
	    if(aux == "S") {
	    	i = true;
	    }
	    
		}
		sc.close();
	    vendas.setVa_total(vendas.ValorTotal());
	    return vendasService.save(vendas);
	}
	
	public static void LerVendas(VendasService vendasService) {
	    List<Vendas> vendasLista = vendasService.findAll();
	    
	    for ( Vendas v : vendasLista) {
	    	System.out.println(v.toString());
	    }
	}

	public static void LerVendasPorCodigo(VendasService vendasService) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite o Código da venda:");
		Long cd_vendas = sc.nextLong();
		
		Optional<Vendas> vendas = vendasService.findById(cd_vendas);
		sc.close();
		if (vendas.isPresent()) {
			System.out.println(vendas.toString());
		} else {
			System.out.println("Venda não encontrado");
		}
		
	}

	public static void DeletarVendas(VendasService vendasService) {
	Scanner sc = new Scanner(System.in);
	
	System.out.println("Digite o código da venda:");
	Long cd_venda = sc.nextLong();
	sc.close();
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
			
			mappingStrategy2.setType(Vendas.class);
			
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

