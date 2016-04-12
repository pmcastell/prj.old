<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <!-- Fecha de Creación: 14/03/2012 -->   
      <!-- Autor: F. Criado -->
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web</title>
    </head>
   <body>
     <form action="registroUsuarios.php" method="post">
        <label>Fecha de Nacimiento</label>
        <select name="dia" id="dia">
           <?php
              for($i=1;$i<=31;$i++) {
                 echo "<option>$i</option>";
              } 
           ?>
        </select>
        <select name="mes" id="mes">
           <?php
              for($i=1;$i<=12;$i++) {
                 echo "<option>$i</option>";
              } 
           ?>
        </select>
        <select name="anio" id="anio">
           <?php
              for($i=1892;$i<=2012;$i+=4) {
                 echo "<option>$i</option>";
              } 
           ?>
        </select>
     </form>
   </body>
</html>