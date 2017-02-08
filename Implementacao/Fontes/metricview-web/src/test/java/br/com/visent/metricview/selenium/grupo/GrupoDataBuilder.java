package br.com.visent.metricview.selenium.grupo;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.visent.metricview.entidade.Grupo;

public class GrupoDataBuilder {

	private Grupo grupo = null;
	
	static {
		Fixture.of(Grupo.class).addTemplate("valido", new Rule(){
			{
				add("nome", "teste grupo");
				add("descricao", "descricao grupo");
			}
		});
	}
	
	public GrupoDataBuilder() {
		grupo = Fixture.from(Grupo.class).gimme("valido");
	}
	
	public GrupoDataBuilder comNome(String nome){
		grupo.setNome(nome);
		
		return this;
	}
	
	public GrupoDataBuilder comDescricao(String descricao){
		grupo.setDescricao(descricao);
		
		return this;
	}
	
	public Grupo constroi(){
		return grupo;
	}
}
