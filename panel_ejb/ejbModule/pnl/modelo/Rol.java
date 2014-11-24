package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;


/**
 * The persistent class for the ROL database table.
 * 
 */
@Entity
@NamedQuery(name="Rol.findAll", query="SELECT r FROM Rol r")
@Cacheable(false)
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
	@Column(name="ID_ROL")
	private long idRol;

    @Size(max = 200)
	private String rol;

	//bi-directional many-to-many association to Usuario
	@ManyToMany(mappedBy="rols")
	private List<Usuario> usuarios;
	
	//bi-directional many-to-one association to UsuarioRol
	@OneToMany(mappedBy="rol")
	private List<UsuarioRol> usuarioRoles;

	public Rol() {
	}

	public long getIdRol() {
		return idRol;
	}

	public void setIdRol(long idRol) {
		this.idRol = idRol;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<UsuarioRol> getUsuarioRoles() {
		return this.usuarioRoles;
	}

	public void setUsuarioRoles(List<UsuarioRol> usuarioRoles) {
		this.usuarioRoles = usuarioRoles;
	}

	public UsuarioRol addUsuarioRole(UsuarioRol usuarioRole) {
		getUsuarioRoles().add(usuarioRole);
		usuarioRole.setRol(this);

		return usuarioRole;
	}

	public UsuarioRol removeUsuarioRole(UsuarioRol usuarioRole) {
		getUsuarioRoles().remove(usuarioRole);
		usuarioRole.setRol(null);

		return usuarioRole;
	}

	

}