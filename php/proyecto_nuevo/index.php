<?php session_start();?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>VideoClub TodoPeliculas</title>
	<link href="estilo/estilo.css" rel="stylesheet" type="text/css" media="screen" />
</head>

<body>

<!-- empieza cabecera-->
<div id="header">
	<div id="logo">
		<h1><a href="index.php?destroy=destroy">VideoClub</a></h1>
		<p>By: Angel Bejarano Moreno</p>
	</div>
	<div id="menu">
		<?php
			//Sesiones
			if(isset($_SESSION['tipo_usuario'])){
				if($_SESSION['tipo_usuario']==0){
					include_once("menu_sup/admin_index.html");//Administrador
					if(isset($_GET['opcion_ad'])){
						if ($_GET['opcion_ad']=="clientes"){
							include_once("menu_sup/admin_cliente.html");
						}elseif ($_GET['opcion_ad']=="actor"){
							include_once("menu_sup/admin_actor_director.html");
						}elseif ($_GET['opcion_ad']=="alquiler"){
							include_once("menu_sup/admin_alquiler.html");
						}elseif ($_GET['opcion_ad']=="devolucion"){
							include_once("menu_sup/admin_devolucion.html");
						}elseif ($_GET['opcion_ad']=="peliculas"){
							include_once("menu_sup/admin_pelicula.html");
						}elseif ($_GET['opcion_ad']=="generos"){
							include_once("menu_sup/admin_genero.html");
						}elseif ($_GET['opcion_ad']=="tarifas"){
							include_once("menu_sup/admin_tarifa.html");
						}elseif ($_GET['opcion_ad']=="informes"){
							include_once("menu_sup/admin_informe.html");
						}
					}
				}
				else{
					include_once("menu_sup/cliente_index_inicio.html");//Cliente
				}
				if (isset($_GET['destroy']) && $_GET['destroy']=="destroy") {
					session_destroy();
					echo '<script language="javascript">document.location="?"</script>';
					exit();
				}
				
			}
			else{//Sino existe variable de sesion - Usuario Sin Privilegios
				if(isset($_GET['opcion'])){
					if ($_GET['opcion']=="catalogo"){
						include_once("menu_sup/index_catalogo.html");
					}
				elseif ($_GET['opcion']=="novedades"){
					include_once("menu_sup/index_novedades.html");
					}
				}
				else{
					include_once("menu_sup/index_inicio.html");
				}
			}

		?>
	</div>
</div>
<!-- termina cabecera-->

<!-- empieza página -->
<div id="page">

	<!-- empieza menu izquierda -->
	<div id="leftbar" class="sidebar">
		<?php
		//Sesiones
		if(isset($_SESSION['tipo_usuario'])){
			if($_SESSION['tipo_usuario']==0){
				include_once("menu_izq/admin_index.php");//Administrador
				if(isset($_GET['opcion_ad'])){
					if ($_GET['opcion_ad']=="clientes"){
						include_once("menu_izq/admin_cliente.html");
					}elseif ($_GET['opcion_ad']=="actor"){
						include_once("menu_izq/admin_actor_director.html");
					}elseif ($_GET['opcion_ad']=="alquiler"){
						include_once("menu_izq/admin_alquiler.html");
					}elseif ($_GET['opcion_ad']=="devolucion"){
						include_once("menu_izq/admin_devolucion.html");
					}elseif ($_GET['opcion_ad']=="peliculas"){
						include_once("menu_izq/admin_pelicula.html");
					}elseif ($_GET['opcion_ad']=="generos"){
						include_once("menu_izq/admin_genero.html");
					}elseif ($_GET['opcion_ad']=="tarifas"){
						include_once("menu_izq/admin_tarifa.html");
					}elseif ($_GET['opcion_ad']=="informes"){
						include_once("menu_izq/admin_informe.html");
					}
				}
			}
			else{
				include_once("menu_izq/cliente_index.html");//Cliente
			}
		}
		else{//Sino existe variable de sesion - Usuario Sin Privilegios
			if(isset($_GET['opcion'])){
				if ($_GET['opcion']=="catalogo"){
					include_once("menu_izq/catalogo_index.php");
				}
			elseif($_GET['opcion']=="novedades"){
				include_once("menu_izq/novedades_index.php");
				}
			}
			else{
				include_once("menu_izq/index.php");
			}
		}
		?>
	</div>
	<!-- termina menu izquierda -->
	
	<!-- sempieza contenido -->
	<div id="content">
		<?php
		//Sesiones
		if(isset($_SESSION['tipo_usuario'])){
			if($_SESSION['tipo_usuario']==0){
				include_once("principales/admin_index.html");//Administrador
				if(isset($_GET['opcion_ad'])){
					if ($_GET['opcion_ad']=="clientes"){
						include_once("principales/admin_cliente.html");
					}elseif ($_GET['opcion_ad']=="actor"){
						include_once("principales/admin_actor_director.html");
					}elseif ($_GET['opcion_ad']=="alquiler"){
						include_once("principales/admin_alquiler.html");
					}elseif ($_GET['opcion_ad']=="devolucion"){
						include_once("principales/admin_devolucion.html");
					}elseif ($_GET['opcion_ad']=="peliculas"){
						include_once("principales/admin_pelicula.html");
					}elseif ($_GET['opcion_ad']=="generos"){
						include_once("principales/admin_genero.html");
					}elseif ($_GET['opcion_ad']=="tarifas"){
						include_once("principales/admin_tarifa.html");
					}elseif ($_GET['opcion_ad']=="informes"){
						include_once("principales/admin_informe.html");
					}
				}
			}
			else{
				include_once("principales/cliente_index.html");//Cliente
			}
		}
		else{//Sino existe variable de sesion - Usuario Sin Privilegios
			if(isset($_GET['opcion'])){
				if ($_GET['opcion']=="catalogo"){
					include_once("procesos/catalogo.php");
				}
			elseif($_GET['opcion']=="novedades"){
				include_once("procesos/novedades.php");
				}
			}
			else{
				include_once("principales/index.html");
			}
		}
		?>
	</div>
	<!-- termina contenido -->
	
	<!-- empieza menu derecha -->
	<div id="rightbar" class="sidebar">
		<?php
			include("menu_der/novedades_alquiladas.php");
		?>
	</div>
	<!-- termina menu derecha -->
<br>
</div>
<!-- termina página -->
<hr />

<!-- empieza pie -->
<div id="footer">
	<?php
		include("pie/pie.html");
	?>
</div>
<!-- termina pie -->

</body>
</html>