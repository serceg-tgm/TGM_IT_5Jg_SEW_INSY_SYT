package erceg_kritzl.utils;

import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Nimmt Benutzereingaben fuer den SOAP-Client entgegen und parst die Argumente.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160107.1
 */
public class SOAPCLIHandler implements CLIHandler{

    private Options options;
    private Map<String, String> optionvalues;

    public SOAPCLIHandler() {
        this.optionvalues = new HashMap<>();
        this.options = new Options();
        this.options.addOption(Option.builder("n").argName("name").desc("Der zu suchende Name").required().hasArg().build());
        this.options.addOption(Option.builder("h").argName("host").desc("Die IP oder Domain des Servers").hasArg().build());
        this.options.addOption(Option.builder("p").argName("port").desc("Der Port auf dem der Server läuft").hasArg().build());
        this.options.addOption(Option.builder("o").argName("output").desc("Das File, in das die Antwort des Servers geschrieben werden soll").hasArg().build());
    }

    @Override
    public void parse(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(this.options, args);

        this.optionvalues.put("name", commandLine.hasOption('n') ? commandLine.getOptionValue('n') : "");
        this.optionvalues.put("host", commandLine.hasOption('h') ? commandLine.getOptionValue('h') : "localhost");
        this.optionvalues.put("port", commandLine.hasOption('p') ? commandLine.getOptionValue('p') : "8080");
        this.optionvalues.put("output", commandLine.hasOption('o') ? commandLine.getOptionValue('o') : "");
    }

    @Override
    public Map<String, String> getOptions() {
        return optionvalues;
    }

    @Override
    public void printHelp() {
        HelpFormatter help = new HelpFormatter();
        help.printHelp("DezSys07", "Client zum suchen eines Personeneintrages ueber ein SOA-Service", this.options, null, true);
    }


}
