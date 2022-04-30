package service;

import dao.TwitterDao;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TwitterService {
    @Autowired
    private TwitterDao twitterDao;

    public List<Feature> searchByCountry(String country) {
        List<Twitter> twitters = twitterDao.searchByCountry(country);
        ArrayList<Feature> features = new ArrayList<>();
        for (Twitter twitter : twitters) {
            Feature feature = new Feature();
            feature.setId(twitter.getId());
            feature.setType(twitter.getCountry());
            feature.setCreated_at(twitter.getCreated_at());
            feature.setProperties(new Properties(new Content(twitter.getText(),
                    twitter.getPictures()),new User(twitter.getUser_name(),
                    twitter.getUser_avatar()), twitter.getTag()));
            feature.setGeometry(new Geometry("Point",twitter.getCoordinates()));
            features.add(feature);
        }
        return features;
    }
}
