package br.com.visent.metricview.service;

import java.util.ArrayList;
import java.util.List;

import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.corporativo.entidade.Entidade;
import br.com.visent.corporativo.exception.CorporativoServiceException;
import br.com.visent.corporativo.service.GenericService;
import br.com.visent.metricview.dao.RegionalDAO;
import br.com.visent.metricview.entidade.Regional;
import br.com.visent.metricview.entidade.UnidadeFederativa;
import br.com.visent.metricview.util.ConstantesUtil;

public class RegionalService extends GenericService{

	RegionalDAO regionalDao = new RegionalDAO();
	
	public void doPreInserir(Entidade entidade) throws CorporativoServiceException {
		Regional regional = (Regional) entidade;
		regional.setNome(regional.getNome().trim());
		if (regionalDao.buscaRegionalMesmoNome(regional)){
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_REGIONAL_JA_CADASTRADO));
		}
	}
	
	public void doPreAtualizar(Entidade entidade) throws CorporativoServiceException {
		Regional regional = (Regional) entidade;
		regional.setNome(regional.getNome().trim());
		if (regionalDao.buscaRegionalMesmoNome(regional)){
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_REGIONAL_JA_CADASTRADO));
		}
	}
	
	public String getUF(Entidade entidade) {
		Regional regional = (Regional) entidade;
		regional.setNome(regional.getNome().trim());
		StringBuffer uf = new StringBuffer();
		List < UnidadeFederativa > ufs = new ArrayList<UnidadeFederativa>(regionalDao.buscarUfs(regional.getNome()));
		for (UnidadeFederativa unidadeFederativa : ufs) {
			if(uf.indexOf(unidadeFederativa.getSigla()) == -1)
				uf.append(unidadeFederativa.getSigla()+",");
		}
		uf.deleteCharAt(uf.length()-1);
		return uf.toString();

	}

}