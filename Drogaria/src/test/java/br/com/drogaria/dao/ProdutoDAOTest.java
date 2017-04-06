package br.com.drogaria.dao;

import java.math.BigDecimal;

import org.junit.Test;

import br.com.drogaria.domain.Fabricante;
import br.com.drogaria.domain.Produto;

public class ProdutoDAOTest {
	
	@Test
	public void salvar(){
		Produto produto = new Produto();
		
		Long codigo = 1L;
		FabricanteDAO dao = new FabricanteDAO();
		Fabricante fabricante = dao.buscar(codigo);
		
		produto.setDescricao("Cataflam 50 mg 20 comprimidos");
		produto.setPreco(new BigDecimal("13.70"));
		produto.setQuantidade(new Short("3"));
		produto.setFabricante(fabricante);
		
		ProdutoDAO dao2 = new ProdutoDAO();
		dao2.salvar(produto);
		
	}

}
