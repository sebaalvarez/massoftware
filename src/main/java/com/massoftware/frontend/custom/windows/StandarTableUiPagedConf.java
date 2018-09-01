package com.massoftware.frontend.custom.windows;

public class StandarTableUiPagedConf {

	private boolean paged = false;
	private boolean pagedCount = false;
	private boolean pagedOrder = false;

	public StandarTableUiPagedConf() {
		super();
		this.paged = true;
		this.pagedCount = true;
		this.pagedOrder = true;
	}

	public StandarTableUiPagedConf(boolean paged, boolean pagedCount,
			boolean pagedOrder) {
		super();
		this.paged = paged;
		this.pagedCount = pagedCount;
		this.pagedOrder = pagedOrder;
	}

	public boolean isPaged() {
		return paged;
	}

	public void setPaged(boolean paged) {
		this.paged = paged;
	}

	public boolean isPagedCount() {
		return pagedCount;
	}

	public void setPagedCount(boolean pagedCount) {
		this.pagedCount = pagedCount;
	}

	public boolean isPagedOrder() {
		return pagedOrder;
	}

	public void setPagedOrder(boolean pagedOrder) {
		this.pagedOrder = pagedOrder;
	}

}
