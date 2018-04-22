package neu.edu.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.entity.UserPaymentInfo;

@Repository
public class UserPaymentInfoDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public UserPaymentInfo savePaymentInfo(UserPaymentInfo userPaymentInfo) {
		Session session = sessionFactory.getCurrentSession();
		session.save(userPaymentInfo);
		return userPaymentInfo;
	}

}
