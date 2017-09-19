package com.massoftware.backend.bo;

import java.util.List;

import com.massoftware.backend.dao.IPlanDeCuentaDAO;
import com.massoftware.model.PlanDeCuenta;

public class PlanDeCuentaBO implements IPlanDeCuentaBO {

	private IPlanDeCuentaDAO planDeCuentaDAO;

	public PlanDeCuentaBO(IPlanDeCuentaDAO planDeCuentaDAO) {
		super();
		this.planDeCuentaDAO = planDeCuentaDAO;
	}

	@Override
	public List<PlanDeCuenta> findAllOrderByCuentaContable(
			String cuentaContable, String nombre) throws Exception {
		
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

		return planDeCuentaDAO.findAllOrderByCuentaContable(
				cuentaContable, nombre);
	}



}
