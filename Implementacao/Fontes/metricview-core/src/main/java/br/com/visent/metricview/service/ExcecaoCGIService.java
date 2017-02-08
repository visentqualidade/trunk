package br.com.visent.metricview.service;

import java.util.List;
import java.util.Map;

import br.com.visent.metricview.dao.ExcecaoCGIDAO;

public class ExcecaoCGIService {
	
	private ExcecaoCGIDAO excecaoCGIDAO = new ExcecaoCGIDAO();

	public List<Map<String, String>> consultarTodos() {
		return excecaoCGIDAO.consultarTodos();
	}

}
