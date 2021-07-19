package springboot.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import springboot.Entity.CompositeKey.PermistionRoleIdKey;

//
@Entity
@Table(name = "authorization_each_role")
//@IdClass(PermistionRoleIdKey.class)
public class AuthorizationEachRoleEntity {

//
//	
	@EmbeddedId
	private PermistionRoleIdKey id;
	
	@ManyToOne( cascade = CascadeType.ALL)
	@MapsId("permistionId")
	@JoinColumn(name = "per_id", referencedColumnName = "id")
	private PermistionEntity permistion;

	@ManyToOne(cascade = CascadeType.ALL)
	@MapsId("roleId")
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private RoleEntity role;

	@Column(name = "description")
	private String description;

	/**
	 * @return the id
	 */
	public PermistionRoleIdKey getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(PermistionRoleIdKey id) {
		this.id = id;
	}

	/**
	 * @return the permistion
	 */
	public PermistionEntity getPermistion() {
		return permistion;
	}

	/**
	 * @param permistion the permistion to set
	 */
	public void setPermistion(PermistionEntity permistion) {
		this.permistion = permistion;
	}

	/**
	 * @return the role
	 */
	public RoleEntity getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(RoleEntity role) {
		this.role = role;
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
	 * @param permistion
	 * @param role
	 * @param description
	 */
	public AuthorizationEachRoleEntity(PermistionRoleIdKey id, PermistionEntity permistion, RoleEntity role,
			String description) {
		super();
		this.id = id;
		this.permistion = permistion;
		this.role = role;
		this.description = description;
	}

	/**
	 * 
	 */
	public AuthorizationEachRoleEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
