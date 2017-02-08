package br.com.visent.metricview.quartz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.quartz.JobExecutionContext;

import br.com.visent.componente.log.Log;
import br.com.visent.componente.util.DataUtil;
import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.corporativo.job.AbstractJob;
import br.com.visent.metricview.entidade.LogEntidade;
import br.com.visent.metricview.service.LogService;
import br.com.visent.metricview.util.ConstantesUtil;

public class GravarLogQuartz extends AbstractJob {

	@Override
	public void executeJob(JobExecutionContext context) {
		Log.debug("Job de gravacao de log iniciado!");
		if(Boolean.parseBoolean(PropertiesUtil.getConfig(ConstantesUtil.KEY_CONFIG_FUNCAO_LOGS_ATIVA))){
			LogService logService = new LogService();
			List<LogEntidade> logsInserir = new ArrayList<>();
			LogEntidade registro = null;
			List<File> arrayFile = montarListaArquivos();
			for (int i = 0; i < arrayFile.size(); i++) {
				if((i + 1) == arrayFile.size()){
					continue;
				}
				File fileArquivo = arrayFile.get(i);
				try {
					BufferedReader bufferedReader = new BufferedReader(new FileReader(fileArquivo));
					bufferedReader.readLine();
					String linha = null;
					while((linha = bufferedReader.readLine()) != null){
						registro = montarObjetoLogEntidade(linha);
						logsInserir.add(registro);
					}
					try {
						genericDAO.inserir(logsInserir);
					} catch (Exception e) {
						logService.gravarLogArquivo("Nao foi possivel gravar log", Log.ERROR , ConstantesUtil.NOME_SISTEMA , "GravarLogQuartz");
					}
					bufferedReader.close();
					fileArquivo.delete();
				} catch (Exception e) {
					e.printStackTrace();
					Log.debug(e.getMessage());
					logService.gravarLogArquivo("Nao foi possivel gravar log", Log.ERROR , ConstantesUtil.NOME_SISTEMA , "GravarLogQuartz");
				}
			}
		}
		Log.debug("Job de gravacao de log terminado!");
	}

	/**
	 * Metodo para montar a lista de arquivos
	 * @return Lista de arquivos ordenada para ser inserida no banco
	 */
	private List<File> montarListaArquivos() {
		File file = new File(PropertiesUtil.getConfig(ConstantesUtil.KEY_CONFIG_CAMINHO_ARQUIVO_GRAVACAO_LOGS));
		if (!file.exists()) {
			file.mkdirs();
		}
		List<File> arquivosParaLer = new ArrayList<>();
		arquivosParaLer.addAll(Arrays.asList(file.listFiles()));
		Collections.sort(arquivosParaLer);
		return arquivosParaLer;
	}

	/**
	 * Metodo para montar o objeto ${@link LogEntidade} para ser inserido no banco
	 * @param linha Linha contendo os dados lidos do arquivo
	 * @return ${@link LogEntidade} devidamente populada baseada no arquivo lido 
	 */
	private LogEntidade montarObjetoLogEntidade(String linha) {
		LogEntidade logEntidade = new LogEntidade();
		String[] linhaArray = linha.split(";");
		logEntidade.setMensagem(linhaArray[0].toString());
		logEntidade.setTipo(linhaArray[1].toString());
		logEntidade.setNomeUsuario(linhaArray[2].toString());
		logEntidade.setDataHora(DataUtil.parse(linhaArray[3].toString(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")));
		logEntidade.setProjetoSolucao(linhaArray[4].toString());
		return logEntidade;
	}

}
