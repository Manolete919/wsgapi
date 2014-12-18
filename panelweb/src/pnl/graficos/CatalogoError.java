package pnl.graficos;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;


public class CatalogoError implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Properties propiedades = new Properties();
	
	public Properties getPropiedades() {
		InputStream iostream = Thread.currentThread().getContextClassLoader().getResourceAsStream("properties/panel-web-ear.properties");
		try {
			propiedades.load(iostream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propiedades; 
		
	}
	
	public String obtenerMensajeDeErrorPorNombrePropiedad(String proveedorBase, int codigoError){
		
		
		String erroresConocidos = null;
		
		if(codigoError == -1){
			erroresConocidos =  getPropiedades().getProperty("pnl.wsg.servicioUsuarioNoExiste");
			if(erroresConocidos==null){
				return "Servicio o usuario no disponibles";
			}
			return erroresConocidos;
			
		}
		
		if(codigoError == -2){
			erroresConocidos = getPropiedades().getProperty("pnl.wsg.claveIncorrecta"); 
			if(erroresConocidos==null){
				return "La clave no coincide";
			}
			return erroresConocidos;
		}
		
		if(codigoError == -3){
			erroresConocidos = getPropiedades().getProperty("pnl.wsg.servicioUsuarioInactivo"); 
			if(erroresConocidos==null){
				return "Servicio o usuario inactivos";
			}
			return erroresConocidos;
		}
		
		if(codigoError == -4){
			erroresConocidos =  getPropiedades().getProperty("pnl.wsg.cuentaBloqueada"); 
			if(erroresConocidos==null){
				return "Cuenta bloqueada";
			}
			return erroresConocidos;
		}
		
		if(codigoError == -5){
			erroresConocidos = getPropiedades().getProperty("pnl.wsg.errorNoCatalogado");
			if(erroresConocidos==null){
				return "Error no catalogado";
			}
			return erroresConocidos;
		}
		
		
		if(codigoError == -6){
			erroresConocidos =  getPropiedades().getProperty("pnl.wsg.nombreNoEncontrado"); 
			if(erroresConocidos==null){
				return "Fuente de datos no encontrada, o no se ha configurado";
			}
			return erroresConocidos;
		}
		
		if(codigoError == -38170){
			erroresConocidos = getPropiedades().getProperty("pnl.wsg.exito"); 
			if(erroresConocidos==null){
				return "Exito";
			}
			return erroresConocidos;
		}
		
		String mensaje = getPropiedades().getProperty(proveedorBase+"."+codigoError);
		
		if(mensaje == null ){
			
			String mensaje2= getPropiedades().getProperty("pnl.wsg.errorNoCatalogado");
			
			if(mensaje2 == null){
				
				return "Error producido con código: " + codigoError +", no catalogado, proveedor->: "+proveedorBase+", contacte con sistemas";
			}
			
			return mensaje2;
		
		}
		
		return mensaje;
		
		
		
	}

}
