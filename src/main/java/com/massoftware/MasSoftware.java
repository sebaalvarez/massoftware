package com.massoftware;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.servlet.annotation.WebServlet;

import com.massoftware.backend.BackendContext;
import com.massoftware.windows.LogAndNotification;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
// @Theme("material")
public class MasSoftware extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2467123584653780193L;

	// private static final Logger LOGGER = Logger.getLogger(MasSoftware.class
	// .getName());

	// @Override
	// protected void init(VaadinRequest vaadinRequest) {
	//
	// try {
	//
	// BackendContext cx = new BackendContext();
	// Window win = new Window("Login");
	// win.setClosable(false);
	// win.setResizable(false);
	// win.setModal(true);
	// PropertiesFormUi ui = new PropertiesFormUi(win, cx, this);
	// win.setContent(ui);
	// getUI().addWindow(win);
	// win.center();
	// win.focus();
	//
	// // TextField filter = new TextField();
	// // TwinColSelect selection = new TwinColSelect("TWINCOL NAME");
	// // VerticalLayout ver = new VerticalLayout();
	// // selection.setColumns(8);
	// // filter.setColumns(7);
	// // filter.setImmediate(true);
	// // selection.setImmediate(true);
	// //
	// // List<String> mystrslist = new ArrayList<String>();
	// // mystrslist.add("HARPAL");
	// // mystrslist.add("MUSTAN");
	// // mystrslist.add("SHANMUKHA");
	// // mystrslist.add("HARI");
	// // mystrslist.add("RAVI");
	// // mystrslist.add("RAGHU");
	// // mystrslist.add("KHUN");
	// // mystrslist.add("CHARAN");
	// // mystrslist.add("SAMEER");
	// // mystrslist.add("AMAR");
	// // mystrslist.add("ANANT");
	// //
	// // final BeanItemContainer<String> beans = new
	// // BeanItemContainer<String>(
	// // String.class);
	// //
	// // beans.addAll(mystrslist);
	// //
	// // selection.setContainerDataSource(beans);
	// // selection
	// //
	// .setItemCaptionMode(TwinColSelect.ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID);
	// //
	// // filter.setTextChangeEventMode(TextChangeEventMode.LAZY);
	// //
	// // filter.addTextChangeListener(new TextChangeListener() {
	// //
	// // /**
	// // * addContain
	// // */
	// // private static final long serialVersionUID = 1L;
	// //
	// // public void textChange(final TextChangeEvent event) {
	// // beans.removeAllContainerFilters();
	// // beans.addContainerFilter(new Filter() {
	// //
	// // @Override
	// // public boolean passesFilter(Object itemId, Item item)
	// // throws UnsupportedOperationException {
	// // if (((String) itemId).toLowerCase().contains(
	// // event.getText().toLowerCase())
	// // || ((Collection) selection.getValue())
	// // .contains(itemId))
	// // return true;
	// // else
	// // return false;
	// // }
	// //
	// // @Override
	// // public boolean appliesToProperty(Object propertyId) {
	// // return true;
	// // }
	// // });
	// // }
	// // });
	// //
	// // ver.addComponent(selection);
	// // ver.addComponent(filter);
	// // setContent(ver);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// LOGGER.log(Level.SEVERE, e.toString());
	// }
	// }

	// ===================================================================================================

	private SessionVar sessionVar;

	private String dbFilesPath = "D:\\dev\\source\\massoftware\\files_db";
	private String fotosUsuariosPath = dbFilesPath + File.separatorChar
			+ "fotos_usuarios";
	private String iconosPath = dbFilesPath + File.separatorChar + "iconos";

	private boolean testMode = false;

	private ValoMenuLayout root = new ValoMenuLayout();
	private ComponentContainer viewDisplay = root.getContentContainer();
	private CssLayout menu = new CssLayout();
	private CssLayout menuItemsLayout = new CssLayout();
	{
		menu.setId("testMenu");
	}
	private Navigator navigator;
	private LinkedHashMap<String, String> menuItems = new LinkedHashMap<String, String>();

	// ===================================================================================================

	public SessionVar getSessionVar() {
		return sessionVar;
	}

	// ===================================================================================================

	@WebServlet(urlPatterns = "/*", name = "MasSoftwareServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MasSoftware.class, productionMode = false)
	public static class MasSoftwareServlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3384068185250400739L;
	}

	// ===================================================================================================

	@Override
	protected void init(VaadinRequest request) {

		if (request.getParameter("test") != null) {

			testMode = true;

			if (browserCantRenderFontsConsistently()) {
				getPage().getStyles().add(
						".v-app.v-app.v-app {font-family: Sans-Serif;}");
			}
		}

		// ----------------------------------------

		sessionVar = new SessionVar();
		sessionVar.setCx(new BackendContext());

		Window win = new Window("Login");
		win.setClosable(false);
		win.setResizable(false);
		win.setModal(true);
		PropertiesFormUi ui = new PropertiesFormUi(win, sessionVar, this);
		win.setContent(ui);
		getUI().addWindow(win);
		win.center();
		win.focus();

		// ----------------------------------------

	}

	private boolean browserCantRenderFontsConsistently() {
		// PhantomJS renders font correctly about 50% of the time, so
		// disable it to have consistent screenshots
		// https://github.com/ariya/phantomjs/issues/10592

		// IE8 also has randomness in its font rendering...

		return getPage().getWebBrowser().getBrowserApplication()
				.contains("PhantomJS")
				|| (getPage().getWebBrowser().isIE() && getPage()
						.getWebBrowser().getBrowserMajorVersion() <= 9);
	}

	public void renderMDI() {

		if (getPage().getWebBrowser().isIE()
				&& getPage().getWebBrowser().getBrowserMajorVersion() == 9) {
			menu.setWidth("320px");
		}

		if (!testMode) {
			Responsive.makeResponsive(this);
		}

		getPage().setTitle("MasSoftware");
		setContent(root);
		root.setWidth("100%");

		root.addMenu(buildMenu());
		addStyleName(ValoTheme.UI_WITH_MENU);

		navigator = new Navigator(this, viewDisplay);
		
		navigator.addView("a", new GeneralMenu(iconosPath, sessionVar));
		navigator.addView("b", new VentasMenu(iconosPath, sessionVar));
		navigator.addView("c", new StockMenu(iconosPath, sessionVar));
		navigator.addView("d", new FondosMenu(iconosPath, sessionVar));
		navigator.addView("e", new FondosMenu(iconosPath, sessionVar));
		navigator.addView("f", new ContabilidadGeneralMenu(iconosPath, sessionVar));
		navigator.addView("g", new FondosMenu(iconosPath, sessionVar));
		navigator.addView("h", new FondosMenu(iconosPath, sessionVar));
		navigator.addView("i", new FondosMenu(iconosPath, sessionVar));
		navigator.addView("j", new FondosMenu(iconosPath, sessionVar));
		navigator.addView("k", new FondosMenu(iconosPath, sessionVar));
		navigator.addView("l", new SuperMenu(iconosPath, sessionVar));

		String f = Page.getCurrent().getUriFragment();
		if (f == null || f.equals("")) {
//			navigator.navigateTo("a");
			navigator.navigateTo("l");
		}

		navigator.setErrorView(new FondosMenu(iconosPath, sessionVar));

		navigator.addViewChangeListener(new ViewChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7457792940869327955L;

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				navigatorViewChangeListener(event);

			}
		});
	}

	private CssLayout buildMenu() {
		// Add items		
		menuItems.put("a", "General");
		menuItems.put("b", "Ventas");
		menuItems.put("c", "Stock");
		menuItems.put("d", "Fondos");
		menuItems.put("e", "Proveedores");
		menuItems.put("f", "Contabilidad general");
		menuItems.put("g", "Recursos humanos");
		menuItems.put("h", "Centralizador");
		menuItems.put("i", "Comercio exterior");
		menuItems.put("j", "Devol.y ganancias");
		menuItems.put("k", "ISO 9001");
		menuItems.put("l", "Super menu");

		HorizontalLayout top = new HorizontalLayout();
		top.setWidth("100%");
		top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		top.addStyleName(ValoTheme.MENU_TITLE);
		menu.addComponent(top);

		Button showMenu = new Button("Menu", new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4405558436737791414L;

			@Override
			public void buttonClick(ClickEvent event) {
				showMenuClickListener();
			}
		});
		showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
		showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
		showMenu.addStyleName("valo-menu-toggle");
		showMenu.setIcon(FontAwesome.LIST);
		menu.addComponent(showMenu);

		Label title = new Label("<h3><strong>Mas</strong>Software</h3>",
				ContentMode.HTML);
		title.setSizeUndefined();
		top.addComponent(title);
		top.setExpandRatio(title, 1);

		MenuBar settings = new MenuBar();
		settings.addStyleName("user-menu");

		File fileImg = new File(fotosUsuariosPath + File.separatorChar
				+ sessionVar.getUsuario().getId());

		if (fileImg.exists() == false) {
			fileImg = new File(iconosPath + File.separatorChar
					+ "round-account-button-with-user-inside (1).png");
		}

		FileResource resource = new FileResource(fileImg);

		MenuItem settingsItem = settings.addItem(this.sessionVar.getUsuario()
				.getNombre(), resource, null);
		settingsItem.setDescription(sessionVar.getUsuario().getId());
		settingsItem.addItem("Editar perfil", null);
		settingsItem.addItem("Preferencias", null);
		settingsItem.addSeparator();
		settingsItem.addItem("Salir", null);
		menu.addComponent(settings);

		menuItemsLayout.setPrimaryStyleName("valo-menuitems");
		menu.addComponent(menuItemsLayout);

		Label label = null;

		for (final Entry<String, String> item : menuItems.entrySet()) {			
			

			if (item.getKey().equals("b")) {
				label = new Label("Módulos", ContentMode.HTML);
				label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
				label.addStyleName(ValoTheme.LABEL_H4);
				label.setSizeUndefined();
				menuItemsLayout.addComponent(label);
			}

			Button button = new Button(item.getValue(), new ClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 2700386436526350875L;

				@Override
				public void buttonClick(ClickEvent event) {
					try {
						navigator.navigateTo(item.getKey());
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}
			});
//			b.setEnabled(false);
			button.setHtmlContentAllowed(true);
			button.setPrimaryStyleName(ValoTheme.MENU_ITEM);
			if (item.getKey().equals("a")) {
				fileImg = new File(iconosPath + File.separatorChar + "menu.png");
			} else if (item.getKey().equals("b")) {
				fileImg = new File(iconosPath + File.separatorChar
						+ "shopping-cart.png");
			} else if (item.getKey().equals("c")) {
				fileImg = new File(iconosPath + File.separatorChar
						+ "factory-stock-house.png");
			} else if (item.getKey().equals("d")) {
				fileImg = new File(iconosPath + File.separatorChar + "money-bags.png");
			} else if (item.getKey().equals("e")) {
				fileImg = new File(iconosPath + File.separatorChar
						+ "worker-loading-boxes.png");
			} else if (item.getKey().equals("f")) {
				fileImg = new File(iconosPath + File.separatorChar
						+ "budget-calculator.png");
			} else if (item.getKey().equals("g")) {
				fileImg = new File(iconosPath + File.separatorChar + "group.png");
			} else if (item.getKey().equals("h")) {
				fileImg = new File(iconosPath + File.separatorChar
						+ "screen-with-network-graph.png");
			} else if (item.getKey().equals("i")) {
				fileImg = new File(iconosPath + File.separatorChar
						+ "oceanic-cargo-ship-global-distribution.png");
			} else if (item.getKey().equals("j")) {
				fileImg = new File(iconosPath + File.separatorChar
						+ "hand-holding-up-a-sack-of-money.png");
			} else if (item.getKey().equals("k")) {
				fileImg = new File(iconosPath + File.separatorChar + "earth-grid.png");
			} else if (item.getKey().equals("l")) {
				fileImg = new File(iconosPath + File.separatorChar + "menu.png");
				button.setEnabled(true);
			}

			// b.setIcon(testIcon.get());
			button.setIcon(new FileResource(fileImg));
			menuItemsLayout.addComponent(button);

		}

		return menu;
	}

	// EVENTS

	private void navigatorViewChangeListener(ViewChangeEvent event) {
		try {
			for (Iterator<Component> it = menuItemsLayout.iterator(); it
					.hasNext();) {
				it.next().removeStyleName("selected");
			}
			for (Entry<String, String> item : menuItems.entrySet()) {
				if (event.getViewName().equals(item.getKey())) {
					for (Iterator<Component> it = menuItemsLayout.iterator(); it
							.hasNext();) {
						Component c = it.next();
						if (c.getCaption() != null
								&& c.getCaption().startsWith(item.getValue())) {
							c.addStyleName("selected");
							break;
						}
					}
					break;
				}
			}
			menu.removeStyleName("valo-menu-visible");

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void showMenuClickListener() {
		try {
			if (menu.getStyleName().contains("valo-menu-visible")) {
				menu.removeStyleName("valo-menu-visible");
			} else {
				menu.addStyleName("valo-menu-visible");
			}
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

} // END CLASS
