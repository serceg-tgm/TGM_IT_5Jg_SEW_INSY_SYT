package at.stefanerceg.dezsys05;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;

/**
 * Mittels dieser Klasse wird es ermoeglicht, eine Verbindung zu einem LDAP-Server aufzubauen. Dabei wird ein
 * initialer Kontext erstellt. Ausserdem bietet die Klasse noch Methoden, um sich die Description von einer
 * bestimmten Gruppe zu holen und diese auch zu setzen bzw. zu veraendern.
 *
 * @author Stefan Erceg
 * @since 27.11.2015
 * @version 1.0
 */
public class LDAPConnector {

    private static DirContext ctx = null;

    // zu Beginn werden Attribute, wie z.B. der Hostname, der Username und das Userpasswort, initialisiert

    public static final String HOST = "ldap://10.0.0.5:389";
    public static final String AUTH_METHOD = "simple";
    public static final String USER_NAME = "admin";
    public static final String USER_DATA = "cn=" + USER_NAME + ",dc=nodomain,dc=com";
    public static final String AUTH_PASSWORD = "user";

    // in diesem Fall holen wir uns die Description von der Gruppe "group.service2" und werden diese setzen
    public final String GROUP = "group.service2";

    /**
     * In dem Konstruktor werden zu Beginn alle notwendigen Informationen, wie z.B. der Hostname, der Username
     * und das Userpasswort, in eine Hash-Table gespeichert. Danach wird versucht, aus dieser Hash-Table einen
     * initalen Kontext zu erstellen.
     */
    public LDAPConnector() {

        // die Umgebung zum Erstellen des initialen Kontexts wird definiert
        Hashtable<String, Object> env = new Hashtable<>(11);

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, HOST);
        env.put(Context.SECURITY_AUTHENTICATION, AUTH_METHOD);
        env.put(Context.SECURITY_PRINCIPAL, USER_DATA);
        env.put(Context.SECURITY_CREDENTIALS, AUTH_PASSWORD);

        // der initiale Kontext wird erstellt
        try {
            ctx = new InitialDirContext(env);
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

    /**
     * Hier wird zu Beginn nach der Gruppe, die man angegeben hat, gesucht. Sobald diese gefunden wurde, wird
     * ueberprueft, ob diese eine bestimmte Description besitzt. Falls dies so ist, wird die Description
     * zurueckgegeben.
     * @return Description der bestimmten Gruppe (falls eine vorhanden ist)
     */
    public String getDescription() {
        NamingEnumeration results;

        try {
            // Angabe, nach welchem Muster gesucht werden soll
            results = ctx.search("dc=nodomain,dc=com", "(&(objectclass=PosixGroup)(cn=" + GROUP + "))", new SearchControls());
            while (results.hasMore()) {
                // Suche nach Gruppe
                SearchResult sr = (SearchResult) results.next();
                // wenn Description der gefundenen Gruppe bestimmten Inhalt besitzt, wird diese zurueckgegeben
                if (sr.getAttributes().get("description") != null) {
                    return sr.getAttributes().get("description").get().toString();
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Diese Methode dient dazu, eine Description, welche als Parameter uebergeben wird, fuer eine bestimmte Gruppe
     * zu setzen.
     * @param description Description, die man fuer die bestimmte Gruppe setzen moechte
     */
    public void setDescription(String description) {

        // Angabe, dass Description geaendert werden soll
        ModificationItem[] mods = new ModificationItem[1];
        Attribute mod0 = new BasicAttribute("description", description);
        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);

        // Attribut wird geandert
        try {
            ctx.modifyAttributes("cn=" + GROUP + ",dc=nodomain,dc=com", mods);
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

    /**
     * Die Methode dient zum Schliessen des Kontext.
     */
    public void closeContext() {
        try {
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}