package service;

import domain.Feature;
import domain.TagCount;

import java.text.ParseException;
import java.util.List;

public interface ITweetService {
    //all tweets about china within 7 days
    List<Feature> searchByCountryPeriod(String country, String period) throws ParseException;

    //5 pictures of china within 7 days
    List<String> searchPictureByCountryPeriod(String country, String period) throws ParseException;

    //All country's tag statistics
    public List<TagCount> countTag();
}
