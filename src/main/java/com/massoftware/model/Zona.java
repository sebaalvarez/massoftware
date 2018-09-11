package com.massoftware.model;

import java.math.BigDecimal;

import com.massoftware.frontend.custom.windows.builder.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassPluralLabelAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassTableMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldAllowDecimalAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldColumnMetaDataAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldColumnsAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldLabelAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMaxLengthAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMaxValueBigDecimalAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMinValueBigDecimalAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldNameMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldRequiredAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldUniqueAnont;

@ClassLabelInTheSingularAnont(value = "Zona")
@ClassPluralLabelAnont(value = "Zonas")
@ClassArticleLabelInTheSingularAnont(value = "la")
@ClassArticleLabelInPluralAnont(value = "las")
// @ClassFormSourceAnont(value = "Zona")
@ClassTableMSAnont(nameTableDB = "[Zonas]")
public class Zona extends EntityId implements Comparable<Zona> {

	@FieldLabelAnont(value = "Zona")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 5)
	@FieldMaxLengthAnont(value = 3)
	// @FieldMinValueIntegerAnont(value = 0)
	// @FieldMaxValueIntegerAnont(value = 999)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[ZONA]", classAttDB = String.class)
	private String codigo;

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 30)
	@FieldMaxLengthAnont(value = 30)
	@FieldColumnMetaDataAnont(attSize = 250)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "% Bonificación")
	// @FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueBigDecimalAnont(value = "0")
	@FieldMaxValueBigDecimalAnont(value = "99.99")
	@FieldAllowDecimalAnont()
	@FieldColumnMetaDataAnont(attSize = 120)
	@FieldNameMSAnont(nameAttDB = "[BONIFICACION]", classAttDB = BigDecimal.class)
	private BigDecimal bonificacion;

	@FieldLabelAnont(value = "% Recargo")
	// @FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueBigDecimalAnont(value = "0")
	@FieldMaxValueBigDecimalAnont(value = "99.99")
	@FieldAllowDecimalAnont()
	@FieldColumnMetaDataAnont(attSize = 120)
	@FieldNameMSAnont(nameAttDB = "[RECARGO]", classAttDB = BigDecimal.class)
	private BigDecimal recargo;

	public String getCodigo() {
		if (codigo != null) {
			codigo = codigo.toUpperCase();
		}
		return codigo;
	}

	public void setCodigo(String codigo) {
		if (codigo != null) {
			codigo = codigo.toUpperCase();
		}
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

	public BigDecimal getBonificacion() {
		if (bonificacion == null) {
			bonificacion = new BigDecimal(0);
		}
		return bonificacion;
	}

	public void setBonificacion(BigDecimal bonificacion) {
		if (bonificacion == null) {
			bonificacion = new BigDecimal(0);
		}
		this.bonificacion = bonificacion;
	}

	public BigDecimal getRecargo() {
		if (recargo == null) {
			recargo = new BigDecimal(0);
		}
		return recargo;
	}

	public void setRecargo(BigDecimal recargo) {
		if (recargo == null) {
			recargo = new BigDecimal(0);
		}
		this.recargo = recargo;
	}

	@Override
	public int compareTo(Zona other) {

		return this.getCodigo().compareTo(other.getCodigo());
	}

	@Override
	public String toString() {
		
		if (getCodigo() != null && getNombre() != null) {
			return "(" + getCodigo() + ") " + getNombre();
		} else if (getCodigo() != null && getNombre() == null) {
			return "(" + getCodigo() + ") ";
		}
		if (getCodigo() == null && getNombre() != null) {
			return getNombre();
		}

		return "";

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
