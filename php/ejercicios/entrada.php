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
         if ($_POST) {
         //isset($_POST['usuario']) && isset($_POST['clave1']) && isset($_POST['clave2'])) {
            $usuario=$_POST['usuario'];
            $clave1=$_POST['clave1'];
            $clave2=$_POST['clave2'];
            if ($clave1!=$clave2) {
               echo "Las claves introducidas no coinciden vuelve a intentarlo.";
               session_destroy();
               unset($_SESION['tipoUsuario']);
               $a=5;
               unset($a);
               
            } else {
               if ($usuario=="Admin" && $clave1=="admin") {
                  $_SESSION['tipoUsuario']="admin";
               } else {
                  echo 'Error: nombre de usuario o contraseña incorrectos.';
               }
            }
         } 
      ?>
   </body>
</html>