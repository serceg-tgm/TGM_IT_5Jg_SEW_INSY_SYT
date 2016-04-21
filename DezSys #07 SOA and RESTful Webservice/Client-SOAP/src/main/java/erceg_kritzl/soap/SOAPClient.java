package erceg_kritzl.soap;

import erceg_kritzl.utils.CLIHandler;
import erceg_kritzl.utils.SOAPCLIHandler;
import erceg_kritzl.utils.SOAPMessageUtils;
import org.apache.commons.cli.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import java.io.*;

/**
 * Ein SOAP-Client der eine Suchanfrage an ein SOAP-Service schickt und die Antwort in ein File schreibt oder in der Konsole anzeigt.
 * Verwendung: <br>
 * DezSys07 [-h &lt;host&gt;] -n &lt;name&gt; [-o &lt;output&gt;] [-p &lt;port&gt;]
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160107.1
 */
public class SOAPClient{
    public static void main(String args[]) {
        CLIHandler cliHandler = new SOAPCLIHandler();

        try {
            cliHandler.parse(args);
            SOAPMessageCreator messageCreator = new SOAPMessageCreatorPerson(cliHandler.getOptions().get("name"));
            SOAPConnector connector = new SOAPConnector(messageCreator, "http://" + cliHandler.getOptions().get("host") + ":" + cliHandler.getOptions().get("port") + "/persons/search");

            SOAPMessage message = connector.call();
            String messageString = SOAPMessageUtils.soapMessageToString(message);

            if (cliHandler.getOptions().get("output").isEmpty()) {
                System.out.println(messageString);
            } else {
                System.out.println("Die Antwort der Suchanfrage wurde in folgendes File geschrieben: " + cliHandler.getOptions().get("output"));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cliHandler.getOptions().get("output"))));
                writer.write(messageString);
                writer.flush();
                writer.close();
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            cliHandler.printHelp();
        } catch (IOException e) {
            System.out.println("Die angegebene Datei " + cliHandler.getOptions().get("output") + " konnte nicht erstellt werden");
            cliHandler.printHelp();
        } catch (SOAPException | IllegalAccessException e) {
            System.out.println("Es konnte keine Verbindung zum SOAP-Client aufgebaut werden. Host: http://" + cliHandler.getOptions().get("host") + ":" + cliHandler.getOptions().get("port"));
            cliHandler.printHelp();
        } catch (TransformerException | InstantiationException | SAXException | ParserConfigurationException | ClassNotFoundException e) {
            System.out.println("Die Suche konnte nicht erfolgreich gestartet werden");
            cliHandler.printHelp();
        }
    }
}
