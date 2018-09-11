package com.massoftware.model;

import com.massoftware.frontend.custom.windows.builder.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassPluralLabelAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassTableMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldAutoMaxValueAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldColumnMetaDataAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldColumnsAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldLabelAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMaxLengthAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMaxValueIntegerAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMinValueIntegerAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldNameMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldRequiredAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldUniqueAnont;

@ClassLabelInTheSingularAnont(value = "Código convenio multilateral")
@ClassPluralLabelAnont(value = "Códigos convenios multilaterales")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Zona")
@ClassTableMSAnont(nameTableDB = "[ActividadesCM]")
public class CodigoConvenioMultilateral extends EntityId implements
		Comparable<CodigoConvenioMultilateral> {

	@FieldLabelAnont(value = "Número")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 9)
	@FieldMaxLengthAnont(value = 9)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Integer.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 120, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[CODIGOCM]", classAttDB = Integer.class)
	@FieldAutoMaxValueAnont()
	private Integer codigo;

	@FieldLabelAnont(value = "Código convenio")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 10)
	@FieldMaxLengthAnont(value = 10)
	@FieldColumnMetaDataAnont(attSize = 120)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[CODIGOCONVENIO]", classAttDB = String.class)
	private String codigoConvenio;

	@FieldLabelAnont(value = "Descripción")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 80)
	@FieldMaxLengthAnont(value = 150)
	@FieldColumnMetaDataAnont(attSize = 250)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[DESCRIPCION]", classAttDB = String.class)
	private String nombre;

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

	public String getCodigoConvenio() {
		codigoConvenio = formatValue(codigoConvenio);
		return codigoConvenio;
	}

	public void setCodigoConvenio(String codigoConvenio) {
		codigoConvenio = formatValue(codigoConvenio);
		this.codigoConvenio = codigoConvenio;
	}

//	@Override
//	public CodigoConvenioMultilateral clone() throws CloneNotSupportedException {
//
//		CodigoConvenioMultilateral other = new CodigoConvenioMultilateral();
//
//		other.setId(this.getId());
//		other.setCodigo(this.getCodigo());
//		other.setNombre(this.getNombre());
//		other.setCodigoConvenio(this.getCodigoConvenio());
//
//		return other;
//	}

	@Override
	public int compareTo(CodigoConvenioMultilateral other) {

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

		if (this.codigoConvenio == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".codigoConvenio es nulo.");
		}

		return true;
	}

}
