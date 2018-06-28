package com.massoftware.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.massoftware.frontend.ui.util.xmd.annotation.model.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.ClassPluralLabelAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.ClassTableMSAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldAllowDecimalAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldColumnMetaDataAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldColumnsAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldLabelAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldMaxLengthAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldMaxValueBigDecimalAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldMinValueBigDecimalAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldNameMSAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldNotVisibleCopy;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldNotVisibleInsert;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldNowTimestampForInsertUpdate;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldReadOnly;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldRequiredAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldSubNameFKAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldUserForInsertUpdate;

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Cotizacion de moneda")
@ClassPluralLabelAnont(value = "Cotizaciones de monedas")
@ClassArticleLabelInTheSingularAnont(value = "la")
@ClassArticleLabelInPluralAnont(value = "las")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[MonedasCotizaciones]")
public class MonedaCotizacion extends EntityId implements Cloneable,
		Comparable<MonedaCotizacion> {

	@FieldLabelAnont(value = "Moneda")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 350)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[MONEDA]", classAttDB = Integer.class)
	private Moneda moneda;

	@FieldLabelAnont(value = "Fecha")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 150)
	@FieldNameMSAnont(nameAttDB = "[FECHASQL]", classAttDB = Date.class)
	private Date fecha;

	@FieldLabelAnont(value = "Compra")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 9)
	@FieldColumnsAnont(value = 9)
	@FieldMinValueBigDecimalAnont(value = "0")
	@FieldMaxValueBigDecimalAnont(value = "999999999")
	@FieldAllowDecimalAnont()
	@FieldColumnMetaDataAnont(attSize = 120)
	@FieldNameMSAnont(nameAttDB = "[COMPRA]", classAttDB = BigDecimal.class)
	private BigDecimal compra;

	@FieldLabelAnont(value = "Venta")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 9)
	@FieldColumnsAnont(value = 9)
	@FieldMinValueBigDecimalAnont(value = "0")
	@FieldMaxValueBigDecimalAnont(value = "999999999")
	@FieldAllowDecimalAnont()
	@FieldColumnMetaDataAnont(attSize = 120)
	@FieldNameMSAnont(nameAttDB = "[VENTA]", classAttDB = BigDecimal.class)
	private BigDecimal venta;

	@FieldNotVisibleInsert()
	@FieldNotVisibleCopy()
	@FieldReadOnly()
	@FieldNowTimestampForInsertUpdate()
	@FieldLabelAnont(value = "Ingreso")
//	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 250)
	@FieldNameMSAnont(nameAttDB = "[FECHAINGRESOSQL]", classAttDB = Timestamp.class)
	private Timestamp fechaIngreso;

	@FieldNotVisibleInsert()
	@FieldNotVisibleCopy()
	@FieldReadOnly()
	@FieldUserForInsertUpdate()
	@FieldLabelAnont(value = "Usuario")
//	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 250)
	@FieldSubNameFKAnont(value = "numero")
	@FieldNameMSAnont(nameAttDB = "[USUARIO]", classAttDB = Integer.class)
	private Usuario usuario;

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getCompra() {
		return compra;
	}

	public void setCompra(BigDecimal compra) {
		this.compra = compra;
	}

	public BigDecimal getVenta() {
		return venta;
	}

	public void setVenta(BigDecimal venta) {
		this.venta = venta;
	}

	public Timestamp getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Timestamp fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public MonedaCotizacion clone() throws CloneNotSupportedException {

		MonedaCotizacion other = new MonedaCotizacion();

		other.setId(this.getId());		
		if (this.getMoneda() != null) {
			other.setMoneda(this.getMoneda().clone());
		} else {
			other.setMoneda(null);
		}
		if (this.getFecha() != null) {
			other.setFecha((Date) this.getFecha().clone());
		} else {
			other.setFecha(null);
		}
		if (this.getCompra() != null) {
			other.setCompra(new BigDecimal(getCompra().toString()));
		} else {
			other.setCompra(null);
		}
		if (this.getVenta() != null) {
			other.setVenta(new BigDecimal(getVenta().toString()));
		} else {
			other.setVenta(null);
		}
		if (this.getFechaIngreso() != null) {
			other.setFechaIngreso((Timestamp) this.getFechaIngreso().clone());
		} else {
			other.setFechaIngreso(null);
		}		
		if (this.getUsuario() != null) {
			other.setUsuario(getUsuario());
		} else {
			other.setUsuario(null);
		}

		return other;
	}

	@Override
	public int compareTo(MonedaCotizacion o) {

		return this.getFecha().compareTo(o.getFecha());
	}

	@Override
	public String toString() {
		return "(" + getMoneda() + ") " + getFecha();

	}

	public boolean validate() throws IllegalArgumentException {

		super.validate();

//		if (this.codigo == null) {
//			throw new IllegalArgumentException(this.getClass()
//					.getCanonicalName() + ".codigo es nulo.");
//		}
//
//		if (this.nombre == null) {
//			throw new IllegalArgumentException(this.getClass()
//					.getCanonicalName() + ".nombre es nulo.");
//		}

		return true;
	}

}
