package wsg.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * The persistent class for the WSG_USUARIO_SERVICIO database table.
 * 
 */
@Entity
@Table(name="WSG_USUARIO_SERVICIO")
//@Cacheable(false)
@NamedQuery(name="WsgUsuarioServicio.findAll", query="SELECT w FROM WsgUsuarioServicio w")
public class WsgUsuarioServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WsgUsuarioServicioPK id;

    @Basic(optional = false)
    @NotNull
    @Size(max = 1)
	private String estado;

	//bi-directional many-to-one association to WsgServicio
	@ManyToOne
	@JoinColumn(name="ID_SERVICIO")
	private WsgServicio wsgServicio;

	//bi-directional many-to-one association to WsgUsuario
	@ManyToOne
	@JoinColumn(name="ID_USUARIO")
	private WsgUsuario wsgUsuario;

	public WsgUsuarioServicio() {
	}

	public WsgUsuarioServicioPK getId() {
		return this.id;
	}

	public void setId(WsgUsuarioServicioPK id) {
		this.id = id;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public WsgServicio getWsgServicio() {
		return this.wsgServicio;
	}

	public void setWsgServicio(WsgServicio wsgServicio) {
		this.wsgServicio = wsgServicio;
	}

	public WsgUsuario getWsgUsuario() {
		return this.wsgUsuario;
	}

	public void setWsgUsuario(WsgUsuario wsgUsuario) {
		this.wsgUsuario = wsgUsuario;
	}

}