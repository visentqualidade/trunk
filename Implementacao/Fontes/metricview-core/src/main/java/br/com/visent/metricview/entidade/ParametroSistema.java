package br.com.visent.metricview.entidade;

public class ParametroSistema {

	private String dirControleUsuario;

	private String enderecoCentral;

	private String dirControleLog;
	
	@Override
	public String toString() {
		return "ParametroSistema [dirControleUsuario=" + dirControleUsuario + ", enderecoCentral=" + enderecoCentral + ", dirControleLog="
				+ dirControleLog + "]";
	}

	public String getDirControleUsuario() {
		return dirControleUsuario;
	}

	public void setDirControleUsuario(String dirControleUsuario) {
		this.dirControleUsuario = dirControleUsuario;
	}

	public String getEnderecoCentral() {
		return enderecoCentral;
	}

	public void setEnderecoCentral(String enderecoCentral) {
		this.enderecoCentral = enderecoCentral;
	}

	public String getDirControleLog() {
		return dirControleLog;
	}

	public void setDirControleLog(String dirControleLog) {
		this.dirControleLog = dirControleLog;
	}

}
