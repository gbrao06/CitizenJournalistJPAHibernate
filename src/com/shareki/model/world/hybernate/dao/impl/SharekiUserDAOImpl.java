package com.shareki.model.world.hybernate.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shareki.model.hybernate.entities.Country;
import com.shareki.model.hybernate.entities.Sharekiuser;
import com.shareki.model.world.hybernate.dao.intf.ISharekiUserDAO;
import com.shareki.model.world.hybernate.util.HibernateUtil;

@Repository
public class SharekiUserDAOImpl implements ISharekiUserDAO{

	//@Autowired
	private SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Sharekiuser> getAllSharekiUsers() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		List<Sharekiuser> list=new ArrayList<Sharekiuser>();
		list.clear();
		try
		{
			session.beginTransaction();
	
			list=session.createQuery("from Sharekiuser").list();
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
	public Sharekiuser addSharekiUser(String firstname, String lastname,
			String countryPK, String email, String passwd, String phone,
			String userid) {
		
		// TODO Auto-generated method stub
		if(countryPK==null || countryPK.equalsIgnoreCase("") || userid==null || userid.equalsIgnoreCase("") || email==null || email.equalsIgnoreCase("")
				|| passwd==null || passwd.equalsIgnoreCase(""))
		{
			System.out.println("::Hibername:addSharekiUser:Invalid Content");
			return null;
		}
		
		System.out.println("::Entered:Hibername:addSharekiUser: Userid:"+userid+":pass:"+passwd+":email:"+email);
		
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			//load is ok. because it is sure that we have object in that pk. If not use get method which may return null
			//load never returns null. it trows exception
			Country country = (Country) session.load(
					Country.class, countryPK);
			
			if(country==null)
				return null;
			List<Sharekiuser> list=new ArrayList<Sharekiuser>();
			
			//check if user already exists
			Query query1=session.createQuery("from Sharekiuser where userid = :userid");
			query1.setParameter("userid", userid);
			list=query1.list();
			
			System.out.println("::IN:Hibername:addSharekiUser: query list Size:"+list.size());
			
			if(list.size()>0)
			{
				//user already exists
				System.out.println("::Hibername:addSharekiUser:User Exists by Userid:"+userid);
				
				return ((Sharekiuser)list.get(0));
			}
			else
				System.out.println("::Hibername:addSharekiUser:User NOT Exists by Userid:"+userid);
			
			Sharekiuser user=new Sharekiuser();
			
			//foreign key setup
			country.getSharekiusers().add(user);
			user.setCountry(country);
			
			user.setEmail(email);
			user.setFirstname(firstname);
			user.setLastname(lastname);
			user.setPasswd(passwd);
			if(phone!=null)
				user.setPhone(phone);
			user.setUserid(userid);
			
			System.out.println("::Hibername:addSharekiUser:Before Save:User NOT Exists by Userid:"+userid);
			
			session.save(user);
			System.out.println("::Hibername:addSharekiUser:After Save:User NOT Exists by Userid:"+userid);
	
			session.flush();
			Query query=session.createQuery("from Sharekiuser where userid = :userid and passwd = :passwd");
			query.setParameter("userid", userid);
			query.setParameter("passwd", passwd);
			
			list=query.list();
			
			session.getTransaction().commit();
		//session.flush();
		//session.close();
		
			if(list.size()>0)
			{
				System.out.println("::Hibername:addSharekiUser:Returing New Record Of Userid:"+userid);
				
				return list.get(0);
			}
		}
		catch(RuntimeException e)
		{
			session.getTransaction().rollback();
		}
		System.out.println("::Hibername:addSharekiUser:Returing NULL Of Userid:"+userid);
		
		return null;
	}


	@Override
	public Sharekiuser getSharekiUserByPK(BigInteger userPK) {
		// TODO Auto-generated method stub
		
		if(userPK==null)
			return null;
		
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
	
			Sharekiuser user = (Sharekiuser) session.get(
					Sharekiuser.class, userPK);
			
			session.getTransaction().commit();
			//session.flush();
			//session.close();s
			if(user!=null)
				System.out.println("Returning User:"+user.getFirstname()+":"+user.getUserid()+":"+user.getPasswd());
			else
				System.out.println("Returning User:NULL");
			return user;
		}
		catch(RuntimeException ee)
		{
			session.getTransaction().rollback();
		}
		return null;
	}

	@Override
	public Sharekiuser getSharekiUserByUserId(String userid,String passwd) {
		// TODO Auto-generated method stub
		
		if(userid==null || userid.equalsIgnoreCase("") || passwd==null || passwd.equalsIgnoreCase(""))
			return null;
		
		System.out.println("Entered:SharekiUserDAOImpl:getSharekiUserByUserId:userId"+userid+":pass:"+passwd);

		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
	
			Query query=session.createQuery("from Sharekiuser where userid = :userid and passwd = :passwd");
			query.setParameter("userid", userid);
			query.setParameter("passwd", passwd);
			
			List<Sharekiuser> list=new ArrayList<Sharekiuser>();
			list.clear();
			list=query.list();
			//session.flush();
			//session.close();
			Sharekiuser user=null;
			if(list!=null && list.size()>0)
			{
				user=list.get(0);		
			}
			if(user!=null)
				System.out.println("Returning User:"+user.getFirstname()+":"+user.getUserid()+":"+user.getPasswd());
			else
			{
				System.out.println("Returning User:NULL:list Size:"+list.size());
				
			}
	
			session.getTransaction().commit();
			return user;
		}
		catch(RuntimeException e)
		{
			session.getTransaction().rollback();
		}
		
		return null;
		
	}
	
}
