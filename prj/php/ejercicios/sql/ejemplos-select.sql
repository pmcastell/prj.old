DROP DATABASE IF EXISTS BD_EJEMPLOS;
CREATE DATABASE BD_EJEMPLOS  DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
#GRANT ALL PRIVILEGES ON BD_EJEMPLOS.* TO BD_EJEMPLOS@localhost IDENTIFIED BY 'video2009';

use BD_EJEMPLOS;
CREATE TABLE DEPARTAMENTO (
   id int(3),
   nombDpto varchar(30),
   primary key(id)
) engine=innodb;

CREATE TABLE EMPLEADO (
   apellido varchar(20),
   id_DEPARTAMENTO int(3),
   PRIMARY KEY(apellido)
   #,FOREIGN KEY(id_DEPARTAMENTO) REFERENCES Departamento(id)
) engine=innodb;

INSERT INTO DEPARTAMENTO VALUES(31,'Ventas');
INSERT INTO DEPARTAMENTO VALUES(33,'Ingeniería');
INSERT INTO DEPARTAMENTO VALUES(34,'Producción');
INSERT INTO DEPARTAMENTO VALUES(35,'Marketing');


INSERT INTO EMPLEADO VALUES('Rafferty',31);
INSERT INTO EMPLEADO VALUES('Jordán',33);
INSERT INTO EMPLEADO VALUES('Steinberg',33);
INSERT INTO EMPLEADO VALUES('Róbinson',34);
INSERT INTO EMPLEADO VALUES('Smith',34);
INSERT INTO EMPLEADO VALUES('Gaspar',36);





