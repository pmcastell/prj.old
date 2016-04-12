<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- Fecha de Creación: 24/02/2011 -->   
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web</title>
   </head>
   <body>
      <form method="post" action="alturaAlumnos.php">
      <table border="1">
         <tr><th>Alumno</th><th>Altura</th></tr>
      <?php
         for($fila=0;$fila<=9;$fila++) {
            echo '<tr><td><input type="text" name="alumnos[]" /></td><td><input type="text" name="alturas[]" /></td></tr>';
         }       
      ?>
      <tr><td colspan="2"><input type="submit" value="Ordernar Alumnos" /></td></tr>
      </table>
      </form>
   </body>
</html>