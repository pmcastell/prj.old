<?
session_start();

$salir=$_GET['destroy'];
if($salir=='destroy'){
	session_destroy();
}
?>