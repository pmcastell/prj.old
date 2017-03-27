<?php
  function error($mensa) {
      echo "<br><center><font color=red size=5>$mensa</font></center>";
  }
  function consulta($consu,$base,$usu,$pass) {
      $conexion=mysql_connect("localhost",$usu,$pass); // 
      if (!$conexion) {
          error("No se ha podido conectar con localhost");
          error(mysql_errno().": ".mysql_error());
          die();
      }
      $result=mysql_selectdb($base);
      if (!$result) {
          error("No se ha podido seleccionar base datos: $base");
          error(mysql_errno().": ".mysql_error());
          die();
      }
      $result=mysql_query("$consu");
      if (!$result) {
          error("Error realizando consulta: $consu");
          error(mysql_errno().": ".mysql_error());
          die();
      }
      if (mysql_num_rows($result)==0) {
          error("No se han encontrado resultados de la consulta: $consu");
          die();
      }
      echo '<link rel="stylesheet" type="text/css"  href="biblioteca.css">';
      echo '<table border="2" cellpadding="2" align="center">';
      $numCampos=mysql_num_fields($result);
      for($i=0; $i<$numCampos; $i++) {
          echo '<th><a href="">',mysql_field_name($result,$i),'</a>';
      }
      while($fila=mysql_fetch_array($result)) {
          echo '<tr>';
          for($i=0; $i<$numCampos; $i++) {
              if (isset($fila[$i])) {
                 echo '<td>',$fila[$i];
              } else {
                  echo '<td>&nbsp;';
              }
          }
      }
      echo '</table>';        
      mysql_free_result($result);
      mysql_close($conexion);
  }
  consulta("select * from libros","biblioteca","biblioteca","biblioteca68");
  consulta("select * from (libros inner join prestamos on codigo=cod_libro) inner join lectores on dni=dni_lector","biblioteca","biblioteca","biblioteca68");

      
?>