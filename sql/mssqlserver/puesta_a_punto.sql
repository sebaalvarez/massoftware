


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

-- DROP VIEW [dbo].[vSeguridadModulo]

CREATE VIEW [dbo].[vSeguridadModulo] AS  


	SELECT	'com.massoftware.model.SeguridadModulo' AS ClassSeguridadModulo
			-----------------------------------------------------------------------------------------------------
			, CAST([SSECUR_DoorGroup].[NO] AS VARCHAR) As id			
			-----------------------------------------------------------------------------------------------------
			, CAST([SSECUR_DoorGroup].[NO] AS INT) AS codigo -- Integer [ 1 - N ] NOT NULL
			, LTRIM(RTRIM(CAST([SSECUR_DoorGroup].[NAME] AS VARCHAR)))  AS nombre -- String (30) NOT NULL
			, CAST([SSECUR_DoorGroup].[FREEZE] AS INT) AS congelado -- Boolean  			
	FROM	[dbo].[SSECUR_DoorGroup];
  
	-- SELECT * FROM dbo.vSeguridadModulo;	
	-- SELECT * FROM dbo.vSeguridadModulo ORDER BY codigo, nombre;	

-------------------------------------------------------------

-- DROP VIEW [dbo].[vSeguridadPuerta]

CREATE VIEW [dbo].[vSeguridadPuerta] AS  


	SELECT	'com.massoftware.model.SeguridadPuerta' AS ClassSeguridadPuerta
			-----------------------------------------------------------------------------------------------------
			, CAST([SSECUR_Door].[NO] AS VARCHAR) As id			
			-----------------------------------------------------------------------------------------------------
			, CAST([SSECUR_Door].[NO] AS INT) AS codigo -- Integer [ 1 - N ] NOT NULL
			-----------------------------------------------------------------------------------------------------
				-- , [DGRPNO] AS seguridadModulo -- Id
				, [vSeguridadModulo].[id] AS seguridadModulo_id -- NOT NULL
				, [vSeguridadModulo].[codigo] AS seguridadModulo_codigo 
				, [vSeguridadModulo].[nombre] AS seguridadModulo_nombre 
				, [vSeguridadModulo].[congelado] AS seguridadModulo_congelado 
			-----------------------------------------------------------------------------------------------------
			, LTRIM(RTRIM(CAST([EQUATE] AS VARCHAR))) AS igualacionID -- String (30) NOT NULL
			, LTRIM(RTRIM(CAST([DESCRIPTION] AS VARCHAR))) AS nombre -- String (60) NOT NULL
			, CAST([SSECUR_Door].[FREEZE] AS INT) AS congelado -- Boolean  	
	FROM	[dbo].[SSECUR_Door]
	LEFT JOIN	[dbo].[vSeguridadModulo] 
		ON [dbo].[vSeguridadModulo].[codigo] = CAST([dbo].[SSECUR_Door].[DGRPNO] AS INT);

	-- SELECT * FROM dbo.vSeguridadPuerta;	
	-- SELECT * FROM dbo.vSeguridadPuerta ORDER BY codigo, nombre;	


-------------------------------------------------------------
	
-- DROP VIEW [dbo].[vEjercicioContable]; 	

CREATE VIEW [dbo].[vEjercicioContable] AS

	SELECT	'com.massoftware.model.EjercicioContable' AS ClassEjercicioContable			
			, CAST([EjerciciosContables].[EJERCICIO] AS VARCHAR) AS id			
			, CAST([EjerciciosContables].[EJERCICIO] AS INT) AS ejercicio						
			, LTRIM(RTRIM(CAST([EjerciciosContables].[COMENTARIO] AS VARCHAR))) AS comentario
			, CAST([EjerciciosContables].[FECHAAPERTURASQL] AS DATE) AS fechaApertura			
			, CAST([EjerciciosContables].[FECHACIERRESQL] AS DATE) AS fechaCierre			
			, CAST([EjerciciosContables].[EJERCICIOCERRADO] AS INT) AS ejercicioCerrado
			, CAST([EjerciciosContables].[EJERCICIOCERRADOMODULOS] AS INT) AS ejercicioCerradoModulos
	FROM	[dbo].[EjerciciosContables];
	
	-- SELECT * FROM dbo.[EjerciciosContables] ;
	-- SELECT * FROM dbo.[vEjercicioContable] ;
	-- SELECT * FROM dbo.[vEjercicioContable] WHERE CAST(ejercicio AS VARCHAR)  LIKE CONCAT('%', CAST(12 AS VARCHAR)) ORDER BY ejercicio DESC;
	-- SELECT * FROM dbo.vEjercicioContable ORDER BY ejercicio DESC;

-------------------------------------------------------------

-- DROP VIEW [dbo].[vUsuario];
	
CREATE VIEW [dbo].[vUsuario] AS

	SELECT	'com.massoftware.model.Usuario' AS ClassUsuario			
			, CAST([SSECUR_User].[NO] AS VARCHAR) AS id			
			, CAST([SSECUR_User].[NO] AS INT) AS numero			
			, LTRIM(RTRIM(CAST([SSECUR_User].[LASTNAME] AS VARCHAR))) AS nombre
			--,[SSECUR_User].[DEFAULT_EJERCICIO_CONTABLE]
				, vEjercicioContable.id  AS ejercicioContable_id			
				, vEjercicioContable.ejercicio AS ejercicioContable_ejercicio		
				, vEjercicioContable.fechaApertura AS ejercicioContable_fechaApertura
				, vEjercicioContable.fechaCierre AS ejercicioContable_fechaCierre
				, vEjercicioContable.ejercicioCerrado AS ejercicioContable_ejercicioCerrado
				, vEjercicioContable.ejercicioCerradoModulos AS ejercicioContable_ejercicioCerradoModulos
				, vEjercicioContable.comentario AS ejercicioContable_comentario
	FROM	[dbo].[SSECUR_User] 
	LEFT JOIN	[dbo].[vEjercicioContable] 
		ON [dbo].[vEjercicioContable].ejercicio = CAST([dbo].[SSECUR_User].[DEFAULT_EJERCICIO_CONTABLE] AS INT);

	-- SELECT * FROM dbo.[SSECUR_User] ;
	-- SELECT * FROM dbo.[vUsuario] ;
	-- SELECT * FROM dbo.vUsuario WHERE nombre = 'Administrador';

	

-------------------------------------------------------------
	
	-- DROP VIEW [dbo].[vCentroDeCostoContable]

CREATE VIEW [dbo].[vCentroDeCostoContable] AS        

	SELECT	'com.massoftware.model.CentroDeCostoContable' AS ClassCentroDeCostoContable			
			, CONCAT ( CAST([vEjercicioContable].[ejercicio] AS VARCHAR), '-', CAST([CentrosDeCostoContable].[CENTRODECOSTOCONTABLE] AS VARCHAR) ) As id			
			, CAST([CentrosDeCostoContable].[CENTRODECOSTOCONTABLE] AS INT) AS numero			 
			, LTRIM(RTRIM(CAST([CentrosDeCostoContable].[NOMBRE] AS VARCHAR))) AS nombre			 			
			, LTRIM(RTRIM(CAST([CentrosDeCostoContable].[ABREVIATURA] AS VARCHAR))) AS abreviatura			 						
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
	
	FROM	[dbo].[CentrosDeCostoContable]
	LEFT JOIN	[dbo].[vEjercicioContable]
		ON [dbo].[vEjercicioContable].[ejercicio] = CAST([dbo].[CentrosDeCostoContable].[EJERCICIO] AS INT);

	-- SELECT * FROM dbo.[CentrosDeCostoContable] ;
	-- SELECT * FROM dbo.[vCentroDeCostoContable] ;
	-- SELECT * FROM dbo.vCentroDeCostoContable ORDER BY nombre;
	-- SELECT * FROM dbo.vCentroDeCostoContable ORDER BY numero;
	-- SELECT * FROM dbo.vCentroDeCostoContable WHERE ejercicioContable_ejercicio = 2015  ORDER BY centroDeCostoContable;
	-- SELECT	MAX(dbo.vCentroDeCostoContable.numero) FROM dbo.vCentroDeCostoContable WHERE ejercicioContable_ejercicio = 2015;


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

	-- SELECT * FROM dbo.[vPuntoDeEquilibrioTipo] ;	
	-- SELECT * FROM dbo.[vPuntoDeEquilibrioTipo] ORDER BY codigo, nombre;	

	
		
-------------------------------------------------------------
		
 -- DROP VIEW [dbo].[vPuntoDeEquilibrio]         

CREATE VIEW [dbo].[vPuntoDeEquilibrio] AS        

	SELECT	'com.massoftware.model.PuntoDeEquilibrio' AS ClassPuntoDeEquilibrio			
			, CONCAT ( [vEjercicioContable].[ejercicio], '-', CAST(PuntoDeEquilibrio.PUNTODEEQUILIBRIO AS VARCHAR) ) AS id
			--,[PuntoDeEquilibrio].[EJERCICIO]   	
				,vEjercicioContable.id  AS ejercicioContable_id			
				,vEjercicioContable.ejercicio AS ejercicioContable_ejercicio		
				,vEjercicioContable.fechaApertura AS ejercicioContable_fechaApertura
				,vEjercicioContable.fechaCierre AS ejercicioContable_fechaCierre
				,vEjercicioContable.ejercicioCerrado AS ejercicioContable_ejercicioCerrado
				,vEjercicioContable.ejercicioCerradoModulos AS ejercicioContable_ejercicioCerradoModulos
				,vEjercicioContable.comentario AS ejercicioContable_comentario
			, CAST([PuntoDeEquilibrio].[PUNTODEEQUILIBRIO] AS INTEGER)  AS puntoDeEquilibrio
			, LTRIM(RTRIM(CAST([PuntoDeEquilibrio].[NOMBRE] AS VARCHAR)))  AS nombre
			--,[PuntoDeEquilibrio].[TIPO] AS tipo	
				,vPuntoDeEquilibrioTipo.id  AS puntoDeEquilibrioTipo_id			
				,vPuntoDeEquilibrioTipo.codigo AS puntoDeEquilibrioTipo_codigo
				,vPuntoDeEquilibrioTipo.nombre AS puntoDeEquilibrioTipo_nombre
			
	FROM	[dbo].[PuntoDeEquilibrio]
	LEFT JOIN	[dbo].[vEjercicioContable]
		ON [dbo].[vEjercicioContable].[ejercicio] = CAST([dbo].[PuntoDeEquilibrio].[EJERCICIO] AS INT)
	LEFT JOIN	[dbo].[vPuntoDeEquilibrioTipo]
		ON [dbo].[vPuntoDeEquilibrioTipo].[codigo] = CAST([dbo].[PuntoDeEquilibrio].[TIPO] AS INT);
	

	-- SELECT * FROM dbo.[PuntoDeEquilibrio] ;
	-- SELECT * FROM dbo.[vPuntoDeEquilibrio] ;
	-- SELECT * FROM dbo.vPuntoDeEquilibrio ORDER BY ejercicioContable_ejercicio DESC, puntoDeEquilibrio;	
	-- SELECT MAX(puntoDeEquilibrio) FROM dbo.vPuntoDeEquilibrio;

	
-------------------------------------------------------------

-- DROP VIEW [dbo].[vCuentaContableEstado]

CREATE VIEW [dbo].[vCuentaContableEstado] AS  

		
	SELECT 'com.massoftware.model.CuentaContableEstado' AS ClassCuentaContableEstado, '0' AS id, 0 AS codigo, 'Cuenta fuera de uso' AS nombre
	UNION ALL
	SELECT 'com.massoftware.model.CuentaContableEstado' AS ClassCuentaContableEstado, '1' AS id, 1, 'Cuenta en uso'; 

	-- SELECT * FROM dbo.vCuentaContableEstado;	
	-- SELECT * FROM dbo.vCuentaContableEstado ORDER BY codigo, nombre;	


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

	-- SELECT * FROM dbo.vCostoDeVenta;	
	-- SELECT * FROM dbo.vCostoDeVenta ORDER BY codigo, nombre;	


-------------------------------------------------------------

-- DROP VIEW [dbo].[vCuentaContable]         

CREATE VIEW [dbo].[vCuentaContable] AS        

	SELECT	'com.massoftware.model.CuentaContable' AS ClassCuentaContable	
			-----------------------------------------------------------------------------------------------------
			, CONCAT ( CAST([vEjercicioContable].[ejercicio] AS VARCHAR), '-', LTRIM(RTRIM(CAST([PlanDeCuentas].[CUENTADEJERARQUIAIND] AS VARCHAR))) ) As id			
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
			, LTRIM(RTRIM(CAST([PlanDeCuentas].[CUENTAINTEGRADORA] AS VARCHAR))) AS codigoCuentaPadre -- integra ej 6.40.00.00.00.00
			, LTRIM(RTRIM(CAST([PlanDeCuentas].[CUENTADEJERARQUIAIND] AS VARCHAR))) AS codigoCuenta -- integra ej 6.40.00.00.00.10
			, LTRIM(RTRIM(CAST([PlanDeCuentas].[CUENTACONTABLE] AS VARCHAR))) AS cuentaContable -- TEXTO LIBRE
			, LTRIM(RTRIM(CAST([PlanDeCuentas].[NOMBRE] AS VARCHAR))) AS nombre -- TEXTO LIBRE
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
				,vCuentaContableEstado.id  AS cuentaContableEstado_id			
				,vCuentaContableEstado.codigo AS cuentaContableEstado_codigo
				,vCuentaContableEstado.nombre AS cuentaContableEstado_nombre
			-----------------------------------------------------------------------------------------------------
			, CAST([PlanDeCuentas].[APROPIA] AS INT) AS cuentaConApropiacion
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
			, LTRIM(RTRIM(CAST([PlanDeCuentas].[CUENTAAGRUPADORA] AS VARCHAR))) AS cuentaAgrupadora -- TEXTO LIBRE
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
			 
			
	FROM	[dbo].[PlanDeCuentas]
	LEFT JOIN	[dbo].[vEjercicioContable]
			ON		[dbo].[vEjercicioContable].[ejercicio] = CAST([dbo].[PlanDeCuentas].[EJERCICIO] AS INT)
	LEFT JOIN	[dbo].[vCuentaContableEstado]
			ON		[dbo].[vCuentaContableEstado].[codigo] = CAST([dbo].[PlanDeCuentas].[ESTADO] AS INT)
	LEFT JOIN	[dbo].[vCentroDeCostoContable]
			ON		[dbo].[vCentroDeCostoContable].numero = CAST([dbo].[PlanDeCuentas].[CENTRODECOSTOCONTABLE] AS INT)
			AND		[dbo].[vCentroDeCostoContable].ejercicioContable_ejercicio = CAST([dbo].[PlanDeCuentas].[EJERCICIO] AS INT)
	LEFT JOIN	[dbo].[vPuntoDeEquilibrio]
			ON		[dbo].[vPuntoDeEquilibrio].puntoDeEquilibrio = CAST([dbo].[PlanDeCuentas].[PUNTODEEQUILIBRIO] AS INT)
			AND		[dbo].[vPuntoDeEquilibrio].ejercicioContable_ejercicio = CAST([dbo].[PlanDeCuentas].[EJERCICIO] AS INT)
	LEFT JOIN	[dbo].[vCostoDeVenta]
			ON		[dbo].[vCostoDeVenta].[codigo] = CAST([dbo].[PlanDeCuentas].[COSTODEVENTA] AS INT)
	WHERE CAST([PlanDeCuentas].[CUENTAINTEGRADORA] AS VARCHAR) IS NOT NULL
			AND LTRIM(RTRIM(CAST([PlanDeCuentas].[CUENTAINTEGRADORA] AS VARCHAR))) <> '';
	

	-- SELECT * FROM dbo.[PlanDeCuentas] ;
	-- SELECT * FROM dbo.[vCuentaContable] ;
	-- SELECT * FROM dbo.[vCuentaContable] WHERE codigoCuentaPadre IS NULL OR codigoCuentaPadre = '' ;
	-- SELECT * FROM dbo.[vCuentaContable] WHERE id = '2017-11010200002' ;	
	-- SELECT * FROM dbo.[vCuentaContable] WHERE id = '2017-10000000000' ;	
	-- SELECT * FROM dbo.[vCuentaContable] WHERE nombre = 'Moneda Extranjera' ;	
	-- SELECT * FROM dbo.[vCuentaContable] ORDER BY ejercicioContable_ejercicio DESC, codigoCuenta;		
	-- SELECT * FROM dbo.[vCuentaContable] ORDER BY ejercicioContable_ejercicio DESC  , codigoCuenta ASC;


-------------------------------------------------------------

-- DROP VIEW [dbo].[vAsientoModelo]         

CREATE VIEW [dbo].[vAsientoModelo] AS        


	SELECT	'com.massoftware.model.AsientoModelo' AS ClassAsientoModelo	
			-----------------------------------------------------------------------------------------------------
			, CONCAT ( CAST([vEjercicioContable].[ejercicio] AS VARCHAR), '-', CAST([AsientosModelos].[ASIENTOMODELO] AS VARCHAR) ) As id			
			-----------------------------------------------------------------------------------------------------
			-- ejercicioContable [PlanDeCuentas].[EJERCICIO] 
				,vEjercicioContable.id  AS ejercicioContable_id			
				,vEjercicioContable.ejercicio AS ejercicioContable_ejercicio		
				,vEjercicioContable.fechaApertura AS ejercicioContable_fechaApertura
				,vEjercicioContable.fechaCierre AS ejercicioContable_fechaCierre
				,vEjercicioContable.ejercicioCerrado AS ejercicioContable_ejercicioCerrado
				,vEjercicioContable.ejercicioCerradoModulos AS ejercicioContable_ejercicioCerradoModulos
				,vEjercicioContable.comentario AS ejercicioContable_comentario
			, CAST([AsientosModelos].[ASIENTOMODELO] AS INT) AS numero 
			, LTRIM(RTRIM(CAST([AsientosModelos].[DENOMINACION] AS VARCHAR))) AS denominacion 
	FROM	[dbo].[AsientosModelos]
	LEFT JOIN	[dbo].[vEjercicioContable]
		ON	[dbo].[vEjercicioContable].[ejercicio] = (	SELECT DISTINCT CAST([AsientosModelosMov].[EJERCICIO] AS INT) 
														FROM	[dbo].[AsientosModelosMov] 
														WHERE CAST([AsientosModelosMov].[ASIENTOMODELO] AS INT) = CAST([AsientosModelos].[ASIENTOMODELO] AS INT)
													  );


-- SELECT * FROM dbo.vAsientoModelo;


-------------------------------------------------------------

-- DROP VIEW [dbo].[vAsientoModeloItem]         

CREATE VIEW [dbo].[vAsientoModeloItem] AS        


	SELECT	'com.massoftware.model.AsientoModeloItem' AS ClassAsientoModeloItem	
			-----------------------------------------------------------------------------------------------------
			, CONCAT ( vAsientoModelo.id, '-', CAST(AsientosModelosMov.REGISTRO AS VARCHAR) ) As id			
			-----------------------------------------------------------------------------------------------------			
			, vAsientoModelo.id AS asientoModelo_id
				, vAsientoModelo.ejercicioContable_id AS asientoModelo_ejercicioContable_id			
					, vAsientoModelo.ejercicioContable_ejercicio AS asientoModelo_ejercicioContable_ejercicio		
					, vAsientoModelo.ejercicioContable_fechaApertura AS asientoModelo_ejercicioContable_fechaApertura
					, vAsientoModelo.ejercicioContable_fechaCierre AS asientoModelo_ejercicioContable_fechaCierre
					, vAsientoModelo.ejercicioContable_ejercicioCerrado AS asientoModelo_ejercicioContable_ejercicioCerrado
					, vAsientoModelo.ejercicioContable_ejercicioCerradoModulos AS asientoModelo_ejercicioContable_ejercicioCerradoModulos
					, vAsientoModelo.ejercicioContable_comentario AS asientoModelo_ejercicioContable_comentario
				, vAsientoModelo.numero AS asientoModelo_numero
				, vAsientoModelo.denominacion AS asientoModelo_denominacion
			-----------------------------------------------------------------------------------------------------			
			, CAST(AsientosModelosMov.REGISTRO AS INT) AS registro						
			 -----------------------------------------------------------------------------------------------------			
			, vCuentaContable.id AS cuentaContable_id			
			-----------------------------------------------------------------------------------------------------			
			, vCuentaContable.ejercicioContable_id AS cuentaContable_ejercicioContable_id			
				, vCuentaContable.ejercicioContable_ejercicio AS cuentaContable_ejercicioContable_ejercicio		
				, vCuentaContable.ejercicioContable_fechaApertura AS cuentaContable_ejercicioContable_fechaApertura
				, vCuentaContable.ejercicioContable_fechaCierre AS cuentaContable_ejercicioContable_fechaCierre
				, vCuentaContable.ejercicioContable_ejercicioCerrado AS cuentaContable_ejercicioContable_ejercicioCerrado
				, vCuentaContable.ejercicioContable_ejercicioCerradoModulos AS cuentaContable_ejercicioContable_ejercicioCerradoModulos
				, vCuentaContable.ejercicioContable_comentario AS cuentaContable_ejercicioContable_comentario
			-----------------------------------------------------------------------------------------------------			
			, vCuentaContable.codigoCuentaPadre  AS cuentaContable_codigoCuentaPadre
			, vCuentaContable.codigoCuenta  AS cuentaContable_codigoCuenta
			, vCuentaContable.cuentaContable AS cuentaContable_cuentaContable
			, vCuentaContable.nombre AS cuentaContable_nombre
			-----------------------------------------------------------------------------------------------------
			, vCuentaContable.imputable AS cuentaContable_imputable
			-----------------------------------------------------------------------------------------------------
			, vCuentaContable.ajustaPorInflacion AS cuentaContable_ajustaPorInflacion			
			-----------------------------------------------------------------------------------------------------			
			, vCuentaContable.cuentaContableEstado_id AS cuentaContable_cuentaContableEstado_id 			
				, vCuentaContable.cuentaContableEstado_codigo AS cuentaContable_cuentaContableEstado_codigo
				, vCuentaContable.cuentaContableEstado_nombre AS cuentaContable_cuentaContableEstado_nombre
			-----------------------------------------------------------------------------------------------------
			, vCuentaContable.cuentaConApropiacion AS cuentaContable_cuentaConApropiacion
			-----------------------------------------------------------------------------------------------------			
			, vCuentaContable.centroDeCostoContable_id AS cuentaContable_centroDeCostoContable_id		
				,  vCuentaContable.centroDeCostoContable_numero AS cuentaContable_centroDeCostoContable_numero
				,  vCuentaContable.centroDeCostoContable_nombre AS cuentaContable_centroDeCostoContable_nombre
				,  vCuentaContable.centroDeCostoContable_abreviatura AS cuentaContable_centroDeCostoContable_abreviatura					
			-----------------------------------------------------------------------------------------------------
			, vCuentaContable.cuentaAgrupadora AS cuentaContable_cuentaAgrupadora 
			, vCuentaContable.porcentaje AS cuentaContable_porcentaje
			-----------------------------------------------------------------------------------------------------			
			, vCuentaContable.puntoDeEquilibrio_id AS cuentaContable_puntoDeEquilibrio_id
				, vCuentaContable.puntoDeEquilibrio_puntoDeEquilibrio AS cuentaContable_puntoDeEquilibrio_puntoDeEquilibrio
				, vCuentaContable.puntoDeEquilibrio_nombre AS cuentaContable_puntoDeEquilibrio_nombre
				-----------------------------------------------------------------------------------------------------								
				, vCuentaContable.puntoDeEquilibrio_puntoDeEquilibrioTipo_id AS cuentaContable_puntoDeEquilibrio_puntoDeEquilibrioTipo_id
					, vCuentaContable.puntoDeEquilibrio_puntoDeEquilibrioTipo_codigo AS cuentaContable_puntoDeEquilibrio_puntoDeEquilibrioTipo_codigo
					, vCuentaContable.puntoDeEquilibrio_puntoDeEquilibrioTipo_nombre AS cuentaContable_puntoDeEquilibrio_puntoDeEquilibrioTipo_nombre 
			
			-----------------------------------------------------------------------------------------------------
			
	FROM	dbo.AsientosModelosMov
	LEFT JOIN	dbo.vAsientoModelo
		ON	vAsientoModelo.numero = CAST(dbo.AsientosModelosMov.ASIENTOMODELO AS INT)
	LEFT JOIN	dbo.vCuentaContable
		ON	dbo.vCuentaContable.cuentaContable = LTRIM(RTRIM(CAST(dbo.AsientosModelosMov.CUENTACONTABLE AS VARCHAR)))
		AND vCuentaContable.ejercicioContable_ejercicio = vAsientoModelo.ejercicioContable_ejercicio;
  -- ORDER BY vAsientoModelo.ejercicioContable_ejercicio DESC, dbo.AsientosModelosMov.ASIENTOMODELO, dbo.AsientosModelosMov.REGISTRO;


-- SELECT * FROM dbo.vAsientoModeloItem ;


--==============================================================================================================

-- FONDOS

-------------------------------------------------------------

	-- DROP VIEW [dbo].[vSucursalTipo]

CREATE VIEW [dbo].[vSucursalTipo] AS  

		
	SELECT 'com.massoftware.model.SucursalTipo' AS ClassCostoDeVenta, '1' AS id, 1 AS codigo, 'Centralizador' AS nombre
	UNION ALL
	SELECT 'com.massoftware.model.SucursalTipo' AS ClassCostoDeVenta, '2' AS id, 2, 'Casa central'
	UNION ALL
	SELECT 'com.massoftware.model.SucursalTipo' AS ClassCostoDeVenta, '3' AS id, 3, 'Sucursal'
	UNION ALL
	SELECT 'com.massoftware.model.SucursalTipo' AS ClassCostoDeVenta, '4' AS id, 4, 'Punto de venta'; 

	-- SELECT * FROM dbo.vSucursalTipo;	
	-- SELECT * FROM dbo.vSucursalTipo ORDER BY codigo, nombre;	

	
-------------------------------------------------------------

-- DROP VIEW [dbo].[vSucursal]

CREATE VIEW [dbo].[vSucursal] AS  


	SELECT	'com.massoftware.model.Sucursal' AS ClassSucursal
			-----------------------------------------------------------------------------------------------------
			, CAST([Sucursales].[SUCURSAL] AS VARCHAR) As id			
			-----------------------------------------------------------------------------------------------------
			, CAST([Sucursales].[SUCURSAL] AS INT) AS codigo -- Integer [ 1 - 99 ] NOT NULL
			, LTRIM(RTRIM(CAST([Sucursales].[NOMBRE] AS VARCHAR))) AS nombre -- String (35) NOT NULL
			, LTRIM(RTRIM(CAST([Sucursales].[ABREVIATURA] AS VARCHAR))) AS abreviatura -- String (4) NOT NULL      
			-----------------------------------------------------------------------------------------------------
				--, [Sucursales].[TIPOSUCURSAL] AS sucursalTipo -- Id			
				, vSucursalTipo.id  AS sucursalTipo_id	-- NOT NULL		
				, vSucursalTipo.codigo AS sucursalTipo_codigo
				, vSucursalTipo.nombre AS sucursalTipo_nombre
			-- ---------------------------------------------------------------------
			, LTRIM(RTRIM(CAST([Sucursales].[CUENTASCLIENTESDESDE] AS VARCHAR))) AS cuentaClientesDesde -- String (6)
			, LTRIM(RTRIM(CAST([Sucursales].[CUENTASCLIENTESHASTA] AS VARCHAR))) AS cuentaClientesHasta -- String (6)
			, CAST([Sucursales].[CANTIDADCARACTERESCLIENTES] AS INT) AS cantidadCaracteresClientes -- Integer	[3 | 4 | 5 | 6 ]  DEFAULT 6 NOT NULL
			, CAST([Sucursales].[NUMERICOCLIENTES] AS INT) AS identificacionNumericaClientes -- Boolean 
			, CAST([Sucursales].[PERMITECAMBIARCLIENTES] AS INT) AS permiteCambiarClientes -- Boolean      
			-- ---------------------------------------------------------------------
			, CAST([Sucursales].[CUENTASCLIENTESOCASIONALESDESDE] AS INT) AS clientesOcasionalesDesde -- Integer	 [9999999999 - (-99999999)]
			, CAST([Sucursales].[CUENTASCLIENTESOCASIONALESHASTA] AS INT) AS clientesOcasionalesHasta -- Integer   [9999999999 - (-99999999)]    
			-- ---------------------------------------------------------------------
			, CAST([Sucursales].[NROCOBRANZADESDE] AS INT) AS nroCobranzaDesde -- Integer [9999999999 - (-99999999)]
			, CAST([Sucursales].[NROCOBRANZAHASTA] AS INT) AS nroCobranzaHasta -- Integer [9999999999 - (-99999999)]       	        
			-- ---------------------------------------------------------------------
			, LTRIM(RTRIM(CAST([Sucursales].[CUENTASPROVEEDORESDESDE] AS VARCHAR))) AS proveedoresDesde -- String (6) 
			, LTRIM(RTRIM(CAST([Sucursales].[CUENTASPROVEEDORESHASTA] AS VARCHAR))) AS proveedoresHasta -- String (6)
			, CAST([Sucursales].[CANTIDADCARACTERESPROVEEDOR] AS INT) AS cantidadCaracteresProveedor -- Integer [3 | 4 | 5 | 6 ] DEFAULT 6 NOT NULL
			, CAST([Sucursales].[NUMERICOPROVEEDOR] AS INT) AS identificacionNumericaProveedores -- Boolean	  
			, CAST([Sucursales].[PERMITECAMBIARPROVEEDOR] AS INT) AS permiteCambiarProveedores -- Boolean      
			-- ---------------------------------------------------------------------      
  
		FROM	[dbo].[Sucursales]
		LEFT JOIN	[dbo].[vSucursalTipo]
				ON	[dbo].[vSucursalTipo].[codigo] = CAST([dbo].[Sucursales].[TIPOSUCURSAL] AS INT);

	-- SELECT * FROM dbo.vSucursal;	
	-- SELECT * FROM dbo.vSucursal ORDER BY codigo, nombre;	

-------------------------------------------------------------

-- DROP VIEW [dbo].[vTalonario]

CREATE VIEW [dbo].[vTalonario] AS  


	SELECT	'com.massoftware.model.Talonario' AS ClassTalonario
			-----------------------------------------------------------------------------------------------------
			, CAST([TablaDeMultiproposito].[MULTIPROPOSITO] AS VARCHAR) As id			
			-----------------------------------------------------------------------------------------------------
			, CAST([TablaDeMultiproposito].[MULTIPROPOSITO] AS INT) AS codigo -- Integer Not NULL [ 1 - 99999 ] 
			, LTRIM(RTRIM(CAST([TablaDeMultiproposito].[NOMBRE] AS VARCHAR))) AS nombre -- String (20) NOT NULL 
			, LTRIM(RTRIM(CAST([TablaDeMultiproposito].[LETRA] AS VARCHAR))) AS letra -- String (1) [A | B | C | M | R | X] NOT NULL DEFAULT X
			-----------------------------------------------------------------------------------------------------
				-- , [TablaDeMultiproposito].[SUCURSAL] AS sucursal -- Id  NULL
				, [vSucursal].id AS sucursal_id  				  
				, [vSucursal].codigo AS sucursal_codigo
				, [vSucursal].nombre AS sucursal_nombre
				, [vSucursal].abreviatura AS sucursal_abreviatura
				-----------------------------------------------------------------------------------------------------			
					, [vSucursal].sucursalTipo_id AS sucursal_sucursalTipo_id			
					, [vSucursal].sucursalTipo_codigo AS sucursal_sucursalTipo_codigo
					, [vSucursal].sucursalTipo_nombre AS sucursal_sucursalTipo_nombre
				-- ---------------------------------------------------------------------
				, [vSucursal].cuentaClientesDesde AS sucursal_cuentaClientesDesde
				, [vSucursal].cuentaClientesHasta AS sucursal_cuentaClientesHasta
				, [vSucursal].cantidadCaracteresClientes AS sucursal_cantidadCaracteresClientes
				, [vSucursal].identificacionNumericaClientes AS sucursal_identificacionNumericaClientes
				, [vSucursal].permiteCambiarClientes AS sucursal_permiteCambiarClientes
				-- ---------------------------------------------------------------------
				, [vSucursal].clientesOcasionalesDesde AS sucursal_clientesOcasionalesDesde
				, [vSucursal].clientesOcasionalesHasta AS sucursal_clientesOcasionalesHasta
				-- ---------------------------------------------------------------------
				, [vSucursal].nroCobranzaDesde AS sucursal_nroCobranzaDesde
				, [vSucursal].nroCobranzaHasta AS sucursal_nroCobranzaHasta
				-- ---------------------------------------------------------------------
				, [vSucursal].proveedoresDesde AS sucursal_proveedoresDesde
				, [vSucursal].proveedoresHasta AS sucursal_proveedoresHasta
				, [vSucursal].cantidadCaracteresProveedor AS sucursal_cantidadCaracteresProveedor
				, [vSucursal].identificacionNumericaProveedores AS sucursal_identificacionNumericaProveedores
				, [vSucursal].permiteCambiarProveedores AS sucursal_permiteCambiarProveedores       
			-----------------------------------------------------------------------------------------------------		
			, CAST([TablaDeMultiproposito].[AUTONUMERACION] AS INT) AS autonumeracion -- Boolean  
			, CAST([TablaDeMultiproposito].[NUMERACIONPREIMPRESA] AS INT) AS numeracionPreImpresa -- Boolean
			, CAST([TablaDeMultiproposito].[RG10098] AS INT) AS asociadoAlRG10098 -- Boolean  				
			, LTRIM(RTRIM(CAST([TablaDeMultiproposito].[CONTROLFISCAL] AS VARCHAR))) AS asociadoAControladorFiscal -- String (1) NOT NULL [ S | H | E | W | M | X ] DEFAULT S
			, CAST([TablaDeMultiproposito].[PRIMERNUMERO] AS INT) AS primerNumero -- Integer [0 - 99999999] NOT NULL
			, CAST([TablaDeMultiproposito].[PROXIMONUMERO] AS INT) AS proximoNumero -- Integer [0 - 99999999] NOT NULL
			, CAST([TablaDeMultiproposito].[ULTIMONUMERO] AS INT) AS ultimoNumero -- Integer [0 - 99999999] NOT NULL
			, CAST([TablaDeMultiproposito].[ALERTACANTIDADMINIMADECBTES] AS INT) AS cantidadMinimaComprobantes -- Integer [0 - 99999] NOT NULL
			, CAST([TablaDeMultiproposito].[ULTIMAFECHASQL] AS DATE) AS ultimaFecha -- Date	  	  
			, [TablaDeMultiproposito].[CAI] AS numeroCAI -- BigDecimal (15,0) [0 - 99999999999999] NOT NULL
			, CAST([TablaDeMultiproposito].[VENCIMIENTOCAISQL] AS DATE) AS vencimientoCAI  -- Date     
			, CAST([TablaDeMultiproposito].[DIASAVISOVENCIMIENTO] AS INT) AS diasAvisoVencimiento -- Integer  [0 - 999] NOT NULL
			-----------------------------------------------------------------------------------------------------		
			--  ,[DOORNOCAMBIAR]      			
				, [vSeguridadPuerta].id	AS puertaCambiar_id
				, [vSeguridadPuerta].codigo	AS puertaCambiar_codigo
					, [vSeguridadPuerta].seguridadModulo_id	AS puertaCambiar_seguridadModulo_id
					, [vSeguridadPuerta].seguridadModulo_codigo	AS puertaCambiar_seguridadModulo_codigo 
					, [vSeguridadPuerta].seguridadModulo_nombre	AS puertaCambiar_seguridadModulo_nombre 
					, [vSeguridadPuerta].seguridadModulo_congelado	AS puertaCambiar_seguridadModulo_congelado
				, [vSeguridadPuerta].igualacionID	AS puertaCambiar_igualacionID
				, [vSeguridadPuerta].nombre	AS puertaCambiar_nombre
				, [vSeguridadPuerta].congelado	AS puertaCambiar_congelado
			-----------------------------------------------------------------------------------------------------		
	FROM	[dbo].[TablaDeMultiproposito]
	LEFT JOIN	[dbo].[vSucursal]
			ON		[dbo].[vSucursal].[codigo] = CAST([dbo].[TablaDeMultiproposito].[SUCURSAL] AS INT)
	LEFT JOIN	[dbo].[vSeguridadPuerta]
			ON		[dbo].[vSeguridadPuerta].[codigo] = CAST([dbo].[TablaDeMultiproposito].[DOORNOCAMBIAR] AS INT);

	-- SELECT DISTINCT [LETRA] AS letra FROM [dbo].[TablaDeMultiproposito]
	-- SELECT DISTINCT [CONTROLFISCAL] AS letra FROM [dbo].[TablaDeMultiproposito]
	-- SELECT DISTINCT [PRIMERNUMERO] AS letra FROM [dbo].[TablaDeMultiproposito]
	-- SELECT DISTINCT [PROXIMONUMERO] AS letra FROM [dbo].[TablaDeMultiproposito]
	-- SELECT DISTINCT [ULTIMONUMERO] AS letra FROM [dbo].[TablaDeMultiproposito]
	-- SELECT DISTINCT [ALERTACANTIDADMINIMADECBTES] AS letra FROM [dbo].[TablaDeMultiproposito]
	-- SELECT DISTINCT [CAI] AS letra FROM [dbo].[TablaDeMultiproposito]
	-- SELECT DISTINCT [DIASAVISOVENCIMIENTO] AS letra FROM [dbo].[TablaDeMultiproposito]

 	-- SELECT * FROM dbo.vTalonario;	
	-- SELECT * FROM dbo.vTalonario ORDER BY codigo, nombre;	

-------------------------------------------------------------

	-- DROP VIEW [dbo].[vModulo]

CREATE VIEW [dbo].[vModulo] AS  

		
	SELECT 'com.massoftware.model.Modulo' AS ClassModulo, '0' AS id, 0 AS codigo, 'Sin definir' AS nombre
	UNION ALL
	SELECT 'com.massoftware.model.Modulo' AS ClassModulo, '1' AS id, 1, 'Comercialización'
	UNION ALL
	SELECT 'com.massoftware.model.Modulo' AS ClassModulo, '2' AS id, 2, 'Producción'
	UNION ALL
	SELECT 'com.massoftware.model.Modulo' AS ClassModulo, '5' AS id, 5, 'Compras'; 

	-- SELECT * FROM dbo.vModulo;	
	-- SELECT * FROM dbo.vModulo ORDER BY codigo, nombre;

-------------------------------------------------------------

-- DROP VIEW [dbo].[vDeposito]

CREATE VIEW [dbo].[vDeposito] AS  


	SELECT	'com.massoftware.model.Deposito' AS ClassDeposito
			-----------------------------------------------------------------------------------------------------
			, CAST([Depositos].[DEPOSITO] AS VARCHAR) As id			
			-----------------------------------------------------------------------------------------------------
			, CAST([Depositos].[DEPOSITO] AS INT) AS codigo -- Integer NOT NULL [ 1 - 999 ]
			, LTRIM(RTRIM(CAST([Depositos].[NOMBRE] AS VARCHAR))) AS nombre  -- String (35) NOT NULL
			, LTRIM(RTRIM(CAST([Depositos].[ABREVIATURA] AS VARCHAR))) AS abreviatura -- String (4) NOT NULL			
			, CAST([Depositos].[DEPOSITOACTIVO] AS INT) AS depositoActivo -- Boolean 
			-----------------------------------------------------------------------------------------------------
				-- , [Depositos].[SUCURSAL] AS sucursal -- Sucursal Id NOT NULL
				, [vSucursal].id AS sucursal_id  				  
				, [vSucursal].codigo AS sucursal_codigo
				, [vSucursal].nombre AS sucursal_nombre
				, [vSucursal].abreviatura AS sucursal_abreviatura
				-----------------------------------------------------------------------------------------------------			
					, [vSucursal].sucursalTipo_id AS sucursal_sucursalTipo_id			
					, [vSucursal].sucursalTipo_codigo AS sucursal_sucursalTipo_codigo
					, [vSucursal].sucursalTipo_nombre AS sucursal_sucursalTipo_nombre
				-- ---------------------------------------------------------------------
				, [vSucursal].cuentaClientesDesde AS sucursal_cuentaClientesDesde
				, [vSucursal].cuentaClientesHasta AS sucursal_cuentaClientesHasta
				, [vSucursal].cantidadCaracteresClientes AS sucursal_cantidadCaracteresClientes
				, [vSucursal].identificacionNumericaClientes AS sucursal_identificacionNumericaClientes
				, [vSucursal].permiteCambiarClientes AS sucursal_permiteCambiarClientes
				-- ---------------------------------------------------------------------
				, [vSucursal].clientesOcasionalesDesde AS sucursal_clientesOcasionalesDesde
				, [vSucursal].clientesOcasionalesHasta AS sucursal_clientesOcasionalesHasta
				-- ---------------------------------------------------------------------
				, [vSucursal].nroCobranzaDesde AS sucursal_nroCobranzaDesde
				, [vSucursal].nroCobranzaHasta AS sucursal_nroCobranzaHasta
				-- ---------------------------------------------------------------------
				, [vSucursal].proveedoresDesde AS sucursal_proveedoresDesde
				, [vSucursal].proveedoresHasta AS sucursal_proveedoresHasta
				, [vSucursal].cantidadCaracteresProveedor AS sucursal_cantidadCaracteresProveedor
				, [vSucursal].identificacionNumericaProveedores AS sucursal_identificacionNumericaProveedores
				, [vSucursal].permiteCambiarProveedores AS sucursal_permiteCambiarProveedores       
			---------------------------------------------------------- -------------------------------------------
			, CAST([Depositos].[CAJA] AS INT) AS caja -- Integer		// ???????????????????????????????
			-----------------------------------------------------------------------------------------------------	
				-- , [Depositos].[MODULO] AS modulo -- Modulo Id  NOT NULL
				, [vModulo].[id]  AS modulo_id			
				, [vModulo].[codigo] AS modulo_codigo
				, [vModulo].[nombre] AS modulo_nombre
			-----------------------------------------------------------------------------------------------------
			-----------------------------------------------------------------------------------------------------
				-- , [Depositos].[DEPOSITODONDEAGRUPA] AS depositoAgrupacion -- Id Deposito
				, CAST([depositoAgrupacion].[DEPOSITO] AS VARCHAR) AS depositoAgrupacion_id			
				-----------------------------------------------------------------------------------------------------
				, CAST([depositoAgrupacion].[DEPOSITO] AS INT) AS depositoAgrupacion_codigo -- Integer NOT NULL [ 1 - 999 ]
				, LTRIM(RTRIM(CAST([depositoAgrupacion].[NOMBRE] AS VARCHAR))) AS depositoAgrupacion_nombre  -- String (35) NOT NULL
				, LTRIM(RTRIM(CAST([depositoAgrupacion].[ABREVIATURA] AS VARCHAR))) AS depositoAgrupacion_abreviatura -- String (4) NOT NULL			
				, CAST([depositoAgrupacion].[DEPOSITOACTIVO] AS INT) AS depositoAgrupacion_depositoActivo -- Boolean
				-----------------------------------------------------------------------------------------------------
					-- , [depositoAgrupacion].[SUCURSAL] AS sucursal -- Sucursal Id NOT NULL
					, [SucursalDepositoAgrupacion].id AS depositoAgrupacion_sucursal_id  				  
					, [SucursalDepositoAgrupacion].codigo AS depositoAgrupacion_sucursal_codigo
					, [SucursalDepositoAgrupacion].nombre AS depositoAgrupacion_sucursal_nombre
					, [SucursalDepositoAgrupacion].abreviatura AS depositoAgrupacion_sucursal_abreviatura
					-----------------------------------------------------------------------------------------------------			
						, [SucursalDepositoAgrupacion].sucursalTipo_id AS depositoAgrupacion_sucursal_sucursalTipo_id			
						, [SucursalDepositoAgrupacion].sucursalTipo_codigo AS depositoAgrupacion_sucursal_sucursalTipo_codigo
						, [SucursalDepositoAgrupacion].sucursalTipo_nombre AS depositoAgrupacion_sucursal_sucursalTipo_nombre
					-- ---------------------------------------------------------------------
					, [SucursalDepositoAgrupacion].cuentaClientesDesde AS depositoAgrupacion_sucursal_cuentaClientesDesde
					, [SucursalDepositoAgrupacion].cuentaClientesHasta AS depositoAgrupacion_sucursal_cuentaClientesHasta
					, [SucursalDepositoAgrupacion].cantidadCaracteresClientes AS depositoAgrupacion_sucursal_cantidadCaracteresClientes
					, [SucursalDepositoAgrupacion].identificacionNumericaClientes AS depositoAgrupacion_sucursal_identificacionNumericaClientes
					, [SucursalDepositoAgrupacion].permiteCambiarClientes AS depositoAgrupacion_sucursal_permiteCambiarClientes
					-- ---------------------------------------------------------------------
					, [SucursalDepositoAgrupacion].clientesOcasionalesDesde AS depositoAgrupacion_sucursal_clientesOcasionalesDesde
					, [SucursalDepositoAgrupacion].clientesOcasionalesHasta AS depositoAgrupacion_sucursal_clientesOcasionalesHasta
					-- ---------------------------------------------------------------------
					, [SucursalDepositoAgrupacion].nroCobranzaDesde AS depositoAgrupacion_sucursal_nroCobranzaDesde
					, [SucursalDepositoAgrupacion].nroCobranzaHasta AS depositoAgrupacion_sucursal_nroCobranzaHasta
					-- ---------------------------------------------------------------------
					, [SucursalDepositoAgrupacion].proveedoresDesde AS depositoAgrupacion_sucursal_proveedoresDesde
					, [SucursalDepositoAgrupacion].proveedoresHasta AS depositoAgrupacion_sucursal_proveedoresHasta
					, [SucursalDepositoAgrupacion].cantidadCaracteresProveedor AS depositoAgrupacion_sucursal_cantidadCaracteresProveedor
					, [SucursalDepositoAgrupacion].identificacionNumericaProveedores AS depositoAgrupacion_sucursal_identificacionNumericaProveedores
					, [SucursalDepositoAgrupacion].permiteCambiarProveedores AS depositoAgrupacion_sucursal_permiteCambiarProveedores       
			-----------------------------------------------------------------------------------------------------
			-----------------------------------------------------------------------------------------------------
				-- , [Depositos].[DOORNOCONSULTAR] AS puertaOperativo -- SeguridadPuerta Id
				, [puertaOperativo].id	AS puertaOperativo_id
				, [puertaOperativo].codigo	AS puertaOperativo_codigo
					, [puertaOperativo].seguridadModulo_id	AS puertaOperativo_seguridadModulo_id
					, [puertaOperativo].seguridadModulo_codigo	AS puertaOperativo_seguridadModulo_codigo 
					, [puertaOperativo].seguridadModulo_nombre	AS puertaOperativo_seguridadModulo_nombre 
					, [puertaOperativo].seguridadModulo_congelado	AS puertaOperativo_seguridadModulo_congelado
				, [puertaOperativo].igualacionID	AS puertaOperativo_igualacionID
				, [puertaOperativo].nombre	AS puertaOperativo_nombre
				, [puertaOperativo].congelado	AS puertaOperativo_congelado
			-----------------------------------------------------------------------------------------------------
				-- , [Depositos].[DOORNOOPERATIVO] AS puertaConsulta -- SeguridadPuerta Id
				, [puertaConsulta].id	AS puertaConsulta_id
				, [puertaConsulta].codigo	AS puertaConsulta_codigo
					, [puertaConsulta].seguridadModulo_id	AS puertaConsulta_seguridadModulo_id
					, [puertaConsulta].seguridadModulo_codigo	AS puertaConsulta_seguridadModulo_codigo 
					, [puertaConsulta].seguridadModulo_nombre	AS puertaConsulta_seguridadModulo_nombre 
					, [puertaConsulta].seguridadModulo_congelado	AS puertaConsulta_seguridadModulo_congelado
				, [puertaConsulta].igualacionID	AS puertaConsulta_igualacionID
				, [puertaConsulta].nombre	AS puertaConsulta_nombre
				, [puertaConsulta].congelado	AS puertaConsulta_congelado
			-----------------------------------------------------------------------------------------------------

	FROM	[dbo].[Depositos]
	LEFT JOIN	[dbo].[vSucursal]
			ON		[dbo].[vSucursal].[codigo] = CAST([dbo].[Depositos].[SUCURSAL] AS INT)
	LEFT JOIN	[dbo].[vSeguridadPuerta] AS [puertaOperativo]
			ON		[puertaOperativo].[codigo] = CAST([dbo].[Depositos].[DOORNOCONSULTAR] AS INT)
	LEFT JOIN	[dbo].[vSeguridadPuerta] AS [puertaConsulta]
			ON		[puertaConsulta].[codigo] = CAST([dbo].[Depositos].[DOORNOOPERATIVO] AS INT)
	LEFT JOIN	[dbo].[vModulo] 
			ON		[dbo].[vModulo].[codigo] = CAST([dbo].[Depositos].[MODULO] AS INT)
	
	LEFT JOIN	[dbo].[Depositos] AS [depositoAgrupacion] 
			ON		CAST([depositoAgrupacion].[DEPOSITO] AS INT) = CAST([dbo].[Depositos].[DEPOSITODONDEAGRUPA] AS INT)
	LEFT JOIN	[dbo].[vSucursal] AS [SucursalDepositoAgrupacion]
			ON		[SucursalDepositoAgrupacion].[codigo] = CAST([depositoAgrupacion].[SUCURSAL] AS INT);


	-- SELECT DISTINCT [DEPOSITO] FROM [dbo].[Depositos]

	-- SELECT * FROM dbo.vDeposito;	
	-- SELECT * FROM dbo.vDeposito ORDER BY codigo, nombre;	


-------------------------------------------------------------

-- DROP VIEW [dbo].[vTipoCbteControl]

CREATE VIEW [dbo].[vTipoCbteControl] AS  


	SELECT	'com.massoftware.model.TipoCbteControl' AS ClassTipoCbteControl
			-----------------------------------------------------------------------------------------------------
			, CAST([TablaDeStock].[TIPOCBTECONTROL] AS VARCHAR) As id			
			-----------------------------------------------------------------------------------------------------
			, CAST([TablaDeStock].[TIPOCBTECONTROL] AS INT) AS codigo -- Integer NOT NULL
			, LTRIM(RTRIM(CAST([TablaDeStock].[NOMBRE] AS VARCHAR))) AS nombre -- String (30)			 
			, LTRIM(RTRIM(CAST([TablaDeStock].[ABREVIATURA] AS VARCHAR))) AS abreviatura -- String (5)
			, CAST([TablaDeStock].[COLUMNAINFORME] AS INT) AS columnaInforme -- Integer [ 0 - 999 ]

	FROM	[dbo].[TablaDeStock];

	-- SELECT * FROM dbo.vTipoCbteControl;	
	-- SELECT * FROM dbo.vTipoCbteControl ORDER BY codigo, nombre;	


-------------------------------------------------------------

-- DROP VIEW [dbo].[vModeloCbteFondo]

CREATE VIEW [dbo].[vModeloCbteFondo] AS  


	SELECT	'com.massoftware.model.ModeloCbteFondo' AS ClassModeloCbteFondo
			-----------------------------------------------------------------------------------------------------
			, CAST([FondosMod].[NUMERO] AS VARCHAR) As id			
			-----------------------------------------------------------------------------------------------------
			, CAST([FondosMod].[NUMERO] AS INT) AS codigo -- Integer NOT NULL
			, LTRIM(RTRIM(CAST([FondosMod].[NOMBRE] AS VARCHAR))) AS nombre -- String (30) NOT NULL

	FROM	[dbo].[FondosMod];

	-- SELECT * FROM dbo.vModeloCbteFondo;	
	-- SELECT * FROM dbo.vModeloCbteFondo ORDER BY codigo, nombre;	

-------------------------------------------------------------

-- DROP VIEW [dbo].[vModeloCbteFondoItemConcepto]

CREATE VIEW [dbo].[vModeloCbteFondoItemConcepto] AS  

		
	SELECT 'com.massoftware.model.ModeloCbteFondoItemConcepto' AS ClassModeloCbteFondoItemConcepto, 'Haber' AS id, 1 AS codigo, 'Haber' AS nombre
	UNION ALL
	SELECT 'com.massoftware.model.ModeloCbteFondoItemConcepto' AS ClassModeloCbteFondoItemConcepto, 'Debe' AS id, 2, 'Debe'	

	-- SELECT * FROM dbo.vModeloCbteFondoItemConcepto;	
	-- SELECT * FROM dbo.vModeloCbteFondoItemConcepto ORDER BY codigo, nombre;	

-------------------------------------------------------------

-- DROP VIEW [dbo].[vModeloCbteFondoItem]

CREATE VIEW [dbo].[vModeloCbteFondoItem] AS  


	SELECT	'com.massoftware.model.ModeloCbteFondoItem' AS ClassModeloCbteFondoItem
			-----------------------------------------------------------------------------------------------------			
			, CONCAT ( CAST([FondosModMov].[NUMERO] AS VARCHAR), '-', CAST([FondosModMov].[AJUSTASALDO] AS VARCHAR) ) AS id			
			-----------------------------------------------------------------------------------------------------
				-- , CAST([NUMERO] AS INT) AS codigo -- Integer NOT NULL
				, [vModeloCbteFondo].id	AS modeloCbteFondo_id
				, [vModeloCbteFondo].codigo AS modeloCbteFondo_codigo
				, [vModeloCbteFondo].nombre AS modeloCbteFondo_nombre
			-----------------------------------------------------------------------------------------------------
			, LTRIM(RTRIM(CAST([CUENTA]  AS VARCHAR))) AS cuenta -- String (11) NOT NULL
			-----------------------------------------------------------------------------------------------------
				-- , [CONCEPTO]
				, [vModeloCbteFondoItemConcepto].[id]  AS modeloCbteFondoItemConcepto_id			
				, [vModeloCbteFondoItemConcepto].[codigo] AS modeloCbteFondoItemConcepto_codigo
				, [vModeloCbteFondoItemConcepto].[nombre] AS modeloCbteFondoItemConcepto_nombre
			, CAST([AJUSTASALDO] AS INT) AS ajustaSaldo -- Boolean
			, CAST([ELIMINAR] AS INT) AS permiteEliminar -- Boolean

	FROM	[dbo].[FondosModMov]
	LEFT JOIN	[dbo].[vModeloCbteFondo] 
			ON		[dbo].[vModeloCbteFondo].[codigo] = CAST([NUMERO] AS INT)
	LEFT JOIN	[dbo].[vModeloCbteFondoItemConcepto] 
			ON		[dbo].[vModeloCbteFondoItemConcepto].[id] = LTRIM(RTRIM(CAST([dbo].[FondosModMov].[CONCEPTO] AS VARCHAR)));


	-- SELECT * FROM dbo.vModeloCbteFondoItem;	
	-- SELECT * FROM dbo.vModeloCbteFondoItem ORDER BY modeloCbteFondo_codigo, ajustaSaldo;
	
-------------------------------------------------------------

-- DROP VIEW [dbo].[vTipoCbteAFIP]

CREATE VIEW [dbo].[vTipoCbteAFIP] AS  


	SELECT	'com.massoftware.model.TipoCbteAFIP' AS ClassTipoCbteAFIP
			-----------------------------------------------------------------------------------------------------			
			, CAST([AfipTiposCbtes].[TIPO] AS VARCHAR) AS id			
			-----------------------------------------------------------------------------------------------------
			, CAST([AfipTiposCbtes].[TIPO] AS INT) AS codigo -- Integer [ 1 - 99999] NOT NULL
			, LTRIM(RTRIM(CAST([AfipTiposCbtes].[DESCRIPCION] AS VARCHAR))) AS descripcion -- String (80) NOT NULL
  
	FROM	[dbo].[AfipTiposCbtes];


	-- SELECT * FROM dbo.vTipoCbteAFIP;	
	-- SELECT * FROM dbo.vTipoCbteAFIP ORDER BY codigo, descripcion;	
		