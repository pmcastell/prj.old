<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web </title>
   </head>
   <body>
     <?php
         $n1=$_POST['n1'];
         $n2=$_POST['n2'];
         $op=$_POST['operacion'];
         if ($op=="Suma") {
         	echo "El resultado de sumar $n1 y $n2 es: ",$n1+$n2;
         } elseif ($op=="Resta") {
         	echo "El resulatdo de restar $n1 y $n2 es: ",$n1-$n2;
         } 
         
     ?>
   </body>
</html>