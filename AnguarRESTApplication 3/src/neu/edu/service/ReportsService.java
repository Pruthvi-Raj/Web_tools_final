package neu.edu.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.bean.ReportsResponseBean;
import neu.edu.dao.CategoryDao;
import neu.edu.dao.CommentsDao;
import neu.edu.dao.PaymentDao;
import neu.edu.dao.ProjectsDao;
import neu.edu.entity.Category;
import neu.edu.entity.Comments;
import neu.edu.entity.Payment;
import neu.edu.entity.Projects;

@Service
public class ReportsService {
	
	@Autowired
	private ProjectsDao projectsDao;
	
	@Autowired
	private PaymentDao paymentDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private CommentsDao commentsDao;
	
	@Transactional
	public ArrayList<ReportsResponseBean> getPaymentsInProjects() {
		ArrayList<Projects> projects = projectsDao.getUndeletedProjects();
		ArrayList<ReportsResponseBean> reportsResponseBeans = null;
		if(!projects.isEmpty()) {
			reportsResponseBeans = new ArrayList<>();
			for(Projects project : projects) {
				ArrayList<Payment> payments = paymentDao.getPaymentByProject(project.getId());
				if(!payments.isEmpty()) {
					ReportsResponseBean reportsResponseBean = new ReportsResponseBean();
					reportsResponseBean.setName(project.getName());
					reportsResponseBean.setNumber(payments.size());
					reportsResponseBeans.add(reportsResponseBean);
				}
			}
		}
		return reportsResponseBeans;
	}
	
	@Transactional
	public ArrayList<ReportsResponseBean> getProjectsInCategory() {
		ArrayList<Category> categories = categoryDao.getAllUndeletedCategories();
		ArrayList<ReportsResponseBean> reportsResponseBeans = null;
		if(!categories.isEmpty()) {
			reportsResponseBeans = new ArrayList<>();
			for(Category category : categories) {
				ArrayList<Projects> projects = projectsDao.getUndeletedProjectsInCategory(category);
				if(!projects.isEmpty()) {
					ReportsResponseBean reportsResponseBean = new ReportsResponseBean();
					reportsResponseBean.setName(category.getName());
					reportsResponseBean.setNumber(projects.size());
					reportsResponseBeans.add(reportsResponseBean);
				}
			}
		}
		return reportsResponseBeans;
	}
	
	@Transactional
	public ArrayList<ReportsResponseBean> getCommentsInProjects() {
		ArrayList<Projects> projects = projectsDao.getUndeletedProjects();
		ArrayList<ReportsResponseBean> reportsResponseBeans = null;
		if(!projects.isEmpty()) {
			reportsResponseBeans = new ArrayList<>();
			for(Projects project : projects) {
				ArrayList<Comments> comments = commentsDao.getCommentsByProject(project.getId());
				if(!comments.isEmpty()) {
					ReportsResponseBean reportsResponseBean = new ReportsResponseBean();
					reportsResponseBean.setName(project.getName());
					reportsResponseBean.setNumber(comments.size());
					reportsResponseBeans.add(reportsResponseBean);
				}
			}
		}
		return reportsResponseBeans;
	}

}
