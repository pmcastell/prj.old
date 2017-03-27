<?php
   function error($mensaje) {
      echo '<span class="error">',$mensaje,'</span>';
   }
   function consulta($consulta,&$conex) {
      $conex=mysql_connect("localhost","videoclub2010","videoclub2010");
      if (!$conex) {
         error("No se ha podido establecer conexión con el servidor.\n".mysql_errno().":".mysql_error());
         return 0;
      }
      mysql_set_charset("utf8");
      $result=mysql_db_query("videoclub2010",$consulta);
      if (!$result) {
         error("Error en la consulta:\n".mysql_errno().":".mysql_error());
         return 0;
      }
      return $result;
   }
   function esCampoImagen($val) {
      $i=strpos($val,".");
      if ($i>0) {
         while(strpos($val,".",$i+1)) {
            $i=strpos($val,".",$i+1);
         }
         $ext=strtoupper(substr($val,$i));
         return $ext=='.JPEG' || $ext=='.PNG' || $ext=='.GIF' 
         			|| $ext=='.JPG';
      }
      return false;
   }
   function getVarsUrl($except=null) {
      $i=count($_GET);
      $url="";
      foreach($_GET as $ind => $val) {
         if ($ind!=$except) {
            $url.=$ind."=".$val;
         }
         if (--$i>0) {
            $url.="&";
         }
      }
      return $url;
   }
   function listado($consulta,$maxLong=80,$reduc=45) {
      $ultimoListado=(isset($_SESSION['ultimoListado']) ? $_SESSION['ultimoListado'] : null);
      if ($ultimoListado==$consulta && !strstr(strtoupper($consulta),"ORDER BY") && isset($_GET['ordenListado'])) {
         if (!isset($_SESSION['tipoOrdenListado'])) {
            $_SESSION['tipoOrdenListado']='ASC';
         } else {
            $_SESSION['tipoOrdenListado']=($_SESSION['tipoOrdenListado']=='ASC' ? 'DESC' : 'ASC');
         }
         $consulta.=" ORDER BY ".$_GET['ordenListado']." ".$_SESSION['tipoOrdenListado'];
      }
      $result=consulta($consulta,$conex);
      if (!$result) {
         return 0;
      }
      $registro=mysql_fetch_assoc($result);
      echo '<table border="1">
               <tr>';
      $url=getVarsUrl("ordenListado");
      foreach($registro as $nombreCampo => $valor) {
         echo '<th><a href="?',$url,'&ordenListado=',$nombreCampo,'" title="ordenar por ',$nombreCampo,'">',$nombreCampo,'</a></th>';
      }
      echo '</tr>';
      while ($registro) {
         echo '<tr>';
         foreach($registro as $in => $val) {
            if (esCampoImagen($val)) {
               echo "<td><img height=\"$reduc%\" width=\"$reduc%\" src=\"$val\" /></td>";
            } else {
               $val="".$val;
               if (strlen($val)>$maxLong) {
                  echo "<td title=\"$val\">",substr($val,0,$maxLong),"</td>";
               } else {
                  echo "<td>$val</td>";
               }
            }
         }
         echo '</tr>';
         $registro=mysql_fetch_assoc($result);
      }
      echo '</table>';
      mysql_free_result($result);
      mysql_close($conex);
      $_SESSION['ultimoListado']=$consulta;
   }
   //esCampoImagen("imagenes/avatar.jpeg");
   //listado("select * from PELICULAS");
   getVarsUrl();
?>