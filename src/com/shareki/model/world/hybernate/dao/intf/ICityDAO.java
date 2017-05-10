package com.shareki.model.world.hybernate.dao.intf;

import java.util.List;

import com.shareki.model.hybernate.entities.City;
import com.shareki.model.hybernate.entities.Country;
import com.shareki.model.hybernate.entities.State;

public interface ICityDAO {
	
    public List<City> getAllCitiesInState(String stateName,String countryName);
    public List<City> getAllCitiesInCountry(String countryName);
    public City getCityByName(String cityName,String countryName);
    
}
