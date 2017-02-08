package br.com.visent.metricview.quartz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.hibernate.Hibernate;
import org.quartz.JobExecutionContext;

import br.com.visent.componente.log.Log;
import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.corporativo.entidade.Entidade;
import br.com.visent.corporativo.exception.BancoException;
import br.com.visent.corporativo.job.AbstractJob;
import br.com.visent.metricview.dao.BilhetadorDAO;
import br.com.visent.metricview.dao.TecnologiaDAO;
import br.com.visent.metricview.entidade.Bilhetador;
import br.com.visent.metricview.entidade.BilhetadorCN;
import br.com.visent.metricview.entidade.CodigoNacional;
import br.com.visent.metricview.entidade.Tecnologia;
import br.com.visent.metricview.entidade.TipoTecnologia;
import br.com.visent.metricview.util.CadastroDinamico;
import br.com.visent.metricview.util.ConstantesUtil;
import br.com.visent.metricview.util.URLManager;

public class CadastroDinamicoQuartz extends AbstractJob {

	private static final String LIMITADOR_BILHETADOR = "\\.";
	private static final String LIMITADOR_DATA_HORA  = "_";
	
	private String re1="((?:[a-z][a-z0-9_]*))";	// Variable Name 1
	private String re2="(\\.)";	// Any Single Character 1
	private String re3="((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3}))(?:[0]?[1-9]|[1][012])(?:(?:[0-2]?\\d{1})|(?:[3][01]{1})))(?![\\d])";	// YYYYMMDD 1
	private String re4="(_)";	// Any Single Character 2
	private String re5="(\\d+)";	// Year 1
	private String re6="(\\.)";	// Any Single Character 3
	private String re7="(dat)";	// Word 1

	BilhetadorDAO bilhetadorDAO = new BilhetadorDAO();
	TecnologiaDAO tecnologiaDAO = new TecnologiaDAO();
	
	/**
	 * Método responsável pela execução do job que irá consultar o portal do CDRView, em busca de:
	 * 
	 *  	1- Listas de bilhetadores e Tecnologias divulgadas pelo portal.
	 *  	2- Lista de associação de bilhetadores e CN [ codigo(s) nacional(is) ]
	 *  	 
	 */
	public void executeJob(JobExecutionContext context) {

		Log.debug("Job de cadastro dinâmico foi iniciado!");

		if(Boolean.parseBoolean(PropertiesUtil.getConfig(ConstantesUtil.KEY_CONFIG_CADASTRO_DINAMICO_ATIVO))){
			
			URLManager urlManager = new URLManager();
			
			// Módulo de busca no Portal do CDRView atrás de Bilhetadores e Ferramentas
			try{
				
				String html = urlManager.obterHtmlPelaUrl(PropertiesUtil.getConfig(ConstantesUtil.KEY_CONFIG_URL_PORTAL_CADASTRO_DINAMICO));
				
				extrairInformacoesDoHtml(html);
				
			}catch (Exception e){
				e.printStackTrace();
				// Em caso de erro utilizar apenas informações 
				Log.debug("Não foi possível extrair as informações do Portal! Erro: "+ e.getMessage());
			}
			
			// Módulo de buscar do PARSER a associação de bilhetadores...
			try{
				File diretorio = new File(PropertiesUtil.getConfig(ConstantesUtil.KEY_CONFIG_DIRETORIO_ARQUIVOS));
				
				List<File> arquivosGeradosParser = Arrays.asList(diretorio.listFiles());
				
				for(File arquivo : arquivosGeradosParser){
					try{
						System.out.print("Processando arquivo... "+arquivo.getName()+"...");
						
						Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6+re7,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
					    
						if (p.matcher(arquivo.getName()).find()){
							System.out.print("válido... "+arquivo.getName());
							// Busco nome do bilhetador localizado no nome do arquivo
							String bilhetador = arquivo.getName().split(LIMITADOR_BILHETADOR)[0];
							bilhetador = bilhetador.split(ConstantesUtil.REMOVER_NOME_CHU_BILHETADOR)[0];
							
							// retiro a data localizada no nome do arquivo
							String data = arquivo.getName().split(LIMITADOR_BILHETADOR)[1];
							
							// retiro caracteres especiais da data
							data = data.replace(LIMITADOR_DATA_HORA," ");
							
							// processo o arquivo
							if (processarArquivoDeAssociacao(bilhetador, arquivo, data)){
								arquivo.delete();
							}
						}else{
							System.out.print("inválido... "+arquivo.getName());	
						}
						System.out.println("processado. "+arquivo.getName());
						
					}catch(Exception e){
						e.printStackTrace();
						Log.debug("Erro ao processar o arquivo: "+arquivo.getName() +" - "+e.getMessage());
					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
				Log.debug("Não foi possível extrair as informações dos arquivos de associação de Bilhetador x CN - "+e.getMessage());
			}
		}
		Log.debug("Job de cadastro dinâmico foi Finalizado!");
	}
	
	/**
	 * Método que tem como funcionalidade principal obter as associações de bilhetadores e CN's de um arquivo.
	 * @param bilhetador 
	 * @param arquivo - Arquivo fornecido para realizar a extração de associação do cadastro dinâmico
	 * @param data 
	 * @return 
	 * @throws IOException 
	 */
	@SuppressWarnings("resource")
	private boolean processarArquivoDeAssociacao(String nomeBilhetador, File arquivo, String data) throws IOException {
		boolean processado = false;
		Bilhetador bilhetador = new Bilhetador(nomeBilhetador, null);
		
		List<Bilhetador> bilhetadores = (List<Bilhetador>) genericDAO.buscarPorFiltro(bilhetador);
		
		for (Bilhetador bil : bilhetadores){
			if (bil.getNome().equals(nomeBilhetador) ){
				Hibernate.initialize(bil.getRelBilhetadores());
				
				Set<BilhetadorCN> bilhetadoresCN = bil.getRelBilhetadores();
				
				BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
				
				String codNacionalArquivo = null;
				try{
					while ((codNacionalArquivo = leitor.readLine()) != null){
						boolean contem = false; 
						for (BilhetadorCN relBilhetador : bilhetadoresCN) {
							if (relBilhetador.getCodigoNacional().getCodigo().equals(new Long(codNacionalArquivo))){
								contem = true;
							}
						}
						
						if (!contem){
							CodigoNacional cn = new CodigoNacional();
							cn.setCodigo(new Long(codNacionalArquivo));
							
							List<CodigoNacional> cns = genericDAO.buscarPorFiltro(cn);
							
							if (cns.size() > 0){
								bil.getRelBilhetadores().add(new BilhetadorCN(cns.get(0), data, bil));
								Log.debug("Nova associação relizada - Bilhetador ("+nomeBilhetador+") - CN ("+codNacionalArquivo+") - Data ("+data+") ");
							}
						}
					}
				}catch(IOException e){
					e.printStackTrace();
					throw new IOException(e.getMessage());
				}
				
				leitor.close();
				processado = true;
				try{
					genericDAO.atualizar(bil);
				}catch(Exception e){
					e.printStackTrace();
					throw new BancoException(e.getMessage());
				}
			}
		}
		return processado;
	}


	/**
	 * Método que abstrai do HTML ou Conteúdo fornecido informações necessárias para realização do cadastro dinâmico.
	 * @param htmlFornecido Variável de controle que pode ser de HTML utilizado como fonte das informações.
	 * @throws Exception
	 */
	private void extrairInformacoesDoHtml(String htmlFornecido) throws Exception {
		
		CadastroDinamico caDinamico = new CadastroDinamico();
		
		Collection<Entidade> camposBilhetadores = caDinamico.extrairCampo(htmlFornecido, Bilhetador.class);
		
		Collection<Bilhetador> novosBilhetadores = bilhetadorDAO.buscarConjuntoPorDiferenca((Collection<Entidade>) camposBilhetadores);
		
		if (!novosBilhetadores.isEmpty()){
			for (Bilhetador bilhetador : novosBilhetadores){
				List<Tecnologia> tecnologias = genericDAO.buscarPorFiltro(bilhetador.getTecnologia());
				if (tecnologias.size() > 0){
					bilhetador.setTecnologia(tecnologias.get(0));
					genericDAO.inserir(bilhetador);
				}else{
					Tecnologia novaTecnologia = new Tecnologia();
					novaTecnologia.setNome(bilhetador.getTecnologia().getNome());
					novaTecnologia.setTipoTecnologia(classificarTipoTecnologia(bilhetador));
					
					genericDAO.inserir(novaTecnologia);
					bilhetador.setTecnologia(novaTecnologia);
					genericDAO.inserir(bilhetador);
				}
			}
		}
		
	}

	/**
	 * Método que classifica o tipo de tecnologia segundo esta ordem
	 * 1- SMS: Se o campo bilhetador  SUBSTR(1;3)= “SMS”
	 * 2- Voz:  Se o campo bilhetador contiver “MSC” ou “MSS”
	 * 3- DADOS: Se o campo bilhetador contiver “SGSN” ou “GGSN” ou “SG”, “GG”;
	 * @param novaTecnologia
	 * @return
	 */
	private TipoTecnologia classificarTipoTecnologia(Bilhetador bilhetador) {
		TipoTecnologia tipoTecnologia = new TipoTecnologia();
		
		if (bilhetador.getNome().substring(0, 3).equalsIgnoreCase(ConstantesUtil.TIPO_TEC_SMS)){
			tipoTecnologia.setId(ConstantesUtil.TIPO_TECNOLOGIA_SMS);
			List<TipoTecnologia> tipos = genericDAO.buscarPorFiltro(tipoTecnologia);
			return tipos.get(0);
		}else{
			if (bilhetador.getNome().toUpperCase().contains(ConstantesUtil.TIPO_TEC_VOZ_MSC) ||
				bilhetador.getNome().toUpperCase().contains(ConstantesUtil.TIPO_TEC_VOZ_MSS)	){
				tipoTecnologia.setId(ConstantesUtil.TIPO_TECNOLOGIA_VOZ);
				List<TipoTecnologia> tipos = genericDAO.buscarPorFiltro(tipoTecnologia);
				return tipos.get(0);
			}else{
				if (bilhetador.getNome().toUpperCase().contains(ConstantesUtil.TIPO_TEC_DADOS_GG) 	||
					bilhetador.getNome().toUpperCase().contains(ConstantesUtil.TIPO_TEC_DADOS_GGSN)	||
					bilhetador.getNome().toUpperCase().contains(ConstantesUtil.TIPO_TEC_DADOS_SG)   ||
					bilhetador.getNome().toUpperCase().contains(ConstantesUtil.TIPO_TEC_DADOS_SGSN)   ){
					tipoTecnologia.setId(ConstantesUtil.TIPO_TECNOLOGIA_DADOS);
					List<TipoTecnologia> tipos = genericDAO.buscarPorFiltro(tipoTecnologia);
					return tipos.get(0);
				}else{
					tipoTecnologia.setId(ConstantesUtil.TIPO_TECNOLOGIA_INDEF);
					List<TipoTecnologia> tipos = genericDAO.buscarPorFiltro(tipoTecnologia);
					return tipos.get(0);
				}
			}
		}
	}
	
}