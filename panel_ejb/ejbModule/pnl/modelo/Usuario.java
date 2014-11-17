package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
	@Column(name="ID_USUARIO")
	private String idUsuario;
	
    @Basic(optional = false)
    @NotNull
	@Size(max = 200)
	private String nombre;
	
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2000)
	private String clave;
    
	@Basic(optional = false)
    @NotNull
    @Size(max = 1)
	@Column(name="CUENTA_NO_EXPIRADA")
	private String cuentaNoExpirada;
    
	@Basic(optional = false)
    @NotNull
    @Size(max = 1)
	@Column(name="CREDENCIALES_NO_EXPIRADAS")
	private String credencialesNoExpiradas;
    
	@Basic(optional = false)
    @NotNull
    @Size(max = 1)
	@Column(name="CUENTA_NO_BLOQUEADA")
	private String cuentaNoBloqueada;

    
	
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
		return idUsuario;
	}



	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}



	public String getNombre() {
		return nombre;
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



	public String getCuentaNoExpirada() {
		return cuentaNoExpirada;
	}



	public void setCuentaNoExpirada(String cuentaNoExpirada) {
		this.cuentaNoExpirada = cuentaNoExpirada;
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



	public List<UsuarioGrupo> getUsuarioGrupos() {
		return usuarioGrupos;
	}



	public void setUsuarioGrupos(List<UsuarioGrupo> usuarioGrupos) {
		this.usuarioGrupos = usuarioGrupos;
	}



	public List<Rol> getRols() {
		return rols;
	}



	public void setRols(List<Rol> rols) {
		this.rols = rols;
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