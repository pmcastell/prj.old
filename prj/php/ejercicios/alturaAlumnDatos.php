<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web </title>
   </head>
   <body>
      <?php
      	function burbuja(&$nombres,&$alturas) {
      		for($i=0;$i<count($nombres);$i++) {
      			for($j=$i;$j<count($nombres);$j++) {
      				if ($alturas[$i]>$alturas[$j]) {
      					$temp1=$alturas[$i];
      					$temp2=$nombres[$i];
      					$alturas[$i]=$alturas[$j];
      					$nombres[$i]=$nombres[$j];
      					$alturas[$j]=$temp1;
      					$nombres[$j]=$temp2;
      				}
      			}
      		}
      		
      	}

      	$nombres=$_POST['nombres'];
      	$alturas=$_POST['alturas'];
      	burbuja($nombres,$alturas);
      	imprimeDosTablas($nombres,$alturas);
      ?>
   </body>
</html>