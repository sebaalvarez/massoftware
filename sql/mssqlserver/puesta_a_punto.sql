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
  


/*
ALTER TABLE [dbo].[SSECUR_User] 
	ADD CONSTRAINT fk_EjerciciosContables_SSECUR_User  
		FOREIGN KEY (DEFAULT_EJERCICIO_CONTABLE)                              
		REFERENCES EjerciciosContables(Ejercicio);
*/


--ALTER TABLE [dbo].[EjerciciosContables] ADD id CHAR(36) NULL;  
--UPDATE [dbo].[EjerciciosContables]  SET [id] = CAST([EjerciciosContables].[EJERCICIO] AS VARCHAR);

--ALTER TABLE [dbo].[SSECUR_User] ADD id CHAR(36) NULL;  
--UPDATE [dbo].[SSECUR_User]  SET [id] = CAST([SSECUR_User].[NO] AS VARCHAR);

--ALTER TABLE [dbo].[CentrosDeCostoContable] ADD id CHAR(36) NULL;  
--UPDATE [dbo].[CentrosDeCostoContable]  SET [id] = CONCAT ( CAST([CentrosDeCostoContable].[EJERCICIO] AS VARCHAR), '-', CAST([CentrosDeCostoContable].[CENTRODECOSTOCONTABLE] AS VARCHAR) );


ALTER TABLE [dbo].[SSECUR_User] ADD DEFAULT_EJERCICIO_CONTABLE int NULL;  
		
		
-- VISTAS

-------------------------------------------------------------
	
	-- DROP VIEW [dbo].[vEjercicioContable]; 	

	CREATE VIEW [dbo].[vEjercicioContable] AS

	SELECT	'com.massoftware.model.EjercicioContable' AS ClassEjercicioContable
			-- ,[EjerciciosContables].[id]  AS id
			, CAST([EjerciciosContables].[EJERCICIO] AS VARCHAR) AS id			
			, CAST([EjerciciosContables].[EJERCICIO] AS INT) AS ejercicio						
			, CAST([EjerciciosContables].[COMENTARIO] AS VARCHAR) AS comentario
			, CAST([EjerciciosContables].[FECHAAPERTURASQL] AS DATE) AS fechaApertura			
			, CAST([EjerciciosContables].[FECHACIERRESQL] AS DATE) AS fechaCierre			
			, [EjerciciosContables].[EJERCICIOCERRADO] AS ejercicioCerrado
			, [EjerciciosContables].[EJERCICIOCERRADOMODULOS] AS ejercicioCerradoModulos
	FROM	VetaroRep.[dbo].[EjerciciosContables];
	
	-- SELECT * FROM VetaroRep.dbo.[EjerciciosContables] ;
	-- SELECT * FROM VetaroRep.dbo.[vEjercicioContable] ;
	-- SELECT * FROM VetaroRep.dbo.[vEjercicioContable] WHERE CAST(ejercicio AS VARCHAR)  LIKE CONCAT('%', CAST(12 AS VARCHAR)) ORDER BY ejercicio DESC;
	-- SELECT * FROM VetaroRep.dbo.vEjercicioContable ORDER BY ejercicio DESC;

-------------------------------------------------------------

	-- DROP VIEW [dbo].[vUsuario];
	
	CREATE VIEW [dbo].[vUsuario] AS

	 SELECT	'com.massoftware.model.Usuario' AS ClassUsuario
			--,[SSECUR_User].[id]  AS id
			, CAST([SSECUR_User].[NO] AS VARCHAR) AS id			
			, CAST([SSECUR_User].[NO] AS INT) AS numero			
			, CAST([SSECUR_User].[LASTNAME] AS VARCHAR) AS nombre
			--,[SSECUR_User].[DEFAULT_EJERCICIO_CONTABLE]
				, vEjercicioContable.id  AS ejercicioContable_id			
				, vEjercicioContable.ejercicio AS ejercicioContable_ejercicio		
				, vEjercicioContable.fechaApertura AS ejercicioContable_fechaApertura
				, vEjercicioContable.fechaCierre AS ejercicioContable_fechaCierre
				, vEjercicioContable.ejercicioCerrado AS ejercicioContable_ejercicioCerrado
				, vEjercicioContable.ejercicioCerradoModulos AS ejercicioContable_ejercicioCerradoModulos
				, vEjercicioContable.comentario AS ejercicioContable_comentario
	FROM	VetaroRep.[dbo].[SSECUR_User] 
	LEFT JOIN	[dbo].[vEjercicioContable] 
		ON [dbo].[vEjercicioContable].ejercicio = VetaroRep.[dbo].[SSECUR_User].[DEFAULT_EJERCICIO_CONTABLE];

	-- SELECT * FROM VetaroRep.dbo.[SSECUR_User] ;
	-- SELECT * FROM VetaroRep.dbo.[vUsuario] ;
	-- SELECT * FROM VetaroRep.dbo.vUsuario WHERE nombre = 'Administrador';

	

-------------------------------------------------------------
	
	-- DROP VIEW [dbo].[vCentroDeCostoContable]

	CREATE VIEW [dbo].[vCentroDeCostoContable] AS        

	SELECT	'com.massoftware.model.CentroDeCostoContable' AS ClassCentroDeCostoContable			
			, CONCAT ( CAST([CentrosDeCostoContable].[EJERCICIO] AS VARCHAR), '-', CAST([CentrosDeCostoContable].[CENTRODECOSTOCONTABLE] AS VARCHAR) ) As id			
			, CAST([CentrosDeCostoContable].[CENTRODECOSTOCONTABLE] AS INT) AS numero			 
			, CAST([CentrosDeCostoContable].[NOMBRE] AS VARCHAR) AS nombre			 			
			, CAST([CentrosDeCostoContable].[ABREVIATURA] AS VARCHAR) AS abreviatura			 						
			--,[CentrosDeCostoContable].[EJERCICIO] 
				,vEjercicioContable.id  AS ejercicioContable_id			
				,vEjercicioContable.ejercicio AS ejercicioContable_ejercicio		
				,vEjercicioContable.fechaApertura AS ejercicioContable_fechaApertura
				,vEjercicioContable.fechaCierre AS ejercicioContable_fechaCierre
				,vEjercicioContable.ejercicioCerrado AS ejercicioContable_ejercicioCerrado
				,vEjercicioContable.ejercicioCerradoModulos AS ejercicioContable_ejercicioCerradoModulos
				,vEjercicioContable.comentario AS ejercicioContable_comentario
			--,[CentrosDeCostoContable].[PRUEBA1] 
			--,[CentrosDeCostoContable].[PRUEBA]
	FROM [dbo].[CentrosDeCostoContable]
	LEFT JOIN	[dbo].[vEjercicioContable]
		ON [dbo].[vEjercicioContable].[ejercicio] = [dbo].[CentrosDeCostoContable].[EJERCICIO];

	-- SELECT * FROM VetaroRep.dbo.[CentrosDeCostoContable] ;
	-- SELECT * FROM VetaroRep.dbo.[vCentroDeCostoContable] ;
	-- SELECT * FROM VetaroRep.dbo.vCentroDeCostoContable ORDER BY nombre;
	-- SELECT * FROM VetaroRep.dbo.vCentroDeCostoContable ORDER BY numero;
	-- SELECT * FROM VetaroRep.dbo.vCentroDeCostoContable WHERE ejercicioContable_ejercicio = 2015  ORDER BY centroDeCostoContable;
	-- SELECT	MAX(VetaroRep.dbo.vCentroDeCostoContable.numero) FROM VetaroRep.dbo.vCentroDeCostoContable WHERE ejercicioContable_ejercicio = 2015;


-------------------------------------------------------------

	-- DROP VIEW [dbo].[vPuntoDeEquilibrioTipo];

	CREATE VIEW [dbo].[vPuntoDeEquilibrioTipo] AS  

		
	SELECT 'com.massoftware.model.PuntoDeEquilibrioTipo' AS ClassPuntoDeEquilibrioTipo, '1' AS id, 1 AS codigo, 'Individual' AS nombre
	UNION ALL
	SELECT 'com.massoftware.model.PuntoDeEquilibrioTipo' AS ClassPuntoDeEquilibrioTipo, '2' AS id, 2, 'Costo de ventas'
	UNION ALL
	SELECT 'com.massoftware.model.PuntoDeEquilibrioTipo' AS ClassPuntoDeEquilibrioTipo, '3' AS id, 3, 'Utilidad bruta'
	UNION ALL
	SELECT 'com.massoftware.model.PuntoDeEquilibrioTipo' AS ClassPuntoDeEquilibrioTipo, '4' AS id, 4, 'Resultados por sección'
	UNION ALL
	SELECT 'com.massoftware.model.PuntoDeEquilibrioTipo' AS ClassPuntoDeEquilibrioTipo, '5' AS id, 5, 'Resultados operativos'; 

	-- SELECT * FROM VetaroRep.dbo.[vPuntoDeEquilibrioTipo] ;	
	-- SELECT * FROM VetaroRep.dbo.[vPuntoDeEquilibrioTipo] ORDER BY codigo, nombre;	

	
		
-------------------------------------------------------------
		
    -- DROP VIEW [dbo].[vPuntoDeEquilibrio]         

	CREATE VIEW [dbo].[vPuntoDeEquilibrio] AS        

	SELECT	'com.massoftware.model.PuntoDeEquilibrio' AS ClassPuntoDeEquilibrio
			,CAST([PuntoDeEquilibrio].[PUNTODEEQUILIBRIO] AS VARCHAR)  AS id
			--,[PuntoDeEquilibrio].[EJERCICIO]   	
				,vEjercicioContable.id  AS ejercicioContable_id			
				,vEjercicioContable.ejercicio AS ejercicioContable_ejercicio		
				,vEjercicioContable.fechaApertura AS ejercicioContable_fechaApertura
				,vEjercicioContable.fechaCierre AS ejercicioContable_fechaCierre
				,vEjercicioContable.ejercicioCerrado AS ejercicioContable_ejercicioCerrado
				,vEjercicioContable.ejercicioCerradoModulos AS ejercicioContable_ejercicioCerradoModulos
				,vEjercicioContable.comentario AS ejercicioContable_comentario
			,CAST([PuntoDeEquilibrio].[PUNTODEEQUILIBRIO] AS INTEGER)  AS puntoDeEquilibrio
			,CAST([PuntoDeEquilibrio].[NOMBRE] AS VARCHAR)  AS nombre			
			--,[PuntoDeEquilibrio].[TIPO] AS tipo	
				,vPuntoDeEquilibrioTipo.id  AS puntoDeEquilibrioTipo_id			
				,vPuntoDeEquilibrioTipo.codigo AS puntoDeEquilibrioTipo_codigo
				,vPuntoDeEquilibrioTipo.nombre AS puntoDeEquilibrioTipo_nombre
								
			/*
			,CAST([PuntoDeEquilibrio].[TIPO] AS tinyint) AS tipo_tipo
			,CASE    
				WHEN [PuntoDeEquilibrio].[TIPO] = 1 THEN 'Individual'
				WHEN [PuntoDeEquilibrio].[TIPO] = 2 THEN 'Costo de ventas'
				WHEN [PuntoDeEquilibrio].[TIPO] = 3 THEN 'Utilidad bruta'
				WHEN [PuntoDeEquilibrio].[TIPO] = 4 THEN 'Resultados por sección'
				WHEN [PuntoDeEquilibrio].[TIPO] = 5 THEN 'Resultados operativos'
				ELSE CAST(null AS VARCHAR)								
			END AS tipo_nombre
			*/
			
	FROM [dbo].[PuntoDeEquilibrio]
	LEFT JOIN	[dbo].[vEjercicioContable]
		ON [dbo].[vEjercicioContable].[ejercicio] = [dbo].[PuntoDeEquilibrio].[EJERCICIO]
	LEFT JOIN	[dbo].[vPuntoDeEquilibrioTipo]
		ON [dbo].[vPuntoDeEquilibrioTipo].[codigo] = [dbo].[PuntoDeEquilibrio].[TIPO];
	

	-- SELECT * FROM VetaroRep.dbo.[PuntoDeEquilibrio] ;
	-- SELECT * FROM VetaroRep.dbo.[vPuntoDeEquilibrio] ;
	-- SELECT * FROM VetaroRep.dbo.vPuntoDeEquilibrio ORDER BY ejercicioContable_ejercicio DESC, puntoDeEquilibrio;	
	-- SELECT MAX(puntoDeEquilibrio) FROM VetaroRep.dbo.vPuntoDeEquilibrio;

	
-------------------------------------------------------------

	-- DROP VIEW [dbo].[vPlanDeCuentaEstado]

	CREATE VIEW [dbo].[vPlanDeCuentaEstado] AS  

		
	SELECT 'com.massoftware.model.PlanDeCuentaEstado' AS ClassPlanDeCuentaEstado, '0' AS id, 0 AS codigo, 'Cuenta fuera de uso' AS nombre
	UNION ALL
	SELECT 'com.massoftware.model.PlanDeCuentaEstado' AS ClassPlanDeCuentaEstado, '1' AS id, 1, 'Cuenta en uso'; 

	-- SELECT * FROM VetaroRep.dbo.vPlanDeCuentaEstado;	
	-- SELECT * FROM VetaroRep.dbo.vPlanDeCuentaEstado ORDER BY codigo, nombre;	


-------------------------------------------------------------

	-- DROP VIEW [dbo].[vCostoDeVenta]

	CREATE VIEW [dbo].[vCostoDeVenta] AS  

		
	SELECT 'com.massoftware.model.CostoDeVenta' AS ClassCostoDeVenta, '1' AS id, 1 AS codigo, 'No Participa' AS nombre
	UNION ALL
	SELECT 'com.massoftware.model.CostoDeVenta' AS ClassCostoDeVenta, '2' AS id, 2, 'Stock'
	UNION ALL
	SELECT 'com.massoftware.model.CostoDeVenta' AS ClassCostoDeVenta, '3' AS id, 3, 'Compras'
	UNION ALL
	SELECT 'com.massoftware.model.CostoDeVenta' AS ClassCostoDeVenta, '4' AS id, 4, 'Gastos'; 

	-- SELECT * FROM VetaroRep.dbo.vCostoDeVenta;	
	-- SELECT * FROM VetaroRep.dbo.vCostoDeVenta ORDER BY codigo, nombre;	


-------------------------------------------------------------

	-- DROP VIEW [dbo].[vPlanDeCuenta]         

	CREATE VIEW [dbo].[vPlanDeCuenta] AS        

	SELECT	'com.massoftware.model.PlanDeCuenta' AS ClassPlanDeCuenta	
			-----------------------------------------------------------------------------------------------------
			, CONCAT ( CAST([PlanDeCuentas].[EJERCICIO] AS VARCHAR), '-', CAST([PlanDeCuentas].[CUENTADEJERARQUIAIND] AS VARCHAR) ) As id			
			-----------------------------------------------------------------------------------------------------
			-- ejercicioContable [PlanDeCuentas].[EJERCICIO] 
				,vEjercicioContable.id  AS ejercicioContable_id			
				,vEjercicioContable.ejercicio AS ejercicioContable_ejercicio		
				,vEjercicioContable.fechaApertura AS ejercicioContable_fechaApertura
				,vEjercicioContable.fechaCierre AS ejercicioContable_fechaCierre
				,vEjercicioContable.ejercicioCerrado AS ejercicioContable_ejercicioCerrado
				,vEjercicioContable.ejercicioCerradoModulos AS ejercicioContable_ejercicioCerradoModulos
				,vEjercicioContable.comentario AS ejercicioContable_comentario
			-----------------------------------------------------------------------------------------------------			
			, CAST([PlanDeCuentas].[CUENTAINTEGRADORA] AS VARCHAR) AS codigoCuentaPadre -- integra ej 6.40.00.00.00.00
			, CAST([PlanDeCuentas].[CUENTADEJERARQUIAIND] AS VARCHAR) AS codigoCuenta -- integra ej 6.40.00.00.00.10
			, CAST([PlanDeCuentas].[CUENTACONTABLE] AS VARCHAR) AS cuentaContable -- TEXTO LIBRE
			, CAST([PlanDeCuentas].[NOMBRE] AS VARCHAR) AS nombre -- TEXTO LIBRE
			-----------------------------------------------------------------------------------------------------
			, CASE    
				WHEN [PlanDeCuentas].[IMPUTABLE] = 'S' THEN 1
				ELSE 0 
			  END AS imputable
			-----------------------------------------------------------------------------------------------------
			, CASE    
				WHEN [PlanDeCuentas].[AJUSTEINF] = 'S' THEN 1
				ELSE 0 
			  END AS ajustaPorInflacion
			-----------------------------------------------------------------------------------------------------
			--,planDeCuentaEstado [PlanDeCuentas].[ESTADO]
				,vPlanDeCuentaEstado.id  AS planDeCuentaEstado_id			
				,vPlanDeCuentaEstado.codigo AS planDeCuentaEstado_codigo
				,vPlanDeCuentaEstado.nombre AS planDeCuentaEstado_nombre
			-----------------------------------------------------------------------------------------------------
			, [PlanDeCuentas].[APROPIA] AS cuentaConApropiacion
			-----------------------------------------------------------------------------------------------------
			-- centroDeCostoContable [PlanDeCuentas].[CENTRODECOSTOCONTABLE]												
				, vCentroDeCostoContable.id  AS centroDeCostoContable_id			
				, vCentroDeCostoContable.numero AS centroDeCostoContable_numero
				, vCentroDeCostoContable.nombre AS centroDeCostoContable_nombre
				, vCentroDeCostoContable.abreviatura AS centroDeCostoContable_abreviatura
					-----------------------------------------------------------------------------------------------------
					-- , vCentroDeCostoContable.ejercicioContable_id AS centroDeCostoContable_ejercicioContable_id		
					-- , vCentroDeCostoContable.ejercicioContable_ejercicio	AS centroDeCostoContable_ejercicioContable_ejercicio	
					-- , vCentroDeCostoContable.ejercicioContable_fechaApertura AS centroDeCostoContable_ejercicioContable_fechaApertura		
					-- , vCentroDeCostoContable.ejercicioContable_fechaCierre AS centroDeCostoContable_ejercicioContable_fechaCierre
					-- , vCentroDeCostoContable.ejercicioContable_ejercicioCerrado AS centroDeCostoContable_ejercicioContable_ejercicioCerrado		
					-- , vCentroDeCostoContable.ejercicioContable_ejercicioCerradoModulos AS centroDeCostoContable_ejercicioContable_ejercicioCerradoModulos	
					-- , vCentroDeCostoContable.ejercicioContable_comentario AS centroDeCostoContable_ejercicioContable_comentario		
			-----------------------------------------------------------------------------------------------------
			, CAST([PlanDeCuentas].[CUENTAAGRUPADORA] AS VARCHAR) AS cuentaAgrupadora -- TEXTO LIBRE
			, CAST([PlanDeCuentas].[PORCENTAJE] AS DOUBLE PRECISION) AS porcentaje
			-----------------------------------------------------------------------------------------------------
			-- puntoDeEquilibrio [PlanDeCuentas].[PUNTODEEQUILIBRIO]
				, vPuntoDeEquilibrio.id AS puntoDeEquilibrio_id
					-----------------------------------------------------------------------------------------------------
					-- , vPuntoDeEquilibrio.ejercicioContable_id AS puntoDeEquilibrio_ejercicioContable_id		
					-- , vPuntoDeEquilibrio.ejercicioContable_ejercicio	AS puntoDeEquilibrio_ejercicioContable_ejercicio	
					-- , vPuntoDeEquilibrio.ejercicioContable_fechaApertura AS puntoDeEquilibrio_ejercicioContable_fechaApertura		
					-- , vPuntoDeEquilibrio.ejercicioContable_fechaCierre AS puntoDeEquilibrio_ejercicioContable_fechaCierre
					-- , vPuntoDeEquilibrio.ejercicioContable_ejercicioCerrado AS puntoDeEquilibrio_ejercicioContable_ejercicioCerrado		
					-- , vPuntoDeEquilibrio.ejercicioContable_ejercicioCerradoModulos AS puntoDeEquilibrio_ejercicioContable_ejercicioCerradoModulos	
					-- , vPuntoDeEquilibrio.ejercicioContable_comentario AS puntoDeEquilibrio_ejercicioContable_comentario
					-----------------------------------------------------------------------------------------------------		
			, vPuntoDeEquilibrio.puntoDeEquilibrio AS puntoDeEquilibrio_puntoDeEquilibrio
			, vPuntoDeEquilibrio.nombre AS puntoDeEquilibrio_nombre
				-----------------------------------------------------------------------------------------------------								
				, vPuntoDeEquilibrio.puntoDeEquilibrioTipo_id AS puntoDeEquilibrio_puntoDeEquilibrioTipo_id				
				, vPuntoDeEquilibrio.puntoDeEquilibrioTipo_codigo AS puntoDeEquilibrio_puntoDeEquilibrioTipo_codigo
				, vPuntoDeEquilibrio.puntoDeEquilibrioTipo_nombre AS puntoDeEquilibrio_puntoDeEquilibrioTipo_nombre 
			-----------------------------------------------------------------------------------------------------
			--, costoDeVenta [PlanDeCuentas].[COSTODEVENTA]
				,vCostoDeVenta.id  AS costoDeVenta_id			
				,vCostoDeVenta.codigo AS costoDeVenta_codigo
				,vCostoDeVenta.nombre AS costoDeVenta_nombre
			-----------------------------------------------------------------------------------------------------
			 
			
	FROM [dbo].[PlanDeCuentas]
	LEFT JOIN	[dbo].[vEjercicioContable]
			ON		[dbo].[vEjercicioContable].[ejercicio] = [dbo].[PlanDeCuentas].[EJERCICIO]
	LEFT JOIN	[dbo].[vPlanDeCuentaEstado]
			ON		[dbo].[vPlanDeCuentaEstado].[codigo] = [dbo].[PlanDeCuentas].[ESTADO]
	LEFT JOIN	[dbo].[vCentroDeCostoContable]
			ON		[dbo].[vCentroDeCostoContable].numero = [dbo].[PlanDeCuentas].[CENTRODECOSTOCONTABLE] 
			AND		[dbo].[vCentroDeCostoContable].ejercicioContable_ejercicio = [dbo].[PlanDeCuentas].[EJERCICIO] 
	LEFT JOIN	[dbo].[vPuntoDeEquilibrio]
			ON		[dbo].[vPuntoDeEquilibrio].puntoDeEquilibrio = [dbo].[PlanDeCuentas].[PUNTODEEQUILIBRIO] 
			AND		[dbo].[vPuntoDeEquilibrio].ejercicioContable_ejercicio = [dbo].[PlanDeCuentas].[EJERCICIO] 
	LEFT JOIN	[dbo].[vCostoDeVenta]
			ON		[dbo].[vCostoDeVenta].[codigo] = [dbo].[PlanDeCuentas].[COSTODEVENTA]
	WHERE CAST([PlanDeCuentas].[CUENTAINTEGRADORA] AS VARCHAR) IS NOT NULL
			AND CAST([PlanDeCuentas].[CUENTAINTEGRADORA] AS VARCHAR) <> '';
	

	-- SELECT * FROM VetaroRep.dbo.[PlanDeCuentas] ;
	-- SELECT * FROM VetaroRep.dbo.[vPlanDeCuenta] ;
	-- SELECT * FROM VetaroRep.dbo.[vPlanDeCuenta] WHERE codigoCuentaPadre IS NULL OR codigoCuentaPadre = '' ;
	-- SELECT * FROM VetaroRep.dbo.[vPlanDeCuenta] WHERE id = '2017-11010200002' ;	
	-- SELECT * FROM VetaroRep.dbo.[vPlanDeCuenta] WHERE id = '2017-10000000000' ;	
	-- SELECT * FROM VetaroRep.dbo.[vPlanDeCuenta] WHERE nombre = 'Moneda Extranjera' ;	
	-- SELECT * FROM VetaroRep.dbo.vPlanDeCuenta ORDER BY ejercicioContable_ejercicio DESC, codigoCuenta;		
	-- SELECT * FROM VetaroRep.dbo.vPlanDeCuenta ORDER BY ejercicioContable_ejercicio DESC  , codigoCuenta ASC;