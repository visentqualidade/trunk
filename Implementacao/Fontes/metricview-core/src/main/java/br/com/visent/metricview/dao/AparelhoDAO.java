package br.com.visent.metricview.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.visent.componente.dwr.reverse.Reverse;
import br.com.visent.componente.dwr.util.SessaoUtil;
import br.com.visent.corporativo.dao.GenericNativeQueryDAO;
import br.com.visent.metricview.entidade.Aparelho;
import br.com.visent.metricview.entidade.Banda;
import br.com.visent.metricview.exception.MetricViewException;
import br.com.visent.metricview.util.ConstantesUtil;

public class AparelhoDAO extends GenericNativeQueryDAO{

	
	
	
	/**
	 * Metodo para dropar as tabelas de o backup das tabelas de BANDA e APARELHO
	 */
	public void droparBackupAparelhosEBandas() {
		
		String queryDropTableBandaBackup = " DROP TABLE BANDA_BACKUP ";
		String queryDropTableAparelhoBackup = " DROP TABLE APARELHO_BACKUP ";
		try {
			executeInsertUpdateQuerySemCommit(queryDropTableBandaBackup, GenericNativeQueryDAO.ACAO_DROP);
			executeInsertUpdateQuerySemCommit(queryDropTableAparelhoBackup, GenericNativeQueryDAO.ACAO_DROP);
		} catch(Exception Ex){
			Ex.printStackTrace();
		}
		
	}
	/**
	 * Metodo para criar o backup das tabelas de BANDA e APARELHO
	 */
	public void criarBackupAparelhosEBandas() {
		
		String queryCreateBandaBackup = " CREATE TABLE BANDA_BACKUP AS SELECT * FROM BANDA ";
		String queryCreateAparelhoBackup = " CREATE TABLE APARELHO_BACKUP AS SELECT * FROM APARELHO ";
		try {
			executeInsertUpdateQuerySemCommit(queryCreateBandaBackup, GenericNativeQueryDAO.ACAO_CREATE);
			executeInsertUpdateQuerySemCommit(queryCreateAparelhoBackup, GenericNativeQueryDAO.ACAO_CREATE);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MetricViewException("Nao foi possivel criar backup dos dados de Aparelho!");
		}
		
	}
	
	/**
	 * Metodo para remover os APARELHOS e BANDAS
	 */
	public void removerTodosAparelhoEBanda() {
		String queryBanda = " DELETE FROM BANDA ";
		String queryAparelho = " DELETE FROM APARELHO ";
		try {
			executeInsertUpdateQuerySemCommit(queryBanda, GenericNativeQueryDAO.ACAO_DELETE);
			executeInsertUpdateQuerySemCommit(queryAparelho, GenericNativeQueryDAO.ACAO_DELETE);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MetricViewException("Nao foi possivel remover os dados de Aparelho!");
		}
		
	}

	/**
	 * Metodo para inserir os aparelhos
	 * @param aparelhos Lista de aparelhos para serem inseridos
	 */
	public void inserirTodosAparelho(List<Aparelho> aparelhos) {
		List<String> listInsertAparelho = criarInsertAparelho(aparelhos);
		int i = 0;
		int qtdLinha = listInsertAparelho.size();
		for (String insertAparelho : listInsertAparelho) {
			if(i % ConstantesUtil.QTD_LINHA_MSG_USUARIO_APARELHO == 0) {
				Reverse.enviarMensagem(ConstantesUtil.FUNCAO_JS_EXIBIR_QTD_LINHA, "Inserido "+i+" aparelhos de "+qtdLinha, SessaoUtil.getId());
			}
			i++;
			executeInsertUpdateQuerySemCommit(insertAparelho, GenericNativeQueryDAO.ACAO_INSERT);
		}
		Reverse.enviarMensagem(ConstantesUtil.FUNCAO_JS_EXIBIR_QTD_LINHA, "Inserido "+i+" aparelhos de "+qtdLinha, SessaoUtil.getId());
	}
	
	/**
	 * Metodo para criar os insertes os aparelhos
	 * @param aparelhos Lista de aparelhos para serem inseridos
	 * @return Lista de String contendo os inserts dos aparelhos
	 */
	private List<String> criarInsertAparelho(List<Aparelho> aparelhos) {
		List<String> listInsert = new ArrayList<>();
		for (int i = 0; i < aparelhos.size(); i++) {
			Aparelho aparelho = aparelhos.get(i);
			String fabricante = aparelho.getFabricante();
			String modelo = aparelho.getModelo();
			String tac = aparelho.getTac();
			String tecnologia = aparelho.getTecnologia();
			String bandas = aparelho.getBandas();
			String tipoAparelho = aparelho.getTipoAparelho();
			Integer quantidadeSim = aparelho.getQuantidadeSim();
			String tipoSim = aparelho.getTipoSim();
			listInsert.add(" INSERT INTO APARELHO VALUES ('"+tac+"' , '"+modelo+"' , '"+fabricante+"' , '"+tecnologia+"' ,"
					+ " '"+bandas+"' , '"+tipoAparelho+"' , "+quantidadeSim+" , '"+tipoSim+"')");
		}
		return listInsert;
	}

	/**
	 * Metodo para inserir os aparelhos
	 * @param bandas Lista de bandas para serem inseridas
	 */
	public void inserirTodasBandas(List<Banda> bandas) {
		List<String> listInsertBanda = criarInsertBanda(bandas);
		for (String insertBanda : listInsertBanda) {
			executeInsertUpdateQuerySemCommit(insertBanda, GenericNativeQueryDAO.ACAO_INSERT);
		}
	}
	
	/**
	 * Metodo para criar os insertes das bandas
	 * @param bandas Lista de bandas para serem inseridas
	 * @return Lista de String contendo os inserts das bandas
	 */
	private List<String> criarInsertBanda(List<Banda> bandas) {
		List<String> listaInsert = new ArrayList<>();
		for (int i = 0; i < bandas.size(); i++) {
			Banda banda = bandas.get(i);
			String descricao = banda.getDescricao();
			String tecnologia = banda.getTecnologia();
			listaInsert.add(" INSERT INTO BANDA VALUES ("+i+" , '"+descricao+"' , '"+tecnologia+"')");
		}
		return listaInsert;
	}

	/**
	 * Metodo para realizar um count(*) na tabela de aparelho
	 * @return Quantidade de resgitros
	 */
	public Integer consultarCountAparelho() {
		String sql = " SELECT COUNT(1) FROM aparelho ";
		return ((BigDecimal) getEntityManager().createNativeQuery(sql).getSingleResult()).intValue();
	}

}
