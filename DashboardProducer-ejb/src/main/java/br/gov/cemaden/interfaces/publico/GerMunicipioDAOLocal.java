package br.gov.cemaden.interfaces.publico;

import br.gov.cemaden.entities.publico.GerMunicipio;
import br.gov.cemaden.interfaces.GenericDAOInterface;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author marcus.costa
 * 05/06/2019
 */
@Local
public interface GerMunicipioDAOLocal extends GenericDAOInterface<GerMunicipio> {
    
	public List<GerMunicipio> getGerMunicipioListByUF(String uf);
	public List<GerMunicipio> getGerMunicipioListWithPcdsEstacaoByUF(String uf);	
	public List<GerMunicipio> getGerMunicipio();
	public List<GerMunicipio> getGerMunicipioListWithPcdsEstacaoByUF(String uf, Integer idUsuarioManuts);
        public int verificarMunicipio(String codigoIbge);                 
}
