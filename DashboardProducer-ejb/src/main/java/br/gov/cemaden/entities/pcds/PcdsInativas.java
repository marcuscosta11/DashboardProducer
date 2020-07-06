package br.gov.cemaden.entities.pcds;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author marcus.costa
 * 05/06/2019
 */
public class PcdsInativas implements Serializable {
     
    private static final long serialVersionUID = 1L;
    
    private int idPcd;
    private int idEstacao;
    private String nome;
    private String tipoEstacao;
    private String codeEstacao;
    private String dhUltimaRemessa;
    private Double latitude;
    private Double longitude;

    public int getIdPcd() {
        return idPcd;
    }

    public void setIdPcd(int idPcd) {
        this.idPcd = idPcd;
    }

    public int getIdEstacao() {
        return idEstacao;
    }

    public void setIdEstacao(int idEstacao) {
        this.idEstacao = idEstacao;
    }   
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoEstacao() {
        return tipoEstacao;
    }

    public void setTipoEstacao(String tipoEstacao) {
        this.tipoEstacao = tipoEstacao;
    }
    
    
    public String getCodeEstacao() {
        return codeEstacao;
    }

    public void setCodeEstacao(String codeEstacao) {
        this.codeEstacao = codeEstacao;
    }

    public String getDhUltimaRemessa() {
        return dhUltimaRemessa;
    }

    public void setDhUltimaRemessa(String dhUltimaRemessa) {
        this.dhUltimaRemessa = dhUltimaRemessa;
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
}

