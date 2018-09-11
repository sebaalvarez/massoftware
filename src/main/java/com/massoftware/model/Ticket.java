package com.massoftware.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMaxValueIntegerAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMinValueBigDecimalAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMinValueIntegerAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldNameMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldNotVisibleCopy;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldNotVisibleInsert;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldNowTimestampForInsertUpdate;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldOptionsIntegerAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldReadOnly;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldRequiredAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldUniqueAnont;

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Ticket")
@ClassPluralLabelAnont(value = "Tickets")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[Tickets]")
public class Ticket extends EntityId implements Cloneable, Comparable<Ticket> {

	@FieldLabelAnont(value = "Código")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[TICKET]", classAttDB = Integer.class)
	private Integer codigo;

	@FieldLabelAnont(value = "Descripción")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 40)
	@FieldColumnsAnont(value = 40)
	@FieldColumnMetaDataAnont(attSize = 450)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[DESCRIPCION]", classAttDB = String.class)
	private String nombre;

	@FieldNotVisibleInsert()
	@FieldNotVisibleCopy()
	@FieldReadOnly()
	@FieldNowTimestampForInsertUpdate()
	@FieldLabelAnont(value = "Fecha actualización")
	// @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 250)
	@FieldNameMSAnont(nameAttDB = "[FECHAACTUALIZACIONSQL]", classAttDB = Timestamp.class)
	private Timestamp fecha;

	@FieldLabelAnont(value = "Cantidad por lotes")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldNameMSAnont(nameAttDB = "[CANTIDADPORLOTES]", classAttDB = Integer.class)
	private Integer cantidadPorLotes;

	@FieldLabelAnont(value = "Control denunciados")
	@FieldOptionsIntegerAnont(values = { 1, 2 }, captions = {
			"Control numeración SIEMPRE en modelo 1",
			"Control numeración en el modelo del ticket" }, defaultValue = 2, horizontal = false)
	@FieldColumnMetaDataAnont(attSize = 80)
	@FieldNameMSAnont(nameAttDB = "[CONTROLDENUNCIADO]", classAttDB = Integer.class)
	private Integer controlNumeracion;

	@FieldLabelAnont(value = "Valor máximo")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 8)
	@FieldColumnsAnont(value = 8)
	@FieldMinValueBigDecimalAnont(value = "0")
	@FieldMaxValueBigDecimalAnont(value = "99999.99")
	@FieldColumnMetaDataAnont(attSize = 120)
	@FieldAllowDecimalAnont()
	@FieldNameMSAnont(nameAttDB = "[VALORMAXIMO]", classAttDB = BigDecimal.class)
	private BigDecimal valorMaximo;

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

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public Integer getCantidadPorLotes() {
		return cantidadPorLotes;
	}

	public void setCantidadPorLotes(Integer cantidadPorLotes) {
		this.cantidadPorLotes = cantidadPorLotes;
	}

	public Integer getControlNumeracion() {
		return controlNumeracion;
	}

	public void setControlNumeracion(Integer controlNumeracion) {
		this.controlNumeracion = controlNumeracion;
	}

	public BigDecimal getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(BigDecimal valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	@Override
	public Ticket clone() throws CloneNotSupportedException {

		Ticket other = new Ticket();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());
		if (this.getFecha() != null) {
			other.setFecha((Timestamp) this.getFecha().clone());
		} else {
			other.setFecha(null);
		}
		other.setCantidadPorLotes(this.getCantidadPorLotes());
		other.setControlNumeracion(getControlNumeracion());
		if (this.getValorMaximo() != null) {
			other.setValorMaximo(new BigDecimal(this.getValorMaximo()
					.toString()));
		} else {
			other.setValorMaximo(null);
		}

		return other;
	}

	@Override
	public int compareTo(Ticket o) {

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
