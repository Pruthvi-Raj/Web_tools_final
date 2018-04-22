package neu.edu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.bean.AddProjectBean;
import neu.edu.bean.ReturnBean;
import neu.edu.bean.UserProjectBean;
import neu.edu.dao.CategoryDao;
import neu.edu.dao.ProjectsDao;
import neu.edu.dao.UserDAO;
import neu.edu.entity.Category;
import neu.edu.entity.Projects;
import neu.edu.entity.UserAccounts;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectsDao projectsDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ReturnsService returnsService;
	
	
	public boolean addProject(AddProjectBean addProjectBean){
		boolean status = true;
		Projects projects = new Projects();
		projects.setName(addProjectBean.getName());
		projects.setDescription(addProjectBean.getDescription());
		projects.setRequiredFunds(addProjectBean.getRequiredFunds());
		projects.setAcquiredFunds(0);
		projects.setStartDate(addProjectBean.getStartDate());
		projects.setEndDate(addProjectBean.getEndDate());
		projects.setDonationsAllowed(addProjectBean.getDonationsAllowed());
		Category category = categoryDao.getCategoryById(addProjectBean.getCategory());
		projects.setCategory(category);
		projects.setIsActive((byte) (1));
		projects.setIsDeleted((byte) (0));
		projects.setDonationReceived(0);
		UserAccounts userAccounts = userDAO.fetchUserAccount(addProjectBean.getUserId());
		projects.setUserAccounts(userAccounts);
		projects = projectsDao.addProject(projects);
		for(ReturnBean returnBean : addProjectBean.getReturns()) {
			if(!(returnsService.addReturns(returnBean, projects))) {
				status = false;
			}
		}
		return status;
	}
//
//	public boolean updateProject(UserProjectBean userProjectBean, Integer userId) {
//		// TODO Auto-generated method stub
//		return userDAO.updateProject(userProjectBean, userId);
//	}
	
	@Transactional
	public List<UserProjectBean> getAllProjects(Integer catID) {
		Category category = categoryDao.getCategoryById(catID);
		ArrayList<Projects> projects1 = projectsDao.getAllProjectsInCategory(category);
		ArrayList<Projects> projects = updateActivity(projects1);
		ArrayList<UserProjectBean> response = null;
		if(projects!=null) {
			response = new ArrayList<UserProjectBean>();
			for(Projects project:projects){
				UserProjectBean userProjectBean = new UserProjectBean();
				userProjectBean.setId(project.getId());
				userProjectBean.setName(project.getName());
				userProjectBean.setDescription(project.getDescription());
				userProjectBean.setStartDate(project.getStartDate());
				userProjectBean.setEndDate(project.getEndDate());
				userProjectBean.setRequiredFunds(project.getRequiredFunds());
				userProjectBean.setAcquiredFunds(project.getAcquiredFunds());
				userProjectBean.setActive(project.getIsActive()!=0);
				userProjectBean.setDeleted(project.getIsDeleted()!=0);
				response.add(userProjectBean);
			}
		}
		
		return response;
	}
	
	@Transactional
	public List<UserProjectBean> getAllUndeletedProjects(Integer catID) {
		Category category = categoryDao.getCategoryById(catID);
		ArrayList<Projects> projects = projectsDao.getUndeletedProjectsInCategory(category);
		ArrayList<UserProjectBean> response = null;
		if(projects!=null) {
			response = new ArrayList<UserProjectBean>();
			for(Projects project:projects){
				UserProjectBean userProjectBean = new UserProjectBean();
				userProjectBean.setId(project.getId());
				userProjectBean.setName(project.getName());
				userProjectBean.setDescription(project.getDescription());
				userProjectBean.setStartDate(project.getStartDate());
				userProjectBean.setEndDate(project.getEndDate());
				userProjectBean.setRequiredFunds(project.getRequiredFunds());
				userProjectBean.setAcquiredFunds(project.getAcquiredFunds());
				userProjectBean.setActive(project.getIsActive()!=0);
				userProjectBean.setDeleted(project.getIsDeleted()!=0);
				response.add(userProjectBean);
			}
		}
		
		return response;
	}
	
	@Transactional
	public List<UserProjectBean> getAllActiveProjects(Integer catID) {
		Category category = categoryDao.getCategoryById(catID);
		ArrayList<Projects> projects = projectsDao.getActiveProjectsInCategory(category);
		ArrayList<UserProjectBean> response = null;
		if(projects!=null) {
			response = new ArrayList<UserProjectBean>();
			for(Projects project:projects){
				UserProjectBean userProjectBean = new UserProjectBean();
				userProjectBean.setId(project.getId());
				userProjectBean.setName(project.getName());
				userProjectBean.setDescription(project.getDescription());
				userProjectBean.setStartDate(project.getStartDate());
				userProjectBean.setEndDate(project.getEndDate());
				userProjectBean.setRequiredFunds(project.getRequiredFunds());
				userProjectBean.setAcquiredFunds(project.getAcquiredFunds());
				userProjectBean.setActive(project.getIsActive()!=0);
				userProjectBean.setDeleted(project.getIsDeleted()!=0);
				response.add(userProjectBean);
			}
		}
		
		return response;
	}
	
	@Transactional
	public List<UserProjectBean> getAllProjectsByUser(Integer userId) {
		
		ArrayList<Projects> projects = projectsDao.getAllProjectsByUser(userId);
		ArrayList<UserProjectBean> response = null;
		if(projects!=null) {
			response = new ArrayList<UserProjectBean>();
			for(Projects project:projects){
				UserProjectBean userProjectBean = new UserProjectBean();
				userProjectBean.setId(project.getId());
				userProjectBean.setName(project.getName());
				userProjectBean.setDescription(project.getDescription());
				userProjectBean.setStartDate(project.getStartDate());
				userProjectBean.setEndDate(project.getEndDate());
				userProjectBean.setRequiredFunds(project.getRequiredFunds());
				userProjectBean.setAcquiredFunds(project.getAcquiredFunds());
				userProjectBean.setActive(project.getIsActive()!=0);
				userProjectBean.setDeleted(project.getIsDeleted()!=0);
				response.add(userProjectBean);
			}
		}
		
		return response;
	}
	
	@Transactional
	public boolean deleteProject(Integer projectId) {
		int rowsEffected = projectsDao.deleteProject(projectId);
		if(rowsEffected==1) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public boolean undeleteProject(Integer projectId) {
		int rowsEffected = projectsDao.undeleteProject(projectId);
		if(rowsEffected==1) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public ArrayList<Projects> updateActivity(ArrayList<Projects> projects) {
		for(Projects project : projects) {
			if(project.getEndDate().before(new Date())) {
				project.setIsActive((byte) 0);
				projectsDao.updateProjects(project);
			}
		}
		return projects;
	}

}
