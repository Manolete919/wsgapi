package wsg.bean;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import wsg.modelo.WsgServiciosLog;
import wsg.qualificadores.AuditorGeneral;

import com.google.gson.Gson;

/**
 * Message-Driven Bean implementation class for: WsgServiciosLogMDB
 */
@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/WsgServiciosLogJMSQueue"), 
				@ActivationConfigProperty(propertyName = "connectionFactoryJndiName", propertyValue = "jms/WsgServiciosLogConnectionFactory"),
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "jms/WsgServiciosLogJMSQueue")
@AuditorGeneral
public class WsgServiciosLogMDB implements MessageListener {
	
	@Resource
	private MessageDrivenContext mdc;


	@Inject
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public WsgServiciosLogMDB() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
	public void onMessage(Message message) {

		Gson gson = new Gson();
		TextMessage textMessage = (TextMessage) message;

		String json;
		try {
			json = textMessage.getText();

			WsgServiciosLog wsgServiciosLog = gson.fromJson(json,WsgServiciosLog.class);

			create(wsgServiciosLog);
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			mdc.setRollbackOnly();
			e.printStackTrace();
		}



	}

	public void create(Object object) {
		em.persist(object);
	}

}
