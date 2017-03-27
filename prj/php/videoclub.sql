
DROP DATABASE IF EXISTS videoclub;
CREATE DATABASE videoclub DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
USE videoclub;
GRANT ALL PRIVILEGES ON videoclub.* TO videoclub@localhost IDENTIFIED BY 'videoclub2007';

CREATE TABLE socios (
   dni CHAR(9)  NOT NULL,
   nombre VARCHAR(20) NOT NULL,
   apellido1 VARCHAR(25) NOT NULL,
   apellido2 VARCHAR(25),
   telefono char(9) NOT NULL,
   direccion VARCHAR(50)  NOT NULL,
   cp char(5) NOT NULL,
   ciudad VARCHAR(20) NOT NULL,
   saldo decimal(10,2) NOT NULL,
   clave VARCHAR(32)  NOT NULL,
   PRIMARY KEY  (dni)
)ENGINE = InnoDB;

CREATE TABLE ingresos (
   id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
   dni_socios CHAR(9) NOT NULL,
   fecha DATE NOT NULL,
   cantidad DECIMAL(10,2) NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (dni_socios) REFERENCES socios(dni) ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE tarifas (
   id INT(6) UNSIGNED NOT NULL AUTO_INCREMENT,
   fecha DATE NOT NULL,
   tarifa3 DECIMAL(5,2) NOT NULL,
   tarifa6 DECIMAL(5,2) NOT NULL,
   tarifa12 DECIMAL(5,2) NOT NULL,
   tarifa24 DECIMAL(5,2) NOT NULL,
   PRIMARY KEY(id)
) ENGINE = InnoDB;

CREATE TABLE peliculas (
   id INTEGER(10) UNSIGNED NOT NULL AUTO_INCREMENT,
   titulo VARCHAR(30)  NOT NULL,
   tema VARCHAR(15)  NOT NULL,
   fecha_estreno DATE NOT NULL,
   director VARCHAR(50),
   actores VARCHAR(50),
   sinopsis VARCHAR(512),
   imagen varchar(30),
   PRIMARY KEY  (id)
) ENGINE = InnoDB;

CREATE TABLE ejemplares (
   id CHAR(3) NOT NULL,
   id_peliculas int(10) UNSIGNED NOT NULL,
   disponible BOOLEAN NOT NULL,
   PRIMARY KEY  (id),
   FOREIGN KEY (id_peliculas) REFERENCES peliculas(id) ON UPDATE CASCADE
) ENGINE = InnoDB ;  


-- OJO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!     
-- se debe elegir un tipo que almacene también la hora para fecha_alquiler

CREATE TABLE alquileres (
   id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
   dni_socios char(9)  NOT NULL,
   fecha_alquiler DATETIME NOT NULL,
   id_tarifas int(6) UNSIGNED NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (dni_socios) REFERENCES socios (dni) ON UPDATE CASCADE,
   FOREIGN KEY (id_tarifas) REFERENCES tarifas(id) ON UPDATE CASCADE
) ENGINE = InnoDB;	  

-- OJO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	  
-- se debe elegir un tipo que almacene también la hora para fecha_devolucion

CREATE TABLE detalle_alquileres (
   id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
   id_alquileres INT(10) UNSIGNED NOT NULL ,
   id_ejemplares CHAR(3) NOT NULL ,
   fecha_devolucion DATETIME,
   importe DECIMAL(5,2),
   PRIMARY KEY (id),
   FOREIGN KEY (id_alquileres) REFERENCES alquileres (id) ON UPDATE CASCADE,
   FOREIGN KEY (id_ejemplares) REFERENCES ejemplares (id) ON UPDATE CASCADE
) ENGINE = InnoDB ;

-- -------------------------------------------------------------------------------------------------------
-- DATOS DE PRUEBA
-- -------------------------------------------------------------------------------------------------------

INSERT INTO `socios` VALUES ('11111111H', 'Francisco', 'Pérez', NULL, '954001122', 'C/ Flor de Romero, 25, 3-A.', '41020', 'Sevilla', 10.00, 'francisco2007');
INSERT INTO `socios` VALUES ('12345678Z', 'admin', 'Criado', 'Navarro', '954073065', 'C/ Flor de Tomillo, 4, 7º B.', '41020', 'Sevilla', 26.25, 'admin2007');
INSERT INTO `socios` VALUES ('22222222', 'Pedro', 'Pica', 'Piedra', '954332211', 'C/ Los alamos', '41020', 'Sevilla', 33.25, 'pedro2007');
INSERT INTO `socios` VALUES ('25126190R', 'Francisco', 'Alegre', 'Y Olé', '954001122', 'C/ Flor de Lis, 23, 2-A', '41020', 'Sevilla', 10.00, 'francisco2007');
INSERT INTO `socios` VALUES ('33333333P', 'p', 'p', NULL, '9', 'p', '41020', 'Sevilla', 10.00, 'p2007');
INSERT INTO `socios` VALUES ('87654321X', 'Emilia', 'Gutiérrez', 'López', '954332211', 'C/ Flor de Gitanilla, 5.', '41020', 'Sevilla', 33.25, 'emilia2007');
INSERT INTO `socios` VALUES ('87654326S', 'Fernando', 'García', NULL, '93442212', 'C/ Olivares, 32.', '08080', 'Barcelona', 23.25, 'fernando2007');
INSERT INTO `socios` VALUES ('88888888Y', 'Luisa', 'Gutiérrez', NULL, '91123456', 'C/ Flor de Gitanilla, 5.', '41020', 'Barcelona', 12.75, 'luisa2007');
INSERT INTO `socios` VALUES ('99999999R', 'Pepito', 'Grillo', 'Saltamontes', '954302030', 'C/ Flor de Esgueva, 6.', '41020', 'Sevilla', 20.25, 'pepito2007');



INSERT INTO `ingresos` VALUES (1, '12345678Z', '2007-01-01', 5.25);
INSERT INTO `ingresos` VALUES (2, '12345678Z', '2007-02-02', 5.25);
INSERT INTO `ingresos` VALUES (3, '12345678Z', '2007-03-03', 5.25);
INSERT INTO `ingresos` VALUES (4, '12345678Z', '2007-04-04', 5.25);
INSERT INTO `ingresos` VALUES (5, '12345678Z', '2007-05-05', 5.25);
INSERT INTO `ingresos` VALUES (6, '99999999R', '2007-01-01', 5.25);
INSERT INTO `ingresos` VALUES (7, '99999999R', '2007-02-02', 5.75);
INSERT INTO `ingresos` VALUES (8, '99999999R', '2007-03-03', 5.25);
INSERT INTO `ingresos` VALUES (9, '99999999R', '2007-04-04', 3.25);
INSERT INTO `ingresos` VALUES (10, '99999999R', '2007-05-05', 0.75);
INSERT INTO `ingresos` VALUES (11, '25126190R', '2007-05-27', 10.00);
INSERT INTO `ingresos` VALUES (12, '33333333P', '2007-05-27', 5.00);
INSERT INTO `ingresos` VALUES (13, '22222222', '2007-06-05', 10.00);

                              

INSERT INTO `tarifas` VALUES (1, '2006-01-01', 0.80, 1.10, 1.30, 1.50);
INSERT INTO `tarifas` VALUES (2, '2007-01-01', 0.90, 1.20, 1.50, 1.75);


INSERT INTO `peliculas` VALUES ( 1,'Algo pasa con Mary', 'Comedia', '1998-05-19', 'Hermanos Farrely', 'Cameron Diaz, Ben Stiller, Mat Dillon', 'Años después de que su novia le abandonara durante su época de estudiante, Ted está dispuesto a recuperar a su antiguo antiguo amor. Lo que puede parecer un convencional argumento de comedia romántica está teñido de soeces, burdas y escatológicas situaciones para provocar escenas delirantes.', 'algoPasaConMary.jpg');
INSERT INTO `peliculas` VALUES ( 2,'Casablanca', 'Drama', '1945-04-16', 'No me acuerdo', 'Humphrey Bogart, Ingrid Bergman', 'Historia de amor en la 2ª guerra mundial', 'casablanca.jpg');
INSERT INTO `peliculas` VALUES ( 3,'Hair', 'Drama, Musical', '1978-02-02', 'Milos Forman', 'John Savage, Treth Williams', 'Opera rock protesta guerra Vietnam', 'hair.jpg');
INSERT INTO `peliculas` VALUES ( 4,'Big Fish', 'Drama', '1978-02-02', 'Milos Forman', 'John Savage, Treth Williams', 'Historia de un padre y un Hijo', 'bigFish.jpg');
INSERT INTO `peliculas` VALUES ( 5,'Diamante de Sangre', 'Drama', '1978-02-02', 'Milos Forman', 'Leonardo di Caprio, Jennifer Connelly', 'Película protesta explotación países africanos', 'diamanteDeSangre.jpg');
INSERT INTO `peliculas` VALUES ( 6,'El caso Slevin', 'Acción', '1978-02-02', 'Milos Forman', 'Bruce Willis', 'Opera rock protesta guerra Vietnam', 'elCasoSlevin.jpg');
INSERT INTO `peliculas` VALUES ( 7,'Lo que el viento se llevó', 'Drama', '1997-05-20', 'Lui Vuitton', 'Clark Gable', 'Historia de amor', 'loQueElVientoSeLlevo.jpg');
INSERT INTO `peliculas` VALUES ( 8,'El expreso de medianoche', 'Drama', '1978-10-06', 'Alan Parker', 'Brad Davis, Irena Miracle, Bo Hopkins', 'Un turista americano es apresado en Turquia', 'elExpresoDeMedianoche.jpg');
INSERT INTO `peliculas` VALUES ( 9,'El último rey de esocia', 'Drama, Acción', '2007-02-23', 'Kevin Macdonald', 'Forest Whitaker,  James McAvoy, Kerry Washington', 'In the early 1970s, Nicholas Garrigan, a young semi-idealistic Scottish doctor, comes to Uganda to assist in a rural hospital. Once there, he soon meets up with the new President, Idi Amin, who promises a golden age for the African nation. Garrigan hits it off immediately with the rabid Scotland fan, who soon offers him a senior position in the national health department and becomes one of Amin''s closest advisers. However as the years pass, Garrigan cannot help but notice Amin''s increasingly erratic ', 'elUltimoReyDeEscocia.jpg');
INSERT INTO `peliculas` VALUES (10,'Ciudadano Kane', 'Drama', '1946-06-04', 'Orson Wells', 'Orson Wells', 'Historia de como un niño llega a ser uno de los hombres más ricos del mundo', 'ciudadanoKane.jpg');



INSERT INTO `ejemplares` VALUES ( 1, 1, 1);
INSERT INTO `ejemplares` VALUES ( 2, 1, 1);
INSERT INTO `ejemplares` VALUES ( 3, 1, 1);
INSERT INTO `ejemplares` VALUES ( 4, 1, 1);
INSERT INTO `ejemplares` VALUES ( 5, 2, 1);
INSERT INTO `ejemplares` VALUES ( 6, 2, 1);
INSERT INTO `ejemplares` VALUES ( 7, 3, 1);
INSERT INTO `ejemplares` VALUES ( 8, 3, 1);
INSERT INTO `ejemplares` VALUES ( 9, 3, 1);
INSERT INTO `ejemplares` VALUES (10, 4, 1);
INSERT INTO `ejemplares` VALUES (11, 4, 1);
INSERT INTO `ejemplares` VALUES (12, 4, 1);
INSERT INTO `ejemplares` VALUES (13, 4, 1);
INSERT INTO `ejemplares` VALUES (14, 4, 1);
INSERT INTO `ejemplares` VALUES (15, 4, 1);
INSERT INTO `ejemplares` VALUES (16, 5, 0);
INSERT INTO `ejemplares` VALUES (17, 5, 0);
INSERT INTO `ejemplares` VALUES (18, 5, 0);
INSERT INTO `ejemplares` VALUES (19, 6, 0);
INSERT INTO `ejemplares` VALUES (20, 6, 1);
INSERT INTO `ejemplares` VALUES (21, 6, 1);
INSERT INTO `ejemplares` VALUES (22, 6, 1);
INSERT INTO `ejemplares` VALUES (23, 6, 1);
INSERT INTO `ejemplares` VALUES (24, 8, 1);
INSERT INTO `ejemplares` VALUES (25, 8, 1);
INSERT INTO `ejemplares` VALUES (26, 9, 1);
INSERT INTO `ejemplares` VALUES (27, 9, 1);
INSERT INTO `ejemplares` VALUES (28,10, 1);
INSERT INTO `ejemplares` VALUES (29,10, 0);

INSERT INTO `alquileres` VALUES (1, '25126190R', '2007-06-05 19:51:00', 1);

INSERT INTO `detalle_alquileres` VALUES (1,1,12, NULL, NULL);

INSERT INTO `alquileres` VALUES (2, '12345678Z', '2007-04-08 12:15:00', 2);

INSERT INTO `detalle_alquileres` VALUES (2,2, 1, '2007-04-08 15:15:00', NULL);
INSERT INTO `detalle_alquileres` VALUES (3,2, 2, '2007-04-08 18:13:00', NULL);
INSERT INTO `detalle_alquileres` VALUES (4,2, 3, '2007-04-09 00:13:00', NULL);
INSERT INTO `detalle_alquileres` VALUES (5,2, 4, '2007-04-09 18:18:00', NULL);


INSERT INTO `alquileres` VALUES (3, '99999999R', '2007-04-18 16:16:00', 2);
INSERT INTO `detalle_alquileres` VALUES (6,3, 4, NULL, NULL);
INSERT INTO `detalle_alquileres` VALUES (7,3, 5, NULL, NULL);

INSERT INTO `alquileres` VALUES (4, '87654321X', '2007-05-20 22:56:00', 2);
INSERT INTO `detalle_alquileres` VALUES (8,4, 7, '2007-05-20 23:29:00', NULL);

INSERT INTO `alquileres` VALUES (5, '87654321X', '2007-05-20 23:07:00', 1);
INSERT INTO `detalle_alquileres` VALUES (9,5, 10, NULL, NULL);

INSERT INTO `alquileres` VALUES (6, '22222222', '2007-06-05 19:38:00', 1);
INSERT INTO `detalle_alquileres` VALUES (10,6, 15, NULL, NULL);


INSERT INTO `alquileres` VALUES (7, '25126190R', '2007-06-06 20:10:00', 1);
INSERT INTO `detalle_alquileres` VALUES (11,7, 20, NULL, NULL);
