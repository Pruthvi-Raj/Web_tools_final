package neu.edu.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.bean.CategoryBean;
import neu.edu.controller.input.AddCategoryBean;
import neu.edu.dao.CategoryDao;
import neu.edu.dao.ProjectsDao;
import neu.edu.entity.Category;
import neu.edu.entity.Projects;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private ProjectsDao projectsDao;
	
	@Transactional
	public ArrayList<CategoryBean> getAllActiveCategories() {
		ArrayList<CategoryBean> categoryBeans = null;
		ArrayList<Category> categories = categoryDao.getAllActiveCategories();
		if(categories != null) {
			categoryBeans = new ArrayList<CategoryBean>();
			for(Category category : categories) {
				CategoryBean categoryBean = new CategoryBean();
				categoryBean.setId(category.getId());
				categoryBean.setName(category.getName());
				categoryBean.setDescription(category.getDescription());
				categoryBean.setEnabled(category.getIsEnabled()!=0);
				categoryBean.setDeleted(category.getIsDeleted()!=0);
				if(hasProjects(category)) {
					categoryBean.setIsdeletable(false);
				} else {
					categoryBean.setIsdeletable(true);
				}
				categoryBeans.add(categoryBean);
			}
		}
		return categoryBeans;
	}
	
	@Transactional
	public ArrayList<CategoryBean> getAllUndeletedCategories() {
		ArrayList<CategoryBean> categoryBeans = null;
		ArrayList<Category> categories = categoryDao.getAllActiveCategories();
		if(categories != null) {
			categoryBeans = new ArrayList<CategoryBean>();
			for(Category category : categories) {
				CategoryBean categoryBean = new CategoryBean();
				categoryBean.setId(category.getId());
				categoryBean.setName(category.getName());
				categoryBean.setDescription(category.getDescription());
				categoryBean.setEnabled(category.getIsEnabled()!=0);
				categoryBean.setDeleted(category.getIsDeleted()!=0);
				if(hasProjects(category)) {
					categoryBean.setIsdeletable(false);
				} else {
					categoryBean.setIsdeletable(true);
				}
				categoryBeans.add(categoryBean);
			}
		}
		return categoryBeans;
	}
	
	@Transactional
	public ArrayList<CategoryBean> getAllCategories() {
		ArrayList<CategoryBean> categoryBeans = null;
		ArrayList<Category> categories = categoryDao.getAllCategories();
		if(categories != null) {
			categoryBeans = new ArrayList<CategoryBean>();
			for(Category category : categories) {
				CategoryBean categoryBean = new CategoryBean();
				categoryBean.setId(category.getId());
				categoryBean.setName(category.getName());
				categoryBean.setDescription(category.getDescription());
				categoryBean.setEnabled(category.getIsEnabled()!=0);
				categoryBean.setDeleted(category.getIsDeleted()!=0);
				if(hasProjects(category)) {
					categoryBean.setIsdeletable(false);
				} else {
					categoryBean.setIsdeletable(true);
				}
				categoryBeans.add(categoryBean);
			}
		}
		return categoryBeans;
	}
	
	
	@Transactional
	public boolean disableCategory(Integer catId) {
		int rowsEffected = categoryDao.disableCategory(catId);
		if(rowsEffected==1) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public boolean enableCategory(Integer catId) {
		int rowsEffected = categoryDao.enableCategory(catId);
		if(rowsEffected==1) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public boolean deleteCategory(Integer catId) {
		int rowsEffected = categoryDao.deleteCategory(catId);
		if(rowsEffected==1) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public boolean undeleteCategory(Integer catId) {
		int rowsEffected = categoryDao.undeleteCategory(catId);
		if(rowsEffected==1) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public boolean checkCategoryExistsByName(String categoryName) {
		Category category = categoryDao.getCategoryByName(categoryName);
		if(category == null) {
			return false;
		} else {
			return true;
		}
		
	}
	
	@Transactional
	public boolean addCategory(AddCategoryBean addCategoryBean) {
		Category category = categoryDao.getCategoryByName(addCategoryBean.getName());
		if(category == null) {
			boolean status = categoryDao.addCategory(addCategoryBean);
			if(status) {
				return true;
			}
		} 
		return false;
	}
	
	@Transactional
	public boolean hasProjects(Category category) {
		ArrayList<Projects> projects = projectsDao.getUndeletedProjectsInCategory(category);
		if(projects == null || projects.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

}
