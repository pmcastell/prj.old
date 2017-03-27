<?php
	if (isset($_COOKIE['contador'])) {
		$num_visitas=$_COOKIE['contador']+1;
		setcookie("contador",$num_visitas,time()+86400*365);
	} else {
		$num_visitas=1;
		setcookie("contador",1,time()+86400*365);
	}		
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web </title>
   </head>
   <body>
      <?php 
      echo "Me has visitado: $num_visitas veces.";
      if (!$_POST) {
      	//muestro formulario
      ?>
      	<form id="formAltaCli" action="?" method="post">
      	<p>
      		<label for="nombre">Nombre:</label>
      		<input type="text" name="nombre" id="nombre" size="35" maxlength="35" />
      		<br />
      		<label for="ap1">Apellido1:</label>
      		<input type="text" name="ap1" id="ap1" size="30" maxlength="30" />
      		<br />
      		<input type="submit" value="enviar" />
      		
      	</p>	
      	</form>
      	 
 		<?php 
      } else {
      	//procesar los datos
      	//validar los datos
      	//insertar registro con los datos recibidos en la tabla CLIENTES
      	echo '<table border="1">';
      	echo '<caption>Datos Recibidos</caption>';
      	foreach($_POST as $clave => $valor) {
      		echo "<tr><td>$clave</td><td>$valor</td></tr>";
      	}
      	echo '</table>';
      }
      ?>
   </body>
</html>