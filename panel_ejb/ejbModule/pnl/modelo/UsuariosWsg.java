package pnl.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the USUARIOS_WSG database table.
 * 
 */
@Entity
@Table(name="USUARIOS_WSG")
@Cacheable(false)
@NamedQuery(name="UsuariosWsg.findAll", query="SELECT u FROM UsuariosWsg u")
public class UsuariosWsg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_USUARIO")
	private String idUsuario;

	private String clave;

	private String estado;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="usuariosWsg")
	private List<Usuario> usuarios;

	public UsuariosWsg() {
	}

	public String getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setUsuariosWsg(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setUsuariosWsg(null);

		return usuario;
	}

}