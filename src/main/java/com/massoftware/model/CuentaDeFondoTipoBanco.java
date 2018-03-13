package com.massoftware.model;

import org.cendra.common.model.EntityId;

import com.massoftware.annotation.model.ClassArticleLabelInPluralAnont;
import com.massoftware.annotation.model.ClassArticleLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassPluralLabelAnont;
import com.massoftware.annotation.model.FieldLabelAnont;

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Tipo banco")
@ClassPluralLabelAnont(value = "Tipos banco")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Deposito")
// @ClassTableMSAnont(nameTableDB = "[TablaDeStock]")
public class CuentaDeFondoTipoBanco extends EntityId implements Cloneable,
		Comparable<CuentaDeFondoTipoBanco> {

	@FieldLabelAnont(value = "Tipo")
	// @FieldRequiredAnont()
	// @FieldMaxLengthAnont(value = 3)
	// @FieldColumnsAnont(value = 5)
	// @FieldMinValueIntegerAnont(value = 1)
	// @FieldMaxValueIntegerAnont(value = 255)
	// @FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	// @FieldUniqueAnont()
	// @FieldNameMSAnont(nameAttDB = "[TIPOCBTECONTROL]", classAttDB =
	// Integer.class)
	private Integer codigo;

	@FieldLabelAnont(value = "Nombre")
	// @FieldRequiredAnont()
	// @FieldMaxLengthAnont(value = 30)
	// @FieldColumnsAnont(value = 30)
	// @FieldColumnMetaDataAnont(attSize = 250)
	// @FieldUniqueAnont()
	// @FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
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
	public CuentaDeFondoTipoBanco clone() throws CloneNotSupportedException {

		CuentaDeFondoTipoBanco other = new CuentaDeFondoTipoBanco();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());

		return other;
	}

	@Override
	public int compareTo(CuentaDeFondoTipoBanco o) {

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
