<?php
   include_once("funcionesAuxiliares.php");
   if (acceso("Admin")) {
      if ($_POST) {
         $fechaIni=$_POST['fechaIni'];
         $fechaFin=$_POST['fechaFin'];
         $c="SELECT PELICULAS.cod ,PELICULAS.titulo, COUNT(*) AS NUM_ALQUILERES FROM alquila JOIN PELICULAS ON 
             PELICULAS.cod=alquila.cod_EJEMPLARES WHERE fecha_i BETWEEN $fechaIni AND $fechaFin 
             GROUP BY PELICULAS.cod ORDER BY PELICULAS.cod ";
         $resAlq=consulta($c);
         if ($resAlq!=0) {
            $c="SELECT PELICULAS.cod,PELICULAS.titulo, COUNT(*) AS NUM_EJEMPLARES FROM PELICULAS JOIN EJEMPLARES
            ON PELICULAS.cod=EJEMPLARES.cod_PELICULAS GROUP BY PELICULAS.cod ORDER BY PELICULAS.cod";
            $resEjemp=consulta($c);
            if ($resEjemp!=0) {
               $regAlq=mysql_fetch_assoc($resAlq);
               $regEje=mysql_fetch_assoc($resEjemp);
               $numTotalDias=numDias($fechaIni,$fechaFin);
               echo '<table border="1"';
               echo '<tr><th>Película</th><th>Porcentaje</th></tr>';
               while ($regAlq!=0 && regEj!=0) {
                  if ($regAlq['PELICULAS.cod']==$regEje['PELICULAS.cod']) {
                     echo '<tr><td>',$regAlq['PELICULAS.titulo'],'</td><td>',
                     $regAlq['NUM_ALQUILERES']/$regEje['NUM_EJEMPLARES']/$numTotalDias*100,
                     '</td></tr>';
                     $regAlq=mysql_fetch_assoc($resAlq);
                     $regEje=mysql_fetch_assoc($resEjemp);
                  } else if ($regAlq['PELICULAS.cod']<$regEje['PELICULAS.cod']) {
                     echo "Error: Película alquilada que no existe en tabla películas: ",$regAlq['PELICULAS.cod'];
                     $regAlq=mysql_fetch_assoc($resAlq);
                     
                  } else {
                     $regEje=mysql_fetch_assoc($resEjemp);
                  }
               }
               
               echo '</table>';
               
            }
         }
      } else {
         //mostrar formulario
         ?>
         <form action="?opcion=InformeDemanda" method="post">
         	<label for="fechaIni">Fecha de Inicio:</label>
         	<input type="text" name="fechaIni" id="fechaIni" maxlength="10" /><br />
         	<label for="fechaFin">Fecha Final:</label>
         	<input type="text" name="fechaFin" id="fechaFin" maxlength="10" /><br />
         	<input type="submit" value="Mostrar Informe" />
         </form>
         <?php
      }
   }
?>