package pnl.modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the USUARIO_ROLES database table.
 * 
 */
@Embeddable
public class UsuarioRolPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_USUARIO", insertable=false, updatable=false)
	private String idUsuario;

	@Column(name="ID_ROL", insertable=false, updatable=false)
	private long idRol;

	public UsuarioRolPK() {
	}
	public String getIdUsuario() {
		return this.idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public long getIdRol() {
		return this.idRol;
	}
	public void setIdRol(long idRol) {
		this.idRol = idRol;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UsuarioRolPK)) {
			return false;
		}
		UsuarioRolPK castOther = (UsuarioRolPK)other;
		return 
			this.idUsuario.equals(castOther.idUsuario)
			&& (this.idRol == castOther.idRol);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idUsuario.hashCode();
		hash = hash * prime + ((int) (this.idRol ^ (this.idRol >>> 32)));
		
		return hash;
	}
}