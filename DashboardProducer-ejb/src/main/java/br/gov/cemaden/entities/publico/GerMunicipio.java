package br.gov.cemaden.entities.publico;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author marcus.costa
 * 05/06/2019
 */
@Entity
@Table(name="ger_municipio", schema="public")
public class GerMunicipio implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_municipio")
        
	private Integer idMunicipio;

	private String cidade;

	@Transient
	private Integer codibge;

	@Transient
	@Column(name="the_geom")
	private Object theGeom;

	private String uf;

	public GerMunicipio() {
	}

	public GerMunicipio(String cidade, String uf) {
		super();
		this.cidade = cidade;
		this.uf = uf;
	}

	public Integer getIdMunicipio() {
		return this.idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Integer getCodibge() {
		return this.codibge;
	}

	public void setCodibge(Integer codibge) {
		this.codibge = codibge;
	}

	public Object getTheGeom() {
		return this.theGeom;
	}

	public void setTheGeom(Object theGeom) {
		this.theGeom = theGeom;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@Override
	public GerMunicipio clone() {
		GerMunicipio clone = new GerMunicipio();
		clone.idMunicipio = this.idMunicipio;
		clone.cidade = this.cidade;
		clone.codibge = this.codibge;
		clone.theGeom = this.theGeom;
		clone.uf = this.uf;
		return clone;
	}
}