package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the LOG_USUARIO database table.
 * 
 */
@Entity
@Table(name="LOG_USUARIO")
@Cacheable(false)
@NamedQuery(name="LogUsuario.findAll", query="SELECT l FROM LogUsuario l")
public class LogUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LOG_USUARIO_IDLOGUSUARIO_GENERATOR", sequenceName="SQ_USUARIOS_LOG",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOG_USUARIO_IDLOGUSUARIO_GENERATOR")
	@Column(name="ID_LOG_USUARIO")
	private long idLogUsuario;

	@Lob
	private String detalle;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	@Column(name="ID_SESION")
	private String idSesion;

	//bi-directional many-to-one association to AccionUsuario
	@ManyToOne
	@JoinColumn(name="ID_ACCION")
	private AccionUsuario accionUsuario;

	//bi-directional many-to-one association to RecursosApp
	@ManyToOne
	@JoinColumn(name="ID_RECURSO")
	private RecursosApp recursosApp;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="ID_USUARIO")
	private Usuario usuario;

	public LogUsuario() {
	}

	public long getIdLogUsuario() {
		return this.idLogUsuario;
	}

	public void setIdLogUsuario(long idLogUsuario) {
		this.idLogUsuario = idLogUsuario;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getIdSesion() {
		return this.idSesion;
	}

	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}

	public AccionUsuario getAccionUsuario() {
		return this.accionUsuario;
	}

	public void setAccionUsuario(AccionUsuario accionUsuario) {
		this.accionUsuario = accionUsuario;
	}

	public RecursosApp getRecursosApp() {
		return this.recursosApp;
	}

	public void setRecursosApp(RecursosApp recursosApp) {
		this.recursosApp = recursosApp;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}