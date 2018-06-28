package com.massoftware.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.massoftware.frontend.ui.util.xmd.annotation.model.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.ClassPluralLabelAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.ClassTableMSAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldAllowDecimalAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldAutoMaxValueAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldColumnMetaDataAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldColumnsAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldLabelAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldMaxLengthAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldMaxValueBigDecimalAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldMaxValueIntegerAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldMinValueBigDecimalAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldMinValueIntegerAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldNameMSAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldReadOnly;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldRequiredAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldSubNameFKAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldUniqueAnont;

@ClassLabelInTheSingularAnont(value = "Moneda")
@ClassPluralLabelAnont(value = "Monedas")
@ClassArticleLabelInTheSingularAnont(value = "la")
@ClassArticleLabelInPluralAnont(value = "las")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[Monedas]")
public class Moneda extends EntityId implements Comparable<Moneda> {
	
	

	public Moneda() {
		super();
		setCotizacion(new BigDecimal("1.0000"));
		 setFecha(new Date(System.currentTimeMillis()));
		
	}

	@FieldLabelAnont(value = "Moneda")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[MONEDA]", classAttDB = Integer.class)
	@FieldAutoMaxValueAnont()
	private Integer codigo;

	@FieldLabelAnont(value = "Descripción")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 30)
	@FieldColumnsAnont(value = 30)
	@FieldColumnMetaDataAnont(attSize = 450)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[DESCRIPCION]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "Abreviatura")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldColumnMetaDataAnont(attSize = 80)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[ABREVIATURA]", classAttDB = String.class)
	private String abreviatura;

	@FieldLabelAnont(value = "Cotizacion")
	@FieldMaxLengthAnont(value = 9)
	@FieldColumnsAnont(value = 9)
	@FieldMinValueBigDecimalAnont(value = "0")
	@FieldMaxValueBigDecimalAnont(value = "999999999")
	@FieldAllowDecimalAnont()
	@FieldColumnMetaDataAnont(attSize = 120)
	@FieldNameMSAnont(nameAttDB = "[COTIZACION]", classAttDB = BigDecimal.class)
	@FieldReadOnly()
	private BigDecimal cotizacion;

	@FieldLabelAnont(value = "Fecha")
	@FieldColumnMetaDataAnont(attSize = 80)
	@FieldNameMSAnont(nameAttDB = "[FECHASQL]", classAttDB = Date.class)
	@FieldReadOnly()
	private Date fecha;

	@FieldLabelAnont(value = "Control de actualizacion")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[CONTROLDEACTUALIZACION]", classAttDB = Boolean.class)
	private Boolean controlDeActualizacion;

	@FieldLabelAnont(value = "Moneda AFIP")
	// @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[MONEDAAFIP]", classAttDB = String.class)
	private MonedaAFIP monedaAFIP;

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
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public BigDecimal getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(BigDecimal cotizacion) {
		this.cotizacion = cotizacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Boolean getControlDeActualizacion() {
		return controlDeActualizacion;
	}

	public void setControlDeActualizacion(Boolean controlDeActualizacion) {
		this.controlDeActualizacion = controlDeActualizacion;
	}

	public MonedaAFIP getMonedaAFIP() {
		return monedaAFIP;
	}

	public void setMonedaAFIP(MonedaAFIP monedaAFIP) {
		this.monedaAFIP = monedaAFIP;
	}

//	@Override
//	public Moneda clone() throws CloneNotSupportedException {
//
//		Moneda other = new Moneda();
//
//		other.setId(this.getId());
//		other.setCodigo(this.getCodigo());
//		other.setNombre(this.getNombre());
//		other.setAbreviatura(getAbreviatura());
//		if (this.getCotizacion() != null) {
//			other.setCotizacion(new BigDecimal(getCotizacion().toString()));
//		} else {
//			other.setCotizacion(null);
//		}
//		if (this.getFecha() != null) {
//			other.setFecha((Date) this.getFecha().clone());
//		} else {
//			other.setFecha(null);
//		}
//		other.setControlDeActualizacion(getControlDeActualizacion());
//		if (this.getMonedaAFIP() != null) {
//			other.setMonedaAFIP(getMonedaAFIP());
//		} else {
//			other.setMonedaAFIP(null);
//		}
//
//		return other;
//	}

	@Override
	public int compareTo(Moneda o) {

		return this.getCodigo().compareTo(o.getCodigo());
	}

	@Override
	public String toString() {
		String fechaString = "";

		if (getFecha() != null) {
			String format = "dd/MM/yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			fechaString = sdf.format(getFecha());
		}

		return "(" + getCodigo() + ") " + getNombre() + " ["
				+ this.getCotizacion() + " " + this.getAbreviatura() + " "
				+ fechaString + "]";

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
