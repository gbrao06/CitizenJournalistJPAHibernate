package com.shareki.model.world.hybernate.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shareki.model.hybernate.entities.City;
import com.shareki.model.hybernate.entities.Country;
import com.shareki.model.hybernate.entities.Sharekiuser;
import com.shareki.model.hybernate.entities.State;
import com.shareki.model.world.hybernate.dao.intf.ICityDAO;
import com.shareki.model.world.hybernate.dao.intf.ISharekiUserDAO;
import com.shareki.model.world.hybernate.util.HibernateUtil;

import org.springframework.orm.hibernate3.HibernateTemplate;

@Repository
public class CityDAOImpl implements ICityDAO{

	//@Autowired
    private SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
	
	@Override
	public List<City> getAllCitiesInState(String stateName, String countryName) {
		// TODO Auto-generated method stub
		System.out.println("getAllCitiesInCountry,stateName,countryName");
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		List<City> clist=new ArrayList<City>();
		
		List<State> list=new ArrayList<State>();
		
		//List<City> clist = this.sessionFactory.getCurrentSession().createCriteria(Country.class)
	    //         .add(Restrictions.eq("name",countryName)).list().get(0);
		//null pointer exception
		Query query = session.createQuery("from City c where c.country.name=:countryName and c.district= :stateName");
		
		//select * from city c, state p, country q where p.StateName='Andhra Pradesh' and q.name='india';
		
		//Query query = session.createQuery("from State p, Country q where q.name=:countryName and c.district= :p.stateName and p!=null and c.district!=null");
		
		query.setParameter("stateName", stateName);
		query.setParameter("countryName", countryName);
		clist=query.list();
		
		session.getTransaction().commit();
		//session.flush();
		//session.close();
		return clist;
	}

	@Override
	public List<City> getAllCitiesInCountry(String countryName) {
		// TODO Auto-generated method stub
		
		System.out.println("getAllCitiesInCountry,countryName");
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List<City> list=new ArrayList<City>();
		Query query=session.createQuery("from City where country.name = :countryName");
		query.setParameter("countryName", countryName);
		list=query.list();
		session.getTransaction().commit();
		//session.flush();
		//session.close();
		return list;
	}

	@Override
	public City getCityByName(String cityName, String countryName) {
		// TODO Auto-generated method stub
		
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List<City> list=new ArrayList<City>();

		Query query=session.createQuery("from City where name= :cityName and country.name = :countryName");
		query.setParameter("cityName", cityName);
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
