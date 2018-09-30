package com.massoftware.backend.bo;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;
import org.cendra.jdbc.ex.crud.UniqueException;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassTableMSAnont;
import com.massoftware.model.Asiento;
import com.massoftware.model.AsientoItem;
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

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			if (dto._fechaRevision != null) {

				dto = insert(connectionWrapper, dto, usuario);
				for (AsientoItem item : dto._items) {
					insert(connectionWrapper, item, usuario);
				}

				// ------------------------------------

				Asiento asientoRevision = (Asiento) dto.clone();
				asientoRevision.setNumero(asientoRevision.getNumero() + 1);
				asientoRevision.setFecha(dto._fechaRevision);

				List<AsientoItem> newItems = new ArrayList<AsientoItem>();

				for (AsientoItem item : dto._items) {
					AsientoItem newItem = (AsientoItem) item.clone();
					newItem.setAsiento(asientoRevision);

					BigDecimal debe = newItem.getDebe();
					BigDecimal haber = newItem.getHaber();

					newItem.setDebe(haber);
					newItem.setHaber(debe);
					newItems.add(newItem);
				}

				asientoRevision._items = newItems;

				asientoRevision = insert(connectionWrapper, asientoRevision,
						usuario);
				for (AsientoItem item : asientoRevision._items) {
					insert(connectionWrapper, item, usuario);
				}

			} else {

				dto = insert(connectionWrapper, dto, usuario);
				for (AsientoItem item : dto._items) {
					insert(connectionWrapper, item, usuario);
				}

			}

			connectionWrapper.commit();

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return dto;

	}

	private Asiento insert(ConnectionWrapper connectionWrapper, Asiento dto,
			Usuario usuario) throws Exception {

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

		String sql = "INSERT INTO " + nameTableDB + " (";

		for (int i = 0; i < nameAtts.length; i++) {
			sql += nameAtts[i];
			if (i < nameAtts.length - 1) {
				sql += ", ";
			}
		}

		sql += ") VALUES (";

		for (int i = 0; i < args.length; i++) {
			sql += "?";
			if (i < args.length - 1) {
				sql += ", ";
			}
		}

		sql += ")";

		int rows = connectionWrapper.insert(sql, args);

		if (rows != 1) {
			throw new Exception(
					"La sentencia debería afectar un registro, la sentencia afecto "
							+ rows + " registros.");
		}

		return dto;
	}

	private AsientoItem insert(ConnectionWrapper connectionWrapper,
			AsientoItem dto, Usuario usuario) throws Exception {

		String nameTableDB = null;

		ClassTableMSAnont[] a = AsientoItem.class
				.getAnnotationsByType(ClassTableMSAnont.class);
		if (a != null && a.length > 0) {
			nameTableDB = a[0].nameTableDB();
		}

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

		String sql = "INSERT INTO " + nameTableDB + " (";

		for (int i = 0; i < nameAtts.length; i++) {
			sql += nameAtts[i];
			if (i < nameAtts.length - 1) {
				sql += ", ";
			}
		}

		sql += ") VALUES (";

		for (int i = 0; i < args.length; i++) {
			sql += "?";
			if (i < args.length - 1) {
				sql += ", ";
			}
		}

		sql += ")";

		int rows = connectionWrapper.insert(sql, args);

		if (rows != 1) {
			throw new Exception(
					"La sentencia debería afectar un registro, la sentencia afecto "
							+ rows + " registros.");
		}

		return dto;

	}

	public Asiento update(Asiento dto, Asiento dtoOriginal, Usuario usuario)
			throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = {

		"[EJERCICIO]", "[NUMEROASIENTO]", "[FECHASQL]",

				// "[COMPROBANTE]",
				"[DETALLE]", "[SUCURSAL]",
				// "[MODULO]",
				"[NROLOTE]", "[MINUTACONTABLE]",
		// "[TIPOID]", "[NUMEROID]",

		// "[TIPOASIENTO]", "[TIPO]", "[LETRA]", "[NUMERO]",
		// "[SECUENCIA]", "[SUCURSALCOMP]", "[NUMEROCOMP]"

		};

		// ----------------------------------------------------------

		Object ejercicioContable = Integer.class;
		Object numero = Integer.class;
		Object fecha = Date.class;
		// Object comprobante = String.class;
		Object detalle = String.class;
		Object sucursal = Integer.class;
		// Object asientoModulo = Integer.class;
		Object lote = Integer.class;
		Object minutaContable = Integer.class;
		// Object tipoComprobanteId = Integer.class;
		// Object nroComprobanteId = Integer.class;

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
		if (dto.getDetalle() != null) {
			detalle = dto.getDetalle();
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

		// ----------------------------------------------------------

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

		// ----------------------------------------------------------

		String where = "[EJERCICIO] = ? AND [NUMEROASIENTO] = ?";

		Object[] args = { ejercicioContable, numero, fecha, detalle, sucursal,
				lote, minutaContable, ejercicioContableOriginal, numeroOriginal };

		// ----------------------------------------------------------

		// update(nameTableDB, nameAtts, args, where);

		String sql = buildUpdate(nameTableDB, nameAtts, where);

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			int rows = connectionWrapper.update(sql, args);

			if (rows != 1) {
				throw new Exception(
						"La sentencia debería afectar un registro, la sentencia afecto "
								+ rows + " registros.");
			}

			List<AsientoItem> oldItems = findAllAsientoItem(connectionWrapper,
					dto);

			for (AsientoItem itemOld : oldItems) {

				boolean exists = false;

				for (AsientoItem asientoItem : dto._items) {
					if (itemOld.equals(asientoItem)) {
						exists = true;
						break;
					}
				}

				if (exists == false) {
					delete(connectionWrapper, itemOld);
				}

			}

			for (AsientoItem asientoItem : dto._items) {

				AsientoItem asientoItemOriginal = (AsientoItem) asientoItem
						.clone();
				asientoItem.setAsiento(dto);

				boolean exists = false;

				for (AsientoItem itemOld : oldItems) {
					if (itemOld.equals(asientoItem)) {
						exists = true;
						break;
					}
				}

				if (exists) {
					update(connectionWrapper, asientoItem, asientoItemOriginal,
							usuario);
				} else {
					insert(connectionWrapper, asientoItem, usuario);
				}

			}

			connectionWrapper.commit();

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return dto;
	}

	private AsientoItem update(ConnectionWrapper connectionWrapper,
			AsientoItem dto, AsientoItem dtoOriginal, Usuario usuario)
			throws Exception {

		String nameTableDB = null;

		ClassTableMSAnont[] a = AsientoItem.class
				.getAnnotationsByType(ClassTableMSAnont.class);
		if (a != null && a.length > 0) {
			nameTableDB = a[0].nameTableDB();
		}

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

		// ----------------------------------------------------------

		// update(nameTableDB, nameAtts, args, where);

		String sql = buildUpdate(nameTableDB, nameAtts, where);

		int rows = connectionWrapper.update(sql, args);

		if (rows != 1) {
			throw new Exception(
					"La sentencia debería afectar un registro, la sentencia afecto "
							+ rows + " registros.");
		}

		return dto;
	}

	private boolean delete(ConnectionWrapper connectionWrapper, AsientoItem dto)
			throws Exception {

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

		String nameTableDB = null;

		ClassTableMSAnont[] a = AsientoItem.class
				.getAnnotationsByType(ClassTableMSAnont.class);
		if (a != null && a.length > 0) {
			nameTableDB = a[0].nameTableDB();
		}

		String sql = "DELETE FROM " + nameTableDB;
		String where = "[EJERCICIO] = ? AND [NUMEROASIENTO] = ? AND [ORDEN] = ?";

		if (where != null && where.trim().length() > 0) {
			sql += " WHERE " + where;
		}

		sql += ";";

		int rows = -1;

		rows = connectionWrapper.delete(sql, ejercicioContable, asiento, orden);

		if (rows != 1) {
			throw new Exception(
					"La sentencia debería afectar un solo registro, la sentencia afecto "
							+ rows + " registros.");
		}

		return false;

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

		String nameTableDB = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			nameTableDB = getClassTableMSAnont(classModel);
		}

		String where = "[EJERCICIO] = ? AND [NUMEROASIENTO] = ?";

		String sql = "DELETE FROM " + nameTableDB;

		if (where != null && where.trim().length() > 0) {
			sql += " WHERE " + where;
		}

		sql += ";";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			// -------------------------------

			List<AsientoItem> oldItems = findAllAsientoItem(connectionWrapper,
					dto);

			for (AsientoItem itemOld : oldItems) {

				delete(connectionWrapper, itemOld);

			}

			// -------------------------------

			int rows = -1;

			rows = connectionWrapper.delete(sql, ejercicioArg, numeroArg);

			if (rows != 1) {
				throw new Exception(
						"La sentencia debería afectar un solo registro, la sentencia afecto "
								+ rows + " registros.");
			}
			
			// -------------------------------

			connectionWrapper.commit();

			return true;

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	@SuppressWarnings({ "unchecked" })
	private List<AsientoItem> findAllAsientoItem(
			ConnectionWrapper connectionWrapper, Asiento asiento)
			throws Exception {

		if (asiento != null) {

			String orderBy = "orden";

			String where = "asiento_ejercicioContable_ejercicio = ? AND asiento_numero = ?";

			String sql = "SELECT * FROM " + getView(AsientoItem.class);

			if (where != null && where.trim().length() > 0) {
				sql += " WHERE " + where;
			}

			if (orderBy != null && orderBy.trim().length() > 0) {
				sql += " ORDER BY " + orderBy;
			} else {
				sql += " ORDER BY " + 1;
			}

			sql += ";";

			List<AsientoItem> list = connectionWrapper
					.findToListByCendraConvention(sql, asiento
							.getEjercicioContable().getEjercicio(), asiento
							.getNumero());

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

}
