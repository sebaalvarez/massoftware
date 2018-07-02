package org.cendra.cx;

import org.cendra.environment.InfoHost;
import org.cendra.log.LogPrinter;

public abstract class AbstractContext {

	public AbstractContext() {
		super();
		init();
	}

	private void init() {

		LogPrinter logPrinter = new LogPrinter();

		try {

			logPrinter.print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_INFO, "\n\n[..] Iniciando contexto\n");

			logPrinter.printJson(AbstractContext.class.getName(),
					LogPrinter.LEVEL_INFO, "\n\n", new InfoHost(), "\n");

		} catch (Exception e) {
			logPrinter.print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_ERROR, e);
		}
	}

}
