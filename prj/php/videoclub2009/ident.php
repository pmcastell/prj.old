<?php
	include_once("funcionesAuxiliares.php");
	if (!$_POST) {
?>
		<form id="formIdent" method="post" action="?opcion=identificarse">
			<table>
				<tr><th colspan="2" class="centro">Introduzca número de cliente y contraseña:</th></tr>
				<tr><td><label for="usuario">N&uacute;mero de cliente:</label></td>
				<td><input type="text" name="usuario" id="usuario" size="10" /></td>
				</tr>
				<tr>
				<td><label for="password">Contrase&ntilde;a:</label></td>
				<td><input type="password" name="password" id="password" /></td>
				</tr><tr>
				<td colspan="2" class="centro"><input type="submit" value="Enviar" /></td>
				</tr>
			</table>
		</form>
<?php
	} else {
		$usu=$_POST['usuario'];
		$pass=$_POST['password'];
		$res=fConsulta("SELECT clave FROM CLIENTE WHERE num_cli='$usu'");
		if ($res && mysql_num_rows($res)==1) {
			$fila=mysql_fetch_assoc($res);
			if ($fila['clave']==$pass && $usu==1) {
				$_SESSION['tipoUsuario']=0;
			} elseif ($fila['clave']==$pass) {
				$_SESSION['tipoUsuario']=1;
			}
			recargarPagina();
		} else {
			error("Usuario no encontrado.");
			volver();
		}
	}
?>