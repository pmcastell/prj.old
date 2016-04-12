<?php  session_start(); ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>V&iacute;deo Club BETA</title>
      <link href="css/mio.css" rel="stylesheet" type="text/css" />
   </head>
   <body>
   	<div class="menu">
   		<?php 
   			include_once("funcionesAuxiliares.php");
   			if (isset($_SESSION['tipoUsuario'])) {
   				if ($_SESSION['tipoUsuario']==0) { // admin
   					$opciones=array("altas","modificacion","salir");
   					$leyendas=array("Altas","Modificaciones","Salir");
   					menu($opciones,"opcion",$leyendas);
   					//include_once("menu_admin.php");
   				} elseif ($_SESSION['tipoUsuario']==1) { // usuario reg.
   					menu(array("catalalogo","datosPersonales","salir"),"opcion",array("Cat&aacute;logo","Datos Personales","Salir"));
   					//include_once("menu_usu_reg.php");
   				}
   			} else {
   				menu(array("catalago","identificarse"),"opcion",array("Cat&aacute;logo","Identificarse"));
   				//include_once("menu.php");
   			}
   		?>
   	</div>
   	<div class="derecha">
   		<?php
   			if (isset($_GET['opcion'])) {
   				$op=$_GET['opcion'];
   				if ($op=="catalago") {
   					
   				} elseif ($op=="identificarse") {
   					include_once("ident.php");
   				} elseif ($op=="salir") {
   					session_destroy();
   					recargarPagina();
   				}
   			} 
   		?>
   	
   	</div>
   </body>
</html>