package at.erceg_kritzl.dezsys10.balancer;

import at.erceg_kritzl.dezsys10.server.CalculatingServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Diese abstrakte Klasse stellt Methoden fuer die jeweiligen Balancer-Algorithmen zur Verfuegung. Beispielsweise
 * existieren hier die Methoden zum Registrieren eines Servers bzw. zum Ermitteln des naechsten Servers.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public abstract class BalancerAlgorithm {

	private Map<String, List<CalculatingServer>> servers;

	public BalancerAlgorithm() {
		this.servers = new HashMap<>();
	}

	/**
	 * Registriert einen neuen Server und fuegt diesen zu der Liste aller verfuegbaren Server hinzu.
	 *
	 * @param server Server, der hinzugefuegt werden soll
	 * @param type Typ des Servers (z.B. Server zum Berechnen von Pi)
	 */
	public void registerServer(CalculatingServer server, String type) {
		if (this.servers.get(type)==null)
			this.servers.put(type, new ArrayList<CalculatingServer>());

		this.servers.get(type).add(server);
	}

	/**
	 * Gibt alle registrierten Server aus.
	 *
	 * @return alle registrierten Server
	 */
	public Map<String, List<CalculatingServer>> getServers() {
		return this.servers;
	}

	/**
	 * Ermittelt, welcher Server als naechster etwas berechnen soll.
	 *
	 * @param type Typ des Servers (z.B. Server zum Berechnen von Pi)
	 * @return Server, der berechnen soll
	 */
	public abstract CalculatingServer nextServer(String type);

	/**
	 * Hier wird angegeben, dass der Server, der als Parameter uebergeben wurde, mit seiner Berechnung fertig ist.
	 *
	 * @param server Server, der fertig gerechnet hat
	 */
	public abstract void serverFinished(CalculatingServer server);

}
