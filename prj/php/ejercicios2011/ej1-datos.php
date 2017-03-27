<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web</title>
   </head>
   <body>
      <?php
         $n1=$_POST['n1'];
         $n2=$_POST['n2'];
         $op=$_POST['op'];
         if ($op=='+') {
            echo "La Suma de $n1 y $n2 es: ",$n1+$n2;
         } elseif ($op=='-') {
            echo "La Resta de $n1 y $n2 es: ",$n1-$n2;
         } elseif ($op=='*') {
            echo "El Producto de $n1 y $n2 es: ",$n1*$n2;
         } else {
            if ($n2==0) {
               echo "Error no puedo dividir $n1 por 0";
            } else {
               echo "La División de $n1 entre $n2 es: ",$n1/$n2;
            }
         }
      ?>
   </body>
</html>