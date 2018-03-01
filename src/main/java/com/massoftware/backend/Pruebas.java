package com.massoftware.backend;

import java.io.File;
import java.lang.reflect.Field;

public class Pruebas {

	public static void main(String[] args) throws Exception {

		String pathSrc = "D:\\dev\\source\\massoftware\\src\\main\\java";
		String packageModel = "com.massoftware.model";
		String packageBO = "com.massoftware.backend.bo";
		String classNamePattern = "EjercicioContable";

		File folderJavaModel = new File(pathSrc + File.separatorChar
				+ packageModel.replace(".", File.separatorChar + ""));
		File[] filesJavaClass = folderJavaModel.listFiles();
		Class objClassPattern = Class.forName(packageModel + "."
				+ classNamePattern);

		for (File fileJavaClass : filesJavaClass) {
			String nameJavaClass = fileJavaClass.getName().split("[.]")[0];

			Class objClass = Class.forName(packageModel + "." + nameJavaClass);

			Field[] fields = objClass.getDeclaredFields();
			for (Field field : fields) {
				if (objClassPattern.equals(field.getType())) {
					Class objClassBO =  Class.forName(packageBO + "." + nameJavaClass + "BO");					
//					Object objBO = objClassBO.newInstance();
					
					System.out.println(objClassBO);
					
					
//					System.out.println(objClass + " " + field.getType());
//					System.out.println(packageBO + "." + field.getType());
				}
			}

		}

	}

}
