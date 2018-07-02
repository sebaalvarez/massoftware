package com.massoftware.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.massoftware.frontend.ui.util.xmd.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassPluralLabelAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassTableMSAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldAllowDecimalAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldColumnMetaDataAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldColumnsAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldLabelAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldMaxLengthAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldMaxValueBigDecimalAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldMinValueBigDecimalAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldNameMSAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldNotVisibleCopy;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldNotVisibleInsert;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldNowTimestampForInsertUpdate;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldReadOnly;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldRequiredAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldSubNameFKAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldTimestamp;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldUniqueAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldUserForInsertUpdate;

@ClassLabelInTheSingularAnont(value = "Cotizacion de moneda")
@ClassPluralLabelAnont(value = "Cotizaciones de monedas")
@ClassArticleLabelInTheSingularAnont(value = "la")
@ClassArticleLabelInPluralAnont(value = "las")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[MonedasCotizaciones]")
public class MonedaCotizacion extends EntityId implements
		Comparable<MonedaCotizacion> {

	@FieldLabelAnont(value = "Moneda")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[MONEDA]", classAttDB = Integer.class)
	private Moneda moneda;

	@FieldLabelAnont(value = "Fecha")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 150, pidFilteringStart = true, ascOrderByStart = false)
	@FieldNameMSAnont(nameAttDB = "[FECHASQL]", classAttDB = Timestamp.class)
	@FieldTimestamp()
	@FieldUniqueAnont()
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
	// @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 250)
	@FieldNameMSAnont(nameAttDB = "[FECHAINGRESOSQL]", classAttDB = Timestamp.class)
	private Timestamp fechaIngreso;

	@FieldNotVisibleInsert()
	@FieldNotVisibleCopy()
	@FieldReadOnly()
	@FieldUserForInsertUpdate()
	@FieldLabelAnont(value = "Usuario")
	// @FieldRequiredAnont()
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

	// @Override
	// public MonedaCotizacion clone() throws CloneNotSupportedException {
	//
	// MonedaCotizacion other = new MonedaCotizacion();
	//
	// other.setId(this.getId());
	// if (this.getMoneda() != null) {
	// other.setMoneda(this.getMoneda().clone());
	// } else {
	// other.setMoneda(null);
	// }
	// if (this.getFecha() != null) {
	// other.setFecha((Date) this.getFecha().clone());
	// } else {
	// other.setFecha(null);
	// }
	// if (this.getCompra() != null) {
	// other.setCompra(new BigDecimal(getCompra().toString()));
	// } else {
	// other.setCompra(null);
	// }
	// if (this.getVenta() != null) {
	// other.setVenta(new BigDecimal(getVenta().toString()));
	// } else {
	// other.setVenta(null);
	// }
	// if (this.getFechaIngreso() != null) {
	// other.setFechaIngreso((Timestamp) this.getFechaIngreso().clone());
	// } else {
	// other.setFechaIngreso(null);
	// }
	// if (this.getUsuario() != null) {
	// other.setUsuario(getUsuario());
	// } else {
	// other.setUsuario(null);
	// }
	//
	// return other;
	// }

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

		// if (this.codigo == null) {
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName() + ".codigo es nulo.");
		// }
		//
		// if (this.nombre == null) {
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName() + ".nombre es nulo.");
		// }

		return true;
	}

}
