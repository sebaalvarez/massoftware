package com.massoftware.backend.cx;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.cendra.common.model.EntityMetaData;
import org.cendra.cx.AbstractContext;
import org.cendra.jdbc.DataSourceProperties;
import org.cendra.jdbc.DataSourceWrapper;
import org.cendra.log.LogPrinter;

import com.massoftware.backend.bo.AsientoModeloBO;
import com.massoftware.backend.bo.CentroDeCostoContableBO;
import com.massoftware.backend.bo.CostoDeVentaBO;
import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.bo.PlanDeCuentaBO;
import com.massoftware.backend.bo.PlanDeCuentaEstadoBO;
import com.massoftware.backend.bo.PuntoDeEquilibrioBO;
import com.massoftware.backend.bo.PuntoDeEquilibrioTipoBO;
import com.massoftware.backend.bo.UsuarioBO;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaContableEstado;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.PuntoDeEquilibrioTipo;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class BackendContext extends AbstractContext {

	public final static String MS = "sqlserver";
	public final static String PG = "Postgresql";

	private DataSourceProperties dataSourceProperties;
	private DataSource dataSource;
	private DataSourceWrapper dataSourceWrapper;
	private Map<String, EntityMetaData> entityMetaDataMap = new HashMap<String, EntityMetaData>();

	public BackendContext() {

	}

	public void start(String type, Properties properties) {

		try {
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

	public EntityMetaData getEntityMetaData(String key) {
		return entityMetaDataMap.get(key);
	}

	public EjercicioContableBO buildEjercicioContableBO() {

		try {

			return new EjercicioContableBO(dataSourceWrapper);
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

	public CentroDeCostoContableBO buildCentroDeCostoContableBO() {

		try {
			return new CentroDeCostoContableBO(dataSourceWrapper);
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

	public PuntoDeEquilibrioTipoBO buildPuntoDeEquilibrioTipoBO() {

		try {

			return new PuntoDeEquilibrioTipoBO(dataSourceWrapper);
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

	public PuntoDeEquilibrioBO buildPuntoDeEquilibrioBO() {

		try {

			return new PuntoDeEquilibrioBO(dataSourceWrapper);
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

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

	public UsuarioBO buildUsuarioBO() {

		try {
			return new UsuarioBO(dataSourceWrapper);
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

	public PlanDeCuentaBO buildPlanDeCuentaBO() {

		try {
			return new PlanDeCuentaBO(dataSourceWrapper);
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

}
