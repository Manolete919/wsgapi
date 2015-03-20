package wsg.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the WSG_SERVICIOS_LOG database table.
 * 
 */
@Entity
@Table(name="WSG_SERVICIOS_LOG")
//@Cacheable(false)
@NamedQuery(name="WsgServiciosLog.findAll", query="SELECT w FROM WsgServiciosLog w")
public class WsgServiciosLog implements Serializable {
	private static final long serialVersionUID = 1L;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
	//@SequenceGenerator(name="WSG_SERVICIOS_LOG_IDSERVICIOSLOG_GENERATOR", sequenceName="SQ_SERVICIOS_LOG",allocationSize=1)
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="WSG_SERVICIOS_LOG_IDSERVICIOSLOG_GENERATOR")
	@Column(name="ID_SERVICIOS_LOG")
	private long idServiciosLog;
    
    @Basic(optional = false)
    @NotNull
    @Column(name="ID_SERVICIO")
	private BigDecimal idServicio;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    private String usuario;

    @Size(max = 2000)
	private String clave;

	
	
    @Basic(optional = false)
    @NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_INICIO")
	private Date fechaInicio;

    @Basic(optional = false)
    @NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_FIN")
	private Date fechaFin;

    @Lob
	private String xml;
	
    @Size(max = 100)
	private String proveedor;
	
	@Lob
	@Column(name="SENTENCIA_SQL")
	private String sentenciaSql;
	
	@Lob
	private String resultado;
	
	@Column(name="COD_ERROR")
	private BigDecimal codError;

	@Lob
	@Column(name="MSG_ERROR")
	private String msgError;

	

	

	

	public WsgServiciosLog() {
	}

	public long getIdServiciosLog() {
		return this.idServiciosLog;
	}

	public void setIdServiciosLog(long idServiciosLog) {
		this.idServiciosLog = idServiciosLog;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public BigDecimal getCodError() {
		return this.codError;
	}

	public void setCodError(BigDecimal codError) {
		this.codError = codError;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public BigDecimal getIdServicio() {
		return this.idServicio;
	}

	public void setIdServicio(BigDecimal idServicio) {
		this.idServicio = idServicio;
	}

	public String getMsgError() {
		return this.msgError;
	}

	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}

	public String getResultado() {
		return this.resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getXml() {
		return this.xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getSentenciaSql() {
		return sentenciaSql;
	}

	public void setSentenciaSql(String sentenciaSql) {
		this.sentenciaSql = sentenciaSql;
	}
	
	
	

}