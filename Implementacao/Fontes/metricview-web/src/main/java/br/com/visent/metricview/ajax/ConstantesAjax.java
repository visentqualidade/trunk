package br.com.visent.metricview.ajax;

import java.lang.reflect.Field;

import br.com.visent.componente.log.Log;
import br.com.visent.metricview.constantes.ConstantesMetricView;

/**
 *	Classe para buscar os parametros ' constantes ' da aplicacao MetricView
 */
public class ConstantesAjax {
	
	/**
	 * Metodo para buscar os parametros da classe {@link ConstantesMetricView}
	 * @param param String contendo o nome dos atributo da classe
	 * @return O valor da propriedade passada
	 */
	public String buscarParametro(String param){
		String parametro = null;
		for (Field field : ConstantesMetricView.class.getDeclaredFields()) {
			field.setAccessible(Boolean.TRUE);
			if(param.equals(field.getName())){
				try {
					switch (field.getType().getSimpleName()) {
					case "Integer":
						parametro = ((Integer)field.get(param)).toString();
						break;
					default:
						parametro = (String) field.get(param);
						break;
					}
					if(parametro != null){
						break;
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
					Log.debug(e.getMessage());
				}
			}
		}
		return parametro;
	}

}
