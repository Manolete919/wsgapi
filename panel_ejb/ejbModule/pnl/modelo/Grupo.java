package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;


/**
 * The persistent class for the GRUPO database table.
 * 
 */
@Entity
@NamedQuery(name="Grupo.findAll", query="SELECT g FROM Grupo g")
public class Grupo implements Serializable {
	private static final long serialVersionUID = 1L;

	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
	@SequenceGenerator(name="GRUPO_IDGRUPO_GENERATOR", sequenceName="SQ_GRUPOS",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GRUPO_IDGRUPO_GENERATOR")
	@Column(name="ID_GRUPO")
	private long idGrupo;
    
    @Size(max = 200)
	private String descripcion;
    
    @Size(max = 1)
	private String estado;
    
    

	//bi-directional many-to-one association to GrupoIndicador
	@OneToMany(mappedBy="grupo")
	private List<GrupoIndicador> grupoIndicadores;

	//bi-directional many-to-one association to UsuarioGrupo
	@OneToMany(mappedBy="grupo")
	private List<UsuarioGrupo> usuarioGrupos;
	
	
	
	@Transient
	boolean activoInactivo;

	@Transient
	private String estadoVisual;
	
	public Grupo() {
		super();
	}

	public long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<GrupoIndicador> getGrupoIndicadores() {
		return grupoIndicadores;
	}

	public void setGrupoIndicadores(List<GrupoIndicador> grupoIndicadores) {
		this.grupoIndicadores = grupoIndicadores;
	}

	public List<UsuarioGrupo> getUsuarioGrupos() {
		return usuarioGrupos;
	}

	public void setUsuarioGrupos(List<UsuarioGrupo> usuarioGrupos) {
		this.usuarioGrupos = usuarioGrupos;
	}

	public GrupoIndicador addGrupoIndicadore(GrupoIndicador grupoIndicadore) {
		getGrupoIndicadores().add(grupoIndicadore);
		grupoIndicadore.setGrupo(this);

		return grupoIndicadore;
	}

	public GrupoIndicador removeGrupoIndicadore(GrupoIndicador grupoIndicadore) {
		getGrupoIndicadores().remove(grupoIndicadore);
		grupoIndicadore.setGrupo(null);

		return grupoIndicadore;
	}

	
	public UsuarioGrupo addUsuarioGrupo(UsuarioGrupo usuarioGrupo) {
		getUsuarioGrupos().add(usuarioGrupo);
		usuarioGrupo.setGrupo(this);

		return usuarioGrupo;
	}

	public UsuarioGrupo removeUsuarioGrupo(UsuarioGrupo usuarioGrupo) {
		getUsuarioGrupos().remove(usuarioGrupo);
		usuarioGrupo.setGrupo(null);

		return usuarioGrupo;
	}

	

	public boolean isActivoInactivo() {
		
		
		if(this.getEstado()==null){
			activoInactivo = false;
		}else{
			if(this.getEstado().equals("A")){
				activoInactivo = true;
			}else{
				activoInactivo = false;
			}
		}
		

			
		return activoInactivo;
	}

	public void setActivoInactivo(boolean activoInactivo) {
		
		if(activoInactivo){
			this.estado = "A";
		}else{
			this.estado = "I";
		}
		
		this.activoInactivo = activoInactivo;
	}

	public String getEstadoVisual() {
		if(this.getEstado()==null){
			estadoVisual = "Inactivo";
		}else{
			if(this.getEstado().equals("A")){
				estadoVisual = "Activo";
			}else{
				estadoVisual = "Inactivo";
			}
		}
		return estadoVisual;
	}



	
	

}