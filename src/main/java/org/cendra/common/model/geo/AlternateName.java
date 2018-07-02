package org.cendra.common.model.geo;

import org.cendra.common.model.AbstractEntityErasable;

public class AlternateName extends AbstractEntityErasable implements Cloneable,
		Comparable<AlternateName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1062079738187686968L;

	private String alternateName;
	private Boolean preferredName;
	private Boolean shortName;
	private Boolean colloquial;
	private Boolean historic;
	private Language language;

	public String getAlternateName() {
		alternateName = this.formatValue(alternateName);
		return alternateName;
	}

	public void setAlternateName(String alternateName) {
		alternateName = this.formatValue(alternateName);
		this.alternateName = alternateName;
	}

	public Boolean getPreferredName() {
		preferredName = this.nullIsFalse(preferredName);
		return preferredName;
	}

	public void setPreferredName(Boolean preferredName) {
		preferredName = this.nullIsFalse(preferredName);
		this.preferredName = preferredName;
	}

	public Boolean getShortName() {
		shortName = this.nullIsFalse(shortName);
		return shortName;
	}

	public void setShortName(Boolean shortName) {
		shortName = this.nullIsFalse(shortName);
		this.shortName = shortName;
	}

	public Boolean getColloquial() {
		colloquial = this.nullIsFalse(colloquial);
		return colloquial;
	}

	public void setColloquial(Boolean colloquial) {
		colloquial = this.nullIsFalse(colloquial);
		this.colloquial = colloquial;
	}

	public Boolean getHistoric() {
		historic = this.nullIsFalse(historic);
		return historic;
	}

	public void setHistoric(Boolean historic) {
		historic = this.nullIsFalse(historic);
		this.historic = historic;
	}

	public Language getLanguaje() {
		return language;
	}

	public void setLanguaje(Language languaje) {
		this.language = languaje;
	}

	public AlternateName clone() throws CloneNotSupportedException {
		AlternateName other = (AlternateName) super.clone();

		other.setAlternateName(this.getAlternateName());
		other.setPreferredName(this.getPreferredName());
		other.setShortName(this.getShortName());
		other.setColloquial(this.getColloquial());
		other.setHistoric(this.getHistoric());
		if (this.getLanguaje() != null) {
			other.setLanguaje(this.getLanguaje().clone());
		} else {
			other.setLanguaje(null);
		}

		return other;
	}

	public int compareTo(AlternateName o) {

		if (this.getAlternateName() != null && o != null) {
			return this.getAlternateName().compareTo(
					((AlternateName) o).getAlternateName());
		}

		if (this.getAlternateName() != null && o == null) {
			return this.getAlternateName().compareTo("");
		}

		return "".compareTo(((AlternateName) o).getAlternateName());
	}

	@Override
	public String toString() {

		String s = super.toString();

		if (this.getAlternateName() != null && s != null
				&& s.trim().isEmpty() == false) {
			return (s + " (" + this.getAlternateName() + ")").trim();
		} else if (this.getAlternateName() != null
				&& (s == null || s.trim().isEmpty() == true)) {
			return this.getAlternateName().trim();
		} else if (this.getAlternateName() == null && s != null
				&& s.trim().isEmpty() == false) {
			return s;
		}

		return "";
	}

}
