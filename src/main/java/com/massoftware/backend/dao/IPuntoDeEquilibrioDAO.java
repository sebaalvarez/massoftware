package com.massoftware.backend.dao;

import java.util.List;

import org.cendra.common.model.EntityId;

import com.massoftware.model.PuntoDeEquilibrio;

public interface IPuntoDeEquilibrioDAO {

	List<PuntoDeEquilibrio> findAllOrderByNombre(Integer ejercicio)
			throws Exception;

	List<PuntoDeEquilibrio> findAllOrderByNombre() throws Exception;

	List<PuntoDeEquilibrio> findAllOrderByPuntoDeEquilibrio() throws Exception;

	List<PuntoDeEquilibrio> findAllOrderByPuntoDeEquilibrio(Integer ejercicio)
			throws Exception;

	PuntoDeEquilibrio findByPuntoDeEquilibrio(Short puntoDeEquilibrio,
			Integer ejercicio) throws Exception;

	EntityId insert(PuntoDeEquilibrio item) throws Exception;

	PuntoDeEquilibrio update(PuntoDeEquilibrio item) throws Exception;

	EntityId delete(PuntoDeEquilibrio item) throws Exception;

	Short findMaxPuntoDeEquilibrio(Integer ejercicio) throws Exception;

}
