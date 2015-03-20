package wsg.interceptor;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;




import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wsg.qualificadores.AuditorGeneralWeb;


@AuditorGeneralWeb
@Interceptor
public class InterceptorAuditorGeneralWeb implements Serializable {

	private static final long serialVersionUID = -8204333469527307739L;

	Logger logger = LogManager.getLogger("AuditorWSG_WEB");

	@AroundInvoke
	public Object auditoriaGeneral(InvocationContext ctx) throws Exception {

		long start = System.currentTimeMillis();

		String target = String.format("%s.%s", 
				ctx.getMethod().getDeclaringClass().getSimpleName(), ctx.getMethod().getName());
		Object[] params = ctx.getParameters();
		Object object =null;
		
		//String estadoEjecucion="Exitosa";

		try {

			
			//logger.debug("********************************************************************");
			//logger.debug("* <" + target + "> : Iniciando ejecución");
			//logger.debug("********************************************************************");
			
			//System.out.println("INICIANDO INTERCEPTOR " + target );

			StringBuilder sbParams = new StringBuilder();
			sbParams.append("[");
			for (int i = 1; i < params.length; i++) {
				sbParams.append((params[i]==null)?"null":params[i].toString());
				sbParams.append(";");
			}
			sbParams.append("]");
			//logger.debug("<" + target + "> - Parámetros:" + sbParams.toString());

			object = ctx.proceed();
			return object;
		} catch (Exception e) {
			//estadoEjecucion="Con errores";			
			logger.error("<" + target + "> (Exception)-->"+e.getMessage());
			throw e;

		}finally{
			long stop = System.currentTimeMillis();
			//logger.debug("********************************************************************");
			//logger.debug("* <" + target + "> : Finalizando Ejecución ["+estadoEjecucion+"].");
			//logger.debug("*------------------------------------------------------------------*");
			logger.debug("* <" + target + "> : tiempo-> "
					+ String.valueOf(stop - start) + " ms.");
			//logger.debug("********************************************************************");

			//System.out.println("FINALIZANDO INTERCEPTOR EN: "  + target + " " + String.valueOf(stop - start) + " ms.");
		}
		
		
	}

}
