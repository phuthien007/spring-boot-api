package springboot.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import springboot.Entity.CompositeKey.ClassStudentIdKey;

//
@Entity
@Table(name = "registrations")
public class RegistrationEntity {

	@EmbeddedId
	private ClassStudentIdKey id = new ClassStudentIdKey();

	@ManyToOne
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@MapsId("classId")
	@JoinColumn(name = "class_id", referencedColumnName = "id")
	private ClassEntity c;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@MapsId("studentId")
	@JoinColumn(name = "student_id", referencedColumnName = "id")
	private StudentEntity s;
	
	@Column(name="register_day")
	@Temporal(TemporalType.TIMESTAMP)
	private Date registerDay;
	
	@Column(name="status")
	private String status;
	
	@Column(name="create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	/**
	 * @return the id
	 */
	public ClassStudentIdKey getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ClassStudentIdKey id) {
		this.id = id;
	}

	/**
	 * @return the c
	 */
	public ClassEntity getC() {
		return c;
	}

	/**
	 * @param c the c to set
	 */
	public void setC(ClassEntity c) {
		this.c = c;
	}

	/**
	 * @return the s
	 */
	public StudentEntity getS() {
		return s;
	}

	/**
	 * @param s the s to set
	 */
	public void setS(StudentEntity s) {
		this.s = s;
	}

	/**
	 * @return the registerDay
	 */
	public Date getRegisterDay() {
		return registerDay;
	}

	/**
	 * @param registerDay the registerDay to set
	 */
	public void setRegisterDay(Date registerDay) {
		this.registerDay = registerDay;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @param c
	 * @param s
	 * @param registerDay
	 * @param status
	 * @param createDate
	 */
	public RegistrationEntity(ClassStudentIdKey id, ClassEntity c, StudentEntity s, Date registerDay, String status,
			Date createDate) {
		super();
		this.id = id;
		this.c = c;
		this.s = s;
		this.registerDay = registerDay;
		this.status = status;
		this.createDate = createDate;
	}

	/**
	 * 
	 */
	public RegistrationEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	

}
