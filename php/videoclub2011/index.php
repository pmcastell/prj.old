<?php
   session_start();
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- Fecha de Creación: 10/03/2011 -->   
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Videoclub BETA</title>
      <link href="videoclub.css" rel="stylesheet" type="text/css" />
   </head>
   <body>
     <div class="cabecera">
        <h1>Videoclub BETA</h1>
     </div>
     <div class="izda">
        <!-- Aquí va el menú -->
        <ul>
           <li><a href="?op=1">Matenimineto de Clientes</a></li>
           <li><a href="?op=2">Mantenimiento de Películas</a></li>
           <li><a href="?op=3">Catálago</a></li>
           <li><a href="?op=4">Mantenimiento de Proveedores</a></li>
           <li><a href="?op=5">Identificarse</a></li>
           
        </ul>
     </div>
     <div class="contenido">
        <!--  aquí mostraremos lo que nos pida el usuario -->
        <?php
           if (!isset($_GET['op'])) {
              echo "Bievenido al Vídeoclub BETA.";
           } else {
              $op=$_GET['op'];
              if ($op==1) {
                 include_once("mantenimientoCli.php");
              } elseif ($op==2) {
                 include_once("mantenimientoPeli.php");
              } elseif ($op==3) {
                 include_once("catalago.php");
              } elseif ($op==4) {
                 include_once("mantenimientoProvee.php");
              } elseif ($op==5) {
                 include_once("ident.php");
              } else {
                 echo "No existe la opcion $op";
              }
           }
        ?>
     </div>
     <div class="pie">
        <h1>Videoclub BETA</h1>
     </div>
   </body>
</html>