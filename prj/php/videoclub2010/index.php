<?php
   //session_destroy();
   session_start(); 
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Bienvenido a la página del VídeoClub Alfa </title>
      <link rel="stylesheet" type="text/css" href="estilo/mio.css" /> 
   </head>
   <body>
      <div class="ancho">
      	<big>Video Club ALFA</big>
      </div>
      <div class="ancho">
      	<?php
      	   include_once("menus.php");
      	   include_once("funcionesAuxiliares.php");
      	   if (!isset($_SESSION['tipoUsuario'])) {
      	      menuPrincipalBasico();
      	   } else {
      	      if ($_SESSION['tipoUsuario']=="Admin") {
      	         menuPrincipalAdmin();
      	      } else {
      	         menuPrincipalUsuario();
      	      }
      	   }           
      	?>
      </div>
      <div class="menu">
      	<?php 
      	   if (!isset($_SESSION['tipoUsuario'])) {
      	      menuSecundarioBasico();
      	   } else {
      	      if ($_SESSION['tipoUsuario']=="Admin") {
      	         menuSecundarioAdmin();
      	      } else {
      	         menuSecundarioUsuario();
      	      }
      	   }
      	?>
      </div>
      <div class="contenido">
      	<big>Contenido:</big>
      	<?php
      	   if (!isset($_GET['opcion'])) {
      	      echo '<big>Catálogo</big>';
      	      listado("SELECT * FROM PELICULAS");
      	   } else {
      	      echo '<big>',$_GET['opcion'],'</big>';
      	      include_once($_GET['opcion'].".php");
      	   }
      	?>
      </div>

   </body>
</html>
