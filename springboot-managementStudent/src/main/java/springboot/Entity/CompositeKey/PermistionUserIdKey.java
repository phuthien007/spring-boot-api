package springboot.Entity.CompositeKey;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class PermistionUserIdKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long per_id;
	private Long user_id;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((per_id == null) ? 0 : per_id.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermistionUserIdKey other = (PermistionUserIdKey) obj;
		if (per_id == null) {
			if (other.per_id != null)
				return false;
		} else if (!per_id.equals(other.per_id))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}
	/**
	 * @return the per_id
	 */
	public Long getPer_id() {
		return per_id;
	}
	/**
	 * @param per_id the per_id to set
	 */
	public void setPer_id(Long per_id) {
		this.per_id = per_id;
	}
	/**
	 * @return the user_id
	 */
	public Long getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	/**
	 * @param per_id
	 * @param user_id
	 */
	public PermistionUserIdKey(Long per_id, Long user_id) {
		super();
		this.per_id = per_id;
		this.user_id = user_id;
	}
	/**
	 * 
	 */
	public PermistionUserIdKey() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
