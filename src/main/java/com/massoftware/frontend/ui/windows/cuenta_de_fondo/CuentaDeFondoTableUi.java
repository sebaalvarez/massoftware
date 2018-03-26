package com.massoftware.frontend.ui.windows.cuenta_de_fondo;

import java.util.List;

import org.cendra.common.model.EntityId;
import org.cendra.ex.crud.DeleteForeingObjectConflictException;

import com.massoftware.backend.bo.CuentaDeFondoABO;
import com.massoftware.backend.bo.CuentaDeFondoBO;
import com.massoftware.backend.bo.CuentaDeFondoGrupoBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.StandardFormUi;
import com.massoftware.frontend.ui.util.StandardTableUi;
import com.massoftware.frontend.ui.util.YesNoDialog;
import com.massoftware.frontend.ui.windows.chequera.ChequeraTableUi;
import com.massoftware.frontend.xmd.BuilderXMD;
import com.massoftware.model.CuentaDeFondo;
import com.massoftware.model.CuentaDeFondoA;
import com.massoftware.model.CuentaDeFondoGrupo;
import com.massoftware.model.CuentaDeFondoRubro;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class CuentaDeFondoTableUi extends StandardTableUi<CuentaDeFondoA> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261872256758L;

	private CheckBox filtroTodasCHK;
	private Tree tree;

	private HorizontalLayout treeBarraDeHerramientasFila1;
	private Button treeAgregarRubroBTN;
	private Button treeAgregarGrupoBTN;
	private Button treeModificarBTN;
	// private Button treeCopiarBTN;
	private HorizontalLayout treeBarraDeHerramientasFila2;
	private Button treeEliminarBTN;

	public CuentaDeFondoTableUi(boolean shortcut, boolean agregar, boolean modificar,
			boolean copiar, boolean eliminar, Window window, BackendContext cx,
			Usuario usuario, Class<CuentaDeFondoA> classModel,
			String pidFiltering, Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty) {

		super(shortcut, agregar, modificar, copiar, eliminar, window, cx, usuario,
				classModel, pidFiltering, searchFilter, searchProperty);

		init(agregar, modificar, copiar, eliminar, window, cx, usuario,
				classModel, pidFiltering, searchFilter, searchProperty, null);

	}

	public CuentaDeFondoTableUi(boolean shortcut, boolean agregar, boolean modificar,
			boolean copiar, boolean eliminar, Window window, BackendContext cx,
			Usuario usuario, Class<CuentaDeFondoA> classModel,
			String pidFiltering, Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty,
			ChequeraTableUi chequeraTableUi) {

		super(shortcut, agregar, modificar, copiar, eliminar, window, cx, usuario,
				classModel, pidFiltering, searchFilter, searchProperty);

		init(agregar, modificar, copiar, eliminar, window, cx, usuario,
				classModel, pidFiltering, searchFilter, searchProperty, chequeraTableUi);

	}

	public void init(boolean agregar, boolean modificar, boolean copiar,
			boolean eliminar, Window window, BackendContext cx,
			Usuario usuario, Class<CuentaDeFondoA> classModel,
			String pidFiltering, Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty,
			ChequeraTableUi chequeraTableUi) {

		window.setWidth("1300px");

		HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
		hsplit.setSplitPosition(550, Unit.PIXELS);
		// hsplit.setHeight("500px");
		this.setCompositionRoot(hsplit);

		hsplit.setFirstComponent(buildTreePanel());

		hsplit.setSecondComponent(rootVL);

		this.setCompositionRoot(hsplit);

	}

	private VerticalLayout buildTreePanel() {

		VerticalLayout vl = new VerticalLayout();
		vl.setMargin(true);
		vl.setSpacing(true);
		// vl.setWidth("100%");

		Panel panel = new Panel("Rubros y grupos");
		panel.setHeight("470px");
		panel.setContent(tree);
		vl.addComponent(panel);

		treeBarraDeHerramientasFila1 = new HorizontalLayout();
		treeBarraDeHerramientasFila1.setSpacing(true);

		vl.addComponent(treeBarraDeHerramientasFila1);
		vl.setComponentAlignment(treeBarraDeHerramientasFila1,
				Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		treeAgregarRubroBTN = new Button();
		treeAgregarRubroBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		treeAgregarRubroBTN.addStyleName(ValoTheme.BUTTON_TINY);
		treeAgregarRubroBTN.setIcon(FontAwesome.PLUS);
		treeAgregarRubroBTN.setCaption("Agregar rubro");
		treeAgregarRubroBTN.setDescription(treeAgregarRubroBTN.getCaption()
				+ " (Ctrl+A)");
		treeAgregarRubroBTN.addClickListener(e -> {
			agregarRubroBTNClick();
		});

		treeBarraDeHerramientasFila1.addComponent(treeAgregarRubroBTN);

		// ----------------------------------------------

		treeAgregarGrupoBTN = new Button();
		treeAgregarGrupoBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		treeAgregarGrupoBTN.addStyleName(ValoTheme.BUTTON_TINY);
		treeAgregarGrupoBTN.setIcon(FontAwesome.PLUS);
		treeAgregarGrupoBTN.setCaption("Agregar grupo");
		treeAgregarGrupoBTN.setDescription(treeAgregarGrupoBTN.getCaption()
				+ " (Ctrl+A)");
		treeAgregarGrupoBTN.addClickListener(e -> {
			agregarGrupoBTNClick();
		});

		treeBarraDeHerramientasFila1.addComponent(treeAgregarGrupoBTN);

		// ----------------------------------------------

		treeModificarBTN = new Button();
		treeModificarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
		treeModificarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		treeModificarBTN.setIcon(FontAwesome.PENCIL);
		treeModificarBTN.setCaption("Modificar");
		treeModificarBTN.setDescription(treeModificarBTN.getCaption()
				+ " (Ctrl+M)");
		treeModificarBTN.addClickListener(e -> {
			modificarTreeBTNClick();
		});

		treeBarraDeHerramientasFila1.addComponent(treeModificarBTN);

		// ----------------------------------------------

		// treeCopiarBTN = new Button();
		// treeCopiarBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		// treeCopiarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		// treeCopiarBTN.setIcon(FontAwesome.PLUS_SQUARE);
		// treeCopiarBTN.setCaption("Copiar");
		// treeCopiarBTN.setDescription(treeCopiarBTN.getCaption() +
		// " (Ctrl+C)");
		// treeCopiarBTN.addClickListener(e -> {
		// copiarTreeBTNClick();
		// });
		//
		// treeBarraDeHerramientasFila1.addComponent(treeCopiarBTN);

		// ----------------------------------------------

		treeBarraDeHerramientasFila2 = new HorizontalLayout();
		treeBarraDeHerramientasFila2.setSpacing(true);

		vl.addComponent(treeBarraDeHerramientasFila2);
		vl.setComponentAlignment(treeBarraDeHerramientasFila2,
				Alignment.MIDDLE_RIGHT);

		// ----------------------------------------------

		treeEliminarBTN = new Button();
		treeEliminarBTN.addStyleName(ValoTheme.BUTTON_DANGER);
		treeEliminarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		treeEliminarBTN.setIcon(FontAwesome.TRASH);
		treeEliminarBTN.setCaption("Eliminar");

		treeEliminarBTN.addClickListener(e -> {
			eliminarTreeBTNClick();
		});

		treeBarraDeHerramientasFila2.addComponent(treeEliminarBTN);

		// --------------------------------------------------

		// this.addShortcutListener(new ShortcutListener("ENTER", KeyCode.ENTER,
		// new int[] {}) {
		//
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public void handleAction(Object sender, Object target) {
		// if (target.equals(tree)) {
		// modificarTreeBTNClick();
		// }
		//
		// }
		// });

		// --------------------------------------------------

		// this.addShortcutListener(new ShortcutListener("CTRL+A", KeyCode.A,
		// new int[] { ModifierKey.CTRL }) {
		//
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public void handleAction(Object sender, Object target) {
		//
		// if (tree.getValue() instanceof CuentaDeFondoGrupo) {
		//
		// agregarGrupoBTNClick();
		//
		// } else if (tree.getValue() instanceof CuentaDeFondoRubro) {
		//
		// agregarRubroBTNClick();
		//
		// }
		//
		// }
		// });
		// --------------------------------------------------

		// this.addShortcutListener(new ShortcutListener("CTRL+M", KeyCode.M,
		// new int[] { ModifierKey.CTRL }) {
		//
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public void handleAction(Object sender, Object target) {
		// modificarTreeBTNClick();
		// }
		// });
		// --------------------------------------------------

		// this.addShortcutListener(new ShortcutListener("CTRL+C", KeyCode.C,
		// new int[] { ModifierKey.CTRL }) {
		//
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public void handleAction(Object sender, Object target) {
		// copiarBTNClick();
		// }
		// });

		// ----------------------------------------------

		return vl;
	}

	private Tree buildTree() {
		try {
			tree = new Tree("Estructura");

			reloadDataTree();

			// tree.addItemClickListener(new ItemClickEvent.ItemClickListener()
			// {
			//
			// private static final long serialVersionUID =
			// -6287768389172009900L;
			//
			// public void itemClick(ItemClickEvent event) {
			// //
			// Notification.show(event.getItem().getItemPropertyIds().toString(),
			// // Notification.Type.HUMANIZED_MESSAGE);
			// }
			// });

			tree.addValueChangeListener(event -> {
				if (event.getProperty() != null
						&& event.getProperty().getValue() != null) {

					treeValueChangeListener(event.getProperty().getValue());

					// Notification.show("The cat is in "
					// + event.getProperty().getValue().getClass(),
					// Notification.Type.HUMANIZED_MESSAGE);

				}
			});

			return tree;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
		return null;
	}

	protected void addControlsFilters() throws Exception {

		buildTree();

		// ----------------------------------

		filtroTodasCHK = BuilderXMD.buildCHK();
		filtroTodasCHK.setCaption("Incluir cuentas obsoletas");

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

	@SuppressWarnings("unchecked")
	private void reloadDataTree() throws Exception {

		tree.removeAllItems();

		List<CuentaDeFondoRubro> rubros = cx.buildBO(CuentaDeFondoRubro.class)
				.findAll();

		tree.addItem("Todos");

		int c = 0;

		for (CuentaDeFondoRubro rubro : rubros) {

			tree.addItem(rubro);
			tree.setParent(rubro, "Todos");

			List<CuentaDeFondoGrupo> grupos = ((CuentaDeFondoGrupoBO) cx
					.buildBO(CuentaDeFondoGrupo.class)).findByRubro(rubro
					.getCodigo());

			for (CuentaDeFondoGrupo grupo : grupos) {
				grupo._setfullToString(false);
				tree.addItem(grupo);
				tree.setParent(grupo, rubro);
				tree.setChildrenAllowed(grupo, false);
				if (c == 0) {

					tree.select(grupo);

					c++;
				}

			}

			tree.expandItem(rubro);

		}

		tree.expandItem("Todos");

	}

	public void reloadData() throws Exception {
		try {

			reloadDataTree();

			super.reloadData();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected List<CuentaDeFondoA> reloadDataList() throws Exception {

		Object item = tree.getValue();

		if (item instanceof CuentaDeFondoGrupo) {

			if (filtroTodasCHK.getValue() == true) {

				return ((CuentaDeFondoABO) cx.buildBO(CuentaDeFondoA.class))
						.findAll((CuentaDeFondoGrupo) item);

			} else {
				return ((CuentaDeFondoABO) cx.buildBO(CuentaDeFondoA.class))
						.findActivas((CuentaDeFondoGrupo) item);
			}
		} else if (item instanceof CuentaDeFondoRubro) {

			if (filtroTodasCHK.getValue() == true) {

				return ((CuentaDeFondoABO) cx.buildBO(CuentaDeFondoA.class))
						.findAll((CuentaDeFondoRubro) item);

			} else {
				return ((CuentaDeFondoABO) cx.buildBO(CuentaDeFondoA.class))
						.findActivas((CuentaDeFondoRubro) item);
			}
		} else if (item.equals("Todos")) {

			if (filtroTodasCHK.getValue() == true) {

				return cx.buildBO(CuentaDeFondoA.class).findAll();

			} else {

				return ((CuentaDeFondoABO) cx.buildBO(CuentaDeFondoA.class))
						.findActivas();
			}
		}
		return null;

	}

	private void agregarRubroBTNClick() {
		try {

			tree.select(null);
			getUI().addWindow(openFormAgregarRubro().getWindow());

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void agregarGrupoBTNClick() {
		try {

			tree.select(null);
			getUI().addWindow(openFormAgregarGrupo().getWindow());

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void modificarTreeBTNClick() {
		try {

			if (tree.getValue() != null) {

				getUI().addWindow(
						openFormModificarTree(tree.getValue()).getWindow());

			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// private void copiarTreeBTNClick() {
	// try {
	//
	// if (tree.getValue() != null) {
	//
	// getUI().addWindow(
	// openFormCopiarTree(tree.getValue()).getWindow());
	// }
	//
	// } catch (Exception e) {
	// LogAndNotification.print(e);
	// }
	// }

	private void eliminarTreeBTNClick() {
		try {

			if (tree.getValue() != null) {

				getUI().addWindow(
						new YesNoDialog("Eliminar",
								"Esta seguro de eliminar el ítem "
										+ tree.getValue(),
								new YesNoDialog.Callback() {
									public void onDialogResult(boolean yes) {
										if (yes) {
											deleteTree();
										}
									}
								}));
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private StandardFormUi openFormAgregarRubro() throws Exception {

		return new StandardFormUi(usuario, CuentaDeFondoRubro.class,
				StandardFormUi.INSERT_MODE, cx, this,
				CuentaDeFondoRubro.class.newInstance());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private StandardFormUi openFormAgregarGrupo() throws Exception {

		return new StandardFormUi(usuario, CuentaDeFondoGrupo.class,
				StandardFormUi.INSERT_MODE, cx, this,
				CuentaDeFondoGrupo.class.newInstance());
	}

	@SuppressWarnings({ "rawtypes" })
	private StandardFormUi openFormModificarTree(Object item) {

		if (item instanceof CuentaDeFondoGrupo) {

			return new StandardFormUi<CuentaDeFondoGrupo>(usuario,
					CuentaDeFondoGrupo.class, StandardFormUi.UPDATE_MODE, cx,
					this, (CuentaDeFondoGrupo) item);

		} else if (item instanceof CuentaDeFondoRubro) {

			return new StandardFormUi<CuentaDeFondoRubro>(usuario,
					CuentaDeFondoRubro.class, StandardFormUi.UPDATE_MODE, cx,
					this, (CuentaDeFondoRubro) item);

		}
		return null;

	}

	// @SuppressWarnings({ "rawtypes" })
	// protected StandardFormUi openFormCopiarTree(Object item) throws Exception
	// {
	//
	// if (item instanceof CuentaDeFondoGrupo) {
	//
	// CuentaDeFondoGrupo o = ((CuentaDeFondoGrupo) item).clone();
	//
	// return new StandardFormUi<CuentaDeFondoGrupo>(usuario,
	// CuentaDeFondoGrupo.class, StandardFormUi.COPY_MODE, cx,
	// this, o);
	//
	// } else if (item instanceof CuentaDeFondoRubro) {
	//
	// CuentaDeFondoRubro o = ((CuentaDeFondoRubro) item).clone();
	//
	// return new StandardFormUi<CuentaDeFondoRubro>(usuario,
	// CuentaDeFondoRubro.class, StandardFormUi.COPY_MODE, cx,
	// this, o);
	//
	// }
	// return null;
	//
	// }

	@SuppressWarnings("unchecked")
	private void deleteTree() {
		try {

			if (tree.getValue() != null) {

				if (tree.getValue() instanceof CuentaDeFondoGrupo) {

					CuentaDeFondoGrupo item = (CuentaDeFondoGrupo) tree
							.getValue();
					try {

						// deleteItem(item);
						cx.buildBO(CuentaDeFondoGrupo.class).delete(item);

					} catch (DeleteForeingObjectConflictException e) {
						LogAndNotification.print(e, "Ítem " + tree.getValue());
						return;
					}

				} else if (tree.getValue() instanceof CuentaDeFondoRubro) {

					CuentaDeFondoRubro item = (CuentaDeFondoRubro) tree
							.getValue();
					try {

						// deleteItem(item);

						cx.buildBO(CuentaDeFondoRubro.class).delete(item);

					} catch (DeleteForeingObjectConflictException e) {
						LogAndNotification.print(e, "Ítem " + tree.getValue());
						return;
					}

				}

				String msg = "Se eliminó con éxito el ítem " + tree.getValue();

				LogAndNotification.printSuccessOk(msg);

				loadData();
			}

		} catch (DeleteForeingObjectConflictException e) {
			LogAndNotification.print(e, "Ítem");
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void treeValueChangeListener(Object item) {
		try {

			super.reloadData();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void filtroTodasCHKValueChangeListener() {
		try {
			super.reloadData();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		return new CuentaDeFondoFormUi(usuario, StandardFormUi.INSERT_MODE, cx,
				this, CuentaDeFondo.class.newInstance());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormModificar(CuentaDeFondoA item)
			throws Exception {

		CuentaDeFondo cuentaDeFondo = ((CuentaDeFondoBO) cx
				.buildBO(CuentaDeFondo.class)).findByCodigo(item.getCodigo());

		return new CuentaDeFondoFormUi(usuario, StandardFormUi.UPDATE_MODE, cx,
				this, cuentaDeFondo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormCopiar(CuentaDeFondoA item)
			throws Exception {

		CuentaDeFondoA o = (CuentaDeFondoA) ((EntityId) item).clone();

		CuentaDeFondo cuentaDeFondo = ((CuentaDeFondoBO) cx
				.buildBO(CuentaDeFondo.class)).findByCodigo(o.getCodigo());

		return new CuentaDeFondoFormUi(usuario, StandardFormUi.COPY_MODE, cx,
				this, cuentaDeFondo);
	}

}
