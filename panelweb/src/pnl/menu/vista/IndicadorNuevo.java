package pnl.menu.vista;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.primefaces.model.DualListModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import pnl.filtro.dinamico.FiltroValorDefault;
import pnl.interfaz.GrupoIndicadorBeanRemote;
import pnl.interfaz.ModeloGraficoBeanRemote;
import pnl.interfaz.UsuarioGrupoBeanRemote;
import pnl.modelo.Grupo;
import pnl.modelo.GrupoIndicadorPK;
import pnl.modelo.Indicador;
import pnl.modelo.IndicadorSerie;
import pnl.modelo.ModeloGrafico;
import pnl.modelo.Usuario;
import pnl.modelo.GrupoIndicador;
import pnl.modelo.UsuarioGrupo;
import pnl.servicio.RegistraLog;
import pnl.servicio.Tema;
import pnl.servicio.UsuarioServicio;
import pnl.webservice.integracion.ConsultaGenerico;
import pnl.webservice.integracion.Utileria;
import pnl.webservice.integracion.WsgServicio;
import pnl.wsg.Servicio;

@ManagedBean
@ViewScoped
public class IndicadorNuevo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GrupoIndicadorBeanRemote grupoIndicadorBeanRemote;
	private Indicador indicador;

	private ModeloGraficoBeanRemote modeloGraficoBeanRemote;
	private List<ModeloGrafico> modeloGraficos;
	private Map<String, Integer> modeloGraficoSelect;
	private int indiceModeloGrafico;

	private UsuarioGrupoBeanRemote usuarioGrupoBeanRemote;
	private List<UsuarioGrupo> usuarioGrupos;

	
	private List<WsgServicio> wsgServicios;
	private Map<String, Integer> wsgServicioSelect;
	private int indiceWsgServicio;
	private String query;
	
	

	private IndicadorSerie indicadorSerie;
	private Usuario usuario;
	private List<FiltroValorDefault> filtroValores;
	List<Grupo> gruposSource;
	List<Grupo> gruposTarget;
	

	@ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;

	@ManagedProperty("#{menuVista}")
	private MenuVista menuVista;
	
	@ManagedProperty("#{registraLog}")
	private RegistraLog registraLog;
	
	
	@ManagedProperty("#{tema}")
	private Tema tema;
	
	private DualListModel<Grupo> grupos;

	@PostConstruct
	public void init() {
		
		System.out.println("tema " + tema.getTema());

		indicador = new Indicador();
		indicador.setValorMiny(new BigDecimal(0));
		indicador.setValorMaxy(new BigDecimal(300));
		indicadorSerie = new IndicadorSerie();
		usuario = usuarioServicio.getUsuario();
		ConsultaGenerico cg = new ConsultaGenerico();
		Utileria u = new Utileria();
		
		
		gruposSource = new ArrayList<Grupo>();
        gruposTarget = new ArrayList<Grupo>();
		

		try {

			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);

			grupoIndicadorBeanRemote = (GrupoIndicadorBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/GrupoIndicadorBean");

			modeloGraficoBeanRemote = (ModeloGraficoBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/ModeloGraficoBean");

			

			usuarioGrupoBeanRemote = (UsuarioGrupoBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/UsuarioGrupoBean");

			
		} catch (Exception e) {
			e.printStackTrace();
		}


		try {
			modeloGraficos = modeloGraficoBeanRemote.getModeloGraficoFindAll();
			filtroValores = new ArrayList<FiltroValorDefault>();
			filtroValores.add(new FiltroValorDefault(null,usuario.getUsuariosWsg().getIdUsuario()));
			Servicio servicio  = cg.consultarServicioWebGenerico(u.convertirFiltroValorEnDocument(filtroValores), new Long(2), usuario.getUsuariosWsg().getIdUsuario(), usuario.getUsuariosWsg().getClave());	
			wsgServicios = new ArrayList<WsgServicio>();
			if(servicio != null ){
				if(servicio.get_any() != null ){					
					wsgServicios = cg.procesaDatosServiciosDeUsuario(servicio.get_any());
				}
			}
			usuarioGrupos = usuarioGrupoBeanRemote.obtenerGruposPorIdUSuarioEstado(usuario.getIdUsuario(),"A");
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		modeloGraficoSelect = new HashMap<String, Integer>();

		int j = 0;

		for (ModeloGrafico modeloGrafico : modeloGraficos) {

			modeloGraficoSelect.put(modeloGrafico.getNombre(), j);
			j++;

		}
	
		
		wsgServicioSelect = new HashMap<String, Integer>();
			
		int l = 0;
		
		

		for (WsgServicio wsgServicio : wsgServicios) {
			wsgServicioSelect.put(wsgServicio.getDescripcion(), l);
			l++;

		}
		
		//inicializar
		if(!wsgServicios.isEmpty()){
			this.getIndicador().setIdServicio(new BigDecimal(this.getWsgServicios().get(this.getIndiceWsgServicio()).getIdServicio()));
		}
		
		

		for (UsuarioGrupo usuarioGrupo : usuarioGrupos) {

			
			gruposSource.add(usuarioGrupo.getGrupo());
	       
			

		}
		
		grupos = new DualListModel<Grupo>(gruposSource, gruposTarget);
				

	}

	public void guardarIndicadorSerieUsuario(ActionEvent actionEvent) {

		// en este caso solo agregaremos la primera por default
		List<IndicadorSerie> indicadorSeries = new ArrayList<IndicadorSerie>();
		indicadorSerie.setIndicador(indicador);
		indicadorSerie.setEstado("A");
		indicadorSeries.add(indicadorSerie);

		List<GrupoIndicador> grupoIndicadores = new ArrayList<GrupoIndicador>();
		
		//por todos los grupos, creamos un grupo indicador
		for(Grupo grupo : grupos.getTarget()){
			GrupoIndicador grupoIndicador = new GrupoIndicador();
			GrupoIndicadorPK id = new GrupoIndicadorPK();
			grupoIndicador.setId(id);
			grupoIndicador.setEstado("A");
			grupoIndicador.setGrupo(grupo);
			grupoIndicador.setIndicador(indicador);
			grupoIndicadores.add(grupoIndicador);
		}


		indicador.setGrupoIndicadores(grupoIndicadores);
		indicador.setEstado("A");
		indicador.setIndicadorSeries(indicadorSeries);
		indicador.setGrupoIndicadores(grupoIndicadores);
		

		if (this.hasRole("ROLE_ADMIN")) {
			
			if(!grupos.getTarget().isEmpty()){
				try {

					grupoIndicadorBeanRemote.persistGrupoIndicadores(grupoIndicadores);
					
					List<Indicador> detalles = new ArrayList<Indicador>();
					detalles.add(indicador);
					registraLog.registrarLog(detalles, RegistraLog.ACCION_CREAR, RegistraLog.RECURSO_INDICADOR);

					// ACTUALIZAR MENU

					addMessage("Se guardo exitosamente!!",FacesMessage.SEVERITY_INFO);

					menuVista.actualizarMenu();
					indicador = new Indicador();
					indicador.setValorMiny(new BigDecimal(0));
					indicador.setValorMaxy(new BigDecimal(300));
					indicadorSerie = new IndicadorSerie();
					gruposTarget = new ArrayList<Grupo>();
					grupos = new DualListModel<Grupo>(gruposSource, gruposTarget);

					

				} catch (Exception e) {
					
					addMessage("Hubo algun error",FacesMessage.SEVERITY_ERROR);

					e.printStackTrace();
				}
			}else{
				addMessage("Debe asignarlo a algun grupo",FacesMessage.SEVERITY_WARN);
			}


		} else {
			addMessage("NO TIENE PERMISO DE ADMINISTRADOR PARA REALIZAR ESTA ACCION",FacesMessage.SEVERITY_WARN);
		}

	}

	public void addMessage(String summary,Severity severity) {
		FacesMessage message = new FacesMessage(severity,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public Indicador getIndicador() {
		return indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public Map<String, Integer> getModeloGraficoSelect() {
		
		return modeloGraficoSelect;
	}

	public void setModeloGraficoSelect(Map<String, Integer> modeloGraficoSelect) {
		this.modeloGraficoSelect = modeloGraficoSelect;
	}

	public int getIndiceModeloGrafico() {
		return indiceModeloGrafico;
	}

	public void setIndiceModeloGrafico(int indiceModeloGrafico) {
		this.indiceModeloGrafico = indiceModeloGrafico;

		ModeloGrafico modeloGrafico = this.getModeloGraficos().get(
				indiceModeloGrafico);
		this.getIndicador().setModeloGrafico(modeloGrafico);

	}

	public List<ModeloGrafico> getModeloGraficos() {
		
		return modeloGraficos;
	}

	public void setModeloGraficos(List<ModeloGrafico> modeloGraficos) {
		this.modeloGraficos = modeloGraficos;
	}

	protected boolean hasRole(String role) {
		// get security context from thread local
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return false;

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return false;

		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (role.equals(auth.getAuthority()))
				return true;
		}

		return false;
	}

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}

	public void setMenuVista(MenuVista menuVista) {
		this.menuVista = menuVista;
	}

	public IndicadorSerie getIndicadorSerie() {
		return indicadorSerie;
	}

	public void setIndicadorSerie(IndicadorSerie indicadorSerie) {
		this.indicadorSerie = indicadorSerie;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public List<UsuarioGrupo> getUsuarioGrupos() {
		return usuarioGrupos;
	}

	public void setUsuarioGrupos(List<UsuarioGrupo> usuarioGrupos) {
		this.usuarioGrupos = usuarioGrupos;
	}



	public int getIndiceWsgServicio() {
		return indiceWsgServicio;
	}

	public void setIndiceWsgServicio(int indiceWsgServicio) {
		
		this.indiceWsgServicio = indiceWsgServicio;
		
		System.out.println("INDICE " + indiceWsgServicio);
		
		WsgServicio wsgServicio = this.getWsgServicios().get(indiceWsgServicio);
		this.getIndicador().setIdServicio(new BigDecimal(wsgServicio.getIdServicio()));
		
				
	}

	public List<WsgServicio> getWsgServicios() {
		return wsgServicios;
	}

	public void setWsgServicios(List<WsgServicio> wsgServicios) {
		this.wsgServicios = wsgServicios;
	}

	public Map<String, Integer> getWsgServicioSelect() {
				
		return wsgServicioSelect;
	}

	public void setWsgServicioSelect(Map<String, Integer> wsgServicioSelect) {
		this.wsgServicioSelect = wsgServicioSelect;
	}

	public String getQuery() {

		
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	public void consultarSentencias(){
		
		filtroValores = new ArrayList<FiltroValorDefault>();
		filtroValores.add(new FiltroValorDefault(null,this.getIndicador().getIdServicio().toString()));
		ConsultaGenerico cg = new ConsultaGenerico();
		Utileria u = new Utileria();		
		
		try {
			Servicio servicio = cg.consultarServicioWebGenerico(u.convertirFiltroValorEnDocument(filtroValores), new Long(3), usuario.getUsuariosWsg().getIdUsuario(), usuario.getUsuariosWsg().getClave());
			if(servicio != null ){
				if(servicio.get_any() != null ){
					query = cg.procesaDatosIdServicio(servicio.get_any());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DualListModel<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(DualListModel<Grupo> grupos) {
		this.grupos = grupos;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}
	
	public void setRegistraLog(RegistraLog registraLog) {
		this.registraLog = registraLog;
	}

}
