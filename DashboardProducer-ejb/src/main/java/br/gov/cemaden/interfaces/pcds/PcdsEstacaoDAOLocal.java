package br.gov.cemaden.interfaces.pcds;

import br.gov.cemaden.entities.pcds.PcdsEstacao;
import br.gov.cemaden.interfaces.GenericDAOInterface;
import javax.ejb.Local;

/**
 *
 * @author marcus.costa
 */
@Local
public interface PcdsEstacaoDAOLocal extends GenericDAOInterface<PcdsEstacao> {
    
    /*geral*/
    public void getStatusPcdsQuadroGeral();

    /*pluvio*/
    public void getStatusPcdsPluvio();

    /*acqua*/
    public void getStatusPcdsAcqua();

    /*hidro*/
    public void getStatusPcdsHidro();

    /*agro*/
    public void getStatusPcdsAgro();

    /*geo*/
    public void getStatusPcdsGeo();                
        
}