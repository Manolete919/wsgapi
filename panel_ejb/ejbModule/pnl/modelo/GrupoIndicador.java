package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the GRUPO_INDICADORES database table.
 * 
 */
@Entity
@Table(name="GRUPO_INDICADORES")
@NamedQuery(name="GrupoIndicador.findAll", query="SELECT g FROM GrupoIndicador g")
public class GrupoIndicador implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GrupoIndicadorPK id;

	private String estado;

	//bi-directional many-to-one association to Grupo
	@ManyToOne
	@JoinColumn(name="ID_GRUPO")
	private Grupo grupo;

	//bi-directional many-to-one association to Indicador
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="ID_INDICADOR")
	private Indicador indicador;

	public GrupoIndicador() {
	}

	public GrupoIndicadorPK getId() {
		return this.id;
	}

	public void setId(GrupoIndicadorPK id) {
		this.id = id;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Grupo getGrupo() {
		return this.grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Indicador getIndicador() {
		return this.indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

}