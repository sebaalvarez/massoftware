package org.cendra.common.model;

public class EntityAttMetaData {

	private String name;
	private String labelShort;
	private String label;

	public EntityAttMetaData(String name, String labelShort, String label) {
		super();
		this.name = name;
		this.labelShort = labelShort;
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabelShort() {
		return labelShort;
	}

	public void setLabelShort(String labelShort) {
		this.labelShort = labelShort;
	}

}
