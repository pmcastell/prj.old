<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web </title>
   </head>
   <body>
      <?php
      	include_once("funcionesAuxiliares.php");
      	$nombres=$_POST['nombres'];
      	$alturas=$_POST['alturas'];
      	for($i=0;$i<count($nombres);$i++) {
      		$alumnos[$nombres[$i]]=$alturas[$i];
      	}
      	asort($alumnos);
      	imrpimeMatrizAsociativa($alumnos);
      ?>
   </body>
</html>