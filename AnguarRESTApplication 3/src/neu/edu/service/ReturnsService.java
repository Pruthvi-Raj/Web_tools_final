package neu.edu.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.bean.ReturnBean;
import neu.edu.controller.output.ResturnsResponseBean;
import neu.edu.dao.ReturnsDao;
import neu.edu.entity.Projects;
import neu.edu.entity.Returns;

@Service
public class ReturnsService {
	
	@Autowired
	private ReturnsDao returnsDao;
	
	@Transactional
	public boolean addReturns(ReturnBean returnBean, Projects projects) {
		Returns returns = new Returns();
		returns.setAmount(returnBean.getAmount());
		returns.setReturnNum(returnBean.getReturnNumber());
		returns.setReturnDesc(returnBean.getReturnDescription());
		returns.setProjects(projects);
		return returnsDao.addReturns(returns);
	}
	
	@Transactional
	public ArrayList<ResturnsResponseBean> getReturnsByProject(Integer projectId) {
		ArrayList<Returns> returns = returnsDao.getReturnsByProject(projectId);
		ArrayList<ResturnsResponseBean> responseBeans = null;
		if(!returns.isEmpty()) {
			responseBeans = new ArrayList<>();
			for(Returns return1 : returns) {
				ResturnsResponseBean responseBean = new ResturnsResponseBean();
				responseBean.setId(return1.getId());
				responseBean.setAmount(return1.getAmount());
				responseBean.setProjectId(return1.getProjects().getId());
				responseBean.setReturnDesc(return1.getReturnDesc());
				responseBean.setReturnNum(return1.getReturnNum());
				responseBeans.add(responseBean);
			}
		}
		return responseBeans;
	}

}
