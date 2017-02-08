package br.com.visent.metricview.ajax;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.visent.componente.log.Log;
import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.corporativo.dao.GenericDAO;
import br.com.visent.corporativo.entidade.Entidade;
import br.com.visent.corporativo.exception.BancoException;
import br.com.visent.corporativo.exception.CorporativoServiceException;
import br.com.visent.metricview.entidade.CodigoNacional;
import br.com.visent.metricview.entidade.Regional;
import br.com.visent.metricview.service.RegionalService;
import br.com.visent.metricview.to.RegionalTO;
import br.com.visent.metricview.util.ConstantesUtil;

public class RegionalAjax extends MetricViewAjax<Regional> {

	private RegionalService regionalService = new RegionalService();
	private GenericDAO      genericDao      = new GenericDAO();

	public RegionalTO inserir(Entidade entidade) throws BancoException {

		Regional regional = (Regional) entidade;
		Set<CodigoNacional> codigos = regional.getCodigoNacional();
		regional.setCodigoNacional(new HashSet<CodigoNacional>());

		for (CodigoNacional cn : codigos){
			CodigoNacional cnFiltro = new CodigoNacional();
			cnFiltro.setCodigo(cn.getCodigo());
			regional.getCodigoNacional().add(genericDao.buscarPorFiltro(cnFiltro).get(0));
		}

		regional = (Regional) regionalService.inserir(regional);
		gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_REGIONAL_INSERIDO_SUCESSO ,((Regional) entidade).getNome()), Log.INFO);
		return criarRegionalTO(regional);
	}

	private RegionalTO criarRegionalTO(Regional regional) {
		RegionalTO to = new RegionalTO();
		to.setId(regional.getId());
		to.setNome(regional.getNome());
		to.setDescricao(regional.getDescricao());
		to.setCns(regional.getCnsFormatados());
		to.setUfs(regional.getUfsFormatados());
		return to;
	}

	public void removerEntidade(Long id) {
		Regional regional = (Regional) buscarPorId(id);
		try {
			regionalService.remover(Regional.class, regional.getId());
			gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_REGIONAL_REMOVIDO_SUCESSO , regional.getNome()), Log.INFO);
		} catch (SecurityException e) {
			e.printStackTrace();
			Log.debug(e.getMessage());
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_ERRO_SECURITY));
		} catch (InstantiationException e) {
			e.printStackTrace();
			Log.debug(e.getMessage());
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_ERRO_INSTANCIA));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Log.debug(e.getMessage());
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_ERRO_ACESSO));
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			Log.debug(e.getMessage());
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_ERRO_CAMPO));
		}
	}


	public void atualizarEntidade(Entidade entidade) {
		Regional regional = (Regional) entidade;

		Set<CodigoNacional> codigos = regional.getCodigoNacional();
		regional.setCodigoNacional(new HashSet<CodigoNacional>());

		for (CodigoNacional cn : codigos){
			CodigoNacional cnFiltro = new CodigoNacional();
			cnFiltro.setCodigo(cn.getCodigo());
			regional.getCodigoNacional().add(genericDao.buscarPorFiltro(cnFiltro).get(0));
		}

		regionalService.atualizar(regional);
		gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_REGIONAL_ALTERADO_SUCESSO ,((Regional) entidade).getNome()), Log.INFO);
		super.atualizarEntidade(entidade); 
	}

	public List<RegionalTO> buscarTodosComUF(){
		List<Regional> regionais = buscarTodos();
		List<RegionalTO> list = new ArrayList<>();
		for (Regional regional : regionais) {
			RegionalTO to = criarRegionalTO(regional);
			list.add(to);
		}
		return list;
	}

}