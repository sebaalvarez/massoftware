package org.cendra.common.model;

import java.util.ArrayList;
import java.util.List;

public class EntityMetaData {

	private String name;
	private String label;
	private String labelShort;
	private String labelPlural;
	private String labelShortPlural;
	private List<EntityAttMetaData> atts = new ArrayList<EntityAttMetaData>();

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

	public String getLabelPlural() {
		return labelPlural;
	}

	public void setLabelPlural(String labelPlural) {
		this.labelPlural = labelPlural;
	}

	public String getLabelShortPlural() {
		return labelShortPlural;
	}

	public void setLabelShortPlural(String labelShortPlural) {
		this.labelShortPlural = labelShortPlural;
	}

	public List<EntityAttMetaData> getAtts() {
		return atts;
	}

	public void setAtts(List<EntityAttMetaData> atts) {
		this.atts = atts;
	}

	public void addAtt(EntityAttMetaData att) {
		this.atts.add(att);
	}

	public void addAtt(String name, String label) {
		this.atts.add(new EntityAttMetaData(name, label, label));
	}

	public void addAtt(String name, String labelShort, String label) {
		this.atts.add(new EntityAttMetaData(name, labelShort, label));
	}

	public String[] getAttNames() {
		List<String> values = new ArrayList<String>();
		for (EntityAttMetaData attMD : atts) {
			values.add(attMD.getName());
		}
		String[] r = new String[values.size()];
		r = values.toArray(r);

		return r;
	}

	public String[] getAttShortLabels() {
		List<String> values = new ArrayList<String>();
		for (EntityAttMetaData attMD : atts) {
			values.add(attMD.getLabelShort());
		}
		String[] r = new String[values.size()];
		r = values.toArray(r);

		return r;
	}

	public String[] getAttLabels() {
		List<String> values = new ArrayList<String>();
		for (EntityAttMetaData attMD : atts) {
			values.add(attMD.getLabel());
		}
		String[] r = new String[values.size()];
		r = values.toArray(r);

		return r;
	}

	public String getAttShortLabelByAttName(String attName) {
		for (EntityAttMetaData attMD : atts) {
			if (attMD.getName().equals(attName)) {
				return attMD.getLabelShort();
			}
		}
		return null;
	}
	
	public String getAttLabelByAttName(String attName) {
		for (EntityAttMetaData attMD : atts) {
			if (attMD.getName().equals(attName)) {
				return attMD.getLabel();
			}
		}
		return null;
		
	}

}
