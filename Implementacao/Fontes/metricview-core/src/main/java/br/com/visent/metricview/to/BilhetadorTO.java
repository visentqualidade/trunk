package br.com.visent.metricview.to;


/**
 * Objeto de transação de "resumo" de bilhetador utilizado na tela de visualização de associação de bilhetadores. 
 *
 */
public class BilhetadorTO {

	private String bilhetador;
	private String cn;
	private String data;
	
	public BilhetadorTO() {}
	
	public BilhetadorTO(String bilhetador, String cn, String data){
		this.bilhetador = bilhetador;
		this.cn = cn;
		this.data = data;
	}
	
	public String toString() {
		return "BilhetadorTO [bilhetador="+bilhetador+", cn="+cn+" ,data="+data+"]";
	}
	
	public String getBilhetador() {
		return bilhetador;
	}
	public void setBilhetador(String bilhetador) {
		this.bilhetador = bilhetador;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
	
}
