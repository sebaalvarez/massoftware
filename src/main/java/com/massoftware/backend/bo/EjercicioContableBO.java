package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.commons.model.EntityId;

import com.massoftware.backend.dao.IEjercicioContableDAO;
import com.massoftware.model.EjercicioContable;

public class EjercicioContableBO implements IEjercicioContableBO {

	private IEjercicioContableDAO ejercicioContableDAO;

	public EjercicioContableBO(IEjercicioContableDAO ejercicioContableDAO) {
		super();
		this.ejercicioContableDAO = ejercicioContableDAO;
	};

	@Override
	public EjercicioContable findByEjercicio(Integer ejercicio)
			throws Exception {

		return this.ejercicioContableDAO.findByEjercicio(ejercicio);
	}

	public List<EjercicioContable> findAll() throws Exception {
		return this.ejercicioContableDAO.findAll();
	}

	@Override
	public List<EjercicioContable> findAll(String ejercicioEndsWith)
			throws Exception {
		return this.ejercicioContableDAO.findAll(ejercicioEndsWith);
	}

	@Override
	public EjercicioContable findMaxEjercicio() throws Exception {
		return this.ejercicioContableDAO.findMaxEjercicio();
	}

	@Override
	public EntityId insert(EjercicioContable item) throws Exception {
		return this.ejercicioContableDAO.insert(item);
	}
	
	@Override
	public EjercicioContable update(EjercicioContable item) throws Exception {
		return this.ejercicioContableDAO.update(item);
	}
	
	@Override
	public EntityId delete(EjercicioContable item) throws Exception {
		return this.ejercicioContableDAO.delete(item);
	}

}
