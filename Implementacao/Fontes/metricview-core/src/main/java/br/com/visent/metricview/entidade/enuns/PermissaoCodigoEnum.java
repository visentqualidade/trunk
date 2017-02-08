package br.com.visent.metricview.entidade.enuns;

public enum PermissaoCodigoEnum {
	
	ADMINISTRADOR(1 , "Administrador"),
	USUARIO(2 , "Usuário");

	private Integer valor;
	private String descricao;
	
	private PermissaoCodigoEnum(Integer valor , String descricao) {
		setValor(valor);
		setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
