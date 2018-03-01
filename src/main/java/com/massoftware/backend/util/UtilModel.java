package com.massoftware.backend.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class UtilModel {

	@SuppressWarnings({ "rawtypes" })
	public List<Class> getObjectsClassModel(String pathSrc, String packageModel)
			throws ClassNotFoundException {

		List<Class> classModel = new ArrayList<Class>();

		File folderJavaModel = new File(pathSrc + File.separatorChar
				+ packageModel.replace(".", File.separatorChar + ""));
		File[] filesJavaClass = folderJavaModel.listFiles();

		for (File fileJavaClass : filesJavaClass) {

			String nameJavaClass = fileJavaClass.getName().split("[.]")[0];
			Class objClass = Class.forName(packageModel + "." + nameJavaClass);
			classModel.add(objClass);
		}

		return classModel;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public List<Class> getClassDependencies(List<Class> objectsClass,
			Class objClassPattern) throws ClassNotFoundException {

		List<Class> classNameDependencies = new ArrayList<Class>();

		for (Class objClass : objectsClass) {

			Field[] fields = objClass.getDeclaredFields();

			for (Field field : fields) {
				if (objClassPattern.equals(field.getType())) {
					classNameDependencies.add(objClass);
					break;
				}
			}
		}

		return classNameDependencies;
	}

}
