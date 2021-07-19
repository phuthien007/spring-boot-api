package springboot.Entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "permistions")
public class PermistionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "per_name")
	private String perName;

	@Column(name = "description")
	private String description;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "permistion")
	private Set<UserEntity> user = new HashSet<UserEntity>();

	@JsonIgnore
	@ManyToMany( cascade = CascadeType.ALL, mappedBy = "permistion")
	private Set<RoleEntity> role = new HashSet<RoleEntity>();

//	@JsonIgnore
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "permistion")
//	private Set<AuthorizationEachAuthorEntity> authorizationEachAuthor = new HashSet<AuthorizationEachAuthorEntity>();

//	@JsonIgnore
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "permistion")
//	private Set<AuthorizationEachRoleEntity> authorizationEachRole = new HashSet<AuthorizationEachRoleEntity>();

	
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
	 * @param id
	 * @param perName
	 * @param description
	 */
	public PermistionEntity(Long id, String perName, String description) {
		super();
		this.id = id;
		this.perName = perName;
		this.description = description;
	}

	/**
	 * 
	 */
	public PermistionEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
