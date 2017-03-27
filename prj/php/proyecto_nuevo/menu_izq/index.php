<?
	if(!$_POST){//Muestra Formulario
		formulario();
	}
	else{//Consulta datos del usuario
		consulta();
	}
	function formulario(){
		echo "<form id='searchform' action='index.php' method='post'>";
		echo "<h2>Iniciar Sesion</h2>";
		echo '<div>';
			echo'<ul>';
				echo'<li>';
					echo'<form id="searchform" method="post" action="index.php">';
						echo'<div>';
							echo'Usuario: <input type="text" name="usuario" size="20"/ value="">';
							echo'<br />';
							echo'Contraseña: <input type="password" name="pass" size="20" value=""/>';
							echo'<br />';
							echo'<input type="submit" value="Login" />';
						echo'</div>';
					echo'</form>';
				echo'</li>';
			echo'</ul>';
		echo '</div>';
		echo "</form>";
	}
	function consulta(){
		//conectamos con la base de datos
		$c=@mysql_connect("localhost", "root", "root") or die ("Fallo en la conexion");
		@mysql_select_db ("BD_VIDEOCLUB", $c) or die ("Error de conexion");
		
		$usuario=$_POST['usuario'];
		//comprobacion
		$comprobacion=mysql_query("SELECT * FROM CLIENTE WHERE num_cli='$usuario'",$c);
		if(mysql_num_rows($comprobacion)==0){
			formulario();
			echo "No existe el numero de usuario";
		}
		else{
			$res=mysql_fetch_assoc($comprobacion);
			
			$id=$res['num_cli'];
			$clave=$res['clave'];
			
			if($clave==$_POST['pass']){
				if($id==1){
					$_SESSION['tipo_usuario']=0;
					echo '<script language="JavaScript">document.location="?"</script>';
					exit();
				}
				else{
					$_SESSION['tipo_usuario']=1;
					echo '<script language="JavaScript">document.location="?"</script>';
					exit();
				}
			}
			else{
				formulario();
				echo "Contraseña Incorrecta.";
			}
		}
		mysql_close($c);
	}
?>
