package org.cendra.commons.util.error;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.cendra.jdbc.SQLExceptionWrapper;

class InfoException {

	private String timeException = "unknown";
	private String nameException = "unknown";
	private String nameExceptionWrapper = "unknown";
	private String title = "unknown";
	private String subject = "unknown";
	private String cause = "unknown";
	private String message = "unknown";
	private InfoTraceElement firstTrace;
	private InfoDb infoDb;
	private List<InfoTraceElement> stackTrace = new ArrayList<InfoTraceElement>();

	public InfoException(Exception exception) {

		timeException = ZonedDateTime.now().toString();
		nameException = exception.getClass().toString();
		if (exception.getCause() != null) {
			cause = exception.getCause().toString();
		}
		if (exception.getMessage() != null) {
			message = exception.getMessage();
		}

		for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
			InfoTraceElement infoTraceElement = new InfoTraceElement();

			infoTraceElement.setFileName(stackTraceElement.getFileName());
			infoTraceElement.setLineNumber(stackTraceElement.getLineNumber());
			infoTraceElement.setClassName(stackTraceElement.getClassName());
			infoTraceElement.setMethodName(stackTraceElement.getMethodName());
			infoTraceElement
					.setNativeMethod(stackTraceElement.isNativeMethod());

			if (infoTraceElement.getClassName().startsWith("com.massoftware.")
					|| infoTraceElement.getClassName()
							.startsWith("org.cendra.")) {
				stackTrace.add(infoTraceElement);

				if (firstTrace == null) {
					firstTrace = infoTraceElement;
				}
			}

		}

		if (exception instanceof SQLExceptionWrapper) {
			SQLExceptionWrapper sQLExceptionWrapper = (SQLExceptionWrapper) exception;

			timeException = sQLExceptionWrapper.getTime().toString();

			nameExceptionWrapper = sQLExceptionWrapper.getSQLException()
					.getClass().toString();

			if (sQLExceptionWrapper.getTitle() != null) {
				title = sQLExceptionWrapper.getTitle();
			}
			if (sQLExceptionWrapper.getSubject() != null) {
				subject = sQLExceptionWrapper.getSubject();
			}

			infoDb = new InfoDb();

			infoDb.setDriverClassName(sQLExceptionWrapper.getDriverClassName());
			infoDb.setInitialSize(sQLExceptionWrapper.getInitialSize() + "");
			infoDb.setMaxActive(sQLExceptionWrapper.getMaxActive() + "");
			infoDb.setMaxIdle(sQLExceptionWrapper.getMaxIdle() + "");
			infoDb.setValidationQuery(sQLExceptionWrapper.getValidationQuery());
			infoDb.setUrl(sQLExceptionWrapper.getUrl());
			infoDb.setUserName(sQLExceptionWrapper.getUserName());

			infoDb.setDatabaseProductName(sQLExceptionWrapper
					.getDatabaseProductName());
			infoDb.setDatabaseProductVersion(sQLExceptionWrapper
					.getDatabaseProductVersion());
			infoDb.setDriverName(sQLExceptionWrapper.getDriverName());
			infoDb.setDriverVersion(sQLExceptionWrapper.getDriverVersion());
			infoDb.setjDBCMajorVersion(sQLExceptionWrapper
					.getjDBCMajorVersion() + "");
			infoDb.setjDBCMinorVersion(sQLExceptionWrapper
					.getjDBCMinorVersion() + "");
			infoDb.setOperationType(sQLExceptionWrapper.getOperationType());
			infoDb.setGetErrorCode(sQLExceptionWrapper.getErrorCode() + "");
			infoDb.setSqlState(sQLExceptionWrapper.getSQLState());
			infoDb.setSqlStatements(sQLExceptionWrapper.getSqlStatements());

		}
	}

	public String getTimeException() {
		return timeException;
	}

	public void setTimeException(String timeException) {
		this.timeException = timeException;
	}

	public String getNameException() {
		return nameException;
	}

	public void setNameException(String nameException) {
		this.nameException = nameException;
	}

	public String getNameExceptionWrapper() {
		return nameExceptionWrapper;
	}

	public void setNameExceptionWrapper(String nameExceptionWrapper) {
		this.nameExceptionWrapper = nameExceptionWrapper;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<InfoTraceElement> getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(List<InfoTraceElement> stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public InfoTraceElement getFirstTrace() {
		return firstTrace;
	}

	public void setFirstTrace(InfoTraceElement firstTrace) {
		this.firstTrace = firstTrace;
	}

	public InfoDb getInfoDb() {
		return infoDb;
	}

	public void setInfoDb(InfoDb infoDb) {
		this.infoDb = infoDb;
	}

}
