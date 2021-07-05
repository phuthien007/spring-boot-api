package springboot.Model.DTO;

public class PlanDTO {

	private Long id;
	
	private String name;
	
	private CourseDTO course;

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
	 * @param id
	 * @param name
	 * @param course
	 */
	public PlanDTO(Long id, String name, CourseDTO course) {
		super();
		this.id = id;
		this.name = name;
		this.course = course;
	}

	/**
	 * 
	 */
	public PlanDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "PlanDTO [id=" + id + ", name=" + name + ", course=" + course + "]";
	}

	
}
