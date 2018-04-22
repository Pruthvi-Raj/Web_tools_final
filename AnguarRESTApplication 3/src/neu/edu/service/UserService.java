package neu.edu.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.bean.UserAccountBean;
import neu.edu.bean.UserRegistrationBean;
import neu.edu.dao.RolesDao;
import neu.edu.dao.UserDAO;
import neu.edu.entity.Roles;
import neu.edu.entity.UserAccounts;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private RolesDao rolesDao;
	
	@Transactional
	public Integer validateUser(String username,String password){
		System.out.println("Service is called Called");
		String pass = DigestUtils.md5Hex(password);
		UserAccounts user = userDao.validateUser(username,pass);
		
		if (user == null) {
			System.out.println("User Not Found");
			return null;
		} else {
			System.out.println("User  Found");
			return user.getId();
		}
	}

	@Transactional
	public UserAccountBean fetchUserAccountDetails(Integer userId) {
		// TODO Auto-generated method stub
		UserAccountBean userAccountBean = null;
		
		UserAccounts userAccount = userDao.fetchUserAccount(userId);
		if(userAccount!=null){
			userAccountBean = new UserAccountBean();
			userAccountBean.setId(userAccount.getId());
			userAccountBean.setName(userAccount.getName());
			userAccountBean.setEmail(userAccount.getEmail());
			userAccountBean.setPhone(userAccount.getPhone());
			userAccountBean.setRole(userAccount.getRoles().getRole());
			userAccountBean.setUsername(userAccount.getUsername());
		}
		
		System.out.println(userAccount.getName());
		
		return userAccountBean;
	}

	@Transactional
	public boolean registerUser(UserRegistrationBean userRegistrationBean) {
		UserAccounts existinguserAccounts = userDao.fetchUserAccountByName(userRegistrationBean.getUsername());
		if(existinguserAccounts != null) {
			return false;
		} else {
			UserAccounts userAccounts = new UserAccounts();
			Roles role = rolesDao.getRole(new Integer(userRegistrationBean.getRole()));
			userAccounts.setName(userRegistrationBean.getName());
			userAccounts.setUsername(userRegistrationBean.getUsername());
			userAccounts.setEmail(userRegistrationBean.getEmail());
			userAccounts.setPhone(userRegistrationBean.getPhone());
			userAccounts.setRoles(role);
			String pass = userRegistrationBean.getPassword();
			String passwordHash = DigestUtils.md5Hex(pass);
			userAccounts.setPassword(passwordHash);
			return userDao.registerAccount(userAccounts);
		}
	}

	
	

}
