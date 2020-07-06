package br.gov.cemaden.daos.publico;

import br.gov.cemaden.interfaces.publico.GerMunicipioDAOLocal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import br.gov.cemaden.daos.GenericDAOClass;
import br.gov.cemaden.entities.publico.GerMunicipio;
import javax.persistence.NoResultException;

/**
 * @author marcus.costa
 * 05/06/2019
 */
@Stateless
public class GerMunicipioDAO extends GenericDAOClass<GerMunicipio> implements GerMunicipioDAOLocal {

    public GerMunicipioDAO() {
        super(GerMunicipio.class);
    }

    @Override
    public List<GerMunicipio> getGerMunicipio() {
        Query query = getEntityManager().createNativeQuery(
                "SELECT * FROM ger_municipio ORDER BY uf, cidade;",
                GerMunicipio.class);
        return query.getResultList();
    }

    @Override
    public List<GerMunicipio> getGerMunicipioListByUF(String uf) {
        TypedQuery<GerMunicipio> typedQuery = getEntityManager()
                .createQuery(
                        "SELECT g FROM GerMunicipio g WHERE g.uf = '" + uf
                        + "' ORDER BY g.cidade ASC", GerMunicipio.class);
        return typedQuery.getResultList();
    }

    @Override
    public List<GerMunicipio> getGerMunicipioListWithPcdsEstacaoByUF(String uf) {
        return getGerMunicipioListWithPcdsEstacaoByUF(uf, null);
    }

    @Override
    public List<GerMunicipio> getGerMunicipioListWithPcdsEstacaoByUF(String uf, Integer idUsuarioManuts) {
        String join = "";
        String where = "";
        if (idUsuarioManuts != null && idUsuarioManuts != 0) {
            join = " LEFT JOIN pcds_sgrp.sgrp_usrpcd ON (pcds_sgrp.sgrp_usrpcd.id_pcd = pcds_sgrp.sgrp_pcd.id_pcd) ";
            where = " and pcds_sgrp.sgrp_usrpcd.id_usuario = " + idUsuarioManuts.intValue();
        }
        Query query = getEntityManager()
                .createNativeQuery(
                        "SELECT DISTINCT (public.ger_municipio.id_municipio), public.ger_municipio.cidade, public.ger_municipio.codibge, public.ger_municipio.uf "
                        + "FROM public.ger_municipio "
                        + "RIGHT JOIN pcds.pcds_estacao ON (pcds.pcds_estacao.id_municipio = public.ger_municipio.id_municipio) "
                        + "RIGHT JOIN pcds_sgrp.sgrp_pcd ON (pcds_sgrp.sgrp_pcd.id_estacao = pcds.pcds_estacao.id_estacao) "
                        + join
                        + "WHERE public.ger_municipio.uf = '"
                        + uf
                        + "'"
                        + where
                        + " ORDER BY public.ger_municipio.cidade ASC",
                        GerMunicipio.class);
        return query.getResultList();
    }

    private String ifNotNull(Object x, String y) {
        if (x != null) {
            return y;
        }
        return "";
    }

    @Override
    public int verificarMunicipio(String codigoIbge) {
        try {
            String select = "SELECT idMunicipio FROM GerMunicipio WHERE codibge = :codibge";
            EntityManager em = getEntityManager();
            Query query = em.createQuery(select);
            query.setParameter("codibge", Integer.parseInt(codigoIbge.substring(0, 7)));
            int idMunicipio = ((Integer) query.getSingleResult());
            if (idMunicipio != 0) {
                return idMunicipio;
            }
        } catch (NoResultException e) {
            System.out.println("*** Exception em -> br.gov.crud.salvar.DadosEstacaoBean.verificaMunicipio(): " + e.getMessage());
        }
        return 0;
    }
}
