SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `BD_MUNICIPIOS` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci ;
USE `BD_MUNICIPIOS`;

-- -----------------------------------------------------
-- Table `BD_MUNICIPIOS`.`MUNICIPIO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BD_MUNICIPIOS`.`MUNICIPIO` ;

CREATE  TABLE IF NOT EXISTS `BD_MUNICIPIOS`.`MUNICIPIO` (
  `id` INT(5) NULL DEFAULT NULL ,
  `nombre` VARCHAR(35) NOT NULL ,
  `provincia` VARCHAR(20) NOT NULL ,
  UNIQUE INDEX (`nombre` ASC) ,
  UNIQUE INDEX (`provincia` ASC) ,
  PRIMARY KEY (`id`) )
ENGINE = innodb;


-- -----------------------------------------------------
-- Table `BD_MUNICIPIOS`.`VIVIENDAS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BD_MUNICIPIOS`.`VIVIENDAS` ;

CREATE  TABLE IF NOT EXISTS `BD_MUNICIPIOS`.`VIVIENDAS` (
  `cod_catastral` INT(16) NULL DEFAULT NULL ,
  `calle` VARCHAR(35) NOT NULL ,
  `num` INT(4) NOT NULL ,
  `piso` CHAR(4) NULL DEFAULT NULL ,
  `cod_postal` INT(5) NOT NULL ,
  `id_MUNICIPIO` INT(5) NOT NULL ,
  PRIMARY KEY (`cod_catastral`) ,
  INDEX `fk_87de2820-fc51-11dd-b5fb-000d88c99489` (`id_MUNICIPIO` ASC) ,
  CONSTRAINT `fk_87de2820-fc51-11dd-b5fb-000d88c99489`
    FOREIGN KEY (`id_MUNICIPIO` )
    REFERENCES `BD_MUNICIPIOS`.`MUNICIPIO` (`id` ))
ENGINE = innodb;


-- -----------------------------------------------------
-- Table `BD_MUNICIPIOS`.`PERSONAS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BD_MUNICIPIOS`.`PERSONAS` ;

CREATE  TABLE IF NOT EXISTS `BD_MUNICIPIOS`.`PERSONAS` (
  `nif` CHAR(9) NULL DEFAULT NULL ,
  `nombre` VARCHAR(20) NOT NULL ,
  `apellido1` VARCHAR(15) NOT NULL ,
  `apellido2` VARCHAR(15) NULL DEFAULT NULL ,
  `habita_VIVIENDAS` INT(16) NULL DEFAULT NULL ,
  `cabezaf_PERSONAS` CHAR(9) NULL DEFAULT NULL ,
  PRIMARY KEY (`nif`) ,
  INDEX `fk_87de282c-fc51-11dd-b5fb-000d88c99489` (`habita_VIVIENDAS` ASC) ,
  INDEX `fk_87de282e-fc51-11dd-b5fb-000d88c99489` (`cabezaf_PERSONAS` ASC) ,
  CONSTRAINT `fk_87de282c-fc51-11dd-b5fb-000d88c99489`
    FOREIGN KEY (`habita_VIVIENDAS` )
    REFERENCES `BD_MUNICIPIOS`.`VIVIENDAS` (`cod_catastral` ),
  CONSTRAINT `fk_87de282e-fc51-11dd-b5fb-000d88c99489`
    FOREIGN KEY (`cabezaf_PERSONAS` )
    REFERENCES `BD_MUNICIPIOS`.`PERSONAS` (`nif` ))
ENGINE = innodb;


-- -----------------------------------------------------
-- Table `BD_MUNICIPIOS`.`posee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BD_MUNICIPIOS`.`posee` ;

CREATE  TABLE IF NOT EXISTS `BD_MUNICIPIOS`.`posee` (
  `cod_catastral_VIVIENDAS` INT(16) NULL DEFAULT NULL ,
  `nif_PERSONAS` CHAR(9) NULL DEFAULT NULL ,
  PRIMARY KEY (`cod_catastral_VIVIENDAS`, `nif_PERSONAS`) ,
  INDEX `fk_87de2839-fc51-11dd-b5fb-000d88c99489` (`nif_PERSONAS` ASC) ,
  CONSTRAINT `fk_87de2837-fc51-11dd-b5fb-000d88c99489`
    FOREIGN KEY (`cod_catastral_VIVIENDAS` )
    REFERENCES `BD_MUNICIPIOS`.`VIVIENDAS` (`cod_catastral` ),
  CONSTRAINT `fk_87de2839-fc51-11dd-b5fb-000d88c99489`
    FOREIGN KEY (`nif_PERSONAS` )
    REFERENCES `BD_MUNICIPIOS`.`PERSONAS` (`nif` ))
ENGINE = innodb;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
