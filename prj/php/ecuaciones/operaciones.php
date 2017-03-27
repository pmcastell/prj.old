<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <!-- Fecha de Creación: 14/03/2012 -->   
      <!-- Autor: F. Criado -->
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web</title>
    </head>
   <body style="background-color: lightblue; margin-left: auto; margin-right: auto;">
     <?php
        $n1=$_POST['n1'];
        $n2=$_POST['n2'];
        $suma=$n1+$n2;
        echo "<table border='1' style='border: 2px solid red; background-color: yellow; color:orange;' >";
        echo "<tr>";
        echo "<td>";
        echo "La Suma vale: ";
        echo "</td>";
        echo "<td>";
        echo $n1+$n2;
        echo "</td>";
        echo "</tr>";
    
        echo "<tr>";
        echo "<td>";
        echo "La Resta vale: ";
        echo "</td>";
        echo "<td>";
        echo $n1-$n2;
        echo "</td>";
        echo "</tr>";
        
        echo "<tr>";
        echo "<td>";
        echo "El Producto vale: ";
        echo "</td>";
        echo "<td>";
        echo $n1*$n2;
        echo "</td>";
        echo "</tr>";
        
        echo "El Producto vale: ",$n1*$n2,"<br/>"; 
        if ($n2==0) {
           echo "</table>";
           echo '<span style="color:red;">El segundo número vale cero y no puedo hacer la división.';
        } else {
           echo "<tr>";
           echo "<td>";
           echo "La División vale: ";
           echo "</td>";
           echo "<td>";
           echo $n1/$n2;
           echo "</td>";
           echo "</tr>";
           
           echo "</table>";
        }
     ?>
   </body>
</html>