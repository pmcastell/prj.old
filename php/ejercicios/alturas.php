<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Alumnos Ordenados por Alturas</title>
   </head>
   <body>
      <?php
         $nomb=$_POST['nombres'];
         $alt=$_POST['alturas'];
         $campoOrden=$_POST['ordenacion'];
         $ascDesc=$_POST['ascDesc'];
         for($i=0; $i<count($nomb); $i++) {
         	$alumno[$nomb[$i]]=$alt[$i];
         }
         if ($campoOrden=="Nombre" and $ascDesc=="Asc") {
         	ksort($alumno);
         } elseif ($campoOrden=="Nombre" and $ascDesc=="Desc") {
         	krsort($alumno);
         } elseif ($campoOrden=="Altura" and $ascDesc=="Asc") {
         	asort($alumno);
         } else { // elseif ($campoOrden="Altura" and $ascDesc="Desc") 
            arsort($alumno);
         }
         echo '<table border="1"';
         echo '<tr><th>Alumno</th><th>Altura</th></tr>';
         foreach($alumno as $nombre=>$altura) {
         	echo "<tr><td>$nombre</td><td>$altura</td></tr>";
         }
      ?>
   </body>
</html>