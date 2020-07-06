package br.gov.cemaden.daos.pcds_sgrp;

import br.gov.cemaden.interfaces.pcds_sgrp.SgrpQuadroGeralDAOLocal;

import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import br.gov.cemaden.daos.GenericDAOClass;
import br.gov.cemaden.entities.pcds_sgrp.SgrpQuadroGeral;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import org.json.JSONObject;

@Stateless
public class SgrpQuadroGeralDAO extends GenericDAOClass<SgrpQuadroGeral> implements SgrpQuadroGeralDAOLocal {

    public SgrpQuadroGeralDAO() {
        super(SgrpQuadroGeral.class);
    }

    @Override
    public BigDecimal getTotalnativa() {
        String select = "SELECT sum(qtd_inativa) FROM ( SELECT uf , SUM(inativa) qtd_inativa\n" +
                "FROM (SELECT uf.uf uf , CASE WHEN estado = 3 THEN QTD ELSE 0 END inativa \n" +
                "FROM (SELECT DISTINCT munic.uf uf FROM ger_municipio munic) uf INNER JOIN (SELECT COUNT(1) qtd,\n" +
                "CASE WHEN pcd.habilitada = TRUE THEN pcd.id_estadopcd ELSE 0 END estado , munic.uf uf FROM pcds_sgrp.sgrp_pcd pcd ,\n" +
                "pcds.pcds_estacao estacao, ger_municipio munic WHERE munic.id_municipio = estacao.id_municipio AND pcd.id_estacao = estacao.id_estacao\n" +
                "GROUP BY CASE WHEN pcd.habilitada = TRUE THEN pcd.id_estadopcd ELSE 0 END , munic.uf) pcd ON (uf.uf = pcd.uf) ORDER BY uf )\n" +
                "pcd_uf_estado GROUP BY uf ) pcd_uf;";
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery(select);
        Object qtd = query.getSingleResult();
        return (BigDecimal) qtd;
    }

    @Override
    public void getTotalPcdsQuadroGeralRede() {
        JSONObject json = new  JSONObject();
        int[] tipoEstacao;
        
        tipoEstacao = new int[6];        
        tipoEstacao[0] = 0; //TOTAL_PCDS 
        tipoEstacao[1] = 1; //PLUVIO 
        tipoEstacao[2] = 3; //HIDRO
        tipoEstacao[3] = 4; //AGRO
        tipoEstacao[4] = 5; //ACQUA
        tipoEstacao[5] = 10; //GEO
        
        String[] tipo;
        tipo = new String[6];
        tipo[0] = "totalPcds";
        tipo[1] = "pluvio";
        tipo[2] = "hidro";
        tipo[3] = "agro";
        tipo[4] = "acqua";
        tipo[5] = "geo";
                             
        for (int a = 0; a < tipoEstacao.length; a++) {
            String select = "SELECT sum(qtd_operacional)as operacional ,sum(qtd_suspeita) as suspeita, sum(qtd_inativa) as inativa, sum(qtd_desabilitada) as desabilitada, "
                    + "sum(qtd_operacional + qtd_suspeita + qtd_inativa + qtd_desabilitada) as total  "
                    + "FROM ( SELECT uf , SUM(operacional) qtd_operacional , SUM(suspeita) qtd_suspeita , SUM(inativa) qtd_inativa , SUM(desabilitada) qtd_desabilitada\n"
                    + "FROM (SELECT uf.uf uf , CASE WHEN estado = 1 THEN QTD ELSE 0 END operacional , CASE WHEN estado = 2 THEN QTD ELSE 0 END suspeita ,\n"
                    + "CASE WHEN estado = 3 THEN QTD ELSE 0 END inativa , CASE WHEN estado = 0 THEN QTD ELSE 0 END desabilitada\n"
                    + "FROM (SELECT DISTINCT munic.uf uf FROM ger_municipio munic) uf INNER JOIN (SELECT COUNT(1) qtd ,\n"
                    + "CASE WHEN pcd.habilitada = TRUE THEN pcd.id_estadopcd ELSE 0 END estado , munic.uf uf FROM pcds_sgrp.sgrp_pcd pcd ,\n"
                    + "pcds.pcds_estacao estacao, ger_municipio munic WHERE munic.id_municipio = estacao.id_municipio AND pcd.id_estacao = estacao.id_estacao\n";                    
                    if(tipoEstacao[a] != 0){
                        select += "AND estacao.id_rede = 11 AND estacao.id_tipoestacao = '" + tipoEstacao[a] + "'";
                    }
                    select +=  "GROUP BY CASE WHEN pcd.habilitada = TRUE THEN pcd.id_estadopcd ELSE 0 END , munic.uf) pcd ON (uf.uf = pcd.uf) ORDER BY uf )\n"
                    + "pcd_uf_estado GROUP BY uf ) pcd_uf;";
            EntityManager em = getEntityManager();
            Query query = em.createNativeQuery(select);
            Object qtdPcdsQuadroGeralRede = query.getResultList();
            json.put(tipo[a], qtdPcdsQuadroGeralRede);
        }
        File file = new File("total_pcds_quadro_geral_rede.json");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(json.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BigDecimal getQtdPcdsInativas(int tipoEstacao) {
        String select = "SELECT sum(qtd_inativa) as pluvio_inativa\n"
                + "FROM ( SELECT uf , SUM(inativa) qtd_inativa \n"
                + "FROM (SELECT uf.uf uf , CASE WHEN estado = 3 THEN QTD ELSE 0 END inativa\n"
                + "FROM (SELECT DISTINCT munic.uf uf FROM ger_municipio munic) uf INNER JOIN (SELECT COUNT(1) qtd ,\n"
                + "CASE WHEN pcd.habilitada = TRUE THEN pcd.id_estadopcd ELSE 0 END estado , munic.uf uf FROM pcds_sgrp.sgrp_pcd pcd ,\n"
                + "pcds.pcds_estacao estacao, ger_municipio munic WHERE munic.id_municipio = estacao.id_municipio AND pcd.id_estacao = estacao.id_estacao\n"
                + "AND estacao.id_rede = 11 AND estacao.id_tipoestacao = '" + tipoEstacao + "'\n"
                + "GROUP BY CASE WHEN pcd.habilitada = TRUE THEN pcd.id_estadopcd ELSE 0 END , munic.uf) pcd ON (uf.uf = pcd.uf) ORDER BY uf )\n"
                + "pcd_uf_estado GROUP BY uf ) pcd_uf;";
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery(select);
        Object qtd = query.getSingleResult();
        return (BigDecimal) qtd;
    }
    
    @Override
    public void getQtdChipsOperadora(){  
        
        JSONObject json = new  JSONObject();
        int[] tipoEstacao;
        
        tipoEstacao = new int[6];        
        tipoEstacao[0] = 0; //TOTAL_PCDS 
        tipoEstacao[1] = 1; //PLUVIO 
        tipoEstacao[2] = 3; //HIDRO
        tipoEstacao[3] = 4; //AGRO
        tipoEstacao[4] = 5; //ACQUA
        tipoEstacao[5] = 10; //GEO
        
        String[] tipo;
        tipo = new String[6];
        tipo[0] = "totalPcds";
        tipo[1] = "pluvio";
        tipo[2] = "hidro";
        tipo[3] = "agro";
        tipo[4] = "acqua";
        tipo[5] = "geo";
        
        for (int a = 0; a < tipoEstacao.length; a++) {
            String select = "WITH sim_card as "
                    + "(SELECT pcd.id_pcd, (SELECT fb.fabricante \n"
                    + "FROM  pcds_sgrp.sgrp_item_pcd item \n"
                    + "INNER JOIN pcds_sgrp.sgrp_fabricante_componente fb ON (fb.id_fabricante_componente = item.id_fabricante_componente)\n"
                    + "WHERE item.id_pcd = pcd.id_pcd AND fb.id_tipo_componente = 16\n"
                    + "ORDER BY id_item_pcd desc limit 1) as fabricante      \n"
                    + "FROM pcds_sgrp.sgrp_pcd pcd INNER JOIN pcds.pcds_estacao estacao ON (pcd.id_estacao = estacao.id_estacao)\n"
                    + "WHERE pcd.habilitada = TRUE AND pcd.id_estadopcd = 3 ";
            if (tipoEstacao[a] != 0) {
                select += "AND estacao.id_tipoestacao = '" + tipoEstacao[a] + "'\n";
            }
            select += "order by 2 )select count(id_pcd), fabricante from sim_card group by fabricante order by fabricante asc";
            EntityManager em = getEntityManager();
            Query query = em.createNativeQuery(select);
            Object qtdChipsInativos = query.getResultList();
            json.put(tipo[a], qtdChipsInativos);
        }
        File file = new File("lista_operadoras_chips_inativos.json");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(json.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void getListPcdsInativasOperadora() {
        JSONObject json = new JSONObject();
        int[] tipoEstacao;
        tipoEstacao = new int[5];
        tipoEstacao[0] = 1; //PLUVIO 
        tipoEstacao[1] = 3; //HIDRO
        tipoEstacao[2] = 4; //AGRO
        tipoEstacao[3] = 5; //ACQUA
        tipoEstacao[4] = 10; //GEO

        String[] tipo;
        tipo = new String[5];
        tipo[0] = "pluvio";
        tipo[1] = "hidro";
        tipo[2] = "agro";
        tipo[3] = "acqua";
        tipo[4] = "geo";

        String select = null;
        for (int a = 0; a < tipoEstacao.length; a++) {
            select = "SELECT pcd.id_pcd ,estacao.id_estacao,estacao.nome,\n" +
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
                    "estacao.id_tipoestacao = '"+ tipoEstacao[a] +"'\n" +
                    "AND estacao.id_rede = 11\n" +
                    "AND pcd.habilitada = TRUE\n" +
                    "AND pcd.id_estadopcd = 3;";
            EntityManager em = getEntityManager();
            Query query = em.createNativeQuery(select);
            Object listPcdsInativasOperadoras = query.getResultList();
            json.put(tipo[a], listPcdsInativasOperadoras);

        }
        File file = new File("lista_pcds_inativas_operadora_vivo.json");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(json.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    @Override
    public void getListRedePcdsByUf() {
        JSONObject json = new JSONObject();
        int[] tipoEstacao;
        tipoEstacao = new int[6];        
        tipoEstacao[0] = 0; //TOTAL_PCDS 
        tipoEstacao[1] = 1; //PLUVIO 
        tipoEstacao[2] = 3; //HIDRO
        tipoEstacao[3] = 4; //AGRO
        tipoEstacao[4] = 5; //ACQUA
        tipoEstacao[5] = 10; //GEO

        String[] tipo;
        tipo = new String[6];
        tipo[0] = "totalPcds";
        tipo[1] = "pluvio";
        tipo[2] = "hidro";
        tipo[3] = "agro";
        tipo[4] = "acqua";
        tipo[5] = "geo";
        for (int a = 0; a < tipoEstacao.length; a++) {
            String select = "SELECT uf, qtd_operacional, qtd_suspeita, qtd_inativa, qtd_desabilitada, qtd_operacional + qtd_suspeita + qtd_inativa + qtd_desabilitada total \n"
                    + "FROM ( SELECT uf , SUM(operacional) qtd_operacional , SUM(suspeita) qtd_suspeita , SUM(inativa) qtd_inativa , SUM(desabilitada) qtd_desabilitada \n"
                    + "FROM (SELECT uf.uf uf , CASE WHEN estado = 1 THEN QTD ELSE 0 END operacional , CASE WHEN estado = 2 THEN QTD ELSE 0 END suspeita ,\n"
                    + "CASE WHEN estado = 3 THEN QTD ELSE 0 END inativa , CASE WHEN estado = 0 THEN QTD ELSE 0 END desabilitada \n"
                    + "FROM (SELECT DISTINCT munic.uf uf FROM ger_municipio munic) uf INNER JOIN (SELECT COUNT(1) qtd , \n"
                    + "CASE WHEN pcd.habilitada = TRUE THEN pcd.id_estadopcd ELSE 0 END estado , munic.uf uf FROM pcds_sgrp.sgrp_pcd pcd , \n"
                    + "pcds.pcds_estacao estacao, ger_municipio munic WHERE munic.id_municipio = estacao.id_municipio AND pcd.id_estacao = estacao.id_estacao\n";                    
                    if(tipoEstacao[a] != 0){
                        select += "AND estacao.id_rede = 11 AND estacao.id_tipoestacao = '" + tipoEstacao[a] + "'";
                    }
                    select +=  "GROUP BY CASE WHEN pcd.habilitada = TRUE THEN pcd.id_estadopcd ELSE 0 END , munic.uf) pcd ON (uf.uf = pcd.uf) ORDER BY uf )\n"
                    + "pcd_uf_estado GROUP BY uf ) pcd_uf;";                   
            EntityManager em = getEntityManager();
            Query query = em.createNativeQuery(select);
            Object qtdPcdsQuadroGeralRede = query.getResultList();
            json.put(tipo[a], qtdPcdsQuadroGeralRede);
        }

        File file = new File("lista_rede_pcds_uf.json");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(json.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getListPcdsInativas() {        
        JSONObject json = new JSONObject();
        BigDecimal x = null;         
        int[] tipoEstacao;
        tipoEstacao = new int[6];        
        tipoEstacao[0] = 0; //TOTAL_PCDS 
        tipoEstacao[1] = 1; //PLUVIO 
        tipoEstacao[2] = 3; //HIDRO
        tipoEstacao[3] = 4; //AGRO
        tipoEstacao[4] = 5; //ACQUA
        tipoEstacao[5] = 10; //GEO

        String[] tipo;
        tipo = new String[6];
        tipo[0] = "totalPcds";
        tipo[1] = "pluvio";
        tipo[2] = "hidro";
        tipo[3] = "agro";
        tipo[4] = "acqua";
        tipo[5] = "geo";
        
        for (int a = 0; a < tipoEstacao.length; a++) {
//        String select = "SELECT datahora,SUM(qtd_inativa) AS inativa\n"
//                + "FROM pcds_sgrp.sgrp_quadro_geral\n"
//                + "WHERE datahora BETWEEN CURRENT_DATE - INTEGER '60' AND CURRENT_DATE\n";
//                if(tipoEstacao[a] != 0){
//                    select +=  "AND id_tipoestacao = '" + tipoEstacao[a] + "'";
//                }                
//                select += "GROUP BY datahora ORDER BY datahora;";
        
        String select2 = "SELECT datahora,SUM(inativa) AS inativa\n"
                + "FROM pcds_sgrp.sgrp_disponibilidade_rede\n"
                + "WHERE datahora BETWEEN CURRENT_DATE - INTEGER '30' AND CURRENT_DATE\n";
                if(tipoEstacao[a] != 0){
                    select2 +=  "AND id_tipoestacao = '" + tipoEstacao[a] + "'";
                }                
                select2 += "GROUP BY datahora ORDER BY datahora;";        
                
                
        EntityManager em = getEntityManager();
            Query query = em.createNativeQuery(select2);
            List qtdPcdsInatividadeRede = query.getResultList();
            if(tipoEstacao[a] != 0){
                x = getQtdPcdsInativas(tipoEstacao[a]);
            }else{
                x = getTotalnativa();
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            SgrpQuadroGeral i = new SgrpQuadroGeral();
             
            i.setDatahora(timestamp);
            i.setQtdInativa(x.intValue());
            qtdPcdsInatividadeRede.add(i);
            json.put(tipo[a], qtdPcdsInatividadeRede);           
        }

        File file = new File("lista_rede_pcds_inativas.json");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(json.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
