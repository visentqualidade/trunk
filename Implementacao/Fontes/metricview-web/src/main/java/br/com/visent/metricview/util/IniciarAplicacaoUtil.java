package br.com.visent.metricview.util;

import java.util.HashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.com.visent.componente.log.Log;
import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.corporativo.job.ControleJob;
import br.com.visent.corporativo.util.JPAUtil;
import br.com.visent.metricview.autenticador.ControleUsuarios;
import br.com.visent.metricview.quartz.CadastroDinamicoQuartz;
import br.com.visent.metricview.quartz.FiltrosDinamicosQuartz;
import br.com.visent.metricview.quartz.GravarLogQuartz;

public class IniciarAplicacaoUtil implements ServletContextListener {
	
	private String UNIT_NAME = "metricview";

	@Override
	public void contextInitialized(ServletContextEvent context) {
		PropertiesUtil.init("metricview", "i18n/messages");
		Log.init(PropertiesUtil.getConfig("logs"));
		Log.debug("Inicializando MetricView...");
		JPAUtil.init(UNIT_NAME); 
		ControleUsuarios.init();
		SessionManagerUtil.getInstance().init();
		iniciarJobs();
		Log.debug("Sistema MetricView iniciado");
	}
	
	/**
	 * metodo: Iniciar os Jobs da Aplicacao
	 */
	private void iniciarJobs() {
		Log.debug("Inicializando Jobs do Sistema...");
		ControleJob.init();

		ControleJob.addJob(ConstantesUtil.JOB_GRAVAR_LOGS, GravarLogQuartz.class, 
				new HashMap<String, Object>(), Integer.parseInt(PropertiesUtil.getConfig(ConstantesUtil.TEMPO_JOB_GRAVA_LOG)));
		ControleJob.addJob(ConstantesUtil.JOB_CADASTRO_DINAMICO, CadastroDinamicoQuartz.class, 
				new HashMap<String, Object>(), Integer.parseInt(PropertiesUtil.getConfig(ConstantesUtil.TEMPO_JOB_CADASTRO_DINAMICO)));
		ControleJob.addJob(ConstantesUtil.JOB_FILTRO_DINAMICO, FiltrosDinamicosQuartz.class, 
				new HashMap<String, Object>(), Integer.parseInt(PropertiesUtil.getConfig(ConstantesUtil.TEMPO_JOB_FILTRO_DINAMICO)),Integer.parseInt(PropertiesUtil.getConfig(ConstantesUtil.HORA_EXECUCAO_JOB_FILTRO_DINAMICO)));
		ControleJob.start();
		Log.debug("Jobs Iniciados com sucesso...");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent context) {
		Log.debug("Encerrando Sistema MetricView...");
		JPAUtil.closeEntityManagerFactory();
		Log.debug("Sistema MetricView encerrado");
	}

}
