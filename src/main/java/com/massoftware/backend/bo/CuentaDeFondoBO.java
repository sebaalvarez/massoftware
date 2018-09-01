package com.massoftware.backend.bo;

import java.math.BigDecimal;
import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.model.CuentaDeFondo;
import com.massoftware.model.CuentaDeFondoGrupo;
import com.massoftware.model.CuentaDeFondoRubro;
import com.massoftware.model.Usuario;

public class CuentaDeFondoBO extends GenericBO<CuentaDeFondo> {

	private final String ATT_NAME_CODIGO = "codigo";
	private final String ATT_NAME_NOMBRE = "nombre";

	public CuentaDeFondoBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(CuentaDeFondo.class, dataSourceWrapper, cx);
	}

	public CuentaDeFondo findByCodigo(Integer codigo) throws Exception {

		List<CuentaDeFondo> items = find("codigo", "codigo = ?", codigo);

		if (items.size() == 1) {
			return items.get(0);
		}

		throw new Exception(
				"La sentencia debería devolver un registro, la sentencia devolvió "
						+ items.size() + " registros.");
	}

	public List<CuentaDeFondo> findAll() throws Exception {
		return findAll("codigo, nombre");
	}

	public List<CuentaDeFondo> findActivas() throws Exception {

		return find("codigo, nombre", "obsoleta = ? OR obsoleta IS NULL", false);
	}

	public List<CuentaDeFondo> findAll(CuentaDeFondoGrupo cuentaDeFondoGrupo)
			throws Exception {

		return find(
				"codigo, nombre",
				"cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo = ? AND cuentaDeFondoGrupo_codigo = ?",
				cuentaDeFondoGrupo.getCuentaDeFondoRubro().getCodigo(),
				cuentaDeFondoGrupo.getCodigo());
	}

	public List<CuentaDeFondo> findActivas(CuentaDeFondoGrupo cuentaDeFondoGrupo)
			throws Exception {

		return find(
				"codigo, nombre",
				"(obsoleta = ? OR obsoleta IS NULL) AND cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo = ? AND cuentaDeFondoGrupo_codigo = ?",
				false, cuentaDeFondoGrupo.getCuentaDeFondoRubro().getCodigo(),
				cuentaDeFondoGrupo.getCodigo());
	}

	public List<CuentaDeFondo> findAll(CuentaDeFondoRubro cuentaDeFondoRubro)
			throws Exception {

		return find("codigo, nombre",
				"cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo = ?",
				cuentaDeFondoRubro.getCodigo());
	}

	public List<CuentaDeFondo> findActivas(CuentaDeFondoRubro cuentaDeFondoRubro)
			throws Exception {

		return find(
				"codigo, nombre",
				"(obsoleta = ? OR obsoleta IS NULL) AND cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo = ?",
				false, cuentaDeFondoRubro.getCodigo());
	}

	@Override
	public void checkUnique(String attName, Object value) throws Exception {

		String viewNameOld = this.getView();

		String viewName = "vCuentaDeFondoA";

		if (attName.equalsIgnoreCase(ATT_NAME_CODIGO)) {

			checkUnique(attName, ATT_NAME_CODIGO + " = ?", value);

		} else if (attName.equalsIgnoreCase(ATT_NAME_NOMBRE)) {

			checkUnique(attName, "LOWER(" + ATT_NAME_NOMBRE + ") = ?", value
					.toString().toLowerCase());

		}

		viewName = viewNameOld;
	}

	public CuentaDeFondo insert(CuentaDeFondo dto, Usuario usuario)
			throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[CUENTA]", "[NOMBRE]", "[RUBRO]", "[GRUPO]",
				"[TIPO]", "[MONEDA]", "[CAJA]", "[OBSOLETA]",
				"[NOIMPRIMECAJA]", "[VENTAS]", "[FONDOS]", "[COMPRAS]",
				"[EJERCICIO]", "[CUENTACONTABLE]", "[DOORNO]",
				"[DOORNOCONSULTA]", "[LIMITEOPERACIONINDIVIDUAL]",
				"[DOORNOLIMITE]", "[CUENTADIFERIDOS]", "[CUENTACAUCION]",
				"[LIMITEDESCUBIERTO]", "[BANCO]", "[CUENTABANCARIA]", "[CBU]",
				"[CONCILIACION]", "[CARTERARECHAZADOS]", "[TIPOBANCO]" };

		Object codigo = String.class;
		Object nombre = String.class;
		Object cuentaDeFondoRubro = Integer.class;
		Object cuentaDeFondoGrupo = Integer.class;
		Object cuentaDeFondoTipo = Integer.class;
		Object moneda = Integer.class;
		Object caja = Integer.class;
		Object obsoleta = Boolean.class;
		Object noImprimeCaja = Boolean.class;
		Object moduloVentas = Boolean.class;
		Object moduloFondos = Boolean.class;
		Object moduloCompras = Boolean.class;
		Object ejercicio = Integer.class;
		Object cuentaContable = String.class;
		Object seguridadPuerta = Integer.class;
		Object puertaConsulta = Integer.class;
		Object limiteOperacionIndividual = BigDecimal.class;
		Object puertaLimite = Integer.class;

		Object cuentaDiferidos = String.class;
		Object cuentaCaucion = String.class;
		Object limiteDescubierto = BigDecimal.class;
		Object banco = Integer.class;
		Object cuentaBancaria = String.class;
		Object cbu = String.class;
		Object conciliacion = Boolean.class;
		Object rechazados = Boolean.class;
		Object cuentaDeFondoTipoBanco = Integer.class;

		if (dto.getCodigo() != null) {
			codigo = dto.getCodigo();
		}
		if (dto.getNombre() != null) {
			nombre = dto.getNombre();
		}
		if (dto.getCuentaDeFondoGrupo() != null
				&& dto.getCuentaDeFondoGrupo().getId() != null
				&& dto.getCuentaDeFondoGrupo().getCuentaDeFondoRubro() != null
				&& dto.getCuentaDeFondoGrupo().getCuentaDeFondoRubro().getId() != null) {
			cuentaDeFondoRubro = dto.getCuentaDeFondoGrupo()
					.getCuentaDeFondoRubro().getCodigo();
		}
		if (dto.getCuentaDeFondoGrupo() != null
				&& dto.getCuentaDeFondoGrupo().getId() != null
				&& dto.getCuentaDeFondoGrupo().getCuentaDeFondoRubro() != null
				&& dto.getCuentaDeFondoGrupo().getCuentaDeFondoRubro().getId() != null) {
			cuentaDeFondoGrupo = dto.getCuentaDeFondoGrupo().getCodigo();
		}
		if (dto.getCuentaDeFondoTipo() != null
				&& dto.getCuentaDeFondoTipo().getId() != null) {
			cuentaDeFondoTipo = dto.getCuentaDeFondoTipo().getCodigo();
		}
		if (dto.getMoneda() != null && dto.getMoneda().getId() != null) {
			moneda = dto.getMoneda().getCodigo();
		}
		if (dto.getCaja() != null && dto.getCaja().getId() != null) {
			caja = dto.getCaja().getCodigo();
		}
		if (dto.getObsoleta() != null) {
			obsoleta = dto.getObsoleta();
		}
		if (dto.getNoImprimeCaja() != null) {
			noImprimeCaja = dto.getNoImprimeCaja();
		}
		if (dto.getModuloVentas() != null) {
			moduloVentas = dto.getModuloVentas();
		}
		if (dto.getModuloFondos() != null) {
			moduloFondos = dto.getModuloFondos();
		}
		if (dto.getModuloCompras() != null) {
			moduloCompras = dto.getModuloCompras();
		}
		if (dto.getCuentaContable() != null
				&& dto.getCuentaContable().getId() != null
				&& dto.getCuentaContable().getEjercicioContable() != null
				&& dto.getCuentaContable().getEjercicioContable().getId() != null) {
			ejercicio = dto.getCuentaContable().getEjercicioContable()
					.getEjercicio();
		}
		if (dto.getCuentaContable() != null
				&& dto.getCuentaContable().getId() != null) {
			cuentaContable = dto.getCuentaContable().getCuentaContable();
		}
		if (dto.getSeguridadPuerta() != null
				&& dto.getSeguridadPuerta().getId() != null) {
			seguridadPuerta = dto.getSeguridadPuerta().getCodigo();
		}
		if (dto.getPuertaConsulta() != null
				&& dto.getPuertaConsulta().getId() != null) {
			puertaConsulta = dto.getPuertaConsulta().getCodigo();
		}
		if (dto.getLimiteOperacionIndividual() != null) {
			limiteOperacionIndividual = dto.getLimiteOperacionIndividual();
		}
		if (dto.getPuertaLimite() != null
				&& dto.getPuertaLimite().getId() != null) {
			puertaLimite = dto.getPuertaLimite().getCodigo();
		}

		if (dto.getCuentaDiferidos() != null
				&& dto.getCuentaDiferidos().getId() != null) {
			cuentaDiferidos = dto.getCuentaDiferidos().getCodigo();
		}
		if (dto.getCuentaCaucion() != null
				&& dto.getCuentaCaucion().getId() != null) {
			cuentaCaucion = dto.getCuentaCaucion().getCodigo();
		}
		if (dto.getLimiteDescubierto() != null) {
			limiteDescubierto = dto.getLimiteDescubierto();
		}
		if (dto.getBanco() != null && dto.getBanco().getId() != null) {
			banco = dto.getBanco().getCodigo();
		}
		if (dto.getCuentaBancaria() != null) {
			cuentaBancaria = dto.getCuentaBancaria();
		}
		if (dto.getCbu() != null) {
			cbu = dto.getCbu();
		}
		if (dto.getConciliacion() != null) {
			conciliacion = dto.getConciliacion();
		}
		if (dto.getRechazados() != null) {
			rechazados = dto.getRechazados();
		}
		if (dto.getCuentaDeFondoTipoBanco() != null
				&& dto.getCuentaDeFondoTipoBanco().getId() != null) {
			cuentaDeFondoTipoBanco = dto.getCuentaDeFondoTipoBanco()
					.getCodigo();
		}

		Object[] args = { codigo, nombre, cuentaDeFondoRubro,
				cuentaDeFondoGrupo, cuentaDeFondoTipo, moneda, caja, obsoleta,
				noImprimeCaja, moduloVentas, moduloFondos, moduloCompras,
				ejercicio, cuentaContable, seguridadPuerta, puertaConsulta,
				limiteOperacionIndividual, puertaLimite, cuentaDiferidos,
				cuentaCaucion, limiteDescubierto, banco, cuentaBancaria, cbu,
				conciliacion, rechazados, cuentaDeFondoTipoBanco };

		insert(nameTableDB, nameAtts, args);

		return dto;

		// return insertByReflection(dto, usuario);
	}

	// public CuentaDeFondo update(CuentaDeFondo dto, CuentaDeFondo dtoOriginal,
	// Usuario usuario) throws Exception {
	//
	// Object codigoArg = null;
	//
	// if (dtoOriginal.getCodigo() != null) {
	// codigoArg = dtoOriginal.getCodigo();
	// } else {
	// codigoArg = Integer.class;
	// }
	//
	// if (dataSourceWrapper.isDatabasePostgreSql()) {
	//
	// } else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
	//
	// return updateByReflection(
	// dto,
	// usuario,
	// getFieldNameMS(classModel.getDeclaredField(ATT_NAME_CODIGO))
	// + " = ?", codigoArg);
	// }
	//
	// return null;
	// }

	public CuentaDeFondo update(CuentaDeFondo dto, CuentaDeFondo dtoOriginal,
			Usuario usuario) throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[CUENTA]", "[NOMBRE]", "[RUBRO]", "[GRUPO]",
				"[TIPO]", "[MONEDA]", "[CAJA]", "[OBSOLETA]",
				"[NOIMPRIMECAJA]", "[VENTAS]", "[FONDOS]", "[COMPRAS]",
				"[EJERCICIO]", "[CUENTACONTABLE]", "[DOORNO]",
				"[DOORNOCONSULTA]", "[LIMITEOPERACIONINDIVIDUAL]",
				"[DOORNOLIMITE]", "[CUENTADIFERIDOS]", "[CUENTACAUCION]",
				"[LIMITEDESCUBIERTO]", "[BANCO]", "[CUENTABANCARIA]", "[CBU]",
				"[CONCILIACION]", "[CARTERARECHAZADOS]", "[TIPOBANCO]" };

		Object codigo = String.class;
		Object nombre = String.class;
		Object cuentaDeFondoRubro = Integer.class;
		Object cuentaDeFondoGrupo = Integer.class;
		Object cuentaDeFondoTipo = Integer.class;
		Object moneda = Integer.class;
		Object caja = Integer.class;
		Object obsoleta = Boolean.class;
		Object noImprimeCaja = Boolean.class;
		Object moduloVentas = Boolean.class;
		Object moduloFondos = Boolean.class;
		Object moduloCompras = Boolean.class;
		Object ejercicio = Integer.class;
		Object cuentaContable = String.class;
		Object seguridadPuerta = Integer.class;
		Object puertaConsulta = Integer.class;
		Object limiteOperacionIndividual = BigDecimal.class;
		Object puertaLimite = Integer.class;

		Object cuentaDiferidos = String.class;
		Object cuentaCaucion = String.class;
		Object limiteDescubierto = BigDecimal.class;
		Object banco = Integer.class;
		Object cuentaBancaria = String.class;
		Object cbu = String.class;
		Object conciliacion = Boolean.class;
		Object rechazados = Boolean.class;
		Object cuentaDeFondoTipoBanco = Integer.class;

		if (dto.getCodigo() != null) {
			codigo = dto.getCodigo();
		}
		if (dto.getNombre() != null) {
			nombre = dto.getNombre();
		}
		if (dto.getCuentaDeFondoGrupo() != null
				&& dto.getCuentaDeFondoGrupo().getId() != null
				&& dto.getCuentaDeFondoGrupo().getCuentaDeFondoRubro() != null
				&& dto.getCuentaDeFondoGrupo().getCuentaDeFondoRubro().getId() != null) {
			cuentaDeFondoRubro = dto.getCuentaDeFondoGrupo()
					.getCuentaDeFondoRubro().getCodigo();
		}
		if (dto.getCuentaDeFondoGrupo() != null
				&& dto.getCuentaDeFondoGrupo().getId() != null
				&& dto.getCuentaDeFondoGrupo().getCuentaDeFondoRubro() != null
				&& dto.getCuentaDeFondoGrupo().getCuentaDeFondoRubro().getId() != null) {
			cuentaDeFondoGrupo = dto.getCuentaDeFondoGrupo().getCodigo();
		}
		if (dto.getCuentaDeFondoTipo() != null
				&& dto.getCuentaDeFondoTipo().getId() != null) {
			cuentaDeFondoTipo = dto.getCuentaDeFondoTipo().getCodigo();
		}
		if (dto.getMoneda() != null && dto.getMoneda().getId() != null) {
			moneda = dto.getMoneda().getCodigo();
		}
		if (dto.getCaja() != null && dto.getCaja().getId() != null) {
			caja = dto.getCaja().getCodigo();
		}
		if (dto.getObsoleta() != null) {
			obsoleta = dto.getObsoleta();
		}
		if (dto.getNoImprimeCaja() != null) {
			noImprimeCaja = dto.getNoImprimeCaja();
		}
		if (dto.getModuloVentas() != null) {
			moduloVentas = dto.getModuloVentas();
		}
		if (dto.getModuloFondos() != null) {
			moduloFondos = dto.getModuloFondos();
		}
		if (dto.getModuloCompras() != null) {
			moduloCompras = dto.getModuloCompras();
		}
		if (dto.getCuentaContable() != null
				&& dto.getCuentaContable().getId() != null
				&& dto.getCuentaContable().getEjercicioContable() != null
				&& dto.getCuentaContable().getEjercicioContable().getId() != null) {
			ejercicio = dto.getCuentaContable().getEjercicioContable()
					.getEjercicio();
		}
		if (dto.getCuentaContable() != null
				&& dto.getCuentaContable().getId() != null) {
			cuentaContable = dto.getCuentaContable().getCuentaContable();
		}
		if (dto.getSeguridadPuerta() != null
				&& dto.getSeguridadPuerta().getId() != null) {
			seguridadPuerta = dto.getSeguridadPuerta().getCodigo();
		}
		if (dto.getPuertaConsulta() != null
				&& dto.getPuertaConsulta().getId() != null) {
			puertaConsulta = dto.getPuertaConsulta().getCodigo();
		}
		if (dto.getLimiteOperacionIndividual() != null) {
			limiteOperacionIndividual = dto.getLimiteOperacionIndividual();
		}
		if (dto.getPuertaLimite() != null
				&& dto.getPuertaLimite().getId() != null) {
			puertaLimite = dto.getPuertaLimite().getCodigo();
		}

		if (dto.getCuentaDiferidos() != null
				&& dto.getCuentaDiferidos().getId() != null) {
			cuentaDiferidos = dto.getCuentaDiferidos().getCodigo();
		}
		if (dto.getCuentaCaucion() != null
				&& dto.getCuentaCaucion().getId() != null) {
			cuentaCaucion = dto.getCuentaCaucion().getCodigo();
		}
		if (dto.getLimiteDescubierto() != null) {
			limiteDescubierto = dto.getLimiteDescubierto();
		}
		if (dto.getBanco() != null && dto.getBanco().getId() != null) {
			banco = dto.getBanco().getCodigo();
		}
		if (dto.getCuentaBancaria() != null) {
			cuentaBancaria = dto.getCuentaBancaria();
		}
		if (dto.getCbu() != null) {
			cbu = dto.getCbu();
		}
		if (dto.getConciliacion() != null) {
			conciliacion = dto.getConciliacion();
		}
		if (dto.getRechazados() != null) {
			rechazados = dto.getRechazados();
		}
		if (dto.getCuentaDeFondoTipoBanco() != null
				&& dto.getCuentaDeFondoTipoBanco().getId() != null) {
			cuentaDeFondoTipoBanco = dto.getCuentaDeFondoTipoBanco()
					.getCodigo();
		}

		Object codigoOriginal = Integer.class;

		if (dtoOriginal.getCodigo() != null) {
			codigoOriginal = dtoOriginal.getCodigo();
		}

		String where = getFieldNameMS(classModel
				.getDeclaredField(ATT_NAME_CODIGO)) + " = ?";

		Object[] args = { codigo, nombre, cuentaDeFondoRubro,
				cuentaDeFondoGrupo, cuentaDeFondoTipo, moneda, caja, obsoleta,
				noImprimeCaja, moduloVentas, moduloFondos, moduloCompras,
				ejercicio, cuentaContable, seguridadPuerta, puertaConsulta,
				limiteOperacionIndividual, puertaLimite, cuentaDiferidos,
				cuentaCaucion, limiteDescubierto, banco, cuentaBancaria, cbu,
				conciliacion, rechazados, cuentaDeFondoTipoBanco,
				codigoOriginal };

		update(nameTableDB, nameAtts, args, where);

		return dto;
	}

}
