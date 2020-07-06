package br.gov.cemaden.entities.pcds;

import br.gov.cemaden.entities.publico.GerMunicipio;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author marcus.costa
 * 05/06/2019
 */

@Entity
@Table(name = "pcds_estacao", schema = "pcds")
public class PcdsEstacao implements Serializable{
    
     private static final long serialVersionUID = 1L;
     
    @Id
    @SequenceGenerator(name = "PCD_ESTACAO_IDESTACAO_GENERATOR", sequenceName = "pcds.estacao_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PCD_ESTACAO_IDESTACAO_GENERATOR")
    @Column(name = "id_estacao")
     
    private Long idEstacao;

    @Column(name = "codestacao")
    private String codestacao;

    @Column(name = "id_municipio")
    private Integer idMunicipio;

    @Column(name = "id_posicao")
    private Long idPosicao;

    @Column(name = "id_rede")
    private Long idRede;

    @Column(name = "id_tipoestacao")
    private Integer idTipoestacao;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "nome")
    private String nome;

    @Column(name = "status")
    private Integer status;

    @Column(nullable = true, name = "\"offset\"")
    private Double offset;
    
    @Column(nullable = true, name = "cota_atencao")
    private Double cota_atencao;
    
    @Column(nullable = true, name = "cota_alerta")
    private Double cota_alerta;
    
    @Column(nullable = true, name = "cota_transbordamento")
    private Double cota_transbordamento;
    
    @Transient
    private PcdsTipoestacao tipoestacao;

    @Transient
    private GerMunicipio municipio;

    public PcdsEstacao() {
    }

    public Long getIdEstacao() {
        return this.idEstacao;
    }

    public void setIdEstacao(Long idEstacao) {
        this.idEstacao = idEstacao;
    }

    public String getCodestacao() {
        return this.codestacao;
    }

    public void setCodestacao(String codestacao) {
        this.codestacao = codestacao;
    }

    public Integer getIdMunicipio() {
        return this.idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public Long getIdPosicao() {
        return this.idPosicao;
    }

    public void setIdPosicao(Long idPosicao) {
        this.idPosicao = idPosicao;
    }

    public Long getIdRede() {
        return this.idRede;
    }

    public void setIdRede(Long idRede) {
        this.idRede = idRede;
    }

    public Integer getIdTipoestacao() {
        return this.idTipoestacao;
    }

    public void setIdTipoestacao(Integer idTipoestacao) {
        this.idTipoestacao = idTipoestacao;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getOffset() {
        return offset;
    }

    public void setOffset(Double offset) {
        this.offset = offset;
    }
    
    public Double getCota_atencao() {
        return cota_atencao;
    }

    public void setCota_atencao(Double cota_atencao) {
        this.cota_atencao = cota_atencao;
    }

    public Double getCota_alerta() {
        return cota_alerta;
    }

    public void setCota_alerta(Double cota_alerta) {
        this.cota_alerta = cota_alerta;
    }

    public Double getCota_transbordamento() {
        return cota_transbordamento;
    }

    public void setCota_transbordamento(Double cota_transbordamento) {
        this.cota_transbordamento = cota_transbordamento;
    }
    
    /**
     * @return the municipio
     */
    public GerMunicipio getMunicipio() {
        return this.municipio;
    }

    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(GerMunicipio municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the tipoestacao
     */
    public PcdsTipoestacao getTipoestacao() {
        return this.tipoestacao;
    }

    /**
     * @param tipoestacao the tipoestacao to set
     */
    public void setTipoestacao(PcdsTipoestacao tipoestacao) {
        this.tipoestacao = tipoestacao;
    }
     
}
