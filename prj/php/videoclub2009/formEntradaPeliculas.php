<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Alta de Pel&iacute;culas</title>
   </head>
   <body>
      <?php
      	include_once("funcionesAuxiliares.php");
      	date_default_timezone_set("Europe/Madrid");
      	if (!$_POST) {
      ?>
      <form id="altaPeliculas" action="?" method="post">
      	<table border="0">
      		<tr><td><label for="titulo">Título</label></td>
      		<td><input type="text" name="titulo" id="titulo" size="35" /></td></tr>
      		<tr><td><label for="anio">A&ntilde;o:</label></td>
      		<td><?php escribeListaValores("anio",date("Y"),1900,date("Y"),-1); ?></td></tr>
      		<tr><td>Duraci&oacute;n:</td>
      		<td><input type="text" size="4" name="duracion" id="duracion" /></td></tr>
      		<tr><td>Sinopsis:</td>
      		<td><textarea name="sinopsis" rows="8" cols="80"></textarea></td></tr>
      		<tr><td>Fichero de car&aacute;tula:</td>
      		<td><input type="text" size="50" name="caratula" id="caratula" /></td></tr>
      		<tr><td>Fecha de Adquisici&oacute;n:</td>
      		<td><?php escribeFecha(date("d-m-Y"),"diaFechaAdq","mesFechaAd","anioFechaAd");?></td></tr>
      		<tr><td>Actores:</td>
      		<td><?php listaMultiple("SELECT * FROM ACTOR_DIRECTOR ORDER BY nombre","actores",array(array('id',10),array('nombre',35)),3);?></td></tr>
      		<tr><td>Directores:</td>
      		<td><?php listaMultiple("SELECT * FROM ACTOR_DIRECTOR ORDER BY nombre","directores",array(array('id',10),array('nombre',35)),3);?></td></tr>
      		<tr><td>G&eacute;neros:</td>
      		<td><?php listaMultiple("SELECT * FROM GENERO ORDER BY nombre","generos",array(array('id',10),array('nombre',20)),3);?></td></tr>
      		<tr><td><input type="submit" value="Grabar" /></td><td><input type="reset" value="Resetear Formulario" /></td></tr>
      	</table>
      </form>
      <?php 
      	} else {
      		$titulo=$_POST['titulo']; $anio=$_POST['anio']; $duracion=$_POST['duracion']; $sinopsis=$_POST['sinopsis'];
      		$caratula=$_POST['caratula']; $actores=$_POST['actores']; $directores=$_POST['directores']; $generos=$_POST['generos'];
      		if (esCadenaVacia($titulo)) {
      			error("Error: El campo título no puede estar en blanco.");
      			volver();
      		} elseif (esCadenaVacia($duracion) || !is_numeric($duracion)) {
      			error("Error: en la duración de la película.");
      			volver();
      		} else {
					
      		}
      		//fconsulta("INSERT INTO PELICULAS VALUES(".$_POST[]
      	}  
      ?>
   </body>
</html>