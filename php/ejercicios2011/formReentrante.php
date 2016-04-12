<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- Fecha de Creación: 24/02/2011 -->   
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web</title>
   </head>
   <body>
      <?php
         if ($_POST) {
            //Procesar los datos recibidos del formulario
            echo "Nombre de usuario enviado: ",$_POST['usuario'],"<br />";
            echo "Contraseña enviada: ",$_POST['pass'],"<br />";
            
         } else {
      ?>            
        <form method="post" action="?">
           <label for="usuario">Nombre: </label>
           <input type="text" name="usuario" id="usuario" maxlength="10" /><br />
           <label for="pass">Contrase&ntilde;a:</label>
           <input type="password" name="pass" id="pass" maxlength="10" /><br />
           <input type="submit" value="Identificarse" />
         </form>
      <?php     
         }
      ?>
   </body>
</html>