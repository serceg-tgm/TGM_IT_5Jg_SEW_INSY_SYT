package at.erceg_kritzl.dezsys10.utils;

import at.erceg_kritzl.dezsys10.server.CalculatingServer;
import at.erceg_kritzl.dezsys10.server.PiCalculatingServer;

import java.util.*;

/**
 * Durch die Klasse werden je nach ausgewaehltem Balancer-Algorithmus die Server sortiert.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20160310
 */
public class ServerSorter {
    /**
     * Sortiert die Server je nach ausgewaehltem Balancer-Algorithmus.
     *
     * @param servers Liste von allen verfuegbaren Servern
     * @param attribute beispielsweise die Gewichtung der Server
     * @param asc Ueberpruefung, ob auf- oder absteigend
     * @return Liste der sortierten Server
     */
    public static List<CalculatingServer> sortByValues(List<CalculatingServer> servers, String attribute, boolean asc) {
        if (servers==null) return null;
        Collections.sort(servers, new Comparator<CalculatingServer>() {
            @Override
            public int compare(CalculatingServer o1, CalculatingServer o2) {
                if (asc)
                    return Integer.parseInt(o1.getArgs().get(attribute))-(Integer.parseInt(o2.getArgs().get(attribute)));
                else
                    return Integer.parseInt(o2.getArgs().get(attribute))-(Integer.parseInt(o1.getArgs().get(attribute)));
            }
        });
        return servers;
    }
}


