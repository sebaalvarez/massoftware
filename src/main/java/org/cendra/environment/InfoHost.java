package org.cendra.environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZonedDateTime;

import org.cendra.environment.vm.InfoJava;

public class InfoHost {

	private String timeInfo = "unknown";
	private String hostName = "unknown";
	private String hostAddress = "unknown";
	private InfoOs os = new InfoOs();
	private InfoJava infoJava = new InfoJava();

	public InfoHost() {
		super();
		timeInfo = ZonedDateTime.now().toString();
		InetAddress localHost;
		try {
			localHost = InetAddress.getLocalHost();
			hostName = localHost.getHostName();
			hostAddress = localHost.getHostAddress();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

	public String getTimeInfo() {
		return timeInfo;
	}

	public void setTimeInfo(String timeInfo) {
		this.timeInfo = timeInfo;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostAddress() {
		return hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	public InfoOs getOs() {
		return os;
	}

	public void setOs(InfoOs os) {
		this.os = os;
	}

	public InfoJava getInfoJava() {
		return infoJava;
	}

	public void setInfoJava(InfoJava infoJava) {
		this.infoJava = infoJava;
	}

}
