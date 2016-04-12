<?php
   include_once("funcionesValidacion.php");
?>
<form action="?opcion=altaPersonas" method="post">
	<table border="1">
<?php cuadroTextoValDni("dniPersona","D.N.I. Persona:")?>
	<!-- Nombre, apellidos..... -->
	<?php cuadroTextoValDni("dniCabeza","D.N.I. Cabeza Familia: ",0)?>
	<?php cuadroTextoValDni("dniCabeza2","D.N.I. Cabeza Familia2: ",0)?>
	</table>
</form>