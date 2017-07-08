package org.cendra.commons.model;

public abstract class AbstractEntityErasable extends EntityId implements
		Erasable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4460664963236741243L;
	
	private Boolean erased;

	public Boolean getErased() {
		return erased;
	}

	public void setErased(Boolean erased) {
		this.erased = erased;
	}

}
