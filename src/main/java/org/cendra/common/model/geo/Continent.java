package org.cendra.common.model.geo;

import java.util.ArrayList;
import java.util.List;

import org.cendra.common.model.EntityId;
import org.cendra.common.model.file.File;

public class Continent extends EntityId implements Cloneable,
		Comparable<Continent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2740302653107486575L;

	private String code;
	private String shortName;
	private String description;
	private List<AlternateName> alternateNames = new ArrayList<AlternateName>();
	private List<String> abbreviations = new ArrayList<String>();
	private File orthographicProjectionFile;
	private File wikipediaFile;
	private Long geonameId;

	public String getId() {
		return this.getCode();
	}

	public void setId(String id) {
		this.setCode(id);
	}

	public String getCode() {
		code = this.formatValue(code);
		return code;
	}

	public void setCode(String code) {
		code = this.formatValue(code);
		this.code = code;
	}

	public String getShortName() {
		shortName = this.formatValue(shortName);
		return shortName;
	}

	public void setShortName(String shortName) {
		shortName = this.formatValue(shortName);
		this.shortName = shortName;
	}

	public String getDescription() {
		description = this.formatValue(description);
		return description;
	}

	public void setDescription(String description) {
		description = this.formatValue(description);
		this.description = description;
	}

	@SuppressWarnings("unchecked")
	public List<AlternateName> getAlternateNames() {
		alternateNames = formatValues(alternateNames);
		return alternateNames;
	}

	@SuppressWarnings("unchecked")
	public void setAlternateNames(List<AlternateName> alternateNames) {
		alternateNames = formatValues(alternateNames);
		this.alternateNames = alternateNames;
	}

	@SuppressWarnings("unchecked")
	public void addAlternateName(AlternateName alternateName) {
		alternateNames = formatValues(alternateNames);
		this.alternateNames.add(alternateName);
	}

	@SuppressWarnings("unchecked")
	public List<String> getAbbreviations() {
		abbreviations = formatValues(abbreviations);
		return abbreviations;
	}

	@SuppressWarnings("unchecked")
	public void setAbbreviations(List<String> abbreviations) {
		abbreviations = formatValues(abbreviations);
		this.abbreviations = abbreviations;
	}

	@SuppressWarnings("unchecked")
	public void addAbbreviation(String abbreviation) {
		abbreviations = formatValues(abbreviations);
		abbreviation = this.formatValue(abbreviation);
		this.abbreviations.add(abbreviation);
	}

	public File getOrthographicProjectionFile() {
		return orthographicProjectionFile;
	}

	public void setOrthographicProjectionFile(File orthographicProjectionFile) {
		this.orthographicProjectionFile = orthographicProjectionFile;
	}

	public File getWikipediaFile() {
		return wikipediaFile;
	}

	public void setWikipediaFile(File wikipediaFile) {
		this.wikipediaFile = wikipediaFile;
	}

	public Long getGeonameId() {
		return geonameId;
	}

	public void setGeonameId(Long geonameId) {
		this.geonameId = geonameId;
	}

	public String allNames() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.getShortName() + "\n");
		sb.append(this.getDescription() + "\n");
		if (this.getAbbreviations() != null) {
			for (String item : this.getAbbreviations()) {
				sb.append(item + "\n");
			}
		}
		if (this.getAlternateNames() != null) {
			for (AlternateName item : this.getAlternateNames()) {
				sb.append(item.getAlternateName() + "\n");
			}
		}

		return sb.toString();
	}

	@Override
	public Continent clone() throws CloneNotSupportedException {
		Continent other = (Continent) super.clone();

		other.setCode(this.getCode());
		other.setShortName(this.getShortName());
		other.setDescription(this.getDescription());

		if (this.getAlternateNames() != null) {
			for (AlternateName alternateName : this.getAlternateNames()) {
				if (alternateName != null) {
					this.addAlternateName(alternateName.clone());
				}

			}
		} else {
			other.setAlternateNames(null);
		}

		if (this.getAbbreviations() != null) {
			for (String abbreviation : this.getAbbreviations()) {
				if (abbreviation != null) {
					this.addAbbreviation(abbreviation);
				}

			}
		} else {
			other.setAbbreviations(null);
		}

		if (other.getOrthographicProjectionFile() != null) {
			other.setOrthographicProjectionFile(this
					.getOrthographicProjectionFile().clone());
		} else {
			other.setOrthographicProjectionFile(null);
		}
		if (other.getWikipediaFile() != null) {
			other.setWikipediaFile(this.getWikipediaFile().clone());
		} else {
			other.setWikipediaFile(null);
		}
		other.setGeonameId(this.getGeonameId());

		return other;
	}

	public int compareTo(Continent o) {

		if (this.getCode() != null && o != null) {
			return this.getCode().compareTo(((Continent) o).getCode());
		}

		if (this.getCode() != null && o == null) {
			return this.getCode().compareTo("");
		}

		return "".compareTo(((Continent) o).getCode());
	}

}
