<?php
	function imprimeMatriz($matriz,$titulo,$limite) {
		echo '<table border="1">';
		echo "<caption>$titulo</caption>";
		foreach($matriz as $clave => $valor) {
			echo "<tr><td>$clave</td><td>$valor</td></tr>";
		}
		/*for($fila=1;$fila<=$limite;$fila++) {
			echo '<tr>';
			for($col=1;$col<=$limite;$col++) {
				echo '<td>',$matriz[$fila][$col],'</td>';
			}
			echo '</tr>';
		}*/
		echo '</table>';
	}
   function imprimeDosTablas($tabla1,$tabla2) {
   	echo '<table border="1">';
     	for($i=0;$i<count($tabla1);$i++) {
     		echo '<tr><td>',$tabla1[$i],'</td><td>',$tabla2[$i],'</td></tr>';
     	}
     	echo '</table>';
   }
   function imrpimeMatrizAsociativa($tabla) {
   	echo '<table border="1">';
   	foreach($tabla as $clave => $valor) {
   		echo "<tr><td>$clave</td><td>$valor</td></tr>";
   	}
   	echo '</table>';
   } 
   function cuadrado(&$n) {
   	$n=$n*$n;
   }
   function f($p1,$p2,$p3=6,$p4=7) {
   	
   }
   function iva($cantidad,$iva=16) {
   	return $cantidad*$iva/100;
   }
   function consulta($consu,&$conex) {
      //Paso 1: Establecer la conexión
      $conex=mysql_connect("localhost","farma","farma2010");
      //Paso 2: Comprobar la conexión
      if (!$conex) {
         echo 'Error: no se ha posido establecer la conexión con el servidor';
         return 0; 
      }
      //Paso 3: Establecer juego de caracteres y lanza la consulta
      mysql_set_charset("utf8");
      $result=mysql_db_query("farmacia",$consu,$conex);
      //Paso 4: Comprobar la consulta se ha lanzado correctamente
      if (!$result) {
         echo "Error en consulta: ",$consu;
         echo mysql_errno($conex),mysql_error($conex);
         return 0;
      }
      return $result;
   }
?>	