package com.massoftware.model;

import org.cendra.common.model.EntityId;

public class Sucursal extends EntityId implements Cloneable,
		Comparable<Sucursal> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3012073096976059386L;

	private Integer codigo;
	private String nombre;
	private String abreviatura;

	private SucursalTipo sucursalTipo;

	private String cuentaClientesDesde;
	private String cuentaClientesHasta;
	private Integer cantidadCaracteresClientes;
	private Boolean identificacionNumericaClientes;
	private Boolean permiteCambiarClientes;

	private Integer clientesOcasionalesDesde;
	private Integer clientesOcasionalesHasta;

	private Integer nroCobranzaDesde;
	private Integer nroCobranzaHasta;

	private String proveedoresDesde;
	private String proveedoresHasta;
	private Integer cantidadCaracteresProveedor;
	private Boolean identificacionNumericaProveedores;
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

		if (this.sucursalTipo == null
				|| this.getSucursalTipo().getId() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".sucursalTipo es nulo.");
		}

		if (this.cantidadCaracteresClientes == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName()
					+ ".cantidadCaracteresClientes es nulo.");
		}

		if (this.cantidadCaracteresProveedor == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName()
					+ ".cantidadCaracteresProveedor es nulo.");
		}

		return true;
	}

}
