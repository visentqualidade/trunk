package br.com.visent.metricview.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import br.com.visent.componente.log.Log;
import br.com.visent.corporativo.entidade.Entidade;

public class CadastroDinamico {

	/**
	 * Método que à partir de um HTML enviado extrai campos já pré-definidos aqui, sendo eles:
	 * 
	 * 		ex: Bilhetador = { asd , ghj , jkl , poi }  Tecnologia = { asd , ghj , jkl }
	 * 
	 * 		1- Passo: Quebra da string presente em um html por " } "
	 * 		2- Passo: 
	 * 			String quebrada:
	 * 				["Bilhetador = { asd , ghj , jkl , poi "]
	 * 				["Tecnologia = { asd , ghj , jkl "]
	 * 
	 * 			- Remove os caracteres "nomeClasse  = {"
	 * 			- Separa as strings por ','
	 * 				 
	 * 
	 * @param html - fonte de extração que será utilizado para se obter os valores.
	 * @param campo - Campo no qual se deseja extrair da fonte
	 * @param clazz - Qual classe representa o campo que será extraído.
	 * @return
	 * @throws Exception 
	 */
	public Collection<Entidade> extrairCampo(String html, Class<?> clazz) throws Exception {
		
		String [] campos = html.split(" }");
		
		Collection<Entidade> entidades = new ArrayList<Entidade>();
		
		for(String dados : campos){
			if (dados.contains(clazz.getSimpleName())){
				String dadosFiltrado = dados.replace(clazz.getSimpleName()+" = {", "");
				String [] nomes = dadosFiltrado.split(";"); 
				
				for (String nome : nomes){
					try {
						Constructor<?> construtor = clazz.getConstructor(new Class[]{String.class, String.class});
						String nomeBil = nome.substring(0,nome.lastIndexOf("(")).trim();
						entidades.add((Entidade)construtor.newInstance(nomeBil.split(ConstantesUtil.REMOVER_NOME_CHU_BILHETADOR)[0], 						// nome
																	   nome.substring(nome.lastIndexOf("(")+1,nome.lastIndexOf(")")).trim())// tecnologia
																	   );
					} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
						Log.debug(e.getMessage() , e);
						throw new Exception(e.getMessage());
					}
				}
			}
		}
		
		return entidades;
	}

}
