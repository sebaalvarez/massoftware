-- CREATE EXTENSION "uuid-ossp";
-- SELECT uuid_generate_v4();

DROP SCHEMA IF EXISTS massoftware CASCADE;

CREATE SCHEMA massoftware AUTHORIZATION massoftwareroot;	


-- ==========================================================================================================================
-- ==========================================================================================================================
-- ==========================================================================================================================
-- =======================																				=====================
-- =======================				FUNCIONES UTILES												=====================	
-- =======================																				=====================
-- ==========================================================================================================================
-- ==========================================================================================================================
-- ==========================================================================================================================



DROP FUNCTION IF EXISTS massoftware.white_is_null (att_val VARCHAR) CASCADE;

CREATE OR REPLACE FUNCTION massoftware.white_is_null(att_val VARCHAR) RETURNS VARCHAR AS $$
BEGIN
	IF CHAR_LENGTH(TRIM(att_val)) = 0 THEN
	
		RETURN null::VARCHAR;
	END IF;

	RETURN att_val::VARCHAR;
		
END;
$$  LANGUAGE plpgsql;


-- ==========================================================================================================================
-- ==========================================================================================================================
-- ==========================================================================================================================
-- =======================																				=====================
-- =======================				TABLAS y TRIGGERS												=====================	
-- =======================																				=====================
-- ==========================================================================================================================
-- ==========================================================================================================================
-- ==========================================================================================================================


-- Table: massoftware.EjercicioContable

DROP TABLE IF EXISTS massoftware.EjercicioContable CASCADE;

CREATE TABLE massoftware.EjercicioContable
(
    -- id VARCHAR NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),  
    id VARCHAR PRIMARY KEY DEFAULT uuid_generate_v4(),  
    ejercicio INTEGER  NOT NULL UNIQUE,
    fechaApertura DATE NOT NULL,
    fechaCierre DATE NOT NULL,
    ejercicioCerrado BOOLEAN NOT NULL DEFAULT false,
    ejercicioCerradoModulos BOOLEAN NOT NULL DEFAULT false,
    comentario VARCHAR                    
);

-- SELECT * FROM massoftware.EjercicioContable;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS massoftware.ftgFormatEjercicioContable() CASCADE;

CREATE OR REPLACE FUNCTION massoftware.ftgFormatEjercicioContable() RETURNS TRIGGER AS $formatEjercicioContable$
DECLARE
BEGIN
   
	-- NEW.comentario := massoftware.white_is_null(REPLACE(TRIM(NEW.comentario), '"', ''));	
    NEW.comentario := massoftware.white_is_null(TRIM(NEW.comentario));

	RETURN NEW;
END;
$formatEjercicioContable$ LANGUAGE plpgsql;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP TRIGGER IF EXISTS tgFormatEjercicioContable ON massoftware.EjercicioContable CASCADE;

CREATE TRIGGER tgFormatEjercicioContable BEFORE INSERT OR UPDATE 
    ON massoftware.EjercicioContable FOR EACH ROW 
    EXECUTE PROCEDURE massoftware.ftgFormatEjercicioContable();
    

-- ==========================================================================================================================

-- Table: massoftware.Usuario

DROP TABLE IF EXISTS massoftware.Usuario CASCADE;

CREATE TABLE massoftware.Usuario
(
    -- id VARCHAR NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),  
    id VARCHAR PRIMARY KEY DEFAULT uuid_generate_v4(),  
    ejercicioContable VARCHAR REFERENCES massoftware.EjercicioContable (id),	
    numero INTEGER NOT NULL UNIQUE,
    nombre VARCHAR NOT NULL
);

CREATE UNIQUE INDEX u_Usuario_nombre ON massoftware.Usuario (LOWER(TRIM(nombre)));

-- SELECT * FROM massoftware.Usuario;    

-- ---------------------------------------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS massoftware.ftgFormatUsuario() CASCADE;

CREATE OR REPLACE FUNCTION massoftware.ftgFormatUsuario() RETURNS TRIGGER AS $formatUsuario$
DECLARE
BEGIN
   
	-- NEW.nombre := massoftware.white_is_null(REPLACE(TRIM(NEW.nombre), '"', ''));	
    NEW.nombre := massoftware.white_is_null(TRIM(NEW.nombre));	

	RETURN NEW;
END;
$formatUsuario$ LANGUAGE plpgsql;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP TRIGGER IF EXISTS tgFormatUsuario ON massoftware.Usuario CASCADE;

CREATE TRIGGER tgFormatUsuario BEFORE INSERT OR UPDATE 
    ON massoftware.Usuario FOR EACH ROW 
    EXECUTE PROCEDURE massoftware.ftgFormatUsuario();


-- ==========================================================================================================================

-- Table: massoftware.CentroDeCostoContable

DROP TABLE IF EXISTS massoftware.CentroDeCostoContable CASCADE;

CREATE TABLE massoftware.CentroDeCostoContable
(
    -- id VARCHAR NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),  
    id VARCHAR PRIMARY KEY DEFAULT uuid_generate_v4(),  
    ejercicioContable VARCHAR NOT NULL REFERENCES massoftware.EjercicioContable (id),	
    numero INTEGER NOT NULL,
    nombre VARCHAR,
    abreviatura VARCHAR
);

CREATE UNIQUE INDEX u_CentroDeCostoContable_ejercicioContable_centroDeCostoContable ON massoftware.CentroDeCostoContable (ejercicioContable, numero);

-- SELECT * FROM massoftware.CentroDeCostoContable;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS massoftware.ftgFormatCentroDeCostoContable() CASCADE;

CREATE OR REPLACE FUNCTION massoftware.ftgFormatCentroDeCostoContable() RETURNS TRIGGER AS $formatCentroDeCostoContable$
DECLARE
BEGIN
   
	-- NEW.nombre := massoftware.white_is_null(REPLACE(TRIM(NEW.nombre), '"', ''));	
    NEW.nombre := massoftware.white_is_null(TRIM(NEW.nombre));	
    -- NEW.abreviatura := massoftware.white_is_null(REPLACE(TRIM(NEW.abreviatura), '"', ''));	
    NEW.abreviatura := massoftware.white_is_null(TRIM(NEW.abreviatura));	

	RETURN NEW;
END;
$formatCentroDeCostoContable$ LANGUAGE plpgsql;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP TRIGGER IF EXISTS tgFormatCentroDeCostoContable ON massoftware.CentroDeCostoContable CASCADE;

CREATE TRIGGER tgFormatCentroDeCostoContable BEFORE INSERT OR UPDATE 
    ON massoftware.CentroDeCostoContable FOR EACH ROW 
    EXECUTE PROCEDURE massoftware.ftgFormatCentroDeCostoContable();

-- ==========================================================================================================================

-- Table: massoftware.PuntoDeEquilibrioTipo

DROP TABLE IF EXISTS massoftware.PuntoDeEquilibrioTipo CASCADE;

CREATE TABLE massoftware.PuntoDeEquilibrioTipo
(
    -- id VARCHAR NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),          
    id VARCHAR PRIMARY KEY DEFAULT uuid_generate_v4(),          
    codigo INTEGER NOT NULL,
    nombre VARCHAR NOT NULL
);	

CREATE UNIQUE INDEX u_PuntoDeEquilibrioTipo_codigo ON massoftware.PuntoDeEquilibrioTipo (codigo);
CREATE UNIQUE INDEX u_PuntoDeEquilibrioTipo_nombre ON massoftware.PuntoDeEquilibrioTipo (LOWER(TRIM(nombre)));

-- SELECT * FROM massoftware.PuntoDeEquilibrioTipo;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS massoftware.ftgFormatPuntoDeEquilibrioTipo() CASCADE;

CREATE OR REPLACE FUNCTION massoftware.ftgFormatPuntoDeEquilibrioTipo() RETURNS TRIGGER AS $formatPuntoDeEquilibrioTipo$
DECLARE
BEGIN
   
	NEW.nombre := massoftware.white_is_null(TRIM(NEW.nombre));	    

	RETURN NEW;
END;
$formatPuntoDeEquilibrioTipo$ LANGUAGE plpgsql;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP TRIGGER IF EXISTS tgFormatPuntoDeEquilibrioTipo ON massoftware.PuntoDeEquilibrioTipo CASCADE;

CREATE TRIGGER tgFormatPuntoDeEquilibrioTipo BEFORE INSERT OR UPDATE 
    ON massoftware.PuntoDeEquilibrioTipo FOR EACH ROW 
    EXECUTE PROCEDURE massoftware.ftgFormatPuntoDeEquilibrioTipo();

-- ---------------------------------------------------------------------------------------------------------------------------
/*    
INSERT INTO massoftware.puntodeequilibriotipo(id, tipo, nombre)	VALUES ('1', 1, 'Individual');
INSERT INTO massoftware.puntodeequilibriotipo(id, tipo, nombre)	VALUES ('2', 2, 'Costo de ventas');
INSERT INTO massoftware.puntodeequilibriotipo(id, tipo, nombre)	VALUES ('3', 3, 'Utilidad bruta');
INSERT INTO massoftware.puntodeequilibriotipo(id, tipo, nombre)	VALUES ('4', 4, 'Resultados por sección');
INSERT INTO massoftware.puntodeequilibriotipo(id, tipo, nombre)	VALUES ('5', 5, 'Resultados operativos');    
*/
-- SELECT * FROM massoftware.PuntoDeEquilibrioTipo;

-- ==========================================================================================================================
	
-- Table: massoftware.PuntoDeEquilibrio

DROP TABLE IF EXISTS massoftware.PuntoDeEquilibrio CASCADE;

CREATE TABLE massoftware.PuntoDeEquilibrio
(
    -- id VARCHAR NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),      
    id VARCHAR PRIMARY KEY DEFAULT uuid_generate_v4(),      
    ejercicioContable VARCHAR NOT NULL REFERENCES massoftware.EjercicioContable (id),	
    puntoDeEquilibrio INTEGER NOT NULL, -- numero
    nombre VARCHAR NOT NULL,
    -- tipo INTEGER NOT NULL DEFAULT 1 --PuntoDeEquilibrioTipo                       
    puntoDeEquilibrioTipo VARCHAR NOT NULL REFERENCES massoftware.PuntoDeEquilibrioTipo (id)	
);	

CREATE UNIQUE INDEX u_PuntoDeEquilibrio_ejercicioContable_puntoDeEquilibrio ON massoftware.PuntoDeEquilibrio (ejercicioContable, puntoDeEquilibrio);

-- SELECT * FROM massoftware.PuntoDeEquilibrio;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS massoftware.ftgFormatPuntoDeEquilibrio() CASCADE;

CREATE OR REPLACE FUNCTION massoftware.ftgFormatPuntoDeEquilibrio() RETURNS TRIGGER AS $formatPuntoDeEquilibrio$
DECLARE
BEGIN
   
	-- NEW.nombre := massoftware.white_is_null(REPLACE(TRIM(NEW.nombre), '"', ''));	
    NEW.nombre := massoftware.white_is_null(TRIM(NEW.nombre));	
    -- NEW.abreviatura := massoftware.white_is_null(REPLACE(TRIM(NEW.abreviatura), '"', ''));	
    -- NEW.abreviatura := massoftware.white_is_null(TRIM(NEW.abreviatura));	

	RETURN NEW;
END;
$formatPuntoDeEquilibrio$ LANGUAGE plpgsql;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP TRIGGER IF EXISTS tgFormatPuntoDeEquilibrio ON massoftware.PuntoDeEquilibrio CASCADE;

CREATE TRIGGER tgFormatPuntoDeEquilibrio BEFORE INSERT OR UPDATE 
    ON massoftware.PuntoDeEquilibrio FOR EACH ROW 
    EXECUTE PROCEDURE massoftware.ftgFormatPuntoDeEquilibrio();


-- ==========================================================================================================================

-- Table: massoftware.PlanDeCuentaEstado

DROP TABLE IF EXISTS massoftware.PlanDeCuentaEstado CASCADE;

CREATE TABLE massoftware.PlanDeCuentaEstado
(
    -- id VARCHAR NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),          
    id VARCHAR PRIMARY KEY DEFAULT uuid_generate_v4(),          
    codigo INTEGER NOT NULL,
    nombre VARCHAR NOT NULL
);	

CREATE UNIQUE INDEX u_PlanDeCuentaEstado_codigo ON massoftware.PlanDeCuentaEstado (codigo);
CREATE UNIQUE INDEX u_PlanDeCuentaEstado_nombre ON massoftware.PlanDeCuentaEstado (LOWER(TRIM(nombre)));

-- SELECT * FROM massoftware.PlanDeCuentaEstado;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS massoftware.ftgFormatPlanDeCuentaEstado() CASCADE;

CREATE OR REPLACE FUNCTION massoftware.ftgFormatPlanDeCuentaEstado() RETURNS TRIGGER AS $formatPlanDeCuentaEstado$
DECLARE
BEGIN
   
	NEW.nombre := massoftware.white_is_null(TRIM(NEW.nombre));	    

	RETURN NEW;
END;
$formatPlanDeCuentaEstado$ LANGUAGE plpgsql;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP TRIGGER IF EXISTS tgFormatPlanDeCuentaEstado ON massoftware.PlanDeCuentaEstado CASCADE;

CREATE TRIGGER tgFormatPlanDeCuentaEstado BEFORE INSERT OR UPDATE 
    ON massoftware.PlanDeCuentaEstado FOR EACH ROW 
    EXECUTE PROCEDURE massoftware.ftgFormatPlanDeCuentaEstado();
    
    
-- SELECT * FROM massoftware.PlanDeCuentaEstado;    

-- ==========================================================================================================================

-- Table: massoftware.CostoDeVenta

DROP TABLE IF EXISTS massoftware.CostoDeVenta CASCADE;

CREATE TABLE massoftware.CostoDeVenta
(
    -- id VARCHAR NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),          
    id VARCHAR PRIMARY KEY DEFAULT uuid_generate_v4(),          
    codigo INTEGER NOT NULL,
    nombre VARCHAR NOT NULL
);	

CREATE UNIQUE INDEX u_CostoDeVenta_codigo ON massoftware.CostoDeVenta (codigo);
CREATE UNIQUE INDEX u_CostoDeVenta_nombre ON massoftware.CostoDeVenta (LOWER(TRIM(nombre)));

-- SELECT * FROM massoftware.CostoDeVenta;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS massoftware.ftgFormatCostoDeVenta() CASCADE;

CREATE OR REPLACE FUNCTION massoftware.ftgFormatCostoDeVenta() RETURNS TRIGGER AS $formatCostoDeVenta$
DECLARE
BEGIN
   
	NEW.nombre := massoftware.white_is_null(TRIM(NEW.nombre));	    

	RETURN NEW;
END;
$formatCostoDeVenta$ LANGUAGE plpgsql;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP TRIGGER IF EXISTS tgFormaCostoDeVenta ON massoftware.CostoDeVenta CASCADE;

CREATE TRIGGER tgFormatCostoDeVenta BEFORE INSERT OR UPDATE 
    ON massoftware.CostoDeVenta FOR EACH ROW 
    EXECUTE PROCEDURE massoftware.ftgFormatCostoDeVenta();
    
    
-- SELECT * FROM massoftware.CostoDeVenta;    

-- ==========================================================================================================================

-- Table: massoftware.PlanDeCuenta

DROP TABLE IF EXISTS massoftware.PlanDeCuenta CASCADE;

CREATE TABLE massoftware.PlanDeCuenta
(
    id VARCHAR PRIMARY KEY DEFAULT uuid_generate_v4(),  
    -- -----------------------------------------------------------------------------------
    ejercicioContable VARCHAR NOT NULL REFERENCES massoftware.EjercicioContable (id),	
    -- -----------------------------------------------------------------------------------
    codigoCuentaPadre VARCHAR  NOT NULL, -- integra
	codigoCuenta VARCHAR  NOT NULL,	-- cuentaDeJerarquia    
    cuentaContable VARCHAR NOT NULL,    
	nombre VARCHAR NOT NULL,
    -- -----------------------------------------------------------------------------------
	imputable BOOLEAN NOT NULL DEFAULT false,
    ajustaPorInflacion BOOLEAN NOT NULL DEFAULT false,
    planDeCuentaEstado VARCHAR NOT NULL REFERENCES massoftware.PlanDeCuentaEstado (id), -- PlanDeCuentaEstado
	cuentaConApropiacion BOOLEAN NOT NULL DEFAULT false,	
	centroDeCostoContable VARCHAR REFERENCES massoftware.CentroDeCostoContable (id),
	cuentaAgrupadora VARCHAR, -- aca va cualquier texto, texto libre
	porcentaje DOUBLE PRECISION NOT NULL DEFAULT 0.0,
	puntoDeEquilibrio VARCHAR REFERENCES massoftware.PuntoDeEquilibrio (id),
    costoDeVenta VARCHAR REFERENCES massoftware.CostoDeVenta (id)	
);	

CREATE UNIQUE INDEX u_PlanDeCuenta_ejercicioContable_codigoCuenta ON massoftware.PlanDeCuenta (ejercicioContable, LOWER(TRIM(codigoCuenta)));
CREATE UNIQUE INDEX u_PlanDeCuenta_ejercicioContable_codigoCuentaPadre_codigoCuenta ON massoftware.PlanDeCuenta (ejercicioContable, LOWER(TRIM(codigoCuentaPadre)), LOWER(TRIM(codigoCuenta)));
CREATE UNIQUE INDEX u_PlanDeCuenta_ejercicioContable_cuentaContable ON massoftware.PlanDeCuenta (ejercicioContable, LOWER(TRIM(cuentaContable)));
-- CREATE UNIQUE INDEX u_PlanDeCuenta_ejercicioContable_nombre ON massoftware.PlanDeCuenta (ejercicioContable, LOWER(TRIM(nombre)));
CREATE UNIQUE INDEX u_PlanDeCuenta_ejercicioContable_codigoCuenta_nombre ON massoftware.PlanDeCuenta (ejercicioContable, LOWER(TRIM(codigoCuenta)), LOWER(TRIM(nombre)));

-- ---------------------------------------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS massoftware.ftgFormatPlanDeCuenta() CASCADE;

CREATE OR REPLACE FUNCTION massoftware.ftgFormatPlanDeCuenta() RETURNS TRIGGER AS $formatPlanDeCuenta$
DECLARE
BEGIN
   
	NEW.codigoCuentaPadre := massoftware.white_is_null(REPLACE(TRIM(NEW.codigoCuentaPadre), '"', ''));	
    NEW.codigoCuenta := massoftware.white_is_null(REPLACE(TRIM(NEW.codigoCuenta), '"', ''));	
    NEW.cuentaContable := massoftware.white_is_null(REPLACE(TRIM(NEW.cuentaContable), '"', ''));
    NEW.nombre := massoftware.white_is_null(REPLACE(TRIM(NEW.nombre), '"', ''));	    
    NEW.cuentaAgrupadora := massoftware.white_is_null(REPLACE(TRIM(NEW.cuentaAgrupadora), '"', ''));

	RETURN NEW;
END;
$formatPlanDeCuenta$ LANGUAGE plpgsql;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP TRIGGER IF EXISTS tgFormatPlanDeCuenta ON massoftware.PlanDeCuenta CASCADE;

CREATE TRIGGER tgFormatPlanDeCuenta BEFORE INSERT OR UPDATE 
    ON massoftware.PlanDeCuenta FOR EACH ROW 
    EXECUTE PROCEDURE massoftware.ftgFormatPlanDeCuenta();

-- SELECT * FROM massoftware.PlanDeCuenta;



-- ==========================================================================================================================

-- ==========================================================================================================================
-- ==========================================================================================================================
-- ==========================================================================================================================
-- =======================																				=====================
-- =======================				VISTAS 															=====================	
-- =======================																				=====================
-- ==========================================================================================================================
-- ==========================================================================================================================
-- ==========================================================================================================================


DROP VIEW IF EXISTS massoftware.vEjercicioContable CASCADE; 

CREATE OR REPLACE VIEW massoftware.vEjercicioContable AS

	SELECT	'com.massoftware.model.EjercicioContable' AS ClassEjercicioContable
			,id
			,ejercicio
			,comentario
			,fechaApertura
			,fechaCierre
			,ejercicioCerrado
			,ejercicioCerradoModulos
	FROM	massoftware.EjercicioContable;
    
-- SELECT * FROM massoftware.vEjercicioContable ORDER BY ejercicio DESC;            
    
-- ==========================================================================================================================

DROP VIEW IF EXISTS massoftware.vUsuario CASCADE; 

CREATE OR REPLACE VIEW massoftware.vUsuario AS

	 SELECT	'com.massoftware.model.Usuario' AS ClassUsuario
			,Usuario.id
			,Usuario.numero
			,Usuario.nombre			
            	,vEjercicioContable.id AS ejercicioContable_id		
				,vEjercicioContable.ejercicio AS ejercicioContable_ejercicio		
				,vEjercicioContable.fechaApertura AS ejercicioContable_fechaApertura
				,vEjercicioContable.fechaCierre AS ejercicioContable_fechaCierre
				,vEjercicioContable.ejercicioCerrado AS ejercicioContable_ejercicioCerrado
				,vEjercicioContable.ejercicioCerradoModulos AS ejercicioContable_ejercicioCerradoModulos
				,vEjercicioContable.comentario AS ejercicioContable_comentario
	FROM	massoftware.Usuario
	LEFT JOIN	massoftware.vEjercicioContable 
		ON vEjercicioContable.id = Usuario.ejercicioContable;
        
-- SELECT * FROM massoftware.vUsuario; 

-- ==========================================================================================================================

DROP VIEW IF EXISTS massoftware.vCentroDeCostoContable CASCADE; 

CREATE OR REPLACE VIEW massoftware.vCentroDeCostoContable AS

	 SELECT	'com.massoftware.model.CentroDeCostoContable' AS ClassCentroDeCostoContable
			,CentroDeCostoContable.id
			,CentroDeCostoContable.numero 
			,CentroDeCostoContable.nombre			
            ,CentroDeCostoContable.abreviatura
            	,vEjercicioContable.id AS ejercicioContable_id		
				,vEjercicioContable.ejercicio AS ejercicioContable_ejercicio		
				,vEjercicioContable.fechaApertura AS ejercicioContable_fechaApertura
				,vEjercicioContable.fechaCierre AS ejercicioContable_fechaCierre
				,vEjercicioContable.ejercicioCerrado AS ejercicioContable_ejercicioCerrado
				,vEjercicioContable.ejercicioCerradoModulos AS ejercicioContable_ejercicioCerradoModulos
				,vEjercicioContable.comentario AS ejercicioContable_comentario
	FROM	massoftware.CentroDeCostoContable
	LEFT JOIN	massoftware.vEjercicioContable 
		ON vEjercicioContable.id = CentroDeCostoContable.ejercicioContable;

--  SELECT * FROM massoftware.vCentroDeCostoContable; 

-- ==========================================================================================================================

DROP VIEW IF EXISTS massoftware.vPuntoDeEquilibrioTipo CASCADE; 

CREATE OR REPLACE VIEW massoftware.vPuntoDeEquilibrioTipo AS                                   

	 SELECT	'com.massoftware.model.PuntoDeEquilibrioTipo' AS ClassPuntoDeEquilibrioTipo
			,PuntoDeEquilibrioTipo.id            	
			,PuntoDeEquilibrioTipo.codigo 
			,PuntoDeEquilibrioTipo.nombre	                                    	
	FROM	massoftware.PuntoDeEquilibrioTipo;

--  SELECT * FROM massoftware.vPuntoDeEquilibrioTipo; 

-- ==========================================================================================================================

DROP VIEW IF EXISTS massoftware.vPuntoDeEquilibrio CASCADE; 

CREATE OR REPLACE VIEW massoftware.vPuntoDeEquilibrio AS                                   

	 SELECT	'com.massoftware.model.PuntoDeEquilibrio' AS ClassPuntoDeEquilibrio
			,PuntoDeEquilibrio.id
            	,vEjercicioContable.id AS ejercicioContable_id		
				,vEjercicioContable.ejercicio AS ejercicioContable_ejercicio		
				,vEjercicioContable.fechaApertura AS ejercicioContable_fechaApertura
				,vEjercicioContable.fechaCierre AS ejercicioContable_fechaCierre
				,vEjercicioContable.ejercicioCerrado AS ejercicioContable_ejercicioCerrado
				,vEjercicioContable.ejercicioCerradoModulos AS ejercicioContable_ejercicioCerradoModulos
				,vEjercicioContable.comentario AS ejercicioContable_comentario
			,PuntoDeEquilibrio.puntoDeEquilibrio 
			,PuntoDeEquilibrio.nombre	           
            	,vPuntoDeEquilibrioTipo.id AS puntoDeEquilibrioTipo_id		
				,vPuntoDeEquilibrioTipo.codigo AS puntoDeEquilibrioTipo_codigo	
				,vPuntoDeEquilibrioTipo.nombre AS puntoDeEquilibrioTipo_nombre            	
	FROM	massoftware.PuntoDeEquilibrio
	LEFT JOIN	massoftware.vEjercicioContable 
		ON vEjercicioContable.id = PuntoDeEquilibrio.ejercicioContable
    LEFT JOIN	massoftware.vPuntoDeEquilibrioTipo 
		ON vPuntoDeEquilibrioTipo.id = PuntoDeEquilibrio.puntoDeEquilibrioTipo;    

-- SELECT * FROM massoftware.vPuntoDeEquilibrio; 
-- SELECT * FROM massoftware.PuntoDeEquilibrio; 
-- SELECT * FROM massoftware.vPuntoDeEquilibrioTipo; 

-- ==========================================================================================================================

DROP VIEW IF EXISTS massoftware.vPlanDeCuentaEstado CASCADE; 

CREATE OR REPLACE VIEW massoftware.vPlanDeCuentaEstado AS                                   

	 SELECT	'com.massoftware.model.PlanDeCuentaEstado' AS ClassPlanDeCuentaEstado
			,PlanDeCuentaEstado.id            	
			,PlanDeCuentaEstado.codigo 
			,PlanDeCuentaEstado.nombre	                                    	
	FROM	massoftware.PlanDeCuentaEstado;

--  SELECT * FROM massoftware.vPlanDeCuentaEstado; 
	
-- ==========================================================================================================================

DROP VIEW IF EXISTS massoftware.vCostoDeVenta CASCADE; 

CREATE OR REPLACE VIEW massoftware.vCostoDeVenta AS                                   

	 SELECT	'com.massoftware.model.CostoDeVenta' AS ClassCostoDeVenta
			,CostoDeVenta.id            	
			,CostoDeVenta.codigo 
			,CostoDeVenta.nombre	                                    	
	FROM	massoftware.CostoDeVenta;

--  SELECT * FROM massoftware.vCostoDeVenta; 

-- ==========================================================================================================================

DROP VIEW IF EXISTS massoftware.vPlanDeCuenta CASCADE; 

CREATE OR REPLACE VIEW massoftware.vPlanDeCuenta AS                                   
	

	SELECT	'com.massoftware.model.PlanDeCuenta' AS ClassPlanDeCuenta	
			-----------------------------------------------------------------------------------------------------
			, PlanDeCuenta.id			
			-----------------------------------------------------------------------------------------------------
			, vEjercicioContable.id AS ejercicioContable_id		
				, vEjercicioContable.ejercicio AS ejercicioContable_ejercicio		
				, vEjercicioContable.fechaApertura AS ejercicioContable_fechaApertura
				, vEjercicioContable.fechaCierre AS ejercicioContable_fechaCierre
				, vEjercicioContable.ejercicioCerrado AS ejercicioContable_ejercicioCerrado
				, vEjercicioContable.ejercicioCerradoModulos AS ejercicioContable_ejercicioCerradoModulos
				, vEjercicioContable.comentario AS ejercicioContable_comentario
			-----------------------------------------------------------------------------------------------------			
			, PlanDeCuenta.codigoCuentaPadre -- integra ej 6.40.00.00.00.00
			, PlanDeCuenta.codigoCuenta -- integra ej 6.40.00.00.00.10
			, PlanDeCuenta.cuentaContable -- TEXTO LIBRE
			, PlanDeCuenta.nombre -- TEXTO LIBRE
			-----------------------------------------------------------------------------------------------------
			, PlanDeCuenta.imputable
			, PlanDeCuenta.ajustaPorInflacion
			-----------------------------------------------------------------------------------------------------			
			, vPlanDeCuentaEstado.id  AS planDeCuentaEstado_id			
				, vPlanDeCuentaEstado.codigo AS planDeCuentaEstado_codigo
				, vPlanDeCuentaEstado.nombre AS planDeCuentaEstado_nombre
			-----------------------------------------------------------------------------------------------------
			, PlanDeCuenta.cuentaConApropiacion
			-----------------------------------------------------------------------------------------------------
			, vCentroDeCostoContable.id  AS centroDeCostoContable_id			
				, vCentroDeCostoContable.numero AS centroDeCostoContable_numero
				, vCentroDeCostoContable.nombre AS centroDeCostoContable_nombre
				, vCentroDeCostoContable.abreviatura AS centroDeCostoContable_abreviatura
					-----------------------------------------------------------------------------------------------------
					, vCentroDeCostoContable.ejercicioContable_id AS centroDeCostoContable_ejercicioContable_id		
					, vCentroDeCostoContable.ejercicioContable_ejercicio	AS centroDeCostoContable_ejercicioContable_ejercicio	
					, vCentroDeCostoContable.ejercicioContable_fechaApertura AS centroDeCostoContable_ejercicioContable_fechaApertura		
					, vCentroDeCostoContable.ejercicioContable_fechaCierre AS centroDeCostoContable_ejercicioContable_fechaCierre
					, vCentroDeCostoContable.ejercicioContable_ejercicioCerrado AS centroDeCostoContable_ejercicioContable_ejercicioCerrado		
					, vCentroDeCostoContable.ejercicioContable_ejercicioCerradoModulos AS centroDeCostoContable_ejercicioContable_ejercicioCerradoModulos	
					, vCentroDeCostoContable.ejercicioContable_comentario AS centroDeCostoContable_ejercicioContable_comentario		
			-----------------------------------------------------------------------------------------------------
			, PlanDeCuenta.cuentaAgrupadora -- TEXTO LIBRE
			, PlanDeCuenta.porcentaje
			-----------------------------------------------------------------------------------------------------
			, vPuntoDeEquilibrio.id AS puntoDeEquilibrio_id
					-----------------------------------------------------------------------------------------------------
					, vPuntoDeEquilibrio.ejercicioContable_id AS puntoDeEquilibrio_ejercicioContable_id		
					, vPuntoDeEquilibrio.ejercicioContable_ejercicio	AS puntoDeEquilibrio_ejercicioContable_ejercicio	
					, vPuntoDeEquilibrio.ejercicioContable_fechaApertura AS puntoDeEquilibrio_ejercicioContable_fechaApertura		
					, vPuntoDeEquilibrio.ejercicioContable_fechaCierre AS puntoDeEquilibrio_ejercicioContable_fechaCierre
					, vPuntoDeEquilibrio.ejercicioContable_ejercicioCerrado AS puntoDeEquilibrio_ejercicioContable_ejercicioCerrado		
					, vPuntoDeEquilibrio.ejercicioContable_ejercicioCerradoModulos AS puntoDeEquilibrio_ejercicioContable_ejercicioCerradoModulos	
					, vPuntoDeEquilibrio.ejercicioContable_comentario AS puntoDeEquilibrio_ejercicioContable_comentario
					-----------------------------------------------------------------------------------------------------		
                , vPuntoDeEquilibrio.puntoDeEquilibrio AS puntoDeEquilibrio_puntoDeEquilibrio
                , vPuntoDeEquilibrio.nombre AS puntoDeEquilibrio_nombre
				-----------------------------------------------------------------------------------------------------								
				, vPuntoDeEquilibrio.puntoDeEquilibrioTipo_id AS puntoDeEquilibrio_puntoDeEquilibrioTipo_id				
				, vPuntoDeEquilibrio.puntoDeEquilibrioTipo_codigo AS puntoDeEquilibrio_puntoDeEquilibrioTipo_codigo
				, vPuntoDeEquilibrio.puntoDeEquilibrioTipo_nombre AS puntoDeEquilibrio_puntoDeEquilibrioTipo_nombre 
			-----------------------------------------------------------------------------------------------------
			,vCostoDeVenta.id  AS costoDeVenta_id			
				,vCostoDeVenta.codigo AS costoDeVenta_codigo
				,vCostoDeVenta.nombre AS costoDeVenta_nombre
			-----------------------------------------------------------------------------------------------------
			
			
	FROM massoftware.PlanDeCuenta
	LEFT JOIN	massoftware.vEjercicioContable
			ON		vEjercicioContable.id = PlanDeCuenta.ejercicioContable
	LEFT JOIN	massoftware.vPlanDeCuentaEstado
			ON		vPlanDeCuentaEstado.id = PlanDeCuenta.planDeCuentaEstado
	LEFT JOIN	massoftware.vCentroDeCostoContable
			ON		vCentroDeCostoContable.id = PlanDeCuenta.centroDeCostoContable 
			AND		vCentroDeCostoContable.ejercicioContable_id = PlanDeCuenta.ejercicioContable 
	LEFT JOIN	massoftware.vPuntoDeEquilibrio
			ON		vPuntoDeEquilibrio.id = PlanDeCuenta.puntoDeEquilibrio
			AND		vPuntoDeEquilibrio.ejercicioContable_id = PlanDeCuenta.ejercicioContable 
	LEFT JOIN	massoftware.vCostoDeVenta
			ON		vCostoDeVenta.id = PlanDeCuenta.costoDeVenta;
	

	-- SELECT * FROM massoftware.PlanDeCuenta ;
	-- SELECT * FROM massoftware.vPlanDeCuenta ;
	-- SELECT * FROM massoftware.vPlanDeCuenta ORDER BY ejercicioContable_ejercicio DESC, codigoCuenta;	
    /*
        SELECT 	centroDeCostoContable_id			
                    , centroDeCostoContable_numero
                    , centroDeCostoContable_nombre
                    , centroDeCostoContable_abreviatura
                        -----------------------------------------------------------------------------------------------------
                        , centroDeCostoContable_ejercicioContable_id		
                        , centroDeCostoContable_ejercicioContable_ejercicio	
                        , centroDeCostoContable_ejercicioContable_fechaApertura		
                        , centroDeCostoContable_ejercicioContable_fechaCierre
                        , centroDeCostoContable_ejercicioContable_ejercicioCerrado		
                        , centroDeCostoContable_ejercicioContable_ejercicioCerradoModulos	
                        , centroDeCostoContable_ejercicioContable_comentario 
        FROM 	massoftware.vPlanDeCuenta 
        WHERE  	ejercicioContable_ejercicio = 2013 
        ORDER BY ejercicioContable_ejercicio DESC, codigoCuenta;	
    */
    
    