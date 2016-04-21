package erceg_kritzl.utils;

import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Enthaelt Methoden zum Umgang mit SOAP-Nachrichten.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160107.1
 */
public class SOAPMessageUtils {

    /**
     * Ermoeglicht die Umwandlung einer SOAP-Message in einen String
     *
     * @param message SOAP-Nachricht die umgewandelt werden soll
     * @return die String-Repraesentation der SOAP-Nachricht
     * @throws TransformerException
     * @throws SOAPException
     * @throws ParserConfigurationException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SAXException
     */
    public static String soapMessageToString(SOAPMessage message) throws TransformerException, SOAPException, ParserConfigurationException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, SAXException {
        String output;
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = message.getSOAPPart().getContent();
        StringWriter writer = new StringWriter();
        transformer.transform(sourceContent, new StreamResult(writer));
        output = writer.getBuffer().toString().replaceAll("\n|\r", "");

        //von http://stackoverflow.com/questions/139076/how-to-pretty-print-xml-from-java kopiert

        InputSource src = new InputSource(new StringReader(output));
        Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
        Boolean keepDeclaration = output.startsWith("<?xml");

        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
        LSSerializer serializer = impl.createLSSerializer();

        serializer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
        serializer.getDomConfig().setParameter("xml-declaration", keepDeclaration);

        output = serializer.writeToString(document);

        return output;
    }

}
