package com.shareki.model.world.hybernate.dao.intf;

import java.util.List;

import com.shareki.model.hybernate.entities.Sharekinews;

public interface ISharekiNewsDAO {
	
    public List<Sharekinews> getAllNewsInCity(String cityName,String countryName);
    
    public List<Sharekinews> getAllNewsInNeighbour(String villageName,String cityName,String countryName);
    
    public List<Sharekinews> getAllNewsOfUser(String userId);
    
    
    public List<Sharekinews> getAllEmergencyInNeighbour(String villageName,String cityName,String countryName);
    
    public List<Sharekinews> getAllEmergencyInCity(String cityName,String countryName);
    
    public List<Sharekinews> getAllEmergencyInCountry(String countryName);
}
