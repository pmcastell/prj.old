<?php
   function factorial($n) {
      if ($n<=1) {
         return 1;
      } else {
         return n*factorial(n-1);
      }
   }

   function iva($cantidad,$tipo) {
      return $cantidad*$tipo/100;
   }
   function cabecera() {
      ?>
      <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web</title>
   </head>
   <body>
   <?php 
      
   }
   function historyBack($texto='Volver Atr&aacute;s',$num=1) {
      echo "<a href=\"javascript:history.back($num);\">$texto</a>";
   }
   function ordernaMatrices(&$alum,&$alt) {
      for($i=0;$i<count($alum)-1;$i++) {
         for($j=$i+1;$j<count($alum);$j++) {
            if ($alt[$i]>$alt[$j]) {
               $aux=$alt[$i];
               $alt[$i]=$alt[$j];
               $alt[$j]=$aux;
               $aux=$alum[$i];
               $alum[$i]=$alum[$j];
               $alum[$j]=$aux;
            }
         }
      }
   }
   function imrpimeArrayAsociativo($a) {
      foreach($a as $i => $v) {
         echo "<tr><td>$i</td><td>$v</td></tr>";
      }
   }
   
   function error($mensaje="Error",$errorMysql=true) {
      echo '<span style="color:red; font-size: 12pt;">',$mensaje,'<br />';
      if ($errorMysql) {
         echo mysql_errno(),': ',mysql_error();
      }
      echo '</span>';
   }   
   function errorNormal($mensaje) {
      error($mensaje,false);
   }
   
   function consulta($consu,$usuario="videoclub",$contrasenia="videoclub2011",$maquina="localhost") {
      $res=false;
      //pasos 1 al paso 3-2
      $conex=mysql_connect($maquina,$usuario,$contrasenia); // paso 1
      if ($conex==0) { //paso 2
         error("Error estableciendo conexion: $maquina");
      } else {
         if (!mysql_select_db($usuario)) {
            error("Error seleccionando base de datos $usuario");
         } else {
            $res=mysql_query($consu); //paso 3.1
            if ($res==0) {
               error("Error lanzando consulta: $consu."); //paso 3.2
            }
         }
      }
      return $res;
   }
   
   function consultaTabla($consulta,$usuario="videoclub",$contrasenia="videoclub2011",$maquina="localhost") {
      $res=consulta($consulta,$usuario,$contrasenia,$maquina);
       if ($res!=0) {
          $reg=mysql_fetch_assoc($res);
          if ($reg!=0) {
             echo '<table border="1">';
             echo '<tr>';   
             foreach($reg as $ind => $val) {
                echo "<th>$ind</th>";
             }
             echo '</tr>';
             while($reg!=0) {
                echo '<tr>';
                foreach($reg as $ind => $val) {
                   echo "<td>$val</td>";
                }
                echo '</tr>';
                $reg=mysql_fetch_assoc($res);
             }
          }
       }
   }
   function acceso($usuario="Admin",$mensaje="No estás Autorizado a ver este informe. Tu intento de acceso ha quedado registrado.<br />") {
      if (isset($_SESSION['tipoUsuario']) && $_SESSION['tipoUsuario']==$usuario) {
         return true;
      } else {
         echo $mensaje;
         echo "Tu dirección I.P. ",$_SERVER['REMOTE_ADDR'], " ha sido registrada.";
         return false;
      }
   }
   function productoMatricesBid($a,$b) {
      $filas=count($a);
      $cols=count($a[0]);
      if ($cols!=count($b)) { // si la primera no tiene el mismo num.de cols que filas la segunda
         return null; // error 
      }
      $c=array(array());
      for($i=0;$i<$filas;$i++) {
         for($j=0;$j<$cols;$j++) {
            $c[$i][$j]=0;
            for($k=0;$k<$cols;$k++) {
               $c[$i][$j]+=$a[$i][$k]*$b[$k][$j];
            }
         }
      }
      return $c;
   }
   function pedirArrayBid($nombre,$fils,$cols,$size=3) {
      for($i=0;$i<$fils;$i++) {
         echo "<tr>"; // empezamos una nueva fila
         for($j=0;$j<$cols;$j++) {
            echo "<td><input type=\"text\" name=\"$nombre","[",$i,"][]","\" size=\"$size\" maxlength=\"$size\" /></td>";
         }
         echo "</tr>"; // terminamos una fila
      }
   }
   function imprimeArrayBid($a,$border=0,$cellpadding=2,$cellspacing=2) {
      echo "<table border=\"$border\" cellpadding=\"$cellpadding\" cellspacing=\"$cellspacing\">";
      for($i=0;$i<count($a);$i++) {
         echo "<tr>";
         for($j=0;$j<count($a);$j++) {
            echo "<td>",$a[$i][$j],"</td>";
         }
         echo "</tr>";
      }
      echo "</table>";
   }
   function recargar($nuevaLoc="?",$tiempo=0) {
      header("refresh:$tiempo;url=$nuevaLoc");
   }
   //   <select name="anio" id="anio">
   function creaSelect($nombreVar,$valIni,$valFin,$seleccionado=null,$dosDigitos=false) {
      //echo '<select name="'.$nombreVAr.'" id="'.$nombreVar.'">';
      echo "<select name=\"$nombreVar\" id=\"$nombreVar\" >";//onChange=\"fechaCambio(fechaNacDia,fechaNacMes,fechaNacAnio);\" >";
   	for($valor=$valIni;$valor<=$valFin;$valor++) {
   	   if ($dosDigitos!=null && $valor<10) { 
   	      $opVal="0".$valor; 
   	   } else { 
   	      $opVal=$valor; 
   	   }
   	   if ($seleccionado!=null && $valor==(integer)$seleccionado) {
   	      echo '<option selected="selected">',$opVal,'</option>';
   	   } else {
            echo "<option>$opVal</option>\n";
   	   } 
      } 
      echo '</select>';
   }
   function pedirFecha($name,$label) {
      $primeraVez=true;
      if ($primeraVez) { ?>
         <script type="text/javascript">
	         function esBisiesto(anio) {
		         if (anio % 4 == 0) {
			         if (anio % 100 !=0 ) {
				         return true;
			         } else if (anio % 400 == 0) {
				         return true;
			         }
		         }
		         return false;
	         }
            function fechaCambio(dia,mes,anio) {
		         if (esBisiesto(anio.value) && mes.selectedIndex==1) {
			         ultimoDia=29;
		         } else {
			         ultimoDia=[31,28,31,30,31,30,31,31,30,31,30,31][mes.selectedIndex];
		         }
		         if (dia.length<ultimoDia) {
			         for(i=dia.length;i<ultimoDia;i++) {
				         dia.options[dia.length]=new Option(""+(dia.length+1),""+(dia.length+1));
			         }
		         } else if (dia.length>ultimoDia) {
			         selectedInd=dia.selectedIndex;
			         for(i=dia.length-1;i>=ultimoDia;i--) {
				         dia.remove(i);
			         }
			         if (selectedInd>i) {
				         dia.selectedIndex=i;
			         }
		         }
            }
         </script>
      <?php
      }

      $dia=date("d");
      $mes=date("m");
      $anio=date("Y");
      echo "<tr><td><label>$label</label></td><td>";
      creaSelect($name."Dia",1,31,$dia,true,true);
      echo "/";
      creaSelect($name."Mes",1,12,$mes,true,true);
      echo "/";
      creaSelect($name."Anio",$anio-100,$anio,$anio,true);
      echo "</td></tr>";
      //document.getElementById("fechaNacDia").onchange=function(){alert("hola");};
      echo '<script type="text/javascript">
               d=document.getElementById("',$name,'Dia");
               m=document.getElementById("',$name,'Mes");
               a=document.getElementById("',$name,'Anio");
               d.onchange=function() {fechaCambio(document.getElementById("',$name,'Dia"),
                                                  document.getElementById("',$name,'Mes"),
                                                  document.getElementById("',$name,'Anio"));};
               m.onchange=function () {fechaCambio(document.getElementById("',$name,'Dia"),
                                                  document.getElementById("',$name,'Mes"),
                                                  document.getElementById("',$name,'Anio"));};
               a.onchange=function() {fechaCambio(document.getElementById("',$name,'Dia"),
                                                  document.getElementById("',$name,'Mes"),
                                                  document.getElementById("',$name,'Anio"));};
               fechaCambio(document.getElementById("',$name,'Dia"),
                           document.getElementById("',$name,'Mes"),
                           document.getElementById("',$name,'Anio"));
            </script>';
      
   }
?>

<?php
   echo '<form action="/datos2.php" method="post">';
   echo '<table border="1">';
   pedirFecha("fechaNac","Fecha de Nacimiento: ");
   pedirFecha("fechaAlta","fecha de Alta: ");
   pedirFecha("fechaBaja","fecha de Baja: ");
   //echo '<tr><td><input type="button" value="Añadir elementos" onClick="anadir();" /></td></tr>';
   echo '<tr><td>Matriz de prueba</td><td><input type="text" name="fecha[\'dia\']" size="2" /></td>
             <td><input type="text" name="fecha[\'mes\']" size="2" /></td>
         </tr>';
   echo '<tr><td colspan="2"><input type="submit" value="Enviar" /></td></tr>';
   echo '</table>';
   echo '</form>';
?>
<!-- 
<script type="text/javascript">
   //document.getElementById("fechaNacDia").onChange="alerta('hola');";
   document.getElementById("fechaNacDia").onchange=function(){alert('hola');};
   document.write("vale: "+document.getElementById("fechaNacDia").onchange);
</script>
 -->
<!--   
      /*
   include_once("funcionesAuxiliares.php");
   //consultaTabla("SELECT nombre,ap1 AS Primer_apellido FROM CLIENTES LEFT JOIN ALQUILERES ON CLIENTES.cod=ALQUILERES.cod_CLIENTES");
   $res=consulta("SELECT COUNT(*) AS NUM_ANTONIOS FROM CLIENTES WHERE nombre LIKE '%Antonio%'");
   if ($res!=0) {
      $reg=mysql_fetch_assoc($res);
      echo "He encontrado ",$reg['NUM_ANTONIOS']," Antonios.";
      
   }
   $contador=0;
   if ($res!=0) {
      while($reg=mysql_fetch_assoc($res)) {
         if (buscaCadena($reg['nombre'],'Antonio')>=0) {
            $contador++;
         }
      }
   }
   echo "He encontrado $contador Antonios.";
   */
      
   
  /* $res=consulta("SELECT * FROM CLIENTES");
   if ($res!=0) {
      echo '<table border="1">';
      echo '<tr><th>Cod</th><th>Nombre Completo</th><th>Direcci&oacute;n</th><th>Tel&eacute;fono</th><th>saldo_24</th>
             <th>saldo_48</th><th>saldo_7</th></tr>';
      while($reg=mysql_fetch_assoc($res)) {
         echo '<tr><td>',$reg['cod'],'</td><td>',$reg['nombre'],' ',$reg['ap1'],' ',$reg['ap2'],'</td><td>',$reg['direccion'],
               '</td><td>',$reg['telefono'],'</td><td>',$reg['saldo_24'],'</td><td>',$reg['saldo_48'],'</td><td>',$reg['saldo_7'],
                '</td></tr>';
      }
      echo '</table>';
      
      
   }*/
   
-->  


   
