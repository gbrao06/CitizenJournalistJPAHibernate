package com.shareki.model.world.hybernate.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shareki.model.hybernate.entities.Country;
import com.shareki.model.world.hybernate.dao.intf.ICountryDAO;
import com.shareki.model.world.hybernate.util.HibernateUtil;
import org.hibernate.Transaction;

@Repository
public class CountryDAOImpl implements ICountryDAO {

	//@Autowired
	private SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
	

	public CountryDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Country> getAllCountries() {
		// TODO Auto-generated method stub
		
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		List<Country> list=new ArrayList<Country>();
		
		session.beginTransaction();
		
		list= session.createQuery("from Country").list();
		//session.flush();
		
		session.getTransaction().commit();
		//session.close();
		return list;
	}

	@Override
	public Country getCountryByName(String countryName) {
		// TODO Auto-generated method stub
		List<Country> list=new ArrayList<Country>();
		Session session=this.sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query=session.createQuery("from Country where name= :countryName");
		query.setParameter("countryName",countryName);
		list=query.list();
		tx.commit();//commit will close the session automatically by thread context session
		//session.flush();
		//session.close();
		if(list.size()>0)
			return list.get(0);
		return null;
	}

}
