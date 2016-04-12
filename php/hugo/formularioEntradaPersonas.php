<?php
		include_once 'funcionesValidacion.php';
?>
<form action="?opcion=altaPersonas" method="post">
	<table border="1">
		<?php 
			cuadroTextoValDni("dniPersona","D.N.I. Persona:");
			cuadroTextoValDni("dniCabeza","D.N.I. Cabeza de Familia:",0);
		?>
	</table>
</form>
