USE [VetaroRep]
GO


/*
ALTER TABLE [dbo].[CentrosDeCostoContable]  
	ADD CONSTRAINT fk_EjerciciosContables  
		FOREIGN KEY (Ejercicio)                              
		REFERENCES EjerciciosContables(Ejercicio);
		
-- ALTER TABLE [dbo].[PuntoDeEquilibrio]  
-- 	ADD CONSTRAINT fk_EjerciciosContables_PuntoDeEquilibrio  
-- 		FOREIGN KEY (EJERCICIO)                              
-- 		REFERENCES EjerciciosContables(Ejercicio);	

ALTER TABLE [dbo].[PlanDeCuentas]  
	ADD CONSTRAINT fk_PlanDeCuentas_EjerciciosContables  
		FOREIGN KEY (Ejercicio)                              
		REFERENCES EjerciciosContables(Ejercicio);		
		
*/
  
ALTER TABLE [dbo].[SSECUR_User] ADD DEFAULT_EJERCICIO_CONTABLE int NULL;  

/*
ALTER TABLE [dbo].[SSECUR_User] 
	ADD CONSTRAINT fk_EjerciciosContables_SSECUR_User  
		FOREIGN KEY (DEFAULT_EJERCICIO_CONTABLE)                              
		REFERENCES EjerciciosContables(Ejercicio);
*/


ALTER TABLE [dbo].[EjerciciosContables] ADD id CHAR(36) NULL;  
UPDATE [dbo].[EjerciciosContables]  SET [id] = CAST([EjerciciosContables].[EJERCICIO] AS VARCHAR);

ALTER TABLE [dbo].[SSECUR_User] ADD id CHAR(36) NULL;  
UPDATE [dbo].[SSECUR_User]  SET [id] = CAST([SSECUR_User].[NO] AS VARCHAR);
		
		
-- VISTAS

-------------------------------------------------------------
		
	CREATE VIEW [dbo].[vEjercicioContable] AS

	SELECT	'com.massoftware.model.EjercicioContable' AS ClassEjercicioContable
			,[EjerciciosContables].[id]  AS id
			,[EjerciciosContables].[EJERCICIO] AS ejercicio
			,[EjerciciosContables].[COMENTARIO] AS comentario
			,[EjerciciosContables].[FECHAAPERTURASQL] AS fechaApertura
			,[EjerciciosContables].[FECHACIERRESQL] AS fechaCierre
			,[EjerciciosContables].[EJERCICIOCERRADO] AS ejercicioCerrado
			,[EjerciciosContables].[EJERCICIOCERRADOMODULOS] AS ejercicioCerradoModulos
	FROM	[dbo].[EjerciciosContables];
	
	-- SELECT * FROM VetaroRep.dbo.[vEjercicioContable] ;
	-- SELECT * FROM VetaroRep.dbo.[vEjercicioContable] WHERE CAST(ejercicio AS VARCHAR)  LIKE CONCAT('%', CAST(12 AS VARCHAR)) ORDER BY ejercicio DESC;

-------------------------------------------------------------

	CREATE VIEW [dbo].[vUsuario] AS

	 SELECT	'com.massoftware.model.Usuario' AS ClassUsuario
			,[SSECUR_User].[id]  AS id
			,[SSECUR_User].[NO] AS numero
			,[SSECUR_User].[LASTNAME] AS nombre
			--,[SSECUR_User].[DEFAULT_EJERCICIO_CONTABLE]
				,vEjercicioContable.ejercicio AS ejercicioContable_ejercicio		
				,vEjercicioContable.fechaApertura AS ejercicioContable_fechaApertura
				,vEjercicioContable.fechaCierre AS ejercicioContable_fechaCierre
				,vEjercicioContable.ejercicioCerrado AS ejercicioContable_ejercicioCerrado
				,vEjercicioContable.ejercicioCerradoModulos AS ejercicioContable_ejercicioCerradoModulos
				,vEjercicioContable.comentario AS ejercicioContable_comentario
	FROM	[dbo].[SSECUR_User] 
	LEFT JOIN	[dbo].[vEjercicioContable] 
		ON [dbo].[vEjercicioContable].EJERCICIO = [dbo].[SSECUR_User].[DEFAULT_EJERCICIO_CONTABLE];

	-- SELECT * FROM VetaroRep.dbo.[vUsuario] ;
	-- SELECT * FROM VetaroRep.dbo.vUsuario WHERE nombre = 'Administrador';

-------------------------------------------------------------
	
	CREATE VIEW [dbo].[v_CentroDeCostoContable] AS        

	SELECT	'com.massoftware.model.CentroDeCostoContable' AS ClassCentroDeCostoContable
			,[CentrosDeCostoContable].[CENTRODECOSTOCONTABLE] AS centroDeCostoContable
			,[CentrosDeCostoContable].[NOMBRE] AS nombre
			,[CentrosDeCostoContable].[ABREVIATURA] AS abreviatura
			--,[CentrosDeCostoContable].[EJERCICIO] 
				,v_EjercicioContable.ejercicio AS ejercicioContable_ejercicio		
				,v_EjercicioContable.fechaApertura AS ejercicioContable_fechaApertura
				,v_EjercicioContable.fechaCierre AS ejercicioContable_fechaCierre
				,v_EjercicioContable.ejercicioCerrado AS ejercicioContable_ejercicioCerrado
				,v_EjercicioContable.ejercicioCerradoModulos AS ejercicioContable_ejercicioCerradoModulos
				,v_EjercicioContable.comentario AS ejercicioContable_comentario
			--,[CentrosDeCostoContable].[PRUEBA1] 
			--,[CentrosDeCostoContable].[PRUEBA]
	FROM [dbo].[CentrosDeCostoContable]
	LEFT JOIN	[dbo].[v_EjercicioContable]
		ON [dbo].[v_EjercicioContable].[ejercicio] = [dbo].[CentrosDeCostoContable].[EJERCICIO];
		
-------------------------------------------------------------
		
	CREATE VIEW [dbo].[v_PuntoDeEquilibrio] AS        

	SELECT	'com.massoftware.model.PuntoDeEquilibrio' AS ClassPuntoDeEquilibrio
			,[PuntoDeEquilibrio].[PUNTODEEQUILIBRIO] AS puntoDeEquilibrio
			,[PuntoDeEquilibrio].[NOMBRE] AS nombre
			
			--,[PuntoDeEquilibrio].[TIPO] AS tipo
			,CAST([PuntoDeEquilibrio].[TIPO] AS tinyint) AS tipo_tipo
			,CASE    
				WHEN [PuntoDeEquilibrio].[TIPO] = 1 THEN 'Individual'
				WHEN [PuntoDeEquilibrio].[TIPO] = 2 THEN 'Costo de ventas'
				WHEN [PuntoDeEquilibrio].[TIPO] = 3 THEN 'Utilidad bruta'
				WHEN [PuntoDeEquilibrio].[TIPO] = 4 THEN 'Resultados por sección'
				WHEN [PuntoDeEquilibrio].[TIPO] = 5 THEN 'Resultados operativos'
				ELSE CAST(null AS VARCHAR)								
			END AS tipo_nombre


			--,[PuntoDeEquilibrio].[EJERCICIO]   	
				,v_EjercicioContable.ejercicio AS ejercicioContable_ejercicio		
				,v_EjercicioContable.fechaApertura AS ejercicioContable_fechaApertura
				,v_EjercicioContable.fechaCierre AS ejercicioContable_fechaCierre
				,v_EjercicioContable.ejercicioCerrado AS ejercicioContable_ejercicioCerrado
				,v_EjercicioContable.ejercicioCerradoModulos AS ejercicioContable_ejercicioCerradoModulos
				,v_EjercicioContable.comentario AS ejercicioContable_comentario
	FROM [dbo].[PuntoDeEquilibrio]
	LEFT JOIN	[dbo].[v_EjercicioContable]
		ON [dbo].[v_EjercicioContable].[ejercicio] = [dbo].[PuntoDeEquilibrio].[EJERCICIO];  	

-------------------------------------------------------------

	CREATE VIEW [dbo].[v_PlanDeCuenta] AS 
	
	SELECT 'com.massoftware.model.PlanDeCuenta' AS ClassPlanDeCuenta 
			,[PlanDeCuentas].[CUENTACONTABLE] AS cuentaContable
			,[PlanDeCuentas].[CUENTAINTEGRADORA] AS integra
			--,[PlanDeCuentas].[C1]
			--,[PlanDeCuentas].[C2]
			--,[PlanDeCuentas].[C3]
			--,[PlanDeCuentas].[C4]
			--,[PlanDeCuentas].[C5]
			--,[PlanDeCuentas].[C6]
			,[PlanDeCuentas].[CUENTADEJERARQUIAIND] AS cuentaDeJerarquia
			,[PlanDeCuentas].[NOMBRE] AS nombre
			,CASE    
				WHEN [PlanDeCuentas].[IMPUTABLE] = 'S' THEN 1
				ELSE 0 
			END AS imputable
			--,[PlanDeCuentas].[IMPUTABLE] AS imputable		
			,[PlanDeCuentas].[APROPIA] AS cuentaConApropiacion
			,CASE    
				WHEN [PlanDeCuentas].[AJUSTEINF] = 'S' THEN 1
				ELSE 0 
			END AS ajustaPorInflacion
			--,[PlanDeCuentas].[AJUSTEINF] AS ajustaPorInflacion
			--,[PlanDeCuentas].[DOORNO]
			,[PlanDeCuentas].[ESTADO] AS estado	
			
			,CAST([PlanDeCuentas].[ESTADO] AS int) AS estado_codigo
			,CASE    
				WHEN [PlanDeCuentas].[COSTODEVENTA] = 1 THEN 'Cuenta fuera de uso'
				WHEN [PlanDeCuentas].[COSTODEVENTA] = 2 THEN 'Cuenta en uso'
				ELSE CAST(null AS VARCHAR)				
			END AS estado_nombre	
			
			--,[PlanDeCuentas].[CENTRODECOSTOCONTABLE] 
			--,dbo.v_CentroDeCostoContable.*					

			,v_CentroDeCostoContable.centroDeCostoContable AS centroDeCostoContable_centroDeCostoContable
			,v_CentroDeCostoContable.nombre AS centroDeCostoContable_nombre
			,v_CentroDeCostoContable.abreviatura AS centroDeCostoContable_abreviatura			
				,v_CentroDeCostoContable.ejercicioContable_ejercicio AS centroDeCostoContable_ejercicioContable_ejercicio		
				,v_CentroDeCostoContable.ejercicioContable_fechaApertura AS centroDeCostoContable_ejercicioContable_fechaApertura
				,v_CentroDeCostoContable.ejercicioContable_fechaCierre AS centroDeCostoContable_ejercicioContable_fechaCierre
				,v_CentroDeCostoContable.ejercicioContable_ejercicioCerrado AS centroDeCostoContable_ejercicioContable_ejercicioCerrado
				,v_CentroDeCostoContable.ejercicioContable_ejercicioCerradoModulos AS centroDeCostoContable_ejercicioContable_ejercicioCerradoModulos
				,v_CentroDeCostoContable.ejercicioContable_comentario AS centroDeCostoContable_ejercicioContable_comentario



			--,[PlanDeCuentas].[PUNTODEEQUILIBRIO]
			--,dbo.v_PuntoDeEquilibrio.*
			,v_PuntoDeEquilibrio.puntoDeEquilibrio AS puntoDeEquilibrio_puntoDeEquilibrio
			,v_PuntoDeEquilibrio.nombre AS puntoDeEquilibrio_nombre
			,v_PuntoDeEquilibrio.tipo_tipo AS puntoDeEquilibrio_tipo_tipo
			,v_PuntoDeEquilibrio.tipo_nombre AS puntoDeEquilibrio_tipo_nombre
				,v_PuntoDeEquilibrio.ejercicioContable_ejercicio AS puntoDeEquilibrio_ejercicioContable_ejercicio		
				,v_PuntoDeEquilibrio.ejercicioContable_fechaApertura AS puntoDeEquilibrio_ejercicioContable_fechaApertura
				,v_PuntoDeEquilibrio.ejercicioContable_fechaCierre AS puntoDeEquilibrio_ejercicioContable_fechaCierre
				,v_PuntoDeEquilibrio.ejercicioContable_ejercicioCerrado AS puntoDeEquilibrio_ejercicioContable_ejercicioCerrado
				,v_PuntoDeEquilibrio.ejercicioContable_ejercicioCerradoModulos AS puntoDeEquilibrio_ejercicioContable_ejercicioCerradoModulos
				,v_PuntoDeEquilibrio.ejercicioContable_comentario AS puntoDeEquilibrio_ejercicioContable_comentario

			,CAST([PlanDeCuentas].[COSTODEVENTA] AS int) AS costoDeVenta_codigo
			,CASE    
				WHEN [PlanDeCuentas].[COSTODEVENTA] = 1 THEN 'No Participa'
				WHEN [PlanDeCuentas].[COSTODEVENTA] = 2 THEN 'Stock'
				WHEN [PlanDeCuentas].[COSTODEVENTA] = 3 THEN 'Compras'
				WHEN [PlanDeCuentas].[COSTODEVENTA] = 4 THEN 'Gastos'
				ELSE CAST(null AS VARCHAR)								
			END AS costoDeVenta_nombre

			,[PlanDeCuentas].[CUENTAAGRUPADORA] AS cuentaAgrupadora
			,CAST([PlanDeCuentas].[PORCENTAJE] AS FLOAT) AS porsentaje
			--,[PlanDeCuentas].[PORCENTAJE] AS porsentaje
			--,[PlanDeCuentas].[EJERCICIO]
				,v_EjercicioContable.ejercicio AS ejercicioContable_ejercicio		
				,v_EjercicioContable.fechaApertura AS ejercicioContable_fechaApertura
				,v_EjercicioContable.fechaCierre AS ejercicioContable_fechaCierre
				,v_EjercicioContable.ejercicioCerrado AS ejercicioContable_ejercicioCerrado
				,v_EjercicioContable.ejercicioCerradoModulos AS ejercicioContable_ejercicioCerradoModulos
				,v_EjercicioContable.comentario AS ejercicioContable_comentario
		
  FROM	[dbo].[PlanDeCuentas]
  LEFT JOIN	dbo.v_CentroDeCostoContable
	ON	dbo.v_CentroDeCostoContable.centroDeCostoContable = [dbo].[PlanDeCuentas].[CENTRODECOSTOCONTABLE] 
	AND	dbo.v_CentroDeCostoContable.ejercicioContable_ejercicio = [dbo].[PlanDeCuentas].[EJERCICIO] 
  LEFT JOIN	dbo.v_PuntoDeEquilibrio
	ON	dbo.v_PuntoDeEquilibrio.puntoDeEquilibrio = [dbo].[PlanDeCuentas].[PUNTODEEQUILIBRIO]
	AND	v_PuntoDeEquilibrio.ejercicioContable_ejercicio = [dbo].[PlanDeCuentas].[EJERCICIO] 
  LEFT JOIN	[dbo].[v_EjercicioContable]
	ON	[dbo].[v_EjercicioContable].[ejercicio] = [dbo].[PlanDeCuentas].[EJERCICIO];		
	
-------------------------------------------------------------

	CREATE VIEW [dbo].[v_PlanDeCuenta2] AS 
	
	SELECT 'com.massoftware.model.PlanDeCuenta' AS ClassPlanDeCuenta 
			,[PlanDeCuentas].[CUENTACONTABLE] AS cuentaContable
			,[PlanDeCuentas].[CUENTAINTEGRADORA] AS integra
			--,[PlanDeCuentas].[C1]
			--,[PlanDeCuentas].[C2]
			--,[PlanDeCuentas].[C3]
			--,[PlanDeCuentas].[C4]
			--,[PlanDeCuentas].[C5]
			--,[PlanDeCuentas].[C6]
			,[PlanDeCuentas].[CUENTADEJERARQUIAIND] AS cuentaDeJerarquia
			,[PlanDeCuentas].[NOMBRE] AS nombre
			,CASE    
				WHEN [PlanDeCuentas].[IMPUTABLE] = 'S' THEN 1
				ELSE 0 
			END AS imputable
			--,[PlanDeCuentas].[IMPUTABLE] AS imputable		
			,[PlanDeCuentas].[APROPIA] AS cuentaConApropiacion
			,CASE    
				WHEN [PlanDeCuentas].[AJUSTEINF] = 'S' THEN 1
				ELSE 0 
			END AS ajustaPorInflacion
			--,[PlanDeCuentas].[AJUSTEINF] AS ajustaPorInflacion
			--,[PlanDeCuentas].[DOORNO]
			,[PlanDeCuentas].[ESTADO] AS estado	
			
			,CAST([PlanDeCuentas].[ESTADO] AS int) AS estado_codigo
			,CASE    
				WHEN [PlanDeCuentas].[COSTODEVENTA] = 1 THEN 'Cuenta fuera de uso'
				WHEN [PlanDeCuentas].[COSTODEVENTA] = 2 THEN 'Cuenta en uso'
				ELSE CAST(null AS VARCHAR)				
			END AS estado_nombre	
			
			--,[PlanDeCuentas].[CENTRODECOSTOCONTABLE] 
			--,dbo.v_CentroDeCostoContable.*					

			,[CentrosDeCostoContable].[CENTRODECOSTOCONTABLE] AS centroDeCostoContable_centroDeCostoContable
			,[CentrosDeCostoContable].[NOMBRE] AS centroDeCostoContable_nombre
			,[CentrosDeCostoContable].[ABREVIATURA] AS centroDeCostoContable_abreviatura
			--,[CentrosDeCostoContable].[EJERCICIO] 
		--		,[EjerciciosContables].[EJERCICIO] AS centroDeCostoContable_ejercicioContable_ejercicio		
		--		,[EjerciciosContables].[FECHAAPERTURASQL] AS centroDeCostoContable_ejercicioContable_fechaApertura
		--		,[EjerciciosContables].[FECHACIERRESQL] AS centroDeCostoContable_ejercicioContable_fechaCierre
		--		,[EjerciciosContables].[EJERCICIOCERRADO] AS centroDeCostoContable_ejercicioContable_ejercicioCerrado
		--		,[EjerciciosContables].[EJERCICIOCERRADOMODULOS] AS centroDeCostoContable_ejercicioContable_ejercicioCerradoModulos
		--		,[EjerciciosContables].[COMENTARIO] AS centroDeCostoContable_ejercicioContable_comentario



			--,[PlanDeCuentas].[PUNTODEEQUILIBRIO]
			--,dbo.v_PuntoDeEquilibrio.*
			,[PuntoDeEquilibrio].[PUNTODEEQUILIBRIO] AS puntoDeEquilibrio_puntoDeEquilibrio
			,[PuntoDeEquilibrio].[NOMBRE] AS puntoDeEquilibrio_nombre
			,CAST([PuntoDeEquilibrio].[TIPO] AS tinyint) AS puntoDeEquilibrio_tipo_tipo
			,CASE    
				WHEN [PuntoDeEquilibrio].[TIPO] = 1 THEN 'Individual'
				WHEN [PuntoDeEquilibrio].[TIPO] = 2 THEN 'Costo de ventas'
				WHEN [PuntoDeEquilibrio].[TIPO] = 3 THEN 'Utilidad bruta'
				WHEN [PuntoDeEquilibrio].[TIPO] = 4 THEN 'Resultados por sección'
				WHEN [PuntoDeEquilibrio].[TIPO] = 5 THEN 'Resultados operativos'
				ELSE CAST(null AS VARCHAR)								
			END AS puntoDeEquilibrio_tipo_nombre
		--		,[EjerciciosContables].[EJERCICIO] AS puntoDeEquilibrio_ejercicioContable_ejercicio		
		--		,[EjerciciosContables].[FECHAAPERTURASQL] AS puntoDeEquilibrio_ejercicioContable_fechaApertura
		--		,[EjerciciosContables].[FECHACIERRESQL] AS puntoDeEquilibrio_ejercicioContable_fechaCierre
		--		,[EjerciciosContables].[EJERCICIOCERRADO] AS puntoDeEquilibrio_ejercicioContable_ejercicioCerrado
		--		,[EjerciciosContables].[EJERCICIOCERRADOMODULOS] AS puntoDeEquilibrio_ejercicioContable_ejercicioCerradoModulos
		--		,[EjerciciosContables].[COMENTARIO] AS puntoDeEquilibrio_ejercicioContable_comentario

			,CAST([PlanDeCuentas].[COSTODEVENTA] AS int) AS costoDeVenta_codigo
			,CASE    
				WHEN [PlanDeCuentas].[COSTODEVENTA] = 1 THEN 'No Participa'
				WHEN [PlanDeCuentas].[COSTODEVENTA] = 2 THEN 'Stock'
				WHEN [PlanDeCuentas].[COSTODEVENTA] = 3 THEN 'Compras'
				WHEN [PlanDeCuentas].[COSTODEVENTA] = 4 THEN 'Gastos'
				ELSE CAST(null AS VARCHAR)								
			END AS costoDeVenta_nombre

			,[PlanDeCuentas].[CUENTAAGRUPADORA] AS cuentaAgrupadora
			,CAST([PlanDeCuentas].[PORCENTAJE] AS FLOAT) AS porsentaje
			--,[PlanDeCuentas].[PORCENTAJE] AS porsentaje
			--,[PlanDeCuentas].[EJERCICIO]
				,[EjerciciosContables].[EJERCICIO] AS ejercicioContable_ejercicio		
				,[EjerciciosContables].[FECHAAPERTURASQL] AS ejercicioContable_fechaApertura
				,[EjerciciosContables].[FECHACIERRESQL] AS ejercicioContable_fechaCierre
				,[EjerciciosContables].[EJERCICIOCERRADO] AS ejercicioContable_ejercicioCerrado
				,[EjerciciosContables].[EJERCICIOCERRADOMODULOS] AS ejercicioContable_ejercicioCerradoModulos
				,[EjerciciosContables].[COMENTARIO] AS ejercicioContable_comentario
		
  FROM	[dbo].[PlanDeCuentas]
  LEFT JOIN	[dbo].[CentrosDeCostoContable]
	ON	[dbo].[CentrosDeCostoContable].[CENTRODECOSTOCONTABLE] = [dbo].[PlanDeCuentas].[CENTRODECOSTOCONTABLE] 
	AND	[dbo].[CentrosDeCostoContable].[EJERCICIO] = [dbo].[PlanDeCuentas].[EJERCICIO] 
  LEFT JOIN	[dbo].[PuntoDeEquilibrio]
	ON	[dbo].[PuntoDeEquilibrio].[PUNTODEEQUILIBRIO] = [dbo].[PlanDeCuentas].[PUNTODEEQUILIBRIO]
	AND	[dbo].[PuntoDeEquilibrio].[EJERCICIO]  = [dbo].[PlanDeCuentas].[EJERCICIO] 
  LEFT JOIN	[dbo].[EjerciciosContables]
	ON	[dbo].[EjerciciosContables].[EJERCICIO] = [dbo].[PlanDeCuentas].[EJERCICIO];

		
-------------------------------------------------------------		
		
		
		
		
		
		
		
		
		