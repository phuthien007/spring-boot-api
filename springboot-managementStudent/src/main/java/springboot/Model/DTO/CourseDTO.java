package springboot.Model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

//@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CourseDTO {

	private Long id;

	private String name;

	private String type;

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

	

	/**
	 * @param id
	 * @param name
	 * @param type
	 * @param exam
	 * @param plan
	 */
	public CourseDTO(Long id, String name, String type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public CourseDTO() {
		// TODO Auto-generated constructor stub
	}
	
}
