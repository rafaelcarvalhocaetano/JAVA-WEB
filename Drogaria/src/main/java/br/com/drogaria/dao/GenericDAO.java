package br.com.drogaria.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.drogaria.util.HibernateUtil;

public class GenericDAO<Entidade> {

	private Class<Entidade> classe;

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];

	}

	public void salvar(Entidade entidade) {

		Session sessao = HibernateUtil.getFabricaDeSessao().openSession();
		Transaction transacao = null;

		try {

			transacao = sessao.beginTransaction();
			sessao.save(entidade);
			transacao.commit();

		} catch (RuntimeException e) {

			if (transacao != null) {
				transacao.rollback();
			}

			throw e;

		} finally {

			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Entidade> listar(){
		
		Session sessao = HibernateUtil.getFabricaDeSessao().openSession();
		
		try {
			Criteria consulta = sessao.createCriteria(classe);
			List<Entidade> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		}finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Entidade buscar(Long codigo){
		
		Session sessao = HibernateUtil.getFabricaDeSessao().openSession();
		
		try {
			Criteria consulta = sessao.createCriteria(classe);
			consulta.add(Restrictions.idEq(codigo));
			Entidade resultado = (Entidade) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		}finally {
			sessao.close();
		}
	}
	
	public void excluir(Entidade entidade) {

		Session sessao = HibernateUtil.getFabricaDeSessao().openSession();
		Transaction transacao = null;

		try {

			transacao = sessao.beginTransaction();
			sessao.delete(entidade);
			transacao.commit();

		} catch (RuntimeException e) {

			if (transacao != null) {
				transacao.rollback();
			}

			throw e;

		} finally {

			sessao.close();
		}
	}

}