package neu.edu.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.entity.Category;
import neu.edu.entity.Projects;

@Repository
public class ProjectsDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public ArrayList<Projects> getActiveProjectsInCategory(Category category) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Projects where category=:category and is_deleted='0' and is_active='1'");
		query.setEntity("category", category);
		ArrayList<Projects> list = (ArrayList<Projects>) query.list();
		return list;
	}
	
	@Transactional
	public ArrayList<Projects> getUndeletedProjectsInCategory(Category category) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Projects where category=:category and is_deleted='0'");
		query.setEntity("category", category);
		ArrayList<Projects> list = (ArrayList<Projects>) query.list();
		return list;
	}
	
	@Transactional
	public ArrayList<Projects> getAllProjectsInCategory(Category category) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Projects where category=:category");
		query.setEntity("category", category);
		ArrayList<Projects> list = (ArrayList<Projects>) query.list();
		return list;
	}
	
	@Transactional
	public ArrayList<Projects> getAllProjectsByUser(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Projects where userAccounts.id=:userID");
		query.setInteger("userID", userId);
		ArrayList<Projects> list = (ArrayList<Projects>) query.list();
		return list;
	}
	
	@Transactional
	public int deleteProject(Integer projectId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Projects set isDeleted = '1' WHERE id = :projectId");
		query.setInteger("projectId", projectId);
		int result = query.executeUpdate();
		return result;
	}
	
	@Transactional
	public int undeleteProject(Integer projectId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Projects set isDeleted = '0' WHERE id = :projectId");
		query.setInteger("projectId", projectId);
		int result = query.executeUpdate();
		return result;
	}
	
	@Transactional
	public Projects addProject(Projects projects) {
		Session session = sessionFactory.getCurrentSession();
		session.save(projects);
		return projects;
	}
	
	@Transactional
	public Projects getProjectById(Integer projectId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Projects where id=:id");
		query.setInteger("id", projectId);
		return (Projects) query.uniqueResult();
	}
	
	@Transactional
	public Projects updateProjects(Projects projects) {
		Session session = sessionFactory.getCurrentSession();
		session.update(projects);
		return projects;
	}
	
	@Transactional
	public ArrayList<Projects> getUndeletedProjects() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Projects where is_deleted='0'");
		ArrayList<Projects> list = (ArrayList<Projects>) query.list();
		return list;
	}

}
