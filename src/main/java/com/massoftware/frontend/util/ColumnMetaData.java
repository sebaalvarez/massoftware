package com.massoftware.frontend.util;


public class ColumnMetaData {

	private String attName;
	private String attLabel;
	private Integer attSize;
	@SuppressWarnings("rawtypes")
	private Class attClass;
	private String simpleStringTraslateFilterMode = SimpleStringTraslateFilter.CONTAINS_WORDS_AND;
	private boolean pidFilteringStart;
	private Boolean hidden;

	@SuppressWarnings("rawtypes")
	public ColumnMetaData(String attName, String attLabel, Integer attSize,
			Class attClass, String simpleStringTraslateFilterMode,
			boolean pidFilteringStart, Boolean hidden) {
		super();
		this.attName = attName;
		this.attLabel = attLabel;
		this.attSize = attSize;
		this.attClass = attClass;
		this.simpleStringTraslateFilterMode = simpleStringTraslateFilterMode;
		this.pidFilteringStart = pidFilteringStart;
		this.hidden = hidden;
	}

	@SuppressWarnings("rawtypes")
	public ColumnMetaData(String attName, String attLabel, Integer attSize,
			Class attClass, String simpleStringTraslateFilterMode,
			boolean pidFilteringStart) {
		super();
		this.attName = attName;
		this.attLabel = attLabel;
		this.attSize = attSize;
		this.attClass = attClass;
		this.simpleStringTraslateFilterMode = simpleStringTraslateFilterMode;
		this.pidFilteringStart = pidFilteringStart;
		this.hidden = false;
	}

	public String getAttName() {
		return attName;
	}

	public void setAttName(String attName) {
		this.attName = attName;
	}

	public String getAttLabel() {
		return attLabel;
	}

	public void setAttLabel(String attLabel) {
		this.attLabel = attLabel;
	}

	public Integer getAttSize() {
		return attSize;
	}

	public void setAttSize(Integer attSize) {
		this.attSize = attSize;
	}

	@SuppressWarnings("rawtypes")
	public Class getAttClass() {
		return attClass;
	}

	@SuppressWarnings("rawtypes")
	public void setAttClass(Class attClass) {
		this.attClass = attClass;
	}

	public String getSimpleStringTraslateFilterMode() {
		return simpleStringTraslateFilterMode;
	}

	public void setSimpleStringTraslateFilterMode(
			String simpleStringTraslateFilterMode) {
		this.simpleStringTraslateFilterMode = simpleStringTraslateFilterMode;
	}

	public boolean isPidFilteringStart() {
		return pidFilteringStart;
	}

	public void setPidFilteringStart(boolean pidFilteringStart) {
		this.pidFilteringStart = pidFilteringStart;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	@Override
	public String toString() {
		return "ColumnMetaData [attName=" + attName + ", attLabel=" + attLabel
				+ ", attSize=" + attSize + ", attClass=" + attClass
				+ ", simpleStringTraslateFilterMode="
				+ simpleStringTraslateFilterMode + ", pidFilteringStart="
				+ pidFilteringStart + "]";
	}

}
