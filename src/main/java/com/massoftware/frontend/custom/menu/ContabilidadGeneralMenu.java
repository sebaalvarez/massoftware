package com.massoftware.frontend.custom.menu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.custom.windows.AsientoFormUi;
import com.massoftware.frontend.custom.windows.EjercicioContableTableUi;
import com.massoftware.frontend.custom.windows.StandardFormUi;
import com.massoftware.frontend.custom.windows.StandardTableUi;
import com.massoftware.frontend.custom.windows.WindowsFactory;
import com.massoftware.model.Asiento;
import com.massoftware.model.AsientoModeloItem;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CostoDeVenta;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.MotivoNotaDeCredito;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.empresa.EmpresaForm;
import com.massoftware.windows.paises.WPaises;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class ContabilidadGeneralMenu extends AbstractMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8506821800939861972L;

	private ComboBox filtroEjercicioCBX;
	private BeanItemContainer<EjercicioContable> ejerciciosContablesBIC;

	private MenuItem nuevoAsientoMI;
	private Button nevoAsientoBTN;

	public ContabilidadGeneralMenu(String iconosPath, SessionVar sessionVar) {
		super("Contabilidad general", iconosPath, sessionVar);

		this.addShortcutListener(new ShortcutListener("F5", KeyCode.F5,
				new int[] {}) {

			private static final long serialVersionUID = -79140067012371655L;

			@Override
			public void handleAction(Object sender, Object target) {
				try {
					openWindow(CuentaContable.class);
				} catch (Exception e) {
					LogAndNotification.print(e);
				}
			}
		});

		this.addShortcutListener(new ShortcutListener("F6", KeyCode.F6,
				new int[] {}) {

			private static final long serialVersionUID = 6542661935089665066L;

			@Override
			public void handleAction(Object sender, Object target) {
				try {
					openNewAsiento();
				} catch (Exception e) {
					LogAndNotification.print(e);
				}
			}
		});

	}

	protected MenuBar getMenuBar() {

		MenuBar menubar = new MenuBar();
		menubar.setWidth("100%");
		menubar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		final MenuBar.MenuItem a1 = menubar.addItem("Archivos", null);
		final MenuBar.MenuItem a2 = menubar.addItem("Editar", null);
		final MenuBar.MenuItem a3 = menubar.addItem("Asientos", null);
		final MenuBar.MenuItem a4 = menubar.addItem("Procesos", null);
		final MenuBar.MenuItem a5 = menubar.addItem("Informes", null);
		final MenuBar.MenuItem a6 = menubar.addItem("Ventana", null);
		final MenuBar.MenuItem a7 = menubar.addItem("Ayuda", null);

		a2.setEnabled(false);
		// a3.setEnabled(false);
		a4.setEnabled(false);
		a5.setEnabled(false);
		a6.setEnabled(false);
		a7.setEnabled(false);

		// a1.addItem("Plan de cuentas (orden -> cta de jerarquía) ...", null)
		// .setEnabled(false);
		a1.addItem("Plan de cuentas ...", openWindowCmd(CuentaContable.class));
		a1.addItem("Ejercicios contables ...",
				openWindowCmd(EjercicioContable.class));
		a1.addItem("Modelos de asientos",
				openWindowCmd(AsientoModeloItem.class));
		a1.addItem("Centros de costos ...",
				openWindowCmd(CentroDeCostoContable.class));
		a1.addItem("Puntos de equilibrio ...",
				openWindowCmd(PuntoDeEquilibrio.class));
		a1.addSeparator();
		a1.addItem("Parámetros generales", openEmpresaFormCmd());		
		a1.addItem("Fecha de cierre por módulos", null).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Especificar impresora ...",
				openWindowCmd(MotivoNotaDeCredito.class)).setEnabled(false);

		nuevoAsientoMI = a3.addItem("Nuevo asiento ...", openNewAsientoCmd());
		a3.addItem("Asientos realizados ...", openWindowCmd(Asiento.class));
		// asientos.addItem("Lotes de asientos importados ...", null);
		// asientos.addItem("Anulación de asientos ...", null);

		// procesos.addItem("Control de asientos ...", null);
		// procesos.addSeparator();
		// procesos.addItem("Cierre contabilidad", null);

		// informes.addItem("Balance general", null);
		// informes.addItem("Balance de comproboación de saldos", null);
		// informes.addItem("Informe estado de resultados", null);
		// informes.addSeparator();
		// informes.addItem("Libro diario", null);
		// informes.addItem("Mayor", null);
		// informes.addSeparator();
		// informes.addItem("Plan de cuenta ...", null);

		return menubar;
	}

	@SuppressWarnings("serial")
	protected HorizontalLayout getControlBar() throws Exception {

		HorizontalLayout row = new HorizontalLayout();
		row.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		row.setSpacing(true);

		Button planDeCuentasBTN = new Button("");
		planDeCuentasBTN.addStyleName(ValoTheme.BUTTON_HUGE);
		planDeCuentasBTN.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		planDeCuentasBTN.setDescription("Plan de cuentas (F5)");
		File fileImg = new File(iconosPath + File.separatorChar
				+ "tree-table.png");
		planDeCuentasBTN.setIcon(new FileResource(fileImg));
		planDeCuentasBTN.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				try {
					openWindow(CuentaContable.class);
				} catch (Exception e) {
					LogAndNotification.print(e);
				}
			}
		});

		row.addComponent(planDeCuentasBTN);

		nevoAsientoBTN = new Button("");
		nevoAsientoBTN.addStyleName(ValoTheme.BUTTON_HUGE);
		nevoAsientoBTN.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		nevoAsientoBTN.setDescription("Nuevo asiento (F6)");
		File fileImg2 = new File(iconosPath + File.separatorChar + "book.png");
		nevoAsientoBTN.setIcon(new FileResource(fileImg2));
		nevoAsientoBTN.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {

				try {
					openNewAsiento();
				} catch (Exception e) {
					LogAndNotification.print(e);
				}

			}
		});
		row.addComponent(nevoAsientoBTN);

		Button mayorBTN = new Button("");
		mayorBTN.addStyleName(ValoTheme.BUTTON_HUGE);
		mayorBTN.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		mayorBTN.setDescription("Mayor (F7)");
		File fileImg3 = new File(iconosPath + File.separatorChar
				+ "notebook.png");
		mayorBTN.setIcon(new FileResource(fileImg3));
		mayorBTN.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Notification.show("Clicked "
						+ event.getButton().getDescription());
			}
		});
		row.addComponent(mayorBTN);

		// ----------------------------------

		ejerciciosContablesBIC = new BeanItemContainer<EjercicioContable>(
				EjercicioContable.class, new ArrayList<EjercicioContable>());

		filtroEjercicioCBX = new ComboBox(); // ControlFactory.buildCB();
		// filtroEjercicioCBX.setCaption("Ejercicio");
		filtroEjercicioCBX.setDescription("Ejercicio");
		// filtroEjercicioCBX.setRequired(true);
		filtroEjercicioCBX.setNullSelectionAllowed(false);
		filtroEjercicioCBX.addStyleName(ValoTheme.COMBOBOX_HUGE);
		filtroEjercicioCBX.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
		filtroEjercicioCBX.addStyleName(ValoTheme.COMBOBOX_ALIGN_CENTER);
		filtroEjercicioCBX.setContainerDataSource(ejerciciosContablesBIC);

		loadEjercicioContable();

		row.addComponent(filtroEjercicioCBX);

		filtroEjercicioCBX.addValueChangeListener(e -> {
			filtroEjercicioCBXCBXValueChange();
		});

		return row;
	}

	public void loadEjercicioContable() throws Exception {

		EjercicioContableBO ejercicioContableBO = (EjercicioContableBO) sessionVar
				.getCx().buildBO(EjercicioContable.class);

		List<EjercicioContable> ejerciciosContables = ejercicioContableBO
				.findAll();
		ejerciciosContablesBIC.removeAllItems();
		for (EjercicioContable ejercicioContable : ejerciciosContables) {
			ejerciciosContablesBIC.addBean(ejercicioContable);
		}

		if (ejerciciosContablesBIC.size() > 0) {

			EjercicioContable ejercicioContable = ejercicioContableBO
					.findDefaultEjercicioContable();

			if (ejercicioContable != null
					&& ejercicioContable.getEjercicio() != null) {

				filtroEjercicioCBX.setValue(ejercicioContable);

			} else {

				ejercicioContable = ejerciciosContablesBIC.getIdByIndex(0);
				filtroEjercicioCBX.setValue(ejercicioContable);
			}

			sessionVar.setEjercicioContable(ejercicioContable);

		}

		boolean b = ((EjercicioContable) filtroEjercicioCBX.getValue())
				.getEjercicioCerrado() == false;

		nuevoAsientoMI.setVisible(b);
		nevoAsientoBTN.setVisible(b);
	}

	private void filtroEjercicioCBXCBXValueChange() {
		try {

			sessionVar
					.setEjercicioContable((EjercicioContable) filtroEjercicioCBX
							.getValue());

			boolean b = ((EjercicioContable) filtroEjercicioCBX.getValue())
					.getEjercicioCerrado() == false;

			nuevoAsientoMI.setVisible(b);
			nevoAsientoBTN.setVisible(b);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected Command openNewAsientoCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				openNewAsiento();

			}
		};
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		Asiento item = Asiento.class.newInstance();

		item.setEjercicioContable((EjercicioContable) filtroEjercicioCBX
				.getValue());

		// if (item.getEjercicioContable() != null) {
		// item.getEjercicioContable()._setfullToString(true);
		// }

		AsientoFormUi form = new AsientoFormUi(sessionVar,
				StandardFormUi.INSERT_MODE, null, item);

		// form.setMaxValues();

		// ----------------------------------

		// ComboBox ejercicioContableCBX = (ComboBox) form
		// .getComponentById("ejercicioContable");

		// BeanItemContainer<EjercicioContable> bicEjercicioContable =
		// (BeanItemContainer<EjercicioContable>) ejercicioContableCBX
		// .getContainerDataSource();

		// if (bicEjercicioContable.size() > 0) {
		// for (int i = 0; i < bicEjercicioContable.size(); i++) {
		// bicEjercicioContable.getIdByIndex(i)._setfullToString(true);
		// }
		//
		// }

		// ----------------------------------

		ComboBox minutaContableCBX = (ComboBox) form
				.getComponentById("minutaContable");

		if (form.dtoBI.getBean().getMinutaContable() == null) {

			BeanItemContainer bic = (BeanItemContainer<CostoDeVenta>) minutaContableCBX
					.getContainerDataSource();

			if (bic.size() > 0) {
				minutaContableCBX.setValue(bic.getIdByIndex(5));
			}
		}

		return form;

	}

	@SuppressWarnings("rawtypes")
	protected void openWindow(Class classModel) {

		StandardTableUi table = WindowsFactory.openWindow(getThis(),
				classModel, sessionVar);

		if (table instanceof EjercicioContableTableUi) {
			((EjercicioContableTableUi) table).contabilidadGeneralMenu = (ContabilidadGeneralMenu) getThis();
		}

	}

	protected void openNewAsiento() {
		try {

			Window window = new Window();

			window.setImmediate(true);
			window.setWidth("-1px");
			window.setHeight("-1px");
			window.setClosable(true);
			window.setResizable(false);
			window.setModal(true);
			window.center();
			window.setContent(openFormAgregar());
			window.setCaption("Agregar asiento contable");
			window.center();
			getThis().getUI().addWindow(window);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// -------------------------------------------------------------

	protected Command openEmpresaFormCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new EmpresaForm(sessionVar);
				getUI().addWindow(window);
			}
		};
	}
	


}
