package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.commons.model.EntityId;

import com.massoftware.backend.dao.ICentroDeCostoContableDAO;
import com.massoftware.model.CentroDeCostoContable;

public class CentroDeCostoContableBO implements ICentroDeCostoContableBO {

	private ICentroDeCostoContableDAO centroDeCostoContableDAO;

	public CentroDeCostoContableBO(
			ICentroDeCostoContableDAO centroDeCostoContableDAO) {
		super();
		this.centroDeCostoContableDAO = centroDeCostoContableDAO;
	}

	@Override
	public List<CentroDeCostoContable> findAllOrderByNombre() throws Exception {

		return centroDeCostoContableDAO.findAllOrderByNombre();
	}

	@Override
	public List<CentroDeCostoContable> findAllOrderByNombre(Integer ejercicio)
			throws Exception {

		return centroDeCostoContableDAO.findAllOrderByNombre(ejercicio);
	}

	@Override
	public List<CentroDeCostoContable> findAllOrderByCentroDeCostoContable()
			throws Exception {

		return centroDeCostoContableDAO.findAllOrderByCentroDeCostoContable();
	}

	@Override
	public List<CentroDeCostoContable> findAllOrderByCentroDeCostoContable(
			Integer ejercicio) throws Exception {

		return centroDeCostoContableDAO
				.findAllOrderByCentroDeCostoContable(ejercicio);
	}

	@Override
	public EntityId insert(CentroDeCostoContable item) throws Exception {

		return centroDeCostoContableDAO.insert(item);
	}

	@Override
	public CentroDeCostoContable findByCentroDeCostoContable(
			Short centroDeCostoContable, Integer ejercicio) throws Exception {

		return centroDeCostoContableDAO
				.findByCentroDeCostoContable(centroDeCostoContable, ejercicio);
	}

	@Override
	public CentroDeCostoContable update(CentroDeCostoContable item)
			throws Exception {

		return centroDeCostoContableDAO.update(item);
	}

	@Override
	public EntityId delete(CentroDeCostoContable item) throws Exception {

		return centroDeCostoContableDAO.delete(item);
	}

	@Override
	public Short findMaxCentroDeCostoContable(Integer ejercicio)
			throws Exception {

		return centroDeCostoContableDAO.findMaxCentroDeCostoContable(ejercicio);
	}

}
