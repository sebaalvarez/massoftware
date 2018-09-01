package com.massoftware.backend;

import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.dbcp.BasicDataSource;
import org.cendra.cx.AbstractContext;
import org.cendra.jdbc.DataSourceProperties;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.bo.AsientoBO;
import com.massoftware.backend.bo.AsientoItemBO;
import com.massoftware.backend.bo.AsientoModeloBO;
import com.massoftware.backend.bo.AsientoModeloItemBO;
import com.massoftware.backend.bo.AsientoModuloBO;
import com.massoftware.backend.bo.BancoBO;
import com.massoftware.backend.bo.BancoFirmanteBO;
import com.massoftware.backend.bo.CajaBO;
import com.massoftware.backend.bo.CentroDeCostoContableBO;
import com.massoftware.backend.bo.CentroDeCostoProyectoBO;
import com.massoftware.backend.bo.CentroDeCostoProyectoTipoBO;
import com.massoftware.backend.bo.ChequeraBO;
import com.massoftware.backend.bo.CiudadBO;
import com.massoftware.backend.bo.CodigoConvenioMultilateralBO;
import com.massoftware.backend.bo.CostoDeVentaBO;
import com.massoftware.backend.bo.CuentaContableBO;
import com.massoftware.backend.bo.CuentaContableEstadoBO;
import com.massoftware.backend.bo.CuentaDeFondoABO;
import com.massoftware.backend.bo.CuentaDeFondoBO;
import com.massoftware.backend.bo.CuentaDeFondoGrupoBO;
import com.massoftware.backend.bo.CuentaDeFondoRubroBO;
import com.massoftware.backend.bo.CuentaDeFondoTipoBO;
import com.massoftware.backend.bo.CuentaDeFondoTipoBancoBO;
import com.massoftware.backend.bo.DepositoBO;
import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.bo.JurisdiccionConvenioMultilateralBO;
import com.massoftware.backend.bo.MinutaContableBO;
import com.massoftware.backend.bo.ModeloCbteFondoBO;
import com.massoftware.backend.bo.ModeloCbteFondoItemBO;
import com.massoftware.backend.bo.ModeloCbteFondoItemConceptoBO;
import com.massoftware.backend.bo.ModuloBO;
import com.massoftware.backend.bo.MonedaAFIPBO;
import com.massoftware.backend.bo.MonedaBO;
import com.massoftware.backend.bo.MonedaCotizacionBO;
import com.massoftware.backend.bo.MotivoComentarioBO;
import com.massoftware.backend.bo.MotivoNotaDeCreditoBO;
import com.massoftware.backend.bo.PaisBO;
import com.massoftware.backend.bo.PlanDeCuentaEstadoBO;
import com.massoftware.backend.bo.ProvinciaBO;
import com.massoftware.backend.bo.PuntoDeEquilibrioBO;
import com.massoftware.backend.bo.PuntoDeEquilibrioTipoBO;
import com.massoftware.backend.bo.SeguridadModuloBO;
import com.massoftware.backend.bo.SeguridadPuertaBO;
import com.massoftware.backend.bo.SucursalBO;
import com.massoftware.backend.bo.SucursalTipoBO;
import com.massoftware.backend.bo.TalonarioBO;
import com.massoftware.backend.bo.TicketBO;
import com.massoftware.backend.bo.TipoCbteAFIPBO;
import com.massoftware.backend.bo.TipoCbteControlBO;
import com.massoftware.backend.bo.TipoClienteBO;
import com.massoftware.backend.bo.TipoDocumentoAFIPBO;
import com.massoftware.backend.bo.UsuarioBO;
import com.massoftware.backend.bo.ZonaBO;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.model.Asiento;
import com.massoftware.model.AsientoItem;
import com.massoftware.model.AsientoModelo;
import com.massoftware.model.AsientoModeloItem;
import com.massoftware.model.AsientoModulo;
import com.massoftware.model.Banco;
import com.massoftware.model.BancoFirmante;
import com.massoftware.model.Caja;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CentroDeCostoProyecto;
import com.massoftware.model.CentroDeCostoProyectoTipo;
import com.massoftware.model.Chequera;
import com.massoftware.model.Ciudad;
import com.massoftware.model.CodigoConvenioMultilateral;
import com.massoftware.model.CostoDeVenta;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaContableEstado;
import com.massoftware.model.CuentaContableFull;
import com.massoftware.model.CuentaDeFondo;
import com.massoftware.model.CuentaDeFondoA;
import com.massoftware.model.CuentaDeFondoGrupo;
import com.massoftware.model.CuentaDeFondoRubro;
import com.massoftware.model.CuentaDeFondoTipo;
import com.massoftware.model.CuentaDeFondoTipoBanco;
import com.massoftware.model.Deposito;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.JurisdiccionConvenioMultilateral;
import com.massoftware.model.MinutaContable;
import com.massoftware.model.Modulo;
import com.massoftware.model.Moneda;
import com.massoftware.model.MonedaAFIP;
import com.massoftware.model.MonedaCotizacion;
import com.massoftware.model.MotivoComentario;
import com.massoftware.model.MotivoNotaDeCredito;
import com.massoftware.model.Pais;
import com.massoftware.model.Provincia;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.PuntoDeEquilibrioTipo;
import com.massoftware.model.SeguridadModulo;
import com.massoftware.model.SeguridadPuerta;
import com.massoftware.model.Sucursal;
import com.massoftware.model.SucursalTipo;
import com.massoftware.model.Talonario;
import com.massoftware.model.Ticket;
import com.massoftware.model.TipoCbteAFIP;
import com.massoftware.model.TipoCbteControl;
import com.massoftware.model.TipoCliente;
import com.massoftware.model.TipoDocumentoAFIP;
import com.massoftware.model.Usuario;
import com.massoftware.model.Zona;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class BackendContext extends AbstractContext {

	public final static String MS = "sqlserver";
	public final static String PG = "Postgresql";

	private DataSourceWrapper dataSourceWrapper;

	public BackendContext() {

	}

	public void start(String type, Properties properties) {

		try {

			init(type, properties);

		} catch (Exception e) {
			printFatal(e);
		}

	}

	private void init(String dbType, Properties properties) throws Exception {

		if (dbType.equals(PG)) {
			initContextDbPostgreSql(properties);
		} else if (dbType.equals(MS)) {
			initContextDbMsSqlServer(properties);
		}
	}

	private void initContextDbMsSqlServer(Properties properties)
			throws Exception {

		String path = "jdbc.";

		SQLServerDataSource ds = new SQLServerDataSource();
		// ds.setIntegratedSecurity(true);
		ds.setServerName(properties.getProperty(path + "serverName"));
		ds.setPortNumber(new Integer(properties
				.getProperty(path + "portNumber")));
		ds.setDatabaseName(properties.getProperty(path + "databaseName"));
		ds.setUser(properties.getProperty(path + "userName"));
		ds.setPassword(properties.getProperty(path + "userPassword"));

		properties.put("jdbc.url", ds.getURL());

		DataSourceProperties dataSourceProperties = new DataSourceProperties();

		dataSourceProperties.setDriverClassName(properties.getProperty(path
				+ "driverClassName"));
		dataSourceProperties.setUrl(properties.getProperty(path + "url"));
		dataSourceProperties.setUserName(properties.getProperty(path
				+ "userName"));
		dataSourceProperties.setUserPassword(properties.getProperty(path
				+ "userPassword"));
		dataSourceProperties.setInitialSize(new Integer(properties
				.getProperty(path + "initialSize")));
		dataSourceProperties.setMaxActive(new Integer(properties
				.getProperty(path + "maxActive")));
		dataSourceProperties.setMaxIdle((new Integer(properties
				.getProperty(path + "maxIdle"))));
		dataSourceProperties.setValidationQuery(properties.getProperty(path
				+ "validationQuery"));
		dataSourceProperties.setVerbose((new Boolean(properties
				.getProperty(path + "verbose"))));

		// dataSource = ds;

		dataSourceWrapper = new DataSourceWrapper(ds, dataSourceProperties);

	}

	private void initContextDbPostgreSql(Properties properties)
			throws Exception {

		String path = "jdbc.";

		DataSourceProperties dataSourceProperties = new DataSourceProperties();

		dataSourceProperties.setDriverClassName(properties.getProperty(path
				+ "driverClassName"));
		dataSourceProperties.setUrl(properties.getProperty(path + "url"));
		dataSourceProperties.setUserName(properties.getProperty(path
				+ "userName"));
		dataSourceProperties.setUserPassword(properties.getProperty(path
				+ "userPassword"));
		dataSourceProperties.setInitialSize(new Integer(properties
				.getProperty(path + "initialSize")));
		dataSourceProperties.setMaxActive(new Integer(properties
				.getProperty(path + "maxActive")));
		dataSourceProperties.setMaxIdle((new Integer(properties
				.getProperty(path + "maxIdle"))));
		dataSourceProperties.setValidationQuery(properties.getProperty(path
				+ "validationQuery"));
		dataSourceProperties.setVerbose((new Boolean(properties
				.getProperty(path + "verbose"))));

		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(dataSourceProperties.getDriverClassName());
		ds.setUrl(dataSourceProperties.getUrl());
		ds.setUsername(dataSourceProperties.getUserName());
		ds.setPassword(dataSourceProperties.getUserPassword());
		ds.setInitialSize(dataSourceProperties.getInitialSize());
		ds.setMaxActive(dataSourceProperties.getMaxActive());
		ds.setMaxIdle(dataSourceProperties.getMaxIdle());
		ds.setValidationQuery(dataSourceProperties.getValidationQuery());

		// dataSource = ds;

		dataSourceWrapper = new DataSourceWrapper(ds, dataSourceProperties);

	}

	// -------------------------------------------------------------

	public ResourceBundle getMessages() {
		return loadResourceBundle("MessagesBundle");
	}

	public String getMessage(String key) {
		return loadResourceBundle("MessagesBundle").getString(key);
	}

	// -------------------------------------------------------------

	// ================================================================================

	@SuppressWarnings("rawtypes")
	public GenericBO buildBO(Class classModel) {

		try {

			if (classModel == Zona.class) {

				return new ZonaBO(dataSourceWrapper, this);

			} else if (classModel == Pais.class) {

				return new PaisBO(dataSourceWrapper, this);

			} else if (classModel == TipoCliente.class) {

				return new TipoClienteBO(dataSourceWrapper, this);

			} else if (classModel == TipoDocumentoAFIP.class) {

				return new TipoDocumentoAFIPBO(dataSourceWrapper, this);

			} else if (classModel == TipoCbteAFIP.class) {

				return new TipoCbteAFIPBO(dataSourceWrapper, this);

			} else if (classModel == MotivoComentario.class) {

				return new MotivoComentarioBO(dataSourceWrapper, this);

			} else if (classModel == MotivoNotaDeCredito.class) {

				return new MotivoNotaDeCreditoBO(dataSourceWrapper, this);

			} else if (classModel == CodigoConvenioMultilateral.class) {

				return new CodigoConvenioMultilateralBO(dataSourceWrapper, this);

			} else if (classModel == CentroDeCostoProyectoTipo.class) {

				return new CentroDeCostoProyectoTipoBO(dataSourceWrapper, this);

			} else if (classModel == CentroDeCostoProyecto.class) {

				return new CentroDeCostoProyectoBO(dataSourceWrapper, this);

			} else if (classModel == EjercicioContable.class) {

				return new EjercicioContableBO(dataSourceWrapper, this);

			} else if (classModel == CentroDeCostoContable.class) {

				return new CentroDeCostoContableBO(dataSourceWrapper, this);

			} else if (classModel == PuntoDeEquilibrio.class) {

				return new PuntoDeEquilibrioBO(dataSourceWrapper, this);

			} else if (classModel == PuntoDeEquilibrioTipo.class) {

				return new PuntoDeEquilibrioTipoBO(dataSourceWrapper, this);

			} else if (classModel == Provincia.class) {

				return new ProvinciaBO(dataSourceWrapper, this);

			} else if (classModel == Ciudad.class) {

				return new CiudadBO(dataSourceWrapper, this);

			} else if (classModel == Banco.class) {

				return new BancoBO(dataSourceWrapper, this);

			} else if (classModel == BancoFirmante.class) {

				return new BancoFirmanteBO(dataSourceWrapper, this);

			} else if (classModel == SucursalTipo.class) {

				return new SucursalTipoBO(dataSourceWrapper, this);

			} else if (classModel == Sucursal.class) {

				return new SucursalBO(dataSourceWrapper, this);

			} else if (classModel == SeguridadPuerta.class) {

				return new SeguridadPuertaBO(dataSourceWrapper, this);

			} else if (classModel == SeguridadModulo.class) {

				return new SeguridadModuloBO(dataSourceWrapper, this);

			} else if (classModel == Caja.class) {

				return new CajaBO(dataSourceWrapper, this);

			} else if (classModel == MonedaAFIP.class) {

				return new MonedaAFIPBO(dataSourceWrapper, this);

			} else if (classModel == Moneda.class) {

				return new MonedaBO(dataSourceWrapper, this);

			} else if (classModel == MonedaCotizacion.class) {

				return new MonedaCotizacionBO(dataSourceWrapper, this,
						new MonedaBO(dataSourceWrapper, this));

			} else if (classModel == AsientoModelo.class) {

				return new AsientoModeloBO(dataSourceWrapper, this);

			} else if (classModel == AsientoModeloItem.class) {

				return new AsientoModeloItemBO(dataSourceWrapper, this);

			} else if (classModel == CuentaContableEstado.class) {

				return new CuentaContableEstadoBO(dataSourceWrapper, this);

			} else if (classModel == CostoDeVenta.class) {

				return new CostoDeVentaBO(dataSourceWrapper, this);

			} else if (classModel == CuentaContable.class
					|| classModel == CuentaContableFull.class) {

				return new CuentaContableBO(dataSourceWrapper, this);

			} else if (classModel == Asiento.class) {

				return new AsientoBO(dataSourceWrapper, this);

			} else if (classModel == AsientoItem.class) {

				return new AsientoItemBO(dataSourceWrapper, this);

			} else if (classModel == MinutaContable.class) {

				return new MinutaContableBO(dataSourceWrapper, this);

			} else if (classModel == AsientoModulo.class) {

				return new AsientoModuloBO(dataSourceWrapper, this);

			}

			else if (classModel == Modulo.class) {

				return new ModuloBO(dataSourceWrapper, this);

			} else if (classModel == Talonario.class) {

				return new TalonarioBO(dataSourceWrapper, this);

			} else if (classModel == Deposito.class) {

				return new DepositoBO(dataSourceWrapper, this);

			} else if (classModel == TipoCbteControl.class) {

				return new TipoCbteControlBO(dataSourceWrapper, this);

			} else if (classModel == Usuario.class) {

				return new UsuarioBO(dataSourceWrapper, this);

			} else if (classModel == CuentaDeFondoRubro.class) {

				return new CuentaDeFondoRubroBO(dataSourceWrapper, this);

			} else if (classModel == CuentaDeFondoGrupo.class) {

				return new CuentaDeFondoGrupoBO(dataSourceWrapper, this);

			} else if (classModel == CuentaDeFondoTipo.class) {

				return new CuentaDeFondoTipoBO(dataSourceWrapper, this);

			} else if (classModel == CuentaDeFondo.class) {

				return new CuentaDeFondoBO(dataSourceWrapper, this);

			} else if (classModel == CuentaDeFondoA.class) {

				return new CuentaDeFondoABO(dataSourceWrapper, this);

			} else if (classModel == JurisdiccionConvenioMultilateral.class) {

				return new JurisdiccionConvenioMultilateralBO(
						dataSourceWrapper, this);

			} else if (classModel == Ticket.class) {

				return new TicketBO(dataSourceWrapper, this);

			} else if (classModel == CuentaDeFondoTipoBanco.class) {

				return new CuentaDeFondoTipoBancoBO(dataSourceWrapper, this);

			} else if (classModel == Chequera.class) {

				return new ChequeraBO(dataSourceWrapper, this);

			} else {

				throw new IllegalArgumentException(classModel + " not found");
			}

		} catch (Exception e) {
			printFatal(e);
		}

		return null;
	}

	// ================================================================================

	public PlanDeCuentaEstadoBO buildPlanDeCuentaEstadoBO() {

		try {

			return new PlanDeCuentaEstadoBO(dataSourceWrapper);
		} catch (Exception e) {
			printFatal(e);
		}

		return null;

	}

	public TalonarioBO buildTalonarioBO() {

		try {
			return new TalonarioBO(dataSourceWrapper, this);
		} catch (Exception e) {
			printFatal(e);
		}

		return null;

	}

	public ModuloBO buildModuloBO() {

		try {
			return new ModuloBO(dataSourceWrapper, this);
		} catch (Exception e) {
			printFatal(e);
		}

		return null;

	}

	public ModeloCbteFondoBO buildModeloCbteFondoBO() {

		try {
			return new ModeloCbteFondoBO(dataSourceWrapper);
		} catch (Exception e) {
			printFatal(e);
		}

		return null;

	}

	public ModeloCbteFondoItemConceptoBO buildModeloCbteFondoItemConceptoBO() {

		try {
			return new ModeloCbteFondoItemConceptoBO(dataSourceWrapper);
		} catch (Exception e) {
			printFatal(e);
		}

		return null;

	}

	public ModeloCbteFondoItemBO buildModeloCbteFondoItemBO() {

		try {
			return new ModeloCbteFondoItemBO(dataSourceWrapper);
		} catch (Exception e) {
			printFatal(e);
		}

		return null;

	}

}
