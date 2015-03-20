package pnl.interceptor;

import java.io.Serializable;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pnl.qualificadores.AuditorGeneral;


@AuditorGeneral
@Interceptor
public class InterceptorAuditorGeneral implements Serializable {

	private static final long serialVersionUID = -8204333469527307739L;

	Logger logger = LogManager.getLogger("Panel_EJB");

	@AroundInvoke
	public Object auditoriaGeneral(InvocationContext ctx) throws Exception {

		long start = System.currentTimeMillis();

		String target = String.format("%s.%s",ctx.getMethod().getDeclaringClass().getSimpleName(), ctx.getMethod().getName());
		Object[] params = ctx.getParameters();
		Object object =null;
		
		

		try {

	
			StringBuilder sbParams = new StringBuilder();
			sbParams.append("[");
			for (int i = 1; i < params.length; i++) {
				sbParams.append(params[i]);
				sbParams.append(";");
			}
			sbParams.append("]");
			if(sbParams != null)
				logger.debug("<" + target + "> - Parametros:" + sbParams.toString());

			object = ctx.proceed();
			return object;
		} catch (Exception e) {
		
			logger.error("<" + target + "> (Exception)-->"+e.getMessage());
			throw e;

		}finally{
			long stop = System.currentTimeMillis();
			logger.debug("<" + target + "> finalizo en "
					+ String.valueOf(stop - start) + " ms.");
					
		}
		
		
	}

}
