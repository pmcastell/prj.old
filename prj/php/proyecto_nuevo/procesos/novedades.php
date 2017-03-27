<div class="post">
	<h1 class="title"><a href="#">Novedades de Películas</a></h1>
		<div class="entry">
			<?php
				// conectar con el gestor de base de datos
				$c=mysql_connect ("localhost","root","root") or die ("Imposible conectar"); 
				// Seleccionar la base de datos videoclub
				mysql_select_db ("bd_videoclub", $c); 
				// query
				$query = "SELECT * from pelicula where fecha_adquisicion is NULL";
				// echo $query, '<br>';
				$resultado = mysql_query($query, $c );
			?>
				<form name="novedades" method="post">
			<?php
			while( $registro = mysql_fetch_row( $resultado ) )
					{
			  echo'<table border="3" width="470">';
				echo'<tr>';
				echo'<th rowspan="8"><img src="./caratulas/',$registro[5],'" width="122"></th>';
				echo'<th>Titulo</th>';
					echo'<td>',$registro[1],'</td>';
				echo'</tr>';
				echo'<tr>';
				echo'<th>Año</th>';
					echo'<td>',$registro[2],'</td>';
				echo'</tr>';
				echo'<th>Duración</th>';
					echo'<td>',$registro[3],'</td>';
				echo'</tr>';
				echo'<th>Director</th>';
					echo'<td>','</td>';
				echo'</tr>';
				echo'<th>Actor</th>';
					echo'<td>','</td>';
				echo'</tr>';
				echo'<th>Genero</th>';
					echo'<td>';
					// query
					$varios_generos = "SELECT * from genero_pelicula where id_PELICULA='$registro[0]'";
					$nombre_gen = mysql_query($varios_generos, $c );
					while( $registro2 = mysql_fetch_row( $nombre_gen ) )
					{
					echo $registro2[1],' ';
					}
					echo'</td>';

				echo'</tr>';
				echo'<th>Sinopsis</th>';
					echo'<td>',$registro[4],'</td>';
				echo'</tr>';
				echo'<th>Fecha Adquisición</th>';
					echo'<td>',$registro[6],'</td>';
				echo'</tr>';
				echo'</table >';
			?>
			<br />
			<?
			}
			?>	
		</div>				
		<p class="links"></p>
			</form>
</div>