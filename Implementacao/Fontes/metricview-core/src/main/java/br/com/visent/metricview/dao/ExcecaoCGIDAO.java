package br.com.visent.metricview.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.visent.corporativo.dao.GenericDAO;

public class ExcecaoCGIDAO extends GenericDAO{

	public List<Map<String, String>> consultarTodos() {
		String sql = "SELECT * FROM EXCECAO_CGI ";
		List<?> resultList = getEntityManager().createNativeQuery(sql).getResultList();
		List<Map<String, String>> list = new ArrayList<>();
		
		for (Object object : resultList) {
			Object[] obj = (Object[])object;
			Map<String, String> map = new HashMap<String, String>();
			map.put("cn", obj[1].toString());
			map.put("bilhetador", obj[0].toString());
			map.put("cgi", obj[2].toString());
			list.add(map);
		}
		return list;
	}

}
