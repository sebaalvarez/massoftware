package org.cendra.environment.vm;

public class JavaInfoVendor {

	/** Java Runtime Environment vendor */
	@SuppressWarnings("unused")
	private String vendor = "unknown";
	/** Java vendor URL */
	@SuppressWarnings("unused")
	private String url = "unknown";

	public String getVendor() {
		return System.getProperty("java.vendor");
	}

	public void setVendor(String vendor) {
		// this.javaVendor = javaVendor;
	}

	public String getUrl() {
		return System.getProperty("java.vendor.url");
	}

	public void setUrl(String url) {
		// this.javaVendorUrl = javaVendorUrl;
	}

}
