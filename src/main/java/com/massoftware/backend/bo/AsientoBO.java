package com.massoftware.backend.bo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;
import org.cendra.jdbc.ex.crud.UniqueException;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.model.Asiento;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;

public class AsientoBO extends GenericBO<Asiento> {

	// private final String ATT_NAME_ABREVIATURA = "abreviatura";
	// private final String ATT_NAME_NOMBRE = "nombre";

	// private final String ORDER_BY =
	// "fecha DESC, ejercicioContable_ejercicio, numero";

	public AsientoBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(Asiento.class, dataSourceWrapper, cx);
	}

	public List<String> findMesesAnios(EjercicioContable ejercicioContable)
			throws Exception {

		List<String> list = new ArrayList<String>();

		// -----------------------------------------------------

		String sql = "SELECT DISTINCT MONTH([FECHASQL]) AS m, YEAR([FECHASQL]) AS y FROM dbo.[Contabilidad] WHERE [EJERCICIO] = ? ORDER BY y, m";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object[][] table = connectionWrapper.findToTable(sql,
					ejercicioContable.getEjercicio());

			for (int i = 0; i < table.length; i++) {
				if (table[i][0].toString().length() == 1) {
					list.add("0" + table[i][0] + "/" + table[i][1]);
				} else {
					list.add(table[i][0] + "/" + table[i][1]);
				}

			}

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return list;

	}

	public Integer count(EjercicioContable ejercicioContable) throws Exception {
		String where = null;
		return count(ejercicioContable, where, new Object[0]);
	}

	public Integer count(EjercicioContable ejercicioContable, String where,
			Object... args) throws Exception {

		Integer ejercicio = ejercicioContable.getEjercicio();

		Object[] args2 = null;

		if (where == null) {

			where = " [EJERCICIO] = ? ";
			args2 = new Object[1];
			args2[0] = ejercicio;

		} else {

			where += " AND [EJERCICIO] = ? ";
			args2 = new Object[args.length + 1];
			for (int i = 0; i < args.length; i++) {
				args2[i] = args[i];
			}
			args2[args.length] = ejercicio;

		}
		args = args2;

		String viewName = "dbo.[Contabilidad]";

		// -----------------------------------------------------

		String sql = "SELECT COUNT(*) FROM " + viewName;

		if (where != null && where.trim().length() > 0) {
			sql += " WHERE " + where;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object[][] table = null;

			if (args.length == 0) {
				table = connectionWrapper.findToTable(sql);
			} else {
				table = connectionWrapper.findToTable(sql, args);
			}

			return (Integer) table[0][0];

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	protected Integer maxValueInteger(String attName, Asiento dto)
			throws Exception {

		String viewName = getView();
		String sql = "SELECT MAX(" + attName + ") + 1 FROM " + viewName
				+ " WHERE ejercicioContable_ejercicio = ?";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object ejercicioContable = Integer.class;

			if (dto.getEjercicioContable() != null
					&& dto.getEjercicioContable().getId() != null) {
				ejercicioContable = dto.getEjercicioContable().getEjercicio();
			}

			Object[][] table = connectionWrapper.findToTable(sql,
					ejercicioContable);

			return (Integer) table[0][0];

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	@Override
	public void checkUnique(String attName, Object value) throws Exception {

		// if (attName.equalsIgnoreCase(ATT_NAME_ABREVIATURA)) {
		//
		// checkUnique(attName, "LOWER(dbo.Translate(" + ATT_NAME_ABREVIATURA
		// + ", null, null)) = LOWER(dbo.Translate(?, null,null))",
		// value.toString().toLowerCase());
		//
		// } else if (attName.equalsIgnoreCase(ATT_NAME_NOMBRE)) {
		//
		// checkUnique(attName, "LOWER(dbo.Translate(" + ATT_NAME_NOMBRE
		// + ", null, null)) = LOWER(dbo.Translate(?, null,null))",
		// value.toString().toLowerCase());
		//
		// }
	}

	public void checkUnique(Asiento dto, Asiento dtoOriginal) throws Exception {

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

			Field fieldEjercicioContable = Asiento.class
					.getDeclaredField("ejercicioContable");
			Field fieldNumero = Asiento.class.getDeclaredField("numero");

			throw new UniqueException(getLabel(fieldEjercicioContable),
					getLabel(fieldNumero));
		}

	}

	public Asiento insert(Asiento dto, Usuario usuario) throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[EJERCICIO]", "[NUMEROASIENTO]", "[FECHASQL]",
				"[SUCURSAL]", "[NROLOTE]", "[MINUTACONTABLE]", "[DETALLE]",
				"[MODULO]" };

		Object ejercicioContable = Integer.class;
		Object numero = Integer.class;
		Object fecha = Date.class;
		Object sucursal = Integer.class;
		Object lote = Integer.class;
		Object minutaContable = Integer.class;
		Object detalle = String.class;
		Object asientoModulo = Integer.class;

		if (dto.getEjercicioContable() != null
				&& dto.getEjercicioContable().getId() != null) {
			ejercicioContable = dto.getEjercicioContable().getEjercicio();
		}
		if (dto.getNumero() != null) {
			numero = dto.getNumero();
		}
		if (dto.getFecha() != null) {
			fecha = dto.getFecha();
		}
		if (dto.getSucursal() != null && dto.getSucursal().getId() != null) {
			sucursal = dto.getSucursal().getCodigo();
		}
		if (dto.getLote() != null) {
			lote = dto.getLote();
		}
		if (dto.getMinutaContable() != null
				&& dto.getMinutaContable().getId() != null) {
			minutaContable = dto.getMinutaContable().getCodigo();
		}
		if (dto.getDetalle() != null) {
			detalle = dto.getDetalle();
		}
		if (dto.getAsientoModulo() != null
				&& dto.getAsientoModulo().getId() != null) {
			asientoModulo = dto.getAsientoModulo().getCodigo();
		}

		Object[] args = { ejercicioContable, numero, fecha, sucursal, lote,
				minutaContable, detalle, asientoModulo };

		insert(nameTableDB, nameAtts, args);

		return dto;

		// return insertByReflection(dto, usuario);
	}

	public Asiento update(Asiento dto, Asiento dtoOriginal, Usuario usuario)
			throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[EJERCICIO]", "[NUMEROASIENTO]", "[FECHASQL]",
				"[SUCURSAL]", "[NROLOTE]", "[MINUTACONTABLE]", "[DETALLE]",
				"[MODULO]" };

		Object ejercicioContable = Integer.class;
		Object numero = Integer.class;
		Object fecha = Date.class;
		Object sucursal = Integer.class;
		Object lote = Integer.class;
		Object minutaContable = Integer.class;
		Object detalle = String.class;
		Object asientoModulo = Integer.class;

		if (dto.getEjercicioContable() != null
				&& dto.getEjercicioContable().getId() != null) {
			ejercicioContable = dto.getEjercicioContable().getEjercicio();
		}
		if (dto.getNumero() != null) {
			numero = dto.getNumero();
		}
		if (dto.getFecha() != null) {
			fecha = dto.getFecha();
		}
		if (dto.getSucursal() != null && dto.getSucursal().getId() != null) {
			sucursal = dto.getSucursal().getCodigo();
		}
		if (dto.getLote() != null) {
			lote = dto.getLote();
		}
		if (dto.getMinutaContable() != null
				&& dto.getMinutaContable().getId() != null) {
			minutaContable = dto.getMinutaContable().getCodigo();
		}
		if (dto.getDetalle() != null) {
			detalle = dto.getDetalle();
		}
		if (dto.getAsientoModulo() != null
				&& dto.getAsientoModulo().getId() != null) {
			asientoModulo = dto.getAsientoModulo().getCodigo();
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

		String where = "[EJERCICIO] = ? AND [NUMEROASIENTO] = ?";

		Object[] args = { ejercicioContable, numero, fecha, sucursal, lote,
				minutaContable, detalle, asientoModulo,
				ejercicioContableOriginal, numeroOriginal };

		update(nameTableDB, nameAtts, args, where);

		return dto;
	}

	public boolean delete(Asiento dto) throws Exception {

		Object ejercicioArg = Integer.class;

		if (dto.getEjercicioContable().getEjercicio() != null) {
			ejercicioArg = dto.getEjercicioContable().getEjercicio();
		}

		Object numeroArg = Integer.class;

		if (dto.getNumero() != null) {
			numeroArg = dto.getNumero();
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			// return delete(ATT_NAME_CODIGO + " = ?", codigoArg);
			return false;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return delete("[EJERCICIO] = ? AND [NUMEROASIENTO] = ?",
					ejercicioArg, numeroArg);
		}

		return false;

	}
}
