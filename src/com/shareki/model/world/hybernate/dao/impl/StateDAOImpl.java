package com.shareki.model.world.hybernate.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shareki.model.hybernate.entities.City;
import com.shareki.model.hybernate.entities.State;
import com.shareki.model.world.hybernate.dao.intf.IStateDAO;
import com.shareki.model.world.hybernate.util.HibernateUtil;

@Repository
public class StateDAOImpl implements IStateDAO {

	//@Autowired
	private SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
	

	@Override
	public List<State> getAllStatesInCountry(String countryName) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List<State> list=new ArrayList<State>();
		Query query=session.createQuery("from State where country.name = :countryName");
		query.setParameter("countryName", countryName);
		list=query.list();
		
		session.getTransaction().commit();
		//session.flush();
		//session.close();
		return list;
	}

	@Override
	public State getStateByName(String stateName, String countryName) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List<State> list=new ArrayList<State>();
		Query query=session.createQuery("from State where StateName= :stateName and country.name = :countryName");
		query.setParameter("stateName", stateName);
		query.setParameter("countryName", countryName);
		list=query.list();
		
		session.getTransaction().commit();
		//session.flush();
		//session.close();
		if(list.size()>0)
			return list.get(0);
		
		return null;
	}

}
