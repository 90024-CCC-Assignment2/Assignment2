package dao;

import domain.Tweet;

import java.util.List;

public interface ITweetDao {
    //根据国家和时间返回推特列表
    public List<Tweet> findByCountryPeriod(String country, String startDate, String endDate);
    //根据国家和态度统计个数
    public int countByCountryAndTag(String country, int tag);
}
