package org.cendra.common.model.geo;

import org.cendra.common.model.EntityId;

public class Language extends EntityId implements Cloneable,
		Comparable<Language> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5219035736133212121L;

	private Boolean macrolanguage = false;
	private String iso639_3;
	private String iso639_2;
	private String iso639_1;
	private String languageName;

	public void setId(String id) {
		this.setIso639_3(id);
	}

	public Boolean getMacrolanguage() {
		macrolanguage = this.nullIsFalse(macrolanguage);
		return macrolanguage;
	}

	public void setMacrolanguage(Boolean macrolanguage) {
		macrolanguage = this.nullIsFalse(macrolanguage);
		this.macrolanguage = macrolanguage;
	}

	public String getIso639_3() {
		iso639_3 = this.formatValue(iso639_3);
		return iso639_3;
	}

	public void setIso639_3(String iso639_3) {
		iso639_3 = this.formatValue(iso639_3);
		this.iso639_3 = iso639_3;
		super.setId(this.iso639_3);
	}

	public String getIso639_2() {
		iso639_2 = this.formatValue(iso639_2);
		return iso639_2;
	}

	public void setIso639_2(String iso639_2) {
		iso639_2 = this.formatValue(iso639_2);
		this.iso639_2 = iso639_2;
	}

	public String getIso639_1() {
		iso639_1 = this.formatValue(iso639_1);
		return iso639_1;
	}

	public void setIso639_1(String iso639_1) {
		iso639_1 = this.formatValue(iso639_1);
		this.iso639_1 = iso639_1;
	}

	public String getLanguageName() {
		languageName = this.formatValue(languageName);
		return languageName;
	}

	public void setLanguageName(String languageName) {
		languageName = this.formatValue(languageName);
		this.languageName = languageName;
	}

	public Language clone() throws CloneNotSupportedException {
		Language other = (Language) super.clone();

		other.setMacrolanguage(this.getMacrolanguage());
		other.setIso639_3(this.getIso639_3());
		other.setIso639_2(this.getIso639_2());
		other.setIso639_1(this.getIso639_1());
		other.setLanguageName(this.getLanguageName());

		return other;
	}

	public int compareTo(Language o) {

		if (this.getIso639_3() != null && o != null) {
			return this.getIso639_3().compareTo(((Language) o).getIso639_3());
		}

		if (this.getIso639_3() != null && o == null) {
			return this.getIso639_3().compareTo("");
		}

		return "".compareTo(((Language) o).getIso639_3());
	}

	@Override
	public String toString() {

		String s = super.toString();

		if (this.getIso639_3() != null && s != null
				&& s.trim().isEmpty() == false) {
			return (s + " (" + this.getIso639_3() + ")").trim();
		} else if (this.getIso639_3() != null
				&& (s == null || s.trim().isEmpty() == true)) {
			return this.getIso639_3().trim();
		} else if (this.getIso639_3() == null && s != null
				&& s.trim().isEmpty() == false) {
			return s;
		}

		return "";
	}

}
