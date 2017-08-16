package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.common.model.EntityId;

import com.massoftware.backend.dao.IPuntoDeEquilibrioDAO;
import com.massoftware.model.PuntoDeEquilibrio;

public class PuntoDeEquilibrioBO implements IPuntoDeEquilibrioBO {

	private IPuntoDeEquilibrioDAO puntoDeEquilibrioDAO;

	public PuntoDeEquilibrioBO(IPuntoDeEquilibrioDAO puntoDeEquilibrioDAO) {
		super();
		this.puntoDeEquilibrioDAO = puntoDeEquilibrioDAO;
	}

	@Override
	public List<PuntoDeEquilibrio> findAllOrderByNombre(Integer ejercicio)
			throws Exception {

		return puntoDeEquilibrioDAO.findAllOrderByNombre(ejercicio);
	}

	@Override
	public List<PuntoDeEquilibrio> findAllOrderByNombre() throws Exception {

		return puntoDeEquilibrioDAO.findAllOrderByNombre();
	}

	@Override
	public List<PuntoDeEquilibrio> findAllOrderByPuntoDeEquilibrio()
			throws Exception {

		return puntoDeEquilibrioDAO.findAllOrderByPuntoDeEquilibrio();
	}

	@Override
	public List<PuntoDeEquilibrio> findAllOrderByPuntoDeEquilibrio(
			Integer ejercicio) throws Exception {

		return puntoDeEquilibrioDAO.findAllOrderByPuntoDeEquilibrio(ejercicio);
	}

	@Override
	public PuntoDeEquilibrio findByPuntoDeEquilibrio(Short puntoDeEquilibrio,
			Integer ejercicio) throws Exception {

		return puntoDeEquilibrioDAO.findByPuntoDeEquilibrio(puntoDeEquilibrio,
				ejercicio);
	}

	@Override
	public EntityId insert(PuntoDeEquilibrio item) throws Exception {

		return puntoDeEquilibrioDAO.insert(item);
	}

	@Override
	public PuntoDeEquilibrio update(PuntoDeEquilibrio item) throws Exception {

		return puntoDeEquilibrioDAO.update(item);
	}

	@Override
	public EntityId delete(PuntoDeEquilibrio item) throws Exception {

		return puntoDeEquilibrioDAO.delete(item);
	}

	@Override
	public Short findMaxPuntoDeEquilibrio(Integer ejercicio) throws Exception {

		return puntoDeEquilibrioDAO.findMaxPuntoDeEquilibrio(ejercicio);
	}

}
