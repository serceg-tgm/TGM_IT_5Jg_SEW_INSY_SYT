package erceg_kritzl.utils;

import org.apache.commons.cli.ParseException;

import java.util.Map;

/**
 * Beschreibt die noetigen Eigenschaften eines CLI-Parsers.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160107.1
 */
public interface CLIHandler {
    /**
     * Parsen der angegebenen Argumente und interner Speicherung
     *
     * @param args Argumente der Benutzereingabe
     * @throws ParseException
     */
    public void parse(String[] args) throws ParseException;

    /**
     * Gibt alle erkannten Argumente in einer Map zurueck. <br>
     * "&lt;Name des Arguments&gt;": "&lt;Inhalt des Arguments&gt;"
     *
     * @return Die geparsten Argumente
     */
    public Map<String, String> getOptions();

    /**
     * Ausgabe der korrekten Verwendung der CLI
     */
    public void printHelp();
}
