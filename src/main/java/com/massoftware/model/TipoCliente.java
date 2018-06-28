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
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldRequiredAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldUniqueAnont;

@ClassLabelInTheSingularAnont(value = "Tipo de cliente")
@ClassPluralLabelAnont(value = "Tipos de cliente")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Zona")
@ClassTableMSAnont(nameTableDB = "[TablaTiposClientes]")
public class TipoCliente extends EntityId implements Comparable<TipoCliente> {

	@FieldLabelAnont(value = "Número.")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 5)
	@FieldMaxLengthAnont(value = 3)
	@FieldMinValueIntegerAnont(value = 0)
	@FieldMaxValueIntegerAnont(value = 255)
	@FieldColumnMetaDataAnont(attSize = 100, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[TIPODECLIENTE]", classAttDB = Integer.class)
	@FieldAutoMaxValueAnont()
//	@FieldReadOnly()
	private Integer codigo;

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 20)
	@FieldMaxLengthAnont(value = 20)
	@FieldColumnMetaDataAnont(attSize = 250)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
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

//	@Override
//	public TipoCliente clone() throws CloneNotSupportedException {
//
//		TipoCliente other = new TipoCliente();
//
//		other.setId(this.getId());
//		other.setCodigo(this.getCodigo());
//		other.setNombre(this.getNombre());
//
//		return other;
//	}

	@Override
	public int compareTo(TipoCliente other) {

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
