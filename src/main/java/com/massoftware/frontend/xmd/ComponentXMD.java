package com.massoftware.frontend.xmd;

import java.util.ArrayList;
import java.util.List;

public class ComponentXMD {

	public static final String VL = "VerticalLayout";
	public static final String HL = "HorizontalLayout";
	public static final String GL = "GridLayout";
	public static final String TS = "TabSheet";
	public static final String LBL = "Label";
	public static final String ATT = "ComponentAtt";

	public ComponentXMD() {
		super();
		init();
	}

	public ComponentXMD(String type) {
		super();
		this.type = type;
		init();
	}

	// private boolean container;
	private String type;

	private String id;

	private String caption;
	private Boolean captionAsHtml = false;
	private String description;

	private Boolean enabled = true;
	private Boolean readOnly = false;
	private Boolean visible = true;

	private String width = "-1px";
	private String height = "-1px";

	private String locale = "es_AR";
	private String errorMessage;

	private List<String> stylesNames = new ArrayList<String>();

	private String defaultComponentAlignment;
	private List<ComponentItemXMD> components = new ArrayList<ComponentItemXMD>();

	@SuppressWarnings("rawtypes")
	private Class classModel;

	private Boolean margin = true;
	private Boolean spacing = true;

	private Integer columns;
	private Integer rows;

	private Boolean tabsVisible = true;
	private Integer selectedIndexTab;

	private String value;

	private String attName;
	private List<String> attsNames = new ArrayList<String>();

	private void init() {

		// captionAsHtml = false;

		// enabled = true;
		// readOnly = false;
		// visible = true;

		// width = "-1px";
		// height = "-1px";

		// locale = "es_AR";

		stylesNames = new ArrayList<String>();

		components = new ArrayList<ComponentItemXMD>();

		// margin = true;
		// spacing = true;

		// tabsVisible = true;

	}

	public boolean _isContainer() {
		if (VL.equalsIgnoreCase(this.getType())) {
			return true;
		} else if (HL.equalsIgnoreCase(this.getType())) {
			return true;
		} else if (TS.equalsIgnoreCase(this.getType())) {
			return true;
		} else if (GL.equalsIgnoreCase(this.getType())) {
			return true;
		} else {
			return false;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public Boolean getCaptionAsHtml() {
		if (captionAsHtml == null) {
			captionAsHtml = false;
		}
		return captionAsHtml;
	}

	public void setCaptionAsHtml(Boolean captionAsHtml) {
		this.captionAsHtml = captionAsHtml;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnabled() {
		if (enabled == null) {
			enabled = true;
		}
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getReadOnly() {
		if (readOnly == null) {
			readOnly = false;
		}
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Boolean getVisible() {
		if (visible == null) {
			visible = true;
		}
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getWidth() {
		if (width == null) {
			width = "-1px";
		}
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		if (height == null) {
			height = "-1px";
		}
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

	public String getDefaultComponentAlignment() {
		return defaultComponentAlignment;
	}

	public void setDefaultComponentAlignment(String defaultComponentAlignment) {
		this.defaultComponentAlignment = defaultComponentAlignment;
	}

	public List<ComponentItemXMD> getComponents() {
		return components;
	}

	public void setComponents(List<ComponentItemXMD> components) {

		for (ComponentItemXMD item : components) {
			if (item.getAlignment() == null
					|| item.getAlignment().trim().length() == 0) {
				item.setAlignment(getDefaultComponentAlignment());
			}
		}

		this.components = components;
	}

	public boolean addComponent(ComponentItemXMD arg0) {
		return components.add(arg0);
	}

	public boolean addComponent(ComponentXMD arg0) {
		ComponentItemXMD item = new ComponentItemXMD();
		item.setAlignment(getDefaultComponentAlignment());
		item.setComponent(arg0);

		return components.add(item);
	}

	@SuppressWarnings("rawtypes")
	public Class getClassModel() {
		return classModel;
	}

	@SuppressWarnings("rawtypes")
	public void setClassModel(Class classModel) {
		this.classModel = classModel;
	}

	public Boolean getMargin() {
		if (margin == null) {
			margin = true;
		}
		return margin;
	}

	public void setMargin(Boolean margin) {
		this.margin = margin;
	}

	public Boolean getSpacing() {
		if (spacing == null) {
			spacing = true;
		}
		return spacing;
	}

	public void setSpacing(Boolean spacing) {
		this.spacing = spacing;
	}

	public Integer getColumns() {
		return columns;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public boolean addComponent(ComponentXMD arg0, int row, int column) {
		ComponentItemXMD item = new ComponentItemXMD();
		item.setAlignment(getDefaultComponentAlignment());
		item.setComponent(arg0);
		item.setRow(row);
		item.setColumn(column);

		return addComponent(item);
	}

	public Boolean getTabsVisible() {
		if (tabsVisible == null) {
			tabsVisible = true;
		}
		return tabsVisible;
	}

	public void setTabsVisible(Boolean tabsVisible) {
		this.tabsVisible = tabsVisible;
	}

	public Integer getSelectedIndexTab() {
		return selectedIndexTab;
	}

	public void setSelectedIndexTab(Integer selectedIndexTab) {
		this.selectedIndexTab = selectedIndexTab;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAttName() {
		return attName;
	}

	public void setAttName(String attName) {
		this.attName = attName;
	}

	public List<String> getAttsNames() {
		return attsNames;
	}

	public void setAttsNames(List<String> attsNames) {
		this.attsNames = attsNames;
	}

	public boolean addAttName(String e) {
		return attsNames.add(e);
	}
	
	

}
