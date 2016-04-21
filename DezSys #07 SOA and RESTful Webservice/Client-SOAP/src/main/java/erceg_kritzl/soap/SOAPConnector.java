package erceg_kritzl.soap;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * Stellt eine Verbindung zu einem SOAP-Service her
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160107.1
 */
public class SOAPConnector {

    private String webServiceURL;
    private SOAPMessageCreator messageCreator;

    public SOAPConnector(SOAPMessageCreator messageCreator, String webServiceURL) {
        this.messageCreator = messageCreator;
        this.webServiceURL = webServiceURL;
    }

    public SOAPMessage call() throws SOAPException {
        // Erstellen der Konnektion
        SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = connectionFactory.createConnection();

        // Senden der Anfrage und erhalten der Antwort
        SOAPMessage message = connection.call(this.messageCreator.create(), this.webServiceURL);

        // Close the connection
        connection.close();

        return message;
    }
}