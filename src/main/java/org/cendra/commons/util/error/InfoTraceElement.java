package org.cendra.commons.util.error;

class InfoTraceElement {

	private String fileName;
	private Integer lineNumber;
	private String className;
	private String methodName;
	private Boolean nativeMethod;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Boolean getNativeMethod() {
		return nativeMethod;
	}

	public void setNativeMethod(Boolean nativeMethod) {
		this.nativeMethod = nativeMethod;
	}

}
