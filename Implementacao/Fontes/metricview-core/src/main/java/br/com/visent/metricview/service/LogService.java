package br.com.visent.metricview.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import br.com.visent.componente.dwr.util.SessaoUtil;
import br.com.visent.componente.log.Log;
import br.com.visent.componente.util.DataUtil;
import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.corporativo.service.GenericService;
import br.com.visent.metricview.entidade.LogEntidade;
import br.com.visent.metricview.entidade.Usuario;
import br.com.visent.metricview.entidade.enuns.ProjetoSolucaoEnum;
import br.com.visent.metricview.util.ConstantesUtil;

/**
 * Classe responsavel por gravar as acoes do usuario durante o seu fluxo de navegacao no Portal MetricView e seus filhos
 * <p>
 * 	Coisas importantes para serem gravadas :
 * 	<p>
 * 		<strong>
 * 		1. Login, LogOut
 * 			<p>
 * 			2. Acesso aos sistemas filhos.
 * 			</p>
 * 			<p>
 * 			3. Acesso e configuracao dos usuario, grupos, permissoes, Regionais e Bilhetadores
 * 			</p>
 * 			<p>
 * 			4. Alteracao dos dados do usuario no menu do canto esquerdo
 * 			</p>
 * 			<p>
 * 			5. Possiveis Erros na aplicacao
 * 			</p>
 * 		</strong>
 * </p>
 * </p>
 */
public class LogService extends GenericService{
	
	/**
	 * Metodo para gravar as acoes do usuario no banco
	 * @param msg Mensagem para ser gravada
	 * @param tipo Tipo de erro <strong> Tipos Possiveis INFO, ERROR </strong>
	 */
	public void gravarLogArquivo(String msg , String tipo){
		Usuario usuario = (Usuario)SessaoUtil.getPojo(SessaoUtil.USUARIO);
		LogEntidade logEntidade = new LogEntidade();
		logEntidade.setNomeUsuario(usuario.getNome());
		logEntidade.setMensagem(msg);
		logEntidade.setTipo(tipo);
		logEntidade.setDataHora(new Date());
		logEntidade.setProjetoSolucao(buscarProjetoSolucao());
		processarArquivo(logEntidade);
	}
	
	/**
	 * Metodo para buscar em qual solucao/projeto as logs deverao ser agrupadas
	 * @return a descricao da solucao em que o log ira gravar os dados
	 */
	private String buscarProjetoSolucao() {
		return ProjetoSolucaoEnum.buscarDescricaoPorUrl(SessaoUtil.getContextPath());
	}

	/**
	 * Metodo para gravar as acoes do sistema no banco (normalmente erros)
	 * @param msg Mensagem para ser gravada
	 * @param tipo Tipo de erro <strong> Tipos Possiveis INFO, ERROR </strong>
	 * @param usuario Nesse caso sempre sera o Sistema
	 * @param classe Nesse caso sera a classe em que o erro ocorreu
	 */
	public void gravarLogArquivo(String msg , String tipo , String usuario , String classe){
		LogEntidade logEntidade = new LogEntidade();
		logEntidade.setNomeUsuario(usuario);
		logEntidade.setMensagem(msg);
		logEntidade.setTipo(tipo);
		logEntidade.setDataHora(new Date());
		logEntidade.setProjetoSolucao(classe);
		processarArquivo(logEntidade);
	}

	/**
	 * Metodo para processar o arquivamento das logs realizadas pelo sistema
	 * @param logEntidade Entidade devidamente preenchida para ser persistida no arquivo
	 */
	private void processarArquivo(LogEntidade logEntidade) {
		if(Boolean.parseBoolean(PropertiesUtil.getConfig(ConstantesUtil.KEY_CONFIG_FUNCAO_LOGS_ATIVA))){
			Log.debug("Tipo: "+logEntidade.getTipo() + " Mensagem: "+logEntidade.getMensagem());
			try {
				String arquivo = PropertiesUtil.getConfig(ConstantesUtil.KEY_CONFIG_CAMINHO_ARQUIVO_GRAVACAO_LOGS);
				arquivo += "logs_"+DataUtil.formataData(new Date(), new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss")) + ".txt";
				File fileArquivo = new File(arquivo);
				StringBuffer logString = new StringBuffer();
				if(fileArquivo.exists()){
					logString.append(logEntidade.toStringArquivo() + "\n");
					FileUtils.writeStringToFile(fileArquivo, logString.toString() , Boolean.TRUE);
				} else {
					fileArquivo.createNewFile();
					logString.append(ConstantesUtil.CABECALHO_LOG);
					logString.append(logEntidade.toStringArquivo() + "\n");
					FileUtils.writeStringToFile(fileArquivo, logString.toString());
				}
			} catch (Exception e) {
				Log.debug(e.getMessage());
			}
		}
	}
	
}
