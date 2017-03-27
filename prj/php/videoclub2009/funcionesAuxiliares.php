<?php
	//Escribe una lista de selección sencilla con valores comprendidos entre $ini y $fin
	function escribeListaValores($campo,$ini,$fin,$seleccionado=1,$incremento=1,$relleno="0") {
		echo '<select name="',$campo,'">\n';
		for($i=$ini; ($incremento >=0 ? $i<=$fin : $i>=$fin) ; $i+=$incremento) {
			if ($i<10) {
				$val=$relleno.$i;
			} else {
				$val=$i;
			}			
			if ($i==$seleccionado) {
				echo "<option selected=\"selected\">$val</option>\n";
			} else {
				echo "<option>$val</option>\n";
			}
		}
		echo '</select>';
	}
	//Escribe tres listas de selección para introducir el día-mes-anio
	function escribeFecha($fecha=null,$nombreCampoDia="dia",$nombreCampoMes="mes",$nombreCampoAnio="anio") {
		if ($fecha==null) {
			$fecha=date("d-m-Y");
		}
		$dia=substr($fecha,0,2);
		$mes=substr($fecha,3,2);
		$anio=substr($fecha,6,4); 
		escribeListaValores($nombreCampoDia,1,31,$dia);
		echo "-";
		escribeListaValores($nombreCampoMes,1,12,$mes);
		echo "-";
		escribeListaValores($nombreCampoAnio,2009,1900,$anio,-1);
	}
	//Ejecuta una consulta a la base de datos y devuelve el recurso de la consulta
	function fConsulta($consulta) {
		$host="localhost";
		$base="BD_VIDEOCLUB";
		$usuario="BD_VIDEOCLUB";
		$pass="videoclub2009"; 
		$c=mysql_connect($host,$base,$pass);
		if (!$c) {
			return false;
		}
		return mysql_db_query($base,$consulta);
	}
	//rellena una cadena con espacios por la derecha
	function espaciosDerecha($cadena,$tamFinal) {
		$tamActual=strlen($cadena);
		for($i=$tamActual;$i<=$tamFinal;$i++) {
			$cadena.="&nbsp;";
		}
		return $cadena;
	}
	//escribe un cuadro de selección múltiple
	function listaMultiple($consulta,$campo,$listaCamposMostrar,$size=5) {
		$res=fConsulta($consulta);
		if (!$res) {
			return false;
		}
		echo '<select multiple="multiple" name="',$campo,'[]" id="',$campo,'" size="',$size,'" >';
		while($filaReg=mysql_fetch_assoc($res)) {
			echo '<option value="',$filaReg[$listaCamposMostrar[0][0]],'">',"\n";
			for($i=1; $i<count($listaCamposMostrar);$i++) {
				echo espaciosDerecha($filaReg[$listaCamposMostrar[$i][0]],$listaCamposMostrar[$i][1]+1),"\n";
			}
			echo '</option>',"\n"; 
		}
		echo "</select>";
		mysql_free_result($res);
	}
	//Una cadena está en blanco 
	function esCadenaDeEspacios($cadena) {
		for($i=0; $i<strlen($cadena); $i++) {
			if ($cadena[$i]!=' ') {
				return false;
			}
		}
		return true;
	}
	//Una cadena representa un valor NULL de SQL?
	function esCadenaVacia($cadena) {
		return $cadena==null || strlen($cadena)==0 || esCadenaDeEspacios($cadena);
	}
	//imprime mensaje de error en rojo y tamaño de letra grande
	function error($cadena) {
		echo '<span style="color: red; font-size: 18px" >',$cadena,'</span>';
	}
	//imprime un enlace para volver atrás
	function volver() {
		echo '<br />';
		echo '<a href="javascript:history.back();">Volver</a>';
		echo '<br />';
	}
	//hace que se recargue la página cambiando el document.location mediante javascript
	function recargarPagina($url="?") {
		echo '<script language="javascript">document.location="',$url,'" </script>';
	}
	//Escribe un menú con enlaces con variables de url y la posibilidad de variar las leyendas
	//y títulos de los enlaces que aparecen
	function menu($listaEnlaces,$parametroMenu="opcion",$listaOpciones=null,$listaTitles=null) {
		echo '<ul>';
		for($i=0; $i<count($listaEnlaces); $i++) {
			echo '<li>';
			echo "<a href=\"?$parametroMenu=",$listaEnlaces[$i],'" ';
			if ($listaTitles!=null) {
				echo 'title="',$listaTitles[$i],'" ';
			}
			echo '>';
			if ($listaOpciones!=null) {
				echo $listaOpciones[$i];
			} else {
				echo $listaEnlaces[$i];
			}
			echo '</a>';
			echo '</li>';
		}
		echo '</ul>';
	} 
		
	//echo 'Géneros: <br />';
	//listaMultiple("SELECT * FROM GENERO ORDER BY nombre","generos",array(array('id',10),array('nombre',20)),5);
	//echo '<br />';
	//echo 'Actores: <br />';
	//listaMultiple("SELECT * FROM ACTOR_DIRECTOR ORDER BY nombre","actores",array(array('id',10),array('nombre',20)),5);
	//echo '<br />';
	//echo 'Directores: <br /> ';
	//listaMultiple("SELECT * FROM ACTOR_DIRECTOR ORDER BY nombre","actores",array(array('id',10),array('nombre',20)),5);
	//echo '<br />';

?>



	
	

