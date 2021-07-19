package springboot.Model.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassDTO {

	private Long id;

	private String name;

//	@JsonFormat(timezone = JsonFormat.DEFAULT_TIMEZONE, pattern = "yyyy-MM-dd@HH:mm:ss.SSSX" , shape = JsonFormat.Shape.STRING)
	private Date startDate;

//	@JsonFormat(timezone = JsonFormat.DEFAULT_TIMEZONE, pattern = "yyyy-MM-dd@HH:mm:ss.SSSX" , shape = JsonFormat.Shape.STRING)
	private Date endDate;

	private Long courseId;

	private Long teacherId;

	public ClassDTO() {

	}

	public ClassDTO(Long id, String name, Date startDate, Date endDate, Long courseId, Long teacherId) {
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.courseId = courseId;
		this.teacherId = teacherId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
}
