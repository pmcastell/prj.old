<?php
  /*class altas {
     var $autoIncrement=false;
     function altas($tabla) {
     }
  }
    */
   if (!esUsuario("admin")) {
       error("No est&aacute; autorizado a ver esta p&aacute;gina.");
       exit();
   }
   include_once("funcionesAuxiliares.php");



   function sentenciaUpdate($camposClave=null) {
      $i=0;
      $set="";
      reset($_POST);
      $valor=$_GET['valorClave'];
      if ($camposClave==null) {
         $campoClave=key($_POST);
      }
      while(key($_POST) && !strstr(key($_POST),"___boton___") ) {
         $val=current($_POST);
         $clave=key($_POST);
         if ($val[2]=='-' && $val[5]=='-' && (strlen($val)==10 || strlen($val)==16)) { // es una fecha
            $val=substr($val,6,4).'-'.substr($val,3,2).'-'.substr($val,0,2).substr($val,10); //fecha a mysql
         } 
         next($_POST);
         $i++;
         $set.=$clave.'=';
         if ($val==null) {
            $set.="NULL";
         } else {
            $set.="'$val'";
         }
         if (key($_POST) && !strstr(key($_POST),"___boton___")) {
            $set.=',';
         }
      }
      return "UPDATE ".$_GET['destino']." SET ".$set." WHERE $campoClave='$valor'";
   }
   function ponerFormulario($camposClave) {
      if ($_GET['destino']) {
         $destino=$_GET['destino'];
         $foreignKeys=null;
         $longitudesCampos=null;
         $action=null;
         $valorClave=null;
         $funcValidacion=null;
         echo '<table width="100%"><tr align="center"><td width="50%" valign="top">';

         if ($destino=='socios') {
            $valorClave="dni";
            campoSelect("select dni as DNI, dni as DNI2, concat(".conIf("nombre").",' ',".conIf("apellido1").",' ',".conIf("apellido2").") as Nombre from socios order by apellido1,apellido2,nombre",
                        "seleccionRegistro",array(9,9,28),true,$_GET['valorClave'],true);
            

         } elseif ($destino=='alquileres') {
            $valorClave="id";
            $foreignKeys=array(null,
                               "select dni as DNI, dni as DNI2, concat(".conIf("nombre").",' ',".conIf("apellido1").",' ',".conIf("apellido2").") as Nombre from socios order by apellido1,apellido2,nombre",
                               null,
                               "select * from tarifas order by fecha desc");
            $longitudesCampos=array(null,array(9,9,28),null,array(10,10,6,6,6,6));
            campoSelect("select alquileres.id as ID,dni as DNI, concat(".conIf("nombre").",' ',".conIf("apellido1").",' ',".conIf("apellido2").") as Nombre, fecha_alquiler as Fecha from socios join alquileres on dni=dni_socios order by fecha desc",
                        "seleccionRegistro",array(10,9,28,16),true,$_GET['valorClave'],true);
              
         } elseif ($destino=='ingresos') {
            $valorClave="id";
            $foreignKeys=array(null,
                               "select dni as DNI, dni as DNI2, concat(".conIf("nombre").",' ',".conIf("apellido1").",' ',".conIf("apellido2").") as Nombre from socios order by apellido1,apellido2,nombre",
                               null,
                               null);
            $longitudesCampos=array(null,array(9,9,28),null,null);
            campoSelect("select ingresos.id as id, dni as DNI, ".nombreApellidos()." as Nombre, fecha, cantidad from ingresos join socios on dni_socios=dni order by fecha DESC",
            "seleccionRegistro",array(10,9,25,10,10),true,$_GET['valorClave'],true);
            
         } elseif ($destino=='tarifas') {
            $valorClave="id";
            campoSelect("select * from tarifas order by fecha desc",
                        "seleccionRegistro",array(10,10,6,6,6,6),true,$_GET['valorClave'],true);

         } elseif ($destino=='detalle_alquileres') {
            $valorClave="id";
            $foreignKeys=array(null,
                               "select alquileres.id as ID,dni as DNI, ".nombreApellidos()." as Nombre, fecha_alquiler as Fecha from socios join alquileres on dni=dni_socios order by fecha desc",
                               //"select ejemplares.id as ID, ejemplares.id as ID2, titulo as Título, actores as Actores from peliculas join ejemplares on id_peliculas=peliculas.id where disponible='1' order by Título",
                               //"select ejemplares.id as ID, ejemplares.id as ID2, titulo as Título, actores as Actores from peliculas join ejemplares on id_peliculas=peliculas.id where ejemplares.id not in (select id_ejemplares from detalle_alquileres where fecha_devolucion is null) order by Titulo",
                               //"select ejemplares.id as ID, ejemplares.id as ID2, titulo as Título, actores as Actores from peliculas join ejemplares on id_peliculas=peliculas.id order by Titulo",
                               "select ejemplares.id as ID, cast(ejemplares.id as unsigned) as ID2, titulo as Título, actores as Actores, 'Disp.'    as Disp from ejemplares join peliculas on id_peliculas=peliculas.id where ejemplares.id not in (select id_ejemplares from detalle_alquileres where fecha_devolucion is null) 
                   union
                                               select ejemplares.id as ID, cast(ejemplares.id as unsigned) as ID2, titulo as Título, actores as Actores, 'No Disp.' as Disp from ejemplares join peliculas on id_peliculas=peliculas.id where ejemplares.id  in (select id_ejemplares from detalle_alquileres where fecha_devolucion is null) 
                   order by Título,ID2",
                               null,
                               null);
            $longitudesCampos=array(null,
                                    array(10,9,28,16),
                                    array(13,19,25,20,8),
                                    null,
                                    null);
            $funcValidacion='
               function valorIdEjemplarCambiado() {
                  idEjemplar=document.getElementsByName("id_ejemplares")["id_ejemplares"];
                  for(i=0; i<idEjemplar.options.length; i++) {
                     if (idEjemplar.options[i].defaultSelected) {
                        break;
                     }
                  }
                  if (i<idEjemplar.options.length && i!=idEjemplar.selectedIndex) {
                     document.form'.$destino.'.action+="&id_ejemplaresAnt="+idEjemplar.options[i].value;
                  }
                  return true;
               }
            ';
            if ($pos=strpos(variablesGet(),"&id_ejemplaresAnt")) {
               $action="?".substr(variablesGet(),0,$pos);
            }
            campoSelect("select detalle_alquileres.id as id, alquileres.id as Id2, ".nombreApellidos()." as Socio, titulo as Título, fecha_alquiler as Retirada, fecha_devolucion as Devolución from ((((alquileres join detalle_alquileres on alquileres.id=id_alquileres) join socios on dni=dni_socios) join ejemplares on id_ejemplares=ejemplares.id) join peliculas on ejemplares.id_peliculas=peliculas.id) order by Retirada DESC",
                        "seleccionRegistro",
                        array(10,10,25,20,16,16),true,$_GET['valorClave'],true);
         } elseif ($destino=='ejemplares') {
            $valorClave="id";
                                  
            $foreignKeys=array(null,
                               "select peliculas.id as ID, titulo as Título, actores as Actores from peliculas order by Título",
                               null);
            $longitudesCampos=array(null,
                                    array(10,25,20),
                                    null);
                                                                      
            campoSelect("select ejemplares.id as Cod1, ejemplares.id as Cod, titulo as Título, actores as Actores from peliculas join ejemplares on id_peliculas=peliculas.id order by Título",
                        "seleccionRegistro",array(3,3,25,20),true,$_GET['valorClave'],true);
                               
         } elseif ($destino=='peliculas') {
            $valorClave="id";
            campoSelect("select id,titulo,actores from peliculas order by titulo",
                        "seleccionRegistro",array(10,25,25),true,$_GET['valorClave'],true);

         }
         botonesNavegacion();
         echo '<tr align="center"><td width="50%">';
         if (isset($_GET['valorClave']) && $_GET['valorClave']!=null) {
            //$valorClave.="='".$_GET['valorClave']."'";
            $valorClave=$camposClave[$_GET['destino']]."='".$_GET['valorClave']."'";
         } else {
            $valorClave=null;
         }
         //crearFormulario($destino,$destino,$foreignKeys,$longitudesCampos,$valorClave);
         formulario2($destino,$foreignKeys,$longitudesCampos,$valorClave,$action,array("Modificar","Borrar"),"POST",$funcValidacion);
      }
   }
         
            
    
      
   
   //////////////////////////////////////////////////////////////////////
   /////////////////////////////////////////////////////////////////////
   //////////////////////////////////////////////////////////////////////
   /////////////////////////////////////////////////////////////////////
   $camposClave=array('socios' => 'dni', 'alquileres' => 'id', 'ingresos' => 'id', 'tarifas' => 'id',
                      'detalle_alquileres' => 'id', 'ejemplares' => 'id', 'peliculas' => 'id');
                      
   if (!$_POST) {
      ponerFormulario($camposClave);
   } else {
      $conexion=abreBase('videoclub','videoclub','videoclub2008');
      $destino=$_GET['destino'];
      if ($conexion) {
         if($destino=='ingresos') {
            if ($result=mysql_query("select cantidad from ingresos where id='".$_GET['valorClave']."'")) {
               $fila=mysql_fetch_assoc($result);
               $cantidadAnterior=$fila['cantidad'];
            }
         } 
         if (isset($_POST['___boton___Borrar'])) { // BORRADO
            $consulta="delete from $destino where ".$camposClave[$destino]."='".$_GET['valorClave']."'";
            if (!mysql_query($consulta)) {
               error("Borrando Registro: ".$_GET['valorClave']);
               error("Consulta: $consulta. ".mysqlError());
            } else {
               //Acciones después de borrar
               if ($destino=='detalle_alquileres') {
                  if ($_POST['fecha_devolucion']) {
                     mysql_query("update detalle_alquileres join ejemplares on id_ejemplares=ejemplares.id set disponible=1 where ejemplares.id='".$_POST['id_ejemplares']."'");
                  }
               } elseif ($destino=='ingresos' && isset($cantidadAnterior)) {
                  mysql_query("update socios set saldo=saldo-".$cantidadAnterior." where dni='".$_POST['dni_socios']."'");
               }
               echo 'Registro Borrado Correctamente.<br>';
               historyBackLink("Volver");
            }

         } else { //         if (isset($_POST['___boton___Modificar'])) { // MODIFICACIÓN
            //Acciones antes de actuializar
            if ($destino=="detalle_alquileres") {
               //comprobar que el total de alquileres no superar
               //el coste de las películas sacadas por un período de 3 horas
               //$consulta="";
               /*if ($result=query("select id_ejemplares from detalle_alquileres where id='".$_POST['id']."'")) {
                  if ((string) $fila['id_ejemplares']!= (string)$_POST['id_ejemplares']) {
                     $id_ejemplaresAnt=$fila['id_ejemplares'];
                  }
               } */
               if (isset($_GET['id_ejemplaresAnt'])) {
                  error("Ha habido cambio de id_ejemplares.");
               }
            } 
            $consulta=sentenciaUpdate();
            if (!mysql_query($consulta)) {
               error("Error Modificando Registro: ".mysqlError());
               error("Consulta: $consulta");
            } else {
               //Accdiones después de actualizar
               if ($destino=='detalle_alquileres') {
                  /*if ($_POST['fecha_devolucion']) {
                     mysql_query("update detalle_alquileres join ejemplares on id_ejemplares=ejemplares.id set disponible=1 where ejemplares.id='".$_POST['id_ejemplares']."'");
                  } else {
                     mysql_query("update detalle_alquileres join ejemplares on id_ejemplares=ejemplares.id set disponible=0 where ejemplares.id='".$_POST['id_ejemplares']."'");
                     
                  } *
                  mysql_query("update ejemplares set disponible=1");
                  mysql_query("update detalle_alquileres join ejemplares on id_ejemplares=ejemplares.id set disponible=0 where fecha_devolucion is null");
                  */
                  if (isset($id_ejemplaresAnt)) {
                     query("update ejemplares set disponible=1 where id='$id_ejemplaresAnt'");
                     query("update ejemplares set disponible=0 where id='".$_POST['id']."'");
                  }
                  if (isset($_GET['id_ejemplaresAnt'])) {
                     error("Ha habido cambio de id_ejemplares.");
                  }
               } elseif ($destino=='ingresos') {
                  mysql_query("update socios set saldo=saldo-".(isset($cantidadAnterior)?$cantidadAnterior:0)."+".$_POST['cantidad']." where dni='".$_POST['dni_socios']."'");
               }
               echo '<script>document.location=document.location;</script>';
               //echo 'Registro Modificado Correctamente.<br>';
               //historyBackLink("Volver");
            }
         } 
      } else {
         error("Conectando con el Servidor de B.D.: ".mysqlError());
      }
   }
?>