<?php
	function busca($cadena,$c) {
		for($i=0;$i<strlen($cadena);$i++) {
			if ($cadena[$i]==$c) {
				return $i;
			}
		}
		return -1;
	}
	function buscaCadena($cad1,$cad2,$pos=0) {
		for($i=$pos;$i<strlen($cad1);$i++) {
			if ($cad1[$i]==$cad2[0]) {
				for($j=1;$j<strlen($cad2) && $i+$j<strlen($cad1);$j++) {
					if ($cad1[$i+$j]!=$cad2[$j]) {
						break;
					}
				}
				if ($j>=strlen($cad2)) {
					return $i;
				}
			}
		}
		return -1;
	}
	
	
	echo busca("hola","a"),'<br />';
	echo busca("hola","e"),'<br />';
	echo buscaCadena("hola t que tal como estas, creo que estas bien","tas");
	echo buscaCadena("hola t que tal como estas, creo que estas bien","esta no está");
	echo buscaCadena("hola t que tal como estas, creo que estas bien","tas",23);
	//Formularios reentrantes
	//Cookies
	
	
	
	
	
?>	