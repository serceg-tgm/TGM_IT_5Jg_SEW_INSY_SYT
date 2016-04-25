package at.erceg_kritzl.dezsys10.utils;

import java.util.Map;

/**
 * Dieses Interface stellt die Methoden zur Verarbeitung der Benutzereingaben bereit.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public interface AppCLI {

	/**
	 * Speichert die Eingabe des Benutzers in Attribute, um diese dann auslesen zu koennen.
	 *
	 * @param args Eingabe des Benutzers
	 */
	public void parseArgs(String[] args);

	/**
	 * Liefert alle verfuegbaren Argumente in einer Map zurueck.
	 *
	 * @return Map mit den jeweiligen Argumenten
	 */
	public Map<String,String> getArgs();

	/**
	 * Zeigt alle verfuegbaren Argumente an und hilft dem Benutzer bei Eingabeproblemen.
	 */
	public void printHelp();

}
