package springboot.Model.DTO;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ExamDTO {
	private Long id;
	
	private String name;
	
	private CourseDTO course;
	
	@JsonIgnore
	private Set<ExamResultDTO> examResult = new HashSet<ExamResultDTO>();

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
	public CourseDTO getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(CourseDTO course) {
		this.course = course;
	}

	/**
	 * @return the examResult
	 */
	public Set<ExamResultDTO> getExamResult() {
		return examResult;
	}

	/**
	 * @param examResult the examResult to set
	 */
	public void setExamResult(Set<ExamResultDTO> examResult) {
		this.examResult = examResult;
	}

	@Override
	public String toString() {
		return "ExamDTO [id=" + id + ", name=" + name + ", course=" + course + "]";
	}

	/**
	 * @param id
	 * @param name
	 * @param course
	 */
	public ExamDTO(Long id, String name, CourseDTO course) {
		super();
		this.id = id;
		this.name = name;
		this.course = course;
	}

	/**
	 * 
	 */
	public ExamDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	

}
