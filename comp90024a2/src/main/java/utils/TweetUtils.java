package utils;


import domain.*;

import java.util.ArrayList;
import java.util.List;

public class TweetUtils {

    public static List<Feature> getFeatures(List<Tweet> tweets) {
        ArrayList<Feature> features = new ArrayList<>();
        for (Tweet tweet : tweets) {
            Feature feature = new Feature();
            feature.setId(tweet.get_id());
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

    public static <T> List<List<T>> zip(List<T>... lists) {
        List<List<T>> zipped = new ArrayList<List<T>>();
        for (List<T> list : lists) {
            for (int i = 0, listSize = list.size(); i < listSize; i++) {
                List<T> list2;
                if (i >= zipped.size())
                    zipped.add(list2 = new ArrayList<T>());
                else
                    list2 = zipped.get(i);
                list2.add(list.get(i));
            }
        }
        return zipped;
    }
}
