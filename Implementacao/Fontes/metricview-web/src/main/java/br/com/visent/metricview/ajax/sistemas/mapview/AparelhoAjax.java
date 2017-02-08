package br.com.visent.metricview.ajax.sistemas.mapview;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.directwebremoting.io.FileTransfer;

import br.com.visent.componente.util.DataUtil;
import br.com.visent.metricview.exception.MetricViewException;
import br.com.visent.metricview.service.AparelhoService;
import br.com.visent.metricview.util.ConstantesUtil;

public class AparelhoAjax {
	
	private static final String XLSX = ".xlsx";
	
	private AparelhoService aparelhoService = new AparelhoService();
	
	/**
	 * Metodo para gerar o arquivo de logs, contendo o que feito durante a operacao de upload
	 * @return Arquivo para ser feito o download
	 * @throws FileNotFoundException 
	 */
	public FileTransfer gerarRelatorioUpload () throws FileNotFoundException {
		File arquivo = aparelhoService.criarArquivoRelatorio();
		String nomeArquivo = ConstantesUtil.NOME_ARQUIVO_APARELHOS + " "+DataUtil.getHoje() + ".txt";
		return new FileTransfer(nomeArquivo, "application/save" , new FileInputStream(arquivo));
	}
	
	/**
	 * Metodo para realizar a carga de dados dos aparelhos
	 */
	public void realizarCargaDados () {
		aparelhoService.inserirDadosAparelho();
	}
	
	/**
	 * Metodo para realizar o upload do arquivo de aparelho
	 * @param arquivo Componente do DWR para poder ler o arquivo
	 * @return String contendo algum erro ao processar o arquivo, ou caso nao ocorra nenhum problema, a mensagem de confirmacao de upload
	 */
	public String uploadArquivoAparelho (FileTransfer arquivo) throws MetricViewException{
		StringBuilder mensagemRetorno = null;
		if(!arquivo.getFilename().contains(XLSX)){
			throw new MetricViewException("Arquivo inválido. O arquivo deve estar no formato xlsx");
		} else {
			try {
				XSSFWorkbook workBook = new XSSFWorkbook (arquivo.getInputStream());			
				aparelhoService.realizarProcessamentoDeValidacaoArquivo(workBook);
				aparelhoService.criarListaDeAparelhos(workBook);
				workBook = null;
				mensagemRetorno = criarMensagemConfirmacaoInsercaoDados();
			} catch (IOException | MetricViewException e) {
				e.printStackTrace();
				throw new MetricViewException(e.getMessage());
			}
		}
		
		return mensagemRetorno.toString();
	}

	/**
	 * Metodo para criar a mensagem de confirmacao para insercao dos dados no banco
	 * @return StringBuilder contendo a mensagem
	 */
	private StringBuilder criarMensagemConfirmacaoInsercaoDados() {
		StringBuilder mensagemRetorno = new StringBuilder();
		mensagemRetorno.append("Quantidade de linhas no banco: ").append(aparelhoService.consultarCountAparelho()).append("\n");
		mensagemRetorno.append("Quantidade de linhas validas : ").append(AparelhoService.getAparelhosValidos().size()).append("\n");
		mensagemRetorno.append("Quantidade de Linhas invalidas : ").append(AparelhoService.getAparelhosInvalidos().size());
		return mensagemRetorno;
	}

}
