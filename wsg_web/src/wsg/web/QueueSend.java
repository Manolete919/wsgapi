package wsg.web;

import java.util.Hashtable;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import wsg.modelo.WsgServiciosLog;


import com.google.gson.Gson;


public class QueueSend implements Runnable {
	/**
	 * 
	 */


	// Defines the JNDI context factory.
	public final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";



	// Defines the queue.
	public final static String QUEUE = "jms/WsgServiciosLogJMSQueue";
	
	// Defines the JMS context factory.
	public final static String JMS_FACTORY="jms/WsgServiciosLogConnectionFactory";
	 // Defines the queue.

	private QueueConnectionFactory qconFactory;
	private QueueConnection qcon;
	private QueueSession qsession;
	private QueueSender qsender;
	private Queue queue;
	private TextMessage msg;
	WsgServiciosLog wsgServiciosLog;
	
	Context ctx = null;
	Hashtable<String, String> ht = new Hashtable<String, String>();

	Gson gson = new Gson();


	
	public  QueueSend(WsgServiciosLog wsgServiciosLog,String urlEJB) {
		
		ht.put(Context.INITIAL_CONTEXT_FACTORY,JNDI_FACTORY);
		ht.put(Context.PROVIDER_URL, urlEJB);
		this.wsgServiciosLog = wsgServiciosLog;
	}



	@Override
	public void run()  {
		try {
			ctx = new InitialContext(ht);
			
			
			qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
			qcon = qconFactory.createQueueConnection();
			
			
			qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			queue = (Queue) ctx.lookup(QUEUE);
			//Creo un productor de mensajes
			qsender = qsession.createSender(queue);
			//Creo el request de mensaje
			msg = qsession.createTextMessage();
			qcon.start();
			
			//Seteo el valor del mensaje
			msg.setText(gson.toJson(wsgServiciosLog));
	        qsender.send(msg);
	        
	        qsender.close();	        
	        qsession.close();
	        qcon.close();
	        

		
		} catch (NamingException e) 
		{
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ctx.close();
			} catch (Exception e) {
		
			}

		}

		
	}




}
