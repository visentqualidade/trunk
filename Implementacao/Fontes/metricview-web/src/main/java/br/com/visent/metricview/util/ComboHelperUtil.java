package br.com.visent.metricview.util;

import java.util.Arrays;
import java.util.List;

import br.com.visent.corporativo.dao.GenericDAO;
import br.com.visent.metricview.entidade.AreaUsuario;
import br.com.visent.metricview.entidade.Bilhetador;
import br.com.visent.metricview.entidade.CodigoNacional;
import br.com.visent.metricview.entidade.Ferramenta;
import br.com.visent.metricview.entidade.RegionalUsuario;
import br.com.visent.metricview.entidade.Usuario;
import br.com.visent.metricview.entidade.enuns.PermissaoCodigoEnum;
import br.com.visent.metricview.entidade.enuns.ProjetoSolucaoEnum;

public class ComboHelperUtil {
	
	private GenericDAO genericDAO = new GenericDAO();
	
	public List<RegionalUsuario> buscarRegionaisUsuario(){
		return genericDAO.buscarTodosOrdenado(RegionalUsuario.class, "descricao" , GenericDAO.ORDER_BY_ASC);
	}
	
	public List<Bilhetador> buscarBilhetadores(){
		return genericDAO.buscarTodosOrdenado(Bilhetador.class, "nome" , GenericDAO.ORDER_BY_ASC);
	}
	
	public List<CodigoNacional> buscarCodigoNacionais(){
		return genericDAO.buscarTodosOrdenado(CodigoNacional.class, "codigo" , GenericDAO.ORDER_BY_ASC);
	}
	
	public List<Usuario> buscarUsuarios (){
		return genericDAO.buscarTodosOrdenado(Usuario.class , "nome" , GenericDAO.ORDER_BY_ASC);
	}
	
	public List<ProjetoSolucaoEnum> buscarSolucoes (){
		return Arrays.asList(ProjetoSolucaoEnum.values());
	}
	
	public List<Ferramenta> buscarFerramentas(){
		return genericDAO.buscarTodos(Ferramenta.class);
	}
	
	public List<PermissaoCodigoEnum> buscarPermissoes(){
		return Arrays.asList(PermissaoCodigoEnum.values());
	}
	
	public List<AreaUsuario> buscarAreasUsuario(){
		return genericDAO.buscarTodos(AreaUsuario.class);
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

}
