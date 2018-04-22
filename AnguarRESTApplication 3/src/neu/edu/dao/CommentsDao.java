package neu.edu.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.entity.Comments;

@Repository
public class CommentsDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public ArrayList<Comments> getCommentsByProject(Integer projectId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Comments where projects.id=:projectId");
		query.setInteger("projectId", projectId);
		return (ArrayList<Comments>) query.list();
	}
	
	@Transactional
	public boolean addComments(Comments comments) {
		Session session = sessionFactory.getCurrentSession();
		session.save(comments);
		return true;
	}

}
