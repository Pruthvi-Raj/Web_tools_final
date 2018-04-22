package neu.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import neu.edu.bean.RolesBean;
import neu.edu.dao.RolesDao;
import neu.edu.entity.Roles;

@Service
public class RolesService {
	
	@Autowired
	private RolesDao rolesDao;
	
	public RolesBean getRoles(Integer rolesId) {
		RolesBean rolesBean = null;
		Roles roles = rolesDao.getRole(rolesId);
		if(roles != null) {
			rolesBean = new RolesBean();
			rolesBean.setId(roles.getId());
			rolesBean.setRole(roles.getRole());
			rolesBean.setRoleDesc(roles.getRoleDesc());
		}
		System.out.println(roles.getRole());
		
		return rolesBean;
	}

}
