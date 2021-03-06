package com.massoftware.model;

import com.massoftware.frontend.custom.windows.builder.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassPluralLabelAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassTableMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldColumnMetaDataAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldColumnsAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldLabelAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMaxLengthAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMaxValueIntegerAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMinValueIntegerAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldNameMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldRequiredAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldUniqueAnont;

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Tipo cbte. control - stock")
@ClassPluralLabelAnont(value = "Tipos cbtes. control - stock")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
//@ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[TablaDeStock]")
public class TipoCbteControl extends EntityId implements Cloneable,
		Comparable<TipoCbteControl> {


	@FieldLabelAnont(value = "Tipo cbte. control")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 3)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = 255)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[TIPOCBTECONTROL]", classAttDB = Integer.class)
	private Integer codigo;
	
	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 30)
	@FieldColumnsAnont(value = 30)
	@FieldColumnMetaDataAnont(attSize = 250)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;
	
	@FieldLabelAnont(value = "Abreviatura")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldColumnMetaDataAnont(attSize = 80)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[ABREVIATURA]", classAttDB = String.class)
	private String abreviatura;
	
	@FieldLabelAnont(value = "Columna informe")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 3)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = 255)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)	
	@FieldNameMSAnont(nameAttDB = "[COLUMNAINFORME]", classAttDB = Integer.class)
	private Integer columnaInforme;

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

	public Integer getColumnaInforme() {
		if (columnaInforme == null) {
			columnaInforme = 0;
		}
		return columnaInforme;
	}

	public void setColumnaInforme(Integer columnaInforme) {
		if (columnaInforme == null) {
			columnaInforme = 0;
		}
		this.columnaInforme = columnaInforme;
	}

	@Override
	public TipoCbteControl clone() throws CloneNotSupportedException {

		TipoCbteControl other = new TipoCbteControl();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());
		other.setAbreviatura(this.getAbreviatura());
		other.setColumnaInforme(this.getColumnaInforme());

		return other;
	}

	@Override
	public int compareTo(TipoCbteControl o) {

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

		if (this.abreviatura == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".abreviatura es nulo.");
		}

		if (this.columnaInforme == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".columnaInforme es nulo.");
		}

		return true;
	}

}
