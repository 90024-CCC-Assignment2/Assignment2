package utils;


import domain.*;

import java.util.ArrayList;
import java.util.List;

public class TweetUtils {

    public static List<Feature> getFeatures(List<Tweet> tweets) {
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
