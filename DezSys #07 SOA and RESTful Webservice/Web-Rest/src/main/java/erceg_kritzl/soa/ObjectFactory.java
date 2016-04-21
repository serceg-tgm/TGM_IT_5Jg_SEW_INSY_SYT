//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.01.04 um 06:42:11 PM CET 
//


package erceg_kritzl.soa;

import erceg_kritzl.data.PersonRecord;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the erceg_kritzl.soa package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: erceg_kritzl.soa
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetPersonResponse }
     * 
     */
    public GetPersonResponse createGetPersonResponse() {
        return new GetPersonResponse();
    }

    /**
     * Create an instance of {@link PersonRecord }
     * 
     */
    public PersonRecord createPersonRecord() {
        return new PersonRecord();
    }

    /**
     * Create an instance of {@link GetPersonRequest }
     * 
     */
    public GetPersonRequest createGetPersonRequest() {
        return new GetPersonRequest();
    }

}
