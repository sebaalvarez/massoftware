package org.cendra.common.model.file;

import org.cendra.common.model.AbstractEntityErasable;

public class File extends AbstractEntityErasable implements Cloneable,
		Comparable<File> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 167791464485824458L;

	private String title;
	private String fileName;
	private String link;
	private String path;

	public String getTitle() {
		title = formatValue(title);
		return title;
	}

	public void setTitle(String title) {
		title = formatValue(title);
		this.title = title;
	}

	public String getFileName() {
		fileName = formatValueFileName(fileName);
		return fileName;
	}

	public void setFileName(String fileName) {
		fileName = formatValueFileName(fileName);
		this.fileName = fileName;
	}

	public String getLink() {
		link = formatValue(link);
		return link;
	}

	public void setLink(String link) {
		link = formatValue(link);
		this.link = link;
	}

	public String getPath() {
		path = formatValue(path);
		return path;
	}

	public void setPath(String path) {
		path = formatValue(path);
		this.path = path;
	}

	@Override
	public File clone() throws CloneNotSupportedException {
		File other = (File) super.clone();

		other.setTitle(this.getTitle());
		other.setFileName(this.getFileName());
		other.setLink(this.getLink());
		other.setPath(this.getPath());

		return other;
	}

	public int compareTo(File o) {

		if (this.getFileName() != null && o != null) {
			return this.getFileName().compareTo(((File) o).getFileName());
		}

		if (this.getFileName() != null && o == null) {
			return this.getFileName().compareTo("");
		}

		return "".compareTo(((File) o).getFileName());
	}

	@Override
	public String toString() {

		String s = super.toString();

		if (this.getFileName() != null && s != null
				&& s.trim().isEmpty() == false) {
			return (s + " (" + this.getFileName() + ")").trim();
		} else if (this.getFileName() != null
				&& (s == null || s.trim().isEmpty() == true)) {
			return this.getFileName().trim();
		} else if (this.getFileName() == null && s != null
				&& s.trim().isEmpty() == false) {
			return s;
		}

		return "";
	}

	protected String formatValueFileName(String value) {

		value = super.formatValue(value);

		if (value != null) {
			value = value.toLowerCase();
		}

		return value;
	}

}
