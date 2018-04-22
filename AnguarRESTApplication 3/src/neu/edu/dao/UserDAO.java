package neu.edu.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.entity.UserAccounts;

@Repository
public class UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public UserAccounts validateUser(String username, String password) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from UserAccounts where username=:username and password=:password ");
		query.setString("username", username);
		query.setString("password", password);

		UserAccounts user = (UserAccounts) query.uniqueResult();

		return user;

	}
	
	@Transactional
	public UserAccounts createUser(UserAccounts userAccount) {
		Session session = sessionFactory.getCurrentSession();
		session.save(userAccount);
		return userAccount;
	}
	
	@Transactional
	public UserAccounts fetchUserAccount(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		return session.load(UserAccounts.class, userId);
	}
	
	@Transactional
	public UserAccounts fetchUserAccountByName(String username) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from UserAccounts where username=:username");
		query.setString("username", username);
		return (UserAccounts) query.uniqueResult();
	}
	
	@Transactional
	public boolean registerAccount(UserAccounts userAccounts) {
		Session session = sessionFactory.getCurrentSession();
		session.save(userAccounts);
		return true;
	}

//	@Transactional
//	public boolean addProject(UserProjectBean userProjectBean,Integer userId) {
//		
//		// TODO Auto-generated method stub
//		System.out.println("im am here");
//		Session session = sessionFactory.openSession();
//		
//		UserProjectId userProjectId = new UserProjectId(userId, userProjectBean.getName());
//		UserProject userProject = new UserProject();
//		userProject.setId(userProjectId);
//		userProject.setDescription(userProjectBean.getDesc());
//		
//		session.save(userProject);
//		session.flush();
//		return true;
//	}
//
//	@Transactional
//	public boolean updateProject(UserProjectBean userProjectBean, Integer userId) {
//		// TODO Auto-generated method stub
//		Session session = sessionFactory.openSession();
//		
//		UserProjectId userProjectId = new UserProjectId(userId, userProjectBean.getName());
//		UserProject userProject = new UserProject();
//		userProject.setId(userProjectId);
//		userProject.setDescription(userProjectBean.getDesc());
//		
//		session.saveOrUpdate(userProject);
//		session.flush();
//
//		return true;
//	}
//	
//	@Transactional
//	public List<UserProject> getAllProjects(Integer userId) {
//		// TODO Auto-generated method stub
//		Session session = sessionFactory.openSession();
//		return session.createQuery(" from UserProject where id.userId = :userid")
//			    .setParameter("userid", userId)
//			    .list();
//
//	}



}
