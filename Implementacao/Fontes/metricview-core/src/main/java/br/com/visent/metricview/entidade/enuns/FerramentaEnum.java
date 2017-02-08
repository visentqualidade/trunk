package br.com.visent.metricview.entidade.enuns;

/**
 *	Enum para representar as ferramentas que se integram com o metricview
 */
public enum FerramentaEnum {
	
	CONCATENACAO(5L),
	DASHBOARD(1L),
	EASYVIEW(2L),
	ITX(3L),
	MAPVIEW(4L);

	private Long valor;
	
	private FerramentaEnum(Long valor ) {
		setValor(valor);
	}

	public Long getValor() {
		return valor;
	}

	public void setValor(Long valor) {
		this.valor = valor;
	}
}
