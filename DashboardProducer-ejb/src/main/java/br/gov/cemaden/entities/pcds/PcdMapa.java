package br.gov.cemaden.entities.pcds;

import br.gov.cemaden.entities.publico.GerMunicipio;
import java.io.Serializable;
import javax.persistence.Transient;

/**
 *
 * @author marcus.costa
 * 05/06/2019
 */
public class PcdMapa implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idSgrpPcd;

	private double latitude;

	private double longitude;

	private String nome;

	private Integer idEstadopcd;

	@Transient
	private PcdsTipoestacao tipoestacao;

	@Transient
	private GerMunicipio municipio;

	public PcdMapa() {
	}

	public Long getIdSgrpPcd() {
		return idSgrpPcd;
	}

	public void setIdSgrpPcd(Long idSgrpPcd) {
		this.idSgrpPcd = idSgrpPcd;
	}

	public Integer getIdEstadopcd() {
		return idEstadopcd;
	}

	public void setIdEstadopcd(Integer idEstadopcd) {
		this.idEstadopcd = idEstadopcd;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public PcdsTipoestacao getTipoestacao() {
		return tipoestacao;
	}

	public void setTipoestacao(PcdsTipoestacao tipoestacao) {
		this.tipoestacao = tipoestacao;
	}

	public GerMunicipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(GerMunicipio municipio) {
		this.municipio = municipio;
	}

}
