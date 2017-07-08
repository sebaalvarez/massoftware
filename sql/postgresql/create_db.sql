
CREATE USER massoftwareroot WITH
	LOGIN
	SUPERUSER
	CREATEDB
	CREATEROLE
	INHERIT
	NOREPLICATION
	CONNECTION LIMIT -1;

CREATE DATABASE massoftware
    WITH 
    OWNER = massoftwareroot
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

CREATE SCHEMA massoftware
    AUTHORIZATION postgres;


CREATE EXTENSION "uuid-ossp";
-- SELECT uuid_generate_v4();	
	
-- Table: massoftware."EjercicioContable"

DROP TABLE IF EXISTS massoftware."EjercicioContable" CASCADE;

CREATE TABLE massoftware."EjercicioContable"
(
    id character varying(36) COLLATE pg_catalog."default" NOT NULL DEFAULT uuid_generate_v4(),
    ejercicio integer NOT NULL,
    "fechaApertura" date NOT NULL,
    "fechaCierre" date NOT NULL,
    "ejercicioCerrado" boolean NOT NULL,
    "ejercicioCerradoModulos" boolean NOT NULL,
    comentario character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT "EjercicioContable_pkey" PRIMARY KEY (id),
    CONSTRAINT "UEjercicioContable.ercercicio" UNIQUE (ejercicio)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE massoftware."EjercicioContable"
    OWNER to massoftwareroot;	
	
	

-- INSERT INTO massoftware."EjercicioContable"(id, ejercicio, "fechaApertura", "fechaCierre", "ejercicioCerrado", "ejercicioCerradoModulos", comentario) VALUES (?, ?, ?, ?, ?, ?, ?);

DELETE FROM massoftware."EjercicioContable";

INSERT INTO massoftware."EjercicioContable"(ejercicio, "fechaApertura", "fechaCierre", "ejercicioCerrado", "ejercicioCerradoModulos") VALUES (2017,'2017-01-01' ,'2017-12-30' ,false,false);
INSERT INTO massoftware."EjercicioContable"(ejercicio, "fechaApertura", "fechaCierre", "ejercicioCerrado", "ejercicioCerradoModulos") VALUES (2016,'2015-03-01' ,'2016-02-28' ,false,false);
INSERT INTO massoftware."EjercicioContable"(ejercicio, "fechaApertura", "fechaCierre", "ejercicioCerrado", "ejercicioCerradoModulos") VALUES (2015,'2014-03-01' ,'2015-02-28' ,true,true);
INSERT INTO massoftware."EjercicioContable"(ejercicio, "fechaApertura", "fechaCierre", "ejercicioCerrado", "ejercicioCerradoModulos") VALUES (2014,'2013-03-01' ,'2014-02-28' ,true,true);
INSERT INTO massoftware."EjercicioContable"(ejercicio, "fechaApertura", "fechaCierre", "ejercicioCerrado", "ejercicioCerradoModulos") VALUES (2013,'2012-03-01' ,'2013-02-28' ,true,true);
INSERT INTO massoftware."EjercicioContable"(ejercicio, "fechaApertura", "fechaCierre", "ejercicioCerrado", "ejercicioCerradoModulos") VALUES (2012,'2011-03-01' ,'2012-02-29' ,true,true);
INSERT INTO massoftware."EjercicioContable"(ejercicio, "fechaApertura", "fechaCierre", "ejercicioCerrado", "ejercicioCerradoModulos") VALUES (2011,'2010-03-01' ,'2011-02-28' ,true,true);
INSERT INTO massoftware."EjercicioContable"(ejercicio, "fechaApertura", "fechaCierre", "ejercicioCerrado", "ejercicioCerradoModulos") VALUES (2010,'2009-03-01' ,'2010-02-28' ,true,true);
INSERT INTO massoftware."EjercicioContable"(ejercicio, "fechaApertura", "fechaCierre", "ejercicioCerrado", "ejercicioCerradoModulos") VALUES (2009,'2008-03-01' ,'2009-02-28' ,true,true);
INSERT INTO massoftware."EjercicioContable"(ejercicio, "fechaApertura", "fechaCierre", "ejercicioCerrado", "ejercicioCerradoModulos") VALUES (2008,'2007-03-01' ,'2008-02-29' ,true,true);
INSERT INTO massoftware."EjercicioContable"(ejercicio, "fechaApertura", "fechaCierre", "ejercicioCerrado", "ejercicioCerradoModulos") VALUES (2007,'2006-03-01' ,'2007-02-28' ,true,true);
INSERT INTO massoftware."EjercicioContable"(ejercicio, "fechaApertura", "fechaCierre", "ejercicioCerrado", "ejercicioCerradoModulos") VALUES (2006,'2005-03-01' ,'2006-02-28' ,true,true);
INSERT INTO massoftware."EjercicioContable"(ejercicio, "fechaApertura", "fechaCierre", "ejercicioCerrado", "ejercicioCerradoModulos") VALUES (2005,'2004-03-01' ,'2005-02-28' ,true,true);
INSERT INTO massoftware."EjercicioContable"(ejercicio, "fechaApertura", "fechaCierre", "ejercicioCerrado", "ejercicioCerradoModulos") VALUES (2003,'2003-03-01' ,'2004-02-29' ,true,true);
INSERT INTO massoftware."EjercicioContable"(ejercicio, "fechaApertura", "fechaCierre", "ejercicioCerrado", "ejercicioCerradoModulos") VALUES (2002,'2002-03-01' ,'2003-02-28' ,true,true);

SELECT * FROM  massoftware."EjercicioContable";	