package at.erceg_kritzl.dezsys10.server;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Stellt die Berechnung von Pi dar.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public interface Calculator extends Remote, Serializable {

	/**
	 * Berechnet Pi und gibt dies wieder zurueck.
	 *
	 * @param anzNachkommastellen Anzahl der Nachkommastellen, die von Pi berechnet werden sollen
	 * @return berechnetes Pi
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public abstract BigDecimal pi(int anzNachkommastellen) throws RemoteException, NotBoundException;

}
