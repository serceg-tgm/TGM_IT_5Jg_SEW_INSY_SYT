package at.erceg_kritzl.dezsys10.connection;

/**
 * Durch dieses Interface werden die Nachrichten, die der jeweilige Applikationstyp sendet bzw. erhaelt, verwaltet.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public interface MessageHandler {

	/**
	 * Die Nachrichten, die der jeweilige Applikationstyp sendet bzw. erhaelt, werden verwaltet.
	 *
	 * @param message Nachricht, die gesendet wird
	 */
	public void handleMessage(ClientNetworking clientNetworking, String message);

}
