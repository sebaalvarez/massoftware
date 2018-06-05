package com.massoftware.model;


public class AsientoModeloItem extends EntityId implements Cloneable,
		Comparable<AsientoModeloItem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6930315341418947136L;

	private AsientoModelo asientoModelo;
	private Integer registro;
	private CuentaContable cuentaContable;

	public AsientoModelo getAsientoModelo() {
		return asientoModelo;
	}

	public void setAsientoModelo(AsientoModelo asientoModelo) {
		this.asientoModelo = asientoModelo;
	}

	public Integer getRegistro() {
		return registro;
	}

	public void setRegistro(Integer registro) {
		this.registro = registro;
	}

	public CuentaContable getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(CuentaContable cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	@Override
	public String toString() {

		if (this.getCuentaContable() != null) {
			return this.getCuentaContable().toString();
		}

		return null;

	}

	@Override
	public AsientoModeloItem clone() throws CloneNotSupportedException {
		AsientoModeloItem other = new AsientoModeloItem();

		other.setId(this.getId());
		if (this.getAsientoModelo() != null) {
			other.setAsientoModelo(this.getAsientoModelo().clone());
		} else {
			other.setAsientoModelo(null);
		}
		other.setRegistro(getRegistro());

		if (this.getCuentaContable() != null) {
			other.setCuentaContable(this.getCuentaContable().clone());
		} else {
			other.setCuentaContable(null);
		}

		return other;
	}

	public int compareTo(AsientoModeloItem o) {

		return this.getRegistro().compareTo(o.getRegistro());
	}

	public boolean validate() {

		super.validate();
		
		if (this.asientoModelo == null) {

			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".asientoModelo es nulo.");
		} else {
//			this.asientoModelo.validate(); // recusivo no hacer
		}

		if (this.cuentaContable == null) {

			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".cuentaContable es nulo.");
		} else {
			this.cuentaContable.validate();
		}

		if (this.registro == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".registro es nulo.");
		}

		return true;
	}

}
