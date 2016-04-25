package at.erceg_kritzl.dezsys10;

import at.erceg_kritzl.dezsys10.balancer.BalancerAlgorithm;
import at.erceg_kritzl.dezsys10.balancer.LeastConnectionBalancer;
import at.erceg_kritzl.dezsys10.balancer.LoadBalancer;
import at.erceg_kritzl.dezsys10.balancer.WeightDistributionBalancer;
import at.erceg_kritzl.dezsys10.client.Client;
import at.erceg_kritzl.dezsys10.server.CalculatingServer;
import at.erceg_kritzl.dezsys10.server.PiCalculatingServer;
import at.erceg_kritzl.dezsys10.utils.AppCLI;
import at.erceg_kritzl.dezsys10.utils.LoadBalancerAppCLI;

import java.util.HashMap;
import java.util.Map;

/**
 * In der Main-Klasse wird je nachdem, welches Argument beim Starten angegeben wurde, der jeweilige Applikationstyp
 * (Balancer, Server, Client) gestartet. Falls ein Balancer erzeugt wurde, wird ebenfalls ueberprueft, welcher
 * Algorithmus (Weight-Distribution, Least-Connection) ausgewaehlt wurde.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public class Main {

    private AppCLI cli;

    /**
     * Hierbei werden die Argumente geparst und ueberprueft, welcher Applikationstyp (Balancer, Server, Client)
     * angegeben wurde.
     * @param args Eingabe des Benutzers
     */
    public Main(String[] args) {
        cli = new LoadBalancerAppCLI();
        cli.parseArgs(args);
        switch (cli.getArgs().get("application-type")) {
            case "balancer":    this.computeBalancer();
                                break;
            case "server":      this.computeServer();
                                break;
            case "client":      this.computeClient();
                                break;
        }
    }


    /**
     * Je nachdem, welcher Balancer-Algorithmus ausgewaehlt wurde, wird ein Objekt eines Load-Balancers erstellt.
     */
    public void computeBalancer() {
        BalancerAlgorithm algorithm = null;
        switch (this.cli.getArgs().get("balancer-type")) {
            case "weight-distributed":  algorithm = new WeightDistributionBalancer();
                                        break;
            case "least-connection": algorithm = new LeastConnectionBalancer();
        }
        new LoadBalancer(algorithm, Integer.parseInt(this.cli.getArgs().get("balancer-server-port")),Integer.parseInt(this.cli.getArgs().get("balancer-client-port")));
    }

    /**
     * Je nachdem, welcher Calculator-Type (bisher steht nur Pi zur Auswahl) ausgewaehlt wurde, wird ein Objekt eines
     * Servers erstellt.
     */
    public void computeServer() {
        CalculatingServer server = null;
        Map<String, String> args = new HashMap<>();
        switch (this.cli.getArgs().get("calculator-type")) {
            case "pi":      server = new PiCalculatingServer(this.cli.getArgs().get("balancer-host"), Integer.parseInt(this.cli.getArgs().get("balancer-server-port")));
                            args.put("weighted", this.cli.getArgs().get("weighted"));
                            server.setArgs(args);
                            break;
        }
        server.registerServer();
    }

    /**
     * Ein Objekt eines Clients wird erstellt.
     */
    public void computeClient() {
        Client client = new Client(this.cli.getArgs().get("balancer-host"), Integer.parseInt(this.cli.getArgs().get("balancer-client-port")));
        client.askForPi(this.cli.getArgs().get("digits"));
    }

    /**
     * Hier wird der Konstruktor der Main-Klasse aufgerufen.
     * @param args Eingabe des Benutzers
     */
    public static void main(String[] args) {
        new Main(args);
    }
}
