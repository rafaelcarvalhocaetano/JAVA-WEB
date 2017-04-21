package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.FabricanteDAO;
import br.com.drogaria.dao.ProdutoDAO;
import br.com.drogaria.domain.Fabricante;
import br.com.drogaria.domain.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable {

	private Produto produto;
	private List<Produto> produtos;
	private List<Fabricante> fabricantes;

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Produto getProduto() {
		return produto;
	}

	@PostConstruct
	public void listar() {
		try {
			ProdutoDAO dao = new ProdutoDAO();

			produtos = dao.listar();

		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao listar a mensagem");
		}
	}

	@PostConstruct
	public void novo() {

		try {

			produto = new Produto();

			FabricanteDAO dao = new FabricanteDAO();
			fabricantes = dao.listar();

			Messages.addGlobalInfo("Produto Salvo com sucesso");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao criar novo produto");
			e.printStackTrace();
		}

	}

	public void salvar() {
		try {
			ProdutoDAO dao = new ProdutoDAO();
			dao.merge(produto);

			produto = new Produto();

			FabricanteDAO fdao = new FabricanteDAO();
			fabricantes = fdao.listar();

			produtos = dao.listar();

			Messages.addGlobalInfo("Produto Salvo com sucesso");

		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar salvar o produto");
			e.printStackTrace();
		}

	}

	public void editar(ActionEvent event) {
		try {
			produto = (Produto) event.getComponent().getAttributes().get("produtoSelecionado");

			FabricanteDAO dao = new FabricanteDAO();
			fabricantes = dao.listar();

		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar editar o produto");
		}
	}

	public void excluir(ActionEvent e) {

		try {

			produto = (Produto) e.getComponent().getAttributes().get("produtoSelecionado");

			ProdutoDAO dao = new ProdutoDAO();
			dao.excluir(produto);

			produtos = dao.listar();

		} catch (RuntimeException e2) {
			Messages.addGlobalError("Erro ao tentar excluir o produto");
			e2.printStackTrace();
		}

	}

}