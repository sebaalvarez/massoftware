package com.massoftware.model;

import com.massoftware.frontend.util.builder.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.util.builder.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.util.builder.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.util.builder.annotation.ClassPluralLabelAnont;
import com.massoftware.frontend.util.builder.annotation.ClassTableMSAnont;
import com.massoftware.frontend.util.builder.annotation.FieldColumnMetaDataAnont;
import com.massoftware.frontend.util.builder.annotation.FieldColumnsAnont;
import com.massoftware.frontend.util.builder.annotation.FieldLabelAnont;
import com.massoftware.frontend.util.builder.annotation.FieldMaxLengthAnont;
import com.massoftware.frontend.util.builder.annotation.FieldMaxValueIntegerAnont;
import com.massoftware.frontend.util.builder.annotation.FieldMinValueIntegerAnont;
import com.massoftware.frontend.util.builder.annotation.FieldNameMSAnont;
import com.massoftware.frontend.util.builder.annotation.FieldRequiredAnont;
import com.massoftware.frontend.util.builder.annotation.FieldUniqueAnont;

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Rubro")
@ClassPluralLabelAnont(value = "Rubros")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[CuentasDeFondosRubro]")
public class CuentaDeFondoRubro extends EntityId implements Cloneable,
		Comparable<CuentaDeFondoRubro> {

	@FieldLabelAnont(value = "Rubro")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[RUBRO]", classAttDB = Integer.class)
	private Integer codigo;

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 30)
	@FieldColumnsAnont(value = 30)
	@FieldColumnMetaDataAnont(attSize = 450)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "Es cuenta de fondo")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[ESCUENTADEFONDO]", classAttDB = Boolean.class)
	private Boolean esCuentaDeFondo;

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

	public Boolean getEsCuentaDeFondo() {
		return esCuentaDeFondo;
	}

	public void setEsCuentaDeFondo(Boolean esCuentaDeFondo) {
		this.esCuentaDeFondo = esCuentaDeFondo;
	}

	@Override
	public CuentaDeFondoRubro clone() throws CloneNotSupportedException {

		CuentaDeFondoRubro other = new CuentaDeFondoRubro();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());
		other.setEsCuentaDeFondo(getEsCuentaDeFondo());

		return other;
	}

	@Override
	public int compareTo(CuentaDeFondoRubro o) {

		return this.getCodigo().compareTo(o.getCodigo());
	}

	@Override
	public String toString() {
		return "(" + formatCodigo() + ") " + getNombre();

	}
	
	private String formatCodigo() {
		String s = "";

		if (getCodigo() != null) {
			s = getCodigo().toString();

			if (s.length() == 1) {
				s = "000" + s;
			} else if (s.length() == 2) {
				s = "00" + s;
			} else if (s.length() == 3) {
				s = "0" + s;
			} 
		}

		return s;
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
