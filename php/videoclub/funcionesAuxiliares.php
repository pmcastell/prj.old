<?php
   
   function error($mensaje,$color='red') {
      echo '<center><font color="'.$color.'"><br><b>',$mensaje,'</b><br></font></center>';
   }
   function mysqlError() {
      return mysql_errno().": ".mysql_error();
   }
   function abreBase($base,$usu,$pass,$maquina="localhost") {
      $conexion=mysql_connect($maquina,$usu,$pass); 
      if (!$conexion) {
          error("No se ha podido conectar con $maquina. ".mysqlError());
          return false;
      }
      $result=mysql_selectdb($base);
      if (!$result) {
          error("No se ha podido seleccionar base datos: $base. ".mysqlError());
          return false;
      }
      return $conexion;
   }
   function query($consulta,$conexion=null) {
      if (!($conexion=abreBase('videoclub','videoclub','videoclub2008'))) {
         error("Error conectando con el servidor de B.D.: ".mysqlError());
      } else {
         if ($result=mysql_query($consulta)) {
            return $result;
         } else {
            error("Error en consulta:  $consulta ==> ".mysqlError());
         }
      }
      return false;
   }
   function fechaMysql($Fecha)   {
      if ($Fecha<>""){
         $trozos=explode("/",$Fecha,3);
         return "'".$trozos[2]."-".$trozos[1]."-".$trozos[0]."'"; 
      } else {
          return "NULL";
      }
   }
   
   function mysqlFecha($fecha)
   {
      if ($fecha == "" || $fecha == "0000-00-00" || $fecha==null) {
          return "";
      }  else {
         return date("d/m/Y",strtotime($fecha));
      }
   }
   /*
   function ponFecha($nombreCampo="",$anioComienzo=null) {
      
      echo '<select name="',$nombreCampo,'Dia">';
      for($i=1; $i<=31; $i++) {
         echo "<option>$i</option>";
      }
      echo '/';
      echo '<select name="',$nombreCampo,'Mes">';
      for($i=1; $i<12; $i++) {
         echo "<option>$i</option>";
      }
      echo '/';
      if ($anioComienzo==null) {
         $anioComienzo=date(
      for($i=$anio-10; $i<=anio+
      }
   } */
   
   function listaOpciones($desde,$hasta,$seleccionar=null) {
      for($i=$desde; $i<=$hasta; $i++) {
         echo '<option';
         if ($seleccionar!=null && $i==$seleccionar) {
            echo ' selected>';
         } else {
            echo '>';
         }
         if ($i<10) {
            echo "0$i";
         } else {
            echo $i;
         }
         echo '</option>';
      }
   }

   function selectFecha($nombre,$fechaSeleccionada=null,$anioComienzo=null,$primerValorBlanco=false) {
      if (gettype($fechaSeleccionada)=='string') {
         $anio=substr($fechaSeleccionada,0,4);
         $mes=substr($fechaSeleccionada,5,2);
         $dia=substr($fechaSeleccionada,8,2);
         $fechaSeleccionada=getdate(mktime(0,0,0,$mes,$dia,$anio));
      }

      echo '<select id="',$nombre,'Dia" name="',$nombre,'Dia" size="1">';
      if ($primerValorBlanco) {
         echo '<option value="">&nbsp</option>';
      }
      if ($fechaSeleccionada!=null) {
         listaOpciones(1,31,$fechaSeleccionada['mday']);
      } else {
         listaOpciones(1,31,null);
      }
      echo '</select>';
      echo '<select id="',$nombre,'Mes"  name="',$nombre,'Mes" size="1">';
      if ($primerValorBlanco) {
         echo '<option value="">&nbsp</option>';
      }
      if ($fechaSeleccionada!=null) {
         listaOpciones(1,12,$fechaSeleccionada['mon']);
      } else {
         listaOpciones(1,12,null);
      }
      echo '</select>';
      echo '<select id="',$nombre,'Anio" name="'.$nombre.'Anio" size="1">';
      if ($primerValorBlanco) {
         echo '<option value="">&nbsp</option>';
      }
      if ($fechaSeleccionada!=null) {
         listaOpciones($fechaSeleccionada['year']-10,$fechaSeleccionada['year']+10,$fechaSeleccionada['year']);
      } else {
         $fechaSeleccionada=getDate();
         if ($anioComienzo!=null) {
            listaOpciones($anioComienzo,$fechaSeleccionada['year']+30,null);
         } else {
            listaOpciones($fechaSeleccionada['year']-15,$fechaSeleccionada['year']+35,null);
         }
      }
      echo '</select>';
   }
   function selectHora($nombre,$horaSeleccionada=null,$primerValorBlanco=false) {
      /*if ($horaSeleccionada==null) {
         $horaSeleccionada=date('H:i');
      } */
      echo '<select id="',$nombre,'Hora" name="',$nombre,'Hora" size="1">';
      if ($primerValorBlanco) {
         echo '<option value="">&nbsp</option>';
      }
      if ($horaSeleccionada!=null) {
         listaOpciones(0,23,substr($horaSeleccionada,0,2));
      } else {
         listaOpciones(0,23,null);
      }
      echo '</select>';
      echo ':';
      echo '<select id="',$nombre,'Minutos"  name="',$nombre,'Minutos" size="1">';
      if ($primerValorBlanco) {
         echo '<option value="">&nbsp</option>';
      }
      if ($horaSeleccionada!=null) {
         listaOpciones(0,59,substr($horaSeleccionada,3,2));
      } else {
         listaOpciones(0,59,null);
      }
      echo '</select>';
   }
   function fechaAlReves($fecha,$quitarSegundos=true) {
      //01-01-2007 13:55:55
      //2007-01-01 13:55:55
      
      $res=null;
      if (strlen($fecha)==10 || strlen($fecha)==16 || strlen($fecha)==19) {
         $separadores='-/.';
         $c4=$fecha[4]; $c7=$fecha[7];
         if ($c4==$c7 && strstr($separadores,$c4)) {
            $res=substr($fecha,8,2).$c4.substr($fecha,5,2).$c4.substr($fecha,0,4);
         } else {
            $c2=$fecha[2]; $c5=$fecha[5];
            if ($c2==$c5 && strstr($separadores,$c2)) {
               $res=substr($fecha,6,4).$c2.substr($fecha,3,2).$c2.substr($fecha,0,2);
            }
         }
         if ($quitarSegundos) {
            $res.=substr($fecha,10,6);
         } else {
            $res.=substr($fecha,10);
         }
      }
      return $res;
   }
   
   function variablesGet() {
     $res="";
     $i=0;
     foreach($_GET as $ind => $val) {
        $res.="$ind=$val";
        if ($i<count($_GET)-1) {
           $res.="&";
        }
        $i++;
     }
     return $res;
  }
  function capitaliza($cadena) {
     $result=$cadena;
     $result[0]=strtoupper($result[0]);
     return $result;
  }
  
  function escribeFuncionValidarDni() {
     echo '
         function esDigito(c) {
            return \'0\' <= c && c <=\'9\';
         }  
         function validarDni(dni) { 
            if (dni.value.length!=9) {
               alert("Error en D.N.I.: debe constar de 8 dígitos y una letra sin espacios");
               dni.focus();
               return false;
            }
            dni.value=dni.value.toUpperCase();
            cadena="TRWAGMYFPDXBNJZSQVHLCKE";
            for(i=0; i<8 && i<dni.value.length; i++) {
               if (! esDigito(dni.value.charAt(i))) {
                  alert("Error en D.N.I.: debe constar de 8 dígitos y una letra sin espacios");
                  dni.focus();
                  return false;
               }
            }
            resto = eval(dni.value.substr(0,8)) % 23;
            letra = cadena.charAt(resto);
            letraDni=dni.value.charAt(dni.value.length-1);
            if (letra!=letraDni) {
               alert("Error en letra D.N.I.. Debería ser: "+letra);
               dni.focus();
               return false;
            } else {
               return true;
            }
         } 
         ';
  }
  function historyBackLink($mensa) {
     echo '<a title="Volver atrás" href="javascript:history.back();">&nbsp;',$mensa,'</a>';
  }
  function calendarFecha($campo,$fechaInicial=null,$noNulo=false,$formato="%d-%m-%Y",$maxLong="10") {
     echo '<input ';
     if ($noNulo) {
        echo ' id="noNull',$campo,'" ';
     } else {
        echo ' id="',$campo,'" ';
     }
     if ($fechaInicial!=null) {
         if (substr($formato,0,8)=="%d-%m-%Y" && is_numeric($fechaInicial[2])) {
            $fechaInicial=fechaAlReves($fechaInicial);
         } elseif (substr($formato,0,8)=="$Y-%m-%d" && is_numeric($fechaInicial[4])) {
            $fechaInicial=fechaAlReves($fechaInicial);
         }
         //echo ', date : new Date("',fechaAlReves($fechaInicial),'")';
         echo ' value="',$fechaInicial,'" ';
      }
      echo ' align="right" type="text" size="',$maxLong,'" maxlength="',$maxLong,'" name="',$campo,'" readonly />
      <input value="..." type="submit" id="',$campo,'-button">
      <script type="text/javascript">
         Calendar.setup({inputField:"',($noNulo ? "noNull".$campo : $campo),'",button:"',$campo,'-button",
         align : "Tr", daFormat : "',$formato,'", ifFormat: "',$formato,'"';
 
      echo '});
      </script>
      ';
  }
  function calendarFechaHora($campo,$fechaInicial=null,$noNulo=false,$formato="%d-%m-%Y %H:%M",$maxLong="16") {
      calendarFecha($campo,$fechaInicial,$nulo,$formato,$maxLong);
  }
  function ajustaTam($cadena, $long,$carPad="&nbsp;") {
     $dif=$long-strlen($cadena);
     if ($dif>0) {
        for($i=1; $i<=$dif; $i++) {
           $cadena.=$carPad;
        }
     } else {
        return substr($cadena,0,$long);
     }
     return $cadena;
  }
  function functionName($script) {
     $posInicio=strpos($script,"function")+9;
     $posFin=strpos($script,"(",posInicio+1);
     return substr($script,$posInicio,$posFin-$posInicio);
  }
  function campoSelect($consulta,$campo,$longitudesCampos=null,$primerValorBlanco=true,$seleccionado=null,$script=false) {
      $primerValor=null;  
      if ($result=query($consulta,&$conexion)) {
         if ($script) {
            $variablesGet=variablesGet();
            $pos=strpos($variablesGet,"&valorClave");
            if ($pos>0) {
               $variablesGet=substr($variablesGet,0,$pos);
            }
            echo '<script>
                        function ',$campo,'Cambia() { 
                           lista=document.getElementById("',$campo,'");
                           document.location="?',$variablesGet,'&valorClave="+lista.options[lista.selectedIndex].value;
                           //alert(document.location);
                        }
                 </script>';
           echo '<select onChange="',$campo,'Cambia();" ';
         } else {
           echo '<select ';
         }
         echo ' class="monoFuente" id="',$campo,'" name="',$campo,'">';
         if ($primerValorBlanco) {
            echo '<option value="">&nbsp</option>',"\n";
         }
         while($fila=mysql_fetch_assoc($result)) {
           reset($fila);
           $valor=current($fila);
           if ($primerValor=null) {
              $primerValor=$valor;
           }
           echo '<option value="',$valor,'"';
           if ($seleccionado==$valor) {
              echo ' selected ';
           }
           echo ">";
           next($fila); //saltamos el primer campo que va en el value
           $i=1;
           while(current($fila)) {
              if ($longitudesCampos!=null && $longitudesCampos[$i]!=null) {
                 echo ajustaTam(current($fila),$longitudesCampos[$i]);
              } else {
                 echo current($fila);
              }
              if (next($fila)) {
                 echo '&nbsp;|&nbsp;';
              }
              $i++;
           }
           echo '</option>',"\n";
         }
         echo '</select>',"\n";
         mysql_free_result($result);
         mysql_close($conexion);
      }
      return $primerValor;
  }
  function conIf($elemento) {
     return "if($elemento is NULL,\"\",$elemento)";
  }
  function cadenaConcat($elementos,$sepadores=null) {
     $result="";
     for($i=0; $i<count($elementos); $i++) {
        //$result.='if('.$elementos[$i].' is NULL,"",'.$elementos[$i].')';
        $result.=conIf($elementos[$i]);
        if ($separadores!=null && $i<count($elementos)-1) {
           $result.=$separadores[$i];
        }
     }
     return $result;
  }

  function extraeLongitud($cadena) {
      for($i=0; $i<strlen($cadena); $i++) {
         if (is_numeric($cadena[$i])) {
            $valor="";
            while(is_numeric($cadena[$i])) {
               $valor.=$cadena[$i];
               $i++;
            }
            if ($cadena[$i]==',' && is_numeric($cadena[$i+1])) {
               $valor+=extraeLongitud(substr($cadena,$i+1));
            } 
            return (integer) $valor;
         }
      }
      return null;
  } 
  
  function formulario($name,$campos,$tipos,$autoIncrementos,$foreignKeys=null,$longitudesCampos=null,$titulo="",$action="?",$values=null,$botones=null,$method="POST",$funcValidacion=null) {
      if ($funcValidacion!=null) {
         echo "<script>$funcValidacion</script>";
      }
      for($i=0; $i<count($tipos); $i++) {
          if (strstr('datetime',strtolower($tipos[$i]))) {
               echo '<script type="text/javascript" src="calendario/calendar.js"></script>
               <script type="text/javascript" src="calendario/calendar-setup.js"></script>
               <script type="text/javascript" src="calendario/lang/calendar-es.js"></script>
    <link rel="stylesheet" type="text/css" media="all" href="calendario/skins/aqua/theme.css" title="Aqua" />';
          }
      }
      echo '
      <form name="',$name,'" action="',$action,'" method="',$method,'">';
      echo '<table>
         <tr><th colspan="2">',capitaliza($titulo);
      for($i=0; $i<count($campos); $i++) {
         if (!$autoIncrementos[$i]) {
            echo '<tr><td>',capitaliza($campos[$i]),'<td>';
         }
         $tipo=strtolower($tipos[$i]);
         if ($foreignKeys!=null && $foreignKeys[$i]!=null) {
            campoSelect($foreignKeys[$i],$campos[$i],$longitudesCampos[$i],false,($values && $values[$i] ? $values[$i] : null));
         } elseif (strstr('datetime',$tipo)) {
            if ($tipo=='datetime') {
                calendarFechaHora($campos[$i],($values && $values[$i] ? $values[$i] : null));
            } else {
                calendarFecha($campos[$i],($values && $values[$i] ? $values[$i] : null));
            }
         
         } else if($tipo=='tinyint(1)') {
            echo '<select name="',$campos[$i],'">
                     <option value="1" selected>Si</option>
                     <option value="0">No</option>
                  </select>';   
         } else  {
            $nombreCampo=strtolower($campos[$i]);
            if (strstr($nombreCampo,"dni") || strstr($nombreCampo,"d.n.i.")) {
               echo '<script>';
               escribeFuncionValidarDni();
               echo '</script>';
               echo '<input onChange="return validarDni(document.',$name,'.',$campos[$i],');" ';
            } else {
               echo '<input ';
            }
            if ($autoIncrementos[$i]) {
               echo 'type="hidden" value="NULL" ';
            } else {
               $size=$maxLong=extraeLongitud($tipo);
               if ($size>70) {
                  $size=70;
               }
               echo 'type="text" size="',$size,'" maxlength="',$maxLong,'"';
            }
            echo ' checked="true" id="',$campos[$i],'" ';
            echo ' name="',$campos[$i],'" ';
            if ($values!=null && $values[$i]!=null) {
               echo ' value="',$values[$i],'" ';
            }
            echo '>';
         }
      }

      echo '<tr><td colspan="2" align="center"><input type="submit" value="Enviar" ';
      if ($funcValidacion!=null) {
         $posInicio=strpos($funcValidacion,"function")+9;
         $posFin=strpos($funcValidacion,"(",posInicio+1);
         $nombreFuncValidacion=substr($funcValidacion,$posInicio,$posFin-$posInicio);
         if ($nombreFuncValidacion!=null && $nombreFuncValidacion!="") {
            echo 'onSubmit="return',$name,'Validar();" ';
         }
      }
      echo '>&nbsp;&nbsp;<input type="reset" value="Valores Iniciales">';
      echo '</table>';
      echo '</form>';
   }
   function formularioBajaModif($name,$campos,$tipos,$autoIncrementos,$foreignKeys=null,$longitudesCampos=null,$titulo="",$action="?",$values=null,$botones=array("Guardar","Borrar"),$method="POST",$funcValidacion=null) {
      formulario($name,$campos,$tipos,$autoIncrementos,$foreignKeys,$longitudesCampos,$titulo,$action,$values,$botones,$method,$funcValidacion);
   }
   function formularioAlta($name,$campos,$tipos,$autoIncrementos,$foreignKeys=null,$longitudesCampos=null,$titulo="",$action="?",$values=null,$botones=array("Guardar"),$method="POST",$funcValidacion=null) {
      formulario($name,$campos,$tipos,$autoIncrementos,$foreignKeys,$longitudesCampos,$titulo,$action,$values,$botones,$method,$funcValidacion);
   }
     
   function crearFormulario($tabla,$titulo="",$foreignKeys=null,$longitudesCampos=null,$valorClave=null) {
      $result=query("show fields from ".$tabla);
      if ($result) {
         $campos=array();
         $tipos=array();
         $clavesAjenas=array();
         $autoIncrementos=array();
         
         for($i=0;$fila=mysql_fetch_array($result);$i++) {
            $campos[$i]=$fila['Field']; 
            $tipos[$i]=$fila['Type'];
            $clavesAjenas[$i]=$fila['Key'];
            if ($fila['Extra']=='auto_increment') {
               $autoIncrementos[$i]=true;
            } else {
               $autoIncrementos[$i]=false;
            }
         }
         $result=mysql_query("show create table ".$tabla);
         $fila=mysql_fetch_array($result);
         $fila=$fila['Create Table'];
         //for($i=0,$pos=0;$pos=strpos("$fila","FOREIGN KEY",$pos);$i++) {
         //}  
         $valores=null; 
         if ($valorClave) {
            $consu="select * from $tabla where $valorClave";
            error($consu);
            $result=query($consu);
            if (mysql_num_rows($result)!=1) {
               error("Función crearFormulario campo clave repetido: $consu.");
            }
            $valores=mysql_fetch_row($result);
         }
             
         formulario($tabla,$campos,$tipos,$autoIncrementos,$foreignKeys,$longitudesCampos,$titulo,"?".variablesGet(),$valores);
      } else {
         error("No se ha podido determinar la estructura de la tabla: ".mysqlError());
      }
   }
   
   function includeCalendar() {
                     echo '<script type="text/javascript" src="calendario/calendar.js"></script>
               <script type="text/javascript" src="calendario/calendar-setup.js"></script>
               <script type="text/javascript" src="calendario/lang/calendar-es.js"></script>
    <link rel="stylesheet" type="text/css" media="all" href="calendario/skins/aqua/theme.css" title="Aqua" />';
   }
   function selectSiNo($name,$valorSeleccionado="Si") {
      echo '<select name="',$name,'">
         <option value="1" ';
         if (strtolower($valorSeleccionado)=="si" || $valorSeleccionado=="1" 
             || strtolower($valorSeleccionado)=='true') {
             echo ' selected ';
         }
         echo '>Si</option>
         <option value="0"';
         if (strtolower($valorSeleccionado)=="no" || $valorSeleccionado=="0" 
             || strtolower($valorSeleccionado)=='false') {
             echo ' selected ';
         }         
         echo '>No</option>
      </select>';   
   }
   function javaScriptValidarCamposNoNulos($form) {
      echo '<script>
              function validarCamposNoNulos() {
                 elementos=document.',$form,'.elements;
                 for(i=0; i<elementos.length; i++) {
                    if (elementos[i].id.substr(0,6)=="noNull" && (elementos[i].value=="" || elementos[i].value==null)) {
                       alert("El campo "+elementos[i].name+" no puede ser nulo.");
                       return false;
                    }
                 }
                 return true;
              }
            </script>';         
   }


   function formulario2($tabla,$foreignKeys=null,$longitudesCampos=null,$valorClave=null,$action=null,$botones=null,$method="POST",$funcValidacion=null) {
      $values=null; 
      if ($valorClave) {
         $consu="select * from $tabla where $valorClave";
         $result=query($consu);
         /*if (mysql_num_rows($result)!=1) {
            error("Función crearFormulario campo clave repetido: $consu.");
         } */
         $values=mysql_fetch_row($result);
      }      
      if (!$result=query("show fields from ".$tabla)) {
         error("Función formulario: no se pudo obtener estructura tabla: $tabla-->".mysqlError());
         return;
      }
      if ($funcValidacion!=null) {
         echo "<script>$funcValidacion</script>";
      }
      if ($action==null) {
         $action="?".variablesGet();
      }

      $includeCalendar=false;
      javaScriptValidarCamposNoNulos("form".$tabla);
      echo '<form name="form',$tabla,'" action="',$action,'" method="',$method,'"  onSubmit="return validarCamposNoNulos() ';
      if ($funcValidacion!=null) {
         $posInicio=strpos($funcValidacion,"function")+9;
         $posFin=strpos($funcValidacion,"(",posInicio+1);
         $nombreFuncValidacion=substr($funcValidacion,$posInicio,$posFin-$posInicio);
         if ($nombreFuncValidacion!=null && $nombreFuncValidacion!="") {
            echo " && $nombreFuncValidacion() ";
         }
      }
      echo ';">';
      echo '<table><tr><th colspan="2">',capitaliza($tabla);
      for($i=0; $fila=mysql_fetch_assoc($result); $i++) {
         if ($fila['Extra']!='auto_increment') {
            echo '<tr><td>',capitaliza($fila['Field']),'<td>';
         }
         $tipo=strtolower($fila['Type']);
         if ($foreignKeys!=null && $foreignKeys[$i]!=null) {                  
            campoSelect($foreignKeys[$i],$fila['Field'],$longitudesCampos[$i],($fila['Null']!="NO"),($values && $values[$i] ? $values[$i] : null));
         } elseif (strstr('datetime',$tipo)) {
            if (!$includeCalendar) {
               echo '<div>';
               includeCalendar();
               echo '</div>';
               $includeCalendar=true;
            }
            if ($tipo=='datetime') {
                calendarFechaHora($fila['Field'],($values && $values[$i] ? fechaAlReves($values[$i]) : null),($fila['Null']=="NO"));
            } else {
                calendarFecha($fila['Field'],($values && $values[$i] ? fechaAlReves($values[$i]) : null),($fila['Null']=="NO"));
            }
         } else if($tipo=='tinyint(1)') {
            if ($values && isset($values[$i])) { 
               selectSiNo($fila['Field'],$values[$i]);
            } else {
               selectSiNo($fila['Field']);
            }
         } else  {
            $nombreCampo=strtolower($fila['Field']);
            if (strstr($nombreCampo,"dni") || strstr($nombreCampo,"d.n.i.")) {
               echo '<script>';
               escribeFuncionValidarDni();
               echo '</script>';
               echo '<input onChange="return validarDni(document.form',$tabla,'.',$fila['Field'],');" ';
            } else {
               echo '<input ';
            }
            if ($fila['Extra']=='auto_increment') {
               echo 'type="hidden" value="',($values && $values[$i] ? $values[$i] : "NULL"),'" ';
            } else {
               $size=$maxLong=extraeLongitud($tipo);
               if ($size>70) {
                  $size=70;
               }
               echo 'type="text" size="',$size,'" maxlength="',$maxLong,'"';
            }
            echo ' name="',$fila['Field'],'" ';
            if ($fila['Null']=="NO") {
               echo ' id="noNull'.$fila['Field'].'" ';
            }  else {
               echo ' id="'.$fila['Field'].'" ';
            }
            if ($values!=null && $values[$i]!=null) {
               echo ' value="',$values[$i],'" ';
            }
            echo ' />';
         }
      }
      echo '<tr><td colspan="2" align="center">';
      if ($botones) {
         foreach($botones as $boton) {
            echo '<input type="submit" name="___boton___',$boton,'" value="',$boton,'">&nbsp;&nbsp;';
         }
      } else {
         echo '<input type="submit" value="Enviar">&nbsp;&nbsp;';
      }

      echo '<input type="reset" value="Valores Iniciales">';
      echo '</table>';
      echo '</form>';
      echo '<script>elementos=document.form',$tabla,'.elements; for(i=0; elementos[i].type=="hidden"; i++); elementos[i].focus();</script>';
   }
   function nombreApellidos() {
      return 'concat('.conIf("nombre").",' ',".conIf("apellido1").",' ',".conIf("apellido2").')';
   }
   function saldo($dniCliente) {
      if ($result=query("select saldo from socios where dni='$dniCliente'")) {
         $fila=mysql_fetch_assoc($result);
         return $fila['saldo'];
      } else {
         error("Obteniendo saldo del cliente: $dniCliente");
      }
      return null;
   }
         
   function costeAlquiler($codigoAlquiler) {
      
   }
   function espacios($n) {
      $result="";
      for($i=0; $i<$n; $i++) {
         $result.="&nbsp;";
      }
      return $result;
   }
   function botonesNavegacion($seleccionRegistro="seleccionRegistro") {
         echo espacios(2);
         echo '<input type="button" value="<<||"  title="Primer Registro"
         onClick="selector=document.getElementById(\'',$seleccionRegistro,'\'); if (selector.selectedIndex>1) { selector.selectedIndex=1; ',$seleccionRegistro,'Cambia();} else { alert(\'Ya está en el primer registro\');}; " />';         
         echo espacios(2);
         echo '<input type="button" value="<<"  title="Anterior Registro"
         onClick="selector=document.getElementById(\'',$seleccionRegistro,'\'); if (selector.selectedIndex>1) { selector.selectedIndex--; ',$seleccionRegistro,'Cambia();} else { alert(\'Primer Registro Alcanzado\');}; " />';
         echo espacios(2);
         echo '<input type="button" value=">>" title="Siguiente Registro"
         onClick="selector=document.getElementById(\'',$seleccionRegistro,'\'); if (selector.selectedIndex<selector.options.length-1) { selector.selectedIndex++; ',$seleccionRegistro,'Cambia();} else { alert(\'Último Registro Alcanzado\');}; " />';
         echo espacios(2);
         echo '<input type="button" value="||>>"  title="Último Registro"
         onClick="selector=document.getElementById(\'',$seleccionRegistro,'\'); if (selector.selectedIndex<selector.options.length-1) { selector.selectedIndex=selector.options.length-1; ',$seleccionRegistro,'Cambia();} else { alert(\'Ya está en el último registro\');}; " />';         
         echo espacios(2);
         echo '<label title="Escriba cualquier cosa y pulse intro">Buscar: </label>';
         echo '<input type="text" id="busqueda" name="busqueda" size="10" maxlength="30" onChange="buscar();" />';         
         echo '<script>
                  function buscar() {
                     selector=document.getElementById("',$seleccionRegistro,'");
                     cadena=document.getElementById("busqueda").value.toLowerCase();
                     for(i=0; i<selector.options.length; i++) {
                        if (selector.options[i].text.toLowerCase().indexOf(cadena)>=0) {
                           selector.selectedIndex=i;
                           ',$seleccionRegistro,'Cambia();
                           return;
                        }
                     }
                     alert("No se ha encontrado ningún registro con ese texto");
                  }
               </script>';
   }
   function nombreMes($mes) {
      $meses=array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre",
                   "Noviembre","Diciembre");
      if (1<=$mes && $mes<=12) {
         return $meses[$mes-1];
      }
      return null;
   }
   function nombreMesAbrev($mes) {
      return substr(nombreMes($mes),0,3);
   }
   function buscaFechaFiesta($dia,$mes,$anio,$fiestas) {
      $fecha=date("d/m/Y",mktime(0,0,0,$mes,$dia,$anio));
      return $fiestas[$fecha];
   }
      
   function calendarioMes($mes,$anio,$fiestas=null) {
      $diaSem=date("w",mktime(0,0,0,$mes,1,$anio));
      $diaActual=-1;
      if (date("n")==(integer)$mes && date("Y")==(integer)$anio) {
         $diaActual=(integer)date("j");
      }
      if ($diaSem==0) {
         $diaSem=7;
      }
      $diasMes=date("t",mktime(0,0,0,$mes,1,$anio));
      echo '<b>',nombreMes($mes)," ",$anio,'</b>';
      echo '<table border>',"\n";
      echo "<tr bgcolor=\"gray\"><th>Lun</th><th>Mar</th><th>Mie</th><th>Jue</th><th>Vie</th><th>Sab</th><th>Dom</th></tr>\n";
      echo "<tr align=\"right\">\n";
      for($dia=1;$dia<$diaSem;$dia++) {
         echo "<td>\n</td>\n";
      }
      for($dia=1; $dia<=$diasMes; $dia++,$diaSem++) {
         if ($diaActual==$dia) {
            $celda='th';
         } else {
            $celda='td';
         }
         echo "<$celda";
         if ($fiestas!=null && $titulo=buscaFechaFiesta($dia,$mes,$anio,$fiestas)) {
            echo ' bgcolor="#ff00ff" title="',$titulo,'"';
         } elseif ($diaSem==6 || $diaSem==7) {
            echo ' bgcolor="#00ffff"';
         }
         echo ">$dia</$celda>";
         if ($diaSem==7) {
            $diaSem=0;
            echo '</tr><tr align="right">',"\n";
         }
      }
      echo '</tr></table>';
   }
   
   function calendario($mesIni,$mesFin=null,$anioIni=null,$anioFin=null,$ancho=4,$fiestas=null) {
      if ($anioIni==null) {
         $anioIni=date("Y");
      }
      if ($anioFin==null) {
         $anioFin=$anioIni;
      }
      if ($mesFin==null) {
         $mesFin=$mesIni;
      }
      echo '<table border align="center">';
      echo '<tr valign="top" align="right">';
      for($cols=1;$mesIni<=$mesFin || $anioIni<$anioFin; $mesIni++,$cols++) {
         if ($mesIni>12) {
            $mesIni=1; $anioIni++;
            echo '</tr><tr valign="top" align="right">';
            $cols=1;
         }
         echo '<td>';
         calendarioMes($mesIni,$anioIni,$fiestas);
         echo '</td>';
         if ($cols==$ancho) {
            $cols=0;
            echo '</tr><tr valign="top" align="right">';
         }
      }
      echo '</table>';
   }
   if (isset($_GET['mesIni'])) {
      calendario($_GET['mesIni'],$_GET['mesFin'],$_GET['anioIni'],$_GET['anioFin']);
   }
   
   //calendario(8,6,1965,2007,4,array("06/08/1965"=>"Nací","07/03/1985"=>"Nació la gorda","23/06/2002"=>"Una gorda no me deja en paz"));
      
   
      
         
   
        
?>
