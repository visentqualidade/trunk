package br.com.visent.metricview.service;

import java.util.ArrayList;
import java.util.List;

import br.com.visent.corporativo.service.GenericService;
import br.com.visent.metricview.dao.BilhetadorDAO;
import br.com.visent.metricview.entidade.Bilhetador;
import br.com.visent.metricview.entidade.BilhetadorCN;
import br.com.visent.metricview.to.BilhetadorTO;

public class BilhetadorService extends GenericService {

	private BilhetadorDAO bilhetadorDAO = new BilhetadorDAO();
	
	/**
	 * Ao passar um conjunto de bilhetadores (que mantém todas os seus relacionamentos, CN e Associação com CN),
	 * transforma cada relacionamento em um elemento de BilhetadorTO que leva consigo nome do bilhetador e a associação com cada CN
	 * que este bilhetador estiver associado.
	 * @param bilhetadores - Lista de bilhetadores que tem todos seus relacionamentos preenchidos.
	 * @return
	 */
	public List<BilhetadorTO> gerarAssociacaoBilhetador(List<Bilhetador> bilhetadores) {
		List<BilhetadorTO> bilhetadoresTo = new ArrayList<BilhetadorTO>();
		for (Bilhetador bilhetador : bilhetadores) {
			if (bilhetador.getRelBilhetadores() != null && bilhetador.getRelBilhetadores().size() > 0){
				for (BilhetadorCN bilhetadorCN : bilhetador.getRelBilhetadores()){
					
					bilhetadoresTo.add(new BilhetadorTO(bilhetador.getNome(),
														bilhetadorCN.getCodigoNacional().getCodigo().toString(),
														bilhetadorCN.getDataFormatada()));
				}
			}else{
				bilhetadoresTo.add(new BilhetadorTO(bilhetador.getNome(), null, null));
			}
		}
		return bilhetadoresTo;
	}
	
	/**
	 * Metodo para consultar todos os bilhetadores de GSM - VOZ
	 * @return Lista de bilhetadores
	 */
	public List<Bilhetador> consultarBilhetadoresGSM() {
		return bilhetadorDAO.consultarBilhetadoresGSM();
	}
	
}
