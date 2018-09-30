package com.massoftware.frontend.custom.windows;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.SessionVar;
import com.massoftware.backend.bo.AsientoModeloItemBO;
import com.massoftware.model.AsientoModelo;
import com.massoftware.model.AsientoModeloItem;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.windows.LogAndNotification;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

class AsientoModeloItemTableUi extends StandardTableUi<AsientoModeloItem> {

	//

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261842256751L;

	public AsientoModelo asientoModeloFilter;

	public AsientoModeloTableUi asientoModeloTableUi;
	
	public VerticalLayout vh;

	protected AsientoModeloItemTableUi(StandarTableUiPagedConf pagedConf,
			StandarTableUiToolbarConf toolbarConf, Window window,
			SessionVar sessionVar, Class<AsientoModeloItem> classModel,
			StandarTableUiFilteringSet filteringSet) {

		super(new StandarTableUiPagedConf(false, false, false),
				new StandarTableUiToolbarConf(true, true, false, true, false),
				window, sessionVar, classModel, filteringSet);

		vh = new VerticalLayout();
		this.setCompositionRoot(vh);

		HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
		hsplit.setSplitPosition(325, Unit.PIXELS);
		vh.addComponent(hsplit);

		asientoModeloTableUi = new AsientoModeloTableUi(sessionVar, this);

		hsplit.setFirstComponent(asientoModeloTableUi);
		hsplit.setSecondComponent(rootVL);

	}

	protected List<AsientoModeloItem> reloadDataList(String orderBy,
			String where, Object value, int limit, int offset) throws Exception {

		return ((AsientoModeloItemBO) sessionVar.getCx().buildBO(classModel))
				.findAll(orderBy, asientoModeloFilter);

	}

	protected void agregarBTNClick() {
		try {

			if (toolbarConf.isAgregar()
					&& asientoModeloTableUi.itemsGRD.getSelectedRow() != null) {

				itemsGRD.select(null);
				// getUI().addWindow(openFormAgregar().getWindow());
				CuentaContableTableUi cuentaContableTableUi = (CuentaContableTableUi) WindowsFactory
						.openWindow(this, CuentaContable.class, sessionVar);

				cuentaContableTableUi.window.setModal(true);

				HorizontalLayout barraDeHerramientasFila3 = new HorizontalLayout();
				barraDeHerramientasFila3.setSpacing(true);

				cuentaContableTableUi.rootVL
						.addComponent(barraDeHerramientasFila3);
				cuentaContableTableUi.rootVL.setComponentAlignment(
						barraDeHerramientasFila3, Alignment.MIDDLE_CENTER);

				// ----------------------------------------------

				Button seleccionarBTN = new Button();
				seleccionarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
				seleccionarBTN.addStyleName(ValoTheme.BUTTON_TINY);
				seleccionarBTN.setIcon(FontAwesome.CHECK_SQUARE_O);
				seleccionarBTN.setCaption("Agregar ->");

				seleccionarBTN.addClickListener(e -> {
					addCuentaContable(cuentaContableTableUi);

				});

				barraDeHerramientasFila3.addComponent(seleccionarBTN);

			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void addCuentaContable(CuentaContableTableUi cuentaContableTableUi) {
		try {

			if (cuentaContableTableUi.itemsGRD.getSelectedRow() != null) {

				CuentaContable cuentaContable = (CuentaContable) cuentaContableTableUi.itemsGRD
						.getSelectedRow();

				if (cuentaContable != null) {

					AsientoModeloItem newAsientoModeloItem = new AsientoModeloItem();
					// newAsientoModeloItem.setId(UUID.randomUUID().toString());
					newAsientoModeloItem.setAsientoModelo(asientoModeloFilter);
					newAsientoModeloItem.setCuentaContable(cuentaContable);
					newAsientoModeloItem.setRegistro(this.itemsBIC.size() + 1);

					// ------------------------------------------
					// itemsBIC.addBean(newAsientoModeloItem);
					// ------------------------------------------
					// List<AsientoModeloItem> oldList = this.itemsBIC
					// .getItemIds();
					//
					// List<AsientoModeloItem> newList = new
					// ArrayList<AsientoModeloItem>();
					//
					// for (AsientoModeloItem oldItem : oldList) {
					// newList.add(oldItem);
					// }
					//
					// newList.add(newAsientoModeloItem);
					//
					// this.reloadData(newList);
					// ------------------------------------------

					this.sessionVar
							.getCx()
							.buildBO(AsientoModeloItem.class)
							.insert(newAsientoModeloItem,
									this.sessionVar.getUsuario());
					this.reloadData();
					// ------------------------------------------

				}
			}

		} catch (Exception ex) {
			LogAndNotification.print(ex);
		}
	}

	// @SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	// protected StandardFormUi openFormAgregar() throws Exception {
	//
	// AsientoModeloItem item = AsientoModeloItem.class.newInstance();
	//
	// item.setAsientoModelo(asientoModeloFilter);
	//
	// StandardFormUi<AsientoModeloItem> form = new
	// StandardFormUi<AsientoModeloItem>(
	// sessionVar, classModel, StandardFormUi.INSERT_MODE, this, item) {
	//
	// protected List getOtrosFiltros() {
	//
	// if (itemsBIC.getItemIds().size() > 0
	// && itemsBIC.getItemIds().get(0).getCuentaContable() != null) {
	//
	// EjercicioContable ejercicioContable = itemsBIC.getItemIds()
	// .get(0).getCuentaContable().getEjercicioContable();
	//
	// List list = new ArrayList();
	// list.add(ejercicioContable);
	//
	// return list;
	// }
	//
	// return null;
	//
	// }
	//
	// };
	//
	// return form;
	//
	// }

	// @SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	// protected StandardFormUi openFormCopiar(AsientoModeloItem item)
	// throws Exception {
	//
	// AsientoModeloItem o = (AsientoModeloItem) item.copy();
	//
	// // item.setAsientoModelo(asientoModeloFilter);
	// // o.setAsientoModelo(asientoModeloFilter);
	//
	// o._setEjercicioContable(item._getEjercicioContable());
	//
	// StandardFormUi<AsientoModeloItem> form = new
	// StandardFormUi<AsientoModeloItem>(
	// sessionVar, classModel, StandardFormUi.COPY_MODE, this, o, item) {
	//
	// protected List getOtrosFiltros() {
	// EjercicioContable ejercicioContable = dtoBI.getBean()
	// ._getEjercicioContable();
	// List list = new ArrayList();
	// list.add(ejercicioContable);
	//
	// return list;
	// }
	//
	// };
	//
	// return form;
	//
	// }

	protected StandardFormUi<AsientoModeloItem> openFormModificar(
			AsientoModeloItem item) throws Exception {

		StandardFormUi<AsientoModeloItem> form = new StandardFormUi<AsientoModeloItem>(
				sessionVar, classModel, StandardFormUi.UPDATE_MODE, this, item) {

			private static final long serialVersionUID = 6254352080004564598L;

			@SuppressWarnings({ "rawtypes", "unchecked" })
			protected List getOtrosFiltros() {
				EjercicioContable ejercicioContable = dtoBI.getBean()
						._getEjercicioContable();
				List list = new ArrayList();
				list.add(ejercicioContable);

				return list;
			}

		};

		return form;
	}

}
