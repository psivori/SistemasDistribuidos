


/**
 * The TextListener class implements the MessageListener
 * interface by defining an onMessage method that displays
 * the contents of a TextMessage.
 *
 * This class acts as the listener for the SimpleAsynchConsumer
 * class.
 */
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


public class ListenerControlador implements MessageListener {
    
	public int prioridadGarzon = 0; 
    
	/**
     * Casts the message to a TextMessage and displays its text.
     *
     * @param message     the incoming message
     */
    public void onMessage(Message message) {
        TextMessage msg = null;

        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                prioridadGarzon = new Integer(msg.getText()).intValue();
                System.out.println("Se recibio mensaje del Controlador para dar prioridad al paso de omnibus.");
            } else {
                System.out.println("Message is not a TextMessage");
            }
        } catch (JMSException e) {
            System.out.println("JMSException in onMessage(): " + e.toString());
        } catch (Throwable t) {
            System.out.println("Exception in onMessage():" + t.getMessage());
        }
    }
    
}
