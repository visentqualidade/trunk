package br.com.visent.metricview.ajax;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.directwebremoting.io.FileTransfer;

import br.com.visent.componente.util.DataUtil;
import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.metricview.service.ExcecaoCGIService;
import br.com.visent.metricview.util.ConstantesUtil;

public class ExcecaoCGIAjax {
	
	private ExcecaoCGIService excecaoCGIService = new ExcecaoCGIService();
	
	public List<Map<String, String>> consultarTodos() {
		return excecaoCGIService.consultarTodos();
	}
	
	public FileTransfer exportarRelatorio(List<Map<String, String>> dados) throws IOException {
		List<String> colunas = criarColunas(dados);
		return exportaArquivo(colunas , dados);
	}
	
	public FileTransfer exportaArquivo(List<String> colunas, List<Map<String, String>> dados) throws IOException {
		Workbook workBook = new XSSFWorkbook();
		Sheet sheet = workBook.createSheet();
		workBook.setSheetName(0, PropertiesUtil.getMessage(ConstantesUtil.LBL_EXCECAO_CGI));
		Row rowFileHeader = sheet.createRow(0);
		rowFileHeader.setHeightInPoints(100);
		Cell headerCell = rowFileHeader.createCell(0);

		RichTextString richString = new XSSFRichTextString(getHeaderString());
		headerCell.setCellValue(richString);
		headerCell.setCellType(Cell.CELL_TYPE_STRING);
		CellStyle headerCellStyle = workBook.createCellStyle();
		headerCellStyle.setWrapText(true);
		headerCellStyle.setAlignment(CellStyle.VERTICAL_TOP);
		headerCell.setCellStyle(headerCellStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, colunas.size() - 1));

		// create Headers
		Row rowHeader = sheet.createRow(1);
		for (int i = 0; i < colunas.size(); i++) {
			Cell cell = rowHeader.createCell(i);
			cell.setCellValue(colunas.get(i));
		}
		
		int indexColuna = 2;
		for(Map<String, String> dado : dados) {
			Row dataRow = sheet.createRow(indexColuna);
			Cell cell = dataRow.createCell(0, Cell.CELL_TYPE_STRING);
			cell.setCellValue(dado.get("cn"));
			Cell cell1 = dataRow.createCell(1, Cell.CELL_TYPE_STRING);
			cell1.setCellValue(dado.get("bilhetador"));
			Cell cell2 = dataRow.createCell(2, Cell.CELL_TYPE_STRING);
			cell2.setCellValue(dado.get("cgi"));
			indexColuna++;
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		workBook.write(baos);
		baos.flush();
		baos.close();
		String nomeArquivo = " Lista de Exceções de CGI - " +DataUtil.sdf_DD_MM_YYYY_HHMMSS.format(new Date()) + ".xlsx";
		return new FileTransfer(nomeArquivo, "application/save", baos.toByteArray());
	}

	private String getHeaderString() {
		StringBuffer header = new StringBuffer();
		header.append("Data da Geração: ").append(DataUtil.sdf_DDMMYYYY.format(new Date())).append("\n");
		return header.toString();
	}
	
	private List<String> criarColunas(List<Map<String, String>> dados) {
		List<String> colunas = new ArrayList<>();
		colunas.add(PropertiesUtil.getMessage(ConstantesUtil.TXT_COL_CN));
		colunas.add(PropertiesUtil.getMessage(ConstantesUtil.TXT_COL_CENTRAL));
		colunas.add(PropertiesUtil.getMessage(ConstantesUtil.TXT_COL_CGI));
		return colunas;
	}

}
