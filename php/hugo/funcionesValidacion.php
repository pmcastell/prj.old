<!-- Poner tildes en JavaScript -->
<!-- 
Escribirlos en unicode.
\u00e1 -> á
\u00e9 -> é
\u00ed -> í
\u00f3 -> ó
\u00fa -> ú

\u00c1 -> Á
\u00c9 -> É
\u00cd -> Í
\u00d3 -> Ó
\u00da -> Ú

\u00f1 -> ñ
\u00d1 -> Ñ
 -->
<?php
	//Función que imprime cuadro de texto DNI para validar.
	function cuadroTextoValDni($name="dni",$label="D.N.I.:", $obligatorio=1){
		static $primeraVez=true;
		if($primeraVez){
		?>
		<script type="text/javascript" language="javascript">
			function validarDni(dni,obligatorio){
				if(obligatorio==0 && dni.value==""){
					return true;
				}else if(obligatorio==1 && dni.value==""){
					alert("Este campo no se pueder quedar en blanco.");
					dni.focus();
					return false;
				}
				expReg=new RegExp("^[1-9][0-9]{0,7}[a-zA-Z]$");
				//expReg=/^[1-9][0-9]{0,7}[a-zA-Z]$/;// Equivalente a lo de arriba, indica que es una expresión regular.

				if(!expReg.test(dni.value)){ //Comprueba el formato del DNI introducido, si no es correcto devulve falso y foco al cuadro de texto.
					alert("El D.N.I: "+dni.value+" no tiene un formato v\u00e1lido");
					dni.focus();
					return false;
				}

				//Si llegamos aquí, formato correcto.
				digitos=dni.value.substring(0,dni.value.length-1); // Coge desde la posición 0 hasta la posición anterior a lenght-1=longitud 9-1
				letra=dni.value.charAt(dni.value.length-1); // Segundo índice opcional, y entonces te dice a partir de que posición coger la cadena.
				letra=letra.toUpperCase();
				if(letra!="TRWAGMYFPDXBNJZSQVHLCKE".charAt(digitos%23)){ // charArt Devuelve carácter de la posición a la que apunta.
					alert("Error en la letra del D.N.I.: "+dni.value);
					dni.focus();
					return false;
				}
				return true;
			}
		</script>
		<?php 
			$primeraVez=false;
		}
		echo '<tr><td><label for="',$name,'">',$label,'</td></label>';
		echo '<td><input type="text" name="',$name,'" id="',$name,'" maxlength="9" 
		onblur="validarDni(document.getElementById(\'',$name,'\'),',$obligatorio,');"/>'; //Llamada a la función javascript aquí.
		echo '</td></tr>';
	}
	
	// Función Validar Cuenta Corriente
	
	function validarCuentaCorriente($name="CuentaCorriente",$label="Cuenta Corriente:"){
		static $primeraVez=true;
		if($primeraVez){
		?>
		<!-- Lo recomendado es calcular los dígitos del dc en una función y compararlo con los introducidos con otra. -->
		<script type="text/javascript" language="javascript">
			function validarCC(entidad,sucursal,dc,cc){

				// Valido que ningún camppo esté vacío.
				if(entidad.value==""){
					
					alert("Por favor, rellene el campo entidad.");
					entidad.focus();
					return false;
					
				}else if(sucursal.value==""){

					alert("Por favor, rellene el campo sucursal.");
					sucursal.focus();
					return false;

				}else if(dc.value==""){

					alert("Por favor, rellene el campo DC.");
					dc.focus();
					return false;

				}else if(cc.value==""){

					alert("Por favor, rellene el campo CC.");
					cc.focus();
					return false;

				}

				//Validar que los campos tenga el formato correcto
				entval=new RegExp("^[0-9]{4}$");// ^ indica principio expresión debe empezar en este caso por dígito no en blanco.
				sucval=new RegExp("^[0-9]{4}$");// $ indica lo mismo que ^ pero al final de la cadena.
				dcval=new RegExp("^[0-9]{2}$");
				ccval=new RegExp("^[0-9]{10}$");

				if(!entval.test(entidad.value)){
					
					alert("La Entidad: "+entidad.value+" no tiene un formato v\u00e1lido");
					entidad.focus();
					return false;
					
				}else if(!sucval.test(sucursal.value)){

					alert("La Sucursal: "+sucursal.value+" no tiene un formato v\u00e1lido");
					sucursal.focus();
					return false;

				}else if(!dcval.test(dc.value)){

					alert("El DC: "+dc.value+" no tiene un formato v\u00e1lido");
					dc.focus();
					return false;

				}else if(!ccval.test(cc.value)){

					alert("La CC: "+cc.value+" no tiene un formato v\u00e1lido");
					cc.focus();
					return false;

				}

				//Relleno el array que lleva los números a multiplicar
				ccMultiplicar=[10,9,7,3,6,1,2,4,8,5];
				campo4=[0,0,0,0];

				//Relleno el array con los números recibidos del campo CC
				for(i=0;i<ccMultiplicar.lenght;i++){
					campo4[i]=cc.value.charAt(i);
				}

				//Voy multiplicando un array con otro y sumando los resultados
				sum=0;
				
				for(i=0;i<10;i++){
					sum=sum+(campo4[i]*ccMultiplicar[i]);
				}

				//El resultado de la suma lo divido entre 11 y cogo el resto y es el resultado del segundo dígito del DC
				if(sum%11!=10){
					digito2=sum%11;
				}else{
					digito2=1;
				}

				//Compruebo si es correcto el dígito dado por el usuario
				if(dc.value.substring(1)!=digito2){
					alert("El DC: "+dc.value+" no es correcto.");
					dc.focus();
					return false;
				}

				//Mismo proceso con los otros dos campos restantes
				entidadMultiplicar=[7,3,6,1];
				sucursalMultiplicar=[2,4,8,5];
				campo1=[0,0,0,0];
				campo2=[0,0,0,0];
				
				for(i=0;i<4;i++){
					campo1[i]=entidad.value.charAt(i);
					campo2[i]=sucursal.value.charAt(i);
				}
				
				sum1=0;
				sum2=0;
				
				for(i=0;i<4;i++){
					sum1=sum1+(campo1[i]*entidadMultiplicar[i]);
					sum2=sum2+(campo2[i]*sucursalMultiplicar[i]);
				}

				sum=sum1+sum2;
	
				if(sum%11!=10){
					digito1=sum%11;
				}else{
					digito1=1;
				}

				
				if(dc.value.charAt(0)!=digito1){
					alert("El DC: "+dc.value+" no es correcto.");
					dc.focus();
					return false;
				}
				return true;
			}
		</script>
		<?php
			$primeraVez=false;
		}
		echo '<table border="1">';
			echo '<tr>';
				echo '<th rowspan="2">',$label,'</th>';
				echo '<th><label for="',$name,'Entidad">Entidad</label></th>';
				echo '<th><label for="',$name,'Sucursal">Sucursal</label></th>';
				echo '<th><label for="',$name,'DC">DC</label></th>';
				echo '<th><label for="',$name,'CC">CC</label></th>';
			echo '</tr>';
			echo '<tr>';
				echo '<td><input type="text name="',$name,'Entidad" id="',$name,'Entidad" maxlength="4" /></td>';
				echo '<td><input type="text name="',$name,'Sucursal" id="',$name,'Sucursal" maxlength="4" /></td>';
				echo '<td><input type="text name="',$name,'DC" id="',$name,'DC" maxlength="2" 
						onblur="validarCC(
												document.getElementById(\'',$name,'Entidad\'),
												document.getElementById(\'',$name,'Sucursal\'),
												document.getElementById(\'',$name,'DC\'),
												document.getElementById(\'',$name,'CC\'));"
						/></td>';
				echo '<td><input type="text name="',$name,'CC" id="',$name,'CC" maxlength="10" /></td>'; 
			echo '</tr>';
		echo '</table>';
	}
	
	
	
	//Función que imprime mensaje de error
	function error($mensaje="Error",$errorMysql=true){
		echo '<span style="color:red; font-size:12pt;">',$mensaje,'<br />';
		if($errorMysql){
			echo mysql_errno(),': ',mysql_error();
		}
		echo '</span>';
	}
	
	//Función que indica a la función de error si el mensaje tiene que ser de MySQL o no.
	function errorNormal($mensaje){
		error($mensaje,false);
	}
	
	//Insertar función de acceso
	
	//Función Cuenta Bancaria
	//Primer cuadro de texto[0-9]{4}
?>
