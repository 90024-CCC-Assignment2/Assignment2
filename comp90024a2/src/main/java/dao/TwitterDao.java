package dao;

import domain.Twitter;
import org.lightcouch.CouchDbClient;
import org.springframework.stereotype.Component;
import utils.CouchDBConnectionUtils;

import java.util.List;

@Component
public class TwitterDao {
    public List<Twitter> searchByCountry(String country) {
        CouchDbClient conn = CouchDBConnectionUtils.getConn(country);
        String jsonQuery = "{\"selector\":{\"country\":\""+country+"\"}}";
        List<Twitter> docs = conn.findDocs(jsonQuery, Twitter.class);
        CouchDBConnectionUtils.closeConn(conn);
        return docs;
    }
}
