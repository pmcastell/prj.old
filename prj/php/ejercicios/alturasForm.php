<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Formulario alturas alumnos</title>
   </head>
   <body>
      <form action="alturas.php" method="post" id="formAlturas">
         <table border="1">
            <tr><th>Alumno</th><th>Altura</th></tr>
		      <?php
		         for($i=1; $i<=10; $i++) {
		         	echo '<tr><td><input type="text" name="nombres[]" size="40" /></td>'; 
		         	echo '<td><input type="text" name="alturas[]" size="5" /></td></tr>',"\n";
		         }
		      ?>
		     <tr>
			     <td rowspan="2">Ordenar:</td>
			     <td>
				     <select name="ordenacion"><option>Nombre</option><option>Altura</option></select>
			     </td>
		     </tr>
		     <tr><td><select name="ascDesc"><option>Asc</option><option>Desc</option></select></td></tr>
	        <tr><td colspan="2"><input type="submit" value="Ordenar" /></td></tr>
	      </table>
      </form>
   </body>
</html>