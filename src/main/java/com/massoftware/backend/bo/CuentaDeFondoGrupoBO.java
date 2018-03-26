package com.massoftware.backend.bo;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.cendra.ex.crud.UniqueException;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.CuentaDeFondoGrupo;
import com.massoftware.model.MonedaCotizacion;
import com.massoftware.model.Usuario;

public class CuentaDeFondoGrupoBO extends GenericBO<CuentaDeFondoGrupo> {

	// private final String ATT_NAME_CODIGO_RUBRO = "codigo";
	private final String ATT_NAME_CODIGO = "codigo";
	private final String ATT_NAME_NOMBRE = "nombre";

	public CuentaDeFondoGrupoBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(CuentaDeFondoGrupo.class, dataSourceWrapper, cx);
	}

	public List<CuentaDeFondoGrupo> findAll() throws Exception {
		return findAll("cuentaDeFondoRubro_codigo, codigo");
	}

	public List<CuentaDeFondoGrupo> findByRubro(Integer codigo)
			throws Exception {
		return find("cuentaDeFondoRubro_codigo, codigo",
				"cuentaDeFondoRubro_codigo = ?", codigo);
	}

	// @Override
	// public void checkUnique(String attName, Object value) throws Exception {
	//
	// if (attName.equalsIgnoreCase(ATT_NAME_CODIGO)) {
	//
	// checkUnique(attName, ATT_NAME_CODIGO + " = ?", value);
	//
	// } else if (attName.equalsIgnoreCase(ATT_NAME_NOMBRE)) {
	//
	// checkUnique(attName, "LOWER(" + ATT_NAME_NOMBRE + ") = ?", value
	// .toString().toLowerCase());
	//
	// }
	// }

	public void checkUnique(CuentaDeFondoGrupo dto,
			CuentaDeFondoGrupo dtoOriginal) throws Exception {

		if (dtoOriginal != null && dto.getCuentaDeFondoRubro().getCodigo()
				.equals(dtoOriginal.getCuentaDeFondoRubro().getCodigo())
				&& dto.getCodigo().equals(dtoOriginal.getCodigo())) {

			return;

		}

		String where = "cuentaDeFondoRubro_codigo = ? AND codigo = ?";

		Object cuentaDeFondoRubro = Integer.class;
		Object codigo = Integer.class;

		if (dto.getCuentaDeFondoRubro() != null
				&& dto.getCuentaDeFondoRubro().getId() != null) {

			cuentaDeFondoRubro = dto.getCuentaDeFondoRubro().getCodigo();
		}
		if (dto.getCodigo() != null) {
			codigo = dto.getCodigo();
		}

		if (ifExists(where, cuentaDeFondoRubro, codigo)) {

			Field fieldRubro = CuentaDeFondoGrupo.class
					.getDeclaredField("cuentaDeFondoRubro");
			Field fieldCodigo = CuentaDeFondoGrupo.class
					.getDeclaredField("codigo");

			throw new UniqueException(getLabel(fieldRubro),
					getLabel(fieldCodigo));
		}

	}

	// public boolean delete(CuentaDeFondoGrupo dto) throws Exception {
	//
	// Object codigoArg = null;
	//
	// if (dto.getCodigo() != null) {
	// codigoArg = dto.getCodigo();
	// } else {
	// codigoArg = Integer.class;
	// }
	//
	// if (dataSourceWrapper.isDatabasePostgreSql()) {
	// return delete(ATT_NAME_CODIGO + " = ?", codigoArg);
	// } else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
	//
	// return delete(
	// getFieldNameMS(classModel.getDeclaredField(ATT_NAME_CODIGO))
	// + " = ?", codigoArg);
	// }
	//
	// return false;
	//
	// }

	public boolean delete(CuentaDeFondoGrupo dto) throws Exception {

		// String nameTableDB = getClassTableMSAnont(classModel);
		//
		// String[] nameAtts = { "[RUBRO]", "[GRUPO]" };

		Object cuentaDeFondoRubro = Integer.class;
		Object codigo = Integer.class;

		if (dto.getCuentaDeFondoRubro() != null
				&& dto.getCuentaDeFondoRubro().getId() != null) {

			cuentaDeFondoRubro = dto.getCuentaDeFondoRubro().getCodigo();
		}
		if (dto.getCodigo() != null) {
			codigo = dto.getCodigo();
		}

		Object[] args = { cuentaDeFondoRubro, codigo };

		String where = "[RUBRO] = ? AND [GRUPO] = ?";

		return delete(where, args);

	}

	// public CuentaDeFondoGrupo insert(CuentaDeFondoGrupo dto, Usuario usuario)
	// throws Exception {
	//
	// return insertByReflection(dto, usuario);
	// }

	public CuentaDeFondoGrupo insert(CuentaDeFondoGrupo dto, Usuario usuario)
			throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[RUBRO]", "[GRUPO]", "[NOMBRE]" };

		Object cuentaDeFondoRubro = Integer.class;
		Object codigo = Integer.class;
		Object nombre = String.class;

		if (dto.getCuentaDeFondoRubro() != null
				&& dto.getCuentaDeFondoRubro().getId() != null) {

			cuentaDeFondoRubro = dto.getCuentaDeFondoRubro().getCodigo();
		}
		if (dto.getCodigo() != null) {
			codigo = dto.getCodigo();
		}
		if (dto.getNombre() != null) {
			nombre = dto.getNombre();
		}

		Object[] args = { cuentaDeFondoRubro, codigo, nombre };

		insert(nameTableDB, nameAtts, args);

		return dto;

	}

	// public CuentaDeFondoGrupo update(CuentaDeFondoGrupo dto,
	// CuentaDeFondoGrupo dtoOriginal, Usuario usuario) throws Exception {
	//
	// Object codigoArg = null;
	//
	// if (dtoOriginal.getCodigo() != null) {
	// codigoArg = dtoOriginal.getCodigo();
	// } else {
	// codigoArg = Integer.class;
	// }
	//
	// if (dataSourceWrapper.isDatabasePostgreSql()) {
	//
	// } else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
	//
	// return updateByReflection(
	// dto,
	// usuario,
	// getFieldNameMS(classModel.getDeclaredField(ATT_NAME_CODIGO))
	// + " = ?", codigoArg);
	// }
	//
	// return null;
	// }

	public CuentaDeFondoGrupo update(CuentaDeFondoGrupo dto,
			CuentaDeFondoGrupo dtoOriginal, Usuario usuario) throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[RUBRO]", "[GRUPO]", "[NOMBRE]" };

		Object cuentaDeFondoRubro = Integer.class;
		Object codigo = Integer.class;
		Object nombre = String.class;

		if (dto.getCuentaDeFondoRubro() != null
				&& dto.getCuentaDeFondoRubro().getId() != null) {

			cuentaDeFondoRubro = dto.getCuentaDeFondoRubro().getCodigo();
		}
		if (dto.getCodigo() != null) {
			codigo = dto.getCodigo();
		}
		if (dto.getNombre() != null) {
			nombre = dto.getNombre();
		}

		Object cuentaDeFondoRubroOriginal = Integer.class;
		Object codigoOriginal = Integer.class;

		if (dtoOriginal.getCuentaDeFondoRubro() != null
				&& dtoOriginal.getCuentaDeFondoRubro().getId() != null) {

			cuentaDeFondoRubroOriginal = dtoOriginal.getCuentaDeFondoRubro()
					.getCodigo();
		}
		if (dtoOriginal.getCodigo() != null) {
			codigoOriginal = dtoOriginal.getCodigo();
		}

		String where = "[RUBRO] = ? AND [GRUPO] = ?";

		Object[] args = { cuentaDeFondoRubro, codigo, nombre,
				cuentaDeFondoRubroOriginal, codigoOriginal };

		String sql = buildUpdate(nameTableDB, nameAtts, where);

		String sql2 = "UPDATE [dbo].[CuentasDeFondos] SET [RUBRO] = ?, [GRUPO] = ? WHERE [RUBRO] = ? AND [GRUPO] = ?";

		Object[] args2 = { cuentaDeFondoRubro, codigo,
				cuentaDeFondoRubroOriginal, codigoOriginal };

		String[] sqls = { sql, sql2 };

		Object[][] argsMatrix = { args, args2 };

		update(sqls, argsMatrix);

		return dto;
	}

}
