package br.com.visent.metricview.entidade.enuns;

/**
 *	Enum para representar os projetos que fazem parte do Portal MetricView
 * <p>
 * 	OBS.: MATRIZ_INTERESSE e o mesmo projeto do INTERCONEXAO
 * </p>
 */
public enum ProjetoSolucaoEnum {

	CONCATENACAO("5","/concatenacao","Concatenação"), 
	DASHBOARD("2","/dashboard","DashBoard"), 
	EASYVIEW("3", "/easyview","EasyView"), 
	MATRIZ_INTERESSE("4", "/itxview" , "ItxView"),
	PORTAL("1" , "/metricview" , "Portal");

	private String value;
	private String url;
	private String descricaoTela;

	private ProjetoSolucaoEnum(String value, String url , String descricaoTela) {
		setUrl(url);
		setValue(value);
		setDescricaoTela(descricaoTela);
	}
	
	public static String buscarDescricaoPorUrl(String url){
		String projetoSolucao = null;
		for (ProjetoSolucaoEnum solucao : ProjetoSolucaoEnum.values()) {
			if(solucao.getUrl().toLowerCase().equals(url.toLowerCase())){
				projetoSolucao = solucao.getDescricaoTela();
				break;
			}
		}
		return projetoSolucao;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescricaoTela() {
		return descricaoTela;
	}

	public void setDescricaoTela(String descricaoTela) {
		this.descricaoTela = descricaoTela;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
