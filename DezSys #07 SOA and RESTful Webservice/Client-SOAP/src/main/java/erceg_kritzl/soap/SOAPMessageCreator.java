package erceg_kritzl.soap;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * Beschreibt die Eigenschaften eines Nachrichtengenerators fuer SOAP-Nachrichten
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160107.1
 */
public interface SOAPMessageCreator {

    /**
     * Erstellt eine SOAP-Nachricht aus den angegebenen Daten
     *
     * @return SOAP-Nachricht
     * @throws SOAPException
     */
    public SOAPMessage create() throws SOAPException;
}
