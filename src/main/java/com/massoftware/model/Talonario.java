package com.massoftware.model;

import java.math.BigDecimal;
import java.util.Date;

import com.massoftware.annotation.model.ClassArticleLabelInPluralAnont;
import com.massoftware.annotation.model.ClassArticleLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassFormSourceAnont;
import com.massoftware.annotation.model.ClassLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassPluralLabelAnont;
import com.massoftware.annotation.model.ClassTableMSAnont;
import com.massoftware.annotation.model.FieldColumnMetaDataAnont;
import com.massoftware.annotation.model.FieldColumnsAnont;
import com.massoftware.annotation.model.FieldLabelAnont;
import com.massoftware.annotation.model.FieldMaxLengthAnont;
import com.massoftware.annotation.model.FieldMaxValueBigDecimalAnont;
import com.massoftware.annotation.model.FieldMaxValueIntegerAnont;
import com.massoftware.annotation.model.FieldMinValueBigDecimalAnont;
import com.massoftware.annotation.model.FieldMinValueIntegerAnont;
import com.massoftware.annotation.model.FieldNameMSAnont;
import com.massoftware.annotation.model.FieldOptionsStringAnont;
import com.massoftware.annotation.model.FieldRequiredAnont;
import com.massoftware.annotation.model.FieldSubNameFKAnont;
import com.massoftware.annotation.model.FieldUniqueAnont;

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Talonario")
@ClassPluralLabelAnont(value = "Talonarios")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
@ClassFormSourceAnont(value = "Talonario")
@ClassTableMSAnont(nameTableDB = "[TablaDeMultiproposito]")
public class Talonario extends EntityId implements Cloneable,
		Comparable<Talonario> {

	@FieldLabelAnont(value = "Número")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = 99999)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[MULTIPROPOSITO]", classAttDB = Integer.class)
	private Integer codigo;

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 20)
	@FieldColumnsAnont(value = 20)
	@FieldColumnMetaDataAnont(attSize = 250)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "Letra")
	@FieldRequiredAnont()
	@FieldOptionsStringAnont(values = { "A", "B", "C", "M", "R", "X" }, defaultValue = "X")
	@FieldColumnMetaDataAnont(attSize = 80)
	@FieldNameMSAnont(nameAttDB = "[LETRA]", classAttDB = String.class)
	private String letra;

	@FieldLabelAnont(value = "Sucursal")
//	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 200)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[SUCURSAL]", classAttDB = Integer.class)
	private Sucursal sucursal;

	@FieldLabelAnont(value = "Auto numeración")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[AUTONUMERACION]", classAttDB = Boolean.class)
	private Boolean autonumeracion;

	@FieldLabelAnont(value = "Numeración pre-impresa")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[NUMERACIONPREIMPRESA]", classAttDB = Boolean.class)
	private Boolean numeracionPreImpresa;

	@FieldLabelAnont(value = "Asociado al RG 100/98")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[RG10098]", classAttDB = Boolean.class)
	private Boolean asociadoAlRG10098;

	@FieldLabelAnont(value = "Asociado a controlador fiscal")
	@FieldRequiredAnont()	
	@FieldOptionsStringAnont(values = { "S", "H", "E", "W", "M", "X" }, captions = { "Sin controlador", "Hasar SMH/P614F", "Epson TM-300A/F", "WSFE", "WSMTXCA", "WSFEX" }, defaultValue = "S", horizontal = false)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[CONTROLFISCAL]", classAttDB = String.class)
	private String asociadoAControladorFiscal;

	@FieldLabelAnont(value = "Primer número")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 8)
	@FieldColumnsAnont(value = 8)
	@FieldMinValueIntegerAnont(value = 0)
	@FieldMaxValueIntegerAnont(value = 99999999)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[PRIMERNUMERO]", classAttDB = Integer.class)
	private Integer primerNumero;
	
	@FieldLabelAnont(value = "Próximo número")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 8)
	@FieldColumnsAnont(value = 8)
	@FieldMinValueIntegerAnont(value = 0)
	@FieldMaxValueIntegerAnont(value = 99999999)
	@FieldColumnMetaDataAnont(attSize = 100)
	@FieldNameMSAnont(nameAttDB = "[PROXIMONUMERO]", classAttDB = Integer.class)
	private Integer proximoNumero;
	
	@FieldLabelAnont(value = "Último número")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 8)
	@FieldColumnsAnont(value = 8)
	@FieldMinValueIntegerAnont(value = 0)
	@FieldMaxValueIntegerAnont(value = 99999999)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[ULTIMONUMERO]", classAttDB = Integer.class)
	private Integer ultimoNumero;
	
	@FieldLabelAnont(value = "Cant. min. cbtes.")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 0)
	@FieldMaxValueIntegerAnont(value = 99999)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[ALERTACANTIDADMINIMADECBTES]", classAttDB = Integer.class)
	private Integer cantidadMinimaComprobantes;
	
	@FieldLabelAnont(value = "Fecha")
	@FieldRequiredAnont()	
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[ULTIMAFECHASQL]", classAttDB = Date.class)
	private Date ultimaFecha;
	
	@FieldLabelAnont(value = "Número C.A.I")
	@FieldRequiredAnont()		
	@FieldMaxLengthAnont(value = 14)
	@FieldColumnsAnont(value = 14)
	@FieldMinValueBigDecimalAnont(value = "0")
	@FieldMaxValueBigDecimalAnont(value = "99999999999999")
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[CAI]", classAttDB = BigDecimal.class)
	private BigDecimal numeroCAI;
	
	@FieldLabelAnont(value = "Fecha")
	@FieldRequiredAnont()	
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[VENCIMIENTOCAISQL]", classAttDB = Date.class)
	private Date vencimientoCAI;
	
	@FieldLabelAnont(value = "Días aviso vto.")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 3)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 0)
	@FieldMaxValueIntegerAnont(value = 255)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[DIASAVISOVENCIMIENTO]", classAttDB = Integer.class)
	private Integer diasAvisoVencimiento;
	
	@FieldLabelAnont(value = "Puerta")
//	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[DOORNOCAMBIAR]", classAttDB = Integer.class)
	private SeguridadPuerta puertaCambiar;

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

	public String getLetra() {
		letra = formatValue(letra);
		return letra;
	}

	public void setLetra(String letra) {
		letra = formatValue(letra);
		this.letra = letra;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		if (sucursal != null && sucursal.getId() == null) {
			return;
		}

		this.sucursal = sucursal;
	}

	public Boolean getAutonumeracion() {
		autonumeracion = this.nullIsFalse(autonumeracion);
		return autonumeracion;
	}

	public void setAutonumeracion(Boolean autonumeracion) {
		autonumeracion = this.nullIsFalse(autonumeracion);
		this.autonumeracion = autonumeracion;
	}

	public Boolean getNumeracionPreImpresa() {
		numeracionPreImpresa = this.nullIsFalse(numeracionPreImpresa);
		return numeracionPreImpresa;
	}

	public void setNumeracionPreImpresa(Boolean numeracionPreImpresa) {
		numeracionPreImpresa = this.nullIsFalse(numeracionPreImpresa);
		this.numeracionPreImpresa = numeracionPreImpresa;
	}

	public Boolean getAsociadoAlRG10098() {
		asociadoAlRG10098 = this.nullIsFalse(asociadoAlRG10098);
		return asociadoAlRG10098;
	}

	public void setAsociadoAlRG10098(Boolean asociadoAlRG10098) {
		asociadoAlRG10098 = this.nullIsFalse(asociadoAlRG10098);
		this.asociadoAlRG10098 = asociadoAlRG10098;
	}

	public String getAsociadoAControladorFiscal() {
		asociadoAControladorFiscal = formatValue(asociadoAControladorFiscal);
		if (asociadoAControladorFiscal == null) {
			asociadoAControladorFiscal = "S";
		}
		return asociadoAControladorFiscal;
	}

	public void setAsociadoAControladorFiscal(String asociadoAControladorFiscal) {
		asociadoAControladorFiscal = formatValue(asociadoAControladorFiscal);
		if (asociadoAControladorFiscal == null) {
			asociadoAControladorFiscal = "S";
		}
		this.asociadoAControladorFiscal = asociadoAControladorFiscal;
	}

	public Integer getPrimerNumero() {
		if (primerNumero == null) {
			primerNumero = 0;
		}
		return primerNumero;
	}

	public void setPrimerNumero(Integer primerNumero) {
		if (primerNumero == null) {
			primerNumero = 0;
		}
		this.primerNumero = primerNumero;
	}

	public Integer getProximoNumero() {
		if (proximoNumero == null) {
			proximoNumero = 0;
		}
		return proximoNumero;
	}

	public void setProximoNumero(Integer proximoNumero) {
		if (proximoNumero == null) {
			proximoNumero = 0;
		}
		this.proximoNumero = proximoNumero;
	}

	public Integer getUltimoNumero() {
		if (ultimoNumero == null) {
			ultimoNumero = 0;
		}
		return ultimoNumero;
	}

	public void setUltimoNumero(Integer ultimoNumero) {
		if (ultimoNumero == null) {
			ultimoNumero = 0;
		}
		this.ultimoNumero = ultimoNumero;
	}

	public Integer getCantidadMinimaComprobantes() {
		if (cantidadMinimaComprobantes == null) {
			cantidadMinimaComprobantes = 0;
		}
		return cantidadMinimaComprobantes;
	}

	public void setCantidadMinimaComprobantes(Integer cantidadMinimaComprobantes) {
		if (cantidadMinimaComprobantes == null) {
			cantidadMinimaComprobantes = 0;
		}
		this.cantidadMinimaComprobantes = cantidadMinimaComprobantes;
	}

	public Date getUltimaFecha() {
		return ultimaFecha;
	}

	public void setUltimaFecha(Date ultimaFecha) {
		this.ultimaFecha = ultimaFecha;
	}

	public BigDecimal getNumeroCAI() {
		if (numeroCAI == null) {
			numeroCAI = new BigDecimal("0");
		}
		return numeroCAI;
	}

	public void setNumeroCAI(BigDecimal numeroCAI) {
		if (numeroCAI == null) {
			numeroCAI = new BigDecimal("0");
		}
		this.numeroCAI = numeroCAI;
	}

	public Date getVencimientoCAI() {
		return vencimientoCAI;
	}

	public void setVencimientoCAI(Date vencimientoCAI) {
		this.vencimientoCAI = vencimientoCAI;
	}

	public Integer getDiasAvisoVencimiento() {
		if (diasAvisoVencimiento == null) {
			diasAvisoVencimiento = 0;
		}
		return diasAvisoVencimiento;
	}

	public void setDiasAvisoVencimiento(Integer diasAvisoVencimiento) {
		if (diasAvisoVencimiento == null) {
			diasAvisoVencimiento = 0;
		}
		this.diasAvisoVencimiento = diasAvisoVencimiento;
	}

	public SeguridadPuerta getPuertaCambiar() {
		return puertaCambiar;
	}

	public void setPuertaCambiar(SeguridadPuerta puertaCambiar) {
		if (puertaCambiar != null && puertaCambiar.getId() == null) {
			return;
		}
		this.puertaCambiar = puertaCambiar;
	}

	@Override
	public Talonario clone() throws CloneNotSupportedException {

		Talonario other = new Talonario();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());
		other.setLetra(this.getLetra());
		if (this.getSucursal() != null) {
			other.setSucursal(this.getSucursal().clone());
		} else {
			other.setSucursal(null);
		}
		other.setAutonumeracion(this.getAutonumeracion());
		other.setNumeracionPreImpresa(this.getNumeracionPreImpresa());
		other.setAsociadoAlRG10098(this.getAsociadoAlRG10098());
		other.setAsociadoAControladorFiscal(this
				.getAsociadoAControladorFiscal());
		other.setPrimerNumero(this.getPrimerNumero());
		other.setProximoNumero(this.getProximoNumero());
		other.setUltimoNumero(this.getUltimoNumero());
		other.setCantidadMinimaComprobantes(this
				.getCantidadMinimaComprobantes());
		if (this.getUltimaFecha() != null) {
			other.setUltimaFecha((Date) this.getUltimaFecha().clone());
		} else {
			other.setUltimaFecha(null);
		}
		if (this.getNumeroCAI() != null) {
			other.setNumeroCAI(new BigDecimal(this.getNumeroCAI().toString()));
		} else {
			other.setNumeroCAI(null);
		}
		if (this.getVencimientoCAI() != null) {
			other.setVencimientoCAI((Date) this.getVencimientoCAI().clone());
		} else {
			other.setVencimientoCAI(null);
		}
		other.setDiasAvisoVencimiento(this.getDiasAvisoVencimiento());
		if (this.getPuertaCambiar() != null) {
			other.setPuertaCambiar(this.getPuertaCambiar().clone());
		} else {
			other.setPuertaCambiar(null);
		}

		return other;
	}

	@Override
	public int compareTo(Talonario o) {

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

		if (this.letra == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".letra es nulo.");
		}

		if (this.asociadoAControladorFiscal == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName()
					+ ".asociadoAControladorFiscal es nulo.");
		}

		if (this.primerNumero == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".primerNumero es nulo.");
		}

		if (this.proximoNumero == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".proximoNumero es nulo.");
		}

		if (this.ultimoNumero == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".ultimoNumero es nulo.");
		}

		if (this.cantidadMinimaComprobantes == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName()
					+ ".cantidadMinimaComprobantes es nulo.");
		}

		if (this.numeroCAI == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".numeroCAI es nulo.");
		}

		if (this.diasAvisoVencimiento == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".diasAvisoVencimiento es nulo.");
		}

		return true;
	}

}
