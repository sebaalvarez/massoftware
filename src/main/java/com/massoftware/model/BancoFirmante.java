package com.massoftware.model;

import com.massoftware.frontend.ui.util.xmd.annotation.model.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.ClassPluralLabelAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.ClassTableMSAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldAutoMaxValueAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldColumnMetaDataAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldColumnsAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldLabelAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldMaxLengthAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldMaxValueIntegerAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldMinValueIntegerAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldNameMSAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldReadOnly;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldRequiredAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldUniqueAnont;

@ClassLabelInTheSingularAnont(value = "Firmante")
@ClassPluralLabelAnont(value = "Firmantes")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[BancosFirmantes]")
public class BancoFirmante extends EntityId implements
		Comparable<BancoFirmante> {

	@FieldLabelAnont(value = "Código")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 3)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = 255)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[CODIGO]", classAttDB = Integer.class)
	@FieldAutoMaxValueAnont()
	@FieldReadOnly()
	private Integer codigo;

	@FieldLabelAnont(value = "Firmante")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 40)
	@FieldColumnsAnont(value = 40)
	@FieldColumnMetaDataAnont(attSize = 450)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "Cargo")
	// @FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 20)
	@FieldColumnsAnont(value = 20)
	@FieldColumnMetaDataAnont(attSize = 200)
	// @FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[CARGO]", classAttDB = String.class)
	private String cargo;

	@FieldLabelAnont(value = "Activo")
	@FieldColumnMetaDataAnont(attSize = 80)
	@FieldNameMSAnont(nameAttDB = "[ACTIVO]", classAttDB = Boolean.class)
	private Boolean activo;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	// @Override
	// public BancoFirmante clone() throws CloneNotSupportedException {
	//
	// BancoFirmante other = new BancoFirmante();
	//
	// other.setId(this.getId());
	// other.setCodigo(this.getCodigo());
	// other.setNombre(this.getNombre());
	// other.setCargo(getCargo());
	// other.setActivo(getActivo());
	//
	// return other;
	// }

	@Override
	public int compareTo(BancoFirmante o) {

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
