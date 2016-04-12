<?php
  session_start();       
  function esUsuario($usuario) {
     return isset($_SESSION['tipoUsuario']) && $_SESSION['tipoUsuario']==$usuario;
  }   
?>
