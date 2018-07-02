package org.cendra.environment;

public class InfoUser {

	/** User's account name */
	@SuppressWarnings("unused")
	private String name = "unknown";
	/** User's home directory */
	@SuppressWarnings("unused")
	private String home = "unknown";

	public String getName() {
		return System.getProperty("user.name");
	}

	public void setName(String name) {
		// this.name = name;
	}

	public String getHome() {
		return System.getProperty("user.home");
	}

	public void setHome(String home) {
		// this.home = home;
	}

}
