package dao.impl;

import dao.ITweetDao;
import domain.Tweet;

import java.util.List;

public class StreamingTweetDaoImpl implements ITweetDao {
    @Override
    public List<Tweet> findByCountryPeriod(String country, String startDate, String endDate) {
        return null;
    }

    @Override
    public int countByCountryAndTag(String country, int tag) {
        return 0;
    }
}
