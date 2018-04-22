package neu.edu.bean;

import java.util.Date;

public class UserProjectBean {
	
	private int id;
	private String name;
	private String description;
	private int requiredFunds;
	private int acquiredFunds;
	private Date startDate;
	private Date endDate;
	private boolean isActive;
	private boolean isDeleted;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public int getAcquiredFunds() {
		return acquiredFunds;
	}
	public void setAcquiredFunds(int acquiredFunds) {
		this.acquiredFunds = acquiredFunds;
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
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
}
