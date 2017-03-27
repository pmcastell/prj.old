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

CREATE TABLE ACTOR_DIRECTOR (
   id INT UNSIGNED AUTO_INCREMENT NOT NULL,
   nombre VARCHAR(35) NOT NULL UNIQUE,
   PRIMARY KEY(id)
) ENGINE=INNODB;
   
CREATE TABLE PELICULA (
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
   id_PELICULA INT UNSIGNED NOT NULL,
   num TINYINT UNSIGNED NOT NULL,
   PRIMARY KEY(id_PELICULA,num),
   FOREIGN KEY(id_PELICULA) REFERENCES PELICULA(id) ON UPDATE CASCADE
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
   FOREIGN KEY(id_EJEMPLAR,num_EJEMPLAR) REFERENCES EJEMPLAR(id_PELICULA,num) ON UPDATE CASCADE
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
   FOREIGN KEY(id_EJEMPLAR,num_EJEMPLAR) REFERENCES EJEMPLAR(id_PELICULA,num) ON UPDATE CASCADE
) ENGINE=INNODB;

CREATE TABLE director_pelicula (
   id_ACTOR_DIRECTOR INT UNSIGNED NOT NULL,
   id_PELICULA INT UNSIGNED NOT NULL,
   PRIMARY KEY(id_ACTOR_DIRECTOR,id_PELICULA),
   FOREIGN KEY(id_ACTOR_DIRECTOR) REFERENCES ACTOR_DIRECTOR(id) ON UPDATE CASCADE,
   FOREIGN KEY(id_PELICULA) REFERENCES PELICULA(id) ON UPDATE CASCADE
) ENGINE=INNODB;

CREATE TABLE actor_pelicula (
   id_ACTOR_DIRECTOR INT UNSIGNED NOT NULL,
   id_PELICULA INT UNSIGNED NOT NULL,
   PRIMARY KEY(id_ACTOR_DIRECTOR,id_PELICULA),
   FOREIGN KEY(id_ACTOR_DIRECTOR) REFERENCES ACTOR_DIRECTOR(id) ON UPDATE CASCADE,
   FOREIGN KEY(id_PELICULA) REFERENCES PELICULA(id) ON UPDATE CASCADE
) ENGINE=INNODB;   

CREATE TABLE genero_pelicula (
   id_PELICULA INT UNSIGNED NOT NULL,
   id_GENERO INT UNSIGNED NOT NULL,
   PRIMARY KEY(id_PELICULA,id_GENERO),
   FOREIGN KEY(id_PELICULA) REFERENCES PELICULA(id) ON UPDATE CASCADE,
   FOREIGN KEY(id_GENERO) REFERENCES GENERO(id) ON UPDATE CASCADE
) ENGINE=INNODB;      

INSERT INTO `pelicula` VALUES (1, 'Gran torino', 2008, 116, 'Walt Kowalski, un trabajador del sector del automovil jubilado, ocupa su tiempo con reparaciones domesticas, cerveza y visitas mensuales al peluquero. Aunque el ultimo deseo de su difunta esposa fue que se confesara, para Walt', 'gran_torino.jpg', '2009/12/03');
INSERT INTO `pelicula` VALUES (2, 'Underworld: La rebelion de los licantropos', 2009, 92, 'Underworld: La rebelion de los licantropos, precuela de Underworld y Underword evolution, cuenta los origenes de la guerra entre vampiros y licantropos.', 'underworld.jpg', '2009/10/03');
INSERT INTO `pelicula` VALUES (3, 'Mas alla de los suenios', 2008, 99, 'La vida de Skeeter va a cambiar para siempre cuando las historias que les cuenta a sus sobrinos antes de dormir se convierten misteriosamente en realidad al dia siguiente.', 'mas_alla.jpg', '2009/12/03');
INSERT INTO `pelicula` VALUES (4, 'Slumdog millionaire', 2008, 120, 'Jamal Malik, un joven huerfano que vive en una barriada pobre de Bombay, decide presentarse a la version india del concurso ¿Quien quiere ser millonario?.', 'slumdog.jpg', '2009/05/03');
INSERT INTO `pelicula` VALUES (5, 'Watchmen', 2009, 163, 'Watchmen esta ambientada en 1985, en unos Estados Unidos alternativos donde los superheroes disfrazados son parte del tejido de la sociedad cotidiana, y el Reloj del Fin del Mundo marca permanentemente las doce menos cinco de la noche.', 'watchmen.jpg', '2009/01/03');
INSERT INTO `pelicula` VALUES (6, 'The code', 2008, 104,  'Gabriel es un ladron que ha tenido muy poca suerte en la vida. Su situacion cambia cuando se encuentra por casualidad con Ripley, un ladron de la vieja escuela que le escoge como companiero para su proximo gran golpe.', 'the_code.jpg', '2009/06/03');
INSERT INTO `pelicula` VALUES (7, 'The reader', 2008, 124, 'La historia comienza en la Alemania despues de la Segunda Guerra Mundial. Volviendo del colegio, el adolescente Michael Berg se siente de pronto enfermo y Hanna.', 'the_reader.jpg', '2009/05/03');
INSERT INTO `pelicula` VALUES (8, 'El curioso caso de Benjamin Button', 2008, 166, 'Benjamin Button es un hombre que nace con ochenta anios y va rejuveneciendo con el tiempo. Desde la Nueva Orleans de finales de la Primera Guerra Mundial hasta el siglo XXI, en un viaje tan inusual como la vida de cualquier ser humano, esta pelicula cuenta la gran historia de un hombre no tan ordinario y la gente que va conociendo por el camino.', 'button.jpg', '2009/03/03');
INSERT INTO `pelicula` VALUES (9, 'La Pantera Rosa 2', 2009, 92, 'En esta secuela de La Pantera Rosa, Steve Martin repite en su papel del intrepido pero torpe detective de policia frances, el inspector Jacques Clouseau.', 'rosa_2.jpg', '2009/08/03');

INSERT INTO `cliente` VALUES (1, 01234567, 'Admin','Admin', 'Admin', 'C/ Cueva de la pileta 2 Blq 1, 7A', 615419850, 'root', 0, '2009/08/04');
INSERT INTO `cliente` VALUES (2, 28768651, 'Angel','Bejarano', 'Moreno', 'C/ Cueva de la pileta 2 Blq 1, 7A', 615419850, 123456, 20, '2009/08/04');
INSERT INTO `cliente` VALUES (3, 65123478, 'Cristina','Lopez', 'Mesa', 'C/ Manila n9 Blq 1 7B', 635945270, 3256, 15, '2009/02/11');
INSERT INTO `cliente` VALUES (4, 28645147, 'Jose Luis','Bejarano', 'Moreno', 'C/ Cueva de la pileta 2 Blq 1, 7A', 602302060, 154200, 30, '2009/05/01');
INSERT INTO `cliente` VALUES (5, 28965896, 'Pablo','Bejarano', 'Moreno', 'C/ Cueva de la pileta 2 Blq 1, 7A', 630159815, 1234, 12, '2009/03/05');
INSERT INTO `cliente` VALUES (6, 56321896, 'Rosario','Moreno', 'Del Castillo', 'C/ Cueva de la pileta 2 Blq 1, 7A', 641258963, 00001, 32, '2009/08/06');
INSERT INTO `cliente` VALUES (7, 28745632, 'Angel', 'Bejarano', 'Chiara', 'C/ Cueva de la pileta 2 Blq 1, 7A', 954518654, 1111, 5, '2009/04/12');
INSERT INTO `cliente` VALUES (8, 23159487, 'Juanma','Gonzalez', 'Romos', 'C/ Cueva de la pileta 2 Blq 4, 9B', 954672760, 965489, 14, '2009/10/01');
INSERT INTO `cliente` VALUES (9, 25123456, 'Antonio Jose','Avila', 'Sanchez', 'C/ Cueva de la pileta 2 Blq 12, 2A', 954671879, 55550, 6, '2009/06/07');
INSERT INTO `cliente` VALUES (10, 65789147, 'Diego','Valencia', 'Gonzalez', 'C/ Cueva de la pileta 2 Blq 5, 3A', 954518591, 1485, 3, '2009/07/09');

INSERT INTO `actor_director` VALUES (1, 'Steven Spielberg');
INSERT INTO `actor_director` VALUES (2, 'Peter Jackson');
INSERT INTO `actor_director` VALUES (3, 'Martin Scorsese');
INSERT INTO `actor_director` VALUES (4, 'Christopher Nolan');
INSERT INTO `actor_director` VALUES (5, 'Steven Soderbergh');
INSERT INTO `actor_director` VALUES (6, 'Quentin Tarantino');
INSERT INTO `actor_director` VALUES (7, 'Michael Mann');
INSERT INTO `actor_director` VALUES (8, 'James Cameron');

INSERT INTO `genero` VALUES (1, 'Accion');
INSERT INTO `genero` VALUES (2, 'Terror');
INSERT INTO `genero` VALUES (3, 'Drama');
INSERT INTO `genero` VALUES (4, 'Humor');
INSERT INTO `genero` VALUES (5, 'Animacion');
INSERT INTO `genero` VALUES (6, 'Belica');
INSERT INTO `genero` VALUES (7, 'Thriller');
INSERT INTO `genero` VALUES (8, 'Ficcion');

INSERT INTO `tarifa` VALUES (120, 1.00);
INSERT INTO `tarifa` VALUES (180, 1.75);
INSERT INTO `tarifa` VALUES (360, 2.50);
INSERT INTO `tarifa` VALUES (540, 3.25);
INSERT INTO `tarifa` VALUES (720, 4.00);
INSERT INTO `tarifa` VALUES (1440, 5.00);
