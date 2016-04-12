<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web</title>
   </head>
   <body>
      <form method="post" action="/datos.php" id="formAltaPelis">
      <!--   campos -->
         <?php
            include_once("funcionesAuxiliares.php");
            creaSelect("edad",18,120); 
            echo '<br />';
         ?>
            Fecha:<br />
            <?php
               creaSelect("fecha['dia']",1,31);
               echo("/");
               creaSelect("fecha['mes']",1,12);
               echo("/");
               creaSelect("fecha['anio']",1900,2011); 
            ?>
            <input type="text" name="a[0][0]" maxlength="5" />
            <input type="text" name="a[0][1]" maxlength="5" /><br />
            <input type="text" name="a[1][0]" maxlength="5" />
            <input type="text" name="a[1][1]" maxlength="5" /><br />
            <br />
            <input type="submit" value="Enviar" />
 
      </form>
   </body>
</html>   
