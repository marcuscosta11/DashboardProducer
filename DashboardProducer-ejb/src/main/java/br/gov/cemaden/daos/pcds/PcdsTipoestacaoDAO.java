package br.gov.cemaden.daos.pcds;

import br.gov.cemaden.interfaces.pcds.PcdsTipoestacaoDAOLocal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import br.gov.cemaden.daos.GenericDAOClass;
import br.gov.cemaden.entities.pcds.PcdsTipoestacao;

/**
 * @author marcus.costa
 * 05/06/2019
 */
@Stateless
public class PcdsTipoestacaoDAO extends GenericDAOClass<PcdsTipoestacao>implements PcdsTipoestacaoDAOLocal {
 
    public PcdsTipoestacaoDAO() {
        super(PcdsTipoestacao.class);
    }

    @Override
    public List<PcdsTipoestacao> getObject() {
        List<PcdsTipoestacao> result = super.getObject();
        Collections.sort(result);
        return result;
    }

    @Override
    public List<PcdsTipoestacao> getTipoDeEstacaoComTarefaByIdTipoTarManut(Integer idTipoTarManut) {
        try {
            EntityManager em = getEntityManager();
            Query query = em
                    .createNativeQuery("SELECT DISTINCT ON (pcds.pcds_tipoestacao.id_tipoestacao)  pcds.pcds_tipoestacao.id_tipoestacao, pcds.pcds_tipoestacao.descricao FROM pcds.pcds_tipoestacao "
                            + "INNER JOIN pcds.pcds_estacao ON pcds.pcds_estacao.id_tipoestacao = pcds.pcds_tipoestacao.id_tipoestacao "
                            + "INNER JOIN pcds_sgrp.sgrp_pcd ON pcds_sgrp.sgrp_pcd.id_estacao = pcds.pcds_estacao.id_estacao "
                            + "INNER JOIN pcds_sgrp.sgrp_tarefa_manut ON pcds_sgrp.sgrp_tarefa_manut.id_pcd = pcds_sgrp.sgrp_pcd.id_pcd AND pcds_sgrp.sgrp_tarefa_manut.id_tipo_tarefa_manut = "
                            + idTipoTarManut.intValue(), PcdsTipoestacao.class);
            List<PcdsTipoestacao> resultList = query.getResultList();
            return resultList;

        } catch (Exception e) {
            System.out.println("Excecao em PcdsTipoestacaoDAO.getTipoDeEstacaoComTarefaByIdTipoTarManut: "
                    + e.getClass());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<Integer, PcdsTipoestacao> getMapTipoestacao() {
        List<PcdsTipoestacao> tipos = getObject();
        Map<Integer, PcdsTipoestacao> result = new LinkedHashMap<Integer, PcdsTipoestacao>();
        for (PcdsTipoestacao tipo : tipos) {
            result.put(tipo.getIdTipoestacao(), tipo);
        }
        return result;
    }

    @Override
    public Map<String, PcdsTipoestacao> getMapTipoestacaoPorDescricao() {
        List<PcdsTipoestacao> tipos = getObject();
        Map<String, PcdsTipoestacao> result = new LinkedHashMap<>();
        for (PcdsTipoestacao tipo : tipos) {
            result.put(tipo.getDescricao(), tipo);
        }
        return result;
    }

    @Override
    public PcdsTipoestacao getTipoDeEstacaoByIdPcd(Integer idPCD) {
        try {
            EntityManager em = getEntityManager();

            Query query = em
                    .createQuery("SELECT tipo_est FROM SgrpPcd pcd, PcdsEstacao est, PcdsTipoestacao tipo_est WHERE "
                            + "est.idEstacao = pcd.idEstacao AND est.idTipoestacao = tipo_est.idTipoestacao AND pcd.idPcd = :idPcd", PcdsTipoestacao.class);
            query.setParameter("idPcd", idPCD);
            PcdsTipoestacao tipoEstacao = (PcdsTipoestacao) query.getSingleResult();
            return tipoEstacao;

        } catch (Exception e) {
            System.out.println("Excecao em PcdsTipoestacaoDAO.getTipoDeEstacaoByIdPcd: "
                    + e.getClass());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<Integer, List<String>> getIdTipoEstacaoPorModelo() {
        try {
            Query query = this
                    .getEntityManager()
                    .createQuery("SELECT  e.idTipoestacao, p.modelo "
                            + "FROM PcdsEstacao e, SgrpPcd p "
                            + "WHERE e.idEstacao = p.idEstacao");
            List<Object[]> resultList = query.getResultList();
            Map<Integer, List<String>> resultMap = new HashMap<>();
            for (Object[] result : resultList) {
                Integer idEstacao = (Integer) result[0];
                if (!resultMap.containsKey(idEstacao)) {
                    resultMap.put(idEstacao, new ArrayList<String>());
                }
                resultMap.get(idEstacao).add((String) result[1]);
            }
            return resultMap;
        } catch (Exception e) {
            System.out.println("***** Exception em PcdsEstacaoDAO.getIdTipoEstacaoPorModelo: " + e.getMessage());
            return new HashMap<>();
        }
    }

    @Override
    public int verificarTipoEstacao(String tipoModelo, String codigo) {
        tipoModelo = tipoModelo.substring(tipoModelo.indexOf("."), tipoModelo.lastIndexOf("."));
        String tipoTratado = tipoModelo.replace(".", "");
        int idTipoEstacao = 0;

        switch (tipoTratado) {
            case "Pluvio":
            case "PLUVIO":
            case "pluvio":
                tipoTratado = "Pluviométrica";
                break;
            case "Agro":
            case "AGRO":
            case "agro":
                tipoTratado = "Agrometeorológica";
                break;
            case "Acqua":
            case "ACQUA":
            case "acqua":
                tipoTratado = "Acqua";
                break;
            case "Geo":
            case "GEO":
            case "geo":
                tipoTratado = "Geotécnica";
                break;
            case "Hidro":
            case "HIDRO":
            case "hidro":
                tipoTratado = "Hidrológica";
                break;
            default:
                break;
        }
        try {
            String select = "SELECT idTipoestacao FROM PcdsTipoestacao WHERE descricao = :descricao";
            EntityManager em = getEntityManager();
            Query query = em.createQuery(select);
            query.setParameter("descricao", tipoTratado);
            idTipoEstacao = ((Integer) query.getSingleResult());

            if (idTipoEstacao != 0) {
                return idTipoEstacao;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("*** Exception em -> br.gov.crud.salvar.DadosEstacaoBean.verificaTipoEstacao(): " + e.getMessage());
        }
        return idTipoEstacao;
    }

}
