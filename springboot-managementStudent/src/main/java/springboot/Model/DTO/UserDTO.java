package springboot.Model.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDTO {


	private Long id;
	private String username;
	private String email;
	private String fullname;
	private Date birthday;
	private Date lastLoginDate;
	private Date lockoutDate;
	
	@JsonIgnore 
	private String password;
	private int loginFailedCount;
	private RoleDTO role;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the fullname
	 */
	public String getFullname() {
		return fullname;
	}
	/**
	 * @param fullname the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	
	/**
	 * @return the lastLoginDate
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	/**
	 * @param lastLoginDate the lastLoginDate to set
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	/**
	 * @return the lockoutDate
	 */
	public Date getLockoutDate() {
		return lockoutDate;
	}
	/**
	 * @param lockoutDate the lockoutDate to set
	 */
	public void setLockoutDate(Date lockoutDate) {
		this.lockoutDate = lockoutDate;
	}
	/**
	 * @return the loginFailedCount
	 */
	public int getLoginFailedCount() {
		return loginFailedCount;
	}
	/**
	 * @param loginFailedCount the loginFailedCount to set
	 */
	public void setLoginFailedCount(int loginFailedCount) {
		this.loginFailedCount = loginFailedCount;
	}
	/**
	 * 
	 */
	
	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the role
	 */
	public RoleDTO getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(RoleDTO role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
