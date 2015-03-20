package wsg.web;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wsg.conexion.Conexion;
import wsg.interfaz.WsgServicioBeanRemote;
import wsg.interfaz.WsgServiciosLogBeanRemote;
import wsg.interfaz.WsgUsuarioServicioBeanRemote;
import wsg.modelo.WsgServicio;
import wsg.modelo.WsgServiciosLog;
import wsg.modelo.WsgUsuario;
import wsg.modelo.WsgUsuarioServicio;
import wsg.modelo.WsgUsuarioServicioPK;
import wsg.query.EjecutaQuery;
import wsg.response.Servicio;


@WebService(serviceName = "GenericoService",  targetNamespace = "http://axis/EISApiOnlineWS.wsdl/types/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class ServicioWebGenerico {
	
	
	static final Logger logger = LogManager.getLogger(ServicioWebGenerico.class);
	
	@Resource(name="wsContext") WebServiceContext wsCtxt;
	
	@Inject EjecutaQuery q;
	

	
	WsgServiciosLogBeanRemote wsgServiciosLogBeanRemote = null;

	String urlEJB = "";
	
	String ipLocal;
	
	int puertoLocal;

	public ServicioWebGenerico() {
	}

	Properties propiedades = new Properties();
	

	private Properties getPropiedades() {
		InputStream iostream = Thread.currentThread().getContextClassLoader().getResourceAsStream("properties/wsg-war-ear.properties");
		try {
			propiedades.load(iostream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propiedades; 
		
	}
	
	
	@WebMethod(operationName = "obtenerXml", action = "http://axis/EISApiOnlineWS.wsdl/types//obtenerXml")
	@WebResult(targetNamespace = "http://axis/EISApiOnlineWS.wsdl/types/", name = "result")
	@RequestWrapper(localName = "servicioElement", targetNamespace = "http://axis/EISApiOnlineWS.wsdl/types/")
	@ResponseWrapper(localName = "servicioResponseElement", targetNamespace = "http://axis/EISApiOnlineWS.wsdl/types/")
	public Servicio obtieneXml(
			@XmlElement(required = true, namespace = "http://axis/EISApiOnlineWS.wsdl/types/") @WebParam(name = "idServicio") long idServicio,
			@XmlElement(required = true, namespace = "http://axis/EISApiOnlineWS.wsdl/types/") @WebParam(name = "usuario") String usuario,
			@XmlElement(required = true, namespace = "http://axis/EISApiOnlineWS.wsdl/types/") @WebParam(name = "clave") String clave,
			@XmlElement(required = false, namespace = "http://axis/EISApiOnlineWS.wsdl/types/") @WebParam(name = "sentencias_binds") Bind sentencias_binds) {
	
		//encriptar la clave
		clave = Utileria.generateHash(clave);
		
	    MessageContext msgCtxt = wsCtxt.getMessageContext();
	    HttpServletRequest req = (HttpServletRequest)msgCtxt.get(MessageContext.SERVLET_REQUEST);

		ipLocal = req.getLocalAddr();
		
		puertoLocal = req.getLocalPort();
		
		Servicio servicio = new Servicio();
		WsgServiciosLog wsgServiciosLog = new WsgServiciosLog();
		wsgServiciosLog.setIdServicio(new BigDecimal(idServicio));
		wsgServiciosLog.setUsuario(usuario);
		wsgServiciosLog.setClave(clave);
		wsgServiciosLog.setFechaInicio(new Date());
		
		//RECUPERAR TODOS LOS PARAMETRSO XML
		Utileria t = new Utileria();
		List<ListaParametros> arrayDeListasDeParametros;
		String xmlString = "";
		WsgServicioBeanRemote wsgServicioBeanRemote;
		WsgUsuarioServicioBeanRemote wsgUsuarioServicioBeanRemote;
		WsgUsuario wsgUsuario;
		WsgUsuarioServicio wsgUsuarioServicio;
		WsgServicio wsgServicio;
		
		
		
		urlEJB = propiedades.getProperty("wsgwar.jndi.modulo.ejb.url");
		
		//si no existe la propiedad definida, se asume que el modulo EJB, está en el mismo servidor
		if(urlEJB == null){
			urlEJB = "t3://"+ipLocal+":"+puertoLocal;
		}
		
		
		if(urlEJB != null){
			//si no existe valor para la propiedad definida, se asume que el modulo EJB, está en el mismo servidor
			if(urlEJB.trim().equals("")){
				urlEJB = "t3://"+ipLocal+":"+puertoLocal;
			}
			
		}
		
		
		
		

		DataSource ds = null;
		
		Context ctx = null;
		String vendor="ninguno";
		Hashtable<String, String> ht = new Hashtable<String, String>();
		ht.put(Context.INITIAL_CONTEXT_FACTORY,
				"weblogic.jndi.WLInitialContextFactory");
		ht.put(Context.PROVIDER_URL, urlEJB);
		
		
	    try {
	    	
			arrayDeListasDeParametros = t.extraerListaDeParametros(sentencias_binds);
			
			xmlString =  t.jaxbObjectToXMLString(sentencias_binds);
			
			ctx = new InitialContext(ht);
			
			wsgServicioBeanRemote = (WsgServicioBeanRemote)ctx.lookup("java:global.wsg_ejb_ear.wsg_ejb/WsgServicioBean"); 
			
			wsgServiciosLogBeanRemote = (WsgServiciosLogBeanRemote)ctx.lookup("java:global.wsg_ejb_ear.wsg_ejb/WsgServiciosLogBean"); 
			
			
			//wsgServicio = wsgServicioBeanRemote.find(idServicio);
			
			wsgServicio = wsgServicioBeanRemote.buscarServicioPorIdActivoYVigente(idServicio);
			
			
			
			//preguntar si tiene acceso, caso contrario devolver mensaje
			wsgUsuarioServicioBeanRemote = (WsgUsuarioServicioBeanRemote)ctx.lookup("java:global.wsg_ejb_ear.wsg_ejb/WsgUsuarioServicioBean"); 

			WsgUsuarioServicioPK id = new WsgUsuarioServicioPK();
			id.setIdServicio(idServicio);
			id.setIdUsuario(usuario);
			
			wsgUsuarioServicio =  wsgUsuarioServicioBeanRemote.find(id);			
			
			
			if(wsgUsuarioServicio == null || wsgServicio == null ){
				
				servicio.setMensajeError(getPropiedades().getProperty("wsgwar.servicioUsuarioNoExiste"));
				logger.error(servicio.getMensajeError());
				servicio.setCodigoError(-1);
				servicio.setProveedorBase(vendor);
				//registro log bd
				
				wsgServiciosLog.setXml(xmlString);
				wsgServiciosLog.setCodError(new BigDecimal(servicio.getCodigoError()));
				wsgServiciosLog.setMsgError(servicio.getMensajeError());
				wsgServiciosLog.setFechaFin(new Date());
				bitacorizar(wsgServiciosLog);
				return servicio;
				
				
				
			}
			
			ds = Conexion.obtenerFuenteDeDatos(wsgServicio.getWsgJndi().getJndi());
			
			wsgUsuario = wsgUsuarioServicio.getWsgUsuario(); 
			
			
			if(!wsgUsuario.getClave().equals(clave)){
				servicio.setMensajeError(getPropiedades().getProperty("wsgwar.claveIncorrecta"));
				logger.error(servicio.getMensajeError());
				servicio.setCodigoError(-2);
				servicio.setProveedorBase(vendor);

				//registro log bd
	
				wsgServiciosLog.setXml(xmlString);
				wsgServiciosLog.setCodError(new BigDecimal(servicio.getCodigoError()));
				wsgServiciosLog.setMsgError(servicio.getMensajeError());
				
				//Grabar actividades del WS
				wsgServiciosLog.setFechaFin(new Date());
				bitacorizar(wsgServiciosLog);
				return servicio;
				

				
			}
				
			Connection conn=null;
			
			if(wsgUsuario.getCuentaNoBloqueada().equals("S")){
				
				if(wsgUsuarioServicio.getEstado().equals("A")){
					
					try{
						//abrir conexion
						
						conn = ds.getConnection();
						vendor = conn.getMetaData().getDatabaseProductName().toLowerCase();
						
						System.out.println("PROVEEDOR DE BASES DE DATOS--> "+vendor);
						
						if(arrayDeListasDeParametros.isEmpty()){
							servicio.setXml(q.obtenerServicioEnDocument(conn, wsgServicio, new ArrayList<String>() ));
						}else{
							servicio.setXml(q.obtenerServicioEnDocument(conn, wsgServicio, arrayDeListasDeParametros.get(0).getParametros()));
						}
						
						//Todo exitoso
						
						
						servicio.setCodigoError(-38170);
						servicio.setProveedorBase(vendor);
						servicio.setMensajeError(getPropiedades().getProperty("wsgwar.resultadoExitoso"));
						wsgServiciosLog.setProveedor(servicio.getProveedorBase());
						wsgServiciosLog.setSentenciaSql(wsgServicio.getWsgQuery().getQuery());
						//registro log bd
						wsgServiciosLog.setXml(xmlString);
						wsgServiciosLog.setResultado(q.getResultadoTotal());
									
						//Grabar actividades del WS
		
						wsgServiciosLog.setCodError(new BigDecimal(servicio.getCodigoError()));
						wsgServiciosLog.setMsgError(servicio.getMensajeError());
						wsgServiciosLog.setFechaFin(new Date());
						bitacorizar(wsgServiciosLog);
						return servicio;
										
						
					} catch (SQLException sqlError) {
						
						
						System.out.println("PROPIEDAD-->" + vendor+"."+sqlError.getErrorCode());
						
						
						servicio.setProveedorBase(vendor);
						servicio.setCodigoError(sqlError.getErrorCode());
						servicio.setMensajeError(sqlError.getMessage());
				
						logger.error(sqlError.getErrorCode());
						logger.error(sqlError.getMessage());
						
						//Grabar actividades del WS
				
						wsgServiciosLog.setCodError(new BigDecimal(sqlError.getErrorCode()));
						wsgServiciosLog.setMsgError(sqlError.getMessage());
						wsgServiciosLog.setFechaFin(new Date());
						wsgServiciosLog.setProveedor(vendor);
						wsgServiciosLog.setSentenciaSql(wsgServicio.getWsgQuery().getQuery());
						bitacorizar(wsgServiciosLog);
						return servicio;
	
					} catch (Exception e0) {
						servicio.setCodigoError(-5);
						servicio.setMensajeError(e0.getMessage());
						servicio.setProveedorBase(vendor);
						logger.error(e0);
						//Grabar actividades del WS
			
						wsgServiciosLog.setCodError(new BigDecimal(servicio.getCodigoError()));
						wsgServiciosLog.setMsgError(servicio.getMensajeError());
						wsgServiciosLog.setFechaFin(new Date());
						bitacorizar(wsgServiciosLog);
						return servicio;
	
					} finally {
						if (conn != null)
							try {
								conn.close();
							} catch (SQLException e) {
								e.printStackTrace();
								servicio.setCodigoError(e.getErrorCode());
								servicio.setMensajeError(e.getMessage());
								servicio.setProveedorBase(vendor);
								logger.error(e);
								//Grabar actividades del WS								
					
								wsgServiciosLog.setCodError(new BigDecimal(servicio.getCodigoError()));
								wsgServiciosLog.setMsgError(servicio.getMensajeError());
								wsgServiciosLog.setProveedor(servicio.getProveedorBase());
								wsgServiciosLog.setFechaFin(new Date());
								bitacorizar(wsgServiciosLog);
								return servicio;
				
								
							}
					}		
					

				
				
				
				}else{
					servicio.setMensajeError(getPropiedades().getProperty("wsgwar.servicioUsuarioInactivo"));
					servicio.setCodigoError(-3);
					servicio.setProveedorBase(vendor);
					logger.error(servicio.getMensajeError());

					//registro log bd
					wsgServiciosLog.setXml(xmlString);
					
					//Grabar actividades del WS								

					wsgServiciosLog.setCodError(new BigDecimal(servicio.getCodigoError()));
					servicio.setProveedorBase(vendor);
					wsgServiciosLog.setMsgError(servicio.getMensajeError());
					wsgServiciosLog.setFechaFin(new Date());
					bitacorizar(wsgServiciosLog);
					return servicio;

					
					
					
				}
			
			}else{
				servicio.setMensajeError(getPropiedades().getProperty("wsgwar.cuentaBloqueada"));
				logger.error(servicio.getMensajeError());
				servicio.setCodigoError(-4);
				servicio.setProveedorBase(vendor);

				//registro log bd
				wsgServiciosLog.setXml(xmlString);
				
				
				//Grabar actividades del WS								
	
				wsgServiciosLog.setCodError(new BigDecimal(servicio.getCodigoError()));
				wsgServiciosLog.setMsgError(servicio.getMensajeError());
				wsgServiciosLog.setFechaFin(new Date());
				bitacorizar(wsgServiciosLog);
				return servicio;

				
			}
			

	   
	    }catch(NameNotFoundException e0 ){

			e0.printStackTrace();
			servicio.setCodigoError(-6);
			servicio.setProveedorBase(vendor);
			servicio.setMensajeError(e0.getMessage());
			logger.error(e0);
			
			ds = null;
			
			//Grabar actividades del WS								
			try{	
				wsgServiciosLog.setCodError(new BigDecimal(servicio.getCodigoError()));
				wsgServiciosLog.setMsgError(servicio.getMensajeError());
				wsgServiciosLog.setFechaFin(new Date());
				bitacorizar(wsgServiciosLog);
				return servicio;
			}catch (Exception ex) {
												
				servicio.setCodigoError(-100);
				servicio.setProveedorBase(vendor);
				servicio.setMensajeError(ex.getMessage());
				logger.error(ex);
				return servicio;
			}
			
			
		}catch(SQLException e){
			
			servicio.setCodigoError(e.getErrorCode());
			servicio.setProveedorBase(vendor);
			servicio.setMensajeError(e.getMessage());
			logger.error("Codigo de Error" + e.getErrorCode());
			logger.error(e);
			e.printStackTrace();
			//Grabar actividades del WS	
	
			wsgServiciosLog.setCodError(new BigDecimal(servicio.getCodigoError()));
			wsgServiciosLog.setMsgError(servicio.getMensajeError());
			wsgServiciosLog.setFechaFin(new Date());
			bitacorizar(wsgServiciosLog);
			return servicio;


		}catch (Exception e0) {
			// TODO Auto-generated catch block
			e0.printStackTrace();
			servicio.setCodigoError(-5);
			servicio.setMensajeError(e0.getMessage());
			servicio.setProveedorBase(vendor);
			ds = null;
			logger.error(e0);
			//Grabar actividades del WS		
	
			wsgServiciosLog.setCodError(new BigDecimal(servicio.getCodigoError()));
			wsgServiciosLog.setMsgError(servicio.getMensajeError());
			wsgServiciosLog.setFechaFin(new Date());
			bitacorizar(wsgServiciosLog);
			return servicio;
			
			
		}
	    
	}
	
	/*private Throwable getLastThrowable(Exception e) {
		Throwable t = null;
		
		for(t = e.getCause(); t.getCause() != null; t = t.getCause());
		return t;
	} */
	
	private void bitacorizar(WsgServiciosLog wsgServiciosLog){
		
		//todo exitoso
		//inicio del metdo
		long startTime4 = System.currentTimeMillis();

		
		Runnable qs1 = new QueueSend(wsgServiciosLog,urlEJB);
		try {
			
			new Thread(qs1).start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long stopTime4 = System.currentTimeMillis();
	    long elapsedTime4 = stopTime4 - startTime4;
	
	    logger.info("bitacorizar: tiempo->"+ elapsedTime4 + " ms" );


	}

}
