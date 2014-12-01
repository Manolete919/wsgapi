package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the ACCION_USUARIO database table.
 * 
 */
@Entity
@Table(name="ACCION_USUARIO")
@Cacheable(false)
@NamedQuery(name="AccionUsuario.findAll", query="SELECT a FROM AccionUsuario a ORDER BY a.idAccionUsuario")
public class AccionUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_ACCION_USUARIO")
	private long idAccionUsuario;

	private String descripcion;

	//bi-directional many-to-one association to LogUsuario
	@OneToMany(mappedBy="accionUsuario")
	private List<LogUsuario> logUsuarios;

	public AccionUsuario() {
	}

	public long getIdAccionUsuario() {
		return this.idAccionUsuario;
	}

	public void setIdAccionUsuario(long idAccionUsuario) {
		this.idAccionUsuario = idAccionUsuario;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<LogUsuario> getLogUsuarios() {
		return this.logUsuarios;
	}

	public void setLogUsuarios(List<LogUsuario> logUsuarios) {
		this.logUsuarios = logUsuarios;
	}

	public LogUsuario addLogUsuario(LogUsuario logUsuario) {
		getLogUsuarios().add(logUsuario);
		logUsuario.setAccionUsuario(this);

		return logUsuario;
	}

	public LogUsuario removeLogUsuario(LogUsuario logUsuario) {
		getLogUsuarios().remove(logUsuario);
		logUsuario.setAccionUsuario(null);

		return logUsuario;
	}

}