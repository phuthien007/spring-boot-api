package springboot.Model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class EventDTO {
	private Long id;

	private String name;

	private ClassDTO classes;

	
	private String status;

	private Date happenDate;

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
	 * @return the happenDate
	 */
	public Date getHappenDate() {
		return happenDate;
	}

	/**
	 * @param happenDate the happenDate to set
	 */
	public void setHappenDate(Date happenDate) {
		this.happenDate = happenDate;
	}

	/**
	 * 
	 */
	public EventDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassDTO getClasses() {
		return classes;
	}

	public void setClasses(ClassDTO classes) {
		this.classes = classes;
	}

	
	
}
