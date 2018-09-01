package com.massoftware.backend.bo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;
import org.cendra.jdbc.ex.crud.UniqueException;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.Usuario;

public class PuntoDeEquilibrioBO extends GenericBO<PuntoDeEquilibrio> {

	private final String ATT_NAME_NOMBRE = "nombre";

	public PuntoDeEquilibrioBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(PuntoDeEquilibrio.class, dataSourceWrapper, cx);
	}

	public List<PuntoDeEquilibrio> findAll() throws Exception {
		return findAll("ejercicioContable_ejercicio, puntoDeEquilibrio");
	}

	public List<PuntoDeEquilibrio> findAll(EjercicioContable dto)
			throws Exception {

		if (dto != null) {
			return find("ejercicioContable_ejercicio, puntoDeEquilibrio",
					"ejercicioContable_ejercicio = ?", dto.getEjercicio());

		}

		return new ArrayList<PuntoDeEquilibrio>();
	}
	
	protected Integer maxValueInteger(String attName, PuntoDeEquilibrio dto) throws Exception {

		String viewName = getView();
		String sql = "SELECT MAX(" + attName + ") + 1 FROM " + viewName + " WHERE ejercicioContable_ejercicio = ?";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {
			
			Object ejercicioContable = Integer.class;
			
			if (dto.getEjercicioContable() != null
					&& dto.getEjercicioContable().getId() != null) {
				ejercicioContable = dto.getEjercicioContable().getEjercicio();
			}

			Object[][] table = connectionWrapper.findToTable(sql, ejercicioContable);

			return (Integer) table[0][0];

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	@Override
	public void checkUnique(String attName, Object value) throws Exception {

		if (attName.equalsIgnoreCase(ATT_NAME_NOMBRE)) {

			checkUnique(attName, "LOWER(dbo.Translate(" + ATT_NAME_NOMBRE
					+ ", null, null)) = LOWER(dbo.Translate(?, null,null))",
					value.toString().toLowerCase());

		}
	}

	public void checkUnique(PuntoDeEquilibrio dto, PuntoDeEquilibrio dtoOriginal)
			throws Exception {

		// fullEquals(Class type, boolean ignoreCaseTraslate,
		// boolean ignoreCase, Object value, Object originalValue);

		if (dtoOriginal != null
				&& dto.getEjercicioContable()
						.getEjercicio()
						.equals(dtoOriginal.getEjercicioContable()
								.getEjercicio())
				&& dto.getPuntoDeEquilibrio().equals(
						dtoOriginal.getPuntoDeEquilibrio())) {

			return;

		}

		String where = "ejercicioContable_ejercicio = ? AND puntoDeEquilibrio = ?";

		Object ejercicioArg = null;

		if (dto.getEjercicioContable().getEjercicio() != null) {
			ejercicioArg = dto.getEjercicioContable().getEjercicio();
		} else {
			ejercicioArg = Integer.class;
		}

		Object puntoDeEquilibrioArg = null;

		if (dto.getPuntoDeEquilibrio() != null) {
			puntoDeEquilibrioArg = dto.getPuntoDeEquilibrio();
		} else {
			puntoDeEquilibrioArg = Integer.class;
		}

		if (ifExists(where, ejercicioArg, puntoDeEquilibrioArg)) {

			Field fieldEjercicioContable = PuntoDeEquilibrio.class
					.getDeclaredField("ejercicioContable");
			Field fieldPuntoDeEquilibrio = PuntoDeEquilibrio.class
					.getDeclaredField("puntoDeEquilibrio");

			throw new UniqueException(getLabel(fieldEjercicioContable),
					getLabel(fieldPuntoDeEquilibrio));
		}

	}

	public PuntoDeEquilibrio insert(PuntoDeEquilibrio dto, Usuario usuario)
			throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[EJERCICIO]", "[PUNTODEEQUILIBRIO]", "[NOMBRE]",
				"[TIPO]" };

		Object ejercicioContable = Integer.class;
		Object puntoDeEquilibrio = Integer.class;
		Object nombre = String.class;
		Object puntoDeEquilibrioTipo = String.class;

		if (dto.getEjercicioContable() != null
				&& dto.getEjercicioContable().getId() != null) {
			ejercicioContable = dto.getEjercicioContable().getEjercicio();
		}
		if (dto.getPuntoDeEquilibrio() != null) {
			puntoDeEquilibrio = dto.getPuntoDeEquilibrio();
		}
		if (dto.getNombre() != null) {
			nombre = dto.getNombre();
		}
		if (dto.getPuntoDeEquilibrioTipo() != null
				&& dto.getPuntoDeEquilibrioTipo().getId() != null) {
			puntoDeEquilibrioTipo = dto.getPuntoDeEquilibrioTipo().getCodigo();
		}

		Object[] args = { ejercicioContable, puntoDeEquilibrio, nombre,
				puntoDeEquilibrioTipo };

		insert(nameTableDB, nameAtts, args);

		return dto;

		// return insertByReflection(dto, usuario);
	}

	public PuntoDeEquilibrio update(PuntoDeEquilibrio dto,
			PuntoDeEquilibrio dtoOriginal, Usuario usuario) throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[EJERCICIO]", "[PUNTODEEQUILIBRIO]", "[NOMBRE]",
				"[TIPO]" };

		Object ejercicioContable = Integer.class;
		Object puntoDeEquilibrio = Integer.class;
		Object nombre = String.class;
		Object puntoDeEquilibrioTipo = String.class;

		if (dto.getEjercicioContable() != null
				&& dto.getEjercicioContable().getId() != null) {
			ejercicioContable = dto.getEjercicioContable().getEjercicio();
		}
		if (dto.getPuntoDeEquilibrio() != null) {
			puntoDeEquilibrio = dto.getPuntoDeEquilibrio();
		}
		if (dto.getNombre() != null) {
			nombre = dto.getNombre();
		}
		if (dto.getPuntoDeEquilibrioTipo() != null
				&& dto.getPuntoDeEquilibrioTipo().getId() != null) {
			puntoDeEquilibrioTipo = dto.getPuntoDeEquilibrioTipo().getCodigo();
		}

		Object ejercicioContableOriginal = Integer.class;

		if (dtoOriginal.getEjercicioContable() != null
				&& dto.getEjercicioContable().getId() != null) {
			ejercicioContableOriginal = dtoOriginal.getEjercicioContable()
					.getEjercicio();
		}

		Object puntoDeEquilibrioOriginal = Integer.class;

		if (dtoOriginal.getPuntoDeEquilibrio() != null) {
			puntoDeEquilibrioOriginal = dtoOriginal.getPuntoDeEquilibrio();
		}

		String where = "[EJERCICIO] = ? AND [PUNTODEEQUILIBRIO] = ?";

		Object[] args = { ejercicioContable, puntoDeEquilibrio, nombre,
				puntoDeEquilibrioTipo, ejercicioContableOriginal,
				puntoDeEquilibrioOriginal };

		update(nameTableDB, nameAtts, args, where);

		return dto;
	}

	public boolean delete(PuntoDeEquilibrio dto) throws Exception {

		Object ejercicioContable = Integer.class;

		if (dto.getEjercicioContable() != null
				&& dto.getEjercicioContable().getId() != null) {
			ejercicioContable = dto.getEjercicioContable().getEjercicio();
		}

		Object puntoDeEquilibrio = Integer.class;

		if (dto.getPuntoDeEquilibrio() != null) {
			puntoDeEquilibrio = dto.getPuntoDeEquilibrio();
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			// return delete(ATT_NAME_CODIGO + " = ?", codigoArg);
			return false;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return delete("[EJERCICIO] = ? AND [PUNTODEEQUILIBRIO] = ?",
					ejercicioContable, puntoDeEquilibrio);
		}

		return false;

	}
}
