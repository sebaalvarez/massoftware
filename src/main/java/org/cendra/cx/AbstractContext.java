package org.cendra.cx;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.cendra.cx.environment.InfoHost;

public abstract class AbstractContext {

	public AbstractContext() {
		super();
		init();
	}

	public static String sep() {
		return "\n\n=================================================================================\n\n";
	}

	private void init() {

		try {

			getInfoHost();

		} catch (Exception e) {
			printFatal(e);
		}
	}

	public void printFatal(Exception e) {
		String msg = sep()
				+ "ERROR FATAL!! - Se procede a interrumpir la ejecución del sistema. "
				+ ZonedDateTime.now();
		System.err.println(msg);
		e.printStackTrace();
		System.err.println(sep());
		System.exit(-1);
	}

	public InfoHost getInfoHost() {

		try {

			System.out
					.println(sep()
							+ "[..] Recopilando informacíon del ambiente de ejecución\n\n");

			InfoHost infoHost = new InfoHost();

			String msg = "Ambiente de ejecución:\n\n" + infoHost.toJson();
			System.out.println(msg);

			System.out
					.println("\n\n[OK] Informacíon del ambiente de ejecución recopilada"
							+ sep());

			return infoHost;

		} catch (Exception e) {
			printFatal(e);
		}
		return null;

	}

	public ResourceBundle loadResourceBundle(String path) {

		try {

			System.out
					.println(sep() + "[..] Leyendo archivo ResourceBundle \n\n"
							+ path + sep());

			ResourceBundle messages = ResourceBundle.getBundle(path);

			System.out.println("[OK] Leyendo archivo ResourceBundle \n\n"
					+ path + sep());

			return messages;

		} catch (Exception e) {
			printFatal(e);
		}
		return null;

	}

	public Properties loadProperties(String path) {

		try {

			System.out.println(sep()
					+ "[..] Leyendo archivo de propiedades\n\n" + path + sep());

			Properties properties = new Properties();
			InputStream input = null;

			try {

				input = new FileInputStream(path);

				properties.load(input);

				String json = "\n{\n";

				for (Enumeration<Object> e = properties.keys(); e
						.hasMoreElements();) {
					Object obj = e.nextElement();
					json += "\n\t\""
							+ obj
							+ "\":"
							+ buildValue(properties.getProperty(obj.toString()))
							+ ",";
				}

				json = json.substring(0, json.length() - 1);

				json += "\n}";

				System.out.println("[OK] Lectura de archivo de propiedades\n\n"
						+ path + "\n\nContenido:\n\n" + json + sep());

			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return properties;

		} catch (Exception e) {
			printFatal(e);
		}
		return null;

	}

	private String buildValue(Object value) {

		if (value == null) {
			return null;
		}

		if (value instanceof Number) {
			return value.toString();
		}

		if (value instanceof Boolean) {
			return value.toString();
		}

		return "\"" + value + "\"";

	}

}
