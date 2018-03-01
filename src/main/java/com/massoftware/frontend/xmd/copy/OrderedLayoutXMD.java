package com.massoftware.frontend.xmd.copy;

class OrderedLayoutXMD extends ComponentContainerXMD {

	@SuppressWarnings("rawtypes")
	private Class classModel;

	private boolean margin = true;
	private boolean spacing = true;

	@SuppressWarnings("rawtypes")
	public Class getClassModel() {
		return classModel;
	}

	@SuppressWarnings("rawtypes")
	public void setClassModel(Class classModel) {
		this.classModel = classModel;
	}

	public boolean isMargin() {
		return margin;
	}

	public void setMargin(boolean margin) {
		this.margin = margin;
	}

	public boolean isSpacing() {
		return spacing;
	}

	public void setSpacing(boolean spacing) {
		this.spacing = spacing;
	}

}
