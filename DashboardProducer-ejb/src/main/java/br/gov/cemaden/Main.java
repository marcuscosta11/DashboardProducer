package br.gov.cemaden;

import br.gov.cemaden.interfaces.pcds.PcdsEstacaoDAOLocal;
import br.gov.cemaden.interfaces.pcds_sgrp.SgrpQuadroGeralDAOLocal;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

@Stateless
public class Main {

    @EJB
    private PcdsEstacaoDAOLocal pcdsEstacaoDAOLocal;
    @EJB
    private SgrpQuadroGeralDAOLocal sgrpQuadroGeralDAOLocal;

    Date data = new Date();
    SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

//    @Schedule(hour = "*", minute = "*/2", second = "*/59", dayOfWeek = "*", year = "*", persistent = false)
    @Schedule(minute = "*/7", hour = "*", persistent = false)
    public void perform() throws IOException, InterruptedException {

        System.out.println("<<< DashBoardProducer 1.0 >>>");
        System.out.println("<<< Iniciando em: " + formatador.format(data) + " >>>");
        System.out.println("<<< Gerando arquivos JSON >>>");   
                
        sgrpQuadroGeralDAOLocal.getTotalPcdsQuadroGeralRede();               
        sgrpQuadroGeralDAOLocal.getListRedePcdsByUf();        
        sgrpQuadroGeralDAOLocal.getListPcdsInativas();
        sgrpQuadroGeralDAOLocal.getQtdChipsOperadora();
        sgrpQuadroGeralDAOLocal.getListPcdsInativasOperadora();
        
        /*DADOS APRESENTADOS NOS MAPAS*/        
        /*geral*/
        pcdsEstacaoDAOLocal.getStatusPcdsQuadroGeral();        
        /*pluvio*/
        pcdsEstacaoDAOLocal.getStatusPcdsPluvio();
        /*acqua*/
        pcdsEstacaoDAOLocal.getStatusPcdsAcqua();
        /*hidro*/
        pcdsEstacaoDAOLocal.getStatusPcdsHidro();
        /*agro*/
        pcdsEstacaoDAOLocal.getStatusPcdsAgro();
        /*geo*/
        pcdsEstacaoDAOLocal.getStatusPcdsGeo();        
        /*DADOS APRESENTADOS NOS MAPAS*/        
        System.out.println("Arquivos Json gerados/atualizados");

    }
}
