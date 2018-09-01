package com.massoftware.model;

import com.massoftware.frontend.util.builder.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.util.builder.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.util.builder.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.util.builder.annotation.ClassPluralLabelAnont;
import com.massoftware.frontend.util.builder.annotation.ClassTableMSAnont;
import com.massoftware.frontend.util.builder.annotation.FieldAutoMaxValueAnont;
import com.massoftware.frontend.util.builder.annotation.FieldColumnMetaDataAnont;
import com.massoftware.frontend.util.builder.annotation.FieldColumnsAnont;
import com.massoftware.frontend.util.builder.annotation.FieldLabelAnont;
import com.massoftware.frontend.util.builder.annotation.FieldMaxLengthAnont;
import com.massoftware.frontend.util.builder.annotation.FieldMaxValueIntegerAnont;
import com.massoftware.frontend.util.builder.annotation.FieldMinValueIntegerAnont;
import com.massoftware.frontend.util.builder.annotation.FieldNameMSAnont;
import com.massoftware.frontend.util.builder.annotation.FieldReadOnly;
import com.massoftware.frontend.util.builder.annotation.FieldRequiredAnont;
import com.massoftware.frontend.util.builder.annotation.FieldUniqueAnont;

@ClassLabelInTheSingularAnont(value = "Módulo")
@ClassPluralLabelAnont(value = "Módulos")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Talonario")
@ClassTableMSAnont(nameTableDB = "[SSECUR_DoorGroup]")
public class SeguridadModulo extends EntityId implements
		Comparable<SeguridadModulo> {

	@FieldLabelAnont(value = "Módulo")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 10)
	@FieldColumnsAnont(value = 10)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Integer.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 100, pidFilteringStart = true, simpleStringTraslateFilterMode = "STARTS_WITCH")
	@FieldUniqueAnont()
	@FieldAutoMaxValueAnont()
	@FieldNameMSAnont(nameAttDB = "[NO]", classAttDB = Integer.class)
	@FieldReadOnly()
	private Integer codigo;

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 30)
	@FieldColumnsAnont(value = 30)
	@FieldColumnMetaDataAnont(attSize = 250)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NAME]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "Congelado")
	@FieldColumnMetaDataAnont(attSize = 80)
	@FieldNameMSAnont(nameAttDB = "[FREEZE]", classAttDB = Boolean.class)
	private Boolean congelado;

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

	public Boolean getCongelado() {
		congelado = this.nullIsFalse(congelado);
		return congelado;
	}

	public void setCongelado(Boolean congelado) {
		congelado = this.nullIsFalse(congelado);
		this.congelado = congelado;
	}

//	@Override
//	public SeguridadModulo clone() throws CloneNotSupportedException {
//
//		SeguridadModulo other = new SeguridadModulo();
//
//		other.setId(this.getId());
//		other.setCodigo(this.getCodigo());
//		other.setNombre(this.getNombre());
//		other.setCongelado(this.getCongelado());
//
//		return other;
//	}

	@Override
	public int compareTo(SeguridadModulo o) {

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
