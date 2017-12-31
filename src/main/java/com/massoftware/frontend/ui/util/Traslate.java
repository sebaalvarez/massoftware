package com.massoftware.frontend.ui.util;

public class Traslate {

	public static String translate(String value, boolean lowerCase) {

		if (value == null) {
			return value;
		}

		value = value.replaceAll("â", "a");
		value = value.replaceAll("ã", "a");
		value = value.replaceAll("ä", "a");
		value = value.replaceAll("å", "a");
		value = value.replaceAll("ā", "a");
		value = value.replaceAll("ă", "a");
		value = value.replaceAll("ą", "a");
		value = value.replaceAll("à", "a");
		value = value.replaceAll("á", "a");

		if (lowerCase) {
			value = value.replaceAll("Á", "a");
			value = value.replaceAll("Â", "a");
			value = value.replaceAll("Ã", "a");
			value = value.replaceAll("Ä", "a");
			value = value.replaceAll("Å", "a");
			value = value.replaceAll("Ā", "a");
			value = value.replaceAll("Ă", "a");
			value = value.replaceAll("Ą", "a");
			value = value.replaceAll("À", "a");
		} else {
			value = value.replaceAll("Á", "A");
			value = value.replaceAll("Â", "A");
			value = value.replaceAll("Ã", "A");
			value = value.replaceAll("Ä", "A");
			value = value.replaceAll("Å", "A");
			value = value.replaceAll("Ā", "A");
			value = value.replaceAll("Ă", "A");
			value = value.replaceAll("Ą", "A");
			value = value.replaceAll("À", "A");
		}

		value = value.replaceAll("è", "e");
		value = value.replaceAll("é", "e");
		value = value.replaceAll("é", "e");
		value = value.replaceAll("ê", "e");
		value = value.replaceAll("ë", "e");
		value = value.replaceAll("ē", "e");
		value = value.replaceAll("ĕ", "e");
		value = value.replaceAll("ė", "e");
		value = value.replaceAll("ę", "e");
		value = value.replaceAll("ě", "e");

		if (lowerCase) {
			value = value.replaceAll("Ē", "e");
			value = value.replaceAll("Ĕ", "e");
			value = value.replaceAll("Ė", "e");
			value = value.replaceAll("Ę", "e");
			value = value.replaceAll("Ě", "e");
			value = value.replaceAll("É", "e");
			value = value.replaceAll("È", "e");
			value = value.replaceAll("Ë", "e");
			value = value.replaceAll("Ê", "e");
		} else {
			value = value.replaceAll("Ē", "E");
			value = value.replaceAll("Ĕ", "E");
			value = value.replaceAll("Ė", "E");
			value = value.replaceAll("Ę", "E");
			value = value.replaceAll("Ě", "E");
			value = value.replaceAll("É", "E");
			value = value.replaceAll("È", "E");
			value = value.replaceAll("Ë", "E");
			value = value.replaceAll("Ê", "E");
		}

		value = value.replaceAll("ì", "i");
		value = value.replaceAll("í", "i");
		value = value.replaceAll("î", "i");
		value = value.replaceAll("ï", "i");
		value = value.replaceAll("ì", "i");
		value = value.replaceAll("ĩ", "i");
		value = value.replaceAll("ī", "i");
		value = value.replaceAll("ĭ", "i");

		if (lowerCase) {
			value = value.replaceAll("Ì", "i");
			value = value.replaceAll("Í", "i");
			value = value.replaceAll("Î", "i");
			value = value.replaceAll("Ï", "i");
			value = value.replaceAll("Ì", "i");
			value = value.replaceAll("Ĩ", "i");
			value = value.replaceAll("Ī", "i");
			value = value.replaceAll("Ĭ", "i");
		} else {
			value = value.replaceAll("Ì", "I");
			value = value.replaceAll("Í", "I");
			value = value.replaceAll("Î", "I");
			value = value.replaceAll("Ï", "I");
			value = value.replaceAll("Ì", "I");
			value = value.replaceAll("Ĩ", "I");
			value = value.replaceAll("Ī", "I");
			value = value.replaceAll("Ĭ", "I");
		}

		value = value.replaceAll("ó", "o");
		value = value.replaceAll("ô", "o");
		value = value.replaceAll("õ", "o");
		value = value.replaceAll("ö", "o");
		value = value.replaceAll("ō", "o");
		value = value.replaceAll("ŏ", "o");
		value = value.replaceAll("ő", "o");
		value = value.replaceAll("ò", "o");

		if (lowerCase) {
			value = value.replaceAll("Ò", "o");
			value = value.replaceAll("Ó", "o");
			value = value.replaceAll("Ô", "o");
			value = value.replaceAll("Õ", "o");
			value = value.replaceAll("Ö", "o");
			value = value.replaceAll("Ō", "o");
			value = value.replaceAll("Ŏ", "o");
			value = value.replaceAll("Ő", "o");
		} else {
			value = value.replaceAll("Ò", "O");
			value = value.replaceAll("Ó", "O");
			value = value.replaceAll("Ô", "O");
			value = value.replaceAll("Õ", "O");
			value = value.replaceAll("Ö", "O");
			value = value.replaceAll("Ō", "O");
			value = value.replaceAll("Ŏ", "O");
			value = value.replaceAll("Ő", "O");
		}

		value = value.replaceAll("ù", "u");
		value = value.replaceAll("ú", "u");
		value = value.replaceAll("û", "u");
		value = value.replaceAll("ü", "u");
		value = value.replaceAll("ũ", "u");
		value = value.replaceAll("ū", "u");
		value = value.replaceAll("ŭ", "u");
		value = value.replaceAll("ů", "u");

		if (lowerCase) {
			value = value.replaceAll("Ù", "u");
			value = value.replaceAll("Ú", "u");
			value = value.replaceAll("Û", "u");
			value = value.replaceAll("Ü", "u");
			value = value.replaceAll("Ũ", "u");
			value = value.replaceAll("Ū", "u");
			value = value.replaceAll("Ŭ", "u");
			value = value.replaceAll("Ů", "u");
		} else {
			value = value.replaceAll("Ù", "U");
			value = value.replaceAll("Ú", "U");
			value = value.replaceAll("Û", "U");
			value = value.replaceAll("Ü", "U");
			value = value.replaceAll("Ũ", "U");
			value = value.replaceAll("Ū", "U");
			value = value.replaceAll("Ŭ", "U");
			value = value.replaceAll("Ů", "U");
		}

		value = value.replaceAll("ç", "c");

		if (lowerCase) {
			value = value.replaceAll("Ç", "c");
		} else {
			value = value.replaceAll("Ç", "C");
		}

		value = value.replaceAll("ñ", "n");

		if (lowerCase) {
			value = value.replaceAll("Ñ", "n");
		} else {
			value = value.replaceAll("Ñ", "N");
		}

		value = value.replaceAll("-", "");
		value = value.replaceAll("_", "");
		value = value.replaceAll(",", "");
		value = value.replaceAll(";", "");
		value = value.replaceAll("'", "");
		value = value.replaceAll("\"", "");
		value = value.replaceAll("\\\\", "");
		value = value.replaceAll("/", "");

		if (lowerCase) {
			return value.toLowerCase();
		}

		return value;

	}

}
