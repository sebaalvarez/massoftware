package org.cendra.environment.vm;

public class JavaVmSpecification {

	/** Java Virtual Machine specification version */
	@SuppressWarnings("unused")
	private String version = "unknown";
	/** Java Virtual Machine specification vendor */
	@SuppressWarnings("unused")
	private String vendor = "unknown";
	/** Java Virtual Machine specification name */
	@SuppressWarnings("unused")
	private String name = "unknown";

	public String getVersion() {
		return System.getProperty("java.vm.specification.version");
	}

	public void setVersion(String javaVmSpecificationVersion) {
		// this.javaVmSpecificationVersion = javaVmSpecificationVersion;
	}

	public String getVendor() {
		return System.getProperty("java.vm.specification.vendor");
	}

	public void setVendor(String javaVmSpecificationVendor) {
		// this.javaVmSpecificationVendor = javaVmSpecificationVendor;
	}

	public String getName() {
		return System.getProperty("java.vm.specification.name");
	}

	public void setName(String javaVmSpecificationName) {
		// this.javaVmSpecificationName = javaVmSpecificationName;
	}

}
