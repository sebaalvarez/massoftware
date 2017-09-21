package com.massoftware.backend;

import java.util.List;

import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.bo.UsuarioBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;

public class MigradorMSToPG {

	public static void main(String[] args) {
		try {
			migrar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void migrar() throws Exception {

		BackendContext cxMSSQL = new BackendContext("sqlserver");
		BackendContext cxPG = new BackendContext("Postgresql");

		EjercicioContableBO ejercicioContableBOMSSQL = cxMSSQL
				.buildEjercicioContableBO();

		EjercicioContableBO ejercicioContableBOPG = cxPG
				.buildEjercicioContableBO();

		List<EjercicioContable> ejercicioContables = ejercicioContableBOMSSQL
				.findAll();
		
//		System.out.println(ejercicioContables);
		ejercicioContableBOPG.insert(ejercicioContables);

		UsuarioBO usuarioBOMSSQL = cxMSSQL.buildUsuarioBO();

		UsuarioBO usuarioBOPG = cxPG.buildUsuarioBO();

		List<Usuario> usuarios = usuarioBOMSSQL.findAll();
		usuarioBOPG.insert(usuarios);

	}
}
