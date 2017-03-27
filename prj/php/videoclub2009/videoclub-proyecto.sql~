DROP DATABASE IF EXISTS BD_VIDEOCLUB;
CREATE DATABASE BD_VIDEOCLUB  DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
GRANT ALL PRIVILEGES ON BD_VIDEOCLUB.* TO BD_VIDEOCLUB@localhost IDENTIFIED BY 'videoclub2009';
use BD_VIDEOCLUB;
CREATE TABLE CLIENTE (
   num_cli INT UNSIGNED AUTO_INCREMENT NOT NULL,
   nif CHAR(9) NOT NULL NOT NULL,
   nombre VARCHAR(20) NOT NULL,
   apellido1 VARCHAR(20) NOT NULL,
   apellido2 VARCHAR(20),
   dir VARCHAR(50),
   telef CHAR(9) NOT NULL,
   clave CHAR(10) NOT NULL,
   saldo DECIMAL(10,2) NOT NULL,
   fecha_alta DATE,
   PRIMARY KEY(num_cli)
) ENGINE=INNODB;
CREATE TABLE INGRESO (
   num_cli_CLIENTE INT UNSIGNED NOT NULL,
   fecha DATETIME NOT NULL,
   cantidad DECIMAL(10,2) NOT NULL,
   PRIMARY KEY(num_cli_CLIENTE,fecha),
   FOREIGN KEY(num_cli_CLIENTE) REFERENCES CLIENTE(num_cli) ON UPDATE CASCADE
) ENGINE=INNODB;
CREATE TABLE GENERO (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   nombre VARCHAR(20) NOT NULL UNIQUE,
   PRIMARY KEY(id)
) ENGINE=INNODB;
CREATE TABLE ACTOR_DIRECTOR  (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   nombre VARCHAR(35) NOT NULL UNIQUE,
   PRIMARY KEY(id)
) ENGINE=INNODB;
CREATE TABLE PELICULA  (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   titulo VARCHAR(35) NOT NULL,
   anio INT(4) NOT NULL,
   duracion INT(3) NOT NULL,
   sinopsis VARCHAR(500) NOT NULL,
   fichero_caratula VARCHAR(50),
   fecha_adquisicion DATE,
   PRIMARY KEY(id)
) ENGINE=INNODB;
CREATE TABLE EJEMPLAR (
   id_pelicula INT UNSIGNED NOT NULL,
   num TINYINT UNSIGNED NOT NULL,
   PRIMARY KEY(id_pelicula,num),
   FOREIGN KEY(id_pelicula) REFERENCES PELICULA(id) ON UPDATE CASCADE
) ENGINE=INNODB;
CREATE TABLE TARIFA (
   minutos_alquiler INT UNSIGNED NOT NULL,
   coste DECIMAL(10,2) NOT NULL,
   PRIMARY KEY(minutos_alquiler)
) ENGINE=INNODB;
CREATE TABLE ALQUILER (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   fecha DATETIME NOT NULL,
   num_cli_CLIENTE INT UNSIGNED NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(num_cli_CLIENTE) REFERENCES CLIENTE(num_cli) ON UPDATE CASCADE
) ENGINE=INNODB;
CREATE TABLE detalle_alquileres (
   id_ALQUILER INT UNSIGNED NOT NULL,
   id_EJEMPLAR INT UNSIGNED NOT NULL,
   num_EJEMPLAR TINYINT UNSIGNED NOT NULL,
   fecha_devolucion DATETIME,
   importe DECIMAL(10,2),
   PRIMARY KEY(id_ALQUILER,id_EJEMPLAR,num_EJEMPLAR),
   FOREIGN KEY(id_ALQUILER) REFERENCES ALQUILER(id) ON UPDATE CASCADE,
   FOREIGN KEY(id_EJEMPLAR,num_EJEMPLAR) REFERENCES EJEMPLAR(id_pelicula,num) ON UPDATE CASCADE
) ENGINE=INNODB;
CREATE TABLE reserva (
   num_cli_CLIENTE INT UNSIGNED NOT NULL,
   id_EJEMPLAR INT UNSIGNED NOT NULL,
   num_EJEMPLAR TINYINT UNSIGNED NOT NULL,
   fecha DATETIME NOT NULL,
   importe DECIMAL(10,2) NOT NULL,
   retirado BIT NOT NULL,
   PRIMARY KEY(num_cli_CLIENTE,id_EJEMPLAR,num_EJEMPLAR),
   FOREIGN KEY(num_cli_CLIENTE) REFERENCES CLIENTE(num_cli) ON UPDATE CASCADE,
   FOREIGN KEY(id_EJEMPLAR,num_EJEMPLAR) REFERENCES EJEMPLAR(id_pelicula,num) ON UPDATE CASCADE
) ENGINE=INNODB;
CREATE TABLE director_pelicula  (
   id_ACTOR_DIRECTOR INT UNSIGNED NOT NULL,
   id_pelicula INT UNSIGNED NOT NULL,
   PRIMARY KEY(id_ACTOR_DIRECTOR,id_pelicula),
   FOREIGN KEY(id_ACTOR_DIRECTOR) REFERENCES ACTOR_DIRECTOR(id) ON UPDATE CASCADE,
   FOREIGN KEY(id_pelicula) REFERENCES PELICULA(id) ON UPDATE CASCADE
) ENGINE=INNODB;
CREATE TABLE actor_pelicula  (
   id_ACTOR_DIRECTOR INT UNSIGNED NOT NULL,
   id_pelicula INT UNSIGNED NOT NULL,
   PRIMARY KEY(id_ACTOR_DIRECTOR,id_pelicula),
   FOREIGN KEY(id_ACTOR_DIRECTOR) REFERENCES ACTOR_DIRECTOR(id) ON UPDATE CASCADE,
   FOREIGN KEY(id_pelicula) REFERENCES PELICULA(id) ON UPDATE CASCADE
) ENGINE=INNODB;   
CREATE TABLE genero_pelicula  (
   id_pelicula INT UNSIGNED NOT NULL,
   id_GENERO INT UNSIGNED NOT NULL,
   PRIMARY KEY(id_pelicula,id_GENERO),
   FOREIGN KEY(id_pelicula) REFERENCES PELICULA(id) ON UPDATE CASCADE,
   FOREIGN KEY(id_GENERO) REFERENCES GENERO(id) ON UPDATE CASCADE
) ENGINE=INNODB;      
--
-- DATOS
--
INSERT INTO CLIENTE VALUES
(1,'11111111H','admin'    ,''         ,NULL         ,'C/ Flor de Romero, 25, 3-A.' ,'954001122','uno'   ,10,'2009-01-01'),
(2,'12345678Z','Antonio'  ,'Pérez'    ,'Pérez'      ,'C/ Flor del Alamo, 3, 4º C.' ,'954073065','dos'   ,20,'2009-01-01'),
(3,'22222222J','Pedro'    ,'Pica'     ,'Piedra'     ,'C/ Los alamos'               ,'954332211','tres'  ,30,'2009-01-01'),
(4,'25126190R','Francisco','Alegre'   ,'Y Olé'      ,'C/ Flor de Lis,23, 2-A'      ,'954001122','cuatro',40,'2009-01-01'),
(5,'33333333P','Pedro'    ,'Fernández','López'      ,'C/ Flor de Papel, 10'        ,'954889977','cinco' ,50,'2009-01-01'),
(6,'87654321X','Emilia'   ,'Gutiérrez','López'      ,'C/ Flor de Gitanilla, 5.'    ,'954332211','seis'  ,60,'2009-01-01'),
(7,'87654326S','Fernando' ,'García'   ,NULL         ,'C/ Olivares, 32.'            ,'934422121','siete' ,70,'2009-01-01'),
(8,'88888888Y','Luisa'    ,'Gutiérrez',NULL         ,'C/ Flor de Gitanilla, 5.'    ,'911234562','ocho'  ,80,'2009-01-01'),
(9,'99999999R','Pepito'   ,'Grillo'   ,'Saltamontes','C/ Flor de Esgueva, 6.'      ,'954302030','nueve' ,90,'2009-01-01');

INSERT INTO INGRESO VALUES
(2, '2009-01-01', 5.25),(2, '2009-02-02', 5.25),(2, '2009-03-03', 5.25),
(2, '2009-04-04', 5.25),(2, '2009-05-05', 5.25),(9, '2009-01-01', 5.25),
(9, '2009-02-02', 5.75),(9, '2009-03-03', 5.25),(9, '2009-04-04', 3.25),
(9, '2009-05-05', 0.75),(3, '2009-05-27', 10.00),(4, '2009-05-27', 5.00),
(5, '2009-06-05', 10.00);

INSERT INTO GENERO VALUES
(1,'Dramático'),(2,'Comedia'),(3,'Romántico'),(4,'Thriller'),(5,'Acción'),
(6,'Terror'),(7,'Musical');

INSERT INTO ACTOR_DIRECTOR VALUES 
( 1,'Cameron Díaz'),( 2,'Ben Stiller'),( 3,'Mat Dillon'),( 4,'Hermanos Farrely'),
( 5,'Geena Davis'),( 6,'Humphrey Bogart'),( 7,'Ingird Bergman'),
( 8,'Milos Forman'),( 9,'John Savage'), (10,'Treth Williams'),
(11,'Leonardo di Caprio'),(12,'Jennifer Connelly'),(13,'Bruce Willis'),
(14,'Clark Gable'),(15,'Alan Parker'),(16,'Brad Davis'),
(17,'Irene Miracle'),(18,'Bo Hopkins'),(19,'Kevin Macdonald'),
(20,'Forest Whitaker'),(21,'James McAvoy'),(22,'Kerry Washington'),
(23,'Orson Wells');   

INSERT INTO PELICULA VALUES
(1, 'Algo pasa con Mary',1995,107,'Años después de que su novia le abandonara durante su época de estudiante, Ted está dispuesto a recuperar a su antiguo antiguo amor. Lo que puede parecer un convencional argumento de comedia romántica está teñido de soeces, burdas y escatológicas situaciones para provocar escenas delirantes.', 'algoPasaConMary.jpg','2000-03-02'),
(2, 'Casablanca', 1945,117,'Historia de amor en la 2ª guerra mundial', 'casablanca.jpg','2001-01-01'),
(3, 'Hair',1978,110,'Opera rock protesta guerra Vietnam', 'hair.jpg','2003-02-02'),
(4, 'Big Fish', 2002,95, 'Historia de un padre y un Hijo', 'bigFish.jpg','2009-03-03'),
(5, 'Diamante de Sangre',2007,95,'Película protesta explotación países africanos', 'diamanteDeSangre.jpg','2007-01-01'),
(6, 'El caso Slevin',2007,99, 'Película de intriga y acción', 'elCasoSlevin.jpg','2008-01-01'),
(7, 'Lo que el viento se llevó',1945,325, 'Historia de amor', 'loQueElVientoSeLlevo.jpg','2009-01-01'),
(8, 'El expreso de medianoche',1980,125, 'Un turista americano es apresado en Turquia', 'elExpresoDeMedianoche.jpg','2008-01-01'),
(9, 'El último rey de esocia', 2007,125,'En los 70, Nicholas Garrigan, un joven doctor escocés idealista, viaja a Uganda...','elUltimoReyDeEscocia.jpg','2007-03-02'),
(10, 'Ciudadano Kane', 1945,144, 'Historia de como un niño llega a ser uno de los hombres más ricos del mundo', 'ciudadanoKane.jpg','2009-02-02');
INSERT INTO director_pelicula VALUES
( 4,1),( 6,2),( 8,3),( 8,4),( 8,5),( 8,6),(14,7),(15,8),(19,9),(23,10);
INSERT INTO actor_pelicula VALUES
( 1,1),( 2,1),( 3,1),( 6,2),( 7,2),( 9,3),(10,3),( 9,4),(10,4),(11,5),(12,5),(13,6),
(14,7),(16,8),(17,8),(18,8),(20,9),(21,9),(22,9),(23,10);
INSERT INTO genero_pelicula VALUES
(1,2),(1,3),(2,1),(2,3),(2,5),(3,1),(3,7),(4,1),(5,1),(6,5),(7,1),(8,1),(9,1),(10,1);
INSERT INTO EJEMPLAR VALUES
(1,1),(1,2),(1,3),(2,1),(2,2),(3,1),(3,2),(3,3),(3,4),(3,5),(3,6),(4,1),(4,2),
(5,1),(6,1),(6,2),(7,1),(7,2),(7,3),(7,4),(7,5),(8,1),(8,2),(8,3),(8,4),(8,5),
(9,1),(9,2),(10,1),(10,2),(10,3);
INSERT INTO TARIFA VALUES
(180,0.90),(360,1.20),(720,1.5),(1440,2.5);
INSERT INTO ALQUILER VALUES
(1,'2009-05-12 12:30',2),(2,'2009-05-12 13:30',3),(3,'2009-05-12 14:30',4),
(4,'2009-05-13 12:30',5),(5,'2009-05-13 13:30',6),(6,'2009-05-13 14:30',7),
(7,'2009-05-14 12:30',8),(8,'2009-05-14 13:30',2),(9,'2009-05-14 14:30',3);
INSERT INTO detalle_alquileres VALUES
(1,1,1,'2009-05-12 14:30',1.20),(1,2,1,'2009-05-12 19:30',1.50),
(2,3,1,'2009-05-12 15:40',0.90),
(3,4,2,'2009-05-13 12:30',2.50),(3,5,1,'2009-05-12 18:30',1.20),
(3,6,2,'2009-05-14 12:30',5.00),
(4,7,1,NULL,NULL),(4,8,5,NULL,NULL),(4,9,2,NULL,NULL),
(5,9,1,'2009-05-13 16:05',0.90),
(6,10,3,'2009-05-13 17:15',0.90),
(7,4,2,'2009-05-14 18:35',1.50),
(8,5,1,'2009-05-14 20:30',1.50),
(9,10,3,'2009-05-14 15:25',0.90),(9,7,5,NULL,NULL);


