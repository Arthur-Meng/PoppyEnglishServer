package com.poppyenglish;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class UserDoDAO implements UserDAO {

	@Override
	public List<User> findByName(String name) {
		Configuration cf = new Configuration().configure();
		SessionFactory sf = cf.buildSessionFactory();
		Session ss = sf.openSession();
		Transaction ts = ss.beginTransaction();
		Criteria crit = ss.createCriteria(User.class);
		Criterion c1 = Restrictions.eq("name", name);
		crit.add(c1);

		List<User> userList = (List<User>) crit.list();
		ts.commit();
		ss.close();
		return userList;
	}

	@Override
	public User save(User user) {
		Configuration cf = new Configuration().configure();
		SessionFactory sf = cf.buildSessionFactory();
		Session ss = sf.openSession();
		Transaction ts = ss.beginTransaction();

		ss.save(user);
		ts.commit();
		ss.close();
		return user;
	}

	@Override
	public User update(User user) {
		Configuration cf = new Configuration().configure();
		SessionFactory sf = cf.buildSessionFactory();
		Session ss = sf.openSession();
		Transaction ts = ss.beginTransaction();

		ss.update(user);
		ts.commit();
		ss.close();
		return user;

	}

	@Override
	public User delete(User user) {
		Configuration cf = new Configuration().configure();
		SessionFactory sf = cf.buildSessionFactory();
		Session ss = sf.openSession();
		Transaction ts = ss.beginTransaction();

		ss.delete(user);
		ts.commit();
		ss.close();
		return user;
	}

	@Override
	public User findByTel(String tel) {
		// TODO Auto-generated method stub
		Configuration cf = new Configuration().configure();
		SessionFactory sf = cf.buildSessionFactory();
		Session ss = sf.openSession();
		Transaction ts = ss.beginTransaction();
		Criteria crit = ss.createCriteria(User.class);
		Criterion c1 = Restrictions.eq("tel", tel);
		crit.add(c1);

		User SearchUser = new User();
		ArrayList<User> userList = (ArrayList<User>) crit.list();
		System.out.println(userList.size());
		for (User u : userList) {
			SearchUser = u;
		}
		ts.commit();
		ss.close();
		return SearchUser;
	}

	@Override
	public List<User> rank() {
		Configuration cf = new Configuration().configure();
		SessionFactory sf = cf.buildSessionFactory();
		Session ss = sf.openSession();
		Transaction ts = ss.beginTransaction();
		Criteria crit = ss.createCriteria(User.class);
		crit.addOrder(Order.desc("honor"));

		ArrayList<User> userList = (ArrayList<User>) crit.list();

		ts.commit();
		ss.close();
		return userList;
	}

	public int findMyRank(String tel, ArrayList<User> userList) {
		int rank = 0;
		while (true) {
			if (tel.equals(userList.get(rank).getTel())) {
				break;
			} else {
				rank++;
			}
		}
		return rank + 1;

	}

}
