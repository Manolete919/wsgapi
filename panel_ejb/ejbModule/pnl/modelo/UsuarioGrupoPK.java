package pnl.modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the USUARIO_GRUPOS database table.
 * 
 */
@Embeddable
public class UsuarioGrupoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_USUARIO", insertable=false, updatable=false)
	private String idUsuario;

	@Column(name="ID_GRUPO", insertable=false, updatable=false)
	private long idGrupo;

	public UsuarioGrupoPK() {
	}
	public String getIdUsuario() {
		return this.idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public long getIdGrupo() {
		return this.idGrupo;
	}
	public void setIdGrupo(long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UsuarioGrupoPK)) {
			return false;
		}
		UsuarioGrupoPK castOther = (UsuarioGrupoPK)other;
		return 
			this.idUsuario.equals(castOther.idUsuario)
			&& (this.idGrupo == castOther.idGrupo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idUsuario.hashCode();
		hash = hash * prime + ((int) (this.idGrupo ^ (this.idGrupo >>> 32)));
		
		return hash;
	}
}