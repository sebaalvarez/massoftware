


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


--------------------------------------------------------------

-- DROP FUNCTION dbo.Translate

CREATE  FUNCTION dbo.Translate
(     @Source      VARCHAR(8000)
   , @ReplaceCharOrder    VARCHAR(8000)
   , @ReplaceWithCharOrder   VARCHAR(8000)
)
   RETURNS VARCHAR(8000) AS  

/*  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *

   Object Name      :   dbo.Translate
   Author         :   UB for DCF, on August05, 2008
   Purpose         :   Like TRANSLATE function in Oracle. Charecter-wise replace in source string with given charecters.
   Input         :   
   Output         :   returns @Translated_Source string 
   Version         :   1.0 as of 08/05/2008
   Modification   :   
   Execute         :   SELECT dbo.Translate('ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890', '1234567890', '0987654321')

 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  */


BEGIN 

   --
   --   Validate input 
   --
   IF   @Source   IS NULL
         RETURN NULL
   
   IF    @Source   = ''
         RETURN ''

   IF    @ReplaceCharOrder IS NULL         OR
      @ReplaceCharOrder = ''
		SELECT @ReplaceCharOrder = '/\"'';,_-âãäåāăąàáÁÂÃÄÅĀĂĄÀèééêëēĕėęěĒĔĖĘĚÉÈËÊìíîïìĩīĭÌÍÎÏÌĨĪĬóôõöōŏőòÒÓÔÕÖŌŎŐùúûüũūŭůÙÚÛÜŨŪŬŮçÇñÑ'	
         --RETURN @Source

   IF   @ReplaceWithCharOrder IS NULL   
			SELECT @ReplaceWithCharOrder = '        aaaaaaaaaAAAAAAAAAeeeeeeeeeeEEEEEEEEEiiiiiiiiIIIIIIIIooooooooOOOOOOOOuuuuuuuuUUUUUUUUcCnN'
        -- RETURN 'Invalid parameters in function call dbo.Translate'


   --
   --   Variables used
   --
   DECLARE   @Curr_Pos_In_Source      INT
      , @Check_Source_Str_Len      INT
      , @nth_source         VARCHAR(1)
      , @Found_Match         INT
      , @Translated_Source      VARCHAR(8000)
      , @Match_In_ReplaceWith      VARCHAR(1)


   --
   --   Assign starting values for variables
   --
   SELECT    @Curr_Pos_In_Source      = 1
      , @Check_Source_Str_Len      = LEN(@Source)
      , @Translated_Source      = ''



   --
   --   Replace each charecter with its corrosponding charecter from @ReplaceWithCharOrder
   --
   WHILE @Curr_Pos_In_Source <= @Check_Source_Str_Len
   BEGIN

      --
      --   Get the n'th charecter in @Source
      --
      SELECT @nth_source = SUBSTRING(@Source, @Curr_Pos_In_Source, 1)
   

      --
      --   See if there is a matching character for @nth_source in the @ReplaceCharOrder String, then replace it with 
      --   corrosponding character in @ReplaceWithCharOrder String. If not..go to next n'th character in @Source
      --      If a match is found in @ReplaceCharOrder but no corrosponding character in @ReplaceWithCharOrder
      --      then, replace it with '' (nothing)
      --   Store the resultant string in a separate variable
      --
      SELECT @Found_Match = CHARINDEX(@nth_source, @ReplaceCharOrder COLLATE SQL_Latin1_General_CP1_CS_AS)

      IF @Found_Match > 0
      BEGIN
         --
         --   Finding corrosponding match in the @Found_Match'th position in @ReplaceWithCharOrder
         --   if not found then replace it with '' (nothing)
         --
         SELECT  @Match_In_ReplaceWith = SUBSTRING(@ReplaceWithCharOrder, @Found_Match, 1)

         --
         --   Now replace @nth_source with @Match_In_ReplaceWith and store it in @Translated_Source
         --
         SELECT @Translated_Source = @Translated_Source + @Match_In_ReplaceWith
      END
      ELSE
      BEGIN
         --
         --   No match found in @ReplaceCharOrder
         --
         SELECT @Translated_Source = @Translated_Source + @nth_source
      END

      --
      --   Increment the current position for loop
      --
      SELECT @Curr_Pos_In_Source = @Curr_Pos_In_Source + 1
      
   END
   
   SELECT @Translated_Source = LTRIM(RTRIM(@Translated_Source))

   RETURN  @Translated_Source

END

/*
TESTING:
   SELECT dbo.Translate('ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890', 'abcdefghijklmnopqrstuvwxyz098765432')
   SELECT dbo.Translate('ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890', '0123456789', '9876543210')
   SELECT dbo.Translate('', '', '')
   SELECT dbo.Translate('âãäåāăąàá', 'âãäåāăąàá', 'aaaaaaaaa')

	
	
	 SELECT dbo.Translate(	
						'/\"'';,_-âãäåāăąàáÁÂÃÄÅĀĂĄÀèééêëēĕėęěĒĔĖĘĚÉÈËÊìíîïìĩīĭÌÍÎÏÌĨĪĬóôõöōŏőòÒÓÔÕÖŌŎŐùúûüũūŭůÙÚÛÜŨŪŬŮçÇñÑ', 
						'/\"'';,_-âãäåāăąàáÁÂÃÄÅĀĂĄÀèééêëēĕėęěĒĔĖĘĚÉÈËÊìíîïìĩīĭÌÍÎÏÌĨĪĬóôõöōŏőòÒÓÔÕÖŌŎŐùúûüũūŭůÙÚÛÜŨŪŬŮçÇñÑ', 
						'        aaaaaaaaaAAAAAAAAAeeeeeeeeeeEEEEEEEEEiiiiiiiiIIIIIIIIooooooooOOOOOOOOuuuuuuuuUUUUUUUUcCnN'
					)


				
		
 SELECT dbo.Translate(	
						'/\"'';,_-âãäåāăąàáÁÂÃÄÅĀĂĄÀèééêëēĕėęěĒĔĖĘĚÉÈËÊìíîïìĩīĭÌÍÎÏÌĨĪĬóôõöōŏőòÒÓÔÕÖŌŎŐùúûüũūŭůÙÚÛÜŨŪŬŮçÇñÑ', 
						null,
						null
					)
		
		
			
			

		
 SELECT * FROM dbo.vZona WHERE LOWER(dbo.Translate(nombre, null, null)) = LOWER(dbo.Translate('/\"'';,_-âãäåaaaàááâãäåaaaàèéé', null,null));	


*/




---------------------------------------------------------------


ALTER TABLE [dbo].[SSECUR_User] ADD DEFAULT_EJERCICIO_CONTABLE int NULL;  
		
		
-- VISTAS

-------------------------------------------------------------

-- DROP VIEW [dbo].[vZona]

CREATE VIEW [dbo].[vZona] AS  


	SELECT	'com.massoftware.model.Zona'										AS ClassZona
			-----------------------------------------------------------------------------------------------------
			, LTRIM(RTRIM(CAST([Zonas].[ZONA] AS VARCHAR)))						AS id			
			-----------------------------------------------------------------------------------------------------
			, LTRIM(RTRIM(CAST([Zonas].[ZONA] AS VARCHAR)))						AS codigo		-- String (3) NOT NULL
			, LTRIM(RTRIM(CAST([Zonas].[NOMBRE] AS VARCHAR)))					AS nombre		-- String (30) NOT NULL UN
			, [Zonas].[BONIFICACION]											AS bonificacion	-- BigDecimal (5,2) [ 0 - 99.99] default 0.00
			, [Zonas].[RECARGO]													AS recargo		-- BigDecimal (5,2) [ 0 - 99.99] default 0.00

	FROM	[dbo].[Zonas];
  
	-- SELECT * FROM dbo.vZona;	
	-- SELECT * FROM dbo.vZona ORDER BY codigo, nombre;
	
-------------------------------------------------------------

-- DROP VIEW [dbo].[vPais]

CREATE VIEW [dbo].[vPais] AS  


	SELECT	'com.massoftware.model.Pais'										AS ClassPais
			-----------------------------------------------------------------------------------------------------
			, CAST([Paises].[PAIS] AS VARCHAR)									AS id			
			-----------------------------------------------------------------------------------------------------
			, CAST([Paises].[PAIS] AS INTEGER)									AS codigo		-- Integer NOT NULL [1-254] UN
			, LTRIM(RTRIM(CAST([Paises].[NOMBRE] AS VARCHAR)))					AS nombre		-- String (25) NOT NULL UN
			, LTRIM(RTRIM(CAST([Paises].[ABREVIATURA] AS VARCHAR)))				AS abreviatura	-- String (5) NOT NULL UN	
					
	FROM	[dbo].[Paises];
  
	-- SELECT * FROM dbo.vPais;	
	-- SELECT * FROM dbo.vPais ORDER BY codigo, nombre;	
	-- SELECT * FROM dbo.vPais ORDER BY codigo, nombre;

	SELECT * FROM dbo.vPais WHERE LOWER(dbo.Translate(nombre, null, null)) = LOWER(dbo.Translate('argentina', null,null));
args = []
	
-------------------------------------------------------------

-- DROP VIEW [dbo].[vTipoCliente]

CREATE VIEW [dbo].[vTipoCliente] AS  


	SELECT	'com.massoftware.model.TipoCliente'									AS ClassTipoCliente
			-----------------------------------------------------------------------------------------------------
			, CAST([TablaTiposClientes].[TIPODECLIENTE] AS VARCHAR)				AS id			
			-----------------------------------------------------------------------------------------------------
			, CAST([TablaTiposClientes].[TIPODECLIENTE] AS INTEGER)				AS codigo		-- Integer NOT NULL [0-255] UN
			, LTRIM(RTRIM(CAST([TablaTiposClientes].[NOMBRE] AS VARCHAR)))		AS nombre		-- String (20) NOT NULL UN			
					
	FROM	[dbo].[TablaTiposClientes];
  
	-- SELECT * FROM dbo.vTipoCliente;	
	-- SELECT * FROM dbo.vTipoCliente ORDER BY codigo, nombre;			

-------------------------------------------------------------

-- DROP VIEW [dbo].[vTipoDocumentoAFIP]

CREATE VIEW [dbo].[vTipoDocumentoAFIP] AS  


	SELECT	'com.massoftware.model.TipoDocumentoAFIP'									AS ClassTipoDocumentoAFIP
			-----------------------------------------------------------------------------------------------------
			, CAST([AfipTiposDocumentos].[TIPO] AS VARCHAR)								AS id			
			-----------------------------------------------------------------------------------------------------
			, CAST([AfipTiposDocumentos].[TIPO] AS INTEGER)								AS codigo		-- Integer NOT NULL [0-Short.MAX_VALUE] UN
			, LTRIM(RTRIM(CAST([AfipTiposDocumentos].[DESCRIPCION] AS VARCHAR)))		AS nombre		-- String (40) NOT NULL UN			
					
	FROM	[dbo].[AfipTiposDocumentos];
  
	-- SELECT * FROM dbo.vTipoDocumentoAFIP;	
	-- SELECT * FROM dbo.vTipoDocumentoAFIP ORDER BY codigo, nombre;	

-------------------------------------------------------------

-- DROP VIEW [dbo].[vTipoCbteAFIP]

CREATE VIEW [dbo].[vTipoCbteAFIP] AS  


	SELECT	'com.massoftware.model.TipoCbteAFIP'											AS ClassTipoCbteAFIP
			-----------------------------------------------------------------------------------------------------			
			, CAST([AfipTiposCbtes].[TIPO] AS VARCHAR)										AS id	-- String NOT NULL PK					
			-----------------------------------------------------------------------------------------------------
			, CAST([AfipTiposCbtes].[TIPO] AS INTEGER)										AS codigo -- Integer NOT NULL UN [ 1 - Short.MAX_VALUE] 
			, LTRIM(RTRIM(CAST([AfipTiposCbtes].[DESCRIPCION] AS VARCHAR)))					AS nombre -- String (80) NOT NULL UN 
  
	FROM	[dbo].[AfipTiposCbtes];


	-- SELECT * FROM dbo.vTipoCbteAFIP;	
	-- SELECT * FROM dbo.vTipoCbteAFIP ORDER BY codigo, nombre;	

-------------------------------------------------------------

-- DROP VIEW [dbo].[vMotivoComentario]

CREATE VIEW [dbo].[vMotivoComentario] AS  


	SELECT	'com.massoftware.model.MotivoComentario'										AS ClassMotivoComentario
			-----------------------------------------------------------------------------------------------------			
			, CAST([TablaMotivosComentarios].[MOTIVO] AS VARCHAR)							AS id	-- String NOT NULL PK					
			-----------------------------------------------------------------------------------------------------
			, CAST([TablaMotivosComentarios].[MOTIVO] AS INTEGER)							AS codigo -- Integer NOT NULL UN [ 0 - 255] 
			, LTRIM(RTRIM(CAST([TablaMotivosComentarios].[NOMBRE] AS VARCHAR)))				AS nombre -- String (20) NOT NULL UN 
  
	FROM	[dbo].[TablaMotivosComentarios];


	-- SELECT * FROM dbo.vMotivoComentario;	
	-- SELECT * FROM dbo.vMotivoComentario ORDER BY codigo, nombre;	

-------------------------------------------------------------

-- DROP VIEW [dbo].[vMotivoNotaDeCredito]

CREATE VIEW [dbo].[vMotivoNotaDeCredito] AS  


	SELECT	'com.massoftware.model.MotivoNotaDeCredito'										AS ClassMotivoNotaDeCredito
			-----------------------------------------------------------------------------------------------------			
			, CAST([TablaMotivosNotasDeCredito].[MOTIVO] AS VARCHAR)							AS id	-- String NOT NULL PK					
			-----------------------------------------------------------------------------------------------------
			, CAST([TablaMotivosNotasDeCredito].[MOTIVO] AS INTEGER)							AS codigo -- Integer NOT NULL UN [ 0 - 255] 
			, LTRIM(RTRIM(CAST([TablaMotivosNotasDeCredito].[NOMBRE] AS VARCHAR)))				AS nombre -- String (30) NOT NULL UN 
  
	FROM	[dbo].[TablaMotivosNotasDeCredito];


	-- SELECT * FROM dbo.vMotivoNotaDeCredito;	
	-- SELECT * FROM dbo.vMotivoNotaDeCredito ORDER BY codigo, nombre;	

-------------------------------------------------------------

-- DROP VIEW [dbo].[vCodigoConvenioMultilateral]

CREATE VIEW [dbo].[vCodigoConvenioMultilateral] AS  


	SELECT	'com.massoftware.model.CodigoConvenioMultilateral'						AS ClassCodigoConvenioMultilateral
			-----------------------------------------------------------------------------------------------------			
			, CAST([ActividadesCM].[CODIGOCM] AS VARCHAR)							AS id								-- String NOT NULL PK					
			-----------------------------------------------------------------------------------------------------
			, CAST([ActividadesCM].[CODIGOCM] AS INTEGER)							AS codigo							-- Integer NOT NULL UN [ 1 - Integer.MAX_VALUE] 
			, LTRIM(RTRIM(CAST([ActividadesCM].[CODIGOCONVENIO] AS VARCHAR)))		AS codigoConvenio					-- String (10) NOT NULL UN 
			, LTRIM(RTRIM(CAST([ActividadesCM].[DESCRIPCION] AS VARCHAR)))			AS nombre							-- String (150) NOT NULL UN 
  
	FROM	[dbo].[ActividadesCM];


	-- SELECT * FROM dbo.vCodigoConvenioMultilateral;	
	-- SELECT * FROM dbo.vCodigoConvenioMultilateral ORDER BY codigo, nombre;

-------------------------------------------------------------

-- DROP VIEW [dbo].[vCentroDeCostoProyectoTipo]

CREATE VIEW [dbo].[vCentroDeCostoProyectoTipo] AS  

		
	SELECT 'com.massoftware.model.CentroDeCostoProyectoTipo' AS ClassCentroDeCostoProyectoTipo, '0' AS id, 0 AS codigo, 'Centro de costo' AS nombre
	UNION ALL
	SELECT 'com.massoftware.model.CentroDeCostoProyectoTipo' AS ClassCentroDeCostoProyectoTipo, '1' AS id, 1, 'Proyecto'	

	-- SELECT * FROM dbo.vCentroDeCostoProyectoTipo;	
	-- SELECT * FROM dbo.vCentroDeCostoProyectoTipo ORDER BY codigo, nombre;	

-------------------------------------------------------------
	
-- DROP VIEW [dbo].[vEjercicioContable]; 	

CREATE VIEW [dbo].[vEjercicioContable] AS

	SELECT	'com.massoftware.model.EjercicioContable'									AS ClassEjercicioContable			
			, CAST([EjerciciosContables].[EJERCICIO] AS VARCHAR)						AS id			
			
			, CAST([EjerciciosContables].[EJERCICIO] AS INT)							AS ejercicio				-- Integer NOT NULL UN [ 1 - Integer.MAX_VALUE]  		
			, LTRIM(RTRIM(CAST([EjerciciosContables].[COMENTARIO] AS VARCHAR)))			AS comentario				-- String (255) 
			, CAST([EjerciciosContables].[FECHAAPERTURASQL] AS DATE)					AS fechaApertura			-- Date NOT NULL    		
			, CAST([EjerciciosContables].[FECHACIERRESQL] AS DATE)						AS fechaCierre				-- Date NOT NULL    		
			, CAST([EjerciciosContables].[EJERCICIOCERRADO] AS INT)						AS ejercicioCerrado			-- Boolean NOT NULL    		
			, CAST([EjerciciosContables].[EJERCICIOCERRADOMODULOS] AS INT)				AS ejercicioCerradoModulos  -- Boolean NOT NULL    		

	FROM	[dbo].[EjerciciosContables];
	
	-- SELECT * FROM dbo.[EjerciciosContables] ;
	-- SELECT * FROM dbo.[vEjercicioContable] ;
	-- SELECT * FROM dbo.[vEjercicioContable] WHERE CAST(ejercicio AS VARCHAR)  LIKE CONCAT('%', CAST(12 AS VARCHAR)) ORDER BY ejercicio DESC;
	-- SELECT * FROM dbo.vEjercicioContable ORDER BY ejercicio DESC;



-- DROP VIEW [dbo].[vCentroDeCostoContable]

CREATE VIEW [dbo].[vCentroDeCostoContable] AS        

	SELECT	'com.massoftware.model.CentroDeCostoContable' AS ClassCentroDeCostoContable			
			, CONCAT ( CAST([vEjercicioContable].[ejercicio] AS VARCHAR), '-', CAST([CentrosDeCostoContable].[CENTRODECOSTOCONTABLE] AS VARCHAR) )	AS id										-- String NOT NULL PK					

			, CAST([CentrosDeCostoContable].[CENTRODECOSTOCONTABLE] AS INT)																			AS numero									-- Integer NOT NULL UN [ 1 - Short.MAX_VALUE] 		 
			, LTRIM(RTRIM(CAST([CentrosDeCostoContable].[NOMBRE] AS VARCHAR)))																		AS nombre			 						-- String (30) NOT NULL UN 		
			, LTRIM(RTRIM(CAST([CentrosDeCostoContable].[ABREVIATURA] AS VARCHAR)))																	AS abreviatura			 					-- String (12) NOT NULL UN 			
			
			--,[CentrosDeCostoContable].[EJERCICIO]																																				-- NOT NULL FK
				,vEjercicioContable.id																												AS ejercicioContable_id			
				,vEjercicioContable.ejercicio																										AS ejercicioContable_ejercicio		
				,vEjercicioContable.fechaApertura																									AS ejercicioContable_fechaApertura
				,vEjercicioContable.fechaCierre																										AS ejercicioContable_fechaCierre
				,vEjercicioContable.ejercicioCerrado																								AS ejercicioContable_ejercicioCerrado
				,vEjercicioContable.ejercicioCerradoModulos																							AS ejercicioContable_ejercicioCerradoModulos
				,vEjercicioContable.comentario																										AS ejercicioContable_comentario
			
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
			, CONCAT ( [vEjercicioContable].[ejercicio], '-', CAST(PuntoDeEquilibrio.PUNTODEEQUILIBRIO AS VARCHAR) )	AS id
			
			--,[PuntoDeEquilibrio].[EJERCICIO]   																														-- NOT NULL FK
				,vEjercicioContable.id																					AS ejercicioContable_id							-- String NOT NULL PK	
				,vEjercicioContable.ejercicio																			AS ejercicioContable_ejercicio					
				,vEjercicioContable.fechaApertura																		AS ejercicioContable_fechaApertura
				,vEjercicioContable.fechaCierre																			AS ejercicioContable_fechaCierre
				,vEjercicioContable.ejercicioCerrado																	AS ejercicioContable_ejercicioCerrado
				,vEjercicioContable.ejercicioCerradoModulos																AS ejercicioContable_ejercicioCerradoModulos
				,vEjercicioContable.comentario																			AS ejercicioContable_comentario
			
			, CAST([PuntoDeEquilibrio].[PUNTODEEQUILIBRIO] AS INTEGER)													AS puntoDeEquilibrio							-- Integer NOT NULL UN [ 1 - Short.MAX_VALUE] 
			, LTRIM(RTRIM(CAST([PuntoDeEquilibrio].[NOMBRE] AS VARCHAR)))												AS nombre										-- String (40) NOT NULL UN 
			
			--,[PuntoDeEquilibrio].[TIPO] AS tipo																														-- NOT NULL FK 	
				,vPuntoDeEquilibrioTipo.id																				AS puntoDeEquilibrioTipo_id			
				,vPuntoDeEquilibrioTipo.codigo																			AS puntoDeEquilibrioTipo_codigo
				,vPuntoDeEquilibrioTipo.nombre																			AS puntoDeEquilibrioTipo_nombre
			
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

 -- DROP VIEW [dbo].[vProvincia] 

CREATE VIEW [dbo].[vProvincia] AS        

	SELECT	'com.massoftware.model.Provincia' AS ClassProvincia			
			, CONCAT ( [vPais].[codigo], '-', CAST([Provincias].[PROVINCIA] AS VARCHAR) )	AS id

			-- [PAIS]																		AS FK NOT NULL
			, [vPais].id																	AS pais_id
			, [vPais].codigo																AS pais_codigo
			, [vPais].nombre																AS pais_nombre	
			, [vPais].abreviatura															AS pais_abreviatura	

			, CAST([Provincias].[PROVINCIA] AS INTEGER)										AS codigo			-- Integer NOT NULL UN [ 1 - 255] 
			, LTRIM(RTRIM(CAST([Provincias].[NOMBRE] AS VARCHAR)))							AS nombre			-- String (25) NOT NULL UN 
			, LTRIM(RTRIM(CAST([Provincias].[ABREVIATURA] AS VARCHAR)))						AS abreviatura		-- String (5) NOT NULL UN 			
			, CAST([Provincias].[NROPROVINCIAAFIP] AS INTEGER)								AS codigoAfip		-- Integer [ 0 - 99] 
			, CAST([Provincias].[NROPROVINCIAINGBRUTOS] AS INTEGER)							AS codigoIngBrutos	-- Integer [ 0 - 99] 
			, CAST([Provincias].[NROPROVINCIARENATEA] AS INTEGER)							AS codigoRenatea	-- Integer [ 0 - 255]     
			      
	FROM [dbo].[Provincias]
	LEFT JOIN	[dbo].[vPais]
		ON [dbo].[vPais].[codigo] = CAST([dbo].[Provincias].[PAIS] AS INT);

	-- SELECT * FROM dbo.[Provincias] ;
	-- SELECT * FROM dbo.[vProvincia] ;
	-- SELECT * FROM dbo.[vProvincia] ORDER BY pais_codigo DESC, codigo;	

-------------------------------------------------------------

 -- DROP VIEW [dbo].[vCiudad] 

CREATE VIEW [dbo].[vCiudad] AS        

	SELECT	'com.massoftware.model.Ciudad'													AS ClassCiudad			
			, CAST([Ciudades].[CIUDAD] AS VARCHAR)											AS id


			-- [PROVINCIA]																	AS FK NOT NULL
			, [vProvincia].id																AS provincia_id
			
			-- [PAIS]																		AS FK NOT NULL
			, [vProvincia].pais_id															AS provincia_pais_id
			, [vProvincia].pais_codigo														AS provincia_pais_codigo
			, [vProvincia].pais_nombre														AS provincia_pais_nombre	
			, [vProvincia].pais_abreviatura													AS provincia_pais_abreviatura	

			, [vProvincia].codigo															AS provincia_codigo
			, [vProvincia].nombre															AS provincia_nombre
			, [vProvincia].abreviatura														AS provincia_abreviatura
			, [vProvincia].codigoAfip														AS provincia_codigoAfip
			, [vProvincia].codigoIngBrutos													AS provincia_codigoIngBrutos
			, [vProvincia].codigoRenatea													AS provincia_codigoRenatea	

			, CAST([Ciudades].[CIUDAD] AS INTEGER)											AS codigo			-- Integer NOT NULL UN [ 1 - Short.MAX_VALUE] 
			, LTRIM(RTRIM(CAST([Ciudades].[NOMBRE] AS VARCHAR)))							AS nombre			-- String (35) NOT NULL  
			, LTRIM(RTRIM(CAST([Ciudades].[DEPARTAMENTO] AS VARCHAR)))						AS departamento		-- String (35) 
			, CAST([Ciudades].[CODIGO] AS INTEGER)											AS codigoAfip		-- Integer [ 0 - 9999]  
			      
	FROM	[dbo].[Ciudades]

	LEFT JOIN	[dbo].[vProvincia]
		ON	[dbo].[vProvincia].[pais_codigo]	=	CAST([dbo].[Ciudades].[PAIS] AS INT)
		AND [dbo].[vProvincia].[codigo]			=	CAST([dbo].[Ciudades].[PROVINCIA] AS INT);

		-- UN (pais_id, provincia_codigo, nombre)

	-- SELECT * FROM dbo.[Provincias] ;
	-- SELECT * FROM dbo.[vCiudad] ;
	-- SELECT * FROM dbo.[vCiudad] ORDER BY provincia_pais_codigo, provincia_codigo, codigo;
	-- SELECT * FROM dbo.vCiudad WHERE provincia_pais_codigo = 2 AND provincia_codigo = 7;
	-- SELECT * FROM dbo.vCiudad WHERE provincia_pais_codigo = 2 AND provincia_codigo = 7 AND LOWER(dbo.Translate(nombre, null, null)) = LOWER(dbo.Translate('ciudad3102', null,null));


-------------------------------------------------------------

-- DROP VIEW [dbo].[vBanco]

CREATE VIEW [dbo].[vBanco] AS  		
	
	SELECT	'com.massoftware.model.Banco'											AS ClassBanco
			-----------------------------------------------------------------------------------------------------
			, CAST([Bancos].[BANCO] AS VARCHAR)										AS id					-- String NOT NULL PK
			-----------------------------------------------------------------------------------------------------
			, CAST([Bancos].[BANCO] AS INTEGER)										AS codigo				-- Integer NOT NULL PK [ 0 - 999 ]
			, LTRIM(RTRIM(CAST([Bancos].[NOMBRE] AS VARCHAR)))						AS nombre				-- String (40) NOT NULL UN
			, [Bancos].[CUIT]														AS cuit					-- BigDecimal (13,0) NOT NULL UN
			, [Bancos].[BLOQUEADO]													AS bloqueado			-- Boolean
			
			, CASE    

				WHEN [Bancos].[NOMBRECOMPLETO]										 IS NULL	THEN LTRIM(RTRIM(CAST([Bancos].[NOMBRE] AS VARCHAR)))
				WHEN LEN(LTRIM(RTRIM(CAST([Bancos].[NOMBRECOMPLETO] AS VARCHAR)))) = 0			THEN LTRIM(RTRIM(CAST([Bancos].[NOMBRE] AS VARCHAR)))
																								ELSE LTRIM(RTRIM(CAST([Bancos].[NOMBRECOMPLETO] AS VARCHAR))) 
			  
			  END																	AS nombreOficial		-- String (40) NOT NULL UN

			-- , [Bancos].[PLANILLA]												AS pathPlanilla			-- SE omite dado que se va a pedir cada vez que se haga la conciliación	
			, CAST([Bancos].[HOJA] AS INTEGER)										AS hoja					-- Integer [ 0 - 231 ] Default [0]
			, CAST([Bancos].[PRIMERAFILA] AS INTEGER)								AS primeraFila			-- Integer [ 0 - 999999 ] Default [0]
			, CAST([Bancos].[ULTIMAFILA] AS INTEGER)								AS uiltimaFila			-- Integer [ 0 - 999999 ] Default [0]
			, UPPER(LTRIM(RTRIM(CAST([Bancos].[COLUMNAFECHA] AS VARCHAR))))			AS columnaFecha			-- String (3) 
			, UPPER(LTRIM(RTRIM(CAST([Bancos].[COLUMNADESCRIPCION] AS VARCHAR))))	AS columnaDescripcion	-- String (3) 
			, UPPER(LTRIM(RTRIM(CAST([Bancos].[COLUMNAREFERENCIA1] AS VARCHAR))))	AS columnaReferencia1	-- String (3) 
			, UPPER(LTRIM(RTRIM(CAST([Bancos].[COLUMNAREFERENCIA2] AS VARCHAR))))	AS columnaReferencia2	-- String (3) 
			, UPPER(LTRIM(RTRIM(CAST([Bancos].[COLUMNAIMPORTE] AS VARCHAR))))		AS columnaImporte		-- String (3) 	
			, UPPER(LTRIM(RTRIM(CAST([Bancos].[COLUMNASALDO] AS VARCHAR))))			AS columnaSaldo			-- String (3) 

	FROM	[dbo].[Bancos];	

	-- SELECT * FROM dbo.vBanco;	
	-- SELECT * FROM dbo.vBanco ORDER BY codigo, nombre;	
	-- SELECT * FROM dbo.vBanco ORDER BY codigo;	
	-- SELECT * FROM dbo.vBanco ORDER BY nombre;	
	-- SELECT * FROM dbo.vBanco ORDER BY nombreOficial;	

	

--=============================================================================================================
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
--=============================================================================================================

USE [VetaroRep]
GO



-- DROP VIEW [dbo].[vCentroDeCostoProyecto]

CREATE VIEW [dbo].[vCentroDeCostoProyecto] AS  


	SELECT	'com.massoftware.model.CentroDeCostoProyecto'							AS ClassCentroDeCostoProyecto
			-----------------------------------------------------------------------------------------------------			
			, CAST([CentrosDeCosto].[NUMERO] AS VARCHAR)							AS id	-- String NOT NULL PK					
			-----------------------------------------------------------------------------------------------------
			, CAST([CentrosDeCosto].[NUMERO] AS INTEGER)							AS codigo -- Integer NOT NULL UN [ 1 - Integer.MAX_VALUE] 			
			, LTRIM(RTRIM(CAST([CentrosDeCosto].[NOMBRE] AS VARCHAR)))				AS nombre -- String (30) NOT NULL UN 
			--, [APROPIACIONCONTABLE]
			
			, CASE    
				WHEN [CentrosDeCosto].[CENTRODECOSTOPROYECTO] IS NULL THEN 0
				ELSE CAST([CentrosDeCosto].[CENTRODECOSTOPROYECTO] AS INTEGER) 
			  END																	AS tipo -- Integer NOT NULL UN [ 0 - 1]						 						
			--, CAST([CentrosDeCosto].[CENTRODECOSTOPROYECTO] AS INTEGER)				AS tipo -- Integer NOT NULL UN [ 0 - 1] 	
			
			-----------------------------------------------------------------------------------------------------
				-- , [CENTRODECOSTOPROYECTO]										AS tipo					-- NOT NULL UN [ 0 - 1] 	
				, [vCentroDeCostoProyectoTipo].[id]									AS tipo_id		
				, [vCentroDeCostoProyectoTipo].[codigo]								AS tipo_codigo 
				, [vCentroDeCostoProyectoTipo].[nombre]								AS tipo_nombre 

								
			, [PROYECTOACTIVO]														AS proyectoActivo-- Boolean 						
			, LTRIM(RTRIM(CAST([CentrosDeCosto].[PROYECTOCOMENTARIO] AS VARCHAR)))	AS proyectoComentario -- String (511) NOT NULL UN 			
  
	FROM	[dbo].[CentrosDeCosto]

	LEFT JOIN	[dbo].[vCentroDeCostoProyectoTipo] 
		ON (
			[dbo].[vCentroDeCostoProyectoTipo].[codigo] = CAST([dbo].[CentrosDeCosto].[CENTRODECOSTOPROYECTO] AS INTEGER)
			OR
			(CAST([dbo].[CentrosDeCosto].[CENTRODECOSTOPROYECTO] AS INTEGER) IS NULL AND [dbo].[vCentroDeCostoProyectoTipo].[codigo] = 0)
		);
			

	-- SELECT * FROM dbo.vCentroDeCostoProyecto;	
	-- SELECT * FROM dbo.vCentroDeCostoProyecto ORDER BY codigo, nombre;





--=============================================================================================================
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
--=============================================================================================================
	



















	
-------------------------------------------------------------

-- DROP VIEW [dbo].[vSeguridadModulo]

CREATE VIEW [dbo].[vSeguridadModulo] AS  


	SELECT	'com.massoftware.model.SeguridadModulo'								AS ClassSeguridadModulo
			-----------------------------------------------------------------------------------------------------
			, CAST([SSECUR_DoorGroup].[NO] AS VARCHAR)							AS id			
			-----------------------------------------------------------------------------------------------------
			, CAST([SSECUR_DoorGroup].[NO] AS INTEGER)							AS codigo -- Integer [ 1 - N ] NOT NULL
			, LTRIM(RTRIM(CAST([SSECUR_DoorGroup].[NAME] AS VARCHAR)))			AS nombre -- String (30) NOT NULL UN
			, [SSECUR_DoorGroup].[FREEZE]										AS congelado -- Boolean  			
	FROM	[dbo].[SSECUR_DoorGroup];
  
	-- SELECT * FROM dbo.vSeguridadModulo;	
	-- SELECT * FROM dbo.vSeguridadModulo ORDER BY codigo, nombre;	

-------------------------------------------------------------

-- DROP VIEW [dbo].[vSeguridadPuerta]

CREATE VIEW [dbo].[vSeguridadPuerta] AS  


	SELECT	'com.massoftware.model.SeguridadPuerta'										AS ClassSeguridadPuerta
			-----------------------------------------------------------------------------------------------------
			, CAST([SSECUR_Door].[NO] AS VARCHAR)										AS id			
			-----------------------------------------------------------------------------------------------------
			, CAST([SSECUR_Door].[NO] AS INTEGER)										AS codigo -- Integer  NOT NULL UN [ 1 - Short.MAX_VALUE ]
			-----------------------------------------------------------------------------------------------------
				-- , [DGRPNO]															AS seguridadModulo			-- NOT NULL
				, [vSeguridadModulo].[id]												AS seguridadModulo_id		
				, [vSeguridadModulo].[codigo]											AS seguridadModulo_codigo 
				, [vSeguridadModulo].[nombre]											AS seguridadModulo_nombre 
				, [vSeguridadModulo].[congelado]										AS seguridadModulo_congelado 
			-----------------------------------------------------------------------------------------------------
			, LTRIM(RTRIM(CAST([EQUATE] AS VARCHAR)))									AS igualacionID -- String (30) NOT NULL UN
			, LTRIM(RTRIM(CAST([DESCRIPTION] AS VARCHAR)))								AS nombre -- String (60) NOT NULL UN
			, [SSECUR_Door].[FREEZE]													AS congelado -- Boolean  	

	FROM	[dbo].[SSECUR_Door]

	LEFT JOIN	[dbo].[vSeguridadModulo] 
		ON [dbo].[vSeguridadModulo].[codigo] = CAST([dbo].[SSECUR_Door].[DGRPNO] AS INTEGER);

	-- SELECT * FROM dbo.vSeguridadPuerta;	
	-- SELECT * FROM dbo.vSeguridadPuerta ORDER BY codigo, nombre;	




-------------------------------------------------------------

-- DROP VIEW [dbo].[vUsuario];
	
CREATE VIEW [dbo].[vUsuario] AS

	SELECT	'com.massoftware.model.Usuario' AS ClassUsuario			
			, CAST([SSECUR_User].[NO] AS VARCHAR) AS id			
			, CAST([SSECUR_User].[NO] AS INTEGER) AS numero			
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
		ON [dbo].[vEjercicioContable].ejercicio = CAST([dbo].[SSECUR_User].[DEFAULT_EJERCICIO_CONTABLE] AS INTEGER);

	-- SELECT * FROM dbo.[SSECUR_User] ;
	-- SELECT * FROM dbo.[vUsuario] ;
	-- SELECT * FROM dbo.vUsuario WHERE nombre = 'Administrador';

	






	
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
			, CONCAT ( CAST([vEjercicioContable].[ejercicio] AS VARCHAR), '-', LTRIM(RTRIM(CAST([PlanDeCuentas].[CUENTACONTABLE] AS VARCHAR))) ) As id			
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
	-- SELECT ejercicioContable_ejercicio, cuentaContable  FROM dbo.[vCuentaContable] WHERE ejercicioContable_ejercicio = 2016 AND codigoCuenta = '';	


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

-- DROP VIEW [dbo].[vBancoFirmante]

CREATE VIEW [dbo].[vBancoFirmante] AS  


	SELECT	'com.massoftware.model.BancoFirmante'												AS ClassBancoFirmante
			-----------------------------------------------------------------------------------------------------			
			, CAST([BancosFirmantes].[CODIGO] AS VARCHAR)										AS id	-- String NOT NULL PK					
			-----------------------------------------------------------------------------------------------------
			, CAST([BancosFirmantes].[CODIGO] AS INTEGER)										AS codigo -- Integer NOT NULL UN [ 1 - 255] 
			, LTRIM(RTRIM(CAST([BancosFirmantes].[NOMBRE] AS VARCHAR)))							AS nombre -- String (40) NOT NULL UN 
			, LTRIM(RTRIM(CAST([BancosFirmantes].[CARGO] AS VARCHAR)))							AS cargo -- String (20) 
			, [BancosFirmantes].[ACTIVO]														AS activo -- Boolean
  
	FROM	[dbo].[BancosFirmantes];


	-- SELECT * FROM dbo.vBancoFirmante;	
	-- SELECT * FROM dbo.vBancoFirmante ORDER BY codigo, nombre;	

-------------------------------------------------------------

-- DROP VIEW [dbo].[vCaja]

CREATE VIEW [dbo].[vCaja] AS  


	SELECT	'com.massoftware.model.Caja'														AS ClassCaja
			-----------------------------------------------------------------------------------------------------			
			, CAST([Cajas].[CAJA] AS VARCHAR)													AS id	-- String NOT NULL PK					
			-----------------------------------------------------------------------------------------------------
			, CAST([Cajas].[CAJA] AS INTEGER)													AS codigo -- Integer NOT NULL UN [ 1 - 255] 
			, LTRIM(RTRIM(CAST([Cajas].[NOMBRE] AS VARCHAR)))									AS nombre -- String (20) NOT NULL UN 			
			--, [Cajas].[CONTROLSTOCK]															AS controlStock -- Boolean
			-----------------------------------------------------------------------------------------------------		
			--  , [Cajas].[DOORNOPERMISO]      														AS seguridadPuerta
				, [vSeguridadPuerta].id																AS seguridadPuerta_id
				, [vSeguridadPuerta].codigo															AS seguridadPuerta_codigo
					, [vSeguridadPuerta].seguridadModulo_id											AS seguridadPuerta_seguridadModulo_id
					, [vSeguridadPuerta].seguridadModulo_codigo										AS seguridadPuerta_seguridadModulo_codigo 
					, [vSeguridadPuerta].seguridadModulo_nombre										AS seguridadPuerta_seguridadModulo_nombre 
					, [vSeguridadPuerta].seguridadModulo_congelado									AS seguridadPuerta_seguridadModulo_congelado
				, [vSeguridadPuerta].igualacionID													AS seguridadPuerta_igualacionID
				, [vSeguridadPuerta].nombre															AS seguridadPuerta_nombre
				, [vSeguridadPuerta].congelado														AS seguridadPuerta_congelado
			-----------------------------------------------------------------------------------------------------	
  
	FROM	[dbo].[Cajas]
	LEFT JOIN	[dbo].[vSeguridadPuerta]
			ON		[dbo].[vSeguridadPuerta].[codigo] = CAST([dbo].[Cajas].DOORNOPERMISO AS INTEGER);


	-- SELECT * FROM dbo.vCaja;	
	-- SELECT * FROM dbo.vCaja ORDER BY codigo, nombre;	

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


	SELECT	'com.massoftware.model.Sucursal'												AS ClassSucursal
			-----------------------------------------------------------------------------------------------------
			, CAST([Sucursales].[SUCURSAL] AS VARCHAR)										AS id									-- String NOT NULL PK
			-----------------------------------------------------------------------------------------------------
			, CAST([Sucursales].[SUCURSAL] AS INTEGER)										AS codigo								-- Integer NOT NULL PK [ 0 - 99 ]
			, LTRIM(RTRIM(CAST([Sucursales].[NOMBRE] AS VARCHAR)))							AS nombre								-- String (35) NOT NULL UN
			, LTRIM(RTRIM(CAST([Sucursales].[ABREVIATURA] AS VARCHAR)))						AS abreviatura							-- String (4) NOT NULL UN
			-----------------------------------------------------------------------------------------------------
				--, [Sucursales].[TIPOSUCURSAL]												AS sucursalTipo							-- Integer			
				, vSucursalTipo.id															AS sucursalTipo_id						-- NOT NULL		
				, vSucursalTipo.codigo														AS sucursalTipo_codigo
				, vSucursalTipo.nombre														AS sucursalTipo_nombre
			-- ---------------------------------------------------------------------
			, LTRIM(RTRIM(CAST([Sucursales].[CUENTASCLIENTESDESDE] AS VARCHAR)))			AS cuentaClientesDesde					-- String (6)
			, LTRIM(RTRIM(CAST([Sucursales].[CUENTASCLIENTESHASTA] AS VARCHAR)))			AS cuentaClientesHasta					-- String (6)
			, CAST([Sucursales].[CANTIDADCARACTERESCLIENTES] AS INTEGER)					AS cantidadCaracteresClientes			-- Integer NOT NULL [ 3 | 4 | 5 | 6 ]  DEFAULT 6 
			, [Sucursales].[NUMERICOCLIENTES] 												AS identificacionNumericaClientes		-- Boolean 
			, [Sucursales].[PERMITECAMBIARCLIENTES]											AS permiteCambiarClientes				-- Boolean      
			-- ---------------------------------------------------------------------
			, CAST([Sucursales].[CUENTASCLIENTESOCASIONALESDESDE] AS INTEGER)				AS clientesOcasionalesDesde				-- Integer [9999999999 - (-99999999)]
			, CAST([Sucursales].[CUENTASCLIENTESOCASIONALESHASTA] AS INTEGER)				AS clientesOcasionalesHasta				-- Integer [9999999999 - (-99999999)]    
			-- ---------------------------------------------------------------------
			, CAST([Sucursales].[NROCOBRANZADESDE] AS INTEGER)								AS nroCobranzaDesde						-- Integer [9999999999 - (-99999999)]
			, CAST([Sucursales].[NROCOBRANZAHASTA] AS INTEGER)								AS nroCobranzaHasta						-- Integer [9999999999 - (-99999999)]       	        
			-- ---------------------------------------------------------------------
			, LTRIM(RTRIM(CAST([Sucursales].[CUENTASPROVEEDORESDESDE] AS VARCHAR)))			AS proveedoresDesde						-- String (6) 
			, LTRIM(RTRIM(CAST([Sucursales].[CUENTASPROVEEDORESHASTA] AS VARCHAR)))			AS proveedoresHasta						-- String (6)
			, CAST([Sucursales].[CANTIDADCARACTERESPROVEEDOR] AS INTEGER)					AS cantidadCaracteresProveedor			-- Integer NOT NULL [ 3 | 4 | 5 | 6 ]  DEFAULT 6 
			, [Sucursales].[NUMERICOPROVEEDOR]												AS identificacionNumericaProveedores	-- Boolean	  
			, [Sucursales].[PERMITECAMBIARPROVEEDOR]										AS permiteCambiarProveedores			-- Boolean      
			-- ---------------------------------------------------------------------      
  
		FROM	[dbo].[Sucursales]
		LEFT JOIN	[dbo].[vSucursalTipo]
				ON	[dbo].[vSucursalTipo].[codigo] = CAST([dbo].[Sucursales].[TIPOSUCURSAL] AS INTEGER);

	-- SELECT * FROM dbo.vSucursal;	
	-- SELECT * FROM dbo.vSucursal ORDER BY codigo, nombre;	
	
	
-------------------------------------------------------------

-- DROP VIEW [dbo].[vTalonario]

CREATE VIEW [dbo].[vTalonario] AS  


	SELECT	'com.massoftware.model.Talonario'														AS ClassTalonario
			-----------------------------------------------------------------------------------------------------
			, CAST([TablaDeMultiproposito].[MULTIPROPOSITO]	AS VARCHAR)								AS id -- String NOT NULL PK			
			-----------------------------------------------------------------------------------------------------
			, CAST([TablaDeMultiproposito].[MULTIPROPOSITO] AS INTEGER)								AS codigo -- Integer NOt NULL UN [ 1 - 99999 ] 
			, LTRIM(RTRIM(CAST([TablaDeMultiproposito].[NOMBRE] AS VARCHAR)))						AS nombre -- String (20) NOT NULL UN 
			, LTRIM(RTRIM(CAST([TablaDeMultiproposito].[LETRA] AS VARCHAR)))						AS letra -- String (1) NOT NULL [A | B | C | M | R | X]  DEFAULT X
			-----------------------------------------------------------------------------------------------------
				-- , [TablaDeMultiproposito].[SUCURSAL]												AS sucursal -- Id  NULL
				, [vSucursal].id																	AS sucursal_id  				  
				, [vSucursal].codigo																AS sucursal_codigo
				, [vSucursal].nombre																AS sucursal_nombre
				, [vSucursal].abreviatura															AS sucursal_abreviatura
				-----------------------------------------------------------------------------------------------------			
					, [vSucursal].sucursalTipo_id													AS sucursal_sucursalTipo_id			
					, [vSucursal].sucursalTipo_codigo												AS sucursal_sucursalTipo_codigo
					, [vSucursal].sucursalTipo_nombre												AS sucursal_sucursalTipo_nombre
				-- ---------------------------------------------------------------------
				, [vSucursal].cuentaClientesDesde													AS sucursal_cuentaClientesDesde
				, [vSucursal].cuentaClientesHasta													AS sucursal_cuentaClientesHasta
				, [vSucursal].cantidadCaracteresClientes											AS sucursal_cantidadCaracteresClientes
				, [vSucursal].identificacionNumericaClientes										AS sucursal_identificacionNumericaClientes
				, [vSucursal].permiteCambiarClientes												AS sucursal_permiteCambiarClientes
				-- ---------------------------------------------------------------------
				, [vSucursal].clientesOcasionalesDesde												AS sucursal_clientesOcasionalesDesde
				, [vSucursal].clientesOcasionalesHasta												AS sucursal_clientesOcasionalesHasta
				-- ---------------------------------------------------------------------
				, [vSucursal].nroCobranzaDesde														AS sucursal_nroCobranzaDesde
				, [vSucursal].nroCobranzaHasta														AS sucursal_nroCobranzaHasta
				-- ---------------------------------------------------------------------
				, [vSucursal].proveedoresDesde														AS sucursal_proveedoresDesde
				, [vSucursal].proveedoresHasta														AS sucursal_proveedoresHasta
				, [vSucursal].cantidadCaracteresProveedor											AS sucursal_cantidadCaracteresProveedor
				, [vSucursal].identificacionNumericaProveedores										AS sucursal_identificacionNumericaProveedores
				, [vSucursal].permiteCambiarProveedores												AS sucursal_permiteCambiarProveedores       
			-----------------------------------------------------------------------------------------------------		
			, CAST([TablaDeMultiproposito].[AUTONUMERACION] AS INTEGER)								AS autonumeracion -- Boolean  
			, CAST([TablaDeMultiproposito].[NUMERACIONPREIMPRESA] AS INTEGER)						AS numeracionPreImpresa -- Boolean
			, CAST([TablaDeMultiproposito].[RG10098] AS INTEGER)									AS asociadoAlRG10098 -- Boolean  				
			, LTRIM(RTRIM(CAST([TablaDeMultiproposito].[CONTROLFISCAL] AS VARCHAR)))				AS asociadoAControladorFiscal -- String (1) NOT NULL [ S | H | E | W | M | X ] DEFAULT S
			, CAST([TablaDeMultiproposito].[PRIMERNUMERO] AS INTEGER)								AS primerNumero -- Integer [0 - 99999999] NOT NULL
			, CAST([TablaDeMultiproposito].[PROXIMONUMERO] AS INTEGER)								AS proximoNumero -- Integer [0 - 99999999] NOT NULL
			, CAST([TablaDeMultiproposito].[ULTIMONUMERO] AS INTEGER)								AS ultimoNumero -- Integer [0 - 99999999] NOT NULL
			, CAST([TablaDeMultiproposito].[ALERTACANTIDADMINIMADECBTES] AS INTEGER)				AS cantidadMinimaComprobantes -- Integer [0 - 99999] NOT NULL
			, CAST([TablaDeMultiproposito].[ULTIMAFECHASQL] AS DATE)								AS ultimaFecha -- Date	  	  
			, [TablaDeMultiproposito].[CAI]															AS numeroCAI -- BigDecimal (15,0) [0 - 99999999999999] NOT NULL
			, CAST([TablaDeMultiproposito].[VENCIMIENTOCAISQL] AS DATE)								AS vencimientoCAI  -- Date     
			, CAST([TablaDeMultiproposito].[DIASAVISOVENCIMIENTO] AS INTEGER)						AS diasAvisoVencimiento -- Integer  [0 - 999] NOT NULL
			-----------------------------------------------------------------------------------------------------		
			--  ,[DOORNOCAMBIAR]      			
				, [vSeguridadPuerta].id																AS puertaCambiar_id
				, [vSeguridadPuerta].codigo															AS puertaCambiar_codigo
					, [vSeguridadPuerta].seguridadModulo_id											AS puertaCambiar_seguridadModulo_id
					, [vSeguridadPuerta].seguridadModulo_codigo										AS puertaCambiar_seguridadModulo_codigo 
					, [vSeguridadPuerta].seguridadModulo_nombre										AS puertaCambiar_seguridadModulo_nombre 
					, [vSeguridadPuerta].seguridadModulo_congelado									AS puertaCambiar_seguridadModulo_congelado
				, [vSeguridadPuerta].igualacionID													AS puertaCambiar_igualacionID
				, [vSeguridadPuerta].nombre															AS puertaCambiar_nombre
				, [vSeguridadPuerta].congelado														AS puertaCambiar_congelado
			-----------------------------------------------------------------------------------------------------		
	FROM	[dbo].[TablaDeMultiproposito]
	LEFT JOIN	[dbo].[vSucursal]
			ON		[dbo].[vSucursal].[codigo] = CAST([dbo].[TablaDeMultiproposito].[SUCURSAL] AS INTEGER)
	LEFT JOIN	[dbo].[vSeguridadPuerta]
			ON		[dbo].[vSeguridadPuerta].[codigo] = CAST([dbo].[TablaDeMultiproposito].[DOORNOCAMBIAR] AS INTEGER);

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
	-- SELECT * FROM dbo.vTalonario WHERE codigo = 113 ORDER BY codigo, nombre;	
	
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


	SELECT	'com.massoftware.model.Deposito'																		AS ClassDeposito
			-----------------------------------------------------------------------------------------------------
			, CAST([Depositos].[DEPOSITO] AS VARCHAR)																AS id	-- String NOT NULL PK					
			-----------------------------------------------------------------------------------------------------
			, CAST([Depositos].[DEPOSITO] AS INTEGER)																AS codigo -- Integer NOT NULL [ 1 - 999 ]
			, LTRIM(RTRIM(CAST([Depositos].[NOMBRE] AS VARCHAR)))													AS nombre  -- String (35) NOT NULL
			, LTRIM(RTRIM(CAST([Depositos].[ABREVIATURA] AS VARCHAR)))												AS abreviatura -- String (4) NOT NULL			
			, [Depositos].[DEPOSITOACTIVO]																			AS depositoActivo -- Boolean 
			-----------------------------------------------------------------------------------------------------
				-- , [Depositos].[SUCURSAL]																			AS sucursal -- Sucursal Id NOT NULL
				, [vSucursal].id																					AS sucursal_id  				  
				, [vSucursal].codigo																				AS sucursal_codigo
				, [vSucursal].nombre																				AS sucursal_nombre
				, [vSucursal].abreviatura																			AS sucursal_abreviatura
				-----------------------------------------------------------------------------------------------------			
					, [vSucursal].sucursalTipo_id																	AS sucursal_sucursalTipo_id			
					, [vSucursal].sucursalTipo_codigo																AS sucursal_sucursalTipo_codigo
					, [vSucursal].sucursalTipo_nombre																AS sucursal_sucursalTipo_nombre
				-- ---------------------------------------------------------------------
				, [vSucursal].cuentaClientesDesde																	AS sucursal_cuentaClientesDesde
				, [vSucursal].cuentaClientesHasta																	AS sucursal_cuentaClientesHasta
				, [vSucursal].cantidadCaracteresClientes															AS sucursal_cantidadCaracteresClientes
				, [vSucursal].identificacionNumericaClientes														AS sucursal_identificacionNumericaClientes
				, [vSucursal].permiteCambiarClientes																AS sucursal_permiteCambiarClientes
				-- ---------------------------------------------------------------------
				, [vSucursal].clientesOcasionalesDesde																AS sucursal_clientesOcasionalesDesde
				, [vSucursal].clientesOcasionalesHasta																AS sucursal_clientesOcasionalesHasta
				-- ---------------------------------------------------------------------
				, [vSucursal].nroCobranzaDesde																		AS sucursal_nroCobranzaDesde
				, [vSucursal].nroCobranzaHasta																		AS sucursal_nroCobranzaHasta
				-- ---------------------------------------------------------------------
				, [vSucursal].proveedoresDesde																		AS sucursal_proveedoresDesde
				, [vSucursal].proveedoresHasta																		AS sucursal_proveedoresHasta
				, [vSucursal].cantidadCaracteresProveedor															AS sucursal_cantidadCaracteresProveedor
				, [vSucursal].identificacionNumericaProveedores														AS sucursal_identificacionNumericaProveedores
				, [vSucursal].permiteCambiarProveedores																AS sucursal_permiteCambiarProveedores       
			---------------------------------------------------------- -------------------------------------------
			-- , CAST([Depositos].[CAJA] AS INT)																	AS caja -- Integer		// No se usa !!!!!!!!!!!!!!!!!!!!!
			-----------------------------------------------------------------------------------------------------	
				-- , [Depositos].[MODULO]																			AS modulo -- Modulo Id  NOT NULL
				, [vModulo].[id]																					AS modulo_id			
				, [vModulo].[codigo]																				AS modulo_codigo
				, [vModulo].[nombre]																				AS modulo_nombre
			-----------------------------------------------------------------------------------------------------

			/*

			-----------------------------------------------------------------------------------------------------
				-- , [Depositos].[DEPOSITODONDEAGRUPA] AS depositoAgrupacion -- Id Deposito
				, CAST([depositoAgrupacion].[DEPOSITO] AS VARCHAR)													AS depositoAgrupacion_id			
				-----------------------------------------------------------------------------------------------------
				, CAST([depositoAgrupacion].[DEPOSITO] AS INT)														AS depositoAgrupacion_codigo -- Integer NOT NULL [ 1 - 999 ]
				, LTRIM(RTRIM(CAST([depositoAgrupacion].[NOMBRE] AS VARCHAR)))										AS depositoAgrupacion_nombre  -- String (35) NOT NULL
				, LTRIM(RTRIM(CAST([depositoAgrupacion].[ABREVIATURA] AS VARCHAR)))									AS depositoAgrupacion_abreviatura -- String (4) NOT NULL			
				, CAST([depositoAgrupacion].[DEPOSITOACTIVO] AS INT)												AS depositoAgrupacion_depositoActivo -- Boolean
				-----------------------------------------------------------------------------------------------------
					-- , [depositoAgrupacion].[SUCURSAL] AS sucursal -- Sucursal Id NOT NULL
					, [SucursalDepositoAgrupacion].id																AS depositoAgrupacion_sucursal_id  				  
					, [SucursalDepositoAgrupacion].codigo															AS depositoAgrupacion_sucursal_codigo
					, [SucursalDepositoAgrupacion].nombre															AS depositoAgrupacion_sucursal_nombre
					, [SucursalDepositoAgrupacion].abreviatura														AS depositoAgrupacion_sucursal_abreviatura
					-----------------------------------------------------------------------------------------------------			
						, [SucursalDepositoAgrupacion].sucursalTipo_id												AS depositoAgrupacion_sucursal_sucursalTipo_id			
						, [SucursalDepositoAgrupacion].sucursalTipo_codigo											AS depositoAgrupacion_sucursal_sucursalTipo_codigo
						, [SucursalDepositoAgrupacion].sucursalTipo_nombre											AS depositoAgrupacion_sucursal_sucursalTipo_nombre
					-- ---------------------------------------------------------------------
					, [SucursalDepositoAgrupacion].cuentaClientesDesde												AS depositoAgrupacion_sucursal_cuentaClientesDesde
					, [SucursalDepositoAgrupacion].cuentaClientesHasta												AS depositoAgrupacion_sucursal_cuentaClientesHasta
					, [SucursalDepositoAgrupacion].cantidadCaracteresClientes										AS depositoAgrupacion_sucursal_cantidadCaracteresClientes
					, [SucursalDepositoAgrupacion].identificacionNumericaClientes									AS depositoAgrupacion_sucursal_identificacionNumericaClientes
					, [SucursalDepositoAgrupacion].permiteCambiarClientes											AS depositoAgrupacion_sucursal_permiteCambiarClientes
					-- ---------------------------------------------------------------------
					, [SucursalDepositoAgrupacion].clientesOcasionalesDesde											AS depositoAgrupacion_sucursal_clientesOcasionalesDesde
					, [SucursalDepositoAgrupacion].clientesOcasionalesHasta											AS depositoAgrupacion_sucursal_clientesOcasionalesHasta
					-- ---------------------------------------------------------------------
					, [SucursalDepositoAgrupacion].nroCobranzaDesde													AS depositoAgrupacion_sucursal_nroCobranzaDesde
					, [SucursalDepositoAgrupacion].nroCobranzaHasta													AS depositoAgrupacion_sucursal_nroCobranzaHasta
					-- ---------------------------------------------------------------------
					, [SucursalDepositoAgrupacion].proveedoresDesde													AS depositoAgrupacion_sucursal_proveedoresDesde
					, [SucursalDepositoAgrupacion].proveedoresHasta													AS depositoAgrupacion_sucursal_proveedoresHasta
					, [SucursalDepositoAgrupacion].cantidadCaracteresProveedor										AS depositoAgrupacion_sucursal_cantidadCaracteresProveedor
					, [SucursalDepositoAgrupacion].identificacionNumericaProveedores								AS depositoAgrupacion_sucursal_identificacionNumericaProveedores
					, [SucursalDepositoAgrupacion].permiteCambiarProveedores										AS depositoAgrupacion_sucursal_permiteCambiarProveedores       
			-----------------------------------------------------------------------------------------------------

			*/
			-----------------------------------------------------------------------------------------------------
				-- , [Depositos].[DOORNOCONSULTAR]																	AS puertaOperativo -- SeguridadPuerta Id
				, [puertaOperativo].id																				AS puertaOperativo_id
				, [puertaOperativo].codigo																			AS puertaOperativo_codigo
					, [puertaOperativo].seguridadModulo_id															AS puertaOperativo_seguridadModulo_id
					, [puertaOperativo].seguridadModulo_codigo														AS puertaOperativo_seguridadModulo_codigo 
					, [puertaOperativo].seguridadModulo_nombre														AS puertaOperativo_seguridadModulo_nombre 
					, [puertaOperativo].seguridadModulo_congelado													AS puertaOperativo_seguridadModulo_congelado
				, [puertaOperativo].igualacionID																	AS puertaOperativo_igualacionID
				, [puertaOperativo].nombre																			AS puertaOperativo_nombre
				, [puertaOperativo].congelado																		AS puertaOperativo_congelado
			-----------------------------------------------------------------------------------------------------
				-- , [Depositos].[DOORNOOPERATIVO]																	AS puertaConsulta -- SeguridadPuerta Id
				, [puertaConsulta].id																				AS puertaConsulta_id
				, [puertaConsulta].codigo																			AS puertaConsulta_codigo
					, [puertaConsulta].seguridadModulo_id															AS puertaConsulta_seguridadModulo_id
					, [puertaConsulta].seguridadModulo_codigo														AS puertaConsulta_seguridadModulo_codigo 
					, [puertaConsulta].seguridadModulo_nombre														AS puertaConsulta_seguridadModulo_nombre 
					, [puertaConsulta].seguridadModulo_congelado													AS puertaConsulta_seguridadModulo_congelado
				, [puertaConsulta].igualacionID																		AS puertaConsulta_igualacionID
				, [puertaConsulta].nombre																			AS puertaConsulta_nombre
				, [puertaConsulta].congelado																		AS puertaConsulta_congelado
			-----------------------------------------------------------------------------------------------------

	FROM	[dbo].[Depositos]
	LEFT JOIN	[dbo].[vSucursal]
			ON		[dbo].[vSucursal].[codigo] = CAST([dbo].[Depositos].[SUCURSAL] AS INTEGER)
	LEFT JOIN	[dbo].[vSeguridadPuerta] AS [puertaOperativo]
			ON		[puertaOperativo].[codigo] = CAST([dbo].[Depositos].[DOORNOCONSULTAR] AS INTEGER)
	LEFT JOIN	[dbo].[vSeguridadPuerta] AS [puertaConsulta]
			ON		[puertaConsulta].[codigo] = CAST([dbo].[Depositos].[DOORNOOPERATIVO] AS INTEGER)
	LEFT JOIN	[dbo].[vModulo] 
			ON		[dbo].[vModulo].[codigo] = CAST([dbo].[Depositos].[MODULO] AS INTEGER);
	/*
	LEFT JOIN	[dbo].[Depositos] AS [depositoAgrupacion] 
			ON		CAST([depositoAgrupacion].[DEPOSITO] AS INT) = CAST([dbo].[Depositos].[DEPOSITODONDEAGRUPA] AS INTEGER)
	LEFT JOIN	[dbo].[vSucursal] AS [SucursalDepositoAgrupacion]
			ON		[SucursalDepositoAgrupacion].[codigo] = CAST([depositoAgrupacion].[SUCURSAL] AS INTEGER);
	*/

	-- SELECT DISTINCT [DEPOSITO] FROM [dbo].[Depositos]

	-- SELECT * FROM dbo.vDeposito;	
	-- SELECT * FROM dbo.vDeposito ORDER BY codigo, nombre;	
	-- SELECT * FROM dbo.vDeposito WHERE sucursal_codigo = 4 ORDER BY codigo, nombre;


-------------------------------------------------------------

-- USE [VetaroRep]
-- GO

-- DROP VIEW [dbo].[vTipoCbteControl]

CREATE VIEW [dbo].[vTipoCbteControl] AS  


	SELECT	'com.massoftware.model.TipoCbteControl'										AS ClassTipoCbteControl
			-----------------------------------------------------------------------------------------------------
			, CAST([TablaDeStock].[TIPOCBTECONTROL] AS VARCHAR)							As id	-- String NOT NULL PK					
			-----------------------------------------------------------------------------------------------------
			, CAST([TablaDeStock].[TIPOCBTECONTROL] AS INTEGER)							AS codigo -- Integer NOT NULL UN [ 1 - 255 ]
			, LTRIM(RTRIM(CAST([TablaDeStock].[NOMBRE] AS VARCHAR)))					AS nombre -- String (30) UN NOT NULL			 
			, LTRIM(RTRIM(CAST([TablaDeStock].[ABREVIATURA] AS VARCHAR)))				AS abreviatura -- String (5) UN NOT NULL
			, CAST([TablaDeStock].[COLUMNAINFORME] AS INTEGER)							AS columnaInforme -- Integer [ 1 - 255 ] NOT NULL

	FROM	[dbo].[TablaDeStock];

	-- SELECT * FROM dbo.vTipoCbteControl;	
	-- SELECT * FROM dbo.vTipoCbteControl ORDER BY codigo, nombre;	



-------------------------------------------------------------

-- DROP VIEW [dbo].[vMonedaAFIP]

CREATE VIEW [dbo].[vMonedaAFIP] AS  


	SELECT	'com.massoftware.model.MonedaAFIP'												AS ClassMonedaAFIP
			-----------------------------------------------------------------------------------------------------			
			, LTRIM(RTRIM(CAST([AfipMonedas].[MONEDAAFIP] AS VARCHAR)))						AS id	-- String NOT NULL PK					
			-----------------------------------------------------------------------------------------------------
			, LTRIM(RTRIM(CAST([AfipMonedas].[MONEDAAFIP]  AS VARCHAR)))					AS codigo -- String (3) NOT NULL UN 
			, LTRIM(RTRIM(CAST([AfipMonedas].[DESCRIPCION] AS VARCHAR)))					AS nombre -- String (30) NOT NULL UN 
  
	FROM	[dbo].[AfipMonedas];


	-- SELECT * FROM dbo.vMonedaAFIP;	
	-- SELECT * FROM dbo.vMonedaAFIP ORDER BY codigo, nombre;	

-------------------------------------------------------------

-- DROP VIEW [dbo].[vMoneda]

CREATE VIEW [dbo].[vMoneda] AS  


	SELECT	'com.massoftware.model.Moneda'												AS ClassMoneda
			-----------------------------------------------------------------------------------------------------			
			, CAST([Monedas].[MONEDA] AS VARCHAR)										AS id	-- String NOT NULL PK	
			-----------------------------------------------------------------------------------------------------
			, CAST([Monedas].[MONEDA] AS INTEGER)										AS codigo -- Integer NOT NULL UN [ 1 - Short.MAX_VALUE]				 
			, LTRIM(RTRIM(CAST([Monedas].[DESCRIPCION] AS VARCHAR)))					AS nombre -- String (30) NOT NULL UN 
			, LTRIM(RTRIM(CAST([Monedas].[ABREVIATURA] AS VARCHAR)))					AS abreviatura -- String (5) NOT NULL UN 
			, [Monedas].[COTIZACION]													AS cotizacion -- BigDecimal (9,4) [0 - 999999999] 
			, CAST([Monedas].[FECHASQL] AS DATE)										AS fecha  -- Date     
			, [Monedas].[CONTROLDEACTUALIZACION]										AS controlDeActualizacion -- Boolean
			------------------------------------------------------------------------------------------------------
				-- , [Monedas].[MONEDAAFIP]												AS monedaAFIP
				, [vMonedaAFIP].id														AS monedaAFIP_id
				, [vMonedaAFIP].codigo													AS monedaAFIP_codigo 
				, [vMonedaAFIP].nombre													AS monedaAFIP_nombre 
  
	FROM	[dbo].[Monedas]
	LEFT JOIN	[dbo].[vMonedaAFIP]
			ON		[dbo].[vMonedaAFIP].[codigo] =  LTRIM(RTRIM(CAST([dbo].[Monedas].[MONEDAAFIP] AS VARCHAR)));

	-- SELECT * FROM dbo.vMoneda;	
	-- SELECT * FROM dbo.vMoneda ORDER BY codigo, nombre;	

-------------------------------------------------------------

-- DROP VIEW [dbo].[vMonedaCotizacion]

CREATE VIEW [dbo].[vMonedaCotizacion] AS  

	SELECT	'com.massoftware.model.MonedaCotizacion'																				AS ClassMonedaCotizacion
			-----------------------------------------------------------------------------------------------------			
			, CONCAT ( CAST([MonedasCotizaciones].[MONEDA] AS VARCHAR), '-', CAST([MonedasCotizaciones].[FECHASQL] AS VARCHAR) )	AS id	-- String NOT NULL PK	
			-----------------------------------------------------------------------------------------------------
				--, [MonedasCotizaciones].[MONEDA]													AS moneda	-- NOT NULL
				, [vMoneda].id																		AS moneda_id
				, [vMoneda].codigo																	AS moneda_codigo
				, [vMoneda].nombre																	AS moneda_nombre
				, [vMoneda].abreviatura																AS moneda_abreviatura
				, [vMoneda].cotizacion																AS moneda_cotizacion
				, [vMoneda].fecha																	AS moneda_fecha
				, [vMoneda].controlDeActualizacion													AS moneda_controlDeActualizacion		
				------------------------------------------------------------------------------------------------------					
					, [vMoneda].monedaAFIP_id														AS moneda_monedaAFIP_id	
					, [vMoneda].monedaAFIP_codigo													AS moneda_monedaAFIP_codigo	
					, [vMoneda].monedaAFIP_nombre													AS moneda_monedaAFIP_nombre
			-----------------------------------------------------------------------------------------------------
			, CAST([MonedasCotizaciones].[FECHASQL] AS DATE)										AS fecha		-- Date NOT NULL 
			, [MonedasCotizaciones].[COMPRA]														AS compra		-- BigDecimal (9,4) [0 - 999999999] 
			, [MonedasCotizaciones].[VENTA]															AS venta		-- BigDecimal (9,4) [0 - 999999999] 
			, CAST([MonedasCotizaciones].[FECHAINGRESOSQL] AS DATETIME)								AS fechaIngreso	-- Date NOT NULL 
			--, CAST([MonedasCotizaciones].[FECHAINGRESOSQL] AS DATE)								AS fechaIngreso	-- Date 
			--, CAST([MonedasCotizaciones].[FECHAINGRESOSQL] AS TIME)								AS horaIngreso	-- Time 
			-----------------------------------------------------------------------------------------------------
				--, [MonedasCotizaciones].[USUARIO]													AS usuario -- NOT NULL 
				, [vUsuario].id																		AS usuario_id
				, [vUsuario].numero																	AS usuario_numero
				, [vUsuario].nombre																	AS usuario_nombre
					, [vUsuario].ejercicioContable_id												AS usuario_ejercicioContable_id				
					, [vUsuario].ejercicioContable_ejercicio										AS usuario_ejercicioContable_ejercicio
					, [vUsuario].ejercicioContable_fechaApertura									AS usuario_ejercicioContable_fechaApertura
					, [vUsuario].ejercicioContable_fechaCierre										AS usuario_ejercicioContable_fechaCierre	
					, [vUsuario].ejercicioContable_ejercicioCerrado									AS usuario_ejercicioContable_ejercicioCerrado
					, [vUsuario].ejercicioContable_ejercicioCerradoModulos							AS usuario_ejercicioContable_ejercicioCerradoModulos
					, [vUsuario].ejercicioContable_comentario										AS usuario_ejercicioContable_comentario      


	FROM [dbo].[MonedasCotizaciones]
	LEFT JOIN	[dbo].[vUsuario] 
		ON [dbo].[vUsuario].[numero] = CAST([dbo].[MonedasCotizaciones].[USUARIO] AS INTEGER)
	LEFT JOIN	[dbo].[vMoneda] 
		ON [dbo].[vMoneda].[codigo] = CAST([dbo].[MonedasCotizaciones].[MONEDA] AS INTEGER);	


	-- SELECT * FROM dbo.vMonedaCotizacion;	
	-- SELECT COUNT(*) FROM dbo.vMonedaCotizacion;	
	-- SELECT * FROM dbo.vMonedaCotizacion ORDER BY fecha, moneda_codigo;	
	-- SELECT fechaIngreso FROM dbo.vMonedaCotizacion ORDER BY moneda_codigo, fecha DESC ;	


-------------------------------------------------------------

-- DROP VIEW [dbo].[vCuentaDeFondoRubro]

CREATE VIEW [dbo].[vCuentaDeFondoRubro] AS  


	SELECT	'com.massoftware.model.CuentaDeFondoRubro'										AS ClassCuentaDeFondoRubro
			-----------------------------------------------------------------------------------------------------			
			, CAST([CuentasDeFondosRubro].[RUBRO] AS VARCHAR)								AS id	-- String NOT NULL PK					
			-----------------------------------------------------------------------------------------------------
			, CAST([CuentasDeFondosRubro].[RUBRO]  AS INTEGER)								AS codigo -- Integer NOT NULL UN [ 1 - Short.MAX_VALUE]
			, LTRIM(RTRIM(CAST([CuentasDeFondosRubro].[NOMBRE] AS VARCHAR)))				AS nombre -- String (30) NOT NULL UN 
			, [CuentasDeFondosRubro].[ESCUENTADEFONDO]										AS esCuentaDeFondo -- Boolean
  
	FROM	[dbo].[CuentasDeFondosRubro];


	-- SELECT * FROM dbo.vCuentaDeFondoRubro;	
	-- SELECT * FROM dbo.vCuentaDeFondoRubro ORDER BY codigo, nombre;

-------------------------------------------------------------

-- DROP VIEW [dbo].[vCuentaDeFondoGrupo]

CREATE VIEW [dbo].[vCuentaDeFondoGrupo] AS  


	SELECT	'com.massoftware.model.CuentaDeFondoGrupo'										AS ClassCuentaDeFondoGrupo
			-----------------------------------------------------------------------------------------------------			
			, CONCAT ( CAST([CuentasDeFondosGrupo].[RUBRO] AS VARCHAR), '-', CAST([CuentasDeFondosGrupo].[GRUPO] AS VARCHAR) )	AS id	-- String NOT NULL PK	
			-----------------------------------------------------------------------------------------------------
				, [vCuentaDeFondoRubro].id													AS cuentaDeFondoRubro_id	-- String NOT NULL PK								
				, [vCuentaDeFondoRubro].codigo												AS cuentaDeFondoRubro_codigo -- Integer NOT NULL UN [ 1 - Short.MAX_VALUE]
				, [vCuentaDeFondoRubro].nombre												AS cuentaDeFondoRubro_nombre -- String (30) NOT NULL UN 
				, [vCuentaDeFondoRubro].esCuentaDeFondo										AS cuentaDeFondoRubro_esCuentaDeFondo -- Boolean
			-----------------------------------------------------------------------------------------------------			
			, CAST([CuentasDeFondosGrupo].[GRUPO]  AS INTEGER)								AS codigo -- Integer NOT NULL UN [ 1 - Short.MAX_VALUE]
			, LTRIM(RTRIM(CAST([CuentasDeFondosGrupo].[NOMBRE] AS VARCHAR)))				AS nombre -- String (30) NOT NULL UN 
			
  
	FROM	[dbo].[CuentasDeFondosGrupo]
	LEFT JOIN	[dbo].[vCuentaDeFondoRubro] 
		ON [dbo].[vCuentaDeFondoRubro].[codigo] = CAST([dbo].[CuentasDeFondosGrupo].[RUBRO] AS INTEGER);	

	-- SELECT * FROM dbo.vCuentaDeFondoGrupo;	
	-- SELECT * FROM dbo.vCuentaDeFondoGrupo ORDER BY codigo, nombre;

-------------------------------------------------------------

	-- DROP VIEW [dbo].[vCuentaDeFondoTipo]

CREATE VIEW [dbo].[vCuentaDeFondoTipo] AS  

		
	SELECT 'com.massoftware.model.CuentaDeFondoTipo' AS ClassModulo, '1' AS id, 1 AS codigo, 'Caja' AS nombre
	UNION ALL
	SELECT 'com.massoftware.model.CuentaDeFondoTipo' AS ClassModulo, '2' AS id, 2, 'Banco'
	UNION ALL
	SELECT 'com.massoftware.model.CuentaDeFondoTipo' AS ClassModulo, '3' AS id, 3, 'Cartera'
	UNION ALL
	SELECT 'com.massoftware.model.CuentaDeFondoTipo' AS ClassModulo, '4' AS id, 4, 'Tarjeta'
	UNION ALL
	SELECT 'com.massoftware.model.CuentaDeFondoTipo' AS ClassModulo, '5' AS id, 5, 'Otra'; 

	-- SELECT * FROM dbo.vCuentaDeFondoTipo;	
	-- SELECT * FROM dbo.vCuentaDeFondoTipo ORDER BY codigo, nombre;

-------------------------------------------------------------

	-- DROP VIEW [dbo].[vCuentaDeFondoTipoBanco]

CREATE VIEW [dbo].[vCuentaDeFondoTipoBanco] AS  

		
	SELECT 'com.massoftware.model.CuentaDeFondoTipoBanco' AS ClassModulo, '1' AS id, 1 AS codigo, 'Caja de ahorro' AS nombre
	UNION ALL
	SELECT 'com.massoftware.model.CuentaDeFondoTipoBanco' AS ClassModulo, '2' AS id, 2, 'Cuenta corriente'
	UNION ALL
	SELECT 'com.massoftware.model.CuentaDeFondoTipoBanco' AS ClassModulo, '3' AS id, 3, 'Cheques diferidos'
	; 

	-- SELECT * FROM dbo.vCuentaDeFondoTipoBanco;	
	-- SELECT * FROM dbo.vCuentaDeFondoTipoBanco ORDER BY codigo, nombre;

-------------------------------------------------------------

-- DROP VIEW [dbo].[vCuentaDeFondo]

CREATE VIEW [dbo].[vCuentaDeFondo] AS  

	SELECT	'com.massoftware.model.CuentaDeFondo' AS ClassCuentaDeFondo
		
			-----------------------------------------------------------------------------------------------------
			, LTRIM(RTRIM(CAST([CuentasDeFondos].[CUENTA] AS VARCHAR)))												AS id	-- String NOT NULL PK			
			-----------------------------------------------------------------------------------------------------			
			, CAST([CuentasDeFondos].[CUENTA] AS INTEGER)															AS codigo	 -- Integer NOT NULL UN  [1 - Integer.MAX_VALUE]				
			, LTRIM(RTRIM(CAST([CuentasDeFondos].[NOMBRE] AS VARCHAR)))												AS nombre -- String (40) NOT NULL UN 			
			-----------------------------------------------------------------------------------------------------			
			/*
				--, [CuentasDeFondos].[RUBRO]																		AS cuentaDeFondoRubro 
				, [vCuentaDeFondoRubro].id																			AS cuentaDeFondoRubro_id				
				, [vCuentaDeFondoRubro].codigo																		AS cuentaDeFondoRubro_codigo
				, [vCuentaDeFondoRubro].nombre																		AS cuentaDeFondoRubro_nombre
				, [vCuentaDeFondoRubro].esCuentaDeFondo																AS cuentaDeFondoRubro_escuentaDeFondo		
			*/
			-----------------------------------------------------------------------------------------------------
				--, [CuentasDeFondos].[GRUPO]																			AS cuentaDeFondoGrupo 
				, [vCuentaDeFondoGrupo].id																			AS cuentaDeFondoGrupo_id			
					, [vCuentaDeFondoGrupo].cuentaDeFondoRubro_id													AS cuentaDeFondoGrupo_cuentaDeFondoRubro_id
					, [vCuentaDeFondoGrupo].cuentaDeFondoRubro_codigo												AS cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo
					, [vCuentaDeFondoGrupo].cuentaDeFondoRubro_nombre												AS cuentaDeFondoGrupo_cuentaDeFondoRubro_nombre
					, [vCuentaDeFondoGrupo].cuentaDeFondoRubro_esCuentaDeFondo										AS cuentaDeFondoGrupo_cuentaDeFondoRubro_esCuentaDeFondo			
				, [vCuentaDeFondoGrupo].codigo																		AS cuentaDeFondoGrupo_codigo		
				, [vCuentaDeFondoGrupo].nombre																		AS cuentaDeFondoGrupo_nombre
			-----------------------------------------------------------------------------------------------------			
				--, [CuentasDeFondos].[TIPO]																			AS cuentaDeFondoTipo
				, [vCuentaDeFondoTipo].id																			AS cuentaDeFondoTipo_id				
				, [vCuentaDeFondoTipo].codigo																		AS cuentaDeFondoTipo_codigo
				, [vCuentaDeFondoTipo].nombre																		AS cuentaDeFondoTipo_nombre
			-----------------------------------------------------------------------------------------------------
				--, [CuentasDeFondos].[MONEDA]																		AS moneda	
				, [vMoneda].id																						AS moneda_id
				, [vMoneda].codigo																					AS moneda_codigo
				, [vMoneda].nombre																					AS moneda_nombre
				, [vMoneda].abreviatura																				AS moneda_abreviatura
				, [vMoneda].cotizacion																				AS moneda_cotizacion
				, [vMoneda].fecha																					AS moneda_fecha
				, [vMoneda].controlDeActualizacion																	AS moneda_controlDeActualizacion		
				------------------------------------------------------------------------------------------------------					
			--		, [vMoneda].monedaAFIP_id																		AS moneda_monedaAFIP_id	
			--		, [vMoneda].monedaAFIP_codigo																	AS moneda_monedaAFIP_codigo	
			--		, [vMoneda].monedaAFIP_nombre																	AS moneda_monedaAFIP_nombre
			-----------------------------------------------------------------------------------------------------
				--, [CuentasDeFondos].[CAJA]																			AS caja
				, [vCaja].id																						AS caja_id
				, [vCaja].codigo																					AS caja_codigo
				, [vCaja].nombre																					AS caja_nombre
			--		, [vCaja].seguridadPuerta_id																	AS caja_seguridadPuerta_id	
			--		, [vCaja].seguridadPuerta_codigo																AS caja_seguridadPuerta_codigo
			--			, [vCaja].seguridadPuerta_seguridadModulo_id												AS caja_seguridadPuerta_seguridadModulo_id
			--			, [vCaja].seguridadPuerta_seguridadModulo_codigo											AS caja_seguridadPuerta_seguridadModulo_codigo	
			--			, [vCaja].seguridadPuerta_seguridadModulo_nombre											AS caja_seguridadPuerta_seguridadModulo_nombre	 
			--			, [vCaja].seguridadPuerta_seguridadModulo_congelado											AS caja_seguridadPuerta_seguridadModulo_congelado	
			--		, [vCaja].seguridadPuerta_igualacionID															AS caja_seguridadPuerta_igualacionID
			--		, [vCaja].seguridadPuerta_nombre																AS caja_seguridadPuerta_nombre
			--		, [vCaja].seguridadPuerta_congelado																AS caja_seguridadPuerta_congelado
			-----------------------------------------------------------------------------------------------------
				--, [CuentasDeFondos].[TIPOBANCO]																	AS tipoBanco 
				, [vCuentaDeFondoTipoBanco].id																		AS cuentaDeFondoTipoBanco_id				
				, [vCuentaDeFondoTipoBanco].codigo																	AS cuentaDeFondoTipoBanco_codigo
				, [vCuentaDeFondoTipoBanco].nombre																	AS cuentaDeFondoTipoBanco_nombre
			-----------------------------------------------------------------------------------------------------
				--, [CuentasDeFondos].[CUENTADIFERIDOS]																AS cuentaDiferidos
				, LTRIM(RTRIM(CAST([cuentaDiferidos].[CUENTA] AS VARCHAR)))											AS cuentaDiferidos_id	-- String NOT NULL PK	
				, CAST([CuentasDeFondos].[CUENTA] AS INTEGER)														AS cuentaDiferidos_codigo	 -- String (11) NOT NULL UN 			
				, LTRIM(RTRIM(CAST([cuentaDiferidos].[NOMBRE] AS VARCHAR)))											AS cuentaDiferidos_nombre -- String (40) NOT NULL UN 	
			-----------------------------------------------------------------------------------------------------
				--, [CuentasDeFondos].[CUENTACAUCION]
				, LTRIM(RTRIM(CAST([cuentaCaucion].[CUENTA] AS VARCHAR)))											AS cuentaCaucion_id	-- String NOT NULL PK	
				, CAST([CuentasDeFondos].[CUENTA] AS INTEGER)														AS cuentaCaucion_codigo	 -- String (11) NOT NULL UN 			
				, LTRIM(RTRIM(CAST([cuentaCaucion].[NOMBRE] AS VARCHAR)))											AS cuentaCaucion_nombre -- String (40) NOT NULL UN 	
			-----------------------------------------------------------------------------------------------------
			, [CuentasDeFondos].[LIMITEDESCUBIERTO]																	AS limiteDescubierto -- BigDecimal (11,2) [ -999999.99 - 9999999.99] 
			-----------------------------------------------------------------------------------------------------
				--, [CuentasDeFondos].[BANCO]																		AS banco
				, [vBanco].id																						AS banco_id
				, [vBanco].codigo																					AS banco_codigo		
				, [vBanco].nombre																					AS banco_nombre
				, [vBanco].cuit																						AS banco_cuit	
				, [vBanco].bloqueado																				AS banco_bloqueado
				, [vBanco].nombreOficial																			AS banco_nombreOficial
			-----------------------------------------------------------------------------------------------------
			 , LTRIM(RTRIM(CAST([CuentasDeFondos].[CUENTABANCARIA] AS VARCHAR)))									AS cuentaBancaria	-- String (22) NOT NULL 	
			 , LTRIM(RTRIM(CAST([CuentasDeFondos].[CBU] AS VARCHAR)))												AS cbu				-- String (22) NOT NULL 							
			, [CuentasDeFondos].[CONCILIACION]																		AS conciliacion		-- Boolean
			, [CuentasDeFondos].[CARTERARECHAZADOS]																	AS rechazados		-- Boolean	
			
			, [CuentasDeFondos].[OBSOLETA]																			AS obsoleta -- Boolean
			, [CuentasDeFondos].[NOIMPRIMECAJA]																		AS noImprimeCaja -- Boolean
			, [CuentasDeFondos].[VENTAS]																			AS moduloVentas -- Boolean
			, [CuentasDeFondos].[FONDOS]																			AS moduloFondos -- Boolean
			, [CuentasDeFondos].[COMPRAS]																			AS moduloCompras -- Boolean			
			-----------------------------------------------------------------------------------------------------
				--, [CuentasDeFondos].[EJERCICIO]
			-----------------------------------------------------------------------------------------------------
				--, [CuentasDeFondos].[CUENTACONTABLE]																AS cuentaContable
				, vCuentaContable.id																				AS cuentaContable_id			
				-----------------------------------------------------------------------------------------------------			
				, vCuentaContable.ejercicioContable_id																AS cuentaContable_ejercicioContable_id			
					, vCuentaContable.ejercicioContable_ejercicio													AS cuentaContable_ejercicioContable_ejercicio		
					, vCuentaContable.ejercicioContable_fechaApertura												AS cuentaContable_ejercicioContable_fechaApertura
					, vCuentaContable.ejercicioContable_fechaCierre													AS cuentaContable_ejercicioContable_fechaCierre
					, vCuentaContable.ejercicioContable_ejercicioCerrado											AS cuentaContable_ejercicioContable_ejercicioCerrado
					, vCuentaContable.ejercicioContable_ejercicioCerradoModulos										AS cuentaContable_ejercicioContable_ejercicioCerradoModulos
					, vCuentaContable.ejercicioContable_comentario													AS cuentaContable_ejercicioContable_comentario
				-----------------------------------------------------------------------------------------------------			
				, vCuentaContable.codigoCuentaPadre																	AS cuentaContable_codigoCuentaPadre
				, vCuentaContable.codigoCuenta																		AS cuentaContable_codigoCuenta
				, vCuentaContable.cuentaContable																	AS cuentaContable_cuentaContable
				, vCuentaContable.nombre																			AS cuentaContable_nombre
				-----------------------------------------------------------------------------------------------------
				, vCuentaContable.imputable																			AS cuentaContable_imputable
				-----------------------------------------------------------------------------------------------------
			/*	, vCuentaContable.ajustaPorInflacion																AS cuentaContable_ajustaPorInflacion			
				-----------------------------------------------------------------------------------------------------			
				, vCuentaContable.cuentaContableEstado_id															AS cuentaContable_cuentaContableEstado_id 			
					, vCuentaContable.cuentaContableEstado_codigo													AS cuentaContable_cuentaContableEstado_codigo
					, vCuentaContable.cuentaContableEstado_nombre													AS cuentaContable_cuentaContableEstado_nombre
				-----------------------------------------------------------------------------------------------------
				, vCuentaContable.cuentaConApropiacion																AS cuentaContable_cuentaConApropiacion
				-----------------------------------------------------------------------------------------------------			
				, vCuentaContable.centroDeCostoContable_id															AS cuentaContable_centroDeCostoContable_id		
					,  vCuentaContable.centroDeCostoContable_numero													AS cuentaContable_centroDeCostoContable_numero
					,  vCuentaContable.centroDeCostoContable_nombre													AS cuentaContable_centroDeCostoContable_nombre
					,  vCuentaContable.centroDeCostoContable_abreviatura											AS cuentaContable_centroDeCostoContable_abreviatura					
				-----------------------------------------------------------------------------------------------------
				, vCuentaContable.cuentaAgrupadora																	AS cuentaContable_cuentaAgrupadora 
				, vCuentaContable.porcentaje																		AS cuentaContable_porcentaje
				-----------------------------------------------------------------------------------------------------			
				, vCuentaContable.puntoDeEquilibrio_id																AS cuentaContable_puntoDeEquilibrio_id
					, vCuentaContable.puntoDeEquilibrio_puntoDeEquilibrio											AS cuentaContable_puntoDeEquilibrio_puntoDeEquilibrio
					, vCuentaContable.puntoDeEquilibrio_nombre														AS cuentaContable_puntoDeEquilibrio_nombre
					-----------------------------------------------------------------------------------------------------								
					, vCuentaContable.puntoDeEquilibrio_puntoDeEquilibrioTipo_id									AS cuentaContable_puntoDeEquilibrio_puntoDeEquilibrioTipo_id
						, vCuentaContable.puntoDeEquilibrio_puntoDeEquilibrioTipo_codigo							AS cuentaContable_puntoDeEquilibrio_puntoDeEquilibrioTipo_codigo
						, vCuentaContable.puntoDeEquilibrio_puntoDeEquilibrioTipo_nombre							AS cuentaContable_puntoDeEquilibrio_puntoDeEquilibrioTipo_nombre 
			*/	
			-----------------------------------------------------------------------------------------------------
				--, [CuentasDeFondos].[DOORNO]																		AS seguridadPuerta				
				, [vSeguridadPuerta].id																				AS SeguridadPuerta_id
				, [vSeguridadPuerta].codigo																			AS SeguridadPuerta_codigo
					, [vSeguridadPuerta].seguridadModulo_id															AS SeguridadPuerta_seguridadModulo_id
					, [vSeguridadPuerta].seguridadModulo_codigo														AS SeguridadPuerta_seguridadModulo_codigo 
					, [vSeguridadPuerta].seguridadModulo_nombre														AS SeguridadPuerta_seguridadModulo_nombre 
					, [vSeguridadPuerta].seguridadModulo_congelado													AS SeguridadPuerta_seguridadModulo_congelado
				, [vSeguridadPuerta].igualacionID																	AS SeguridadPuerta_igualacionID
				, [vSeguridadPuerta].nombre																			AS SeguridadPuerta_nombre
				, [vSeguridadPuerta].congelado																		AS SeguridadPuerta_congelado
			-----------------------------------------------------------------------------------------------------

				--, [CuentasDeFondos].[DOORNOCONSULTA]																AS puertaConsulta			
				, [puertaConsulta].id																				AS puertaConsulta_id
				, [puertaConsulta].codigo																			AS puertaConsulta_codigo
					, [puertaConsulta].seguridadModulo_id															AS puertaConsulta_seguridadModulo_id
					, [puertaConsulta].seguridadModulo_codigo														AS puertaConsulta_seguridadModulo_codigo 
					, [puertaConsulta].seguridadModulo_nombre														AS puertaConsulta_seguridadModulo_nombre 
					, [puertaConsulta].seguridadModulo_congelado													AS puertaConsulta_seguridadModulo_congelado
				, [puertaConsulta].igualacionID																		AS puertaConsulta_igualacionID
				, [puertaConsulta].nombre																			AS puertaConsulta_nombre
				, [puertaConsulta].congelado																		AS puertaConsulta_congelado
			-----------------------------------------------------------------------------------------------------			
			, [CuentasDeFondos].[LIMITEOPERACIONINDIVIDUAL]															AS limiteOperacionIndividual -- BigDecimal (11,2) [ -999999.99 - 9999999.99] 
			-----------------------------------------------------------------------------------------------------			
				--, [CuentasDeFondos].[DOORNOLIMITE]
				, [puertaLimite].id																				AS puertaLimite_id
				, [puertaLimite].codigo																			AS puertaLimite_codigo
					, [puertaLimite].seguridadModulo_id															AS puertaLimite_seguridadModulo_id
					, [puertaLimite].seguridadModulo_codigo														AS puertaLimite_seguridadModulo_codigo 
					, [puertaLimite].seguridadModulo_nombre														AS puertaLimite_seguridadModulo_nombre 
					, [puertaLimite].seguridadModulo_congelado													AS puertaLimite_seguridadModulo_congelado
				, [puertaLimite].igualacionID																	AS puertaLimite_igualacionID
				, [puertaLimite].nombre																			AS puertaLimite_nombre
				, [puertaLimite].congelado																		AS puertaLimite_congelado
			-----------------------------------------------------------------------------------------------------				

			--, [CuentasDeFondos].[UNIDADMONETARIA] -- String (4) FK ?
			--, [CuentasDeFondos].[COTIZACION] -- BigDecimal (9,3)												
			
			--, [CuentasDeFondos].[DESTINOIMPRESIONTRANSF]
			--, [CuentasDeFondos].[FORMATOTRANSF]
			--, [CuentasDeFondos].[COPIASTRANSF]									
			
			--, [CuentasDeFondos].[FECHACIERRESQL]
			--, [CuentasDeFondos].[SALDOCIERRE]
			--, [CuentasDeFondos].[FECHADEPURACIONSQL]
			--, [CuentasDeFondos].[SALDODEPURADO]
			--, [CuentasDeFondos].[SALDOCONCILIADO]
			--, [CuentasDeFondos].[SALDOACTUAL]
			--, [CuentasDeFondos].[NOMBRECOMPROBANTE]			
			
			--, [CuentasDeFondos].[CUENTACONCILIACIONPENDIENTE]
			--, [CuentasDeFondos].[CANTIDADCONCILIACIONPENDIENTE]
			--, [CuentasDeFondos].[ARRASTRASALDOSCONCILIACION]			
			

	FROM	[dbo].[CuentasDeFondos]
	
	LEFT JOIN	[dbo].[vCuentaDeFondoRubro] 
		ON [dbo].[vCuentaDeFondoRubro].[codigo] = CAST([dbo].[CuentasDeFondos].[RUBRO] AS INTEGER)
	
		LEFT JOIN	[dbo].[vCuentaDeFondoGrupo] 
			ON [dbo].[vCuentaDeFondoGrupo].[cuentaDeFondoRubro_codigo] = [dbo].[vCuentaDeFondoRubro].[codigo]		
			AND [dbo].[vCuentaDeFondoGrupo].[codigo] = CAST([dbo].[CuentasDeFondos].[GRUPO] AS INTEGER)	

	LEFT JOIN	[dbo].[vCuentaDeFondoTipo] 
		ON [dbo].[vCuentaDeFondoTipo].[codigo] = CAST([dbo].[CuentasDeFondos].[TIPO] AS INTEGER)

	LEFT JOIN	[dbo].[vMoneda] 
		ON [dbo].[vMoneda].[codigo] = CAST([dbo].[CuentasDeFondos].[MONEDA] AS INTEGER)

	LEFT JOIN	[dbo].[vCaja] 
		ON [dbo].[vCaja].[codigo] = CAST([dbo].[CuentasDeFondos].[CAJA] AS INTEGER)

	LEFT JOIN	[dbo].[vCuentaDeFondoTipoBanco] 
		ON [dbo].[vCuentaDeFondoTipoBanco].[codigo] = CAST([dbo].[CuentasDeFondos].[TIPOBANCO] AS INTEGER) 

	

	LEFT JOIN	[dbo].[vCuentaContable] 
		ON [dbo].[vCuentaContable].[ejercicioContable_ejercicio] = CAST([dbo].[CuentasDeFondos].[EJERCICIO] AS INTEGER)
		AND [dbo].[vCuentaContable].[cuentaContable] = LTRIM(RTRIM(CAST([CuentasDeFondos].[CUENTACONTABLE] AS VARCHAR))) 


	LEFT JOIN	[dbo].[CuentasDeFondos] AS [cuentaDiferidos]
		ON  CAST([cuentaDiferidos].[CUENTA] AS INTEGER) = CAST([CuentasDeFondos].[CUENTADIFERIDOS] AS INTEGER)


	LEFT JOIN	[dbo].[CuentasDeFondos] AS [cuentaCaucion]
		ON  CAST([cuentaCaucion].[CUENTA] AS INTEGER) = CAST([CuentasDeFondos].[CUENTACAUCION] AS INTEGER)

	LEFT JOIN	[dbo].[vBanco] 
			ON		[vBanco].[codigo] = CAST([dbo].[CuentasDeFondos].[BANCO] AS INTEGER)
	
	LEFT JOIN	[dbo].[vSeguridadPuerta] 
			ON		[vSeguridadPuerta].[codigo] = CAST([dbo].[CuentasDeFondos].[DOORNO] AS INTEGER)

	LEFT JOIN	[dbo].[vSeguridadPuerta] AS [puertaConsulta]
			ON		[puertaConsulta].[codigo] = CAST([dbo].[CuentasDeFondos].[DOORNOCONSULTA] AS INTEGER)
	
	LEFT JOIN	[dbo].[vSeguridadPuerta] AS [puertaLimite]
			ON		[puertaLimite].[codigo] = CAST([dbo].[CuentasDeFondos].[DOORNOLIMITE] AS INTEGER);


		--where [CuentasDeFondos].[CUENTA] = 24108

	-- SELECT COUNT(*) FROM [dbo].[CuentasDeFondos]
	-- SELECT * FROM dbo.vCuentaDeFondo;	
	-- SELECT COUNT(*) FROM dbo.vCuentaDeFondo;	
	-- SELECT COUNT(*) FROM dbo.vCuentaDeFondo WHERE cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo = 1 AND cuentaDeFondoGrupo_codigo = 1 AND obsoleta = 0 AND cuentaDeFondoTipo_codigo = 1;	
	-- SELECT COUNT(*) FROM dbo.vCuentaDeFondo WHERE cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo = 1 AND cuentaDeFondoGrupo_codigo = 1 AND obsoleta = 0 AND cuentaDeFondoTipo_codigo = 2;	
	-- SELECT COUNT(*) FROM dbo.vCuentaDeFondo WHERE cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo = 1 AND cuentaDeFondoGrupo_codigo = 1 AND obsoleta = 0 AND cuentaDeFondoTipo_codigo = 3;	
	-- SELECT COUNT(*) FROM dbo.vCuentaDeFondo WHERE cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo = 1 AND cuentaDeFondoGrupo_codigo = 1 AND obsoleta = 0 AND cuentaDeFondoTipo_codigo = 4;	
	-- SELECT COUNT(*) FROM dbo.vCuentaDeFondo WHERE cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo = 1 AND cuentaDeFondoGrupo_codigo = 1 AND obsoleta = 0 AND cuentaDeFondoTipo_codigo = 5;	
	-- SELECT * FROM dbo.vCuentaDeFondo ORDER BY codigo, nombre;
	-- SELECT * FROM dbo.vCuentaDeFondo WHERE codigo = '11202' ORDER BY codigo, nombre;
	-- SELECT TOP 20 * FROM dbo.vCuentaDeFondo WHERE cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo = 1 AND cuentaDeFondoGrupo_codigo = 1 AND obsoleta = 0 AND cuentaDeFondoTipo_codigo = 1 ;	

	-- SELECT * FROM dbo.vCuentaDeFondo WHERE (obsoleta = 0 OR obsoleta IS NULL) AND cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo = 1 AND cuentaDeFondoGrupo_codigo = 1 AND cuentaDeFondoTipo_codigo = 1 ORDER BY codigo, nombre;;

	-- SELECT cuentaDiferidos_id, cuentaCaucion_id, * FROM dbo.vCuentaDeFondo WHERE codigo = 666
	
-------------------------------------------------------------

-- DROP VIEW [dbo].[vCuentaDeFondoA]

CREATE VIEW [dbo].[vCuentaDeFondoA] AS  

	SELECT	'com.massoftware.model.CuentaDeFondoA' AS ClassCuentaDeFondoA
		
			-----------------------------------------------------------------------------------------------------
			, LTRIM(RTRIM(CAST([CuentasDeFondos].[CUENTA] AS VARCHAR)))												AS id	-- String NOT NULL PK			
			-----------------------------------------------------------------------------------------------------			
			, CAST(LTRIM(RTRIM(CAST([CuentasDeFondos].[CUENTA] AS VARCHAR))) AS INTEGER)							AS codigo	 -- Integer NOT NULL UN  [1 - Integer.MAX_VALUE]				
			, LTRIM(RTRIM(CAST([CuentasDeFondos].[NOMBRE] AS VARCHAR)))												AS nombre -- String (40) NOT NULL UN 							
			-----------------------------------------------------------------------------------------------------
			, [CuentasDeFondos].[OBSOLETA]																			AS obsoleta -- Boolean
			-----------------------------------------------------------------------------------------------------
				--, [CuentasDeFondos].[GRUPO]																			AS cuentaDeFondoGrupo 
				, [vCuentaDeFondoGrupo].id																			AS cuentaDeFondoGrupo_id			
					, [vCuentaDeFondoGrupo].cuentaDeFondoRubro_id													AS cuentaDeFondoGrupo_cuentaDeFondoRubro_id
					, [vCuentaDeFondoGrupo].cuentaDeFondoRubro_codigo												AS cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo
					, [vCuentaDeFondoGrupo].cuentaDeFondoRubro_nombre												AS cuentaDeFondoGrupo_cuentaDeFondoRubro_nombre
					, [vCuentaDeFondoGrupo].cuentaDeFondoRubro_esCuentaDeFondo										AS cuentaDeFondoGrupo_cuentaDeFondoRubro_esCuentaDeFondo			
				, [vCuentaDeFondoGrupo].codigo																		AS cuentaDeFondoGrupo_codigo		
				, [vCuentaDeFondoGrupo].nombre																		AS cuentaDeFondoGrupo_nombre
			-----------------------------------------------------------------------------------------------------			
				--, [CuentasDeFondos].[TIPO]																			AS cuentaDeFondoTipo
				, [vCuentaDeFondoTipo].id																			AS cuentaDeFondoTipo_id				
				, [vCuentaDeFondoTipo].codigo																		AS cuentaDeFondoTipo_codigo
				, [vCuentaDeFondoTipo].nombre																		AS cuentaDeFondoTipo_nombre
			-----------------------------------------------------------------------------------------------------			

	FROM	[dbo].[CuentasDeFondos]

	LEFT JOIN	[dbo].[vCuentaDeFondoRubro] 
		ON [dbo].[vCuentaDeFondoRubro].[codigo] = CAST([dbo].[CuentasDeFondos].[RUBRO] AS INTEGER)
	
		LEFT JOIN	[dbo].[vCuentaDeFondoGrupo] 
			ON [dbo].[vCuentaDeFondoGrupo].[cuentaDeFondoRubro_codigo] = [dbo].[vCuentaDeFondoRubro].[codigo]		
			AND [dbo].[vCuentaDeFondoGrupo].[codigo] = CAST([dbo].[CuentasDeFondos].[GRUPO] AS INTEGER)	

	LEFT JOIN	[dbo].[vCuentaDeFondoTipo] 
		ON [dbo].[vCuentaDeFondoTipo].[codigo] = CAST([dbo].[CuentasDeFondos].[TIPO] AS INTEGER);

	-- SELECT COUNT(*) FROM [dbo].[CuentasDeFondos]
	-- SELECT COUNT(*) FROM dbo.vCuentaDeFondoA;		
	-- SELECT * FROM dbo.vCuentaDeFondoA;		
	-- SELECT * FROM dbo.vCuentaDeFondoA ORDER BY codigo, nombre;
	


-------------------------------------------------------------

-- DROP VIEW [dbo].[vJurisdiccionConvenioMultilateral]

CREATE VIEW [dbo].[vJurisdiccionConvenioMultilateral] AS  


	SELECT	'com.massoftware.model.JurisdiccionConvenioMultilateral'									AS ClassJurisdiccionConvenioMultilateral
			-----------------------------------------------------------------------------------------------------			
			, CAST([ConvenioMultilateralJurisdicciones].[JURISDICCION] AS VARCHAR)						AS id	-- String NOT NULL PK				
			-----------------------------------------------------------------------------------------------------			
			, CAST([ConvenioMultilateralJurisdicciones].[JURISDICCION]  AS INTEGER)						AS codigo -- Integer NOT NULL UN [ 1 - Short.MAX_VALUE]
			, LTRIM(RTRIM(CAST([ConvenioMultilateralJurisdicciones].[NOMBRE] AS VARCHAR)))				AS nombre -- String (30) NOT NULL UN 
			-----------------------------------------------------------------------------------------------------				
				, [vCuentaDeFondo].id																	AS cuentaDeFondo_id
				, [vCuentaDeFondo].codigo																AS cuentaDeFondo_codigo
				, [vCuentaDeFondo].nombre																AS cuentaDeFondo_nombre
				, [vCuentaDeFondo].cuentaDeFondoGrupo_id												AS cuentaDeFondo_cuentaDeFondoGrupo_id
					, [vCuentaDeFondo].cuentaDeFondoGrupo_cuentaDeFondoRubro_id							AS cuentaDeFondo_cuentaDeFondoGrupo_cuentaDeFondoRubro_id	
					, [vCuentaDeFondo].cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo						AS cuentaDeFondo_cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo
					, [vCuentaDeFondo].cuentaDeFondoGrupo_cuentaDeFondoRubro_nombre						AS cuentaDeFondo_cuentaDeFondoGrupo_cuentaDeFondoRubro_nombre
					, [vCuentaDeFondo].cuentaDeFondoGrupo_cuentaDeFondoRubro_esCuentaDeFondo			AS cuentaDeFondo_cuentaDeFondoGrupo_cuentaDeFondoRubro_esCuentaDeFondo	
				, [vCuentaDeFondo].cuentaDeFondoGrupo_codigo											AS cuentaDeFondo_cuentaDeFondoGrupo_codigo
				, [vCuentaDeFondo].cuentaDeFondoGrupo_nombre											AS cuentaDeFondo_cuentaDeFondoGrupo_nombre
			-----------------------------------------------------------------------------------------------------				

			
  
	FROM	[dbo].[ConvenioMultilateralJurisdicciones]
	LEFT JOIN	[dbo].[vCuentaDeFondo] 
		ON [dbo].[vCuentaDeFondo].[codigo] = CAST([dbo].[ConvenioMultilateralJurisdicciones].[CUENTAFONDO] AS VARCHAR);	

	-- SELECT * FROM dbo.vJurisdiccionConvenioMultilateral;	
	-- SELECT * FROM dbo.vJurisdiccionConvenioMultilateral ORDER BY codigo, nombre;

-------------------------------------------------------------

-- DROP VIEW [dbo].[vChequera]

CREATE VIEW [dbo].[vChequera] AS  

	SELECT	'com.massoftware.model.Chequera'												AS ClassChequera
			-----------------------------------------------------------------------------------------------------			
			, CONCAT (  LTRIM(RTRIM(CAST([Chequeras].[CUENTA] AS VARCHAR))), '-',  LTRIM(RTRIM(CAST([Chequeras].[CHEQUERA] AS VARCHAR))) )	AS id	-- String NOT NULL PK	
			-----------------------------------------------------------------------------------------------------
				--, [Chequeras].[CUENTA]																AS cuentaDeFondo
				, [vCuentaDeFondo].id																	AS cuentaDeFondo_id
				, [vCuentaDeFondo].codigo																AS cuentaDeFondo_codigo
				, [vCuentaDeFondo].nombre																AS cuentaDeFondo_nombre
				, [vCuentaDeFondo].cuentaDeFondoGrupo_id												AS cuentaDeFondo_cuentaDeFondoGrupo_id
					, [vCuentaDeFondo].cuentaDeFondoGrupo_cuentaDeFondoRubro_id							AS cuentaDeFondo_cuentaDeFondoGrupo_cuentaDeFondoRubro_id	
					, [vCuentaDeFondo].cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo						AS cuentaDeFondo_cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo
					, [vCuentaDeFondo].cuentaDeFondoGrupo_cuentaDeFondoRubro_nombre						AS cuentaDeFondo_cuentaDeFondoGrupo_cuentaDeFondoRubro_nombre
					, [vCuentaDeFondo].cuentaDeFondoGrupo_cuentaDeFondoRubro_esCuentaDeFondo			AS cuentaDeFondo_cuentaDeFondoGrupo_cuentaDeFondoRubro_esCuentaDeFondo	
				, [vCuentaDeFondo].cuentaDeFondoGrupo_codigo											AS cuentaDeFondo_cuentaDeFondoGrupo_codigo
				, [vCuentaDeFondo].cuentaDeFondoGrupo_nombre											AS cuentaDeFondo_cuentaDeFondoGrupo_nombre
			-----------------------------------------------------------------------------------------------------
			, CAST([Chequeras].[CHEQUERA]  AS INTEGER)										AS codigo				-- Integer NOT NULL UN [ 1 - Short.MAX_VALUE]
			, CAST([Chequeras].[PRIMERNRO]  AS INTEGER)										AS primerNumero			-- Integer NOT NULL [ 1 - Integer.MAX_VALUE]
			, CAST([Chequeras].[ULTIMONRO]  AS INTEGER)										AS ultimoNumero			-- Integer NOT NULL [ 1 - Integer.MAX_VALUE]
			, CAST([Chequeras].[PROXIMONRO]  AS INTEGER)									AS proximoNumero		-- Integer NOT NULL [ 1 - Integer.MAX_VALUE]
			, [Chequeras].[BLOQUEADO]														AS bloqueado			-- Boolean
			, [Chequeras].[IMPRESIONDIFERIDA]												AS impresionDiferida	-- Boolean
			, LTRIM(RTRIM(CAST([Chequeras].[DESTINOIMPRESION] AS VARCHAR)))					AS destinoImpresion		-- String (40)
			, LTRIM(RTRIM(CAST([Chequeras].[FORMATO] AS VARCHAR)))							AS formato				-- String (30)
	
  
	FROM	[dbo].[Chequeras]
	LEFT JOIN	[dbo].[vCuentaDeFondo] 
		ON  [vCuentaDeFondo].[codigo] = CAST([Chequeras].[CUENTA] AS INTEGER);

	-- SELECT * FROM dbo.vChequera;	
	-- SELECT * FROM dbo.vChequera ORDER BY cuentaDeFondo_codigo, codigo;

-------------------------------------------------------------

-- DROP VIEW [dbo].[vTicket]

CREATE VIEW [dbo].[vTicket] AS  


	SELECT	'com.massoftware.model.Ticket'										AS ClassTicket
			-----------------------------------------------------------------------------------------------------			
			, CAST([Tickets].[TICKET] AS VARCHAR)								AS id					-- String NOT NULL PK					
			-----------------------------------------------------------------------------------------------------
			, CAST([Tickets].[TICKET]  AS INTEGER)								AS codigo				-- Integer NOT NULL UN [ 1 - Short.MAX_VALUE]
			, LTRIM(RTRIM(CAST([Tickets].[DESCRIPCION] AS VARCHAR)))			AS nombre				-- String (40) NOT NULL UN 
			, CAST([Tickets].[FECHAACTUALIZACIONSQL] AS DATETIME)				AS fecha				-- Timestamp NOT NULL 
			, CAST([Tickets].[CANTIDADPORLOTES]  AS INTEGER)					AS cantidadPorLotes		-- Integer NOT NULL [ 0 - Short.MAX_VALUE]
			, CAST([Tickets].[CONTROLDENUNCIADO]  AS INTEGER)					AS controlNumeracion	-- Integer NOT NULL [ 1 - 2]
			, [Tickets].[VALORMAXIMO]											AS valorMaximo			-- BigDecimal (7,2) [ 0 - 99999.99] 
  
	FROM	[dbo].[Tickets];


	-- SELECT * FROM dbo.vTicket;	
	-- SELECT * FROM dbo.vTicket ORDER BY codigo, nombre;

-------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-------------------------------------------------------------

-- DROP VIEW [dbo].[vModeloCbteFondo]

CREATE VIEW [dbo].[vModeloCbteFondo] AS  


	SELECT	'com.massoftware.model.ModeloCbteFondo'										AS ClassModeloCbteFondo
			-----------------------------------------------------------------------------------------------------
			, CAST([FondosMod].[NUMERO] AS VARCHAR)										AS id			
			-----------------------------------------------------------------------------------------------------
			, CAST([FondosMod].[NUMERO] AS INTEGER)										AS codigo -- Integer NOT NULL UN [1 - Short.MAX_VALUE]
			, LTRIM(RTRIM(CAST([FondosMod].[NOMBRE] AS VARCHAR)))						AS nombre -- String (30) NOT NULL UN 

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
			-- , LTRIM(RTRIM(CAST([CUENTA]  AS VARCHAR))) AS cuenta -- String (11) NOT NULL -- CuentaFondo Id !!!!!!!! Falta hacer la vista !!!!!!!!!!!!!!!!!!
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
	-- SELECT * FROM dbo.vModeloCbteFondoItem WHERE modeloCbteFondo_codigo = 3 ORDER BY modeloCbteFondo_codigo, ajustaSaldo;
	

		



