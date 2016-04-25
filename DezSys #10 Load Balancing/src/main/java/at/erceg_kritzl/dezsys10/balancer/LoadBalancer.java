package at.erceg_kritzl.dezsys10.balancer;

import at.erceg_kritzl.dezsys10.connection.*;
import at.erceg_kritzl.dezsys10.server.CalculatingServer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Diese Klasse repraesentiert einen Load-Balancer. Dieser verwaltet die Anfragen des Clients bzw. die Antworten des
 * Servers.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public class LoadBalancer {

	private BalancerAlgorithm algorithm;
	private static final Logger LOG = LogManager.getLogger(LoadBalancer.class);
	private AtomicInteger clientCounter;
	private Map<Integer, ClientNetworking> openRequests;
	private Map<Integer, CalculatingServer> openCalculations;

	/**
	 * Im Konstruktor wird eine Verbindung zu den verfuegbaren Clients bzw. Servern aufgebaut, indem fuer diese
	 * ein neuer Port eroeffnet wird und diese auf unterschiedlichen Ports laufen.
	 *
	 * @param algorithm Balancer-Algorithmus, der verwendet wird
	 * @param portForServers Port, der fuer die Server eroeffnet wird
	 * @param portForClients Port, der fuer die Clients eroeffnet wird
	 */
	public LoadBalancer(BalancerAlgorithm algorithm, int portForServers, int portForClients) {
		this.algorithm = algorithm;
		this.clientCounter = new AtomicInteger(0);
		this.openRequests = Collections.synchronizedMap(new HashMap<>());
		this.openCalculations = Collections.synchronizedMap(new HashMap<>());

		ServerMessageHandler serverMessageHandler = new ServerMessageHandler(this);
		ClientMessageHandler clientMessageHandler = new ClientMessageHandler(this);

		new Thread(new ServerNetworking(portForServers, serverMessageHandler)).start();
		new Thread(new ServerNetworking(portForClients, clientMessageHandler)).start();

		LOG.info("Started Load-Balancer");
		LOG.info("Server Port: " + portForServers);
		LOG.info("Client Port: " + portForClients);
	}

	/**
	 * Gibt den Balancer-Algorithmus, der verwendet wird, zurueck.
	 *
	 * @return verwendeter Balancer-Algorithmus
	 */
	public BalancerAlgorithm getAlgorithm() {
		return algorithm;
	}

	/**
	 * Gibt alle Berechnungen zurueck, die noch offen sind und nicht berechnet wurden.
	 *
	 * @return alle offenen Berechnungen
	 */
	public Map<Integer, CalculatingServer> getOpenCalculations() {
		return openCalculations;
	}

	/**
	 * Fuegt eine neue Berechnung hinzu, die von einem bestimmten Server durchgefuehrt werden soll.
	 *
	 * @param id ID des Clients, von welchem die Anfrage kommt
	 * @param server Server, der die Berechnung durchfuehren soll
	 */
	public void addCalculation(Integer id, CalculatingServer server) {
		this.openCalculations.put(id, server);
	}

	/**
	 * Entfernt eine Berechnung, die noch offen ist.
	 *
	 * @param id ID des Clients, von welchem die Anfrage kommt
	 */
	public void removeCalculation(Integer id) {
		this.openCalculations.remove(id);
	}

	/**
	 * Weist einem neu hinzugefuegten Client eine ID zu.
	 *
	 * @return ID des neu hinzugefuegten Clients
	 */
	public int getNewCounter() {
		return this.clientCounter.getAndIncrement();
	}

	/**
	 * Gibt alle offenen Anfragen, die von den Clients eingereicht werden, zurueck.
	 *
	 * @return offene Anfragen der Clients
	 */
	public Map<Integer, ClientNetworking> getOpenRequests() {
		return openRequests;
	}

	/**
	 * Fuegt eine neu angekommene Anfrage des Clients zu den offenen Anfragen hinzu.
	 *
	 * @param id ID des Clients, von welchem die Anfrage kommt
	 * @param connection Netzwerk, in dem sich der Client befindet
	 */
	public void addRequest(Integer id, ClientNetworking connection) {
		this.openRequests.put(id, connection);
	}

	/**
	 * Entfernt eine Anfrage des Clients, die noch offen ist.
	 *
	 * @param id ID des Clients, von welchem die Anfrage kommt
	 */
	public void removeRequest(Integer id) {
		this.openRequests.remove(id);
	}
}
