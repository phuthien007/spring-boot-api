package springboot.Entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="exams")
public class ExamEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name", nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "course_id", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private CourseEntity course;
	
	@JsonIgnore
	@OneToMany(mappedBy = "exam",cascade = CascadeType.ALL)
	private Set<ExamResultEntity> examResult = new HashSet<ExamResultEntity>();

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

	@Override
	public String toString() {
		return "ExamEntity [id=" + id + ", name=" + name + ", course=" + course.toString() + "]";
	}

	
}
