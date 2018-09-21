package com.massoftware.backend.bo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;
import org.cendra.jdbc.ex.crud.UniqueException;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.model.AsientoModelo;
import com.massoftware.model.AsientoModeloItem;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;

public class AsientoModeloItemBO extends GenericBO<AsientoModeloItem> {

	// private final String ATT_NAME_REGISTRO = "registro";

	private final String ORDER_BY = "registro, cuentaContable_cuentaContable";

	public AsientoModeloItemBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(AsientoModeloItem.class, dataSourceWrapper, cx);
	}

	public List<AsientoModeloItem> findAll() throws Exception {
		return findAll(ORDER_BY);
	}

	public List<AsientoModeloItem> findAll(String orderBy, AsientoModelo asientoModelo)
			throws Exception {

		if (asientoModelo != null) {

			String where = "asientoModelo_numero = ?";

			List<AsientoModeloItem> list = find(orderBy, where,
					asientoModelo.getNumero());

			EjercicioContable ejercicioContable = null;
			if (list.size() > 0 && list.get(0).getCuentaContable() != null) {

				ejercicioContable = list.get(0).getCuentaContable()
						.getEjercicioContable();

				for (AsientoModeloItem item : list) {
					item._setEjercicioContable(ejercicioContable);
				}
			}

			return list;

		}

		return new ArrayList<AsientoModeloItem>();

	}

	// @Override
	// public void checkUnique(String attName, Object value) throws Exception {
	//
	// if (attName.equalsIgnoreCase(ATT_NAME_REGISTRO)) {
	//
	// checkUnique(attName, ATT_NAME_REGISTRO + " = ?", value);
	//
	// }
	//
	// }

	public void checkUnique(AsientoModeloItem dto, AsientoModeloItem dtoOriginal)
			throws Exception {

		// fullEquals(Class type, boolean ignoreCaseTraslate,
		// boolean ignoreCase, Object value, Object originalValue);

		if (dtoOriginal != null
				&& dto.getAsientoModelo().getNumero()
						.equals(dtoOriginal.getAsientoModelo().getNumero())
				&& dto.getRegistro().equals(dtoOriginal.getRegistro())) {

			return;

		}

		String where = "asientoModelo_numero = ? AND registro = ?";

		Object asientoModelo = Integer.class;

		if (dto.getAsientoModelo().getNumero() != null) {
			asientoModelo = dto.getAsientoModelo().getNumero();
		}

		Object registro = Integer.class;

		if (dto.getRegistro() != null) {
			registro = dto.getRegistro();
		}

		if (ifExists(where, asientoModelo, registro)) {

			Field fieldAsientoModelo = AsientoModeloItem.class
					.getDeclaredField("asientoModelo");
			Field fieldRegistro = AsientoModeloItem.class
					.getDeclaredField("registro");

			throw new UniqueException(getLabel(fieldAsientoModelo),
					getLabel(fieldRegistro));
		}

	}

	protected Integer maxValueInteger(String attName, AsientoModeloItem dto)
			throws Exception {

		String viewName = getView();
		String sql = "SELECT MAX(" + attName + ") + 1 FROM " + viewName
				+ " WHERE asientoModelo_numero = ?";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object asientoModelo = Integer.class;

			if (dto.getAsientoModelo() != null
					&& dto.getAsientoModelo().getId() != null) {
				asientoModelo = dto.getAsientoModelo().getNumero();
			}

			Object[][] table = connectionWrapper
					.findToTable(sql, asientoModelo);

			return (Integer) table[0][0];

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public boolean delete(AsientoModeloItem dto) throws Exception {

		Object ejercicioContable = Integer.class;
		Object asientoModelo = Integer.class;
		Object registro = Integer.class;

		if (dto.getCuentaContable() != null
				&& dto.getCuentaContable().getId() != null
				&& dto.getCuentaContable().getEjercicioContable() != null
				&& dto.getCuentaContable().getEjercicioContable().getId() != null) {
			ejercicioContable = dto.getCuentaContable().getEjercicioContable()
					.getEjercicio();
		}

		if (dto.getAsientoModelo() != null
				&& dto.getAsientoModelo().getId() != null) {
			asientoModelo = dto.getAsientoModelo().getNumero();
		}

		if (dto.getRegistro() != null) {
			registro = dto.getRegistro();
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			// return delete(ATT_NAME_CODIGO + " = ?", codigoArg);
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return delete(
					"[EJERCICIO] = ? AND [ASIENTOMODELO] = ? AND [REGISTRO] = ?",
					ejercicioContable, asientoModelo, registro);
		}

		return false;

	}

	public AsientoModeloItem insert(AsientoModeloItem dto, Usuario usuario)
			throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[EJERCICIO]", "[ASIENTOMODELO]", "[REGISTRO]",
				"[CUENTACONTABLE]" };

		Object ejercicioContable = Integer.class;
		Object asientoModelo = Integer.class;
		Object registro = Integer.class;
		Object cuentaContable = String.class;

		if (dto.getCuentaContable() != null
				&& dto.getCuentaContable().getId() != null
				&& dto.getCuentaContable().getEjercicioContable() != null
				&& dto.getCuentaContable().getEjercicioContable().getId() != null) {
			ejercicioContable = dto.getCuentaContable().getEjercicioContable()
					.getEjercicio();
		}

		if (dto.getAsientoModelo() != null
				&& dto.getAsientoModelo().getId() != null) {
			asientoModelo = dto.getAsientoModelo().getNumero();
		}

		if (dto.getRegistro() != null) {
			registro = dto.getRegistro();
		}

		if (dto.getCuentaContable() != null
				&& dto.getCuentaContable().getId() != null) {
			cuentaContable = dto.getCuentaContable().getCuentaContable();
		}

		Object[] args = { ejercicioContable, asientoModelo, registro,
				cuentaContable };

		insert(nameTableDB, nameAtts, args);

		return dto;

		// return insertByReflection(dto, usuario);
	}

	public AsientoModeloItem update(AsientoModeloItem dto,
			AsientoModeloItem dtoOriginal, Usuario usuario) throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[EJERCICIO]", "[ASIENTOMODELO]", "[REGISTRO]",
				"[CUENTACONTABLE]" };

		Object ejercicioContable = Integer.class;
		Object asientoModelo = Integer.class;
		Object registro = Integer.class;
		Object cuentaContable = String.class;

		if (dto.getCuentaContable() != null
				&& dto.getCuentaContable().getId() != null
				&& dto.getCuentaContable().getEjercicioContable() != null
				&& dto.getCuentaContable().getEjercicioContable().getId() != null) {
			ejercicioContable = dto.getCuentaContable().getEjercicioContable()
					.getEjercicio();
		}

		if (dto.getAsientoModelo() != null
				&& dto.getAsientoModelo().getId() != null) {
			asientoModelo = dto.getAsientoModelo().getNumero();
		}

		if (dto.getRegistro() != null) {
			registro = dto.getRegistro();
		}

		if (dto.getCuentaContable() != null
				&& dto.getCuentaContable().getId() != null) {
			cuentaContable = dto.getCuentaContable().getCuentaContable();
		}

		Object ejercicioContableOriginal = Integer.class;
		Object asientoModeloOriginal = Integer.class;
		Object registroOriginal = Integer.class;

		if (dtoOriginal.getCuentaContable() != null
				&& dtoOriginal.getCuentaContable().getId() != null
				&& dtoOriginal.getCuentaContable().getEjercicioContable() != null
				&& dtoOriginal.getCuentaContable().getEjercicioContable()
						.getId() != null) {
			ejercicioContableOriginal = dtoOriginal.getCuentaContable()
					.getEjercicioContable().getEjercicio();
		}

		if (dtoOriginal.getAsientoModelo() != null
				&& dtoOriginal.getAsientoModelo().getId() != null) {
			asientoModeloOriginal = dtoOriginal.getAsientoModelo().getNumero();
		}

		if (dtoOriginal.getRegistro() != null) {
			registroOriginal = dtoOriginal.getRegistro();
		}

		String where = "[EJERCICIO] = ? AND [ASIENTOMODELO] = ? AND [REGISTRO] = ?";

		Object[] args = { ejercicioContable, asientoModelo, registro,
				cuentaContable, ejercicioContableOriginal,
				asientoModeloOriginal, registroOriginal };

		update(nameTableDB, nameAtts, args, where);

		return dto;
	}

}
