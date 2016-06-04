/**
 * The SimpleAsynchConsumer class consists only of a main
 * method, which receives one or more messages from a queue or
 * topic using asynchronous message delivery.  It uses the
 * message listener TextListener.  Run this program in
 * conjunction with SimpleProducer.
 *
 * Specify a queue or topic name on the command line when you run
 * the program. To end the program, type Q or q on the command
 * line.
 */
import javax.jms.*;
import javax.naming.*;
import java.io.*;


public class Controlador {

	static Listener listener;
	static ListenerSemaforo listenerSemaforo;

	public static void main(String[] args) {
		String destNameSensor = null;
		String destNameSemaforo = null;
		Context jndiContext = null;
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		Session session = null;
		Destination destSensor = null;
		Destination destSemaforo = null;
		MessageConsumer consumerSensor = null;
		MessageConsumer consumerSemaforo = null;
		InputStreamReader inputStreamReader = null;
		char answer = '\0';

		destNameSensor = "jms/Topic";
		System.out.println("Destination name is " + destNameSensor);
		destNameSemaforo = "jms/Queue";
		System.out.println("Destination name is " + destNameSemaforo);

		/*
		 * Create a JNDI API InitialContext object if none exists
		 * yet.
		 */
		try {
			jndiContext = new InitialContext();
		} catch (NamingException e) {
			System.out.println("Could not create JNDI API context: " + e.toString());
			System.exit(1);
		}

		/*
		 * Look up connection factory and destination.  If either
		 * does not exist, exit.  If you look up a
		 * TopicConnectionFactory or a QueueConnectionFactory,
		 * program behavior is the same.
		 */
		try {
			connectionFactory = (ConnectionFactory) jndiContext.lookup(
					"jms/ConnectionFactory");
			destSensor = (Destination) jndiContext.lookup(destNameSensor);
			destSemaforo = (Destination) jndiContext.lookup(destNameSemaforo);
		} catch (Exception e) {
			System.out.println("JNDI API lookup failed: " + e.toString());
			System.exit(1);
		}

		/*
		 * Create connection.
		 * Create session from connection; false means session is
		 * not transacted.
		 * Create consumer.
		 * Register message listener (TextListener).
		 * Receive text messages from destination.
		 * When all messages have been received, type Q to quit.
		 * Close connection.
		 */
		try {
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			consumerSensor = session.createConsumer(destSensor);
			consumerSemaforo = session.createConsumer(destSemaforo);
			listener = new Listener();
			listenerSemaforo = new ListenerSemaforo();
			consumerSensor.setMessageListener(listener);
			consumerSemaforo.setMessageListener(listenerSemaforo);
			connection.start();

			while (true) {            	
				if ("verde".equals(listenerSemaforo.luz)){ 
					moveEastWest();
				}else if ("rojo".equals(listenerSemaforo.luz)){ 
					moveNorthSouth();
				}	
			}

		} catch (JMSException e) {
			System.out.println("Exception occurred: " + e.toString());
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
				}
			}
		}
	}

	private static void moveEastWest() {
		if (!listener.EGarzonL.isEmpty()) {
			Vehicle car = (Vehicle)(listener.EGarzonL.remove());
			//outFile.println(car);
		}
		if (!listener.EGarzonR.isEmpty()) {
			Vehicle car = (Vehicle) listener.EGarzonR.remove();
			//outFile.println(car);
		}
		if (!listener.WGarzonL.isEmpty()) {
			Vehicle car = (Vehicle) listener.WGarzonL.remove();
			//outFile.println(car);
		}
		if (!listener.WGarzonR.isEmpty()) {
			Vehicle car = (Vehicle) listener.WGarzonR.remove();
			//outFile.println(car);
		}
	}

	private static void moveNorthSouth() {
		if (!listener.NMillanL.isEmpty()) {
			Vehicle car = (Vehicle) listener.NMillanL.remove();
			//outFile.println(car);
		}
		if (!listener.NMillanR.isEmpty()) {
			Vehicle car = (Vehicle) listener.NMillanR.remove();
			//outFile.println(car);
		}
		if (!listener.SMillanL.isEmpty()) {
			Vehicle car = (Vehicle) listener.SMillanL.remove();
			//outFile.println(car);
		}
		if (!listener.SMillanR.isEmpty()) {
			Vehicle car = (Vehicle) listener.SMillanR.remove();
			//outFile.println(car);
		}
	}
}
