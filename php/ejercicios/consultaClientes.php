<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web </title>
   </head>
   <body>
      <?php
         include_once("funcionesAuxiliares.php");
         
         $result=consulta("SELECT * FROM CLIENTES",$conex);
         //INSERT INTO CLIENTES VALUES(NULL,'Alfonso','Pérez','Fernández','32931233499020180336')",$conex);
         if ($result!=0) {
            //Paso 5: Procesar los registros devueltos por la consulta
            echo '<table border="1">';
            echo '<caption>Listado de Clientes</caption>';
            echo '<tr><th>Código</th><th>Nombre</th><th>Apellido1</th><th>Apellido2</th><th>Cuenta</th></tr>';
            while($registro=mysql_fetch_assoc($result)) {
                echo '<tr><td>',$registro['cod'],'</td><td>',$registro['nombre'],'</td><td>',$registro['ap1'],
                     '</td><td>',$registro['ap2'],'</td><td>',$registro['cuenta'],'</td></tr>';
            }
            echo '</table>';
         }
         mysql_free_result($result);
         mysql_close($conex);
         /*
         $result=consulta("select * from CLIENTES");
         //Paso 1: Establecer conexión con el servidor
         $conex=mysql_connect("localhost","farma","farma2010");
         //Paso 2: Comprobar si ha habido error al establecer la conexión
         if (!$conex) {
             echo 'Error: no se ha podido establecer conexión con el servidor.';
         } else {
            //Paso 3: Establecer juego de caracteres y lanzar la consulta
            mysql_set_charset("utf8");
            $result=mysql_db_query("farmacia","SELECT * FROM CLIENTES",$conex);
            //Paso 4: Comprobar que la consulta se ha lanzado correctamente
            if (!$result) {
               echo "Error: no se ha podido ejecutar bien la consulta.";
               echo mysql_errno($conex),mysql_error($conex);
            } else {
               //Paso 5: Procesar los registros devueltos por la consulta
               echo '<table border="1">';
               echo '<caption>Listado de Clientes</caption>';
               echo '<tr><th>Código</th><th>Nombre</th><th>Apellido1</th><th>Apellido2</th><th>Cuenta</th></tr>';
               while($registro=mysql_fetch_assoc($result)) {
                   echo '<tr><td>',$registro['cod'],'</td><td>',$registro['nombre'],'</td><td>',$registro['ap1'],
                        '</td><td>',$registro['ap2'],'</td><td>',$registro['cuenta'],'</td></tr>';
               }
               echo '</table>';
            }
            //Paso 6: Liberar recursos en el servidor
            mysql_free_result($result);
         }
         //Paso 7: Liberar la conexión
         mysql_close($conex);
         */
          
      ?>
   </body>
</html>