package com.massoftware.frontend.xmd.copy;

import java.util.ArrayList;
import java.util.List;

class ComponentContainerXMD extends ComponentXMD {

	private String defaultComponentAlignment;
	private List<ComponentItemXMD> components = new ArrayList<ComponentItemXMD>();

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
		
		for(ComponentItemXMD item : components){
			if(item.getAlignment() == null || item.getAlignment().trim().length() == 0){
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

}
