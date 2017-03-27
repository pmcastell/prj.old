<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <!-- Fecha de Creación: 07/03/2012 -->   
      <!-- Autor: F. Criado -->
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web</title>
      
    </head>
   <body style="background-color: blue;">
   
     <?php
        $a=$_POST['a'];
        $b=$_POST['b'];
        $c=$_POST['c'];
        if ($a==0) {
           echo '<span style="color:red;">La ecuación no tiene solución, a vale cero. Intenta de nuevo</span>';
        } else {
           if ($b*$b-4*$a*$c<0) {
              echo '<span style="color:red;">La ecuación no tiene solución Real.</span>';
           } else {
              $x1 = (-$b+sqrt($b*$b-4*$a*$c))/(2*$a);
              $x2 = (-$b-sqrt($b*$b-4*$a*$c))/(2*$a);
              echo "Las soluciones son<br/>";
              echo "x1: $x1<br/>";
              echo "x2: $x2<br/>";
           }
        }
     ?>
   </body>
</html>