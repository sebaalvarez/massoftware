package com.massoftware.model;

import org.cendra.common.model.EntityId;

import com.massoftware.annotation.model.ClassArticleLabelInPluralAnont;
import com.massoftware.annotation.model.ClassArticleLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassLabelInTheSingularAnont;
import com.massoftware.annotation.model.ClassPluralLabelAnont;
import com.massoftware.annotation.model.ClassTableMSAnont;
import com.massoftware.annotation.model.FieldColumnMetaDataAnont;
import com.massoftware.annotation.model.FieldColumnsAnont;
import com.massoftware.annotation.model.FieldLabelAnont;
import com.massoftware.annotation.model.FieldMaxLengthAnont;
import com.massoftware.annotation.model.FieldMaxValueIntegerAnont;
import com.massoftware.annotation.model.FieldMinValueIntegerAnont;
import com.massoftware.annotation.model.FieldNameMSAnont;
import com.massoftware.annotation.model.FieldRequiredAnont;
import com.massoftware.annotation.model.FieldSubNameFKAnont;
import com.massoftware.annotation.model.FieldUniqueAnont;

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Centros de costo - Proyecto")
@ClassPluralLabelAnont(value = "Centros de costos - Proyectos")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Zona")
@ClassTableMSAnont(nameTableDB = "[CentrosDeCosto]")
public class CentroDeCostoProyecto extends EntityId implements Cloneable,
		Comparable<CentroDeCostoProyecto> {

	@FieldLabelAnont(value = "Número")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 9)
	@FieldMaxLengthAnont(value = 9)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Integer.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 120, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NUMERO]", classAttDB = Integer.class)
	private Integer codigo;

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 30)
	@FieldMaxLengthAnont(value = 30)
	@FieldColumnMetaDataAnont(attSize = 250)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;

	@FieldLabelAnont(value = "Tipo")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 200)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[CENTRODECOSTOPROYECTO]", classAttDB = Integer.class)
	private CentroDeCostoProyectoTipo tipo;

	@FieldLabelAnont(value = "Activo", visible = true)
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldNameMSAnont(nameAttDB = "[PROYECTOACTIVO]", classAttDB = Boolean.class)
	private Boolean proyectoActivo;

	@FieldLabelAnont(value = "Comentario")
	// @FieldRequiredAnont(value = true)
	@FieldColumnsAnont(value = 70)
	@FieldMaxLengthAnont(value = 511)	
	@FieldColumnMetaDataAnont(hidden = true)
	// @FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[PROYECTOCOMENTARIO]", classAttDB = String.class)
	private String proyectoComentario;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		nombre = formatValue(nombre);
		return nombre;
	}

	public void setNombre(String nombre) {
		nombre = formatValue(nombre);
		this.nombre = nombre;
	}

	public CentroDeCostoProyectoTipo getTipo() {
		return tipo;
	}

	public void setTipo(CentroDeCostoProyectoTipo tipo) {
		if (tipo != null && tipo.getId() == null) {
			return;
		}
		this.tipo = tipo;
	}

	public Boolean getProyectoActivo() {
		proyectoActivo = this.nullIsFalse(proyectoActivo);
		return proyectoActivo;
	}

	public void setProyectoActivo(Boolean proyectoActivo) {
		proyectoActivo = this.nullIsFalse(proyectoActivo);
		this.proyectoActivo = proyectoActivo;
	}

	public String getProyectoComentario() {
		proyectoComentario = formatValue(proyectoComentario);
		return proyectoComentario;
	}

	public void setProyectoComentario(String proyectoComentario) {
		proyectoComentario = formatValue(proyectoComentario);
		this.proyectoComentario = proyectoComentario;
	}

	@Override
	public CentroDeCostoProyecto clone() throws CloneNotSupportedException {

		CentroDeCostoProyecto other = new CentroDeCostoProyecto();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());
		if (this.getTipo() != null) {
			other.setTipo(getTipo().clone());
		} else {
			other.setTipo(null);
		}
		other.setProyectoActivo(getProyectoActivo());
		other.setProyectoComentario(getProyectoComentario());

		return other;
	}

	@Override
	public int compareTo(CentroDeCostoProyecto other) {

		return this.getCodigo().compareTo(other.getCodigo());
	}

	@Override
	public String toString() {
		return "(" + getCodigo() + ") " + getNombre();

	}

	public boolean validate() throws IllegalArgumentException {

		super.validate();

		if (this.codigo == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".codigo es nulo.");
		}

		if (this.nombre == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".nombre es nulo.");
		}

		if (this.tipo == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".tipo es nulo.");
		}

		return true;
	}

}
