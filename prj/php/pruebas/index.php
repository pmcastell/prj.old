<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web </title>
      <script>
         function prueba() {
             alert("hola");
         }
      </script>
   </head>
   <body>
      <div>
		<form action="datos.php" method="post">
			<p>
				<label for="nombre">Nombre:</label>
				<input type="text" name="nombre" id="nombre" />
				<input type="submit" name="enviar" value="Enviar" />
			</p>
		</form>
      </div>
   </body>
</html>