package neu.edu.bean;

import java.util.Date;

public class PaymentBean {

	private int id;
	private int projectId;
	private int userId;
	private int userPaymentInfoId;
	private int amount;
	private Date paymentDate;
	private String userName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUserPaymentInfoId() {
		return userPaymentInfoId;
	}
	public void setUserPaymentInfoId(int userPaymentInfoId) {
		this.userPaymentInfoId = userPaymentInfoId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
