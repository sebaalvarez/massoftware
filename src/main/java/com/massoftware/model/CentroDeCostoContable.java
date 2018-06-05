package com.massoftware.model;

import org.cendra.common.model.EntityId;

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

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Centro de costo contable")
@ClassPluralLabelAnont(value = "Centros de costos contables")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[CentrosDeCostoContable]")
public class CentroDeCostoContable extends EntityId implements Cloneable,
		Comparable<CentroDeCostoContable> {

	@FieldLabelAnont(value = "Ejercicio")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "ejercicio")
//	@FieldNameMSAnont(nameAttDB = "[CUENTA]", classAttDB = String.class)
//	@FieldCBBox(attName = "ejercicio")	
	private EjercicioContable ejercicioContable;
	
	@FieldLabelAnont(value = "C.C")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 100, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[CENTRODECOSTOCONTABLE]", classAttDB = Integer.class)
	@FieldAutoMaxValueAnont()
	private Integer numero;
	
	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 30)
	@FieldColumnsAnont(value = 30)
	@FieldColumnMetaDataAnont(attSize = 300)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;
	
	@FieldLabelAnont(value = "Abreviatura")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 12)
	@FieldColumnsAnont(value = 12)
	@FieldColumnMetaDataAnont(attSize = 120)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[ABREVIATURA]", classAttDB = String.class)
	private String abreviatura;

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getNombre() {
		nombre = formatValue(nombre);
		return nombre;
	}

	public void setNombre(String nombre) {
		nombre = formatValue(nombre);
		this.nombre = nombre;
	}

	public String getAbreviatura() {
		abreviatura = formatValue(abreviatura);
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		abreviatura = formatValue(abreviatura);
		this.abreviatura = abreviatura;
	}

	public EjercicioContable getEjercicioContable() {
		return ejercicioContable;
	}

	public void setEjercicioContable(EjercicioContable ejercicioContable) {
		if (ejercicioContable != null && ejercicioContable.getId() == null) {
			return;
		}
		this.ejercicioContable = ejercicioContable;
	}

	@Override
	public CentroDeCostoContable clone() throws CloneNotSupportedException {
		CentroDeCostoContable other = new CentroDeCostoContable();

		other.setId(this.getId());
		other.setAbreviatura(this.getAbreviatura());
		other.setNumero(this.getNumero());
		if (this.getEjercicioContable() != null) {
			other.setEjercicioContable(this.getEjercicioContable().clone());
		} else {
			other.setEjercicioContable(null);
		}
		other.setNombre(this.getNombre());

		return other;
	}

	public int compareTo(CentroDeCostoContable o) {

		return this.getNumero().compareTo(o.getNumero());
	}

	@Override
	public String toString() {
		if (this.getEjercicioContable() != null) {
			return this.getNumero() + " - " + this.getNombre() + " - "
					+ this.getEjercicioContable();
		}
		return this.getNumero() + " - " + this.getNombre();
	}

	public boolean validate() {

		super.validate();

		if (this.ejercicioContable == null) {

			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".ejercicioContable es nulo.");
		} else {
			this.ejercicioContable.validate();
		}

		if (this.numero == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".numero es nulo.");
		}

		return true;
	}

}
