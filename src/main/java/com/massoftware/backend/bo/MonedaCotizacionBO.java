package com.massoftware.backend.bo;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cendra.ex.crud.UniqueException;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.Moneda;
import com.massoftware.model.MonedaCotizacion;
import com.massoftware.model.Usuario;

public class MonedaCotizacionBO extends GenericBO<MonedaCotizacion> {

	private final String ATT_NAME_MONEDA_CODIGO = "moneda_codigo";
	private final String ATT_NAME_FECHA = "fecha";
	private final String ATT_NAME_CODIGO = "codigo";
	private final String orderBy = ATT_NAME_FECHA + " DESC, "
			+ ATT_NAME_MONEDA_CODIGO;

	private MonedaBO monedaBO;

	public MonedaCotizacionBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx, MonedaBO monedaBO) {
		super(MonedaCotizacion.class, dataSourceWrapper, cx);
		this.monedaBO = monedaBO;
	}

	public List<MonedaCotizacion> findAll() throws Exception {
		return findAll(orderBy);
	}

	public List<MonedaCotizacion> findAll(Moneda dto, Integer anio)
			throws Exception {

		if (dto != null) {
			return find(orderBy, ATT_NAME_MONEDA_CODIGO + " = ? AND YEAR("
					+ ATT_NAME_FECHA + ") >= ?", dto.getCodigo(), anio);

		}

		return new ArrayList<MonedaCotizacion>();
	}

	// @Override
	// public void checkUnique(String attName, Object value) throws Exception {
	//
	// // if (attName.equalsIgnoreCase(ATT_NAME_CODIGO)) {
	// //
	// // checkUnique(attName, ATT_NAME_CODIGO + " = ?", value);
	// //
	// // } else if (attName.equalsIgnoreCase(ATT_NAME_FECHA)) {
	// //
	// // checkUnique(attName, ATT_NAME_FECHA + " = ?", value.toString()
	// // .toLowerCase());
	//
	// //
	// // }
	// }

	public void checkUnique(MonedaCotizacion dto, MonedaCotizacion dtoOriginal)
			throws Exception {

		Timestamp ts = new Timestamp(dto.getFecha().getTime());
		Timestamp tsOriginal = null;

		if (dtoOriginal != null && dtoOriginal.getFecha() != null) {
			tsOriginal = new Timestamp(dtoOriginal.getFecha().getTime());
		}

		if (dtoOriginal != null
				&& dto.getMoneda().getCodigo()
						.equals(dtoOriginal.getMoneda().getCodigo())
				&& ts.equals(tsOriginal)) {

			return;

		}

		String where = ATT_NAME_MONEDA_CODIGO + " = ? AND " + ATT_NAME_FECHA
				+ " = ?";

		Object codigoMonedaArg = Integer.class;

		if (dto.getMoneda() != null && dto.getMoneda().getCodigo() != null) {
			codigoMonedaArg = dto.getMoneda().getCodigo();
		}

		Object fechaArg = Timestamp.class;

		if (dto.getFecha() != null) {
			fechaArg = new Timestamp(dto.getFecha().getTime());
		}

		if (ifExists(where, codigoMonedaArg, fechaArg)) {

			Field fieldMoneda = MonedaCotizacion.class
					.getDeclaredField("moneda");
			Field fieldFecha = MonedaCotizacion.class.getDeclaredField("fecha");

			throw new UniqueException(getLabel(fieldMoneda),
					getLabel(fieldFecha));
		}

	}

	public boolean delete(MonedaCotizacion dto) throws Exception {

		Object codigoMonedaArg = Date.class;

		if (dto.getMoneda() != null && dto.getMoneda().getCodigo() != null) {
			codigoMonedaArg = dto.getMoneda().getCodigo();
		}

		Object fechaArg = Date.class;

		if (dto.getFecha() != null) {
			fechaArg = dto.getFecha();
		}
		if (dataSourceWrapper.isDatabasePostgreSql()) {
			return delete(ATT_NAME_MONEDA_CODIGO + " = ?", fechaArg);
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return delete("[MONEDA] = ? AND [FECHASQL] = ?", codigoMonedaArg,
					fechaArg);
		}

		return false;

	}

	public MonedaCotizacion insert(MonedaCotizacion dto, Usuario usuario)
			throws Exception {

		dto.getMoneda().setCotizacion(dto.getVenta());
		dto.getMoneda().setFecha(dto.getFecha());
		
		MonedaCotizacion monedaCotizacion =  insertByReflection(dto, usuario);
		
		
		monedaBO.update(dto.getMoneda(), dto.getMoneda(), usuario);
		
		return monedaCotizacion;
	}

	public MonedaCotizacion update(MonedaCotizacion dto,
			MonedaCotizacion dtoOriginal, Usuario usuario) throws Exception {
		
		dto.getMoneda().setCotizacion(dto.getVenta());
		dto.getMoneda().setFecha(dto.getFecha());

		Object codigoMonedaArg = Date.class;

		if (dto.getMoneda() != null && dto.getMoneda().getCodigo() != null) {
			codigoMonedaArg = dto.getMoneda().getCodigo();
		}

		Object fechaArg = Date.class;

		if (dto.getFecha() != null) {
			fechaArg = dto.getFecha();
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			MonedaCotizacion monedaCotizacion =  updateByReflection(
					dto,
					usuario,
					getFieldNameMS(dto.getMoneda().getClass()
							.getDeclaredField(ATT_NAME_CODIGO))
							+ " = ? AND "
							+ getFieldNameMS(classModel
									.getDeclaredField(ATT_NAME_FECHA)) + " = ?",
					codigoMonedaArg, fechaArg);
		
			monedaBO.update(dto.getMoneda(), dto.getMoneda(), usuario);
			
			return monedaCotizacion;
		}
		
		

		return null;
	}

}
