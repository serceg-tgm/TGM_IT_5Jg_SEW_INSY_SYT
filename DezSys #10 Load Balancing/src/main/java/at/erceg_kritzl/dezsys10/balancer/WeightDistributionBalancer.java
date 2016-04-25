package at.erceg_kritzl.dezsys10.balancer;

import at.erceg_kritzl.dezsys10.server.CalculatingServer;
import at.erceg_kritzl.dezsys10.utils.ServerSorter;

import java.util.List;

/**
 * Diese Klasse stellt den Balancer-Algorithmus "Weighted Distribution" bereit. Bei diesem Algorithmus wird jedem
 * Server, der sich als nächster anmeldet, die Gewichtung "letzter Server + 1" zugewiesen.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public class WeightDistributionBalancer extends BalancerAlgorithm {

    @Override
    public CalculatingServer nextServer(String type) {
        List<CalculatingServer> servers = ServerSorter.sortByValues(this.getServers().get(type), "remainingCalculations", false);
        if (servers==null) return null;
        CalculatingServer nextServer = servers.get(0);
        if (nextServer.getArgs().get("remainingCalculations").equals("0"))
            this.resetWeight(type);

        nextServer.getArgs().put("remainingCalculations", Integer.parseInt(nextServer.getArgs().get("remainingCalculations"))-1+"");
        return nextServer;
    }

    private void resetWeight(String type) {
        for (CalculatingServer server: this.getServers().get(type)) {
            server.getArgs().put("remainingCalculations", server.getArgs().get("weighted"));
        }
    }

    @Override
    public void registerServer(CalculatingServer server, String type) {
        super.registerServer(server, type);
        server.addArg("remainingCalculations", server.getArgs().get("weighted"));
    }

    @Override
    public void serverFinished(CalculatingServer server) {

    }
}
