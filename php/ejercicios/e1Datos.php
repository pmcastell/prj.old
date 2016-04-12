<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web</title>
   </head>
   <body>
<?php
	if (isset($_GET['n1']) && isset($_GET['n2'])) {
		$n1=$_GET['n1'];
		$n2=$_GET['n2'];
	} else if (isset($_POST['n1']) && isset($_POST['n2'])) {
		$n1=$_POST['n1'];
		$n2=$_POST['n2'];
	} else {
		echo "Error no he encontrado datos para operar.";
		echo "</body>\n</html>";
		exit();
	}
	
	echo "La suma de $n1 y $n2 es:",$n1+$n2,"<br />";
	echo "La resta de $n1 y $n2 es:",$n1-$n2,"<br />";
	echo "El Producto de $n1 y $n2 es: ",$n1*$n2,"<br />";
	echo "La división de $n1 y $n2 es: ",$n1/$n2,"<br />";
?>	
   </body>
</html>