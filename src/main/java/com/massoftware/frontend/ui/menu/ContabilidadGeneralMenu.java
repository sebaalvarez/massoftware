package com.massoftware.frontend.ui.menu;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.bo.UsuarioBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.cx.FrontendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.windows.asiento_modelo.AsientoModeloTableUi;
import com.massoftware.frontend.ui.windows.centro_de_costo_contable.CentroDeCostoContableTableUi;
import com.massoftware.frontend.ui.windows.cuenta_contable.CuentaContableTableUi;
import com.massoftware.frontend.ui.windows.ejercicio_contable.EjercicioContableTableUi;
import com.massoftware.frontend.ui.windows.punto_de_equilibrio.PuntoDeEquilibrioTableUi;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class ContabilidadGeneralMenu extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8506821800999861972L;

	private BackendContext cx;

	private UsuarioBO usuarioBO;
	private EjercicioContableBO ejercicioContableBO;

	private ComboBox ejercicioContableCBX;

	private Usuario usuario;

	public ContabilidadGeneralMenu(BackendContext cx, Usuario usuario) {

		try {

			this.cx = cx;
			this.usuario = usuario;

			initObjectBO();

			setMargin(true);
			// setSpacing(true);

			Label h1 = new Label("Contabilidad general");
			h1.addStyleName(ValoTheme.LABEL_H1);
			h1.addStyleName(ValoTheme.LABEL_COLORED);
			addComponent(h1);

			addComponent(getMenuBar());

			addComponent(getControlBar());

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	private void initObjectBO() {
		this.usuarioBO = (UsuarioBO) cx.buildBO(Usuario.class);
		this.ejercicioContableBO = cx.buildEjercicioContableBO();
	}

	private MenuBar getMenuBar() {

		Command click = new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 6678419241635254963L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show("Clicked " + selectedItem.getText());
			}
		};

		MenuBar menubar = new MenuBar();
		menubar.setWidth("100%");
		menubar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		final MenuBar.MenuItem archivos = menubar.addItem("Archivos", null);

		archivos.addItem("Plan de cuentas (orden -> cta de jerarquía) ...",
				click);

		archivos.addItem("Plan de cuentas ...", open(CuentaContable.class));

		Command openEjercicioContableTableUi = new Command() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5020933057074532017L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				openEjercicioContableTableUi();
			}
		};
		archivos.addItem("Ejercicios contables ...",
				openEjercicioContableTableUi);

		Command openAsientoModeloTableUi = new Command() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5689416708863299947L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				openAsientoModeloTableUi();
			}
		};
		archivos.addItem("Modelos de asientos", openAsientoModeloTableUi);

		Command openCentroDeCostoContableTableUi = new Command() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5689416708863299947L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				openCentroDeCostoContableTableUi();
			}
		};
		archivos.addItem("Centros de costos ...",
				openCentroDeCostoContableTableUi);

		Command openPuntoDeEquilibrioTableUi = new Command() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3890088916049691923L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				openPuntoDeEquilibrioTableUi();
			}
		};
		archivos.addItem("Puntos de equilibrio ...",
				openPuntoDeEquilibrioTableUi);

		archivos.addSeparator();
		archivos.addItem("Parámetros generales", click);
		archivos.addItem("Fecha de cierre por módulos", click);

		// final MenuBar.MenuItem edicion = menubar.addItem("Edición", null);

		final MenuBar.MenuItem asientos = menubar.addItem("Asientos", null);

		asientos.addItem("Nuevo asiento ...", click);
		asientos.addItem("Asientos realizados ...", click);
		asientos.addItem("Lotes de asientos importados ...", click);
		asientos.addItem("Anulación de asientos ...", click);

		final MenuBar.MenuItem procesos = menubar.addItem("Procesos", null);

		procesos.addItem("Control de asientos ...", click);
		procesos.addSeparator();
		procesos.addItem("Cierre contabilidad", click);

		final MenuBar.MenuItem informes = menubar.addItem("Informes", null);

		informes.addItem("Balance general", click);
		informes.addItem("Balance de comproboación de saldos", click);
		informes.addItem("Informe estado de resultados", click);
		informes.addSeparator();
		informes.addItem("Libro diario", click);
		informes.addItem("Mayor", click);
		informes.addSeparator();
		informes.addItem("Plan de cuenta ...", click);

		return menubar;
	}

	private HorizontalLayout getControlBar() throws Exception {

		HorizontalLayout row = new HorizontalLayout();
		row.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		row.setSpacing(true);

		Button planDeCuentas = new Button("");
		planDeCuentas.addStyleName(ValoTheme.BUTTON_HUGE);
		planDeCuentas.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		planDeCuentas.setDescription("Plan de cuentas (F5)");
		planDeCuentas.setIcon(FontAwesome.APPLE);
		planDeCuentas.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Notification.show("Clicked "
						+ event.getButton().getDescription());
			}
		});
		row.addComponent(planDeCuentas);

		Button nevoAsiento = new Button("");
		nevoAsiento.addStyleName(ValoTheme.BUTTON_HUGE);
		nevoAsiento.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		nevoAsiento.setDescription("Nuevo asiento (F6)");
		nevoAsiento.setIcon(FontAwesome.APPLE);
		nevoAsiento.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Notification.show("Clicked "
						+ event.getButton().getDescription());
			}
		});
		row.addComponent(nevoAsiento);

		Button mayor = new Button("");
		mayor.addStyleName(ValoTheme.BUTTON_HUGE);
		mayor.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		mayor.setDescription("Mayor (F7)");
		mayor.setIcon(FontAwesome.APPLE);
		mayor.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Notification.show("Clicked "
						+ event.getButton().getDescription());
			}
		});
		row.addComponent(mayor);

		Label ejercicioContableTitle = new Label("Ejercicio contable:");
		ejercicioContableTitle.addStyleName(ValoTheme.LABEL_COLORED);
		ejercicioContableTitle.addStyleName(ValoTheme.TEXTAREA_ALIGN_RIGHT);
		ejercicioContableTitle.addStyleName(ValoTheme.LABEL_H2);
		// ejercicioContableTitle.addStyleName(ValoTheme.BUTTON_HUGE);
		// addComponent(ejercicioContableTitle);

		ejercicioContableCBX = new ComboBox();
		ejercicioContableCBX.addStyleName(ValoTheme.COMBOBOX_HUGE);
		ejercicioContableCBX.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
		ejercicioContableCBX.addStyleName(ValoTheme.COMBOBOX_ALIGN_CENTER);
		ejercicioContableCBX.setNullSelectionAllowed(false);

		String itemCaptionPropertyId = cx.getEntityMetaData(
				EjercicioContable.class.getCanonicalName()).getAttNames()[0];
		ejercicioContableCBX.setItemCaptionPropertyId(itemCaptionPropertyId);

		loadEjerciciosCBX();

		EjercicioContable ejercicioContableDefault = usuario
				.getEjercicioContable();
		if (ejercicioContableDefault != null
				&& ejercicioContableDefault.getEjercicio() != null) {
			ejercicioContableCBX.setValue(ejercicioContableDefault);
		} else {
			EjercicioContable maxEjercicioContable = ejercicioContableBO
					.findMaxEjercicio();
			ejercicioContableCBX.setValue(maxEjercicioContable);
		}

		ejercicioContableCBX.addValueChangeListener(e -> {
			changeEjercicioContableDefault();
		});

		HorizontalLayout ejercicioContableBlock = new HorizontalLayout();
		// ejercicioContableBlock.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		ejercicioContableBlock.addComponents(ejercicioContableTitle,
				ejercicioContableCBX);
		ejercicioContableBlock.setSpacing(false);
		ejercicioContableBlock.setMargin(false);

		row.addComponent(ejercicioContableBlock);

		return row;
	}

	private void changeEjercicioContableDefault() {
		try {

			EjercicioContable ejercicioContableDefault = (EjercicioContable) ejercicioContableCBX
					.getValue();

			usuario.setEjercicioContable(ejercicioContableDefault);

			usuarioBO.update(usuario, usuario.clone(), usuario);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void loadEjerciciosCBX() {
		try {

			List<EjercicioContable> items = ejercicioContableBO.findAll();

			ejercicioContableCBX
					.setContainerDataSource(new BeanItemContainer<>(
							EjercicioContable.class, items));

			if (ejercicioContableCBX.getContainerDataSource() == null
					|| ejercicioContableCBX.getContainerDataSource().size() == 0) {
				ejercicioContableCBX.setEnabled(false);
			}

		} catch (Exception e) {
			ejercicioContableCBX
					.setContainerDataSource(new BeanItemContainer<>(
							EjercicioContable.class, new ArrayList<>()));

			LogAndNotification.print(e);
		}

	}

	private void openEjercicioContableTableUi() {
		try {

			String title = cx.getEntityMetaData(
					EjercicioContable.class.getCanonicalName())
					.getLabelPlural();

			EjercicioContableTableUi ui = new EjercicioContableTableUi(cx,
					usuario);

			Window win = new Window(title);
			// win.setWidth((float)(ejerciciosContablesDesing.rootVH.getWidth()
			// * 1.2),
			// ejerciciosContablesDesing.grid.getHeightUnits());
			win.setWidth("660px");
			win.setClosable(true);
			win.setResizable(false);
			win.setContent(ui);
			// win.addCloseShortcut(KeyCode.ESCAPE, null);
			getUI().addWindow(win);

			win.center();
			win.focus();

		} catch (Exception e) {

			LogAndNotification.print(e);
		}

	}

	private void openCentroDeCostoContableTableUi() {
		try {
			// PlanDeCuentaTableUi ui = new PlanDeCuentaTableUi(cx, usuario);

			Window win = new Window("Centros de costos");
			win.setClosable(true);
			win.setResizable(false);
			CentroDeCostoContableTableUi ui = new CentroDeCostoContableTableUi(
					win, cx, usuario);
			win.setContent(ui);
			getUI().addWindow(win);
			win.center();
			win.focus();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// private void openPuntoDeEquilibrioTableUi2() {
	// try {
	//
	// String title = cx.getEntityMetaData(
	// PuntoDeEquilibrio.class.getCanonicalName())
	// .getLabelPlural();
	//
	// PuntoDeEquilibrioTableUi ui = new PuntoDeEquilibrioTableUi(cx,
	// usuario);
	//
	// Window win = new Window(title);
	// // win.setWidth((float)(ejerciciosContablesDesing.rootVH.getWidth()
	// // * 1.2),
	// // ejerciciosContablesDesing.grid.getHeightUnits());
	// win.setWidth("660px");
	// win.setClosable(true);
	// win.setResizable(false);
	// win.setContent(ui);
	// // win.addCloseShortcut(KeyCode.ESCAPE, null);
	// getUI().addWindow(win);
	//
	// win.center();
	// win.focus();
	//
	// } catch (Exception e) {
	//
	// LogAndNotification.print(e);
	// }
	//
	// }

	private void openPuntoDeEquilibrioTableUi() {
		try {
			// PlanDeCuentaTableUi ui = new PlanDeCuentaTableUi(cx, usuario);

			Window win = new Window("Puntos de equilibrios");
			win.setClosable(true);
			win.setResizable(false);
			PuntoDeEquilibrioTableUi ui = new PuntoDeEquilibrioTableUi(win, cx,
					usuario);
			win.setContent(ui);
			getUI().addWindow(win);
			win.center();
			win.focus();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void openAsientoModeloTableUi() {
		try {
			// PlanDeCuentaTableUi ui = new PlanDeCuentaTableUi(cx, usuario);

			Window win = new Window("Modelos de asientos");
			win.setClosable(true);
			win.setResizable(false);
			AsientoModeloTableUi ui = new AsientoModeloTableUi(win, cx, usuario);
			win.setContent(ui);
			getUI().addWindow(win);
			win.center();
			win.focus();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	private Component getThis() {
		return this;
	}

	@SuppressWarnings("rawtypes")
	private Command open(Class classModel) {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				FrontendContext.openWindows(true, true, true,  true, true, getThis(), classModel, cx, usuario, null, null, null);

			}
		};
	}

}
