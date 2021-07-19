package springboot.Model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

//@JsonInclude(value = JsonInclude.Include.NON_NULL)

public class PlanDTO {

	private Long id;
	
	private String name;

	public PlanDTO() {
	}

	public PlanDTO(Long id, String name, Long courseId) {
		this.id = id;
		this.name = name;
		this.courseId = courseId;
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

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	private Long courseId;

}
