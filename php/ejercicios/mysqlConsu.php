<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <title>Título de la web </title>
      <script>
      function validarDni(dni) {
          return dni;
      }
    	</script>
   </head>
   <body>
      <?php
      	function error($mensaje) {
      		echo '<p style="color: red">',$mensaje,'</p>';
      	}
      	$conex=mysql_connect("localhost","farma","farma2010");
      	if (!$conex) {
      		error(mysql_errno().":".mysql_error());
      	} else {
      		mysql_set_charset("utf8");
      		$result=mysql_db_query("farmacia","select * from CLIENTES");
      		if (!$result) {
      			error(mysql_errno().":".mysql_error());
      		} else {
      			echo '<table border="1">';
      			echo '<caption>Listado de Clientes</caption>';
      			echo '<tr><th>Código</th><th>Nombre</th><th>Cuenta</th>';
      			while($fila=mysql_fetch_assoc($result)) {
      				echo '<tr><td>';
      				echo $fila['cod'];
      				echo '</td><td>';
      				$nombreCompleto=$fila['nombre']." ".$fila['ap1']." ".$fila['ap2'];
      				//echo "<script>alert('$nombreCompleto');</script>";
      				echo ($nombreCompleto);
      				echo '</td><td>';
      				echo $fila['cuenta'];
      				echo '</td></tr>';
      			}
					echo '</table>';
					mysql_free_result($result);
      		}
      		mysql_close($conex);
      	}
      ?>
   </body>
</html>