package com.massoftware.frontend.ui.custom.menu;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.bo.UsuarioBO;
import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.ui.util.AbstractMenu;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.Usuario;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

public class ContabilidadGeneralMenu extends AbstractMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8506821800939861972L;

	private UsuarioBO usuarioBO;
	private EjercicioContableBO ejercicioContableBO;

	private ComboBox ejercicioContableCBX;

	public ContabilidadGeneralMenu(SessionVar sessionVar) {
		super("Contabilidad general", sessionVar);
		initObjectBO();

	}

	protected void preinit() {
		initObjectBO();
	}

	private void initObjectBO() {
		this.usuarioBO = (UsuarioBO) sessionVar.getCx().buildBO(Usuario.class);
		this.ejercicioContableBO = (EjercicioContableBO) sessionVar.getCx()
				.buildBO(EjercicioContable.class);
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
		a3.setEnabled(false);
		a4.setEnabled(false);
		a5.setEnabled(false);
		a6.setEnabled(false);
		a7.setEnabled(false);

		a1.addItem("Plan de cuentas (orden -> cta de jerarquía) ...", null)
				.setEnabled(false);
		a1.addItem("Plan de cuentas ...", open(CuentaContable.class))
				.setEnabled(false);
		a1.addItem("Ejercicios contables ...", open(EjercicioContable.class));
		a1.addItem("Modelos de asientos", null).setEnabled(false);
		a1.addItem("Centros de costos ...", open(CentroDeCostoContable.class));
		a1.addItem("Puntos de equilibrio ...", open(PuntoDeEquilibrio.class));
		a1.addSeparator();
		a1.addItem("Parámetros generales", null).setEnabled(false);
		a1.addItem("Fecha de cierre por módulos", null).setEnabled(false);

		// asientos.addItem("Nuevo asiento ...", null);
		// asientos.addItem("Asientos realizados ...", null);
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

	protected HorizontalLayout getControlBar() throws Exception {

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

		String itemCaptionPropertyId = sessionVar.getCx()
				.getEntityMetaData(EjercicioContable.class.getCanonicalName())
				.getAttNames()[0];
		ejercicioContableCBX.setItemCaptionPropertyId(itemCaptionPropertyId);

		loadEjerciciosCBX();

		EjercicioContable ejercicioContableDefault = sessionVar.getUsuario()
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

			sessionVar.getUsuario().setEjercicioContable(
					ejercicioContableDefault);

			usuarioBO.update(sessionVar.getUsuario(), (Usuario) sessionVar
					.getUsuario().clone(), sessionVar.getUsuario());

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

}
