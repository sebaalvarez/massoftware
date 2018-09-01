package com.massoftware.backend.bo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;
import org.cendra.jdbc.ex.crud.UniqueException;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.model.Chequera;
import com.massoftware.model.CuentaDeFondoA;
import com.massoftware.model.Usuario;

public class ChequeraBO extends GenericBO<Chequera> {

	public ChequeraBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(Chequera.class, dataSourceWrapper, cx);
	}

	public List<Chequera> findAll() throws Exception {
		return findAll("cuentaDeFondo_codigo, codigo");
	}

	public List<Chequera> findAll(CuentaDeFondoA cuentaDeFondo,
			boolean incluirBloqueadas) throws Exception {

		if (cuentaDeFondo != null) {
			if (incluirBloqueadas) {
				return find("cuentaDeFondo_codigo, codigo",
						"cuentaDeFondo_codigo = ?", cuentaDeFondo.getCodigo());
			} else {
				return find("cuentaDeFondo_codigo, codigo",
						"bloqueado = ? AND cuentaDeFondo_codigo = ?", false,
						cuentaDeFondo.getCodigo());
			}

		}

		return new ArrayList<Chequera>();
	}

	public void checkUnique(Chequera dto, Chequera dtoOriginal)
			throws Exception {

		// fullEquals(Class type, boolean ignoreCaseTraslate,
		// boolean ignoreCase, Object value, Object originalValue);

		if (dtoOriginal != null
				&& dto.getCuentaDeFondo().getCodigo()
						.equals(dtoOriginal.getCuentaDeFondo().getCodigo())
				&& dto.getCodigo().equals(dtoOriginal.getCodigo())) {

			return;

		}

		String where = "cuentaDeFondo_codigo = ? AND codigo = ?";

		Object codigoCuentaDeFondoArg = null;

		if (dto.getCuentaDeFondo().getCodigo() != null) {
			codigoCuentaDeFondoArg = dto.getCuentaDeFondo().getCodigo();
		} else {
			codigoCuentaDeFondoArg = Integer.class;
		}

		Object codigoArg = null;

		if (dto.getCodigo() != null) {
			codigoArg = dto.getCodigo();
		} else {
			codigoArg = Integer.class;
		}

		if (ifExists(where, codigoCuentaDeFondoArg, codigoArg)) {

			Field fieldCuentaDeFondo = Chequera.class
					.getDeclaredField("cuentaDeFondo");
			Field fieldCodigo = Chequera.class.getDeclaredField("codigo");

			throw new UniqueException(getLabel(fieldCuentaDeFondo),
					getLabel(fieldCodigo));
		}

	}

	public Chequera insert(Chequera dto, Usuario usuario) throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[CUENTA]", "[CHEQUERA]", "[PRIMERNRO]",
				"[ULTIMONRO]", "[PROXIMONRO]", "[BLOQUEADO]",
				"[IMPRESIONDIFERIDA]", "[DESTINOIMPRESION]", "[FORMATO]" };

		Object cuentaDeFondo = Integer.class;
		Object codigo = Integer.class;
		Object primerNumero = Integer.class;
		Object ultimoNumero = Integer.class;
		Object proximoNumero = Integer.class;
		Object bloqueado = Boolean.class;
		Object impresionDiferida = Boolean.class;
		Object destinoImpresion = String.class;
		Object formato = String.class;

		if (dto.getCuentaDeFondo() != null
				&& dto.getCuentaDeFondo().getId() != null) {
			cuentaDeFondo = dto.getCuentaDeFondo().getCodigo();
		}
		if (dto.getCodigo() != null) {
			codigo = dto.getCodigo();
		}
		if (dto.getPrimerNumero() != null) {
			primerNumero = dto.getPrimerNumero();
		}
		if (dto.getUltimoNumero() != null) {
			ultimoNumero = dto.getUltimoNumero();
		}
		if (dto.getProximoNumero() != null) {
			proximoNumero = dto.getProximoNumero();
		}
		if (dto.getBloqueado() != null) {
			bloqueado = dto.getBloqueado();
		}
		if (dto.getImpresionDiferida() != null) {
			impresionDiferida = dto.getImpresionDiferida();
		}
		if (dto.getDestinoImpresion() != null) {
			destinoImpresion = dto.getDestinoImpresion();
		}
		if (dto.getFormato() != null) {
			formato = dto.getFormato();
		}

		Object[] args = { cuentaDeFondo, codigo, primerNumero, ultimoNumero,
				proximoNumero, bloqueado, impresionDiferida, destinoImpresion,
				formato };

		insert(nameTableDB, nameAtts, args);

		return dto;

		// return insertByReflection(dto, usuario);
	}

	public Chequera update(Chequera dto, Chequera dtoOriginal, Usuario usuario)
			throws Exception {

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[CUENTA]", "[CHEQUERA]", "[PRIMERNRO]",
				"[ULTIMONRO]", "[PROXIMONRO]", "[BLOQUEADO]",
				"[IMPRESIONDIFERIDA]", "[DESTINOIMPRESION]", "[FORMATO]" };

		Object cuentaDeFondo = Integer.class;
		Object codigo = Integer.class;
		Object primerNumero = Integer.class;
		Object ultimoNumero = Integer.class;
		Object proximoNumero = Integer.class;
		Object bloqueado = Boolean.class;
		Object impresionDiferida = Boolean.class;
		Object destinoImpresion = String.class;
		Object formato = String.class;

		if (dto.getCuentaDeFondo() != null
				&& dto.getCuentaDeFondo().getId() != null) {
			cuentaDeFondo = dto.getCuentaDeFondo().getCodigo();
		}
		if (dto.getCodigo() != null) {
			codigo = dto.getCodigo();
		}
		if (dto.getPrimerNumero() != null) {
			primerNumero = dto.getPrimerNumero();
		}
		if (dto.getUltimoNumero() != null) {
			ultimoNumero = dto.getUltimoNumero();
		}
		if (dto.getProximoNumero() != null) {
			proximoNumero = dto.getProximoNumero();
		}
		if (dto.getBloqueado() != null) {
			bloqueado = dto.getBloqueado();
		}
		if (dto.getImpresionDiferida() != null) {
			impresionDiferida = dto.getImpresionDiferida();
		}
		if (dto.getDestinoImpresion() != null) {
			destinoImpresion = dto.getDestinoImpresion();
		}
		if (dto.getFormato() != null) {
			formato = dto.getFormato();
		}

		Object cuentaDeFondoOriginal = Integer.class;

		if (dtoOriginal.getCuentaDeFondo() != null
				&& dto.getCuentaDeFondo().getId() != null) {
			cuentaDeFondoOriginal = dtoOriginal.getCuentaDeFondo().getCodigo();
		}

		Object codigoOriginal = Integer.class;

		if (dtoOriginal.getCodigo() != null) {
			codigoOriginal = dtoOriginal.getCodigo();
		}

		String where = "[CUENTA] = ? AND [CHEQUERA] = ?";

		Object[] args = { cuentaDeFondo, codigo, primerNumero, ultimoNumero,
				proximoNumero, bloqueado, impresionDiferida, destinoImpresion,
				formato, cuentaDeFondoOriginal, codigoOriginal };

		update(nameTableDB, nameAtts, args, where);

		return dto;
	}

	public boolean delete(Chequera dto) throws Exception {

		Object codigoCuentaDeFondoArg = null;

		if (dto.getCuentaDeFondo().getCodigo() != null) {
			codigoCuentaDeFondoArg = dto.getCuentaDeFondo().getCodigo();
		} else {
			codigoCuentaDeFondoArg = Integer.class;
		}

		Object codigoArg = null;

		if (dto.getCodigo() != null) {
			codigoArg = dto.getCodigo();
		} else {
			codigoArg = Integer.class;
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			// return delete(ATT_NAME_CODIGO + " = ?", codigoArg);
			return false;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return delete("[CUENTA] = ? AND [CHEQUERA] = ?",
					codigoCuentaDeFondoArg, codigoArg);
		}

		return false;

	}
}
