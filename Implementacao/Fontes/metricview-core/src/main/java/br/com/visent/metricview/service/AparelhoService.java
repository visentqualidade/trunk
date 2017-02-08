package br.com.visent.metricview.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.visent.componente.dwr.reverse.Reverse;
import br.com.visent.componente.dwr.util.SessaoUtil;
import br.com.visent.corporativo.service.GenericService;
import br.com.visent.metricview.dao.AparelhoDAO;
import br.com.visent.metricview.entidade.Aparelho;
import br.com.visent.metricview.entidade.Banda;
import br.com.visent.metricview.exception.MetricViewException;
import br.com.visent.metricview.util.ConstantesUtil;

public class AparelhoService extends GenericService{
	
	private AparelhoDAO aparelhoDAO = new AparelhoDAO();
	private BandaService bandaService = new BandaService();
	
	//Constantens para tratamento do arquivo
	public static final String TAC = "Tac";
	public static final String MANUFACTURER = "Manufacturer";
	public static final String BANDS = "Bands";
	public static final String MODEL_NAME = "Model Name";
	public static final String DEVICE_TYPE = "Device Type";

	//Variavies para controlar o processamento do arquivo
	private static List<Aparelho> aparelhosValidos = null;
	private static List<Aparelho> aparelhosInvalidos = null;
	private static Set<String> frequenciasInvalidas = null;
	private static List<Banda> bandasInvalidas = null;
	private static List<Banda> bandasValidas = null;
	private static Set<Banda> listaBandas = null;
 
	/**
	 * Metodo para aplicar a validacao do arquivo
	 * @param workBook Componente do POI para que seja possivel fazer a varredura dos dados
	 * @throws MetricViewException Caso ocorra algum problema na validacao dos dados
	 */
	public void realizarProcessamentoDeValidacaoArquivo(XSSFWorkbook workBook) throws MetricViewException {
		aparelhosValidos = new ArrayList<>();
		aparelhosInvalidos = new ArrayList<>();
		frequenciasInvalidas = new HashSet<>();
		bandasInvalidas = bandaService.consultarBandasInvalidas();
		XSSFRow row = null;  
		XSSFCell cell = null;  
		String erro = "Não foi possível realizar o processamento do arquivo. O sistema não identificou os dados esperados na coluna ";
		XSSFSheet sheet = workBook.getSheetAt(0); 
		for (Iterator<Row> rowIterator = (Iterator<Row>) sheet.rowIterator(); rowIterator.hasNext();) {
			row = (XSSFRow) rowIterator.next();  
			if(row.getRowNum() == 0){
				for (Iterator<Cell> cellIterator = (Iterator<Cell>) row.cellIterator(); cellIterator.hasNext();) {
					cell = (XSSFCell) cellIterator.next(); 
					if (cell.getColumnIndex() == 0 && !cell.toString().equalsIgnoreCase(TAC)){
						throw new MetricViewException(erro + TAC);
					} else if (cell.getColumnIndex() == 3 && !cell.toString().equalsIgnoreCase(MANUFACTURER)){
						throw new MetricViewException(erro + MANUFACTURER);
					} else if (cell.getColumnIndex() == 4 && !(cell.toString().equalsIgnoreCase(BANDS))){
						throw new MetricViewException(erro + BANDS);
					} else if (cell.getColumnIndex() == 11 && !(cell.toString().equalsIgnoreCase(MODEL_NAME))){
						throw new MetricViewException(erro + MODEL_NAME);
					}else if (cell.getColumnIndex() == 16 && !(cell.toString().equalsIgnoreCase(DEVICE_TYPE))){
						throw new MetricViewException(erro + DEVICE_TYPE);
					} else if(cell.getColumnIndex() >= 16) {
						break;
					}
				}
			} else {
				break;
			}
		}
	}

	/**
	 * Metodo para popular a lista de aparelhos
	 * @param workBook Componente do POI para que seja possivel fazer a varredura dos dados
	 */
	public void criarListaDeAparelhos(XSSFWorkbook workBook) {
		XSSFRow row = null;  
		XSSFCell cell = null;  
		listaBandas = new HashSet<>();
		XSSFSheet sheet = workBook.getSheetAt(0);
		workBook = null;
		int qtdLinhasArquivo = sheet.getLastRowNum();
		int linha = 0;
		Map<Integer, String> quantidadeNomeSim = new HashMap<Integer, String>();
		for (Iterator<Row> rowIterator = (Iterator<Row>) sheet.rowIterator(); rowIterator.hasNext();) {
			row = (XSSFRow) rowIterator.next();
			//Enviando mensagem para o usuario de QTD_LINHA_MSG_USUARIO em QTD_LINHA_MSG_USUARIO registros
			linha = row.getRowNum(); 
			if(linha % ConstantesUtil.QTD_LINHA_MSG_USUARIO == 0) {
				Reverse.enviarMensagem(ConstantesUtil.FUNCAO_JS_EXIBIR_QTD_LINHA, "Linhas Processadas "+linha+" de "+qtdLinhasArquivo, SessaoUtil.getId());
			}
			if(linha > 0){
				Aparelho aparelho = new Aparelho();
				for (Iterator<Cell> cellIterator = (Iterator<Cell>) row.cellIterator(); cellIterator.hasNext();) {
					cell = (XSSFCell) cellIterator.next();
					if (cell.getColumnIndex() == 0 && !cell.toString().equalsIgnoreCase(AparelhoService.TAC)){
						aparelho.setTac(cell.toString().trim());
					} else if (cell.getColumnIndex() == 3 && !cell.toString().equalsIgnoreCase(AparelhoService.MANUFACTURER)){
						aparelho.setFabricante(cell.toString().trim());
					} else if (cell.getColumnIndex() == 4 && !(cell.toString().equalsIgnoreCase(AparelhoService.BANDS))){
						aparelho.setBandas(this.criarStringBandas(cell.toString().trim()));
						aparelho.setTecnologia(this.classificarTecnologiaPorFrequencia(cell.toString()));
						listaBandas.addAll(this.criarListaBandas(cell.toString().trim()));
						quantidadeNomeSim = this.recuperarQuantidadeSim(cell.toString().trim());
						aparelho.setQuantidadeSim(quantidadeNomeSim.keySet().iterator().next());
						aparelho.setTipoSim(quantidadeNomeSim.values().iterator().next().trim());
					} else if (cell.getColumnIndex() == 11 && !(cell.toString().equalsIgnoreCase(AparelhoService.MODEL_NAME))){
						aparelho.setModelo(cell.toString().trim());
					} else if (cell.getColumnIndex() == 16 && !(cell.toString().equalsIgnoreCase(AparelhoService.DEVICE_TYPE))){
						aparelho.setTipoAparelho(cell.toString().trim());
					}else if(cell.getColumnIndex() > 16) {
						break;
					}
				}
				adicionaAparelhoListaCorreta(aparelho);
			}
		}
		Reverse.enviarMensagem(ConstantesUtil.FUNCAO_JS_EXIBIR_QTD_LINHA, "Linhas Processadas "+linha+" de "+qtdLinhasArquivo, SessaoUtil.getId());
	}
	
	private Map<Integer,String> recuperarQuantidadeSim(String bandas) {
		Map<Integer, String> mapaNomes = new HashMap<Integer, String>(); 
			if(bandas.contains("2 SIM")){
				mapaNomes.put(2, "2 SIM");
			}
			else if (bandas.contains("2SIM")) {
				mapaNomes.put(2, "2SIM");
			}
			else if (bandas.contains("DUAL")){
				mapaNomes.put(2, "DUAL");
			}
			else if (bandas.contains("DUAL MICRO SIM")){
				mapaNomes.put(2, "DUAL MICRO SIM");
			}
			else if (bandas.contains("DUAL SIM")){
				mapaNomes.put(2, "DUAL SIM");
			}
			else if (bandas.contains("Dual SIM as option")){
				mapaNomes.put(2, "Dual SIM as option");
			}
			else if (bandas.contains("3 SIM")){
				mapaNomes.put(3, "3 SIM");
			}
			else if (bandas.contains("4 SIM")){
				mapaNomes.put(4, "4 SIM");
			}else{
				mapaNomes.put(1,"N/I");
			}
		 return mapaNomes;
	}

	/**
	 * Metodo para criar a lista de bandas de um aparelho
	 * @param bandas String contendo as bandas
	 * @return Lista de Bandas
	 */
	private Set<Banda> criarListaBandas(String bandas) {
		Set<Banda> listBandas = new HashSet<>();
		for (String bandaStr : bandas.split(",")) {
			Banda banda = new Banda();
			banda.setDescricao(bandaStr.trim());
			if(!bandasInvalidas.contains(banda)) {
				String tecnologia = classificarTecnologiaPorFrequencia(bandaStr);
				if(tecnologia != null){
					banda.setTecnologia(tecnologia);
					listBandas.add(banda);				
				}
			}
		}
		return listBandas;
	}
	
	/**
	 * Metodo para criar a lista de bandas de um aparelho
	 * @param bandas String contendo as bandas
	 * @return Lista de Bandas
	 */
	private String criarStringBandas(String bandas) {
		String stringBandas = new String();
		stringBandas = "";
		for (String bandaStr : bandas.split(",")) {
			Banda banda = new Banda();
			banda.setDescricao(bandaStr.trim());
			if(!bandasInvalidas.contains(banda)) {
				if(classificarTecnologiaPorFrequencia(bandaStr) != null){
					stringBandas = stringBandas.concat(banda.getDescricao() + ",");
				}
			}
		}
		if(stringBandas.length() == 0){
			stringBandas = "NI";
		}
		
		return stringBandas;
	}

	/**
	 * Metodo para classificar a tecnologia baseada na frequencia
	 * @param freq String contendo a frequencia do aparelho
	 * @return String contendo a classificacao da frequencia
	 */
	private String classificarTecnologiaPorFrequencia(String freq) {
		String tec = null;
		if(freq.contains("LTE")) {
			tec = "4G";
		} else if(freq.contains("UMTS") || freq.contains("HSD") || freq.contains("WCDMA")) {
			tec = "3G";
		} else if(freq.contains("GSM")) {
			tec = "2G";
		} else {
			AparelhoService.getFrequenciasInvalidas().add(freq);
		}
		return tec;
	}

	/**
	 * Metodo para verificar a linha processada e uma linha valida ou nao
	 * @param aparelho Entidade aparelho 
	 */
	private void adicionaAparelhoListaCorreta(Aparelho aparelho) {
		if(aparelho.getTecnologia() == null) {
			AparelhoService.getAparelhosInvalidos().add(aparelho);
		} else {
			AparelhoService.getAparelhosValidos().add(aparelho);
		}
	}

	/**
	 * Metodo para realizar a carga de dados dos aparelhos
	 */
	public void inserirDadosAparelho() {
		try {
			aparelhoDAO.getEntityManager().getTransaction().begin();
			Reverse.enviarMensagem(ConstantesUtil.FUNCAO_JS_EXIBIR_QTD_LINHA, "Removendo as Linhas de Aparelho", SessaoUtil.getId());
			aparelhoDAO.droparBackupAparelhosEBandas();
			aparelhoDAO.criarBackupAparelhosEBandas();
			aparelhoDAO.removerTodosAparelhoEBanda();
			aparelhoDAO.inserirTodosAparelho(getAparelhosValidos());
			aparelhoDAO.inserirTodasBandas(new ArrayList<Banda>(listaBandas));
			aparelhoDAO.getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			aparelhoDAO.getEntityManager().getTransaction().rollback();
			throw new MetricViewException("Erro ao inserir os dados de aparelho");
		}
	}
	
	/**
	 * Metodo para gerar o arquivo contendo as informacoes de erro que foram capturadas durante 
	 * o processamento do arquivo de aparelhos
	 * @return Arquivo para ser feito o download para o usuario
	 */
	public File criarArquivoRelatorio() {
		FileWriter writer = null;
		File arquivo = new File("arquivo");
		try {
			writer = new FileWriter(arquivo);
			writer.append("Frequencias Invalidas : "+AparelhoService.getFrequenciasInvalidas().size()+"\n");
			int i = 0;
			for (Iterator<String> freqIterator = AparelhoService.getFrequenciasInvalidas().iterator(); freqIterator.hasNext();) {
				String freq = (String) freqIterator.next();
				if(i != 0 && i % 7 == 0) {
					writer.append(freq +",\n");
				} else {
					writer.append(freq +", ");
				}
				i++;
			}
			writer.append("\n\nLinhas Descartadas : "+AparelhoService.getAparelhosInvalidos().size()+"\n");
			for (Aparelho aparelho : AparelhoService.getAparelhosInvalidos()) {
				String descricao = AparelhoService.TAC + ": "+aparelho.getTac() + " ; ";
				descricao += AparelhoService.MANUFACTURER + ": "+aparelho.getFabricante() + " ; ";
				descricao += AparelhoService.BANDS + ": " + aparelho.getBandas() + " ; ";
				descricao += AparelhoService.MODEL_NAME + ": "+aparelho.getModelo()+ "\n";
				writer.append(descricao);
			}
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return arquivo;
	}
	
	/**
	 * Metodo para realizar um count(*) na tabela de aparelho
	 * @return Quantidade de resgitros
	 */
	public Integer consultarCountAparelho() {
		return aparelhoDAO.consultarCountAparelho();
	}
	
	public static List<Aparelho> getAparelhosValidos() {
		return aparelhosValidos;
	}
	
	public static List<Aparelho> getAparelhosInvalidos() {
		return aparelhosInvalidos;
	}
	
	public static Set<String> getFrequenciasInvalidas() {
		return frequenciasInvalidas;
	}
	
	public static List<Banda> getBandasInvalidas() {
		return bandasInvalidas;
	}

	public static List<Banda> getBandasValidas() {
		return bandasValidas;
	}

	public static void setBandasValidas(List<Banda> bandasValidas) {
		AparelhoService.bandasValidas = bandasValidas;
	}

}
