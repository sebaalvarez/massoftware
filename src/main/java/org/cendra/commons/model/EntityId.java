package org.cendra.commons.model;

import java.io.Serializable;

public class EntityId extends Entity implements Identifiable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1395536691801454116L;

	private String id;

	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

	public void setByArray(Object[] row) {

		int column = 0;

		if (row[column] != null) {
			this.setId((String) row[column]);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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

		EntityId other = (EntityId) obj;

		if (this.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!this.getId().equals(other.getId()))
			return false;

		return true;
	}

	@Override
	public EntityId clone() throws CloneNotSupportedException {
		EntityId other = new EntityId();

		other.setId(this.getId());

		return other;
	}

	@Override
	public String toString() {

		if (this.getId() != null) {
			return this.getId();
		}

		return "";
	}

	protected String formatValue(String value) {
		if (value != null) {
			value = value.trim();
		}

		if (value != null && value.isEmpty()) {
			value = null;
		}

		return value;
	}

}
