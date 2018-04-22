package neu.edu.bean;

import java.util.ArrayList;
import java.util.Date;

public class AddProjectBean {

	private String name;
	private String description;
	private int requiredFunds;
	private Date startDate;
	private Date endDate;
	private int category;
	private ArrayList<ReturnBean> returns;
	private int userId;
	private int donationsAllowed;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getRequiredFunds() {
		return requiredFunds;
	}
	public void setRequiredFunds(int requiredFunds) {
		this.requiredFunds = requiredFunds;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public ArrayList<ReturnBean> getReturns() {
		return returns;
	}
	public void setReturns(ArrayList<ReturnBean> returns) {
		this.returns = returns;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getDonationsAllowed() {
		return donationsAllowed;
	}
	public void setDonationsAllowed(int donationsAllowed) {
		this.donationsAllowed = donationsAllowed;
	}
}
