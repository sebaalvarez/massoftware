package com.massoftware.backend.bo;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.cendra.ex.crud.UniqueException;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.MonedaCotizacion;
import com.massoftware.model.Usuario;

public class MonedaCotizacionBO extends GenericBO<MonedaCotizacion> {

	private final String ATT_NAME_MONEDA_CODIGO = "moneda_codigo";
	private final String ATT_NAME_FECHA = "fecha";
	private final String ATT_NAME_CODIGO = "codigo";

	public MonedaCotizacionBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(MonedaCotizacion.class, dataSourceWrapper, cx);
	}

	public List<MonedaCotizacion> findAll() throws Exception {
		return findAll(ATT_NAME_FECHA + ", " + ATT_NAME_MONEDA_CODIGO);
	}

	@Override
	public void checkUnique(String attName, Object value) throws Exception {

		// if (attName.equalsIgnoreCase(ATT_NAME_CODIGO)) {
		//
		// checkUnique(attName, ATT_NAME_CODIGO + " = ?", value);
		//
		// } else if (attName.equalsIgnoreCase(ATT_NAME_FECHA)) {
		//
		// checkUnique(attName, ATT_NAME_FECHA + " = ?", value.toString()
		// .toLowerCase());

		//
		// }
	}
	
	public void checkUnique(MonedaCotizacion dto) throws Exception {
		
		String where = ATT_NAME_MONEDA_CODIGO + " = ? AND " + ATT_NAME_FECHA + " = ?";
		
		Object codigoMonedaArg = null;

		if (dto.getMoneda() != null && dto.getMoneda().getCodigo() != null) {
			codigoMonedaArg = dto.getMoneda().getCodigo();
		} else {
			codigoMonedaArg = Date.class;
		}

		Object fechaArg = null;

		if (dto.getFecha() != null) {
			fechaArg = dto.getFecha();
		} else {
			fechaArg = Date.class;
		}
		
		if (ifExists(where, codigoMonedaArg, fechaArg)) {

			Field fieldMoneda = MonedaCotizacion.class.getDeclaredField("moneda");
			Field fieldFecha = MonedaCotizacion.class.getDeclaredField("fecha");
			
			throw new UniqueException(getLabel(fieldMoneda), getLabel(fieldFecha));
		}

	}

	public boolean delete(MonedaCotizacion dto) throws Exception {

		Object codigoMonedaArg = null;

		if (dto.getMoneda() != null && dto.getMoneda().getCodigo() != null) {
			codigoMonedaArg = dto.getMoneda().getCodigo();
		} else {
			codigoMonedaArg = Date.class;
		}

		Object fechaArg = null;

		if (dto.getFecha() != null) {
			fechaArg = dto.getFecha();
		} else {
			fechaArg = Date.class;
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			return delete(ATT_NAME_MONEDA_CODIGO + " = ?", fechaArg);
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return delete(
					getFieldNameMS(classModel.getDeclaredField(ATT_NAME_MONEDA_CODIGO))
							+ " = ? AND "
							+ getFieldNameMS(classModel.getDeclaredField(ATT_NAME_FECHA))
							+ " = ?", codigoMonedaArg, fechaArg);
		}

		return false;

	}

	public MonedaCotizacion insert(MonedaCotizacion dto, Usuario usuario)
			throws Exception {

		return insertByReflection(dto, usuario);
	}

	public MonedaCotizacion update(MonedaCotizacion dto,
			MonedaCotizacion dtoOriginal, Usuario usuario) throws Exception {

		Object codigoMonedaArg = null;

		if (dto.getMoneda() != null && dto.getMoneda().getCodigo() != null) {
			codigoMonedaArg = dto.getMoneda().getCodigo();
		} else {
			codigoMonedaArg = Date.class;
		}

		Object fechaArg = null;

		if (dto.getFecha() != null) {
			fechaArg = dto.getFecha();
		} else {
			fechaArg = Date.class;
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return updateByReflection(
					dto, usuario,
					getFieldNameMS(dto.getMoneda().getClass()
							.getDeclaredField(ATT_NAME_CODIGO))
							+ " = ? AND "
							+ getFieldNameMS(classModel
									.getDeclaredField(ATT_NAME_FECHA)) + " = ?",
					codigoMonedaArg, fechaArg);
		}

		return null;
	}

}
