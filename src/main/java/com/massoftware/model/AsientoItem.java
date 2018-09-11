package com.massoftware.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.massoftware.frontend.custom.windows.builder.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassPluralLabelAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassTableMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldAllowDecimalAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldAutoMaxValueAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldCBBox;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldColumnMetaDataAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldColumnsAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldLabelAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMaxLengthAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMaxValueBigDecimalAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMaxValueIntegerAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMinValueBigDecimalAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMinValueIntegerAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldNameMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldReadOnly;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldRequiredAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldSubNameFKAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldUniqueAnont;

@ClassLabelInTheSingularAnont(value = "Asiento contable")
@ClassPluralLabelAnont(value = "Asientos contables")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Talonario")
@ClassTableMSAnont(nameTableDB = "[MovContabilidad]")
public class AsientoItem extends EntityId implements Comparable<AsientoItem> {

	@FieldLabelAnont(value = "Asiento")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	// @FieldSubNameFKAnont(value = "numero")
	// @FieldNameMSAnont(nameAttDB = "[ASIENTOMODELO]", classAttDB =
	// Integer.class)
	@FieldReadOnly()
	private Asiento asiento;

	@FieldLabelAnont(value = "Orden")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldUniqueAnont()
	@FieldAutoMaxValueAnont()
	@FieldNameMSAnont(nameAttDB = "[ORDEN]", classAttDB = Integer.class)
	@FieldReadOnly()
	private Integer orden;

	@FieldLabelAnont(value = "Registro")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Integer.MAX_VALUE)
	@FieldColumnMetaDataAnont(hidden = true)
	// @FieldUniqueAnont()
	@FieldAutoMaxValueAnont()
	@FieldNameMSAnont(nameAttDB = "[NROREGISTRO]", classAttDB = Integer.class)
	@FieldReadOnly()
	private Integer registro;

	@FieldLabelAnont(value = "Fecha")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[FECHASQL]", classAttDB = Timestamp.class)
	// @FieldTimestamp()
	// @FieldUniqueAnont()
	private Date fecha;

	@FieldLabelAnont(value = "Cuenta contable")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 210, pidFilteringStart = true)
	@FieldSubNameFKAnont(value = "cuentaContable")
	@FieldNameMSAnont(nameAttDB = "[CUENTACONTABLE]", classAttDB = String.class)
	@FieldCBBox(attName = "cuentaContable")
	private CuentaContable cuentaContable;

	@FieldLabelAnont(value = "Debe")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 9)
	@FieldColumnsAnont(value = 9)
	@FieldMinValueBigDecimalAnont(value = "0")
	@FieldMaxValueBigDecimalAnont(value = "999999999")
	@FieldAllowDecimalAnont()
	@FieldColumnMetaDataAnont(attSize = 100)
	@FieldNameMSAnont(nameAttDB = "[DEBE]", classAttDB = BigDecimal.class)
	private BigDecimal debe;

	@FieldLabelAnont(value = "Haber")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 9)
	@FieldColumnsAnont(value = 9)
	@FieldMinValueBigDecimalAnont(value = "0")
	@FieldMaxValueBigDecimalAnont(value = "999999999")
	@FieldAllowDecimalAnont()
	@FieldColumnMetaDataAnont(attSize = 100)
	@FieldNameMSAnont(nameAttDB = "[HABER]", classAttDB = BigDecimal.class)
	private BigDecimal haber;

	@FieldLabelAnont(value = "Detalle")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 60)
	@FieldColumnsAnont(value = 45)
//	@FieldColumnMetaDataAnont(hidden = true)
	@FieldColumnMetaDataAnont(attSize = 150)
	// @FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[DETALLE]", classAttDB = String.class)
	private String detalle;

	public Asiento getAsiento() {
		return asiento;
	}

	public void setAsiento(Asiento asiento) {
		if (asiento != null && asiento.getId() == null) {
			return;
		}
		this.asiento = asiento;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getRegistro() {
		return registro;
	}

	public void setRegistro(Integer registro) {
		this.registro = registro;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public BigDecimal getDebe() {
		return debe;
	}

	public void setDebe(BigDecimal debe) {
		this.debe = debe;
	}

	public BigDecimal getHaber() {
		return haber;
	}

	public void setHaber(BigDecimal haber) {
		this.haber = haber;
	}

	public String getDetalle() {
		detalle = formatValue(detalle);
		return detalle;
	}

	public void setDetalle(String detalle) {
		detalle = formatValue(detalle);
		this.detalle = detalle;
	}

	private EjercicioContable _ejercicioContable;

	public void _setEjercicioContable(EjercicioContable ejercicioContable) {
		_ejercicioContable = ejercicioContable;
	}

	public EjercicioContable _getEjercicioContable() {
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

	public int compareTo(AsientoItem o) {

		return this.getRegistro().compareTo(o.getRegistro());
	}

	public boolean validate() {

		super.validate();

		if (this.asiento == null) {

			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".asiento es nulo.");
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
