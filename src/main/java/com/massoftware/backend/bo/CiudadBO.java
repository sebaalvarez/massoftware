package com.massoftware.backend.bo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;
import org.cendra.jdbc.ex.crud.UniqueException;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.model.Ciudad;
import com.massoftware.model.Provincia;
import com.massoftware.model.Usuario;

public class CiudadBO extends GenericBO<Ciudad> {

	private final String ORDER_BY = "provincia_pais_codigo, provincia_codigo, codigo";

	private final String ATT_NAME_NOMBRE = "nombre";

	public CiudadBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(Ciudad.class, dataSourceWrapper, cx);
	}

	public List<Ciudad> findAll() throws Exception {
		return findAll(ORDER_BY);
	}

	public List<Ciudad> findAll(Provincia dto) throws Exception {

		if (dto != null) {
			return find(ORDER_BY,
					"provincia_pais_codigo = ? AND provincia_codigo = ?", dto
							.getPais().getCodigo(), dto.getCodigo());

		}

		return new ArrayList<Ciudad>();
	}

	// protected Integer maxValueInteger(String attName, CentroDeCostoContable
	// dto)
	// throws Exception {
	//
	// String viewName = getViewName();
	// String sql = "SELECT MAX(" + attName + ") + 1 FROM " + viewName
	// + " WHERE ejercicioContable_ejercicio = ?";
	//
	// ConnectionWrapper connectionWrapper = dataSourceWrapper
	// .getConnectionWrapper();
	//
	// try {
	//
	// Object ejercicioContable = Integer.class;
	//
	// if (dto.getEjercicioContable() != null
	// && dto.getEjercicioContable().getId() != null) {
	// ejercicioContable = dto.getEjercicioContable().getEjercicio();
	// }
	//
	// Object[][] table = connectionWrapper.findToTable(sql,
	// ejercicioContable);
	//
	// return (Integer) table[0][0];
	//
	// } catch (Exception e) {
	// throw e;
	// } finally {
	// connectionWrapper.close(connectionWrapper);
	// }
	//
	// }

	// @Override
	// public void checkUnique(String attName, Object value) throws Exception {
	//
	// if (attName.equalsIgnoreCase(ATT_NAME_NOMBRE)) {
	//
	// checkUnique(attName, "LOWER(dbo.Translate(" + ATT_NAME_NOMBRE +
	// ", null, null)) = LOWER(dbo.Translate(?, null,null))",
	// value.toString().toLowerCase());
	//
	// }
	// }

	public void checkUnique(Ciudad dto, Ciudad dtoOriginal) throws Exception {

		// fullEquals(Class type, boolean ignoreCaseTraslate,
		// boolean ignoreCase, Object value, Object originalValue);

		if (dtoOriginal != null
				&& dto.getProvincia()
						.getPais()
						.getCodigo()
						.equals(dtoOriginal.getProvincia().getPais()
								.getCodigo())
				&& dto.getProvincia().getCodigo()
						.equals(dtoOriginal.getProvincia().getCodigo())
				&& dto.getCodigo().equals(dtoOriginal.getCodigo())) {

			return;

		}

		String where = "provincia_pais_codigo = ? AND provincia_codigo = ? AND LOWER(dbo.Translate("
				+ ATT_NAME_NOMBRE
				+ ", null, null)) = LOWER(dbo.Translate(?, null,null))";

		Object pais = Integer.class;
		Object provincia = Integer.class;
		Object nombre = String.class;

		if (dto.getProvincia().getPais().getId() != null) {
			pais = dto.getProvincia().getPais().getCodigo();
		}

		if (dto.getProvincia().getId() != null) {
			provincia = dto.getProvincia().getCodigo();
		}

		if (dto.getNombre() != null) {
			nombre = dto.getNombre();
		}

		if (ifExists(where, pais, provincia, nombre)) {

			Field fieldPais = Provincia.class.getDeclaredField("pais");
			Field fieldProvincia = Ciudad.class.getDeclaredField("provincia");
			Field fieldCodigo = Ciudad.class.getDeclaredField("nombre");

			throw new UniqueException(getLabel(fieldPais),
					getLabel(fieldProvincia), getLabel(fieldCodigo));
		}

	}

	public Ciudad insert(Ciudad dto, Usuario usuario) throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[PAIS]", "[PROVINCIA]", "[CIUDAD]", "[NOMBRE]",
				"[DEPARTAMENTO]", "[CODIGO]" };

		Object pais = Integer.class;
		Object provincia = Integer.class;
		Object codigo = Integer.class;
		Object nombre = String.class;
		Object departamento = String.class;
		Object codigoAfip = Integer.class;

		if (dto.getProvincia() != null && dto.getProvincia().getPais() != null
				&& dto.getProvincia().getPais().getId() != null) {
			pais = dto.getProvincia().getPais().getCodigo();
		}
		if (dto.getProvincia() != null && dto.getProvincia().getId() != null) {
			provincia = dto.getProvincia().getCodigo();
		}
		if (dto.getCodigo() != null) {
			codigo = dto.getCodigo();
		}
		if (dto.getNombre() != null) {
			nombre = dto.getNombre();
		}
		if (dto.getDepartamento() != null) {
			departamento = dto.getDepartamento();
		}
		if (dto.getCodigoAfip() != null) {
			codigoAfip = dto.getCodigoAfip();
		}

		Object[] args = { pais, provincia, codigo, nombre, departamento,
				codigoAfip };

		insert(nameTableDB, nameAtts, args);

		return dto;

		// return insertByReflection(dto, usuario);
	}

	public Ciudad update(Ciudad dto, Ciudad dtoOriginal, Usuario usuario)
			throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[PAIS]", "[PROVINCIA]", "[CIUDAD]", "[NOMBRE]",
				"[DEPARTAMENTO]", "[CODIGO]" };

		Object pais = Integer.class;
		Object provincia = Integer.class;
		Object codigo = Integer.class;
		Object nombre = String.class;
		Object departamento = String.class;
		Object codigoAfip = Integer.class;

		if (dto.getProvincia() != null && dto.getProvincia().getPais() != null
				&& dto.getProvincia().getPais().getId() != null) {
			pais = dto.getProvincia().getPais().getCodigo();
		}
		if (dto.getProvincia() != null && dto.getProvincia().getId() != null) {
			provincia = dto.getProvincia().getCodigo();
		}
		if (dto.getCodigo() != null) {
			codigo = dto.getCodigo();
		}
		if (dto.getNombre() != null) {
			nombre = dto.getNombre();
		}
		if (dto.getDepartamento() != null) {
			departamento = dto.getDepartamento();
		}
		if (dto.getCodigoAfip() != null) {
			codigoAfip = dto.getCodigoAfip();
		}

		Object codigoOriginal = Integer.class;

		if (dtoOriginal.getCodigo() != null) {
			codigoOriginal = dtoOriginal.getCodigo();
		}

		String where = "[CIUDAD] = ?";

		Object[] args = { pais, provincia, codigo, nombre, departamento,
				codigoAfip, codigoOriginal };

		update(nameTableDB, nameAtts, args, where);

		return dto;
	}

	public boolean delete(Ciudad dto) throws Exception {

		Object codigo = Integer.class;

		if (dto.getCodigo() != null) {
			codigo = dto.getCodigo();
		}

		return delete("[CIUDAD] = ?", codigo);

	}
}
