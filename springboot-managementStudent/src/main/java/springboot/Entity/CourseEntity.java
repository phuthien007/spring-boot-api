package springboot.Entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "courses")
public class CourseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "type")
	private String type;

	@Column(name = "create_date")
	@Temporal(TemporalType.DATE)
	private Date createDate;

	/**
	 * 
	 */
	public CourseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param name
	 * @param type
	 * @param createDate
	 * @param exam
	 * @param plan
	 */
	public CourseEntity(Long id, String name, String type, Date createDate, Set<ExamEntity> exam,
			Set<PlanEntity> plan) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.createDate = createDate;
		this.exam = exam;
		this.plan = plan;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "course")
	@Cascade(CascadeType.ALL)
	private Set<ExamEntity> exam = new HashSet<ExamEntity>();

	@JsonIgnore
	@OneToMany(mappedBy = "course")
	@Cascade(CascadeType.ALL)
	private Set<PlanEntity> plan = new HashSet<>();

	
	@Override
	public String toString() {
		return "CourseEntity [id=" + id + ", name=" + name + ", type=" + type + ", createDate=" + createDate + "]";
	}

	/**
	 * @return the plan
	 */
	public Set<PlanEntity> getPlan() {
		return plan;
	}

	/**
	 * @param plan the plan to set
	 */
	public void setPlan(Set<PlanEntity> plan) {
		this.plan = plan;
	}

	/**
	 * @return the exam
	 */
	public Set<ExamEntity> getExam() {
		return exam;
	}

	/**
	 * @param exam the exam to set
	 */
	public void setExam(Set<ExamEntity> exam) {
		this.exam = exam;
	}

}
