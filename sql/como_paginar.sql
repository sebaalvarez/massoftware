/****** Script para el comando SelectTopNRows de SSMS  ******/
SELECT TOP 1000 [EJERCICIO]
      ,[COMENTARIO]
      ,[FECHAAPERTURASQL]
      ,[FECHACIERRESQL]
      ,[EJERCICIOCERRADO]
      ,[EJERCICIOCERRADOMODULOS]
  FROM [VetaroRep].[dbo].[EjerciciosContables]


  
SELECT * 
FROM [VetaroRep].[dbo].[EjerciciosContables]
ORDER BY [EJERCICIO]
OFFSET 0 ROWS
FETCH NEXT 2 ROWS ONLY

SELECT * 
FROM [VetaroRep].[dbo].[EjerciciosContables]
ORDER BY [EJERCICIO]
OFFSET 2 ROWS
FETCH NEXT 2 ROWS ONLY

SELECT * 
FROM [VetaroRep].[dbo].[EjerciciosContables]
ORDER BY [EJERCICIO]
OFFSET 6 ROWS
FETCH NEXT 2 ROWS ONLY