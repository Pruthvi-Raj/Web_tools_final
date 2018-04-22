package neu.edu.bean;

public class CategoryBean {
	
	private int id;
	private String name;
	private String description;
	private boolean isEnabled;
	private boolean isDeleted;
	private boolean isdeletable;
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
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public boolean isIsdeletable() {
		return isdeletable;
	}
	public void setIsdeletable(boolean isdeletable) {
		this.isdeletable = isdeletable;
	}
}
