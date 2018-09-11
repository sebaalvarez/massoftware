package com.massoftware.model;

import com.massoftware.frontend.custom.windows.builder.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassPluralLabelAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassTableMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldAutoMaxValueAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldCBBox;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldColumnMetaDataAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldColumnsAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldLabelAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMaxLengthAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMaxValueIntegerAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMinValueIntegerAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldNameMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldRequiredAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldSubNameFKAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldUniqueAnont;

@ClassLabelInTheSingularAnont(value = "Caja")
@ClassPluralLabelAnont(value = "Cajas")
@ClassArticleLabelInTheSingularAnont(value = "la")
@ClassArticleLabelInPluralAnont(value = "las")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[Cajas]")
public class Caja extends EntityId implements Cloneable, Comparable<Caja> {

	@FieldLabelAnont(value = "Nro. caja")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 3)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = 255)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[CAJA]", classAttDB = Integer.class)
	@FieldAutoMaxValueAnont()
	private Integer codigo;

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 20)
	@FieldColumnsAnont(value = 20)
	@FieldColumnMetaDataAnont(attSize = 450)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "Puerta")
	// @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[DOORNOPERMISO]", classAttDB = Integer.class)
	@FieldCBBox(attName = "codigo")
	private SeguridadPuerta seguridadPuerta;

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

	public SeguridadPuerta getSeguridadPuerta() {
		return seguridadPuerta;
	}

	public void setSeguridadPuerta(SeguridadPuerta seguridadPuerta) {
		if (seguridadPuerta != null && seguridadPuerta.getId() == null) {
			return;
		}
		this.seguridadPuerta = seguridadPuerta;
	}

	// @Override
	// public Caja clone() throws CloneNotSupportedException {
	//
	// Caja other = new Caja();
	//
	// other.setId(this.getId());
	// other.setCodigo(this.getCodigo());
	// other.setNombre(this.getNombre());
	// if (this.getSeguridadPuerta() != null) {
	// other.setSeguridadPuerta(getSeguridadPuerta().clone());
	// } else {
	// other.setSeguridadPuerta(null);
	// }
	//
	// return other;
	// }

	@Override
	public int compareTo(Caja o) {

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
