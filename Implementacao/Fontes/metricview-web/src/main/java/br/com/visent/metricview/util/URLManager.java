package br.com.visent.metricview.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.visent.componente.log.Log;

/**
 * Classe que agrega funcionalidades voltadas a manipulação de URL's, desde a conexão quanto ao 
 * retorno de objetos a fim de serem obtidos.
 */
public class URLManager {

	/**
	 * Método que realiza a conexão com uma determinada URL e retorna o seu conteúdo.
	 * @param urlOrigem - URL a ser conectada.
	 * @return - HTML ou conteúdo da página solicitada a conectar
	 * @throws MalformedURLException - validação da composição da URL passada.
	 * @throws IOException - validação de conexão com a URL validada.
	 */
	public String obterHtmlPelaUrl(String urlOrigem) throws MalformedURLException, IOException{
		  StringBuilder html = new StringBuilder();
		
	      try {
	 
	         URL url = new URL(urlOrigem);
	         HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	         BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	         
	         String line = null;
	 
	         while( (line = in.readLine()) != null ){
	            html.append(line.toString());
	         }
	 
	         in.close();
	         urlConnection.disconnect();
	 
	      } catch (MalformedURLException e){
	    	  e.printStackTrace();
	    	  Log.debug(e.getMessage() , e);
	        throw new MalformedURLException ("Erro ao criar URL. Formato inválido. "+e.getMessage());
	      } catch (IOException e) {
	    	  e.printStackTrace();
	    	  Log.debug(e.getMessage() , e);
	    	throw new IOException ("Erro ao criar URL. Formato inválido. "+e.getMessage());
	      }
	      
	      return html.toString();
	}
	
}
