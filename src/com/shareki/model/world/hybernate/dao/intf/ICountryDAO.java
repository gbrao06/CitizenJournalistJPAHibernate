package com.shareki.model.world.hybernate.dao.intf;

import java.util.List;

import com.shareki.model.hybernate.entities.Country;

public interface ICountryDAO {
	
    public List<Country> getAllCountries();
    public Country getCountryByName(String countryName);
    
}
