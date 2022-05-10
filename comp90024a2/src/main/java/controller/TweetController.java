package controller;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.IAurinService;
import service.ITweetService;
import utils.Constant;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/tweet")
@CrossOrigin(origins = "*")
public class TweetController {
    @Autowired
    private ITweetService historicalTweetServiceImpl;
    @Autowired
    private ITweetService restfulTweetServiceImpl;
    @Autowired
    private ITweetService streamingTweetServiceImpl;
    @Autowired
    private IAurinService aurinServiceImpl;


    @RequestMapping(path = "/search-tweet", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult searchByDbTypeCountryPeriod(String dbType, String country, String period) throws ParseException {
        List<Feature> features;
        if (dbType == Constant.DB_TYPE_HISTORICAL){
            features = historicalTweetServiceImpl.searchByCountryPeriod(country, period);
        }else if (dbType == Constant.DB_TYPE_RESTFUl){
            features = restfulTweetServiceImpl.searchByCountryPeriod(country,period);
        }else if (dbType == Constant.DB_TYPE_STREAMING){
            features = streamingTweetServiceImpl.searchByCountryPeriod(country,period);
        }else {
            return JsonResult.error("Parameter error");
        }
        Data data = new Data();
        data.setFeatures(features);
        RequestedJson geojson = new RequestedJson("geojson", data);
        JsonResult success = JsonResult.success(geojson);
        return success;
    }

    @RequestMapping(path = "/search-pic", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult searchPictureByDbTypeCountryPeriod(String dbType, String country, String period) throws ParseException {
        List<String> urls;
        if (dbType == Constant.DB_TYPE_HISTORICAL){
            urls = historicalTweetServiceImpl.searchPictureByCountryPeriod(country,period);
        }else if (dbType == Constant.DB_TYPE_RESTFUl){
            urls = restfulTweetServiceImpl.searchPictureByCountryPeriod(country,period);
        }else if (dbType == Constant.DB_TYPE_STREAMING){
            urls = streamingTweetServiceImpl.searchPictureByCountryPeriod(country,period);
        }else {
            JsonResult parameter_error = JsonResult.error("Parameter error");
            return parameter_error;
        }
        RequestedJson images = new RequestedJson("images", urls);
        JsonResult success = JsonResult.success(images);
        return success;
    }

    @RequestMapping(path = "/count-all-tags", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult countAllTags(String dbType) {
        List<TagCount> tagCounts;
        if (dbType == Constant.DB_TYPE_HISTORICAL){
            tagCounts = historicalTweetServiceImpl.countTag();
        }else if (dbType == Constant.DB_TYPE_RESTFUl){
            tagCounts = restfulTweetServiceImpl.countTag();
        }else if (dbType == Constant.DB_TYPE_STREAMING){
            tagCounts = streamingTweetServiceImpl.countTag();
        }else {
            JsonResult parameter_error = JsonResult.error("Parameter error");
            return parameter_error;
        }
        RequestedJson tweet_count = new RequestedJson("Tweet Count", tagCounts);
        JsonResult success = JsonResult.success(tweet_count);
        return success;
    }
}
