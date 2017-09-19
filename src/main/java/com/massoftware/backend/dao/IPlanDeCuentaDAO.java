package com.massoftware.backend.dao;

import java.util.List;

import com.massoftware.model.PlanDeCuenta;

public interface IPlanDeCuentaDAO {

	List<PlanDeCuenta> findAllOrderByCuentaContable(String cuentaContable,
			String nombre) throws Exception;

//	List<PlanDeCuenta> findAllOrderByNombre(String cuentaContable, String nombre)
//			throws Exception;
//
//	List<PlanDeCuenta> findAllOrderByCuentaAgrupadora(String cuentaContable,
//			String nombre) throws Exception;
//
//	List<PlanDeCuenta> findAllOrderByCentroDeCostoContable(
//			String cuentaContable, String nombre) throws Exception;
//
//	List<PlanDeCuenta> findAllOrderByPuntoDeEquilibrio(String cuentaContable,
//			String nombre) throws Exception;

}
