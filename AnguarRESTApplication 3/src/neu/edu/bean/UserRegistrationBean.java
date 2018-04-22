package neu.edu.bean;

public class UserRegistrationBean {
	private String name;
	private String username;
	private String password;
	private String email;
	private String phone;
	private int role;
	
	
	
	public UserRegistrationBean() {
		// TODO Auto-generated constructor stub
	}
	


	public UserRegistrationBean(String name, String username, String password, String email) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}



	public int getRole() {
		return role;
	}



	public void setRole(int role) {
		this.role = role;
	}

	
	
	

}
