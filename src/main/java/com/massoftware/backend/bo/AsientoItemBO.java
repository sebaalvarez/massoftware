package com.massoftware.backend.bo;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;
import org.cendra.jdbc.ex.crud.UniqueException;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.model.Asiento;
import com.massoftware.model.AsientoItem;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;

public class AsientoItemBO extends GenericBO<AsientoItem> {

	// private final String ATT_NAME_REGISTRO = "registro";

	private final String ORDER_BY = "orden";

	public AsientoItemBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(AsientoItem.class, dataSourceWrapper, cx);
	}

	public List<AsientoItem> findAll() throws Exception {
		return findAll(ORDER_BY);
	}

	public List<AsientoItem> findAll(Asiento asiento) throws Exception {

		if (asiento != null) {

			String where = "asiento_ejercicioContable_ejercicio = ? AND asiento_numero = ?";

			List<AsientoItem> list = find(ORDER_BY, where, asiento
					.getEjercicioContable().getEjercicio(), asiento.getNumero());

			EjercicioContable ejercicioContable = null;
			if (list.size() > 0 && list.get(0).getCuentaContable() != null) {

				ejercicioContable = list.get(0).getCuentaContable()
						.getEjercicioContable();

				for (AsientoItem item : list) {
					item._setEjercicioContable(ejercicioContable);
				}
			}

			return list;

		}

		return new ArrayList<AsientoItem>();

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

	public void checkUnique(AsientoItem dto, AsientoItem dtoOriginal)
			throws Exception {

		// fullEquals(Class type, boolean ignoreCaseTraslate,
		// boolean ignoreCase, Object value, Object originalValue);

		if (dtoOriginal != null
				&& dto.getAsiento()
						.getEjercicioContable()
						.getEjercicio()
						.equals(dtoOriginal.getAsiento().getEjercicioContable()
								.getEjercicio())
				&& dto.getAsiento().getNumero()
						.equals(dtoOriginal.getAsiento().getNumero())
				&& dto.getOrden().equals(dtoOriginal.getOrden())) {

			return;

		}

		String where = "asiento_ejercicioContable_ejercicio = ? AND asiento_numero = ? AND orden = ?";

		Object ejercicioContable = Integer.class;
		Object asiento = Integer.class;
		Object orden = Integer.class;

		if (dto.getAsiento().getEjercicioContable().getEjercicio() != null) {
			ejercicioContable = dto.getAsiento().getEjercicioContable()
					.getEjercicio();
		}

		if (dto.getAsiento().getNumero() != null) {
			asiento = dto.getAsiento().getNumero();
		}

		if (dto.getOrden() != null) {
			orden = dto.getOrden();
		}

		if (ifExists(where, ejercicioContable, asiento, orden)) {

			Field fieldAsiento = AsientoItem.class.getDeclaredField("asiento");
			Field fieldRegistro = AsientoItem.class.getDeclaredField("orden");

			throw new UniqueException(getLabel(fieldAsiento),
					getLabel(fieldRegistro));
		}

	}

	protected Integer maxValueInteger(String attName, AsientoItem dto)
			throws Exception {

		String viewName = getView();
		String sql = "SELECT MAX("
				+ attName
				+ ") + 1 FROM "
				+ viewName
				+ " WHERE asiento_ejercicioContable_ejercicio = ? AND asiento_numero = ?";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object ejercicioContable = Integer.class;
			Object asiento = Integer.class;

			if (dto.getAsiento() != null && dto.getAsiento().getId() != null) {
				ejercicioContable = dto.getAsiento().getEjercicioContable()
						.getEjercicio();
				asiento = dto.getAsiento().getNumero();
			}

			Object[][] table = connectionWrapper.findToTable(sql,
					ejercicioContable, asiento);

			return (Integer) table[0][0];

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public boolean delete(AsientoItem dto) throws Exception {

		Object ejercicioContable = Integer.class;
		Object asiento = Integer.class;
		Object orden = Integer.class;

		if (dto.getAsiento() != null && dto.getAsiento().getId() != null
				&& dto.getAsiento().getEjercicioContable() != null
				&& dto.getAsiento().getEjercicioContable().getId() != null) {

			ejercicioContable = dto.getAsiento().getEjercicioContable()
					.getEjercicio();
		}

		if (dto.getAsiento() != null && dto.getAsiento().getId() != null) {
			asiento = dto.getAsiento().getNumero();
		}

		if (dto.getOrden() != null) {
			orden = dto.getOrden();
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			// return delete(ATT_NAME_CODIGO + " = ?", codigoArg);
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return delete(
					"[EJERCICIO] = ? AND [NUMEROASIENTO] = ? AND [ORDEN] = ?",
					ejercicioContable, asiento, orden);
		}

		return false;

	}

	public AsientoItem insert(AsientoItem dto, Usuario usuario)
			throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[EJERCICIO]", "[NUMEROASIENTO]",
				"[NROREGISTRO]", "[CUENTACONTABLE]", "[DEBE]", "[HABER]",
				"[DETALLE]", "[FECHASQL]", "[ORDEN]" };

		Object ejercicioContable = Integer.class;
		Object asiento = Integer.class;
		Object registro = Integer.class;
		Object cuentaContable = String.class;
		Object debe = BigDecimal.class;
		Object haber = BigDecimal.class;
		Object detalle = String.class;
		Object fecha = Date.class;
		Object orden = String.class;

		if (dto.getCuentaContable() != null
				&& dto.getCuentaContable().getId() != null
				&& dto.getCuentaContable().getEjercicioContable() != null
				&& dto.getCuentaContable().getEjercicioContable().getId() != null) {
			ejercicioContable = dto.getCuentaContable().getEjercicioContable()
					.getEjercicio();
		}

		if (dto.getAsiento() != null && dto.getAsiento().getNumero() != null) {
			asiento = dto.getAsiento().getNumero();
		}

		if (dto.getAsiento() != null && dto.getAsiento().getFecha() != null) {
			fecha = dto.getAsiento().getFecha();
		}

		if (dto.getRegistro() != null) {
			registro = dto.getRegistro();
		}

		if (dto.getCuentaContable() != null
				&& dto.getCuentaContable().getId() != null) {
			cuentaContable = dto.getCuentaContable().getCuentaContable();
		}

		if (dto.getDebe() != null) {
			debe = dto.getDebe();
		}

		if (dto.getHaber() != null) {
			haber = dto.getHaber();
		}

		if (dto.getDetalle() != null) {
			detalle = dto.getDetalle();
		}

		if (dto.getOrden() != null) {
			orden = dto.getOrden();
		}

		Object[] args = { ejercicioContable, asiento, registro, cuentaContable,
				debe, haber, detalle, fecha, orden };

		insert(nameTableDB, nameAtts, args);

		return dto;

		// return insertByReflection(dto, usuario);
	}

	public AsientoItem update(AsientoItem dto, AsientoItem dtoOriginal,
			Usuario usuario) throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[EJERCICIO]", "[NUMEROASIENTO]",
				"[NROREGISTRO]", "[CUENTACONTABLE]", "[DEBE]", "[HABER]",
				"[DETALLE]", "[FECHASQL]", "[ORDEN]" };

		// --------------------------------------------

		Object ejercicioContable = Integer.class;
		Object asiento = Integer.class;
		Object registro = Integer.class;
		Object cuentaContable = String.class;
		Object debe = BigDecimal.class;
		Object haber = BigDecimal.class;
		Object detalle = String.class;
		Object fecha = Date.class;
		Object orden = String.class;

		if (dto.getCuentaContable() != null
				&& dto.getCuentaContable().getId() != null
				&& dto.getCuentaContable().getEjercicioContable() != null
				&& dto.getCuentaContable().getEjercicioContable().getId() != null) {
			ejercicioContable = dto.getCuentaContable().getEjercicioContable()
					.getEjercicio();
		}

		if (dto.getAsiento() != null && dto.getAsiento().getId() != null) {
			asiento = dto.getAsiento().getNumero();
			fecha = dto.getAsiento().getFecha();
		}

		if (dto.getRegistro() != null) {
			registro = dto.getRegistro();
		}

		if (dto.getCuentaContable() != null
				&& dto.getCuentaContable().getId() != null) {
			cuentaContable = dto.getCuentaContable().getCuentaContable();
		}

		if (dto.getDebe() != null) {
			debe = dto.getDebe();
		}

		if (dto.getHaber() != null) {
			haber = dto.getHaber();
		}

		if (dto.getDetalle() != null) {
			detalle = dto.getDetalle();
		}

		if (dto.getOrden() != null) {
			orden = dto.getOrden();
		}

		// --------------------------------------------

		Object ejercicioContableOriginal = Integer.class;
		Object asientoModeloOriginal = Integer.class;
		Object ordenOriginal = Integer.class;

		if (dtoOriginal.getCuentaContable() != null
				&& dtoOriginal.getCuentaContable().getId() != null
				&& dtoOriginal.getCuentaContable().getEjercicioContable() != null
				&& dtoOriginal.getCuentaContable().getEjercicioContable()
						.getId() != null) {

			ejercicioContableOriginal = dtoOriginal.getCuentaContable()
					.getEjercicioContable().getEjercicio();
		}

		if (dtoOriginal.getAsiento() != null
				&& dtoOriginal.getAsiento().getId() != null) {

			asientoModeloOriginal = dtoOriginal.getAsiento().getNumero();
		}

		if (dtoOriginal.getOrden() != null) {

			ordenOriginal = dtoOriginal.getOrden();
		}

		// --------------------------------------------

		String where = "[EJERCICIO] = ? AND [NUMEROASIENTO] = ? AND [ORDEN] = ?";

		Object[] args = { ejercicioContable, asiento, registro, cuentaContable,
				debe, haber, detalle, fecha, orden, ejercicioContableOriginal,
				asientoModeloOriginal, ordenOriginal };

		update(nameTableDB, nameAtts, args, where);

		return dto;
	}

}
