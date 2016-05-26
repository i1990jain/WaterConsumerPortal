package com.wcp.service;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.wcp.model.User;

@Service
@Transactional
public class LoginService {

	public int userLogin(User user) throws MySQLIntegrityConstraintViolationException, SQLException, Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		int userId = 0;

		try {
			session.beginTransaction();
			String hql = "select u.oid from User u where u.username=:username and u.password=:password";

			System.out.println(hql);
			System.out.println("username: " + user.getUsername());
			System.out.println("password: " + user.getPassword());
			Query query = session.createQuery(hql);

			query.setParameter("userName", user.getUsername());
			query.setParameter("password", user.getPassword());
			List result = query.list();
			System.out.println(result);
			System.out.println("resultset:" + result);

			Iterator iterator = result.iterator();
			while (iterator.hasNext()) {
				userId = (int) iterator.next();

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session.close();
		}
		return userId;
	}
}
