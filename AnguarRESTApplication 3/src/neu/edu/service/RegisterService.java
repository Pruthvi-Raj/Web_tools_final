package neu.edu.service;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import neu.edu.bean.UserRegistrationBean;
import neu.edu.dao.UserDAO;
import neu.edu.entity.UserAccounts;

@Service
public class RegisterService {

	@Autowired
	private UserDAO userDao;

	public Integer createUser(UserRegistrationBean userRegistrationBean) {

		UserAccounts userAccount = new UserAccounts();
		userAccount.setName(userRegistrationBean.getName());
		System.out.println(userRegistrationBean.getName());
		userAccount.setUsername(userRegistrationBean.getUsername());
		userAccount.setPassword(userRegistrationBean.getPassword());
		// 2017-12-31
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//		if (userRegistrationBean.getDob() != null) {
//			try {
//				userAccount.setAge(sdf.parse(userRegistrationBean.getDob()));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		userAccount = userDao.createUser(userAccount);

		return userAccount.getId();
	}

}
