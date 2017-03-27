<?
SESSION_START();

$salir=$_GET['destroy'];
if($salir=='destroy'){
	session_destroy();
	echo "<script languaje=\"JavaScript\">document.location=\"index.php\"</script>";
}
?>	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Videoclub</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<link href="temas/azul/style.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
	<div id="contenedor">
	<div id="cabecera">
		<div id="cabecera_izq">
			<div id="titulo_videoclub"><a href='index.php?destroy=destroy'>Videoclub</a><br />/*Texto*/</div>
		</div>
		<div id="cabecera_derc">
			<div id='buscar'>
				<form id='formulario_buscar' action='index.php?buscar=1' method='post'>
					<div>
					<input type="text" name="busqueda" id="busqueda" size="30" class="input_formulario" value="Titulo de la pelicula.." onFocus="if(this.value=='Titulo de la pelicula..')this.value='';" onBlur="if(this.value=='')this.value='Titulo de la pelicula..'">
					<input class='boton_buscar' type='submit' value=' ' />
					</div>
				</form>
			</div>
		</div>
	</div>
	<div id="principal">
		<div id="izquierda">
			<?
			//Sesiones
			if(isset($_SESSION['tipo_usuario'])){
				if($_SESSION['tipo_usuario']==0){
					include_once("menu_admin.php");//Administrador
				}
				else{
					include_once("menu_cliente.php");//Cliente
				}
			}
			else{//Sino existe variable de sesion - Usuario Sin Privilegios
				if(isset($_GET['completo'])){
					echo"<h1><span class='link'><a href='index.php'>Inicio</a> -> Catalogo</span></h1>";
					include_once("catalogo.php");
				}
				elseif(isset($_GET['buscar'])){
					echo"<h1><span class='link'><a href='index.php'>Inicio</a> -> Busqueda</span></h1>";
					include_once("buscar.php");
				}
				else{
					echo"<h1><a href='index.php?completo=1'>Catalogo Completo</a></h1>";
					include_once("peliculas.php");
				}
			}
			?>
		</div>
		<div id="derecha">
			<?
				if(isset($_SESSION['tipo_usuario'])){
					if($_SESSION['tipo_usuario']==0){
						include_once("derecha_admin.php");/*Administrador*/
					}
					else{
						include_once("derecha_cliente.php");/*Cliente*/
					}
				}
				else{
					include_once("derecha_usuario.php");//Usuario - Aparece Formulario Login
				}
			?>
		</div>
	</div>
	<div style="clear: both"><br /></div>
		<div id="imagen_pie">
			<div id="mi_nombre">
				By: Daniel Ardila Ayala
			</div>
		</div>
	</div>
	</body>
</html>