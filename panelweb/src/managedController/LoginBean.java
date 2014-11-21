package managedController;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/*import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;*/
//import org.springframework.security.core.context.SecurityContextHolder;
///Users/manuelgarcia/Oracle/Middleware/Oracle_Home/oracle_common/common/lib

@ManagedBean(name="loginMgmtBean")
@RequestScoped
public class LoginBean {
  
    private String userName = null; 
    private String password = null;
    
    

    //@ManagedProperty(value="#{authenticationManager}")
    //private AuthenticationManager authenticationManager = null;

    public String login() {
    	
    	System.out.println("USUARIO " + this.getUserName() + " PASSWORD " + this.getPassword());
    	
       /* Authentication request = new UsernamePasswordAuthenticationToken(this.getUserName(), this.getPassword());
		
        Authentication result = authenticationManager.authenticate(request);
		SecurityContextHolder.getContext().setAuthentication(result); */
		
		
		
       /*UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(this.getUserName(), this.getPassword());

       
        Authentication authenticate = authenticationManager.authenticate(token);            

        if (authenticate.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } */
		
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check");

        try {
			dispatcher.forward((ServletRequest) context.getRequest(),
			 (ServletResponse) context.getResponse());
			FacesContext.getCurrentInstance().responseComplete();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        
        
        return null;
		
		
		/*ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();

		try {

			context.redirect(context.getRequestContextPath()+"/paginas/graficos/index.xhtml");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    }

    public String cancel() {
        return null;
    }

    public void logout(){
    	
    	
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_logout");

        try {
			dispatcher.forward((ServletRequest) context.getRequest(),
			 (ServletResponse) context.getResponse());
			FacesContext.getCurrentInstance().responseComplete();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
        //SecurityContextHolder.clearContext();
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
		/*ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();

		try {

			context.redirect(context.getRequestContextPath()+"/paginas/general/login.xhtml");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
    }
 
   /* public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
*/
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
 
}