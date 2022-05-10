package utils;

import org.lightcouch.CouchDbClient;

public class CouchDBConnectionUtils {

    public static CouchDbClient getConn(String dbName) {
        CouchDbClient conn = new CouchDbClient(dbName, false, "http", "172.26.133.175", 5984,
                "admin", "password1234");
        return conn;
    }

    public static void closeConn(CouchDbClient conn) {
        try {
            conn.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
