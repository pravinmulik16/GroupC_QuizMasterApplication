package miniproject.QuizMasterApplication;

public class StudentRegistration {

	private String first_name;
	private String last_name;
	private String username;
	private String Password;
	private String city;
	private String email;
	private long mobile;

	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	@Override
	public String toString() {
		return "StudentRegistration [first_name=" + first_name + ", last_name=" + last_name
				+ ", username=" + username + ", Password=" + Password + ", city=" + city + ", email=" + email
				+ ", mobile=" + mobile + "]";
	}
	public StudentRegistration(String first_name, String last_name, String username, String password,
			String city, String email, long mobile) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		Password = password;
		this.city = city;
		this.email = email;
		this.mobile = mobile;
	}
	
	
}
