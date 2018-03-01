package com.massoftware.model;

import org.cendra.common.model.EntityId;

public class ModeloCbteFondoItem extends EntityId implements Cloneable,
		Comparable<ModeloCbteFondoItem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1780990144309548763L;

	private ModeloCbteFondo modeloCbteFondo;
	// private CuentaFondo cuentaFondo; // 777 Falta hacer la clase
	private ModeloCbteFondoItemConcepto modeloCbteFondoItemConcepto;
	private Boolean ajustaSaldo;
	private Boolean permiteEliminar;

	public ModeloCbteFondo getModeloCbteFondo() {
		return modeloCbteFondo;
	}

	public void setModeloCbteFondo(ModeloCbteFondo modeloCbteFondo) {
		this.modeloCbteFondo = modeloCbteFondo;
	}

	public ModeloCbteFondoItemConcepto getModeloCbteFondoItemConcepto() {
		return modeloCbteFondoItemConcepto;
	}

	public void setModeloCbteFondoItemConcepto(
			ModeloCbteFondoItemConcepto modeloCbteFondoItemConcepto) {
		this.modeloCbteFondoItemConcepto = modeloCbteFondoItemConcepto;
	}

	public Boolean getAjustaSaldo() {
		ajustaSaldo = this.nullIsFalse(ajustaSaldo);
		return ajustaSaldo;
	}

	public void setAjustaSaldo(Boolean ajustaSaldo) {
		ajustaSaldo = this.nullIsFalse(ajustaSaldo);
		this.ajustaSaldo = ajustaSaldo;
	}

	public Boolean getPermiteEliminar() {
		permiteEliminar = this.nullIsFalse(permiteEliminar);
		return permiteEliminar;
	}

	public void setPermiteEliminar(Boolean permiteEliminar) {
		permiteEliminar = this.nullIsFalse(permiteEliminar);
		this.permiteEliminar = permiteEliminar;
	}

	@Override
	public ModeloCbteFondoItem clone() throws CloneNotSupportedException {

		ModeloCbteFondoItem other = new ModeloCbteFondoItem();

		other.setId(this.getId());
		if (this.getModeloCbteFondo() != null) {
			other.setModeloCbteFondo(this.getModeloCbteFondo());
		} else {
			other.setModeloCbteFondo(null);
		}

		// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		// CuentaFondo

		if (this.getModeloCbteFondoItemConcepto() != null) {
			other.setModeloCbteFondoItemConcepto(this
					.getModeloCbteFondoItemConcepto());
		} else {
			other.setModeloCbteFondoItemConcepto(null);
		}
		other.setAjustaSaldo(this.getAjustaSaldo());
		other.setPermiteEliminar(this.getPermiteEliminar());

		return other;
	}

	@Override
	public int compareTo(ModeloCbteFondoItem o) {

		// return this.getCodigo().compareTo(o.getCodigo()); Devolver
		// comparacion por numero de cuenta !!!!!!!!!!!!!!!!!
		return -1;
	}

	@Override
	public String toString() {
		return "(" + this.getModeloCbteFondo() + ") " + this.getAjustaSaldo();

	}

	public boolean validate() throws IllegalArgumentException {

		super.validate();

		if (this.modeloCbteFondo == null
				|| this.modeloCbteFondo.getId() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".modeloCbteFondo es nulo.");
		}

		// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		// CuentaFondo

		if (this.modeloCbteFondoItemConcepto == null
				|| this.modeloCbteFondoItemConcepto.getId() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName()
					+ ".modeloCbteFondoItemConcepto es nulo.");
		}

		return true;
	}

}
