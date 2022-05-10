package service.impl;

import dao.ITweetDao;
import domain.Feature;
import domain.TagCount;
import domain.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ITweetService;
import utils.Constant;
import utils.DateCalculationUtils;
import utils.StringUtils;
import utils.TweetUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestfulTweetServiceImpl implements ITweetService {

    @Autowired
    private ITweetDao restfulTweetDaoImpl;

    @Override
    public List<Feature> searchByCountryPeriod(String country, String period) throws ParseException {
        String startDate =  DateCalculationUtils.calculateByCurrentDate(period);
        List<Tweet> tweets = restfulTweetDaoImpl.findByCountryPeriod(country, startDate,
                DateCalculationUtils.getCurrentFormatDate());
        return TweetUtils.getFeatures(tweets);
    }

    @Override
    public List<String> searchPictureByCountryPeriod(String country, String period) throws ParseException {
        String startDate =  DateCalculationUtils.calculateByCurrentDate(period);
        List<Tweet> tweets = restfulTweetDaoImpl.findByCountryPeriod(country, startDate, DateCalculationUtils.getCurrentFormatDate());
        ArrayList<String> picUrls = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (!StringUtils.isEmpty(tweet.getPictures())) {
                picUrls.add(tweet.getPictures());
                if (picUrls.size() >= 5) {
                    break;
                }
            }
        }
        return picUrls;
    }

    @Override
    public List<TagCount> countTag() {
        List<TagCount> tagCounts = new ArrayList<TagCount>();
        for (String country : Constant.COUNTRY_LIST) {
            int like = restfulTweetDaoImpl.countByCountryAndTag(country, 1);
            int dislike = restfulTweetDaoImpl.countByCountryAndTag(country, 0);
            int total = like + dislike;
            tagCounts.add(new TagCount(country,total,like,dislike));
        }
        return tagCounts;
    }
}
