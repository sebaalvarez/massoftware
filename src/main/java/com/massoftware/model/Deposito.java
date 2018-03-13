package com.massoftware.model;

import org.cendra.common.model.EntityId;

import com.massoftware.annotation.model.ClassArticleLabelInPluralAnont;
import com.massoftware.annotation.model.ClassArticleLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassPluralLabelAnont;
import com.massoftware.annotation.model.ClassTableMSAnont;
import com.massoftware.annotation.model.FieldColumnMetaDataAnont;
import com.massoftware.annotation.model.FieldColumnsAnont;
import com.massoftware.annotation.model.FieldLabelAnont;
import com.massoftware.annotation.model.FieldMaxLengthAnont;
import com.massoftware.annotation.model.FieldMaxValueIntegerAnont;
import com.massoftware.annotation.model.FieldMinValueIntegerAnont;
import com.massoftware.annotation.model.FieldNameMSAnont;
import com.massoftware.annotation.model.FieldRequiredAnont;
import com.massoftware.annotation.model.FieldSubNameFKAnont;
import com.massoftware.annotation.model.FieldUniqueAnont;

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Depósito")
@ClassPluralLabelAnont(value = "Depósitos")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
//@ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[Depositos]")
public class Deposito extends EntityId implements Cloneable,
		Comparable<Deposito> {


	@FieldLabelAnont(value = "Depósito")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 3)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = 255)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[DEPOSITO]", classAttDB = Integer.class)
	private Integer codigo;
	
	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 35)
	@FieldColumnsAnont(value = 35)
	@FieldColumnMetaDataAnont(attSize = 250)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;
	
	@FieldLabelAnont(value = "Abreviatura")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 4)
	@FieldColumnsAnont(value = 4)
	@FieldColumnMetaDataAnont(attSize = 80)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[ABREVIATURA]", classAttDB = String.class)
	private String abreviatura;
	
	@FieldLabelAnont(value = "Depósito activo")
	@FieldColumnMetaDataAnont(attSize = 80)
	@FieldNameMSAnont(nameAttDB = "[DEPOSITOACTIVO]", classAttDB = Boolean.class)
	private Boolean depositoActivo;
	
	@FieldLabelAnont(value = "Sucursal")
//	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 200)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[SUCURSAL]", classAttDB = Integer.class)
	private Sucursal sucursal;
	
	// private Integer caja; // No se usa !!!
	
	@FieldLabelAnont(value = "Módulo")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 200)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[MODULO]", classAttDB = Integer.class)
	private Modulo modulo;
	
//	@FieldLabelAnont(value = "Depósito agrupación")
////	@FieldRequiredAnont()
//	@FieldColumnMetaDataAnont(hidden = true)
//	@FieldSubNameFKAnont(value = "codigo")
//	@FieldNameMSAnont(nameAttDB = "[DEPOSITO]", classAttDB = Integer.class)
//	private Deposito depositoAgrupacion;
	
	@FieldLabelAnont(value = "Puerta operativo")
//	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[DOORNOCONSULTAR]", classAttDB = Integer.class)
	private SeguridadPuerta puertaOperativo;
	
	@FieldLabelAnont(value = "Puerta consulta")
//	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[DOORNOOPERATIVO]", classAttDB = Integer.class)
	private SeguridadPuerta puertaConsulta;

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

	public String getAbreviatura() {
		abreviatura = formatValue(abreviatura);
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		abreviatura = formatValue(abreviatura);
		this.abreviatura = abreviatura;
	}

	public Boolean getDepositoActivo() {
		depositoActivo = this.nullIsFalse(depositoActivo);
		return depositoActivo;
	}

	public void setDepositoActivo(Boolean depositoActivo) {
		depositoActivo = this.nullIsFalse(depositoActivo);
		this.depositoActivo = depositoActivo;
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

	// public Integer getCaja() {
	// return caja;
	// }
	//
	// public void setCaja(Integer caja) {
	// this.caja = caja;
	// }

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		if (modulo != null && modulo.getId() == null) {
			return;
		}
		this.modulo = modulo;
	}

//	public Deposito getDepositoAgrupacion() {
//		return depositoAgrupacion;
//	}
//
//	public void setDepositoAgrupacion(Deposito depositoAgrupacion) {
//		if (depositoAgrupacion != null && depositoAgrupacion.getId() == null) {
//			return;
//		}
//		this.depositoAgrupacion = depositoAgrupacion;
//	}

	public SeguridadPuerta getPuertaOperativo() {
		return puertaOperativo;
	}

	public void setPuertaOperativo(SeguridadPuerta puertaOperativo) {
		if (puertaOperativo != null && puertaOperativo.getId() == null) {
			return;
		}
		this.puertaOperativo = puertaOperativo;
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

	@Override
	public Deposito clone() throws CloneNotSupportedException {

		Deposito other = new Deposito();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());
		other.setAbreviatura(this.getAbreviatura());
		other.setDepositoActivo(this.getDepositoActivo());
		if (this.getSucursal() != null) {
			other.setSucursal(this.getSucursal().clone());
		} else {
			other.setSucursal(null);
		}
		// other.setCaja(this.getCaja());
		if (this.getModulo() != null) {
			other.setModulo(this.getModulo().clone());
		} else {
			other.setModulo(null);
		}
//		if (this.getDepositoAgrupacion() != null) {
//			other.setDepositoAgrupacion(this.getDepositoAgrupacion().clone());
//		} else {
//			other.setDepositoAgrupacion(null);
//		}
		if (this.getPuertaOperativo() != null) {
			other.setPuertaOperativo(this.getPuertaOperativo().clone());
		} else {
			other.setPuertaOperativo(null);
		}
		if (this.getPuertaConsulta() != null) {
			other.setPuertaConsulta(this.getPuertaConsulta().clone());
		} else {
			other.setPuertaConsulta(null);
		}

		return other;
	}

	@Override
	public int compareTo(Deposito o) {

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

		if (this.abreviatura == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".abreviatura es nulo.");
		}

		if (this.sucursal == null || this.sucursal.getId() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".sucursal es nulo.");
		}

		if (this.modulo == null || this.modulo.getId() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".modulo es nulo.");
		}

		return true;
	}

}
