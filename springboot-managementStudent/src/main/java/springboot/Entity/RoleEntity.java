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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="roles")
public class RoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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

	@Column(name="role_name" , unique = true)
	private String roleName;
	
	@Column(name="description")
	private String description;
		
	@JsonIgnore
	@ManyToMany( cascade = CascadeType.ALL)
	@JoinTable(name="authorization_each_role",
			joinColumns = @JoinColumn(name="role_id"),
			inverseJoinColumns = @JoinColumn(name="per_id")
			)
	private Set<PermistionEntity> permistion = new HashSet<PermistionEntity>();

	@JsonIgnore
	@OneToMany( cascade = CascadeType.ALL,mappedBy = "role")
	private Set<UserEntity> user = new HashSet<UserEntity>();
	
//	@JsonIgnore
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "role")
//	private Set<AuthorizationEachRoleEntity> authorizationEachRole= new HashSet<AuthorizationEachRoleEntity>();
	
	/**
	 * 
	 */
	public RoleEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param roleName
	 * @param description
	 */
	public RoleEntity(String roleName, String description) {
		super();
		this.roleName = roleName;
		this.description = description;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	
}
