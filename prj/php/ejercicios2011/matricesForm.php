<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- Fecha de Creación: 07/03/2011 -->   
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Producto de matrices</title>
   </head>
   <body>
      <?php
         include_once("funcionesAuxiliares.php");
         if (!$_POST) {
            echo '<form method="post" action="?">';
            echo '<table border="1">';
            echo '<tr><td>Matriz A:</td><td><table>';
            pedirArrayBid("a",3,3);
            echo '</table border="1"></td><td>Matriz B:</td><td><table>';
            pedirArrayBid("b",3,3);
            echo '</table></td></tr>';
            echo '<tr><td  colspan="4"><input type="submit" value="Calcular Producto" /></td></tr>';
            echo '</table>';
            echo '</form>';
         } else {
            if (isset($_POST['a']) && isset($_POST['b'])) {
               imprimeArrayBid(productoMatricesBid($_POST['a'],$_POST['b']),1,5,5);
            }
         }
      ?>
     
   </body>
</html>