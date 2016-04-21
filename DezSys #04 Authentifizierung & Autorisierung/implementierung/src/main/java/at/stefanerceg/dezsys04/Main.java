package at.stefanerceg.dezsys04;

import javax.naming.*;
import javax.naming.directory.*;

import java.util.Hashtable;

/**
 * In dieser Klasse wird ein Versuch zum Erstellen eines initialen Kontexts zu einem LDAP-Server durchgefuehrt.
 * Falls dies erfolgen kann, konnte sich der User erfolgreich authentifizieren. Ausserdem wird in der Klasse
 * noch ueberprueft, ob der User sich in einer bestimmte Gruppe befindet.
 *
 * @author Stefan Erceg
 * @since 20.11.2015
 * @version 1.0
 */
class Main {

    // zu Beginn werden Attribute, wie z.B. der Hostname, der Username und das Userpasswort, initialisiert

    public static final String HOST = "ldap://10.0.106.159:389";
    public static final String AUTH_METHOD = "simple";
    public static final String USER_NAME = "serceg";
    public static final String USER_DATA = "cn=stefan.erceg,dc=nodomain,dc=com";
    public static final String USER_PASSWORD = "12345";
    public static final String SEARCH_GROUP = "group.service1";

    /**
     * In der main-Methode wird der Versuch einer Authentifizierung und Autorisierung durchgefuehrt.
     */
    public static void main(String[] args) {

        // die Umgebung zum Erstellen des initialen Kontexts wird definiert

        Hashtable<String, Object> env = new Hashtable<String, Object>(11);

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, HOST);
        env.put(Context.SECURITY_AUTHENTICATION, AUTH_METHOD);
        env.put(Context.SECURITY_PRINCIPAL, USER_DATA);
        env.put(Context.SECURITY_CREDENTIALS, USER_PASSWORD);

        System.out.println("Connecting to host " + HOST);

        try {
            // der initiale Kontext wird erstellt
            DirContext ctx = new InitialDirContext(env);

            // falls eine Authentifizierung erfolgen konnte, wird eine Erfolgsmeldung ausgegeben
            System.out.println("Authentifizierung: OK");

            /* hier wird definiert, wo genau im Verzeichnisdienst nach dem User gesucht werden soll und nach welcher
                Zeit ein Timeout erfolgt */
            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            searchControls.setTimeLimit(30000);

            // nun wird ueberprueft, ob der User "serceg" sich in der Gruppe "group.service1" befindet
            NamingEnumeration<?> namingEnum = ctx.search("cn=" + SEARCH_GROUP + ",dc=nodomain,dc=com", "(objectclass=posixGroup)", searchControls);
            while (namingEnum.hasMore()) {
                SearchResult result = (SearchResult) namingEnum.next();
                Attributes attrs = result.getAttributes();
                if(attrs.get("memberUid") != null && attrs.get("memberUid").contains(USER_NAME))
                    System.out.println("Autorisierung fuer die Gruppe " + SEARCH_GROUP + ": OK");
                else
                    System.out.println("Autorisierung fuer die Gruppe " + SEARCH_GROUP + ": NOK");
            }

            // Kontext wird geschlossen
            ctx.close();

        // falls keine Authentifizierung erfolgen konnte, wird eine Fehlermeldung ausgegeben
        } catch (NamingException e) {
            System.out.println("Authentifizierung: NOK");
        }
    }
}