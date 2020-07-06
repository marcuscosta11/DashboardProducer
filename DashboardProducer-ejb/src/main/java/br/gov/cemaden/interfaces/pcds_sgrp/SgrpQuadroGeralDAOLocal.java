package br.gov.cemaden.interfaces.pcds_sgrp;

import br.gov.cemaden.interfaces.GenericDAOInterface;
import javax.ejb.Local;
import br.gov.cemaden.entities.pcds_sgrp.SgrpQuadroGeral;
import java.math.BigDecimal;
import java.util.List;

@Local
public interface SgrpQuadroGeralDAOLocal extends GenericDAOInterface<SgrpQuadroGeral> {
        
    public void getTotalPcdsQuadroGeralRede();
    public void getListRedePcdsByUf();   
    public void getListPcdsInativas();
    public BigDecimal getTotalnativa();
    public BigDecimal getQtdPcdsInativas(int tipoEstacao);
    public void getQtdChipsOperadora();
    public void getListPcdsInativasOperadora();
}
