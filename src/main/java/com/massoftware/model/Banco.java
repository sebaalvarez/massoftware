package com.massoftware.model;

import java.math.BigDecimal;

import org.cendra.common.model.EntityId;

import com.massoftware.annotation.model.ClassArticleLabelInPluralAnont;
import com.massoftware.annotation.model.ClassArticleLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassFormSourceAnont;
import com.massoftware.annotation.model.ClassLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassTableMSAnont;
import com.massoftware.annotation.model.ClassPluralLabelAnont;
import com.massoftware.annotation.model.FieldAllowDecimalAnont;
import com.massoftware.annotation.model.FieldColumnMetaDataAnont;
import com.massoftware.annotation.model.FieldColumnsAnont;
import com.massoftware.annotation.model.FieldInputMaskAnont;
import com.massoftware.annotation.model.FieldLabelAnont;
import com.massoftware.annotation.model.FieldMaxLengthAnont;
import com.massoftware.annotation.model.FieldMaxValueBigDecimalAnont;
import com.massoftware.annotation.model.FieldMaxValueIntegerAnont;
import com.massoftware.annotation.model.FieldMinLengthAnont;
import com.massoftware.annotation.model.FieldMinValueBigDecimalAnont;
import com.massoftware.annotation.model.FieldMinValueIntegerAnont;
import com.massoftware.annotation.model.FieldNameMSAnont;
import com.massoftware.annotation.model.FieldRequiredAnont;
import com.massoftware.annotation.model.FieldUniqueAnont;

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Banco")
@ClassPluralLabelAnont(value = "Bancos")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
@ClassFormSourceAnont(value = "Banco")
@ClassTableMSAnont(nameTableDB = "[Bancos]")
public class Banco extends EntityId implements Cloneable, Comparable<Banco> {

	@FieldLabelAnont(value = "Banco")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 5)
	@FieldMaxLengthAnont(value = 3)
	@FieldMinValueIntegerAnont(value = 0)
	@FieldMaxValueIntegerAnont(value = 999)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[BANCO]", classAttDB = Integer.class)
	private Integer codigo;

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 40)
	@FieldMaxLengthAnont(value = 40)
	@FieldColumnMetaDataAnont(attSize = 350)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "CUIT")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 11)
	@FieldMinLengthAnont(value = 11)
	@FieldMaxLengthAnont(value = 11)
	@FieldMinValueBigDecimalAnont(value = "0")
	@FieldMaxValueBigDecimalAnont(value = "99999999999")
	@FieldAllowDecimalAnont(value = false)
	@FieldInputMaskAnont(mask = "99-99999999-9")
	@FieldColumnMetaDataAnont(attSize = 100)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[CUIT]", classAttDB = BigDecimal.class)
	private BigDecimal cuit;

	@FieldLabelAnont(value = "Bloqueado")
	@FieldNameMSAnont(nameAttDB = "[BLOQUEADO]", classAttDB = Boolean.class)
	private Boolean bloqueado;

	@FieldLabelAnont(value = "Nombre oficial")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 40)
	@FieldMaxLengthAnont(value = 40)
	@FieldColumnMetaDataAnont(attSize = 350)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRECOMPLETO]", classAttDB = String.class)
	private String nombreOficial;

	@FieldLabelAnont(value = "Hoja")
	@FieldColumnsAnont(value = 5)
	@FieldMaxLengthAnont(value = 3)
	@FieldMinValueIntegerAnont(value = 0)
	@FieldMaxValueIntegerAnont(value = 231)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[HOJA]", classAttDB = Short.class)
	private Integer hoja;

	@FieldLabelAnont(value = "Primera fila")
	@FieldColumnsAnont(value = 6)
	@FieldMaxLengthAnont(value = 6)
	@FieldMinValueIntegerAnont(value = 0)
	@FieldMaxValueIntegerAnont(value = 999999)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[PRIMERAFILA]", classAttDB = Integer.class)
	private Integer primeraFila;

	@FieldLabelAnont(value = "Última fila")
	@FieldColumnsAnont(value = 6)
	@FieldMaxLengthAnont(value = 6)
	@FieldMinValueIntegerAnont(value = 0)
	@FieldMaxValueIntegerAnont(value = 999999)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[ULTIMAFILA]", classAttDB = Integer.class)
	private Integer uiltimaFila;

	@FieldLabelAnont(value = "Fecha")
	@FieldColumnsAnont(value = 5)
	@FieldMaxLengthAnont(value = 3)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[COLUMNAFECHA]", classAttDB = String.class)
	private String columnaFecha;

	@FieldLabelAnont(value = "Descripción")
	@FieldColumnsAnont(value = 5)
	@FieldMaxLengthAnont(value = 3)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[COLUMNADESCRIPCION]", classAttDB = String.class)
	private String columnaDescripcion;

	@FieldLabelAnont(value = "Referencia 1")
	@FieldColumnsAnont(value = 5)
	@FieldMaxLengthAnont(value = 3)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[COLUMNAREFERENCIA1]", classAttDB = String.class)
	private String columnaReferencia1;

	@FieldLabelAnont(value = "Referencia 2")
	@FieldColumnsAnont(value = 5)
	@FieldMaxLengthAnont(value = 3)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[COLUMNAREFERENCIA2]", classAttDB = String.class)
	private String columnaReferencia2;

	@FieldLabelAnont(value = "Importe")
	@FieldColumnsAnont(value = 5)
	@FieldMaxLengthAnont(value = 3)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[COLUMNAIMPORTE]", classAttDB = String.class)
	private String columnaImporte;

	@FieldLabelAnont(value = "Saldo")
	@FieldColumnsAnont(value = 5)
	@FieldMaxLengthAnont(value = 3)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[COLUMNASALDO]", classAttDB = String.class)
	private String columnaSaldo;

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

	public BigDecimal getCuit() {
		if (cuit != null && cuit.equals(new BigDecimal("0"))) {
			cuit = null;
		}
		return cuit;
	}

	public void setCuit(BigDecimal cuit) {
		if (cuit != null && cuit.equals(new BigDecimal("0"))) {
			cuit = null;
		}
		this.cuit = cuit;
	}

	public Boolean getBloqueado() {
		bloqueado = this.nullIsFalse(bloqueado);
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		bloqueado = this.nullIsFalse(bloqueado);
		this.bloqueado = bloqueado;
	}

	public String getNombreOficial() {
		nombreOficial = formatValue(nombreOficial);
		return nombreOficial;
	}

	public void setNombreOficial(String nombreOficial) {
		nombreOficial = formatValue(nombreOficial);
		this.nombreOficial = nombreOficial;
	}

	public Integer getHoja() {
		return hoja;
	}

	public void setHoja(Integer hoja) {
		this.hoja = hoja;
	}

	public Integer getPrimeraFila() {
		return primeraFila;
	}

	public void setPrimeraFila(Integer primeraFila) {
		this.primeraFila = primeraFila;
	}

	public Integer getUiltimaFila() {
		return uiltimaFila;
	}

	public void setUiltimaFila(Integer uiltimaFila) {
		this.uiltimaFila = uiltimaFila;
	}

	public String getColumnaFecha() {
		columnaFecha = formatValue(columnaFecha);
		return columnaFecha;
	}

	public void setColumnaFecha(String columnaFecha) {
		columnaFecha = formatValue(columnaFecha);
		this.columnaFecha = columnaFecha;
	}

	public String getColumnaDescripcion() {
		columnaDescripcion = formatValue(columnaDescripcion);
		return columnaDescripcion;
	}

	public void setColumnaDescripcion(String columnaDescripcion) {
		columnaDescripcion = formatValue(columnaDescripcion);
		this.columnaDescripcion = columnaDescripcion;
	}

	public String getColumnaReferencia1() {
		columnaReferencia1 = formatValue(columnaReferencia1);
		return columnaReferencia1;
	}

	public void setColumnaReferencia1(String columnaReferencia1) {
		columnaReferencia1 = formatValue(columnaReferencia1);
		this.columnaReferencia1 = columnaReferencia1;
	}

	public String getColumnaReferencia2() {
		columnaReferencia2 = formatValue(columnaReferencia2);
		return columnaReferencia2;
	}

	public void setColumnaReferencia2(String columnaReferencia2) {
		columnaReferencia2 = formatValue(columnaReferencia2);
		this.columnaReferencia2 = columnaReferencia2;
	}

	public String getColumnaImporte() {
		columnaImporte = formatValue(columnaImporte);
		return columnaImporte;
	}

	public void setColumnaImporte(String columnaImporte) {
		columnaImporte = formatValue(columnaImporte);
		this.columnaImporte = columnaImporte;
	}

	public String getColumnaSaldo() {
		columnaSaldo = formatValue(columnaSaldo);
		return columnaSaldo;
	}

	public void setColumnaSaldo(String columnaSaldo) {
		columnaSaldo = formatValue(columnaSaldo);
		this.columnaSaldo = columnaSaldo;
	}

	@Override
	public Banco clone() throws CloneNotSupportedException {

		Banco other = new Banco();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());
		if (this.getCuit() != null) {
			other.setCuit(new BigDecimal(this.getCuit().toString()));
		} else {
			other.setCuit(null);
		}
		other.setBloqueado(this.getBloqueado());
		other.setNombreOficial(this.getNombreOficial());
		other.setHoja(getHoja());
		other.setPrimeraFila(getPrimeraFila());
		other.setUiltimaFila(getUiltimaFila());
		other.setColumnaFecha(getColumnaFecha());
		other.setColumnaDescripcion(getColumnaDescripcion());
		other.setColumnaReferencia1(getColumnaReferencia1());
		other.setColumnaReferencia2(getColumnaReferencia2());
		other.setColumnaImporte(getColumnaImporte());
		other.setColumnaSaldo(getColumnaSaldo());

		return other;
	}

	@Override
	public int compareTo(Banco other) {

		return this.getCodigo().compareTo(other.getCodigo());
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

		// if (this.cuit == null) {
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName() + ".cuit es nulo.");
		// }

		if (this.nombreOficial == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".nombreOficial es nulo.");
		}

		return true;
	}

}
