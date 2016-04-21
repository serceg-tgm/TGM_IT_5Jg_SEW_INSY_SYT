package at.stefanerceg.dezsys05;

/**
 * In der Main-Klasse wird lediglich der Ablauf des Programms in der richtigen Reihenfolge (Server und Client
 * werden erstellt, Server speichert Public Key am LDAP-Server, usw.) abgearbeitet.
 *
 * @author Stefan Erceg
 * @since 27.11.2015
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        // Objekte vom Server und Client werden erstellt
        Service1 s = new Service1(12345);
        Client c = new Client("localhost",12345);

        // Public Key wird vom Server am LDAP-Server gespeichert - Client holt sich diesen
        s.updateDescription();
        c.setPublicKey(c.getPublicKey());

        // Verbindung zwischen Server und Client wird aufgebaut
        s.startConnection();
        c.connectToServer();

        // symmetrischer Key wird ver- und entschluesselt
        c.encryptSymKey();
        s.decryptSymKey();

        // Nachricht wird ver- und entschluesselt
        s.sendEncryptedMessage("Mal schauen, ob's funktioniert...");
        c.printDecryptedMessage();

        // alle moeglichen Verbindungen werden geschlossen
        c.closeAllConnections();
        s.closeAllConnections();
    }
}