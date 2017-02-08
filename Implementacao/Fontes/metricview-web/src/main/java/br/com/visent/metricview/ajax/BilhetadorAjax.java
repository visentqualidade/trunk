package br.com.visent.metricview.ajax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.visent.metricview.dao.BilhetadorDAO;
import br.com.visent.metricview.entidade.Bilhetador;
import br.com.visent.metricview.service.BilhetadorService;
import br.com.visent.metricview.to.BilhetadorTO;

public class BilhetadorAjax extends MetricViewAjax<Bilhetador> {

	private BilhetadorDAO 			bilhetadorDAO 		= new BilhetadorDAO		();
	private BilhetadorService		bilhetadorService 	= new BilhetadorService	(); 
	
	/**
	 * Busca todos os bilhetadores, associações de bilhetadores e codigos nacionais associados a um bilhetador.
	 */
	public List<BilhetadorTO> buscarTodasAssociacoes() {
		List<Bilhetador> bilhetadores = new ArrayList<Bilhetador>();
		
		for (Iterator<Bilhetador> iterator = bilhetadorDAO.buscarTodosBilhetadoresFetch().iterator(); iterator.hasNext();) {
			Bilhetador bil = (Bilhetador) iterator.next();
			
			bilhetadores.add(bil);
		}
		
		return bilhetadorService.gerarAssociacaoBilhetador(bilhetadores);
		
	}
	
}
