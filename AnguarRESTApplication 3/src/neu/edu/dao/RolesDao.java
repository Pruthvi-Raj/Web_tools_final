package neu.edu.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import neu.edu.entity.Roles;

@Repository
public class RolesDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Roles getRole(Integer roleId) {
		Session session = sessionFactory.getCurrentSession();
		return session.load(Roles.class, roleId);
	}

}
