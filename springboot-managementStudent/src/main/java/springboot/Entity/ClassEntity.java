package springboot.Entity;

import java.sql.Timestamp;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "classes")
public class ClassEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "start_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(timezone = JsonFormat.DEFAULT_TIMEZONE, pattern = "yyyy-MM-dd@HH:mm:ss.SSSX" , shape = JsonFormat.Shape.STRING)
	private Date startDate;

	@Column(name = "end_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(timezone = JsonFormat.DEFAULT_TIMEZONE, pattern = "yyyy-MM-dd@HH:mm:ss.SSSX" , shape = JsonFormat.Shape.STRING)
	private Date endDate;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JoinColumn(name = "course_id", referencedColumnName = "id")
	private CourseEntity course;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JoinColumn(name = "teacher_id", referencedColumnName = "id")
	private TeacherEntity teacher;

	@Column(name = "status")
	private String status;

	@JsonIgnore
	@ManyToMany( cascade = CascadeType.ALL, mappedBy = "c")
	public Set<StudentEntity> student = new HashSet<StudentEntity>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "c")
	private Set<ExamResultEntity> examResult = new HashSet<ExamResultEntity>();

	@JsonIgnore
	@OneToMany( cascade = CascadeType.ALL, mappedBy = "c")
	private Set<EventEntity> event = new HashSet<EventEntity>();

//	@JsonIgnore
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "C")
//	private Set<RegistrationEntity> registration = new HashSet<RegistrationEntity>();

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the course
	 */
	public CourseEntity getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(CourseEntity course) {
		this.course = course;
	}

	/**
	 * @return the teacher
	 */
	public TeacherEntity getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher the teacher to set
	 */
	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
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
	 * @return the student
	 */
	public Set<StudentEntity> getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(Set<StudentEntity> student) {
		this.student = student;
	}

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
	 * @return the event
	 */
	public Set<EventEntity> getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(Set<EventEntity> event) {
		this.event = event;
	}

	/**
	 * @param id
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param course
	 * @param teacher
	 * @param status
	 * @param student
	 * @param examResult
	 * @param event
	 */
	public ClassEntity(Long id, String name, Date startDate, Date endDate, CourseEntity course, TeacherEntity teacher,
			String status, Set<StudentEntity> student, Set<ExamResultEntity> examResult, Set<EventEntity> event) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.course = course;
		this.teacher = teacher;
		this.status = status;
		this.student = student;
		this.examResult = examResult;
		this.event = event;
	}

	/**
	 * 
	 */
	public ClassEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
