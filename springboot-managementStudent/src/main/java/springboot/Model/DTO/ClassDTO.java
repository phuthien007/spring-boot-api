package springboot.Model.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassDTO {

	private Long id;

	private String name;

	@JsonFormat(timezone = JsonFormat.DEFAULT_TIMEZONE, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", shape = JsonFormat.Shape.STRING)
	private Date startDate;

	@JsonFormat( pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", shape = JsonFormat.Shape.STRING)
	private Date endDate;

	private CourseDTO course;

	private TeacherDTO teacher;

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
	 * @return the teacher
	 */
	public TeacherDTO getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher the teacher to set
	 */
	public void setTeacher(TeacherDTO teacher) {
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

	private String status;

	public ClassDTO() {
		// TODO Auto-generated constructor stub
	}

}
