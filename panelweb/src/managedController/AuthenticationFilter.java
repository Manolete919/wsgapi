package managedController;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.DispatcherType;

import org.apache.log4j.MDC;

@WebFilter(dispatcherTypes = {DispatcherType.ERROR,DispatcherType.REQUEST}, 
urlPatterns = {"/paginas/*"})  
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
    	
    	
    	//HttpServletRequest req = (HttpServletRequest) request;
		//HttpServletResponse res = (HttpServletResponse) response;

        try {
        	
        	System.out.println("FILTRO ACTIVO");

            MDC.put("userName", "MANUEL EXITO");

            chain.doFilter(request, response);

        } finally {
            MDC.remove("userName");
        }

    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}