package com.massoftware.frontend.custom.windows;

import java.util.List;

import org.cendra.jdbc.ex.crud.InsertDuplicateException;
import org.cendra.jdbc.ex.crud.UniqueException;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.bo.CentroDeCostoContableBO;
import com.massoftware.backend.bo.PuntoDeEquilibrioBO;
import com.massoftware.frontend.custom.validator.CuentaContableCodigoPadreUniqueValidator;
import com.massoftware.frontend.custom.validator.CuentaContableCodigoUniqueValidator;
import com.massoftware.frontend.custom.validator.CuentaContableCuentaContableUniqueValidator;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.frontend.util.window.StandardFormUi;
import com.massoftware.frontend.util.window.StandardTableUi;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CostoDeVenta;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaContableEstado;
import com.massoftware.model.CuentaContableFull;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class CuentaContableFullFormUi extends StandardFormUi<CuentaContableFull> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8926034680178527276L;

	private TabSheet pestaniasTBS;
	private VerticalLayout pestaniaCCVL;
	
	private TextField codigoCuentaPadreTXT;
	private TextField codigoCuentaTXT;
	private TextField cuentaContableTXT;
	private TextField cuentaAgrupadoraTXT;
	private TextField porcentajeTXT;
	private CheckBox imputableCKB;
	private ComboBox cuentaContableEstadoCBX;
	private ComboBox costoDeVentaCBX;

	private ComboBox filtroEjercicioCBX;
	private BeanItemContainer<EjercicioContable> ejerciciosContablesBIC;

	private ComboBox filtroCentroDeCostoContableCBX;
	protected BeanItemContainer<CentroDeCostoContable> centrosDeCostosContablesBIC;

	private ComboBox filtroPuntoDeEquilibrioCBX;
	protected BeanItemContainer<PuntoDeEquilibrio> puntosDeEquilibrioBIC;

	public CuentaContableFullFormUi(Usuario usuario,/*
			Class<CuentaContableFull> classModel,*/ String mode, BackendContext cx,
			CuentaContableFull object) {
		super(usuario, CuentaContableFull.class, mode, cx, object);
		start();
	}

	@SuppressWarnings("rawtypes")
	public CuentaContableFullFormUi(Usuario usuario,/*
			Class<CuentaContableFull> classModel,*/ String mode, BackendContext cx,
			StandardTableUi tableUi, CuentaContableFull objectClone,
			CuentaContableFull object) {
		super(usuario, CuentaContableFull.class, mode, cx, tableUi, objectClone, object);
		start();
	}

	@SuppressWarnings("rawtypes")
	public CuentaContableFullFormUi(Usuario usuario,/*
			Class<CuentaContableFull> classModel,*/ String mode, BackendContext cx,
			StandardTableUi tableUi, CuentaContableFull objectClone) {
		super(usuario, CuentaContableFull.class, mode, cx, tableUi, objectClone);
		start();
	}

	@SuppressWarnings("rawtypes")
	public CuentaContableFullFormUi(Usuario usuario,/*
			Class<CuentaContableFull> classModel,*/ String mode, BackendContext cx,
			StandardTableUi tableUi) {
		super(usuario, CuentaContableFull.class, mode, cx, tableUi);
		start();
	}

	public CuentaContableFullFormUi(Usuario usuario,/*
			Class<CuentaContableFull> classModel,*/ String mode, BackendContext cx) {
		super(usuario, CuentaContableFull.class, mode, cx);
		start();
	}

	@SuppressWarnings({ "serial", "unchecked" })
	private void start() {

		// ----------------------------------
		
		pestaniasTBS = (TabSheet) getComponentById("pestaniasTBS");				
		pestaniaCCVL = (VerticalLayout) getComponentById("pestaniaCC");

		imputableCKB = (CheckBox) getComponentById("imputable");
		codigoCuentaPadreTXT = (TextField) getComponentById("codigoCuentaPadre");
		codigoCuentaTXT = (TextField) getComponentById("codigoCuenta");
		cuentaContableTXT = (TextField) getComponentById("cuentaContable");
		cuentaAgrupadoraTXT = (TextField) getComponentById("cuentaAgrupadora");
		porcentajeTXT = (TextField) getComponentById("porcentaje");
		filtroEjercicioCBX = (ComboBox) getComponentById("ejercicioContable");
		filtroCentroDeCostoContableCBX = (ComboBox) getComponentById("centroDeCostoContable");
		filtroPuntoDeEquilibrioCBX = (ComboBox) getComponentById("puntoDeEquilibrio");
		cuentaContableEstadoCBX = (ComboBox) getComponentById("cuentaContableEstado");
		costoDeVentaCBX = (ComboBox) getComponentById("costoDeVenta");

		ejerciciosContablesBIC = (BeanItemContainer<EjercicioContable>) filtroEjercicioCBX
				.getContainerDataSource();
		centrosDeCostosContablesBIC = (BeanItemContainer<CentroDeCostoContable>) filtroCentroDeCostoContableCBX
				.getContainerDataSource();
		puntosDeEquilibrioBIC = (BeanItemContainer<PuntoDeEquilibrio>) filtroPuntoDeEquilibrioCBX
				.getContainerDataSource();

		// ----------------------------------

		// codigoCuentaPadreTXT.setColumns((int) (16 * 0.7));

		codigoCuentaPadreTXT.setEnabled(false);
		codigoCuentaPadreTXT.setReadOnly(false);

		// codigoCuentaPadreTXT
		// .addValidator(new StringLengthValidatorInputMask(
		// "El campo "
		// + codigoCuentaPadreTXT.getCaption()
		// +
		// " es requerido. Es decir no debe estar vacio y debe contener 16 caracteres con un formato como el siguiente 9.99.99.99.99.99",
		// 11, 11, false, '_'));

		addValidatorCodigoCuentaPadreTXT();

		// ----------------------------------

		// codigoCuentaTXT.setColumns((int) (16 * 0.7));

		codigoCuentaTXT.addTextChangeListener(new TextChangeListener() {

			public void textChange(TextChangeEvent event) {
				codigoCuentaTXTTextChange(event.getText());
			}
		});

		codigoCuentaTXT
				.addValueChangeListener(new Property.ValueChangeListener() {
					public void valueChange(ValueChangeEvent event) {
						codigoCuentaTXTTextChange((String) event.getProperty()
								.getValue());
					}
				});

		// codigoCuentaTXT
		// .addValidator(new StringLengthValidatorInputMask(
		// "El campo "
		// + codigoCuentaTXT.getCaption()
		// +
		// " es requerido. Es decir no debe estar vacio y debe contener 16 caracteres con un formato como el siguiente 9.99.99.99.99.99",
		// 11, 11, false, '_'));
		
		addValidatorCodigoCuentaTXT();
		
		// ----------------------------------
		
		 addValidatorCuentaContableTXT();

		// ----------------------------------

		if (dtoBI.getBean().getCuentaContableEstado() == null) {

			@SuppressWarnings({ "rawtypes" })
			BeanItemContainer bic = (BeanItemContainer<CuentaContableEstado>) cuentaContableEstadoCBX
					.getContainerDataSource();

			if (bic.size() > 1) {
				cuentaContableEstadoCBX.setValue(bic.getIdByIndex(1));
			}
		}

		// ----------------------------------

		if (dtoBI.getBean().getCostoDeVenta() == null) {

			@SuppressWarnings({ "rawtypes" })
			BeanItemContainer bic = (BeanItemContainer<CostoDeVenta>) costoDeVentaCBX
					.getContainerDataSource();

			if (bic.size() > 0) {
				costoDeVentaCBX.setValue(bic.getIdByIndex(0));
			}
		}

		// ----------------------------------

		ejercicioCBXValueChange();

		filtroEjercicioCBX.addValueChangeListener(e -> {
			ejercicioCBXValueChange();
		});

		// ----------------------------------

		imputableCKBChange();

		imputableCKB.addValueChangeListener(listener -> imputableCKBChange());

		// ----------------------------------

	}

	protected void codigoCuentaTXTTextChange(String value) {
		try {
			if (value == null) {
				return;
			}
			value = value.trim();
			String value2 = null;
			if (value.length() == 1) {
				value2 = "00000000000";
			} else if (value.length() == 2) {
				value2 = value.substring(0, 1) + "0000000000";
			} else if (value.length() == 3) {
				value2 = value.substring(0, 1) + "0000000000";
			} else if (value.length() == 4) {
				value2 = value.substring(0, 3) + "00000000";
			} else if (value.length() == 5) {
				value2 = value.substring(0, 3) + "00000000";
			} else if (value.length() == 6) {
				value2 = value.substring(0, 5) + "000000";
			} else if (value.length() == 7) {
				value2 = value.substring(0, 5) + "000000";
			} else if (value.length() == 8) {
				value2 = value.substring(0, 7) + "0000";
			} else if (value.length() == 9) {
				value2 = value.substring(0, 7) + "0000";
			} else if (value.length() == 10) {
				value2 = value.substring(0, 9) + "00";
			} else if (value.length() == 11) {
				value2 = value.substring(0, 9) + "00";
			} else {
				value2 = null;
			}

			codigoCuentaPadreTXT.setValue(value2);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	private void imputableCKBChange() {
		try {

			
			
			boolean b = dtoBI.getBean().getImputable().booleanValue();

//			pestaniaCCVL.setEnabled(b);
			pestaniasTBS.getTab(pestaniaCCVL).setEnabled(b);
			
			filtroCentroDeCostoContableCBX.setEnabled(b);
			filtroPuntoDeEquilibrioCBX.setEnabled(b);
			costoDeVentaCBX.setEnabled(b);
			cuentaAgrupadoraTXT.setEnabled(b);
			porcentajeTXT.setEnabled(b);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void ejercicioCBXValueChange() {
		try {

			if (ejerciciosContablesBIC.size() > 0) {
				
				filtroCentroDeCostoContableCBX.setValue(null);				
				filtroPuntoDeEquilibrioCBX.setValue(null);
				
				// ----------------------------------

				EjercicioContable ejercicioContable = (EjercicioContable) filtroEjercicioCBX
						.getValue();

				// ----------------------------------

				CentroDeCostoContableBO centroDeCostoContableBO = (CentroDeCostoContableBO) cx
						.buildBO(CentroDeCostoContable.class);

				List<CentroDeCostoContable> centroDeCostosContable = centroDeCostoContableBO
						.findAll(ejercicioContable);
				centrosDeCostosContablesBIC.removeAllItems();
				for (CentroDeCostoContable centroDeCostoContable : centroDeCostosContable) {
					centrosDeCostosContablesBIC.addBean(centroDeCostoContable);
				}

				// if (centrosDeCostosContablesBIC.size() > 0) {
				// filtroCentroDeCostoContableCBX.setValue(null);
				// }

				// ----------------------------------

				PuntoDeEquilibrioBO puntoDeEquilibrioBO = (PuntoDeEquilibrioBO) cx
						.buildBO(PuntoDeEquilibrio.class);

				List<PuntoDeEquilibrio> puntosDeEquilibrio = puntoDeEquilibrioBO
						.findAll(ejercicioContable);
				puntosDeEquilibrioBIC.removeAllItems();
				for (PuntoDeEquilibrio puntoDeEquilibrio : puntosDeEquilibrio) {
					puntosDeEquilibrioBIC.addBean(puntoDeEquilibrio);
				}

				// if (puntosDeEquilibrioBIC.size() > 0) {
				// filtroPuntoDeEquilibrioCBX.setValue(null);
				// }

				// ----------------------------------

				addValidatorCodigoCuentaTXT();
				addValidatorCodigoCuentaPadreTXT();
				addValidatorCuentaContableTXT();

				codigoCuentaTXT.validate();

				// validateAllFields();

			}

		} catch (InvalidValueException e) {
			// LogAndNotification.print(e);
		} catch (InsertDuplicateException e) {
			// LogAndNotification.print(e);
		} catch (UniqueException e) {
			// LogAndNotification.print(e);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	private void addValidatorCodigoCuentaTXT() {
		
		codigoCuentaTXT.removeAllValidators();

		CuentaContableCodigoUniqueValidator cuentaContableCodigoUniqueValidator;

		if (StandardFormUi.INSERT_MODE.equalsIgnoreCase(mode)) {
			cuentaContableCodigoUniqueValidator = new CuentaContableCodigoUniqueValidator(
					cx, dtoBI);
		} else {
			cuentaContableCodigoUniqueValidator = new CuentaContableCodigoUniqueValidator(
					cx, dtoBI, ((CuentaContableFull) originalDTO).getCodigoCuenta());
		}
		codigoCuentaTXT.addValidator(cuentaContableCodigoUniqueValidator);
	}
	
	private void addValidatorCodigoCuentaPadreTXT() {
		
		codigoCuentaPadreTXT.removeAllValidators();

		CuentaContableCodigoPadreUniqueValidator stringPlanDeCuentaCodigoPadreValidator = new CuentaContableCodigoPadreUniqueValidator(
				cx, dtoBI);
		codigoCuentaPadreTXT
				.addValidator(stringPlanDeCuentaCodigoPadreValidator);
	}
	
	private void addValidatorCuentaContableTXT() {
		
		cuentaContableTXT.removeAllValidators();
		
		CuentaContableCuentaContableUniqueValidator stringPlanDeCuentaCuentaContableValidator = null;

		if (StandardFormUi.INSERT_MODE.equalsIgnoreCase(mode)) {
			stringPlanDeCuentaCuentaContableValidator = new CuentaContableCuentaContableUniqueValidator(
					cx, dtoBI);
		} else {
			stringPlanDeCuentaCuentaContableValidator = new CuentaContableCuentaContableUniqueValidator(
					cx, dtoBI, ((CuentaContable) originalDTO).getCuentaContable());
		}
		cuentaContableTXT
				.addValidator(stringPlanDeCuentaCuentaContableValidator);

	}
	


}