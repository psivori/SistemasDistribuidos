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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Controlador {

	final static Listener listener = new Listener();
	static ListenerSemaforo listenerSemaforo;
	private static int MAX_AUTOS_DIRECCION = 15;
	static FileWriter fw;
	static BufferedWriter bw;
	static PrintWriter outFile;
	private static boolean optimizar;

	public static void main(String[] args) {
		optimizar = args[0].equals("true");
		try {
			fw = new FileWriter("output.txt");
			bw = new BufferedWriter(fw);
			outFile = new PrintWriter(bw);
		} catch (IOException e1) {
			System.out.println("Exception occurred: " + e1.toString());
		}
		outFile.print("--- Comienza la simulacion. --- \n");

		String destNameSensor = null;
		String destNameSemaforo = null;
		String destName = null;
		Context jndiContext = null;
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		Destination destSensor = null;
		Destination destSemaforo = null;
		Destination dest = null;
		MessageConsumer consumerSensor = null;
		MessageConsumer consumerSemaforo = null;

		destNameSensor = "jms/Topic";
		System.out.println("Destination name is " + destNameSensor);
		destNameSemaforo = "jms/Queue";
		System.out.println("Destination name is " + destNameSemaforo);
		destName = "jms/Queue2";
		System.out.println("Destination name is " + destName);


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
			dest = (Destination) jndiContext.lookup(destName);

		} catch (Exception e) {
			System.out.println("JNDI API lookup failed: " + e.toString());
			System.exit(1);
		}


		try {
			connection = connectionFactory.createConnection();
			final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			consumerSensor = session.createConsumer(destSensor);
			consumerSemaforo = session.createConsumer(destSemaforo);
			final MessageProducer producer = session.createProducer(dest);
			listenerSemaforo = new ListenerSemaforo(new ISemaforo() {

				public void onRed() {
					if (optimizar && checkBus()){
						try {
							TextMessage message = session.createTextMessage();
							message.setText("1");
							System.out.println("Controlador notificando cambio de prioridad.");
							producer.send(message);
						} catch (JMSException e) {
							System.out.println("Exception occurred: " + e.toString());
						}
					}
					writeMessage("--- ROJA --- Pasan vehículos Av. Millan");
					printColas();
				}

				public void onGreen() {
					if (optimizar && checkBus()){
						try {
							TextMessage message = session.createTextMessage();
							message.setText("1");
							System.out.println("Controlador notificando cambio de prioridad.");
							producer.send(message);
						} catch (JMSException e) {
							System.out.println("Exception occurred: " + e.toString());
						}
					}
					writeMessage("--- VERDE --- Pasan vehículos Av. Garzon");
					printColas();
				}

			});

			consumerSensor.setMessageListener(listener);
			consumerSemaforo.setMessageListener(listenerSemaforo);
			connection.start();

			String luz = "rojo";

			while (true) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if ("verde".equals(listenerSemaforo.luz)){
					if (!luz.equals(listenerSemaforo.luz)){
						luz = listenerSemaforo.luz;
					}
					moveEastWest();
				}else if ("rojo".equals(listenerSemaforo.luz)){ 
					if (!luz.equals(listenerSemaforo.luz)){
						luz = listenerSemaforo.luz;
					}
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

	private static void printColas(){
		writeMessage("Av. Millan SUR = " + (listener.SMillanL.size() + listener.SMillanR.size()));
		writeMessage("Av. Millan NORTE = " + (listener.NMillanL.size() + listener.NMillanR.size()));
		writeMessage("#omnibus esperando = " + (listener.EGarzonL.size() + listener.WGarzonL.size()));
	}

	private static void writeMessage(String msg) {
		try {
			fw = new FileWriter("output.txt", true);
			bw = new BufferedWriter(fw);
			outFile = new PrintWriter(bw);
			outFile.println(msg);
			outFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void moveEastWest() {
		if (!listener.EGarzonL.isEmpty()) {
			Vehicle car = (Vehicle)(listener.EGarzonL.remove());
			writeMessage(car.toString());
		}
		if (!listener.EGarzonR.isEmpty()) {
			Vehicle car = (Vehicle) listener.EGarzonR.remove();
		}
		if (!listener.WGarzonL.isEmpty()) {
			Vehicle car = (Vehicle) listener.WGarzonL.remove();
			writeMessage(car.toString());
		}
		if (!listener.WGarzonR.isEmpty()) {
			Vehicle car = (Vehicle) listener.WGarzonR.remove();
		}
	}

	private static void moveNorthSouth() {
		if (!listener.NMillanL.isEmpty()) {
			Vehicle car = (Vehicle) listener.NMillanL.remove();
		}
		if (!listener.NMillanR.isEmpty()) {
			Vehicle car = (Vehicle) listener.NMillanR.remove();
		}
		if (!listener.SMillanL.isEmpty()) {
			Vehicle car = (Vehicle) listener.SMillanL.remove();
		}
		if (!listener.SMillanR.isEmpty()) {
			Vehicle car = (Vehicle) listener.SMillanR.remove();
		}
	}

	private static boolean checkBus() {
		int autosNorte = listener.NMillanL.size() + listener.NMillanR.size();
		int autosSur = listener.SMillanL.size() + listener.SMillanR.size();
		boolean pocosAutosEsperando = autosNorte < MAX_AUTOS_DIRECCION && autosSur < MAX_AUTOS_DIRECCION;
		boolean alMenosUnOmnibus = !listener.EGarzonL.isEmpty() || !listener.WGarzonL.isEmpty();
		if (pocosAutosEsperando && alMenosUnOmnibus) {
			return true;
		} else {
			return false;
		}
	}

}
