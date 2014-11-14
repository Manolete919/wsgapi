package pnl.webservice.integracion;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.axis.message.MessageElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pnl.wsg.Bind;
import pnl.wsg.GenericoPortType;
import pnl.wsg.GenericoServiceLocator;
import pnl.wsg.Servicio;
import pnl.wsg.ServicioElement;
import pnl.wsg.ServicioResponseElement;


public class ConsultaGenerico {




	
	public List<Generico> consultaDatosWsg(
			Document documento,
			long idServicio, String usuario, String clave) {
		// TODO Auto-generated method stub
		
		List<Generico> listaGenerica = new ArrayList<Generico>();

		
		
		try {
			
			GenericoServiceLocator localizadorDeServicio = new GenericoServiceLocator();
			GenericoPortType genericoPortType = localizadorDeServicio.getGenericoPortTypePort();

			
			ServicioElement parameters = new ServicioElement();
			
			parameters.setIdServicio(idServicio);
			parameters.setUsuario(usuario);
			parameters.setClave(clave);
			
			
			Bind sentencias_binds = new Bind();
			MessageElement[] _any = new MessageElement[1];
			
			//NodeList sentenciasList = doc.getChildNodes().item(0).getChildNodes();
			
			Element element = (Element) documento.getDocumentElement().getChildNodes().item(0);
			_any[0] = new MessageElement(element);
			
			sentencias_binds.set_any(_any);
			parameters.setSentencias_binds(sentencias_binds);
			
			
			ServicioResponseElement servicioResponseElement = genericoPortType.obtenerXml(parameters);
			
			Servicio servicio = servicioResponseElement.getResult();
			
			
		

			Iterator it = servicio.get_any()[0].getChildElements();

			while (it.hasNext()) {
				MessageElement me = (MessageElement) it.next();
				System.out.println("RAIZ = " + me.getName());
				Iterator it2 = me.getChildElements();
				
				
				
				//it2.hasNext();
				MessageElement me2 = (MessageElement) it2.next();
				Object objetoX = me2.getValue();
				
				//it2.hasNext();
				MessageElement me3 = (MessageElement) it2.next();
				int objetoY = Integer.parseInt(me3.getValue());
				
				//System.out.println("ÖBJETO X "+ objetoX + " OBJETO Y " + objetoY);
				listaGenerica.add(new Generico(objetoX,objetoY));
				

			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaGenerica;
		
	}
		
	
	public List<WsgServicio> consultaWsgServiciosDeUsuario(Document documento, long idServicio, String usuario, String clave) {
		// TODO Auto-generated method stub
		
		List<WsgServicio> wsgServicios = new ArrayList<WsgServicio>();
		
		try {
			
			GenericoServiceLocator localizadorDeServicio = new GenericoServiceLocator();
			GenericoPortType genericoPortType = localizadorDeServicio.getGenericoPortTypePort();

			
			ServicioElement parameters = new ServicioElement();
			
			parameters.setIdServicio(idServicio);
			parameters.setUsuario(usuario);
			parameters.setClave(clave);
			
			
			Bind sentencias_binds = new Bind();
			MessageElement[] _any = new MessageElement[1];
			
			
			Element element = (Element) documento.getDocumentElement().getChildNodes().item(0);
			_any[0] = new MessageElement(element);
			
			sentencias_binds.set_any(_any);
			parameters.setSentencias_binds(sentencias_binds);
			
			
			ServicioResponseElement servicioResponseElement = genericoPortType.obtenerXml(parameters);
			
			Servicio servicio = servicioResponseElement.getResult();
		

			Iterator it = servicio.get_any()[0].getChildElements();

			while (it.hasNext()) {
				MessageElement me = (MessageElement) it.next();
				
				Iterator it2 = me.getChildElements();
				
				WsgServicio wsgServicio = new WsgServicio();
				
				//it2.hasNext();
				MessageElement me2 = (MessageElement) it2.next();
				wsgServicio.setIdServicio(new Long(me2.getValue()));
				
				//it2.hasNext();
				MessageElement me3 = (MessageElement) it2.next();
				wsgServicio.setDescripcion(me3.getValue());
				
				
				wsgServicios.add(wsgServicio);
				

			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return wsgServicios;
		
	}
	
	public String consultaWsgQueryPorIdServicio(Document documento, long idServicio, String usuario, String clave) {
		// TODO Auto-generated method stub
		
		List<WsgServicio> wsgServicios = new ArrayList<WsgServicio>();
		String query = "";
		try {
			
			GenericoServiceLocator localizadorDeServicio = new GenericoServiceLocator();
			GenericoPortType genericoPortType = localizadorDeServicio.getGenericoPortTypePort();

			
			ServicioElement parameters = new ServicioElement();
			
			parameters.setIdServicio(idServicio);
			parameters.setUsuario(usuario);
			parameters.setClave(clave);
			
			
			Bind sentencias_binds = new Bind();
			MessageElement[] _any = new MessageElement[1];
			
			
			Element element = (Element) documento.getDocumentElement().getChildNodes().item(0);
			_any[0] = new MessageElement(element);
			
			sentencias_binds.set_any(_any);
			parameters.setSentencias_binds(sentencias_binds);
			
			
			ServicioResponseElement servicioResponseElement = genericoPortType.obtenerXml(parameters);
			
			Servicio servicio = servicioResponseElement.getResult();
		

			Iterator it = servicio.get_any()[0].getChildElements();

			
			while (it.hasNext()) {
				MessageElement me = (MessageElement) it.next();
				
				Iterator it2 = me.getChildElements();
				
				
				
				//it2.hasNext();
				MessageElement me2 = (MessageElement) it2.next();
				query = me2.getValue();
				
				

			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return query;
		
	}
		
	

}
