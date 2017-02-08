package br.com.visent.metricview.quartz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.quartz.JobExecutionContext;

import br.com.visent.componente.log.Log;
import br.com.visent.componente.util.JDBCUtil;
import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.corporativo.exception.BancoException;
import br.com.visent.corporativo.job.AbstractJob;
import br.com.visent.metricview.entidade.Bilhetador;
import br.com.visent.metricview.entidade.Tecnologia;
import br.com.visent.metricview.util.ConstantesUtil;

/**
 * Classe responsável por realizar os procedimentos necessários para as atualizações dos filtros que necessitam ser atualizados dinamicamente de acordo com os dados existentes.
 * @author osx
 *
 */
public class FiltrosDinamicosQuartz extends AbstractJob {

	@Override
	public void executeJob(JobExecutionContext context) {
		Log.debug("Job de filtros dinâmicos iniciado!");
		
		atualizaBilhetador();
		
		Log.debug("Job de gravacao de log terminado!");
	}

	/**
	 * Método responsável por atualiza a tabela de bilhetadores dinamicamente
	 */
	private void atualizaBilhetador() {
		
		ArrayList<Bilhetador> listaBilTotal = getListaBilhetadorCompleta();		
		ArrayList<Bilhetador> listaBilEasy = getListaBilhetadorEasy();
//		ArrayList<Bilhetador> listaBilItx = getListaBilhetadorItx();
		
		mergeBilhetadores(listaBilTotal, listaBilEasy);

		relacionaBilhetadorCN();
		
			
	}

	private void relacionaBilhetadorCN() {
		JDBCUtil.init(PropertiesUtil.getConfig(ConstantesUtil.USUARIO_EASYVIEW), PropertiesUtil.getConfig(ConstantesUtil.SENHA_EASYVIEW), PropertiesUtil.getConfig(ConstantesUtil.URL_EASYVIEW), PropertiesUtil.getConfig(ConstantesUtil.DRIVER_JDBC));
		Connection conexao = JDBCUtil.criarConexao(); 
		
		String sql = 
						"INSERT INTO REL_BILHETADOR_CN                                             "+
						"select SEQ_REL_BILHETADOR_CN.NEXTVAL, sysdate, id_bilhetador, id_cn from ("+
						"WITH bilhetadores_cn_smart AS                                             "+
						"  ( SELECT DISTINCT central, cn FROM cdrview_smp5                         "+
						"  UNION                                                                   "+
						"  SELECT DISTINCT central, cn FROM cdrview_smp8                           "+
						"  UNION                                                                   "+
						"  SELECT DISTINCT central, cn FROM cdrview_smp9                           "+
						"  ),                                                                      "+
						"  bilhetadores_cn_banco AS                                                "+
						"  (SELECT id_bilhetador,                                                  "+
						"    id_cn                                                                 "+
						"  FROM bilhetador,                                                        "+
						"    cn,                                                                   "+
						"    bilhetadores_cn_smart                                                 "+
						"  WHERE bilhetador.nome = central                                         "+
						"  AND cn.nome           = cn                                              "+
						"  ),                                                                      "+
						"  compara_banco_smart AS                                                  "+
						"  ( SELECT id_bilhetador, id_cn FROM bilhetadores_cn_banco                "+
						"  MINUS                                                                   "+
						"  SELECT id_bilhetador, id_cn FROM rel_bilhetador_cn                      "+
						"  )                                                                       "+
						"SELECT                                                                    "+
						"  ID_BILHETADOR,                                                          "+
						"  ID_CN,                                                                  "+
						"  SYSDATE                                                                 "+
						"FROM compara_banco_smart)                                                 ";
		
		try {
			conexao.prepareStatement(sql).execute();
			
			conexao.commit();
			

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
	
	/** Metodo responsavel por retornar a lista atual de bilhetadores existentes na base de dados
	 * @return Lista de {@link Bilhetador}
	 */
	private ArrayList<Bilhetador> getListaBilhetadorCompleta() {
		// TODO Auto-generated method stub
		return (ArrayList<Bilhetador>) genericDAO.buscarTodos(Bilhetador.class);
		
	}

	/** Metodo responavel por pegar as listas de bilhetadores atuais e novos bilhetadores e mescalar na base de dados. 
	 * @param listaBilTotal lista de bilhetadores existentes na base de dados
	 * @param listaBilEasy lista de bilhetadores existentes na base de dados do Easyview
	 */
	private void mergeBilhetadores(ArrayList<Bilhetador> listaBilTotal, ArrayList<Bilhetador> listaBilEasy) {
		//HashMap<String, Bilhetador> totalBil = new HashMap<>();
		
		int cont = 0;
		StringBuffer bils = new StringBuffer();
		
		for (Bilhetador bilhetador : listaBilEasy) {
			if(!listaBilTotal.contains(bilhetador)) {
				
				try {
					genericDAO.inserir(bilhetador);
					cont++;
					bils.append(bilhetador.getNome());
					bils.append(";");
					Log.info("[FiltrosDinamicosQuartz] Inserido bilhetador "+bilhetador.getNome()+" ");
				}catch (BancoException e){
					Log.info("[FiltrosDinamicosQuartz]Erro ao inserir bilhetador "+bilhetador.getNome()+" ",e);
				}
			}
		}
		if(cont > 0)
			Log.info("[FiltrosDinamicosQuartz] Foram inseridos "+cont+" bilhetadores novos. \n"+bils.toString());
		else{
			Log.info("[FiltrosDinamicosQuartz] Não existem bilhetadores novos.");
		}
			
	}

	/** Metodo responsavel por retorna a lista de bilhetadores existentes nas tabelas de dados do easyview
	 * @return lista de {@link Bilhetador}
	 */
	private ArrayList<Bilhetador> getListaBilhetadorEasy() {
		
		JDBCUtil.init(PropertiesUtil.getConfig(ConstantesUtil.USUARIO_EASYVIEW), PropertiesUtil.getConfig(ConstantesUtil.SENHA_EASYVIEW), PropertiesUtil.getConfig(ConstantesUtil.URL_EASYVIEW), PropertiesUtil.getConfig(ConstantesUtil.DRIVER_JDBC));
		Connection conexao = JDBCUtil.criarConexao(); 
		
		String sql = "with msc as ( "+
				"select unique(CENTRAL) as msc, "+ 
			    "case "+
			      "when UPPER(substr(CENTRAL,1,3)) = 'SMS' then 4 "+
			      "when instr(UPPER(CENTRAL),'MSS') > 0 OR instr(UPPER(CENTRAL),'MSC') > 0 then 2 "+
			      "when (UPPER(substr(CENTRAL,1,2)) = 'GG' AND  (instr(UPPER(CENTRAL),'GG') > 0 and UPPER(substr(CENTRAL,1,2)) <> 'GG')) OR "+
			      "     ( UPPER(substr(CENTRAL,1,2)) <> 'CGSN'  AND instr(UPPER(CENTRAL),'CGSN') > 0) OR "+
			      "    ( UPPER(substr(CENTRAL,1,2)) <> 'SG'  AND instr(UPPER(CENTRAL),'SG') > 0) then 3 "+
			      "ELSE 5 "+
			    "END as id_tipo_tecnologia "+          
			"from cdrview_smp5 "+
			"UNION "+
			"select unique(CENTRAL) as msc, "+ 
			    "case " +
			      "when UPPER(substr(CENTRAL,1,3)) = 'SMS' then 4 "+
			      "when instr(UPPER(CENTRAL),'MSS') > 0 OR instr(UPPER(CENTRAL),'MSC') > 0 then 2 "+
			      "when (UPPER(substr(CENTRAL,1,2)) = 'GG' AND  (instr(UPPER(CENTRAL),'GG') > 0 and UPPER(substr(CENTRAL,1,2)) <> 'GG')) OR "+
			           "( UPPER(substr(CENTRAL,1,2)) <> 'CGSN'  AND instr(UPPER(CENTRAL),'CGSN') > 0) OR "+
			           "( UPPER(substr(CENTRAL,1,2)) <> 'SG'  AND instr(UPPER(CENTRAL),'SG') > 0) then 3 "+
			      "ELSE 5 "+
			    "END as id_tipo_tecnologia  "+        
			"from cdrview_smp8 "+
			"UNION "+
			"select unique(CENTRAL) as msc, "+ 
			    "case "+
			      "when UPPER(substr(CENTRAL,1,3)) = 'SMS' then 4 "+
			      "when instr(UPPER(CENTRAL),'MSS') > 0 OR instr(UPPER(CENTRAL),'MSC') > 0 then 2 "+
			      "when (UPPER(substr(CENTRAL,1,2)) = 'GG' AND  (instr(UPPER(CENTRAL),'GG') > 0 and UPPER(substr(CENTRAL,1,2)) <> 'GG')) OR "+
			           "( UPPER(substr(CENTRAL,1,2)) <> 'CGSN'  AND instr(UPPER(CENTRAL),'CGSN') > 0) OR "+
			           "( UPPER(substr(CENTRAL,1,2)) <> 'SG'  AND instr(UPPER(CENTRAL),'SG') > 0) then 3 "+
			      "ELSE 5 "+ 
			    "END as id_tipo_tecnologia "+          
			"from cdrview_smp9 "+
			"),"+
			"tecnologias_nomes as ( "+
			  "select max(nome) as nome from tecnologia "+
			  "group by id_tipo_tecnologia "+
			"), "+
			"tecnologias as ( "+
			  "select * from tecnologia where nome in (select nome from tecnologias_nomes) "+
			") "+
			"select rownum as id_msc, tab.msc as nome, tec.id as id_tecnologia "+
			"from msc tab "+
			"inner join tecnologias tec on tec.id_tipo_tecnologia = tab.id_tipo_tecnologia";
		
		try {
			Statement st = conexao.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			ArrayList<Bilhetador> novosBilhetadores = montaBilhetadores(rs);
			
			rs.close();
			st.close();
			
			return novosBilhetadores;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
	}

	/** Metodo responsavel por montar a lista de objetos {@link Bilhetador} a partir de um resultSet
	 * @param rs ResultSet para obtencao dos dados
	 * @return lista de {@link Bilhetador} retornados do banco de dados.
	 * @throws SQLException
	 */
	private ArrayList<Bilhetador> montaBilhetadores(ResultSet rs) throws SQLException {
		
		ArrayList<Bilhetador> lista = new ArrayList<>();
		
		while(rs.next()){
			Bilhetador b = new Bilhetador();
			Tecnologia tec = new Tecnologia();
			
			tec.setId(rs.getLong("ID_TECNOLOGIA"));
			
			b.setNome(rs.getString("nome"));
			b.setTecnologia(tec);
			
			lista.add(b);	
		}
		
		return lista;
	}

}
