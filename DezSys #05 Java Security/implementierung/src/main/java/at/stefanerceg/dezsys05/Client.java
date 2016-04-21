package at.stefanerceg.dezsys05;

import javax.crypto.*;
import javax.xml.bind.DatatypeConverter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Diese Klasse repraesentiert einen Client, welcher mittels Sockets eine Verbindung zum Service aufbaut. Byte-Arrays
 * koennen dabei mittels DataInputStream und DataOutputStream gesendet werden.
 * Die Aufgabe vom Client ist es, sich den Public Key, welcher in der Description einer bestimmten Gruppe am
 * LDAP-Server steht, zu holen und mit diesem Public Key einen generierten symmetrischen Key zu verschluesseln. Die
 * verschluesselte Nachricht vom Service wird daraufhin empfangen und entschluesselt und die Nachricht in die Konsole
 * geschrieben.
 *
 * @author Stefan Erceg
 * @since 27.11.2015
 * @version 1.0
 */
public class Client {

    // alle notwendigen Attribute: Socket, Host, Port, LDAPConnector, usw.
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String host;
    private int port;
    private LDAPConnector connector;

    private SecretKey symKey;
    private PublicKey pubKey;

    /**
     * In dem Konstruktor werden die Host-Adresse und der Port des Servers als Parameter uebergeben. Ausserdem wird
     * hier bereits die Methode zum Generieren des symmetrischen Keys aufgerufen.
     *
     * @param host Host des Servers
     * @param port Port des Servers
     */
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.connector = new LDAPConnector();

        this.symKey = generateSymKey();
        this.pubKey = null;
    }

    /**
     * Der Client baut eine Verbindung zum Server auf (Angabe der Host-Adresse und des Ports vom Server erfolgte
     * bereits im Konstruktor).
     */
    public void connectToServer() {
        try {
            this.socket = new Socket(host, port);
            this.in = new DataInputStream(this.socket.getInputStream());
            this.out = new DataOutputStream(this.socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mit dieser Methode wird es ermoeglicht, das Byte-Array vom Server zu lesen. Hier kommt die Klasse
     * DataInputStream zur Verwendung.
     * @return Byte-Array vom Server
     */
    public byte[] readMsg() {
        try {
            int length = this.in.readInt();
            if (length > 0) {
                byte[] message = new byte[length];
                this.in.readFully(message, 0, message.length);
                return message;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Mit dieser Methode wird es ermoeglicht, ein bestimmtes Byte-Array zu schreiben, welches spaeter an den
     * Server gesendet wird. Hier kommt die Klasse DataOutputStream zur Verwendung.
     * @param bytes Byte-Array, welches spaeter an der Server gesendet wird
     */
    public void writeMsg(byte[] bytes) {
        try {
            out.writeInt(bytes.length);
            out.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ein Hex-String wird in ein Byte-Array umgewandelt.
     * @param s Hex-String
     * @return Byte-Array
     */
    private byte[] hexStringToByteArray(String s) {
        System.out.println("Client wandelt folgenden Hex-String erneut in ein Byte-Array um: " + s);
        return DatatypeConverter.parseHexBinary(s);
    }

    /**
     * Der Client holt sich den Public Key von der Description einer bestimmten Gruppe am LDAP-Server. Dieser
     * Hex-String wird in ein Byte-Array zurueck umgewandelt.
     * @return Public Key vom LDAP-Server
     */
    public PublicKey getPublicKey() {

        try {
            // Hex-String vom LDAP-Server wird erneut in Byte-Array umgewandelt
            String ldapKey = this.connector.getDescription();
            byte[] key = hexStringToByteArray(ldapKey);

            // Erstellung einer Key-Spezifikation, damit Java einen Public Key generieren kann
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Den Public-Key, welchen man dem Attribut "pubKey" zuweisen moechte, wird als Parameter uebergeben.
     * @param pk Public-Key
     */
    public void setPublicKey(PublicKey pk) {
        this.pubKey = pk;
    }

    /**
     * Hier wird ein symmetrischer Key mittels des AES-Verfahrens generiert.
     * @return generierter symmetrischer Key
     */
    public SecretKey generateSymKey() {

        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            System.out.println("Client generiert den symmetrischen Key.");
            return keygen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Mittels der Sockets wird der symmetrische Key, welcher mit dem Public Key verschluesselt wird, zum Service
     * gesendet.
     */
    public void encryptSymKey() {

        try {
            // Verschluesselung des symmetrischen Keys mit dem Public Key
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, this.pubKey);
            System.out.println("Client verschluesselt den symmetrischen Key mit dem Public-Key.");
            byte[] encoded = cipher.doFinal(this.symKey.getEncoded());

            // verschluesselter symmetrischer Key wird an Server gesendet
            this.writeMsg(encoded);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | InvalidKeyException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Die verschluesselte Nachricht vom Service wird mittels des AES-Verfahrens erneut entschluesselt. Dazu wird
     * erneut der symmetrische Key verwendet. Mittels der Sockets wird die verschluesselte Nachricht vom Server
     * gelesen.
     */
    public void printDecryptedMessage() {
        try {
            // AES-Verfahren wird zum Entschluesseln der Nachricht mit dem symmetrischen Key verwendet
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, this.symKey);

            // verschluesselte Nachricht vom Server wird entschluesselt
            byte[] decoded = cipher.doFinal(this.readMsg());
            System.out.println("Client entschluesselt folgende Nachricht vom Server: " + new String(decoded));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | InvalidKeyException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Die Data-In-/Output-Streams, das Socket und die Verbindung zum LDAP-Server werden geschlossen.
     */
    public void closeAllConnections() {
        try {
            System.out.println("Client schliesst die Verbindung.");
            out.close();
            in.close();
            socket.close();
            this.connector.closeContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}