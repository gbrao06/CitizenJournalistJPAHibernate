package com.shareki.model.world.hybernate.dao.intf;

import java.util.List;

import com.shareki.model.hybernate.entities.Cityneighbour;
import com.shareki.model.hybernate.entities.Sharekinews;

public interface ICityNeighbourDAO {
	
    public List<Cityneighbour> getAllNeighboursOfCity(String cityName,String countryName);
    public Cityneighbour getCityneighbour(String village,String cityName,String countryName);
    public Cityneighbour addCityNeighbour(String villageName,String cityName,String countryName);
    
    public Sharekinews addSharekiNew(String title,String news,byte[] image,String villageName,String nearestCity,String countryName,String userPK);
    public Sharekinews addSharekiEHelp(String title,String news,String villageName,String nearestCity,String countryName,String userPK);
    
}
