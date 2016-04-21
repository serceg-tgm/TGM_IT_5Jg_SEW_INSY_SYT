package erceg_kritzl.rest;

import erceg_kritzl.data.PersonRecord;
import erceg_kritzl.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import java.util.List;


/**
 * Diese Klasse ermoeglicht das Erstellen, Loeschen, Bearbeiten und Suchen von Personeneintraegen.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160107.1
 */
@RestController
public class PersonRestController {

    @Autowired
    private PersonService service;

    /**
     * GET auf "/persons" <br>
     * Ermoeglicht die Suche eines Personeneintrages. Wenn kein Parameter angegeben wird, werden die ersten 50 Eintraege zurueckgegeben.
     *
     * @param name Name der gesuchten Person. Kann auch nur teilweisse enthalten sein.
     * @return Liste der gefundenen Eintraege und den Statuscode 200
     */
    @RequestMapping(value = "/persons", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<PersonRecord>> findDataRecordsByName(@RequestParam(value = "name", defaultValue = "") String name) {
        if (name.length() == 0)
            return new ResponseEntity<>(service.findTop50(), HttpStatus.OK);
        else
            return new ResponseEntity<>(service.findTop50ByNameContainingIgnoreCase(name), HttpStatus.OK);
    }

    /**
     * POST auf "/persons" <br>
     * Ermoeglicht das Hinzufuegen einer Person.
     *
     * @param personRecord der Personeneintrag der erstellt werden soll
     * @return Der erstellte Eintrag und den Statuscode 200
     */
    @RequestMapping(value = "/persons", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<PersonRecord> createDataRecord(@RequestBody PersonRecord personRecord) {
        service.create(personRecord);
        return new ResponseEntity<>(personRecord, HttpStatus.CREATED);
    }

    /**
     * GET auf "/persons/{id}" <br>
     * Ermoeglicht das Lesen eines Personeneintrags. Dabei ist die der Parameter id ueber die URL anzugeben
     *
     * @param id ID des zu lesenden Personeneintrags
     * @return Den gesuchten Eintrag und den Statuscode 200. Wenn dieser nicht gefunden wurde dann einen Statuscode von 404
     */
    @RequestMapping(value = "/persons/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<PersonRecord> findDataRecord(@PathVariable String id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    /**
     * PUT auf "/persons/{id}" <br>
     * Ermoeglicht das Aendern eines Personeneintrags. Dabei ist die der Parameter id ueber die URL anzugeben
     *
     * @param id ID des zu aendernden Personeneintrags
     * @param newDataRecord Der Personeneintrag mit den neuen Daten
     * @return Den geaenderten Eintrag und den Statuscode 200. Wenn dieser nicht gefunden wurde dann einen Statuscode von 404
     */
    @RequestMapping(value = "/persons/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<PersonRecord> updateDataRecord(@PathVariable String id, @RequestBody PersonRecord newDataRecord) {
        PersonRecord dataRecord = service.findById(id);
        dataRecord.setName(newDataRecord.getName());
        dataRecord.setDescription(newDataRecord.getDescription());

        return new ResponseEntity<>(service.update(dataRecord), HttpStatus.OK);
    }

    /**
     * DELETE auf "/persons/{id}" <br>
     * Ermoeglicht das Loeschen eines Personeneintrags. Dabei ist die der Parameter id ueber die URL anzugeben
     *
     * @param id ID des zu lesenden Personeneintrags
     * @return Den gesuchten Eintrag und den Statuscode 200. Wenn dieser nicht gefunden wurde dann einen Statuscode von 404
     */
    @RequestMapping(value = "/persons/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<PersonRecord> updateDataRecord(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Diese Methode wird aufgerufen wenn eine IllegalArgumentException auftritt und generiert daraufhin einen HTTP-Response
     * mit dem Statuscode von BAD_REQUEST.
     *
     * @param ex Exception die behandlet werden soll
     * @return Eine 400 BAD_REQUEST Antwort mit dem Inhalt der Exception
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleBadRequest(Exception ex) {
        return generateJsonErrorResponse(HttpStatus.BAD_REQUEST, ex);
    }

    /**
     * Diese Methode wird aufgerufen wenn eine EmptyResultDataAccessException auftritt und generiert daraufhin einen HTTP-Response
     * mit dem Statuscode von NOT_FOUND.
     *
     * @param ex Exception die behandlet werden soll
     * @return Eine 404 NOT_FOUND Antwort mit dem Inhalt der Exception
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFound(Exception ex) {
        return generateJsonErrorResponse(HttpStatus.NOT_FOUND, ex);
    }

    /**
     * Generiert eine JSON-Error-Antwort mit dem angegebenen HTTP-Status und dem Inhalt der Exception
     *
     * @param status Status der Antwort
     * @param ex Exception der Antwort
     * @return Antwort mit Fehler
     */
    private ResponseEntity<String> generateJsonErrorResponse(HttpStatus status, Exception ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(Json.createBuilderFactory(null).createObjectBuilder()
                .add("code", status.value())
                .add("message", ex.getMessage()).build().toString(), headers, status);

    }
}