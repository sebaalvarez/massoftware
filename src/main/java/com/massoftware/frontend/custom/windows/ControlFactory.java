package com.massoftware.frontend.custom.windows;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.massoftware.frontend.util.UtilDate;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ControlFactory {
	
	public static Label buildLBL() {

		Label df = new Label();
		df.addStyleName(ValoTheme.LABEL_TINY);

		return df;

	}

	public static TextField buildTXT() {
		
		TextField txt = new TextField();

		txt.addStyleName(ValoTheme.TEXTFIELD_TINY);
		txt.setWidth("-1px");
		txt.setHeight("-1px");
		txt.setValidationVisible(true);
		txt.setRequiredError("El campo es requerido. Es decir no debe estar vacio.");
		txt.setNullRepresentation("");
		txt.setVisible(true);
		txt.setEnabled(true);
		txt.setReadOnly(false);
		txt.setImmediate(true);

		return txt;
	}

	public static TextArea buildTXA() {

		TextArea txt = new TextArea();

		txt.addStyleName(ValoTheme.TEXTFIELD_TINY);
		txt.setWidth("-1px");
		txt.setHeight("-1px");
		txt.setValidationVisible(true);
		txt.setRequiredError("El campo es requerido. Es decir no debe estar vacio.");
		txt.setNullRepresentation("");
		txt.setVisible(true);
		txt.setEnabled(true);
		txt.setReadOnly(false);
		txt.setImmediate(true);

		return txt;
	}
	
	public static DateField buildDF(boolean timestamp) {

		DateField df = null;

		if (timestamp) {
			df = new DateField();
			df.setConversionError("Se espera un valor dd/MM/yyyy HH:mm:ss ej 12/11/1979 22:36:54");
		} else {

			df = new DateField() {

				private static final long serialVersionUID = -1814526872789903256L;

				@Override
				protected Date handleUnparsableDateString(String dateString)
						throws Converter.ConversionException {

					return UtilDate.parseDate(dateString);
					// return new Timestamp(System.currentTimeMillis());
				}

				public void changeVariables(Object source,
						Map<String, Object> variables) {

					if (variables.containsKey("dateString") == false) {
						variables.put(
								"dateString",
								variables.get("day") + "/"
										+ variables.get("month") + "/"
										+ variables.get("year"));
					}

					variables.put("day", -1);
					variables.put("year", -1);
					variables.put("month", -1);
					// variables.put("sec", -1);
					// variables.put("min", -1);
					// variables.put("hour", -1);
					super.changeVariables(source, variables);
				}

			};
		}

		df.addStyleName(ValoTheme.DATEFIELD_TINY);
		df.setLocale(new Locale("es", "AR"));
		if (timestamp) {
			df.setDateFormat("dd/MM/yyyy HH:mm:ss");
			df.setResolution(Resolution.SECOND);
			// df.setResolution(DateResolution.DAY);
			df.setShowISOWeekNumbers(true);
		} else {
			df.setDateFormat("dd/MM/yyyy");
			df.setWidth("105px");
		}
		df.setLenient(true);
		// df.setReadOnly(false);
		df.setImmediate(true);

		return df;
	}
	
	public static CheckBox buildCHK() {

		CheckBox chk = new CheckBox();

		chk.addStyleName(ValoTheme.CHECKBOX_SMALL);
		chk.setWidth("-1px");
		chk.setHeight("-1px");
		chk.setVisible(true);
		chk.setEnabled(true);
		chk.setReadOnly(false);
		chk.setImmediate(true);

		return chk;
	}
	
	public static ComboBox buildCB() {

		ComboBox cb = new ComboBox();
		cb.addStyleName(ValoTheme.COMBOBOX_TINY);

		cb.setWidth("100%");
		cb.setHeight("-1px");
		cb.setRequiredError("El campo es requerido. Es decir no debe estar vacio.");
		cb.setValidationVisible(true);
		cb.setVisible(true);
		cb.setEnabled(true);
		cb.setReadOnly(false);
		cb.setImmediate(true);
		cb.setFilteringMode(FilteringMode.CONTAINS);
		
//		cb.addValueChangeListener(e -> {
//			try{
//				Object o = cb.getValue();
//				if(o != null){
//					cb.setDescription(o.toString());	
//				} else {
//					cb.setDescription("");
//				}
//				
//			}catch(Exception ex){
//				LogAndNotification.print(ex);
//			}
//		});
//		
//		
//		Object o = cb.getValue();
//		if(o != null){
//			cb.setDescription(o.toString());	
//		} else {
//			cb.setDescription("");
//		}

		return cb;
	}
	
	public static OptionGroup buildOG() {
		OptionGroup og = new OptionGroup();
		og.addStyleName(ValoTheme.OPTIONGROUP_SMALL);

		return og;
	}
	
	
	public static VerticalLayout buildVL() {
		VerticalLayout vl = new VerticalLayout();
		vl.setWidth("-1px");
		vl.setHeight("-1px");
		vl.setMargin(true);
		vl.setSpacing(true);

		return vl;
	}

}
