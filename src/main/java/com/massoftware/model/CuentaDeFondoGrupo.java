package com.massoftware.model;

import com.massoftware.frontend.ui.util.xmd.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassPluralLabelAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassTableMSAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldColumnMetaDataAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldColumnsAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldLabelAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldMaxLengthAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldMaxValueIntegerAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldMinValueIntegerAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldNameMSAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldRequiredAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldSubNameFKAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldUniqueAnont;

@SuppressWarnings("serial")
@ClassLabelInTheSingularAnont(value = "Grupo")
@ClassPluralLabelAnont(value = "Grupos")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Deposito")
@ClassTableMSAnont(nameTableDB = "[CuentasDeFondosGrupo]")
public class CuentaDeFondoGrupo extends EntityId implements Cloneable,
		Comparable<CuentaDeFondoGrupo> {

	@FieldLabelAnont(value = "Rubro")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 150)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[RUBRO]", classAttDB = Integer.class)
	private CuentaDeFondoRubro cuentaDeFondoRubro;

	@FieldLabelAnont(value = "Grupo")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 80, pidFilteringStart = true)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[GRUPO]", classAttDB = Integer.class)
	private Integer codigo;

	@FieldLabelAnont(value = "Nombre")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 30)
	@FieldColumnsAnont(value = 30)
	@FieldColumnMetaDataAnont(attSize = 450)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NOMBRE]", classAttDB = String.class)
	private String nombre;

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

	public CuentaDeFondoRubro getCuentaDeFondoRubro() {
		return cuentaDeFondoRubro;
	}

	public void setCuentaDeFondoRubro(CuentaDeFondoRubro cuentaDeFondoRubro) {
		this.cuentaDeFondoRubro = cuentaDeFondoRubro;
	}

	@Override
	public CuentaDeFondoGrupo clone() throws CloneNotSupportedException {

		CuentaDeFondoGrupo other = new CuentaDeFondoGrupo();

		other.setId(this.getId());
		if (this.getCuentaDeFondoRubro() != null) {
			other.setCuentaDeFondoRubro(getCuentaDeFondoRubro().clone());
		} else {
			other.setCuentaDeFondoRubro(null);
		}
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());

		return other;
	}

	@Override
	public int compareTo(CuentaDeFondoGrupo o) {

		return this.getCodigo().compareTo(o.getCodigo());
	}

	private boolean _setfullToString = true;

	public void _setfullToString(boolean _setfullToString) {
		this._setfullToString = _setfullToString;
	}

	@Override
	public String toString() {
		if (this._setfullToString) {
			return this.getCuentaDeFondoRubro() + " - " + "(" + formatCodigo()
					+ ") " + getNombre();
		} else {
			return "(" + formatCodigo() + ") " + getNombre();
		}

	}

	private String formatCodigo() {
		String s = "";

		if (getCodigo() != null) {
			s = getCodigo().toString();

			if (s.length() == 1) {
				s = "000" + s;
			} else if (s.length() == 2) {
				s = "00" + s;
			} else if (s.length() == 3) {
				s = "0" + s;
			}
		}

		return s;
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

		return true;
	}

}
