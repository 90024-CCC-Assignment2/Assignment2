package service.impl;

import dao.ITweetDao;
import domain.*;
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
public class HistoricalTweetServiceImpl implements ITweetService {

    @Autowired
    private ITweetDao historicalTweetDaoImpl;

    @Override
    public List<Feature> searchByCountryPeriod(String country, String period) throws ParseException {
        String startDate =  DateCalculationUtils.calculateByGivenDate(Constant.HISTORICAL_TWEET_END_DATE,period);
        List<Tweet> tweets = historicalTweetDaoImpl.findByCountryPeriod(country, startDate,
                Constant.HISTORICAL_TWEET_END_DATE);
        return TweetUtils.getFeatures(tweets);
    }

    @Override
    public List<String> searchPictureByCountryPeriod(String country, String period) throws ParseException {
        String startDate =  DateCalculationUtils.calculateByGivenDate(Constant.HISTORICAL_TWEET_END_DATE,period);
        List<Tweet> tweets = historicalTweetDaoImpl.findByCountryPeriod(country, startDate, Constant.HISTORICAL_TWEET_END_DATE);
        ArrayList<String> picUrls = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (!StringUtils.isEmpty(tweet.getPictures())){
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
            int like = historicalTweetDaoImpl.countByCountryAndTag(country, 1);
            int dislike = historicalTweetDaoImpl.countByCountryAndTag(country, 0);
            int total = like + dislike;
            tagCounts.add(new TagCount(country,total,like,dislike));
        }
        return tagCounts;
    }
}
