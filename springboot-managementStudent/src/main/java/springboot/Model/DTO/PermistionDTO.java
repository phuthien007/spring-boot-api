package springboot.Model.DTO;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonInclude;
import springboot.Entity.RoleEntity;
import springboot.Entity.UserEntity;
//@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PermistionDTO {

	private Long id;

	private String perName;

	private String description;

	@JsonIgnore
	private Set<UserEntity> user = new HashSet<UserEntity>();

	@JsonIgnore
	private Set<RoleEntity> role = new HashSet<RoleEntity>();

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
	 * @return the perName
	 */
	public String getPerName() {
		return perName;
	}

	/**
	 * @param perName the perName to set
	 */
	public void setPerName(String perName) {
		this.perName = perName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the user
	 */
	public Set<UserEntity> getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Set<UserEntity> user) {
		this.user = user;
	}

	/**
	 * @return the role
	 */
	public Set<RoleEntity> getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Set<RoleEntity> role) {
		this.role = role;
	}

	/**
	 * @param id
	 * @param perName
	 * @param description
	 * @param user
	 * @param role
	 */
	public PermistionDTO(Long id, String perName, String description, Set<UserEntity> user, Set<RoleEntity> role) {
		super();
		this.id = id;
		this.perName = perName;
		this.description = description;
		this.user = user;
		this.role = role;
	}

}
