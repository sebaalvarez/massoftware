package com.massoftware.frontend;

import java.util.List;

import z.old.deprecated.CentroDeCostoProyectoTableUi;
import z.old.deprecated.ChequeraTableUi;
import z.old.deprecated.CuentaDeFondoTableUi;

import com.massoftware.backend.BackendContext;
import com.massoftware.frontend.custom.windows.AsientoModeloItemTableUi;
import com.massoftware.frontend.custom.windows.AsientoTableUi;
import com.massoftware.frontend.custom.windows.CentroDeCostoContableTableUi;
import com.massoftware.frontend.custom.windows.CiudadTableUi;
import com.massoftware.frontend.custom.windows.CuentaContableTableUi;
import com.massoftware.frontend.custom.windows.EjercicioContableTableUi;
import com.massoftware.frontend.custom.windows.MonedaCotizacionTableUi;
import com.massoftware.frontend.custom.windows.ProvinciaTableUi;
import com.massoftware.frontend.custom.windows.PuntoDeEquilibrioTableUi;
import com.massoftware.frontend.custom.windows.SeguridadPuertaTableUi;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.frontend.util.window.StandardTableUi;
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
import com.massoftware.model.Usuario;
import com.massoftware.model.Zona;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public class FrontendContext {

	@SuppressWarnings("rawtypes")
	public static StandardTableUi openWindows(boolean paged,
			boolean pagedCount, boolean pagedOrder, boolean shortcut,
			boolean agregar, boolean modificar, boolean copiar,
			boolean eliminar, Component component, Class classModel,
			BackendContext cx, Usuario usuario, String pidFiltering,
			Object valueFilter, Property searchProperty,
			List<Object> otrosFiltros) {
		try {
			Window win = new Window();
			StandardTableUi standardTableUi = null;

			if (classModel == Zona.class) {

				standardTableUi = new StandardTableUi<Zona>(paged, pagedCount,
						pagedOrder, shortcut, agregar, modificar, copiar,
						eliminar, win, cx, usuario, Zona.class, pidFiltering,
						valueFilter, searchProperty, otrosFiltros);

			} else if (classModel == Pais.class) {

				standardTableUi = new StandardTableUi<Pais>(paged, pagedCount,
						pagedOrder, shortcut, agregar, modificar, copiar,
						eliminar, win, cx, usuario, Pais.class, pidFiltering,
						valueFilter, searchProperty, otrosFiltros);

			} else if (classModel == TipoCliente.class) {

				standardTableUi = new StandardTableUi<TipoCliente>(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario, TipoCliente.class,
						pidFiltering, valueFilter, searchProperty, otrosFiltros);

			} else if (classModel == TipoDocumentoAFIP.class) {

				standardTableUi = new StandardTableUi<TipoDocumentoAFIP>(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario,
						TipoDocumentoAFIP.class, pidFiltering, valueFilter,
						searchProperty, otrosFiltros);

			} else if (classModel == TipoCbteAFIP.class) {

				standardTableUi = new StandardTableUi<TipoCbteAFIP>(shortcut,
						paged, pagedCount, pagedOrder, agregar, modificar,
						copiar, eliminar, win, cx, usuario, TipoCbteAFIP.class,
						pidFiltering, valueFilter, searchProperty, otrosFiltros);

			} else if (classModel == MotivoComentario.class) {

				standardTableUi = new StandardTableUi<MotivoComentario>(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario,
						MotivoComentario.class, pidFiltering, valueFilter,
						searchProperty, otrosFiltros);

			} else if (classModel == MotivoNotaDeCredito.class) {

				standardTableUi = new StandardTableUi<MotivoNotaDeCredito>(
						paged, pagedCount, pagedOrder, shortcut, agregar,
						modificar, copiar, eliminar, win, cx, usuario,
						MotivoNotaDeCredito.class, pidFiltering, valueFilter,
						searchProperty, otrosFiltros);

			} else if (classModel == CodigoConvenioMultilateral.class) {

				standardTableUi = new StandardTableUi<CodigoConvenioMultilateral>(
						paged, pagedCount, pagedOrder, shortcut, agregar,
						modificar, copiar, eliminar, win, cx, usuario,
						CodigoConvenioMultilateral.class, pidFiltering,
						valueFilter, searchProperty, otrosFiltros);

			} else if (classModel == EjercicioContable.class) {

				// new StandardTableUi<EjercicioContable>(shortcut,
				// agregar, modificar, copiar, eliminar, win, cx, usuario,
				// EjercicioContable.class, pidFiltering,
				// searchFilter, searchProperty);

				standardTableUi = new EjercicioContableTableUi(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario,
						EjercicioContable.class, pidFiltering, valueFilter,
						searchProperty);

			} else if (classModel == CentroDeCostoProyecto.class) {

				standardTableUi = new CentroDeCostoProyectoTableUi(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario,
						CentroDeCostoProyecto.class, pidFiltering, valueFilter,
						searchProperty);

			} else if (classModel == CentroDeCostoContable.class) {

				standardTableUi = new CentroDeCostoContableTableUi(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario,
						CentroDeCostoContable.class, pidFiltering, valueFilter,
						searchProperty);

			} else if (classModel == PuntoDeEquilibrio.class) {

				standardTableUi = new PuntoDeEquilibrioTableUi(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario,
						PuntoDeEquilibrio.class, pidFiltering, valueFilter,
						searchProperty);

			} else if (classModel == Provincia.class) {

				standardTableUi = new ProvinciaTableUi(paged, pagedCount,
						pagedOrder, shortcut, agregar, modificar, copiar,
						eliminar, win, cx, usuario, Provincia.class,
						pidFiltering, valueFilter, searchProperty);

			} else if (classModel == Ciudad.class) {

				standardTableUi = new CiudadTableUi(paged, pagedCount,
						pagedOrder, shortcut, agregar, modificar, copiar,
						eliminar, win, cx, usuario, Ciudad.class, pidFiltering,
						valueFilter, searchProperty);

			} else if (classModel == Sucursal.class) {

				standardTableUi = new StandardTableUi<Sucursal>(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario, Sucursal.class,
						pidFiltering, valueFilter, searchProperty, otrosFiltros);

			} else if (classModel == SeguridadPuerta.class) {

				standardTableUi = new SeguridadPuertaTableUi(paged, pagedCount,
						pagedOrder, shortcut, agregar, modificar, copiar,
						eliminar, win, cx, usuario, SeguridadPuerta.class,
						pidFiltering, valueFilter, searchProperty);

			} else if (classModel == BancoFirmante.class) {

				standardTableUi = new StandardTableUi<BancoFirmante>(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario,
						BancoFirmante.class, pidFiltering, valueFilter,
						searchProperty, otrosFiltros);

			} else if (classModel == Banco.class) {

				standardTableUi = new StandardTableUi<Banco>(paged, pagedCount,
						pagedOrder, shortcut, agregar, modificar, copiar,
						eliminar, win, cx, usuario, Banco.class, pidFiltering,
						valueFilter, searchProperty, otrosFiltros);

			} else if (classModel == Caja.class) {

				standardTableUi = new StandardTableUi<Caja>(paged, pagedCount,
						pagedOrder, shortcut, agregar, modificar, copiar,
						eliminar, win, cx, usuario, Caja.class, pidFiltering,
						valueFilter, searchProperty, otrosFiltros);

			} else if (classModel == MonedaAFIP.class) {

				standardTableUi = new StandardTableUi<MonedaAFIP>(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario, MonedaAFIP.class,
						pidFiltering, valueFilter, searchProperty, otrosFiltros);

			} else if (classModel == Moneda.class) {

				standardTableUi = new StandardTableUi<Moneda>(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario, Moneda.class,
						pidFiltering, valueFilter, searchProperty, otrosFiltros);

			} else if (classModel == MonedaCotizacion.class) {

				standardTableUi = new MonedaCotizacionTableUi(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario,
						MonedaCotizacion.class, pidFiltering, valueFilter,
						searchProperty);

			} else if (classModel == AsientoModeloItem.class) {

				// new StandardTableUi<AsientoModeloItem>(shortcut, agregar,
				// modificar,
				// copiar, eliminar, win, cx, usuario, AsientoModeloItem.class,
				// pidFiltering, searchFilter, searchProperty);

				standardTableUi = new AsientoModeloItemTableUi(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario,
						AsientoModeloItem.class, pidFiltering, valueFilter,
						searchProperty);

			} else if (classModel == CuentaContable.class) {

				// new StandardTableUi<CuentaContable>(shortcut, agregar,
				// modificar,
				// copiar, eliminar, win, cx, usuario, CuentaContable.class,
				// pidFiltering, searchFilter, searchProperty);

				standardTableUi = new CuentaContableTableUi(paged, pagedCount,
						pagedOrder, shortcut, agregar, modificar, copiar,
						eliminar, win, cx, usuario, CuentaContable.class,
						pidFiltering, valueFilter, searchProperty, otrosFiltros);

				// new CuentaContableTableUi(win, cx, usuario, pidFiltering,
				// searchFilter, searchProperty);

			} else if (classModel == Asiento.class) {

				paged = true;
				pagedCount = false;
				pagedOrder = true;

				standardTableUi = new AsientoTableUi(paged, pagedCount,
						pagedOrder, shortcut, agregar, modificar, copiar,
						eliminar, win, cx, usuario,
						/* CentroDeCostoContable.class, */pidFiltering,
						valueFilter, searchProperty);
			}

			else if (classModel == Talonario.class) {

				standardTableUi = new StandardTableUi<Talonario>(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario, Talonario.class,
						pidFiltering, valueFilter, searchProperty, otrosFiltros);

			} else if (classModel == Deposito.class) {

				standardTableUi = new StandardTableUi<Deposito>(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario, Deposito.class,
						pidFiltering, valueFilter, searchProperty, otrosFiltros);

			} else if (classModel == TipoCbteControl.class) {

				standardTableUi = new StandardTableUi<TipoCbteControl>(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario,
						TipoCbteControl.class, pidFiltering, valueFilter,
						searchProperty, otrosFiltros);

			} else if (classModel == CuentaDeFondo.class) {

				standardTableUi = new CuentaDeFondoTableUi(paged, pagedCount,
						pagedOrder, shortcut, agregar, modificar, copiar,
						eliminar, win, cx, usuario, CuentaDeFondoA.class,
						pidFiltering, valueFilter, searchProperty);

			} else if (classModel == CuentaDeFondoA.class) {

				standardTableUi = new StandardTableUi<CuentaDeFondoA>(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario,
						CuentaDeFondoA.class, pidFiltering, valueFilter,
						searchProperty, otrosFiltros);

			} else if (classModel == JurisdiccionConvenioMultilateral.class) {

				standardTableUi = new StandardTableUi<JurisdiccionConvenioMultilateral>(
						paged, pagedCount, pagedOrder, shortcut, agregar,
						modificar, copiar, eliminar, win, cx, usuario,
						JurisdiccionConvenioMultilateral.class, pidFiltering,
						valueFilter, searchProperty, otrosFiltros);

			} else if (classModel == Ticket.class) {

				standardTableUi = new StandardTableUi<Ticket>(paged,
						pagedCount, pagedOrder, shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario, Ticket.class,
						pidFiltering, valueFilter, searchProperty, otrosFiltros);

			} else if (classModel == Chequera.class) {

				standardTableUi = new ChequeraTableUi(paged, pagedCount,
						pagedOrder, shortcut, agregar, modificar, copiar,
						eliminar, win, cx, usuario, Chequera.class,
						pidFiltering, valueFilter, searchProperty);

			}

			component.getUI().addWindow(win);

			standardTableUi.filtrar();

			return standardTableUi;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
		return null;
	}

}
