package service;

import dao.TwitterDao;
import domain.Feature;
import domain.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreamingTwitterService {
    @Autowired
    private TwitterDao twitterDao;

    public List<Feature> searchByCountry(String country) {
        List<Tweet> tweets = twitterDao.searchByCountry(country);
        return null;
        //getFeatures(tweets);
    }
}
