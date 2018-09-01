package com.massoftware.model;

import com.massoftware.frontend.util.builder.annotation.FieldUniqueAnont;

public class EntityId extends Entity implements Identifiable {

	@FieldUniqueAnont()
	private String id;

	public String getId() {
		id = this.formatValue(id);
		return id;
	}

	public void setId(String id) {
		id = this.formatValue(id);
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

	// @Override
	// public Object clone() throws CloneNotSupportedException {
	// // EntityId other = new EntityId();
	// //
	// // other.setId(this.getId());
	// //
	// // return other;
	//
	// return clone(this.getClass());
	//
	// }

	@Override
	public String toString() {

		if (this.getId() != null) {
			return this.getId();
		}

		return "";
	}

	public boolean validate() throws IllegalArgumentException {

		if (this.id == null || this.id.isEmpty()) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".id is null.");
		}

		return true;
	}

}
