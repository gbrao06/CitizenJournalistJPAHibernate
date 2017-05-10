package com.shareki.model.world.hybernate.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shareki.model.hybernate.entities.City;
import com.shareki.model.hybernate.entities.Cityneighbour;
import com.shareki.model.hybernate.entities.Country;
import com.shareki.model.hybernate.entities.Sharekinews;
import com.shareki.model.hybernate.entities.Sharekiuser;
import com.shareki.model.world.hybernate.dao.intf.ICityNeighbourDAO;
import com.shareki.model.world.hybernate.dao.intf.SharekiGUID;
import com.shareki.model.world.hybernate.util.HibernateUtil;

@Repository
public class NeighbourDAOImpl implements ICityNeighbourDAO {

	//@Autowired
	private SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
	
	
	public NeighbourDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Cityneighbour> getAllNeighboursOfCity(String cityName,
			String countryName) {
		// TODO Auto-generated method stub
		
		List<Cityneighbour> nlist=new ArrayList<Cityneighbour>();
		
		//List<City> clist = this.sessionFactory.getCurrentSession().createCriteria(Country.class)
	    //         .add(Restrictions.eq("name",countryName)).list().get(0);
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			
			//get the city and return all neighbours
			System.out.println("City name="+cityName+"::Country="+countryName);
			
			Query query = session.createQuery("from Cityneighbour where city.name= :cityName and city.country.name = :countryName");
			query.setParameter("cityName", cityName);
			query.setParameter("countryName", countryName);
			nlist= query.list();
			session.getTransaction().commit();
			//session.flush();
			//session.close();
		}
		catch(RuntimeException e)
		{
			System.out.println("NeighbourDaoImpl:getAllNeighboursOfCity:"+e.getMessage());
			session.getTransaction().rollback();
		}
			return nlist;
	}

	@Override
	public Cityneighbour getCityneighbour(String villageName, String cityName,
			String countryName) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		
		try{
			session.beginTransaction();
	
			List<Cityneighbour> list=new ArrayList<Cityneighbour>();
			System.out.println("NEIGHBOURDAOIMPL:getCityNeighbour:Before createQuery:");
			
			Query query = session.createQuery("from Cityneighbour  where villagename = :villageName and city.name= :cityName and city.country.name= :countryName");
			
			query.setParameter("villageName", villageName);
			query.setParameter("cityName", cityName);
			query.setParameter("countryName", countryName);
			System.out.println("NEIGHBOURDAOIMPL:getCityNeighbour:Before query.list:");
			
			list= query.list();
			
			System.out.println("NEIGHBOURDAOIMPL:getCityNeighbour:Before Commit:");
			session.getTransaction().commit();
			System.out.println("NEIGHBOURDAOIMPL:getCityNeighbour:After Commit:");
			
			//session.flush();
			//session.close();
			if(list.size()>0)
			{
				System.out.println("NEIGHBOURDAOIMPL:getCityNeighbour: VILLAGE ALREADY EXISTS:");
				
				return list.get(0);
			}
			else
			{
				System.out.println("NEIGHBOURDAOIMPL:getCityNeighbour: VILLAGE NOT EXISTS:");
				
			}
		}
		catch (RuntimeException e) {
			System.out.println("NEIGHBOURDAOIMPL:getCityNeighbour:RUNTIME EXCEPTION:"+e.getMessage());
			
			session.getTransaction().rollback();
		}
		finally
		{
			//HibernateUtil.shutdown();
		}
		return null;
	
	}

	public Cityneighbour getCityneighbour_NT(Session session,String villageName, String cityName,
			String countryName) {
		// TODO Auto-generated method stub
			if(session==null)
			{
				System.out.println("NEIGHBOURDAOIMPL:getCityNeighbour_NT:Session=NULL");
				
				return null;
			}	
			
			List<Cityneighbour> list=new ArrayList<Cityneighbour>();
			System.out.println("NEIGHBOURDAOIMPL:getCityNeighbour_NT:Before createQuery:");
			
			Query query = session.createQuery("from Cityneighbour  where villagename = :villageName and city.name= :cityName and city.country.name= :countryName");
			
			query.setParameter("villageName", villageName);
			query.setParameter("cityName", cityName);
			query.setParameter("countryName", countryName);
			System.out.println("NEIGHBOURDAOIMPL:getCityNeighbour_NT:Before query.list:");
			
			list= query.list();
			
			
			//session.flush();
			//session.close();
			if(list.size()>0)
			{
				System.out.println("NEIGHBOURDAOIMPL:getCityNeighbour_NT: VILLAGE ALREADY EXISTS:"+list.get(0).getVillagename());
				
				return list.get(0);
			}
			else
			{
				System.out.println("NEIGHBOURDAOIMPL:getCityNeighbour_NT: VILLAGE NOT EXISTS:");
				
			}
		return null;
	
	}

	@Override
	public Cityneighbour addCityNeighbour(String villageName, String cityName,
			String countryName) {
		// TODO Auto-generated method stub
		//get cityPK
		
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		
		try
		{
			session.beginTransaction();
	
			List<City> list=new ArrayList<City>();
			Query query = session.createQuery("from City  where name = :cityName and country.name= :countryName");
			
			query.setParameter("cityName", cityName);
			query.setParameter("countryName", countryName);
			list= query.list();
			if(list.size()<=0)
			{
				session.getTransaction().commit();
				System.out.println("NEIGHBOURDAOIMPL:addCityNeighbour:CITY IS NULL");
				return null;
			}
		
			City c=list.get(0);
			
			Cityneighbour neighbour=new Cityneighbour();
			
			//foreign key setup
			neighbour.setCity(c);
			c.getCityneighbours().add(neighbour);
			
			neighbour.setVillagename(villageName);

			System.out.println("NEIGHBOURDAOIMPL:addCityNeighbour:BEFORE SAVE");
			session.save(neighbour);
			session.flush();
			System.out.println("NEIGHBOURDAOIMPL:addCityNeighbour:AFTER SAVE, BEFORE FLUSH");
			//session.flush();

			System.out.println("NEIGHBOURDAOIMPL:addCityNeighbour:AFTER FLUSH, BEFORE COMMIT");
			session.getTransaction().commit();

			System.out.println("NEIGHBOURDAOIMPL:addCityNeighbour:AFTER SAVE, AFTER COMMIT");
		}
		catch (RuntimeException e) {
			System.out.println("NeighbourhoodDAOImpl:addSharekiNew:addCityneighbour:RUNTIME EXCEPTION:"+e.getMessage());
			
			session.getTransaction().rollback();
		}
		
		return getCityneighbour(villageName,cityName,countryName);
		
	}

	public Cityneighbour addCityNeighbour_NT(Session session,String villageName, String cityName,
			String countryName) {
		// TODO Auto-generated method stub
		//get cityPK
		
		if(session==null)
		{
			System.out.println("NEIGHBOURDAOIMPL:addCityNeighbour_NT:SESSION = NULL");
			
			return null;
		}
		
			List<City> list=new ArrayList<City>();
			Query query = session.createQuery("from City  where name = :cityName and country.name= :countryName");
			
			query.setParameter("cityName", cityName);
			query.setParameter("countryName", countryName);
			list= query.list();
			if(list.size()<=0)
			{
				System.out.println("NEIGHBOURDAOIMPL:addCityNeighbour_NT:CITY IS NULL");
				return null;
			}
		
			City c=list.get(0);
			
			Cityneighbour neighbour=new Cityneighbour();
			
			//foreign key setup
			neighbour.setCity(c);
			c.getCityneighbours().add(neighbour);
			
			neighbour.setVillagename(villageName);

			System.out.println("NEIGHBOURDAOIMPL:addCityNeighbour_NT:BEFORE SAVE");
			session.save(neighbour);

			System.out.println("NEIGHBOURDAOIMPL:addCityNeighbour_NT:AFTER SAVE, BEFORE FLUSH");
			//session.flush();

			System.out.println("NEIGHBOURDAOIMPL:addCityNeighbour_NT:AFTER FLUSH");
		
		
		return getCityneighbour_NT(session,villageName,cityName,countryName);
		
	}
 
	@Override
	public Sharekinews addSharekiNew(String title,String news, byte[] image,
			String villageName, String nearestCity, String countryName,String userPK) {
		// TODO Auto-generated method stub
		
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			
			session.beginTransaction();
	
			Sharekiuser user = (Sharekiuser) session.get(
					Sharekiuser.class, userPK);
			System.out.println("NeighbourhoodDAOImpl:addSharekiNew:Calling for Existince Of Village");
			
			Cityneighbour n=getCityneighbour_NT(session,villageName,nearestCity,countryName);
			System.out.println("NeighbourhoodDAOImpl:addSharekiNew:After Calling For Existining Village");
			
			
			if(n==null)
			{
				//commit and again load
				
				//add city newighbour
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew:getCityneighbour_NT:RETURNED NULL: INSERTING NEW VILLAGE");
					
				n=addCityNeighbour_NT(session,villageName,nearestCity,countryName);
				//session.beginTransaction().commit();
				//session=HibernateUtil.getSessionFactory().getCurrentSession();
			}
			
			if(n!=null && user!=null)
			{
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew: VILLAGE And USER Not NULL");
				
				Sharekinews snews=new Sharekinews();
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew: Created Sharekinew object");
				
				//foregn keys
				snews.setCityneighbour(n);
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew: Set village as Foreign key");
				
				n.getSharekinews().add(snews);
				
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew:Before Setting User FK");
				
				//shareki user
				snews.setSharekiuser(user);
				
				user.getSharekinews().add(snews);
				
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew::Before SETTING IMAGE");
				if(image==null)
					System.out.println("NeighbourhoodDAOImpl:addSharekiNew::IMAGE==NULL");
				
				snews.setImage(image);
				snews.setNews(news);
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew:Before SAVE4");
				
				BigInteger nid=SharekiGUID.getGUID();
				snews.setNewsid(nid.toString());
				
				Timestamp t=new Timestamp(Calendar.getInstance().getTimeInMillis());
				snews.setLastUpdated(t);
				
				//titie and ehep
				Byte ehep=0;
				snews.setEhelp(ehep);
				snews.setTitle(title);
				
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew:Before SAVE:News ID:"+nid.toString());
				
				displaySharekiNews(snews);
				session.flush();
				
				session.save(snews);
				
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew:After SAVE");
				
				List<Sharekinews> list=new ArrayList<Sharekinews>();
				Query query=session.createQuery("from Sharekinews where newsid = :nid");
				query.setParameter("nid", nid.toString());
				list=query.list();
				
				session.getTransaction().commit();
				//session.clear();
				//	session.flush();
			//	session.close();
				if(list.size()>0)
				{

					System.out.println("NeighbourhoodDAOImpl:addSharekiNew::Returining List0:News"+list.get(0).getNews());
					return list.get(0);
				}
			}
			else
			{

				System.out.println("NeighbourhoodDAOImpl:addSharekiNew:After calling getCityneighbour:RETURNED NULL,COMMITING");
				session.getTransaction().commit();
				//session.clear();
			}
			
		}
		catch (RuntimeException e) {
			System.out.println("NeighbourhoodDAOImpl:addSharekiNew:RUNTIME EXCEPTION:ROLLING BACK NOW::"+e.getMessage());
			System.out.println("NeighbourhoodDAOImpl:addSharekiNew:RUNTIME EXCEPTION:ROLLING BACK NOW1::"+e.getLocalizedMessage());
			
			System.out.println("NeighbourhoodDAOImpl:addSharekiNew:RUNTIME EXCEPTION:ROLLING BACK NOW2::"+e.getCause());
			System.out.println("NeighbourhoodDAOImpl:addSharekiNew:RUNTIME EXCEPTION:ROLLING BACK NOW3::"+e.getStackTrace());
			
			session.getTransaction().rollback();	
		}
		
		return null;
	}


	@Override
	public Sharekinews addSharekiEHelp(String title,String news,String villageName, String nearestCity, String countryName,String userPK) {
		// TODO Auto-generated method stub
		
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			
			session.beginTransaction();
	
			Sharekiuser user = (Sharekiuser) session.get(
					Sharekiuser.class, userPK);
			System.out.println("NeighbourhoodDAOImpl:addSharekiEHelp:Calling for Existince Of Village");
			
			Cityneighbour n=getCityneighbour_NT(session,villageName,nearestCity,countryName);
			System.out.println("NeighbourhoodDAOImpl:addSharekiEHelp:After Calling For Existining Village");
			
			
			if(n==null)
			{
				//commit and again load
				
				//add city newighbour
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew:getCityneighbour_NT:RETURNED NULL: INSERTING NEW VILLAGE");
					
				n=addCityNeighbour_NT(session,villageName,nearestCity,countryName);
				//session.beginTransaction().commit();
				//session=HibernateUtil.getSessionFactory().getCurrentSession();
			}
			
			if(n!=null && user!=null)
			{
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew: VILLAGE And USER Not NULL");
				
				Sharekinews snews=new Sharekinews();
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew: Created Sharekinew object");
				
				//foregn keys
				snews.setCityneighbour(n);
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew: Set village as Foreign key");
				
				n.getSharekinews().add(snews);
				
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew:Before Setting User FK");
				
				//shareki user
				snews.setSharekiuser(user);
				
				user.getSharekinews().add(snews);
				
				snews.setNews(news);
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew:Before SAVE4");
				
				BigInteger nid=SharekiGUID.getGUID();
				snews.setNewsid(nid.toString());
				
				Timestamp t=new Timestamp(Calendar.getInstance().getTimeInMillis());
				snews.setLastUpdated(t);
				
				//titie and ehep
				Byte ehep=1;
				snews.setEhelp(ehep);
				snews.setTitle(title);
				
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew:Before SAVE:News ID:"+nid.toString());
				
				displaySharekiNews(snews);
				session.flush();
				
				session.save(snews);
				
				System.out.println("NeighbourhoodDAOImpl:addSharekiNew:After SAVE");
				
				List<Sharekinews> list=new ArrayList<Sharekinews>();
				Query query=session.createQuery("from Sharekinews where newsid = :nid");
				query.setParameter("nid", nid.toString());
				list=query.list();
				
				session.getTransaction().commit();
				//session.clear();
				//	session.flush();
			//	session.close();
				if(list.size()>0)
				{

					System.out.println("NeighbourhoodDAOImpl:addSharekiEHelp::Returining List0:News"+list.get(0).getNews());
					return list.get(0);
				}
			}
			else
			{

				System.out.println("NeighbourhoodDAOImpl:addSharekiEHelp:After calling getCityneighbour:RETURNED NULL,COMMITING");
				session.getTransaction().commit();
				//session.clear();
			}
			
		}
		catch (RuntimeException e) {
			System.out.println("NeighbourhoodDAOImpl:addSharekiEHelp:RUNTIME EXCEPTION:ROLLING BACK NOW::"+e.getMessage());
			System.out.println("NeighbourhoodDAOImpl:addSharekiEHelp:RUNTIME EXCEPTION:ROLLING BACK NOW1::"+e.getLocalizedMessage());
			
			System.out.println("NeighbourhoodDAOImpl:addSharekiEHelp:RUNTIME EXCEPTION:ROLLING BACK NOW2::"+e.getCause());
			System.out.println("NeighbourhoodDAOImpl:addSharekiEHelp:RUNTIME EXCEPTION:ROLLING BACK NOW3::"+e.getStackTrace());
			
			session.getTransaction().rollback();	
		}
		
		return null;
	}

	private void displaySharekiNews(Sharekinews news)
	{
		if(news==null)
			System.out.println("NeighbourhoodDAOImpl:displayShakiNews:sharekinews=NULL");
		
		try
		{
			System.out.println("NeighbourhoodDAOImpl:displayShakiNews:news="+news.getNews());
			System.out.println("NeighbourhoodDAOImpl:displayShakiNews:newsid="+news.getNewsid());
			System.out.println("NeighbourhoodDAOImpl:displayShakiNews:userid="+news.getSharekiuser().getEmail());
			System.out.println("NeighbourhoodDAOImpl:displayShakiNews:image="+news.getImage());
			System.out.println("NeighbourhoodDAOImpl:displayShakiNews:lastUpdated="+news.getLastUpdated());
			System.out.println("NeighbourhoodDAOImpl:displayShakiNews:villagename="+news.getCityneighbour().getVillagename());
			System.out.println("NeighbourhoodDAOImpl:displayShakiNews:City Of villagename="+news.getCityneighbour().getCity().getName());
			
		}
		catch(Exception ee)
		{
			System.out.println("NeighbourhoodDAOImpl:displayShakiNews:Exception="+ee.getMessage());
			
		}
		
		
	}
}
