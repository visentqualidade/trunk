package br.com.visent.metricview.service;

import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.corporativo.entidade.Entidade;
import br.com.visent.corporativo.exception.CorporativoServiceException;
import br.com.visent.corporativo.service.GenericService;
import br.com.visent.metricview.dao.GrupoDAO;
import br.com.visent.metricview.entidade.Grupo;
import br.com.visent.metricview.util.ConstantesUtil;


/**
 * Classe com objetivo de auxiliar o uso de serviços genéridos voltados aos grupos.
 *
 */
public class GrupoService extends GenericService {
	
	private GrupoDAO grupoDao = new GrupoDAO();
	
	public void doPreInserir(Entidade entidade) throws CorporativoServiceException {
		Grupo grupo = (Grupo) entidade;
		if (grupoDao.buscaGrupoMesmoNome(grupo)){
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_GRUPO_JA_CADASTRADO));
		}
	}
	
	public Grupo buscarPorID(Long id) {
		return super.buscarPorID(Grupo.class, id);
	}
	
}
