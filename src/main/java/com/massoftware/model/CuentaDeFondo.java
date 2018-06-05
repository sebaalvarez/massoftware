package com.massoftware.model;

import java.math.BigDecimal;

import com.massoftware.annotation.model.ClassArticleLabelInPluralAnont;
import com.massoftware.annotation.model.ClassArticleLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassFormSourceAnont;
import com.massoftware.annotation.model.ClassLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassPluralLabelAnont;
import com.massoftware.annotation.model.ClassTableMSAnont;
import com.massoftware.annotation.model.FieldAllowDecimalAnont;
import com.massoftware.annotation.model.FieldAutoMaxValueAnont;
import com.massoftware.annotation.model.FieldCBBox;
import com.massoftware.annotation.model.FieldColumnMetaDataAnont;
import com.massoftware.annotation.model.FieldColumnsAnont;
import com.massoftware.annotation.model.FieldLabelAnont;
import com.massoftware.annotation.model.FieldMaxLengthAnont;
import com.massoftware.annotation.model.FieldMaxValueBigDecimalAnont;
import com.massoftware.annotation.model.FieldMaxValueIntegerAnont;
import com.massoftware.annotation.model.FieldMinValueBigDecimalAnont;
import com.massoftware.annotation.model.FieldMinValueIntegerAnont;
import com.massoftware.annotation.model.FieldNameMSAnont;
import com.massoftware.annotation.model.FieldRequiredAnont;
import com.massoftware.annotation.model.FieldSubNameFKAnont;
import com.massoftware.annotation.model.FieldUniqueAnont;

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Cuenta de fondo")
@ClassPluralLabelAnont(value = "Cuentas de fondo")
@ClassArticleLabelInTheSingularAnont(value = "la")
@ClassArticleLabelInPluralAnont(value = "las")
@ClassFormSourceAnont(value = "CuentaDeFondo")
@ClassTableMSAnont(nameTableDB = "[CuentasDeFondos]")
public class CuentaDeFondo extends EntityId implements Cloneable,
		Comparable<CuentaDeFondo> {

	@FieldLabelAnont(value = "Cuenta")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 10)
	@FieldColumnsAnont(value = 10)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Integer.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 100, pidFilteringStart = true, simpleStringTraslateFilterMode = "STARTS_WITCH")
	@FieldUniqueAnont()
	@FieldAutoMaxValueAnont()
	@FieldNameMSAnont(nameAttDB = "[CUENTA]", classAttDB = Integer.class)
	private Integer codigo;

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 40)
	@FieldColumnsAnont(value = 40)
	@FieldColumnMetaDataAnont(attSize = 350)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "Rubro - Grupo")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	// @FieldColumnMetaDataAnont(attSize = 450)
	// @FieldSubNameFKAnont(value = "codigo")// no va funcionar por que es doble
	// codigo la PK
	// @FieldNameMSAnont(nameAttDB = "[GRUPO]", classAttDB = Integer.class) //
	// no va funcionar por que es doble codigo la PK
	private CuentaDeFondoGrupo cuentaDeFondoGrupo;

	@FieldLabelAnont(value = "Tipo")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 150)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[TIPO]", classAttDB = Integer.class)
	private CuentaDeFondoTipo cuentaDeFondoTipo;

	@FieldLabelAnont(value = "Moneda")
	// @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[MONEDA]", classAttDB = Integer.class)
	private Moneda moneda;

	@FieldLabelAnont(value = "Caja")
	// @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[CAJA]", classAttDB = Integer.class)
	private Caja caja;

	@FieldLabelAnont(value = "Tipo banco")
	// @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[TIPOBANCO]", classAttDB = Integer.class)
	private CuentaDeFondoTipoBanco cuentaDeFondoTipoBanco;

	@FieldLabelAnont(value = "Cuenta obsoleta")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[OBSOLETA]", classAttDB = Boolean.class)
	private Boolean obsoleta;

	@FieldLabelAnont(value = "No imprime caja")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[NOIMPRIMECAJA]", classAttDB = Boolean.class)
	private Boolean noImprimeCaja;

	@FieldLabelAnont(value = "Ventas")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[VENTAS]", classAttDB = Boolean.class)
	private Boolean moduloVentas;

	@FieldLabelAnont(value = "Fondos")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[FONDOS]", classAttDB = Boolean.class)
	private Boolean moduloFondos;

	@FieldLabelAnont(value = "Compras")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[COMPRAS]", classAttDB = Boolean.class)
	private Boolean moduloCompras;

	@FieldLabelAnont(value = "Cuenta contable")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	// @FieldSubNameFKAnont(value = "codigo")
	// @FieldNameMSAnont(nameAttDB = "[CUENTACONTABLE]", classAttDB =
	// Integer.class) // no va funcionar por que es doble codigo la PK
	@FieldCBBox(attName = "cuentaContable")
	private CuentaContable cuentaContable;

	@FieldLabelAnont(value = "Cuenta diferidos")
	// @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	// @FieldSubNameFKAnont(value = "codigo")
	// @FieldNameMSAnont(nameAttDB = "[CUENTACONTABLE]", classAttDB =
	// Integer.class) // no va funcionar por que es doble codigo la PK
	@FieldCBBox(attName = "codigo")
	private CuentaDeFondoA cuentaDiferidos;

	@FieldLabelAnont(value = "Cuenta caución")
	// @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	// @FieldSubNameFKAnont(value = "codigo")
	// @FieldNameMSAnont(nameAttDB = "[CUENTACONTABLE]", classAttDB =
	// Integer.class) // no va funcionar por que es doble codigo la PK
	@FieldCBBox(attName = "codigo")
	private CuentaDeFondoA cuentaCaucion;

	@FieldLabelAnont(value = "Límite descubierto")
	@FieldMaxLengthAnont(value = 10)
	@FieldColumnsAnont(value = 10)
	@FieldAllowDecimalAnont(value = true)
	@FieldMinValueBigDecimalAnont(value = "-9999999")
	@FieldMaxValueBigDecimalAnont(value = "9999999")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[LIMITEDESCUBIERTO]", classAttDB = BigDecimal.class)
	private BigDecimal limiteDescubierto;

	@FieldLabelAnont(value = "Banco")
	// @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[BANCO]", classAttDB = Integer.class)
	private Banco banco;

	@FieldLabelAnont(value = "Cuenta bancaria")
	// @FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 22)
	@FieldColumnsAnont(value = 22)
	@FieldColumnMetaDataAnont(hidden = true)
	// @FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[CUENTABANCARIA]", classAttDB = String.class)
	private String cuentaBancaria;

	@FieldLabelAnont(value = "CBU")
	// @FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 22)
	@FieldColumnsAnont(value = 22)
	@FieldColumnMetaDataAnont(hidden = true)
	// @FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[CBU]", classAttDB = String.class)
	private String cbu;

	@FieldLabelAnont(value = "Conciliación")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[CONCILIACION]", classAttDB = Boolean.class)
	private Boolean conciliacion;

	@FieldLabelAnont(value = "Rechazados")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[CARTERARECHAZADOS]", classAttDB = Boolean.class)
	private Boolean rechazados;

	@FieldLabelAnont(value = "Puerta para uso de cta.")
	// @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
//	@FieldSubNameFKAnont(value = "codigo")
//	@FieldNameMSAnont(nameAttDB = "[DOORNO]", classAttDB = Integer.class)
	@FieldCBBox(attName = "codigo")
	private SeguridadPuerta seguridadPuerta;

	@FieldLabelAnont(value = "Puerta para consulta")
	// @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
//	@FieldSubNameFKAnont(value = "codigo")
//	@FieldNameMSAnont(nameAttDB = "[DOORNOCONSULTA]", classAttDB = Integer.class)
	@FieldCBBox(attName = "codigo")
	private SeguridadPuerta puertaConsulta;

	@FieldLabelAnont(value = "Límite operacion individual")
	@FieldMaxLengthAnont(value = 10)
	@FieldColumnsAnont(value = 10)
	@FieldAllowDecimalAnont(value = true)
	@FieldMinValueBigDecimalAnont(value = "-9999999")
	@FieldMaxValueBigDecimalAnont(value = "9999999")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[LIMITEOPERACIONINDIVIDUAL]", classAttDB = BigDecimal.class)
	private BigDecimal limiteOperacionIndividual;

	@FieldLabelAnont(value = "Puerta, derecho para superar límite")
	// @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	// @FieldSubNameFKAnont(value = "codigo")
	// @FieldNameMSAnont(nameAttDB = "[DOORNOLIMITE]", classAttDB =
	// Integer.class)
	@FieldCBBox(attName = "codigo")
	private SeguridadPuerta puertaLimite;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		nombre = this.formatValue(nombre);
		return nombre;
	}

	public void setNombre(String nombre) {
		nombre = this.formatValue(nombre);
		this.nombre = nombre;
	}

	public CuentaDeFondoGrupo getCuentaDeFondoGrupo() {
		return cuentaDeFondoGrupo;
	}

	public void setCuentaDeFondoGrupo(CuentaDeFondoGrupo cuentaDeFondoGrupo) {
		if (cuentaDeFondoGrupo != null && cuentaDeFondoGrupo.getId() == null) {
			return;
		}
		this.cuentaDeFondoGrupo = cuentaDeFondoGrupo;
	}

	public CuentaDeFondoTipo getCuentaDeFondoTipo() {
		return cuentaDeFondoTipo;
	}

	public void setCuentaDeFondoTipo(CuentaDeFondoTipo cuentaDeFondoTipo) {
		if (cuentaDeFondoTipo != null && cuentaDeFondoTipo.getId() == null) {
			return;
		}
		this.cuentaDeFondoTipo = cuentaDeFondoTipo;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		if (moneda != null && moneda.getId() == null) {
			return;
		}
		this.moneda = moneda;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		if (caja != null && caja.getId() == null) {
			return;
		}
		this.caja = caja;
	}

	public CuentaDeFondoTipoBanco getCuentaDeFondoTipoBanco() {
		return cuentaDeFondoTipoBanco;
	}

	public void setCuentaDeFondoTipoBanco(
			CuentaDeFondoTipoBanco cuentaDeFondoTipoBanco) {
		if (cuentaDeFondoTipoBanco != null
				&& cuentaDeFondoTipoBanco.getId() == null) {
			return;
		}
		this.cuentaDeFondoTipoBanco = cuentaDeFondoTipoBanco;
	}

	public Boolean getObsoleta() {
		obsoleta = this.nullIsFalse(obsoleta);
		return obsoleta;
	}

	public void setObsoleta(Boolean obsoleta) {
		obsoleta = this.nullIsFalse(obsoleta);
		this.obsoleta = obsoleta;
	}

	public Boolean getNoImprimeCaja() {
		noImprimeCaja = this.nullIsFalse(noImprimeCaja);
		return noImprimeCaja;
	}

	public void setNoImprimeCaja(Boolean noImprimeCaja) {
		noImprimeCaja = this.nullIsFalse(noImprimeCaja);
		this.noImprimeCaja = noImprimeCaja;
	}

	public Boolean getModuloVentas() {
		moduloVentas = this.nullIsFalse(moduloVentas);
		return moduloVentas;
	}

	public void setModuloVentas(Boolean moduloVentas) {
		moduloVentas = this.nullIsFalse(moduloVentas);
		this.moduloVentas = moduloVentas;
	}

	public Boolean getModuloFondos() {
		moduloFondos = this.nullIsFalse(moduloFondos);
		return moduloFondos;
	}

	public void setModuloFondos(Boolean moduloFondos) {
		moduloFondos = this.nullIsFalse(moduloFondos);
		this.moduloFondos = moduloFondos;
	}

	public Boolean getModuloCompras() {
		moduloCompras = this.nullIsFalse(moduloCompras);
		return moduloCompras;
	}

	public void setModuloCompras(Boolean moduloCompras) {
		moduloCompras = this.nullIsFalse(moduloCompras);
		this.moduloCompras = moduloCompras;
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

	// --------------------------------------------------------------------------

	public CuentaDeFondoA getCuentaDiferidos() {
		return cuentaDiferidos;
	}

	public void setCuentaDiferidos(CuentaDeFondoA cuentaDiferidos) {
		if (cuentaDiferidos != null && cuentaDiferidos.getId() == null) {
			return;
		}
		this.cuentaDiferidos = cuentaDiferidos;
	}

	public CuentaDeFondoA getCuentaCaucion() {
		return cuentaCaucion;
	}

	public void setCuentaCaucion(CuentaDeFondoA cuentaCaucion) {
		if (cuentaCaucion != null && cuentaCaucion.getId() == null) {
			return;
		}
		this.cuentaCaucion = cuentaCaucion;
	}

	public BigDecimal getLimiteDescubierto() {
		return limiteDescubierto;
	}

	public void setLimiteDescubierto(BigDecimal limiteDescubierto) {
		this.limiteDescubierto = limiteDescubierto;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		if (banco != null && banco.getId() == null) {
			return;
		}
		this.banco = banco;
	}

	public String getCuentaBancaria() {
		cuentaBancaria = this.formatValue(cuentaBancaria);
		return cuentaBancaria;
	}

	public void setCuentaBancaria(String cuentaBancaria) {
		cuentaBancaria = this.formatValue(cuentaBancaria);
		this.cuentaBancaria = cuentaBancaria;
	}

	public String getCbu() {
		cbu = this.formatValue(cbu);
		return cbu;
	}

	public void setCbu(String cbu) {
		cbu = this.formatValue(cbu);
		this.cbu = cbu;
	}

	public Boolean getConciliacion() {
		conciliacion = this.nullIsFalse(conciliacion);
		return conciliacion;
	}

	public void setConciliacion(Boolean conciliacion) {
		conciliacion = this.nullIsFalse(conciliacion);
		this.conciliacion = conciliacion;
	}

	// --------------------------------------------------------------------------

	public Boolean getRechazados() {
		rechazados = this.nullIsFalse(rechazados);
		return rechazados;
	}

	public void setRechazados(Boolean rechazados) {
		rechazados = this.nullIsFalse(rechazados);
		this.rechazados = rechazados;
	}

	public SeguridadPuerta getSeguridadPuerta() {
		return seguridadPuerta;
	}

	public void setSeguridadPuerta(SeguridadPuerta seguridadPuerta) {
		if (seguridadPuerta != null && seguridadPuerta.getId() == null) {
			return;
		}
		this.seguridadPuerta = seguridadPuerta;
	}

	public SeguridadPuerta getPuertaConsulta() {
		return puertaConsulta;
	}

	public void setPuertaConsulta(SeguridadPuerta puertaConsulta) {
		if (puertaConsulta != null && puertaConsulta.getId() == null) {
			return;
		}
		this.puertaConsulta = puertaConsulta;
	}

	public BigDecimal getLimiteOperacionIndividual() {
		return limiteOperacionIndividual;
	}

	public void setLimiteOperacionIndividual(
			BigDecimal limiteOperacionIndividual) {
		this.limiteOperacionIndividual = limiteOperacionIndividual;
	}

	public SeguridadPuerta getPuertaLimite() {
		return puertaLimite;
	}

	public void setPuertaLimite(SeguridadPuerta puertaLimite) {
		if (puertaLimite != null && puertaLimite.getId() == null) {
			return;
		}
		this.puertaLimite = puertaLimite;
	}

	@Override
	public CuentaDeFondo clone() throws CloneNotSupportedException {

		CuentaDeFondo other = new CuentaDeFondo();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());
		if (this.getCuentaDeFondoGrupo() != null) {
			other.setCuentaDeFondoGrupo(getCuentaDeFondoGrupo().clone());
		} else {
			other.setCuentaDeFondoGrupo(null);
		}
		if (this.getCuentaDeFondoTipo() != null) {
			other.setCuentaDeFondoTipo(getCuentaDeFondoTipo().clone());
		} else {
			other.setCuentaDeFondoTipo(null);
		}
		if (this.getMoneda() != null) {
			other.setMoneda(getMoneda().clone());
		} else {
			other.setMoneda(null);
		}
		if (this.getCaja() != null) {
			other.setCaja(getCaja().clone());
		} else {
			other.setCaja(null);
		}
		if (this.getCuentaDeFondoTipoBanco() != null) {
			other.setCuentaDeFondoTipoBanco(getCuentaDeFondoTipoBanco().clone());
		} else {
			other.setCuentaDeFondoTipoBanco(null);
		}
		other.setObsoleta(getObsoleta());
		other.setNoImprimeCaja(getNoImprimeCaja());
		other.setModuloCompras(getModuloCompras());
		other.setModuloFondos(getModuloFondos());
		other.setModuloVentas(getModuloVentas());
		if (this.getCuentaContable() != null) {
			other.setCuentaContable(getCuentaContable().clone());
		} else {
			other.setCuentaContable(null);
		}

		if (this.getCuentaDiferidos() != null) {
			other.setCuentaDiferidos(getCuentaDiferidos().clone());
		} else {
			other.setCuentaDiferidos(null);
		}
		if (this.getCuentaCaucion() != null) {
			other.setCuentaCaucion(getCuentaCaucion().clone());
		} else {
			other.setCuentaCaucion(null);
		}
		if (this.getLimiteDescubierto() != null) {
			other.setLimiteDescubierto(new BigDecimal(getLimiteDescubierto()
					.toString()));
		} else {
			other.setLimiteDescubierto(null);
		}
		if (this.getBanco() != null) {
			other.setBanco(getBanco().clone());
		} else {
			other.setBanco(null);
		}
		other.setCuentaBancaria(getCuentaBancaria());
		other.setCbu(getCbu());
		other.setConciliacion(getConciliacion());
		other.setRechazados(getRechazados());

		if (this.getSeguridadPuerta() != null) {
			other.setSeguridadPuerta(getSeguridadPuerta().clone());
		} else {
			other.setSeguridadPuerta(null);
		}
		if (this.getPuertaConsulta() != null) {
			other.setPuertaConsulta(getPuertaConsulta().clone());
		} else {
			other.setPuertaConsulta(null);
		}
		if (this.getLimiteOperacionIndividual() != null) {
			other.setLimiteOperacionIndividual(new BigDecimal(
					getLimiteOperacionIndividual().toString()));
		} else {
			other.setLimiteOperacionIndividual(null);
		}
		if (this.getPuertaLimite() != null) {
			other.setPuertaLimite(getPuertaLimite().clone());
		} else {
			other.setPuertaLimite(null);
		}

		return other;
	}

	@Override
	public int compareTo(CuentaDeFondo o) {

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
