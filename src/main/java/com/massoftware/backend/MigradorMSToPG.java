package com.massoftware.backend;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.cendra.jdbc.ex.crud.DeleteForeingObjectConflictException;

import com.massoftware.backend.util.UtilModel;
import com.massoftware.model.Deposito;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.ModeloCbteFondo;
import com.massoftware.model.ModeloCbteFondoItem;
import com.massoftware.model.ModeloCbteFondoItemConcepto;
import com.massoftware.model.Modulo;
import com.massoftware.model.Sucursal;
import com.massoftware.model.SucursalTipo;
import com.massoftware.model.Talonario;
import com.massoftware.model.TipoCbteAFIP;
import com.massoftware.model.TipoCbteControl;

public class MigradorMSToPG {

	public static void main(String[] args) {
		try {

			Properties propertiesMS = new Properties();

			propertiesMS.put("jdbc.driverClassName",
					"com.microsoft.sqlserver.jdbc.SQLServerDriver");
			propertiesMS.put("jdbc.userName", "sa");
			propertiesMS.put("jdbc.userPassword", "cordoba");
			// properties.put("jdbc.url", ds.getURL());
			propertiesMS.put("jdbc.maxActive", "10");
			propertiesMS.put("jdbc.initialSize", "5");
			propertiesMS.put("jdbc.maxIdle", "5");
			propertiesMS.put("jdbc.validationQuery", "SELECT 1");
			propertiesMS.put("jdbc.verbose", "true");
			propertiesMS.put("jdbc.serverName", "localhost");
			propertiesMS.put("jdbc.databaseName", "VetaroRep");
			propertiesMS.put("jdbc.portNumber", "1433");

			Properties propertiesPG = new Properties();

			propertiesPG.put("jdbc.driverClassName", "org.postgresql.Driver");
			propertiesPG.put("jdbc.userName", "postgres");
			propertiesPG.put("jdbc.userPassword", "cordoba");
			propertiesPG
					.put("jdbc.url",
							"jdbc:postgresql://localhost:5432/massoftware?searchpath=massoftware");
			propertiesPG.put("jdbc.maxActive", "10");
			propertiesPG.put("jdbc.initialSize", "5");
			propertiesPG.put("jdbc.maxIdle", "5");
			propertiesPG.put("jdbc.validationQuery", "SELECT 1");
			propertiesPG.put("jdbc.verbose", "true");

			// migrar(propertiesPG, propertiesMS);
			prueas(propertiesPG, propertiesMS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	private static void prueas(Properties propertiesPG, Properties propertiesMS)
			throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		String pathSrc = "D:\\dev\\source\\massoftware\\src\\main\\java";
		String packageModel = "com.massoftware.model";
		
		EjercicioContable ejercicioContable = new EjercicioContable();
		ejercicioContable.setEjercicio(2018);
		Object value = ejercicioContable;
		

		UtilModel utilModel = new UtilModel();
		
		List<Class> objectsClass = utilModel.getObjectsClassModel(pathSrc, packageModel);

		List<Class> classNameDependencies = utilModel.getClassDependencies(objectsClass,
				value.getClass());
	
		BackendContext cx = new BackendContext();
		cx.start(BackendContext.MS, propertiesMS);

		for (Class objClass : classNameDependencies) {

			String methodName = "build" + objClass.getSimpleName() + "BO";
			Method method = cx.getClass().getDeclaredMethod(methodName);
			
			Object objectBO = method.invoke(cx);
			Method methodBO = objectBO.getClass().getDeclaredMethod("checkRefIntegrity" , value.getClass());
			@SuppressWarnings("unchecked")
			List<Object> listMSG = (List<Object>) methodBO.invoke(objectBO, value);
			if(listMSG != null && listMSG.size() > 0){
				
				String labelValue = cx.getMessages().getString("model." + value.getClass().getSimpleName());
				String pre = cx.getMessages().getString("model.pre." + value.getClass().getSimpleName());
				
//				throw new DeleteForeingObjectConflictException(pre, labelValue, value);
			}
		}
	}

	public static void migrar(Properties propertiesPG, Properties propertiesMS)
			throws Exception {

		System.out.println("\n\nStart Mgrador\n\n");

		BackendContext cxMSSQL = new BackendContext();
		cxMSSQL.start(BackendContext.MS, propertiesMS);
		BackendContext cxPG = new BackendContext();
		cxPG.start(BackendContext.PG, propertiesPG);

		// cxPG.buildEjercicioContableBO().insert(
		// cxMSSQL.buildEjercicioContableBO().findAll());
		//
		// cxPG.buildUsuarioBO().insert(cxMSSQL.buildUsuarioBO().findAll());
		//
		// cxPG.buildCentroDeCostoContableBO().insert(
		// cxMSSQL.buildCentroDeCostoContableBO()
		// .findAllOrderByCentroDeCostoContable());
		//
		// cxPG.buildPuntoDeEquilibrioTipoBO().insert(
		// cxMSSQL.buildPuntoDeEquilibrioTipoBO().findAll());
		//
		// cxPG.buildPuntoDeEquilibrioBO().insert(
		// cxMSSQL.buildPuntoDeEquilibrioBO()
		// .findAllOrderByPuntoDeEquilibrio());
		//
		// cxPG.buildPlanDeCuentaEstadoBO().insert(
		// cxMSSQL.buildPlanDeCuentaEstadoBO().findAll());
		//
		// cxPG.buildCostoDeVentaBO().insert(
		// cxMSSQL.buildCostoDeVentaBO().findAll());
		//
		// cxPG.buildPlanDeCuentaBO().insert(
		// cxMSSQL.buildPlanDeCuentaBO().findAll());

		List<SucursalTipo> sucursalTipoList = cxMSSQL.buildBO(SucursalTipo.class)
				.findAll();
		System.out.println(sucursalTipoList);

		List<Sucursal> sucursalList = cxMSSQL.buildBO(Sucursal.class).findAll();
		System.out.println(sucursalList);

		List<Talonario> talonarioList = cxMSSQL.buildTalonarioBO().findAll();
		System.out.println(talonarioList);

		List<Modulo> moduloList = cxMSSQL.buildModuloBO().findAll();
		System.out.println(moduloList);

		List<Deposito> depositoList = cxMSSQL.buildBO(Deposito.class).findAll();
		System.out.println(depositoList);

		

		List<TipoCbteControl> tipoCbteControlList = cxMSSQL
				.buildBO(TipoCbteControl.class).findAll();
		System.out.println(tipoCbteControlList);

		List<ModeloCbteFondo> modeloCbteFondoList = cxMSSQL
				.buildModeloCbteFondoBO().findAll();
		System.out.println(modeloCbteFondoList);

		List<ModeloCbteFondoItemConcepto> modeloCbteFondoItemConceptoList = cxMSSQL
				.buildModeloCbteFondoItemConceptoBO().findAll();
		System.out.println(modeloCbteFondoItemConceptoList);

		List<ModeloCbteFondoItem> modeloCbteFondoItemList = cxMSSQL
				.buildModeloCbteFondoItemBO().findAll();
		System.out.println(modeloCbteFondoItemList);

		modeloCbteFondoItemList = cxMSSQL.buildModeloCbteFondoItemBO()
				.findAllByModeloCbteFondo(3);
		System.out.println(modeloCbteFondoItemList);

		List<TipoCbteAFIP> tipoCbteAFIPList = cxMSSQL.buildBO(TipoCbteAFIP.class)
				.findAll();
		System.out.println(tipoCbteAFIPList);

		System.out.println("\n\nEnd Migrador\n\n");

	}
}
