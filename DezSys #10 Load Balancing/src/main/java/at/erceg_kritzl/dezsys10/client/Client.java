package at.erceg_kritzl.dezsys10.client;

import at.erceg_kritzl.dezsys10.connection.ClientNetworking;
import at.erceg_kritzl.dezsys10.connection.MessageHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Diese Klasse repraesentiert einen Client. Dieser sendet eine Anfrage an den Load-Balancer, der diese Anfrage an
 * einen bestimmten Server weiterleitet.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public class Client implements MessageHandler {

	private ClientNetworking networking;
	private static final Logger LOG = LogManager.getLogger(Client.class);

	/**
	 * Im Konstruktor wird eine neue Verbindung des Clients aufgebaut, indem sich dieser mit der Host-Adresse und
	 * dem Port zu einem Balancer verbindet.
	 *
	 * @param balancerHost Host-Adresse des Balancers
	 * @param balancerPort Port des Balancers
	 */
	public Client(String balancerHost, int balancerPort) {
		this.networking = new ClientNetworking(this, balancerHost, balancerPort);
		this.networking.connect();
	}

	/**
	 * @see at.erceg_kritzl.dezsys10.connection.MessageHandler#handleMessage(at.erceg_kritzl.dezsys10.connection.ClientNetworking, java.lang.String)
	 */
	public void handleMessage(ClientNetworking networking, String message) {
		LOG.info("Got message from balancer: " + message);
		this.networking.disconnect();
	}

	/**
	 * Stellt eine Anfrage zum Berechnen von Pi bereit
	 *
	 * @param numberDigits Anzahl der zu berechnenden Nachkommastellen von Pi
	 */
	public void askForPi(String numberDigits) {
		LOG.info("Asking balancer for pi with " + numberDigits + " digits");
		this.networking.write("pi " + numberDigits);
	}

}
