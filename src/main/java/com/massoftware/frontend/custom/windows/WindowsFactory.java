package com.massoftware.frontend.custom.windows;

import z.old.deprecated.CentroDeCostoProyectoTableUi;
import z.old.deprecated.ChequeraTableUi;
import z.old.deprecated.CuentaDeFondoTableUi;

import com.massoftware.SessionVar;
import com.massoftware.model.Asiento;
import com.massoftware.model.AsientoModeloItem;
import com.massoftware.model.Banco;
import com.massoftware.model.BancoFirmante;
import com.massoftware.model.Caja;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CentroDeCostoProyecto;
import com.massoftware.model.Chequera;
import com.massoftware.model.Ciudad;
import com.massoftware.model.CodigoConvenioMultilateral;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaDeFondo;
import com.massoftware.model.CuentaDeFondoA;
import com.massoftware.model.Deposito;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.JurisdiccionConvenioMultilateral;
import com.massoftware.model.Moneda;
import com.massoftware.model.MonedaAFIP;
import com.massoftware.model.MonedaCotizacion;
import com.massoftware.model.MotivoComentario;
import com.massoftware.model.MotivoNotaDeCredito;
import com.massoftware.model.Pais;
import com.massoftware.model.Provincia;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.SeguridadPuerta;
import com.massoftware.model.Sucursal;
import com.massoftware.model.Talonario;
import com.massoftware.model.Ticket;
import com.massoftware.model.TipoCbteAFIP;
import com.massoftware.model.TipoCbteControl;
import com.massoftware.model.TipoCliente;
import com.massoftware.model.TipoDocumentoAFIP;
import com.massoftware.model.Zona;
import com.massoftware.windows.LogAndNotification;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public class WindowsFactory {

	@SuppressWarnings("rawtypes")
	public static StandardTableUi openWindow(Component component,
			Class classModel, SessionVar sessionVar) {

		return openWindow(new StandarTableUiPagedConf(),
				new StandarTableUiToolbarConf(), component, classModel,
				sessionVar, new StandarTableUiFilteringSet());
	}

	@SuppressWarnings("rawtypes")
	public static StandardTableUi openWindow(Component component,
			Class classModel, SessionVar sessionVar,
			StandarTableUiFilteringSet filteringSet) {

		return openWindow(new StandarTableUiPagedConf(),
				new StandarTableUiToolbarConf(), component, classModel,
				sessionVar, filteringSet);
	}

	@SuppressWarnings("rawtypes")
	public static StandardTableUi openWindow(StandarTableUiPagedConf pagedConf,
			StandarTableUiToolbarConf toolbarConf, Component component,
			Class classModel, SessionVar sessionVar,
			StandarTableUiFilteringSet filteringSet) {
		try {

			Window win = new Window();

			StandardTableUi standardTableUi = buildTable(pagedConf,
					toolbarConf, component, classModel, sessionVar,
					filteringSet, win);

			component.getUI().addWindow(win);

			standardTableUi.filtrar();

			return standardTableUi;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static StandardTableUi buildTable(
			StandarTableUiPagedConf pagedConf,
			StandarTableUiToolbarConf toolbarConf, Component component,
			Class classModel, SessionVar sessionVar,
			StandarTableUiFilteringSet filteringSet, Window win) {

		if (classModel == Zona.class) {

			return new StandardTableUi<Zona>(pagedConf, toolbarConf, win,
					sessionVar, classModel, filteringSet);

		} else if (classModel == Pais.class) {

			return new StandardTableUi<Pais>(pagedConf, toolbarConf, win,
					sessionVar, classModel, filteringSet);

		} else if (classModel == TipoCliente.class) {

			return new StandardTableUi<TipoCliente>(pagedConf, toolbarConf,
					win, sessionVar, classModel, filteringSet);

		} else if (classModel == TipoDocumentoAFIP.class) {

			return new StandardTableUi<TipoDocumentoAFIP>(pagedConf,
					toolbarConf, win, sessionVar, classModel, filteringSet);

		} else if (classModel == TipoCbteAFIP.class) {

			return new StandardTableUi<TipoCbteAFIP>(pagedConf, toolbarConf,
					win, sessionVar, classModel, filteringSet);

		} else if (classModel == MotivoComentario.class) {

			return new StandardTableUi<MotivoComentario>(pagedConf,
					toolbarConf, win, sessionVar, classModel, filteringSet);

		} else if (classModel == MotivoNotaDeCredito.class) {

			return new StandardTableUi<MotivoNotaDeCredito>(pagedConf,
					toolbarConf, win, sessionVar, classModel, filteringSet);

		} else if (classModel == CodigoConvenioMultilateral.class) {

			return new StandardTableUi<CodigoConvenioMultilateral>(pagedConf,
					toolbarConf, win, sessionVar, classModel, filteringSet);

		} else if (classModel == EjercicioContable.class) {

			return new EjercicioContableTableUi(pagedConf, toolbarConf, win,
					sessionVar, classModel, filteringSet);

		} else if (classModel == CentroDeCostoProyecto.class) {

			return new CentroDeCostoProyectoTableUi(win, sessionVar);

		} else if (classModel == CentroDeCostoContable.class) {

			return new CentroDeCostoContableTableUi(pagedConf, toolbarConf,
					win, sessionVar, classModel, filteringSet);

		} else if (classModel == PuntoDeEquilibrio.class) {

			return new PuntoDeEquilibrioTableUi(pagedConf, toolbarConf, win,
					sessionVar, classModel, filteringSet);

		} else if (classModel == Provincia.class) {

			return new ProvinciaTableUi(pagedConf, toolbarConf, win,
					sessionVar, classModel, filteringSet);

		} else if (classModel == Ciudad.class) {

			return new CiudadTableUi(pagedConf, toolbarConf, win, sessionVar,
					classModel, filteringSet);

		} else if (classModel == Sucursal.class) {

			return new StandardTableUi<Sucursal>(pagedConf, toolbarConf, win,
					sessionVar, classModel, filteringSet);

		} else if (classModel == SeguridadPuerta.class) {

			return new SeguridadPuertaTableUi(pagedConf, toolbarConf, win,
					sessionVar, classModel, filteringSet);

		} else if (classModel == BancoFirmante.class) {

			return new StandardTableUi<BancoFirmante>(pagedConf, toolbarConf,
					win, sessionVar, classModel, filteringSet);

		} else if (classModel == Banco.class) {

			return new StandardTableUi<Banco>(pagedConf, toolbarConf, win,
					sessionVar, classModel, filteringSet);

		} else if (classModel == Caja.class) {

			return new StandardTableUi<Caja>(pagedConf, toolbarConf, win,
					sessionVar, classModel, filteringSet);

		} else if (classModel == MonedaAFIP.class) {

			return new StandardTableUi<MonedaAFIP>(pagedConf, toolbarConf, win,
					sessionVar, classModel, filteringSet);

		} else if (classModel == Moneda.class) {

			return new StandardTableUi<Moneda>(pagedConf, toolbarConf, win,
					sessionVar, Moneda.class, filteringSet);

		} else if (classModel == MonedaCotizacion.class) {

			return new MonedaCotizacionTableUi(pagedConf, toolbarConf, win,
					sessionVar, classModel, filteringSet);

		} else if (classModel == AsientoModeloItem.class) {

			return new AsientoModeloItemTableUi(pagedConf, toolbarConf, win,
					sessionVar, classModel, filteringSet);

		} else if (classModel == CuentaContable.class) {

			return new CuentaContableTableUi(pagedConf, toolbarConf, win,
					sessionVar, classModel, filteringSet);

		} else if (classModel == Asiento.class) {

			return new AsientoTableUi(pagedConf, toolbarConf, win, sessionVar,
					classModel, filteringSet);
		}
		// ==============================================================
		else if (classModel == Talonario.class) {

			return new StandardTableUi<Talonario>(pagedConf, toolbarConf, win,
					sessionVar, classModel, filteringSet);

		} else if (classModel == Deposito.class) {

			return new StandardTableUi<Deposito>(pagedConf, toolbarConf, win,
					sessionVar, classModel, filteringSet);

		} else if (classModel == TipoCbteControl.class) {

			return new StandardTableUi<TipoCbteControl>(pagedConf, toolbarConf,
					win, sessionVar, classModel, filteringSet);

		} else if (classModel == CuentaDeFondo.class) {

			return new CuentaDeFondoTableUi(win, sessionVar);

		} else if (classModel == CuentaDeFondoA.class) {

			return new StandardTableUi<CuentaDeFondoA>(pagedConf, toolbarConf,
					win, sessionVar, classModel, filteringSet);

		} else if (classModel == JurisdiccionConvenioMultilateral.class) {

			return new StandardTableUi<JurisdiccionConvenioMultilateral>(
					pagedConf, toolbarConf, win, sessionVar,
					JurisdiccionConvenioMultilateral.class, filteringSet);

		} else if (classModel == Ticket.class) {

			return new StandardTableUi<Ticket>(pagedConf, toolbarConf, win,
					sessionVar, classModel, filteringSet);

		} else if (classModel == Chequera.class) {

			return new ChequeraTableUi(win, sessionVar);

		}
		return null;

	}

}
