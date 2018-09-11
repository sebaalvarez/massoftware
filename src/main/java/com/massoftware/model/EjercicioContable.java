package com.massoftware.model;

import java.util.Date;

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
import com.massoftware.frontend.custom.windows.builder.annotation.FieldUniqueAnont;
import com.massoftware.util.FormatDate;

@ClassLabelInTheSingularAnont(value = "ejercicio contable")
@ClassPluralLabelAnont(value = "Ejercicios contables")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[EjerciciosContables]")
public class EjercicioContable extends EntityId implements
		Comparable<EjercicioContable> {

	@FieldLabelAnont(value = "Ejercicio")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 5)
	@FieldMaxLengthAnont(value = 4)
	@FieldMinValueIntegerAnont(value = 1990)
	@FieldMaxValueIntegerAnont(value = 2030)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true, simpleStringTraslateFilterMode = "ENDS_WITCH", ascOrderByStart = false)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[EJERCICIO]", classAttDB = Integer.class)
	@FieldAutoMaxValueAnont()
	@FieldReadOnly()
	private Integer ejercicio;

	@FieldLabelAnont(value = "Apertura")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 150)
	@FieldNameMSAnont(nameAttDB = "[FECHAAPERTURASQL]", classAttDB = Date.class)
	private Date fechaApertura;

	@FieldLabelAnont(value = "Cierre")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 150)
	@FieldNameMSAnont(nameAttDB = "[FECHACIERRESQL]", classAttDB = Date.class)
	private Date fechaCierre;

	@FieldLabelAnont(value = "Cerrado")
	@FieldColumnMetaDataAnont(attSize = 150)
	@FieldNameMSAnont(nameAttDB = "[EJERCICIOCERRADO]", classAttDB = Boolean.class)
	private Boolean ejercicioCerrado;

	@FieldLabelAnont(value = "Módulos")
	@FieldColumnMetaDataAnont(attSize = 150)
	@FieldNameMSAnont(nameAttDB = "[EJERCICIOCERRADOMODULOS]", classAttDB = Boolean.class)
	private Boolean ejercicioCerradoModulos;

	@FieldLabelAnont(value = "Comentario")
	// @FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 70)
	@FieldMaxLengthAnont(value = 255)
	@FieldColumnMetaDataAnont(hidden = true)
	// @FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[COMENTARIO]", classAttDB = String.class)
	private String comentario;

	// public EjercicioContable(Object[] row) {
	// setterByArray(row);
	// }

	// public void setId(String id) {
	// id = formatValue(id);
	// if (id != null) {
	// this.setEjercicio(new Integer(id));
	// } else {
	// this.setEjercicio(null);
	// }
	// }

	public Integer getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
		// if (this.ejercicio != null) {
		// super.setId(this.ejercicio.toString());
		// } else {
		// super.setId(null);
		// }
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Boolean getEjercicioCerrado() {
		ejercicioCerrado = this.nullIsFalse(ejercicioCerrado);
		return ejercicioCerrado;
	}

	public void setEjercicioCerrado(Boolean ejercicioCerrado) {
		ejercicioCerrado = this.nullIsFalse(ejercicioCerrado);
		this.ejercicioCerrado = ejercicioCerrado;
	}

	public Boolean getEjercicioCerradoModulos() {
		ejercicioCerradoModulos = this.nullIsFalse(ejercicioCerradoModulos);
		return ejercicioCerradoModulos;
	}

	public void setEjercicioCerradoModulos(Boolean ejercicioCerradoModulos) {
		ejercicioCerradoModulos = this.nullIsFalse(ejercicioCerradoModulos);
		this.ejercicioCerradoModulos = ejercicioCerradoModulos;
	}

	public String getComentario() {
		comentario = formatValue(comentario);
		return comentario;
	}

	public void setComentario(String comentario) {
		comentario = formatValue(comentario);
		this.comentario = comentario;
	}

	// public void setterByArray(Object[] row) {
	//
	// int column = 0;
	//
	// if (row[column] != null) {
	// this.setEjercicio((Integer) row[column]);
	// }
	//
	// column++;
	//
	// if (row[column] != null) {
	// this.setComentario((String) row[column]);
	// }
	//
	// column++;
	//
	// if (row[column] != null) {
	//
	// Timestamp t = (Timestamp) row[column];
	// Date d = new Date(t.getTime());
	//
	// this.setFechaApertura(d);
	// }
	//
	// column++;
	//
	// if (row[column] != null) {
	// Timestamp t = (Timestamp) row[column];
	// Date d = new Date(t.getTime());
	//
	// this.setFechaCierre(d);
	// }
	//
	// column++;
	//
	// if (row[column] != null) {
	// Short s = (Short) row[column];
	// Boolean b = (s != null && s == 1);
	//
	// this.setEjercicioCerrado(b);
	// }
	//
	// column++;
	//
	// if (row[column] != null) {
	// Short s = (Short) row[column];
	// Boolean b = (s != null && s == 1);
	//
	// this.setEjercicioCerradoModulos(b);
	// }
	// }

	// @Override
	// public EjercicioContable clone() throws CloneNotSupportedException {
	// EjercicioContable other = new EjercicioContable();
	//
	// other.setId(this.getId());
	// other.setEjercicio(this.getEjercicio());
	// if (this.getFechaApertura() != null) {
	// other.setFechaApertura((Date) this.getFechaApertura().clone());
	// } else {
	// other.setFechaApertura(null);
	// }
	// if (this.getFechaCierre() != null) {
	// other.setFechaCierre((Date) this.getFechaCierre().clone());
	// } else {
	// other.setFechaCierre(null);
	// }
	// other.setEjercicioCerrado(this.getEjercicioCerrado());
	// other.setEjercicioCerradoModulos(this.getEjercicioCerradoModulos());
	// other.setComentario(this.getComentario());
	//
	// return other;
	// }

	public int compareTo(EjercicioContable o) {

		return this.getEjercicio().compareTo(o.getEjercicio());

	}

	private boolean _setfullToString = false;

	public void _setfullToStringx(boolean _setfullToString) {
		this._setfullToString = _setfullToString;
	}

	@Override
	public String toString() {

		if (this._setfullToString) {
			return toStringFull();
		} else {
			return this.getEjercicio().toString();
		}

	}

	public String toStringFull() {
		return this.getEjercicio().toString() + " [ "
				+ FormatDate.format(fechaApertura) + " - "
				+ FormatDate.format(fechaCierre) + " ]";
	}

	public boolean validate() throws IllegalArgumentException {

		super.validate();

//		if (this.ejercicio == null || this.ejercicio == 0) {
//			throw new IllegalArgumentException(this.getClass()
//					.getCanonicalName() + ".ejercicio es nulo o igual a cero.");
//		}
//
//		if (this.fechaApertura == null) {
//			throw new IllegalArgumentException(this.getClass()
//					.getCanonicalName() + ".fechaApertura es nulo.");
//		}
//
//		if (this.fechaCierre == null) {
//			throw new IllegalArgumentException(this.getClass()
//					.getCanonicalName() + ".fechaCierre es nulo.");
//		}
//
//		if (this.ejercicioCerrado == null) {
//			throw new IllegalArgumentException(this.getClass()
//					.getCanonicalName() + ".ejercicioCerrado es nulo.");
//		}
//
//		if (this.ejercicioCerradoModulos == null) {
//			throw new IllegalArgumentException(this.getClass()
//					.getCanonicalName() + ".ejercicioCerradoModulos es nulo.");
//		}

		return true;
	}

}
