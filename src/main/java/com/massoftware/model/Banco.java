package com.massoftware.model;

import java.math.BigDecimal;

import org.cendra.common.model.EntityId;

import com.massoftware.annotation.model.AllowDecimalAnont;
import com.massoftware.annotation.model.ArticleLabelInPluralAnont;
import com.massoftware.annotation.model.ArticleLabelInTheSingularAnont;
import com.massoftware.annotation.model.ColumnMetaDataAnont;
import com.massoftware.annotation.model.ColumnsAnont;
import com.massoftware.annotation.model.FormSourceAnont;
import com.massoftware.annotation.model.InputMaskAnont;
import com.massoftware.annotation.model.LabelAnont;
import com.massoftware.annotation.model.LabelInTheSingularAnont;
import com.massoftware.annotation.model.MaxLengthAnont;
import com.massoftware.annotation.model.MaxValueBigDecimalAnont;
import com.massoftware.annotation.model.MaxValueIntegerAnont;
import com.massoftware.annotation.model.MinValueBigDecimalAnont;
import com.massoftware.annotation.model.MinValueIntegerAnont;
import com.massoftware.annotation.model.PluralLabelAnont;
import com.massoftware.annotation.model.RequiredAnont;

@SuppressWarnings("serial")
@LabelInTheSingularAnont(value = "Banco")
@PluralLabelAnont(value = "Bancos")
@ArticleLabelInTheSingularAnont(value = "el")
@ArticleLabelInPluralAnont(value = "los")
@FormSourceAnont(value = "Banco")
public class Banco extends EntityId implements Cloneable, Comparable<Banco> {

	@LabelAnont(value = "Banco")
	@RequiredAnont(value = true)
	@ColumnsAnont(value = 5)
	@MaxLengthAnont(value = 3)
	@MinValueIntegerAnont(value = 0)
	@MaxValueIntegerAnont(value = 999)
	@ColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	private Integer codigo;

	@LabelAnont(value = "Nombre")
	@RequiredAnont(value = true)
	@ColumnsAnont(value = 40)
	@MaxLengthAnont(value = 40)
	@ColumnMetaDataAnont(attSize = 350)
	private String nombre;

	@LabelAnont(value = "CUIT")
	@RequiredAnont(value = true)
	@ColumnsAnont(value = 11)
	@MaxLengthAnont(value = 11)
	@MinValueBigDecimalAnont(value = "0")
	@MaxValueBigDecimalAnont(value = "99999999999")
	@AllowDecimalAnont(value = false)
	@InputMaskAnont(mask = "99-99999999-9")
	@ColumnMetaDataAnont(attSize = 100)
	private BigDecimal cuit;

	@LabelAnont(value = "Bloqueado")
	private Boolean bloqueado;

	@LabelAnont(value = "Nombre oficial")
	@RequiredAnont(value = true)
	@ColumnsAnont(value = 40)
	@MaxLengthAnont(value = 40)
	@ColumnMetaDataAnont(attSize = 350)
	private String nombreOficial;

	@LabelAnont(value = "Hoja")
	@ColumnsAnont(value = 5)
	@MaxLengthAnont(value = 3)
	@MinValueIntegerAnont(value = 0)
	@MaxValueIntegerAnont(value = 231)
	@ColumnMetaDataAnont(hidden = true)
	private Integer hoja;

	@LabelAnont(value = "Primera fila")
	@ColumnsAnont(value = 6)
	@MaxLengthAnont(value = 6)
	@MinValueIntegerAnont(value = 0)
	@MaxValueIntegerAnont(value = 999999)
	@ColumnMetaDataAnont(hidden = true)
	private Integer primeraFila;

	@LabelAnont(value = "Última fila")
	@ColumnsAnont(value = 6)
	@MaxLengthAnont(value = 6)
	@MinValueIntegerAnont(value = 0)
	@MaxValueIntegerAnont(value = 999999)
	@ColumnMetaDataAnont(hidden = true)
	private Integer uiltimaFila;

	@LabelAnont(value = "Fecha")
	@ColumnsAnont(value = 5)
	@MaxLengthAnont(value = 3)
	@ColumnMetaDataAnont(hidden = true)
	private String columnaFecha;

	@LabelAnont(value = "Descripción")
	@ColumnsAnont(value = 5)
	@MaxLengthAnont(value = 3)
	@ColumnMetaDataAnont(hidden = true)
	private String columnaDescripcion;

	@LabelAnont(value = "Referencia 1")
	@ColumnsAnont(value = 5)
	@MaxLengthAnont(value = 3)
	@ColumnMetaDataAnont(hidden = true)
	private String columnaReferencia1;

	@LabelAnont(value = "Referencia 2")
	@ColumnsAnont(value = 5)
	@MaxLengthAnont(value = 3)
	@ColumnMetaDataAnont(hidden = true)
	private String columnaReferencia2;

	@LabelAnont(value = "Importe")
	@ColumnsAnont(value = 5)
	@MaxLengthAnont(value = 3)
	@ColumnMetaDataAnont(hidden = true)
	private String columnaImporte;

	@LabelAnont(value = "Saldo")
	@ColumnsAnont(value = 5)
	@MaxLengthAnont(value = 3)
	@ColumnMetaDataAnont(hidden = true)
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
