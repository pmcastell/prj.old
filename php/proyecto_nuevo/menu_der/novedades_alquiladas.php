<ul>
	<li>
		<h2>Novedades</h2>
		<ul>
			<?php
			// conectar con el gestor de base de datos
			$c=mysql_connect ("localhost","root","root") or die ("Imposible conectar"); 
			// Seleccionar la base de datos videoclub
			mysql_select_db ("BD_VIDEOCLUB", $c); 
			// query
			$query = "SELECT * from PELICULA where fecha_adquisicion IS NULL LIMIT 5";
			// echo $query, '<br>';
			$resultado = mysql_query($query, $c);
			while( $registro = mysql_fetch_row( $resultado ) )
				{
				echo '<li>',$registro[1],'</li>';
				}
			?>
			
		</ul>
		<h2>Las + Alquiladas</h2>
		<ul>
			<li><a href="#">1. Pelicula 1</a></li>
			<li><a href="#">2. Pelicula 2</a></li>
			<li><a href="#">3. Pelicula 3</a></li>
			<li><a href="#">4. Pelicula 4</a></li>
			<li><a href="#">5. Pelicula 5</a></li>
		</ul>
	</li>
</ul>