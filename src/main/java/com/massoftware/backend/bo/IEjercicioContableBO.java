package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.commons.model.EntityId;

import com.massoftware.model.EjercicioContable;

public interface IEjercicioContableBO {

	EjercicioContable findByEjercicio(Integer ejercicio) throws Exception;

	List<EjercicioContable> findAll() throws Exception;

	List<EjercicioContable> findAll(String ejercicioEndsWith) throws Exception;

	EjercicioContable findMaxEjercicio() throws Exception;

	EntityId insert(EjercicioContable item) throws Exception;

	EjercicioContable update(EjercicioContable item) throws Exception;

	EntityId delete(EjercicioContable item) throws Exception;
}
