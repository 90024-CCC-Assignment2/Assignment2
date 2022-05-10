package dao.impl;

import dao.IAurinDao;
import org.lightcouch.CouchDbClient;
import org.springframework.stereotype.Component;
import utils.CouchDBConnectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class Aurin2008DaoImpl implements IAurinDao {
    @Override
    public List<Integer> countByCountryList(List<String> countries) {
        List<Integer> integers = new ArrayList<>();
        CouchDbClient conn = CouchDBConnectionUtils.getConn("aurin_2008");
        for (String country : countries) {
            int count = conn.view("Aurin2008Info/Country").key(country).queryForInt();
            integers.add(count);
        }
        CouchDBConnectionUtils.closeConn(conn);
        return integers;
    }
}
