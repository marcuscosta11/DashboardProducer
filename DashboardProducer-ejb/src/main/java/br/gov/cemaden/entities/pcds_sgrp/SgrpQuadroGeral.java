package br.gov.cemaden.entities.pcds_sgrp;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;

/**
 *@author marcus.costa
 * 05/06/2019
 */
@Entity
@Table(name = "sgrp_quadro_geral", schema = "pcds_sgrp")
public class SgrpQuadroGeral implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SGRP_QUADRO_GERAL_GENERATOR", sequenceName = "pcds_sgrp.seq_sgrp_quadro_geral", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGRP_QUADRO_GERAL_GENERATOR")
    @Column(name = "id_quadro_geral")
    
    private Integer idQuadroGeral;

    private Timestamp datahora;

    @Column(name = "id_tipoestacao")
    private Integer idTipoestacao;

    @Column(name = "qtd_desabilitada")
    private Integer qtdDesabilitada;

    @Column(name = "qtd_inativa")
    private Integer qtdInativa;

    @Column(name = "qtd_operacional")
    private Integer qtdOperacional;

    @Column(name = "qtd_suspeita")
    private Integer qtdSuspeita;

    @Column(name = "total_uf")
    private Integer totalUf;

    private String uf;

    public SgrpQuadroGeral() {
    }

    public Integer getIdQuadroGeral() {
        return this.idQuadroGeral;
    }

    public void setIdQuadroGeral(Integer idQuadroGeral) {
        this.idQuadroGeral = idQuadroGeral;
    }

    public Timestamp getDatahora() {
        return this.datahora;
    }

    public void setDatahora(Timestamp datahora) {
        this.datahora = datahora;
    }

    public Integer getIdTipoestacao() {
        return this.idTipoestacao;
    }

    public void setIdTipoestacao(Integer idTipoestacao) {
        this.idTipoestacao = idTipoestacao;
    }

    public Integer getQtdDesabilitada() {
        return this.qtdDesabilitada;
    }

    public void setQtdDesabilitada(Integer qtdDesabilitada) {
        this.qtdDesabilitada = qtdDesabilitada;
    }

    public Integer getQtdInativa() {
        return this.qtdInativa;
    }

    public void setQtdInativa(Integer qtdInativa) {
        this.qtdInativa = qtdInativa;
    }

    public Integer getQtdOperacional() {
        return this.qtdOperacional;
    }

    public void setQtdOperacional(Integer qtdOperacional) {
        this.qtdOperacional = qtdOperacional;
    }

    public Integer getQtdSuspeita() {
        return this.qtdSuspeita;
    }

    public void setQtdSuspeita(Integer qtdSuspeita) {
        this.qtdSuspeita = qtdSuspeita;
    }

    public Integer getTotalUf() {
        return this.totalUf;
    }

    public void setTotalUf(Integer totalUf) {
        this.totalUf = totalUf;
    }

    public String getUf() {
        return this.uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

}
