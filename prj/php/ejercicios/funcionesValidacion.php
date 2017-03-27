<?php
   function letraNif($dni) {
   	return substr("TRWAGMYFPDXBNJZSQVHLCKEI",$dni % 23,1);
   }
   
   function validarNif($nif) {
   	$lon=strlen($nif);
   	if ($lon==0) {
   		return false;
   	}
   	$dni=sustr($nif,0,$lon-1);
   	$letra=substr($nif,$lon-1,1);
   	if (!is_numeric($dni)) {
   		return false;
   	}
   	return letraNif($dni)==strtoupper($letra);
   }
   function cerosIzda($valor,$longitudTotal) {
   	$cadenaAux="".$valor;
   	while(strlen($cadenaAux)<$longitudTotal) {
   		$cadenaAux="0".$cadenaAux;
   	}
   	return $cadenaAux;
   }
   function digitosControl($banco,$sucursal,$cuenta) {
   	//$pesos=array(6,3,7,9,10,5,8,4,2,1);
   	$pesos=array(1,2,4,8,5,10,9,7,3,6);
   	$banco=cerosIzda($banco,4);
   	$sucursal=cerosIzda($sucursal,4);
   	$bancSuc=cerosIzda($banco.$sucursal,10);
   	$cuenta=cerosIzda($cuenta,10);
   	$dig1=0;
   	$dig2=0;
   	for($i=0; $i<=9; $i++) {
   		$dig1+=$bancSuc[$i]*$pesos[$i];
   		$dig2+=$cuenta[$i]*$pesos[$i];
   	}
   	$dig1=11-($dig1 % 11);
   	if ($dig1==10) {
   		$dig1=1;
   	} elseif($dig1==11) {
   		$dig1=0;
   	}
   	$dig2=11-($dig2 % 11);
      if ($dig2==10) {
         $dig2=1;
      } elseif($dig1==11) {
         $dig2=0;
      }
      return $dig1.$dig2;   	
   }
   digitosControl(2100,150,200455826);//cuidado con los números que empiezan por 0 se consideran en octal (base 8)
   digitosControL(123,4567,123456789);//cuidado con los números que empiezan por 0 se consideran en octal (base 8)

   
?>