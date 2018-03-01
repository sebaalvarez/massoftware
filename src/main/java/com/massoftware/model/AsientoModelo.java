package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;

import org.cendra.common.model.EntityId;

public class AsientoModelo extends EntityId implements Cloneable,
		Comparable<AsientoModelo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1712132788611396268L;

	private EjercicioContable ejercicioContable;
	private Integer numero;
	private String denominacion;
	private List<AsientoModeloItem> cuentasContables = new ArrayList<AsientoModeloItem>();

	public EjercicioContable getEjercicioContable() {
		return ejercicioContable;
	}

	public void setEjercicioContable(EjercicioContable ejercicioContable) {
		this.ejercicioContable = ejercicioContable;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getDenominacion() {
		denominacion = formatValue(denominacion);
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		denominacion = formatValue(denominacion);
		this.denominacion = denominacion;
	}

	@SuppressWarnings("unchecked")
	public List<AsientoModeloItem> getCuentasContables() {
		cuentasContables = formatValues(cuentasContables);
		return cuentasContables;
	}

	@SuppressWarnings("unchecked")
	public void setCuentasContables(List<AsientoModeloItem> cuentasContables) {
		cuentasContables = formatValues(cuentasContables);
		this.cuentasContables = cuentasContables;
	}

	public boolean addCuentaContable(AsientoModeloItem e) {
		return cuentasContables.add(e);
	}

	@Override
	public String toString() {

		return this.getNumero() + " - " + this.getDenominacion();

	}

	@Override
	public AsientoModelo clone() throws CloneNotSupportedException {
		AsientoModelo other = new AsientoModelo();

		other.setId(this.getId());
		if (this.getEjercicioContable() != null) {
			other.setEjercicioContable(this.getEjercicioContable().clone());
		} else {
			other.setEjercicioContable(null);
		}
		other.setNumero(getNumero());
		other.setDenominacion(this.getDenominacion());
		if (this.getCuentasContables() != null) {
			for (AsientoModeloItem item : this.getCuentasContables()) {
				other.addCuentaContable(item.clone());
			}
		}

		return other;
	}

	public int compareTo(AsientoModelo o) {

		return this.getNumero().compareTo(o.getNumero());
	}

	public boolean validate() {

		super.validate();

		if (this.getEjercicioContable() == null) {

			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".ejercicioContable es nulo.");
		} else {
			this.getEjercicioContable().validate();
		}

		if (this.getNumero() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".numero es nulo.");
		}

		if (this.getNumero() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".numero es nulo.");
		}

		if (this.getDenominacion() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".denominacion es nulo.");
		}

		if (this.getCuentasContables() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".cuentasContables es nulo.");
		} else if (this.getCuentasContables().size() == 0) {
//			throw new IllegalArgumentException(this.getClass()
//					.getCanonicalName() + ".cuentasContables.size() es 0.");
		} else if (this.getCuentasContables().size() > 0) {
			for (AsientoModeloItem item : cuentasContables) {
				item.validate();
			}
		}

		return true;
	}

}
