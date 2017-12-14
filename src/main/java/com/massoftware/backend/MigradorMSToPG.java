package com.massoftware.backend;

import com.massoftware.backend.cx.BackendContext;

public class MigradorMSToPG {

	public static void main(String[] args) {
		try {
			migrar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void migrar() throws Exception {

		BackendContext cxMSSQL = new BackendContext("sqlserver");
		BackendContext cxPG = new BackendContext("Postgresql");

		cxPG.buildEjercicioContableBO().insert(
				cxMSSQL.buildEjercicioContableBO().findAll());

		cxPG.buildUsuarioBO().insert(cxMSSQL.buildUsuarioBO().findAll());

		cxPG.buildCentroDeCostoContableBO().insert(
				cxMSSQL.buildCentroDeCostoContableBO()
						.findAllOrderByCentroDeCostoContable());
		
		
		cxPG.buildPuntoDeEquilibrioTipoBO().insert(cxMSSQL.buildPuntoDeEquilibrioTipoBO().findAll());
		
		cxPG.buildPuntoDeEquilibrioBO().insert(cxMSSQL.buildPuntoDeEquilibrioBO().findAllOrderByPuntoDeEquilibrio());
		
		cxPG.buildPlanDeCuentaEstadoBO().insert(cxMSSQL.buildPlanDeCuentaEstadoBO().findAll());
		
		cxPG.buildCostoDeVentaBO().insert(cxMSSQL.buildCostoDeVentaBO().findAll());
		
		cxPG.buildPlanDeCuentaBO().insert(cxMSSQL.buildPlanDeCuentaBO().findAll());

	}
}
