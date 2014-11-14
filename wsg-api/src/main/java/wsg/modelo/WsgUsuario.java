package wsg.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the WSG_USUARIO database table.
 * 
 */
@Entity
@Table(name="WSG_USUARIO")
@Cacheable(false)
@NamedQuery(name="WsgUsuario.findAll", query="SELECT w FROM WsgUsuario w")
public class WsgUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_USUARIO")
	private String idUsuario;

	private String clave;

	@Column(name="CREDENCIALES_NO_EXPIRADAS")
	private String credencialesNoExpiradas;

	@Column(name="CUENTA_NO_BLOQUEADA")
	private String cuentaNoBloqueada;

	@Column(name="CUENTA_NO_EXPIRADA")
	private String cuentaNoExpirada;

	//bi-directional many-to-one association to WsgUsuarioServicio
	@OneToMany(mappedBy="wsgUsuario")
	private List<WsgUsuarioServicio> wsgUsuarioServicios;

	public WsgUsuario() {
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

	public String getCredencialesNoExpiradas() {
		return this.credencialesNoExpiradas;
	}

	public void setCredencialesNoExpiradas(String credencialesNoExpiradas) {
		this.credencialesNoExpiradas = credencialesNoExpiradas;
	}

	public String getCuentaNoBloqueada() {
		return this.cuentaNoBloqueada;
	}

	public void setCuentaNoBloqueada(String cuentaNoBloqueada) {
		this.cuentaNoBloqueada = cuentaNoBloqueada;
	}

	public String getCuentaNoExpirada() {
		return this.cuentaNoExpirada;
	}

	public void setCuentaNoExpirada(String cuentaNoExpirada) {
		this.cuentaNoExpirada = cuentaNoExpirada;
	}

	public List<WsgUsuarioServicio> getWsgUsuarioServicios() {
		return this.wsgUsuarioServicios;
	}

	public void setWsgUsuarioServicios(List<WsgUsuarioServicio> wsgUsuarioServicios) {
		this.wsgUsuarioServicios = wsgUsuarioServicios;
	}

	public WsgUsuarioServicio addWsgUsuarioServicio(WsgUsuarioServicio wsgUsuarioServicio) {
		getWsgUsuarioServicios().add(wsgUsuarioServicio);
		wsgUsuarioServicio.setWsgUsuario(this);

		return wsgUsuarioServicio;
	}

	public WsgUsuarioServicio removeWsgUsuarioServicio(WsgUsuarioServicio wsgUsuarioServicio) {
		getWsgUsuarioServicios().remove(wsgUsuarioServicio);
		wsgUsuarioServicio.setWsgUsuario(null);

		return wsgUsuarioServicio;
	}

}