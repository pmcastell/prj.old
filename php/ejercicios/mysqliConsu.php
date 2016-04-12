<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web </title>
   </head>
   <body>
   	<?php 
      	$mysqli = new mysqli('localhost','farma','farma2010','farmacia');
      	// Check for errors
			if(mysqli_connect_errno()){
			 echo mysqli_connect_error();
			}
			$mysqli->set_charset("utf8");
			// Escape Query
			$query = $mysqli->real_escape_string("SELECT * FROM CLIENTES");;
			
			if($result = $mysqli->query($query)){
				 $nombresCampos=$result->fetch_fields();
				 echo '<table border="1">';
				 echo '<caption>Listado de Clientes</caption>';
				 echo '<tr>';
				 foreach($nombresCampos as $valor) {
				 	echo "<td>$valor->name</<td>";				 
				 }
				 echo '</tr>';
				 while($row = $result->fetch_assoc()){
				 	echo '<tr>';
				 	foreach($row as $val) {
				 		echo "<td>$val</td>";
				 	}
				 	echo '</tr>';
				   
				 }
				 // Free result set
				 $result->close();
			}
			// Close connection
			$mysqli->close();
		?>

   </body>
</html>