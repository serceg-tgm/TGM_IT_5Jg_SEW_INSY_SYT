package erceg_kritzl.mongodb;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * Diese Klasse dient zum Verbindungsaufbau mit der Datenbank. Dabei wird von der abstrakten Klasse
 * "AbstractMongoConfiguration" vererbt.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160107.1
 */
@Configuration
public class DatabaseConfig extends AbstractMongoConfiguration {

    /**
     * Diese Methode stellt eine getter-Methode dar, welche den Namen der Datenbank zurueckgibt.
     * @return Datenbankname
     */
    @Override
    protected String getDatabaseName() {
        return "PersonWiki";
    }

    /**
     * Mit dieser Methode wird eine Verbindung mittels der MongoClient-Klasse zu einer spezifischen IP-Adresse
     * aufgebaut.
     * @return MongoClient mit der bestimmten IP-Adresse
     * @throws Exception wird geworfen, falls keine Verbindung zu der IP-Adresse aufgebaut werden kann
     */
    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient("192.168.0.100");
    }
}
