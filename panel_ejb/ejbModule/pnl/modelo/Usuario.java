package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the USUARIO database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
@Cacheable(false)
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_USUARIO")
	private String idUsuario;

	private String nombre;
	
	private String clave;
	
	@Column(name="CREDENCIALES_NO_EXPIRADAS")
	private String credencialesNoExpiradas;

	@Column(name="CUENTA_NO_BLOQUEADA")
	private String cuentaNoBloqueada;

	@Column(name="CUENTA_NO_EXPIRADA")
	private String cuentaNoExpirada;
	
	//bi-directional many-to-one association to UsuarioGrupo
	@OneToMany(mappedBy="usuario")
	private List<UsuarioGrupo> usuarioGrupos;

	//bi-directional many-to-many association to Rol
	@ManyToMany
	@JoinTable(
		name="USUARIO_ROLES"
		, joinColumns={
			@JoinColumn(name="ID_USUARIO")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ID_ROL")
			}
		)
	private List<Rol> rols;
	
	

	public Usuario() {
	}

	public String getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	


	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	
	
	

	public String getCredencialesNoExpiradas() {
		return credencialesNoExpiradas;
	}

	public void setCredencialesNoExpiradas(String credencialesNoExpiradas) {
		this.credencialesNoExpiradas = credencialesNoExpiradas;
	}

	public String getCuentaNoBloqueada() {
		return cuentaNoBloqueada;
	}

	public void setCuentaNoBloqueada(String cuentaNoBloqueada) {
		this.cuentaNoBloqueada = cuentaNoBloqueada;
	}

	public String getCuentaNoExpirada() {
		return cuentaNoExpirada;
	}

	public void setCuentaNoExpirada(String cuentaNoExpirada) {
		this.cuentaNoExpirada = cuentaNoExpirada;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Rol> getRols() {
		return this.rols;
	}

	public void setRols(List<Rol> rols) {
		this.rols = rols;
	}

	public List<UsuarioGrupo> getUsuarioGrupos() {
		return this.usuarioGrupos;
	}

	public void setUsuarioGrupos(List<UsuarioGrupo> usuarioGrupos) {
		this.usuarioGrupos = usuarioGrupos;
	}

	public UsuarioGrupo addUsuarioGrupo(UsuarioGrupo usuarioGrupo) {
		getUsuarioGrupos().add(usuarioGrupo);
		usuarioGrupo.setUsuario(this);

		return usuarioGrupo;
	}

	public UsuarioGrupo removeUsuarioGrupo(UsuarioGrupo usuarioGrupo) {
		getUsuarioGrupos().remove(usuarioGrupo);
		usuarioGrupo.setUsuario(null);

		return usuarioGrupo;
	}


}