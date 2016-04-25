package at.erceg_kritzl.dezsys10.balancer;

import at.erceg_kritzl.dezsys10.server.CalculatingServer;
import at.erceg_kritzl.dezsys10.utils.ServerSorter;

import java.util.List;

/**
 * Diese Klasse stellt den Balancer-Algorithmus "Least Connections" bereit. Bei diesem Algorithmus wird ermittelt,
 * welcher Server die geringsten gleichzeitig aktiv vorhandenen Verbindungen besitzt und diesem wird dann eine neue
 * Anfrage zugesandt.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public class LeastConnectionBalancer extends BalancerAlgorithm {

    @Override
    public CalculatingServer nextServer(String type) {
        List<CalculatingServer> servers = ServerSorter.sortByValues(this.getServers().get(type), "connections", true);
        CalculatingServer nextServer = servers.get(0);
        nextServer.getArgs().put("connections", Integer.parseInt(nextServer.getArgs().get("connections"))+1+"");
        return nextServer;
    }

    @Override
    public void registerServer(CalculatingServer server, String type) {
        super.registerServer(server, type);
        server.addArg("connections", "0");
    }

    @Override
    public void serverFinished(CalculatingServer server) {
        server.getArgs().put("connections", Integer.parseInt(server.getArgs().get("connections"))-1+"");
    }
}
