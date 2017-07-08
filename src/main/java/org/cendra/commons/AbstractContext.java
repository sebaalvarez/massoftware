package org.cendra.commons;

import org.cendra.commons.util.error.LogPrinter;
import org.cendra.commons.util.system.InfoHost;
import org.codehaus.jackson.map.ObjectMapper;

import com.massoftware.backend.cx.BackendContext;

public abstract class AbstractContext {

	public AbstractContext() {
		super();
		init();
	}

	private void init() {

		LogPrinter errorPrinter = new LogPrinter();

		try {

			errorPrinter.print(BackendContext.class.getName(),
					LogPrinter.LEVEL_INFO, "\n\n[..] Iniciando contexto\n");

			ObjectMapper mapper = new ObjectMapper();
			String msg = "[OK] Ambiente de ejecución\n\n"
					+ mapper.writerWithDefaultPrettyPrinter()
							.writeValueAsString(new InfoHost());

			errorPrinter.print(BackendContext.class.getName(),
					LogPrinter.LEVEL_INFO, "\n\n" + msg + "\n");

		} catch (Exception e) {
			errorPrinter.print(AbstractContext.class.getName(),
					LogPrinter.LEVEL_ERROR, e);
		}
	}

}
