package neu.edu.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.bean.AddCommentsBean;
import neu.edu.bean.CommentsResponseBean;
import neu.edu.dao.CommentsDao;
import neu.edu.dao.ProjectsDao;
import neu.edu.dao.UserDAO;
import neu.edu.entity.Comments;
import neu.edu.entity.Projects;
import neu.edu.entity.UserAccounts;

@Service
public class CommentsService {
	
	@Autowired
	private CommentsDao commentsDao;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ProjectsDao projectsDao;
	
	@Transactional
	public ArrayList<CommentsResponseBean> getCommentsByProject(Integer projectId) {
		ArrayList<Comments> comments = commentsDao.getCommentsByProject(projectId);
		ArrayList<CommentsResponseBean> commentsResponseBeans = null;
		if(!comments.isEmpty()) {
			commentsResponseBeans = new ArrayList<>();
			for(Comments comment : comments) {
				CommentsResponseBean commentsResponseBean = new CommentsResponseBean();
				commentsResponseBean.setId(comment.getId());
				commentsResponseBean.setProjectId(comment.getProjects().getId());
				commentsResponseBean.setUserId(comment.getUserAccounts().getId());
				commentsResponseBean.setUserName(comment.getUserAccounts().getName());
				commentsResponseBean.setComments(comment.getComments());
				commentsResponseBeans.add(commentsResponseBean);
			}
		}
		return commentsResponseBeans;
	}
	
	@Transactional
	public boolean addComment(AddCommentsBean addCommentsBean) {
		UserAccounts userAccounts = userDAO.fetchUserAccount(addCommentsBean.getUserId());
		Projects projects = projectsDao.getProjectById(addCommentsBean.getProjectId());
		Comments comments = new Comments();
		comments.setUserAccounts(userAccounts);
		comments.setProjects(projects);
		comments.setComments(addCommentsBean.getComment());
		return commentsDao.addComments(comments);
	}
	
	

}
