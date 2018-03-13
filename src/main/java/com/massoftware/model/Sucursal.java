package com.massoftware.model;

import org.cendra.common.model.EntityId;

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
import com.massoftware.annotation.model.FieldMaxValueIntegerAnont;
import com.massoftware.annotation.model.FieldMinValueIntegerAnont;
import com.massoftware.annotation.model.FieldNameMSAnont;
import com.massoftware.annotation.model.FieldOptionsIntegerAnont;
import com.massoftware.annotation.model.FieldRequiredAnont;
import com.massoftware.annotation.model.FieldSubNameFKAnont;
import com.massoftware.annotation.model.FieldUniqueAnont;

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Sucursal")
@ClassPluralLabelAnont(value = "Sucursales")
@ClassArticleLabelInTheSingularAnont(value = "la")
@ClassArticleLabelInPluralAnont(value = "las")
@ClassFormSourceAnont(value = "Sucursal")
@ClassTableMSAnont(nameTableDB = "[Sucursales]")
public class Sucursal extends EntityId implements Cloneable,
		Comparable<Sucursal> {

	@FieldLabelAnont(value = "Sucursal")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 2)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = 99)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[SUCURSAL]", classAttDB = Integer.class)
	private Integer codigo;

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 35)
	@FieldColumnsAnont(value = 35)
	@FieldColumnMetaDataAnont(attSize = 350)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "Abreviatura")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 4)
	@FieldColumnsAnont(value = 5)
	@FieldColumnMetaDataAnont(attSize = 80)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[ABREVIATURA]", classAttDB = String.class)
	private String abreviatura;

	@FieldLabelAnont(value = "Tipo")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 200)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[TIPOSUCURSAL]", classAttDB = Integer.class)
	private SucursalTipo sucursalTipo;

	@FieldLabelAnont(value = "Cuenta clientes - Desde", visible = false)
	@FieldMaxLengthAnont(value = 6)
	@FieldColumnsAnont(value = 6)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[CUENTASCLIENTESDESDE]", classAttDB = String.class)
	private String cuentaClientesDesde;

	@FieldLabelAnont(value = "Cuenta clientes - Hasta", visible = false)
	@FieldMaxLengthAnont(value = 6)
	@FieldColumnsAnont(value = 6)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[CUENTASCLIENTESHASTA]", classAttDB = String.class)
	private String cuentaClientesHasta;

	@FieldLabelAnont(value = "Cuenta clientes - Cant. caracteres", visible = false)	
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldOptionsIntegerAnont(values = { 3, 4, 5, 6 }, defaultValue = 6)
	@FieldNameMSAnont(nameAttDB = "[CANTIDADCARACTERESCLIENTES]", classAttDB = Integer.class)
	private Integer cantidadCaracteresClientes;

	@FieldLabelAnont(value = "Identificación numérica", visible = true)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[NUMERICOCLIENTES]", classAttDB = Boolean.class)
	private Boolean identificacionNumericaClientes;

	@FieldLabelAnont(value = "Permite cambiar", visible = true)
	@FieldNameMSAnont(nameAttDB = "[PERMITECAMBIARCLIENTES]", classAttDB = Boolean.class)
	private Boolean permiteCambiarClientes;

	@FieldLabelAnont(value = "Clientes ocacionales - Desde", visible = false)
	@FieldMaxLengthAnont(value = 9)
	@FieldColumnsAnont(value = 9)
	@FieldMinValueIntegerAnont(value = -99999999)
	@FieldMaxValueIntegerAnont(value = Integer.MAX_VALUE)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[CUENTASCLIENTESOCASIONALESDESDE]", classAttDB = Integer.class)
	private Integer clientesOcasionalesDesde;

	@FieldLabelAnont(value = "Clientes ocacionales - Hasta", visible = false)
	@FieldMaxLengthAnont(value = 9)
	@FieldColumnsAnont(value = 9)
	@FieldMinValueIntegerAnont(value = -99999999)
	@FieldMaxValueIntegerAnont(value = Integer.MAX_VALUE)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[CUENTASCLIENTESOCASIONALESHASTA]", classAttDB = Integer.class)
	private Integer clientesOcasionalesHasta;

	@FieldLabelAnont(value = "Nro. cobranza - Desde", visible = false)
	@FieldMaxLengthAnont(value = 6)
	@FieldColumnsAnont(value = 6)
	@FieldMinValueIntegerAnont(value = Short.MIN_VALUE)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[NROCOBRANZADESDE]", classAttDB = Integer.class)
	private Integer nroCobranzaDesde;

	@FieldLabelAnont(value = "Nro. cobranza - Hasta", visible = false)
	@FieldMaxLengthAnont(value = 6)
	@FieldColumnsAnont(value = 6)
	@FieldMinValueIntegerAnont(value = Short.MIN_VALUE)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[NROCOBRANZAHASTA]", classAttDB = Integer.class)
	private Integer nroCobranzaHasta;

	@FieldLabelAnont(value = "Proveedores - Desde", visible = false)
	@FieldMaxLengthAnont(value = 6)
	@FieldColumnsAnont(value = 6)
	@FieldUniqueAnont(value = false)
	@FieldNameMSAnont(nameAttDB = "[CUENTASPROVEEDORESDESDE]", classAttDB = String.class)
	private String proveedoresDesde;

	@FieldLabelAnont(value = "Proveedores - Hasta", visible = false)
	@FieldMaxLengthAnont(value = 6)
	@FieldColumnsAnont(value = 6)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[CUENTASPROVEEDORESHASTA]", classAttDB = String.class)
	private String proveedoresHasta;

	@FieldLabelAnont(value = "Proveedores - Cant. caracteres", visible = false)
	@FieldOptionsIntegerAnont(values = { 3, 4, 5, 6 }, defaultValue = 6)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[CANTIDADCARACTERESPROVEEDOR]", classAttDB = Integer.class)
	private Integer cantidadCaracteresProveedor;

	@FieldLabelAnont(value = "Identificación numérica", visible = true)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[NUMERICOPROVEEDOR]", classAttDB = Boolean.class)
	private Boolean identificacionNumericaProveedores;

	@FieldLabelAnont(value = "Permite cambiar", visible = true)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[PERMITECAMBIARPROVEEDOR]", classAttDB = Boolean.class)
	private Boolean permiteCambiarProveedores;

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

	public SucursalTipo getSucursalTipo() {
		return sucursalTipo;
	}

	public void setSucursalTipo(SucursalTipo sucursalTipo) {
		if (sucursalTipo != null && sucursalTipo.getId() == null) {
			return;
		}
		this.sucursalTipo = sucursalTipo;
	}

	public String getCuentaClientesDesde() {
		cuentaClientesDesde = formatValue(cuentaClientesDesde);
		return cuentaClientesDesde;
	}

	public void setCuentaClientesDesde(String cuentaClientesDesde) {
		cuentaClientesDesde = formatValue(cuentaClientesDesde);
		this.cuentaClientesDesde = cuentaClientesDesde;
	}

	public String getCuentaClientesHasta() {
		cuentaClientesHasta = formatValue(cuentaClientesHasta);
		return cuentaClientesHasta;
	}

	public void setCuentaClientesHasta(String cuentaClientesHasta) {
		cuentaClientesHasta = formatValue(cuentaClientesHasta);
		this.cuentaClientesHasta = cuentaClientesHasta;
	}

	public Integer getCantidadCaracteresClientes() {
		if (cantidadCaracteresClientes == null) {
			cantidadCaracteresClientes = 6;
		}
		return cantidadCaracteresClientes;
	}

	public void setCantidadCaracteresClientes(Integer cantidadCaracteresClientes) {
		if (cantidadCaracteresClientes == null) {
			cantidadCaracteresClientes = 6;
		}
		this.cantidadCaracteresClientes = cantidadCaracteresClientes;
	}

	public Boolean getIdentificacionNumericaClientes() {
		identificacionNumericaClientes = this
				.nullIsFalse(identificacionNumericaClientes);
		return identificacionNumericaClientes;
	}

	public void setIdentificacionNumericaClientes(
			Boolean identificacionNumericaClientes) {
		identificacionNumericaClientes = this
				.nullIsFalse(identificacionNumericaClientes);
		this.identificacionNumericaClientes = identificacionNumericaClientes;
	}

	public Boolean getPermiteCambiarClientes() {
		permiteCambiarClientes = this.nullIsFalse(permiteCambiarClientes);
		return permiteCambiarClientes;
	}

	public void setPermiteCambiarClientes(Boolean permiteCambiarClientes) {
		permiteCambiarClientes = this.nullIsFalse(permiteCambiarClientes);
		this.permiteCambiarClientes = permiteCambiarClientes;
	}

	public Integer getClientesOcasionalesDesde() {
		return clientesOcasionalesDesde;
	}

	public void setClientesOcasionalesDesde(Integer clientesOcasionalesDesde) {
		this.clientesOcasionalesDesde = clientesOcasionalesDesde;
	}

	public Integer getClientesOcasionalesHasta() {
		return clientesOcasionalesHasta;
	}

	public void setClientesOcasionalesHasta(Integer clientesOcasionalesHasta) {
		this.clientesOcasionalesHasta = clientesOcasionalesHasta;
	}

	public Integer getNroCobranzaDesde() {
		return nroCobranzaDesde;
	}

	public void setNroCobranzaDesde(Integer nroCobranzaDesde) {
		this.nroCobranzaDesde = nroCobranzaDesde;
	}

	public Integer getNroCobranzaHasta() {
		return nroCobranzaHasta;
	}

	public void setNroCobranzaHasta(Integer nroCobranzaHasta) {
		this.nroCobranzaHasta = nroCobranzaHasta;
	}

	public String getProveedoresDesde() {
		proveedoresDesde = formatValue(proveedoresDesde);
		return proveedoresDesde;
	}

	public void setProveedoresDesde(String proveedoresDesde) {
		proveedoresDesde = formatValue(proveedoresDesde);
		this.proveedoresDesde = proveedoresDesde;
	}

	public String getProveedoresHasta() {
		proveedoresHasta = formatValue(proveedoresHasta);
		return proveedoresHasta;
	}

	public void setProveedoresHasta(String proveedoresHasta) {
		proveedoresHasta = formatValue(proveedoresHasta);
		this.proveedoresHasta = proveedoresHasta;
	}

	public Integer getCantidadCaracteresProveedor() {
		if (cantidadCaracteresProveedor == null) {
			cantidadCaracteresProveedor = 6;
		}
		return cantidadCaracteresProveedor;
	}

	public void setCantidadCaracteresProveedor(
			Integer cantidadCaracteresProveedor) {
		if (cantidadCaracteresProveedor == null) {
			cantidadCaracteresProveedor = 6;
		}
		this.cantidadCaracteresProveedor = cantidadCaracteresProveedor;
	}

	public Boolean getIdentificacionNumericaProveedores() {
		identificacionNumericaProveedores = this
				.nullIsFalse(identificacionNumericaProveedores);
		return identificacionNumericaProveedores;
	}

	public void setIdentificacionNumericaProveedores(
			Boolean identificacionNumericaProveedores) {
		identificacionNumericaProveedores = this
				.nullIsFalse(identificacionNumericaProveedores);
		this.identificacionNumericaProveedores = identificacionNumericaProveedores;
	}

	public Boolean getPermiteCambiarProveedores() {
		permiteCambiarProveedores = this.nullIsFalse(permiteCambiarProveedores);
		return permiteCambiarProveedores;
	}

	public void setPermiteCambiarProveedores(Boolean permiteCambiarProveedores) {
		permiteCambiarProveedores = this.nullIsFalse(permiteCambiarProveedores);
		this.permiteCambiarProveedores = permiteCambiarProveedores;
	}

	@Override
	public Sucursal clone() throws CloneNotSupportedException {

		Sucursal other = new Sucursal();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());
		other.setAbreviatura(this.getAbreviatura());
		if (this.getSucursalTipo() != null) {
			other.setSucursalTipo(this.getSucursalTipo().clone());
		} else {
			other.setSucursalTipo(null);
		}
		other.setCuentaClientesDesde(this.getCuentaClientesDesde());
		other.setCuentaClientesHasta(this.getCuentaClientesHasta());
		other.setCantidadCaracteresClientes(this
				.getCantidadCaracteresClientes());
		other.setIdentificacionNumericaClientes(this
				.getIdentificacionNumericaClientes());
		other.setPermiteCambiarClientes(this.getPermiteCambiarClientes());
		other.setClientesOcasionalesDesde(this.getClientesOcasionalesDesde());
		other.setClientesOcasionalesHasta(this.getClientesOcasionalesHasta());
		other.setNroCobranzaDesde(this.getNroCobranzaDesde());
		other.setNroCobranzaHasta(this.getNroCobranzaHasta());
		other.setProveedoresDesde(this.getProveedoresDesde());
		other.setProveedoresHasta(this.getProveedoresHasta());
		other.setCantidadCaracteresProveedor(this
				.getCantidadCaracteresProveedor());
		other.setIdentificacionNumericaProveedores(this
				.getIdentificacionNumericaProveedores());
		other.setPermiteCambiarProveedores(this.getPermiteCambiarProveedores());

		return other;
	}

	@Override
	public int compareTo(Sucursal o) {

		// if (this.getCodigo() == null && o.getCodigo() == null)
		// return 0;// EQUAL; // make null==null
		//
		// if (this.getCodigo() == null)
		// return -1; // THIS_IS_LESS; // this null < other not null
		//
		// if (o.getCodigo() == null)
		// return 1; // THIS_IS_GREATER; // this not null > other null

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

		if (this.sucursalTipo == null || this.getSucursalTipo().getId() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".sucursalTipo es nulo.");
		}

		// if (this.cantidadCaracteresClientes == null) {
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName()
		// + ".cantidadCaracteresClientes es nulo.");
		// }

		// if (this.cantidadCaracteresProveedor == null) {
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName()
		// + ".cantidadCaracteresProveedor es nulo.");
		// }

		return true;
	}

}
