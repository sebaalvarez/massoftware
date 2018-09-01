package com.massoftware.frontend.custom.windows;

import java.util.List;

import z.old.deprecated.CentroDeCostoProyectoTableUi;
import z.old.deprecated.ChequeraTableUi;
import z.old.deprecated.CuentaDeFondoTableUi;

import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.custom.menu.AbstractMenu;
import com.massoftware.frontend.util.LogAndNotification;
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
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public class WindowsFactory {

	// ABRE LA VENTANA EN MODO ABM
	@SuppressWarnings("rawtypes")
	public static StandardTableUi openWindowFromMenu(AbstractMenu component,
			Class classModel, SessionVar sessionVar) {

		StandarTableUiPagedConf pagedConf = new StandarTableUiPagedConf();

		pagedConf.setPaged(false);
		pagedConf.setPagedCount(false);
		pagedConf.setPagedOrder(false);

		return openWindow(pagedConf, true, true, true, true, true, component,
				classModel, sessionVar, null, null, null, null);
	}

	// ABRE LA VENTANA EN MODO SELECCION
	@SuppressWarnings("rawtypes")
	public static StandardTableUi openWindowFromForm(StandardFormUi component,
			Class classModel, SessionVar sessionVar, String pidFiltering,
			Object valueFilter, Property searchProperty,
			List<Object> otrosFiltros) {

		StandarTableUiPagedConf pagedConf = new StandarTableUiPagedConf();

		pagedConf.setPaged(false);
		pagedConf.setPagedCount(false);
		pagedConf.setPagedOrder(false);

		return openWindow(pagedConf, true, true, true, true, true, component,
				classModel, sessionVar, pidFiltering, valueFilter,
				searchProperty, otrosFiltros);

	}

	@SuppressWarnings("rawtypes")
	private static StandardTableUi openWindow(
			StandarTableUiPagedConf pagedConf, boolean shortcut,
			boolean agregar, boolean modificar, boolean copiar,
			boolean eliminar, Component component, Class classModel,
			SessionVar sessionVar, String pidFiltering, Object valueFilter,
			Property searchProperty, List<Object> otrosFiltros) {
		try {

			Window win = new Window();

			StandardTableUi standardTableUi = buildTable(pagedConf, shortcut,
					agregar, modificar, copiar, eliminar, component,
					classModel, sessionVar, pidFiltering, valueFilter,
					searchProperty, otrosFiltros, win);

			component.getUI().addWindow(win);

			standardTableUi.filtrar();

			return standardTableUi;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private static StandardTableUi buildTable(
			StandarTableUiPagedConf pagedConf, boolean shortcut,
			boolean agregar, boolean modificar, boolean copiar,
			boolean eliminar, Component component, Class classModel,
			SessionVar sessionVar, String pidFiltering, Object valueFilter,
			Property searchProperty, List<Object> otrosFiltros, Window win) {

		if (classModel == Zona.class) {

			return new StandardTableUi<Zona>(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar, Zona.class,
					pidFiltering, valueFilter, searchProperty, otrosFiltros);

		} else if (classModel == Pais.class) {

			return new StandardTableUi<Pais>(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar, Pais.class,
					pidFiltering, valueFilter, searchProperty, otrosFiltros);

		} else if (classModel == TipoCliente.class) {

			return new StandardTableUi<TipoCliente>(pagedConf, shortcut,
					agregar, modificar, copiar, eliminar, win, sessionVar,
					TipoCliente.class, pidFiltering, valueFilter,
					searchProperty, otrosFiltros);

		} else if (classModel == TipoDocumentoAFIP.class) {

			return new StandardTableUi<TipoDocumentoAFIP>(pagedConf, shortcut,
					agregar, modificar, copiar, eliminar, win, sessionVar,
					TipoDocumentoAFIP.class, pidFiltering, valueFilter,
					searchProperty, otrosFiltros);

		} else if (classModel == TipoCbteAFIP.class) {

			return new StandardTableUi<TipoCbteAFIP>(pagedConf, shortcut,
					agregar, modificar, copiar, eliminar, win, sessionVar,
					TipoCbteAFIP.class, pidFiltering, valueFilter,
					searchProperty, otrosFiltros);

		} else if (classModel == MotivoComentario.class) {

			return new StandardTableUi<MotivoComentario>(pagedConf, shortcut,
					agregar, modificar, copiar, eliminar, win, sessionVar,
					MotivoComentario.class, pidFiltering, valueFilter,
					searchProperty, otrosFiltros);

		} else if (classModel == MotivoNotaDeCredito.class) {

			return new StandardTableUi<MotivoNotaDeCredito>(pagedConf,
					shortcut, agregar, modificar, copiar, eliminar, win,
					sessionVar, MotivoNotaDeCredito.class, pidFiltering,
					valueFilter, searchProperty, otrosFiltros);

		} else if (classModel == CodigoConvenioMultilateral.class) {

			return new StandardTableUi<CodigoConvenioMultilateral>(pagedConf,
					shortcut, agregar, modificar, copiar, eliminar, win,
					sessionVar, CodigoConvenioMultilateral.class, pidFiltering,
					valueFilter, searchProperty, otrosFiltros);

		} else if (classModel == EjercicioContable.class) {

			// new StandardTableUi<EjercicioContable>(shortcut,
			// agregar, modificar, copiar, eliminar, win, cx, usuario,
			// EjercicioContable.class, pidFiltering,
			// searchFilter, searchProperty);

			return new EjercicioContableTableUi(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar,
					EjercicioContable.class, pidFiltering, valueFilter,
					searchProperty);

		} else if (classModel == CentroDeCostoProyecto.class) {

			return new CentroDeCostoProyectoTableUi(pagedConf, shortcut,
					agregar, modificar, copiar, eliminar, win, sessionVar,
					CentroDeCostoProyecto.class, pidFiltering, valueFilter,
					searchProperty);

		} else if (classModel == CentroDeCostoContable.class) {

			return new CentroDeCostoContableTableUi(pagedConf, shortcut,
					agregar, modificar, copiar, eliminar, win, sessionVar,
					CentroDeCostoContable.class, pidFiltering, valueFilter,
					searchProperty);

		} else if (classModel == PuntoDeEquilibrio.class) {

			return new PuntoDeEquilibrioTableUi(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar,
					PuntoDeEquilibrio.class, pidFiltering, valueFilter,
					searchProperty);

		} else if (classModel == Provincia.class) {

			return new ProvinciaTableUi(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar,
					Provincia.class, pidFiltering, valueFilter, searchProperty);

		} else if (classModel == Ciudad.class) {

			return new CiudadTableUi(pagedConf, shortcut, agregar, modificar,
					copiar, eliminar, win, sessionVar, Ciudad.class,
					pidFiltering, valueFilter, searchProperty);

		} else if (classModel == Sucursal.class) {

			return new StandardTableUi<Sucursal>(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar,
					Sucursal.class, pidFiltering, valueFilter, searchProperty,
					otrosFiltros);

		} else if (classModel == SeguridadPuerta.class) {

			return new SeguridadPuertaTableUi(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar,
					SeguridadPuerta.class, pidFiltering, valueFilter,
					searchProperty);

		} else if (classModel == BancoFirmante.class) {

			return new StandardTableUi<BancoFirmante>(pagedConf, shortcut,
					agregar, modificar, copiar, eliminar, win, sessionVar,
					BancoFirmante.class, pidFiltering, valueFilter,
					searchProperty, otrosFiltros);

		} else if (classModel == Banco.class) {

			return new StandardTableUi<Banco>(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar, Banco.class,
					pidFiltering, valueFilter, searchProperty, otrosFiltros);

		} else if (classModel == Caja.class) {

			return new StandardTableUi<Caja>(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar, Caja.class,
					pidFiltering, valueFilter, searchProperty, otrosFiltros);

		} else if (classModel == MonedaAFIP.class) {

			return new StandardTableUi<MonedaAFIP>(pagedConf, shortcut,
					agregar, modificar, copiar, eliminar, win, sessionVar,
					MonedaAFIP.class, pidFiltering, valueFilter,
					searchProperty, otrosFiltros);

		} else if (classModel == Moneda.class) {

			return new StandardTableUi<Moneda>(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar, Moneda.class,
					pidFiltering, valueFilter, searchProperty, otrosFiltros);

		} else if (classModel == MonedaCotizacion.class) {

			return new MonedaCotizacionTableUi(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar,
					MonedaCotizacion.class, pidFiltering, valueFilter,
					searchProperty);

		} else if (classModel == AsientoModeloItem.class) {

			// new StandardTableUi<AsientoModeloItem>(shortcut, agregar,
			// modificar,
			// copiar, eliminar, win, cx, usuario, AsientoModeloItem.class,
			// pidFiltering, searchFilter, searchProperty);

			return new AsientoModeloItemTableUi(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar,
					AsientoModeloItem.class, pidFiltering, valueFilter,
					searchProperty);

		} else if (classModel == CuentaContable.class) {

			// new StandardTableUi<CuentaContable>(shortcut, agregar,
			// modificar,
			// copiar, eliminar, win, cx, usuario, CuentaContable.class,
			// pidFiltering, searchFilter, searchProperty);

			return new CuentaContableTableUi(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar,
					CuentaContable.class, pidFiltering, valueFilter,
					searchProperty, otrosFiltros);

			// new CuentaContableTableUi(win, cx, usuario, pidFiltering,
			// searchFilter, searchProperty);

		} else if (classModel == Asiento.class) {

			return new AsientoTableUi(sessionVar, win);
		}

		else if (classModel == Talonario.class) {

			return new StandardTableUi<Talonario>(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar,
					Talonario.class, pidFiltering, valueFilter, searchProperty,
					otrosFiltros);

		} else if (classModel == Deposito.class) {

			return new StandardTableUi<Deposito>(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar,
					Deposito.class, pidFiltering, valueFilter, searchProperty,
					otrosFiltros);

		} else if (classModel == TipoCbteControl.class) {

			return new StandardTableUi<TipoCbteControl>(pagedConf, shortcut,
					agregar, modificar, copiar, eliminar, win, sessionVar,
					TipoCbteControl.class, pidFiltering, valueFilter,
					searchProperty, otrosFiltros);

		} else if (classModel == CuentaDeFondo.class) {

			return new CuentaDeFondoTableUi(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar,
					CuentaDeFondoA.class, pidFiltering, valueFilter,
					searchProperty);

		} else if (classModel == CuentaDeFondoA.class) {

			return new StandardTableUi<CuentaDeFondoA>(pagedConf, shortcut,
					agregar, modificar, copiar, eliminar, win, sessionVar,
					CuentaDeFondoA.class, pidFiltering, valueFilter,
					searchProperty, otrosFiltros);

		} else if (classModel == JurisdiccionConvenioMultilateral.class) {

			return new StandardTableUi<JurisdiccionConvenioMultilateral>(
					pagedConf, shortcut, agregar, modificar, copiar, eliminar,
					win, sessionVar, JurisdiccionConvenioMultilateral.class,
					pidFiltering, valueFilter, searchProperty, otrosFiltros);

		} else if (classModel == Ticket.class) {

			return new StandardTableUi<Ticket>(pagedConf, shortcut, agregar,
					modificar, copiar, eliminar, win, sessionVar, Ticket.class,
					pidFiltering, valueFilter, searchProperty, otrosFiltros);

		} else if (classModel == Chequera.class) {

			return new ChequeraTableUi(pagedConf, shortcut,
					agregar, modificar, copiar, eliminar, win, sessionVar,
					Chequera.class, pidFiltering, valueFilter, searchProperty);

		}
		return null;

	}

}
