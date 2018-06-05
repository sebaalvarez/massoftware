package com.massoftware.backend.bo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.cendra.ex.crud.UniqueException;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.Chequera;
import com.massoftware.model.EjercicioContable;

public class CentroDeCostoContableBO extends GenericBO<CentroDeCostoContable> {

	public CentroDeCostoContableBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(CentroDeCostoContable.class, dataSourceWrapper, cx);
	}

	public List<CentroDeCostoContable> findAll() throws Exception {
		return findAll("ejercicioContable_ejercicio, numero");
	}

	public List<CentroDeCostoContable> findAll(EjercicioContable dto)
			throws Exception {

		if (dto != null) {
			return find("ejercicioContable_ejercicio, numero",
					"ejercicioContable_ejercicio = ?", dto.getEjercicio());

		}

		return new ArrayList<CentroDeCostoContable>();
	}

	public void checkUnique(CentroDeCostoContable dto,
			CentroDeCostoContable dtoOriginal) throws Exception {

		// fullEquals(Class type, boolean ignoreCaseTraslate,
		// boolean ignoreCase, Object value, Object originalValue);

		if (dtoOriginal != null
				&& dto.getEjercicioContable()
						.getEjercicio()
						.equals(dtoOriginal.getEjercicioContable()
								.getEjercicio())
				&& dto.getNumero().equals(dtoOriginal.getNumero())) {

			return;

		}

		String where = "ejercicioContable_ejercicio = ? AND numero = ?";

		Object ejercicioArg = null;

		if (dto.getEjercicioContable().getEjercicio() != null) {
			ejercicioArg = dto.getEjercicioContable().getEjercicio();
		} else {
			ejercicioArg = Integer.class;
		}

		Object numeroArg = null;

		if (dto.getNumero() != null) {
			numeroArg = dto.getNumero();
		} else {
			numeroArg = Integer.class;
		}

		if (ifExists(where, ejercicioArg, numeroArg)) {

			Field fieldEjercicioContable = CentroDeCostoContable.class
					.getDeclaredField("ejercicioContable");
			Field fieldNumero = CentroDeCostoContable.class
					.getDeclaredField("numero");

			throw new UniqueException(getLabel(fieldEjercicioContable),
					getLabel(fieldNumero));
		}

	}

	// public CentroDeCostoContable insert(CentroDeCostoContable dto, Usuario
	// usuario) throws Exception {
	//
	// String nameTableDB = getClassTableMSAnont(classModel);
	//
	// String[] nameAtts = { "[CUENTA]", "[CHEQUERA]", "[PRIMERNRO]",
	// "[ULTIMONRO]", "[PROXIMONRO]", "[BLOQUEADO]",
	// "[IMPRESIONDIFERIDA]", "[DESTINOIMPRESION]", "[FORMATO]" };
	//
	// Object cuentaDeFondo = Integer.class;
	// Object codigo = Integer.class;
	// Object primerNumero = Integer.class;
	// Object ultimoNumero = Integer.class;
	// Object proximoNumero = Integer.class;
	// Object bloqueado = Boolean.class;
	// Object impresionDiferida = Boolean.class;
	// Object destinoImpresion = String.class;
	// Object formato = String.class;
	//
	// if (dto.getCuentaDeFondo() != null
	// && dto.getCuentaDeFondo().getId() != null) {
	// cuentaDeFondo = dto.getCuentaDeFondo().getCodigo();
	// }
	// if (dto.getCodigo() != null) {
	// codigo = dto.getCodigo();
	// }
	// if (dto.getPrimerNumero() != null) {
	// primerNumero = dto.getPrimerNumero();
	// }
	// if (dto.getUltimoNumero() != null) {
	// ultimoNumero = dto.getUltimoNumero();
	// }
	// if (dto.getProximoNumero() != null) {
	// proximoNumero = dto.getProximoNumero();
	// }
	// if (dto.getBloqueado() != null) {
	// bloqueado = dto.getBloqueado();
	// }
	// if (dto.getImpresionDiferida() != null) {
	// impresionDiferida = dto.getImpresionDiferida();
	// }
	// if (dto.getDestinoImpresion() != null) {
	// destinoImpresion = dto.getDestinoImpresion();
	// }
	// if (dto.getFormato() != null) {
	// formato = dto.getFormato();
	// }
	//
	// Object[] args = { cuentaDeFondo, codigo, primerNumero, ultimoNumero,
	// proximoNumero, bloqueado, impresionDiferida, destinoImpresion,
	// formato };
	//
	// insert(nameTableDB, nameAtts, args);
	//
	// return dto;
	//
	// // return insertByReflection(dto, usuario);
	// }

	// public Chequera update(Chequera dto, Chequera dtoOriginal, Usuario
	// usuario)
	// throws Exception {
	//
	// String nameTableDB = getClassTableMSAnont(classModel);
	//
	// String[] nameAtts = { "[CUENTA]", "[CHEQUERA]", "[PRIMERNRO]",
	// "[ULTIMONRO]", "[PROXIMONRO]", "[BLOQUEADO]",
	// "[IMPRESIONDIFERIDA]", "[DESTINOIMPRESION]", "[FORMATO]" };
	//
	// Object cuentaDeFondo = Integer.class;
	// Object codigo = Integer.class;
	// Object primerNumero = Integer.class;
	// Object ultimoNumero = Integer.class;
	// Object proximoNumero = Integer.class;
	// Object bloqueado = Boolean.class;
	// Object impresionDiferida = Boolean.class;
	// Object destinoImpresion = String.class;
	// Object formato = String.class;
	//
	// if (dto.getCuentaDeFondo() != null
	// && dto.getCuentaDeFondo().getId() != null) {
	// cuentaDeFondo = dto.getCuentaDeFondo().getCodigo();
	// }
	// if (dto.getCodigo() != null) {
	// codigo = dto.getCodigo();
	// }
	// if (dto.getPrimerNumero() != null) {
	// primerNumero = dto.getPrimerNumero();
	// }
	// if (dto.getUltimoNumero() != null) {
	// ultimoNumero = dto.getUltimoNumero();
	// }
	// if (dto.getProximoNumero() != null) {
	// proximoNumero = dto.getProximoNumero();
	// }
	// if (dto.getBloqueado() != null) {
	// bloqueado = dto.getBloqueado();
	// }
	// if (dto.getImpresionDiferida() != null) {
	// impresionDiferida = dto.getImpresionDiferida();
	// }
	// if (dto.getDestinoImpresion() != null) {
	// destinoImpresion = dto.getDestinoImpresion();
	// }
	// if (dto.getFormato() != null) {
	// formato = dto.getFormato();
	// }
	//
	// Object cuentaDeFondoOriginal = Integer.class;
	//
	// if (dtoOriginal.getCuentaDeFondo() != null
	// && dto.getCuentaDeFondo().getId() != null) {
	// cuentaDeFondoOriginal = dtoOriginal.getCuentaDeFondo().getCodigo();
	// }
	//
	// Object codigoOriginal = Integer.class;
	//
	// if (dtoOriginal.getCodigo() != null) {
	// codigoOriginal = dtoOriginal.getCodigo();
	// }
	//
	// String where = "[CUENTA] = ? AND [CHEQUERA] = ?";
	//
	// Object[] args = { cuentaDeFondo, codigo, primerNumero, ultimoNumero,
	// proximoNumero, bloqueado, impresionDiferida, destinoImpresion,
	// formato, cuentaDeFondoOriginal, codigoOriginal };
	//
	// update(nameTableDB, nameAtts, args, where);
	//
	// return dto;
	// }

	public boolean delete(CentroDeCostoContable dto) throws Exception {

		Object ejercicioArg = null;

		if (dto.getEjercicioContable().getEjercicio() != null) {
			ejercicioArg = dto.getEjercicioContable().getEjercicio();
		} else {
			ejercicioArg = Integer.class;
		}

		Object numeroArg = null;

		if (dto.getNumero() != null) {
			numeroArg = dto.getNumero();
		} else {
			numeroArg = Integer.class;
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			// return delete(ATT_NAME_CODIGO + " = ?", codigoArg);
			return false;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return delete("[EJERCICIO] = ? AND [CENTRODECOSTOCONTABLE] = ?",
					ejercicioArg, numeroArg);
		}

		return false;

	}
}
