package erceg_kritzl.data;

import org.springframework.data.annotation.Id;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import static org.thymeleaf.util.Validate.*;

/**
 * Repraesentiert ein Datenobjekt einer Person. Diese wird verwendet um die Datenbankeintraege als Objekte darzustellen.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160107.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "personRecord", propOrder = {
        "id",
        "name",
        "description"
})
public class PersonRecord {

    static final int MAX_LENGTH_NAME = 70;
    static final int MAX_LENGTH_DESCRIPTION = 500;

    @Id
    @XmlElement(required = true)
    private String id;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String description;

    /**
     * Erstellt eine Person, ohne Angabe von Werten
     */
    public PersonRecord() {
    }

    /** Erstellt ein neues Personenobjekt mit den angegebenen Werten
     *
     * @param name Name der Person
     * @param description Beschreibung der Person
     * @param id Identifikation der Person (uuid)
     */
    public PersonRecord(String name, String description, String id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    @Override
    public String toString() {
        return "DataRecord{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Ueberprueft die Werte des Users auf bestimmte Einschraenkungen
     * Laenge Name:         max 70 Zeichen
     * Laenge Beschreibung: 500 Zeichen
     */
    private void checkRecordData() {
        notNull(this.name, "Name cannot be null");
        notEmpty(this.name, "Name cannot be empty");
        isTrue(this.name.length() <= MAX_LENGTH_NAME, "Name cannot be longer than " + MAX_LENGTH_NAME + " characters");

        if (this.description != null) {
            isTrue(this.description.length() <= MAX_LENGTH_DESCRIPTION, "Description cannot be longer than " + MAX_LENGTH_DESCRIPTION + " characters");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
