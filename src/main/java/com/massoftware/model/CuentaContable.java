package com.massoftware.model;

import java.math.BigDecimal;

import com.massoftware.frontend.custom.windows.builder.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassFormSourceAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassPluralLabelAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassTableMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldAllowDecimalAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldCBBox;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldColumnMetaDataAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldColumnsAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldInputMaskAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldLabelAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMaxLengthAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMaxValueBigDecimalAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMinValueBigDecimalAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldNameMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldRequiredAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldSubNameFKAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldUniqueAnont;
import com.massoftware.util.FormatCuentaContableCodigoCuenta;

@ClassLabelInTheSingularAnont(value = "Cuenta contable")
@ClassPluralLabelAnont(value = "Cuentas contables")
@ClassArticleLabelInTheSingularAnont(value = "la")
@ClassArticleLabelInPluralAnont(value = "las")
@ClassFormSourceAnont(value = "CuentaContable")
@ClassTableMSAnont(nameTableDB = "[PlanDeCuentas]")
public class CuentaContable extends EntityId implements
		Comparable<CuentaContable> {

	// Al crear controlar
	// 1 que tenga un padre que existe
	// 2 que no exista otra cuenta con el mismo código

	/*
	 * 
	 * 
	 * Al actualizar controlar 1 que no tenga cuentas hijas 2 que tenga un padre
	 * que exista Al borrar controlar 1 que no tengacuentas hijas
	 */

	/**
	 * 
	 */
	// private static final long serialVersionUID = -6146349571117309204L;

	// --------------------------------------------------------------------------
	@FieldLabelAnont(value = "Ejercicio")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "ejercicio")
	// @FieldNameMSAnont(nameAttDB = "[CUENTA]", classAttDB = String.class)
	// @FieldCBBox(attName = "ejercicio")
	private EjercicioContable ejercicioContable;

	@FieldLabelAnont(value = "Integra")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 16)
	@FieldColumnsAnont(value = 16)
	@FieldColumnMetaDataAnont(hidden = true, simpleStringTraslateFilterMode = "STARTS_WITCH")
	// @FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[CUENTAINTEGRADORA]", classAttDB = String.class)
	// @FieldReadOnly()
	@FieldInputMaskAnont(mask = "9.99.99.99.99.99")
	private String codigoCuentaPadre; // integra; // cuenta padre
										// 6.40.00.00.00.00
	@FieldLabelAnont(value = "Cuenta de jerarquia")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 16)
	@FieldColumnsAnont(value = 16)
	@FieldColumnMetaDataAnont(attSize = 100, simpleStringTraslateFilterMode = "ENDS_WITCH")
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[CUENTADEJERARQUIAIND]", classAttDB = String.class)
	@FieldInputMaskAnont(mask = "9.99.99.99.99.99")
	private String codigoCuenta; // cuentaDeJerarquia; 6.40.00.00.00.01

	@FieldLabelAnont(value = "Cuenta contable")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 11)
	@FieldColumnsAnont(value = 11)
	@FieldColumnMetaDataAnont(attSize = 90, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[CUENTACONTABLE]", classAttDB = String.class)
	private String cuentaContable; // texto libre

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 35)
	@FieldColumnsAnont(value = 35)
	@FieldColumnMetaDataAnont(attSize = 200)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre; // texto libre

	// --------------------------------------------------------------------------
	@FieldLabelAnont(value = "Imputable")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[AJUSTEINF]", classAttDB = Boolean.class)
	private Boolean imputable = false;

	@FieldLabelAnont(value = "Ajusta por inflación")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[IMPUTABLE]", classAttDB = Boolean.class)
	private Boolean ajustaPorInflacion = false;

	@FieldLabelAnont(value = "Estado")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[ESTADO]", classAttDB = Integer.class)
	private CuentaContableEstado cuentaContableEstado;

	@FieldLabelAnont(value = "Cuenta con apropiación")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[APROPIA]", classAttDB = Boolean.class)
	private Boolean cuentaConApropiacion = false;

	// --------------------------------------------------------------------------

	@FieldLabelAnont(value = "Centro de costo")
	@FieldColumnMetaDataAnont(attSize = 110)
	@FieldSubNameFKAnont(value = "numero")
	@FieldNameMSAnont(nameAttDB = "[CENTRODECOSTOCONTABLE]", classAttDB = Integer.class)
	private CentroDeCostoContable centroDeCostoContable;

	@FieldLabelAnont(value = "Cuenta agrupadora")
	@FieldMaxLengthAnont(value = 11)
	@FieldColumnsAnont(value = 11)
	@FieldColumnMetaDataAnont(hidden = true)
//	@FieldColumnMetaDataAnont(attSize = 100)
	@FieldNameMSAnont(nameAttDB = "[CUENTAAGRUPADORA]", classAttDB = String.class)
	private String cuentaAgrupadora; // aca va cualquier texto, texto libre

	@FieldLabelAnont(value = "Porcentaje")
	@FieldMaxLengthAnont(value = 6)
	@FieldColumnsAnont(value = 6)
	@FieldMinValueBigDecimalAnont(value = "0")
	@FieldMaxValueBigDecimalAnont(value = "999.99")
	@FieldAllowDecimalAnont()
	@FieldColumnMetaDataAnont(attSize = 70)
	@FieldNameMSAnont(nameAttDB = "[PORCENTAJE]", classAttDB = BigDecimal.class)
	private BigDecimal porcentaje = new BigDecimal("0.0");

	@FieldLabelAnont(value = "Punto de equilibrio")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "puntoDeEquilibrio")
	@FieldNameMSAnont(nameAttDB = "[PUNTODEEQUILIBRIO]", classAttDB = Integer.class)
	private PuntoDeEquilibrio puntoDeEquilibrio;

	@FieldLabelAnont(value = "Costo de venta")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[COSTODEVENTA]", classAttDB = Integer.class)
	private CostoDeVenta costoDeVenta;

	@FieldLabelAnont(value = "Puerta")
	// @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[DOORNOPERMISO]", classAttDB = Integer.class)
	@FieldCBBox(attName = "codigo")
	private SeguridadPuerta seguridadPuerta;

	public EjercicioContable getEjercicioContable() {
		return ejercicioContable;
	}

	public void setEjercicioContable(EjercicioContable ejercicioContable) {
		this.ejercicioContable = ejercicioContable;
	}

	public String getCodigoCuentaPadre() {
		codigoCuentaPadre = formatValue(codigoCuentaPadre);
		return codigoCuentaPadre;
	}

	public void setCodigoCuentaPadre(String codigoCuentaPadre) {
		codigoCuentaPadre = formatValue(codigoCuentaPadre);
		this.codigoCuentaPadre = codigoCuentaPadre;
	}

	public String getCodigoCuenta() {
		codigoCuenta = formatValue(codigoCuenta);
		return codigoCuenta;
	}

	public void setCodigoCuenta(String codigoCuenta) {
		codigoCuenta = formatValue(codigoCuenta);
		this.codigoCuenta = codigoCuenta;
	}

	public String getCuentaContable() {
		cuentaContable = formatValue(cuentaContable);
		return cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		cuentaContable = formatValue(cuentaContable);
		this.cuentaContable = cuentaContable;
	}

	public String getNombre() {
		nombre = formatValue(nombre);
		return nombre;
	}

	public void setNombre(String nombre) {
		nombre = formatValue(nombre);
		this.nombre = nombre;
	}

	public Boolean getImputable() {
		imputable = this.nullIsFalse(imputable);
		return imputable;
	}

	public void setImputable(Boolean imputable) {
		imputable = this.nullIsFalse(imputable);
		this.imputable = imputable;
	}

	public Boolean getAjustaPorInflacion() {
		ajustaPorInflacion = this.nullIsFalse(ajustaPorInflacion);
		return ajustaPorInflacion;
	}

	public void setAjustaPorInflacion(Boolean ajustaPorInflacion) {
		ajustaPorInflacion = this.nullIsFalse(ajustaPorInflacion);
		this.ajustaPorInflacion = ajustaPorInflacion;
	}

	public CuentaContableEstado getCuentaContableEstado() {
		return cuentaContableEstado;
	}

	public void setCuentaContableEstado(
			CuentaContableEstado cuentaContableEstado) {
		this.cuentaContableEstado = cuentaContableEstado;
		if (this.cuentaContableEstado != null
				&& this.cuentaContableEstado.getId() == null) {
			throw new IllegalArgumentException(this.cuentaContableEstado
					.getClass().getCanonicalName() + ".id is null");
		}
	}

	public Boolean getCuentaConApropiacion() {
		cuentaConApropiacion = this.nullIsFalse(cuentaConApropiacion);
		return cuentaConApropiacion;
	}

	public void setCuentaConApropiacion(Boolean cuentaConApropiacion) {
		cuentaConApropiacion = this.nullIsFalse(cuentaConApropiacion);
		this.cuentaConApropiacion = cuentaConApropiacion;
	}

	public CentroDeCostoContable getCentroDeCostoContable() {
		return centroDeCostoContable;
	}

	public void setCentroDeCostoContable(
			CentroDeCostoContable centroDeCostoContable) {
		this.centroDeCostoContable = centroDeCostoContable;
	}

	public String getCuentaAgrupadora() {
		cuentaAgrupadora = formatValue(cuentaAgrupadora);
		return cuentaAgrupadora;
	}

	public void setCuentaAgrupadora(String cuentaAgrupadora) {
		cuentaAgrupadora = formatValue(cuentaAgrupadora);
		this.cuentaAgrupadora = cuentaAgrupadora;
	}

	public BigDecimal getPorcentaje() {
		// porcentaje = nullIsZero(porcentaje);
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		// porcentaje = nullIsZero(porcentaje);
		this.porcentaje = porcentaje;
	}

	public PuntoDeEquilibrio getPuntoDeEquilibrio() {
		return puntoDeEquilibrio;
	}

	public void setPuntoDeEquilibrio(PuntoDeEquilibrio puntoDeEquilibrio) {
		this.puntoDeEquilibrio = puntoDeEquilibrio;
	}

	public CostoDeVenta getCostoDeVenta() {
		return costoDeVenta;
	}

	public void setCostoDeVenta(CostoDeVenta costoDeVenta) {
		this.costoDeVenta = costoDeVenta;
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

	// @Override
	// public CuentaContable clone() throws CloneNotSupportedException {
	// CuentaContable other = new CuentaContable();
	//
	// other.setId(this.getId());
	// if (this.getEjercicioContable() != null) {
	// other.setEjercicioContable(this.getEjercicioContable().clone());
	// } else {
	// other.setEjercicioContable(null);
	// }
	// other.setCuentaContable(this.getCuentaContable());
	// other.setCodigoCuentaPadre(this.getCodigoCuentaPadre());
	// other.setCodigoCuenta(this.getCodigoCuenta());
	// other.setNombre(this.getNombre());
	// other.setImputable(this.getImputable());
	// other.setAjustaPorInflacion(this.getAjustaPorInflacion());
	// if (this.getCuentaContableEstado() != null) {
	// other.setCuentaContableEstado(this.getCuentaContableEstado()
	// .clone());
	// } else {
	// other.setCuentaContableEstado(null);
	// }
	// other.setCuentaConApropiacion(this.getCuentaConApropiacion());
	// if (this.getCentroDeCostoContable() != null) {
	// other.setCentroDeCostoContable(this.getCentroDeCostoContable()
	// .clone());
	// } else {
	// other.setCentroDeCostoContable(null);
	// }
	// other.setCuentaAgrupadora(this.getCuentaAgrupadora());
	// other.setPorcentaje(this.getPorcentaje());
	// if (this.getPuntoDeEquilibrio() != null) {
	// other.setPuntoDeEquilibrio(this.getPuntoDeEquilibrio().clone());
	// } else {
	// other.setPuntoDeEquilibrio(null);
	// }
	// if (this.getCostoDeVenta() != null) {
	// other.setCostoDeVenta(this.getCostoDeVenta().clone());
	// } else {
	// other.setCostoDeVenta(null);
	// }
	//
	// return other;
	// }

	private boolean _setfullToString = true;

	public void _setfullToString(boolean _setfullToString) {
		this._setfullToString = _setfullToString;
	}

	@Override
	public String toString() {

		if (this._setfullToString) {
			return "(" + this.getEjercicioContable() + ") ("
					+ this.getCuentaContable() + ") " + this.getNombre();
		} else {

			// return "(" + this.getEjercicioContable() + ") "
			// + FormatCuentaContableCodigoCuenta.format(
			// this.getCodigoCuenta()) + " " + this.getNombre();

			return FormatCuentaContableCodigoCuenta.format(this
					.getCodigoCuenta()) + " " + this.getNombre();
		}

	}

	@Override
	public int compareTo(CuentaContable o) {

		return this.getCodigoCuenta().compareTo(o.getCodigoCuenta());
	}

	public boolean validate() {

		super.validate();

		// if (this.ejercicioContable == null) {
		//
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName() + ".ejercicioContable es nulo.");
		// } else {
		// this.ejercicioContable.validate();
		// }
		//
		// if (this.codigoCuentaPadre == null) {
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName() + ".codigoCuentaPadre es nulo.");
		// }
		//
		// if (this.codigoCuenta == null) {
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName() + ".codigoCuenta es nulo.");
		// }
		//
		// if (this.cuentaContable == null) {
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName() + ".cuentaContable es nulo.");
		// }
		//
		// if (this.nombre == null) {
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName() + ".nombre es nulo.");
		// }
		//
		// if (this.imputable == null) {
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName() + ".imputable es nulo.");
		// }
		//
		// if (this.ajustaPorInflacion == null) {
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName() + ".ajustaPorInflacion es nulo.");
		// }
		//
		// if (this.cuentaContableEstado == null) {
		//
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName() + ".cuentaContableEstado es nulo.");
		// } else {
		// this.cuentaContableEstado.validate();
		// }
		//
		// if (this.cuentaConApropiacion == null) {
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName() + ".cuentaConApropiacion es nulo.");
		// }
		//
		// if (this.porcentaje == null) {
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName() + ".porcentaje es nulo.");
		// }

		return true;
	}



}
