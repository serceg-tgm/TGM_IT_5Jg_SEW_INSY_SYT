package at.stefanerceg.dezsys05;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;

/**
 * Diese Klasse repraesentiert ein Service, welcher mit einem Server-Socket einen Port oeffnet und einen
 * Verbindungsaufbau mit dem Client gewaehrleistet. Byte-Arrays koennen dabei mittels DataInputStream und
 * DataOutputStream gesendet werden.
 * Die Aufgabe vom Service ist es, den generierten Public Key in die Description einer bestimmten Gruppe am
 * LDAP-Server zu speichern. Der symmetrische Key vom Client wird daraufhin empfangen und eine verschluesselte
 * Nachricht gesendet.
 *
 * @author Stefan Erceg
 * @since 27.11.2015
 * @version 1.0
 */
public class Service1 {

    // alle notwendigen Attribute: Server- und Client-Socket, LDAPConnector, usw.
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private LDAPConnector connector;

    private KeyPair keyPair;
    private SecretKey symKey;

    /**
     * In dem Konstruktor wird der Port, der fuer die Verbindung mit dem Client geoeffnet werden soll, als Parameter
     * uebergeben. Ausserdem wird hier bereits die Methode zum Generieren des Public-Keys aufgerufen.
     * @param port Port, der geoeffnet werden soll
     */
    public Service1(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            this.connector = new LDAPConnector();
            this.keyPair = generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * In dieser Methode erlaubt der Server die Client-Verbindung zu dem Socket und startet somit die Kommunikation.
     */
    public void startConnection() {
        new Thread(() -> {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Verbindung zwischen Server und Client wurde aufgebaut.");
                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Hier liest der Server das Byte-Array vom Client aus. Hier kommt die Klasse DataInputStream zur Verwendung.
     * @return Byte-Array vom Client
     */
    public byte[] readByte() {
        try {
            int length = in.readInt();
            byte[] message = new byte[length];
            in.readFully(message, 0, message.length);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Ein Byte-Array wird in ein Hex-String umgewandelt.
     * @param array Byte-Array
     * @return Hex-String
     */
    private String byteArrayToHexString(byte[] array) {
        String hex = DatatypeConverter.printHexBinary(array);
        System.out.println("Server wandelt das Byte-Array in folgenden Hex-String um: " + hex);
        return hex;
    }

    /**
     * Hier wird der asymmetrische Key mittels des RSA-Verfahrens generiert.
     * @return asymmetrischer Key
     * @throws NoSuchAlgorithmException Default
     * @throws NoSuchProviderException Default
     */
    private KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        // RSA-Verfahren wird angewendet
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");

        // einige Eigenschaften werden angegeben, damit der Schluessel tatsaechlich zufaellig generiert wird
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        generator.initialize(1024, random);

        // asymmetrischer Key wird generiert
        KeyPair keyPair = generator.generateKeyPair();
        System.out.println("Server generiert den Public-Key.");
        return keyPair;
    }

    /**
     * Hier wird die Methode zum Umwandeln des Byte-Arrays in ein Hex-String aufgerufen. Der Public Key (in
     * String-Form) wird in die Description einer bestimmten Gruppe am LDAP-Server gespeichert.
     */
    public void updateDescription() {
        System.out.println("Server speichert den Public-Key in die Description der Gruppe " + this.connector.GROUP +
                " am LDAP-Server.");
        this.connector.setDescription(byteArrayToHexString(this.keyPair.getPublic().getEncoded()));
    }

    /**
     * Der symmetrische Key vom Client wird mit dem Private Key vom Key-Pair entschluesselt.
     */
    public void decryptSymKey() {
        try {
            // symmetrischer Key wird gelesen
            byte[] encryptedSymKey = this.readByte();

            // RSA-Verfahren wird angewendet und der symmetrische Key wird entschluesselt
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            System.out.println("Server entschluesselt den symmetrischen Key erneut mit dem Private-Key.");
            byte[] decrypted = cipher.doFinal(encryptedSymKey);
            this.symKey = new SecretKeySpec(decrypted, 0, decrypted.length, "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Eine bestimmte Nachricht, welche ueber den Parameter angegeben wird, wird mittels des symmetrischen Keys
     * verschluesselt und zum Client gesendet.
     * @param message Nachricht, welche verschluesselt und spaeter vom Client entschluesselt wird
     */
    public void sendEncryptedMessage(String message) {
        if (this.symKey != null) {
            try {
                // AES-Verfahren wird verwendet und Nachricht wird mit symmetrischen Key verschluesselt
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, this.symKey);
                byte[] encrypted = cipher.doFinal((message).getBytes());

                // Nachricht wird zum Client gesendet
                this.out.writeInt(encrypted.length);
                this.out.write(encrypted);

                System.out.println("Server verschluesselt folgende Nachricht: " + message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new NullPointerException("Symmetrischer Key ist null!");
        }
    }

    /**
     * Die Data-In-/Output-Streams, das Client-Socket und die Verbindung zum LDAP-Server werden geschlossen.
     */
    public void closeAllConnections() {
        try {
            System.out.println("Server schliesst die Verbindung.");
            out.close();
            in.close();
            clientSocket.close();
            this.connector.closeContext();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}