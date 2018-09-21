package com.massoftware.backend.bo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;
import org.cendra.jdbc.ex.crud.UniqueException;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;

public class CentroDeCostoContableBO extends GenericBO<CentroDeCostoContable> {

	private final String ATT_NAME_ABREVIATURA = "abreviatura";
	private final String ATT_NAME_NOMBRE = "nombre";

	public CentroDeCostoContableBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(CentroDeCostoContable.class, dataSourceWrapper, cx);
	}

	public List<CentroDeCostoContable> findAll() throws Exception {
		return findAll("ejercicioContable_ejercicio, numero");
	}

	public List<CentroDeCostoContable> findAll(EjercicioContable dto)
			throws Exception {

		if (dto != null) {
			return find("ejercicioContable_ejercicio, numero",
					"ejercicioContable_ejercicio = ?", dto.getEjercicio());

		}

		return new ArrayList<CentroDeCostoContable>();
	}
	
	public List<CentroDeCostoContable> findAll(String orderBy, EjercicioContable dto)
			throws Exception {

		if (dto != null) {
			return find(orderBy,
					"ejercicioContable_ejercicio = ?", dto.getEjercicio());

		}

		return new ArrayList<CentroDeCostoContable>();
	}
	
	protected Integer maxValueInteger(String attName, CentroDeCostoContable dto) throws Exception {

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

		if (attName.equalsIgnoreCase(ATT_NAME_ABREVIATURA)) {

			checkUnique(attName, "LOWER(dbo.Translate(" + ATT_NAME_ABREVIATURA
					+ ", null, null)) = LOWER(dbo.Translate(?, null,null))",
					value.toString().toLowerCase());

		} else if (attName.equalsIgnoreCase(ATT_NAME_NOMBRE)) {

			checkUnique(attName, "LOWER(dbo.Translate(" + ATT_NAME_NOMBRE
					+ ", null, null)) = LOWER(dbo.Translate(?, null,null))",
					value.toString().toLowerCase());

		}
	}

	public void checkUnique(CentroDeCostoContable dto,
			CentroDeCostoContable dtoOriginal) throws Exception {

		// fullEquals(Class type, boolean ignoreCaseTraslate,
		// boolean ignoreCase, Object value, Object originalValue);
		
		if (dtoOriginal != null
				&& dto.getEjercicioContable()
						.getEjercicio()
						.equals(dtoOriginal.getEjercicioContable()
								.getEjercicio())
				&& dto.getNumero().equals(dtoOriginal.getNumero())) {

			return;

		}

		String where = "ejercicioContable_ejercicio = ? AND numero = ?";

		Object ejercicioArg = null;

		if (dto.getEjercicioContable().getEjercicio() != null) {
			ejercicioArg = dto.getEjercicioContable().getEjercicio();
		} else {
			ejercicioArg = Integer.class;
		}

		Object numeroArg = null;

		if (dto.getNumero() != null) {
			numeroArg = dto.getNumero();
		} else {
			numeroArg = Integer.class;
		}

		if (ifExists(where, ejercicioArg, numeroArg)) {

			Field fieldEjercicioContable = CentroDeCostoContable.class
					.getDeclaredField("ejercicioContable");
			Field fieldNumero = CentroDeCostoContable.class
					.getDeclaredField("numero");

			throw new UniqueException(getLabel(fieldEjercicioContable),
					getLabel(fieldNumero));
		}

	}

	public CentroDeCostoContable insert(CentroDeCostoContable dto,
			Usuario usuario) throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[EJERCICIO]", "[CENTRODECOSTOCONTABLE]",
				"[NOMBRE]", "[ABREVIATURA]", "[PRUEBA]" };

		Object ejercicioContable = Integer.class;
		Object numero = Integer.class;
		Object nombre = String.class;
		Object abreviatura = String.class;

		if (dto.getEjercicioContable() != null
				&& dto.getEjercicioContable().getId() != null) {
			ejercicioContable = dto.getEjercicioContable().getEjercicio();
		}
		if (dto.getNumero() != null) {
			numero = dto.getNumero();
		}
		if (dto.getNombre() != null) {
			nombre = dto.getNombre();
		}
		if (dto.getAbreviatura() != null) {
			abreviatura = dto.getAbreviatura();
		}

		Object[] args = { ejercicioContable, numero, nombre, abreviatura, 0 };

		insert(nameTableDB, nameAtts, args);

		return dto;

		// return insertByReflection(dto, usuario);
	}

	public CentroDeCostoContable update(CentroDeCostoContable dto,
			CentroDeCostoContable dtoOriginal, Usuario usuario)
			throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[EJERCICIO]", "[CENTRODECOSTOCONTABLE]",
				"[NOMBRE]", "[ABREVIATURA]" };

		Object ejercicioContable = Integer.class;
		Object numero = Integer.class;
		Object nombre = String.class;
		Object abreviatura = String.class;

		if (dto.getEjercicioContable() != null
				&& dto.getEjercicioContable().getId() != null) {
			ejercicioContable = dto.getEjercicioContable().getEjercicio();
		}
		if (dto.getNumero() != null) {
			numero = dto.getNumero();
		}
		if (dto.getNombre() != null) {
			nombre = dto.getNombre();
		}
		if (dto.getAbreviatura() != null) {
			abreviatura = dto.getAbreviatura();
		}

		Object ejercicioContableOriginal = Integer.class;

		if (dtoOriginal.getEjercicioContable() != null
				&& dto.getEjercicioContable().getId() != null) {
			ejercicioContableOriginal = dtoOriginal.getEjercicioContable()
					.getEjercicio();
		}

		Object numeroOriginal = Integer.class;

		if (dtoOriginal.getNumero() != null) {
			numeroOriginal = dtoOriginal.getNumero();
		}

		String where = "[EJERCICIO] = ? AND [CENTRODECOSTOCONTABLE] = ?";

		Object[] args = { ejercicioContable, numero, nombre, abreviatura,
				ejercicioContableOriginal, numeroOriginal };

		update(nameTableDB, nameAtts, args, where);

		return dto;
	}

	public boolean delete(CentroDeCostoContable dto) throws Exception {

		Object ejercicioArg = null;

		if (dto.getEjercicioContable().getEjercicio() != null) {
			ejercicioArg = dto.getEjercicioContable().getEjercicio();
		} else {
			ejercicioArg = Integer.class;
		}

		Object numeroArg = null;

		if (dto.getNumero() != null) {
			numeroArg = dto.getNumero();
		} else {
			numeroArg = Integer.class;
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			// return delete(ATT_NAME_CODIGO + " = ?", codigoArg);
			return false;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return delete("[EJERCICIO] = ? AND [CENTRODECOSTOCONTABLE] = ?",
					ejercicioArg, numeroArg);
		}

		return false;

	}
}
