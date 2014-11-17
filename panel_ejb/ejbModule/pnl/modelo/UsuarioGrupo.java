package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * The persistent class for the USUARIO_GRUPOS database table.
 * 
 */
@Entity
@Table(name="USUARIO_GRUPOS")
@NamedQuery(name="UsuarioGrupo.findAll", query="SELECT u FROM UsuarioGrupo u")
public class UsuarioGrupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsuarioGrupoPK id;

    @Basic(optional = false)
    @NotNull
	@Size(max = 1)
	private String estado;
	
	//bi-directional many-to-one association to Grupo
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="ID_GRUPO")
	private Grupo grupo;
	
	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="ID_USUARIO")
	private Usuario usuario;

	public UsuarioGrupo() {
	}

	public UsuarioGrupoPK getId() {
		return id;
	}

	public void setId(UsuarioGrupoPK id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	

}