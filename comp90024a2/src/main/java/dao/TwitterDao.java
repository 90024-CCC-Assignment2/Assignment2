package dao;

import domain.Tweet;
import org.lightcouch.CouchDbClient;
import org.springframework.stereotype.Component;
import utils.CouchDBConnectionUtils;

import java.util.List;

@Component
public class TwitterDao {
    public List<Tweet> searchByCountry(String country) {
        CouchDbClient conn = CouchDBConnectionUtils.getConn(country);
        String jsonQuery = "{\"selector\":{\"country\":\""+country+"\"}}";
        List<Tweet> docs = conn.findDocs(jsonQuery, Tweet.class);
        CouchDBConnectionUtils.closeConn(conn);
        return docs;
    }
}
