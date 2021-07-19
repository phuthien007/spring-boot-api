package springboot.Model.DTO;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

//@JsonInclude(value = JsonInclude.Include.NON_NULL)

public class ExamDTO {
	private Long id;

	private String name;

	private Long courseId;

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

	public ExamDTO(Long id, String name, Long courseId) {
		this.id = id;
		this.name = name;
		this.courseId = courseId;
	}

	public ExamDTO() {
	}
}
