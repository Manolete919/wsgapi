package spring.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pnl.interfaz.RolBeanRemote;
import pnl.interfaz.UsuarioBeanRemote;
import pnl.modelo.Rol;
import pnl.modelo.Usuario;



@Service
@Transactional(readOnly=true)
public class CustomUserDetailsService implements UserDetailsService ,Serializable {
	private static final long serialVersionUID = 1L;
	Logger logger = LogManager.getLogger(CustomUserDetailsService.class.getName());

    private List<Rol> roles;
    private Usuario usuario;
    private RolBeanRemote rolBeanRemote;
    private UsuarioBeanRemote usuarioBeanRemote;

    public UserDetails loadUserByUsername(String idUsuario) throws UsernameNotFoundException {
       	
	     
   	try {
			
			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);

			rolBeanRemote = (RolBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/RolBean");
			usuarioBeanRemote = (UsuarioBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/UsuarioBean");
			
			usuario = usuarioBeanRemote.obtieneUsuarioPorId(idUsuario);
			roles = rolBeanRemote.obtieneRolesPorUsuarioPorId(usuario.getIdUsuario());
			

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
    	
        
       
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new User(
            usuario.getIdUsuario(),
            usuario.getClave(),
            enabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            (Collection<? extends GrantedAuthority>) getAuthorities(roles)
        );
    }
   
    public Collection<? extends GrantedAuthority> getAuthorities(List<Rol> roles2) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(roles2));
        return authList;
    }
   
    public List<String> getRoles(List<Rol> roles) {

        List<String> roles2 = new ArrayList<String>();

        for(Rol rol : roles){
        	roles2.add(rol.getRol());
        }
     
        return roles2;
    }
   
    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles2) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
       
        for (String role : roles2) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

} 