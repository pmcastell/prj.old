<?php
  include_once("funcionesAuxiliares.php");
  function tienePermiso() {
      $consulta=$_GET['destino'];
      $tipoUsuario=$_SESSION['tipoUsuario'];
      if ($consulta=="peliculas" || $consulta=="tarifas") {
          return true; // todo el mundo puede ver el catálogo de películas
      } elseif ($tipoUsuario=="admin") {
          return true; // el administrador puede consultarlo todo
      } elseif ($tipoUsuario=="socio" && $consulta=="datosPersonales") {
          return true;
      }
      return false;
  }

  function variablesGetSinOrden() {
      $result=variablesGet();
      $i=strpos($result,"__orden__");
      if ($i) {
         return substr($result,0,$i-1);
      } else {
         return $result;
      }
      
  }
        
  function consulta($consu,$base,$usu,$pass,$title=null,$fotos=null,$totalizar=false) {


      if (isset($_GET['__orden__'])) {
         $consu.=" order by ".$_GET['__orden__'];
      }
      if (isset($_GET['__tipoOrden__'])) {
          $consu.=" ".$_GET['__tipoOrden__'];
      }
      if (!($result=query($consu,&$conexion))) {
         return false;
      }
      if (mysql_num_rows($result)==0) {
          //error("No se han encontrado resultados de la consulta: $consu"); return false;
      }
      //Mostramos resultados de la consulta
      //echo '<div class="tabla">';
      echo '<table class="listado" cellpadding="2" align="center">';
      echo '<tr>';
      $numCampos=mysql_num_fields($result);
      for($i=1; $i<=$numCampos; $i++) {
          if ($title==null || !strstr($title,"$i")) {  
              echo '<th><a href="?',variablesGetSinOrden();
              echo '&__orden__=',$i;
              if (isset($_GET['__orden__']) && $i==$_GET['__orden__'] && !isset($_GET['__tipoOrden__'])) {
                  echo '&__tipoOrden__=DESC';
              }
              echo '" title="Ordenar por ',mysql_field_name($result,$i-1),'">',mysql_field_name($result,$i-1),'</a>';
              //mb_convert_case(mysql_field_name($result,$i-1), MB_CASE_TITLE),'</a>';
          }
      }        
      $fila=mysql_fetch_array($result);
      $totales=array();
      for($i=1; $i<$numCampos; $i++) {
         if (is_numeric($fila[$i-1])) {
           $totales[$i]=0;
         }
      }
      do {
          echo '<tr>';
          $titulo="";

          if ($title!=null) {
             for($i=1; $i<=$numCampos; $i++) {
                 if (strstr($title,"$i")) {
                     $titulo.=" | ".$fila[$i-1];
                 }
             }
          }
          for($i=1; $i<=$numCampos; $i++) {
              if ($title==null || !strstr($title,"$i")) {
                  if (!isset($fila[$i-1]) || $fila[$i-1]==null) {
                     echo '<td>&nbsp;';
                  } else { 
                     echo '<td title="',$titulo,'"';
                     if (strstr(mysql_field_type($result,$i-1),'date')) {
                        echo ' align="right">',fechaAlReves($fila[$i-1]);
                     } else {
                        if (is_numeric($fila[$i-1])) {
                            echo ' align="right"';
                            $totales[$i-1]+=$fila[$i-1];
                        } 
                        if ($fotos!=null && "$i"==substr($fotos,strpos($fotos,"-")+1)) {
                           //"window.open('http://www.pageresource.com/jscript/jex5.htm','mywindow','width=400,height=200')"> 
                           echo ' align="center">';
                           echo '<a href="javascript:void(0);" onMouseOver="javascript:window.open(\'',substr($fotos,0,strpos($fotos,"-")),'/';
                           echo $fila[$i-1];
                           echo "','",substr($fotos,0,strpos($fotos,"-")),"','width=350,height=450');\" >";
                           echo '<img src=',substr($fotos,0,strpos($fotos,"-")),'/';
                           echo $fila[$i-1];
                           echo ' width="35%" align="center">';
                           echo '</a>';
                        } else {
                           echo '>',$fila[$i-1];
                        }
                     }
                  }
              }
          }
      } while($fila=mysql_fetch_array($result));
      if ($totalizar) {
         echo '<tr>';
         for($i=0; $i<$numCampos; $i++) {
            echo '<th align="right">';
            if (isset($totales[$i])) {
               echo $totales[$i];
            } else {
               echo '&nbsp;';
            }
         }
      }
      echo '</table>';        
      mysql_free_result($result);
      mysql_close($conexion);
  }
  function informes() {
      if ((isset($_GET['fechaInicioIngresos']) && isset($_GET['fechaFinIngresos']))) {
         $fechaInicio=fechaAlReves($_GET['fechaInicioIngresos']);
         $fechaFin=fechaAlReves($_GET['fechaFinIngresos']);
         echo "<table><td><b>Informe de ";
         if (isset($_GET['inf']) && $_GET['inf']=="cli") {
            echo "<u>Ingresos de Socios</u> efectuados entre ";
         } elseif (isset($_GET['inf']) && $_GET['inf']=="alq") {
            echo "<u>Ingresos de Alquileres</u> efectuados entre ";
         } elseif (isset($_GET['inf']) && $_GET['inf']=="pel") {
            echo "<u>Utilización de Películas</u> entre ";
         }
         echo ($_GET['fechaInicioIngresos'])," y ",($_GET['fechaFinIngresos']),". </b></table>";
         if ($_GET['inf']=="cli") {
            $select="select dni as DNI, ".nombreApellidos()." as Socio, fecha as Fecha, cantidad as Cantidad from socios join ingresos on socios.dni=ingresos.dni_socios where fecha between '$fechaInicio 00:00'  and '$fechaFin 23:59'";
         } elseif ($_GET['inf']=="alq") {
            //$select="select titulo as Película, fecha_alquiler as Retirada, fecha_devolucion Devolución, horas as Horas, if(tiempo<=3*3600, tarifa3,if(tiempo<=6*3600, tarifa6,if(tiempo<=12*3600,tarifa12,(tiempo div (24*3600)+1)*tarifa24))) as Importe from (select titulo, fecha_alquiler, fecha_devolucion,timestampdiff(SECOND,fecha_alquiler,fecha_devolucion) as tiempo,timediff(fecha_devolucion,fecha_alquiler) as horas, tarifas.*  from (((alquileres join tarifas on id_tarifas=tarifas.id) join detalle_alquileres on alquileres.id=id_alquileres) join ejemplares on id_ejemplares=ejemplares.id_peliculas and num_ejemplares=ejemplares.num) join peliculas on ejemplares.id_peliculas=peliculas.id) as sub where fecha_devolucion is not null and fecha_devolucion between '$fechaInicio 00:00' and '$fechaFin 23:59'";
            $select="select titulo as Película, fecha_alquiler as Retirada, fecha_devolucion Devolución, horas as Horas, id_tarifas as Tarifa,if(tiempo<=3*3600, tarifa3,if(tiempo<=6*3600, tarifa6,if(tiempo<=12*3600,tarifa12,(tiempo div (24*3600)+1)*tarifa24))) as Importe from (select titulo, fecha_alquiler, fecha_devolucion,timestampdiff(SECOND,fecha_alquiler,fecha_devolucion) as tiempo,timediff(fecha_devolucion,fecha_alquiler) as horas,id_tarifas, tarifas.*  from (((alquileres join tarifas on id_tarifas=tarifas.id) join detalle_alquileres on alquileres.id=id_alquileres) join ejemplares on id_ejemplares=ejemplares.id) join peliculas on ejemplares.id_peliculas=peliculas.id) as sub where fecha_devolucion is not null and fecha_devolucion between '$fechaInicio 00:00' and '$fechaFin 23:59'"; 
            
         } else {
            //$select="select titulo as Películas, count(disponible) from ((((socios join alquileres on dni=dni_socios) join detalle_alquieres on id=id_alquileres) join ejemplares on id_ejemplares=ejemplares.id and num_ejemplares=ejemplares.num) join peliculas on ejemplares.id_peliculas=peliculas.id)";
            //$select="select titulo as Película,num_ejemplares_pelicula as Num_Ejemplares,num_alquileres_pelicula as Num_Alquileres, round(num_alquileres_pelicula*100/(num_total_alquileres*num_ejemplares_pelicula),2) as Porcentaje, round(num_alquileres_pelicula*100/(num_total_alquileres),2) as Porcentaje_Global from ((((select id_ejemplares,count(num_ejemplares) as num_alquileres_pelicula from alquileres join detalle_alquileres on id=id_alquileres where fecha_alquiler between '$fechaInicio 00:00' and '$fechaFin 23:59' group by id_ejemplares) as parte1) join ((select titulo,id_peliculas,count(num) as num_ejemplares_pelicula from peliculas join ejemplares on id=id_peliculas group by id_peliculas) as parte2) on id_ejemplares=id_peliculas) join (select count(*) as num_total_alquileres from alquileres join detalle_alquileres on id=id_alquileres where fecha_alquiler between '$fechaInicio' and '$fechaFin' ) as parte3)";
            $select="select titulo as Película,num_ejemplares_pelicula as Num_Ejemplares,num_alquileres_pelicula as Num_Alquileres, round(num_alquileres_pelicula*100/(num_total_alquileres*num_ejemplares_pelicula),2) as Porcentaje, round(num_alquileres_pelicula*100/(num_total_alquileres),2) as Porcentaje_Global from ((((select id_ejemplares,count(id_ejemplares) as num_alquileres_pelicula,peliculas.id as peliculas_id from (((alquileres join detalle_alquileres on alquileres.id=id_alquileres) join ejemplares on id_ejemplares=ejemplares.id) join peliculas on id_peliculas=peliculas.id) where fecha_alquiler between '$fechaInicio 00:00' and '$fechaFin 23:59' group by peliculas.id) as parte1) join ((select titulo,id_peliculas,count(ejemplares.id) as num_ejemplares_pelicula from peliculas join ejemplares on peliculas.id=id_peliculas group by id_peliculas) as parte2) on peliculas_id=id_peliculas) join (select count(*) as num_total_alquileres from alquileres join detalle_alquileres on alquileres.id=id_alquileres where fecha_alquiler between '$fechaInicio 00:00' and '$fechaFin 23:59' ) as parte3)";
         }
         consulta($select,'videoclub','videoclub','videoclub2008',null,null,true);
      } else { 
         echo '<br><br><br>
         <script>
            function envio() {
               loca=""+document.location;
               for(i=0; i<document.forms[0].elements.length; i++) {
                  temp=document.forms[0].elements[i];
                  loca+="&"+temp.name+"="+temp.value;
               }
               document.location=loca;
            }
         </script>
         <script type="text/javascript" src="calendario/calendar.js"></script>
         <script type="text/javascript" src="calendario/calendar-setup.js"></script>
         <script type="text/javascript" src="calendario/lang/calendar-es.js"></script>
         <link rel="stylesheet" type="text/css" media="all" href="calendario/skins/aqua/theme.css" title="Aqua" />
         
         <form name="consultaIngresos" action="?',variablesGet(),'" method="GET">
         <table cellspacing="15">
         <tr><th colspan="2">Informe de ';
         if (isset($_GET['inf']) && $_GET['inf']=="cli") {
            echo "Ingresos efectuados por los Clientes";
         } elseif (isset($_GET['inf']) && $_GET['inf']=="alq") {
            echo "Ingresos provenientes de alquileres";
         } elseif (isset($_GET['inf']) && $_GET['inf']=="pel") {
            echo "Porcentaje de demanda de Películas";
         }
         echo '<tr><td colspan="2">Seleccione Intervalo de fechas:
         <tr><td>
         Fecha Inicial: <td>';
         calendarFecha("fechaInicioIngresos");
         //selectFecha("fechaInicioIngresos",getdate());
         echo '<tr><td>Fecha Final: <td>';
         calendarFecha("fechaFinIngresos");
         //selectFecha("fechaFinIngresos",getDate());
         echo '<tr><td colspan="2" align="center"><input type="button" value="Ver Informe" onClick="envio();">
               </form>';
      }
  }
  //consulta("show fields from ".$_GET['destino'],"videoclub","videoclub","videoclub2008");    
  //consulta("show create table ".$_GET['destino'],"videoclub","videoclub","videoclub2008");   
  //consulta("show keys from " .$_GET['destino'],"videoclub","videoclub","videoclub2008");   
  if (tienePermiso()) {
      $consulta=$_GET['destino'];
      // formatear las fechas en mysql date_format(fecha_estreno, '%d/%m/%Y')
      if ($consulta=="datosPersonales") {
          echo '<br><br><br><br><br><br>';
          $select="select dni as DNI, ".nombreApellidos()." as Nombre,telefono as Teléfono, concat(".conIf("direccion").",'. ',".conIf("cp").",' - ',".conIf("ciudad").") as Dirección, saldo as Saldo from socios where dni='".$_SESSION['dni']."'";
          consulta($select,'videoclub','videoclub','videoclub2008');
      } elseif ($consulta=="peliculas") {
         $select="select distinct peliculas.id as Cod,titulo as Título,tema as Género,fecha_estreno as Estreno,director as Director,actores as Actores,sum(if(disponible is null,0,disponible)) as Disp, imagen as Carátula from peliculas left join ejemplares on peliculas.id=id_peliculas group by cod";
         $select="select Cod, Título, Género, Estreno, Director, Actores,  sum(Dispo) as Disp, Carátula from (
                  select peliculas.id as Cod, ejemplares.id as Ejem, titulo as Título, tema as Género, fecha_estreno as Estreno, director as Director, actores as Actores, 1 as Dispo, imagen as Carátula from ejemplares join peliculas on id_peliculas=peliculas.id where ejemplares.id not in (select id_ejemplares from detalle_alquileres where fecha_devolucion is null) 
                  union
                  select peliculas.id as Cod, ejemplares.id as Ejem, titulo as Título, tema as Género, fecha_estreno as Estreno, director as Director, actores as Actores, 0 as Dispo, imagen as Carátula from ejemplares join peliculas on id_peliculas=peliculas.id where ejemplares.id  in (select id_ejemplares from detalle_alquileres where fecha_devolucion is null) 
         order by Cod) as subcon group by Cod";
         consulta($select,'videoclub','videoclub','videoclub2008',null,"caratulas-8");
      } elseif ($consulta=="ejemplares") {
          //$select="select ejemplares.id as Cod,titulo as Título,tema as Género, fecha_estreno as Estreno,director as Director,actores as Actores,disponible as Disp from peliculas join ejemplares on peliculas.id=id_peliculas";
          //$select="select ejemplares.id as Cod,titulo as Título,tema as Género, fecha_estreno as Estreno,director as Director,actores as Actores,disponible as Disp from (peliculas join ejemplares on peliculas.id=id_peliculas) left join (detalle_alquileres join alquileres on id_alquileres=alquileres.id) on ejemplares.id=id_ejemplares where fecha_devolucion is null";
          $select="select * from (select cast(ejemplares.id as unsigned) as Cod, titulo as Título, tema as Género, fecha_estreno as Estreno, director as Director, actores as Actores, 1 as Disp from ejemplares join peliculas on id_peliculas=peliculas.id where ejemplares.id not in (select id_ejemplares from detalle_alquileres where fecha_devolucion is null) 
                   union
                   select cast(ejemplares.id as unsigned) as Cod, titulo as Título, tema as Género, fecha_estreno as Estreno, director as Director, actores as Actores, 0 as Disp from ejemplares join peliculas on id_peliculas=peliculas.id where ejemplares.id  in (select id_ejemplares from detalle_alquileres where fecha_devolucion is null) 
                   order by Cod) as subcon";
          consulta($select,'videoclub','videoclub','videoclub2008');
      } elseif ($consulta=="socios") {
          $select="select dni as DNI, ".nombreApellidos()." as Nombre, telefono as Teléfono,concat(".conIf("direccion").",'. ',".conIf("cp").",' - ',".conIf("ciudad").") as Dirección,saldo as Saldo from socios";
          consulta($select,'videoclub','videoclub','videoclub2008');
      } elseif ($consulta=="alquileres") {
          //$title="2,3,4,5,6,7,8";
          //$select="select dni as DNI,nombre as Nombre,apellido1 as Apellido1,apellido2 as Apellido2, titulo as Título, fecha_alquiler as Retirada, fecha_devolucion as Devolución from peliculas join (socios join (alquileres left join detalle_alquileres on id=id_alquileres) on dni=dni_socios) on id_ejemplares=peliculas.id";
          //$select="select alquileres.id as Id,dni as DNI,nombre as Nombre,concat(".conIf("apellido1").",' ',".conIf("apellido2").") as Apellidos, titulo as Título, fecha_alquiler as Retirada, fecha_devolucion as Devolución from (((alquileres left join detalle_alquileres on id=id_alquileres) join socios on dni=dni_socios) left join peliculas on id_ejemplares=peliculas.id)";
          //if(fecha_devolucion is NULL,NULL,timestampdiff(SECOND,fecha_alquiler,fecha_devolucion)*if(timestampdiff(SECOND,fecha_alquiler,fecha_devolucion)<=3*3600, tarifa3,if(timestampdiff(SECOND,fecha_alquiler,fecha_devolucion)<=6*3600, tarifa6,if(timestampdiff(SECOND,fecha_alquiler,fecha_devolucion)<=12*3600,tarifa12,(timestampdiff(SECOND,fecha_alquiler,fecha_devolucion) div (24*3600)+1)*tarifa24))))
          //$select="select alquileres.id as Id,dni as DNI, ".nombreApellidos()." as Socio, titulo as Título, fecha_alquiler as Retirada, fecha_devolucion as Devolución, if(fecha_devolucion is NULL,NULL,if(timestampdiff(SECOND,fecha_alquiler,fecha_devolucion)<=3*3600, tarifa3,if(timestampdiff(SECOND,fecha_alquiler,fecha_devolucion)<=6*3600, tarifa6,if(timestampdiff(SECOND,fecha_alquiler,fecha_devolucion)<=12*3600,tarifa12,(timestampdiff(SECOND,fecha_alquiler,fecha_devolucion) div (24*3600)+1)*tarifa24)))) as Importe from (((alquileres left join detalle_alquileres on alquileres.id=id_alquileres) join socios on dni=dni_socios) left join peliculas on id_ejemplares=peliculas.id) left join tarifas on alquileres.id_tarifas=tarifas.id";
          $select="select alquileres.id as Id,dni as DNI, ".nombreApellidos()." as Socio,cast(ejemplares.id as unsigned) as Cod, titulo as Título, fecha_alquiler as Retirada, fecha_devolucion as Devolución, if(fecha_devolucion is NULL,NULL,if(timestampdiff(SECOND,fecha_alquiler,fecha_devolucion)<=3*3600, tarifa3,if(timestampdiff(SECOND,fecha_alquiler,fecha_devolucion)<=6*3600, tarifa6,if(timestampdiff(SECOND,fecha_alquiler,fecha_devolucion)<=12*3600,tarifa12,(timestampdiff(SECOND,fecha_alquiler,fecha_devolucion) div (24*3600)+1)*tarifa24)))) as Importe from ((((alquileres left join detalle_alquileres on alquileres.id=id_alquileres) join socios on dni=dni_socios) left join ejemplares on id_ejemplares=ejemplares.id) join peliculas on ejemplares.id_peliculas=peliculas.id) left join tarifas on alquileres.id_tarifas=tarifas.id";
          
          //$select="select * from (alquileres left join detalle_alquileres on id=id_alquileres) join socios on dni=dni_socios";
          consulta($select,'videoclub','videoclub','videoclub2008');
      } elseif ($consulta=="tarifas") {
         if (esUsuario("admin")) {
            $select="select * from ((select id as Id,fecha as Fecha, tarifa3 as 3_Horas, tarifa6 as 6_Horas, tarifa12 as 12_Horas, tarifa24 as Días from tarifas order by fecha desc) as tabla1)";
         } else {
            $select="select * from (select fecha as Tarifa_Vigente_Desde, tarifa3 as 3_Horas, tarifa6 as 6_Horas, tarifa12 as 12_Horas, tarifa24 as Días from tarifas order by Fecha desc limit 1) as Tabla";
            echo '<p align="left" style="text-align:left"><h4>
            Los precios a continuación indican el pago que se realizará por película y por
            el período indicado. Si la película es devuelta en menos de 24 horas se la aplicarán
            las tarifas indicadas a continuación. A partir de las 24 primeras horas se cobrará
            por días completos.</p></h4>';
         }
         echo '<center><h3><u><b>Todos los precios están en Euros</b></u></h3></center>';
         consulta($select,'videoclub','videoclub','videoclub2008');
      } elseif ($consulta=="informes") {
         informes();
      } 
      
  } else {
      error("No está autorizado a ver esta página"); 
  }     
                        

  //  id,titulo,tema,fecha_estreno,director,actores,
?>



