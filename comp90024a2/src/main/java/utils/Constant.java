package utils;

import java.util.Arrays;
import java.util.List;

public class Constant {
    private Constant() {
    }

    public static final String HISTORICAL_TWEET_END_DATE = "2017-06-29";
    public static final List<String> COUNTRY_LIST = Arrays.asList("China", "Thai", "Korea",
            "Japan", "Mexican", "India", "Italy", "America", "Spain", "Turkey", "Greece",
            "Pakistan", "Ukraine", "Australia");
    public static final String DB_TYPE_HISTORICAL = "historical";
    public static final String DB_TYPE_RESTFUl = "restful";
    public static final String DB_TYPE_STREAMING = "streaming";

}
