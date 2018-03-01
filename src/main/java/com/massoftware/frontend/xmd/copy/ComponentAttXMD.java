package com.massoftware.frontend.xmd.copy;

class ComponentAttXMD extends ComponentXMD {

	@SuppressWarnings("rawtypes")
	private Class classModel;
	private String attName;

	@SuppressWarnings("rawtypes")
	public Class getClassModel() {
		return classModel;
	}

	@SuppressWarnings("rawtypes")
	public void setClassModel(Class classModel) {
		this.classModel = classModel;
	}

	public String getAttName() {
		return attName;
	}

	public void setAttName(String attName) {
		this.attName = attName;
	}

}
