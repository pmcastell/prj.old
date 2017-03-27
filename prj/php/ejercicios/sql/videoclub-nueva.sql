DROP DATABASE IF EXISTS BD_VIDEOCLUB;
CREATE DATABASE BD_VIDEOCLUB  DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
GRANT ALL PRIVILEGES ON BD_VIDEOCLUB.* TO BD_VIDEOCLUB@localhost IDENTIFIED BY 'video2009';

use BD_VIDEOCLUB;

CREATE TABLE CLIENTE (
   nif char(9),
   nombre varchar(20) not null,
   apellido1 varchar(20) not null,
   apellido2 varchar(20),
   dir varchar(50),
   telef char(9) not null,
   PRIMARY KEY(nif)
) ENGINE=INNODB;

CREATE TABLE PELICULA (
   id int unsigned AUTO_INCREMENT,
   titulo varchar(35) not null,
   director varchar(20) not null,
   anio int(4) not null,
   duracion int(3) not null,
   genero varchar(30) not null,
   sinopsis varchar(500) not null,
   PRIMARY KEY(id)
) ENGINE=INNODB;
   
CREATE TABLE EJEMPLAR (
   id_PELICULA int unsigned,
   n_ejemplar tinyint unsigned,
   disponible bit,
   PRIMARY KEY(id_PELICULA,n_ejemplar),
   FOREIGN KEY(id_PELICULA) references PELICULA(id) ON UPDATE CASCADE
) ENGINE=INNODB;

CREATE TABLE BONO(
   id tinyint unsigned AUTO_INCREMENT,
   tipo char(8),
   duracion smallint unsigned,
   precio real,
   PRIMARY KEY(id)
)ENGINE=INNODB;

CREATE TABLE PROVEEDOR (
   nif char(9),
   nombre varchar(30),
   dir varchar(35),
   telef char(9),
   PRIMARY KEY(nif)
)ENGINE=INNODB;

CREATE TABLE PEDIDO (
   id int unsigned AUTO_INCREMENT,
   fecha DATE,
   nif_PROVEEDOR char(9),
   PRIMARY KEY(id),
   FOREIGN KEY(nif_PROVEEDOR) references PROVEEDOR(nif) ON UPDATE CASCADE 
)ENGINE=INNODB;

CREATE TABLE FACTURA (
   id INT UNSIGNED AUTO_INCREMENT,
   fecha DATE,
   id_PEDIDO INT UNSIGNED,
   PRIMARY KEY(id),
   FOREIGN KEY(id_PEDIDO) REFERENCES PEDIDO(id) ON UPDATE CASCADE
)ENGINE=INNODB;

CREATE TABLE PAGO (
   id_FACTURA INT UNSIGNED,
   num_pago TINYINT UNSIGNED,
   cantidad INT UNSIGNED,   
   PRIMARY KEY(id_FACTURA,num_pago),
   FOREIGN KEY(id_FACTURA) references FACTURA(id) ON UPDATE CASCADE 
)ENGINE=INNODB;

CREATE TABLE ENTREGA (
   id_PEDIDO INT UNSIGNED,
   num_entrega TINYINT UNSIGNED,
   fecha_entrega DATE,
   PRIMARY KEY(id_PEDIDO,num_entrega),
   FOREIGN KEY(id_PEDIDO) references PEDIDO() ON UPDATE CASCADE 
)ENGINE=INNODB;



CREATE TABLE (
   PRIMARY KEY(),
   FOREIGN KEY() references () ON UPDATE CASCADE 
)ENGINE=INNODB;



