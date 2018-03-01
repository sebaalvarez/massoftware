package com.massoftware.frontend.xmd.copy;

import java.util.ArrayList;
import java.util.List;

class ComponentXMD {

	private String id;

	private String caption;
	private boolean captionAsHtml = false;
	private String description;

	private boolean enabled = true;
	private boolean readOnly = false;
	private boolean visible = true;

	private String width = "-1px";
	private String height = "-1px";

	private String locale = "es_AR";
	private String errorMessage;

	private List<String> stylesNames = new ArrayList<String>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public boolean isCaptionAsHtml() {
		return captionAsHtml;
	}

	public void setCaptionAsHtml(boolean captionAsHtml) {
		this.captionAsHtml = captionAsHtml;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<String> getStylesNames() {
		return stylesNames;
	}

	public void setStylesNames(List<String> stylesNames) {
		this.stylesNames = stylesNames;
	}

	public boolean addStyleName(String arg0) {
		return stylesNames.add(arg0);
	}
	
	

}
