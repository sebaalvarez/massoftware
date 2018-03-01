package com.massoftware.model;

import java.util.ArrayList;
import java.util.List;

import org.cendra.common.model.EntityId;

public class ModeloCbteFondo extends EntityId implements Cloneable,
		Comparable<ModeloCbteFondo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8679616935580319783L;

	private Integer codigo;
	private String nombre;
	private List<ModeloCbteFondoItem> cuentas = new ArrayList<ModeloCbteFondoItem>();

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		nombre = formatValue(nombre);
		return nombre;
	}

	public void setNombre(String nombre) {
		nombre = formatValue(nombre);
		this.nombre = nombre;
	}

	@SuppressWarnings("unchecked")
	public List<ModeloCbteFondoItem> getCuentas() {
		cuentas = formatValues(cuentas);
		return cuentas;
	}

	@SuppressWarnings("unchecked")
	public void setCuentas(List<ModeloCbteFondoItem> cuentas) {
		cuentas = formatValues(cuentas);				
		this.cuentas = cuentas;
	}

	public boolean addCuenta(ModeloCbteFondoItem cuenta) {
		if(cuenta != null && cuenta.getId() == null){
			return false;
		}
		return cuentas.add(cuenta);
	}

	@Override
	public ModeloCbteFondo clone() throws CloneNotSupportedException {

		ModeloCbteFondo other = new ModeloCbteFondo();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());
		if (this.getCuentas() != null) {
			for (ModeloCbteFondoItem item : this.getCuentas()) {
				other.addCuenta(item.clone());
			}
		}

		return other;
	}

	@Override
	public int compareTo(ModeloCbteFondo o) {

		return this.getCodigo().compareTo(o.getCodigo());
	}

	@Override
	public String toString() {
		return "(" + getCodigo() + ") " + getNombre();

	}

	public boolean validate() throws IllegalArgumentException {

		super.validate();

		if (this.codigo == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".codigo es nulo.");
		}

		if (this.nombre == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".nombre es nulo.");
		}

		return true;
	}

}
