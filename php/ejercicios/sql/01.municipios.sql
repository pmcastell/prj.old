DROP DATABASE IF EXISTS BD_MUNICIPIOS;
CREATE DATABASE BD_MUNICIPIOS  DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
GRANT ALL PRIVILEGES ON BD_MUNICIPIOS.* TO BD_MUNICIPIOS@localhost IDENTIFIED BY 'pepe';

use BD_MUNICIPIOS;

CREATE TABLE MUNICIPIO (
   id int(5),
   nombre varchar(35) unique not null, #no puede haber 2 municipios con el mismo nombre
   provincia varchar(20) unique not null, #idem para las provincias
   primary key(id)
) engine=innodb;

CREATE TABLE VIVIENDAS (
   cod_catastral int(16),
   calle varchar(35) not null,
   num int(4) not null,
   piso char(4),
   cod_postal int(5) not null,
   id_MUNICIPIO int(5) not null,
   primary key(cod_catastral),
   foreign key(id_MUNICIPIO) references MUNICIPIO(id) ON UPDATE CASCADE 
)engine=innodb;
   

CREATE TABLE PERSONAS (
   nif char(9),
   nombre varchar(20) not null,
   apellido1 varchar(15) not null,
   apellido2 varchar(15),
   habita_VIVIENDAS  int(16), # relación habita
   cabezaf_PERSONAS char(9), # relación reflexiva cabeza_familia
   primary key(nif),
   foreign key(habita_viviendas) references VIVIENDAS(cod_catastral) ON UPDATE CASCADE,
   foreign key(cabezaf_PERSONAS) references PERSONAS(nif) ON UPDATE CASCADE
) engine=innodb;

CREATE TABLE posee (
   cod_catastral_VIVIENDAS int(16),
   nif_PERSONAS char(9),
   primary key(cod_catastral_VIVIENDAS,nif_PERSONAS),
   foreign key(cod_catastral_VIVIENDAS) references VIVIENDAS(cod_catastral) ON UPDATE CASCADE,
   foreign key(nif_PERSONAS) references PERSONAS(nif) ON UPDATE CASCADE
) engine=innodb;


