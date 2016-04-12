<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>Título de la web</title>
	<link href="documento.css" rel="stylesheet" type="text/css" />
   </head>
   	<body>
   		<form action="?opcion=formularioCuentaCorriente" method="post">
			<?php 
				include_once 'funcionesValidacion.php';
				validarCuentaCorriente();
			?>
		</form>
	</body>
</html>
