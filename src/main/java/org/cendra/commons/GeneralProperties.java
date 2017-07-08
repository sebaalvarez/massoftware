package org.cendra.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GeneralProperties {

	private final static String MSG_1 = "Error al levantar las propiedades de configuración.";

	public String urlFiles; // URL de Repositorio de Archivos
	public Properties properties;

	public String getUrlFiles() {
		return urlFiles;
	}

	public void setUrlFiles(String urlFiles) {
		if (urlFiles == null) {
			urlFiles = "/opt/yacare_api";
		}
		this.urlFiles = urlFiles;

	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Properties loadx() {
		return load(urlFiles);
	}

	public Properties load(String urlFiles) {

		try {

			setUrlFiles(urlFiles);
			urlFiles = this.getUrlFiles();

			String urlFilesProperties = urlFiles + File.separatorChar
					+ "yacare.properties";

//			System.out.println("\n[..] Leyendo el archivo de configuración "
//					+ urlFiles);
//
//			System.out.println("\n[OK] Archivo de configuración "
//					+ urlFilesProperties + " levantado.");
//
//			System.out
//					.println("\n[==================================== START - CONTENIDO DEL ARCHIVO DE CONFIGURACION ===================================================]\n\n"
//
//							+ readFilePlainText(urlFilesProperties)
//
//							+ "\n[====================================  END - CONTENIDO DEL ARCHIVO DE CONFIGURACION ===================================================]\n");

			if (properties == null) {
				properties = new Properties();
			}

			properties.load(new FileInputStream(urlFilesProperties));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(MSG_1, e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(MSG_1, e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(MSG_1, e);
		}

		return properties;
	}

	public String readFilePlainText(String path) throws Exception {
		// path = path.replace('/', File.separatorChar);
		String txt = "";
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File(path);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;
			while ((linea = br.readLine()) != null)
				txt += linea + "\n";
			// System.out.println(linea);

		} catch (Exception e) {
			throw e;
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				throw e2;
			}
		}

		return txt;
	}

}
