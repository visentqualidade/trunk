package br.com.visent.metricview.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.visent.corporativo.dao.GenericNativeQueryDAO;
import br.com.visent.metricview.entidade.Banda;

public class BandaDAO extends GenericNativeQueryDAO{

	/**
	 * Metodo para consultar as bandas que o sistema ira considerar invalidas
	 * @return Lista de Banda
	 */
	public List<Banda> consultarBandasInvalidas() {
		List<Banda> list = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			Banda banda = new Banda();
			switch (i) {
				case 0://3G
					banda.setDescricao("UMTS Band 19".toUpperCase());
					list.add(banda);
					break;
				case 1:
					banda.setDescricao("UMTS900 Band viii".toUpperCase());
					list.add(banda);
					break;
				case 2:
					banda.setDescricao("DC-HSDPA".toUpperCase());
					list.add(banda);
					break;
				case 3:
					banda.setDescricao("HSDPA".toUpperCase());
					list.add(banda);
					break;
				case 4:
					banda.setDescricao("HSDPA/HSUPA".toUpperCase());
					list.add(banda);
					break;
				case 5:
					banda.setDescricao("WCDMA FDD".toUpperCase());
					list.add(banda);
					break;
				case 6:
					banda.setDescricao("WCDMA TDD".toUpperCase());
					list.add(banda);
					break;
				case 7:
					banda.setDescricao("WCDMA2100".toUpperCase());
					list.add(banda);
					break;
				case 8:
					banda.setDescricao("UMTS 900".toUpperCase());
					list.add(banda);
					break;
				case 9:
					banda.setDescricao("HSDPA7.2M; WCDMA2100".toUpperCase());
					list.add(banda);
					break;
				case 10://2G
					banda.setDescricao("ER GSM".toUpperCase());
					list.add(banda);
					break;
				case 11:
					banda.setDescricao("GSM-R RAILWAY APPL.".toUpperCase());
					list.add(banda);
					break;
				case 12:
					banda.setDescricao("GSM".toUpperCase());
					list.add(banda);
					break;
				case 13: // 4G
					banda.setDescricao("LTE FDD BAND 60".toUpperCase());
					list.add(banda);
					break;
				case 14:
					banda.setDescricao("LTE FDD BAND 61".toUpperCase());
					list.add(banda);
					break;
				case 15:
					banda.setDescricao("LTE FDD BAND 62".toUpperCase());
					list.add(banda);
					break;
				case 16:
					banda.setDescricao("LTE FDD Band 21".toUpperCase());
					list.add(banda);
					break;
				case 17:
					banda.setDescricao("LTE FDD 26".toUpperCase());
					list.add(banda);
					break;
				case 18:
					banda.setDescricao("LTE FDD Band 25".toUpperCase());
					list.add(banda);
					break;
				case 19:
					banda.setDescricao("LTE BAND 7".toUpperCase());
					list.add(banda);
					break;
				case 20:
					banda.setDescricao("LTE FDD 25 and 26".toUpperCase());
					list.add(banda);
					break;
				case 21:
					banda.setDescricao("LTE FDD Band 41".toUpperCase());
					list.add(banda);
					break;
				case 22:
					banda.setDescricao("LTE 29".toUpperCase());
					list.add(banda);
					break;
				case 23:
					banda.setDescricao("LTE FDD 25".toUpperCase());
					list.add(banda);
					break;

			}
		}
		return list;
	}

}
