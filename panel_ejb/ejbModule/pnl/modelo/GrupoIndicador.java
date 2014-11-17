package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Size;


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
	
	

	//bi-directional many-to-one association to Grupo
	@ManyToOne(optional = false)
	@JoinColumn(name="ID_GRUPO")
	private Grupo grupo;
	

	//bi-directional many-to-one association to Indicador
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="ID_INDICADOR")
	private Indicador indicador;
	
	
	@Size(max = 1)
	private String estado;

	public GrupoIndicador() {
	}

	public GrupoIndicadorPK getId() {
		return id;
	}

	public void setId(GrupoIndicadorPK id) {
		this.id = id;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Indicador getIndicador() {
		return indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	
	

}