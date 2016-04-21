package erceg_kritzl.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Erweitert die Definitionen von Methoden von Spring wie auf Datensaetze zugegriffen werden kann.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160107.1
 */
@Repository
public interface PersonRepository extends MongoRepository<PersonRecord, String> {

    /**
     * Sucht in der Datenbank nach Personeneintraegen, die den angegebenen String enthalten und gibt die ersten 50 zurueck
     *
     * @param name Name der gesuchten Person
     * @return Die ersten 50 Personen, die den angegebenen String enthalten
     */
    List<PersonRecord> findTop50ByNameContainingIgnoreCase(@Param("name") String name);

}
