package br.gov.cemaden.entities.pcds;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author marcus.costa
 * 05/06/2019
 */
public class PcdHistoricoInatividade implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idPcd;
    private Timestamp dataHora;
    private String estadoDestino;
    private String nome;
    private String codeEstacao;
    private Double latitude;
    private Double longitude;
    private String cidade;
    private String uf;

    public int getIdPcd() {
        return idPcd;
    }

    public void setIdPcd(int idPcd) {
        this.idPcd = idPcd;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public String getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(String estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodeEstacao() {
        return codeEstacao;
    }

    public void setCodeEstacao(String codeEstacao) {
        this.codeEstacao = codeEstacao;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

}
