package erceg_kritzl.soa;

import erceg_kritzl.data.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Diese Klasse stellt den Endpunkt des SOAP-Services dar. <br>
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160107.1
 */
@Endpoint
public class PersonRecordEndpoint {
    public static final String NAMESPACE_URI = "http://erceg_kritzl/soa/";

    private PersonRepository dataRecordRepository;

    /**
     * Wird verwendet um das repository zu injekten
     *
     * @param dataRecordRepository Das Repository, welches Injektet werden soll
     */
    @Autowired
    public PersonRecordEndpoint(PersonRepository dataRecordRepository) {
        this.dataRecordRepository = dataRecordRepository;
    }

    /**
     * Eine SOAP-Anfrage wird auf eine SOAP-Antwort gemappt.
     *
     * @param request Die SOAP-Anfrage
     * @return Eine Antwort mit den ersten 50 Personeneintraegen
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonRequest")
    @ResponsePayload
    public GetPersonResponse getPersonRecord(@RequestPayload GetPersonRequest request) {
        GetPersonResponse response = new GetPersonResponse();
        response.setPersonRecord(dataRecordRepository.findTop50ByNameContainingIgnoreCase(request.getName()));
        return response;
    }
}
