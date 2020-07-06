package br.gov.cemaden.interfaces.pcds;

import br.gov.cemaden.entities.pcds.PcdsTipoestacao;
import br.gov.cemaden.interfaces.GenericDAOInterface;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author marcus.costa
 * 05/06/2019
 */

@Local
public interface PcdsTipoestacaoDAOLocal extends GenericDAOInterface<PcdsTipoestacao> {

	public List<PcdsTipoestacao> getTipoDeEstacaoComTarefaByIdTipoTarManut(Integer idtarManut);
	public Map<Integer, PcdsTipoestacao> getMapTipoestacao();
	public Map<String, PcdsTipoestacao> getMapTipoestacaoPorDescricao();
	public PcdsTipoestacao getTipoDeEstacaoByIdPcd(Integer idPCD);
	public Map<Integer, List<String>> getIdTipoEstacaoPorModelo() ;        
        public int verificarTipoEstacao(String tipoModelo, String codigo);
}
