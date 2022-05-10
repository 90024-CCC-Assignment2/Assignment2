package dao;

import java.util.List;

public interface IAurinDao {
    public List<Integer> countByCountryList(List<String> countries);
}
