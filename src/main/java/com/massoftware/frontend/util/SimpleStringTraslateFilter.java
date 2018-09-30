package com.massoftware.frontend.util;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.filter.SimpleStringFilter;

public class SimpleStringTraslateFilter implements Filter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3915765281909681501L;

	public static String S_N = "S/N";
	public static String STARTS_WITCH = "STARTS_WITCH";
	public static String ENDS_WITCH = "ENDS_WITCH";
	public static String CONTAINS = "CONTAINS";
	public static String CONTAINS_WORDS_OR = "CONTAINS_WORDS_OR";
	public static String CONTAINS_WORDS_AND = "CONTAINS_WORDS_AND";

	final Object propertyId;
	final String filterString;
	final boolean ignoreCase;
	final String matchMode;

	public SimpleStringTraslateFilter(Object propertyId, String filterString,
			boolean ignoreCase, String matchMode) {
		this.propertyId = propertyId;
		this.filterString = Traslate.translate(filterString, ignoreCase).trim();
		this.ignoreCase = ignoreCase;
		this.matchMode = matchMode;

	}

	@Override
	public boolean passesFilter(Object itemId, Item item) {
		final Property<?> p = item.getItemProperty(propertyId);
		if (p == null) {
			return false;
		}
		
		Object propertyValue = p.getValue();
		
		if (propertyValue == null) {
			return false;
		}
		// if(filterString.trim().length() == 0){
		// return true;
		// }

		if (propertyValue instanceof Boolean) {
			
			Boolean b = (Boolean) propertyValue;

			if (b == true && filterString.equalsIgnoreCase("si")) {
				return true;
			}
			if (b == true && filterString.equalsIgnoreCase("s")) {
				return true;
			}
			if (b == false && filterString.equalsIgnoreCase("n")) {
				return true;
			}
			if (b == false && filterString.equalsIgnoreCase("no")) {
				return true;
			}

			return false;

		} else {
			final String value = Traslate.translate(propertyValue.toString()
					.trim(), ignoreCase);

			if (matchMode.equalsIgnoreCase(STARTS_WITCH)) {
				if (!value.startsWith(filterString.trim())) {
					return false;
				}
			} else if (matchMode.equalsIgnoreCase(ENDS_WITCH)) {
				if (!value.endsWith(filterString.trim())) {
					return false;
				}
			} else if (matchMode.equalsIgnoreCase(CONTAINS)) {
				if (!value.contains(filterString.trim())) {
					return false;
				}
			} else if (matchMode.equalsIgnoreCase(CONTAINS_WORDS_OR)) {
				String words[] = filterString.trim().split(" ");
				boolean b = false;

				for (String word : words) {
					word = word.trim();

					if (word.length() > 0) {
						if (value.contains(word)) {
							b = true;
						}
					}
				}

				if (b == false) {
					return false;
				}

			} else if (matchMode.equalsIgnoreCase(CONTAINS_WORDS_AND)) {
				String words[] = filterString.trim().split(" ");
				int b = 0;
				int cantWords = 0;

				for (String word : words) {
					word = word.trim();

					if (word.length() > 0) {

						cantWords++;

						if (value.contains(word)) {
							b++;
						}
					}

				}

				return (b == cantWords);

			}

		}

		return true;
	}

	@Override
	public boolean appliesToProperty(Object propertyId) {
		return this.propertyId.equals(propertyId);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		// Only ones of the objects of the same class can be equal
		if (!(obj instanceof SimpleStringFilter)) {
			return false;
		}
		final SimpleStringTraslateFilter o = (SimpleStringTraslateFilter) obj;

		// Checks the properties one by one
		if (propertyId != o.propertyId && o.propertyId != null
				&& !o.propertyId.equals(propertyId)) {
			return false;
		}
		if (filterString != o.filterString && o.filterString != null
				&& !o.filterString.equals(filterString)) {
			return false;
		}
		if (ignoreCase != o.ignoreCase) {
			return false;
		}
		if (matchMode != o.matchMode) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return (propertyId != null ? propertyId.hashCode() : 0)
				^ (filterString != null ? filterString.hashCode() : 0);
	}

	/**
	 * Returns the property identifier to which this filter applies.
	 *
	 * @return property id
	 */
	public Object getPropertyId() {
		return propertyId;
	}

	/**
	 * Returns the filter string.
	 *
	 * Note: this method is intended only for implementations of lazy string
	 * filters and may change in the future.
	 *
	 * @return filter string given to the constructor
	 */
	public String getFilterString() {
		return filterString;
	}

	/**
	 * Returns whether the filter is case-insensitive or case-sensitive.
	 *
	 * Note: this method is intended only for implementations of lazy string
	 * filters and may change in the future.
	 *
	 * @return true if performing case-insensitive filtering, false for
	 *         case-sensitive
	 */
	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public String getMatchMode() {
		return matchMode;
	}

	// /**
	// * Returns true if the filter only applies to the beginning of the value
	// * string, false for any location in the value.
	// *
	// * Note: this method is intended only for implementations of lazy string
	// * filters and may change in the future.
	// *
	// * @return true if checking for matches at the beginning of the value
	// only,
	// * false if matching any part of value
	// */
	// public boolean isOnlyMatchPrefix() {
	// return onlyMatchPrefix;
	// }

}