package org.cendra.common.model;

public abstract class AbstractEntityErasable extends EntityId implements
		Erasable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4460664963236741243L;
	
	private Boolean erased;

	public Boolean getErased() {
		erased = this.nullIsFalse(erased);
		return erased;
	}

	public void setErased(Boolean erased) {
		erased = this.nullIsFalse(erased);
		this.erased = erased;
	}

}
