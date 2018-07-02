package com.massoftware.model;

import com.massoftware.frontend.ui.util.xmd.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassPluralLabelAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassTableMSAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldAutoMaxValueAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldColumnMetaDataAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldColumnsAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldLabelAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldMaxLengthAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldMaxValueIntegerAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldMinValueIntegerAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldNameMSAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldRequiredAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldSubNameFKAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldUniqueAnont;

@ClassLabelInTheSingularAnont(value = "Provincia")
@ClassPluralLabelAnont(value = "Provincias")
@ClassArticleLabelInTheSingularAnont(value = "la")
@ClassArticleLabelInPluralAnont(value = "las")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[Provincias]")
public class Provincia extends EntityId implements Comparable<Provincia> {

	@FieldLabelAnont(value = "País")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "pais")
	// @FieldNameMSAnont(nameAttDB = "[CUENTA]", classAttDB = String.class)
	// @FieldCBBox(attName = "ejercicio")
	private Pais pais;

	@FieldLabelAnont(value = "Provincia")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = 255)
	@FieldColumnMetaDataAnont(attSize = 100, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[PROVINCIA]", classAttDB = Integer.class)
	@FieldAutoMaxValueAnont()
	private Integer codigo;

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 25)
	@FieldColumnsAnont(value = 25)
	@FieldColumnMetaDataAnont(attSize = 300)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "Abreviatura")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldColumnMetaDataAnont(attSize = 120)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[ABREVIATURA]", classAttDB = String.class)
	private String abreviatura;

	@FieldLabelAnont(value = "Provincia AFIP")
	@FieldMaxLengthAnont(value = 2)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 0)
	@FieldMaxValueIntegerAnont(value = 99)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[NROPROVINCIAAFIP]", classAttDB = Integer.class)
	private Integer codigoAfip;

	@FieldLabelAnont(value = "Provincia Ing. Brutos ")
	@FieldMaxLengthAnont(value = 2)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 0)
	@FieldMaxValueIntegerAnont(value = 99)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[NROPROVINCIAINGBRUTOS]", classAttDB = Integer.class)
	private Integer codigoIngBrutos;

	@FieldLabelAnont(value = "Provincia RENATEA")
	@FieldMaxLengthAnont(value = 3)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 0)
	@FieldMaxValueIntegerAnont(value = 231)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[NROPROVINCIARENATEA]", classAttDB = Integer.class)
	private Integer codigoRenatea;

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

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		if (pais != null && pais.getId() == null) {
			return;
		}
		this.pais = pais;
	}

	public Integer getCodigoAfip() {
		return codigoAfip;
	}

	public void setCodigoAfip(Integer codigoAfip) {
		this.codigoAfip = codigoAfip;
	}

	public Integer getCodigoIngBrutos() {
		return codigoIngBrutos;
	}

	public void setCodigoIngBrutos(Integer codigoIngBrutos) {
		this.codigoIngBrutos = codigoIngBrutos;
	}

	public Integer getCodigoRenatea() {
		return codigoRenatea;
	}

	public void setCodigoRenatea(Integer codigoRenatea) {
		this.codigoRenatea = codigoRenatea;
	}

	// @Override
	// public CentroDeCostoContable clone() throws CloneNotSupportedException {
	// CentroDeCostoContable other = new CentroDeCostoContable();
	//
	// other.setId(this.getId());
	// other.setAbreviatura(this.getAbreviatura());
	// other.setNumero(this.getNumero());
	// if (this.getEjercicioContable() != null) {
	// other.setEjercicioContable(this.getEjercicioContable().clone());
	// } else {
	// other.setEjercicioContable(null);
	// }
	// other.setNombre(this.getNombre());
	//
	// return other;
	// }

	public int compareTo(Provincia o) {

		return this.getCodigo().compareTo(o.getCodigo());
	}

	@Override
	public String toString() {
//		if (this.getPais() != null) {
//			return this.getPais() + " - (" + this.getCodigo() + ") "
//					+ this.getNombre();
//		}
		return "(" + this.getCodigo() + ") " + this.getNombre();
	}

	public boolean validate() {

		super.validate();

		if (this.pais == null) {

			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".pais es nulo.");
		} else {
			this.pais.validate();
		}

		if (this.codigo == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".codigo es nulo.");
		}

		return true;
	}

}
