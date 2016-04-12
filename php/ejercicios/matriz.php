<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web </title>
   </head>
   <body>
     <?php
         $a00=$_POST['a00'];
         $a01=$_POST['a01'];
         $a10=$_POST['a10'];
         $a11=$_POST['a11'];
         
         $b00=$_POST['b00'];
         $b01=$_POST['b01'];
         $b10=$_POST['b10'];
         $b11=$_POST['b11'];
         $op=$_POST['operacion'];
         if ($op=="Suma") {
         	echo "El resultado es: <br />";
         	echo $a00+$b00, " ", $a01+$b01;
         	echo "<br />";
         	echo $a10+$b10, " ", $a11+$b11;
         } else if ($op=="Resta") {
            echo "El resultado es: <br />";
            echo $a00-$b00, " ", $a01-$b01;
            echo "<br />";
            echo $a10-$b10, " ", $a11-$b11;
         } else if ($op="Producto") {
         	echo "El resultado es: <br />";
         	echo $a00*$b00+$a01*$b10, " ",$a00*$b01+$a01*$b11;
         	echo '<br />';
         	echo $a10*$b00+$a11*$b10, " ",$a10*$b01+$a11*$b11;
         } else {
         	echo "Ha habido un error. La operación no es reconocida.";
         }         
     ?>
   </body>
</html>