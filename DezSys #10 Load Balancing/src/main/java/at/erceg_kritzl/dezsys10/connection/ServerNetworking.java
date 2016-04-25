package at.erceg_kritzl.dezsys10.connection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Der Server-Socket verbindet sich hier eroeffnet einen Port, auf welchen sich die Client-Sockets verbinden koennen.
 * Methoden zum Verbinden bzw. Disconnecten einer Client-Socket-Verbindung werden unter anderem angeboten.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public class ServerNetworking implements Runnable{
    protected static final Logger LOG = LogManager.getLogger(ServerNetworking.class);

    private ServerSocket serverSocket;
    private volatile boolean running = true;

    private MessageHandler handler;

    /**
     * Im Konstruktor wird ein neuer Server-Socket auf einem angegebenen Port eroeffnet.
     *
     * @param port Port, der eroeffnet wird
     * @param handler Handler zum Verwalten einer Nachricht
     */
    public ServerNetworking(int port, MessageHandler handler) {
        this.handler = handler;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * Der Server akzeptiert hier eine Client-Verbindung. Daraufhin holt sich dieser die Streams zum Lesen und Schreiben
     * einer Nachricht.
     *
     * @return neu eroeffneter Socket
     */
    public Socket connect() {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Durch diese Methode werden der Thread und der Client- und Server-Socket geschlossen.
     */
    public void disconnect() {

        this.running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            LOG.error(e.getMessage());
            System.exit(-1);
        }

    }

    /**
     * Hier wird ein neuer Thread gestartet, indem ein Client-Netzwerk angegeben wird.
     */
    @Override
    public void run() {
        while (running) {
            Socket socket = this.connect();
            new Thread(new ClientNetworking(handler, socket)).start();
        }

    }
}
