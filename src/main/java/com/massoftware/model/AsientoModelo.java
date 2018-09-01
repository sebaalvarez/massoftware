package com.massoftware.model;

import com.massoftware.frontend.util.builder.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.util.builder.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.util.builder.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.util.builder.annotation.ClassPluralLabelAnont;
import com.massoftware.frontend.util.builder.annotation.ClassTableMSAnont;
import com.massoftware.frontend.util.builder.annotation.FieldAutoMaxValueAnont;
import com.massoftware.frontend.util.builder.annotation.FieldColumnMetaDataAnont;
import com.massoftware.frontend.util.builder.annotation.FieldColumnsAnont;
import com.massoftware.frontend.util.builder.annotation.FieldLabelAnont;
import com.massoftware.frontend.util.builder.annotation.FieldMaxLengthAnont;
import com.massoftware.frontend.util.builder.annotation.FieldMaxValueIntegerAnont;
import com.massoftware.frontend.util.builder.annotation.FieldMinValueIntegerAnont;
import com.massoftware.frontend.util.builder.annotation.FieldNameMSAnont;
import com.massoftware.frontend.util.builder.annotation.FieldReadOnly;
import com.massoftware.frontend.util.builder.annotation.FieldRequiredAnont;
import com.massoftware.frontend.util.builder.annotation.FieldUniqueAnont;

@ClassLabelInTheSingularAnont(value = "Asiento modelo")
@ClassPluralLabelAnont(value = "Asientos modelo")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Talonario")
@ClassTableMSAnont(nameTableDB = "[AsientosModelos]")
public class AsientoModelo extends EntityId implements
		Comparable<AsientoModelo> {

	// private EjercicioContable ejercicioContable;
	
	@FieldLabelAnont(value = "Asiento")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 120, pidFilteringStart = true, simpleStringTraslateFilterMode = "STARTS_WITCH")
	@FieldUniqueAnont()
	@FieldAutoMaxValueAnont()
	@FieldNameMSAnont(nameAttDB = "[ASIENTOMODELO]", classAttDB = Integer.class)
	@FieldReadOnly()
	private Integer numero;
	
	@FieldLabelAnont(value = "Denominación")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 25)
	@FieldColumnsAnont(value = 25)
	@FieldColumnMetaDataAnont(attSize = 250)
	@FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[DENOMINACION]", classAttDB = String.class)
	private String denominacion;

	// private List<AsientoModeloItem> cuentasContables = new
	// ArrayList<AsientoModeloItem>();

	// public EjercicioContable getEjercicioContable() {
	// return ejercicioContable;
	// }
	//
	// public void setEjercicioContable(EjercicioContable ejercicioContable) {
	// if (ejercicioContable != null && ejercicioContable.getId() == null) {
	// return;
	// }
	// this.ejercicioContable = ejercicioContable;
	// }

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getDenominacion() {
		denominacion = formatValue(denominacion);
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		denominacion = formatValue(denominacion);
		this.denominacion = denominacion;
	}

	// @SuppressWarnings("unchecked")
	// public List<AsientoModeloItem> getCuentasContables() {
	// cuentasContables = formatValues(cuentasContables);
	// return cuentasContables;
	// }
	//
	// @SuppressWarnings("unchecked")
	// public void setCuentasContables(List<AsientoModeloItem> cuentasContables)
	// {
	// cuentasContables = formatValues(cuentasContables);
	// this.cuentasContables = cuentasContables;
	// }
	//
	// public boolean addCuentaContable(AsientoModeloItem e) {
	// return cuentasContables.add(e);
	// }

	@Override
	public String toString() {

		return "(" + this.getNumero() + ") " + this.getDenominacion();

	}

	// @Override
	// public AsientoModelo clone() throws CloneNotSupportedException {
	// AsientoModelo other = new AsientoModelo();
	//
	// other.setId(this.getId());
	// if (this.getEjercicioContable() != null) {
	// other.setEjercicioContable(this.getEjercicioContable().clone());
	// } else {
	// other.setEjercicioContable(null);
	// }
	// other.setNumero(getNumero());
	// other.setDenominacion(this.getDenominacion());
	// if (this.getCuentasContables() != null) {
	// for (AsientoModeloItem item : this.getCuentasContables()) {
	// other.addCuentaContable(item.clone());
	// }
	// }
	//
	// return other;
	// }

	public int compareTo(AsientoModelo o) {

		return this.getNumero().compareTo(o.getNumero());
	}

	public boolean validate() {

		super.validate();

		// if (this.getEjercicioContable() == null) {
		//
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName() + ".ejercicioContable es nulo.");
		// } else {
		// this.getEjercicioContable().validate();
		// }

		if (this.getNumero() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".numero es nulo.");
		}

		if (this.getNumero() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".numero es nulo.");
		}

		if (this.getDenominacion() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".denominacion es nulo.");
		}

		// if (this.getCuentasContables() == null) {
		// throw new IllegalArgumentException(this.getClass()
		// .getCanonicalName() + ".cuentasContables es nulo.");
		// } else if (this.getCuentasContables().size() == 0) {
		// // throw new IllegalArgumentException(this.getClass()
		// // .getCanonicalName() + ".cuentasContables.size() es 0.");
		// } else if (this.getCuentasContables().size() > 0) {
		// for (AsientoModeloItem item : cuentasContables) {
		// item.validate();
		// }
		// }

		return true;
	}

}
