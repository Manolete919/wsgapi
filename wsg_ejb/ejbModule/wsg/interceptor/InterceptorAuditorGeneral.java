package wsg.interceptor;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

import wsg.qualificadores.AuditorGeneral;


@AuditorGeneral
@Interceptor
public class InterceptorAuditorGeneral implements Serializable {

	private static final long serialVersionUID = -8204333469527307739L;

	Logger logger = Logger.getLogger("AuditorGeneral");

	@AroundInvoke
	public Object auditoriaGeneral(InvocationContext ctx) throws Exception {

		long start = System.currentTimeMillis();

		String target = String.format("%s.%s",ctx.getMethod().getDeclaringClass().getSimpleName(), ctx.getMethod().getName());
		Object[] params = ctx.getParameters();
		Object object =null;
		
		String estadoEjecucion="Exitosa";

		try {

			
			logger.debug("********************************************************************");
			logger.debug("* <" + target + "> : Iniciando ejecución");
			logger.debug("********************************************************************");

			StringBuilder sbParams = new StringBuilder();
			sbParams.append("[");
			for (int i = 1; i < params.length; i++) {
				sbParams.append(params[i].toString());
				sbParams.append(";");
			}
			sbParams.append("]");
			logger.debug("<" + target + "> - Parámetros:" + sbParams.toString());

			object = ctx.proceed();
			return object;
		} catch (Exception e) {
			estadoEjecucion="Con errores";			
			logger.error("<" + target + "> (Exception)-->"+e.getMessage());
			throw e;

		}finally{
			long stop = System.currentTimeMillis();
			logger.debug("********************************************************************");
			logger.debug("* <" + target + "> : Finalizando Ejecución ["+estadoEjecucion+"].");
			logger.debug("*------------------------------------------------------------------*");
			logger.debug("* <" + target + "> : El tiempo de respuesta fue de "
					+ String.valueOf(stop - start) + " ms.");
			logger.debug("********************************************************************");

			
		}
		
		
	}

}
