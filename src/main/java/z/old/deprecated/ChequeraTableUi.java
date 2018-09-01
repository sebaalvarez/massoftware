package z.old.deprecated;

import java.util.List;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.bo.ChequeraBO;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.frontend.util.builder.BuilderXMD;
import com.massoftware.frontend.util.window.StandardFormUi;
import com.massoftware.frontend.util.window.StandardTableUi;
import com.massoftware.model.Chequera;
import com.massoftware.model.CuentaDeFondoA;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Window;

public class ChequeraTableUi extends StandardTableUi<Chequera> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261882256758L;

	private CheckBox filtroTodasCHK;

	private CuentaDeFondoATableUi cuentaDeFondoATableUi;

	public ChequeraTableUi(boolean paged, boolean pagedCount,
			boolean pagedOrder, boolean shortcut, boolean agregar,
			boolean modificar, boolean copiar, boolean eliminar, Window window,
			BackendContext cx, Usuario usuario, Class<Chequera> classModel,
			String pidFiltering, Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty) {

		super(paged, pagedCount, pagedOrder, shortcut, agregar, modificar,
				copiar, eliminar, window, cx, usuario, classModel,
				pidFiltering, searchFilter, searchProperty, null);

		window.setWidth("1300px");

		HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
		hsplit.setSplitPosition(550, Unit.PIXELS);
		this.setCompositionRoot(hsplit);

		cuentaDeFondoATableUi = new CuentaDeFondoATableUi(false, false, false, false, false, false,
				false, false, null, cx, usuario, CuentaDeFondoA.class, null,
				null, null, this);

		hsplit.setFirstComponent(cuentaDeFondoATableUi);

		hsplit.setSecondComponent(rootVL);

		this.setCompositionRoot(hsplit);

	}

	protected void addControlsFilters() throws Exception {

		// ----------------------------------

		filtroTodasCHK = BuilderXMD.buildCHK();
		filtroTodasCHK.setCaption("Incluir cuentas bloqueadas");

		filaFiltro1HL.addComponent(filtroTodasCHK);
		filaFiltro1HL.setComponentAlignment(filtroTodasCHK,
				Alignment.MIDDLE_CENTER);

		filtroTodasCHK
				.addValueChangeListener(new Property.ValueChangeListener() {

					private static final long serialVersionUID = -6857112166321059475L;

					public void valueChange(ValueChangeEvent event) {
						filtroTodasCHKValueChangeListener();
					}
				});
	}

	public CuentaDeFondoA cuentaDeFondoFilter;

	protected List<Chequera> reloadDataList() throws Exception {

		return ((ChequeraBO) cx.buildBO(classModel)).findAll(
				cuentaDeFondoFilter, filtroTodasCHK.getValue());

	}

	private void filtroTodasCHKValueChangeListener() {
		try {
			// super.reloadData();
			reloadData();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		CuentaDeFondoA cuentaDeFondo = (CuentaDeFondoA) cuentaDeFondoATableUi.itemsGRD
				.getSelectedRow();

		Chequera chequera = Chequera.class.newInstance();
		chequera.setCuentaDeFondo(cuentaDeFondo);

		return new ChequeraFormUi(usuario, StandardFormUi.INSERT_MODE, cx,
				this, chequera);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormModificar(Chequera item) throws Exception {

		return new ChequeraFormUi(usuario, StandardFormUi.UPDATE_MODE, cx,
				this, item);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormCopiar(Chequera item) throws Exception {

		Chequera chequera = ((Chequera) item).clone();

		return new ChequeraFormUi(usuario, StandardFormUi.COPY_MODE, cx, this,
				chequera);
	}

}
