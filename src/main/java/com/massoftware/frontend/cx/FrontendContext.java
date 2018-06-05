package com.massoftware.frontend.cx;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.StandardTableUi;
import com.massoftware.frontend.ui.windows.centro_de_costo_contable.CentroDeCostoContableTableUi;
import com.massoftware.frontend.ui.windows.centro_de_costo_proyecto.CentroDeCostoProyectoTableUi;
import com.massoftware.frontend.ui.windows.chequera.ChequeraTableUi;
import com.massoftware.frontend.ui.windows.cuenta_contable.CuentaContableTableUi;
import com.massoftware.frontend.ui.windows.cuenta_de_fondo.CuentaDeFondoTableUi;
import com.massoftware.frontend.ui.windows.seguridad.SeguridadPuertaTableUi;
import com.massoftware.model.Banco;
import com.massoftware.model.BancoFirmante;
import com.massoftware.model.Caja;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CentroDeCostoProyecto;
import com.massoftware.model.Chequera;
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
	public static void openWindows(boolean shortcut, boolean agregar,
			boolean modificar, boolean copiar, boolean eliminar,
			Component component, Class classModel, BackendContext cx,
			Usuario usuario, String pidFiltering, Object searchFilter,
			Property searchProperty) {
		try {
			Window win = new Window();

			if (classModel == Zona.class) {

				new StandardTableUi<Zona>(shortcut, agregar, modificar, copiar,
						eliminar, win, cx, usuario, Zona.class, pidFiltering,
						searchFilter, searchProperty);

			} else if (classModel == Pais.class) {

				new StandardTableUi<Pais>(shortcut, agregar, modificar, copiar,
						eliminar, win, cx, usuario, Pais.class, pidFiltering,
						searchFilter, searchProperty);

			} else if (classModel == TipoCliente.class) {

				new StandardTableUi<TipoCliente>(shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario, TipoCliente.class,
						pidFiltering, searchFilter, searchProperty);

			} else if (classModel == TipoDocumentoAFIP.class) {

				new StandardTableUi<TipoDocumentoAFIP>(shortcut, agregar,
						modificar, copiar, eliminar, win, cx, usuario,
						TipoDocumentoAFIP.class, pidFiltering, searchFilter,
						searchProperty);

			} else if (classModel == TipoCbteAFIP.class) {

				new StandardTableUi<TipoCbteAFIP>(shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario, TipoCbteAFIP.class,
						pidFiltering, searchFilter, searchProperty);

			} else if (classModel == MotivoComentario.class) {

				new StandardTableUi<MotivoComentario>(shortcut, agregar,
						modificar, copiar, eliminar, win, cx, usuario,
						MotivoComentario.class, pidFiltering, searchFilter,
						searchProperty);

			} else if (classModel == MotivoNotaDeCredito.class) {

				new StandardTableUi<MotivoNotaDeCredito>(shortcut, agregar,
						modificar, copiar, eliminar, win, cx, usuario,
						MotivoNotaDeCredito.class, pidFiltering, searchFilter,
						searchProperty);

			} else if (classModel == CodigoConvenioMultilateral.class) {

				new StandardTableUi<CodigoConvenioMultilateral>(shortcut,
						agregar, modificar, copiar, eliminar, win, cx, usuario,
						CodigoConvenioMultilateral.class, pidFiltering,
						searchFilter, searchProperty);

			} else if (classModel == EjercicioContable.class) {

				new StandardTableUi<EjercicioContable>(shortcut,
						agregar, modificar, copiar, eliminar, win, cx, usuario,
						EjercicioContable.class, pidFiltering,
						searchFilter, searchProperty);

			} else if (classModel == CentroDeCostoProyecto.class) {

				new CentroDeCostoProyectoTableUi(shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario,
						CentroDeCostoProyecto.class, pidFiltering,
						searchFilter, searchProperty);

			}else if (classModel == CentroDeCostoContable.class) {
				
//				new StandardTableUi<CentroDeCostoContable>(shortcut,
//						agregar, modificar, copiar, eliminar, win, cx, usuario,
//						CentroDeCostoContable.class, pidFiltering,
//						searchFilter, searchProperty);

				new CentroDeCostoContableTableUi(shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario,
						CentroDeCostoContable.class, pidFiltering,
						searchFilter, searchProperty);

			}
			
			
			

			else if (classModel == SeguridadPuerta.class) {

				new SeguridadPuertaTableUi(shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario,
						SeguridadPuerta.class, pidFiltering, searchFilter,
						searchProperty);

			} else if (classModel == Sucursal.class) {

				new StandardTableUi<Sucursal>(shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario, Sucursal.class,
						pidFiltering, searchFilter, searchProperty);

			} else if (classModel == Talonario.class) {

				new StandardTableUi<Talonario>(shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario, Talonario.class,
						pidFiltering, searchFilter, searchProperty);

			} else if (classModel == Deposito.class) {

				new StandardTableUi<Deposito>(shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario, Deposito.class,
						pidFiltering, searchFilter, searchProperty);

			} else if (classModel == Banco.class) {

				new StandardTableUi<Banco>(shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario, Banco.class,
						pidFiltering, searchFilter, searchProperty);

			} else if (classModel == TipoCbteControl.class) {

				new StandardTableUi<TipoCbteControl>(shortcut, agregar,
						modificar, copiar, eliminar, win, cx, usuario,
						TipoCbteControl.class, pidFiltering, searchFilter,
						searchProperty);

			} else if (classModel == MonedaAFIP.class) {

				new StandardTableUi<MonedaAFIP>(shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario, MonedaAFIP.class,
						pidFiltering, searchFilter, searchProperty);

			} else if (classModel == Moneda.class) {

				new StandardTableUi<Moneda>(shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario, Moneda.class,
						pidFiltering, searchFilter, searchProperty);

			} else if (classModel == MonedaCotizacion.class) {

				new StandardTableUi<MonedaCotizacion>(shortcut, agregar,
						modificar, copiar, eliminar, win, cx, usuario,
						MonedaCotizacion.class, pidFiltering, searchFilter,
						searchProperty);

			} else if (classModel == BancoFirmante.class) {

				new StandardTableUi<BancoFirmante>(shortcut, agregar,
						modificar, copiar, eliminar, win, cx, usuario,
						BancoFirmante.class, pidFiltering, searchFilter,
						searchProperty);

			} else if (classModel == Caja.class) {

				new StandardTableUi<Caja>(shortcut, agregar, modificar, copiar,
						eliminar, win, cx, usuario, Caja.class, pidFiltering,
						searchFilter, searchProperty);

			} else if (classModel == CuentaDeFondo.class) {

				new CuentaDeFondoTableUi(shortcut, agregar, modificar, copiar,
						eliminar, win, cx, usuario, CuentaDeFondoA.class,
						pidFiltering, searchFilter, searchProperty);

			} else if (classModel == CuentaDeFondoA.class) {

				new StandardTableUi<CuentaDeFondoA>(shortcut, agregar,
						modificar, copiar, eliminar, win, cx, usuario,
						CuentaDeFondoA.class, pidFiltering, searchFilter,
						searchProperty);

			} else if (classModel == CuentaContable.class) {

				new CuentaContableTableUi(win, cx, usuario, pidFiltering,
						searchFilter, searchProperty);

			} else if (classModel == JurisdiccionConvenioMultilateral.class) {

				new StandardTableUi<JurisdiccionConvenioMultilateral>(shortcut,
						agregar, modificar, copiar, eliminar, win, cx, usuario,
						JurisdiccionConvenioMultilateral.class, pidFiltering,
						searchFilter, searchProperty);

			} else if (classModel == Ticket.class) {

				new StandardTableUi<Ticket>(shortcut, agregar, modificar,
						copiar, eliminar, win, cx, usuario, Ticket.class,
						pidFiltering, searchFilter, searchProperty);

			} else if (classModel == Chequera.class) {

				new ChequeraTableUi(shortcut, agregar, modificar, copiar,
						eliminar, win, cx, usuario, Chequera.class,
						pidFiltering, searchFilter, searchProperty);

			}

			component.getUI().addWindow(win);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

}
