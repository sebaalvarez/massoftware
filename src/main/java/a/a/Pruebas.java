package a.a;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.massoftware.model.Zona;

public class Pruebas {

	public static void main(String[] args) throws Exception {
		
		Timestamp t = new Timestamp(System.currentTimeMillis());
		System.out.println(t);
		Date d = new Date(t.getTime());
		System.out.println(d);
		Timestamp tt = new Timestamp(d.getTime());
		System.out.println(tt);
		
		System.exit(0);
		
		Zona z = new Zona();
		z.setId("123");
		z.setCodigo("codigo");
		z.setNombre("nombre");
		z.setBonificacion(new BigDecimal("2.344444"));
		z.setRecargo(new BigDecimal("2.3"));
		
		Object other = z.clone();
		
		System.out.println(other);
		
		System.out.println("END");
		
		
		System.exit(0);

		// ////////////////////////////////////////////////////

		String errorMessage = "XXXXXXXXXX";
		BigDecimal minValue =  new BigDecimal("-9999999");
		BigDecimal maxValue = new BigDecimal("99999999");

		
		MyBigDecimalRangeValidator v = new  MyBigDecimalRangeValidator(errorMessage, minValue,
				maxValue);
		
		boolean b = v.isValidValue(new BigDecimal("999999900"));
		
		System.out.println(b);

		System.exit(0);

		// ////////////////////////////////////////////////////

		// create 2 BigDecimal objects
		BigDecimal bg1, bg2;

		bg1 = new BigDecimal("99999999");
		// bg1 = new BigDecimal("-9999999");
		bg2 = new BigDecimal("9999999.00");

		// bg1 = new BigDecimal("3");
		// bg2 = new BigDecimal("3");

		// create int object
		int res;

		res = bg1.compareTo(bg2); // compare bg1 with bg2

		System.out.println("res " + res);

		String str1 = "Ambos valores son iguales " + bg1 + " = " + bg2;
		String str2 = "Primer valor es mayor " + bg1 + " > " + bg2;
		String str3 = "Segundo valor es mayor " + bg1 + " < " + bg2;

		if (res == 0)
			System.out.println(str1);
		else if (res == 1)
			System.out.println(str2);
		else if (res == -1)
			System.out.println(str3);

		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");

		System.exit(0);

		// ////////////////////////////////////////////////////

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
					Class objClassBO = Class.forName(packageBO + "."
							+ nameJavaClass + "BO");
					// Object objBO = objClassBO.newInstance();

					System.out.println(objClassBO);

					// System.out.println(objClass + " " + field.getType());
					// System.out.println(packageBO + "." + field.getType());
				}
			}

		}

	}

}
