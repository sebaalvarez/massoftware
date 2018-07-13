package com.massoftware.frontend.ui.custom.windows;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.bo.AsientoModeloItemBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.window.StandardFormUi;
import com.massoftware.frontend.ui.util.window.StandardTableUi;
import com.massoftware.model.AsientoModelo;
import com.massoftware.model.AsientoModeloItem;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;
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

	public AsientoModeloItemTableUi(boolean paged, boolean pagedCount,
			boolean pagedOrder, boolean shortcut, boolean agregar,
			boolean modificar, boolean copiar, boolean eliminar, Window window,
			BackendContext cx, Usuario usuario,
			Class<AsientoModeloItem> classModel, String pidFiltering,
			Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty) {

		super(paged, pagedCount, pagedOrder, shortcut, agregar, modificar,
				copiar, eliminar, window, cx, usuario, classModel,
				pidFiltering, searchFilter, searchProperty, null);

		window.setWidth("1300px");

		HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
		hsplit.setSplitPosition(550, Unit.PIXELS);
		this.setCompositionRoot(hsplit);

		AsientoModeloTableUi asientoModeloTableUi = new AsientoModeloTableUi(
				false, false, false, true, true, true, true, true, null, cx,
				usuario, AsientoModelo.class, null, null, null, this);

		hsplit.setFirstComponent(asientoModeloTableUi);

		hsplit.setSecondComponent(rootVL);

		this.setCompositionRoot(hsplit);

	}

	protected List<AsientoModeloItem> reloadDataList() throws Exception {

		return ((AsientoModeloItemBO) cx.buildBO(classModel))
				.findAll(asientoModeloFilter);

	}

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	protected StandardFormUi openFormAgregar() throws Exception {

		AsientoModeloItem item = AsientoModeloItem.class.newInstance();

		item.setAsientoModelo(asientoModeloFilter);

		StandardFormUi<AsientoModeloItem> form = new StandardFormUi<AsientoModeloItem>(
				usuario, classModel, StandardFormUi.INSERT_MODE, cx, this, item) {

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
				usuario, classModel, StandardFormUi.COPY_MODE, cx, this, o,
				item) {

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
				usuario, classModel, StandardFormUi.UPDATE_MODE, cx, this, item) {

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
