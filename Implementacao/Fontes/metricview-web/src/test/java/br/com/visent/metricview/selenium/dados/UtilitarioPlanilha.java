package br.com.visent.metricview.selenium.dados;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class UtilitarioPlanilha {
	private String nome = "ResultadoTeste";
	private Integer quantidadeLinhas = 999;

	public FileOutputStream copularTabela(HSSFWorkbook areaTrabalho) {
		HSSFSheet abaPlanilha;
		HSSFRow linha = null;
		HSSFCell celula;
		FileOutputStream planilha = null;

		try {
			planilha = new FileOutputStream(
					"C:/Users/admin/workspace/metricview/metricview-web/src/test/selenium/massaDados/"
							+ nome + ".xls");
		} catch (FileNotFoundException e) {
			System.out.println("Erro os criar tabela");
			e.printStackTrace();
		}
		abaPlanilha = areaTrabalho.createSheet("Grupos");
		for (int i = 0; i <= quantidadeLinhas; i++) {
			linha = abaPlanilha.createRow(i);
			for (int j = 0; j <= 1; j++) {
				celula = linha.createCell(j);
				if (j == 0) {
					celula.setCellValue("Teste " + i);
				}
				if (j == 1) {
					celula.setCellValue("Desc " + i);
				}
			}
		}
		return planilha;
	}

	public void salvarTabela(HSSFWorkbook areaTrabalho,	FileOutputStream planilha) {
		try {
			areaTrabalho.write(planilha);
			planilha.close();
		} catch (IOException e) {
			System.out.println("outro");
			e.printStackTrace();
		}
	}
	
	public HSSFSheet abrirPlanilha(String url, Integer numeroPlanilha) throws IOException { 
		 FileInputStream planilha = new FileInputStream(url);  
		 HSSFWorkbook area = new HSSFWorkbook(planilha);  
		 return area.getSheetAt(numeroPlanilha);
	 }
	 
	 public String lerPlanilha(HSSFSheet abaPlanilha, Integer numeroPlanilha, Integer numeroLinha, Integer numeroColuna) throws IOException { 
		 HSSFRow linha = null;  
		 HSSFCell celula;		 
		 linha = abaPlanilha.getRow(numeroLinha);
		 celula = linha.getCell(numeroColuna);
		 return celula.getStringCellValue();
	 }
	 
	 public String abritLerPlanilha(String url, Integer numeroPlanilha, Integer numeroLinha, Integer numeroColuna) throws IOException { 
		 FileInputStream planilha = new FileInputStream(url);  
		 HSSFWorkbook area = new HSSFWorkbook(planilha);
		 HSSFSheet abaPlanilha;
		 abaPlanilha = area.getSheetAt(numeroPlanilha);		 
		 HSSFRow linha = null;  
		 HSSFCell celula;		 
		 linha = abaPlanilha.getRow(numeroLinha);
		 celula = linha.getCell(numeroColuna);
		 return celula.getStringCellValue();
	 }
	 
	 @SuppressWarnings("unused")
	public void escreverPlanilha(String url,HSSFSheet abaPlanilha) throws IOException {  
		 	 HSSFWorkbook area = new HSSFWorkbook(); 
	     HSSFRow linha = null;  
	     HSSFCell celula;
	     linha = abaPlanilha.getRow(0);
	     celula = linha.createCell(2);
	     System.out.println(linha.getCell(2));
	     linha.getCell(2).setCellValue("Incluido");	    
	     System.out.println(linha.getCell(2));	     	     	     	   
	     FileOutputStream stream = new FileOutputStream(url);  
	     area.write(stream);
	     stream.close();
	 } 
}
