package at.erceg_kritzl.dezsys10.balancer;

import at.erceg_kritzl.dezsys10.connection.ClientNetworking;
import at.erceg_kritzl.dezsys10.connection.MessageHandler;
import at.erceg_kritzl.dezsys10.server.CalculatingServer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Diese Klasse stellt den Message-Handler des Clients dar.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public class ClientMessageHandler implements MessageHandler {

    private static final Logger LOG = LogManager.getLogger(ClientMessageHandler.class);
    private LoadBalancer loadBalancer;

    /**
     * Im Konstruktor wird der Load-Balancer, der die Anfragen des Clients bekommen soll, initialisiert.
     *
     * @param loadBalancer Load-Balancer, der die Anfragen des Clients bekommen soll
     */
    public ClientMessageHandler(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    @Override
    public void handleMessage(ClientNetworking networking, String message) {
        int id = loadBalancer.getNewCounter();
        loadBalancer.addRequest(id, networking);
        LOG.info("Got message from client: " + message);
        CalculatingServer nextServer = this.loadBalancer.getAlgorithm().nextServer(message.split(" ")[0]);
        this.loadBalancer.addCalculation(id, nextServer);

        nextServer.getNetworking().write(id + " " + message.split(" ")[1]);
        LOG.info("Sending request to " + networking.getDestination());
    }
}
