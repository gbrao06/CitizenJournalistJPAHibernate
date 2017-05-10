package com.shareki.model.world.hybernate.dao.intf;

import java.util.List;

import com.shareki.model.hybernate.entities.Country;
import com.shareki.model.hybernate.entities.State;

public interface IStateDAO {
	
    public List<State> getAllStatesInCountry(String countryName);
    public State getStateByName(String stateName,String countryName);
}
