<?php
	include_once 'funcionesValidacion.php';
	if(acceso("Admin")){
		if(!$_POST){
		?>
			<form action="?opcion=altaClientes" method="post">
				<table border="1">
					<?php cuadroTextoValDni("dniCliente")?>
					<tr>
						<td><label for="nombre">Nombre</label></td>
						<td><input type="text" name="nombre" id="nombre" maxlength="30" /></td>
					</tr>
					<!-- Queda formulario por hacer -->
				</table>
			</form>
		<?php 
		}else{
			$dni=$_POST['dni'];
			$nombre=$_POST['nombre'];
			//TODO: validar(...);
			$r=consulta("INSERT INTO CLIENTES VALUES($dni,$nombre....)");
		}
	}else{
		//Acceso no autorizado
	}
?>
