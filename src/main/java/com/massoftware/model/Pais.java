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
import com.massoftware.annotation.model.FieldReadOnly;
import com.massoftware.annotation.model.FieldRequiredAnont;
import com.massoftware.annotation.model.FieldUniqueAnont;

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "País")
@ClassPluralLabelAnont(value = "Países")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Zona")
@ClassTableMSAnont(nameTableDB = "[Paises]")
public class Pais extends EntityId implements Cloneable, Comparable<Pais> {

	@FieldLabelAnont(value = "Nro. país")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 5)
	@FieldMaxLengthAnont(value = 3)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = 254)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[PAIS]", classAttDB = Integer.class)
	@FieldAutoMaxValueAnont()
	@FieldReadOnly()
	private Integer codigo;

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 25)
	@FieldMaxLengthAnont(value = 25)
	@FieldColumnMetaDataAnont(attSize = 250)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "Abreviatura")
	// @FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 5)
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnMetaDataAnont(attSize = 80)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[ABREVIATURA]", classAttDB = String.class)
	private String abreviatura;

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

	public String getAbreviatura() {
		abreviatura = formatValue(abreviatura);
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		abreviatura = formatValue(abreviatura);
		this.abreviatura = abreviatura;
	}

	@Override
	public Pais clone() throws CloneNotSupportedException {

		Pais other = new Pais();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());
		other.setAbreviatura(this.getAbreviatura());

		return other;
	}

	@Override
	public int compareTo(Pais other) {

		return this.getCodigo().compareTo(other.getCodigo());
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

		// if (this.abreviatura == null) {
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName() + ".abreviatura es nulo.");
		// }

		return true;
	}

}
