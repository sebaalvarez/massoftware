package z.old.trash;

import java.util.HashMap;
import java.util.Map;

import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaContableEstado;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.PuntoDeEquilibrioTipo;

class MetaData {

	private Map<String, EntityMetaData> entityMetaDataMap = new HashMap<String, EntityMetaData>();

	@Deprecated
	public EntityMetaData getEntityMetaData(String key) {
		return entityMetaDataMap.get(key);
	}

	public MetaData() {
		entityMetaDataMap = new HashMap<String, EntityMetaData>();

		// --------------------------------------------------

		EntityMetaData ejercicioContableMD = new EntityMetaData();
		ejercicioContableMD.setName(EjercicioContable.class.getCanonicalName());
		ejercicioContableMD.setLabel("Ejercicio contable");
		ejercicioContableMD.setLabelShort("Ejercicio");
		ejercicioContableMD.setLabelPlural("Ejercicios contables");

		ejercicioContableMD.addAtt("ejercicio", "Ejercicio");
		ejercicioContableMD.addAtt("fechaApertura", "Apertura",
				"Fecha de apertura");
		ejercicioContableMD.addAtt("fechaCierre", "Cierre", "Fecha de cierre");
		ejercicioContableMD.addAtt("ejercicioCerrado", "Cerrado");
		ejercicioContableMD.addAtt("ejercicioCerradoModulos", "Módulos");
		ejercicioContableMD.addAtt("comentario", "Comentario");

		entityMetaDataMap.put(ejercicioContableMD.getName(),
				ejercicioContableMD);

		// --------------------------------------------------

		EntityMetaData centroDeCostoContableMD = new EntityMetaData();
		centroDeCostoContableMD.setName(CentroDeCostoContable.class
				.getCanonicalName());
		centroDeCostoContableMD.setLabel("Centro de costo contable");
		centroDeCostoContableMD.setLabelShort("Cto. de costo");
		centroDeCostoContableMD.setLabelPlural("Centros de costos contables");

		centroDeCostoContableMD.addAtt("ejercicioContable",
				ejercicioContableMD.getLabelShort(),
				ejercicioContableMD.getLabel());
		centroDeCostoContableMD.addAtt("numero", "C.C.",
				"Centro de costo contable");
		centroDeCostoContableMD.addAtt("nombre", "Nomnbre");
		centroDeCostoContableMD.addAtt("abreviatura", "Abreviatura");

		entityMetaDataMap.put(centroDeCostoContableMD.getName(),
				centroDeCostoContableMD);

		// --------------------------------------------------

		EntityMetaData puntoDeEquilibrioMD = new EntityMetaData();
		puntoDeEquilibrioMD.setName(PuntoDeEquilibrio.class.getCanonicalName());
		puntoDeEquilibrioMD.setLabel("Punto de equilibrio");
		puntoDeEquilibrioMD.setLabelPlural("Puntos de equilibrio");

		puntoDeEquilibrioMD.addAtt("ejercicioContable",
				ejercicioContableMD.getLabelShort(),
				ejercicioContableMD.getLabel());
		puntoDeEquilibrioMD.addAtt("puntoDeEquilibrio", "Pto. de Equ.",
				"Punto de equilibrio");
		puntoDeEquilibrioMD.addAtt("nombre", "Nombre");
		puntoDeEquilibrioMD.addAtt("puntoDeEquilibrioTipo", "Tipo");

		entityMetaDataMap.put(puntoDeEquilibrioMD.getName(),
				puntoDeEquilibrioMD);

		// --------------------------------------------------

		EntityMetaData puntoDeEquilibrioTipoMD = new EntityMetaData();
		puntoDeEquilibrioTipoMD.setName(PuntoDeEquilibrioTipo.class
				.getCanonicalName());
		puntoDeEquilibrioTipoMD.setLabel("Tipo de punto de equilibrio");
		puntoDeEquilibrioTipoMD.setLabelPlural("Tipos de punto de equilibrio");

		puntoDeEquilibrioTipoMD.addAtt("tipo", "Tipo");
		puntoDeEquilibrioTipoMD.addAtt("nombre", "Nombre");

		entityMetaDataMap.put(puntoDeEquilibrioTipoMD.getName(),
				puntoDeEquilibrioTipoMD);

		// --------------------------------------------------

		EntityMetaData planDeCuentaEstadoMD = new EntityMetaData();

		planDeCuentaEstadoMD.setName(CuentaContableEstado.class
				.getCanonicalName());
		planDeCuentaEstadoMD.setLabel("Estado");
		planDeCuentaEstadoMD.setLabelPlural("Estados");

		planDeCuentaEstadoMD.addAtt("codigo", "Codigo");
		planDeCuentaEstadoMD.addAtt("nombre", "Nombre");

		entityMetaDataMap.put(planDeCuentaEstadoMD.getName(),
				planDeCuentaEstadoMD);

		// --------------------------------------------------

		EntityMetaData costoDeVentaMD = new EntityMetaData();

		costoDeVentaMD.setName(CuentaContableEstado.class.getCanonicalName());
		costoDeVentaMD.setLabel("Costo de venta");
		costoDeVentaMD.setLabelPlural("Costos de venta");

		costoDeVentaMD.addAtt("codigo", "Codigo");
		costoDeVentaMD.addAtt("nombre", "Nombre");

		entityMetaDataMap.put(costoDeVentaMD.getName(), costoDeVentaMD);

		// --------------------------------------------------

		EntityMetaData planDeCuentaMD = new EntityMetaData();
		planDeCuentaMD.setName(CuentaContable.class.getCanonicalName());
		planDeCuentaMD.setLabel("Plan de cuenta");
		planDeCuentaMD.setLabelPlural("Plan de cuentas");

		planDeCuentaMD.addAtt("ejercicioContable",
				ejercicioContableMD.getLabelShort(),
				ejercicioContableMD.getLabel());
		planDeCuentaMD.addAtt("codigoCuentaPadre", "Integra");
		planDeCuentaMD.addAtt("codigoCuenta", "Cta. jerarquia",
				"Cuenta de jerarquia");
		planDeCuentaMD.addAtt("cuentaContable", "Cta. contable",
				"Cuenta contable");
		planDeCuentaMD.addAtt("nombre", "Nombre");
		planDeCuentaMD.addAtt("imputable", "Imputable");
		planDeCuentaMD.addAtt("ajustaPorInflacion", "Ajusta por inflación");
		planDeCuentaMD.addAtt("planDeCuentaEstado",
				planDeCuentaEstadoMD.getLabelShort(),
				planDeCuentaEstadoMD.getLabel());
		planDeCuentaMD.addAtt("cuentaConApropiacion", "Cuenta con apropiación");
		planDeCuentaMD.addAtt("centroDeCostoContable",
				centroDeCostoContableMD.getLabelShort(),
				centroDeCostoContableMD.getLabel());
		planDeCuentaMD.addAtt("cuentaAgrupadora", "Cta. agrupadora",
				"Cuenta agrupadora");
		planDeCuentaMD.addAtt("porcentaje", "%", "Porcentaje");
		planDeCuentaMD.addAtt("puntoDeEquilibrio",
				puntoDeEquilibrioMD.getLabelShort(),
				puntoDeEquilibrioMD.getLabel());
		planDeCuentaMD.addAtt("costoDeVenta", costoDeVentaMD.getLabelShort(),
				costoDeVentaMD.getLabel());

		entityMetaDataMap.put(planDeCuentaMD.getName(), planDeCuentaMD);

	}

}
