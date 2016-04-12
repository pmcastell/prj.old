<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web</title>
   </head>
   <body>
<?php
	include_once("funcionesAuxiliares.php");
	
	
	for($i=1;$i<=2;$i++) {
		for($j=1;$j<=2;$j++) {
			$a[$i][$j]=$_POST["A$i$j"];
			$b[$i][$j]=$_POST["B$i$j"];
			$suma[$i][$j]=$a[$i][$j]+$b[$i][$j];
			$resta[$i][$j]=$a[$i][$j]-$b[$i][$j];
		}
	}
	$producto[1][1]=$a[1][1]*$b[1][1]+$a[1][2]*$b[2][1];
	$producto[1][2]=$a[1][1]*$b[1][2]+$a[1][2]*$b[2][2];
	$producto[2][1]=$a[2][1]*$b[1][1]+$a[2][2]*$b[2][1];
	$producto[2][2]=$a[2][1]*$b[1][2]+$a[2][2]*$b[2][2];

	imprimeMatriz($suma,"Suma",2);
	imprimeMatriz($resta,"Resta",2);
	imprimeMatriz($producto,"Producto",2);
	
	
?>	
   </body>
</html>