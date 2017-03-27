<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web </title>
   </head>
   <body>
   	<form id="alturas" method="post" action="alturaAlumnDatos2.php">
   	<table border="1">
   		<tr><th>Alumno</th><th>Altura</th></tr>
      <?php
      	for($i=1;$i<=10;$i++) {
      		echo '<tr>
      					<td><input type="text" name="nombres[]" maxlength="35" size="35" /></td>
      					<td><input type="text" name="alturas[]" maxlength="5" size="5" /></td>
      				</tr>
      				';
      	}
      ?>
      	<tr><td colspan="2"><input type="submit" value="Ordenar" /></td></tr>
      </table>
      </form>
   </body>
</html>