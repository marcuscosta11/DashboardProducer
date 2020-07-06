package br.gov.cemaden.daos.pcds;

import br.gov.cemaden.interfaces.pcds.PcdsEstacaoDAOLocal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import br.gov.cemaden.daos.GenericDAOClass;
import br.gov.cemaden.entities.pcds.PcdsEstacao;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

@Stateless
public class PcdsEstacaoDAO extends GenericDAOClass<PcdsEstacao> implements
         PcdsEstacaoDAOLocal {    
    
    public PcdsEstacaoDAO() {
        super(PcdsEstacao.class);
    } 
    
    /*pluvio*/
    @Override
    public void getStatusPcdsPluvio() { 
        JSONObject json = new JSONObject();
        int [] estadoEstacao;
        estadoEstacao = new  int[4];
        estadoEstacao[0] = 1; /*ativas*/
        estadoEstacao[1] = 2; /*suspeitas*/
        estadoEstacao[2] = 3; /*inativas*/       
        estadoEstacao[3] = 3; /*desabilitadas - a condição habilitadas recebe o valor false*/               
        String[] status; 
        boolean pcdHabilitada = true;
        status = new String[4];
        status[0] = "PLUVIO_ATIVA";
        status[1] = "PLUVIO_SUSPEITA";
        status[2] = "PLUVIO_INATIVA";
        status[3] = "PLUVIO_DESABILITADA";
        
        for (int a = 0; a < estadoEstacao.length; a++) {
            if(a == 3){pcdHabilitada = false;}
            Query query = this.getEntityManager().createNativeQuery("SELECT pcd.id_pcd ,estacao.id_estacao,estacao.nome,\n"
                    + "estacao.codestacao,estacao.latitude, estacao.longitude,uf.uf,uf.cidade,pcd.dh_ultima_remessa,\n"
                    + "(SELECT fab.fabricante FROM pcds_sgrp.sgrp_item_pcd AS item \n"
                    + "INNER JOIN pcds_sgrp.sgrp_fabricante_componente AS fab \n"
                    + "ON fab.id_fabricante_componente = item.id_fabricante_componente AND id_pcd = pcd.id_pcd AND fab.id_tipo_componente = 16                        \n"
                    + "ORDER BY id_item_pcd DESC\n"
                    + "LIMIT 1) as fabricante,\n"
                    + "(SELECT fab.modelo FROM pcds_sgrp.sgrp_item_pcd AS item \n"
                    + "INNER JOIN pcds_sgrp.sgrp_fabricante_componente AS fab \n"
                    + "ON fab.id_fabricante_componente = item.id_fabricante_componente AND id_pcd = pcd.id_pcd AND fab.id_tipo_componente = 16\n"
                    + "ORDER BY id_item_pcd DESC\n"
                    + "LIMIT 1) as modelo,\n"
                    + "(SELECT item.numero_serie FROM pcds_sgrp.sgrp_item_pcd AS item \n"
                    + "INNER JOIN pcds_sgrp.sgrp_fabricante_componente AS fab \n"
                    + "ON fab.id_fabricante_componente = item.id_fabricante_componente AND id_pcd = pcd.id_pcd AND fab.id_tipo_componente = 16\n"
                    + "ORDER BY id_item_pcd DESC\n"
                    + "LIMIT 1) as numero_serie\n"
                    + "FROM \n"
                    + "pcds_sgrp.sgrp_pcd pcd\n"
                    + "INNER JOIN pcds.pcds_estacao AS estacao ON(pcd.id_estacao = estacao.id_estacao)\n"
                    + "INNER JOIN public.ger_municipio AS uf ON (estacao.id_municipio = uf.id_municipio)\n"
                    + "WHERE\n"
                    + "estacao.id_tipoestacao = 1\n"
                    + "AND estacao.id_rede = 11\n"
                    + "AND pcd.habilitada =  '" + pcdHabilitada + "'\n"
                    + "AND pcd.id_estadopcd ='" + estadoEstacao[a] + "';");
            List listStatusPluvio = query.getResultList();
            json.put(status[a], listStatusPluvio);
        }
        File file = new File("status_pcds_pluvio.json");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(json.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
    /*pluvio*/
    
    /*acqua*/
    @Override
    public void getStatusPcdsAcqua() {        
        JSONObject json = new JSONObject();
        int [] estadoEstacao;
        estadoEstacao = new  int[4];
        estadoEstacao[0] = 1; /*ativas*/
        estadoEstacao[1] = 2; /*suspeitas*/
        estadoEstacao[2] = 3; /*inativas*/       
        estadoEstacao[3] = 3; /*desabilitadas - a condição habilitadas recebe o valor false*/               
        String[] status; 
        boolean pcdHabilitada = true;
        status = new String[4];
        status[0] = "ACQUA_ATIVA";
        status[1] = "ACQUA_SUSPEITA";
        status[2] = "ACQUA_INATIVA";
        status[3] = "ACQUA_DESABILITADA";
        
        for (int a = 0; a < estadoEstacao.length; a++) {
            if(a == 3){pcdHabilitada = false;}                        
            Query query = this.getEntityManager().createNativeQuery("SELECT pcd.id_pcd ,estacao.id_estacao,estacao.nome,\n"
                    + "estacao.codestacao,estacao.latitude, estacao.longitude,uf.uf,uf.cidade,pcd.dh_ultima_remessa,\n"
                    + "(SELECT fab.fabricante FROM pcds_sgrp.sgrp_item_pcd AS item \n"
                    + "INNER JOIN pcds_sgrp.sgrp_fabricante_componente AS fab \n"
                    + "ON fab.id_fabricante_componente = item.id_fabricante_componente AND id_pcd = pcd.id_pcd AND fab.id_tipo_componente = 16                        \n"
                    + "ORDER BY id_item_pcd DESC\n"
                    + "LIMIT 1) as fabricante,\n"
                    + "(SELECT fab.modelo FROM pcds_sgrp.sgrp_item_pcd AS item \n"
                    + "INNER JOIN pcds_sgrp.sgrp_fabricante_componente AS fab \n"
                    + "ON fab.id_fabricante_componente = item.id_fabricante_componente AND id_pcd = pcd.id_pcd AND fab.id_tipo_componente = 16\n"
                    + "ORDER BY id_item_pcd DESC\n"
                    + "LIMIT 1) as modelo,\n"
                    + "(SELECT item.numero_serie FROM pcds_sgrp.sgrp_item_pcd AS item \n"
                    + "INNER JOIN pcds_sgrp.sgrp_fabricante_componente AS fab \n"
                    + "ON fab.id_fabricante_componente = item.id_fabricante_componente AND id_pcd = pcd.id_pcd AND fab.id_tipo_componente = 16\n"
                    + "ORDER BY id_item_pcd DESC\n"
                    + "LIMIT 1) as numero_serie\n"
                    + "FROM \n"
                    + "pcds_sgrp.sgrp_pcd pcd\n"
                    + "INNER JOIN pcds.pcds_estacao AS estacao ON(pcd.id_estacao = estacao.id_estacao)\n"
                    + "INNER JOIN public.ger_municipio AS uf ON (estacao.id_municipio = uf.id_municipio)\n"
                    + "WHERE\n"
                    + "estacao.id_tipoestacao = 5\n"
                    + "AND estacao.id_rede = 11\n"
                    + "AND pcd.habilitada =  '" + pcdHabilitada + "'\n"
                    + "AND pcd.id_estadopcd ='" + estadoEstacao[a] + "';");
         List listStatusAcqua = query.getResultList();            
            json.put(status[a], listStatusAcqua);        
        }                
        File file = new File("status_pcds_acqua.json");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(json.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*acqua*/
        
    /*hidro*/
    @Override
    public void getStatusPcdsHidro() {        
        JSONObject json = new JSONObject();
        int [] estadoEstacao;
        estadoEstacao = new  int[4];
        estadoEstacao[0] = 1; /*ativas*/
        estadoEstacao[1] = 2; /*suspeitas*/
        estadoEstacao[2] = 3; /*inativas*/       
        estadoEstacao[3] = 3; /*desabilitadas - a condição habilitadas recebe o valor false*/               
        String[] status; 
        boolean pcdHabilitada = true;
        status = new String[4];
        status[0] = "HIDRO_ATIVA";
        status[1] = "HIDRO_SUSPEITA";
        status[2] = "HIDRO_INATIVA";
        status[3] = "HIDRO_DESABILITADA";
        
        for (int a = 0; a < estadoEstacao.length; a++) {
            if(a == 3){pcdHabilitada = false;}                        
            Query query = this.getEntityManager().createNativeQuery("SELECT pcd.id_pcd ,estacao.id_estacao,estacao.nome,\n"
                    + "estacao.codestacao,estacao.latitude, estacao.longitude,uf.uf,uf.cidade,pcd.dh_ultima_remessa,\n"
                    + "(SELECT fab.fabricante FROM pcds_sgrp.sgrp_item_pcd AS item \n"
                    + "INNER JOIN pcds_sgrp.sgrp_fabricante_componente AS fab \n"
                    + "ON fab.id_fabricante_componente = item.id_fabricante_componente AND id_pcd = pcd.id_pcd AND fab.id_tipo_componente = 16                        \n"
                    + "ORDER BY id_item_pcd DESC\n"
                    + "LIMIT 1) as fabricante,\n"
                    + "(SELECT fab.modelo FROM pcds_sgrp.sgrp_item_pcd AS item \n"
                    + "INNER JOIN pcds_sgrp.sgrp_fabricante_componente AS fab \n"
                    + "ON fab.id_fabricante_componente = item.id_fabricante_componente AND id_pcd = pcd.id_pcd AND fab.id_tipo_componente = 16\n"
                    + "ORDER BY id_item_pcd DESC\n"
                    + "LIMIT 1) as modelo,\n"
                    + "(SELECT item.numero_serie FROM pcds_sgrp.sgrp_item_pcd AS item \n"
                    + "INNER JOIN pcds_sgrp.sgrp_fabricante_componente AS fab \n"
                    + "ON fab.id_fabricante_componente = item.id_fabricante_componente AND id_pcd = pcd.id_pcd AND fab.id_tipo_componente = 16\n"
                    + "ORDER BY id_item_pcd DESC\n"
                    + "LIMIT 1) as numero_serie\n"
                    + "FROM \n"
                    + "pcds_sgrp.sgrp_pcd pcd\n"
                    + "INNER JOIN pcds.pcds_estacao AS estacao ON(pcd.id_estacao = estacao.id_estacao)\n"
                    + "INNER JOIN public.ger_municipio AS uf ON (estacao.id_municipio = uf.id_municipio)\n"
                    + "WHERE\n"
                    + "estacao.id_tipoestacao = 3\n"
                    + "AND estacao.id_rede = 11\n"
                    + "AND pcd.habilitada =  '" + pcdHabilitada + "'\n"
                    + "AND pcd.id_estadopcd ='" + estadoEstacao[a] + "';");
         List listStatusHidro = query.getResultList();            
            json.put(status[a], listStatusHidro);        
        }                
        File file = new File("status_pcds_hidro.json");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(json.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    /*hidro*/
    
    /*agro*/
    @Override
    public void getStatusPcdsAgro() {
        JSONObject json = new JSONObject();
        int [] estadoEstacao;
        estadoEstacao = new  int[4];
        estadoEstacao[0] = 1; /*ativas*/
        estadoEstacao[1] = 2; /*suspeitas*/
        estadoEstacao[2] = 3; /*inativas*/       
        estadoEstacao[3] = 3; /*desabilitadas - a condição habilitadas recebe o valor false*/               
        String[] status; 
        boolean pcdHabilitada = true;
        status = new String[4];
        status[0] = "AGRO_ATIVA";
        status[1] = "AGRO_SUSPEITA";
        status[2] = "AGRO_INATIVA";
        status[3] = "AGRO_DESABILITADA";
        
        for (int a = 0; a < estadoEstacao.length; a++) {
            if(a == 3){pcdHabilitada = false;}                                    
                Query query = this.getEntityManager().createNativeQuery("SELECT pcd.id_pcd ,estacao.id_estacao,estacao.nome,\n"
                    + "estacao.codestacao,estacao.latitude, estacao.longitude,uf.uf,uf.cidade,pcd.dh_ultima_remessa,\n"
                    + "(SELECT fab.fabricante FROM pcds_sgrp.sgrp_item_pcd AS item \n"
                    + "INNER JOIN pcds_sgrp.sgrp_fabricante_componente AS fab \n"
                    + "ON fab.id_fabricante_componente = item.id_fabricante_componente AND id_pcd = pcd.id_pcd AND fab.id_tipo_componente = 16                        \n"
                    + "ORDER BY id_item_pcd DESC\n"
                    + "LIMIT 1) as fabricante,\n"
                    + "(SELECT fab.modelo FROM pcds_sgrp.sgrp_item_pcd AS item \n"
                    + "INNER JOIN pcds_sgrp.sgrp_fabricante_componente AS fab \n"
                    + "ON fab.id_fabricante_componente = item.id_fabricante_componente AND id_pcd = pcd.id_pcd AND fab.id_tipo_componente = 16\n"
                    + "ORDER BY id_item_pcd DESC\n"
                    + "LIMIT 1) as modelo,\n"
                    + "(SELECT item.numero_serie FROM pcds_sgrp.sgrp_item_pcd AS item \n"
                    + "INNER JOIN pcds_sgrp.sgrp_fabricante_componente AS fab \n"
                    + "ON fab.id_fabricante_componente = item.id_fabricante_componente AND id_pcd = pcd.id_pcd AND fab.id_tipo_componente = 16\n"
                    + "ORDER BY id_item_pcd DESC\n"
                    + "LIMIT 1) as numero_serie\n"
                    + "FROM \n"
                    + "pcds_sgrp.sgrp_pcd pcd\n"
                    + "INNER JOIN pcds.pcds_estacao AS estacao ON(pcd.id_estacao = estacao.id_estacao)\n"
                    + "INNER JOIN public.ger_municipio AS uf ON (estacao.id_municipio = uf.id_municipio)\n"
                    + "WHERE\n"
                    + "estacao.id_tipoestacao = 4\n"
                    + "AND estacao.id_rede = 11\n"
                    + "AND pcd.habilitada =  '" + pcdHabilitada + "'\n"
                    + "AND pcd.id_estadopcd ='" + estadoEstacao[a] + "';");
         List listStatusAgro = query.getResultList();            
            json.put(status[a], listStatusAgro);        
        }                
        File file = new File("status_pcds_agro.json");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(json.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*agro*/
   
    /*geral*/
    @Override
    public void getStatusPcdsQuadroGeral() {
        JSONObject json = new JSONObject();
        int [] estadoEstacao;
        estadoEstacao = new  int[4];
        estadoEstacao[0] = 1; /*ativas*/
        estadoEstacao[1] = 2; /*suspeitas*/
        estadoEstacao[2] = 3; /*inativas*/       
        estadoEstacao[3] = 3; /*desabilitadas - a condição habilitadas recebe o valor false*/               
        String[] status; 
        boolean pcdHabilitada = true;
        status = new String[4];
        status[0] = "PCD_ATIVA";
        status[1] = "PCD_SUSPEITA";
        status[2] = "PCD_INATIVA";
        status[3] = "PCD_DESABILITADA";
        
        for (int a = 0; a < estadoEstacao.length; a++) {
            if(a == 3){pcdHabilitada = false;}                        
            Query query = this.getEntityManager().createNativeQuery("SELECT pcd.id_pcd, estacao.id_estacao, pcd.dh_ultima_remessa ,estacao.nome, tipo.descricao ,estacao.codestacao,estacao.latitude, estacao.longitude, uf.uf, uf.cidade\n"
                    + "FROM pcds_sgrp.sgrp_pcd pcd  \n"
                    + "LEFT JOIN pcds.pcds_estacao as estacao ON(pcd.id_estacao = estacao.id_estacao)\n"
                    + "LEFT JOIN public.ger_municipio as uf ON (estacao.id_municipio = uf.id_municipio)\n"
                    + "LEFT JOIN pcds.pcds_tipoestacao as tipo ON (estacao.id_tipoestacao = tipo.id_tipoestacao)\n"
                    + "WHERE estacao.id_rede = 11 \n"
                    + "AND pcd.habilitada = '" + pcdHabilitada + "' AND pcd.id_estadopcd = '" + estadoEstacao[a] + "';");         
         List listStatusPcdsQuadroGeral = query.getResultList();            
            json.put(status[a], listStatusPcdsQuadroGeral);        
        }                
        File file = new File("status_pcds_quadro_geral.json");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(json.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }                                                   
    }
    /*geral*/
    
    /*geo*/
    @Override
    public void getStatusPcdsGeo() {
       JSONObject json = new JSONObject();
        int [] estadoEstacao;
        estadoEstacao = new  int[4];
        estadoEstacao[0] = 1; /*ativas*/
        estadoEstacao[1] = 2; /*suspeitas*/
        estadoEstacao[2] = 3; /*inativas*/       
        estadoEstacao[3] = 3; /*desabilitadas - a condição habilitadas recebe o valor false*/               
        String[] status; 
        boolean pcdHabilitada = true;
        status = new String[4];
        status[0] = "GEO_ATIVA";
        status[1] = "GEO_SUSPEITA";
        status[2] = "GEO_INATIVA";
        status[3] = "GEO_DESABILITADA";
        
        for (int a = 0; a < estadoEstacao.length; a++) {
            if(a == 3){pcdHabilitada = false;}                                   
             Query query = this.getEntityManager().createNativeQuery("SELECT pcd.id_pcd ,estacao.id_estacao,estacao.nome,\n" +
                    "estacao.codestacao,estacao.latitude, estacao.longitude,uf.uf,uf.cidade,pcd.dh_ultima_remessa,\n" +
                    "(SELECT fab.fabricante FROM pcds_sgrp.sgrp_item_pcd AS item \n" +
                    "INNER JOIN pcds_sgrp.sgrp_fabricante_componente AS fab \n" +
                    "ON fab.id_fabricante_componente = item.id_fabricante_componente AND id_pcd = pcd.id_pcd AND fab.id_tipo_componente = 16                        \n" +
                    "ORDER BY id_item_pcd DESC\n" +
                    "LIMIT 1) as fabricante,\n" +
                    "(SELECT fab.modelo FROM pcds_sgrp.sgrp_item_pcd AS item \n" +
                    "INNER JOIN pcds_sgrp.sgrp_fabricante_componente AS fab \n" +
                    "ON fab.id_fabricante_componente = item.id_fabricante_componente AND id_pcd = pcd.id_pcd AND fab.id_tipo_componente = 16\n" +
                    "ORDER BY id_item_pcd DESC\n" +
                    "LIMIT 1) as modelo,\n" +
                    "(SELECT item.numero_serie FROM pcds_sgrp.sgrp_item_pcd AS item \n" +
                    "INNER JOIN pcds_sgrp.sgrp_fabricante_componente AS fab \n" +
                    "ON fab.id_fabricante_componente = item.id_fabricante_componente AND id_pcd = pcd.id_pcd AND fab.id_tipo_componente = 16\n" +
                    "ORDER BY id_item_pcd DESC\n" +
                    "LIMIT 1) as numero_serie\n" +
                    "FROM \n" +
                    "pcds_sgrp.sgrp_pcd pcd\n" +
                    "INNER JOIN pcds.pcds_estacao AS estacao ON(pcd.id_estacao = estacao.id_estacao)\n" +
                    "INNER JOIN public.ger_municipio AS uf ON (estacao.id_municipio = uf.id_municipio)\n" +
                    "WHERE\n" +
                    "estacao.id_tipoestacao = 10\n" +
                    "AND estacao.id_rede = 11\n" +
                    "AND pcd.habilitada =  '" + pcdHabilitada + "'\n" +
                    "AND pcd.id_estadopcd ='" + estadoEstacao[a] + "';");
            
         List listStatusGeo = query.getResultList();            
            json.put(status[a], listStatusGeo);        
        }                
        File file = new File("status_pcds_geo.json");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(json.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*geo*/    
}