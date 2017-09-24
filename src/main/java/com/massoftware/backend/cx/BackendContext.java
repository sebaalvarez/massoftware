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

import com.massoftware.backend.bo.CentroDeCostoContableBO;
import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.bo.IPuntoDeEquilibrioBO;
import com.massoftware.backend.bo.PlanDeCuentaBO;
import com.massoftware.backend.bo.PuntoDeEquilibrioBO;
import com.massoftware.backend.bo.UsuarioBO;
import com.massoftware.backend.dao.PlanDeCuentaDAO;
import com.massoftware.backend.dao.PuntoDeEquilibrioDAO;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.PuntoDeEquilibrioTipo;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class BackendContext extends AbstractContext {

	private DataSourceProperties dataSourceProperties;
	private DataSource dataSource;
	private DataSourceWrapper dataSourceWrapper;
	private Map<String, EntityMetaData> entityMetaDataMap = new HashMap<String, EntityMetaData>();

	public BackendContext(String type) {
		super();
		try {
			// init("Postgresql");
			init(type);
			initMetaData();

			// buildPlanDeCuentaBO().findAllOrderByCuentaContable(null, null);

		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

	}

	private void initMetaData() {
		entityMetaDataMap = new HashMap<String, EntityMetaData>();

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

		EntityMetaData centroDeCostoContableMD = new EntityMetaData();
		centroDeCostoContableMD.setName(CentroDeCostoContable.class
				.getCanonicalName());
		centroDeCostoContableMD.setLabel("Centro de costo contable");
		centroDeCostoContableMD.setLabelPlural("Centros de costos contables");
		centroDeCostoContableMD.addAtt("ejercicioContable",
				ejercicioContableMD.getLabelShort(),
				ejercicioContableMD.getLabel());
		centroDeCostoContableMD.addAtt("numero", "C.C.",
				"Centro de costo contable");
		centroDeCostoContableMD.addAtt("nombre", "Nomnre");
		centroDeCostoContableMD.addAtt("abreviatura", "Abreviatura");
		entityMetaDataMap.put(centroDeCostoContableMD.getName(),
				centroDeCostoContableMD);

		EntityMetaData puntoDeEquilibrioMD = new EntityMetaData();
		puntoDeEquilibrioMD.setName(PuntoDeEquilibrio.class.getCanonicalName());
		puntoDeEquilibrioMD.setLabel("Punto de equilibrio");
		puntoDeEquilibrioMD.setLabelPlural("Puntos de equilibrio");
		// puntoDeEquilibrioMD.addAtt("ejercicioContable",
		// ejercicioContableMD.getLabelShort(),
		// ejercicioContableMD.getLabel());
		puntoDeEquilibrioMD.addAtt("puntoDeEquilibrio", "Pto. de Equ.",
				"Punto de equilibrio");
		puntoDeEquilibrioMD.addAtt("nombre", "Nombre");
		puntoDeEquilibrioMD.addAtt("tipo", "Tipo");
		// puntoDeEquilibrioMD.addAtt("ejercicio", "Ejercicio");
		entityMetaDataMap.put(puntoDeEquilibrioMD.getName(),
				puntoDeEquilibrioMD);

		EntityMetaData puntoDeEquilibrioTipoMD = new EntityMetaData();
		puntoDeEquilibrioTipoMD.setName(PuntoDeEquilibrioTipo.class
				.getCanonicalName());
		puntoDeEquilibrioTipoMD.setLabel("Tipo de punto de equilibrio");
		puntoDeEquilibrioTipoMD.setLabelPlural("Tipos de punto de equilibrio");
		puntoDeEquilibrioTipoMD.addAtt("tipo", "Tipo");
		puntoDeEquilibrioTipoMD.addAtt("nombre", "Nombre");
		entityMetaDataMap.put(puntoDeEquilibrioTipoMD.getName(),
				puntoDeEquilibrioTipoMD);

	}

	private void init(String dbType) throws Exception {

		if (dbType.equals("Postgresql")) {
			initContextDbPostgreSql(new LogPrinter());
		} else if (dbType.equals("sqlserver")) {
			initContextDbMsSqlServer(new LogPrinter());
		}

		String msg = "\n\n[Ok] " + ZonedDateTime.now()
				+ " Services Server Context start !!!\n\n";

		new LogPrinter().print(this.getClass().getName(),
				LogPrinter.LEVEL_INFO, msg);
	}

	private void initContextDbMsSqlServer(LogPrinter errorPrinter)
			throws Exception {

		SQLServerDataSource ds = new SQLServerDataSource();
		// ds.setIntegratedSecurity(true);
		ds.setServerName("localhost");
		ds.setPortNumber(1433);
		ds.setDatabaseName("VetaroRep");
		ds.setUser("sa");
		ds.setPassword("cordoba");

		Properties properties = new Properties();

		properties.put("jdbc.driverClassName",
				"com.microsoft.sqlserver.jdbc.SQLServerDriver");
		properties.put("jdbc.userName", "");
		properties.put("jdbc.userPassword", "");
		properties.put("jdbc.url", ds.getURL());
		properties.put("jdbc.maxActive", "10");
		properties.put("jdbc.initialSize", "5");
		properties.put("jdbc.maxIdle", "5");
		properties.put("jdbc.validationQuery", "SELECT 1");
		properties.put("jdbc.verbose", "true");

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

		// BasicDataSource basicDataSource = new BasicDataSource();
		// basicDataSource.setDriverClassName(dataSourceProperties
		// .getDriverClassName());
		// basicDataSource.setUrl(dataSourceProperties.getUrl());
		// basicDataSource.setUsername(dataSourceProperties.getUserName());
		// basicDataSource.setPassword(dataSourceProperties.getUserPassword());
		// basicDataSource.setInitialSize(dataSourceProperties.getInitialSize());
		// basicDataSource.setMaxActive(dataSourceProperties.getMaxActive());
		// basicDataSource.setMaxIdle(dataSourceProperties.getMaxIdle());
		// basicDataSource.setValidationQuery(dataSourceProperties
		// .getValidationQuery());

		dataSource = ds;

		dataSourceWrapper = new DataSourceWrapper(dataSource,
				dataSourceProperties, new LogPrinter());

	}

	private void initContextDbPostgreSql(LogPrinter errorPrinter)
			throws Exception {

		Properties properties = new Properties();

		properties.put("jdbc.driverClassName", "org.postgresql.Driver");
		properties.put("jdbc.userName", "postgres");
		properties.put("jdbc.userPassword", "cordoba");
		properties
				.put("jdbc.url",
						"jdbc:postgresql://localhost:5432/massoftware?searchpath=massoftware");
		properties.put("jdbc.maxActive", "10");
		properties.put("jdbc.initialSize", "5");
		properties.put("jdbc.maxIdle", "5");
		properties.put("jdbc.validationQuery", "SELECT 1");
		properties.put("jdbc.verbose", "true");

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

	public IPuntoDeEquilibrioBO buildPuntoDeEquilibrioBO() {

		try {

			buildPlanDeCuentaBO().findAllOrderByCuentaContable(null, null);
			return new PuntoDeEquilibrioBO(new PuntoDeEquilibrioDAO(
					dataSourceWrapper));
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
			return new PlanDeCuentaBO(new PlanDeCuentaDAO(dataSourceWrapper));
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

}
