package pnl.servicio;

import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import pnl.interfaz.UsuarioBeanRemote;
import pnl.modelo.Usuario;

@ManagedBean(name="usuarioServicio", eager = true)
@SessionScoped
public class UsuarioServicio implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UsuarioBeanRemote usuarioBeanRemote;
	private Usuario usuario;

	
	@PostConstruct
	public void init(){
		
		usuario = new Usuario();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String idUsuario = auth.getName(); // get logged in username
		
		System.out.println("ID USUARIO " + idUsuario);
		
    	try {
			
			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);

			usuarioBeanRemote = (UsuarioBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/UsuarioBean");
			
			usuario = usuarioBeanRemote.obtieneUsuarioPorId(idUsuario);
			
				

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	public Usuario getUsuario() {
		return usuario;
	}




	
	
	
	
	

}
