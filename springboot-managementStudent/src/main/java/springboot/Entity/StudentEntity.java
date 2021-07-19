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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "students")
public class StudentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "full_name")
	private String fullname;

	@Column(name = "address")
	private String address;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "birthday")
	@Temporal(TemporalType.DATE)
	private Date birthday;
	@Column(name = "note")
	private String note;

	@Column(name = "facebook")
	private String facebook;

	@Column(name = "create_date")
	@CreatedDate
	private Date createDate;

	@JsonIgnore
	@OneToMany(mappedBy = "student")
	private Set<ExamResultEntity> examResult = new HashSet<ExamResultEntity>();
	
	@JsonIgnore
	@ManyToMany( cascade = CascadeType.ALL )
	@JoinTable(name="registrations",
			joinColumns = @JoinColumn(name="student_id"),
			inverseJoinColumns = @JoinColumn(name="class_id")
			)
	public Set<ClassEntity> c = new HashSet<ClassEntity>();
	
	

	//	@JsonIgnore
//	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "student")
//	private Set<RegistrationEntity> registration = new HashSet<RegistrationEntity>();
//	
	
	/**
	 * @return the examResult
	 */
	public Set<ExamResultEntity> getExamResult() {
		return examResult;
	}

	/**
	 * @param examResult the examResult to set
	 */
	public void setExamResult(Set<ExamResultEntity> examResult) {
		this.examResult = examResult;
	}

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
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the facebook
	 */
	public String getFacebook() {
		return facebook;
	}

	/**
	 * @param facebook the facebook to set
	 */
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param id
	 * @param fullname
	 * @param address
	 * @param email
	 * @param phone
	 * @param birthday
	 * @param note
	 * @param facebook
	 * @param createDate
	 */
	public StudentEntity(Long id, String fullname, String address, String email, String phone, Date birthday,
			String note, String facebook, Date createDate) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.birthday = birthday;
		this.note = note;
		this.facebook = facebook;
		this.createDate = createDate;
	}

	/**
	 * 
	 */
	public StudentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
