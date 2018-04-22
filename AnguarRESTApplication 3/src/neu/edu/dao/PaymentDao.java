package neu.edu.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.entity.Payment;

@Repository
public class PaymentDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public ArrayList<Payment> getPaymentByProject(Integer projId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Payment where projects.id=:projectId");
		query.setInteger("projectId", projId);
		return (ArrayList<Payment>) query.list();
	}
	
	@Transactional
	public boolean makePayment(Payment payment) {
		Session session = sessionFactory.getCurrentSession();
		session.save(payment);
		return true;
	}

}
