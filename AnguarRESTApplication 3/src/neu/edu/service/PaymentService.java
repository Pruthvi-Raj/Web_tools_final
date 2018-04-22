package neu.edu.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.bean.MakePaymentBean;
import neu.edu.bean.PaymentBean;
import neu.edu.dao.PaymentDao;
import neu.edu.dao.ProjectsDao;
import neu.edu.dao.UserDAO;
import neu.edu.dao.UserPaymentInfoDao;
import neu.edu.entity.Payment;
import neu.edu.entity.Projects;
import neu.edu.entity.UserAccounts;
import neu.edu.entity.UserPaymentInfo;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentDao paymentDao;
	
	@Autowired
	private UserPaymentInfoDao userPaymentInfoDao;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ProjectsDao projectsDao;
	
	@Transactional
	public ArrayList<PaymentBean> getPaymentByProject(Integer projectID) {
		ArrayList<PaymentBean> paymentBeans = null;
		ArrayList<Payment> payments = paymentDao.getPaymentByProject(projectID);
		if(!payments.isEmpty()) {
			paymentBeans = new ArrayList<PaymentBean>();
			for(Payment payment:payments) {
				PaymentBean paymentBean = new PaymentBean();
				paymentBean.setAmount(payment.getAmount());
				paymentBean.setId(payment.getId());
				paymentBean.setPaymentDate(payment.getPaymentDate());
				paymentBean.setProjectId(payment.getProjects().getId());
				paymentBean.setUserId(payment.getUserAccounts().getId());
				paymentBean.setUserPaymentInfoId(payment.getUserPaymentInfo().getId());
				paymentBean.setUserName(payment.getUserAccounts().getName());
				paymentBeans.add(paymentBean);
			}
		}
		return paymentBeans;
	}
	
	@Transactional
	public boolean makePayment(MakePaymentBean makePaymentBean) {
		UserAccounts userAccounts = userDAO.fetchUserAccount(makePaymentBean.getUserId());
		UserPaymentInfo userPaymentInfo = new UserPaymentInfo();
		userPaymentInfo.setCardNum(makePaymentBean.getCardNum());
		userPaymentInfo.setCardCvv(makePaymentBean.getCardCvv());
		userPaymentInfo.setCardExp(makePaymentBean.getCardExp());
		userPaymentInfo.setUserAccounts(userAccounts);
		UserPaymentInfo savedUserPaymentInfo = userPaymentInfoDao.savePaymentInfo(userPaymentInfo);
		Projects projects = projectsDao.getProjectById(makePaymentBean.getProjectId());
		int funds = projects.getAcquiredFunds();
		projects.setAcquiredFunds(funds+makePaymentBean.getAmount());
		projects.setDonationReceived(projects.getDonationReceived()+1);
		if(projects.getDonationReceived()>=projects.getDonationsAllowed()) {
			projects.setIsActive((byte) 0);
		}
		projectsDao.updateProjects(projects);
		Payment payment = new Payment();
		payment.setAmount(makePaymentBean.getAmount());
		payment.setPaymentDate(new Date());
		payment.setUserAccounts(userAccounts);
		payment.setUserPaymentInfo(savedUserPaymentInfo);
		payment.setProjects(projects);
		return paymentDao.makePayment(payment);
	}
	
	
	

}
