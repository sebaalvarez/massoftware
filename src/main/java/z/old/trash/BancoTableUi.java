package z.old.trash;

import com.massoftware.backend.BackendContext;
import com.massoftware.frontend.custom.windows.StandardTableUi;
import com.massoftware.model.Banco;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property;
import com.vaadin.ui.Window;

class BancoTableUi extends StandardTableUi<Banco> {

	// ----------------------------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = 5518000600411055037L;

	// ----------------------------------------------
	// CONTROLES

	// ----------------------------------------------
	// OPCIONES

	// ----------------------------------------------
	// MODELO

	// ----------------------------------------------

	public BancoTableUi(Window window, BackendContext cx, Usuario usuario,
			String pidFiltering, Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty) {
		super(false, false, false, true, true, true,  true, true, window, cx, usuario, Banco.class, pidFiltering, searchFilter,
				searchProperty, null);
	}

	// protected List<ColumnMetaData> buildColumnsMetaData() throws Exception {
	// List<ColumnMetaData> columnsMetaData = new ArrayList<ColumnMetaData>();
	//
	// columnsMetaData.add(new ColumnMetaData("codigo", "Código", 80,
	// String.class, SimpleStringTraslateFilter.CONTAINS_WORDS_AND,
	// true));
	// columnsMetaData.add(new ColumnMetaData("nombre", "Nombre", 350,
	// String.class, SimpleStringTraslateFilter.CONTAINS_WORDS_AND,
	// false));
	// columnsMetaData.add(new ColumnMetaData("cuit", "CUIT", 100,
	// BigDecimal.class,
	// SimpleStringTraslateFilter.CONTAINS_WORDS_AND, false));
	// columnsMetaData.add(new ColumnMetaData("bloqueado", "Bloqueado", 100,
	// Boolean.class, SimpleStringTraslateFilter.CONTAINS_WORDS_AND,
	// false));
	// columnsMetaData.add(new ColumnMetaData("nombreOficial",
	// "Nombre oficial", 350, String.class,
	// SimpleStringTraslateFilter.CONTAINS_WORDS_AND, false));
	//
	// return columnsMetaData;
	// }

	// protected void buildContainersOptions() throws Exception {
	// // sucursalesBIC = new BeanItemContainer<Sucursal>(Sucursal.class,
	// // new ArrayList<Sucursal>());
	// }

	// protected StandardFormUi<Banco> openFormAgregar() throws Exception {
	//
	// // return new BancoFormUi(StandardFormUi.INSERT_MODE, cx, new Banco(),
	// // this);
	//
	// return new StandardFormUi<Banco>(Banco.class,
	// StandardFormUi.INSERT_MODE, cx, this, new Banco());
	// }

	// protected StandardFormUi<Banco> openFormModificar(Banco item) {
	//
	// // return new BancoFormUi(StandardFormUi.UPDATE_MODE, cx, (Banco) item,
	// // this);
	//
	// return new StandardFormUi<Banco>(Banco.class,
	// StandardFormUi.UPDATE_MODE, cx, this, (Banco) item);
	// }

	// protected StandardFormUi<Banco> openFormCopiar(Banco item) throws
	// Exception {
	// // Deposito itemNew = item.clone();
	// // DepositoFormUi ui = new DepositoFormUi(StandardFormUi.COPY_MODE, cx,
	// // itemNew, this);
	//
	// return new StandardFormUi<Banco>(Banco.class,
	// StandardFormUi.COPY_MODE, cx, this, ((Banco) item).clone());
	//
	// // return null;
	// }

	// protected void deleteItem(Banco item) {
	// // cx.buildPuntoDeEquilibrioBO().delete((Sucursal) item);
	// }

	// protected void loadDataOptions() {
	//
	// // SucursalBO sucursalBO = cx.buildSucursalBO();
	// //
	// // List<Sucursal> sucursales = sucursalBO.findAll();
	// // sucursalesBIC.removeAllItems();
	// // for (Sucursal item : sucursales) {
	// // sucursalesBIC.addBean(item);
	// // }
	//
	// }

	// @SuppressWarnings("unchecked")
	// protected List<Banco> reloadDataList() throws Exception {
	//
	// return cx.buildBO(Banco.class).findAll();
	//
	// }

}
