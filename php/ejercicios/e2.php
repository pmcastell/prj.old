<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web</title>
   </head>
   <body>
      <form id="formularioMatrices" action="e2Datos.php" method="post">
      <p>
      	<label for="A">Matriz A:</label>
      	<br />
			<!-- <input type="text" name="A11" maxlength="5" size="5" />
			<input type="text" name="A12" maxlength="5" size="5" />
			<input type="text" name="A13" maxlength="5" size="5" />
			<br />
			<input type="text" name="A21" maxlength="5" size="5" />
			<input type="text" name="A22" maxlength="5" size="5" />
			<input type="text" name="A23" maxlength="5" size="5" />
			<br />
			<input type="text" name="A31" maxlength="5" size="5" />
			<input type="text" name="A32" maxlength="5" size="5" />
			<input type="text" name="A33" maxlength="5" size="5" />
			<br /> 
			-->
			<?php
			   function pideMatriz($dimension,$nombre) {
					
					for($i=1;$i<=$dimension;$i++) {
						for($j=1;$j<=$dimension;$j++) {
							echo '<input type="text" maxlength="5" size="5" name="',$nombre,$i,$j,'" />',"\n";
						}
						echo '<br />';
					}
				}
				
				pideMatriz(6,"A");
				echo '<br />';
				echo '<label for="B">Matriz B:</label><br />';
				pideMatriz(6,"B");
				echo '<br />';
			?>
			
			
			
			Introduce año Nacimiento:
			
			<select name="edad">
				<?php
					for($anio=2010;$anio>=1900;$anio--) {
						echo "<option>$anio</option>\n";
					} 
				?>				
			</select>
			<br />
			<input type="submit" value="Realizar Operaciones" />
			
			
		</p>
      </form>

   </body>
</html>