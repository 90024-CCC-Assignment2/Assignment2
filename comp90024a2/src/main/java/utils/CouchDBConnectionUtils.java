package utils;

import org.lightcouch.CouchDbClient;

public class CouchDBConnectionUtils {

    public static CouchDbClient getConn(String dbName) {
        CouchDbClient conn = new CouchDbClient(dbName, false, "http", "127.0.0.1", 5984,
                "admin", "123456");
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
