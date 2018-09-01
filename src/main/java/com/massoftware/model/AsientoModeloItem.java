package com.massoftware.model;

import com.massoftware.frontend.util.builder.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.util.builder.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.util.builder.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.util.builder.annotation.ClassPluralLabelAnont;
import com.massoftware.frontend.util.builder.annotation.ClassTableMSAnont;
import com.massoftware.frontend.util.builder.annotation.FieldAutoMaxValueAnont;
import com.massoftware.frontend.util.builder.annotation.FieldCBBox;
import com.massoftware.frontend.util.builder.annotation.FieldColumnMetaDataAnont;
import com.massoftware.frontend.util.builder.annotation.FieldColumnsAnont;
import com.massoftware.frontend.util.builder.annotation.FieldLabelAnont;
import com.massoftware.frontend.util.builder.annotation.FieldMaxLengthAnont;
import com.massoftware.frontend.util.builder.annotation.FieldMaxValueIntegerAnont;
import com.massoftware.frontend.util.builder.annotation.FieldMinValueIntegerAnont;
import com.massoftware.frontend.util.builder.annotation.FieldNameMSAnont;
import com.massoftware.frontend.util.builder.annotation.FieldReadOnly;
import com.massoftware.frontend.util.builder.annotation.FieldRequiredAnont;
import com.massoftware.frontend.util.builder.annotation.FieldSubNameFKAnont;
import com.massoftware.frontend.util.builder.annotation.FieldUniqueAnont;

@ClassLabelInTheSingularAnont(value = "Asiento modelo")
@ClassPluralLabelAnont(value = "Asientos modelo")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Talonario")
@ClassTableMSAnont(nameTableDB = "[AsientosModelosMov]")
public class AsientoModeloItem extends EntityId implements
		Comparable<AsientoModeloItem> {

	@FieldLabelAnont(value = "Asiento modelo")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "numero")
	@FieldNameMSAnont(nameAttDB = "[ASIENTOMODELO]", classAttDB = Integer.class)
	@FieldReadOnly()
	private AsientoModelo asientoModelo;	
	
	@FieldLabelAnont(value = "Registro")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldAutoMaxValueAnont()
	@FieldNameMSAnont(nameAttDB = "[REGISTRO]", classAttDB = Integer.class)
	@FieldReadOnly()
	private Integer registro;
	
//	@FieldLabelAnont(value = "Cuenta contable")
//	@FieldRequiredAnont()
//	@FieldColumnMetaDataAnont(attSize = 550)
//	@FieldSubNameFKAnont(value = "cuentaContable")
//	@FieldNameMSAnont(nameAttDB = "[CUENTACONTABLE]", classAttDB = String.class)
	//-----------------------------------------------
	@FieldLabelAnont(value = "Cuenta contable")
	 @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 550)
	@FieldSubNameFKAnont(value = "cuentaContable")
	@FieldNameMSAnont(nameAttDB = "[CUENTACONTABLE]", classAttDB = String.class)
	@FieldCBBox(attName = "cuentaContable")	
	private CuentaContable cuentaContable;

	public AsientoModelo getAsientoModelo() {
		return asientoModelo;
	}

	public void setAsientoModelo(AsientoModelo asientoModelo) {
		if (asientoModelo != null && asientoModelo.getId() == null) {
			return;
		}			
		this.asientoModelo = asientoModelo;
	}

	public Integer getRegistro() {
		return registro;
	}

	public void setRegistro(Integer registro) {
		this.registro = registro;
	}

	public CuentaContable getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(CuentaContable cuentaContable) {
		if (cuentaContable != null && cuentaContable.getId() == null) {
			return;
		}
		this.cuentaContable = cuentaContable;
	}
	
	private EjercicioContable _ejercicioContable;
	
	public void _setEjercicioContable(EjercicioContable ejercicioContable){
		_ejercicioContable = ejercicioContable;
	}
	
	public EjercicioContable _getEjercicioContable(){
		return _ejercicioContable;
	}

	@Override
	public String toString() {

		if (this.getCuentaContable() != null) {
			return this.getCuentaContable().toString();
		}

		return null;

	}

	// @Override
	// public AsientoModeloItem clone() throws CloneNotSupportedException {
	// AsientoModeloItem other = new AsientoModeloItem();
	//
	// other.setId(this.getId());
	// if (this.getAsientoModelo() != null) {
	// other.setAsientoModelo(this.getAsientoModelo().clone());
	// } else {
	// other.setAsientoModelo(null);
	// }
	// other.setRegistro(getRegistro());
	//
	// if (this.getCuentaContable() != null) {
	// other.setCuentaContable(this.getCuentaContable().clone());
	// } else {
	// other.setCuentaContable(null);
	// }
	//
	// return other;
	// }

	public int compareTo(AsientoModeloItem o) {

		return this.getRegistro().compareTo(o.getRegistro());
	}

	public boolean validate() {

		super.validate();

		if (this.asientoModelo == null) {

			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".asientoModelo es nulo.");
		} else {
			// this.asientoModelo.validate(); // recusivo no hacer
		}

		if (this.cuentaContable == null) {

			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".cuentaContable es nulo.");
		} else {
			this.cuentaContable.validate();
		}

		if (this.registro == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".registro es nulo.");
		}

		return true;
	}

}
