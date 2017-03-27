<?php
   if (isset($_SESSION['tipoUsuario'])) {
      $tipoUsu=$_SESSION['tipoUsuario'];
      if (!($tipoUsu=="Admin")) {
         echo "Hay pillín no tienes acceso a esta página.";
      } else {
         echo "Mantenimiento de Películas";
      } 
   } else {
      echo "No te has identificado en el sistema. Identíficate.";
   }
   
?>   