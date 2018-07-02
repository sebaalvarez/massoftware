package org.cendra.commons.util.error;

import java.util.ArrayList;
import java.util.List;

class InfoDb {

	private String driverClassName = "unknown";
	private String initialSize = "unknown";
	private String maxActive = "unknown";
	private String maxIdle = "unknown";
	private String validationQuery = "unknown";
	private String url = "unknown";
	private String userName = "unknown";

	public String databaseProductName = "unknown";
	public String databaseProductVersion = "unknown";
	public String driverName = "unknown";
	public String driverVersion = "unknown";
	public String jDBCMajorVersion = "unknown";
	public String jDBCMinorVersion = "unknown";

	private String operationType = "unknown";

	private String getErrorCode = "unknown";
	private String sqlState = "unknown";
	private List<String> sqlStatements = new ArrayList<String>();

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(String initialSize) {
		this.initialSize = initialSize;
	}

	public String getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(String maxActive) {
		this.maxActive = maxActive;
	}

	public String getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(String maxIdle) {
		this.maxIdle = maxIdle;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDatabaseProductName() {
		return databaseProductName;
	}

	public void setDatabaseProductName(String databaseProductName) {
		this.databaseProductName = databaseProductName;
	}

	public String getDatabaseProductVersion() {
		return databaseProductVersion;
	}

	public void setDatabaseProductVersion(String databaseProductVersion) {
		this.databaseProductVersion = databaseProductVersion;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverVersion() {
		return driverVersion;
	}

	public void setDriverVersion(String driverVersion) {
		this.driverVersion = driverVersion;
	}

	public String getjDBCMajorVersion() {
		return jDBCMajorVersion;
	}

	public void setjDBCMajorVersion(String jDBCMajorVersion) {
		this.jDBCMajorVersion = jDBCMajorVersion;
	}

	public String getjDBCMinorVersion() {
		return jDBCMinorVersion;
	}

	public void setjDBCMinorVersion(String jDBCMinorVersion) {
		this.jDBCMinorVersion = jDBCMinorVersion;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getGetErrorCode() {
		return getErrorCode;
	}

	public void setGetErrorCode(String getErrorCode) {
		this.getErrorCode = getErrorCode;
	}

	public String getSqlState() {
		return sqlState;
	}

	public void setSqlState(String sqlState) {
		this.sqlState = sqlState;
	}

	public List<String> getSqlStatements() {
		return sqlStatements;
	}

	public void setSqlStatements(List<String> sqlStatements) {
		this.sqlStatements = sqlStatements;
	}

}
