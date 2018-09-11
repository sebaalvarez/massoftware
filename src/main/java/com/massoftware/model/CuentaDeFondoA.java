package com.massoftware.model;

import com.massoftware.frontend.custom.windows.builder.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassFormSourceAnont;
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
import com.massoftware.frontend.custom.windows.builder.annotation.FieldSubNameFKAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldUniqueAnont;

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Cuenta de fondo")
@ClassPluralLabelAnont(value = "Cuentas de fondo")
@ClassArticleLabelInTheSingularAnont(value = "la")
@ClassArticleLabelInPluralAnont(value = "las")
@ClassFormSourceAnont(value = "CuentaDeFondo")
@ClassTableMSAnont(nameTableDB = "[CuentasDeFondos]")
public class CuentaDeFondoA extends EntityId implements Cloneable,
		Comparable<CuentaDeFondoA> {

	@FieldLabelAnont(value = "Cuenta")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 10)
	@FieldColumnsAnont(value = 10)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Integer.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 100, pidFilteringStart = true, simpleStringTraslateFilterMode = "STARTS_WITCH")
	@FieldUniqueAnont()
	@FieldAutoMaxValueAnont()
	@FieldNameMSAnont(nameAttDB = "[CUENTA]", classAttDB = Integer.class)
	private Integer codigo;

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 40)
	@FieldColumnsAnont(value = 40)
	@FieldColumnMetaDataAnont(attSize = 350)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "Obsoleta")
	@FieldColumnMetaDataAnont(attSize = 80)
	@FieldNameMSAnont(nameAttDB = "[OBSOLETA]", classAttDB = Boolean.class)
	private Boolean obsoleta;
	
	@FieldLabelAnont(value = "Rubro - Grupo")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	// @FieldColumnMetaDataAnont(attSize = 450)
	// @FieldSubNameFKAnont(value = "codigo")// no va funcionar por que es doble
	// codigo la PK
	// @FieldNameMSAnont(nameAttDB = "[GRUPO]", classAttDB = Integer.class) //
	// no va funcionar por que es doble codigo la PK
	private CuentaDeFondoGrupo cuentaDeFondoGrupo;

	@FieldLabelAnont(value = "Tipo")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 150)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[TIPO]", classAttDB = Integer.class)
	private CuentaDeFondoTipo cuentaDeFondoTipo;

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

	public Boolean getObsoleta() {
		obsoleta = this.nullIsFalse(obsoleta);
		return obsoleta;
	}

	public void setObsoleta(Boolean obsoleta) {
		obsoleta = this.nullIsFalse(obsoleta);
		this.obsoleta = obsoleta;
	}
	
	public CuentaDeFondoGrupo getCuentaDeFondoGrupo() {
		return cuentaDeFondoGrupo;
	}

	public void setCuentaDeFondoGrupo(CuentaDeFondoGrupo cuentaDeFondoGrupo) {
		this.cuentaDeFondoGrupo = cuentaDeFondoGrupo;
	}

	public CuentaDeFondoTipo getCuentaDeFondoTipo() {
		return cuentaDeFondoTipo;
	}

	public void setCuentaDeFondoTipo(CuentaDeFondoTipo cuentaDeFondoTipo) {
		this.cuentaDeFondoTipo = cuentaDeFondoTipo;
	}


	@Override
	public CuentaDeFondoA clone() throws CloneNotSupportedException {

		CuentaDeFondoA other = new CuentaDeFondoA();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());
		other.setObsoleta(getObsoleta());
		if (this.getCuentaDeFondoGrupo() != null) {
			other.setCuentaDeFondoGrupo(getCuentaDeFondoGrupo().clone());
		} else {
			other.setCuentaDeFondoGrupo(null);
		}
		if (this.getCuentaDeFondoTipo() != null) {
			other.setCuentaDeFondoTipo(getCuentaDeFondoTipo().clone());
		} else {
			other.setCuentaDeFondoTipo(null);
		}

		return other;
	}

	@Override
	public int compareTo(CuentaDeFondoA o) {

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
