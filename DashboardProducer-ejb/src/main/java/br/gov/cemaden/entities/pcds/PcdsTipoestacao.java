package br.gov.cemaden.entities.pcds;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author marcus.costa
 * 05/06/2019
 */
@Entity
@Table(name="pcds_tipoestacao", schema = "pcds")
public class PcdsTipoestacao implements Serializable, Comparable<PcdsTipoestacao> {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipoestacao")
        
	private Integer idTipoestacao;

	private String descricao;

	public PcdsTipoestacao() {
	}

	public Integer getIdTipoestacao() {
		return this.idTipoestacao;
	}

	public void setIdTipoestacao(Integer idTipoestacao) {
		this.idTipoestacao = idTipoestacao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int compareTo(PcdsTipoestacao that) {
		return this.descricao.compareTo(that.descricao);
	}
}