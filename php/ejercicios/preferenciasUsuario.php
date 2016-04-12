<?php
		//setcookie('SistemaOp',$SistemaOp,time()-3600);
		//setcookie('Navegador',$Navegador,time()-3600);
		//setcookie('Lenguaje',$Lenguaje,time()-3600);
	if ($_POST) {
		$SistemaOp=$_POST['SistemaOp'];
		$Lenguaje=$_POST['Lenguaje'];
		$Navegador=$_POST['Navegador'];
		setcookie('SistemaOp',$SistemaOp,time()+86400*365*10); $_COOKIE['SistemaOp']=$SistemaOp;
		setcookie('Navegador',$Navegador,time()+86400*365*10); $_COOKIE['Navegador']=$Navegador;
		setcookie('Lenguaje',$Lenguaje,time()+86400*365*10); $_COOKIE['Lenguaje']=$Lenguaje;
	} 
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web </title>
   </head>
   <body>
      <?php 
      if (!isset($_COOKIE['SistemaOp']) && !$_POST) {
      	//mostrar formulario
      ?>

      <form id="prefUsuario" action="?" method="post">
      <p>
      	<label for="SistemaOp">Sistema Operativo:</label>
      	<select name="SistemaOp" id="SistemaOp">
      		<option>Windows</option>
      		<option>Linux</option>
      		<option>Solaris</option>
      	</select>
      	<br />
      	<label for="Navegador">Navegador:</label>
      	<select name="Navegador" id="Navegador">
      		<option>Firefox</option>
      		<option>Explorer</option>
      		<option>Opera</option>
      	</select>
      	<br />
      	<label for="Lenguaje">Lenguaje:</label>
      	<select name="Lenguaje" id="Lenguaje">
      		<option>C</option>
      		<option>Java</option>
      		<option>PHP</option>
      	</select>
      	<br />
      	<input type="submit" value="Guardar Preferencias" />
      </p>	
      </form>
      
      <?php 	
      } else {
      	//mostrar las preferencias
			echo '<table border="1">';
			echo '<caption>Tus Preferencias</caption>';
			echo '<tr><td>Sistema Operativo:</td><td>',$_COOKIE['SistemaOp'],'</td></tr>';
			echo '<tr><td>Navegador:</td><td>',$_COOKIE['Navegador'],'</td></tr>';
			echo '<tr><td>Lenguaje:</td><td>',$_COOKIE['Lenguaje'],'</td></tr>';
      }
      ?>
   </body>
</html>