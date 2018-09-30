package com.massoftware.model;

import com.massoftware.frontend.custom.windows.builder.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassPluralLabelAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassTableMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldAutoMaxValueAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldColumnMetaDataAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldColumnsAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldLabelAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMaxLengthAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMaxValueIntegerAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldMinValueIntegerAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldNameMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldReadOnly;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldRequiredAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldSubNameFKAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldUniqueAnont;

@ClassLabelInTheSingularAnont(value = "Parámetros generales")
@ClassPluralLabelAnont(value = "Parámetros generales")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Zona")
@ClassTableMSAnont(nameTableDB = "[Empresa]")
public class Empresa extends EntityId implements Comparable<Empresa> {
	
	
	@FieldLabelAnont(value = "Nro. país")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 5)
	@FieldMaxLengthAnont(value = 3)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = 255)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NROEMPRESA]", classAttDB = Integer.class)
	@FieldAutoMaxValueAnont()
	@FieldReadOnly()
	private Integer codigo;
	
	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 50)
	@FieldMaxLengthAnont(value = 50)
	@FieldColumnMetaDataAnont(attSize = 150)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[EMPRESA]", classAttDB = String.class)
	private String nombre;
	
	@FieldLabelAnont(value = "Firma")	
	@FieldColumnsAnont(value = 50)
	@FieldMaxLengthAnont(value = 50)
	@FieldColumnMetaDataAnont(attSize = 150)	
	@FieldNameMSAnont(nameAttDB = "[FIRMA]", classAttDB = String.class)
	private String firma;
	
	@FieldLabelAnont(value = "Actividad")	
	@FieldColumnsAnont(value = 50)
	@FieldMaxLengthAnont(value = 50)
	@FieldColumnMetaDataAnont(attSize = 150)	
	@FieldNameMSAnont(nameAttDB = "[ACTIVIDAD]", classAttDB = String.class)
	private String actividad;
	
	@FieldLabelAnont(value = "Firma")	
	@FieldColumnsAnont(value = 750)
	@FieldMaxLengthAnont(value = 750)
	@FieldColumnMetaDataAnont(attSize = 750)	
	@FieldNameMSAnont(nameAttDB = "[ACTIVIDADESSECUNDARIAS]", classAttDB = String.class)
	private String otrasActividades;
	
	@FieldLabelAnont(value = "Domicilio")	
	@FieldColumnsAnont(value = 20)
	@FieldMaxLengthAnont(value = 20)
	@FieldColumnMetaDataAnont(attSize = 100)	
	@FieldNameMSAnont(nameAttDB = "[DOMICILIO]", classAttDB = String.class)
	private String domicilio;
	
	@FieldLabelAnont(value = "Código postal")	
	@FieldColumnsAnont(value = 4)
	@FieldMaxLengthAnont(value = 4)
	@FieldColumnMetaDataAnont(attSize = 60)	
	@FieldNameMSAnont(nameAttDB = "[CODIGOPOSTAL]", classAttDB = String.class)
	private String codigoPostal;
	
	@FieldLabelAnont(value = "Ciudad")	
	@FieldColumnsAnont(value = 20)
	@FieldMaxLengthAnont(value = 20)
	@FieldColumnMetaDataAnont(attSize = 100)	
	@FieldNameMSAnont(nameAttDB = "[CIUDAD]", classAttDB = String.class)
	private String ciudad;
	
	@FieldLabelAnont(value = "Departamento")	
	@FieldColumnsAnont(value = 60)
	@FieldMaxLengthAnont(value = 60)
	@FieldColumnMetaDataAnont(attSize = 100)	
	@FieldNameMSAnont(nameAttDB = "[DEPARTAMENTO]", classAttDB = String.class)
	private String departamento;
	
	@FieldLabelAnont(value = "Provincia")	
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "Provincia")
	// @FieldNameMSAnont(nameAttDB = "[CUENTA]", classAttDB = String.class)
	// @FieldCBBox(attName = "ejercicio")
	private Provincia provincia;
	
	@FieldLabelAnont(value = "Teléfono")	
	@FieldColumnsAnont(value = 20)
	@FieldMaxLengthAnont(value = 20)
	@FieldColumnMetaDataAnont(attSize = 100)	
	@FieldNameMSAnont(nameAttDB = "[TELEFONO]", classAttDB = String.class)
	private String telefono;

	@Override
	public int compareTo(Empresa arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}


