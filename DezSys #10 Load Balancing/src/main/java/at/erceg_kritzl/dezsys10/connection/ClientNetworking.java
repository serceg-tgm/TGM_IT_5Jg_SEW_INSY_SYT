package at.erceg_kritzl.dezsys10.connection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Der Client-Socket verbindet sich hier zu einem Server-Socket. Methoden zum Verbinden bzw. Disconnecten einer
 * Client-Socket-Verbindung werden unter anderem angeboten.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public class ClientNetworking implements Runnable{
    protected static final Logger LOG = LogManager.getLogger(ClientNetworking.class);

    private Socket socket;

    private String host;
    private int port;

    private volatile boolean running = true;

    private DataInputStream input;
    private DataOutputStream output;

    private MessageHandler handler;

    /**
     * Im Konstruktor werden der Message-Handler und die Host-Adresse und der Port des Server-Sockets initialisiert.
     *
     * @param handler Handler zum Verwalten einer Nachricht
     * @param host Host-Adresse des Server-Sockets
     * @param port Port des Server-Sockets
     */
    public ClientNetworking(MessageHandler handler, String host, int port) {
        this.handler = handler;
        this.host = host;
        this.port = port;
    }

    /**
     * Bei diesem Konstruktor wird statt der Host-Adresse und des Ports des Server-Sockets ein Socket initialisiert.
     *
     * @param handler Handler zum Verwalten einer Nachricht
     * @param socket Socket
     */
    public ClientNetworking(MessageHandler handler, Socket socket) {
        this.handler = handler;
        this.socket=socket;

        try {
            input = new DataInputStream(this.socket.getInputStream());
            output = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gibt die Host-Adresse und den Port der Verbindung zurueck.
     *
     * @return String der Verbindung
     */
    public String getDestination() {
        return this.socket.getInetAddress().getHostName() + ":" + this.socket.getPort();
    }

    /**
     * Der Client verbindet sich hier zum Server-Socket. Daraufhin holt sich dieser die Streams zum Lesen und Schreiben
     * einer Nachricht.
     */
    public void connect() {

        try {
            // connect to server socket, get streams to read/write
            this.socket = new Socket(host, port);
            input = new DataInputStream(this.socket.getInputStream());
            output = new DataOutputStream(this.socket.getOutputStream());

            // start listening for incoming messages
            new Thread(this).start();

        } catch (Exception e) {
            LOG.error(e.getMessage());
            System.exit(-1);
        }

    }

    /**
     * Durch diese Methode werden der Thread und die IO-Streams geschlossen.
     */
    public void disconnect() {

        this.running = false;

        try {
            this.input.close();
            this.output.close();
        } catch (IOException e) {
            LOG.error(e.getMessage());
            System.exit(-1);
        }

        try {
            socket.close();
        } catch (IOException e) {
            LOG.error(e.getMessage());
            System.exit(-1);
        }

    }

    /**
     * Sendet die Laenge und den Inhalt einer Nachricht zum verbundenen Netzwerk.
     *
     * @param message Nachricht, die gesendet werden soll
     */
    public void write(String message) {

        try {
            this.output.writeInt(message.length());
            this.output.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Hier wird zunaechst auf eine neue Nachricht gewartet solange der jeweilige Thread laueft. Danach wird die Laenge
     * und der Inhalt der Nachricht gelesen.
     */
    @Override
    public void run() {

        if (input == null || output == null)
            throw new IllegalStateException("Unable to listen for incoming messages: Connection has not been established yet.");

        // while running is true listen for new messages
        while (running){
            try {

                // at first read the length of the message (sent as int)
                int length = input.readInt();

                // then read the message content
                byte[] messageContentBytes = new byte[length];
                input.readFully(messageContentBytes, 0, messageContentBytes.length);
                String messageContent = new String(messageContentBytes);
                handler.handleMessage(this, messageContent);
            } catch (IOException e) {
                LOG.info("Connection closed: " + this.getDestination());
                this.disconnect();
            }
        }

    }
}
