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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import pnl.filtro.dinamico.FiltroValorDefault;
import pnl.interfaz.GrupoIndicadorBeanRemote;
import pnl.interfaz.IndicadorBeanRemote;
import pnl.interfaz.ModeloGraficoBeanRemote;
import pnl.interfaz.UsuarioGrupoBeanRemote;
import pnl.modelo.Grupo;
import pnl.modelo.GrupoIndicadorPK;
import pnl.modelo.Indicador;
import pnl.modelo.ModeloGrafico;
import pnl.modelo.Usuario;
import pnl.modelo.GrupoIndicador;
import pnl.modelo.UsuarioGrupo;
import pnl.servicio.RegistraLog;
import pnl.servicio.UsuarioServicio;
import pnl.webservice.integracion.ConsultaGenerico;
import pnl.webservice.integracion.Utileria;
import pnl.webservice.integracion.WsgServicio;
import pnl.wsg.Servicio;


@ManagedBean
@ViewScoped
public class IndicadorEditar implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GrupoIndicadorBeanRemote grupoIndicadorBeanRemote;
	private List<GrupoIndicador> indicadorGrupos;	
	private Indicador indicador;
	private ModeloGraficoBeanRemote modeloGraficoBeanRemote;
	private List<ModeloGrafico> modeloGraficos;
	private Map<String, Integer> modeloGraficoSelect;
    private int indiceModeloGrafico;  
    private Usuario usuario;

    
    private IndicadorBeanRemote indicadorBeanRemote;
	private UsuarioGrupoBeanRemote usuarioGrupoBeanRemote;
    private List<UsuarioGrupo> usuarioGrupos;
    
	private List<WsgServicio> wsgServicios;
	private Map<String, Integer> wsgServicioSelect;
	private int indiceWsgServicio;
	private String query;
	private List<FiltroValorDefault> filtroValores;
	
	private DualListModel<Grupo> grupos;

	
	@ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;
	
	@ManagedProperty("#{menuVista}")
	private MenuVista menuVista;
	
	@ManagedProperty("#{registraLog}")
	private RegistraLog registraLog;
	
	
	
	@PostConstruct
	public void init(){
		
		
		usuario = usuarioServicio.getUsuario();
		ConsultaGenerico cg = new ConsultaGenerico();
		Utileria u = new Utileria();
		

		
    	try {
			
			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);

			grupoIndicadorBeanRemote = (GrupoIndicadorBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/GrupoIndicadorBean");
			
			modeloGraficoBeanRemote = (ModeloGraficoBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/ModeloGraficoBean");
			
			usuarioGrupoBeanRemote = (UsuarioGrupoBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/UsuarioGrupoBean");
			
			modeloGraficos = modeloGraficoBeanRemote.getModeloGraficoFindAll();
			
			filtroValores = new ArrayList<FiltroValorDefault>();
			
			filtroValores.add(new FiltroValorDefault(null,usuario.getIdUsuario()));
			
			Servicio servicio  = cg.consultarServicioWebGenerico(u.convertirFiltroValorEnDocument(filtroValores), new Long(2), usuario.getUsuariosWsg().getIdUsuario(), usuario.getUsuariosWsg().getClave());	
			wsgServicios = new ArrayList<WsgServicio>();
			if(servicio != null ){
				if(servicio.get_any() != null ){					
					wsgServicios = cg.procesaDatosServiciosDeUsuario(servicio.get_any());
				}
			}
			
			

			usuarioGrupos = usuarioGrupoBeanRemote.obtenerGruposPorIdUSuarioEstado(usuario.getIdUsuario(),"A");

			indicadorBeanRemote = (IndicadorBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/IndicadorBean");

		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    
    	
		
	}
	
	//metodo llamado por el PRERENDERVIEW
	public void inicializar(long idIndicador){
		
		
		try {
			
			//picklist
	        List<Grupo> gruposSource = new ArrayList<Grupo>();
	        List<Grupo> gruposTarget = new ArrayList<Grupo>();
			
			indicadorGrupos = grupoIndicadorBeanRemote.obtieneIndicadorGruposPorIdIndicador(idIndicador);
		
			if(!indicadorGrupos.isEmpty()){
				indicador = indicadorGrupos.get(0).getIndicador();				
			}
			
		
	    	modeloGraficoSelect = new HashMap<String, Integer>();
	    	
	    	int j = 0;
	    	
	    	
	    	for( ModeloGrafico modeloGrafico : modeloGraficos){
	    		
	    		if(modeloGrafico.getIdModelo()  ==  getIndicador().getModeloGrafico().getIdModelo()){
	    			this.indiceModeloGrafico = j;
	    		} 
	    		
	    		modeloGraficoSelect.put(modeloGrafico.getNombre(),j);
	    		j++;
	    		
	    	}
	    	
	    	
	
	    	//grupos agregados
			for (GrupoIndicador grupoIndicador : indicadorGrupos) {
				
				gruposTarget.add(grupoIndicador.getGrupo());
				
			}
			
			
	    	for(UsuarioGrupo usuarioGrupo : usuarioGrupos ){
	    		
	  
	    		boolean encontrado = false;
	    		for(Grupo g : gruposTarget){
	    			if(usuarioGrupo.getGrupo().getIdGrupo() == g.getIdGrupo()){
	    				encontrado = true;
	    				break;
	    			}
	    		}
	    		
	      		//si no esta en el target, ponerlo en el source
	    		if(!encontrado){
	    			gruposSource.add(usuarioGrupo.getGrupo());
	    		}
	    		
	    	}
			
			
			
			
			
			grupos = new DualListModel<Grupo>(gruposSource, gruposTarget);
			
			
	    	
	    	
	    	
			wsgServicioSelect = new HashMap<String, Integer>();
			
			int l = 0;

			for (WsgServicio wsgServicio : wsgServicios) {

				if(wsgServicio.getIdServicio() == getIndicador().getIdServicio().intValue() ){
					this.indiceWsgServicio = l;
				}
				wsgServicioSelect.put(wsgServicio.getDescripcion(), l);
				l++;

			}
		
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public void actualizarGrupoIndicador(ActionEvent actionEvent) {
		
		
		
		
		
		//si el grupo, no estaba en el target inicialmente debe ponerse en la lista de creacion
		List<GrupoIndicador> grupoCreacionIndicadores = new ArrayList<GrupoIndicador>();
		for(Grupo grupo : grupos.getTarget()){
			
			boolean noEncontrado = true;
			for(GrupoIndicador gi : indicadorGrupos){
				if(grupo.getIdGrupo() == gi.getGrupo().getIdGrupo()){
					noEncontrado = false;
					break;
				}
			}
			
			//si no fue encontrado, es nuevo
			if(noEncontrado){
				GrupoIndicador grupoIndicador = new GrupoIndicador();
				GrupoIndicadorPK id = new GrupoIndicadorPK();
				grupoIndicador.setId(id);
				grupoIndicador.setEstado("A");
				grupoIndicador.setGrupo(grupo);
				grupoIndicador.setIndicador(this.getIndicador());
				grupoCreacionIndicadores.add(grupoIndicador);
			}
			
			
		}
		
		
	
		
		List<GrupoIndicador> grupoEliminacionIndicadores = new ArrayList<GrupoIndicador>();
		//agregados inicial, menos agregados final
		for(GrupoIndicador gi : indicadorGrupos){
			
			boolean encontrado = false;
			//si el grupo, ya no esta en el target, debe ser puesto en la lista a eliminar
			for(Grupo grupo : grupos.getTarget()){
				//si lo encontro
				if(grupo.getIdGrupo() == gi.getGrupo().getIdGrupo()){
					encontrado = true;
					break;
				}
			}
			
			//si no fue encontrado el target, ponerlo en la lista a eliminar
			if(!encontrado){
				grupoEliminacionIndicadores.add(gi);
			}
			
			
			
		}
		
			

		
		
		
  	
   	
    	if(this.hasRole("ROLE_ADMIN")){
    		
    		  		
    		
    		try{
    			
        		if(!grupos.getTarget().isEmpty()){
        			
	        		
	    			
	    			grupoIndicadorBeanRemote.mergeGrupoIndicadores(grupoCreacionIndicadores);    			
	        		
	    			
	    			
	    			grupoIndicadorBeanRemote.removeGrupoIndicadores(grupoEliminacionIndicadores);
	        		
	    			//registrar el log primero, para poder consultar antes de actualizar
	    			
	           		List<Indicador> detalles = new ArrayList<Indicador>();

	           		detalles.add(indicador);
	           		
	         		registraLog.registrarLog(detalles, RegistraLog.ACCION_EDITAR, RegistraLog.RECURSO_INDICADOR);

	    			
	    			
	    			indicadorBeanRemote.mergeIndicador(this.getIndicador());
	    			

	    				    			
	    			
	                addMessage("Los datos fueron actualizados exitosamente!!",FacesMessage.SEVERITY_INFO);
	                
	                menuVista.actualizarMenu();
                
        		}else{
        			addMessage("Debe existir al menos un grupo asignado",FacesMessage.SEVERITY_WARN);
        		}
        		
    		}catch(Exception e){
    			addMessage("Hubo algun error",FacesMessage.SEVERITY_ERROR);
    			e.printStackTrace();
    		}
    		

    	
    	
    	}else{
    		addMessage("NO TIENE PERMISO ADMINISTRADOR PARA REALIZAR ESTA ACCION!!",FacesMessage.SEVERITY_WARN);
    	}
	
    }
     

    
	public void addMessage(String summary,Severity severity) {
		FacesMessage message = new FacesMessage(severity,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}



	public List<GrupoIndicador> getIndicadorGrupos() {
		return indicadorGrupos;
	}

	public void setIndicadorGrupos(List<GrupoIndicador> indicadorGrupos) {
		this.indicadorGrupos = indicadorGrupos;
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
		
		ModeloGrafico modeloGrafico = this.getModeloGraficos().get(indiceModeloGrafico);
		getIndicador().setModeloGrafico(modeloGrafico);
		
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

	public int getIndiceWsgServicio() {
		return indiceWsgServicio;
	}

	public void setIndiceWsgServicio(int indiceWsgServicio) {
		this.indiceWsgServicio = indiceWsgServicio;
		WsgServicio wsgServicio = this.getWsgServicios().get(
				indiceWsgServicio);
		getIndicador().setIdServicio(new BigDecimal(wsgServicio.getIdServicio()));

	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}


	public void consultarSentencias(){
		filtroValores = new ArrayList<FiltroValorDefault>();
		filtroValores.add(new FiltroValorDefault(null,getIndicador().getIdServicio().toString()));
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




	public List<UsuarioGrupo> getUsuarioGrupos() {
		return usuarioGrupos;
	}

	public void setUsuarioGrupos(List<UsuarioGrupo> usuarioGrupos) {
		this.usuarioGrupos = usuarioGrupos;
	}

	public Indicador getIndicador() {
		return indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public DualListModel<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(DualListModel<Grupo> grupos) {
		this.grupos = grupos;
	}

	public void setRegistraLog(RegistraLog registraLog) {
		this.registraLog = registraLog;
	}
    
	
	
    
}
