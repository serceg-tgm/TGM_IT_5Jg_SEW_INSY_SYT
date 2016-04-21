package erceg_kritzl.rest;

import erceg_kritzl.data.PersonRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;

import java.util.List;


/**
 * Diese Klasse behandelt HTTP GET-Requests des Browsers, der text/html Antworten fordert. <br>
 * Um dies zu ermoeglichen werden Templates mit den Daten des Rest-Controllers befuellt und an den Client uebermittelt.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160107.1
 */
@Controller
public class PersonViewController {

    @Autowired
    private PersonRestController restController;

    /**
     * Leitet den Client bei der Anfrage von "/" auf "/persons" um.
     *
     * @return Weitergeleiteten Pfad "/persons"
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/html")
    public String redirectToShowPersons() {
        return "redirect:persons";
    }

    /**
     * GET auf "/persons" (mit accept auf text/html)<br>
     * Ist fuer die Darstellung der Personeneintraege auf der Hauptseite zustaendig. Optional kann auch ein Name angegeben werden,
     * was dazu fuehrt, dass nur Eintraege die diesen Namen beeinhalten angezeigt werden. Als weiteren Parameter wird ein model
     * uebergeben, auf welches dann im Template zugegriffen werden kann. Zurueckgegeben wird der Name des Templates.
     *
     * @param name Filter des Namens
     * @param model Model welches im Template verwendet wird
     * @return String des Templates
     */
    @RequestMapping(value = "/persons", method = RequestMethod.GET, produces = "text/html")
    public String showAllPersons(@RequestParam(value = "name", defaultValue = "") String name, Model model) {
        List<PersonRecord> personRecords = restController.findDataRecordsByName(name).getBody();
        model.addAttribute("personRecords", personRecords);

        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("personRecords", personRecords);
        }

        return "showPersons";
    }

    /**
     * GET auf "/persons/add" (mit accept auf text/html)<br>
     * Ist fuer die Erstellung eines Eintrages verantwortlich
     *
     * @return String des Templates
     */
    @RequestMapping(value = "/persons/add", method = RequestMethod.GET, produces = "text/html")
    public String addPerson() {
        return "addPerson";
    }

    /**
     * GET auf "/persons/{id}" (mit accept auf text/html)<br>
     * Ist fuer die Bearbeitung eines Eintrages verantwortlich
     *
     * @param id Id des Eintrages
     * @param model Model welches im Template verwendet wird
     * @return String des Templates
     */
    @RequestMapping(value = "/persons/{id}", method = RequestMethod.GET, produces = "text/html")
    public String editPerson(@PathVariable String id, Model model) {
        PersonRecord personRecord = restController.findDataRecord(id).getBody();
        model.addAttribute("personRecord", personRecord);

        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("personRecord", personRecord);
        }

        return "editPerson";
    }

}