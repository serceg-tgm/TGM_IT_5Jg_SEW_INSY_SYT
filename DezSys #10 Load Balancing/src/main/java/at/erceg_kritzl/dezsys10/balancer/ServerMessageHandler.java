package at.erceg_kritzl.dezsys10.balancer;

import at.erceg_kritzl.dezsys10.connection.ClientNetworking;
import at.erceg_kritzl.dezsys10.connection.MessageHandler;
import at.erceg_kritzl.dezsys10.server.CalculatingServer;
import at.erceg_kritzl.dezsys10.server.PiCalculatingServer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Diese Klasse stellt den Message-Handler des Servers dar.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public class ServerMessageHandler implements MessageHandler {

    private static final Logger LOG = LogManager.getLogger(ServerMessageHandler.class);
    private LoadBalancer loadBalancer;

    /**
     * Im Konstruktor wird der Load-Balancer, der die Antworten des Servers bekommen soll, initialisiert.
     *
     * @param loadBalancer Load-Balancer, der die Antworten des Servers bekommen soll
     */
    public ServerMessageHandler(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    @Override
    public void handleMessage(ClientNetworking clientNetworking, String message) {
        LOG.info("Got message from Server: " + message);
        if (message.contains("/register pi")) {
            CalculatingServer server = new PiCalculatingServer(clientNetworking);
            if (message.split(" ").length > 2)
                server.addArg("weighted", message.split(" ")[2]);
            server.addArg("connections", "0");
            this.loadBalancer.getAlgorithm().registerServer(server, message.split(" ")[1]);
            LOG.info("Server registered: " + clientNetworking.getDestination());

        } else {
            int id = Integer.parseInt(message.split(" ")[0]);
            ClientNetworking net = this.loadBalancer.getOpenRequests().get(id);
            this.loadBalancer.removeRequest(id);

            this.loadBalancer.getAlgorithm().serverFinished(this.loadBalancer.getOpenCalculations().get(id));
            this.loadBalancer.removeCalculation(id);
            net.write(message.split(" ")[1]);

            LOG.info("Sending response to client: " + net.getDestination());
        }
    }
}
