package com.massoftware.backend.bo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;
import org.cendra.jdbc.ex.crud.UniqueException;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.model.Provincia;
import com.massoftware.model.Pais;
import com.massoftware.model.Usuario;

public class ProvinciaBO extends GenericBO<Provincia> {

	private final String ATT_NAME_ABREVIATURA = "abreviatura";
	private final String ATT_NAME_NOMBRE = "nombre";

	public ProvinciaBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(Provincia.class, dataSourceWrapper, cx);
	}

	public List<Provincia> findAll() throws Exception {
		return findAll("pais_codigo, codigo");
	}

	public List<Provincia> findAll(Pais dto) throws Exception {

		if (dto != null) {
			return find("pais_codigo, codigo", "pais_codigo = ?",
					dto.getCodigo());

		}

		return new ArrayList<Provincia>();
	}

	protected Integer maxValueInteger(String attName, Provincia dto)
			throws Exception {

		String viewName = getView();
		String sql = "SELECT MAX(" + attName + ") + 1 FROM " + viewName
				+ " WHERE pais_codigo = ?";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object paisCodigo = Integer.class;

			if (dto.getPais() != null && dto.getPais().getId() != null) {
				paisCodigo = dto.getPais().getCodigo();
			}

			Object[][] table = connectionWrapper.findToTable(sql, paisCodigo);

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

	public void checkUnique(Provincia dto, Provincia dtoOriginal)
			throws Exception {

		// fullEquals(Class type, boolean ignoreCaseTraslate,
		// boolean ignoreCase, Object value, Object originalValue);

		if (dtoOriginal != null
				&& dto.getPais().getCodigo()
						.equals(dtoOriginal.getPais().getCodigo())
				&& dto.getCodigo().equals(dtoOriginal.getCodigo())) {

			return;

		}

		String where = "pais_codigo = ? AND codigo = ?";

		Object paisCodigo = Integer.class;

		if (dto.getPais() != null && dto.getPais().getId() != null) {
			paisCodigo = dto.getPais().getCodigo();
		}

		Object codigo = Integer.class;

		if (dto.getCodigo() != null) {
			codigo = dto.getCodigo();
		}

		if (ifExists(where, paisCodigo, codigo)) {

			Field fieldEjercicioContable = Provincia.class
					.getDeclaredField("pais");
			Field fieldNumero = Provincia.class.getDeclaredField("codigo");

			throw new UniqueException(getLabel(fieldEjercicioContable),
					getLabel(fieldNumero));
		}

	}

	public Provincia insert(Provincia dto, Usuario usuario) throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[PAIS]", "[PROVINCIA]",
				"[NOMBRE]", "[ABREVIATURA]", "[NROPROVINCIAAFIP]", "[NROPROVINCIAINGBRUTOS]", "[NROPROVINCIARENATEA]" };

		Object paisCodigo = Integer.class;
		Object codigo = Integer.class;
		Object nombre = String.class;
		Object abreviatura = String.class;
		Object codigoAfip = Integer.class;
		Object codigoIngBrutos = Integer.class;
		Object codigoRenatea = Integer.class;

		if (dto.getPais() != null
				&& dto.getPais().getId() != null) {
			paisCodigo = dto.getPais().getCodigo();
		}
		if (dto.getCodigo() != null) {
			codigo = dto.getCodigo();
		}
		if (dto.getNombre() != null) {
			nombre = dto.getNombre();
		}
		if (dto.getAbreviatura() != null) {
			abreviatura = dto.getAbreviatura();
		}
		if (dto.getCodigoAfip() != null) {
			codigoAfip = dto.getCodigoAfip();
		}
		if (dto.getCodigoIngBrutos() != null) {
			codigoIngBrutos = dto.getCodigoIngBrutos();
		}
		if (dto.getCodigoRenatea() != null) {
			codigoRenatea = dto.getCodigoRenatea();
		}

		Object[] args = { paisCodigo, codigo, nombre, abreviatura, codigoAfip, codigoIngBrutos, codigoRenatea };

		insert(nameTableDB, nameAtts, args);

		return dto;

		// return insertByReflection(dto, usuario);
	}

	public Provincia update(Provincia dto,
			Provincia dtoOriginal, Usuario usuario)
			throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[PAIS]", "[PROVINCIA]",
				"[NOMBRE]", "[ABREVIATURA]", "[NROPROVINCIAAFIP]", "[NROPROVINCIAINGBRUTOS]", "[NROPROVINCIARENATEA]" };

		Object paisCodigo = Integer.class;
		Object codigo = Integer.class;
		Object nombre = String.class;
		Object abreviatura = String.class;
		Object codigoAfip = Integer.class;
		Object codigoIngBrutos = Integer.class;
		Object codigoRenatea = Integer.class;

		if (dto.getPais() != null
				&& dto.getPais().getId() != null) {
			paisCodigo = dto.getPais().getCodigo();
		}
		if (dto.getCodigo() != null) {
			codigo = dto.getCodigo();
		}
		if (dto.getNombre() != null) {
			nombre = dto.getNombre();
		}
		if (dto.getAbreviatura() != null) {
			abreviatura = dto.getAbreviatura();
		}
		if (dto.getCodigoAfip() != null) {
			codigoAfip = dto.getCodigoAfip();
		}
		if (dto.getCodigoIngBrutos() != null) {
			codigoIngBrutos = dto.getCodigoIngBrutos();
		}
		if (dto.getCodigoRenatea() != null) {
			codigoRenatea = dto.getCodigoRenatea();
		}


		Object paisCodigoOriginal = Integer.class;

		if (dtoOriginal.getPais() != null
				&& dtoOriginal.getPais().getId() != null) {
			paisCodigoOriginal = dtoOriginal.getPais().getCodigo();
		}

		Object codigoOriginal = Integer.class;

		if (dtoOriginal.getCodigo() != null) {
			codigoOriginal = dtoOriginal.getCodigo();
		}

		String where = "[PAIS] = ? AND [PROVINCIA] = ?";

		Object[] args = { paisCodigo, codigo, nombre, abreviatura, codigoAfip, codigoIngBrutos, codigoRenatea, 
				paisCodigoOriginal, codigoOriginal };

		update(nameTableDB, nameAtts, args, where);

		return dto;
	}

	public boolean delete(Provincia dto) throws Exception {

		Object paisCodigo = Integer.class;
		Object codigo = Integer.class;

		if (dto.getPais() != null
				&& dto.getPais().getId() != null) {
			paisCodigo = dto.getPais().getCodigo();
		}
		if (dto.getCodigo() != null) {
			codigo = dto.getCodigo();
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			// return delete(ATT_NAME_CODIGO + " = ?", codigoArg);
			return false;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return delete("[PAIS] = ? AND [PROVINCIA] = ?",
					paisCodigo, codigo);
		}

		return false;

	}
}
