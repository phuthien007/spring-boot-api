package springboot.Entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username",unique = true)
	private String username;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "fullname")
	private String fullname;

	@Column(name = "birthday")
	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column(name = "last_login_date")
	private Date lastLoginDate;

	@Column(name = "lockout_date")
	private Date lockoutDate;

	@Column(name = "login_failed_count")
	private int loginFailedCount;

	@Column(name = "register_date")
	@CreatedDate
	private Date registerDate;
	
	@Column(name="forgot_password_token")
	private String forgotPassWordToken;

/**
	 * @return the forgotPassWordToken
	 */
	public String getForgotPassWordToken() {
		return forgotPassWordToken;
	}



	/**
	 * @param forgotPassWordToken the forgotPassWordToken to set
	 */
	public void setForgotPassWordToken(String forgotPassWordToken) {
		this.forgotPassWordToken = forgotPassWordToken;
	}



	/**
	 * @return the permistion
	 */
	public Set<PermistionEntity> getPermistion() {
		return permistion;
	}



	/**
	 * @param permistion the permistion to set
	 */
	public void setPermistion(Set<PermistionEntity> permistion) {
		this.permistion = permistion;
	}



	//	@JsonIgnore
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
//	private Set<AuthorizationEachAuthorEntity> authorization = new HashSet<AuthorizationEachAuthorEntity>();
//	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JoinColumn( name = "role", referencedColumnName = "id" )
	private RoleEntity role;

	@JsonIgnore
	@ManyToMany( cascade = CascadeType.ALL)
	@JoinTable(name = "authorization_each_author", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "per_id"))
	private Set<PermistionEntity> permistion = new HashSet<PermistionEntity>();

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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}



	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the registerDate
	 */
	public Date getRegisterDate() {
		return registerDate;
	}



	/**
	 * @param registerDate the registerDate to set
	 */
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}



	/**
	 * @return the role
	 */
	public RoleEntity getRole() {
		return role;
	}



	/**
	 * @param role the role to set
	 */
	public void setRole(RoleEntity role) {
		this.role = role;
	}



	



	/**
	 * 
	 */
	public UserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}



	public UserEntity(String string, String string2) {
		// TODO Auto-generated constructor stub
	}

}
