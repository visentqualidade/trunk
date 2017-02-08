package br.com.visent.metricview.selenium.regional;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.visent.metricview.entidade.CodigoNacional;

public class CodigoNacionallDataBuilder {

	private CodigoNacional codigoNacional = null;
	
	static {
		Fixture.of(CodigoNacional.class).addTemplate("valido", new Rule(){
			{
				add("id", random(Long.class, range(1L, 2000L)));	
				add("codigo", Long.valueOf(11));
//				add("uf", random("DF", "SP", "RJ") );
			}
		});
	}
	
	public CodigoNacionallDataBuilder() {
		codigoNacional = Fixture.from(CodigoNacional.class).gimme("valido");
	}
	
	public CodigoNacionallDataBuilder comId(Long id){
		codigoNacional.setId(id);
		
		return this;
	}
	
	public CodigoNacionallDataBuilder comCodigo(Long codigo){
		codigoNacional.setCodigo(codigo);
		
		return this;
	}
	
	
	public CodigoNacional constroi(){
		return codigoNacional;
	}
}
