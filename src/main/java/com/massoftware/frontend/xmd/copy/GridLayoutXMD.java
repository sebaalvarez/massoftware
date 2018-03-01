package com.massoftware.frontend.xmd.copy;


class GridLayoutXMD extends ComponentContainerXMD {

	private int columns;
	private int rows;

	private boolean margin = true;
	private boolean spacing = true;

	public GridLayoutXMD() {
		super();
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public boolean isMargin() {
		return margin;
	}

	public void setMargin(boolean margin) {
		this.margin = margin;
	}

	public boolean isSpacing() {
		return spacing;
	}

	public void setSpacing(boolean spacing) {
		this.spacing = spacing;
	}
	
	public boolean addComponent(ComponentXMD arg0, int row, int column) {
		ComponentItemXMD item = new ComponentItemXMD();
		item.setAlignment(getDefaultComponentAlignment());
		item.setComponent(arg0);
		item.setRow(row);
		item.setColumn(column);

		return addComponent(item);
	}

}
