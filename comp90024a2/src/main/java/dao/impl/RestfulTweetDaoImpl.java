package dao.impl;

import dao.ITweetDao;
import domain.Tweet;
import org.lightcouch.CouchDbClient;
import org.lightcouch.NoDocumentException;
import org.springframework.stereotype.Component;
import utils.CouchDBConnectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestfulTweetDaoImpl implements ITweetDao {

    @Override
    public List<Tweet> findByCountryPeriod(String country, String startDate, String endDate) {
        ArrayList start = new ArrayList<String>();
        start.add(country);
        start.add(startDate);
        ArrayList end = new ArrayList<String>();
        end.add(country);
        end.add(endDate);
        CouchDbClient conn = CouchDBConnectionUtils.getConn("restful");
        List<Tweet> list = conn.view("RestfulInfo/CountryAndCreated_at")
                .startKey(start) //["China","2014-07-28"]
                .endKey(end) //["China","2014-07-30"]
                .includeDocs(true)
                .query(Tweet.class);
        CouchDBConnectionUtils.closeConn(conn);
        return list;
    }

    @Override
    public int countByCountryAndTag(String country, int tag) {
        ArrayList key = new ArrayList<String>();
        key.add(country);
        key.add(tag);
        CouchDbClient conn = CouchDBConnectionUtils.getConn("restful");
        int count;
        try {
            count = conn.view("RestfulInfo/CountryAndTag").key(key).queryForInt();
        }catch (NoDocumentException e){
            count = 0;
        }
        CouchDBConnectionUtils.closeConn(conn);
        return count;
    }
}