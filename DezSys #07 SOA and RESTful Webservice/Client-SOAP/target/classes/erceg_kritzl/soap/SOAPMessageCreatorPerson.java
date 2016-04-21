package erceg_kritzl.soap;

import javax.xml.soap.*;

/**
 * Erstellt eine SOAP-Message fuer den Typ personRecord.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160107.1
 */
public class SOAPMessageCreatorPerson implements SOAPMessageCreator{

    private String name;
    private static final String NAMESPACE = "http://erceg_kritzl/soa/";

    /**
     * Initialisiert ein neues Objekt mit der angegebenen Suchanfrage
     *
     * @param name Name der zu suchenden Person
     */
    public SOAPMessageCreatorPerson(String name) {
        this.name = name;
    }

    /**
     * Erstellt eine neue SOAP-Message fuer die Suchanfrage einer Person.
     *
     * @return SOAP-Nachricht
     * @throws SOAPException
     */
    @Override
    public SOAPMessage create() throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("ek", NAMESPACE);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement requestElement = soapBody.addChildElement("getPersonRequest", "ek");
        SOAPElement nameElement = requestElement.addChildElement("name", "ek");
        nameElement.addTextNode(this.name);

        // Headers
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", NAMESPACE + "getPersonRequest");

        soapMessage.saveChanges();

        return soapMessage;
    }
}
