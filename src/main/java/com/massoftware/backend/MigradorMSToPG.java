package com.massoftware.backend;

import java.util.Properties;

import com.massoftware.backend.cx.BackendContext;

public class MigradorMSToPG {

	public static void main(String[] args) {
		try {
//			migrar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void migrar(Properties propertiesPG, Properties propertiesMS)
			throws Exception {

		BackendContext cxMSSQL = new BackendContext();
		cxMSSQL.start(BackendContext.MS, propertiesMS);
		BackendContext cxPG = new BackendContext();
		cxPG.start(BackendContext.PG, propertiesPG);

		cxPG.buildEjercicioContableBO().insert(
				cxMSSQL.buildEjercicioContableBO().findAll());

		cxPG.buildUsuarioBO().insert(cxMSSQL.buildUsuarioBO().findAll());

		cxPG.buildCentroDeCostoContableBO().insert(
				cxMSSQL.buildCentroDeCostoContableBO()
						.findAllOrderByCentroDeCostoContable());

		cxPG.buildPuntoDeEquilibrioTipoBO().insert(
				cxMSSQL.buildPuntoDeEquilibrioTipoBO().findAll());

		cxPG.buildPuntoDeEquilibrioBO().insert(
				cxMSSQL.buildPuntoDeEquilibrioBO()
						.findAllOrderByPuntoDeEquilibrio());

		cxPG.buildPlanDeCuentaEstadoBO().insert(
				cxMSSQL.buildPlanDeCuentaEstadoBO().findAll());

		cxPG.buildCostoDeVentaBO().insert(
				cxMSSQL.buildCostoDeVentaBO().findAll());

		cxPG.buildPlanDeCuentaBO().insert(
				cxMSSQL.buildPlanDeCuentaBO().findAll());

	}
}
