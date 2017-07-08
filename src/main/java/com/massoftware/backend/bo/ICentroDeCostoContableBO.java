package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.commons.model.EntityId;

import com.massoftware.model.CentroDeCostoContable;

public interface ICentroDeCostoContableBO {

	List<CentroDeCostoContable> findAllOrderByNombre(Integer ejercicio)
			throws Exception;

	List<CentroDeCostoContable> findAllOrderByNombre() throws Exception;

	List<CentroDeCostoContable> findAllOrderByCentroDeCostoContable()
			throws Exception;

	List<CentroDeCostoContable> findAllOrderByCentroDeCostoContable(
			Integer ejercicio) throws Exception;

	CentroDeCostoContable findByCentroDeCostoContable(
			Short centroDeCostoContable, Integer ejercicio) throws Exception;

	EntityId insert(CentroDeCostoContable item) throws Exception;

	CentroDeCostoContable update(CentroDeCostoContable item) throws Exception;

	EntityId delete(CentroDeCostoContable item) throws Exception;
	
	Short findMaxCentroDeCostoContable(Integer ejercicio) throws Exception;

}
