package at.erceg_kritzl.dezsys10.server;

import at.erceg_kritzl.dezsys10.connection.ClientNetworking;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Diese Klasse repraesentiert einen Server, der Pi berechnet. Dabei wird die abstrakte Klasse "CalculatingServer"
 * erweitert.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public class PiCalculatingServer extends CalculatingServer {

    private CalculatorAlgorithm alg;
    private static final Logger LOG = LogManager.getLogger(PiCalculatingServer.class);

    /**
     * Im Konstruktor wird eine neue Verbindung des Pi-Servers aufgebaut, indem sich dieser mit der Host-Adresse und
     * dem Port zu einem Balancer verbindet und auf Anfragen wartet.
     *
     * @param balancerHost Host-Adresse des Balancers
     * @param balancerPort Port des Balancers
     */
    public PiCalculatingServer(String balancerHost, int balancerPort) {
        super(balancerHost, balancerPort);
        this.alg = new CalculatorAlgorithm();
    }

    /**
     * Mittels diesem Konstruktor kann ein Pi-Server angeben, zu welchem Netzwerk sich dieser verbinden moechte.
     *
     * @param networking Netzwerk, zu welchem sich Pi-Server verbinden moechte
     */
    public PiCalculatingServer(ClientNetworking networking) {
        super(networking);
        this.alg = new CalculatorAlgorithm();
    }

    @Override
    public void registerServer() {
        LOG.info("Register Server on Balancer");
        String register = "/register pi";
        if (this.getArgs().get("weighted")!=null)
            register += " "+this.getArgs().get("weighted");
        this.networking.write(register);
    }

    @Override
    public String calculate(int input) {
        return alg.pi(input).toString();
    }
}
