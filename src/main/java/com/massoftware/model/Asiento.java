package com.massoftware.model;

import java.sql.Timestamp;
import java.util.Date;

import com.massoftware.frontend.ui.util.xmd.annotation.ClassArticleLabelInPluralAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassArticleLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassPluralLabelAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassTableMSAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldAutoMaxValueAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldColumnMetaDataAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldColumnsAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldLabelAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldMaxLengthAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldMaxValueIntegerAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldMinValueIntegerAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldNameMSAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldNotVisibleCopy;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldNotVisibleInsert;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldReadOnly;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldRequiredAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldSubNameFKAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldUniqueAnont;

@ClassLabelInTheSingularAnont(value = "Asiento contable")
@ClassPluralLabelAnont(value = "Asientos contables")
@ClassArticleLabelInTheSingularAnont(value = "el")
@ClassArticleLabelInPluralAnont(value = "los")
// @ClassFormSourceAnont(value = "Talonario")
@ClassTableMSAnont(nameTableDB = "[Contabilidad]")
public class Asiento extends EntityId implements Comparable<Asiento> {

	@FieldLabelAnont(value = "Ejercicio")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)	
	@FieldSubNameFKAnont(value = "ejercicio")
	// @FieldNameMSAnont(nameAttDB = "[EJERCICIO]", classAttDB = Integer.class)
	// @FieldCBBox(attName = "ejercicio")
	private EjercicioContable ejercicioContable;

	@FieldLabelAnont(value = "Número")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 8)
	@FieldColumnsAnont(value = 8)
	@FieldMinValueIntegerAnont(value = 1)
	@FieldMaxValueIntegerAnont(value = Integer.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 70, simpleStringTraslateFilterMode = "STARTS_WITCH")
	@FieldUniqueAnont()
	@FieldAutoMaxValueAnont()
	@FieldNameMSAnont(nameAttDB = "[NUMEROASIENTO]", classAttDB = Integer.class)
	// @FieldReadOnly()
	private Integer numero;

	@FieldLabelAnont(value = "Fecha")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 90, pidFilteringStart = true, ascOrderByStart = false)
	@FieldNameMSAnont(nameAttDB = "[FECHASQL]", classAttDB = Timestamp.class)
	// @FieldTimestamp()
	// @FieldUniqueAnont()
	private Date fecha;

	@FieldLabelAnont(value = "Detalle")
	@FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 60)
	@FieldColumnsAnont(value = 45)
	@FieldColumnMetaDataAnont(attSize = 215)
	// @FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[DETALLE]", classAttDB = String.class)
	private String detalle;

	@FieldLabelAnont(value = "Sucursal")
	// @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "codigo")
	// @FieldNameMSAnont(nameAttDB = "[SUCURSAL]", classAttDB = Integer.class)
	// @FieldCBBox(attName = "ejercicio")
	@FieldReadOnly()
	@FieldNotVisibleInsert()
	@FieldNotVisibleCopy()
	private Sucursal sucursal;

	@FieldLabelAnont(value = "Módulo")
	// @FieldRequiredAnont()
	@FieldColumnMetaDataAnont(attSize = 80)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[MODULO]", classAttDB = Integer.class)
	@FieldNotVisibleInsert()
	@FieldNotVisibleCopy()
	private AsientoModulo asientoModulo;

	@FieldLabelAnont(value = "Lote")
	// @FieldRequiredAnont()
	@FieldMaxLengthAnont(value = 5)
	@FieldColumnsAnont(value = 5)
	@FieldMinValueIntegerAnont(value = 0)
	@FieldMaxValueIntegerAnont(value = Short.MAX_VALUE)
	@FieldColumnMetaDataAnont(attSize = 70)
	// @FieldUniqueAnont()
	@FieldNameMSAnont(nameAttDB = "[NROLOTE]", classAttDB = Integer.class)
	// @FieldAutoMaxValueAnont()
	@FieldReadOnly()
	@FieldNotVisibleInsert()
	@FieldNotVisibleCopy()
	private Integer lote;

	@FieldLabelAnont(value = "Minuta contable")
	@FieldRequiredAnont()
	@FieldColumnMetaDataAnont(hidden = true)
	@FieldSubNameFKAnont(value = "codigo")
	@FieldNameMSAnont(nameAttDB = "[MINUTACONTABLE]", classAttDB = Integer.class)
	private MinutaContable minutaContable;

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public EjercicioContable getEjercicioContable() {
		return ejercicioContable;
	}

	public void setEjercicioContable(EjercicioContable ejercicioContable) {
		if (ejercicioContable != null && ejercicioContable.getId() == null) {
			return;
		}
		this.ejercicioContable = ejercicioContable;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDetalle() {
		detalle = formatValue(detalle);
		return detalle;
	}

	public void setDetalle(String detalle) {
		detalle = formatValue(detalle);
		this.detalle = detalle;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		if (sucursal != null && sucursal.getId() == null) {
			return;
		}
		this.sucursal = sucursal;
	}

	public AsientoModulo getAsientoModulo() {
		return asientoModulo;
	}

	public void setAsientoModulo(AsientoModulo asientoModulo) {
		if (asientoModulo != null && asientoModulo.getId() == null) {
			return;
		}
		this.asientoModulo = asientoModulo;
	}

	public Integer getLote() {
		return lote;
	}

	public void setLote(Integer lote) {
		this.lote = lote;
	}

	public MinutaContable getMinutaContable() {
		return minutaContable;
	}

	public void setMinutaContable(MinutaContable minutaContable) {
		if (minutaContable != null && minutaContable.getId() == null) {
			return;
		}
		this.minutaContable = minutaContable;
	}

	@Override
	public String toString() {

		if (this.getEjercicioContable() != null) {
			return "(" + this.getEjercicioContable() + ") (" + this.getNumero()
					+ ") " + this.getDetalle();
		}
		return "(" + this.getNumero() + ") " + this.getDetalle();

	}

	public int compareTo(Asiento o) {

		return this.getNumero().compareTo(o.getNumero());
	}

	public boolean validate() {

		super.validate();

		if (this.ejercicioContable == null) {

			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".ejercicioContable es nulo.");
		} else {
			this.ejercicioContable.validate();
		}

		if (this.getNumero() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".numero es nulo.");
		}

		return true;
	}

}
