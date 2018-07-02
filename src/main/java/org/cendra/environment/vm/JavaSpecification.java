package org.cendra.environment.vm;

public class JavaSpecification {

	/** Java Runtime Environment specification version */
	@SuppressWarnings("unused")
	private String version = "unknown";
	/** Java Runtime Environment specification vendor */
	@SuppressWarnings("unused")
	private String vendor = "unknown";
	/** Java Runtime Environment specification name */
	@SuppressWarnings("unused")
	private String name = "unknown";

	public String getVersion() {
		return System.getProperty("java.specification.version");
	}

	public void setVersion(String version) {
		// this.javaSpecificationVersion = javaSpecificationVersion;
	}

	public String getVendor() {
		return System.getProperty("java.specification.vendor");
	}

	public void setVendor(String vendor) {
		// this.javaSpecificationVendor = javaSpecificationVendor;
	}

	public String getName() {
		return System.getProperty("java.specification.name");
	}

	public void setName(String name) {
		// this.javaSpecificationName = javaSpecificationName;
	}

}
