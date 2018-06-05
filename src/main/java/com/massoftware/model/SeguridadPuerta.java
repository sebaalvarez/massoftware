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

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Puerta")
@ClassPluralLabelAnont(value = "Puertas")
@ClassArticleLabelInTheSingularAnont(value = "la")
@ClassArticleLabelInPluralAnont(value = "las")
//@ClassFormSourceAnont(value = "Talonario")
@ClassTableMSAnont(nameTableDB = "[SSECUR_Door]")
public class SeguridadPuerta extends EntityId implements Cloneable,
		Comparable<SeguridadPuerta> {

	@FieldLabelAnont(value = "Puerta")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldAutoMaxValueAnont()
	@FieldNameMSAnont(nameAttDB = "[NO]", classAttDB = Integer.class)
	private Integer codigo;
	
	@FieldLabelAnont(value = "Módulo")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[DGRPNO]", classAttDB = Integer.class)
	private SeguridadModulo seguridadModulo;
	
	@FieldLabelAnont(value = "ID")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 30)
	@FieldColumnsAnont(value = 30)
	@FieldColumnMetaDataAnont(attSize = 250)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[EQUATE]", classAttDB = String.class)
	private String igualacionID;
	
	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 60)
	@FieldColumnsAnont(value = 60)
	@FieldColumnMetaDataAnont(attSize = 350)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[DESCRIPTION]", classAttDB = String.class)
	private String nombre;
	
	@FieldLabelAnont(value = "Congelado")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[FREEZE]", classAttDB = Boolean.class)
	private Boolean congelado;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public SeguridadModulo getSeguridadModulo() {
		return seguridadModulo;
	}

	public void setSeguridadModulo(SeguridadModulo seguridadModulo) {
		if (seguridadModulo != null && seguridadModulo.getId() == null) {
			return;
		}
		this.seguridadModulo = seguridadModulo;
	}

	public String getIgualacionID() {
		igualacionID = formatValue(igualacionID);
		return igualacionID;
	}

	public void setIgualacionID(String igualacionID) {
		igualacionID = formatValue(igualacionID);
		this.igualacionID = igualacionID;
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

	@Override
	public SeguridadPuerta clone() throws CloneNotSupportedException {

		SeguridadPuerta other = new SeguridadPuerta();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		if (this.getSeguridadModulo() != null) {
			other.setSeguridadModulo(this.getSeguridadModulo().clone());
		} else {
			other.setSeguridadModulo(null);
		}
		other.setIgualacionID(this.getIgualacionID());
		other.setNombre(this.getNombre());
		other.setCongelado(this.getCongelado());

		return other;
	}

	@Override
	public int compareTo(SeguridadPuerta o) {

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

		if (this.igualacionID == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".igualacionID es nulo.");
		}

		if (this.nombre == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".nombre es nulo.");
		}
		
		if (this.seguridadModulo == null || this.seguridadModulo.getId() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".seguridadModulo es nulo.");
		}

		return true;
	}

}
