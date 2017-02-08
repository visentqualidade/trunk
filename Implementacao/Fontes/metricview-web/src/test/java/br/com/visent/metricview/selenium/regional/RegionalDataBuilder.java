package br.com.visent.metricview.selenium.regional;

import java.util.Set;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.visent.metricview.entidade.CodigoNacional;
import br.com.visent.metricview.entidade.Regional;

public class RegionalDataBuilder {

	private Regional regional = null;
	
	
	static {
		Fixture.of(Regional.class).addTemplate("valido", new Rule(){
			{
				add("nome", "teste Regional");
				add("descricao", "descricao grupo");
				add("nomeRelatorio", "teste");
			}
		});
	}
	
	public RegionalDataBuilder() {
		regional = Fixture.from(Regional.class).gimme("valido");
	}
	
	public RegionalDataBuilder comNome(String nome){
		regional.setNome(nome);
		
		return this;
	}
	
	public RegionalDataBuilder comDescricao(String descricao){
		regional.setDescricao(descricao);
		
		return this;
	}
	
	public RegionalDataBuilder comNomeRelatorio(String nomeRelatorio){
		regional.setNomeRelatorio(nomeRelatorio);
		
		return this;
	}
	
	public RegionalDataBuilder comCodigoNacional(Set<CodigoNacional> codigoNacional){
		regional.setCodigoNacional(codigoNacional);
		
		return this;
	}
	
	public Regional constroi(){
		return regional;
	}
}
