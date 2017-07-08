package com.massoftware.backend.cx;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.cendra.commons.AbstractContext;
import org.cendra.commons.model.EntityMetaData;
import org.cendra.commons.util.dao.jdbc.DataSourceProperties;
import org.cendra.commons.util.dao.jdbc.DataSourceWrapper;
import org.cendra.commons.util.dao.jdbc.SQLExceptionWrapper;
import org.cendra.commons.util.error.LogPrinter;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.massoftware.backend.bo.CentroDeCostoContableBO;
import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.bo.ICentroDeCostoContableBO;
import com.massoftware.backend.bo.IEjercicioContableBO;
import com.massoftware.backend.bo.IPuntoDeEquilibrioBO;
import com.massoftware.backend.bo.PuntoDeEquilibrioBO;
import com.massoftware.backend.dao.CentroDeCostoContableDAO;
import com.massoftware.backend.dao.EjercicioContableDAO;
import com.massoftware.backend.dao.PuntoDeEquilibrioDAO;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

public class BackendContext extends AbstractContext {

	private DataSourceProperties dataSourceProperties;
	private DataSource dataSource;
	private DataSourceWrapper dataSourceWrapper;
	private Map<String, EntityMetaData> entityMetaDataMap = new HashMap<String, EntityMetaData>();

	public BackendContext() {
		super();
		try {
			// init("Postgresql");
			init("sqlserver");
			initMetaData();

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
		centroDeCostoContableMD.addAtt("centroDeCostoContable", "C.C.",
				"Centro de costo contable");
		centroDeCostoContableMD.addAtt("nombre", "Nomnre");
		centroDeCostoContableMD.addAtt("abreviatura", "Abreviatura");
		entityMetaDataMap.put(centroDeCostoContableMD.getName(),
				centroDeCostoContableMD);
		
		EntityMetaData puntoDeEquilibrioMD = new EntityMetaData();
		puntoDeEquilibrioMD.setName(PuntoDeEquilibrio.class
				.getCanonicalName());
		puntoDeEquilibrioMD.setLabel("Punto de equilibrio");
		puntoDeEquilibrioMD.setLabelPlural("Puntos de equilibrio");
		puntoDeEquilibrioMD.addAtt("ejercicioContable",
				ejercicioContableMD.getLabelShort(),
				ejercicioContableMD.getLabel());
		puntoDeEquilibrioMD.addAtt("puntoDeEquilibrio", "Pto. de Equ.",
				"Punto de equilibrio");
		puntoDeEquilibrioMD.addAtt("nombre", "Nombre");
		puntoDeEquilibrioMD.addAtt("tipo", "Tipo");
		entityMetaDataMap.put(puntoDeEquilibrioMD.getName(),
				puntoDeEquilibrioMD);

	}

	private void init(String dbType) throws JsonGenerationException,
			JsonMappingException, SQLExceptionWrapper, IOException,
			SQLServerException {

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
			throws SQLExceptionWrapper, JsonGenerationException,
			JsonMappingException, IOException, SQLServerException {

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

		ObjectMapper mapper = new ObjectMapper();
		String msg = "\n\n[..] Conectandose a \n\n"
				+ mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
						dataSourceProperties);

		errorPrinter.print(this.getClass().getName(), LogPrinter.LEVEL_INFO,
				msg);

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
			throws SQLExceptionWrapper, JsonGenerationException,
			JsonMappingException, IOException {

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

		ObjectMapper mapper = new ObjectMapper();
		String msg = "\n\n[..] Conectandose a \n\n"
				+ mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
						dataSourceProperties);

		errorPrinter.print(this.getClass().getName(), LogPrinter.LEVEL_INFO,
				msg);

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

	public IEjercicioContableBO buildEjercicioContableBO() {

		try {
			return new EjercicioContableBO(new EjercicioContableDAO(
					dataSourceWrapper));
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

	public ICentroDeCostoContableBO buildCentroDeCostoContableBO() {

		try {
			return new CentroDeCostoContableBO(new CentroDeCostoContableDAO(
					dataSourceWrapper));
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

	public IPuntoDeEquilibrioBO buildPuntoDeEquilibrioBO() {

		try {
			return new PuntoDeEquilibrioBO(new PuntoDeEquilibrioDAO(
					dataSourceWrapper));
		} catch (Exception e) {
			e.printStackTrace();
			new LogPrinter().print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_FATAL, e);
		}

		return null;

	}

}
