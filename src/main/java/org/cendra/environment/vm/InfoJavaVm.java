package org.cendra.environment.vm;

public class InfoJavaVm {

	/** Java installation directory */
	@SuppressWarnings("unused")
	private String javaHome = "unknown";
	private JavaVmSpecification specification = new JavaVmSpecification();
	/** Java Virtual Machine implementation version */
	@SuppressWarnings("unused")
	private String javaVmVersion = "unknown";
	/** Java Virtual Machine implementation vendor */
	@SuppressWarnings("unused")
	private String javaVmVendor = "unknown";
	/** Java Virtual Machine implementation name */
	@SuppressWarnings("unused")
	private String javaVmName = "unknown";
	/** Name of JIT compiler to use */
	@SuppressWarnings("unused")
	private String javaCompiler = "unknown";

	public String getVersion() {
		return System.getProperty("java.vm.version");
	}

	public void setVersion(String version) {
		// this.javaVmVersion = javaVmVersion;
	}

	public String getVendor() {
		return System.getProperty("java.vm.vendor");
	}

	public void setVendor(String vendor) {
		// this.javaVmVendor = javaVmVendor;
	}

	public String getName() {
		return System.getProperty("java.vm.name");
	}

	public void setName(String name) {
		// this.javaVmName = javaVmName;
	}

	public JavaVmSpecification getSpecification() {
		return specification;
	}

	public void setSpecification(JavaVmSpecification specification) {
		this.specification = specification;
	}

	public String getJavaHome() {
		return System.getProperty("java.home");
	}

	public void setJavaHome(String javaHome) {
		// this.javaHome = javaHome;
	}

	public String getJavaCompiler() {
		return System.getProperty("java.compiler");
	}

	public void setJavaCompiler(String javaCompiler) {
		// this.javaCompiler = javaCompiler;
	}

}
