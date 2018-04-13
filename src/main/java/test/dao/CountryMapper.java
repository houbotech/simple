package test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import test.model.Country;

public interface CountryMapper {
    int deleteByPrimaryKey(Integer id);

	int insert(Country record);

	Country selectByPrimaryKey(Integer id);

	List<Country> selectAll();

	int updateByPrimaryKey(Country record);

    int updateAllMessage(@Param("countryname") String countryname,@Param("countrycode") String countrycode);

    List<Country> selectByCountry(Country country);
}