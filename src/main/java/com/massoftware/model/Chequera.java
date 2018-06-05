package com.massoftware.model;

import com.massoftware.annotation.model.ClassArticleLabelInPluralAnont;
import com.massoftware.annotation.model.ClassArticleLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassPluralLabelAnont;
import com.massoftware.annotation.model.ClassTableMSAnont;
import com.massoftware.annotation.model.FieldCBBox;
import com.massoftware.annotation.model.FieldColumnMetaDataAnont;
import com.massoftware.annotation.model.FieldColumnsAnont;
import com.massoftware.annotation.model.FieldLabelAnont;
import com.massoftware.annotation.model.FieldMaxLengthAnont;
import com.massoftware.annotation.model.FieldMaxValueIntegerAnont;
import com.massoftware.annotation.model.FieldMinValueIntegerAnont;
import com.massoftware.annotation.model.FieldNameMSAnont;
import com.massoftware.annotation.model.FieldRequiredAnont;
import com.massoftware.annotation.model.FieldUniqueAnont;

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Chequera")
@ClassPluralLabelAnont(value = "Chequeras")
@ClassArticleLabelInTheSingularAnont(value = "la")
@ClassArticleLabelInPluralAnont(value = "las")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[Chequeras]")
public class Chequera extends EntityId implements Cloneable,
		Comparable<Chequera> {

	@FieldLabelAnont(value = "Cuenta de fondo")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
//	@FieldSubNameFKAnont(value = "codigo")
//	@FieldNameMSAnont(nameAttDB = "[CUENTA]", classAttDB = String.class)
	@FieldCBBox(attName = "codigo")
	private CuentaDeFondoA cuentaDeFondo;

	@FieldLabelAnont(value = "Chequera")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 100, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[CHEQUERA]", classAttDB = Integer.class)
	private Integer codigo;

	@FieldLabelAnont(value = "Primer número")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 10)
	@FieldColumnsAnont(value = 10)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Integer.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 150)
	// @FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[PRIMERNRO]", classAttDB = Integer.class)
	private Integer primerNumero;

	@FieldLabelAnont(value = "Último número")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 10)
	@FieldColumnsAnont(value = 10)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Integer.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 150)
	// @FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[ULTIMONRO]", classAttDB = Integer.class)
	private Integer ultimoNumero;

	@FieldLabelAnont(value = "Próximo número")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 10)
	@FieldColumnsAnont(value = 10)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Integer.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 150)
	// @FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[PROXIMONRO]", classAttDB = Integer.class)
	private Integer proximoNumero;

	@FieldLabelAnont(value = "Bloqueado")
	@FieldColumnMetaDataAnont(attSize = 80)
	@FieldNameMSAnont(nameAttDB = "[BLOQUEADO]", classAttDB = Boolean.class)
	private Boolean bloqueado;

	@FieldLabelAnont(value = "Impresión diferida")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[IMPRESIONDIFERIDA]", classAttDB = Boolean.class)
	private Boolean impresionDiferida;

	@FieldLabelAnont(value = "Destino impresión")
	// @FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 40)
	@FieldColumnsAnont(value = 40)
	@FieldColumnMetaDataAnont(hidden = true)
	// @FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[DESTINOIMPRESION]", classAttDB = String.class)
	private String destinoImpresion;

	@FieldLabelAnont(value = "Formato")
	// @FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 30)
	@FieldColumnsAnont(value = 30)
	@FieldColumnMetaDataAnont(hidden = true)
	// @FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[FORMATO]", classAttDB = String.class)
	private String formato;

	public CuentaDeFondoA getCuentaDeFondo() {
		return cuentaDeFondo;
	}

	public void setCuentaDeFondo(CuentaDeFondoA cuentaDeFondo) {
		if (cuentaDeFondo != null && cuentaDeFondo.getId() == null) {
			return;
		}
		this.cuentaDeFondo = cuentaDeFondo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getPrimerNumero() {
		return primerNumero;
	}

	public void setPrimerNumero(Integer primerNumero) {
		this.primerNumero = primerNumero;
	}

	public Integer getUltimoNumero() {
		return ultimoNumero;
	}

	public void setUltimoNumero(Integer ultimoNumero) {
		this.ultimoNumero = ultimoNumero;
	}

	public Integer getProximoNumero() {
		return proximoNumero;
	}

	public void setProximoNumero(Integer proximoNumero) {
		this.proximoNumero = proximoNumero;
	}

	public Boolean getBloqueado() {
		bloqueado = this.nullIsFalse(bloqueado);
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		bloqueado = this.nullIsFalse(bloqueado);
		this.bloqueado = bloqueado;
	}

	public Boolean getImpresionDiferida() {
		impresionDiferida = this.nullIsFalse(impresionDiferida);
		return impresionDiferida;
	}

	public void setImpresionDiferida(Boolean impresionDiferida) {
		impresionDiferida = this.nullIsFalse(impresionDiferida);
		this.impresionDiferida = impresionDiferida;
	}

	public String getDestinoImpresion() {
		destinoImpresion = this.formatValue(destinoImpresion);
		return destinoImpresion;
	}

	public void setDestinoImpresion(String destinoImpresion) {
		destinoImpresion = this.formatValue(destinoImpresion);
		this.destinoImpresion = destinoImpresion;
	}

	public String getFormato() {
		formato = this.formatValue(formato);
		return formato;
	}

	public void setFormato(String formato) {
		formato = this.formatValue(formato);
		this.formato = formato;
	}

	@Override
	public int compareTo(Chequera o) {
		return this.getCodigo().compareTo(o.getCodigo());
	}

	@Override
	public String toString() {
		return "(" + cuentaDeFondo.getCodigo() + ") " + getCodigo();

	}

	@Override
	public Chequera clone() throws CloneNotSupportedException {

		Chequera other = new Chequera();

		other.setId(this.getId());
		if (this.getCuentaDeFondo() != null) {
			other.setCuentaDeFondo(getCuentaDeFondo().clone());
		} else {
			other.setCuentaDeFondo(null);
		}
		other.setCodigo(this.getCodigo());
		other.setPrimerNumero(getPrimerNumero());
		other.setUltimoNumero(getUltimoNumero());
		other.setProximoNumero(getProximoNumero());
		other.setBloqueado(getBloqueado());
		other.setImpresionDiferida(getImpresionDiferida());
		other.setDestinoImpresion(getDestinoImpresion());
		other.setFormato(getFormato());

		return other;
	}

	public boolean validate() throws IllegalArgumentException {

		super.validate();

		if (this.cuentaDeFondo == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".cuentaDeFondo es nulo.");
		}

		if (this.codigo == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".codigo es nulo.");
		}

		return true;
	}

}
