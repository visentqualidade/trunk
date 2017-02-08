package br.com.visent.metricview.to;

import java.util.List;

import br.com.visent.metricview.entidade.Ferramenta;
import br.com.visent.metricview.entidade.Grupo;

public class VisualizacaoPermissaoIndividualTO {
	
	private Ferramenta ferramenta;
	private String permissaoPrincipal;
	private List<Grupo> grupos;
	private String permissaoIndividual;

	
	public Ferramenta getFerramenta() {
		return ferramenta;
	}
	
	public List<Grupo> getGrupos() {
		return grupos;
	}
	
	public String getPermissaoIndividual() {
		return permissaoIndividual;
	}
	
	public String getPermissaoPrincipal() {
		return permissaoPrincipal;
	}
	
}
