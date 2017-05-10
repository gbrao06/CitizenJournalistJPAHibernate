package com.shareki.model.world.hybernate.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shareki.model.hybernate.entities.City;
import com.shareki.model.hybernate.entities.Cityneighbour;
import com.shareki.model.hybernate.entities.Sharekinews;
import com.shareki.model.hybernate.entities.State;
import com.shareki.model.world.hybernate.dao.intf.ISharekiNewsDAO;
import com.shareki.model.world.hybernate.util.HibernateUtil;

@Repository
public class SharekiNewsDAOImpl implements ISharekiNewsDAO {

	//@Autowired
	private SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
	
	
	public SharekiNewsDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Sharekinews> getAllNewsInCity(String cityName, String countryName) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();

		List<Sharekinews> news=new ArrayList<Sharekinews>();
		news.clear();
		try
		{
			session.beginTransaction();
	
			List<City> clist=new ArrayList<City>();
			clist.clear();
			
			Query query=session.createQuery("from City p where p.name = :cityName and p.country.name= :countryName");
			query.setParameter("cityName", cityName);
			query.setParameter("countryName", countryName);
			clist=query.list();
			if(clist!=null && 0<clist.size())
			{
				List<Cityneighbour> nlist=new ArrayList<Cityneighbour>();
				nlist.clear();
				nlist=clist.get(0).getCityneighbours();
				for(int j=0;j<nlist.size();j++)
				{
					news.addAll(nlist.get(j).getSharekinews());
				}
			}
			System.out.println("SharekiNewDAImp::getAllNewsInCity:istSize:"+news.size());
			session.getTransaction().commit();
		//session.flush();
		//session.close();
		}
		catch(RuntimeException ee)
		{
			session.getTransaction().rollback();
		}
		return news;
	}

	@Override
	public List<Sharekinews> getAllNewsInNeighbour(String villageName,
			String cityName, String countryName) {
		// TODO Auto-generated method stub
		
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();

		List<Sharekinews> list=new ArrayList<Sharekinews>();
		list.clear();
		try
		{
			session.beginTransaction();
			
			Query query=session.createQuery("from Sharekinews s where s.cityneighbour.villagename = :villageName and s.cityneighbour.city.name= :cityName and s.cityneighbour.city.country.name= :countryName");
			query.setParameter("villageName", villageName);
			query.setParameter("cityName", cityName);
			query.setParameter("countryName", countryName);
			list=query.list();
			System.out.println("SharekiNewDAImp::getAllNewsInNeighbour:istSize:"+list.size());
			session.getTransaction().commit();
			//session.flush();
			//session.close();
		}
		catch(RuntimeException e)
		{
			session.getTransaction().rollback();
		}
		return list;
	}

	@Override
	public List<Sharekinews> getAllNewsOfUser(String userId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();

		List<Sharekinews> news=new ArrayList<Sharekinews>();
		news.clear();
		try
		{
			session.beginTransaction();
	
			Query query=session.createQuery("from Sharekinews p where p.sharekiuser.userid = :userId");
			query.setParameter("userId", userId);
			
			news=query.list();
			
			System.out.println("SharekiNewDAOImpl::getAllNewsOfUser:ListSize:"+news.size());
			session.getTransaction().commit();
		//session.flush();
		//session.close();
		}
		catch(RuntimeException ee)
		{
			session.getTransaction().rollback();
		}
		return news;
	}

	@Override
	public List<Sharekinews> getAllEmergencyInNeighbour(String villageName,
			String cityName, String countryName) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();

		List<Sharekinews> news=new ArrayList<Sharekinews>();
		news.clear();
		try
		{
			session.beginTransaction();
			Byte eHelp=1;
			Query query=session.createQuery("from Sharekinews p where p.ehelp = :eHelp and p.cityneighbour=:villageName and p.cityneighbour.city.name=:cityName");
			
			query.setParameter("eHelp",eHelp);
			query.setParameter("villageName",villageName);
			query.setParameter("cityName", cityName);
			
			news=query.list();
			
			System.out.println("SharekiNewDAOImpl::getAllEmergencyInNeighbour:ListSize:"+news.size());
			session.getTransaction().commit();
		//session.flush();
		//session.close();
		}
		catch(RuntimeException ee)
		{
			session.getTransaction().rollback();
		}
		return news;
	}

	@Override
	public List<Sharekinews> getAllEmergencyInCity(String cityName,
			String countryName) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();

		List<Sharekinews> news=new ArrayList<Sharekinews>();
		news.clear();
		try
		{
			session.beginTransaction();
			Byte eHelp=1;
			Query query=session.createQuery("from Sharekinews p where p.ehelp = :eHelp and p.cityneighbour.city.name=:cityName and p.cityneighbour.city.country.name=:countryName");
			
			query.setParameter("eHelp",eHelp);
			query.setParameter("cityName", cityName);
			query.setParameter("countryName",countryName);
			
			news=query.list();
			
			System.out.println("SharekiNewDAOImpl::getAllEmergencyInCity:ListSize:"+news.size());
			session.getTransaction().commit();
		//session.flush();
		//session.close();
		}
		catch(RuntimeException ee)
		{
			session.getTransaction().rollback();
		}
		return news;
	}

	@Override
	public List<Sharekinews> getAllEmergencyInCountry(String countryName) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();

		List<Sharekinews> news=new ArrayList<Sharekinews>();
		news.clear();
		try
		{
			session.beginTransaction();
			Byte eHelp=1;
			Query query=session.createQuery("from Sharekinews p where p.ehelp = :eHelp and p.cityneighbour.city.country.name=:countryName");
			
			query.setParameter("eHelp",eHelp);
			query.setParameter("countryName",countryName);
			
			news=query.list();
			
			System.out.println("SharekiNewDAOImpl::getAllEmergencyInCountry:ListSize:"+news.size());
			session.getTransaction().commit();
		//session.flush();
		//session.close();
		}
		catch(RuntimeException ee)
		{
			session.getTransaction().rollback();
		}
		return news;
	}

}
