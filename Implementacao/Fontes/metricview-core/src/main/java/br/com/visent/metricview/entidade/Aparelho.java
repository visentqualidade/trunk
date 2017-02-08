package br.com.visent.metricview.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.visent.corporativo.entidade.Entidade;

@Entity
@Table(name="APARELHO")
public class Aparelho implements Entidade{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TAC")
	private String tac;
	
	@Column(name="FABRICANTE")
	private String fabricante;
	
	@Column(name="TECNOLOGIA")
	private String tecnologia;
	
	@Column(name="MODELO")
	private String modelo;
	
	@Column(name="BANDAS")
	private String bandas;
	
	@Column(name="TIPO_APARELHO")
	private String tipoAparelho;
	
	@Column(name="QTD_SIM")
	private Integer quantidadeSim;
	
	@Column(name="TIPO_SIM")
	private String tipoSim;
	
	
	
	@Override
	public String toString() {
		return "Aparelho [tac=" + tac + ", fabricante="
				+ fabricante + ", tecnologia=" + tecnologia + ", modelo="
				+ modelo + ", bandas=" + bandas + "]";
	}

	public String getId() {
		return tac;
	}

	public void setId(String tac) {
		this.tac = tac;
	}

	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getTecnologia() {
		return tecnologia;
	}

	public void setTecnologia(String tecnologia) {
		this.tecnologia = tecnologia;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getBandas(){
		return this.bandas;
	}
	
	public void setBandas(String banda){
		this.bandas = banda;
	}

	public String getTipoAparelho() {
		return tipoAparelho;
	}

	public void setTipoAparelho(String tipoAparelho) {
		this.tipoAparelho = tipoAparelho;
	}

	public Integer getQuantidadeSim() {
		return quantidadeSim;
	}

	public void setQuantidadeSim(Integer quantidadeSim) {
		this.quantidadeSim = quantidadeSim;
	}

	public String getTipoSim() {
		return tipoSim;
	}

	public void setTipoSim(String tipoSim) {
		this.tipoSim = tipoSim;
	}
	
}
