package at.erceg_kritzl.dezsys10.utils;

import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Durch die Klasse werden die Eingaben des Benutzers geparst und bei Fehlern die korrekte Verwendung ausgegeben.
 * Die Klasse implementiert dafuer das Interface "AppCLI".
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public class LoadBalancerAppCLI implements AppCLI {

    private Options options;
    private Map<String, String> optionvalues;

    /**
     * Im Konstruktor werden alle Argumente hinzugefuegt. Jedes Argument besitzt eine zusaetzliche Beschreibung um die
     * Verwendung des Arguments genauer zu spezifizieren.
     */
    public LoadBalancerAppCLI() {
        this.optionvalues = new HashMap<String, String>();
        this.options = new Options();
        this.options.addOption(Option.builder("h").argName("balancer-host").desc("The host-address of the balancer").hasArg().build());
        this.options.addOption(Option.builder("H").argName("balancer-server-port").desc("The port of the balancer for the servers").hasArg().build());
        this.options.addOption(Option.builder("C").argName("balancer-client-port").desc("The port of the balancer for the clients").hasArg().build());
        this.options.addOption(Option.builder("a").argName("application-type").desc("The type of application that should be started [balancer, server, client]").required().hasArg().build());
        this.options.addOption(Option.builder("b").argName("balancer-type").desc("The type of balancer that should be used [weight-distributed, least-connection]").hasArg().build());
        this.options.addOption(Option.builder("w").argName("weighted").desc("The weight of the given server (only if balancer-type is weighted-distributed)").hasArg().build());
        this.options.addOption(Option.builder("d").argName("digits").desc("The digits of pi that should be calculated (only when application-type is client)").hasArg().build());
        this.options.addOption(Option.builder("c").argName("calculator-type").desc("The type of calculator that should be used [pi]").hasArg().build());
    }


    /**
     * @see at.erceg_kritzl.dezsys10.utils.AppCLI#parseArgs(java.lang.String[])
     */
    public void parseArgs(String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(this.options, args);

            this.optionvalues.put("balancer-host", commandLine.hasOption('h') ? commandLine.getOptionValue('h') : "localhost");
            this.optionvalues.put("balancer-server-port", commandLine.hasOption('H') ? Integer.parseInt(commandLine.getOptionValue('H'))+"" : "33000");
            this.optionvalues.put("balancer-client-port", commandLine.hasOption('C') ? Integer.parseInt(commandLine.getOptionValue('H'))+"" : "44000");
            this.optionvalues.put("application-type", commandLine.hasOption('a') ? commandLine.getOptionValue('a') : "client");
            this.optionvalues.put("balancer-type", commandLine.hasOption('b') ? commandLine.getOptionValue('b') : "least-connection");
            this.optionvalues.put("weighted", commandLine.hasOption('w') ? Integer.parseInt(commandLine.getOptionValue('w'))+"" : "10");
            this.optionvalues.put("digits", commandLine.hasOption('d') ? Integer.parseInt(commandLine.getOptionValue('d'))+"" : "1000");
            this.optionvalues.put("calculator-type", commandLine.hasOption('c') ? commandLine.getOptionValue('c') : "pi");
        } catch(ParseException e) {
            this.printHelp();
            System.exit(1);
        }
    }


    /**
     * @see at.erceg_kritzl.dezsys10.utils.AppCLI#getArgs()
     */
    public Map<String,String> getArgs() {
        return optionvalues;
    }


    /**
     * @see at.erceg_kritzl.dezsys10.utils.AppCLI#printHelp()
     */
    public void printHelp() {
        HelpFormatter help = new HelpFormatter();
        help.printHelp("DezSys10", "Client, Server and Loadbalancer", this.options, null, true);
    }

}
