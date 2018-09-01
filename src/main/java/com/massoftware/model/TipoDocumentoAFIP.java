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
import com.massoftware.frontend.util.builder.annotation.FieldRequiredAnont;
import com.massoftware.frontend.util.builder.annotation.FieldUniqueAnont;

@ClassLabelInTheSingularAnont(value = "Tipo doc. AFIP")
@ClassPluralLabelAnont(value = "Tipos docs. AFIP")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[AfipTiposDocumentos]")
public class TipoDocumentoAFIP extends EntityId implements
		Comparable<TipoDocumentoAFIP> {

	@FieldLabelAnont(value = "Tipo")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 100, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[TIPO]", classAttDB = Integer.class)
	// @FieldReadOnly()
	@FieldAutoMaxValueAnont()
	private Integer codigo;

	@FieldLabelAnont(value = "Descripción")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 40)
	@FieldColumnsAnont(value = 40)
	@FieldColumnMetaDataAnont(attSize = 350)
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

	// @Override
	// public TipoDocumentoAFIP clone() throws CloneNotSupportedException {
	//
	// TipoDocumentoAFIP other = new TipoDocumentoAFIP();
	//
	// other.setId(this.getId());
	// other.setCodigo(this.getCodigo());
	// other.setNombre(this.getNombre());
	//
	// return other;
	// }

	@Override
	public int compareTo(TipoDocumentoAFIP o) {

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
