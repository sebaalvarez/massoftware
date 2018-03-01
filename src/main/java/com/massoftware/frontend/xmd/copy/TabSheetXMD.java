package com.massoftware.frontend.xmd.copy;

class TabSheetXMD extends ComponentContainerXMD {

	private boolean tabsVisible = true;
	private int selectedIndexTab;
	
	

	public TabSheetXMD() {
		super();
		this.setWidth("-1px");
	}

	public boolean isTabsVisible() {
		return tabsVisible;
	}

	public void setTabsVisible(boolean tabsVisible) {
		this.tabsVisible = tabsVisible;
	}

	public int getSelectedIndexTab() {
		return selectedIndexTab;
	}

	public void setSelectedIndexTab(int selectedIndexTab) {
		this.selectedIndexTab = selectedIndexTab;
	}

}
