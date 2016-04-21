package erceg_kritzl.service;

import erceg_kritzl.data.PersonRecord;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

/**
 * Beschreibt die notwendigen Methoden zum Erstellen, Bearbeiten, Loeschen und Suchen von Personeneintraegen.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160107.1
 */
public interface PersonServiceDeclaration {

    /**
     * Erstellen eines Personeneintrags in der Datenbank.
     *
     * @param personRecord Der Eintrag der persistiert werden soll
     * @return Der persistierte Eintrag
     */
    PersonRecord create(PersonRecord personRecord);

    /**
     * Loeschen eines Personeneintrags in der Datenbank.
     *
     * @param id Die ID des Eintrages der geloescht werden soll
     * @return Der geloeschte Eintrag
     */
    PersonRecord delete(String id);

    /**
     * Suchen eines Personeneintrags mit der angegebenen id in der Datenbank.
     *
     * @param id Die ID des zu suchenden Eintrages
     * @return Der gesuchte Eintrag
     */
    PersonRecord findById(String id) throws EmptyResultDataAccessException;

    /**
     * Suchen von Personeneintraegen die den angegebenen Namen beinhalten.
     *
     * @param name Name der zu suchenden Eintraege
     * @return Liste der gefundenen Eintraege
     */
    List<PersonRecord> findTop50ByNameContainingIgnoreCase(String name);

    /**
     * Bearbeiten eines Personeneintrags in der Datenbank.
     *
     * @param personRecord Eintrag der bearbeitet werden soll
     * @return Der geänderte Eintrag
     */
    PersonRecord update(PersonRecord personRecord);

    /**
     * Gibt die ersten 50 Personeneintraege zurueck
     *
     * @return Liste der ersten 50 Eintraege
     */
    List<PersonRecord> findTop50();
}
