package com.massoftware.frontend;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.windows.properties.PropertiesFormUi;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

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
public class MasSoftware extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2467123584653780193L;

	private static final Logger LOGGER = Logger.getLogger(MasSoftware.class
			.getName());

	@Override
	protected void init(VaadinRequest vaadinRequest) {

		try {

			BackendContext cx = new BackendContext();
			Window win = new Window("Login");
			win.setClosable(false);
			win.setResizable(false);
			win.setModal(true);
			PropertiesFormUi ui = new PropertiesFormUi(win, cx, this);
			win.setContent(ui);
			getUI().addWindow(win);
			win.center();
			win.focus();

			// TextField filter = new TextField();
			// TwinColSelect selection = new TwinColSelect("TWINCOL NAME");
			// VerticalLayout ver = new VerticalLayout();
			// selection.setColumns(8);
			// filter.setColumns(7);
			// filter.setImmediate(true);
			// selection.setImmediate(true);
			//
			// List<String> mystrslist = new ArrayList<String>();
			// mystrslist.add("HARPAL");
			// mystrslist.add("MUSTAN");
			// mystrslist.add("SHANMUKHA");
			// mystrslist.add("HARI");
			// mystrslist.add("RAVI");
			// mystrslist.add("RAGHU");
			// mystrslist.add("KHUN");
			// mystrslist.add("CHARAN");
			// mystrslist.add("SAMEER");
			// mystrslist.add("AMAR");
			// mystrslist.add("ANANT");
			//
			// final BeanItemContainer<String> beans = new
			// BeanItemContainer<String>(
			// String.class);
			//
			// beans.addAll(mystrslist);
			//
			// selection.setContainerDataSource(beans);
			// selection
			// .setItemCaptionMode(TwinColSelect.ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID);
			//
			// filter.setTextChangeEventMode(TextChangeEventMode.LAZY);
			//
			// filter.addTextChangeListener(new TextChangeListener() {
			//
			// /**
			// * addContain
			// */
			// private static final long serialVersionUID = 1L;
			//
			// public void textChange(final TextChangeEvent event) {
			// beans.removeAllContainerFilters();
			// beans.addContainerFilter(new Filter() {
			//
			// @Override
			// public boolean passesFilter(Object itemId, Item item)
			// throws UnsupportedOperationException {
			// if (((String) itemId).toLowerCase().contains(
			// event.getText().toLowerCase())
			// || ((Collection) selection.getValue())
			// .contains(itemId))
			// return true;
			// else
			// return false;
			// }
			//
			// @Override
			// public boolean appliesToProperty(Object propertyId) {
			// return true;
			// }
			// });
			// }
			// });
			//
			// ver.addComponent(selection);
			// ver.addComponent(filter);
			// setContent(ver);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.toString());
		}
	}

	@WebServlet(urlPatterns = "/*", name = "MasSoftwareServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MasSoftware.class, productionMode = false)
	public static class MasSoftwareServlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3384068185250400739L;
	}
}
