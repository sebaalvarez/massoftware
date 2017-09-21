-- CREATE EXTENSION "uuid-ossp";
-- SELECT uuid_generate_v4();

DROP SCHEMA IF EXISTS massoftware CASCADE;

CREATE SCHEMA massoftware AUTHORIZATION postgres;	


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
    id VARCHAR NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),  
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
   
	NEW.comentario := massoftware.white_is_null(REPLACE(TRIM(NEW.comentario), '"', ''));	

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
    id VARCHAR NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),  
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
   
	NEW.nombre := massoftware.white_is_null(REPLACE(TRIM(NEW.nombre), '"', ''));	

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
    id VARCHAR NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),  
    ejercicioContable VARCHAR NOT NULL REFERENCES massoftware.EjercicioContable (id),	
    centroDeCostoContable INTEGER NOT NULL,
    nombre VARCHAR,
    abreviatura VARCHAR
);

CREATE UNIQUE INDEX u_CentroDeCostoContable_ejercicioContable_centroDeCostoContable ON massoftware.CentroDeCostoContable (ejercicioContable, centroDeCostoContable);

-- SELECT * FROM massoftware.CentroDeCostoContable;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS massoftware.ftgFormatCentroDeCostoContable() CASCADE;

CREATE OR REPLACE FUNCTION massoftware.ftgFormatCentroDeCostoContable() RETURNS TRIGGER AS $formatCentroDeCostoContable$
DECLARE
BEGIN
   
	NEW.nombre := massoftware.white_is_null(REPLACE(TRIM(NEW.nombre), '"', ''));	
    NEW.abreviatura := massoftware.white_is_null(REPLACE(TRIM(NEW.abreviatura), '"', ''));	

	RETURN NEW;
END;
$formatCentroDeCostoContable$ LANGUAGE plpgsql;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP TRIGGER IF EXISTS tgFormatCentroDeCostoContable ON massoftware.CentroDeCostoContable CASCADE;

CREATE TRIGGER tgFormatCentroDeCostoContable BEFORE INSERT OR UPDATE 
    ON massoftware.CentroDeCostoContable FOR EACH ROW 
    EXECUTE PROCEDURE massoftware.ftgFormatCentroDeCostoContable();

-- ==========================================================================================================================
	
-- Table: massoftware.PuntoDeEquilibrio

DROP TABLE IF EXISTS massoftware.PuntoDeEquilibrio CASCADE;

CREATE TABLE massoftware.PuntoDeEquilibrio
(
    id VARCHAR NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),      
    ejercicioContable VARCHAR NOT NULL REFERENCES massoftware.EjercicioContable (id),	
    puntoDeEquilibrio INTEGER NOT NULL, -- numero
    nombre VARCHAR NOT NULL,
    tipo INTEGER NOT NULL DEFAULT 1 --PuntoDeEquilibrioTipo                       
);	

CREATE UNIQUE INDEX u_PuntoDeEquilibrio_ejercicioContable_puntoDeEquilibrio ON massoftware.PuntoDeEquilibrio (ejercicioContable, puntoDeEquilibrio);

-- ---------------------------------------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS massoftware.ftgFormatPuntoDeEquilibrio() CASCADE;

CREATE OR REPLACE FUNCTION massoftware.ftgFormatPuntoDeEquilibrio() RETURNS TRIGGER AS $formatPuntoDeEquilibrio$
DECLARE
BEGIN
   
	NEW.nombre := massoftware.white_is_null(REPLACE(TRIM(NEW.nombre), '"', ''));	
    NEW.abreviatura := massoftware.white_is_null(REPLACE(TRIM(NEW.abreviatura), '"', ''));	

	RETURN NEW;
END;
$formatPuntoDeEquilibrio$ LANGUAGE plpgsql;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP TRIGGER IF EXISTS tgFormatPuntoDeEquilibrio ON massoftware.PuntoDeEquilibrio CASCADE;

CREATE TRIGGER tgFormatPuntoDeEquilibrio BEFORE INSERT OR UPDATE 
    ON massoftware.PuntoDeEquilibrio FOR EACH ROW 
    EXECUTE PROCEDURE massoftware.ftgFormatPuntoDeEquilibrio();

-- ==========================================================================================================================

-- Table: massoftware.PlanDeCuenta

DROP TABLE IF EXISTS massoftware.PlanDeCuenta CASCADE;

CREATE TABLE massoftware.PlanDeCuenta
(
    id VARCHAR NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),  
    ejercicioContable VARCHAR NOT NULL REFERENCES massoftware.EjercicioContable (id),	
    cuentaContable VARCHAR NOT NULL,
    integra VARCHAR  NOT NULL,
	cuentaDeJerarquia VARCHAR  NOT NULL,	
	nombre VARCHAR NOT NULL,
	imputable BOOLEAN NOT NULL DEFAULT false,
	cuentaConApropiacion BOOLEAN NOT NULL DEFAULT false,
	ajustaPorInflacion BOOLEAN NOT NULL DEFAULT false,
	estado INTEGER NOT NULL DEFAULT 1, -- PlanDeCuentaEstado
	centroDeCostoContable VARCHAR NOT NULL REFERENCES massoftware.CentroDeCostoContable (id),
	cuentaAgrupadora VARCHAR,
	porsentaje DOUBLE PRECISION NOT NULL DEFAULT 0.0,
	puntoDeEquilibrio VARCHAR NOT NULL REFERENCES massoftware.PuntoDeEquilibrio (id),
	costoDeVenta INTEGER NOT NULL DEFAULT 1 -- CostoDeVenta                        
);	

CREATE UNIQUE INDEX u_PlanDeCuenta_ejercicioContable_puntoDeEquilibrio ON massoftware.PlanDeCuenta (ejercicioContable, LOWER(TRIM(cuentaContable)));

-- ---------------------------------------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS massoftware.ftgFormatPlanDeCuenta() CASCADE;

CREATE OR REPLACE FUNCTION massoftware.ftgFormatPlanDeCuenta() RETURNS TRIGGER AS $formatPlanDeCuenta$
DECLARE
BEGIN
   
	NEW.nombre := massoftware.white_is_null(REPLACE(TRIM(NEW.nombre), '"', ''));	
    NEW.abreviatura := massoftware.white_is_null(REPLACE(TRIM(NEW.abreviatura), '"', ''));	

	RETURN NEW;
END;
$formatPlanDeCuenta$ LANGUAGE plpgsql;

-- ---------------------------------------------------------------------------------------------------------------------------

DROP TRIGGER IF EXISTS tgFormatPlanDeCuenta ON massoftware.PlanDeCuenta CASCADE;

CREATE TRIGGER tgFormatPlanDeCuenta BEFORE INSERT OR UPDATE 
    ON massoftware.PlanDeCuenta FOR EACH ROW 
    EXECUTE PROCEDURE massoftware.ftgFormatPlanDeCuenta();

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
    
-- SELECT * FROM massoftware.vEjercicioContable;            
    
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
	

