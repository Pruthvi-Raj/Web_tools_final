package neu.edu.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.entity.Returns;

@Repository
public class ReturnsDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public boolean addReturns(Returns returns) {
		Session session = sessionFactory.getCurrentSession();
		session.save(returns);
		return true;
	}
	
	@Transactional
	public ArrayList<Returns> getReturnsByProject(Integer projectId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Returns where projects.id=:projectId");
		query.setInteger("projectId", projectId);
		return (ArrayList<Returns>) query.list();
	}

}
