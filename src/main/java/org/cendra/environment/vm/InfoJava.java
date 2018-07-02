package org.cendra.environment.vm;

public class InfoJava {

	/** Java Runtime Environment version */
	@SuppressWarnings("unused")
	private String javaVersion = "unknown";
	/** Java class format version number */
	@SuppressWarnings("unused")
	private String javaClassVersion = "unknown";
	private JavaInfoVendor vendor = new JavaInfoVendor();
	private JavaSpecification specification = new JavaSpecification();
	private InfoJavaVm vm = new InfoJavaVm();
	private BinaryPath binaryPath = new BinaryPath();

	public String getJavaVersion() {
		return System.getProperty("java.version");
	}

	public void setJavaVersion(String javaVersion) {
		// this.javaVersion = javaVersion;
	}

	public InfoJavaVm getVm() {
		return vm;
	}

	public void setVm(InfoJavaVm vm) {
		this.vm = vm;
	}

	public String getJavaClassVersion() {
		return System.getProperty("java.class.version");
	}

	public void setJavaClassVersion(String javaClassVersion) {
		// this.javaClassVersion = javaClassVersion;
	}

	public JavaInfoVendor getVendor() {
		return vendor;
	}

	public void setVendor(JavaInfoVendor vendor) {
		this.vendor = vendor;
	}

	public JavaSpecification getSpecification() {
		return specification;
	}

	public void setSpecification(JavaSpecification specification) {
		this.specification = specification;
	}

	public BinaryPath getBinaryPath() {
		return binaryPath;
	}

	public void setBinaryPath(BinaryPath binaryPath) {
		this.binaryPath = binaryPath;
	}

}
