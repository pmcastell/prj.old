<?php
   session_start(); 
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web </title>
   </head>
   <body>
   	<?php 
         if (isset($_SESSION['tipoUsuario']) && $_SESSION['tipoUsuario']=='admin') {
            //mostrar el formulario de alta de clientes
            echo 'Estás autorizado';
         } else {
            echo "No estás autorizado a ver esta página. <br />";
            echo "Los accesos a esta página están registrados.<br />";
            echo "Tu dirección I.P.: ",$_SERVER['REMOTE_ADDR'],'<br />';
            echo "Tu Navegtador es: ",$_SERVER['HTTP_USER_AGENT'];
            
         }
      ?>
   </body>
</html>