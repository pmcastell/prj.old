DROP DATABASE IF EXISTS BD_CAMPEONATOS;
CREATE DATABASE BD_CAMPEONATOS  DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
GRANT ALL PRIVILEGES ON BD_CAMPEONATOS.* TO BD_CAMPEONATOS@localhost IDENTIFIED BY 'pepe';

use BD_CAMPEONATOS;

CREATE TABLE PAIS (
   id INT UNSIGNED AUTO_INCREMENT,
   nombre VARCHAR(30) NOT NULL UNIQUE,
   num_clubes TINYINT UNSIGNED NOT NULL,
   es_representado_PAIS INT UNSIGNED,
   PRIMARY KEY(id),
   FOREIGN KEY(es_representado_PAIS) REFERENCES PAIS(id) ON UPDATE CASCADE
) ENGINE=INNODB;

CREATE TABLE PARTICIPANTE (
   num_asoc INT UNSIGNED AUTO_INCREMENT,
   nombre VARCHAR(20) NOT NULL,
   apellido1 VARCHAR(20) NOT NULL,
   apellido2 VARCHAR(20),
   dir VARCHAR(50) NOT NULL,
   telef CHAR(12),
   id_PAIS INT UNSIGNED NOT NULL,
   PRIMARY KEY(num_asoc),
   FOREIGN KEY(id_PAIS) REFERENCES PAIS(id) ON UPDATE CASCADE
) ENGINE=INNODB;

CREATE TABLE CAMPEONATO (
   id INT UNSIGNED AUTO_INCREMENT,
   fecha DATE NOT NULL,
   lugar VARCHAR(30) NOT NULL,
   PRIMARY KEY(id)
) ENGINE=INNODB;

CREATE TABLE HOTEL (
   nombre VARCHAR(30),
   dir VARCHAR(50) NOT NULL,
   telef CHAR(12) NOT NULL,
   PRIMARY KEY(nombre)
) ENGINE=INNODB;

CREATE TABLE SALA ( 
   nombre_HOTEL VARCHAR(30),
   num TINYINT UNSIGNED,
   capacidad SMALLINT UNSIGNED NOT NULL,
   PRIMARY KEY(nombre_HOTEL,num),
   FOREIGN KEY(nombre_HOTEL) REFERENCES HOTEL(nombre) ON UPDATE CASCADE
) ENGINE=INNODB;

CREATE TABLE PARTIDA (
   id_CAMPEONATO INT UNSIGNED,
   cod_p SMALLINT UNSIGNED,
   fecha DATE,
   jug_blanca_PARTICIPANTE INT UNSIGNED NOT NULL,
   jug_negra_PARTICIPANTE INT UNSIGNED NOT NULL,
   arbitro_PARTICIPANTE INT UNSIGNED NOT NULL,
   nombre_SALA VARCHAR(30) NOT NULL,
   num_SALA TINYINT UNSIGNED NOT NULL,
   n_entradas SMALLINT UNSIGNED NOT NULL,
   PRIMARY KEY(id_CAMPEONATO,cod_p),
   FOREIGN KEY(jug_blanca_PARTICIPANTE) REFERENCES PARTICIPANTE(num_asoc) ON UPDATE CASCADE,
   FOREIGN KEY(jug_negra_PARTICIPANTE) REFERENCES PARTICIPANTE(num_asoc) ON UPDATE CASCADE,
   FOREIGN KEY(arbitro_PARTICIPANTE) REFERENCES PARTICIPANTE(num_asoc) ON UPDATE CASCADE,
   FOREIGN KEY(nombre_SALA,num_SALA) REFERENCES SALA(nombre_HOTEL,num) ON UPDATE CASCADE
) ENGINE=INNODB;

CREATE TABLE MOVIMIENTO (
   id_PARTIDA INT UNSIGNED,
   cod_p_PARTIDA SMALLINT UNSIGNED,
   num SMALLINT UNSIGNED,
   jugada CHAR(5) NOT NULL,
   comentario VARCHAR(200),
   PRIMARY KEY(id_PARTIDA,cod_p_PARTIDA,num),
   FOREIGN KEY(id_PARTIDA,cod_p_PARTIDA) REFERENCES PARTIDA(id_CAMPEONATO,cod_p) ON UPDATE CASCADE
) ENGINE=INNODB;

CREATE TABLE MEDIO (
   id TINYINT UNSIGNED AUTO_INCREMENT,
   descripcion VARCHAR(20),
   PRIMARY KEY(id)
) ENGINE=INNODB;

CREATE TABLE participante_campeonato(
   num_asoc_PARTICIPANTE INT UNSIGNED,
   id_CAMPEONATO INT UNSIGNED,
   tipo enum('jugardor','arbitro') NOT NULL,
   PRIMARY KEY(num_asoc_PARTICIPANTE,id_CAMPEONATO),
   FOREIGN KEY(num_asoc_PARTICIPANTE) REFERENCES PARTICIPANTE(num_asoc) ON UPDATE CASCADE,
   FOREIGN KEY(id_CAMPEONATO) REFERENCES CAMPEONATO(id) ON UPDATE CASCADE
) ENGINE=INNODB;

CREATE TABLE sala_medio (
   nombre_SALA VARChAR(30),
   num_SALA TINYINT UNSIGNED,
   id_MEDIO TINYINT UNSIGNED,
   PRIMARY KEY(nombre_SALA,num_SALA,id_MEDIO),
   FOREIGN KEY(nombre_SALA,num_SALA) REFERENCES SALA(nombre_HOTEL,num) ON UPDATE CASCADE,
   FOREIGN KEY(id_MEDIO) REFERENCES MEDIO(id) ON UPDATE CASCADE
) ENGINE=INNODB;

CREATE TABLE alojamiento (
   num_asoc_PARTICIPANTE INT UNSIGNED,
   nombre_HOTEL VARCHAR(30),
   fecha_entrada DATE,
   fecha_salida DATE NOT NULL,
   PRIMARY KEY(num_asoc_PARTICIPANTE,nombre_HOTEL,fecha_entrada),
   FOREIGN KEY(num_asoc_PARTICIPANTE) REFERENCES PARTICIPANTE(num_asoc) ON UPDATE CASCADE,
   FOREIGN KEY(nombre_HOTEL) REFERENCES HOTEL(nombre) ON UPDATE CASCADE
) ENGINE=INNODB;

   
      

   
   
