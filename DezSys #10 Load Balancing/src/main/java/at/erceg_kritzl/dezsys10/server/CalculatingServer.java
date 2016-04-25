package at.erceg_kritzl.dezsys10.server;

import at.erceg_kritzl.dezsys10.connection.ClientNetworking;
import at.erceg_kritzl.dezsys10.connection.MessageHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Diese abstrakte Klasse repraesentiert einen Server, der etwas bestimmtes berechnen soll. Der Server verbindet sich
 * zu dem Load-Balancer und wartet auf Anfragen.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public abstract class CalculatingServer implements MessageHandler {

	private static final Logger LOG = LogManager.getLogger(CalculatingServer.class);

	protected ClientNetworking networking;
	private String balancerHost;
	private int balancerPort;
	private Map<String,String> args;

	/**
	 * Im Konstruktor wird eine neue Verbindung des Servers aufgebaut, indem sich dieser mit der Host-Adresse und
	 * dem Port zu einem Balancer verbindet und auf Anfragen wartet.
	 *
	 * @param balancerHost Host-Adresse des Balancers
	 * @param balancerPort Port des Balancers
	 */
	public CalculatingServer(String balancerHost, int balancerPort) {
		this.networking = new ClientNetworking(this, balancerHost, balancerPort);
		this.networking.connect();
		this.balancerHost = balancerHost;
		this.balancerPort = balancerPort;
		this.args = new HashMap<>();
	}

	/**
	 * Mittels diesem Konstruktor kann ein Server angeben, zu welchem Netzwerk sich dieser verbinden moechte.
	 *
	 * @param networking Netzwerk, zu welchem sich Server verbinden moechte
	 */
	public CalculatingServer(ClientNetworking networking) {
		this.networking = networking;
		this.args = new HashMap<>();
	}

	/**
	 * Gibt das Netzwerk, in welchem sich der Server befindet, zurueck.
	 *
	 * @return Netzwerk, in welchem sich der Server befindet
	 */
	public ClientNetworking getNetworking() {
		return networking;
	}

	/**
	 * Diese Methode dient zum Registrieren eines neu hinzugefuegten Server-Typs.
	 */
	public abstract void registerServer();

	/**
	 * Verwaltet die Anfrage und berechnet das Angeforderte (in diesem Fall Pi).
	 *
	 * @param input Anfrage, die vom Server berechnet wird
	 * @return Antwort des Servers
	 */
	public abstract String calculate(int input);

	/**
	 * Gibt alle verfuegbaren Argumente zurueck.
	 *
	 * @return alle verfuegbaren Argumente
	 */
	public Map<String,String> getArgs() { return this.args; }

	/**
	 * Setter-Methode zum Hinzuefugen von Argumenten.
	 *
	 * @param args Argumente
	 */
	public void setArgs(Map<String, String> args) {
		this.args = args;
	}

	/**
	 * Fuegt ein neues Argument zur Map hinzu.
	 *
	 * @param key Key des neu hinzugefuegten Arguments
	 * @param value Wert des neu hinzugefuegten Arguments
	 */
	public void addArg(String key, String value) {
		this.args.put(key, value);
	}

	/**
	 * @see at.erceg_kritzl.dezsys10.connection.MessageHandler#handleMessage(at.erceg_kritzl.dezsys10.connection.ClientNetworking, java.lang.String)
	 */
	public void handleMessage(ClientNetworking networking, String message) {
		LOG.info("Got message from balancer: " + message);
		String id = message.split(" ")[0];
		int digits = Integer.parseInt(message.split(" ")[1]);
		String result = this.calculate(digits);
		networking.write(id + " "+ result);
		LOG.info("Calculated result for client " + id + ": " + digits);
	}

}
