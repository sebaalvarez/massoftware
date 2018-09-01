package com.massoftware.frontend.custom.windows;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.bo.AsientoModeloItemBO;
import com.massoftware.frontend.SessionVar;
import com.massoftware.model.AsientoModelo;
import com.massoftware.model.AsientoModeloItem;
import com.massoftware.model.EjercicioContable;
import com.vaadin.data.Property;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Window;

public class AsientoModeloItemTableUi extends
		StandardTableUi<AsientoModeloItem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261842256751L;

	public AsientoModelo asientoModeloFilter;

	public AsientoModeloItemTableUi(StandarTableUiPagedConf pagedConf,
			boolean shortcut, boolean agregar, boolean modificar,
			boolean copiar, boolean eliminar, Window window,
			SessionVar sessionVar, Class<AsientoModeloItem> classModel,
			String pidFiltering, Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty) {

		super(pagedConf, shortcut, agregar, modificar, copiar, eliminar,
				window, sessionVar, classModel, pidFiltering, searchFilter,
				searchProperty, null);

		window.setWidth("1300px");

		HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
		hsplit.setSplitPosition(550, Unit.PIXELS);
		this.setCompositionRoot(hsplit);

		AsientoModeloTableUi asientoModeloTableUi = new AsientoModeloTableUi(
				true, true, true, true, true, null, sessionVar,
				AsientoModelo.class, null, null, null, this);

		hsplit.setFirstComponent(asientoModeloTableUi);

		hsplit.setSecondComponent(rootVL);

		this.setCompositionRoot(hsplit);

	}

	protected List<AsientoModeloItem> reloadDataList() throws Exception {

		return ((AsientoModeloItemBO) sessionVar.getCx().buildBO(classModel))
				.findAll(asientoModeloFilter);

	}

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	protected StandardFormUi openFormAgregar() throws Exception {

		AsientoModeloItem item = AsientoModeloItem.class.newInstance();

		item.setAsientoModelo(asientoModeloFilter);

		StandardFormUi<AsientoModeloItem> form = new StandardFormUi<AsientoModeloItem>(
				sessionVar, classModel, StandardFormUi.INSERT_MODE, this, item) {

			protected List getOtrosFiltros() {

				if (itemsBIC.getItemIds().size() > 0
						&& itemsBIC.getItemIds().get(0).getCuentaContable() != null) {

					EjercicioContable ejercicioContable = itemsBIC.getItemIds()
							.get(0).getCuentaContable().getEjercicioContable();

					List list = new ArrayList();
					list.add(ejercicioContable);

					return list;
				}

				return null;

			}

		};

		return form;

	}

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	protected StandardFormUi openFormCopiar(AsientoModeloItem item)
			throws Exception {

		AsientoModeloItem o = (AsientoModeloItem) item.copy();

		// item.setAsientoModelo(asientoModeloFilter);
		// o.setAsientoModelo(asientoModeloFilter);

		o._setEjercicioContable(item._getEjercicioContable());

		StandardFormUi<AsientoModeloItem> form = new StandardFormUi<AsientoModeloItem>(
				sessionVar, classModel, StandardFormUi.COPY_MODE, this, o, item) {

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

	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	protected StandardFormUi openFormModificar(AsientoModeloItem item)
			throws Exception {

		StandardFormUi<AsientoModeloItem> form = new StandardFormUi<AsientoModeloItem>(
				sessionVar, classModel, StandardFormUi.UPDATE_MODE, this, item) {

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
