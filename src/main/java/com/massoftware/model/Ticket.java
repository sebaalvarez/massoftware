package com.massoftware.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.cendra.common.model.EntityId;

import com.massoftware.annotation.model.ClassArticleLabelInPluralAnont;
import com.massoftware.annotation.model.ClassArticleLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassPluralLabelAnont;
import com.massoftware.annotation.model.ClassTableMSAnont;
import com.massoftware.annotation.model.FieldAllowDecimalAnont;
import com.massoftware.annotation.model.FieldColumnMetaDataAnont;
import com.massoftware.annotation.model.FieldColumnsAnont;
import com.massoftware.annotation.model.FieldLabelAnont;
import com.massoftware.annotation.model.FieldMaxLengthAnont;
import com.massoftware.annotation.model.FieldMaxValueBigDecimalAnont;
import com.massoftware.annotation.model.FieldMaxValueIntegerAnont;
import com.massoftware.annotation.model.FieldMinValueBigDecimalAnont;
import com.massoftware.annotation.model.FieldMinValueIntegerAnont;
import com.massoftware.annotation.model.FieldNameMSAnont;
import com.massoftware.annotation.model.FieldNotVisibleCopy;
import com.massoftware.annotation.model.FieldNotVisibleInsert;
import com.massoftware.annotation.model.FieldNowTimestampForInsertUpdate;
import com.massoftware.annotation.model.FieldOptionsIntegerAnont;
import com.massoftware.annotation.model.FieldReadOnly;
import com.massoftware.annotation.model.FieldRequiredAnont;
import com.massoftware.annotation.model.FieldUniqueAnont;

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
