package z.old.deprecated;

import com.massoftware.SessionVar;
import com.massoftware.frontend.custom.windows.StandardFormUi;
import com.massoftware.frontend.custom.windows.StandardTableUi;
import com.massoftware.model.CentroDeCostoProyecto;
import com.vaadin.ui.Window;

public class CentroDeCostoProyectoTableUi extends
		StandardTableUi<CentroDeCostoProyecto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261883256758L;

	public CentroDeCostoProyectoTableUi(Window window, SessionVar sessionVar) {

//		super(null, null, window, sessionVar, CentroDeCostoProyecto.class);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		CentroDeCostoProyecto item = CentroDeCostoProyecto.class.newInstance();

		return new CentroDeCostoProyectoFormUi(sessionVar,
				StandardFormUi.INSERT_MODE, this, item);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormModificar(CentroDeCostoProyecto item)
			throws Exception {

		return new CentroDeCostoProyectoFormUi(sessionVar,
				StandardFormUi.UPDATE_MODE, this, item);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormCopiar(CentroDeCostoProyecto item)
			throws Exception {

		CentroDeCostoProyecto itemClone = ((CentroDeCostoProyecto) item)
				.clone();

		return new CentroDeCostoProyectoFormUi(sessionVar,
				StandardFormUi.COPY_MODE, this, itemClone);
	}

}
