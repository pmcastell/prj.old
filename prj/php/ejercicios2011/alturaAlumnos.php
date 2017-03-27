<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- Fecha de Creación: 24/02/2011 -->   
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web</title>
   </head>
   <body>
      <?php
         include_once("funcionesAuxiliares.php");
         /*for($i=0;$i<=9;$i++) {
            $alumnos[$i]=$_POST["alumno".$i];
            $alturas[$i]=$_POST["altura".$i];
         } */
         $alumnos=$_POST['alumnos'];
         $alturas=$_POST['alturas'];
         
         
         
         ordernaMatrices($alumnos,$alturas);
         echo '<table border="1">';
         echo '<tr><th>Alumno</th><th>Altura</th></tr>';
         for($i=0;$i<=9;$i++) {
            echo "<tr><td>$alumnos[$i]</td><td>$alturas[$i]</td></tr>";
         }
         echo '</table>';
         
         
         
         if (isset($_POST['alturas']) && isset($_POST['alumnos'])) {
            $alturas=$_POST['alturas'];
            $alumnos=$_POST['alumnos'];
            for($i=0;$i<count($alturas);$i++) {
               $alumnosAlturas[$alumnos[$i]]=$alturas[$i];
            }
            //ksort($alumnosAlturas);
            //arsort($alumnosAlturas);
            echo '<table border="1" cellpading="5" cellspacing="5">';
            imrpimeArrayAsociativo($alumnosAlturas);
            echo '</table>';
         } else {
            echo "Error en los datos. No se ha recibido ningún dato.\n<br />";
         }
            
      ?>
   </body>
</html>