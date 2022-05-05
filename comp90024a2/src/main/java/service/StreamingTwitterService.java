package service;

import dao.TwitterDao;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StreamingTwitterService {
    @Autowired
    private TwitterDao twitterDao;

    public List<Feature> searchByCountry(String country) {
        List<Tweet> tweets = twitterDao.searchByCountry(country);
        ArrayList<Feature> features = new ArrayList<>();
        for (Tweet tweet : tweets) {
            Feature feature = new Feature();
            feature.setId(tweet.getId());
            feature.setType(tweet.getCountry());
            feature.setCreated_at(tweet.getCreated_at());
            feature.setProperties(new Properties(new Content(tweet.getText(),
                    tweet.getPictures()),new User(tweet.getUser_name(),
                    tweet.getUser_avatar()), tweet.getTag()));
            feature.setGeometry(new Geometry(tweet.getCoordinates().getType(),
                    tweet.getCoordinates().getCoordinates()));
            features.add(feature);
        }
        return features;
    }
}
