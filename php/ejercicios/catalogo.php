<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web </title>
   </head>
   <body>
      <?php
         function pie() {
         	echo "</body>\n</html>";
         	exit();
         }
         $conex=mysql_connect("localhost","videoclub","pepe");
         if (!$conex) {
        	   echo("No se ha podido establecer la conexión con el servidor.");
        	   pie();
         }
         $result=mysql_db_query("videoclub2","select * from PELICULAS",$conex);
         if (!$result) {
            echo("No se ha podido realizar la consulta.");	
            pie();
         }
         echo '<table border="1"';
         echo '<tr><th>Título</th><th>Año</th><th>Duración</th><th>Director</th><th>Sinopsis</th>';
         while($fila=mysql_fetch_assoc($result)) {
         	echo '<tr>';
            foreach($fila as $campo=>$valorCampo) {
            	if ($campo!="id") {
            		echo '<td>',$valorCampo,'</td>';
            	}
            }           
            echo '</tr>';             
         }
         echo '</table>';
         mysql_free_result($result);
      ?>
   </body>
</html>