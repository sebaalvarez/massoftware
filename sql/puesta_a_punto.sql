ALTER TABLE CentrosDeCostoContable  
	ADD CONSTRAINT fk_EjerciciosContables  
		FOREIGN KEY (Ejercicio)                              
		REFERENCES EjerciciosContables(Ejercicio);