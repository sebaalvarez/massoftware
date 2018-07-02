package com.massoftware.model;

import com.massoftware.frontend.ui.util.xmd.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassPluralLabelAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassTableMSAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldCBBox;
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

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Jurisdicción convenio multilateral")
@ClassPluralLabelAnont(value = "Jurisdicciones de convenio multilateral")
@ClassArticleLabelInTheSingularAnont(value = "la")
@ClassArticleLabelInPluralAnont(value = "las")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[ConvenioMultilateralJurisdicciones]")
public class JurisdiccionConvenioMultilateral extends EntityId implements
		Cloneable, Comparable<JurisdiccionConvenioMultilateral> {

	@FieldLabelAnont(value = "Jurisdicción")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[JURISDICCION]", classAttDB = Integer.class)
	private Integer codigo;

	@FieldLabelAnont(value = "Denominación")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 30)
	@FieldColumnsAnont(value = 30)
	@FieldColumnMetaDataAnont(attSize = 450)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "Cuenta de fondo")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 250)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[CUENTAFONDO]", classAttDB = String.class)
	@FieldCBBox(attName = "codigo")
	private CuentaDeFondo cuentaDeFondo;

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

	public CuentaDeFondo getCuentaDeFondo() {
		return cuentaDeFondo;
	}

	public void setCuentaDeFondo(CuentaDeFondo cuentaDeFondo) {
		this.cuentaDeFondo = cuentaDeFondo;
	}

	@Override
	public JurisdiccionConvenioMultilateral clone()
			throws CloneNotSupportedException {

		JurisdiccionConvenioMultilateral other = new JurisdiccionConvenioMultilateral();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());
		if (this.getCuentaDeFondo() != null) {
			other.setCuentaDeFondo(getCuentaDeFondo());
		} else {
			other.setCuentaDeFondo(null);
		}

		return other;
	}

	@Override
	public int compareTo(JurisdiccionConvenioMultilateral o) {

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
