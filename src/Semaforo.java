/*
 * Copyright (c) 2006 Sun Microsystems, Inc.  All rights reserved.  U.S.
 * Government Rights - Commercial software.  Government users are subject
 * to the Sun Microsystems, Inc. standard license agreement and
 * applicable provisions of the FAR and its supplements.  Use is subject
 * to license terms.
 *
 * This distribution may include materials developed by third parties.
 * Sun, Sun Microsystems, the Sun logo, Java and J2EE are trademarks
 * or registered trademarks of Sun Microsystems, Inc. in the U.S. and
 * other countries.
 *
 * Copyright (c) 2006 Sun Microsystems, Inc. Tous droits reserves.
 *
 * Droits du gouvernement americain, utilisateurs gouvernementaux - logiciel
 * commercial. Les utilisateurs gouvernementaux sont soumis au contrat de
 * licence standard de Sun Microsystems, Inc., ainsi qu'aux dispositions
 * en vigueur de la FAR (Federal Acquisition Regulations) et des
 * supplements a celles-ci.  Distribue par des licences qui en
 * restreignent l'utilisation.
 *
 * Cette distribution peut comprendre des composants developpes par des
 * tierces parties. Sun, Sun Microsystems, le logo Sun, Java et J2EE
 * sont des marques de fabrique ou des marques deposees de Sun
 * Microsystems, Inc. aux Etats-Unis et dans d'autres pays.
 */



/**
 * The SimpleProducer class consists only of a main method,
 * which sends several messages to a queue or topic.
 *
 * Run this program in conjunction with SimpleSynchConsumer or
 * SimpleAsynchConsumer. Specify a queue or topic name on the
 * command line when you run the program.  By default, the
 * program sends one message.  Specify a number after the
 * destination name to send that number of messages.
 */
import javax.jms.*;
import javax.naming.*;


public class Semaforo {
    /**
     * Main method.
     *
     * @param args     the destination used by the example
     *                 and, optionally, the number of
     *                 messages to send
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        String destName = "jms/Queue";
        System.out.println("Destination name is " + destName);
        
        /*
         * Create a JNDI API InitialContext object if none exists
         * yet.
         */
        Context jndiContext = null;

        try {
            jndiContext = new InitialContext();
        } catch (NamingException e) {
            System.out.println("Could not create JNDI API context: " +
                e.toString());
            System.exit(1);
        }

        /*
         * Look up connection factory and destination.  If either
         * does not exist, exit.  If you  look up a
         * TopicConnectionFactory or a QueueConnectionFactory,
         * program behavior is the same.
         */
        ConnectionFactory connectionFactory = null;
        Destination dest = null;

        try {
            connectionFactory = (ConnectionFactory) jndiContext.lookup(
                    "jms/JupiterConnectionFactory");
            dest = (Destination) jndiContext.lookup(destName);
        } catch (Exception e) {
            System.out.println("JNDI API lookup failed: " + e.toString());
            e.printStackTrace();
            System.exit(1);
        }

        /*
         * Create connection.
         * Create session from connection; false means session is
         * not transacted.
         * Create producer and text message.
         * Send messages, varying text slightly.
         * Send end-of-messages message.
         * Finally, close connection.
         */
        Connection connection = null;
        MessageProducer producer = null;

        try {
            connection = connectionFactory.createConnection();
            
            Session session =
                connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(dest);
            
            TextMessage message = session.createTextMessage();
            
            while(true){
                message.setText("rojo");
                System.out.println("Semaforo cambio la luz a "+ message.getText());
                producer.send(message);
                Thread.sleep(2000);
                
                message.setText("verde");
                System.out.println("Semaforo cambio la luz a "+ message.getText());
                producer.send(message);
                Thread.sleep(3000);
            }
            
            /*
             * Send a non-text control message indicating end of
             * messages.
             */
            
			//producer.send(session.createMessage());
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
}
