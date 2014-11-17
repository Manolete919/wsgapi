package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * The primary key class for the GRUPO_INDICADORES database table.
 * 
 */
@Embeddable
public class GrupoIndicadorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
    @Basic(optional = false)
    @NotNull
	@Column(name="ID_GRUPO", insertable=false, updatable=false)
	private long idGrupo;

    @Basic(optional = false)
    @NotNull
	@Column(name="ID_INDICADOR", insertable=false, updatable=false)
	private long idIndicador;

	public GrupoIndicadorPK() {
	}
	public long getIdGrupo() {
		return this.idGrupo;
	}
	public void setIdGrupo(long idGrupo) {
		this.idGrupo = idGrupo;
	}
	public long getIdIndicador() {
		return this.idIndicador;
	}
	public void setIdIndicador(long idIndicador) {
		this.idIndicador = idIndicador;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GrupoIndicadorPK)) {
			return false;
		}
		GrupoIndicadorPK castOther = (GrupoIndicadorPK)other;
		return 
			(this.idGrupo == castOther.idGrupo)
			&& (this.idIndicador == castOther.idIndicador);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idGrupo ^ (this.idGrupo >>> 32)));
		hash = hash * prime + ((int) (this.idIndicador ^ (this.idIndicador >>> 32)));
		
		return hash;
	}
}