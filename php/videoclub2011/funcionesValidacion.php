<!-- 
1. Escribir cuadro de texto para pedir con función javascript de validación
2.Código cuentas corrientes
3.Guardar los datos de un formulario para cuando se vuelva al formulario
4.Pedir fecha con fecha actual seleccionada por defecto
5.Pedir campo que es clave ajena con la lista de valores permitidos
 -->

<?php

   function cuadroTextoValDni($name="dni",$label="D.N.I.:",$obligatorio=1) {
      static $primeraVez=true;
      
      if ($primeraVez) { ?>
         <script type="text/javascript" language="javascript">
         	function validarDni(dni,obligatorio) {
             	if (obligatorio==0 && dni.value=="") {
                 	return true;
             	} else if (obligatorio==1 && dni.value=="") {
                 	alert("Este campo no se puede quedar en blanco");
                 	dni.focus();
                 	return false;
             	}
         		exprReg=new RegExp("^[1-9][0-9]{0,7}[a-zA-Z]$");
               if (!exprReg.test(dni.value)) {
                   alert("El D.N.I.: "+dni.value+" no tiene un formato Válido");
                   dni.focus();
                   return false;
               }
               digitos=dni.value.substring(0,dni.value.length-1);
               letra=dni.value.substring(dni.value.length-1);
               if (letra.toUpperCase()!="TRWAGMYFPDXBNJZSQVHLCKE".charAt(digitos % 23)) {
                   alert("Errro en la letra del D.N.I.: "+dni.value);
                   dni.focus();
                   return false;
               }
               return true;
         	}	
         </script> 
         <?php
         $primeraVez=false; 
      }
      echo '<tr><td><label for="',$name,'">',$label,'</label></td>';
      echo '<td><input type="text" name="',$name,'" id="',$name,'" maxlength="9" 
             onblur="validarDni(document.getElementById(\'',$name,'\'),',$obligatorio,');" />';
      echo '</td></tr>';
   }
   function cuadroTextoValCC($name="cuentaCorriente",$label="Cuenta Corriente: ",$obligatorio=1) {
      static $primeraVez=true;
      if ($primeraVez) {
      ?>
      	<script language="javascript" type="text/javascript">
            function calculaDigitosControl(entidad,sucursal,cuenta) {
                pesos=[1,2,4,8,5,10,9,7,3,6];
                entSuc="00"+entidad+sucursal;
                d1=d2=0;
                for(i=0;i<pesos.length;i++) {
                    d1+=entSuc[i]*pesos[i];
                    d2+=cuenta[i]*pesos[i];
                }
                d1=11 - d1 %11; d2=11 - d2 %11;
                if (d1 == 11) { d1=0; } else if (d1==10) { d1=1; }
                if (d2 == 11) { d2=0; } else if (d2==10) { d2=1; }
                return ""+d1+d2;
            }
         	function validarCuenta(entidad,sucursal,dc,cuenta,obligatorio) {
             	 if (obligatorio!=1 && entidad.value=="" && sucursal.value=="" && dc.value=="" 
                 	     && cuenta.value=="") {
             	    return true;
             	 } /*else  if (obligatorio==1 && ( entidad.value=="" || sucursal.value=="" || dc.value==""
                 	    || cuenta.value=="")) {
              	    alert("Todos los campos de la cuenta bancaria son obligatorios.");
              	    entidad.focus();
              	    return false;
             	 }*/
         		 exprReg=new RegExp("^[0-9]{20}$");
                if (!exprReg.test(entidad.value+sucursal.value+dc.value+cuenta.value)) {
                    alert("Cuenta no válida.");
                    entidad.focus();
                    return false;
                }
             	 if (dc.value != calculaDigitosControl(entidad.value,sucursal.value,cuenta.value)) {
                 	 alert("Cuenta no válida: los dígitos de control no concuerdan.");
                 	 return false;
             	 } 
   				 return true;
         	}
      	</script>
      <?php 
      }
      ?>
      <tr>
         <?php
            echo "<td><label>$label</label></td>"; 
         ?>
         <td>
         	<table border="1">
         	   <tr><th>Entidad</th><th>Sucursal</th><th>D.C.</th><th>N&ordm;.Cuenta</th></tr>
         	   <tr>
         	       <td>
         	       <?php 
         	       echo '<input size="4" type="text" name="',$name,'[\'Entidad\']" id="',$name,'Entidad" maxlength="4" /> </td>';
         	       echo '<td>';
         	       echo '<input size="4" type="text" name="',$name,'[\'Sucursal\']" id="',$name,'Sucursal" maxlength="4" /> </td>';
         	       echo '<td>'; 
         	       echo '<input size="2" type="text" name="',$name,'[\'DC\']" id="',$name,'DC" maxlength="2" /> </td>';
                   echo '<td>'; 
         	       echo '<input size="10" type="text" name="',$name,'[\'Cuenta\']" id="',$name,'Cuenta" maxlength="10" onblur="validarCuenta(
         	       		document.getElementById(\'',$name,'Entidad\'),
         	       		document.getElementById(\'',$name,'Sucursal\'),
         	       		document.getElementById(\'',$name,'DC\'),
         	       		document.getElementById(\'',$name,'Cuenta\'),'
         	       		,$obligatorio,');" /> 
         	       		</td>';
         	       ?>
         	   </tr>
         	</table>
         </td>
      </tr>
      <?php 
   }
   ?>

  
   <!-- 
   <form action="?opcion=formularioEntradaClientes" method="post">
      <table border="1">
      <?php /*cuadroTextoValDni("dniPersona","D.N.I. Clientes:");
            pedirFecha("fechaNac","Fecha de Nacimiento: ");
            cuadroTextoValCC("ccCliente","Cuenta Corriente Cliente: ",0);*/
            
          
      ?>

      </table>
   </form> 
   
    -->
   
