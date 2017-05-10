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
import com.shareki.model.hybernate.entities.Sharekicomment;
import com.shareki.model.hybernate.entities.Sharekinews;
import com.shareki.model.hybernate.entities.Sharekiuser;
import com.shareki.model.world.hybernate.dao.intf.SharekiGUID;
import com.shareki.model.world.hybernate.dao.intf.ISharekiNewsCommentDAO;
import com.shareki.model.world.hybernate.util.HibernateUtil;

@Repository
public class SharekiCommentDAOImpl implements ISharekiNewsCommentDAO {

	//@Autowired
	private SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		
	public SharekiCommentDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Sharekicomment> getAllComments(String newsPK) {
		// TODO Auto-generated method stub
		List<Sharekicomment> comments=new ArrayList<Sharekicomment>();
		
		if(newsPK==null || newsPK.equalsIgnoreCase(""))
			return comments;
		
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
	
			Sharekinews news = (Sharekinews) session.load(
					Sharekinews.class, newsPK);
			
			session.getTransaction().commit();
			//session.flush();
			//session.close();
			if(news!=null)
				return news.getSharekicomments();
		}
		catch(RuntimeException e)
		{
			session.getTransaction().rollback();
		}
		return null;
	}

	@Override
	public List<Sharekinews> getAllNewsByDirtyFlag(byte dirtyFlag) {
		// TODO Auto-generated method stub
	
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Query query=session.createQuery("from Sharekicomment p where p.dirtyflag = :dirtyFlag");
		query.setParameter("dirtyFlag", dirtyFlag);
		
		List<Sharekinews> nlist= new ArrayList<Sharekinews>();
		
		List<Sharekicomment> clist= new ArrayList<Sharekicomment>();
		
		nlist.clear();clist.clear();
		clist=query.list();
		for(int i=0;i<clist.size();i++)
		{
			if(!nlist.contains(clist.get(i).getSharekinew()))
				nlist.add(clist.get(i).getSharekinew());
		}
		
		session.getTransaction().commit();
		//session.flush();
		//session.close();
		return nlist;
	}

	@Override
	public List<Sharekicomment> getAllCommentsByDirtyFlag(byte flag) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Query query=session.createQuery("from Sharekicomment p where p.dirtyflag = :dirtyFlag");
		query.setParameter("dirtyFlag", flag);
		
		session.getTransaction().commit();
		//session.flush();
		//session.close();
		return query.list();
		
	}

	@Override
	public Sharekicomment addSharekiComment(String comment, String newsPK,
			String userPK, byte dirtyflag) {
		// TODO Auto-generated method stub
		
		if(newsPK==null || userPK==null || newsPK.equalsIgnoreCase("") || userPK.equalsIgnoreCase(""))
			return null;
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		
		try
		{
		session.beginTransaction();

		Sharekinews news = (Sharekinews) session.get(
				Sharekinews.class, newsPK);
		
		Sharekiuser user = (Sharekiuser) session.get(
				Sharekiuser.class, userPK);
		
			if(news!=null && user!=null)
			{
				Sharekicomment scomment=new Sharekicomment();
				scomment.setComment(comment);
				
				//foreign keys
				scomment.setSharekinew(news);
				news.getSharekicomments().add(scomment);
				
				scomment.setSharekiuser(user);
				user.getSharekicomments().add(scomment);
				//
				scomment.setDirtyflag(dirtyflag);
				BigInteger cid=SharekiGUID.getGUID();
				scomment.setCommentid(cid.toString());
				
				session.save(scomment);
				
				session.flush();
				
				Query query=session.createQuery("from Sharekicomment p where p.commentid = :commentID");
				query.setParameter("commentID", cid.toString());
				
				List<Sharekicomment> list=new ArrayList();
				list.clear();
				list=query.list();
				
				session.getTransaction().commit();
				//session.flush();
				//session.close();
				if(list.size()>0)
					return list.get(0);		
			}
		}
		catch(RuntimeException ee)
		{
			session.getTransaction().rollback();
		}
		
		return null;
	}

}
