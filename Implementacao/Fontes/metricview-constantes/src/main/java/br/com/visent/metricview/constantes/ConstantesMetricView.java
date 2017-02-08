package br.com.visent.metricview.constantes;

/**
 *	Classe para representar todas as constantes que poderam sofrer alguma alterar dependendo docliente
 *	
 *	
 */
public class ConstantesMetricView {
	
	/**
	 *	<p>
	 *	 	Representam a quantidade de usuarios administradores que poderao estar conectados ao mesmo tempo
	 *	</p> 
	 */
	public static final Integer QTD_USUARIO_CONECTADO_ADMIN = 5;
	
	/**
	 *	<p>
	 *		Representam a quantidade de usuarios comuns que poderao estar conectados ao mesmo tempo
	 *	</p> 
	 */
	public static final Integer QTD_USUARIO_CONECTADO_COMUM = 20;
	
	/**
	 * Representa a quantidade maxima da diferenca de dias na pesquisa das logs
	 */
	public static final Integer QTD_MAX_DIAS_PESQUISA_FILTRO = 7;
	
	/**
	 * A senha do usuario system da aplicacao sera: visent.1970
	 */
	public static final String SENHA_USUARIO_SYSTEM = "4jqVZHc38mXquhjTmU8E1w==";
	
	/**
	 * o nome do usuario system da aplicacao
	 */
	public static final String NOME_USUARIO_SYSTEM = "system";

}
