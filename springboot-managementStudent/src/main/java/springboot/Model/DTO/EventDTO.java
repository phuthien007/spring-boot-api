package springboot.Model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
//@JsonInclude(value = JsonInclude.Include.NON_NULL)

public class EventDTO {
	private Long id;

	private String name;

	private Long classId;

	
	private String status;

	private Date happenDate;

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

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getHappenDate() {
		return happenDate;
	}

	public void setHappenDate(Date happenDate) {
		this.happenDate = happenDate;
	}

	public EventDTO() {
	}

	public EventDTO(Long id, String name, Long classId, String status, Date happenDate) {
		this.id = id;
		this.name = name;
		this.classId = classId;
		this.status = status;
		this.happenDate = happenDate;
	}
}
