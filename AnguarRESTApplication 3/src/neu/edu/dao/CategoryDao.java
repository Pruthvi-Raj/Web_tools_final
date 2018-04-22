package neu.edu.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.controller.input.AddCategoryBean;
import neu.edu.entity.Category;

@Repository
public class CategoryDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public ArrayList<Category> getAllActiveCategories() {
		Session session = sessionFactory.getCurrentSession();
		ArrayList<Category> list = (ArrayList<Category>) session.createQuery("from Category where isEnabled = '1' and isDeleted = '0'").list();
		return list;
	}
	
	@Transactional
	public ArrayList<Category> getAllUndeletedCategories() {
		Session session = sessionFactory.getCurrentSession();
		ArrayList<Category> list = (ArrayList<Category>) session.createQuery("from Category where isDeleted = '0'").list();
		return list;
	}
	
	@Transactional
	public ArrayList<Category> getAllCategories() {
		Session session = sessionFactory.getCurrentSession();
		ArrayList<Category> list = (ArrayList<Category>) session.createQuery("from Category").list();
		return list;
	}

	@Transactional
	public int disableCategory(Integer catId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Category set isEnabled = '0' WHERE id = :catId");
		query.setInteger("catId", catId);
		int result = query.executeUpdate();
		return result;
	}
	
	@Transactional
	public int enableCategory(Integer catId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Category set isEnabled = '1' WHERE id = :catId");
		query.setInteger("catId", catId);
		int result = query.executeUpdate();
		return result;
	}
	
	@Transactional
	public int deleteCategory(Integer catId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Category set isDeleted = '1' WHERE id = :catId");
		query.setInteger("catId", catId);
		int result = query.executeUpdate();
		return result;
	}
	
	@Transactional
	public int undeleteCategory(Integer catId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Category set isDeleted = '0' WHERE id = :catId");
		query.setInteger("catId", catId);
		int result = query.executeUpdate();
		return result;
	}
	
	@Transactional
	public Category getCategoryByName(String categoryName) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Category where name=:name");
		query.setString("name", categoryName);
		Category category = (Category) query.uniqueResult();
		return category;
	}
	
	@Transactional
	public Category getCategoryById(Integer categoryId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Category where id=:id");
		query.setInteger("id", categoryId);
		Category category = (Category) query.uniqueResult();
		return category;
	}
	
	@Transactional
	public boolean addCategory(AddCategoryBean addCategoryBean) {
		Session session = sessionFactory.getCurrentSession();
		Category category = new Category();
		category.setName(addCategoryBean.getName());
		category.setDescription(addCategoryBean.getDescription());
		category.setIsDeleted((byte) 0);
		category.setIsEnabled((byte) 1);
		session.save(category);
		return true;
	}
	
}
