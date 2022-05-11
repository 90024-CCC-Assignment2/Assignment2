package service.impl;

import dao.IAurinDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.IAurinService;
import utils.Constant;
import utils.TweetUtils;

import java.util.List;

@Service
public class AurinServiceImpl implements IAurinService {
    @Autowired
    private IAurinDao aurin2002DaoImpl;
    @Autowired
    private IAurinDao aurin2003DaoImpl;
    @Autowired
    private IAurinDao aurin2004DaoImpl;
    @Autowired
    private IAurinDao aurin2005DaoImpl;
    @Autowired
    private IAurinDao aurin2006DaoImpl;
    @Autowired
    private IAurinDao aurin2007DaoImpl;
    @Autowired
    private IAurinDao aurin2008DaoImpl;
    @Autowired
    private IAurinDao aurin2009DaoImpl;
    @Autowired
    private IAurinDao aurin2010DaoImpl;
    @Autowired
    private IAurinDao aurin2011DaoImpl;
    @Autowired
    private IAurinDao aurin2012DaoImpl;
    @Autowired
    private IAurinDao aurin2013DaoImpl;
    @Autowired
    private IAurinDao aurin2014DaoImpl;
    @Autowired
    private IAurinDao aurin2015DaoImpl;
    @Autowired
    private IAurinDao aurin2016DaoImpl;
    @Autowired
    private IAurinDao aurin2017DaoImpl;

    @Override
    public List<List<Integer>> countAllAurinByCountry() {
        //List<List<Integer>> source = new ArrayList<>();
        List<Integer> count2002 = aurin2002DaoImpl.countByCountryList(Constant.AURIN_COUNTRY_LIST);
        List<Integer> count2003 = aurin2003DaoImpl.countByCountryList(Constant.AURIN_COUNTRY_LIST);
        List<Integer> count2004 = aurin2004DaoImpl.countByCountryList(Constant.AURIN_COUNTRY_LIST);
        List<Integer> count2005 = aurin2005DaoImpl.countByCountryList(Constant.AURIN_COUNTRY_LIST);
        List<Integer> count2006 = aurin2006DaoImpl.countByCountryList(Constant.AURIN_COUNTRY_LIST);
        List<Integer> count2007 = aurin2007DaoImpl.countByCountryList(Constant.AURIN_COUNTRY_LIST);
        List<Integer> count2008 = aurin2008DaoImpl.countByCountryList(Constant.AURIN_COUNTRY_LIST);
        List<Integer> count2009 = aurin2009DaoImpl.countByCountryList(Constant.AURIN_COUNTRY_LIST);
        List<Integer> count2010 = aurin2010DaoImpl.countByCountryList(Constant.AURIN_COUNTRY_LIST);
        List<Integer> count2011 = aurin2011DaoImpl.countByCountryList(Constant.AURIN_COUNTRY_LIST);
        List<Integer> count2012 = aurin2012DaoImpl.countByCountryList(Constant.AURIN_COUNTRY_LIST);
        List<Integer> count2013 = aurin2013DaoImpl.countByCountryList(Constant.AURIN_COUNTRY_LIST);
        List<Integer> count2014 = aurin2014DaoImpl.countByCountryList(Constant.AURIN_COUNTRY_LIST);
        List<Integer> count2015 = aurin2015DaoImpl.countByCountryList(Constant.AURIN_COUNTRY_LIST);
        List<Integer> count2016 = aurin2016DaoImpl.countByCountryList(Constant.AURIN_COUNTRY_LIST);
        List<Integer> count2017 = aurin2017DaoImpl.countByCountryList(Constant.AURIN_COUNTRY_LIST);
        List<List<Integer>> source = TweetUtils.zip(count2002,count2003,count2004,count2005,
                count2006,count2007,count2008,count2009,count2010,count2011,count2012,count2013,
                count2014,count2015,count2016,count2017);
        return source;
    }
}
