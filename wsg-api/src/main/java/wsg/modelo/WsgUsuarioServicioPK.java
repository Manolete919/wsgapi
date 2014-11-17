package wsg.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The primary key class for the WSG_USUARIO_SERVICIO database table.
 * 
 */
@Embeddable
public class WsgUsuarioServicioPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
	@Column(name="ID_USUARIO", insertable=false, updatable=false)
	private String idUsuario;

	@Basic(optional = false)
    @NotNull
	@Column(name="ID_SERVICIO", insertable=false, updatable=false)
	private long idServicio;

	public WsgUsuarioServicioPK() {
	}
	public String getIdUsuario() {
		return this.idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public long getIdServicio() {
		return this.idServicio;
	}
	public void setIdServicio(long idServicio) {
		this.idServicio = idServicio;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WsgUsuarioServicioPK)) {
			return false;
		}
		WsgUsuarioServicioPK castOther = (WsgUsuarioServicioPK)other;
		return 
			this.idUsuario.equals(castOther.idUsuario)
			&& (this.idServicio == castOther.idServicio);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idUsuario.hashCode();
		hash = hash * prime + ((int) (this.idServicio ^ (this.idServicio >>> 32)));
		
		return hash;
	}
}