package br.com.visent.metricview.ajax;

import java.util.ArrayList;
import java.util.List;

import br.com.visent.componente.log.Log;
import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.corporativo.service.GenericService;
import br.com.visent.metricview.dao.PermissaoDAO;
import br.com.visent.metricview.entidade.Ferramenta;
import br.com.visent.metricview.entidade.Permissao;
import br.com.visent.metricview.exception.MetricViewException;
import br.com.visent.metricview.util.ConstantesUtil;

public class FerramentaAjax extends MetricViewAjax<Ferramenta> {
	
	private GenericService genericService = new GenericService();
	private PermissaoDAO permissaoDAO = new PermissaoDAO();
	
	
	/**
	 * Método utilizado para salvar uma lista de permissão no banco de dados, da seguinte forma:
	 * <p>
	 * Remove as permissões presentes no banco de dados que são diferentes da lista de permissões adicionadas na lista que possuem id.
	 * Insere todas as permissões da lista passada como parâmetro no banco de dados.
	 * </p>
	 * @param permissoes - Lista de permissões que serão persistidas no banco de dados. 
	 */
	public void salvarFerramenta(List<Permissao> permissoes){
		removerPermissoesExistentes(permissoes);
		inserirPermissoes(permissoes);
		
		gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_ALTEROU_PERMISSOES), Log.INFO);
	}

	/**
	 * <p>
	 * Insere uma lista de permissões no banco de dados.
	 * </p>
	 * @param permissoes - Lista de permissões que serão persistidas no banco de dados. 
	 */
	private void inserirPermissoes(List<Permissao> permissoes) {
		List<Permissao> permissaoInserir = new ArrayList<>();
		for (Permissao permissao : permissoes) {
			if (permissao.getId() == null){
				permissaoInserir.add(permissao);
			}
		}
		genericService.inserir(permissaoInserir);
	}

	
	/**
	 * Remove uma lista de permissões que já estão presentes no banco de dados:
	 * <p>
	 * Remove as permissões diferentes das que contem nesta lista.
	 * </p>
	 * @param permissoes - Lista de permissões que serão checadas para removeção de permissão no banco de dados. 
	 */
	private void removerPermissoesExistentes(List<Permissao> permissoes) {
		List<Permissao> permissoesBanco = permissaoDAO.buscarPermissoesRemoverBanco(permissoes);
		for (Permissao permissao : permissoesBanco) {
			if (permissao.getId() != null){
				try {
					genericService.remover(Permissao.class, permissao.getId());
				} catch (SecurityException | InstantiationException| IllegalAccessException | NoSuchFieldException e) {
					throw new MetricViewException("Nao foi possivel remover a entidade : Permissao");
				}
			}
		}
	}

}
