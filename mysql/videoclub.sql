SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
CREATE SCHEMA IF NOT EXISTS `videoclub` ;
USE `videoclub`;

-- -----------------------------------------------------
-- Table `videoclub`.`peliculas`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `videoclub`.`peliculas` (
  `idpeliculas` INT NOT NULL AUTO_INCREMENT ,
  `titulo` VARCHAR(45) NOT NULL ,
  `año` INT NULL ,
  `director` VARCHAR(45) NULL ,
  PRIMARY KEY (`idpeliculas`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `videoclub`.`clientes`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `videoclub`.`clientes` (
  `dni` CHAR(9) NOT NULL ,
  `nombre` VARCHAR(35) NULL ,
  `apellidos` VARCHAR(50) NULL ,
  `dir` VARCHAR(50) NULL ,
  `peliculas_idpeliculas` INT NULL ,
  PRIMARY KEY (`dni`) ,
  INDEX `fk_clientes_peliculas` (`peliculas_idpeliculas` ASC) ,
  CONSTRAINT `fk_clientes_peliculas`
    FOREIGN KEY (`peliculas_idpeliculas` )
    REFERENCES `videoclub`.`peliculas` (`idpeliculas` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `videoclub`.`ejemplares`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `videoclub`.`ejemplares` (
  `peliculas_idpeliculas` INT NOT NULL ,
  `idejemplar` INT NOT NULL AUTO_INCREMENT ,
  PRIMARY KEY (`idejemplar`, `peliculas_idpeliculas`) ,
  INDEX `fk_ejemplares_peliculas` (`peliculas_idpeliculas` ASC) ,
  CONSTRAINT `fk_ejemplares_peliculas`
    FOREIGN KEY (`peliculas_idpeliculas` )
    REFERENCES `videoclub`.`peliculas` (`idpeliculas` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
