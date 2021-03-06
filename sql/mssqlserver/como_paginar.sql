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
  
SELECT * 
FROM [VetaroRep].[dbo].[EjerciciosContables]
ORDER BY [EJERCICIO]
OFFSET 0 ROWS
FETCH NEXT 2 ROWS ONLY

SELECT * 
FROM [VetaroRep].[dbo].[EjerciciosContables]
ORDER BY [EJERCICIO]
OFFSET 2 ROWS FETCH NEXT 2 ROWS ONLY

SELECT * 
FROM [VetaroRep].[dbo].[EjerciciosContables]
ORDER BY [EJERCICIO]
OFFSET 6 ROWS
FETCH NEXT 2 ROWS ONLY


SELECT * FROM dbo.vAsiento WHERE ejercicioContable_ejercicio = 2016 ORDER BY fecha DESC, ejercicioContable_ejercicio, numero OFFSET 0 ROWS FETCH NEXT 15 ROWS ONLY ;

SELECT * FROM dbo.vAsiento ORDER BY ejercicioContable_ejercicio OFFSET 0 ROWS FETCH NEXT 15 ROWS ONLY ;
SELECT * FROM dbo.vAsiento ORDER BY ejercicioContable_ejercicio OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY ;

 SELECT * FROM dbo.vAsiento ORDER BY fecha DESC OFFSET 0 ROWS FETCH NEXT 15 ROWS ONLY ;

 SELECT * FROM dbo.vAsiento WHERE detalle like '%Ba%' ORDER BY detalle OFFSET 15 ROWS FETCH NEXT 15 ROWS ONLY ;

 SELECT * FROM dbo.vAsiento WHERE LOWER(dbo.Translate(fecha , null, null)) like LOWER(dbo.Translate('%7%', null,null)) ORDER BY fecha DESC OFFSET 15 ROWS FETCH NEXT 15 ROWS ONLY ;

 SELECT * FROM dbo.vAsiento WHERE ejercicioContable_ejercicio = 2016 AND LOWER(dbo.Translate(fecha , null, null)) like LOWER(dbo.Translate('%7%', null,null)) ORDER BY fecha DESC OFFSET 15 ROWS FETCH NEXT 15 ROWS ONLY ;


SELECT * FROM dbo.vAsiento WHERE  ejercicioContable_ejercicio = 2016 ORDER BY fecha DESC OFFSET 0 ROWS FETCH NEXT 15 ROWS ONLY ;

SELECT * FROM dbo.[Contabilidad] WHERE  [EJERCICIO] = 2016 ORDER BY [FECHASQL] DESC OFFSET 0 ROWS FETCH NEXT 15 ROWS ONLY ;

 