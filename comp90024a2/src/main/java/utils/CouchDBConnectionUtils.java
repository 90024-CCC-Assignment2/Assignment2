package utils;

import org.lightcouch.CouchDbClient;

public class CouchDBConnectionUtils {

    public static CouchDbClient getConn(String country) {
        CouchDbClient dbClient = new CouchDbClient(country, false, "http", "127.0.0.1", 5984,
                "admin", "123456");

        return dbClient;
    }

    public static void closeConn(CouchDbClient conn) {
        try {
            conn.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
