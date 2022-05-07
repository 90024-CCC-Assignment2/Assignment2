package dao.impl;

import dao.ITweetDao;
import domain.Tweet;
import org.lightcouch.CouchDbClient;
import utils.CouchDBConnectionUtils;

import java.util.ArrayList;
import java.util.List;

public class HistoricalTweetDaoImpl implements ITweetDao {
    @Override
    public List<Tweet> findByCountryPeriod(String country, String startDate, String endDate) {
        ArrayList start = new ArrayList<String>();
        //"China"
        start.add(country);
        //"2014-07-28"
        start.add("2014-07-28");
        ArrayList end = new ArrayList<String>();
        //"China"
        end.add(country);
        //"2014-07-31"
        end.add("2014-07-31");
        CouchDbClient conn = CouchDBConnectionUtils.getConn("myhistorical");
        List<Tweet> list = conn.view("HistoricalInfo/CountryAndCreated_at")
                .startKey(start) //["China","2014-07-28"]
                .endKey(end) //["China","2014-07-30"]
                .includeDocs(true)
                .query(Tweet.class);
        CouchDBConnectionUtils.closeConn(conn);
        return list;
    }
    @Override
    public int countByCountryAndTag(String country, int tag){
        ArrayList key = new ArrayList<String>();
        key.add(country);
        key.add(tag);
        CouchDbClient conn = CouchDBConnectionUtils.getConn("myhistorical");
        int count = conn.view("HistoricalInfo/CountryAndTag").key(key).queryForInt();
        CouchDBConnectionUtils.closeConn(conn);
        return count;
    }

}
