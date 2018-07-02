package com.massoftware.model;

public class Usuario extends EntityId implements Comparable<Usuario> {

	private Integer numero;
	private String nombre;
	private EjercicioContable ejercicioContable;

	public Usuario() {

	}

	// public Usuario(Object[] row) {
	// setterByArray(row);
	// }

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer nnumero) {
		this.numero = nnumero;
	}

	public String getNombre() {
		nombre = this.formatValue(nombre);
		return nombre;
	}

	public void setNombre(String nombre) {
		nombre = this.formatValue(nombre);
		this.nombre = nombre;
	}

	public EjercicioContable getEjercicioContable() {
		return ejercicioContable;
	}

	public void setEjercicioContable(EjercicioContable ejercicioContable) {
		this.ejercicioContable = ejercicioContable;
	}

	// public void setterByArray(Object[] row) {
	//
	// int column = 0;
	//
	// if (row[column] != null) {
	// this.setNumero((Integer) row[column]);
	// }
	//
	// column++;
	//
	// if (row[column] != null) {
	// this.setNombre((String) row[column]);
	// }
	//
	// EjercicioContable ejercicioContable = new EjercicioContable(
	// ArrayUtils.subarray(row, 2, 8));
	//
	// if (ejercicioContable.getEjercicio() != null) {
	// this.setEjercicioContable(ejercicioContable);
	// }
	//
	// }

	// public Usuario clone() throws CloneNotSupportedException {
	// Usuario other = (Usuario) super.clone();
	//
	// other.setNumero(numero);
	// other.setNombre(nombre);
	// if (this.getEjercicioContable() != null) {
	// other.setEjercicioContable(getEjercicioContable().clone());
	// } else {
	// other.setEjercicioContable(null);
	// }
	//
	// return other;
	// }

	@Override
	public int compareTo(Usuario o) {
		if (this.getNombre() != null && o != null && o.getNombre() != null) {
			return this.getNombre().toLowerCase()
					.compareTo(((Usuario) o).getNombre().toLowerCase());
		}

		return 0;
	}

	@Override
	public String toString() {

		String s = super.toString();

		if (this.getNombre() != null && s != null
				&& s.trim().isEmpty() == false) {
			return (s + " (" + this.getNombre() + ")").trim();
		} else if (this.getNombre() != null
				&& (s == null || s.trim().isEmpty() == true)) {
			return this.getNombre().trim();
		} else if (this.getNombre() == null && s != null
				&& s.trim().isEmpty() == false) {
			return s;
		}

		return "";
	}

	public boolean validate() throws IllegalArgumentException {

		super.validate();

		if (this.numero == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".numero es nulo.");
		}

		if (this.nombre == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".nombre es nulo.");
		}

		return true;
	}

}
