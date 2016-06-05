


/**
 * The TextListener class implements the MessageListener
 * interface by defining an onMessage method that displays
 * the contents of a TextMessage.
 *
 * This class acts as the listener for the SimpleAsynchConsumer
 * class.
 */
import java.util.LinkedList;

import javax.jms.*;


public class Listener implements MessageListener {
    
	//inicializamos las colas que vamos a usar, una por cada sensor
    public LinkedList EGarzonL = new LinkedList();
    public LinkedList EGarzonR = new LinkedList();
    public LinkedList WGarzonL = new LinkedList();
    public LinkedList WGarzonR = new LinkedList();
    public LinkedList NMillanL = new LinkedList();
    public LinkedList NMillanR = new LinkedList();
    public LinkedList SMillanL = new LinkedList();
    public LinkedList SMillanR = new LinkedList();
    
    int vehicleNum = 1;
    int time = 0;
    
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
                this.populate(new Integer(msg.getText()).intValue());
                System.out.println("Nuevo vehiculo detectado por sensor: " + msg.getText());
            } else {
                System.out.println("Message is not a TextMessage");
            }
        } catch (JMSException e) {
            System.out.println("JMSException in onMessage(): " + e.toString());
        } catch (Throwable t) {
            System.out.println("Exception in onMessage():" + t.getMessage());
        }
    }
    
    private void populate(int sensorId) {
    	LinkedList queue = null;
    	Vehicle vehicle = null; 
    	switch (sensorId) {
        case 1:
        	queue = EGarzonL; 
        	vehicle = new Vehicle(vehicleNum, "E", "Garzon", "L");
        	vehicleNum++;
            break;
        case 2:
        	queue = EGarzonR; 
        	vehicle = new Vehicle(vehicleNum, "E", "Garzon", "R");
        	vehicleNum++;
            break;
        case 3:  
        	queue = WGarzonL;
        	vehicle = new Vehicle(vehicleNum, "W", "Garzon", "L");
        	vehicleNum++;
            break;
        case 4:
        	queue = WGarzonR; 
        	vehicle = new Vehicle(vehicleNum, "W", "Garzon", "R");
        	vehicleNum++;
            break;
        case 5:
        	queue = NMillanL;
        	vehicle = new Vehicle(vehicleNum, "N", "Millan", "L");
        	vehicleNum++;
            break;
        case 6:  
        	queue = NMillanR; 
        	vehicle = new Vehicle(vehicleNum, "N", "Millan", "R");
        	vehicleNum++;
            break;
        case 7:  
        	queue = SMillanL; 
        	vehicle = new Vehicle(vehicleNum, "S", "Millan", "L");
        	vehicleNum++;
            break;
        case 8:  
        	queue = SMillanR; 
        	vehicle = new Vehicle(vehicleNum, "S", "Millan", "R");
        	vehicleNum++;
            break;
        default: 
        	System.out.println("Invalid sensorId: " + sensorId);
            break;
    	}
    	//Se crea el vehículo y se agrega a la cola
    	if (queue!=null && vehicle != null)
    		queue.add(vehicle);
    }
}
