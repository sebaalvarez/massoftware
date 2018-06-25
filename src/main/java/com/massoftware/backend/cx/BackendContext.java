package com.massoftware.backend.cx;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.cendra.common.model.EntityMetaData;
import org.cendra.cx.AbstractContext;
import org.cendra.jdbc.DataSourceProperties;
import org.cendra.jdbc.DataSourceWrapper;
import org.cendra.log.LogPrinter;

import com.massoftware.backend.bo.AsientoModeloBO;
import com.massoftware.backend.bo.BancoBO;
import com.massoftware.backend.bo.BancoFirmanteBO;
import com.massoftware.backend.bo.CajaBO;
import com.massoftware.backend.bo.CentroDeCostoContableBO;
import com.massoftware.backend.bo.CentroDeCostoProyectoBO;
import com.massoftware.backend.bo.CentroDeCostoProyectoTipoBO;
import com.massoftware.backend.bo.ChequeraBO;
import com.massoftware.backend.bo.CodigoConvenioMultilateralBO;
import com.massoftware.backend.bo.CostoDeVentaBO;
import com.massoftware.backend.bo.CuentaContableBO;
import com.massoftware.backend.bo.CuentaContableOldBO;
import com.massoftware.backend.bo.CuentaDeFondoABO;
import com.massoftware.backend.bo.CuentaDeFondoBO;
import com.massoftware.backend.bo.CuentaDeFondoGrupoBO;
import com.massoftware.backend.bo.CuentaDeFondoRubroBO;
import com.massoftware.backend.bo.CuentaDeFondoTipoBO;
import com.massoftware.backend.bo.CuentaDeFondoTipoBancoBO;
import com.massoftware.backend.bo.DepositoBO;
import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.bo.JurisdiccionConvenioMultilateralBO;
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
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.Banco;
import com.massoftware.model.BancoFirmante;
import com.massoftware.model.Caja;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CentroDeCostoProyecto;
import com.massoftware.model.CentroDeCostoProyectoTipo;
import com.massoftware.model.Chequera;
import com.massoftware.model.CodigoConvenioMultilateral;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaContableEstado;
import com.massoftware.model.CuentaDeFondo;
import com.massoftware.model.CuentaDeFondoA;
import com.massoftware.model.CuentaDeFondoGrupo;
import com.massoftware.model.CuentaDeFondoRubro;
import com.massoftware.model.CuentaDeFondoTipo;
import com.massoftware.model.CuentaDeFondoTipoBanco;
import com.massoftware.model.Deposito;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.JurisdiccionConvenioMultilateral;
import com.massoftware.model.Modulo;
import com.massoftware.model.Moneda;
import com.massoftware.model.MonedaAFIP;
import com.massoftware.model.MonedaCotizacion;
import com.massoftware.model.MotivoComentario;
import com.massoftware.model.MotivoNotaDeCredito;
import com.massoftware.model.Pais;
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

	private ResourceBundle messages;
	private DataSourceProperties dataSourceProperties;
	private DataSource dataSource;
	private DataSourceWrapper dataSourceWrapper;

	private Map<String, EntityMetaData> entityMetaDataMap = new HashMap<String, EntityMetaData>();

	public BackendContext() {

	}

	public void start(String type, Properties properties) {

		try {
			messages = ResourceBundle.getBundle("MessagesBundle");
			init(type, properties);
			initMetaData();

		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

	}

	private void initMetaData() {
		entityMetaDataMap = new HashMap<String, EntityMetaData>();

		// --------------------------------------------------

		EntityMetaData ejercicioContableMD = new EntityMetaData();
		ejercicioContableMD.setName(EjercicioContable.class.getCanonicalName());
		ejercicioContableMD.setLabel("Ejercicio contable");
		ejercicioContableMD.setLabelShort("Ejercicio");
		ejercicioContableMD.setLabelPlural("Ejercicios contables");

		ejercicioContableMD.addAtt("ejercicio", "Ejercicio");
		ejercicioContableMD.addAtt("fechaApertura", "Apertura",
				"Fecha de apertura");
		ejercicioContableMD.addAtt("fechaCierre", "Cierre", "Fecha de cierre");
		ejercicioContableMD.addAtt("ejercicioCerrado", "Cerrado");
		ejercicioContableMD.addAtt("ejercicioCerradoModulos", "Módulos");
		ejercicioContableMD.addAtt("comentario", "Comentario");

		entityMetaDataMap.put(ejercicioContableMD.getName(),
				ejercicioContableMD);

		// --------------------------------------------------

		EntityMetaData centroDeCostoContableMD = new EntityMetaData();
		centroDeCostoContableMD.setName(CentroDeCostoContable.class
				.getCanonicalName());
		centroDeCostoContableMD.setLabel("Centro de costo contable");
		centroDeCostoContableMD.setLabelShort("Cto. de costo");
		centroDeCostoContableMD.setLabelPlural("Centros de costos contables");

		centroDeCostoContableMD.addAtt("ejercicioContable",
				ejercicioContableMD.getLabelShort(),
				ejercicioContableMD.getLabel());
		centroDeCostoContableMD.addAtt("numero", "C.C.",
				"Centro de costo contable");
		centroDeCostoContableMD.addAtt("nombre", "Nomnbre");
		centroDeCostoContableMD.addAtt("abreviatura", "Abreviatura");

		entityMetaDataMap.put(centroDeCostoContableMD.getName(),
				centroDeCostoContableMD);

		// --------------------------------------------------

		EntityMetaData puntoDeEquilibrioMD = new EntityMetaData();
		puntoDeEquilibrioMD.setName(PuntoDeEquilibrio.class.getCanonicalName());
		puntoDeEquilibrioMD.setLabel("Punto de equilibrio");
		puntoDeEquilibrioMD.setLabelPlural("Puntos de equilibrio");

		puntoDeEquilibrioMD.addAtt("ejercicioContable",
				ejercicioContableMD.getLabelShort(),
				ejercicioContableMD.getLabel());
		puntoDeEquilibrioMD.addAtt("puntoDeEquilibrio", "Pto. de Equ.",
				"Punto de equilibrio");
		puntoDeEquilibrioMD.addAtt("nombre", "Nombre");
		puntoDeEquilibrioMD.addAtt("puntoDeEquilibrioTipo", "Tipo");

		entityMetaDataMap.put(puntoDeEquilibrioMD.getName(),
				puntoDeEquilibrioMD);

		// --------------------------------------------------

		EntityMetaData puntoDeEquilibrioTipoMD = new EntityMetaData();
		puntoDeEquilibrioTipoMD.setName(PuntoDeEquilibrioTipo.class
				.getCanonicalName());
		puntoDeEquilibrioTipoMD.setLabel("Tipo de punto de equilibrio");
		puntoDeEquilibrioTipoMD.setLabelPlural("Tipos de punto de equilibrio");

		puntoDeEquilibrioTipoMD.addAtt("tipo", "Tipo");
		puntoDeEquilibrioTipoMD.addAtt("nombre", "Nombre");

		entityMetaDataMap.put(puntoDeEquilibrioTipoMD.getName(),
				puntoDeEquilibrioTipoMD);

		// --------------------------------------------------

		EntityMetaData planDeCuentaEstadoMD = new EntityMetaData();

		planDeCuentaEstadoMD.setName(CuentaContableEstado.class
				.getCanonicalName());
		planDeCuentaEstadoMD.setLabel("Estado");
		planDeCuentaEstadoMD.setLabelPlural("Estados");

		planDeCuentaEstadoMD.addAtt("codigo", "Codigo");
		planDeCuentaEstadoMD.addAtt("nombre", "Nombre");

		entityMetaDataMap.put(planDeCuentaEstadoMD.getName(),
				planDeCuentaEstadoMD);

		// --------------------------------------------------

		EntityMetaData costoDeVentaMD = new EntityMetaData();

		costoDeVentaMD.setName(CuentaContableEstado.class.getCanonicalName());
		costoDeVentaMD.setLabel("Costo de venta");
		costoDeVentaMD.setLabelPlural("Costos de venta");

		costoDeVentaMD.addAtt("codigo", "Codigo");
		costoDeVentaMD.addAtt("nombre", "Nombre");

		entityMetaDataMap.put(costoDeVentaMD.getName(), costoDeVentaMD);

		// --------------------------------------------------

		EntityMetaData planDeCuentaMD = new EntityMetaData();
		planDeCuentaMD.setName(CuentaContable.class.getCanonicalName());
		planDeCuentaMD.setLabel("Plan de cuenta");
		planDeCuentaMD.setLabelPlural("Plan de cuentas");

		planDeCuentaMD.addAtt("ejercicioContable",
				ejercicioContableMD.getLabelShort(),
				ejercicioContableMD.getLabel());
		planDeCuentaMD.addAtt("codigoCuentaPadre", "Integra");
		planDeCuentaMD.addAtt("codigoCuenta", "Cta. jerarquia",
				"Cuenta de jerarquia");
		planDeCuentaMD.addAtt("cuentaContable", "Cta. contable",
				"Cuenta contable");
		planDeCuentaMD.addAtt("nombre", "Nombre");
		planDeCuentaMD.addAtt("imputable", "Imputable");
		planDeCuentaMD.addAtt("ajustaPorInflacion", "Ajusta por inflación");
		planDeCuentaMD.addAtt("planDeCuentaEstado",
				planDeCuentaEstadoMD.getLabelShort(),
				planDeCuentaEstadoMD.getLabel());
		planDeCuentaMD.addAtt("cuentaConApropiacion", "Cuenta con apropiación");
		planDeCuentaMD.addAtt("centroDeCostoContable",
				centroDeCostoContableMD.getLabelShort(),
				centroDeCostoContableMD.getLabel());
		planDeCuentaMD.addAtt("cuentaAgrupadora", "Cta. agrupadora",
				"Cuenta agrupadora");
		planDeCuentaMD.addAtt("porcentaje", "%", "Porcentaje");
		planDeCuentaMD.addAtt("puntoDeEquilibrio",
				puntoDeEquilibrioMD.getLabelShort(),
				puntoDeEquilibrioMD.getLabel());
		planDeCuentaMD.addAtt("costoDeVenta", costoDeVentaMD.getLabelShort(),
				costoDeVentaMD.getLabel());

		entityMetaDataMap.put(planDeCuentaMD.getName(), planDeCuentaMD);

	}

	private void init(String dbType, Properties properties) throws Exception {

		if (dbType.equals(PG)) {
			initContextDbPostgreSql(new LogPrinter(), properties);
		} else if (dbType.equals(MS)) {
			initContextDbMsSqlServer(new LogPrinter(), properties);
		}

		String msg = "\n\n[Ok] " + ZonedDateTime.now()
				+ " Services Server Context start !!!\n\n";

		new LogPrinter().print(this.getClass().getName(),
				LogPrinter.LEVEL_INFO, msg);
	}

	private void initContextDbMsSqlServer(LogPrinter errorPrinter,
			Properties properties) throws Exception {

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

		dataSourceProperties = new DataSourceProperties();

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

		LogPrinter logPrinter = new LogPrinter();

		logPrinter.printJson(this.getClass().getName(), LogPrinter.LEVEL_INFO,
				"", dataSourceProperties, "");

		dataSource = ds;

		dataSourceWrapper = new DataSourceWrapper(dataSource,
				dataSourceProperties, new LogPrinter());

	}

	private void initContextDbPostgreSql(LogPrinter errorPrinter,
			Properties properties) throws Exception {

		String path = "jdbc.";

		dataSourceProperties = new DataSourceProperties();

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

		LogPrinter logPrinter = new LogPrinter();

		logPrinter.printJson(this.getClass().getName(), LogPrinter.LEVEL_INFO,
				"", dataSourceProperties, "");

		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(dataSourceProperties
				.getDriverClassName());
		basicDataSource.setUrl(dataSourceProperties.getUrl());
		basicDataSource.setUsername(dataSourceProperties.getUserName());
		basicDataSource.setPassword(dataSourceProperties.getUserPassword());
		basicDataSource.setInitialSize(dataSourceProperties.getInitialSize());
		basicDataSource.setMaxActive(dataSourceProperties.getMaxActive());
		basicDataSource.setMaxIdle(dataSourceProperties.getMaxIdle());
		basicDataSource.setValidationQuery(dataSourceProperties
				.getValidationQuery());

		dataSource = basicDataSource;

		dataSourceWrapper = new DataSourceWrapper(dataSource,
				dataSourceProperties, new LogPrinter());

	}

	// -------------------------------------------------------------

	public ResourceBundle getMessages() {
		return messages;
	}

	public String getMessage(String key) {
		return messages.getString(key);
	}

	// -------------------------------------------------------------

	@Deprecated
	public EntityMetaData getEntityMetaData(String key) {
		return entityMetaDataMap.get(key);
	}

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

			}else if (classModel == PuntoDeEquilibrioTipo.class) {

				return new PuntoDeEquilibrioTipoBO(dataSourceWrapper, this);

			}

			else if (classModel == Banco.class) {

				return new BancoBO(dataSourceWrapper, this);

			} else if (classModel == BancoFirmante.class) {

				return new BancoFirmanteBO(dataSourceWrapper, this);

			} else if (classModel == SucursalTipo.class) {

				return new SucursalTipoBO(dataSourceWrapper, this);

			} else if (classModel == Sucursal.class) {

				return new SucursalBO(dataSourceWrapper, this);

			} else if (classModel == Talonario.class) {

				return new TalonarioBO(dataSourceWrapper, this);

			} else if (classModel == SeguridadPuerta.class) {

				return new SeguridadPuertaBO(dataSourceWrapper, this);

			} else if (classModel == Modulo.class) {

				return new ModuloBO(dataSourceWrapper, this);

			} else if (classModel == Deposito.class) {

				return new DepositoBO(dataSourceWrapper, this);

			} else if (classModel == TipoCbteControl.class) {

				return new TipoCbteControlBO(dataSourceWrapper, this);

			} else if (classModel == MonedaAFIP.class) {

				return new MonedaAFIPBO(dataSourceWrapper, this);

			} else if (classModel == Moneda.class) {

				return new MonedaBO(dataSourceWrapper, this);

			} else if (classModel == Caja.class) {

				return new CajaBO(dataSourceWrapper, this);

			} else if (classModel == MonedaCotizacion.class) {

				return new MonedaCotizacionBO(dataSourceWrapper, this);

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

			} else if (classModel == CuentaContable.class) {

				return new CuentaContableBO(dataSourceWrapper, this);

			} else if (classModel == JurisdiccionConvenioMultilateral.class) {

				return new JurisdiccionConvenioMultilateralBO(
						dataSourceWrapper, this);

			} else if (classModel == Ticket.class) {

				return new TicketBO(dataSourceWrapper, this);

			} else if (classModel == CuentaDeFondoTipoBanco.class) {

				return new CuentaDeFondoTipoBancoBO(dataSourceWrapper, this);

			} else if (classModel == SeguridadModulo.class) {

				return new SeguridadModuloBO(dataSourceWrapper, this);

			} else if (classModel == Chequera.class) {

				return new ChequeraBO(dataSourceWrapper, this);

			} else {

				throw new IllegalArgumentException(classModel + " not found");
			}

		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;
	}

	// ================================================================================

	public PlanDeCuentaEstadoBO buildPlanDeCuentaEstadoBO() {

		try {

			return new PlanDeCuentaEstadoBO(dataSourceWrapper);
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

	public CostoDeVentaBO buildCostoDeVentaBO() {

		try {

			return new CostoDeVentaBO(dataSourceWrapper);
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

	public CuentaContableOldBO buildCuentaContableBO() {

		try {
			return new CuentaContableOldBO(dataSourceWrapper);
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

	public AsientoModeloBO buildAsientoModeloBO() {

		try {
			return new AsientoModeloBO(dataSourceWrapper);
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

	public SucursalTipoBO buildSucursalTipoBO() {

		try {
			return new SucursalTipoBO(dataSourceWrapper, this);
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

	public SucursalBO buildSucursalBO() {

		try {
			return new SucursalBO(dataSourceWrapper, this);
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

	public TalonarioBO buildTalonarioBO() {

		try {
			return new TalonarioBO(dataSourceWrapper, this);
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

	public ModuloBO buildModuloBO() {

		try {
			return new ModuloBO(dataSourceWrapper, this);
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

	public ModeloCbteFondoBO buildModeloCbteFondoBO() {

		try {
			return new ModeloCbteFondoBO(dataSourceWrapper);
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

	public ModeloCbteFondoItemConceptoBO buildModeloCbteFondoItemConceptoBO() {

		try {
			return new ModeloCbteFondoItemConceptoBO(dataSourceWrapper);
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

	public ModeloCbteFondoItemBO buildModeloCbteFondoItemBO() {

		try {
			return new ModeloCbteFondoItemBO(dataSourceWrapper);
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

}
