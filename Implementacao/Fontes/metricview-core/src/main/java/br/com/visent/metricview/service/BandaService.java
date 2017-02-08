package br.com.visent.metricview.service;

import java.util.List;

import br.com.visent.metricview.dao.BandaDAO;
import br.com.visent.metricview.entidade.Banda;

public class BandaService {
	
	private BandaDAO bandaDao = new BandaDAO();
	
	/**
	 * Metodo para consultar as bandas que o sistema ira considerar invalidas
	 * @return Lista de Banda
	 */
	public List<Banda> consultarBandasInvalidas() {
		return bandaDao.consultarBandasInvalidas();
	}

}
