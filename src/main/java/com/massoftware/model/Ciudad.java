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

@ClassLabelInTheSingularAnont(value = "Ciudad")
@ClassPluralLabelAnont(value = "Ciudades")
@ClassArticleLabelInTheSingularAnont(value = "la")
@ClassArticleLabelInPluralAnont(value = "las")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[Ciudades]")
public class Ciudad extends EntityId implements Comparable<Ciudad> {

	@FieldLabelAnont(value = "Provincia")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "Provincia")
	// @FieldNameMSAnont(nameAttDB = "[CUENTA]", classAttDB = String.class)
	// @FieldCBBox(attName = "ejercicio")
	private Provincia provincia;

	@FieldLabelAnont(value = "Ciudad")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 120, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[CIUDAD]", classAttDB = Integer.class)
	@FieldAutoMaxValueAnont()
	private Integer codigo;

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 35)
	@FieldColumnsAnont(value = 35)
	@FieldColumnMetaDataAnont(attSize = 300)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "Departamento")
	@FieldMaxLengthAnont(value = 35)
	@FieldColumnsAnont(value = 35)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[DEPARTAMENTO]", classAttDB = String.class)
	private String departamento;

	@FieldLabelAnont(value = "Ciudad AFIP")
	@FieldMaxLengthAnont(value = 4)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 0)
	@FieldMaxValueIntegerAnont(value = 9999)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[CODIGO]", classAttDB = Integer.class)
	private Integer codigoAfip;

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

	public String getDepartamento() {
		departamento = formatValue(departamento);
		return departamento;
	}

	public void setDepartamento(String departamento) {
		departamento = formatValue(departamento);
		this.departamento = departamento;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		if (provincia != null && provincia.getId() == null) {
			return;
		}
		this.provincia = provincia;
	}

	public Integer getCodigoAfip() {
		return codigoAfip;
	}

	public void setCodigoAfip(Integer codigoAfip) {
		this.codigoAfip = codigoAfip;
	}

	public int compareTo(Ciudad o) {

		return this.getCodigo().compareTo(o.getCodigo());
	}

	@Override
	public String toString() {
//		if (this.getProvincia() != null) {
//			return this.getProvincia() + " - (" + this.getCodigo() + ") "
//					+ this.getNombre();
//		}
		return "(" + this.getCodigo() + ") " + this.getCodigo();
	}

	public boolean validate() {

		super.validate();

		if (this.provincia == null) {

			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".pais es nulo.");
		} else {
			this.provincia.validate();
		}

		if (this.codigo == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".codigo es nulo.");
		}

		return true;
	}

}
