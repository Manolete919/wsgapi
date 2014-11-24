package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the RECURSOS_APP database table.
 * 
 */
@Entity
@Table(name="RECURSOS_APP")
@Cacheable(false)
@NamedQuery(name="RecursosApp.findAll", query="SELECT r FROM RecursosApp r")
public class RecursosApp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_RECURSOS_APP")
	private long idRecursosApp;

	private String descripcion;

	//bi-directional many-to-one association to LogUsuario
	@OneToMany(mappedBy="recursosApp")
	private List<LogUsuario> logUsuarios;

	public RecursosApp() {
	}

	public long getIdRecursosApp() {
		return this.idRecursosApp;
	}

	public void setIdRecursosApp(long idRecursosApp) {
		this.idRecursosApp = idRecursosApp;
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
		logUsuario.setRecursosApp(this);

		return logUsuario;
	}

	public LogUsuario removeLogUsuario(LogUsuario logUsuario) {
		getLogUsuarios().remove(logUsuario);
		logUsuario.setRecursosApp(null);

		return logUsuario;
	}

}