package br.com.visent.metricview.service;

import br.com.visent.corporativo.service.GenericService;
import br.com.visent.metricview.entidade.AreaUsuario;

public class AreaUsuarioService extends GenericService{
	
	/**
	 * Metodo para verificar se ha necessidade de inserir uma nova area ou nao no banco
	 * @param area String contendo as informacoes sobre a area
	 */
	public void verificarInsericaoNovaArea(String area){
		AreaUsuario areaUsuario = new AreaUsuario();
		areaUsuario.setDescricao(area);
		if(areaUsuario != null || areaUsuario.getDescricao().trim().length() != 0 || areaUsuario.getDescricao() != null){
			if(buscarPorFiltro(areaUsuario).isEmpty()){
				inserir(areaUsuario);
			}
		}
	}

}
