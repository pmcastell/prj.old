<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Formulario de alta de Clientes </title>
   </head>
   <body>
      <?php
         function validar($cuenta) {
            return true;
         } 
         if (!$_POST) {
      ?>
      <form id="altaClientesFarmacia" action="?" method="post">
      	<table border="1">
      		<tr><td><label for="nombre">Nombre:</label></td>
      		    <td><input type="text" name="nombre" id="nombre" size="35" maxlength="35" /></td>
      		</tr>
      		<tr><td><label for="ap1">Apellido1:</label></td>
      		    <td><input type="text" name="ap1" id="ap1" size="20" maxlength="20" /></td>
      		</tr>
      		<tr><td><label for="ap2">Apellido2:</label></td>
      		    <td><input type="text" name="ap2" id="ap2" size="20" maxlength="20" /></td>
      		</tr>
      		<tr><td><label for="cuenta">Cuenta:</label></td>
      			 <td><input type="text" id="cuenta" name="cuenta" size="20" maxlength="20" /></td>
      		</tr>
      		    <tr><td colspan="2"><input type="submit" value="Dar de Alta" /></td>
      		</tr>
      	</table>
      </form>
      <?php 
      	} else {
      		//procesar el formulario       
      		include_once("funcionesAuxiliares.php");
      		$nombre=$_POST['nombre']; $ap1=$_POST['ap1']; $ap2=$_POST['ap2']; 
      		$cuenta=$_POST['cuenta'];
      		if (!validar($cuenta)) {
      		   echo "Error en el número de cuenta ";
      		   echo '<a href=javascript:history.back();>Vuelve a intentarlo</a>';
      		} else {
      		   $result=consulta("SELECT * FROM CLIENTES WHERE nombre='$nombre' AND ap1='$ap1' AND ap2='$ap2' AND cuenta='$cuenta')");
      		   if (mysql_num_rows($result)==0) {
         		      
             		$result=consulta("INSERT INTO CLIENTES VALUES(NULL,'$nombre','$ap1','$ap2','$cuenta')",$conex);
            		if ($result!=0) {
            		   echo "Clientes insertado $nombre $ap1 $ap2 correctamente.";
            		   
            		} else {
            		   //Tratamiento del error
            		}
      		   } else {
      		      echo "El cliente $nombre $ap1 $ap2 ya estaba en la base de datos.";
      		   }
            	
      		   //mysql_free_result($result);
      		   mysql_close($conex);
      		}
         }
      ?>
   </body>
</html>