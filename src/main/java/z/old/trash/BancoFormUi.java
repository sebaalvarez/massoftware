package z.old.trash;

import com.massoftware.backend.BackendContext;
import com.massoftware.frontend.util.window.StandardFormUi;
import com.massoftware.model.Banco;

class BancoFormUi extends StandardFormUi<Banco> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2757079005614422662L;

	// ----------------------------------------------
	// OPCIONES

	// ----------------------------------------------
	// MODELOS

	// ----------------------------------------------
	// CONTROLES

	// ----------------------------------------------

	// ----------------------------------------------

	public BancoFormUi(String mode, BackendContext cx, Banco banco,
			BancoTableUi tableUi) {
		super(null, Banco.class, mode, cx, tableUi, banco);
	}

	// ======================================================

//	protected void buildContainers(Banco dto) throws Exception {
//
//		if (dto != null) {
//			originalDTO = dto.clone();
//		}
//
//		// ======================================================================
//		// OPCIONES
//
//		// ======================================================================
//		// MODELOS
//
//		if (dto != null) {
//			dtoBI = new BeanItem<Banco>(dto);
//		} else {
//			dto = new Banco();
//			dtoBI = new BeanItem<Banco>(dto);
//		}
//
//		// ----------------------------------------------------------------------
//
//		if (StandardFormUi.INSERT_MODE.equalsIgnoreCase(mode)) {
//			// LO DEJO COMENTADO POR LAS DUDAS QUE LUEGO QUERRAMOS IMPLEMENTAR
//			// ESTA FUNCIONALIDAD
//			// Integer maxNumero = cx.buildSucursalBO().findMaxSucursal();
//			// if (maxNumero == null || maxNumero < 1) {
//			// maxNumero = 1;
//			// }
//			//
//			// sucursalBI.getBean().setCodigo(maxNumero);
//		}
//
//	}

	// protected void buildBodyControls() throws Exception {
	//
	// // nombreOficialTXT.addValidator(new
	// // GenericUniqueValidator(String.class,
	// // "nombreOficial", true, true, cx.buildBancoBO(),
	// // ((Banco) originalDTO).getNombreOficial()));
	//
	// rootVL.addComponent(BuilderXMD.loadModel(Banco.class.getSimpleName()));
	//
	// }

	// EVTs LISTENERS ===================================================

	// protected void preInsertUpdate() {
	// for (int i = 0; i < rootVL.getComponentCount(); i++) {
	// if (rootVL.getComponent(i) instanceof AbstractField) {
	// ((AbstractField) rootVL.getComponent(i)).validate();
	// }
	// }
	// }

	// protected void preInsert() {
	// }

	// protected void preUpdate() {
	// }

	// protected void insert() {
	// // cx.buildSucursalBO().insert(sucursalBI.getBean());
	// }

	// protected void update() {
	// // cx.buildSucursalBO().update(sucursalBI.getBean(),
	// // sucursalBI.getBean().clone());
	// }

	// protected void postInsertUpdate() throws Exception {
	//
	// }

} // END CLASS ///////////////////////////////////////////////////////////
