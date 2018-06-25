package com.massoftware.model;

import com.massoftware.annotation.model.ClassArticleLabelInPluralAnont;
import com.massoftware.annotation.model.ClassArticleLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassPluralLabelAnont;
import com.massoftware.annotation.model.ClassTableMSAnont;
import com.massoftware.annotation.model.FieldAutoMaxValueAnont;
import com.massoftware.annotation.model.FieldColumnMetaDataAnont;
import com.massoftware.annotation.model.FieldColumnsAnont;
import com.massoftware.annotation.model.FieldLabelAnont;
import com.massoftware.annotation.model.FieldMaxLengthAnont;
import com.massoftware.annotation.model.FieldMaxValueIntegerAnont;
import com.massoftware.annotation.model.FieldMinValueIntegerAnont;
import com.massoftware.annotation.model.FieldNameMSAnont;
import com.massoftware.annotation.model.FieldRequiredAnont;
import com.massoftware.annotation.model.FieldSubNameFKAnont;
import com.massoftware.annotation.model.FieldUniqueAnont;

@ClassLabelInTheSingularAnont(value = "Punto de equilibrio")
@ClassPluralLabelAnont(value = "Puntos de equilibrio")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[PuntoDeEquilibrio]")
public class PuntoDeEquilibrio extends EntityId implements
		Comparable<PuntoDeEquilibrio> {

	@FieldLabelAnont(value = "Ejercicio")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "ejercicio")
	// @FieldNameMSAnont(nameAttDB = "[CUENTA]", classAttDB = String.class)
	// @FieldCBBox(attName = "ejercicio")
	private EjercicioContable ejercicioContable;

	@FieldLabelAnont(value = "Número")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 100, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[PUNTODEEQUILIBRIO]", classAttDB = Integer.class)
	@FieldAutoMaxValueAnont()
	private Integer puntoDeEquilibrio;

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 30)
	@FieldColumnsAnont(value = 30)
	@FieldColumnMetaDataAnont(attSize = 300)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "Tipo")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "puntoDeEquilibrioTipo")
	private PuntoDeEquilibrioTipo puntoDeEquilibrioTipo;

	// public PuntoDeEquilibrio(Object[] row) {
	// // setterByArray(row);
	// }

	public Integer getPuntoDeEquilibrio() {
		return puntoDeEquilibrio;
	}

	public void setPuntoDeEquilibrio(Integer puntoDeEquilibrio) {
		this.puntoDeEquilibrio = puntoDeEquilibrio;
	}

	public String getNombre() {
		nombre = formatValue(nombre);
		return nombre;
	}

	public void setNombre(String nombre) {
		nombre = formatValue(nombre);
		this.nombre = nombre;
	}

	public PuntoDeEquilibrioTipo getPuntoDeEquilibrioTipo() {
		return puntoDeEquilibrioTipo;
	}

	public void setPuntoDeEquilibrioTipo(
			PuntoDeEquilibrioTipo puntoDeEquilibrioTipo) {
		if (puntoDeEquilibrioTipo != null
				&& puntoDeEquilibrioTipo.getId() != null) {
			this.puntoDeEquilibrioTipo = puntoDeEquilibrioTipo;
		}

	}

	public EjercicioContable getEjercicioContable() {
		return ejercicioContable;
	}

	public void setEjercicioContable(EjercicioContable ejercicioContable) {
		if (ejercicioContable != null && ejercicioContable.getId() != null) {
			this.ejercicioContable = ejercicioContable;
		}

	}

	// @Override
	// public PuntoDeEquilibrio clone() throws CloneNotSupportedException {
	//
	// PuntoDeEquilibrio other = new PuntoDeEquilibrio();
	//
	// other.setId(this.getId());
	// if (this.getEjercicioContable() != null) {
	// other.setEjercicioContable(this.getEjercicioContable().clone());
	// } else {
	// other.setEjercicioContable(null);
	// }
	// other.setNombre(this.getNombre());
	// other.setPuntoDeEquilibrio(this.getPuntoDeEquilibrio());
	// if (this.getPuntoDeEquilibrioTipo() != null) {
	// other.setPuntoDeEquilibrioTipo(this.getPuntoDeEquilibrioTipo()
	// .clone());
	// } else {
	// other.setPuntoDeEquilibrioTipo(null);
	// }
	//
	// return other;
	// }

	public int compareTo(PuntoDeEquilibrio o) {

		return this.getPuntoDeEquilibrio().compareTo(o.getPuntoDeEquilibrio());

	}

	@Override
	public String toString() {
		if (this.getEjercicioContable() != null) {
			return this.getPuntoDeEquilibrio() + " - " + this.getNombre()
					+ " - " + this.getEjercicioContable();
		}
		return this.getPuntoDeEquilibrio() + " - " + this.getNombre();
	}

	public boolean validate() {

		super.validate();

		if (this.ejercicioContable == null) {

			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".ejercicioContable es nulo.");
		} else {
			this.ejercicioContable.validate();
		}

		if (this.puntoDeEquilibrio == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".puntoDeEquilibrio es nulo.");
		}

		if (this.nombre == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".nombre es nulo.");
		}

		if (this.puntoDeEquilibrioTipo == null) {

			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".puntoDeEquilibrioTipo es nulo.");
		} else {
			this.puntoDeEquilibrioTipo.validate();
		}

		return true;
	}

}
