package com.massoftware.frontend.custom.windows;

import java.util.List;

import com.vaadin.data.Property;

@SuppressWarnings("rawtypes")
public class StandarTableUiFilteringSet {

	private String pidFiltering = null;
	private Object valueFilter = null;
	private Property searchProperty = null;
	private List<Object> otrosFiltros = null;

	public String getPidFiltering() {
		return pidFiltering;
	}

	public void setPidFiltering(String pidFiltering) {
		this.pidFiltering = pidFiltering;
	}

	public Object getValueFilter() {
		return valueFilter;
	}

	public void setValueFilter(Object valueFilter) {
		this.valueFilter = valueFilter;
	}

	public Property getSearchProperty() {
		return searchProperty;
	}

	public void setSearchProperty(Property searchProperty) {
		this.searchProperty = searchProperty;
	}

	public List<Object> getOtrosFiltros() {
		return otrosFiltros;
	}

	public void setOtrosFiltros(List<Object> otrosFiltros) {
		this.otrosFiltros = otrosFiltros;
	}

}
