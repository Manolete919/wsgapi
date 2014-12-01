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
		
		String mensaje = null;
		
		if(codigoError == -1){
			mensaje = getPropiedades().getProperty("pnl.wsg.servicioUsuarioNoExiste"); 
			if(mensaje == null){
				return "Error codigo: " +codigoError+" no catalogado";
			}else{
				return mensaje;
			}
		}
		
		if(codigoError == -2){
			mensaje = getPropiedades().getProperty("pnl.wsg.claveIncorrecta"); 
			if(mensaje == null){
				return "Error codigo: " +codigoError+" no catalogado";
			}else{
				return mensaje;
			}
		}
		
		if(codigoError == -3){
			mensaje = getPropiedades().getProperty("pnl.wsg.servicioUsuarioInactivo"); 
			if(mensaje == null){
				return "Error codigo: " +codigoError+" no catalogado";
			}else{
				return mensaje;
			}
		}
		
		if(codigoError == -4){
			mensaje = getPropiedades().getProperty("pnl.wsg.cuentaBloqueada"); 
			if(mensaje == null){
				return "Error codigo: " +codigoError+" no catalogado";
			}else{
				return mensaje;
			}
		}
		
		if(codigoError == -5){
			mensaje = getPropiedades().getProperty("pnl.wsg.errorNoCatalogado"); 
			
			if(mensaje == null){
				return "Error codigo: " +codigoError+" no catalogado";
			}else{
				return mensaje;
			}
		}
		
		
		if(codigoError == -6){
			
			
			mensaje = getPropiedades().getProperty("pnl.wsg.nombreNoEncontrado");
			if(mensaje == null){
				return "Error codigo: " +codigoError+" no catalogado";
			}else{
				return mensaje;
			}
		
		}
		
		if(codigoError == -38170){
			mensaje = getPropiedades().getProperty("pnl.wsg.exito"); 
			if(mensaje == null){
				return "Error codigo: " +codigoError+" no catalogado";
			}else{
				return mensaje;
			}
		}
		
		mensaje = getPropiedades().getProperty(proveedorBase+"."+codigoError);
		
		if(mensaje == null ){
			return "Error codigo: " +codigoError+" no catalogado";
		}else{
			return mensaje;
		}
		
		
	}

}
