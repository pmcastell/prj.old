<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web</title>
   </head>
   <body>
   <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>sistema solar</title>
      <link href="capa.css" rel="stylesheet" type="text/css" />
   </head>
   <body>  
   <div class="cabecera">
   SISTEMA SOLAR
   </div> 
   <div class="izq">
      <h1>clasificacion de los planetas</h1><br />
         <ol>
	<li>
	<a href="?op=1"><img src="sol.jpeg" width="2%" height="2%"/>&nbsp;sol</a>
	</li>
	<br />
	<li>
	<a href="?op=2"><img src="mercurio.jpg" width="2%" height="2%"/>&nbsp;mercurio</a>
	</li>
	<br />
	<li>
	<a href="?op=3"><img src="venus.jpg" width="2%" height="2%"/>&nbsp;venus</a>
	</li>
	<br />
	<li>
	<a href="?op=4"><img src="tierra.jpg" width="10%" height="10%"/>&nbsp;tierra</a>
	</li>
	<br />
	<li>
	<a href="?op=5"><img src="marte.jpg" width="2%" height="2%"/>&nbsp;marte</a>
	</li>
	</ol>
	</div>
	<div class="der">
	<?php
	if (!isset($_GET['op']))
	{
		include_once('inicio.html');
	} 
	elseif($_GET['op']==1)
	{
		include_once('sol.php');
	}
	elseif ($_GET['op']==2)
	{
		include_once('mercurio.php');
	}
	elseif($_GET['op']==3)
	{
		include_once ('venus.php');
	}
		elseif($_GET['op']==4)
	{
		include_once ('tierra.php');
	}
		elseif($_GET['op']==5)
	{
		include_once ('martes.php');
	}
	?>
	</div>
	<div class="pie">
	Agustin Fdez
	</div>
   </body>
</html>
<?php 
?>
   </body>
</html>

