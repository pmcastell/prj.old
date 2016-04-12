<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- Fecha de Creación: 07/03/2011 -->   
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Datos de POST y GET</title>
   </head>
   <body>
      <table border="1" cellpadding="5" cellspacing="5">
         <caption>Variables GET</caption>
         <tr><th>Variable</th><th>Valor</th><th>Tipo</th></tr>
      <?php
         foreach($_GET as $ind=>$val) {
           echo "<tr><td>$ind</td><td>$val</td><td>",getType($val),"</td></tr>";
         } 
      ?>
      </table>
      <br />
      <table border="1" cellpadding="5" cellspacing="5">
         <caption>Variables POST</caption>
         <tr><th>Variables</th><th>Valor</th><th>Tipo</th></tr>
      <?php 
         foreach($_POST as $ind=>$val){
            echo "<tr>$ind<td></td><td>$val</td><td>",getType($val),"</td></tr>";
         }
      ?>
      </table>
   </body>
</html>