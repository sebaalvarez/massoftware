package org.cendra.log;

import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;

public class LogPrinter {

	// LOS LOGS SE DEBEN PODER PRENDER Y APAGAR POR EL ADMINISTRADOR DEL SISTEMA

	public static final int LEVEL_TRACE = 1;
	public static final int LEVEL_DEBUG = 2;
	public static final int LEVEL_INFO = 3;
	public static final int LEVEL_WARN = 4;
	public static final int LEVEL_ERROR = 5;
	public static final int LEVEL_ERROR_BUSSINES = 7;
	public static final int LEVEL_FATAL = 6;

	public void print(String name, int level, Exception exception) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String msg = "\n\n"
					+ mapper.writerWithDefaultPrettyPrinter()
							.writeValueAsString(exception);

			print(name, level, msg);

			System.err.println(exception);
			exception.printStackTrace();

		} catch (Exception e) {
			System.err.println(exception);
			exception.printStackTrace();

			System.err.println(e);
			e.printStackTrace();
		}
	}

	public void print(String name, int level, String msg) {
		try {
			Logger.getLogger(name).log(getLevel(level), msg);

			if (level == LEVEL_FATAL) {
				Logger.getLogger(name).log(
						getLevel(level),
						"ERROR FATAL!! - Se procede a interrumpir la ejecución del sistema. "
								+ ZonedDateTime.now());
				System.exit(-1);
			}
		} catch (Exception e) {
			System.err.println(msg);
			System.err.println(e);
			e.printStackTrace();
		}
	}

	public void printJson(String name, int level, String startMsg, Object obj,
			String endMsg) {

		String msg = "";

		try {

			ObjectMapper mapper = new ObjectMapper();
			msg = "[OK] Ambiente de ejecución\n\n"
					+ mapper.writerWithDefaultPrettyPrinter()
							.writeValueAsString(obj);
		} catch (Exception e) {
			System.err.println(obj);
			System.err.println(e);
			e.printStackTrace();
		}

		print(name, level, startMsg + msg + endMsg);
	}

	private Level getLevel(int level) {

		switch (level) {

		case LEVEL_TRACE:
			return Level.ALL;
		case LEVEL_DEBUG:
			return Level.INFO;
		case LEVEL_INFO:
			return Level.INFO;
		case LEVEL_WARN:
			return Level.WARNING;
		case LEVEL_ERROR:
			return Level.SEVERE;
		case LEVEL_ERROR_BUSSINES:
			return Level.SEVERE;
		case LEVEL_FATAL:
			return Level.SEVERE;

		default:
			return Level.SEVERE;
		}

	}

}
