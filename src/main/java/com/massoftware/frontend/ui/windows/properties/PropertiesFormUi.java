package com.massoftware.frontend.ui.windows.properties;

import java.util.Properties;

import com.massoftware.backend.MigradorMSToPG;
import com.massoftware.backend.bo.UsuarioBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.MasSoftware;
import com.massoftware.frontend.ui.menu.ContabilidadGeneralMenu;
import com.massoftware.frontend.ui.menu.FondosMenu;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.model.Usuario;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class PropertiesFormUi extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 641401251451017754L;

	// ----------------------------------------------

	protected Window window;
	protected BackendContext cx;
	protected MasSoftware masSoftware;

	// ----------------------------------------------
	// CONTROLES

	protected VerticalLayout rootVL;

	protected TabSheet pestaniasTBS;

	protected FormLayout userFL;
	protected TextField userTXT;

	protected FormLayout msFL;
	protected TextField userNameMSTXT;
	protected TextField userPasswordMSTXT;
	protected TextField serverNameMSTXT;
	protected TextField portNumberMSTXT;
	protected TextField databaseNameMSTXT;

	protected FormLayout pgFL;
	protected TextField userNamePGTXT;
	protected TextField userPasswordPGTXT;
	protected TextField serverNamePGTXT;
	protected TextField portNumberPGTXT;
	protected TextField databaseNamePGTXT;

	protected HorizontalLayout barraDeHerramientasFila1;
	protected OptionGroup optionGroup;
	protected Button ingresarBTN;
	protected Button migrarBTN;

	public PropertiesFormUi(Window window, BackendContext cx,
			MasSoftware masSoftware) {
		super();
		try {
			this.window = window;
			this.cx = cx;
			this.masSoftware = masSoftware;
			viewPort768x1024();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void exit() {

		try {
			window.close();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// ESTATE VIEWS ======================================================

	// AAA ................................................................

	protected void viewPort768x1024() throws Exception {

		// ----------------------------------------------
		// CONTROLES

		// this.setCaption("Plan de cuenta");
		// this.setImmediate(true);
		// this.setWidth("-1px");
		// this.setHeight("-1px");
		// this.setClosable(true);
		// this.setResizable(false);
		// this.center();

		// --------------------------------------------------

		rootVL = new VerticalLayout();
		rootVL.setMargin(true);
		rootVL.setSpacing(true);
		rootVL.setWidth("-1px");

		this.setCompositionRoot(rootVL);

		// --------------------------------------------------

		pestaniasTBS = new TabSheet();
		pestaniasTBS.setTabIndex(1);
		pestaniasTBS.setWidth("-1px");
		pestaniasTBS.setHeight("-1px");
		pestaniasTBS.setTabsVisible(true);
		pestaniasTBS.setVisible(true);
		pestaniasTBS.setEnabled(true);
		pestaniasTBS.setReadOnly(false);
		pestaniasTBS.setImmediate(true);

		rootVL.addComponent(pestaniasTBS);

		// --------------------------------------------------
		// --------------------------------------------------

		userFL = new FormLayout();
		userFL.setMargin(true);
		userFL.setSpacing(true);
		userFL.setWidth("-1px");
		userFL.setHeight("-1px");
		userFL.setVisible(true);
		userFL.setEnabled(true);
		userFL.setReadOnly(false);
		userFL.setImmediate(true);

		pestaniasTBS.addTab(userFL, "Usuario");

		// --------------------------------------------------

		userTXT = new TextField();
		userTXT.setValue("Administrador");
		userTXT.setCaption("Usuario");
		userTXT.addStyleName(ValoTheme.TEXTAREA_TINY);
		userTXT.setTabIndex(-1);
		userTXT.setWidth("-1px");
		userTXT.setHeight("-1px");
		// userNameTXT.setColumns(4);
		// userNameTXT.setMaxLength(4);
		userTXT.setRequired(true);
		userTXT.setRequiredError("El campo " + userTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		userTXT.setValidationVisible(true);
		userTXT.setNullRepresentation("");
		userTXT.setVisible(true);
		userTXT.setEnabled(true);
		userTXT.setReadOnly(false);
		userTXT.setImmediate(true);

		userFL.addComponent(userTXT);

		// --------------------------------------------------
		// --------------------------------------------------

		msFL = new FormLayout();
		msFL.setMargin(true);
		msFL.setSpacing(true);
		msFL.setWidth("-1px");
		msFL.setHeight("-1px");
		msFL.setVisible(true);
		msFL.setEnabled(true);
		msFL.setReadOnly(false);
		msFL.setImmediate(true);

		pestaniasTBS.addTab(msFL, "MS SQL Server");

		// --------------------------------------------------

		userNameMSTXT = new TextField();
		userNameMSTXT.setValue("sa");
		userNameMSTXT.setCaption("User name");
		userNameMSTXT.addStyleName(ValoTheme.TEXTAREA_TINY);
		userNameMSTXT.setTabIndex(-1);
		userNameMSTXT.setWidth("-1px");
		userNameMSTXT.setHeight("-1px");
		// userNameTXT.setColumns(4);
		// userNameTXT.setMaxLength(4);
		userNameMSTXT.setRequired(true);
		userNameMSTXT.setRequiredError("El campo " + userNameMSTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		userNameMSTXT.setValidationVisible(true);
		userNameMSTXT.setNullRepresentation("");
		userNameMSTXT.setVisible(true);
		userNameMSTXT.setEnabled(true);
		userNameMSTXT.setReadOnly(false);
		userNameMSTXT.setImmediate(true);

		msFL.addComponent(userNameMSTXT);

		// --------------------------------------------------

		userPasswordMSTXT = new TextField();
		userPasswordMSTXT.setValue("cordoba");
		userPasswordMSTXT.setCaption("User password");
		userPasswordMSTXT.addStyleName(ValoTheme.TEXTAREA_TINY);
		userPasswordMSTXT.setTabIndex(-1);
		userPasswordMSTXT.setWidth("-1px");
		userPasswordMSTXT.setHeight("-1px");
		// userPasswordTXT.setColumns(4);
		// userPasswordTXT.setMaxLength(4);
		userPasswordMSTXT.setRequired(true);
		userPasswordMSTXT.setRequiredError("El campo "
				+ userPasswordMSTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		userPasswordMSTXT.setValidationVisible(true);
		userPasswordMSTXT.setNullRepresentation("");
		userPasswordMSTXT.setVisible(true);
		userPasswordMSTXT.setEnabled(true);
		userPasswordMSTXT.setReadOnly(false);
		userPasswordMSTXT.setImmediate(true);

		msFL.addComponent(userPasswordMSTXT);

		// --------------------------------------------------

		serverNameMSTXT = new TextField();
		serverNameMSTXT.setValue("localhost");
		serverNameMSTXT.setCaption("Server name");
		serverNameMSTXT.addStyleName(ValoTheme.TEXTAREA_TINY);
		serverNameMSTXT.setTabIndex(-1);
		serverNameMSTXT.setWidth("-1px");
		serverNameMSTXT.setHeight("-1px");
		// serverNameTXT.setColumns(4);
		// serverNameTXT.setMaxLength(4);
		serverNameMSTXT.setRequired(true);
		serverNameMSTXT.setRequiredError("El campo "
				+ serverNameMSTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		serverNameMSTXT.setValidationVisible(true);
		serverNameMSTXT.setNullRepresentation("");
		serverNameMSTXT.setVisible(true);
		serverNameMSTXT.setEnabled(true);
		serverNameMSTXT.setReadOnly(false);
		serverNameMSTXT.setImmediate(true);

		msFL.addComponent(serverNameMSTXT);

		// --------------------------------------------------

		portNumberMSTXT = new TextField();
		portNumberMSTXT.setValue("1433");
		portNumberMSTXT.setCaption("Port number ");
		portNumberMSTXT.addStyleName(ValoTheme.TEXTAREA_TINY);
		portNumberMSTXT.setTabIndex(-1);
		portNumberMSTXT.setWidth("-1px");
		portNumberMSTXT.setHeight("-1px");
		// portNumberTXT.setColumns(4);
		// portNumberTXT.setMaxLength(4);
		portNumberMSTXT.setRequired(true);
		portNumberMSTXT.setRequiredError("El campo "
				+ portNumberMSTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		portNumberMSTXT.setValidationVisible(true);
		portNumberMSTXT.setNullRepresentation("");
		portNumberMSTXT.setVisible(true);
		portNumberMSTXT.setEnabled(true);
		portNumberMSTXT.setReadOnly(false);
		portNumberMSTXT.setImmediate(true);

		msFL.addComponent(portNumberMSTXT);

		// --------------------------------------------------

		databaseNameMSTXT = new TextField();
		databaseNameMSTXT.setValue("VetaroRep");
		databaseNameMSTXT.setCaption("Database name");
		databaseNameMSTXT.addStyleName(ValoTheme.TEXTAREA_TINY);
		databaseNameMSTXT.setTabIndex(-1);
		databaseNameMSTXT.setWidth("-1px");
		databaseNameMSTXT.setHeight("-1px");
		// databaseNameTXT.setColumns(4);
		// databaseNameTXT.setMaxLength(4);
		databaseNameMSTXT.setRequired(true);
		databaseNameMSTXT.setRequiredError("El campo "
				+ databaseNameMSTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		databaseNameMSTXT.setValidationVisible(true);
		databaseNameMSTXT.setNullRepresentation("");
		databaseNameMSTXT.setVisible(true);
		databaseNameMSTXT.setEnabled(true);
		databaseNameMSTXT.setReadOnly(false);
		databaseNameMSTXT.setImmediate(true);

		msFL.addComponent(databaseNameMSTXT);

		// --------------------------------------------------
		// --------------------------------------------------

		pgFL = new FormLayout();
		pgFL.setMargin(true);
		pgFL.setSpacing(true);
		pgFL.setWidth("-1px");
		pgFL.setHeight("-1px");
		pgFL.setVisible(true);
		pgFL.setEnabled(true);
		pgFL.setReadOnly(false);
		pgFL.setImmediate(true);

		pestaniasTBS.addTab(pgFL, "PostgreSQL");

		// --------------------------------------------------

		userNamePGTXT = new TextField();
		userNamePGTXT.setValue("postgres");
		userNamePGTXT.setCaption("User name");
		userNamePGTXT.addStyleName(ValoTheme.TEXTAREA_TINY);
		userNamePGTXT.setTabIndex(-1);
		userNamePGTXT.setWidth("-1px");
		userNamePGTXT.setHeight("-1px");
		// userNamePGTXT.setColumns(4);
		// userNamePGTXT.setMaxLength(4);
		userNamePGTXT.setRequired(true);
		userNamePGTXT.setRequiredError("El campo " + userNamePGTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		userNamePGTXT.setValidationVisible(true);
		userNamePGTXT.setNullRepresentation("");
		userNamePGTXT.setVisible(true);
		userNamePGTXT.setEnabled(true);
		userNamePGTXT.setReadOnly(false);
		userNamePGTXT.setImmediate(true);

		pgFL.addComponent(userNamePGTXT);

		// --------------------------------------------------

		userPasswordPGTXT = new TextField();
		userPasswordPGTXT.setValue("cordoba");
		userPasswordPGTXT.setCaption("User password");
		userPasswordPGTXT.addStyleName(ValoTheme.TEXTAREA_TINY);
		userPasswordPGTXT.setTabIndex(-1);
		userPasswordPGTXT.setWidth("-1px");
		userPasswordPGTXT.setHeight("-1px");
		// userPasswordPGTXT.setColumns(4);
		// userPasswordPGTXT.setMaxLength(4);
		userPasswordPGTXT.setRequired(true);
		userPasswordPGTXT.setRequiredError("El campo "
				+ userPasswordPGTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		userPasswordPGTXT.setValidationVisible(true);
		userPasswordPGTXT.setNullRepresentation("");
		userPasswordPGTXT.setVisible(true);
		userPasswordPGTXT.setEnabled(true);
		userPasswordPGTXT.setReadOnly(false);
		userPasswordPGTXT.setImmediate(true);

		pgFL.addComponent(userPasswordPGTXT);

		// --------------------------------------------------

		serverNamePGTXT = new TextField();
		serverNamePGTXT.setValue("localhost");
		serverNamePGTXT.setCaption("Server name");
		serverNamePGTXT.addStyleName(ValoTheme.TEXTAREA_TINY);
		serverNamePGTXT.setTabIndex(-1);
		serverNamePGTXT.setWidth("-1px");
		serverNamePGTXT.setHeight("-1px");
		// serverNamePGTXT.setColumns(4);
		// serverNamePGTXT.setMaxLength(4);
		serverNamePGTXT.setRequired(true);
		serverNamePGTXT.setRequiredError("El campo "
				+ serverNamePGTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		serverNamePGTXT.setValidationVisible(true);
		serverNamePGTXT.setNullRepresentation("");
		serverNamePGTXT.setVisible(true);
		serverNamePGTXT.setEnabled(true);
		serverNamePGTXT.setReadOnly(false);
		serverNamePGTXT.setImmediate(true);

		pgFL.addComponent(serverNamePGTXT);

		// --------------------------------------------------

		portNumberPGTXT = new TextField();
		portNumberPGTXT.setValue("5432");
		portNumberPGTXT.setCaption("Port number ");
		portNumberPGTXT.addStyleName(ValoTheme.TEXTAREA_TINY);
		portNumberPGTXT.setTabIndex(-1);
		portNumberPGTXT.setWidth("-1px");
		portNumberPGTXT.setHeight("-1px");
		// portNumberPGTXT.setColumns(4);
		// portNumberPGTXT.setMaxLength(4);
		portNumberPGTXT.setRequired(true);
		portNumberPGTXT.setRequiredError("El campo "
				+ portNumberPGTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		portNumberPGTXT.setValidationVisible(true);
		portNumberPGTXT.setNullRepresentation("");
		portNumberPGTXT.setVisible(true);
		portNumberPGTXT.setEnabled(true);
		portNumberPGTXT.setReadOnly(false);
		portNumberPGTXT.setImmediate(true);

		pgFL.addComponent(portNumberPGTXT);

		// --------------------------------------------------

		databaseNamePGTXT = new TextField();
		databaseNamePGTXT.setValue("massoftware");
		databaseNamePGTXT.setCaption("Database name");
		databaseNamePGTXT.addStyleName(ValoTheme.TEXTAREA_TINY);
		databaseNamePGTXT.setTabIndex(-1);
		databaseNamePGTXT.setWidth("-1px");
		databaseNamePGTXT.setHeight("-1px");
		// databaseNamePGTXT.setColumns(4);
		// databaseNamePGTXT.setMaxLength(4);
		databaseNamePGTXT.setRequired(true);
		databaseNamePGTXT.setRequiredError("El campo "
				+ databaseNamePGTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		databaseNamePGTXT.setValidationVisible(true);
		databaseNamePGTXT.setNullRepresentation("");
		databaseNamePGTXT.setVisible(true);
		databaseNamePGTXT.setEnabled(true);
		databaseNamePGTXT.setReadOnly(false);
		databaseNamePGTXT.setImmediate(true);

		pgFL.addComponent(databaseNamePGTXT);

		// ----------------------------------------------

		barraDeHerramientasFila1 = new HorizontalLayout();
		barraDeHerramientasFila1.setSpacing(true);

		rootVL.addComponent(barraDeHerramientasFila1);
		rootVL.setComponentAlignment(barraDeHerramientasFila1,
				Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		optionGroup = new OptionGroup();
		optionGroup.addItems("MS SQL Server", "PostgreSQL");
		optionGroup.setValue("MS SQL Server");

		barraDeHerramientasFila1.addComponent(optionGroup);

		// --------------------------------------------------

		ingresarBTN = new Button();
		ingresarBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		ingresarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		ingresarBTN.setIcon(FontAwesome.PLAY_CIRCLE_O);
		ingresarBTN.setCaption("Ingresar");
		ingresarBTN.addClickListener(e -> {
			ingresarBTNClick();
		});

		barraDeHerramientasFila1.addComponent(ingresarBTN);

		// --------------------------------------------------

		migrarBTN = new Button();
		migrarBTN.addStyleName(ValoTheme.BUTTON_DANGER);
		migrarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		migrarBTN.setIcon(FontAwesome.PLAY_CIRCLE);
		migrarBTN.setCaption("Migrar a PostgreSQL");
		migrarBTN.addClickListener(e -> {
			migrarBTNClick();
		});

		barraDeHerramientasFila1.addComponent(migrarBTN);

		// --------------------------------------------------

	}

	protected void migrarBTNClick() {
		try {

			Properties propertiesMS = new Properties();

			propertiesMS.put("jdbc.driverClassName",
					"com.microsoft.sqlserver.jdbc.SQLServerDriver");
			propertiesMS.put("jdbc.userName", userNameMSTXT.getValue());
			propertiesMS.put("jdbc.userPassword", userPasswordMSTXT.getValue());
			// properties.put("jdbc.url", ds.getURL());
			propertiesMS.put("jdbc.maxActive", "10");
			propertiesMS.put("jdbc.initialSize", "5");
			propertiesMS.put("jdbc.maxIdle", "5");
			propertiesMS.put("jdbc.validationQuery", "SELECT 1");
			propertiesMS.put("jdbc.verbose", "true");
			propertiesMS.put("jdbc.serverName", serverNameMSTXT.getValue());
			propertiesMS.put("jdbc.databaseName", databaseNameMSTXT.getValue());
			propertiesMS.put("jdbc.portNumber", portNumberMSTXT.getValue());

			Properties propertiesPG = new Properties();

			propertiesPG.put("jdbc.driverClassName", "org.postgresql.Driver");
			propertiesPG.put("jdbc.userName", userNamePGTXT.getValue());
			propertiesPG.put("jdbc.userPassword", userPasswordPGTXT.getValue());
			propertiesPG.put("jdbc.url",
					"jdbc:postgresql://" + serverNamePGTXT.getValue() + ":"
							+ portNumberPGTXT.getValue() + "/"
							+ databaseNamePGTXT.getValue() + "?searchpath="
							+ databaseNamePGTXT.getValue() + "");
			propertiesPG.put("jdbc.maxActive", "10");
			propertiesPG.put("jdbc.initialSize", "5");
			propertiesPG.put("jdbc.maxIdle", "5");
			propertiesPG.put("jdbc.validationQuery", "SELECT 1");
			propertiesPG.put("jdbc.verbose", "true");
			
			MigradorMSToPG migradorMSToPG = new MigradorMSToPG();
			migradorMSToPG.migrar(propertiesPG, propertiesMS);
			cx.start(BackendContext.PG, propertiesPG);
			
//			if (optionGroup.getValue().equals("PostgreSQL")) {
//				cx.start(BackendContext.PG, propertiesPG);
//			} else {
//				cx.start(BackendContext.MS, propertiesMS);
//			}

			Usuario usuario = ((UsuarioBO)cx.buildBO(Usuario.class)).findByNombre(userTXT.getValue());

			VerticalLayout layout = new VerticalLayout();

			layout.addComponent(new ContabilidadGeneralMenu(cx, usuario));

			masSoftware.setContent(layout);

			window.close();

			exit();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	// EVTs LISTENERS ===================================================

	protected void ingresarBTNClick() {
		try {

			Properties propertiesMS = new Properties();

			propertiesMS.put("jdbc.driverClassName",
					"com.microsoft.sqlserver.jdbc.SQLServerDriver");
			propertiesMS.put("jdbc.userName", userNameMSTXT.getValue());
			propertiesMS.put("jdbc.userPassword", userPasswordMSTXT.getValue());
			// properties.put("jdbc.url", ds.getURL());
			propertiesMS.put("jdbc.maxActive", "10");
			propertiesMS.put("jdbc.initialSize", "5");
			propertiesMS.put("jdbc.maxIdle", "5");
			propertiesMS.put("jdbc.validationQuery", "SELECT 1");
			propertiesMS.put("jdbc.verbose", "true");
			propertiesMS.put("jdbc.serverName", serverNameMSTXT.getValue());
			propertiesMS.put("jdbc.databaseName", databaseNameMSTXT.getValue());
			propertiesMS.put("jdbc.portNumber", portNumberMSTXT.getValue());

			Properties propertiesPG = new Properties();

			propertiesPG.put("jdbc.driverClassName", "org.postgresql.Driver");
			propertiesPG.put("jdbc.userName", userNamePGTXT.getValue());
			propertiesPG.put("jdbc.userPassword", userPasswordPGTXT.getValue());
			propertiesPG.put("jdbc.url",
					"jdbc:postgresql://" + serverNamePGTXT.getValue() + ":"
							+ portNumberPGTXT.getValue() + "/"
							+ databaseNamePGTXT.getValue() + "?searchpath="
							+ databaseNamePGTXT.getValue() + "");
			propertiesPG.put("jdbc.maxActive", "10");
			propertiesPG.put("jdbc.initialSize", "5");
			propertiesPG.put("jdbc.maxIdle", "5");
			propertiesPG.put("jdbc.validationQuery", "SELECT 1");
			propertiesPG.put("jdbc.verbose", "true");

			if (optionGroup.getValue().equals("PostgreSQL")) {
				cx.start(BackendContext.PG, propertiesPG);
			} else {
				cx.start(BackendContext.MS, propertiesMS);
			}

			Usuario usuario = ((UsuarioBO)cx.buildBO(Usuario.class)).findByNombre(
					userTXT.getValue());

			VerticalLayout layout = new VerticalLayout();

			layout.addComponent(new ContabilidadGeneralMenu(cx, usuario));
			layout.addComponent(new FondosMenu(cx, usuario));

			masSoftware.setContent(layout);

			window.close();

			exit();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

} // END CLASS ///////////////////////////////////////////////////////////
