package service.impl;

import domain.Feature;
import domain.TagCount;
import service.ITweetService;

import java.util.List;

public class RestfulTweetServiceImpl implements ITweetService {
    @Override
    public List<Feature> searchByCountryPeriod(String country, String period) {
        return null;
    }

    @Override
    public List<String> searchPictureByCountryPeriod(String country, String period) {
        return null;
    }

    @Override
    public List<TagCount> countTag() {
        return null;
    }
}
