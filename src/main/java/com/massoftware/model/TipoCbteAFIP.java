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
import com.massoftware.frontend.custom.windows.builder.annotation.FieldReadOnly;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldRequiredAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldUniqueAnont;

@ClassLabelInTheSingularAnont(value = "Tipo cbte. AFIP")
@ClassPluralLabelAnont(value = "Tipos cbtes. AFIP")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[AfipTiposCbtes]")
public class TipoCbteAFIP extends EntityId implements Cloneable,
		Comparable<TipoCbteAFIP> {

	@FieldLabelAnont(value = "Tipo")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 100, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[TIPO]", classAttDB = Integer.class)
	@FieldReadOnly()
	@FieldAutoMaxValueAnont()
	private Integer codigo;

	@FieldLabelAnont(value = "Descripción")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 80)
	@FieldColumnsAnont(value = 80)
	@FieldColumnMetaDataAnont(attSize = 450)
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

	@Override
	public TipoCbteAFIP clone() throws CloneNotSupportedException {

		TipoCbteAFIP other = new TipoCbteAFIP();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());

		return other;
	}

	@Override
	public int compareTo(TipoCbteAFIP o) {

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
